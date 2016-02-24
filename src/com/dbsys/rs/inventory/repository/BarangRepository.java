package com.dbsys.rs.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.inventory.entity.BahanHabisPakai;
import com.dbsys.rs.inventory.entity.Barang;
import com.dbsys.rs.inventory.entity.ObatFarmasi;

public interface BarangRepository extends JpaRepository<Barang, Long> {

	@Query("FROM ObatFarmasi obat")
	List<ObatFarmasi> findAllObat();

	@Query("FROM ObatFarmasi obat WHERE obat.nama LIKE %:keyword% OR obat.kode LIKE %:keyword%")
	List<ObatFarmasi> findAllObat(@Param("keyword") String keyword);

	@Query("FROM BahanHabisPakai bhp")
	List<BahanHabisPakai> findAllBhp();

	@Query("FROM BahanHabisPakai bhp WHERE bhp.nama LIKE %:keyword% OR bhp.kode LIKE %:keyword%")
	List<BahanHabisPakai> findAllBhp(@Param("keyword") String keyword);

	List<Barang> findByNamaContainingOrKodeContaining(String nama, String kode);

}
