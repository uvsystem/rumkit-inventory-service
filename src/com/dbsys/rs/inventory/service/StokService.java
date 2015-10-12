package com.dbsys.rs.inventory.service;

import java.sql.Date;
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
	 * @param stok
	 */
	void simpanStokMasuk(Long idBarang, Long jumlah);

	/**
	 * Simpan stok keluar.
	 * 
	 * @param stok
	 * @throws NumberException 
	 */
	void simpanStokKeluar(Long idBarang, Long jumlah) throws NumberException;

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
