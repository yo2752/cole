package org.gestoresmadrid.core.tipoInmueble.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TIPO_INMUEBLE")
public class TipoInmuebleVO implements Serializable{

	private static final long serialVersionUID = -7386670224541775288L;

	@EmbeddedId
	private TipoInmueblePK id;
	
	@Column(name="DESC_TIPO_INMUEBLE")
	private String descTipoInmueble;

	public TipoInmueblePK getId() {
		return id;
	}

	public void setId(TipoInmueblePK id) {
		this.id = id;
	}

	public String getDescTipoInmueble() {
		return descTipoInmueble;
	}

	public void setDescTipoInmueble(String descTipoInmueble) {
		this.descTipoInmueble = descTipoInmueble;
	}
	
	
}
