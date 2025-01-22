package com.exengg.jupiter.Dto.Requests;

import lombok.Data;

@Data
public class AuthRequest {
    private String emailId;
    private String password;
}
