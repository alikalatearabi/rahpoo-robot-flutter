package mc.csst.com.selfchassislibrary.content;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief type常量
 * @Description
 */
public interface TypeContent {
    /**
     * ROS下 geometry_msgs/Twist 话题类型数据
     * 3D情况下, 6自由度DOF对应各个轴的速度
     */
    public String GEOMETRY_MSGS_TWIST = "geometry_msgs/Twist";
    /**
     * ROS下 geometry_msgs/Pose2D 话题类型数据
     * 2D情况下, 平面点的信息,包含方向角
     */
    public String GEOMETRY_MSGS_POSE2D = "geometry_msgs/Pose2D";
    /**
     * ROS下 yutong_assistance/point_array 话题类型数据
     * 2D情况下, 全局坐标点的表达集合
     */
    public String YUTONG_ASSISTANCE_POINT_ARRAY = "yutong_assistance/point_array";
    /**
     * ROS下 geometry_msgs/PoseStamped 话题类型数据
     * 3D环境中,点的表达,带有时间戳及坐标系信息
     */
    public String GEOMETRY_MSGS_POSESTAMPED = "geometry_msgs/PoseStamped";
    /**
     * ROS下 actionlib_msgs/GoalID 话题类型数据
     * 用于标记当前目标点的ID号及时间戳信息,不带任何参数则默认针对所有目标点有效
     */
    public String ACTIONLIB_MSGS_GOALID = "actionlib_msgs/GoalID";
    /**
     * ROS下 geometry_msgs/PoseWithCovarianceStamped 话题类型数据
     * 3D环境中,点的表达,带有时间戳及坐标系信息,并且包含各个轴数据对应的协方差信息
     */
    public String GEOMETRY_MSGS_POSEWITHCOVARIANCESTAMPED = "geometry_msgs/PoseWithCovarianceStamped";
    /**
     * ROS下 nav_msgs/OccupancyGrid 话题类型数据
     * 存储地图格式,包含地图相关信息及其具体数据
     */
    public String NAV_MSGS_OCCUPANCYGRID = "nav_msgs/OccupancyGrid";


    public String YUTONG_ASSISTANCE_ROBOTSTATUS = "yutong_assistance/RobotStatus";

    public String ACTIONLIB_MSGS_GOALSTATUS = "actionlib_msgs/GoalStatus";

    public String STD_MSGS_INT32 = "std_msgs/Int32";

    public String STD_MSGS_INT8 = "std_msgs/Int8";

    public String STD_MSGS_BOOL = "std_msgs/Bool";

    public String YUTONG_ASSISTANCE_VIRTUALWALLS = "yutong_assistance/VirtualWalls";

    public String YUTONG_ASSISTANCE_WAYPOINTLIST = "yutong_assistance/WaypointList";

    public String YUTONG_ASSISTANCE_INSERTPOSE = "yutong_assistance/InsertPose";

    public String YUTONG_ASSISTANCE_PENCILOPLIST = "yutong_assistance/PencilOpList";

    public String STD_MSGS_FLOAT64 = "std_msgs/Float64";


}
