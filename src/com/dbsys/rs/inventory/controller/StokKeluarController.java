package com.dbsys.rs.inventory.controller;

import java.sql.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.inventory.service.StokService;
import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.ListEntityRestMessage;
import com.dbsys.rs.lib.RestMessage;
import com.dbsys.rs.lib.entity.StokKeluar;

@Controller
@RequestMapping("/stok/keluar")
public class StokKeluarController {

	@Autowired
	private StokService stokService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/barang/{barang}/jumlah/{jumlah}")
	@ResponseBody
	public RestMessage simpan(@PathVariable Long barang, @PathVariable Long jumlah) throws ApplicationException, PersistenceException {
		stokService.simpanStokKeluar(barang, jumlah);
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{awal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<StokKeluar> get(@PathVariable Date awal, @PathVariable Date akhir) throws ApplicationException, PersistenceException {
		List<StokKeluar> list = stokService.getStokKeluar(awal, akhir);
		return ListEntityRestMessage.createListStokKeluar(list);
	}
}
