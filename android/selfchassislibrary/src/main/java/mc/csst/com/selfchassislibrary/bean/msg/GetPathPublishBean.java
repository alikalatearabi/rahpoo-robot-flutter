package mc.csst.com.selfchassislibrary.bean.msg;

import java.util.List;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief 返回的导航规划路径
 * @Description
 */
public class GetPathPublishBean {

    /**
     * topic : \/global_path
     * msg : {"px":[-5.1999999344,-5.2023387254,-5.1985957444,-5.1948771774,-5.1908435165,-0.0241644386,1.431E-7,0],"py":[-3.2999999061,-3.255480481,-3.2307623868,-0.0064093114,1.431E-7,0],"pt":[0.01,0.01]}
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
        private List<Float> px;
        private List<Float> py;
        private List<Float> pt;

        public List<Float> getPx() {
            return px;
        }

        public void setPx(List<Float> px) {
            this.px = px;
        }

        public List<Float> getPy() {
            return py;
        }

        public void setPy(List<Float> py) {
            this.py = py;
        }

        public List<Float> getPt() {
            return pt;
        }

        public void setPt(List<Float> pt) {
            this.pt = pt;
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "px=" + px +
                    ", py=" + py +
                    ", pt=" + pt +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetPathPublishBean{" +
                "topic='" + topic + '\'' +
                ", msg=" + msg +
                ", op='" + op + '\'' +
                '}';
    }
}
