package org.oladushek.module25.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.oladushek.module25.entity.FileEntity;
import org.oladushek.module25.entity.UserEntity;
import org.oladushek.module25.rest.dto.FileResponseDto;
import org.oladushek.module25.rest.dto.UserDto;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileResponseDto map(FileEntity userEntity);

}
