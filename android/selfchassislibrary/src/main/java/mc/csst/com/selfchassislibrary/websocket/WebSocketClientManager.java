package mc.csst.com.selfchassislibrary.websocket;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;

import java.util.concurrent.TimeUnit;

import mc.csst.com.selfchassislibrary.content.WebSocketContent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/5
 * @brief websocket客户端Manager
 * @Description
 */
public class WebSocketClientManager {

    private static final String TAG = WebSocketClientManager.class.getSimpleName();

    /**
     * 重连时间 单位：毫秒
     */
    private static final int MILLIS = 5000;
    /**
     * 当前实例
     */
    private static volatile WebSocketClientManager mInstance;

    private OkHttpClient mClient;

    private Request mRequest;

    private IReceiveMessage mIReceiveMessage;

    private WebSocket mWebSocket;
    /**
     * 连接状态
     * true：连接 false：断开
     */
    private boolean mIsConnect = false;

    /**
     * 最大重连次数
     */
    private int mMaxNum = 5;

    /**
     * 当前重连次数
     */
    private int mConnentNum = 0;

    private WebSocketClientManager() {
    }

    /**
     * 获取当前实例
     */
    public static WebSocketClientManager getInstance() {
        if (mInstance == null) {
            synchronized (WebSocketClientManager.class) {
                if (mInstance == null) {
                    mInstance = new WebSocketClientManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化数据
     *
     * @param url             连接地址
     * @param iReceiveMessage 接收消息接口
     */
    public void init(String url, IReceiveMessage iReceiveMessage) {
        //初始化数据
        mClient = new OkHttpClient.Builder()
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        mRequest = new Request.Builder().url(url).build();
        mIReceiveMessage = iReceiveMessage;
        //连接
        connect();
    }

    /**
     * 连接
     */
    public void connect() {
        if (mIsConnect) {
            Log.i(TAG, "web socket connected");
        } else {
            mClient.newWebSocket(mRequest, createListener());
        }
    }

    /**
     * 重连
     */
    public void reconnect() {
        if (mConnentNum <= mMaxNum) {
            SystemClock.sleep(MILLIS);
            connect();
            mConnentNum++;
        } else if (mMaxNum == -1) {
            SystemClock.sleep(MILLIS);
            connect();
        } else {
            Log.i(TAG, "reconnect over " + mMaxNum + ",please check url or network");
        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (mIsConnect) {
            mWebSocket.cancel();
            mWebSocket.close(WebSocketContent.CLOSE_NORMAL, "客服端主动关闭");
            ToastUtils.showShort("客服端主动关闭");
        }
    }

    /**
     * 发送消息
     *
     * @param text 发送的消息
     * @return true：成功 false：失败
     */
    public boolean sendMessage(String text) {
//        Log.e(TAG, text);
        if (!mIsConnect) {
            return false;
        }
        return mWebSocket.send(text);
    }

    /**
     * 发送消息
     *
     * @param text 发送的消息
     * @return true：成功 false：失败
     */
    public boolean sendMessage(ByteString text) {
        if (!mIsConnect) {
            return false;
        }
        return mWebSocket.send(text);
    }

    /**
     * 是否连接
     *
     * @return true：成功 false：失败
     */
    public boolean isConnect() {
        return mWebSocket != null && mIsConnect;
    }

    private class MyWebSocketListener extends WebSocketListener {
        /**
         * 当WebSocket和远程建立连接时回调
         */
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mWebSocket = webSocket;
            Log.i(TAG, "Connect:" + response.code());
            mIsConnect = response.code() == 101;
            if (mIsConnect) {
                Log.d(TAG, "WebSocket Connect Success");
                if (mIReceiveMessage != null) {
                    mIReceiveMessage.onConnectSuccess();
                }
            } else {
                reconnect();
            }
        }

        /**
         * 接收到消息时回调
         */
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
//            Log.d(TAG, "WebSocket onMessage(text):" + text);
            if (!TextUtils.isEmpty(text)) {
                mIReceiveMessage.onMessage(text);
            }
        }

        /**
         * 接收到消息时回调
         */
        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            byte[] data = bytes.toByteArray();
//            Log.d(TAG, "WebSocket onMessage(bytes):" + data);
            if (data != null && data.length != 0) {
                mIReceiveMessage.onMessage(data);
            }
        }

        /**
         * 当远程端暗示没有数据交互时回调（即此时准备关闭，但连接还没有关闭）
         */
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            mWebSocket = null;
            mIsConnect = false;
            Log.i(TAG, "closing：" + code);
            if (mIReceiveMessage != null) {
                mIReceiveMessage.onClosing();
            }
        }

        /**
         * 当连接已经释放的时候被回调
         */
        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            mWebSocket = null;
            mIsConnect = false;
            Log.i(TAG, "closed：" + code);
            if (mIReceiveMessage != null) {
                mIReceiveMessage.onClosed();
            }
        }

        /**
         * 失败时被回调（包括连接失败，发送失败等）
         */
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            mIsConnect = false;
            mIReceiveMessage.onClosing();
            mIReceiveMessage.onClosed();
            if (mIReceiveMessage != null) {
                mIReceiveMessage.onConnectFailed(t);
            }
        }
    }

    /**
     * 获取重写后的websocket回调接口
     *
     * @return 重写后的websocket回调接口
     */
    private WebSocketListener createListener() {
        return new MyWebSocketListener();
    }

}
