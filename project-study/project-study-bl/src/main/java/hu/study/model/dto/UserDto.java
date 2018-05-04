package hu.study.model.dto;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;

/**
 * Created by martin4955 on 2018. 04. 04..
 */
@Data
public class UserDto implements BaseDto {

    private String firstName;

    private String lastName;

    private String email;

    private String description;

    private boolean instructor;

}
