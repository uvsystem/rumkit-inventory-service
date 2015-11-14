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
import com.dbsys.rs.lib.Penanggung;
import com.dbsys.rs.lib.entity.Barang;
import com.dbsys.rs.lib.entity.ObatFarmasi;
import com.dbsys.rs.lib.entity.Stok;
import com.dbsys.rs.lib.entity.StokEksternal;
import com.dbsys.rs.lib.entity.Stok.JenisStok;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class StokEksternalControllerTest {

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
		barang.setJumlah(0L);
		barang.setKode("BHP01");
		barang.setNama("Bahan Habis Pakai");
		barang.setSatuan("Satuan");
		barang.setPenanggung(Penanggung.BPJS);
		barang = barangService.save(barang);

		Stok stokMasuk = new StokEksternal();
		stokMasuk.setBarang(barang);
		stokMasuk.setJumlah(10L);
		stokMasuk.setJenis(JenisStok.MASUK);
		stokMasuk = stokService.simpan(stokMasuk);
		
		assertEquals(count + 1, stokRepository.count());
		assertEquals(new Long(10), stokMasuk.getBarang().getJumlah());
		
		Stok stokKeluar = new StokEksternal();
		stokKeluar.setBarang(barang);
		stokKeluar.setJumlah(2L);
		stokKeluar.setJenis(JenisStok.KELUAR);
		stokKeluar = stokService.simpan(stokKeluar);
		
		assertEquals(count + 2, stokRepository.count());
		assertEquals(new Long(8), stokKeluar.getBarang().getJumlah());
	}
	
	@Test
	public void testStokMasuk() throws Exception {
		this.mockMvc.perform(
				post("/stok")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"jumlah\": \"10\","
						+ "\"jenis\": \"MASUK\","
						+ "\"tipeStok\": \"EKSTERNAL\","
						+ "\"barang\": {"
						+ "\"harga\": \"20000\","
						+ "\"jumlah\": \"100\","
						+ "\"kode\": \"BHP02\","
						+ "\"nama\":\"BHP 2\","
						+ "\"satuan\":\"satuan\","
						+ "\"penanggung\":\"UMUM\","
						+ "\"tipeBarang\": \"BHP\""
						+ "}"
						+ "}")
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.tipe").value("EKSTERNAL"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(count + 3, stokRepository.count());
	}

	@Test
	public void testGetStokMasuk() throws Exception {
		this.mockMvc.perform(
				get(String.format("/stok/masuk/%s/to/%s", "2015-10-1", "2015-11-30"))
				.contentType(MediaType.APPLICATION_JSON)
						
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}
	
	@Test
	public void testStokKeluar() throws Exception {
		this.mockMvc.perform(
				post("/stok")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tipeStok\": \"EKSTERNAL\","
						+ "\"jumlah\": \"2\","
						+ "\"jenis\": \"KELUAR\","
						+ "\"barang\": {"
						+ "\"harga\": \"20000\","
						+ "\"jumlah\": \"100\","
						+ "\"kode\": \"BHP02\","
						+ "\"nama\":\"BHP 2\","
						+ "\"satuan\":\"satuan\","
						+ "\"penanggung\":\"UMUM\","
						+ "\"keterangan\":\"keterangan\","
						+ "\"tipeBarang\": \"OBAT\""
						+ "}"
						+ "}")
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.tipe").value("EKSTERNAL"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(count + 3, stokRepository.count());
	}
	
	@Test
	public void testStokKeluarTidakCukup() throws Exception {
		this.mockMvc.perform(
				post("/stok")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tipeStok\": \"EKSTERNAL\","
						+ "\"jumlah\": \"10\","
						+ "\"jenis\": \"KELUAR\","
						+ "\"barang\": {"
						+ "\"harga\": \"20000\","
						+ "\"jumlah\": \"2\","
						+ "\"kode\": \"BHP02\","
						+ "\"nama\":\"BHP 2\","
						+ "\"satuan\":\"satuan\","
						+ "\"penanggung\":\"UMUM\","
						+ "\"keterangan\":\"keterangan\","
						+ "\"tipeBarang\": \"OBAT\""
						+ "}"
						+ "}")
			)
			.andExpect(jsonPath("$.tipe").value("ERROR"))
			.andExpect(jsonPath("$.message").value("Jumlah barang tidak cukup untuk dikurangi.\n Jumlah saat ini 2"));
	}
}
