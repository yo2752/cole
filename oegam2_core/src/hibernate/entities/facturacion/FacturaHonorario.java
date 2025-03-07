package hibernate.entities.facturacion;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;

/**
 * The persistent class for the FACTURA_HONORARIO database table.
 * 
 */
@Entity
@Table(name="FACTURA_HONORARIO")
public class FacturaHonorario implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "honorario_secuencia", sequenceName = "ID_HONORARIO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "honorario_secuencia")
	@Column(name="ID_HONORARIO", unique=true, nullable=false, precision=22)
	private long idHonorario;

	@Column(length=100)
	private Float honorario;

	@Column(name="HONORARIO_CHECK_DESCUENTO", length=1)
	private String honorarioCheckDescuento;

	@Column(name="HONORARIO_CHECK_IRPF", precision=1)
	private BigDecimal honorarioCheckIrpf;

	@Column(name="HONORARIO_CHECK_IVA", precision=1)
	private BigDecimal honorarioCheckIva;

	@Column(name="HONORARIO_DESCRIPCION", length=255)
	private String honorarioDescripcion;

	@Column(name="HONORARIO_DESCUENTO", length=10)
	private Float honorarioDescuento;

	@Column(name="HONORARIO_IRPF", length=2)
	private int honorarioIrpf;

	@Column(name="HONORARIO_IVA", length=2)
	private int honorarioIva;

	@Column(name="HONORARIO_TOTAL", length=10)
	private Float honorarioTotal;

	@Column(name="HONORARIO_TOTAL_IRPF", length=10)
	private Float honorarioTotalIrpf;

	@Column(name="HONORARIO_TOTAL_IVA", length=10)
	private Float honorarioTotalIva;

	// bi-directional many-to-one association to FacturaConcepto
	@ManyToOne
	@JoinColumn(name = "ID_CONCEPTO", referencedColumnName = "ID_CONCEPTO")
	private FacturaConcepto facturaConcepto;

	public FacturaHonorario() {
	}

	public long getIdHonorario() {
		return this.idHonorario;
	}

	public void setIdHonorario(long idHonorario) {
		this.idHonorario = idHonorario;
	}

	public String getHonorarioCheckDescuento() {
		return this.honorarioCheckDescuento;
	}

	public void setHonorarioCheckDescuento(String honorarioCheckDescuento) {
		this.honorarioCheckDescuento = honorarioCheckDescuento;
	}

	public BigDecimal getHonorarioCheckIrpf() {
		return this.honorarioCheckIrpf;
	}

	public void setHonorarioCheckIrpf(BigDecimal honorarioCheckIrpf) {
		this.honorarioCheckIrpf = honorarioCheckIrpf;
	}

	public BigDecimal getHonorarioCheckIva() {
		return this.honorarioCheckIva;
	}

	public void setHonorarioCheckIva(BigDecimal honorarioCheckIva) {
		this.honorarioCheckIva = honorarioCheckIva;
	}

	public String getHonorarioDescripcion() {
		return this.honorarioDescripcion;
	}

	public void setHonorarioDescripcion(String honorarioDescripcion) {
		this.honorarioDescripcion = honorarioDescripcion;
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

	public void setHonorario(Float honorario) {
		this.honorario = honorario;
	}

	public Float getHonorario() {
		return honorario;
	}

	public Float getHonorarioDescuento() {
		return honorarioDescuento;
	}

	public void setHonorarioDescuento(Float honorarioDescuento) {
		this.honorarioDescuento = honorarioDescuento;
	}

	public Float getHonorarioTotal() {
		return honorarioTotal;
	}

	public void setHonorarioTotal(Float honorarioTotal) {
		this.honorarioTotal = honorarioTotal;
	}

	public Float getHonorarioTotalIrpf() {
		return honorarioTotalIrpf;
	}

	public void setHonorarioTotalIrpf(Float honorarioTotalIrpf) {
		this.honorarioTotalIrpf = honorarioTotalIrpf;
	}

	public Float getHonorarioTotalIva() {
		return honorarioTotalIva;
	}

	public void setHonorarioTotalIva(Float honorarioTotalIva) {
		this.honorarioTotalIva = honorarioTotalIva;
	}

	public int getHonorarioIrpf() {
		return honorarioIrpf;
	}

	public void setHonorarioIrpf(int honorarioIrpf) {
		this.honorarioIrpf = honorarioIrpf;
	}

	public void setHonorarioIva(int honorarioIva) {
		this.honorarioIva = honorarioIva;
	}

	public int getHonorarioIva() {
		return honorarioIva;
	}

}