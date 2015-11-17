package com.dbsys.rs.inventory.service;

import java.sql.Date;
import java.util.List;

import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.entity.Stok;

/**
 * Interface untuk mengelola data stok.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface StokService {

	/**
	 * Simpan stok.
	 * 
	 * @param stok
	 * @return stok
	 * @throws ApplicationException 
	 */
	Stok simpan(Stok stok) throws ApplicationException;

	/**
	 * Mengambil semua stok masuk sesuai tanggal.
	 * 
	 * @param awal
	 * @param akhir
	 * 
	 * @return daftar stok
	 */
	List<Stok> getStokMasuk(Date awal, Date akhir);

	/**
	 * Mengambil semua stok masuk sesuai tanggal.
	 * 
	 * @param awal
	 * @param akhir
	 * 
	 * @return daftar stok
	 */
	List<Stok> getStokKeluar(Date awal, Date akhir);

	/**
	 * Mengambil semua stok yang keluar ke unit.
	 * 
	 * @param awal
	 * @param akhir
	 * 
	 * @return daftar stok
	 */
	List<Stok> getStokInternal(Date awal, Date akhir);

	/**
	 * Mengambil semua stok yang keluar ke unit.
	 * 
	 * @param awal
	 * @param akhir
	 * @param idUnit
	 * 
	 * @return daftar stok
	 */
	List<Stok> getStokInternal(Date awal, Date akhir, Long idUnit);

	/**
	 * Mengambil semua stok yang kembali dari pasien.
	 * @param awal
	 * @param akhir
	 * @return daftar stok
	 */
	List<Stok> getStokKembali(Date awal, Date akhir);

	/**
	 * Mengambil semua stok yang kembali dari pasien.
	 * 
	 * @param pasien
	 * @return daftar stok
	 */
	List<Stok> getStokKembali(Long pasien);

	/**
	 * Mengambil semua stok yang kembali dari berdasarkan nomor.
	 * 
	 * @param pasien
	 * @return daftar stok
	 */
	List<Stok> getStokKembali(String nomor);
}
