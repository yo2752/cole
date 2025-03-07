package hibernate.entities.trafico;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TIPO_REDUCCION database table.
 * 
 */
@Entity
@Table(name="TIPO_REDUCCION")
public class TipoReduccionBean implements Serializable {

	private static final long serialVersionUID = 4713198005326785441L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TIPO_REDUCCION")
	private String tipoReduccion;

	@Column(name="DESC_TIPO_REDUCCION")
	private String descripcion;

	@Column(name="PORCENTAJE_REDUCCION")
	private BigDecimal porcentajeReduccion;

	public TipoReduccionBean() {
	}

	public String getTipoReduccion() {
		return this.tipoReduccion;
	}

	public void setTipoReduccion(String tipoReduccion) {
		this.tipoReduccion = tipoReduccion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPorcentajeReduccion() {
		return porcentajeReduccion;
	}

	public void setPorcentajeReduccion(BigDecimal porcentajeReduccion) {
		this.porcentajeReduccion = porcentajeReduccion;
	}

}