package ru.cipherapp.services;

import ru.cipherapp.models.EncryptKey;

import java.util.List;

/*
interface for abstraction
 */
public interface CrudService<T, ID> {
    List<T> findAll(EncryptKey encryptKey);
    void save(T object, EncryptKey encryptKey);
}
