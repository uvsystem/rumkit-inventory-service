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

import com.dbsys.rs.inventory.repository.PasienRepository;
import com.dbsys.rs.inventory.repository.StokRepository;
import com.dbsys.rs.inventory.service.BarangService;
import com.dbsys.rs.inventory.service.StokService;
import com.dbsys.rs.inventory.test.TestConfig;
import com.dbsys.rs.ApplicationException;
import com.dbsys.rs.DateUtil;
import com.dbsys.rs.Penanggung;
import com.dbsys.rs.inventory.entity.Barang;
import com.dbsys.rs.inventory.entity.ObatFarmasi;
import com.dbsys.rs.inventory.entity.Pasien;
import com.dbsys.rs.inventory.entity.Pasien.Pendaftaran;
import com.dbsys.rs.inventory.entity.Pasien.Perawatan;
import com.dbsys.rs.inventory.entity.Penduduk;
import com.dbsys.rs.inventory.entity.Pasien.StatusPasien;
import com.dbsys.rs.inventory.entity.Penduduk.Kelamin;
import com.dbsys.rs.inventory.entity.Stok;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class StokKembaliControllerTest {

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
	private PasienRepository pasienRepository;

	private Barang barang;
	private Pasien pasien;
	
	@Before
	public void setup() throws ApplicationException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		count = stokRepository.count();
		
		barang = new ObatFarmasi("Keterangan");
		barang.setHarga(10000l);
		barang.setJumlah(10L);
		barang.setKode("BHP blum ada");
		barang.setNama("Bahan Habis Pakai");
		barang.setSatuan("Satuan");
		barang.setPenanggung(Penanggung.BPJS);
		barang = barangService.save(barang);
		
		Penduduk penduduk = new Penduduk();
		penduduk.setAgama("Kristen");
		penduduk.setDarah("O");
		penduduk.setKelamin(Kelamin.PRIA);
		penduduk.setNama("Penduduk xxx");
		penduduk.setNik("Nik xxx");
		penduduk.setTanggalLahir(DateUtil.getDate());
		penduduk.setTelepon("Telepon");
		penduduk.setKode("PDK Blum Ada");

		pasien = new Pasien();
		pasien.setPenduduk(penduduk);
		pasien.setPenanggung(Penanggung.BPJS);
		pasien.setStatus(StatusPasien.PERAWATAN);
		pasien.setTipePerawatan(Perawatan.RAWAT_JALAN);
		pasien.setTanggalMasuk(DateUtil.getDate());
		pasien.setPendaftaran(Pendaftaran.LOKET);
		pasien.setKode("PSN Blum Ada");
		pasien = pasienRepository.save(pasien);

		Stok stok = new Stok();
		stok.setBarang(barang);
		stok.setJumlah(2L);
		stok = stokService.simpan(stok);
		
		assertEquals(count + 1, stokRepository.count());
		assertEquals(new Long(12), stok.getBarang().getJumlah());
	}
	
	@Test
	public void testStok() throws Exception {
		this.mockMvc.perform(
				post("/stok")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
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
						+ "\"pasien\": {"
						+ "\"penduduk\": {"
						+ "\"agama\": \"Kristen\","
						+ "\"darah\": \"O\","
						+ "\"kelamin\": \"PRIA\","
						+ "\"nama\":\"Penduduk xxxx\","
						+ "\"nik\":\"nik xxxx\","
						+ "\"tanggalLahir\":\"1991-12-05\","
						+ "\"telepon\":\"telepon 2\","
						+ "\"kode\": \"KODE\""
						+ "},"
						+ "\"penanggung\": \"BPJS\","
						+ "\"status\": \"PERAWATAN\","
						+ "\"tipePerawatan\": \"RAWAT_JALAN\","
						+ "\"tanggalMasuk\": \"2015-10-1\","
						+ "\"kode\": \"KODE\","
						+ "\"pendaftaran\": \"LOKET\""
						+ "}"
						+ "}")
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
		
		assertEquals(count + 2, stokRepository.count());
	}

	@Test
	public void testGet() throws Exception {
		this.mockMvc.perform(
				get(String.format("/stok/%s/to/%s", "2016-2-1", "2016-2-29"))
				.contentType(MediaType.APPLICATION_JSON)
						
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testGetByPasien() throws Exception {
		this.mockMvc.perform(
				get(String.format("/stok/pasien/%d", pasien.getId()))
				.contentType(MediaType.APPLICATION_JSON)
						
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}
}
