package org.gestoresmadrid.core.tasas.model.vo;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;

import org.hibernate.annotations.FetchMode;


@Entity
@SecondaryTable(name="TASA_MATW")
@org.hibernate.annotations.Table(appliesTo = "TASA_MATW", fetch = FetchMode.SELECT)
@DiscriminatorValue("ALMACEN_MATW")
public class TasaMatwVO extends TasaVO{

	private static final long serialVersionUID = -1005021733642598421L;
	
	@Column(table="TASA_MATW",name = "TIPO_TRAMITE")
	private String tipoTramite;

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
}
