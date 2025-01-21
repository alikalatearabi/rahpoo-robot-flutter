package mc.csst.com.selfchassislibrary.bean.msg;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief 发送取消目标点导航
 * @Description
 */
public class CancelGoalPublishBean {
    /**
     * op : publish
     * topic : /move_base/cancel
     * id : cancel_goal
     * msg : {"stamp":"","id":""}
     */

    private String op;
    private String topic;
    private String id;
    private MsgBean msg;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * stamp :
         * id :
         */

        private String stamp;
        private String id;

        public String getStamp() {
            return stamp;
        }

        public void setStamp(String stamp) {
            this.stamp = stamp;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "stamp='" + stamp + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CancelGoalPublishBean{" +
                "op='" + op + '\'' +
                ", topic='" + topic + '\'' +
                ", id='" + id + '\'' +
                ", msg=" + msg +
                '}';
    }
}
