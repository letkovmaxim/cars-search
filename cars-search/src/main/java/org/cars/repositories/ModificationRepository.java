package org.cars.repositories;

import org.cars.models.Model;
import org.cars.models.Modification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModificationRepository extends JpaRepository<Modification, Long> {
    List<Modification> findByNameIgnoreCase(String name);

    List<Modification> findByNameIgnoreCaseAndPeriodBeginAndPeriodEnd(String name, int periodBegin, int periodEnd);

    List<Modification> findByNameIgnoreCaseAndPeriodBegin(String name, int periodBegin);

    List<Modification> findByNameIgnoreCaseAndPeriodEnd(String name, int periodEnd);

    List<Modification> findByPeriodBeginAndPeriodEnd(int periodBegin, int periodEnd);

    List<Modification> findByPeriodBegin(int periodBegin);

    List<Modification> findByPeriodEnd(int periodEnd);
}
