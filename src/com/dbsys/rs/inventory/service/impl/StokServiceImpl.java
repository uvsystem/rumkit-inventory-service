package com.dbsys.rs.inventory.service.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.inventory.repository.BarangRepository;
import com.dbsys.rs.inventory.repository.StokRepository;
import com.dbsys.rs.inventory.service.StokService;
import com.dbsys.rs.lib.DateUtil;
import com.dbsys.rs.lib.NumberException;
import com.dbsys.rs.lib.entity.Barang;
import com.dbsys.rs.lib.entity.Stok;
import com.dbsys.rs.lib.entity.StokKeluar;
import com.dbsys.rs.lib.entity.StokMasuk;

@Service
@Transactional(readOnly = true)
public class StokServiceImpl implements StokService {

	@Autowired
	private BarangRepository barangRepository;
	@Autowired
	private StokRepository stokRepository;
	
	@Override
	@Transactional(readOnly = false)
	public void simpanStokMasuk(Long idBarang, Long jumlah, Date tanggal, Time jam) {
		Barang barang = barangRepository.findOne(idBarang);
		barang.tambah(jumlah);
		barangRepository.save(barang);
		
		if (tanggal == null)
			tanggal = DateUtil.getDate();
		if (jam == null)
			jam = DateUtil.getTime();
		
		Stok stok = new StokMasuk();
		stok.setBarang(barang);
		stok.setJumlah(jumlah);
		stok.setTanggal(tanggal);
		stok.setJam(jam);
		stokRepository.save(stok);
	}

	@Override
	@Transactional(readOnly = false)
	public void simpanStokKeluar(Long idBarang, Long jumlah, Date tanggal, Time jam) throws NumberException {
		Barang barang = barangRepository.findOne(idBarang);
		barang.kurang(jumlah);
		barangRepository.save(barang);
		
		if (tanggal == null)
			tanggal = DateUtil.getDate();
		if (jam == null)
			jam = DateUtil.getTime();
		
		Stok stok = new StokKeluar();
		stok.setBarang(barang);
		stok.setJumlah(jumlah);
		stok.setTanggal(tanggal);
		stok.setJam(jam);
		stokRepository.save(stok);
	}

	@Override
	public List<StokMasuk> getStokMasuk(Date awal, Date akhir) {
		return stokRepository.findAllStokMasuk(awal, akhir);
	}

	@Override
	public List<StokKeluar> getStokKeluar(Date awal, Date akhir) {
		return stokRepository.findAllStokKeluar(awal, akhir);
	}
}
