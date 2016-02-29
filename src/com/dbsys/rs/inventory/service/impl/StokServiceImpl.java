package com.dbsys.rs.inventory.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.inventory.repository.BarangRepository;
import com.dbsys.rs.inventory.repository.PasienRepository;
import com.dbsys.rs.inventory.repository.StokRepository;
import com.dbsys.rs.inventory.service.StokService;
import com.dbsys.rs.ApplicationException;
import com.dbsys.rs.DateUtil;
import com.dbsys.rs.inventory.entity.Barang;
import com.dbsys.rs.inventory.entity.Stok;
import com.dbsys.rs.inventory.entity.StokKembali;

@Service
@Transactional(readOnly = true)
public class StokServiceImpl implements StokService {

	@Autowired
	private BarangRepository barangRepository;
	@Autowired
	private StokRepository stokRepository;
	@Autowired
	private PasienRepository pasienRepository;

	@Override
	@Transactional(readOnly = false)
	public Stok simpan(Stok stok) throws ApplicationException {
		if (stok.getTanggal() == null)
			stok.setTanggal(DateUtil.getDate());
		if (stok.getJam() == null)
			stok.setJam(DateUtil.getTime());
		
		Barang barang = stok.getBarang();
		if (Stok.JenisStok.MASUK.equals(stok.getJenis())) {
			barang.tambah(stok.getJumlah());
		} else {
			barang.kurang(stok.getJumlah());
		}
		
		barangRepository.save(barang);
		return stokRepository.save(stok);
	}

	@Override
	public List<StokKembali> getStokKembali(Date awal, Date akhir) {
		List<StokKembali> list = stokRepository.findAllStokKembali(awal, akhir);
		return list;
	}

	@Override
	public List<StokKembali> getStokKembali(Long idPasien) {
		List<StokKembali> list = stokRepository.findAllStokKembali(idPasien);
		return list;
	}

	@Override
	public List<StokKembali> getStokKembali(String nomor) {
		List<StokKembali> list = stokRepository.findAllStokKembali(nomor);
		return list;
	}

	@Override
	public List<Stok> getStokMasuk(Date awal, Date akhir) {
		List<Stok> list = stokRepository.findAllStokMasuk(awal, akhir);
		return list;
	}
}
