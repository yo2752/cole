package trafico.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class TipoReduccionBean implements Serializable {

	private static final long serialVersionUID = 6741342921680468681L;

	private String idTipoReduccion;
	private String descripcion;
	private BigDecimal porcentaje;

	public TipoReduccionBean(String idTipoReduccion, String descripcion, BigDecimal porcentaje) {
		this.idTipoReduccion = idTipoReduccion;
		this.descripcion = descripcion;
		this.porcentaje = porcentaje;
	}

	public TipoReduccionBean() {}

	public String getIdTipoReduccion() {
		return idTipoReduccion;
	}

	public void setIdTipoReduccion(String idTipoReduccion) {
		this.idTipoReduccion = idTipoReduccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

}