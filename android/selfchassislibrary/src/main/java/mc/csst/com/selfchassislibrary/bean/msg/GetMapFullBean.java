package mc.csst.com.selfchassislibrary.bean.msg;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief 获取整包地图数据
 * @Description
 */
public class GetMapFullBean {

    /**
     * topic : \/map
     * msg : {"info":{"origin":{"position":{"y":-9.6,"x":-9.6},"orientation":{"y":0,"x":0,"z":0,"w":1}},"width":384,"resolution":0.05,"height":384},"data":"iVBORw0KGgoAAAANSUhEUg "}
     * op : png
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
         * info : {"origin":{"position":{"y":-9.6,"x":-9.6},"orientation":{"y":0,"x":0,"z":0,"w":1}},"width":384,"resolution":0.05,"height":384}
         * data : iVBORw0KGgoAAAANSUhEUg
         */

        private InfoBean info;
        private String data;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public static class InfoBean {
            /**
             * origin : {"position":{"y":-9.6,"x":-9.6},"orientation":{"y":0,"x":0,"z":0,"w":1}}
             * width : 384
             * resolution : 0.05
             * height : 384
             */

            private OriginBean origin;
            private int width;
            private float resolution;
            private int height;

            public OriginBean getOrigin() {
                return origin;
            }

            public void setOrigin(OriginBean origin) {
                this.origin = origin;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public float getResolution() {
                return resolution;
            }

            public void setResolution(float resolution) {
                this.resolution = resolution;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public static class OriginBean {
                /**
                 * position : {"y":-9.6,"x":-9.6}
                 * orientation : {"y":0,"x":0,"z":0,"w":1}
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
                     * y : -9.6
                     * x : -9.6
                     */

                    private float y;
                    private float x;

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
                }

                public static class OrientationBean {
                    /**
                     * y : 0
                     * x : 0
                     * z : 0
                     * w : 1
                     */

                    private float y;
                    private float x;
                    private float z;
                    private float w;

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
            }

            @Override
            public String toString() {
                return "InfoBean{" +
                        "origin=" + origin +
                        ", width=" + width +
                        ", resolution=" + resolution +
                        ", height=" + height +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "info=" + info +
                    ", data='" + data + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetMapFullBean{" +
                "topic='" + topic + '\'' +
                ", msg=" + msg +
                ", op='" + op + '\'' +
                '}';
    }
}
