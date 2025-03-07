package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FICHERO database table.
 * 
 */
@Entity
@Table(name="FICHERO")
public class FicheroVO implements Serializable {

	private static final long serialVersionUID = -5906457956795208162L;

	@Id
	@SequenceGenerator(name = "fichero_secuencia", sequenceName = "FICHERO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "fichero_secuencia")
	@Column(name="ID_FICHERO")
	private long idFichero;

	@Column(name="CODIGO_CSV")
	private String codigoCsv;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_CREACION")
	private Date fecCreacion;

	private String formato;

	@Column(name="NOMBRE_FICHERO")
	private String nombreFichero;

	@Column(name="OCUPACION_BYTES")
	private BigDecimal ocupacionBytes;

	@Column(name="\"PATH\"")
	private String path;

	@Column(name="URL_CSV")
	private String urlCsv;

	//bi-directional many-to-many association to Mensaje
	@ManyToMany
	@JoinTable(
		name="FICHERO_MENSAJE"
		, joinColumns={
			@JoinColumn(name="ID_FICHERO")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ID_MENSAJE")
			}
		)
	private List<MensajeVO> mensajes;

	public FicheroVO() {
	}

	public long getIdFichero() {
		return this.idFichero;
	}

	public void setIdFichero(long idFichero) {
		this.idFichero = idFichero;
	}

	public String getCodigoCsv() {
		return this.codigoCsv;
	}

	public void setCodigoCsv(String codigoCsv) {
		this.codigoCsv = codigoCsv;
	}

	public Date getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Date fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public String getFormato() {
		return this.formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getNombreFichero() {
		return this.nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public BigDecimal getOcupacionBytes() {
		return this.ocupacionBytes;
	}

	public void setOcupacionBytes(BigDecimal ocupacionBytes) {
		this.ocupacionBytes = ocupacionBytes;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrlCsv() {
		return this.urlCsv;
	}

	public void setUrlCsv(String urlCsv) {
		this.urlCsv = urlCsv;
	}

	public List<MensajeVO> getMensajes() {
		return this.mensajes;
	}

	public void setMensajes(List<MensajeVO> mensajes) {
		this.mensajes = mensajes;
	}

}