package mc.csst.com.selfchassislibrary.chassis;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.blankj.utilcode.util.ConvertUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mc.csst.com.selfchassislibrary.bean.MapInfoBean;
import mc.csst.com.selfchassislibrary.bean.PointBean;
import mc.csst.com.selfchassislibrary.bean.msg.BreakPackageBean;
import mc.csst.com.selfchassislibrary.bean.msg.ChargeServerResultPublishBean;
import mc.csst.com.selfchassislibrary.bean.msg.GetMapFullBean;
import mc.csst.com.selfchassislibrary.bean.msg.GetPathPublishBean;
import mc.csst.com.selfchassislibrary.bean.msg.GetPosePublishBean;
import mc.csst.com.selfchassislibrary.bean.msg.LaserDataPublishBean;
import mc.csst.com.selfchassislibrary.bean.msg.NodeManagerControlResponseBean;
import mc.csst.com.selfchassislibrary.bean.msg.ObstacleRegionBean;
import mc.csst.com.selfchassislibrary.bean.msg.PoiResponseBean;
import mc.csst.com.selfchassislibrary.bean.msg.RobotStatusPublishBean;
import mc.csst.com.selfchassislibrary.bean.msg.StartRechargeResponseBean;
import mc.csst.com.selfchassislibrary.content.OpContent;
import mc.csst.com.selfchassislibrary.content.ServiceContent;
import mc.csst.com.selfchassislibrary.content.TopicContent;
import mc.csst.com.selfchassislibrary.utils.eventbus.SelfChassisEventMsg;
import mc.csst.com.selfchassislibrary.utils.eventbus.SelfChassisEventUtils;


/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2019/8/9
 * @brief 底盘消息回调
 * @Description
 */
public class SelfChassisMsgCallBack implements SelfChassis.OnMessageReceivedCallBack {

    private static final String TAG = SelfChassisMsgCallBack.class.getSimpleName();

    private static final String TOPIC = "topic";
    private static final String SERVICE = "service";

    private static final String OP = "op";

    /**
     * 拼凑完整 地图 数据
     */
    private StringBuffer mapSb = new StringBuffer();
    /**
     * 拼凑完整 路径 数据
     */
    private StringBuffer pathSb = new StringBuffer();

    /**
     * 接收消息回调
     */
    private OnMapInfoCallBack mOnMapInfoCallBack;

    public interface OnMapInfoCallBack {
        public void path(ArrayList<PointBean> pointList);

        public void map(MapInfoBean mapInfo, Bitmap map);

        public void laser(ArrayList<PointBean> pointList);

        public void pose(PointBean pose);

        public void targetName(String targetName);

    }

    public void setOnMapInfoCallBack(OnMapInfoCallBack callBack) {
        mOnMapInfoCallBack = callBack;
    }


    /**
     * 接收消息回调
     */
    private OnRobotStateInfoCallBack mOnRobotStateInfoCallBack;

    public void setOnRobotStateInfoCallBack(OnRobotStateInfoCallBack callBack) {
        mOnRobotStateInfoCallBack = callBack;
    }

    public interface OnRobotStateInfoCallBack {
        public void robotState(SelfChassisState state);

        public void obstacleRegion(int state);
    }

    @Override
    public void messageReceive(String jsonStr) {
//        Log.v(TAG, "messageReceive-- " + jsonStr);

        //判空
        if (TextUtils.isEmpty(jsonStr)) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            //通过op判断是否为分包数据
            String op = jsonObject.getString(OP);
            if (TextUtils.equals(OpContent.FRAGMENT, op)) {
                //组包
                Gson gson = new Gson();
                BreakPackageBean breakData = gson.fromJson(jsonStr, BreakPackageBean.class);

                int total = breakData.getTotal();
                int num = breakData.getNum();

                switch (breakData.getTopic()) {
                    case TopicContent.GLOBAL_PATH:
                        //路径
                        pathSb.append(breakData.getData());
                        break;
                    case TopicContent.MAP:
                        //获取地图
                        mapSb.append(breakData.getData());
                        break;
                    default:
                        break;
                }

                if (num != total - 1) {
                    return;
                }
                switch (breakData.getTopic()) {
                    case TopicContent.GLOBAL_PATH:
                        //路径
                        dataParse(pathSb.toString());
                        pathSb.delete(0, pathSb.length());
                        break;
                    case TopicContent.MAP:
                        //获取地图
                        dataParse(mapSb.toString());
                        mapSb.delete(0, mapSb.length());
                        break;
                    default:
                        break;
                }
            } else {
                dataParse(jsonStr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据解析
     *
     * @param jsonStr json字符串
     */
    private void dataParse(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr)) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            String op = jsonObject.getString(OP);
            if (TextUtils.equals(OpContent.SERVICE_RESPONSE, op)) {
                String service = jsonObject.getString(SERVICE);
                switch (service) {
                    //TODO 添加service
                    case ServiceContent.NODE_MANAGER_CONTROL:
                        //程序控制框架
                        nodeManagerControl(jsonStr);
                        break;
                    case ServiceContent.POI:
                        //发布导航点信息(poi名称模式)
                        poi(jsonStr);
                        break;
                    case ServiceContent.START_RECHARGE:
                        //回充控制接口
                        startRecharge(jsonStr);
                        break;
                    default:
                        break;
                }
            } else {
                String topic = jsonObject.getString(TOPIC);
                //TODO 添加topic
                switch (topic) {
                    case TopicContent.ROBOT_POSE:
                        //获取机器人坐标
                        getPose(jsonStr);
                        break;
                    case TopicContent.LASER_DATA:
                        //获取雷达点云数据
                        getLaserData(jsonStr);
                        break;
                    case TopicContent.GLOBAL_PATH:
                        //获取导航规划路径
                        getPath(jsonStr, op);
                        break;
                    case TopicContent.MAP:
                        //获取地图
                        getMap(jsonStr);
                        break;
                    case TopicContent.ROBOT_STATUS:
                        //机器人全局状态
                        robotStatus(jsonStr);
                        break;
                    case TopicContent.OBSTACLE_REGION:
                        //激光检测障碍物区域
                        obstacleRegion(jsonStr);
                        break;
                    default:
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void messageConnect(boolean b) {
//        SelfChassisEventMsg event = new SelfChassisEventMsg(SelfChassisEventMsg.CODE_CONNECT_STATE, b);
//        SelfChassisEventUtils.post(event);
        if (b) {
            //订阅类
            //获取机器人坐标
            SelfChassis.getInstance().initGetPose();
            //获取雷达点云数据
            SelfChassis.getInstance().initLaserData();
            //获取导航规划路径
            SelfChassis.getInstance().initGetPath();
            //获取机器人全局状态
            SelfChassis.getInstance().sendGetRobotStatus();
            //激光检测障碍物区域
            SelfChassis.getInstance().initObstacleRegion();

            //发布类
            //发布目标点(具体坐标值方式)
            SelfChassis.getInstance().initSendGoal();
            //取消目标点导航
            SelfChassis.getInstance().initCancelGoalMsg();
            //初始化立马返回地图
            SelfChassis.getInstance().initGetCurrentMap();
            //初始化激光检测停止移动
            SelfChassis.getInstance().initNodeManagerLaserSafetyController();
            //初始化激光检测距离
            SelfChassis.getInstance().initNodeManagerLaserSafetyRange();

            //获取地图
            SelfChassis.getInstance().sendGetMap();
            SystemClock.sleep(500);
//            SystemClock.sleep(500);
//
//            //获取建筑列表
//            SelfChassis.getInstance().serviceLayeredMapCmd();
//
//            SystemClock.sleep(500);
        }
    }

    @Override
    public void close() {

    }

    /**
     * 获取导航规划路径
     *
     * @param jsonStr json字符串
     * @param op      op为fragment为分包导航信息
     */
    private void getPath(String jsonStr, String op) {
        Gson gson = new Gson();
        GetPathPublishBean fullData = gson.fromJson(jsonStr, GetPathPublishBean.class);

        List<Float> px = fullData.getMsg().getPx();
        List<Float> py = fullData.getMsg().getPy();
        if (px == null || py == null || px.size() == 0 || py.size() == 0 || px.size() != py.size()) {
            return;
        }
        ArrayList<PointBean> pointList = new ArrayList<>();
        for (int i = 0; i < px.size(); i++) {
            PointBean point = new PointBean();
            point.setX(px.get(i));
            point.setY(py.get(i));
            pointList.add(point);
        }
        if (mOnMapInfoCallBack != null) {
            mOnMapInfoCallBack.path(pointList);
        }
    }

    /**
     * 回充详细状态 (状态转变时)
     *
     * @param jsonStr json字符串
     */
    private void chargeServerResult(String jsonStr) {
        Gson gson = new Gson();
        ChargeServerResultPublishBean chargeServerResultPublishBean = gson.fromJson(jsonStr, ChargeServerResultPublishBean.class);
        ChargeServerResultPublishBean.MsgBean msg = chargeServerResultPublishBean.getMsg();
        //状态码
        int data = msg.getData();

        //TODO 回充详细状态
    }

    /**
     * 机器人全局状态
     *
     * @param jsonStr json字符串
     */
    private void robotStatus(String jsonStr) {
        Gson gson = new Gson();

        RobotStatusPublishBean data = gson.fromJson(jsonStr, RobotStatusPublishBean.class);

        RobotStatusPublishBean.MsgBean msg = data.getMsg();
        //当前地图对应的建筑名
        String currentBuildingName = msg.getCurrent_building_name();
        SelfChassisState.getInstance().setBuildingName(currentBuildingName);
        //软急停,true为急停中
        boolean softEstop = msg.isSoft_estop();
        SelfChassisState.getInstance().setSoftStop(softEstop);
        //电池电量的百分比
        int battery = msg.getBattery();
        SelfChassisState.getInstance().setBattery(battery);
        //充电状态 0未连接/1成功充电/2回充进行中/-1回充失败
        int charger = msg.getCharger();
        SelfChassisState.getInstance().setCharging(charger);
        //当前导航状态 等待600/运行中601/取消602/成功603/失败604
        int navStatus = msg.getNav_status();
        SelfChassisState.getInstance().setNavStatus(navStatus);
        //多点导航状态
        int patrolStatus = msg.getPatrol_status();
        SelfChassisState.getInstance().setPatrolStatus(patrolStatus);
        //当前地图对应的楼层名
        String currentFloorName = msg.getCurrent_floor_name();
        SelfChassisState.getInstance().setFloorName(currentFloorName);
        //硬件急停,true为急停中
        boolean hardEstop = msg.isHard_estop();
        SelfChassisState.getInstance().setHardStop(hardEstop);
        //当前程序状态 建图20/定位导航30/错误99
        int controlState = msg.getControl_state();
        SelfChassisState.getInstance().setControlStatus(controlState);
        //当前导航目标点名称
        String currentGoalName = msg.getCurrent_goal_name();
        SelfChassisState.getInstance().setCurrentGoalName(currentGoalName);
        if (mOnMapInfoCallBack != null) {
            mOnMapInfoCallBack.targetName(currentGoalName);
        }

        //当前导航点的具体坐标
        RobotStatusPublishBean.MsgBean.CurrentGoalCoordinateBean currentGoalCoordinate = msg.getCurrent_goal_coordinate();
        //目标点theta: 地图中的的角度表达,单位弧度
        float theta = currentGoalCoordinate.getTheta();
//        SelfChassisState.getInstance().setTheta(theta);
        //目标点x轴值,单位米
        float x = currentGoalCoordinate.getX();
//        SelfChassisState.getInstance().setX(x);
        //目标点y轴值,单位米
        float y = currentGoalCoordinate.getY();
//        SelfChassisState.getInstance().setY(y);
//        SelfChassisState instance = SelfChassisState.getInstance();
//        SelfChassisEventMsg eventMsg = new SelfChassisEventMsg(TopicContent.ROBOT_STATUS, instance);
//        SelfChassisEventUtils.post(eventMsg);
        if (mOnRobotStateInfoCallBack != null) {
            mOnRobotStateInfoCallBack.robotState(SelfChassisState.getInstance());
        }
    }

    private void obstacleRegion(String jsonStr) {
        Gson gson = new Gson();

        ObstacleRegionBean data = gson.fromJson(jsonStr, ObstacleRegionBean.class);
        ObstacleRegionBean.MsgBean msg = data.getMsg();
        //0： 周围无障碍物   1：正右侧 90 度范围内有障碍物   2：正前方 90 度范围内有障碍物   4：正左方 90 度范围内有障碍物
        int data1 = msg.getData();
        //TODO 回调出去
        if (mOnRobotStateInfoCallBack != null) {
            mOnRobotStateInfoCallBack.obstacleRegion(data1);
        }
    }

    /**
     * 获取地图
     *
     * @param jsonStr json字符串
     */
    private void getMap(String jsonStr) {
        //获取地图

        Gson gson = new Gson();
        GetMapFullBean fullData = gson.fromJson(jsonStr, GetMapFullBean.class);

        MapInfoBean mapInfo = new MapInfoBean();
        GetMapFullBean.MsgBean.InfoBean info = fullData.getMsg().getInfo();
        //高
        int h = info.getHeight();
        //宽
        int w = info.getWidth();
        //分辨率
        float r = info.getResolution();
        //初始点的x
        float x = info.getOrigin().getPosition().getX();
        //初始点的y
        float y = info.getOrigin().getPosition().getY();
        String base64Data = fullData.getMsg().getData();
        byte[] data = Base64.decode(base64Data, Base64.DEFAULT);
        Bitmap bitmap = ConvertUtils.bytes2Bitmap(data);

        mapInfo.setH(h);
        mapInfo.setW(w);
        mapInfo.setR(r);
        mapInfo.setX(x);
        mapInfo.setY(y);
        if (mOnMapInfoCallBack != null) {
            mOnMapInfoCallBack.map(mapInfo, bitmap);
        }
    }

    /**
     * 获取点云数据
     *
     * @param jsonStr json字符串
     */
    private void getLaserData(String jsonStr) {
        Gson gson = new Gson();
        //获取雷达点云数据
        LaserDataPublishBean laserData = gson.fromJson(jsonStr, LaserDataPublishBean.class);
        ArrayList<PointBean> pointList = new ArrayList<>();
        List<Float> px = laserData.getMsg().getPx();
        List<Float> py = laserData.getMsg().getPy();
        if (px == null || px.size() == 0 || py == null || py.size() == 0 || py.size() != px.size()) {
            return;
        }

        for (int i = 0; i < px.size(); i++) {
            PointBean point = new PointBean();
            point.setX(px.get(i));
            point.setY(py.get(i));
            pointList.add(point);
        }
        if (mOnMapInfoCallBack != null) {
            mOnMapInfoCallBack.laser(pointList);
        }
    }

    /**
     * 获取机器人坐标
     *
     * @param jsonStr json字符串
     */
    private void getPose(String jsonStr) {
        Gson gson = new Gson();
        //获取机器人坐标
        GetPosePublishBean pose = gson.fromJson(jsonStr, GetPosePublishBean.class);

        PointBean point = new PointBean();
        //x坐标
        float x = pose.getMsg().getX();
        //y坐标
        float y = pose.getMsg().getY();
        //弧度值
        float theta = pose.getMsg().getTheta();

        point.setX(x);
        point.setY(y);
        point.setTheta(theta);
        if (mOnMapInfoCallBack != null) {
            mOnMapInfoCallBack.pose(point);
        }
        //设置到全状态里面
        SelfChassisState.getInstance().setX(x);
        SelfChassisState.getInstance().setY(y);
        SelfChassisState.getInstance().setTheta(theta);
    }

    /**
     * 程序控制框架
     *
     * @param jsonStr json字符串
     */
    private void nodeManagerControl(String jsonStr) {
        Log.e(TAG, "nodeManagerControl: " + jsonStr);
        Gson gson = new Gson();
        NodeManagerControlResponseBean info = gson.fromJson(jsonStr, NodeManagerControlResponseBean.class);
        //当前指令执行的结果码返回
        int result = info.getValues().getResult();
        if ("3".equals(info.getId()) && result == 0) {
            SelfChassis.getInstance().serviceNav();
//            SystemClock.sleep(200);
//            SelfChassis.getInstance().serviceLayeredMapCmd();
        }
        //回馈消息
//        String text = info.getValues().getText();
//        SelfChassisEventMsg msg = new SelfChassisEventMsg(ServiceContent.NODE_MANAGER_CONTROL, info);
//        EventBus.getDefault().post(msg);

//        //TODO ----
//        if (info.isResult() && result == 0) {
//            SelfChassis.getInstance().serviceGetVirtualWalls();
//        }
    }

    /**
     * 发布导航点信息(poi名称模式)
     *
     * @param jsonStr json字符串
     */
    private void poi(String jsonStr) {
        Gson gson = new Gson();
        PoiResponseBean info = gson.fromJson(jsonStr, PoiResponseBean.class);
    }

    /**
     * 回充控制接口
     *
     * @param jsonStr json字符串
     */
    private void startRecharge(String jsonStr) {
        Gson gson = new Gson();
        StartRechargeResponseBean info = gson.fromJson(jsonStr, StartRechargeResponseBean.class);
    }
}
