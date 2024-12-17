package de.iammichaelpeter.airbnbclone.user.mapper;

import org.mapstruct.Mapper;

import de.iammichaelpeter.airbnbclone.user.application.dto.ReadUserDTO;
import de.iammichaelpeter.airbnbclone.user.domain.Authority;
import de.iammichaelpeter.airbnbclone.user.domain.User;


@Mapper(componentModel = "spring")
public interface UserMapper {

    ReadUserDTO readUserDTOToUser(User user);

    default String mapAuthoritiesToString(Authority authority) {
        return authority.getName();
    }
}
