package ru.cipherapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.cipherapp.models.Catalog;
import ru.cipherapp.models.EncryptKey;
import ru.cipherapp.services.CatalogService;
import ru.cipherapp.services.UserService;

import java.util.ArrayList;
import java.util.List;

/*
Controller for handling web requests
 */

@Controller
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    @GetMapping({"/", ""})
    public String getTasks(Model model) {
        EncryptKey encryptKey = new EncryptKey();
        encryptKey.setKey("1");
        List<Catalog> catalogItems = new ArrayList<>(catalogService.findAll(encryptKey));
        model.addAttribute("catalogs", catalogItems);
        model.addAttribute("encryptkey", encryptKey);
        return "catalog";
    }

    @GetMapping("/catalog")
    public String getTasksWithKey(@RequestParam(name = "key", required = false) String key, Model model) {
        logger.info("key is " + key);

        EncryptKey encryptKey = new EncryptKey();

        if (key == null) {
            encryptKey.setKey("1");
        } else{
            encryptKey.setKey(key);
        }

        List<Catalog> catalogItems = new ArrayList<>(catalogService.findAll(encryptKey));
        model.addAttribute("catalogs", catalogItems);
        return "catalog";
    }

    @GetMapping("create")
    public String catalogItemForm(Model model) {
        model.addAttribute("catalog", new Catalog());
        return "catalogForm";
    }

    @PostMapping("create")
    public String createCatalogItem(Catalog catalog, EncryptKey encryptKey) {
        encryptKey.setKey(catalog.getPassword());
        userService.save(catalog.getUser(), encryptKey);
        catalogService.save(catalog, encryptKey);
        return "redirect:/catalog";
    }

}
