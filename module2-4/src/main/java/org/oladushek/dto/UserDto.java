package org.oladushek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.oladushek.entity.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    public UserDto(UserEntity entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
