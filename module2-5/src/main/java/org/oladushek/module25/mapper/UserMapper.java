package org.oladushek.module25.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.oladushek.module25.entity.UserEntity;
import org.oladushek.module25.rest.dto.UserDto;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity map(UserDto dto);
}
