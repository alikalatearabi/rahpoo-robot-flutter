package mc.csst.com.selfchassislibrary.bean.msg;

import java.util.List;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief 接收雷达点云数据
 * @Description
 */
public class LaserDataPublishBean {

    /**
     * topic : /laser_data
     * msg : {"px":[-0.2764660083,0.3991022995,1.6344430933,6.7809104004,3.2198716479,0.9409952679,-0.103713031,-1.0924293294,-2.927517425,-6.9109612059,-2.4952350525,-0.9657125186],"py":[-1.1970169552,-1.205381846,-1.2136405211,-0.4152919407,1.71844952,1.7411410892,1.7474594,1.7568190952,1.7703766989,0.340344386,-1.1785926253,-1.1910652306],"pt":[0,0]}
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
        private List<Integer> pt;

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

        public List<Integer> getPt() {
            return pt;
        }

        public void setPt(List<Integer> pt) {
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
        return "LaserDataPublishBean{" +
                "topic='" + topic + '\'' +
                ", msg=" + msg +
                ", op='" + op + '\'' +
                '}';
    }
}
