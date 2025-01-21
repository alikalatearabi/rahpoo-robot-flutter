package mc.csst.com.selfchassislibrary.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import mc.csst.com.selfchassislibrary.bean.QuaternionBean;
import mc.csst.com.selfchassislibrary.bean.msg.CancelGoalPublishBean;
import mc.csst.com.selfchassislibrary.bean.msg.GetCurrentMapBean;
import mc.csst.com.selfchassislibrary.bean.msg.NodeManagerLaserSafetyControllerBean;
import mc.csst.com.selfchassislibrary.bean.msg.NodeManagerLaserSafetyRangeBean;
import mc.csst.com.selfchassislibrary.bean.msg.RequestBean;
import mc.csst.com.selfchassislibrary.bean.msg.SendGoalPublishBean;
import mc.csst.com.selfchassislibrary.bean.msg.VelocityPublishBean;
import mc.csst.com.selfchassislibrary.content.OpContent;
import mc.csst.com.selfchassislibrary.content.ServiceContent;
import mc.csst.com.selfchassislibrary.content.TopicContent;
import mc.csst.com.selfchassislibrary.content.TypeContent;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief 消息处理工具类
 * @Description
 */
public class MsgManager {

    /**
     * 初始化获取机器人坐标
     *
     * @return 初始化获取机器人坐标的json字符串
     */
    public static String initGetPoseMsg() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.SUBSCRIBE);
        info.setTopic(TopicContent.ROBOT_POSE);
        info.setType(TypeContent.GEOMETRY_MSGS_POSE2D);
        return requestToJson(info);
    }

    /**
     * 终止获取机器人坐标
     *
     * @return 终止获取机器人坐标的json字符串
     */
    public static String stopGetPoseMsg() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.UNSUBSCRIBE);
        info.setTopic(TopicContent.ROBOT_POSE);
        return requestToJson(info);
    }

    /**
     * 初始化获取雷达点云数据
     *
     * @return 初始化获取雷达点云数据的json字符串
     */
    public static String initLaserDataMsg() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.SUBSCRIBE);
        info.setTopic(TopicContent.LASER_DATA);
        info.setType(TypeContent.YUTONG_ASSISTANCE_POINT_ARRAY);
        return requestToJson(info);
    }

    /**
     * 终止获取雷达点云数据
     *
     * @return 终止获取雷达点云数据的json字符串
     */
    public static String stopLaserDataMsg() {
        RequestBean info = new RequestBean();
        info.setType(TypeContent.YUTONG_ASSISTANCE_POINT_ARRAY);
        info.setOp(OpContent.UNSUBSCRIBE);
        info.setTopic(TopicContent.LASER_DATA);
        return requestToJson(info);
    }

    /**
     * 初始化发布目标点
     *
     * @return 初始化发布目标点的json字符串
     */
    public static String initSendGoalMsg() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.ADVERTISE);
        info.setTopic(TopicContent.NAVI_GOAL);
        info.setType(TypeContent.GEOMETRY_MSGS_POSESTAMPED);
        return requestToJson(info);
    }

    /**
     * 发布目标点
     *
     * @param x       x轴坐标（单位：米）
     * @param y       y轴坐标（单位：米）
     * @param radians 弧度
     * @return 发布目标点的json字符串
     */
    public static String sendGoalMsg(float x, float y, float radians) {
        //角度转弧度
//        float radians = (float) Math.toRadians(angle);
        //欧拉角转四元素
        QuaternionBean quaternion = ConvertorUtils.eular2quaternion(0, 0, radians);

        //组装数据
        SendGoalPublishBean data = new SendGoalPublishBean();
        data.setOp(OpContent.PUBLISH);
        data.setTopic(TopicContent.NAVI_GOAL);

        SendGoalPublishBean.MsgBean msg = new SendGoalPublishBean.MsgBean();

        SendGoalPublishBean.MsgBean.HeaderBean header = new SendGoalPublishBean.MsgBean.HeaderBean();
        header.setFrame_id("map");

        SendGoalPublishBean.MsgBean.PoseBean pose = new SendGoalPublishBean.MsgBean.PoseBean();
        SendGoalPublishBean.MsgBean.PoseBean.PositionBean position = new SendGoalPublishBean.MsgBean.PoseBean.PositionBean();
        position.setX(x);
        position.setY(y);

        SendGoalPublishBean.MsgBean.PoseBean.OrientationBean orientation = new SendGoalPublishBean.MsgBean.PoseBean.OrientationBean();
        orientation.setW(quaternion.getW());
        orientation.setX(quaternion.getX());
        orientation.setY(quaternion.getY());
        orientation.setZ(quaternion.getZ());

        pose.setPosition(position);
        pose.setOrientation(orientation);

        msg.setHeader(header);
        msg.setPose(pose);

        data.setMsg(msg);
        return toJson(data, SendGoalPublishBean.class);
    }

    /**
     * 终止发布目标点
     *
     * @return 终止发布目标点的json字符串
     */
    public static String stopSendGoalMsg() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.UNSUBSCRIBE);
        info.setTopic(TopicContent.ROBOT_POSE);
        return requestToJson(info);
    }

    /**
     * 初始化取消目标点导航
     *
     * @return 初始化取消目标点导航的json字符串
     */
    public static String initCancelGoalMsg() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.ADVERTISE);
        info.setTopic(TopicContent.MOVE_BASE_CANCEL);
        info.setType(TypeContent.ACTIONLIB_MSGS_GOALID);
        return requestToJson(info);
    }

    /**
     * 取消目标点导航
     *
     * @return 取消目标点导航的json字符串
     */
    public static String cancelGoalMsg() {
        CancelGoalPublishBean data = new CancelGoalPublishBean();
        data.setOp(OpContent.PUBLISH);
        data.setTopic(TopicContent.MOVE_BASE_CANCEL);

        CancelGoalPublishBean.MsgBean msg = new CancelGoalPublishBean.MsgBean();
        msg.setId("");
        msg.setStamp("");

        data.setMsg(msg);

        return toJson(data, CancelGoalPublishBean.class);
    }

    /**
     * 终止取消目标点导航
     *
     * @return 终止取消目标点导航的json字符串
     */
    public static String stopCancelGoalMsg() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.UNSUBSCRIBE);
        info.setTopic(TopicContent.MOVE_BASE_CANCEL);
        return requestToJson(info);
    }

    /**
     * 获取地图
     *
     * @return 获取地图的json字符串
     */
    public static String getMapMsg() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.SUBSCRIBE);
        info.setTopic(TopicContent.MAP);
        info.setType(TypeContent.NAV_MSGS_OCCUPANCYGRID);
        info.setCompression("png");
        info.setFragment_size(6000);
        info.setThrottle_rate(88);
        return requestToJson(info);
    }

    /**
     * 初始化获取导航规划路径
     *
     * @return 初始化获取导航规划路径的json字符串
     */
    public static String initGetPathMsg() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.SUBSCRIBE);
        info.setTopic(TopicContent.GLOBAL_PATH);
        info.setType(TypeContent.YUTONG_ASSISTANCE_POINT_ARRAY);
        return requestToJson(info);
    }

    /**
     * 终止获取导航规划路径
     *
     * @return 终止获取导航规划路径的json字符串
     */
    public static String stopGetPathMsg() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.UNSUBSCRIBE);
        info.setTopic(TopicContent.GLOBAL_PATH);
        info.setType(TypeContent.YUTONG_ASSISTANCE_POINT_ARRAY);
        return requestToJson(info);
    }

    /**
     * 初始化获取机器人全局状态
     *
     * @return 初始化获取机器人全局状态的json字符串
     */
    public static String initRobotStatus() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.SUBSCRIBE);
        info.setTopic(TopicContent.ROBOT_STATUS);
        info.setType(TypeContent.YUTONG_ASSISTANCE_ROBOTSTATUS);
        return requestToJson(info);
    }

    /**
     * 终止获取机器人全局状态
     *
     * @return 终止获取机器人全局状态的json字符串
     */
    public static String stopRobotStatus() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.UNSUBSCRIBE);
        info.setTopic(TopicContent.ROBOT_STATUS);
        info.setType(TypeContent.YUTONG_ASSISTANCE_ROBOTSTATUS);
        return requestToJson(info);
    }


    /**
     * 初始化立马返回地图
     *
     * @return
     */
    public static String initGetCurrentMap() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.ADVERTISE);
        info.setTopic(TopicContent.GET_CURRENT_MAP);
        info.setType(TypeContent.STD_MSGS_BOOL);
        return requestToJson(info);
    }

    /**
     * 立马返回地图
     *
     * @return
     */
    public static String getCurrentMap() {
        GetCurrentMapBean data = new GetCurrentMapBean();
        GetCurrentMapBean.MsgBean msg = new GetCurrentMapBean.MsgBean();
        msg.setData(true);
        data.setTopic(TopicContent.GET_CURRENT_MAP);
        data.setOp(OpContent.PUBLISH);
        data.setMsg(msg);
        return toJson(data, GetCurrentMapBean.class);
    }

    /**
     * 终止立马返回地图
     *
     * @return
     */
    public static String stopGetCurrentMap() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.UNADVERTISE);
        info.setTopic(TopicContent.GET_CURRENT_MAP);
        return requestToJson(info);
    }

    /**
     * 初始化激光检测障碍物区域
     */
    public static String initObstacleRegion() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.SUBSCRIBE);
        info.setTopic(TopicContent.OBSTACLE_REGION);
        info.setType(TypeContent.STD_MSGS_INT8);
        return requestToJson(info);
    }

    /**
     * 终止激光检测障碍物区域
     */
    public static String stopObstacleRegion() {
        RequestBean info = new RequestBean();
        info.setTopic(TopicContent.OBSTACLE_REGION);
        info.setType(TypeContent.STD_MSGS_INT8);
        info.setOp(OpContent.UNSUBSCRIBE);
        return requestToJson(info);
    }


    /**
     * 初始化激光检测停止移动
     */
    public static String initNodeManagerLaserSafetyController() {
        RequestBean info = new RequestBean();
        info.setTopic(TopicContent.NODE_MANAGER_LASER_SAFETY_CONTROLLER);
        info.setType(TypeContent.STD_MSGS_BOOL);
        info.setOp(OpContent.ADVERTISE);
        return requestToJson(info);
    }

    /**
     * 激光检测停止移动
     */
    public static String nodeManagerLaserSafetyController(boolean isOpen) {
        NodeManagerLaserSafetyControllerBean data = new NodeManagerLaserSafetyControllerBean();
        NodeManagerLaserSafetyControllerBean.MsgBean msg = new NodeManagerLaserSafetyControllerBean.MsgBean();
        msg.setData(isOpen);
        data.setMsg(msg);
        data.setTopic(TopicContent.NODE_MANAGER_LASER_SAFETY_CONTROLLER);
        data.setOp(OpContent.PUBLISH);
        return toJson(data, NodeManagerLaserSafetyControllerBean.class);
    }

    /**
     * 终止激光检测停止移动
     */
    public static String stopNodeManagerLaserSafetyController() {
        RequestBean info = new RequestBean();
        info.setTopic(TopicContent.NODE_MANAGER_LASER_SAFETY_CONTROLLER);
        info.setOp(OpContent.UNADVERTISE);
        return requestToJson(info);
    }


    /**
     * 初始化激光检测距离
     */
    public static String initNodeManagerLaserSafetyRange() {
        RequestBean info = new RequestBean();
        info.setTopic(TopicContent.NODE_MANAGER_LASER_SAFETY_RANGE);
        info.setType(TypeContent.STD_MSGS_FLOAT64);
        info.setOp(OpContent.ADVERTISE);
        return requestToJson(info);
    }

    /**
     * 设置激光检测距离
     *
     * @param distance 距离
     */
    public static String nodeManagerLaserSafetyRange(float distance) {
        NodeManagerLaserSafetyRangeBean data = new NodeManagerLaserSafetyRangeBean();
        NodeManagerLaserSafetyRangeBean.MsgBean msg = new NodeManagerLaserSafetyRangeBean.MsgBean();
        msg.setData(distance);

        data.setMsg(msg);
        data.setTopic(TopicContent.NODE_MANAGER_LASER_SAFETY_RANGE);
        data.setOp(OpContent.PUBLISH);
        return toJson(data, NodeManagerLaserSafetyRangeBean.class);
    }

    /**
     * 终止激光检测停止移动
     */
    public static String stopNodeManagerLaserSafetyRange() {
        RequestBean info = new RequestBean();
        info.setTopic(TopicContent.NODE_MANAGER_LASER_SAFETY_RANGE);
        info.setOp(OpContent.UNADVERTISE);
        return requestToJson(info);
    }

    /**
     * 程序控制框架
     *
     * @param cmd          0: 开启建图  1: 继续建图 (预留)  2: 重启建图(预留)  3: 保存地图  4: 开启定位导航 7: 切换地图
     * @param floorNum     楼层编号
     * @param buildingName 建筑物名称
     * @return 程序控制框架的json字符串
     */
    public static String serviceNodeManagerControl(int cmd, String floorNum, String buildingName) {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.CALL_SERVICE);
        info.setService(ServiceContent.NODE_MANAGER_CONTROL);
        info.setId(cmd + "");

        RequestBean.ArgsBean args = new RequestBean.ArgsBean();
        args.setArgs(0);
        args.setCmd(cmd);
        args.setFloor_num(floorNum);
        args.setBuilding_name(buildingName);
        info.setArgs(args);
        return requestToJson(info);
    }

    /**
     * 获取地图信息接口
     *
     * @return 获取地图信息接口的json字符串
     */
    public static String serviceGetMapInfo() {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.CALL_SERVICE);
        info.setService(ServiceContent.GET_MAP_INFO);
        RequestBean.ArgsBean arg = new RequestBean.ArgsBean();
        arg.setCmd(0);
        info.setArgs(arg);
        return requestToJson(info);
    }

    /**
     * 发布导航点信息(poi名称模式)
     *
     * @param pointName 导航点名称
     * @return 发布导航点信息的json字符串
     */
    public static String servicePoi(String pointName) {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.CALL_SERVICE);
        info.setService(ServiceContent.POI);

        RequestBean.ArgsBean arg = new RequestBean.ArgsBean();
        arg.setPoi(pointName);
        info.setArgs(arg);

        return requestToJson(info);
    }

    /**
     * 回充控制接口
     *
     * @param cmd       控制状态 1启动
     * @param pointName 需要去的回充桩位置对应POI名称.如果不设置的话,则会使用默认值
     * @return 回充控制接口的json字符串
     */
    public static String serviceStartRecharge(int cmd, String pointName) {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.CALL_SERVICE);
        info.setService(ServiceContent.START_RECHARGE);
        RequestBean.ArgsBean arg = new RequestBean.ArgsBean();
        arg.setCmd(cmd);
        arg.setStr(pointName);
        info.setArgs(arg);
        return requestToJson(info);
    }


    /**
     * 导航速度修改
     *
     * @param cmd 速度指令
     * @return 导航速度修改的json字符串
     */
    public static String serviceVelocityControl(int cmd) {
        RequestBean info = new RequestBean();
        info.setOp(OpContent.CALL_SERVICE);
        info.setService(ServiceContent.VELOCITY_CONTROL);
        RequestBean.ArgsBean arg = new RequestBean.ArgsBean();
        arg.setCmd(cmd);
        arg.setStr("");
        info.setArgs(arg);
        return requestToJson(info);
    }

    /**
     * 设置速度
     *
     * @param z 旋转速度
     * @param x 前进速度
     * @return 设置速度的json字符串
     */
    public static String velocityMsg(float z, float x) {
        VelocityPublishBean data = new VelocityPublishBean();
        data.setOp(OpContent.PUBLISH);
        data.setTopic(TopicContent.CMD_VEL_MUX_NPUT_TELEOP);

        VelocityPublishBean.MsgBean msg = new VelocityPublishBean.MsgBean();

        VelocityPublishBean.MsgBean.AngularBean angular = new VelocityPublishBean.MsgBean.AngularBean();
        angular.setZ(z);

        VelocityPublishBean.MsgBean.LinearBean linear = new VelocityPublishBean.MsgBean.LinearBean();
        linear.setX(x);

        msg.setAngular(angular);
        msg.setLinear(linear);
        data.setMsg(msg);
        return toJson(data, VelocityPublishBean.class);
    }


    /**
     * 将实体转换成json字符串
     *
     * @param src       原实体数据
     * @param typeOfSrc 数据类型
     * @return 转换后的json字符串
     */
    private static String toJson(Object src, Type typeOfSrc) {
        Gson gson = new Gson();
        String s = gson.toJson(src, typeOfSrc);
        return s == null ? "" : s;
    }

    /**
     * 将RequestBean实体转换成json字符串
     *
     * @param src RequestBean原数据
     * @return 转换后的json字符串
     */
    private static String requestToJson(Object src) {
        return toJson(src, RequestBean.class);
    }
}
