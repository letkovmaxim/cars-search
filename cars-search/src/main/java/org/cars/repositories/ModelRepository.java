package org.cars.repositories;

import org.cars.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {
    Model findByName(String name);
    List<Model> findByNameIgnoreCase(String name);
}
