package org.oladushek.controller;

import java.util.List;

public interface GenericController<DTO, ID> {
    DTO getById(Long id);

    List<DTO> getAll();

    void delete(ID id);
}
