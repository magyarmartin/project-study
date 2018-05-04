package hu.study.model.dto;

import lombok.Data;

/**
 * Created by martin4955 on 2018. 03. 27..
 */
@Data
public class AuthenticationDto extends UserDto implements BaseDto {

    private String token;

}
