package mc.csst.com.selfchassislibrary.bean.msg;

import java.util.List;

/**
 * @author zhiyu.zhang
 * @version 1.0
 * @date 2020/2/17
 * @brief 请求信息实体
 * @Description
 */
public class RequestBean {

    /**
     * compression : png
     * topic : /map
     * throttle_rate: 100
     * fragment_size : 2000
     * type : nav_msgs/OccupancyGrid
     * id : get_map
     * op : subscribe
     */

    private String compression;
    private String topic;
    private String service;
    private Integer throttle_rate;
    private Integer fragment_size;
    private String type;
    private String id;
    private String op;
    private ArgsBean args;


    public String getCompression() {
        return compression;
    }

    public void setCompression(String compression) {
        this.compression = compression;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getFragment_size() {
        return fragment_size;
    }

    public void setFragment_size(Integer fragment_size) {
        this.fragment_size = fragment_size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getThrottle_rate() {
        return throttle_rate;
    }

    public void setThrottle_rate(Integer throttle_rate) {
        this.throttle_rate = throttle_rate;
    }

    public static class ArgsBean {
        private String floor_num;
        private String floor_name;
        private Integer cmd;
        private String arg;
        private Integer args;
        private String building_name;
        private Integer op;
        private String poi;
        private Integer patrol_time;
        private Integer patrol_mode;
        private List<String> poi_patrol_list;
        private String str;
        private String spec_version;
        private String map_name;

        public String getSpec_version() {
            return spec_version;
        }

        public void setSpec_version(String spec_version) {
            this.spec_version = spec_version;
        }

        public String getFloor_num() {
            return floor_num;
        }

        public void setFloor_num(String floor_num) {
            this.floor_num = floor_num;
        }

        public String getFloor_name() {
            return floor_name;
        }

        public void setFloor_name(String floor_name) {
            this.floor_name = floor_name;
        }

        public Integer getCmd() {
            return cmd;
        }

        public void setCmd(Integer cmd) {
            this.cmd = cmd;
        }

        public Integer getArgs() {
            return args;
        }

        public void setArgs(Integer args) {
            this.args = args;
        }

        public String getArg() {
            return arg;
        }

        public void setArg(String arg) {
            this.arg = arg;
        }

        public String getBuilding_name() {
            return building_name;
        }

        public void setBuilding_name(String building_name) {
            this.building_name = building_name;
        }

        public Integer getOp() {
            return op;
        }

        public void setOp(Integer op) {
            this.op = op;
        }

        public String getPoi() {
            return poi;
        }

        public void setPoi(String poi) {
            this.poi = poi;
        }

        public Integer getPatrol_time() {
            return patrol_time;
        }

        public void setPatrol_time(Integer patrol_time) {
            this.patrol_time = patrol_time;
        }

        public Integer getPatrol_mode() {
            return patrol_mode;
        }

        public void setPatrol_mode(Integer patrol_mode) {
            this.patrol_mode = patrol_mode;
        }

        public List<String> getPoi_patrol_list() {
            return poi_patrol_list;
        }

        public void setPoi_patrol_list(List<String> poi_patrol_list) {
            this.poi_patrol_list = poi_patrol_list;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public String getMap_name() {
            return map_name;
        }

        public void setMap_name(String map_name) {
            this.map_name = map_name;
        }
    }

    public ArgsBean getArgs() {
        return args;
    }

    public void setArgs(ArgsBean args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "RequestBean{" +
                "compression='" + compression + '\'' +
                ", topic='" + topic + '\'' +
                ", service='" + service + '\'' +
                ", fragment_size=" + fragment_size +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", op='" + op + '\'' +
                ", args=" + args +
                '}';
    }
}
