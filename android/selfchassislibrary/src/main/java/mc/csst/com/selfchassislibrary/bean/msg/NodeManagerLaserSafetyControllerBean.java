package mc.csst.com.selfchassislibrary.bean.msg;

public class NodeManagerLaserSafetyControllerBean {

    private String topic;
    private MsgBean msg;
    private String id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public static class MsgBean {
        //设置为 true 为开启检测到周围障碍物停止移动功能 false 为关闭检测到周围障碍物停止移动功能
        private boolean data;

        public boolean isData() {
            return data;
        }

        public void setData(boolean data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "data=" + data +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NodeManagerLaserSafetyControllerBean{" +
                "topic='" + topic + '\'' +
                ", msg=" + msg +
                ", id='" + id + '\'' +
                ", op='" + op + '\'' +
                '}';
    }
}
