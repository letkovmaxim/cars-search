package org.cars.contollers;

import org.cars.models.Modification;
import org.cars.services.ModificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ModificationController {
    @Autowired
    ModificationService modificationService;

    @GetMapping("/modification")
    public List<Modification> getAllModifications() {
        return modificationService.getAll();
    }

    @GetMapping("/modification/{id}")
    public Modification getModificationById(@PathVariable long id) {
        if (modificationService.getById(id) != null) {
            return modificationService.getById(id);
        }
        throw new NoSuchElementException();
    }
}