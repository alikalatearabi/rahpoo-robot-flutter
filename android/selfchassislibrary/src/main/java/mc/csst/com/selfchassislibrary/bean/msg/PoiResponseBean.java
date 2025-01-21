package mc.csst.com.selfchassislibrary.bean.msg;

import java.util.List;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/3/14
 * @brief 发布导航点信息(poi名称模式)（响应）
 * @Description
 */
public class PoiResponseBean {

    /**
     * id : service_poi
     * values : {"avaliable_list":["P2","P3","P0","P1"],"success":true}
     * result : true
     * service : /poi
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
         * avaliable_list : ["P2","P3","P0","P1"]
         * success : true
         */

        private boolean success;
        private List<String> avaliable_list;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public List<String> getAvaliable_list() {
            return avaliable_list;
        }

        public void setAvaliable_list(List<String> avaliable_list) {
            this.avaliable_list = avaliable_list;
        }
    }
}
