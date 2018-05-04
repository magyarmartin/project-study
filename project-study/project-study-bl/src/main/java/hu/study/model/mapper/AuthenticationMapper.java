package hu.study.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import hu.study.model.dto.AuthenticationDto;
import hu.study.model.entity.Token;
import hu.study.model.entity.User;

/**
 * Simple mapper from {@link User} and {@link Token} to {@link AuthenticationDto}.
 *
 * @author magyarm
 *
 */
@Mapper
public interface AuthenticationMapper {

    AuthenticationMapper INSTANCE = Mappers.getMapper(AuthenticationMapper.class);

    @Mapping( source = "token.token", target = "token" )
    AuthenticationDto userAndTokenToAuthenticationDto( final User user, final Token token );

}
