package com.dbsys.rs.inventory.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.inventory.entity.Stok;
import com.dbsys.rs.inventory.entity.StokKembali;

public interface StokRepository extends JpaRepository<Stok, Long> {

	@Query("FROM StokKembali stok WHERE stok.tanggal BETWEEN :awal AND :akhir")
	List<StokKembali> findAllStokKembali(@Param("awal") Date awal, @Param("akhir") Date akhir);

	@Query("FROM StokKembali stok WHERE stok.pasien.id = :pasien")
	List<StokKembali> findAllStokKembali(@Param("pasien") Long idPasien);

	@Query("FROM StokKembali stok WHERE stok.nomor = :nomor")
	List<StokKembali> findAllStokKembali(@Param("nomor") String nomor);

	@Query("FROM Stok stok WHERE stok.tanggal BETWEEN :awal AND :akhir")
	List<Stok> findAllStokMasuk(Date awal, Date akhir);

}
