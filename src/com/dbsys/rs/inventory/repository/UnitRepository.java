package com.dbsys.rs.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbsys.rs.lib.entity.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {

}
