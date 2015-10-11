package com.dbsys.rs.inventory.controller;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.inventory.service.BarangService;
import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.EntityRestMessage;
import com.dbsys.rs.lib.ListEntityRestMessage;
import com.dbsys.rs.lib.entity.BahanHabisPakai;
import com.dbsys.rs.lib.entity.Barang;

@Controller
@RequestMapping("/bhp")
public class BhpController {
	
	@Autowired
	private BarangService barangService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EntityRestMessage<Barang> save(@RequestBody BahanHabisPakai bhp) throws ApplicationException, PersistenceException {
		bhp = (BahanHabisPakai)barangService.save(bhp);
		return EntityRestMessage.createBarang(bhp);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ListEntityRestMessage<BahanHabisPakai> getAll() throws ApplicationException, PersistenceException {
		List<BahanHabisPakai> list = barangService.getBhp();
		return ListEntityRestMessage.createListBhp(list);
	}
}
