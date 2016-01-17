package com.dbsys.rs.inventory.controller;

import java.sql.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.inventory.service.StokService;
import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.EntityRestMessage;
import com.dbsys.rs.lib.ListEntityRestMessage;
import com.dbsys.rs.lib.entity.Stok;

@Controller
@RequestMapping("/stok")
public class StokController {

	@Autowired
	private StokService stokService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EntityRestMessage<Stok> simpan(@RequestBody Stok stok) throws ApplicationException, PersistenceException {
		stok = stokService.simpan(stok);
		return EntityRestMessage.createStok(stok);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/masuk/{awal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<Stok> getStokMasuk(@PathVariable Date awal, @PathVariable Date akhir) throws ApplicationException, PersistenceException {
		List<Stok> list = stokService.getStokMasuk(awal, akhir);
		return ListEntityRestMessage.createListStok(list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/keluar/{awal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<Stok> getStokKeluar(@PathVariable Date awal, @PathVariable Date akhir) throws ApplicationException, PersistenceException {
		List<Stok> list = stokService.getStokKeluar(awal, akhir);
		return ListEntityRestMessage.createListStok(list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pasien/{pasien}")
	@ResponseBody
	public ListEntityRestMessage<Stok> getStokKembali(@PathVariable Long pasien) throws ApplicationException, PersistenceException {
		List<Stok> list = stokService.getStokKembali(pasien);
		return ListEntityRestMessage.createListStok(list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/nomor/{nomor}")
	@ResponseBody
	public ListEntityRestMessage<Stok> getStokKembali(@PathVariable String nomor) throws ApplicationException, PersistenceException {
		List<Stok> list = stokService.getStokKembali(nomor);
		return ListEntityRestMessage.createListStok(list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{awal}/to/{akhir}/pasien")
	@ResponseBody
	public ListEntityRestMessage<Stok> getStokKembali(@PathVariable Date awal, @PathVariable Date akhir) throws ApplicationException, PersistenceException {
		List<Stok> list = stokService.getStokKembali(awal, akhir);
		return ListEntityRestMessage.createListStok(list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{awal}/to/{akhir}/unit")
	@ResponseBody
	public ListEntityRestMessage<Stok> getStokInternal(@PathVariable Date awal, @PathVariable Date akhir) throws ApplicationException, PersistenceException {
		List<Stok> list = stokService.getStokInternal(awal, akhir);
		return ListEntityRestMessage.createListStok(list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{awal}/to/{akhir}/unit/{unit}")
	@ResponseBody
	public ListEntityRestMessage<Stok> getStokInternal(@PathVariable Date awal, @PathVariable Date akhir, @PathVariable Long unit) throws ApplicationException, PersistenceException {
		List<Stok> list = stokService.getStokInternal(awal, akhir, unit);
		return ListEntityRestMessage.createListStok(list);
	}
}
