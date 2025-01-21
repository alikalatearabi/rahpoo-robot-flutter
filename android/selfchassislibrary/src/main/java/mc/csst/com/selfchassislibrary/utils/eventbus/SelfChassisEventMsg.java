package mc.csst.com.selfchassislibrary.utils.eventbus;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/3/24
 * @brief 底盘的eventbus消息
 * @Description
 */
public class SelfChassisEventMsg<T> {
    public static final String CODE_CONNECT_STATE = "connectstate";

    private String code;
    private T data;

    public SelfChassisEventMsg(String code) {
        this.code = code;
    }

    public SelfChassisEventMsg(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
