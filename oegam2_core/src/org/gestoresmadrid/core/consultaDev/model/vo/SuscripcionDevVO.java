package org.gestoresmadrid.core.consultaDev.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="SUSCRIPCION_DEV")
public class SuscripcionDevVO implements Serializable{

	private static final long serialVersionUID = 1980400726564072049L;

	@Id
	@Column(name = "ID_SUSCRIPCION_DEV")
	@SequenceGenerator(name = "suscripcion_dev_secuencia", sequenceName = "ID_SUSCRIPCION_DEV_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "suscripcion_dev_secuencia")
	private Long idSuscripcionDev;
	
	@Column(name = "ID_CONSULTA_DEV")
	private Long idConsultaDev;
	
	@Column(name="DATOS_PERSONALES")
	private String datosPersonales;
	
	@Column(name="COD_PROCEDIMIENTO")
	private String codProcedimiento;
	
	@Column(name="DESC_PROCEDIMIENTO")
	private String descProcedimiento;
	
	@Column(name="EMISOR")
	private String emisor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_SUPCRIPCION")
	private Date fechaSuscripcion;
	
	@Column(name="COD_ESTADO")
	private String codEstado;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ID_CONSULTA_DEV", insertable = false, updatable=false)
	private ConsultaDevVO consultaDev;

	public Long getIdSuscripcionDev() {
		return idSuscripcionDev;
	}

	public void setIdSuscripcionDev(Long idSuscripcionDev) {
		this.idSuscripcionDev = idSuscripcionDev;
	}

	public Long getIdConsultaDev() {
		return idConsultaDev;
	}

	public void setIdConsultaDev(Long idConsultaDev) {
		this.idConsultaDev = idConsultaDev;
	}

	public String getCodProcedimiento() {
		return codProcedimiento;
	}

	public void setCodProcedimiento(String codProcedimiento) {
		this.codProcedimiento = codProcedimiento;
	}

	public String getDescProcedimiento() {
		return descProcedimiento;
	}

	public void setDescProcedimiento(String descProcedimiento) {
		this.descProcedimiento = descProcedimiento;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public Date getFechaSuscripcion() {
		return fechaSuscripcion;
	}

	public void setFechaSuscripcion(Date fechaSuscripcion) {
		this.fechaSuscripcion = fechaSuscripcion;
	}

	public String getCodEstado() {
		return codEstado;
	}

	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}

	public ConsultaDevVO getConsultaDev() {
		return consultaDev;
	}

	public void setConsultaDev(ConsultaDevVO consultaDev) {
		this.consultaDev = consultaDev;
	}

	public String getDatosPersonales() {
		return datosPersonales;
	}

	public void setDatosPersonales(String datosPersonales) {
		this.datosPersonales = datosPersonales;
	}
	
}
