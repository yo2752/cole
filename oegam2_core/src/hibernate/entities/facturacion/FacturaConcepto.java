package hibernate.entities.facturacion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the FACTURA_CONCEPTO database table.
 */
@Entity
@Table(name="FACTURA_CONCEPTO")
public class FacturaConcepto implements Cloneable,Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "concepto_secuencia", sequenceName = "ID_CONCEPTO_SEC")
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "concepto_secuencia")	
	@Column(name="ID_CONCEPTO", unique=true, nullable=false, precision=22)
	private long idConcepto;

	@Column(name="NUM_EXPEDIENTE", length=20)
	private String numExpediente;

	private String concepto;

	@Column(name="NUM_COLEGIADO",nullable=false)
	private String numColegiado;

	@Column(name="NUM_FACTURA",nullable=false)
	private String numFactura;

	//bi-directional many-to-one association to Factura
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="NUM_COLEGIADO", referencedColumnName="NUM_COLEGIADO",insertable=false, updatable=false),
		@JoinColumn(name="NUM_FACTURA", referencedColumnName="NUM_FACTURA",insertable=false, updatable=false)
	})
	private Factura factura;

	//bi-directional many-to-one association to FacturaGasto
	@OneToMany(mappedBy="facturaConcepto", cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE_ORPHAN})	
	private List<FacturaGasto> facturaGastos;

	//bi-directional many-to-one association to FacturaHonorario
	@OneToMany(mappedBy="facturaConcepto", cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private List<FacturaHonorario> facturaHonorarios;

	//bi-directional many-to-one association to FacturaSuplido
	@OneToMany(mappedBy="facturaConcepto", cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private List<FacturaSuplido> facturaSuplidos;

//	@OneToMany(mappedBy="facturaConcepto", cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
//	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
//	private List<FacturaColegiadoConcepto> FacturaColegiadoConcepto;

	public FacturaConcepto() {
	}
	public long getIdConcepto() {
		return this.idConcepto;
	}
	public void setIdConcepto(long idConcepto) {
		this.idConcepto = idConcepto;
	}
	public String getConcepto() {
		return this.concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Factura getFactura() {
		return this.factura;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getNumFactura() {
		return numFactura;
	}
	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}
	public List<FacturaGasto> getFacturaGastos() {
		return this.facturaGastos;
	}

	public void setFacturaGastos(List<FacturaGasto> facturaGastos) {
		this.facturaGastos = facturaGastos;
	}

	public List<FacturaHonorario> getFacturaHonorarios() {
		return this.facturaHonorarios;
	}

	public void setFacturaHonorarios(List<FacturaHonorario> facturaHonorarios) {
		this.facturaHonorarios = facturaHonorarios;
	}

	public List<FacturaSuplido> getFacturaSuplidos() {
		return this.facturaSuplidos;
	}

	public void setFacturaSuplidos(List<FacturaSuplido> facturaSuplidos) {
		this.facturaSuplidos = facturaSuplidos;
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

}