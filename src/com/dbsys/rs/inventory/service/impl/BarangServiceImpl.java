package com.dbsys.rs.inventory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.inventory.repository.BarangRepository;
import com.dbsys.rs.inventory.service.BarangService;
import com.dbsys.rs.lib.NumberException;
import com.dbsys.rs.lib.entity.BahanHabisPakai;
import com.dbsys.rs.lib.entity.Barang;
import com.dbsys.rs.lib.entity.ObatFarmasi;

@Service
@Transactional(readOnly = true)
public class BarangServiceImpl implements BarangService {

	@Autowired
	private BarangRepository barangRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Barang save(Barang barang) {
		return barangRepository.save(barang);
	}

	@Override
	public List<ObatFarmasi> getObat() {
		return barangRepository.findAllObat();
	}

	@Override
	public List<ObatFarmasi> getObat(String keyword) {
		return barangRepository.findAllObat(keyword);
	}

	@Override
	public List<BahanHabisPakai> getBhp() {
		return barangRepository.findAllBhp();
	}

	@Override
	public List<BahanHabisPakai> getBhp(String keyword) {
		return barangRepository.findAllBhp(keyword);
	}

	@Override
	public void kurang(Long id, Long jumlah) throws NumberException {
		Barang barang = barangRepository.findOne(id);
		barang.kurang(jumlah);
		
		barangRepository.save(barang);
	}

	@Override
	public void tambah(Long id, Long jumlah) {
		Barang barang = barangRepository.findOne(id);
		barang.tambah(jumlah);
		
		barangRepository.save(barang);
	}

	@Override
	public List<Barang> getBarang() {
		return barangRepository.findAll();
	}

	@Override
	public List<Barang> getBarang(String keyword) {
		return barangRepository.findByNamaContainingOrKodeContaining(keyword, keyword);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Long id) {
		barangRepository.delete(id);
	}
}
