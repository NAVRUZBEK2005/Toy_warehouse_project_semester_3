package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.RoleEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private RoleEnum role;
    private String username;
    private String password;
    private String phoneNumber;


    public UserDTO(int id, String firstName, String lastName, RoleEnum role, String password, String phoneNumber) {

    }
}
