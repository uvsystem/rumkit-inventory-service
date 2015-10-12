package com.dbsys.rs.inventory.test.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.dbsys.rs.inventory.repository.StokRepository;
import com.dbsys.rs.inventory.service.BarangService;
import com.dbsys.rs.inventory.service.StokService;
import com.dbsys.rs.inventory.test.TestConfig;
import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.Tanggungan;
import com.dbsys.rs.lib.entity.Barang;
import com.dbsys.rs.lib.entity.ObatFarmasi;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class StokKeluarControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private long count;

	@Autowired
	private StokService stokService;
	@Autowired
	private BarangService barangService;
	@Autowired
	private StokRepository stokRepository;

	private Barang barang;
	
	@Before
	public void setup() throws ApplicationException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		count = stokRepository.count();
		
		barang = new ObatFarmasi("Keterangan");
		barang.setHarga(10000l);
		barang.setJumlah(20l);
		barang.setKode("BHP01");
		barang.setNama("Bahan Habis Pakai");
		barang.setSatuan("Satuan");
		barang.setTanggungan(Tanggungan.BPJS);
		barang = barangService.save(barang);
		
		stokService.simpanStokKeluar(barang.getId(), 10l);
		assertEquals(count + 1, stokRepository.count());
	}
	
	@Test
	public void testTambah() throws Exception {
		this.mockMvc.perform(
				post(String.format("/stok/keluar/barang/%s/jumlah/%s", barang.getId(), 10))
				.contentType(MediaType.APPLICATION_JSON)
						
			)
			.andExpect(jsonPath("$.tipe").value("SUCCESS"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(count + 2, stokRepository.count());
	}
	
	@Test
	public void testTambahNumberException() throws Exception {
		this.mockMvc.perform(
				post(String.format("/stok/keluar/barang/%s/jumlah/%s", barang.getId(), 20))
				.contentType(MediaType.APPLICATION_JSON)
						
			)
			.andExpect(jsonPath("$.tipe").value("ERROR"))
			.andExpect(jsonPath("$.message").value("Jumlah barang tidak cukup untuk dikurangi.\n Jumlah saat ini 10"));
	}

	@Test
	public void testGet() throws Exception {
		this.mockMvc.perform(
				get(String.format("/stok/keluar/%s/to/%s", "2015-10-1", "2015-10-19"))
				.contentType(MediaType.APPLICATION_JSON)
						
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}
}
