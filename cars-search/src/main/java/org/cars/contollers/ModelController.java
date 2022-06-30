package org.cars.contollers;

import org.cars.models.Model;
import org.cars.models.Modification;
import org.cars.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ModelController {
    @Autowired
    ModelService modelService;

    @GetMapping("/model")
    public List<Model> getAllModels() {
        return modelService.getAll();
    }

    @GetMapping("/model/{id}")
    public Model getModelById(@PathVariable long id) {
        if (modelService.getById(id) != null) {
            return modelService.getById(id);
        }
        throw new NoSuchElementException();
    }

    @GetMapping("model/{id}/modification")
    public List<Modification> getAllModifications(@PathVariable long id) {
        if (modelService.getById(id) != null) {
            return modelService.getById(id).getModifications();
        }
        throw new NoSuchElementException();
    }

    @GetMapping("model/{modelId}/modification/{modificationId}")
    public Modification getModificationById(@PathVariable long modelId, @PathVariable long modificationId) {
        for (Modification modification : modelService.getById(modelId).getModifications()) {
            if (modification.getUid() == modificationId) {
                return modification;
            }
        }
        throw new NoSuchElementException();
    }
}