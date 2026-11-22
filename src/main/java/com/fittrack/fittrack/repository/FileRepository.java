package com.fittrack.fittrack.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FileRepository<T extends Serializable> implements Repository<T> {
    private final File file;
    private final Function<T, String> idExtractor;

    public FileRepository(String filePath, Function<T, String> idExtractor) {
        this.file = new File(filePath);
        this.idExtractor = idExtractor;
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    @Override
    public synchronized T save(T entity) {
        List<T> items = readFromFile();
        String targetId = idExtractor.apply(entity);
        items.removeIf(item -> idExtractor.apply(item).equals(targetId));
        items.add(entity);
        writeToFile(items);
        return entity;
    }

    @Override
    public synchronized Optional<T> findById(String id) {
        return readFromFile().stream()
                .filter(item -> idExtractor.apply(item).equals(id))
                .findFirst();
    }

    @Override
    public synchronized List<T> findAll() {
        return readFromFile();
    }

    @Override
    public synchronized boolean deleteById(String id) {
        List<T> items = readFromFile();
        boolean removed = items.removeIf(item -> idExtractor.apply(item).equals(id));
        if (removed) {
            writeToFile(items);
        }
        return removed;
    }

    @SuppressWarnings("unchecked")
    private List<T> readFromFile() {
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to read from " + file.getPath(), e);
        }
    }

    private void writeToFile(List<T> items) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<>(items));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to " + file.getPath(), e);
        }
    }
}