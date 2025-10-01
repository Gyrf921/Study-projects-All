package org.oladushek.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.oladushek.entity.FileEntity;
import org.oladushek.entity.UserEntity;

@Data
@AllArgsConstructor
public class EventDto {

    private UserDto user;

    private FileDto file;
}
