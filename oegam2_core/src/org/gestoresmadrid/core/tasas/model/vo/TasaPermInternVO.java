package org.gestoresmadrid.core.tasas.model.vo;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;

import org.hibernate.annotations.FetchMode;


@Entity
@SecondaryTable(name="TASA_PERMISO_INT")
@org.hibernate.annotations.Table(appliesTo = "TASA_PERMISO_INT", fetch = FetchMode.SELECT)
@DiscriminatorValue("ALMACEN_PERMISO_INT")
public class TasaPermInternVO extends TasaVO{

	private static final long serialVersionUID = -1005021733642598421L;
	
	@Column(table="TASA_PERMISO_INT",name = "TIPO_TRAMITE")
	private String tipoTramite;

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
}
