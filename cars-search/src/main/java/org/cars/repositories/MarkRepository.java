package org.cars.repositories;

import org.cars.models.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findByNameIgnoreCase(String name);
}
