package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.RoleEnum;

@Getter
@Setter
@AllArgsConstructor
public class User   {
    private Integer id;
    private String firstName;
    private String lastName;
    private RoleEnum role;
    private String password;
    private String phoneNumber;

}

