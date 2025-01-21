package mc.csst.com.selfchassislibrary.bean.msg;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief 发送发布目标点
 * @Description
 */
public class SendGoalPublishBean {
    /**
     * op : publish
     * topic : /move_base_simple/goal
     * id : ”send_goal”
     * msg : {"header":{"frame_id":"map"},"pose":{"position":{"x":0.161087304354,"y":-1.66296613216},"orientation":{"x":0,"y":0,"z":-0.0844636898107,"w":0.996426557807}}}
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
         * header : {"frame_id":"map"}
         * pose : {"position":{"x":0.161087304354,"y":-1.66296613216},"orientation":{"x":0,"y":0,"z":-0.0844636898107,"w":0.996426557807}}
         */

        private HeaderBean header;
        private PoseBean pose;

        public HeaderBean getHeader() {
            return header;
        }

        public void setHeader(HeaderBean header) {
            this.header = header;
        }

        public PoseBean getPose() {
            return pose;
        }

        public void setPose(PoseBean pose) {
            this.pose = pose;
        }

        public static class HeaderBean {
            /**
             * frame_id : map
             */

            private String frame_id;

            public String getFrame_id() {
                return frame_id;
            }

            public void setFrame_id(String frame_id) {
                this.frame_id = frame_id;
            }

            @Override
            public String toString() {
                return "HeaderBean{" +
                        "frame_id='" + frame_id + '\'' +
                        '}';
            }
        }

        public static class PoseBean {
            /**
             * position : {"x":0.161087304354,"y":-1.66296613216}
             * orientation : {"x":0,"y":0,"z":-0.0844636898107,"w":0.996426557807}
             */

            private PositionBean position;
            private OrientationBean orientation;

            public PositionBean getPosition() {
                return position;
            }

            public void setPosition(PositionBean position) {
                this.position = position;
            }

            public OrientationBean getOrientation() {
                return orientation;
            }

            public void setOrientation(OrientationBean orientation) {
                this.orientation = orientation;
            }

            public static class PositionBean {
                /**
                 * x : 0.161087304354
                 * y : -1.66296613216
                 */

                private float x;
                private float y;

                public float getX() {
                    return x;
                }

                public void setX(float x) {
                    this.x = x;
                }

                public float getY() {
                    return y;
                }

                public void setY(float y) {
                    this.y = y;
                }
            }

            public static class OrientationBean {
                /**
                 * x : 0
                 * y : 0
                 * z : -0.0844636898107
                 * w : 0.996426557807
                 */

                private float x;
                private float y;
                private float z;
                private float w;

                public float getX() {
                    return x;
                }

                public void setX(float x) {
                    this.x = x;
                }

                public float getY() {
                    return y;
                }

                public void setY(float y) {
                    this.y = y;
                }

                public float getZ() {
                    return z;
                }

                public void setZ(float z) {
                    this.z = z;
                }

                public float getW() {
                    return w;
                }

                public void setW(float w) {
                    this.w = w;
                }
            }

            @Override
            public String toString() {
                return "PoseBean{" +
                        "position=" + position +
                        ", orientation=" + orientation +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "header=" + header +
                    ", pose=" + pose +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SendGoalPublishBean{" +
                "op='" + op + '\'' +
                ", topic='" + topic + '\'' +
                ", id='" + id + '\'' +
                ", msg=" + msg +
                '}';
    }
}
