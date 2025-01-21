package mc.csst.com.selfchassislibrary.bean.msg;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/3/14
 * @brief 回充详细状态
 * @Description
 */
public class ChargeServerResultPublishBean {

    /**
     * topic : /charge_server/result
     * msg : {"data":919}
     * op : publish
     * <p>
     * 910: 导航到回充桩附近失败(尝试三次)
     * 912: 后置传感器检测到障碍物在回充座前(尝试三次)
     * 913: 检测回充座失败
     * 914: 没有接收到回充座信息
     * 915: 超过规定时间完成回充行为(60秒)
     * 916: 正在导航回回充座附近
     * 917: 正在根据红外信号对准回充座
     * 918: 用户取消回充行为
     * 919: 等待状态
     */

    private String topic;
    private MsgBean msg;
    private String op;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public static class MsgBean {
        /**
         * data : 919
         */

        private int data;

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }
}
