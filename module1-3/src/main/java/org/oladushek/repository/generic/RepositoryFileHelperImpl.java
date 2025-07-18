package org.oladushek.repository.generic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.oladushek.model.BaseEntity;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositoryFileHelperImpl<T extends BaseEntity> implements RepositoryFileHelper<T> {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type typeToken;

    public RepositoryFileHelperImpl(Type typeToken) {
        this.typeToken = typeToken;
    }

    @Override
    public Long generateAutoIncrementedId(List<T> cuurentList) {
        return cuurentList.stream()
                .mapToLong(T::getId)
                .max().orElse(0) + 1L;
    }

    @Override
    public void writeAll(List<T> listToSave, String fileName) {
        try (Writer writer = new FileWriter(fileName)){
            gson.toJson(listToSave, writer);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<T> readAllWithoutFilter(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {

            List<T> entities = gson.fromJson(reader, typeToken);
            return entities != null ? entities : new ArrayList<T>();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return new ArrayList<T>();
        }
    }
}
