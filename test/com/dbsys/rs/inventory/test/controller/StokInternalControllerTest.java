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
import com.dbsys.rs.inventory.repository.UnitRepository;
import com.dbsys.rs.inventory.service.BarangService;
import com.dbsys.rs.inventory.service.StokService;
import com.dbsys.rs.inventory.test.TestConfig;
import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.Penanggung;
import com.dbsys.rs.lib.entity.Barang;
import com.dbsys.rs.lib.entity.ObatFarmasi;
import com.dbsys.rs.lib.entity.Stok.JenisStok;
import com.dbsys.rs.lib.entity.Unit.TipeUnit;
import com.dbsys.rs.lib.entity.StokInternal;
import com.dbsys.rs.lib.entity.Unit;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class StokInternalControllerTest {

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
	@Autowired
	private UnitRepository unitRepository;

	private Barang barang;
	private Unit unit;
	
	@Before
	public void setup() throws ApplicationException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		count = stokRepository.count();
		
		barang = new ObatFarmasi("Keterangan");
		barang.setHarga(10000l);
		barang.setJumlah(10L);
		barang.setKode("BHP01");
		barang.setNama("Bahan Habis Pakai");
		barang.setSatuan("Satuan");
		barang.setPenanggung(Penanggung.BPJS);
		barang = barangService.save(barang);
		
		unit = new Unit();
		unit.setNama("Unit");
		unit.setTipe(TipeUnit.APOTEK_FARMASI);
		unit.setBobot(1F);
		unit = unitRepository.save(unit);

		StokInternal stok = new StokInternal();
		stok.setBarang(barang);
		stok.setJumlah(2L);
		stok.setJenis(JenisStok.MASUK);
		stok.setUnit(unit);
		stok = (StokInternal) stokService.simpan(stok);
		
		assertEquals(count + 1, stokRepository.count());
		assertEquals(new Long(8), stok.getBarang().getJumlah());
	}
	
	@Test
	public void testStok() throws Exception {
		this.mockMvc.perform(
				post("/stok")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tipeStok\": \"INTERNAL\","
						+ "\"jumlah\": \"10\","
						+ "\"barang\": {"
						+ "\"harga\": \"20000\","
						+ "\"jumlah\": \"100\","
						+ "\"kode\": \"BHP02\","
						+ "\"nama\":\"BHP 2\","
						+ "\"satuan\":\"satuan\","
						+ "\"penanggung\":\"UMUM\","
						+ "\"keterangan\":\"keterangan\","
						+ "\"tipeBarang\": \"OBAT\""
						+ "},"
						+ "\"unit\": {"
						+ "\"nama\": \"Unit2\","
						+ "\"bobot\": \"1F\","
						+ "\"tipe\": \"POLIKLINIK\""
						+ "}"
						+ "}")
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.tipe").value("INTERNAL"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(count + 2, stokRepository.count());
	}
	
	@Test
	public void testStokTakCukup() throws Exception {
		this.mockMvc.perform(
				post("/stok")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tipeStok\": \"INTERNAL\","
						+ "\"jumlah\": \"11\","
						+ "\"barang\": {"
						+ "\"harga\": \"20000\","
						+ "\"jumlah\": \"10\","
						+ "\"kode\": \"BHP02\","
						+ "\"nama\":\"BHP 2\","
						+ "\"satuan\":\"satuan\","
						+ "\"penanggung\":\"UMUM\","
						+ "\"keterangan\":\"keterangan\","
						+ "\"tipeBarang\": \"OBAT\""
						+ "},"
						+ "\"unit\": {"
						+ "\"nama\": \"Unit2\","
						+ "\"bobot\": \"1F\","
						+ "\"tipe\": \"POLIKLINIK\""
						+ "}"
						+ "}")
			)
			.andExpect(jsonPath("$.tipe").value("ERROR"))
			.andExpect(jsonPath("$.message").value("Jumlah barang tidak cukup untuk dikurangi.\n Jumlah saat ini 10"));
	}

	@Test
	public void testGet() throws Exception {
		this.mockMvc.perform(
				get(String.format("/stok/%s/to/%s/unit", "2015-10-1", "2015-11-30"))
				.contentType(MediaType.APPLICATION_JSON)
						
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testGetByUnit() throws Exception {
		this.mockMvc.perform(
				get(String.format("/stok/%s/to/%s/unit/%d", "2015-10-1", "2015-11-30", unit.getId()))
				.contentType(MediaType.APPLICATION_JSON)
						
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}
}
