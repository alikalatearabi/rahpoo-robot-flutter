package mc.csst.com.selfchassislibrary.bean.msg;

public class ObstacleRegionBean {
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
        //0： 周围无障碍物   1：正右侧 90 度范围内有障碍物   2：正前方 90 度范围内有障碍物   4：正左方 90 度范围内有障碍物
        private int data;

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }
}
