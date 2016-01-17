package com.dbsys.rs.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbsys.rs.lib.entity.Pasien;

public interface PasienRepository extends JpaRepository<Pasien, Long> {

}
