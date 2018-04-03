package hu.study.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import hu.study.model.dto.UserDto;
import hu.study.model.entity.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser( UserDto userDto );

}
