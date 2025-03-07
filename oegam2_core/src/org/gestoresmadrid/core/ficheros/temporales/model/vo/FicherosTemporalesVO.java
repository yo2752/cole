package org.gestoresmadrid.core.ficheros.temporales.model.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "FICHEROS_TEMPORALES")
public class FicherosTemporalesVO {
	
	@Id
	@Column(name="ID_FICHEROS_TEMPORALES")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEC_FICHEROS_TEMPORALES")
	@SequenceGenerator(name = "SEC_FICHEROS_TEMPORALES", sequenceName = "ID_FICHEROS_TEMPORALES_SEQ")
	private Long idFicheroTemporal;
	
	@Column(name="NOMBRE_FICHERO")
	private String nombre;
	
	@Column(name="EXTENSION")
	private String extension;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA")
	private Date fecha;
	
	@Column(name="TIPO")
	private String tipoDocumento;
	
	@Column(name="SUB_TIPO")
	private String subTipoDocumento;
	
	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;
	
	@Column(name = "ID_USUARIO")
	private Long idUsuario;
	
	@Column(name = "ID_CONTRATO")
	private Long idContrato;
	
	@Column(name="ESTADO_IMPRESION")
	private Integer estado;

	public Long getIdFicheroTemporal() {
		return idFicheroTemporal;
	}

	public void setIdFicheroTemporal(Long idFicheroTemporal) {
		this.idFicheroTemporal = idFicheroTemporal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getSubTipoDocumento() {
		return subTipoDocumento;
	}

	public void setSubTipoDocumento(String subTipoDocumento) {
		this.subTipoDocumento = subTipoDocumento;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
}
