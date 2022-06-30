package org.cars.services;

import org.cars.models.Mark;
import org.cars.repositories.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarkService implements ServiceInterface<Mark> {
    @Autowired
    MarkRepository markRepository;

    @Override
    public List<Mark> getAll() {
        return new ArrayList<>(markRepository.findAll());
    }

    @Override
    public Mark getById(long id) {
        return markRepository.findById(id).get();
    }

    @Override
    public List<Mark> getByName(String name) {
        return new ArrayList<>(markRepository.findByNameIgnoreCase(name));
    }

    @Override
    public void saveOrUpdate(Mark mark) {
        markRepository.save(mark);
    }
}
