package com.dbsys.rs.inventory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.inventory.repository.BarangRepository;
import com.dbsys.rs.inventory.service.BarangService;
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
	public List<BahanHabisPakai> getBhp() {
		return barangRepository.findAllBhp();
	}
}
