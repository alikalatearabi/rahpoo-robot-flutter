package mc.csst.com.selfchassislibrary.bean.msg;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/3/10
 * @brief 机器人全局状态
 * @Description
 */
public class RobotStatusPublishBean {

    /**
     * topic : /robot_status
     * msg : {"current_building_name":"tefa","soft_estop":false,"battery":0,"charger":0,"nav_status":2,"patrol_status":0,"current_floor_name":"4","current_goal_coordinate":{"y":0.256,"x":-0.721,"theta":-2.667},"hard_estop":false,"control_state":2,"current_goal_name":"P0"}
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
        /**
         * current_building_name : tefa
         * soft_estop : false
         * battery : 0
         * charger : 0
         * nav_status : 2
         * patrol_status : 0
         * current_floor_name : 4
         * current_goal_coordinate : {"y":0.256,"x":-0.721,"theta":-2.667}
         * hard_estop : false
         * control_state : 2
         * current_goal_name : P0
         */

        private String current_building_name;
        private boolean soft_estop;
        private int battery;
        private int charger;
        private int nav_status;
        private int patrol_status;
        private String current_floor_name;
        private CurrentGoalCoordinateBean current_goal_coordinate;
        private boolean hard_estop;
        private int control_state;
        private String current_goal_name;

        public String getCurrent_building_name() {
            return current_building_name;
        }

        public void setCurrent_building_name(String current_building_name) {
            this.current_building_name = current_building_name;
        }

        public boolean isSoft_estop() {
            return soft_estop;
        }

        public void setSoft_estop(boolean soft_estop) {
            this.soft_estop = soft_estop;
        }

        public int getBattery() {
            return battery;
        }

        public void setBattery(int battery) {
            this.battery = battery;
        }

        public int getCharger() {
            return charger;
        }

        public void setCharger(int charger) {
            this.charger = charger;
        }

        public int getNav_status() {
            return nav_status;
        }

        public void setNav_status(int nav_status) {
            this.nav_status = nav_status;
        }

        public int getPatrol_status() {
            return patrol_status;
        }

        public void setPatrol_status(int patrol_status) {
            this.patrol_status = patrol_status;
        }

        public String getCurrent_floor_name() {
            return current_floor_name;
        }

        public void setCurrent_floor_name(String current_floor_name) {
            this.current_floor_name = current_floor_name;
        }

        public CurrentGoalCoordinateBean getCurrent_goal_coordinate() {
            return current_goal_coordinate;
        }

        public void setCurrent_goal_coordinate(CurrentGoalCoordinateBean current_goal_coordinate) {
            this.current_goal_coordinate = current_goal_coordinate;
        }

        public boolean isHard_estop() {
            return hard_estop;
        }

        public void setHard_estop(boolean hard_estop) {
            this.hard_estop = hard_estop;
        }

        public int getControl_state() {
            return control_state;
        }

        public void setControl_state(int control_state) {
            this.control_state = control_state;
        }

        public String getCurrent_goal_name() {
            return current_goal_name;
        }

        public void setCurrent_goal_name(String current_goal_name) {
            this.current_goal_name = current_goal_name;
        }

        public static class CurrentGoalCoordinateBean {
            /**
             * y : 0.256
             * x : -0.721
             * theta : -2.667
             */

            private float y;
            private float x;
            private float theta;

            public float getY() {
                return y;
            }

            public void setY(float y) {
                this.y = y;
            }

            public float getX() {
                return x;
            }

            public void setX(float x) {
                this.x = x;
            }

            public float getTheta() {
                return theta;
            }

            public void setTheta(float theta) {
                this.theta = theta;
            }
        }
    }
}
