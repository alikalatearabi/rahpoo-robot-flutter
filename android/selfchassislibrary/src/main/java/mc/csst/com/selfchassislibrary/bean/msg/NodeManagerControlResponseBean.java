package mc.csst.com.selfchassislibrary.bean.msg;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/3/14
 * @brief 程序控制框架（响应）
 * @Description
 */
public class NodeManagerControlResponseBean {

    /**
     * info : this message success
     * id : service_node_manager_control
     * values : {"text":"bringup mapping success!","result":0}
     * result : true
     * service : /node_manager_control
     * op : service_response
     */
    private String info;
    private String id;
    private ValuesBean values;
    private boolean result;
    private String service;
    private String op;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ValuesBean getValues() {
        return values;
    }

    public void setValues(ValuesBean values) {
        this.values = values;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public static class ValuesBean {
        /**
         * text : bringup mapping success!
         * result : 0
         */

        private String text;
        private int result;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }
    }
}
