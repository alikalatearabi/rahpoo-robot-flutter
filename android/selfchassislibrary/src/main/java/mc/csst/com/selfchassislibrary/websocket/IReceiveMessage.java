package mc.csst.com.selfchassislibrary.websocket;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/6
 * @brief 接收消息接口
 * @Description
 */
public interface IReceiveMessage {
    /**
     * 连接成功
     */
    void onConnectSuccess();

    /**
     * 连接失败
     */
    void onConnectFailed(Throwable t);

    /**
     * 关闭中
     */
    void onClosing();

    /**
     * 已关闭
     */
    void onClosed();

    /**
     * 接收到的数据
     */
    void onMessage(String text);

    /**
     * 接收到的数据
     */
    void onMessage(byte[] text);
}
