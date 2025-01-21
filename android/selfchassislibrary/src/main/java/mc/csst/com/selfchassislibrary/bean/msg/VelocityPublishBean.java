package mc.csst.com.selfchassislibrary.bean.msg;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief 接收速度控制命令
 * @Description
 */
public class VelocityPublishBean {


    /**
     * op : publish
     * topic : /cmd_vel_mux/input/teleop
     * id : velocity_control
     * msg : {"angular":{"z":0.2},"linear":{"x":0.2}}
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
         * angular : {"z":0.2}
         * linear : {"x":0.2}
         */

        private AngularBean angular;
        private LinearBean linear;

        public AngularBean getAngular() {
            return angular;
        }

        public void setAngular(AngularBean angular) {
            this.angular = angular;
        }

        public LinearBean getLinear() {
            return linear;
        }

        public void setLinear(LinearBean linear) {
            this.linear = linear;
        }

        public static class AngularBean {
            /**
             * z : 0.2
             */

            private float z;

            public float getZ() {
                return z;
            }

            public void setZ(float z) {
                this.z = z;
            }

            @Override
            public String toString() {
                return "AngularBean{" +
                        "z=" + z +
                        '}';
            }
        }

        public static class LinearBean {
            /**
             * x : 0.2
             */

            private float x;

            public float getX() {
                return x;
            }

            public void setX(float x) {
                this.x = x;
            }

            @Override
            public String toString() {
                return "LinearBean{" +
                        "x=" + x +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "angular=" + angular +
                    ", linear=" + linear +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "VelocityPublishBean{" +
                "op='" + op + '\'' +
                ", topic='" + topic + '\'' +
                ", id='" + id + '\'' +
                ", msg=" + msg +
                '}';
    }
}
