package com.dbsys.rs.inventory.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.inventory.repository.BarangRepository;
import com.dbsys.rs.inventory.repository.PasienRepository;
import com.dbsys.rs.inventory.repository.StokRepository;
import com.dbsys.rs.inventory.repository.UnitRepository;
import com.dbsys.rs.inventory.service.StokService;
import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.entity.Barang;
import com.dbsys.rs.lib.entity.Stok;
import com.dbsys.rs.lib.entity.StokEksternal;
import com.dbsys.rs.lib.entity.StokInternal;
import com.dbsys.rs.lib.entity.StokKembali;
import com.dbsys.rs.lib.entity.Stok.JenisStok;

@Service
@Transactional(readOnly = true)
public class StokServiceImpl implements StokService {

	@Autowired
	private BarangRepository barangRepository;
	@Autowired
	private StokRepository stokRepository;
	@Autowired
	private UnitRepository unitRepository;
	@Autowired
	private PasienRepository pasienRepository;

	@Override
	@Transactional(readOnly = false)
	public Stok simpan(Stok stok) throws ApplicationException {
		Barang barang = stok.getBarang();
		
		if ((stok instanceof StokEksternal && JenisStok.MASUK.equals(stok.getJenis())) || (stok instanceof StokKembali)) {
			barang.tambah(stok.getJumlah());
		} else if ((stok instanceof StokEksternal && JenisStok.KELUAR.equals(stok.getJenis())) || (stok instanceof StokInternal)) {
			barang.kurang(stok.getJumlah());
		} else {
			throw new ApplicationException("Silahkan pilih jenis stok");
		}

		barangRepository.save(barang);

		return stokRepository.save(stok);
	}

	@Override
	public List<Stok> getStokMasuk(Date awal, Date akhir) {
		List<StokEksternal> list = stokRepository.findAllStokEksternal(awal, akhir, JenisStok.MASUK);
		List<Stok> listStok = new ArrayList<>();
		for (StokEksternal stok : list)
			listStok.add(stok);
		
		return listStok;
	}

	@Override
	public List<Stok> getStokKeluar(Date awal, Date akhir) {
		List<StokEksternal> list = stokRepository.findAllStokEksternal(awal, akhir, JenisStok.KELUAR);
		List<Stok> listStok = new ArrayList<>();
		for (StokEksternal stok : list)
			listStok.add(stok);
		
		return listStok;
	}

	@Override
	public List<Stok> getStokInternal(Date awal, Date akhir) {
		List<StokInternal> list = stokRepository.findAllStokInternal(awal, akhir);
		List<Stok> listStok = new ArrayList<>();
		for (StokInternal stok : list)
			listStok.add(stok);
		
		return listStok;
	}

	@Override
	public List<Stok> getStokInternal(Date awal, Date akhir, Long idUnit) {
		List<StokInternal> list = stokRepository.findAllStokInternal(awal, akhir, idUnit);
		List<Stok> listStok = new ArrayList<>();
		for (StokInternal stok : list)
			listStok.add(stok);
		
		return listStok;
	}

	@Override
	public List<Stok> getStokKembali(Date awal, Date akhir) {
		List<StokKembali> list = stokRepository.findAllStokKembali(awal, akhir);
		List<Stok> listStok = new ArrayList<>();
		for (StokKembali stok : list)
			listStok.add(stok);
		
		return listStok;
	}

	@Override
	public List<Stok> getStokKembali(Long idPasien) {
		List<StokKembali> list = stokRepository.findAllStokKembali(idPasien);
		List<Stok> listStok = new ArrayList<>();
		for (StokKembali stok : list)
			listStok.add(stok);
		
		return listStok;
	}

	@Override
	public List<Stok> getStokKembali(String nomor) {
		List<StokKembali> list = stokRepository.findAllStokKembali(nomor);
		List<Stok> listStok = new ArrayList<>();
		for (StokKembali stok : list)
			listStok.add(stok);
		
		return listStok;
	}
}
