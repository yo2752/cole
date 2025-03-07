package hibernate.entities.facturacion;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the FACTURA_OTRO database table.
 * 
 */
@Entity
@Table(name="FACTURA_OTRO")
public class FacturaOtro implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "otro_secuencia", sequenceName = "ID_OTRO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "otro_secuencia")
	@Column(name="ID_OTRO", unique=true, nullable=false, precision=22)
	private long idOtro;

	private float otro;

	@Column(name="OTRO_CHECK_DESCUENTO")
	private BigDecimal otroCheckDescuento;

	@Column(name="OTRO_CHECK_IVA")
	private BigDecimal otroCheckIva;

	@Column(name="OTRO_DESCRIPCION")
	private String otroDescripcion;

	@Column(name="OTRO_DESCUENTO")
	private float otroDescuento;

	@Column(name="OTRO_IVA")
	private int otroIva;

	@Column(name="OTRO_TOTAL")
	private float otroTotal;

	@Column(name="OTRO_TOTAL_IVA")
	private float otroTotalIva;

	//bi-directional many-to-one association to Factura
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="NUM_COLEGIADO", referencedColumnName="NUM_COLEGIADO"),
		@JoinColumn(name="NUM_FACTURA", referencedColumnName="NUM_FACTURA")
	})
	private Factura factura;

	@Transient
	private String OtroCheckIvaHidden;
	@Transient
	private String OtroCheckDescuentoHidden;

	public FacturaOtro() {
	}

	public long getIdOtro() {
		return this.idOtro;
	}

	public void setIdOtro(long idOtro) {
		this.idOtro = idOtro;
	}

	public BigDecimal getOtroCheckDescuento() {
		return this.otroCheckDescuento;
	}

	public void setOtroCheckDescuento(BigDecimal otroCheckDescuento) {
		this.otroCheckDescuento = otroCheckDescuento;
	}

	public BigDecimal getOtroCheckIva() {
		return this.otroCheckIva;
	}

	public void setOtroCheckIva(BigDecimal otroCheckIva) {
		this.otroCheckIva = otroCheckIva;
	}

	public String getOtroDescripcion() {
		return this.otroDescripcion;
	}

	public void setOtroDescripcion(String otroDescripcion) {
		this.otroDescripcion = otroDescripcion;
	}

	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
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

	public float getOtro() {
		return otro;
	}

	public void setOtro(float otro) {
		this.otro = otro;
	}

	public float getOtroDescuento() {
		return otroDescuento;
	}

	public void setOtroDescuento(float otroDescuento) {
		this.otroDescuento = otroDescuento;
	}

	public float getOtroTotal() {
		return otroTotal;
	}

	public void setOtroTotal(float otroTotal) {
		this.otroTotal = otroTotal;
	}

	public float getOtroTotalIva() {
		return otroTotalIva;
	}

	public void setOtroTotalIva(float otroTotalIva) {
		this.otroTotalIva = otroTotalIva;
	}

	public int getOtroIva() {
		return otroIva;
	}

	public void setOtroIva(int otroIva) {
		this.otroIva = otroIva;
	}

	@Transient
	public String getOtroCheckIvaHidden() {
		return OtroCheckIvaHidden;
	}

	@Transient
	public void setOtroCheckIvaHidden(String otroCheckIvaHidden) {
		OtroCheckIvaHidden = otroCheckIvaHidden;
	}

	@Transient
	public String getOtroCheckDescuentoHidden() {
		return OtroCheckDescuentoHidden;
	}

	@Transient
	public void setOtroCheckDescuentoHidden(String otroCheckDescuentoHidden) {
		OtroCheckDescuentoHidden = otroCheckDescuentoHidden;
	}

}