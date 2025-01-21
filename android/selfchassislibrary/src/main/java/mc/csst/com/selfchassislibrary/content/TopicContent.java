package mc.csst.com.selfchassislibrary.content;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief Topic常量
 * @Description
 */
public interface TopicContent {
    /**
     * 速度控制命令
     */
    String CMD_VEL_MUX_NPUT_TELEOP = "/cmd_vel_mux/input/teleop";
    /**
     * 机器人全局状态
     */
    String ROBOT_STATUS = "/robot_status";
    /**
     * 获取机器人坐标
     */
    String ROBOT_POSE = "/robot_pose";
    /**
     * 获取雷达点云数据
     */
    String LASER_DATA = "/laser_data";
    /**
     * 获取导航规划路径
     */
    String GLOBAL_PATH = "/global_path";
    /**
     * 发布目标点
     */
    String NAVI_GOAL = "/navi_goal";
    /**
     * 取消目标点导航
     */
    String MOVE_BASE_CANCEL = "/move_base/cancel";
    /**
     * 获取地图
     */
    String MAP = "/map";
    /**
     * 立马返回地图
     */
    String GET_CURRENT_MAP = "/get_current_map";
    /**
     * 激光检测障碍物区域
     */
    String OBSTACLE_REGION = "/obstacle_region";
    /**
     * 激光检测停止移动
     */
    String NODE_MANAGER_LASER_SAFETY_CONTROLLER = "/node_manager/laser_safety_controller";
    /**
     * 激光检测距离
     */
    String NODE_MANAGER_LASER_SAFETY_RANGE = "/node_manager/laser_safety_range";
}
