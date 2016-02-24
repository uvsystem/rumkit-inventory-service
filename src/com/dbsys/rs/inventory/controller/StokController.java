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
import com.dbsys.rs.ApplicationException;
import com.dbsys.rs.EntityRestMessage;
import com.dbsys.rs.ListEntityRestMessage;
import com.dbsys.rs.inventory.entity.StokKembali;

@Controller
@RequestMapping("/stok")
public class StokController {

	@Autowired
	private StokService stokService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EntityRestMessage<StokKembali> simpan(@RequestBody StokKembali stok) throws ApplicationException, PersistenceException {
		stok = stokService.simpan(stok);
		return new EntityRestMessage<StokKembali>(stok);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pasien/{pasien}")
	@ResponseBody
	public ListEntityRestMessage<StokKembali> getStokKembali(@PathVariable Long pasien) throws ApplicationException, PersistenceException {
		List<StokKembali> list = stokService.getStokKembali(pasien);
		return new ListEntityRestMessage<StokKembali>(list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/nomor/{nomor}")
	@ResponseBody
	public ListEntityRestMessage<StokKembali> getStokKembali(@PathVariable String nomor) throws ApplicationException, PersistenceException {
		List<StokKembali> list = stokService.getStokKembali(nomor);
		return new ListEntityRestMessage<StokKembali>(list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{awal}/to/{akhir}/pasien")
	@ResponseBody
	public ListEntityRestMessage<StokKembali> getStokKembali(@PathVariable Date awal, @PathVariable Date akhir) throws ApplicationException, PersistenceException {
		List<StokKembali> list = stokService.getStokKembali(awal, akhir);
		return new ListEntityRestMessage<StokKembali>(list);
	}

}
