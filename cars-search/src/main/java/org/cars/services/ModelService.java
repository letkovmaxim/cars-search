package org.cars.services;

import org.cars.models.Mark;
import org.cars.models.Model;
import org.cars.repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelService implements ServiceInterface<Model> {
    @Autowired
    ModelRepository modelRepository;

    @Override
    public List<Model> getAll() {
        return new ArrayList<>(modelRepository.findAll());
    }

    @Override
    public Model getById(long id) {
        return modelRepository.findById(id).get();
    }

    @Override
    public List<Model> getByName(String name) {
        return new ArrayList<>(modelRepository.findByNameIgnoreCase(name));
    }

    @Override
    public void saveOrUpdate(Model model) {
        modelRepository.save(model);
    }
}
