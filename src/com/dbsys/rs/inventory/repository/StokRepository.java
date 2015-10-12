package com.dbsys.rs.inventory.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.lib.entity.Stok;
import com.dbsys.rs.lib.entity.StokKeluar;
import com.dbsys.rs.lib.entity.StokMasuk;

public interface StokRepository extends JpaRepository<Stok, Long> {

	@Query("FROM StokMasuk stok WHERE stok.tanggal BETWEEN :awal AND :akhir")
	List<StokMasuk> findAllStokMasuk(@Param("awal") Date awal, @Param("akhir") Date akhir);

	@Query("FROM StokKeluar stok WHERE stok.tanggal BETWEEN :awal AND :akhir")
	List<StokKeluar> findAllStokKeluar(@Param("awal") Date awal, @Param("akhir") Date akhir);

}
