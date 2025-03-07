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
 * The persistent class for the FACTURA_GASTO database table.
 * 
 */
@Entity
@Table(name="FACTURA_GASTO")
public class FacturaGasto implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "gasto_secuencia", sequenceName = "ID_GASTO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "gasto_secuencia")
	@Column(name="ID_GASTO", unique=true, nullable=false, precision=22)
	private long idGasto;

	private Float gasto;

	@Column(name="GASTO_CHECK")
	private String gastoCheck;

	@Column(name="GASTO_DESCRIPCION")
	private String gastoDescripcion;

	@Column(name="GASTO_IVA")
	private int gastoIva;

	@Column(name="GASTO_TOTAL")
	private Float gastoTotal;

	@Column(name="GASTO_TOTAL_IVA")
	private Float gastoTotalIva;

	//bi-directional many-to-one association to FacturaConcepto
	@ManyToOne
	@JoinColumn(name="ID_CONCEPTO", referencedColumnName="ID_CONCEPTO")
	private FacturaConcepto facturaConcepto;

	public FacturaGasto() {
	}

	public long getIdGasto() {
		return this.idGasto;
	}

	public void setIdGasto(long idGasto) {
		this.idGasto = idGasto;
	}

	public String getGastoCheck() {
		return this.gastoCheck;
	}

	public void setGastoCheck(String gastoCheck) {
		this.gastoCheck = gastoCheck;
	}

	public String getGastoDescripcion() {
		return this.gastoDescripcion;
	}

	public void setGastoDescripcion(String gastoDescripcion) {
		this.gastoDescripcion = gastoDescripcion;
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

	public Float getGastoTotal() {
		return gastoTotal;
	}

	public void setGastoTotal(Float gastoTotal) {
		this.gastoTotal = gastoTotal;
	}

	public Float getGastoTotalIva() {
		return gastoTotalIva;
	}

	public void setGastoTotalIva(Float gastoTotalIva) {
		this.gastoTotalIva = gastoTotalIva;
	}

	public Float getGasto() {
		return gasto;
	}

	public void setGasto(Float gasto) {
		this.gasto = gasto;
	}

	public void setGastoIva(int gastoIva) {
		this.gastoIva = gastoIva;
	}

	public int getGastoIva() {
		return gastoIva;
	}

}