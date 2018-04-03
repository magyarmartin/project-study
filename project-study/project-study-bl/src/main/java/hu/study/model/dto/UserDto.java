package hu.study.model.dto;

import lombok.Data;

@Data
public class UserDto implements BaseDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String description;

    private boolean instructor;

}
