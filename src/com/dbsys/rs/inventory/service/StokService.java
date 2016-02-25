package com.dbsys.rs.inventory.service;

import java.sql.Date;
import java.util.List;

import com.dbsys.rs.ApplicationException;
import com.dbsys.rs.inventory.entity.Stok;
import com.dbsys.rs.inventory.entity.StokKembali;

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
	 * Mengambil semua stok yang kembali dari pasien.
	 * @param awal
	 * @param akhir
	 * @return daftar stok
	 */
	List<StokKembali> getStokKembali(Date awal, Date akhir);

	/**
	 * Mengambil semua stok yang kembali dari pasien.
	 * 
	 * @param pasien
	 * @return daftar stok
	 */
	List<StokKembali> getStokKembali(Long pasien);

	/**
	 * Mengambil semua stok yang kembali dari berdasarkan nomor.
	 * 
	 * @param pasien
	 * @return daftar stok
	 */
	List<StokKembali> getStokKembali(String nomor);

	List<Stok> getStokMasuk(Date awal, Date akhir);

}
