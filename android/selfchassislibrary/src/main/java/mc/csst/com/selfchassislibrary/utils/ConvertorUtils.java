package mc.csst.com.selfchassislibrary.utils;

import mc.csst.com.selfchassislibrary.bean.EularBean;
import mc.csst.com.selfchassislibrary.bean.QuaternionBean;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/18
 * @brief 转换工具类
 * @Description
 */
public class ConvertorUtils {
    /**
     * 欧拉角转四元素
     *
     * @param r 横滚(单位：弧度)
     * @param p 俯仰(单位：弧度)
     * @param y 偏航(单位：弧度)
     * @return 四元素实体类
     */
    public static QuaternionBean eular2quaternion(float r, float p, float y) {
        QuaternionBean quaternion = new QuaternionBean();
        float ww = (float) (Math.cos(r / 2) * Math.cos(p / 2) * Math.cos(y / 2) + Math.sin(r / 2) * Math.sin(p / 2) * Math.sin(y / 2));
        float xx = (float) (Math.sin(r / 2) * Math.cos(p / 2) * Math.cos(y / 2) - Math.cos(r / 2) * Math.sin(p / 2) * Math.sin(y / 2));
        float yy = (float) (Math.cos(r / 2) * Math.sin(p / 2) * Math.cos(y / 2) + Math.sin(r / 2) * Math.cos(p / 2) * Math.sin(y / 2));
        float zz = (float) (Math.cos(r / 2) * Math.cos(p / 2) * Math.sin(y / 2) - Math.sin(r / 2) * Math.sin(p / 2) * Math.cos(y / 2));

        quaternion.setW(ww);
        quaternion.setX(xx);
        quaternion.setY(yy);
        quaternion.setZ(zz);

        return quaternion;
    }

    /**
     * 欧拉角转四元素
     *
     * @param eular 欧拉角实体
     * @return 四元素实体类
     */
    public static QuaternionBean eular2quaternion(EularBean eular) {
        return eular2quaternion(eular.getR(), eular.getP(), eular.getY());

    }

    /**
     * 四元素转欧拉角
     *
     * @param w w参数
     * @param x x参数
     * @param y y参数
     * @param z z参数
     * @return 欧拉角实体（弧度）
     */
    public static EularBean quaternion2eular(float w, float x, float y, float z) {
        EularBean eular = new EularBean();

        float rr = (float) Math.atan(2 * (w * x + y * z) / (1 - 2 * (x * x + y * y)));
        float pp = (float) Math.asin(2 * (w * y - z * x));
        float yy = (float) Math.atan(2 * (w * z + x * y) / (1 - 2 * (y * y + z * z)));

        eular.setR(rr);
        eular.setP(pp);
        eular.setY(yy);

        return eular;
    }

    /**
     * 四元素转欧拉角
     *
     * @param quaternion 四元素实体
     * @return 欧拉角实体（弧度）
     */
    public static EularBean quaternion2eular(QuaternionBean quaternion) {
        return quaternion2eular(quaternion.getW(), quaternion.getX(), quaternion.getY(), quaternion.getZ());
    }

    /**
     * 获得两点之间的距离
     *
     * @param x1 第一个点的x轴坐标
     * @param y1 第一个点的y轴坐标
     * @param x2 第二个点的x轴坐标
     * @param y2 第二个点的y轴坐标
     * @return
     */
    public static float getDistanceBetween2Points(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(y1 - y2, 2) + Math.pow(x1 - x2, 2));
    }
}
