package mc.csst.com.selfchassislibrary.bean.msg;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/3/14
 * @brief 回充控制接口（响应）
 * @Description
 */
public class StartRechargeResponseBean {

    /**
     * id : service_start_recharge
     * values : {"info":"using POI name home as home pose and start recharging","result":1}
     * result : true
     * service : /start_recharge
     * op : service_response
     */

    private String id;
    private ValuesBean values;
    private boolean result;
    private String service;
    private String op;

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
         * info : using POI name home as home pose and start recharging
         * result : 1
         */

        private String info;
        private int result;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }
    }
}
