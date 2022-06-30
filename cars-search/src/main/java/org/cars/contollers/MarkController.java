package org.cars.contollers;

import org.cars.models.Mark;
import org.cars.models.Model;
import org.cars.models.Modification;
import org.cars.services.MarkService;
import org.cars.services.ModelService;
import org.cars.services.ModificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class MarkController {
    @Autowired
    MarkService markService;
    @Autowired
    ModelService modelService;
    @Autowired
    ModificationService modificationService;

    @GetMapping("/mark")
    public List<Mark> getAllMarks() {
        return markService.getAll();
    }

    @GetMapping("/mark/{id}")
    public Mark getMarkById(@PathVariable long id) {
        if (markService.getById(id) != null) {
            return markService.getById(id);
        }
        throw new NoSuchElementException();
    }

    @GetMapping("mark/{id}/model")
    public List<Model> getAllModels(@PathVariable long id) {
        if (markService.getById(id) != null) {
            return markService.getById(id).getModels();
        }
        return new ArrayList<>();
    }

    @GetMapping("mark/{markId}/model/{modelId}")
    public Model getModelById(@PathVariable long markId, @PathVariable long modelId) {
        if (markService.getById(markId) != null) {
            for (Model model : markService.getById(markId).getModels()) {
                if (model.getUid() == modelId) {
                    return model;
                }
            }
        }
        throw new NoSuchElementException();
    }

    @GetMapping("mark/{markId}/model/{modelId}/modification")
    public List<Modification> getAllModifications(@PathVariable long markId, @PathVariable long modelId) {
        if (markService.getById(markId) != null) {
            for (Model model : markService.getById(markId).getModels()) {
                if (model.getUid() == modelId) {
                    return model.getModifications();
                }
            }
        }
        throw new NoSuchElementException();
    }

    @GetMapping("mark/{markId}/model/{modelId}/modification/{modificationId}")
    public Modification getModificationById(@PathVariable long markId,
                                                     @PathVariable long modelId,
                                                     @PathVariable long modificationId) {
        if (markService.getById(modelId) != null) {
            for (Model model : markService.getById(markId).getModels()) {
                if (model.getUid() == modelId) {
                    for (Modification modification : model.getModifications()) {
                        if (modification.getUid() == modificationId) {
                            return modification;
                        }
                    }
                }
            }
        }
        throw new NoSuchElementException();
    }


    @PostMapping("/mark")
    public Mark saveMark(@RequestBody Mark mark) {
        markService.saveOrUpdate(mark);
        return mark;
    }

    @PostMapping("/mark/{id}/model")
    public Model saveModel(@PathVariable long id, @RequestBody Model model) {
        if (markService.getById(id) != null) {
            markService.getById(id).getModels().add(model);
            model.setMark(markService.getById(id));
            modelService.saveOrUpdate(model);
            return model;
        }
        throw new NoSuchElementException();
    }

    @PostMapping("/mark/{markId}/model/{modelId}/modification")
    public Modification saveModification(@PathVariable long markId,
                                  @PathVariable long modelId,
                                  @RequestBody Modification modification) {
        if (markService.getById(markId) != null && modelService.getById(modelId) != null) {
            modelService.getById(modelId).getModifications().add(modification);
            modification.setModel(modelService.getById(modelId));
            modificationService.saveOrUpdate(modification);
            return modification;
        }
        throw new NoSuchElementException();
    }
}