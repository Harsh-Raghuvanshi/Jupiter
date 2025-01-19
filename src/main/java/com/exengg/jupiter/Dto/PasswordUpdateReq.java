package com.exengg.jupiter.Dto;

import lombok.Data;

@Data
public class PasswordUpdateReq {
    private String userId;
    private String newPassword;
}
