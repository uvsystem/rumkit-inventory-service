package com.dbsys.rs.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbsys.rs.lib.entity.BahanHabisPakai;
import com.dbsys.rs.lib.entity.Barang;
import com.dbsys.rs.lib.entity.ObatFarmasi;

public interface BarangRepository extends JpaRepository<Barang, Long> {

	/**
	 * Mengambil semua obat.
	 * 
	 * @return daftar obat
	 */
	@Query("FROM ObatFarmasi obat")
	List<ObatFarmasi> findAllObat();

	/**
	 * Mengambil semua Bahan Habis Pakai
	 * 
	 * @return daftar BHP
	 */
	@Query("FROM BahanHabisPakai bhp")
	List<BahanHabisPakai> findAllBhp();

}
