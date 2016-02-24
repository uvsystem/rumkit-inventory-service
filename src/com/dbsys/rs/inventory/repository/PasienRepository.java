package com.dbsys.rs.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbsys.rs.inventory.entity.Pasien;

public interface PasienRepository extends JpaRepository<Pasien, Long> {

}
