package mc.csst.com.selfchassislibrary.bean;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/24
 * @brief 地图相关的信息
 * @Description
 */
public class MapInfoBean {
    /**
     * 高
     */
    private int h;
    /**
     * 宽
     */
    private int w;
    /**
     * 分辨率
     */
    private float r;
    /**
     * 初始点x
     */
    private float x;
    /**
     * 初始点y
     */
    private float y;

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

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
