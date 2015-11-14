package com.dbsys.rs.inventory.test.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.dbsys.rs.inventory.repository.BarangRepository;
import com.dbsys.rs.inventory.service.BarangService;
import com.dbsys.rs.inventory.test.TestConfig;
import com.dbsys.rs.lib.Penanggung;
import com.dbsys.rs.lib.entity.BahanHabisPakai;
import com.dbsys.rs.lib.entity.Barang;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class BhpControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private long count;

	@Autowired
	private BarangService barangService;
	@Autowired
	private BarangRepository barangRepository;
	
	private Barang barang;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		count = barangRepository.count();
		
		barang = new BahanHabisPakai();
		barang.setHarga(10000l);
		barang.setJumlah(20l);
		barang.setKode("BHP01");
		barang.setNama("Bahan Habis Pakai");
		barang.setSatuan("Satuan");
		barang.setPenanggung(Penanggung.BPJS);
		barang = barangService.save(barang);
		
		assertEquals(count + 1, barangRepository.count());
	}
	
	@Test
	public void testSave() throws Exception {
		this.mockMvc.perform(
				post("/barang")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"harga\": \"20000\","
						+ "\"jumlah\": \"100\","
						+ "\"kode\": \"BHP02\","
						+ "\"nama\":\"BHP 2\","
						+ "\"satuan\":\"satuan\","
						+ "\"penanggung\":\"UMUM\","
						+ "\"tipeBarang\": \"BHP\""
						+ "}")
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.tipe").value("BHP"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(count + 2, barangRepository.count());
	}

	@Test
	public void testGetAll() throws Exception {
		this.mockMvc.perform(
				get("/barang")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testCariNama() throws Exception {
		this.mockMvc.perform(
				get(String.format("/barang/keyword/%s", barang.getNama()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testCariKode() throws Exception {
		this.mockMvc.perform(
				get(String.format("/barang/keyword/%s", barang.getKode()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testGetAllBhp() throws Exception {
		this.mockMvc.perform(
				get("/barang/class/BahanHabisPakai")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testCariNamaBhp() throws Exception {
		this.mockMvc.perform(
				get(String.format("/barang/keyword/%s/class/%s", barang.getNama(), BahanHabisPakai.class.getSimpleName()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testCariKodeBhp() throws Exception {
		this.mockMvc.perform(
				get(String.format("/barang/keyword/%s/class/%s", barang.getKode(), BahanHabisPakai.class.getSimpleName()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}
}
