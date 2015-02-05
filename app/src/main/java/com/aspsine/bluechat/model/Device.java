package com.aspsine.bluechat.model;

/**
 * Created by littlexi on 2015/1/30.
 */
public class Device {
    public String id;
    public String name;
    public String address;

    public Device(){

    }

    public Device(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
