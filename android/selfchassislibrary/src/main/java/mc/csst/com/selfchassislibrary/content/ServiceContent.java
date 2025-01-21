package mc.csst.com.selfchassislibrary.content;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/3/9
 * @brief service常量
 * @Description
 */
public interface ServiceContent {
    /**
     * 程序控制框架
     */
    String NODE_MANAGER_CONTROL = "/node_manager_control";

    /**
     * 发布导航点信息(poi名称模式)
     */
    String POI = "/poi";

    /**
     * 回充控制接口
     */
    String START_RECHARGE = "/start_recharge";

    /**
     * 导航速度修改
     */
    String VELOCITY_CONTROL = "/velocity_control";
    /**
     * 获取地图信息接口
     */
    String GET_MAP_INFO = "/get_map_info";

}
