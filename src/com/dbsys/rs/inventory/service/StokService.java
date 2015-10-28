package com.dbsys.rs.inventory.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.dbsys.rs.lib.NumberException;
import com.dbsys.rs.lib.entity.StokKeluar;
import com.dbsys.rs.lib.entity.StokMasuk;

/**
 * Interface untuk mengelola data stok.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface StokService {

	/**
	 * Simpan stok masuk.
	 * 
	 * @param idBarang
	 * @param jumlah
	 * @param tanggal
	 * @param jam
	 */
	void simpanStokMasuk(Long idBarang, Long jumlah, Date tanggal, Time jam);

	/**
	 * Simpan stok keluar.
	 * 
	 * @param idBarang
	 * @param jumlah
	 * @param tanggal
	 * @param jam
	 * 
	 * @throws NumberException jumlah barang tidak mencukui untuk dikurangi
	 */
	void simpanStokKeluar(Long idBarang, Long jumlah, Date tanggal, Time jam) throws NumberException;

	/**
	 * Mengambil semua stok masuk sesuai tanggal
	 * 
	 * @param awal
	 * @param akhir
	 * 
	 * @return daftar stok masuk
	 */
	List<StokMasuk> getStokMasuk(Date awal, Date akhir);

	/**
	 * Mengambil semua stok keluar sesuai tanggal
	 * 
	 * @param awal
	 * @param akhir
	 * 
	 * @return daftar stok keluar
	 */
	List<StokKeluar> getStokKeluar(Date awal, Date akhir);

}
