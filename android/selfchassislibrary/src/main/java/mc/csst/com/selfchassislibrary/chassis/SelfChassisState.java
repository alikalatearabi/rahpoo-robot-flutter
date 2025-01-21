package mc.csst.com.selfchassislibrary.chassis;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/16
 * @brief 自研底盘全状态
 * @Description
 */
public class SelfChassisState {
    /**
     * 建筑名
     */
    private String buildingName;
    /**
     * 楼层名
     */
    private String floorName;
    /**
     * 软急停
     */
    private boolean softStop = false;
    /**
     * 硬急停
     */
    private boolean hardStop = false;
    /**
     * 电池电量
     */
    private int battery;
    /**
     * 充电状态
     */
    private int charging;
    /**
     * 导航状态   等待600/运行中601/取消602/成功603/失败604
     */
    private int navStatus;
    /**
     * 多点导航状态（待定）
     */
    private int patrolStatus;
    /**
     * 当前程序状态
     */
    private int controlStatus;
    /**
     * 角速度
     */
    private float angularSpeed;
    /**
     * 直线速度
     */
    private float linearSpeed;
    /**
     * 导航点名称
     */
    private String currentGoalName;

    /**
     * x坐标（单位：米）
     */
    private float x;
    /**
     * y坐标（单位：米）
     */
    private float y;
    /**
     * 弧度
     */
    private float theta;

    /**
     * 是否初始化获取速度引擎
     */
    private boolean velocityState = false;
    /**
     * 是否初始化获取机器人位置引擎
     */
    private boolean getPoseState = false;
    /**
     * 是否初始化点云数据引擎
     */
    private boolean laserDataState = false;
    /**
     * 是否初始化 引擎
     */
    private boolean sendGoalState = false;
    /**
     * 是否初始化 引擎
     */
    private boolean cancelGoalState = false;
    /**
     * 是否初始化设置引擎
     */
    private boolean setPoseState = false;
    /**
     * 是否初始化获取路径引擎
     */
    private boolean getPathState = false;
    /**
     * 0:目标点导航
     * 1:初始点
     */
    private int pointState = 0;


    private static volatile SelfChassisState mInstance;


    private SelfChassisState() {
    }

    /**
     * 获取当前实例
     */
    public static SelfChassisState getInstance() {
        if (mInstance == null) {
            synchronized (SelfChassisState.class) {
                if (mInstance == null) {
                    mInstance = new SelfChassisState();
                }
            }
        }
        return mInstance;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getTheta() {
        return theta;
    }

    public void setTheta(float theta) {
        this.theta = theta;
    }

    public float getAngularSpeed() {
        return angularSpeed;
    }

    public void setAngularSpeed(float angularSpeed) {
        this.angularSpeed = angularSpeed;
    }

    public float getLinearSpeed() {
        return linearSpeed;
    }

    public void setLinearSpeed(float linearSpeed) {
        this.linearSpeed = linearSpeed;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getCharging() {
        return charging;
    }

    public void setCharging(int charging) {
        this.charging = charging;
    }

    public boolean isVelocityState() {
        return velocityState;
    }

    public void setVelocityState(boolean velocityState) {
        this.velocityState = velocityState;
    }

    public boolean isGetPoseState() {
        return getPoseState;
    }

    public void setGetPoseState(boolean getPoseState) {
        this.getPoseState = getPoseState;
    }

    public boolean isLaserDataState() {
        return laserDataState;
    }

    public void setLaserDataState(boolean laserDataState) {
        this.laserDataState = laserDataState;
    }

    public boolean isSendGoalState() {
        return sendGoalState;
    }

    public void setSendGoalState(boolean sendGoalState) {
        this.sendGoalState = sendGoalState;
    }

    public boolean isCancelGoalState() {
        return cancelGoalState;
    }

    public void setCancelGoalState(boolean cancelGoalState) {
        this.cancelGoalState = cancelGoalState;
    }

    public boolean isSetPoseState() {
        return setPoseState;
    }

    public void setSetPoseState(boolean setPoseState) {
        this.setPoseState = setPoseState;
    }

    public boolean isGetPathState() {
        return getPathState;
    }

    public void setGetPathState(boolean getPathState) {
        this.getPathState = getPathState;
    }

    public int getPointState() {
        return pointState;
    }

    public void setPointState(int pointState) {
        this.pointState = pointState;
    }

    public void setNavStatus(int navStatus) {
        this.navStatus = navStatus;
    }

    public int getNavStatus() {
        return navStatus;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public boolean isSoftStop() {
        return softStop;
    }

    public void setSoftStop(boolean softStop) {
        this.softStop = softStop;
    }

    public boolean isHardStop() {
        return hardStop;
    }

    public void setHardStop(boolean hardStop) {
        this.hardStop = hardStop;
    }

    public int getPatrolStatus() {
        return patrolStatus;
    }

    public void setPatrolStatus(int patrolStatus) {
        this.patrolStatus = patrolStatus;
    }

    public int getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(int controlStatus) {
        this.controlStatus = controlStatus;
    }

    public String getCurrentGoalName() {
        return currentGoalName;
    }

    public void setCurrentGoalName(String currentGoalName) {
        this.currentGoalName = currentGoalName;
    }

    @Override
    public String toString() {
        return "SelfChassisState{" +
                "buildingName='" + buildingName + '\'' +
                ", floorName='" + floorName + '\'' +
                ", softStop=" + softStop +
                ", hardStop=" + hardStop +
                ", battery=" + battery +
                ", charging=" + charging +
                ", navStatus=" + navStatus +
                ", patrolStatus=" + patrolStatus +
                ", controlStatus=" + controlStatus +
                ", angularSpeed=" + angularSpeed +
                ", linearSpeed=" + linearSpeed +
                ", currentGoalName='" + currentGoalName + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", theta=" + theta +
                ", velocityState=" + velocityState +
                ", getPoseState=" + getPoseState +
                ", laserDataState=" + laserDataState +
                ", sendGoalState=" + sendGoalState +
                ", cancelGoalState=" + cancelGoalState +
                ", setPoseState=" + setPoseState +
                ", getPathState=" + getPathState +
                ", pointState=" + pointState +
                '}';
    }
}
