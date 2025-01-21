package mc.csst.com.selfchassislibrary.bean;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/18
 * @brief 点坐标
 * @Description
 */
public class PointBean {
    /**
     * x轴坐标
     */
    private float x;
    /**
     * y轴坐标
     */
    private float y;
    /**
     * 弧度
     */
    private float theta;
    /**
     * 点名称
     */
    private String name;
    /**
     * 点类型(0 一般点位【带方向的标准点位】  11 充电点 【充电桩的位置】)
     */
    private int type;


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

    public float getTheta() {
        return theta;
    }

    public void setTheta(float theta) {
        this.theta = theta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PointBean{" +
                "x=" + x +
                ", y=" + y +
                ", theta=" + theta +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
