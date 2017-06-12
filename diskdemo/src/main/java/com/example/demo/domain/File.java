package com.example.demo.domain;

/**
 * Created by Lenovo on 2017/6/11.
 */
public class File {
    private int id;//编号
    private String introduce;//文件介绍
    private String username;//上传文件人的账号
    private String filepath;//上传文件的路径
    private String time;//上传时间

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    private String mark;//是否被查看的标记
    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setId(int id) {
        this.id = id;
    }
}
