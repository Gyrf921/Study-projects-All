package org.oladushek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventDto {

    private UserDto user;

    private FileDto file;
}
