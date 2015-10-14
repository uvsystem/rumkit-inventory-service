package com.dbsys.rs.inventory.controller;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.inventory.service.BarangService;
import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.EntityRestMessage;
import com.dbsys.rs.lib.ListEntityRestMessage;
import com.dbsys.rs.lib.RestMessage;
import com.dbsys.rs.lib.entity.Barang;
import com.dbsys.rs.lib.entity.ObatFarmasi;

@Controller
@RequestMapping("/obat")
public class ObatController {
	
	@Autowired
	private BarangService barangService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EntityRestMessage<Barang> save(@RequestBody ObatFarmasi obatFarmasi) throws ApplicationException, PersistenceException {
		obatFarmasi = (ObatFarmasi)barangService.save(obatFarmasi);
		return EntityRestMessage.createBarang(obatFarmasi);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ListEntityRestMessage<ObatFarmasi> getAll() throws ApplicationException, PersistenceException {
		List<ObatFarmasi> list = barangService.getObat();
		return ListEntityRestMessage.createListObat(list);
	}

	@RequestMapping(method = RequestMethod.GET, value="/keyword/{keyword}")
	@ResponseBody
	public ListEntityRestMessage<ObatFarmasi> get(@PathVariable String keyword) throws ApplicationException, PersistenceException {
		List<ObatFarmasi> list = barangService.getObat(keyword);
		return ListEntityRestMessage.createListObat(list);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/jumlah/{jumlah}")
	@ResponseBody
	public RestMessage kurang(@PathVariable Long id, @PathVariable Long jumlah) throws ApplicationException, PersistenceException {
		barangService.kurang(id, jumlah);
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/test/test")
	@ResponseBody
	public RestMessage test() throws ApplicationException, PersistenceException {
		return RestMessage.success();
	}
}
