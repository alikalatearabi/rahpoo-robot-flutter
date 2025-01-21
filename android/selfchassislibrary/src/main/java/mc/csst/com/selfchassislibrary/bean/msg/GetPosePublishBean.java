package mc.csst.com.selfchassislibrary.bean.msg;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief 接收机器人坐标
 * @Description
 */
public class GetPosePublishBean {

    /**
     * topic : /robot_pose
     * msg : {"y":-0.047,"x":0.009,"theta":0.0114}
     * op : publish
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
         * y : -0.047
         * x : 0.009
         * theta : 0.0114
         */

        private float y;
        private float x;
        private float theta;

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getTheta() {
            return theta;
        }

        public void setTheta(float theta) {
            this.theta = theta;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "y=" + y +
                    ", x=" + x +
                    ", theta=" + theta +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetPosePublishBean{" +
                "topic='" + topic + '\'' +
                ", msg=" + msg +
                ", op='" + op + '\'' +
                '}';
    }
}
