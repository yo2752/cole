package org.gestoresmadrid.core.codigoIae.model.vo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FACTURA_IRPF database table.
 * 
 */
@Entity
@Table(name="CODIGO_IAE")
public class CodigoIAEVO implements Serializable {

	private static final long serialVersionUID = -1990004293685898822L;

	@Id
	@Column(name="CODIGO_IAE")
	private String codigoIAE;

	private String descripcion;

    public CodigoIAEVO() {
    }

	public String getCodigoIAE() {
		return codigoIAE;
	}

	public void setCodigoIAE(String codigoIAE) {
		this.codigoIAE = codigoIAE;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}