package ru.kotikov.blog.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.kotikov.blog.dtos.UserDto;
import ru.kotikov.blog.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userDto.password", target = "password", qualifiedByName = "stringToCharArray")
    User userDtoToUser(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto userToUserDto(User user);

    @Named("stringToCharArray")
    default char[] stringToCharArray(String str) {
        return str.toCharArray();
    }

}
