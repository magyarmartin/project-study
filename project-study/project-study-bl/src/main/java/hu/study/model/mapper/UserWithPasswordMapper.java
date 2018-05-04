package hu.study.model.mapper;

import hu.study.model.dto.UserWIthPasswordDto;
import hu.study.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserWithPasswordMapper {

    UserWithPasswordMapper INSTANCE = Mappers.getMapper(UserWithPasswordMapper.class);

    User userWithPasswordDtoToUser( final UserWIthPasswordDto userWIthPasswordDto );

}
