package com.dbsys.rs.inventory.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dbsys.rs.DateUtil;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Table(name = "stok")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
	name = "tipe",
	discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue("STOK")
@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "tipeStok"
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = StokKembali.class, name = "KEMBALI"),
	@JsonSubTypes.Type(value = Stok.class, name = "STOK")
})
public class Stok {
	
	public enum JenisStok {
		MASUK,
		KELUAR
	}

	protected Long id;
	protected Long jumlah;
	protected Date tanggal;
	protected Time jam;
	protected Barang barang;
	protected JenisStok jenis;
	
	// Untuk JSON bukan JPA
	private String tipeStok;
	
	public Stok() {
		super();
		this.tipeStok = "STOK";
		setTanggal(DateUtil.getDate());
		setJam(DateUtil.getTime());
	}
	
	public Stok(String name) {
		this();
		this.tipeStok = name;
	}

	@Transient
	public String getTipe() {
		return tipeStok;
	}

	public void setTipe(String tipeStok) {
		this.tipeStok = tipeStok;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "jumlah")
	public Long getJumlah() {
		return jumlah;
	}

	public void setJumlah(Long jumlah) {
		this.jumlah = jumlah;
	}

	@Column(name = "tanggal")
	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	@Column(name = "jam")
	public Time getJam() {
		return jam;
	}

	public void setJam(Time jam) {
		this.jam = jam;
	}

	@ManyToOne
	@JoinColumn(name = "barang")
	public Barang getBarang() {
		return barang;
	}

	public void setBarang(Barang barang) {
		this.barang = barang;
	}

	@Column(name = "jenis")
	public JenisStok getJenis() {
		return jenis;
	}

	public void setJenis(JenisStok jenis) {
		this.jenis = jenis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jam == null) ? 0 : jam.hashCode());
		result = prime * result + ((jumlah == null) ? 0 : jumlah.hashCode());
		result = prime * result + ((tanggal == null) ? 0 : tanggal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stok other = (Stok) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jam == null) {
			if (other.jam != null)
				return false;
		} else if (!jam.equals(other.jam))
			return false;
		if (jumlah == null) {
			if (other.jumlah != null)
				return false;
		} else if (!jumlah.equals(other.jumlah))
			return false;
		if (tanggal == null) {
			if (other.tanggal != null)
				return false;
		} else if (!tanggal.equals(other.tanggal))
			return false;
		return true;
	}
}
