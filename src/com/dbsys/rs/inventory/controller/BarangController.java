package com.dbsys.rs.inventory.controller;

import java.util.ArrayList;
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
import com.dbsys.rs.lib.entity.BahanHabisPakai;
import com.dbsys.rs.lib.entity.Barang;
import com.dbsys.rs.lib.entity.ObatFarmasi;

@Controller
@RequestMapping("/barang")
public class BarangController {
	
	@Autowired
	private BarangService barangService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EntityRestMessage<Barang> save(@RequestBody Barang barang) throws ApplicationException, PersistenceException {
		barang = barangService.save(barang);
		return EntityRestMessage.createBarang(barang);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ListEntityRestMessage<Barang> getAll() throws ApplicationException, PersistenceException {
		List<Barang> list = barangService.getBarang();
		return ListEntityRestMessage.createListBarang(list);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/class/{class}")
	@ResponseBody
	public ListEntityRestMessage<Barang> getAll(@PathVariable("class") String cls) throws ApplicationException, PersistenceException {
		List<?> list = null;
		
		if (ObatFarmasi.class.getSimpleName().equals(cls)) {
			list = barangService.getObat();
		} else if(BahanHabisPakai.class.getSimpleName().equals(cls)) {
			list = barangService.getBhp();
		} else {
			throw new ApplicationException("Class tidak terdaftar");
		}
		
		List<Barang> listBarang = new ArrayList<>();
		for (Object object : list)
			listBarang.add((Barang) object);
		
		return ListEntityRestMessage.createListBarang(listBarang);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/keyword/{keyword}")
	@ResponseBody
	public ListEntityRestMessage<Barang> get(@PathVariable String keyword) throws ApplicationException, PersistenceException {
		List<Barang> list = barangService.getBarang(keyword);
		return ListEntityRestMessage.createListBarang(list);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/keyword/{keyword}/class/{class}")
	@ResponseBody
	public ListEntityRestMessage<Barang> get(@PathVariable String keyword, @PathVariable("class") String cls) throws ApplicationException, PersistenceException {
		List<?> list = null;
		
		if (ObatFarmasi.class.getSimpleName().equals(cls)) {
			list = barangService.getObat(keyword);
		} else if(BahanHabisPakai.class.getSimpleName().equals(cls)) {
			list = barangService.getBhp(keyword);
		} else {
			throw new ApplicationException("Class tidak terdaftar");
		}
		
		List<Barang> listBarang = new ArrayList<>();
		for (Object object : list)
			listBarang.add((Barang) object);
		
		return ListEntityRestMessage.createListBarang(listBarang);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/kurang/{id}/jumlah/{jumlah}")
	@ResponseBody
	public RestMessage kurang(@PathVariable Long id, @PathVariable Long jumlah) throws ApplicationException, PersistenceException {
		barangService.kurang(id, jumlah);
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/tambah/{id}/jumlah/{jumlah}")
	@ResponseBody
	public RestMessage tambah(@PathVariable Long id, @PathVariable Long jumlah) throws ApplicationException, PersistenceException {
		barangService.tambah(id, jumlah);
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage hapus(@PathVariable Long id) throws ApplicationException, PersistenceException {
		barangService.hapus(id);
		return RestMessage.success();
	}
}
