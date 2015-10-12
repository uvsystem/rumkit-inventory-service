package com.dbsys.rs.inventory.controller;

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
import com.dbsys.rs.lib.DateUtil;
import com.dbsys.rs.lib.ListEntityRestMessage;
import com.dbsys.rs.lib.RestMessage;
import com.dbsys.rs.lib.entity.StokMasuk;

@Controller
@RequestMapping("/stok/masuk")
public class StokMasukController {

	@Autowired
	private StokService stokService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/barang/{barang}/jumlah/{jumlah}")
	@ResponseBody
	public RestMessage tambah(@PathVariable Long barang, @PathVariable Long jumlah) throws ApplicationException, PersistenceException {
		stokService.simpanStokMasuk(barang, jumlah);
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{awal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<StokMasuk> get(@PathVariable String awal, @PathVariable String akhir) throws ApplicationException, PersistenceException {
		List<StokMasuk> list = stokService.getStokMasuk(DateUtil.getDate(awal), DateUtil.getDate(akhir));
		return ListEntityRestMessage.createListStokMasuk(list);
	}
}