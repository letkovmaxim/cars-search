package org.cars.repositories;

import org.cars.models.Modification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModificationRepository extends JpaRepository<Modification, Long> {
    List<Modification> findByNameIgnoreCase(String name);


    List<Modification> findByNameIgnoreCaseAndPeriodBeginLessThanEqualAndPeriodEndGreaterThanEqual(String name, int periodBegin, int periodEnd);

    List<Modification> findByNameIgnoreCaseAndPeriodEndGreaterThanEqual(String name, int periodBegin);

    List<Modification> findByNameIgnoreCaseAndPeriodBeginLessThanEqual(String name, int periodEnd);

    List<Modification> findByPeriodBeginLessThanEqualAndPeriodEndGreaterThanEqual(int periodBegin, int periodEnd);

    List<Modification> findByPeriodEndGreaterThanEqual(int periodBegin);

    List<Modification> findByPeriodBeginLessThanEqual(int periodEnd);
}
