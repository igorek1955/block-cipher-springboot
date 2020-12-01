package ru.cipherapp.services;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.cipherapp.models.Catalog;
import ru.cipherapp.models.EncryptKey;
import ru.cipherapp.models.User;
import ru.cipherapp.repositories.CatalogRepository;
import ru.cipherapp.repositories.UserRepository;

import java.util.*;


/*
Service class for handling catalog repository
 */
@Service
public class CatalogService implements CrudService<Catalog, Long> {

    private static final Logger logger = LoggerFactory.getLogger(CatalogService.class);

    private final UserRepository userRepository;

    private final CatalogRepository catalogRepository;

    public CatalogService(UserRepository userRepository, CatalogRepository catalogRepository) {
        this.userRepository = userRepository;
        this.catalogRepository = catalogRepository;
    }


    @Override
    public List<Catalog> findAll(EncryptKey encryptKey) {
       logger.info("findAll was initiated with key: " + encryptKey);

        List<Catalog> catalogs = new ArrayList<>();
        catalogRepository.findAll().forEach(catalogs::add);

        //checking if we have catalog items with the specified key
        for(Catalog c: catalogs){
            if(c.getPassword().equals(encryptKey.getKey())){
                c.setName(EncryptorService.decrypt(c.getName(), encryptKey));
                c.setIp(EncryptorService.decrypt(c.getIp(), encryptKey));
                c.setOperatingSystem(EncryptorService.decrypt(c.getOperatingSystem(), encryptKey));
                c.setPassword(EncryptorService.decrypt(c.getPassword(), encryptKey));
                User user = c.getUser();
                user.setName(EncryptorService.decrypt(user.getName(), encryptKey));
            }
        }
        return catalogs;
    }


    @Override
    public void save(Catalog catalog, EncryptKey encryptKey) {
        //encoding data and then persisting it into database
        catalog.setName(EncryptorService.encrypt(catalog.getName(), encryptKey));
        catalog.setIp(EncryptorService.encrypt(catalog.getIp(), encryptKey));
        catalog.setOperatingSystem(EncryptorService.encrypt(catalog.getOperatingSystem(), encryptKey));
        User user = catalog.getUser();
        user.setName(EncryptorService.encrypt(user.getName(), encryptKey));
        logger.info("save catalog item was initiated with key: " + encryptKey);
        logger.info("object properties: " + catalog.toString());
        catalogRepository.save(catalog);
    }



    public Set<Catalog> getCatalogs() {
        Set<Catalog> catalogSet = new HashSet<>();
        catalogRepository.findAll().iterator().forEachRemaining(catalogSet::add);
        return catalogSet;
    }
}
