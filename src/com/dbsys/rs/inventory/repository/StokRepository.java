package com.dbsys.rs.inventory.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.lib.entity.Stok;
import com.dbsys.rs.lib.entity.StokEksternal;
import com.dbsys.rs.lib.entity.Stok.JenisStok;
import com.dbsys.rs.lib.entity.StokInternal;
import com.dbsys.rs.lib.entity.StokKembali;

public interface StokRepository extends JpaRepository<Stok, Long> {

	@Query("FROM StokEksternal stok WHERE stok.tanggal BETWEEN :awal AND :akhir AND stok.jenis = :jenis")
	List<StokEksternal> findAllStokEksternal(@Param("awal") Date awal, @Param("akhir") Date akhir, @Param("jenis") JenisStok jenis);

	@Query("FROM StokInternal stok WHERE stok.tanggal BETWEEN :awal AND :akhir")
	List<StokInternal> findAllStokInternal(@Param("awal") Date awal, @Param("akhir") Date akhir);

	@Query("FROM StokInternal stok WHERE stok.tanggal BETWEEN :awal AND :akhir AND stok.unit.id = :unit")
	List<StokInternal> findAllStokInternal(@Param("awal") Date awal, @Param("akhir") Date akhir, @Param("unit") Long unit);

	@Query("FROM StokKembali stok WHERE stok.tanggal BETWEEN :awal AND :akhir")
	List<StokKembali> findAllStokKembali(@Param("awal") Date awal, @Param("akhir") Date akhir);

	@Query("FROM StokKembali stok WHERE stok.pasien.id = :pasien")
	List<StokKembali> findAllStokKembali(@Param("pasien") Long idPasien);

	@Query("FROM StokKembali stok WHERE stok.nomor = :nomor")
	List<StokKembali> findAllStokKembali(@Param("nomor") String nomor);

}
