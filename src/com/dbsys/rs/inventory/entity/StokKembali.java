package com.dbsys.rs.inventory.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.dbsys.rs.CodedEntity;
import com.dbsys.rs.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("STOK_KEMBALI")
public class StokKembali extends Stok implements CodedEntity {

	private Pasien pasien;
	private String nomor;
	
	public StokKembali() {
		super();
	}

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}) // Testing
	// @ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "pasien")
	public Pasien getPasien() {
		return pasien;
	}

	public void setPasien(Pasien pasien) {
		this.pasien = pasien;
	}

	@Column(name = "nomor_kembali")
	public String getNomor() {
		return nomor;
	}

	public void setNomor(String nomor) {
		this.nomor = nomor;
	}

	public Long hitungPengembalian() {
		return barang.getHarga() * jumlah;
	}
	
	@Override
	public String generateKode() {
		return createKode();
	}
	
	public static String createKode() {
		Integer d = Math.abs(DateUtil.getDate().hashCode());
		Integer t = Math.abs(DateUtil.getTime().hashCode());
		
		return String.format("30%s00%s", d, t);
	}

	@Override
	@JsonIgnore
	@Transient
	public String getKode() {
		return getNomor();
	}

	@Override
	public void setKode(String kode) {
		setNomor(kode);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((pasien == null) ? 0 : pasien.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StokKembali other = (StokKembali) obj;
		if (pasien == null) {
			if (other.pasien != null)
				return false;
		} else if (!pasien.equals(other.pasien))
			return false;
		return true;
	}
}
