package hibernate.entities.facturacion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the FACTURA_SUPLIDO database table.
 * 
 */
@Entity
@Table(name="FACTURA_SUPLIDO")
public class FacturaSuplido implements Cloneable,Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "suplido_secuencia", sequenceName = "ID_SUPLIDO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "suplido_secuencia")
	@Column(name="ID_SUPLIDO", unique=true, nullable=false, precision=22)
	private long idSuplido;

	@Column(length=20)
	private Float suplido;

	@Column(name="SUPLIDO_DESCRIPCION", length=255)
	private String suplidoDescripcion;

	@Column(name="SUPLIDO_TOTAL", length=10)
	private Float suplidoTotal;

	@Column(name="SUPLIDO_CHECK_DESCUENTO", length=1)
	private String suplidoCheckDescuento;

	@Column(name="SUPLIDO_DESCUENTO", length=3)
	private Float suplidoDescuento;

	//bi-directional many-to-one association to FacturaConcepto
	@ManyToOne
	@JoinColumn(name="ID_CONCEPTO", referencedColumnName="ID_CONCEPTO")
	private FacturaConcepto facturaConcepto;

	public FacturaSuplido() {
	}
	public long getIdSuplido() {
		return this.idSuplido;
	}
	public void setIdSuplido(long idSuplido) {
		this.idSuplido = idSuplido;
	}
	public String getSuplidoDescripcion() {
		return this.suplidoDescripcion;
	}
	public void setSuplidoDescripcion(String suplidoDescripcion) {
		this.suplidoDescripcion = suplidoDescripcion;
	}
	public String getSuplidoCheckDescuento() {
		return suplidoCheckDescuento;
	}
	public void setSuplidoCheckDescuento(String suplidoCheckDescuento) {
		this.suplidoCheckDescuento = suplidoCheckDescuento;
	}
	public FacturaConcepto getFacturaConcepto() {
		return this.facturaConcepto;
	}

	public void setFacturaConcepto(FacturaConcepto facturaConcepto) {
		this.facturaConcepto = facturaConcepto;
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			System.out.println(" no se puede duplicar");
		}
		return obj;
	}
	public Float getSuplido() {
		return suplido;
	}
	public void setSuplido(Float suplido) {
		this.suplido = suplido;
	}
	public Float getSuplidoTotal() {
		return suplidoTotal;
	}
	public void setSuplidoTotal(Float suplidoTotal) {
		this.suplidoTotal = suplidoTotal;
	}
	public Float getSuplidoDescuento() {
		return suplidoDescuento;
	}
	public void setSuplidoDescuento(Float suplidoDescuento) {
		this.suplidoDescuento = suplidoDescuento;
	}
}