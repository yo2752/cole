package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the RECONOCIMIENTO_DEUDA database table.
 * 
 */
@Entity
@Table(name="RECONOCIMIENTO_DEUDA")
public class ReconocimientoDeudaVO implements Serializable {

	private static final long serialVersionUID = -9073174074876712250L;

	@Id
	@SequenceGenerator(name = "reconocimiento_deuda_secuencia", sequenceName = "RECONOCIMIENTO_DEUDA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "reconocimiento_deuda_secuencia")
	@Column(name="ID_RECONOCIMIENTO_DEUDA")
	private long idReconocimientoDeuda;

	@Column(name="DIA_VENCIMIENTO")
	private BigDecimal diaVencimiento;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	@Column(name="FEC_PRIMER_VENCIMIENTO")
	private Timestamp fecPrimerVencimiento;

	@Column(name="IMP_PLAZOS")
	private BigDecimal impPlazos;

	@Column(name="IMP_RECONOCIDO")
	private BigDecimal impReconocido;

	@Column(name="NUM_PLAZOS")
	private BigDecimal numPlazos;

	@Column(name="TIEMPO_ENTRE_PAGOS")
	private BigDecimal tiempoEntrePagos;

	@Column(name="ID_DATOS_FINANCIEROS")
	private BigDecimal idDatosFinancieros;

	@ManyToOne
	@JoinColumn(name="ID_DATOS_FINANCIEROS", insertable=false, updatable=false)
	private DatosFinancierosVO datosFinancieros;

	public ReconocimientoDeudaVO() {
	}

	public long getIdReconocimientoDeuda() {
		return this.idReconocimientoDeuda;
	}

	public void setIdReconocimientoDeuda(long idReconocimientoDeuda) {
		this.idReconocimientoDeuda = idReconocimientoDeuda;
	}

	public BigDecimal getDiaVencimiento() {
		return this.diaVencimiento;
	}

	public void setDiaVencimiento(BigDecimal diaVencimiento) {
		this.diaVencimiento = diaVencimiento;
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

	public Timestamp getFecPrimerVencimiento() {
		return this.fecPrimerVencimiento;
	}

	public void setFecPrimerVencimiento(Timestamp fecPrimerVencimiento) {
		this.fecPrimerVencimiento = fecPrimerVencimiento;
	}

	public BigDecimal getImpPlazos() {
		return this.impPlazos;
	}

	public void setImpPlazos(BigDecimal impPlazos) {
		this.impPlazos = impPlazos;
	}

	public BigDecimal getImpReconocido() {
		return this.impReconocido;
	}

	public void setImpReconocido(BigDecimal impReconocido) {
		this.impReconocido = impReconocido;
	}

	public BigDecimal getNumPlazos() {
		return this.numPlazos;
	}

	public void setNumPlazos(BigDecimal numPlazos) {
		this.numPlazos = numPlazos;
	}

	public BigDecimal getTiempoEntrePagos() {
		return this.tiempoEntrePagos;
	}

	public void setTiempoEntrePagos(BigDecimal tiempoEntrePagos) {
		this.tiempoEntrePagos = tiempoEntrePagos;
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