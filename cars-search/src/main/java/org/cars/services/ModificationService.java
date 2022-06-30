package org.cars.services;

import org.cars.models.Modification;
import org.cars.repositories.ModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModificationService implements ServiceInterface<Modification> {
    @Autowired
    ModificationRepository modificationRepository;

    @Override
    public List<Modification> getAll() {
        return new ArrayList<>(modificationRepository.findAll());
    }

    @Override
    public Modification getById(long id) {
        return modificationRepository.findById(id).get();
    }

    @Override
    public List<Modification> getByName(String name) {
        return new ArrayList<>(modificationRepository.findByNameIgnoreCase(name));
    }

    public List<Modification> getByNameAndPeriodBeginAndPeriodEnd(String name, int periodBegin, int periodEnd) {
        return new ArrayList<>(modificationRepository.findByNameIgnoreCaseAndPeriodBeginAndPeriodEnd(name, periodBegin, periodEnd));
    }

    public List<Modification> getByNameAndPeriodBegin(String name, int periodBegin) {
        return new ArrayList<>(modificationRepository.findByNameIgnoreCaseAndPeriodBegin(name, periodBegin));
    }

    public List<Modification> getByNameAndPeriodEnd(String name, int periodEnd) {
        return new ArrayList<>(modificationRepository.findByNameIgnoreCaseAndPeriodEnd(name, periodEnd));
    }

    public List<Modification> getByPeriodBeginAndPeriodEnd(int periodBegin, int periodEnd) {
        return new ArrayList<>(modificationRepository.findByPeriodBeginAndPeriodEnd(periodBegin, periodEnd));
    }

    public List<Modification> getByPeriodBegin(int periodBegin) {
        return new ArrayList<>(modificationRepository.findByPeriodBegin(periodBegin));
    }

    public List<Modification> getByPeriodEnd(int periodEnd) {
        return new ArrayList<>(modificationRepository.findByPeriodEnd(periodEnd));
    }

    @Override
    public void saveOrUpdate(Modification modification) {
        modificationRepository.save(modification);
    }
}