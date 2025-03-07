package hibernate.entities.facturacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hibernate.entities.personas.Persona;

/**
 * The persistent class for the FACTURA database table.
 * 
 */
@Entity
@Table(name="FACTURA")
public class Factura implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacturaPK id;

	@Column(name="CHECK_PDF", length=1)
	private String checkPdf;

	@Column(name="FAC_ANULADA", length=30)
	private String facAnulada;

	@Temporal( TemporalType.DATE)
	@Column(name="FECHA_FACTURA")
	private Date fechaFactura;

	@Temporal( TemporalType.DATE)
	@Column(name="FECHA_ALTA")
	private Date fechaAlta;

	@Column(name="IMPORTE_TOTAL", length=20)
	private float importeTotal;

	@Column(name="NIF_EMISOR", length=9)
	private String nifEmisor;

	@Column(name="NUM_CODIGO", length=5)
	private String numCodigo;

	@Column(name="NUM_EXPEDIENTE", length=20)
	private String numExpediente;

	private BigDecimal numeracion;

	@Column(name="NUM_SERIE", length=5)
	private String numSerie;

	@Column(name="NIF",nullable=false)
	private String nif;

	@Column(precision=1)
	private BigDecimal visible;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="NIF", referencedColumnName="NIF",insertable=false, updatable=false),
		@JoinColumn(name="NUM_COLEGIADO", referencedColumnName="NUM_COLEGIADO",insertable=false, updatable=false)
	})
	private Persona persona;

	//bi-directional many-to-one association to FacturaConcepto
	@OneToMany(mappedBy="factura", cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private List<FacturaConcepto> facturaConceptos;

	@OneToOne(mappedBy="factura", cascade={CascadeType.PERSIST})
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private FacturaOtro facturaOtro;

	//bi-directional ono-to-one association to FacturaProvFondo
	@OneToOne(mappedBy="factura", cascade={CascadeType.PERSIST})
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private FacturaProvFondo facturaProvFondo;

	@Column(name="EMISOR", length=10)
	private String emisor;

	public Factura() {
	}

	public FacturaPK getId() {
		return this.id;
	}

	public void setId(FacturaPK id) {
		this.id = id;
	}
	
	public String getCheckPdf() {
		return this.checkPdf;
	}

	public void setCheckPdf(String checkPdf) {
		this.checkPdf = checkPdf;
	}

	public String getFacAnulada() {
		return this.facAnulada;
	}

	public void setFacAnulada(String facAnulada) {
		this.facAnulada = facAnulada;
	}

	public Date getFechaFactura() {
		return this.fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getNifEmisor() {
		return this.nifEmisor;
	}

	public void setNifEmisor(String nifEmisor) {
		this.nifEmisor = nifEmisor;
	}

	public String getNumCodigo() {
		return this.numCodigo;
	}

	public void setNumCodigo(String numCodigo) {
		this.numCodigo = numCodigo;
	}

	public String getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getNumeracion() {
		return this.numeracion;
	}

	public void setNumeracion(BigDecimal numeracion) {
		this.numeracion = numeracion;
	}

	public String getNumSerie() {
		return this.numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public BigDecimal getVisible() {
		return this.visible;
	}

	public void setVisible(BigDecimal visible) {
		this.visible = visible;
	}

	public List<FacturaConcepto> getFacturaConceptos() {
		return this.facturaConceptos;
	}

	public void setFacturaConceptos(List<FacturaConcepto> facturaConceptos) {
		this.facturaConceptos = facturaConceptos;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public FacturaOtro getFacturaOtro() {
		return facturaOtro;
	}

	public void setFacturaOtro(FacturaOtro facturaOtro) {
		this.facturaOtro = facturaOtro;
	}

	public FacturaProvFondo getFacturaProvFondo() {
		return facturaProvFondo;
	}

	public void setFacturaProvFondo(FacturaProvFondo facturaProvFondo) {
		this.facturaProvFondo = facturaProvFondo;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
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

	public void setImporteTotal(float importeTotal) {
		this.importeTotal = importeTotal;
	}

	public float getImporteTotal() {
		return importeTotal;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

}