package mc.csst.com.selfchassislibrary.content;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief op常量
 * @Description
 */
public interface OpContent {
    /**
     * 向服务端注册需要发布的话题名称及类型
     */
    public String ADVERTISE = "advertise";
    /**
     * 向服务端注销需要发布的话题名称及类型
     */
    public String UNADVERTISE = "unadvertise";
    /**
     * 向服务端发布话题名对应的具体数据
     */
    public String PUBLISH = "publish";
    /**
     * 向服务端注册需要获取的话题名称及类型
     */
    public String SUBSCRIBE = "subscribe";
    /**
     * 向服务端注销需要获取的话题名称及类型
     */
    public String UNSUBSCRIBE = "unsubscribe";

    /**
     * 分包
     */
    public String FRAGMENT = "fragment";


    public String CALL_SERVICE = "call_service";


    public String SERVICE_RESPONSE = "service_response";
}
