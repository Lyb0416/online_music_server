package com.lyb.olinemusicserver.model.request;

import lombok.Data;

@Data
public class UserSupportRequest {

    private Integer id;

    private Integer commentId;

    private String userId;
}
