package mc.csst.com.selfchassislibrary.chassis;

import android.util.Log;

import java.util.Arrays;

import mc.csst.com.selfchassislibrary.utils.MsgManager;
import mc.csst.com.selfchassislibrary.websocket.IReceiveMessage;
import mc.csst.com.selfchassislibrary.websocket.WebSocketClientManager;
import okio.ByteString;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/13
 * @brief 自研底盘封装
 * @Description
 */
public class SelfChassis {

    private final String TAG = this.getClass().getSimpleName();

    private static volatile SelfChassis mInstance;

    private final WebSocketClientManager mWebSocketClient = WebSocketClientManager.getInstance();

    /**
     * 接收消息回调
     */
    private OnMessageReceivedCallBack mOnMessageReceivedCallBack;

    public interface OnMessageReceivedCallBack {
        /**
         * 接收到的json字符串
         *
         * @param jsonStr 接收到的消息
         */
        public void messageReceive(String jsonStr);

        /**
         * 连接状态
         *
         * @param b true连接成功  false连接失败
         */
        public void messageConnect(boolean b);

        /**
         * 关闭
         */
        public void close();
    }


    public void setOnMessageReceivedCallBack(OnMessageReceivedCallBack callBack) {
        mOnMessageReceivedCallBack = callBack;
    }

    public void setOnMapInfoCallBack(SelfChassisMsgCallBack.OnMapInfoCallBack onMapInfoCallBack) {
        selfChassisMsgCallBack.setOnMapInfoCallBack(onMapInfoCallBack);
    }

    public void setOnRobotStateInfoCallBack(SelfChassisMsgCallBack.OnRobotStateInfoCallBack onRobotStateInfoCallBack) {
        selfChassisMsgCallBack.setOnRobotStateInfoCallBack(onRobotStateInfoCallBack);
    }

    private SelfChassis() {
    }

    /**
     * 获取当前实例
     */
    public static SelfChassis getInstance() {
        if (mInstance == null) {
            synchronized (SelfChassis.class) {
                if (mInstance == null) {
                    mInstance = new SelfChassis();
                }
            }
        }
        return mInstance;
    }

    private final SelfChassisMsgCallBack selfChassisMsgCallBack = new SelfChassisMsgCallBack();

    /**
     * 连接
     *
     * @param url ws://192.168.0.1:9090
     */
    public void connectSelfChassis(String url) {
        System.out.println("Connecting to URL: " + url);
        mWebSocketClient.init(url, new MyIReceiveMessage());
        setOnMessageReceivedCallBack(selfChassisMsgCallBack);
    }

    public boolean isConnect() {
        return mWebSocketClient.isConnect();
    }

    /**
     * 关闭连接
     */
    public void disconnectSelfChassis() {
        mWebSocketClient.close();
    }

    /**
     * 初始化全状态
     */
    public void sendGetRobotStatus() {
        if (mWebSocketClient.sendMessage(MsgManager.initRobotStatus())) {

        }
    }

    /**
     * 获取当前地图信息
     */
    public void sendGetRobotCurrentMap() {
        mWebSocketClient.sendMessage(MsgManager.serviceGetMapInfo());
    }


    /**
     * 回充电桩
     */
    public void sendGoHome() {
        mWebSocketClient.sendMessage(MsgManager.serviceStartRecharge(1, null));
    }

    /**
     * 移动
     *
     * @param markerName 点位名称
     */
    public void sendMoveByMarkerName(String markerName) {
        mWebSocketClient.sendMessage(MsgManager.servicePoi(markerName));
    }

    /**
     * 通过坐标移动
     *
     * @param x       x轴坐标（单位：米）
     * @param y       y轴坐标（单位：米）
     * @param radians 弧度
     */
    public void sendMoveByLocation(float x, float y, float radians) {
        mWebSocketClient.sendMessage(MsgManager.sendGoalMsg(x, y, radians));
    }

    /**
     * 设置速度
     *
     * @param z 旋转速度
     * @param x 直线速度
     */
    public void setVelocity(float z, float x) {
        mWebSocketClient.sendMessage(MsgManager.velocityMsg(z, x));
    }

    /**
     * 取消导航
     */
    public void sendCancelMove() {
        mWebSocketClient.sendMessage(MsgManager.cancelGoalMsg());
    }
    //TODO -----

    /**
     * 发送消息
     *
     * @param msg 消息
     */
    public void send(String msg) {
        mWebSocketClient.sendMessage(msg);
    }

    /**
     * 发送消息
     *
     * @param msg 消息
     */
    public void send(ByteString msg) {
        mWebSocketClient.sendMessage(msg);
    }

    /**
     * 处理消息
     */
    private class MyIReceiveMessage implements IReceiveMessage {
        @Override
        public void onConnectSuccess() {
//            ToastUtils.showShort("onConnectSuccess");
            Log.e(TAG, "onConnectSuccess");
            if (mOnMessageReceivedCallBack != null) {
                mOnMessageReceivedCallBack.messageConnect(true);
            }
        }

        @Override
        public void onConnectFailed(Throwable t) {
            t.printStackTrace();
            Log.e(TAG, "onConnectFailed t:" + t.getLocalizedMessage());
        }

        @Override
        public void onClosing() {
            Log.e(TAG, "onClosing");
//            ToastUtils.showShort("onClosed");
            if (mOnMessageReceivedCallBack != null) {
                mOnMessageReceivedCallBack.messageConnect(false);
                mOnMessageReceivedCallBack.close();
            }
        }

        @Override
        public void onClosed() {

            Log.e(TAG, "onClosed");

        }

        @Override
        public void onMessage(String text) {
//            Log.e(TAG, "onMessage-- " + text);
            if (mOnMessageReceivedCallBack != null) {
                mOnMessageReceivedCallBack.messageReceive(text);
            }

        }

        @Override
        public void onMessage(byte[] text) {
            Log.e(TAG, "onMessage:" + Arrays.toString(text));
        }
    }

    /**
     * 初始化获取机器人坐标模块
     */
    public void initGetPose() {
        if (mWebSocketClient.sendMessage(MsgManager.initGetPoseMsg())) {
            SelfChassisState.getInstance().setGetPoseState(true);
        }
    }

    /**
     * 初始化获取雷达点云数据模块
     */
    public void initLaserData() {
        if (mWebSocketClient.sendMessage(MsgManager.initLaserDataMsg())) {
            SelfChassisState.getInstance().setLaserDataState(true);
        }
    }

    /**
     * 初始化发布目标点模块
     */
    public void initSendGoal() {
        if (mWebSocketClient.sendMessage(MsgManager.initSendGoalMsg())) {
            SelfChassisState.getInstance().setSendGoalState(true);
        }
    }

    /**
     * 初始化取消目标点导航模块
     */
    public void initCancelGoalMsg() {
        if (mWebSocketClient.sendMessage(MsgManager.initCancelGoalMsg())) {
            SelfChassisState.getInstance().setCancelGoalState(true);
        }
    }

    /**
     * 初始化获取导航规划路径模块
     */
    public void initGetPath() {
        if (mWebSocketClient.sendMessage(MsgManager.initGetPathMsg())) {
            SelfChassisState.getInstance().setGetPathState(true);
        }
    }

    /**
     * 初始化立马返回地图
     */
    public void initGetCurrentMap() {
        if (mWebSocketClient.sendMessage(MsgManager.initGetCurrentMap())) {
        }
    }

    /**
     * 初始化激光检测障碍物区域
     */
    public void initObstacleRegion() {
        if (mWebSocketClient.sendMessage(MsgManager.initObstacleRegion())) {
        }
    }

    /**
     * 初始化激光检测停止移动
     */
    public void initNodeManagerLaserSafetyController() {
        if (mWebSocketClient.sendMessage(MsgManager.initNodeManagerLaserSafetyController())) {
        }
    }

    /**
     * 初始化激光检测距离
     */
    public void initNodeManagerLaserSafetyRange() {
        if (mWebSocketClient.sendMessage(MsgManager.initNodeManagerLaserSafetyRange())) {
        }
    }

    /**
     * 终止获取机器人坐标模块
     */
    public void stopGetPose() {
        if (mWebSocketClient.sendMessage(MsgManager.stopGetPoseMsg())) {
            SelfChassisState.getInstance().setGetPoseState(false);
        }
    }

    /**
     * 终止获取雷达点云数据模块
     */
    public void stopLaserData() {
        if (mWebSocketClient.sendMessage(MsgManager.stopLaserDataMsg())) {
            SelfChassisState.getInstance().setLaserDataState(false);
        }
    }

    /**
     * 终止发布目标点模块
     */
    public void stopSendGoal() {
        if (mWebSocketClient.sendMessage(MsgManager.stopSendGoalMsg())) {
            SelfChassisState.getInstance().setSendGoalState(false);
        }
    }

    /**
     * 终止取消目标点导航模块
     */
    public void stopCancelGoal() {
        if (mWebSocketClient.sendMessage(MsgManager.stopCancelGoalMsg())) {
            SelfChassisState.getInstance().setCancelGoalState(false);
        }
    }

    /**
     * 终止获取导航规划路径模块
     */
    public void stopGetPath() {
        if (mWebSocketClient.sendMessage(MsgManager.stopGetPathMsg())) {
            SelfChassisState.getInstance().setGetPathState(false);
        }
    }

    /**
     * 终止获取机器人全局状态
     */
    public void stopRobotStatus() {
        if (mWebSocketClient.sendMessage(MsgManager.stopRobotStatus())) {
        }
    }

    /**
     * 终止立马返回地图
     */
    public void stopGetCurrentMap() {
        if (mWebSocketClient.sendMessage(MsgManager.stopGetCurrentMap())) {
        }
    }

    /**
     * 终止激光检测障碍物区域
     */
    public void stopObstacleRegion() {
        if (mWebSocketClient.sendMessage(MsgManager.stopObstacleRegion())) {
        }
    }

    /**
     * 终止激光检测停止移动
     */
    public void stopNodeManagerLaserSafetyController() {
        if (mWebSocketClient.sendMessage(MsgManager.stopNodeManagerLaserSafetyController())) {
        }
    }

    /**
     * 程序控制框架
     *
     * @param cmd          0: 开启建图  1: 继续建图 (预留)  2: 重启建图(预留)  3: 保存地图  4: 开启定位导航 7:切图
     * @param floorNum     楼层编号
     * @param buildingName 建筑物名称
     */
    public void serviceNodeManagerControl(int cmd, String floorNum, String buildingName) {
        mWebSocketClient.sendMessage(MsgManager.serviceNodeManagerControl(cmd, floorNum, buildingName));
    }

    /**
     * 切图
     *
     * @param floorNum     楼层名称
     * @param buildingName 建筑物名称
     */
    public void serviceChangeMap(String floorNum, String buildingName) {
        serviceNodeManagerControl(7, floorNum, buildingName);
    }

    /**
     * 开启建图
     *
     * @param floorNum     楼层名称
     * @param buildingName 建筑物名称
     */
    public void serviceBuilding(String floorNum, String buildingName) {
        serviceNodeManagerControl(0, floorNum, buildingName);
    }

    /**
     * 开启建图
     *
     * @param cmd          建图类型
     * @param floorNum     楼层名称
     * @param buildingName 建筑物名称
     */
    public void serviceBuilding(int cmd, String floorNum, String buildingName) {
        serviceNodeManagerControl(cmd, floorNum, buildingName);
    }

    /**
     * 保存
     */
    public void serviceSave() {
        serviceNodeManagerControl(3, null, null);
    }

    /**
     * 开启定位导航
     */
    public void serviceNav() {
        serviceNodeManagerControl(4, null, null);
    }

    /**
     * 获取地图
     */
    public void sendGetMap() {
        mWebSocketClient.sendMessage(MsgManager.getMapMsg());
    }

    /**
     * 立马返回地图
     */
    public void getCurrentMap() {
        mWebSocketClient.sendMessage(MsgManager.getCurrentMap());
    }

    /**
     * 激光检测停止移动
     */
    public void nodeManagerLaserSafetyController(boolean isOpen) {
        mWebSocketClient.sendMessage(MsgManager.nodeManagerLaserSafetyController(isOpen));
    }

    /**
     * 设置激光检测距离
     *
     * @param distance 距离
     */
    public void nodeManagerLaserSafetyRange(float distance) {
        mWebSocketClient.sendMessage(MsgManager.nodeManagerLaserSafetyRange(distance));
    }

    /**
     * 导航速度修改
     *
     * @param cmd 速度指令
     */
    public void serviceVelocityControl(int cmd) {
        mWebSocketClient.sendMessage(MsgManager.serviceVelocityControl(cmd));
    }
}
