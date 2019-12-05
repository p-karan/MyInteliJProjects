package com.training.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private long phone;
}
