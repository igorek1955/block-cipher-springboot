package ru.cipherapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cipherapp.models.Catalog;

import java.util.Optional;

@Repository
public interface CatalogRepository extends CrudRepository<Catalog, Long> {
    Optional<Catalog> findByName(String name);
}
