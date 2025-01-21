package mc.csst.com.selfchassislibrary.bean.msg;

public class NodeManagerLaserSafetyRangeBean {

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
        private float data;

        public float getData() {
            return data;
        }

        public void setData(float data) {
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
        return "NodeManagerLaserSafetyRangeBean{" +
                "topic='" + topic + '\'' +
                ", msg=" + msg +
                ", id='" + id + '\'' +
                ", op='" + op + '\'' +
                '}';
    }
}
