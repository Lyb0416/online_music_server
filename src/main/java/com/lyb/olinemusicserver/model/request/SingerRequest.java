package com.lyb.olinemusicserver.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class SingerRequest {
    private Integer id;
    private String name;
    private Integer gender;
    private String pic;
    private Date birth;
    private String location;
    private String introduction;
}
