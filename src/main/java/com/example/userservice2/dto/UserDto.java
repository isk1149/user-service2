package com.example.userservice2.dto;


import com.example.userservice2.vo.ResponseOrder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String email;
    private String pwd;
    private String name;
    private String userId;
    private Date createdAt;
    private String encryptedPwd;

    private List<ResponseOrder> orders;
}
