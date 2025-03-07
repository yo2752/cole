package hibernate.entities.facturacion;

import java.io.Serializable;

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

/**
 * The persistent class for the FACTURA_PROV_FONDO database table.
 * 
 */
@Entity
@Table(name="FACTURA_PROV_FONDO")
public class FacturaProvFondo implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "provison_secuencia", sequenceName = "ID_PROVISION_FONDO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "provison_secuencia")
	@Column(name="ID_FONDO", unique=true, nullable=false, precision=22)	
	private long idFondo;

	private float fondo;

	@Column(name="FONDO_DESCRIPCION")
	private String fondoDescripcion;

	@Column(name="FONDO_TOTAL")
	private float fondoTotal;

	//bi-directional many-to-one association to Factura
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="NUM_COLEGIADO", referencedColumnName="NUM_COLEGIADO"),
		@JoinColumn(name="NUM_FACTURA", referencedColumnName="NUM_FACTURA")
	})
	private Factura factura;

	public FacturaProvFondo() {
	}

	public long getIdFondo() {
		return this.idFondo;
	}

	public void setIdFondo(long idFondo) {
		this.idFondo = idFondo;
	}

	public String getFondoDescripcion() {
		return this.fondoDescripcion;
	}

	public void setFondoDescripcion(String fondoDescripcion) {
		this.fondoDescripcion = fondoDescripcion;
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

	public float getFondo() {
		return fondo;
	}

	public void setFondo(float fondo) {
		this.fondo = fondo;
	}

	public float getFondoTotal() {
		return fondoTotal;
	}

	public void setFondoTotal(float fondoTotal) {
		this.fondoTotal = fondoTotal;
	}

}