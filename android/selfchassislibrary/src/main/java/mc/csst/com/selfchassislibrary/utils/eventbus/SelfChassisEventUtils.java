package mc.csst.com.selfchassislibrary.utils.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/3/24
 * @brief eventbus工具类
 * @Description
 */
public class SelfChassisEventUtils {
    /**
     * 注册 EventBus
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(subscriber)) {
            eventBus.register(subscriber);
        }
    }

    /**
     * 解除注册 EventBus
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber);
        }
    }

    /**
     * 发送事件消息
     *
     * @param event
     */
    public static void post(SelfChassisEventMsg event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发送粘性事件消息
     *
     * @param event
     */
    public static void postSticky(SelfChassisEventMsg event) {
        EventBus.getDefault().postSticky(event);
    }
}
