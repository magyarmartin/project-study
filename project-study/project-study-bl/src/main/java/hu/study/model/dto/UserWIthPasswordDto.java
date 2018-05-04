package hu.study.model.dto;

import lombok.Data;

@Data
public class UserWIthPasswordDto extends UserDto implements BaseDto {

    private String password;

}
