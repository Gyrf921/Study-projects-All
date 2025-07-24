package org.oladushek.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatusEntity extends BaseEntity{

    @ToString.Exclude
    private Status status;
}
