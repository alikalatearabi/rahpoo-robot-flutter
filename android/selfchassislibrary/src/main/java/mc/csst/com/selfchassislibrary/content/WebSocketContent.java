package mc.csst.com.selfchassislibrary.content;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/7
 * @brief websocket常量
 * @Description
 */
public interface WebSocketContent {
    /**
     * 正常关闭; 无论为何目的而创建, 该链接都已成功完成任务.
     */
    public int CLOSE_NORMAL = 1000;
    /**
     * 终端离开, 可能因为服务端错误, 也可能因为浏览器正从打开连接的页面跳转离开.
     */
    public int CLOSE_GOING_AWAY = 1001;
    /**
     * 由于协议错误而中断连接.
     */
    public int CLOSE_PROTOCOL_ERROR = 1002;
    /**
     * 由于接收到不允许的数据类型而断开连接 (如仅接收文本数据的终端接收到了二进制数据).
     */
    public int CLOSE_UNSUPPORTED = 1003;
}
