package ru.cipherapp.bootstrap;


import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.cipherapp.repositories.CatalogRepository;
import ru.cipherapp.repositories.UserRepository;

/*
This class automatically loads data into database, can be filled out if one desires
 */
@Profile("default")
@Component
public class InitDataWeb implements ApplicationListener<ContextRefreshedEvent> {

    private final CatalogRepository catalogRepository;
    private final UserRepository userRepository;



    public InitDataWeb(CatalogRepository catalogRepository, UserRepository userRepository){
        this.catalogRepository = catalogRepository;
        this.userRepository = userRepository;
    }


    @SneakyThrows
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {

    }
}
