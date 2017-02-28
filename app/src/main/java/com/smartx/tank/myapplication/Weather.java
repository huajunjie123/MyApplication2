package com.smartx.tank.myapplication;

/**
 * Created by daniel on 17/2/27.
 */

public class Weather {

    /**
     * 城市
     */
    public String city;
    /**
     * 日期
     */
    public String date;
    /**
     * 温度
     */
    public String temperature;
    /**
     * 风向
     */
    public String direction;
    /**
     * 风力
     */
    public String power;
    /**
     * 天气状况
     */
    public String status;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("城市:" + city + "\r\n");
        builder.append("日期:" + date + "\r\n");
        builder.append("天气状况:" + status + "\r\n");
        builder.append("温度:" + temperature + "\r\n");
        builder.append("风向:" + direction + "\r\n");
        builder.append("风力:" + power + "\r\n");
        return builder.toString();
    }

    public void init() {
        city = "上海";
        date = "2017-02-27";
        temperature = "40度";
        direction = "东南风";
        power = " 3 级";
        status = "不错";
    }
}
