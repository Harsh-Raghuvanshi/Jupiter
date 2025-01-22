package com.exengg.jupiter.Dto.Requests;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String emailId;
}
