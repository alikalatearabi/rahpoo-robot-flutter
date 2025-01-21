package mc.csst.com.selfchassislibrary.bean.msg;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/5/19
 * @brief
 * @Description
 */
public class GetCurrentMapBean {

    /**
     * topic : /get_current_map
     * msg : {"data":true}
     * id : get_current_map
     * op : publish
     */

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
        /**
         * data : true
         */

        private boolean data = true;

        public boolean isData() {
            return data;
        }

        public void setData(boolean data) {
            this.data = data;
        }
    }
}
