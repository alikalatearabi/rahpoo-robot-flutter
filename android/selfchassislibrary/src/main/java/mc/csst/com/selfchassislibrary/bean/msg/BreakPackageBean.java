package mc.csst.com.selfchassislibrary.bean.msg;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief 拆包信息
 * @Description
 */
public class BreakPackageBean {


    /**
     * total : 4
     * num : 0
     * data : {xxxxx}
     * id : "0"
     * op : fragment
     * topic : ""
     */

    private int total;
    private int num;
    private String data;
    private String id;
    private String op;
    private String topic;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "BreakPackageBean{" +
                "total=" + total +
                ", num=" + num +
                ", data='" + data + '\'' +
                ", id='" + id + '\'' +
                ", op='" + op + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
