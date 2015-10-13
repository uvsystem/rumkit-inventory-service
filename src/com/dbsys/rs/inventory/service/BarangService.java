package com.dbsys.rs.inventory.service;

import java.util.List;

import com.dbsys.rs.lib.NumberException;
import com.dbsys.rs.lib.entity.BahanHabisPakai;
import com.dbsys.rs.lib.entity.Barang;
import com.dbsys.rs.lib.entity.ObatFarmasi;

/**
 * Interface untuk mengelola data barang.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface BarangService {

	/**
	 * Menyimpan barang.
	 * 
	 * @param barangEntity
	 * 
	 * @return barang yang sudah tersimpan
	 */
	Barang save(Barang barang);

	/**
	 * Mengambil semua obat.
	 * 
	 * @return daftar obat
	 */
	List<ObatFarmasi> getObat();

	/**
	 * Mengambil semua Bahan Habis Pakai (BHP).
	 * 
	 * @return daftar bhp
	 */
	List<BahanHabisPakai> getBhp();

	/**
	 * Kurangi jumlah barang.
	 * 
	 * @param id
	 * @param jumlah
	 * @throws NumberException jumlah barang tidak cukup untuk dikurangi.
	 */
	void kurang(Long id, Long jumlah) throws NumberException;

	/**
	 * Tambah jumlah barang.
	 * 
	 * @param id
	 * @param jumlah
	 */
	void tambah(Long id, Long jumlah);

}
