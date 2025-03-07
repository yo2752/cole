package hibernate.entities.facturacion;

import hibernate.entities.general.Colegiado;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the FACTURA_COLEGIADO_CONCEPTO database table.
 * 
 */
@Entity
@Table(name="FACTURA_COLEGIADO_CONCEPTO")
public class FacturaColegiadoConcepto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="genSeq", sequenceName="CLAVE_COLEGIADO_CONCEPTO_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="genSeq")
	@Column(name="ID_COLEGIADO_CONCEPTO")
	private long idColegiadoConcepto;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="NOMBRE_COLEGIADO_CONCEPTO")
	private String nombreColegiadoConcepto;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="NUM_COLEGIADO", referencedColumnName="NUM_COLEGIADO", insertable=false, updatable=false)
	private Colegiado colegiado;

	public FacturaColegiadoConcepto() { }

	public String getNombreColegiadoConcepto() {
		return this.nombreColegiadoConcepto;
	}
	public void setNombreColegiadoConcepto(String nombreColegiadoConcepto) {
		this.nombreColegiadoConcepto = nombreColegiadoConcepto;
	}

	public long getIdColegiadoConcepto() {
		return idColegiadoConcepto;
	}

	public void setIdColegiadoConcepto(long idColegiadoConcepto) {
		this.idColegiadoConcepto = idColegiadoConcepto;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
}