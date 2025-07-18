package org.oladushek.repository.generic;

import java.util.List;

public interface AutoIncrementer <T>{
    Long generateAutoIncrementedId(List<T> cuurentList);
}
