package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the COMISION database table.
 * 
 */
@Entity
@Table(name="COMISION")
public class ComisionVO implements Serializable {

	private static final long serialVersionUID = 6252728358101699658L;

	@Id
	@SequenceGenerator(name = "comision_secuencia", sequenceName = "COMISION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "comision_secuencia")
	@Column(name="ID_COMISION")
	private long idComision;

	@Column(name="CONDICION_APLICACION")
	private String condicionAplicacion;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	private String financiado;

	@Column(name="IMPORTE_MAXIMO")
	private BigDecimal importeMaximo;

	@Column(name="IMPORTE_MINIMO")
	private BigDecimal importeMinimo;

	private BigDecimal porcentaje;

	@Column(name="TIPO_COMISION")
	private String tipoComision;

	@Column(name="ID_DATOS_FINANCIEROS")
	private BigDecimal idDatosFinancieros;

	@ManyToOne
	@JoinColumn(name="ID_DATOS_FINANCIEROS", insertable=false, updatable=false)
	private DatosFinancierosVO datosFinancieros;

	public ComisionVO() {
	}

	public long getIdComision() {
		return this.idComision;
	}

	public void setIdComision(long idComision) {
		this.idComision = idComision;
	}

	public String getCondicionAplicacion() {
		return this.condicionAplicacion;
	}

	public void setCondicionAplicacion(String condicionAplicacion) {
		this.condicionAplicacion = condicionAplicacion;
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

	public String getFinanciado() {
		return this.financiado;
	}

	public void setFinanciado(String financiado) {
		this.financiado = financiado;
	}

	public BigDecimal getImporteMaximo() {
		return this.importeMaximo;
	}

	public void setImporteMaximo(BigDecimal importeMaximo) {
		this.importeMaximo = importeMaximo;
	}

	public BigDecimal getImporteMinimo() {
		return this.importeMinimo;
	}

	public void setImporteMinimo(BigDecimal importeMinimo) {
		this.importeMinimo = importeMinimo;
	}

	public BigDecimal getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getTipoComision() {
		return this.tipoComision;
	}

	public void setTipoComision(String tipoComision) {
		this.tipoComision = tipoComision;
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