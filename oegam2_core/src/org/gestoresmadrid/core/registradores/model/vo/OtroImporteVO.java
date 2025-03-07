package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the OTRO_IMPORTE database table.
 * 
 */
@Entity
@Table(name="OTRO_IMPORTE")
public class OtroImporteVO implements Serializable {

	private static final long serialVersionUID = 579894990914877362L;

	@Id
	@SequenceGenerator(name = "otro_importe_secuencia", sequenceName = "OTRO_IMPORTE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "otro_importe_secuencia")
	@Column(name="ID_OTRO_IMPORTE")
	private long idOtroImporte;

	private String condicionante;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	private BigDecimal importe;

	private String observaciones;

	private BigDecimal porcentaje;

	@Column(name="PORCENTAJE_BASE")
	private BigDecimal porcentajeBase;

	@Column(name="TIPO_OTRO_IMPORTE")
	private String tipoOtroImporte;

	@Column(name="ID_DATOS_FINANCIEROS")
	private BigDecimal idDatosFinancieros;

	@ManyToOne
	@JoinColumn(name="ID_DATOS_FINANCIEROS", insertable=false, updatable=false)
	private DatosFinancierosVO datosFinancieros;

	public OtroImporteVO() {
	}

	public long getIdOtroImporte() {
		return this.idOtroImporte;
	}

	public void setIdOtroImporte(long idOtroImporte) {
		this.idOtroImporte = idOtroImporte;
	}

	public String getCondicionante() {
		return this.condicionante;
	}

	public void setCondicionante(String condicionante) {
		this.condicionante = condicionante;
	}

	public Timestamp getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public BigDecimal getImporte() {
		return this.importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public BigDecimal getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public BigDecimal getPorcentajeBase() {
		return this.porcentajeBase;
	}

	public void setPorcentajeBase(BigDecimal porcentajeBase) {
		this.porcentajeBase = porcentajeBase;
	}

	public String getTipoOtroImporte() {
		return this.tipoOtroImporte;
	}

	public void setTipoOtroImporte(String tipoOtroImporte) {
		this.tipoOtroImporte = tipoOtroImporte;
	}

	public BigDecimal getIdDatosFinancieros() {
		return idDatosFinancieros;
	}

	public void setIdDatosFinancieros(BigDecimal idDatosFinancieros) {
		this.idDatosFinancieros = idDatosFinancieros;
	}

	public DatosFinancierosVO getDatosFinancieros() {
		return datosFinancieros;
	}

	public void setDatosFinancieros(DatosFinancierosVO datosFinancieros) {
		this.datosFinancieros = datosFinancieros;
	}

}