package org.oladushek.repository.generic;

import java.util.List;

public interface FileHelper<T> {

    void writeAll(List<T> listToSave, String fileName);

    List<T> readAllWithoutFilter(String fileName);

}
