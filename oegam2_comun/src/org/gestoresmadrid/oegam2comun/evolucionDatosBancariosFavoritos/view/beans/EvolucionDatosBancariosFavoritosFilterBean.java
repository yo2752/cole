package org.gestoresmadrid.oegam2comun.evolucionDatosBancariosFavoritos.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionDatosBancariosFavoritosFilterBean implements Serializable{

	private static final long serialVersionUID = 8890518628880994183L;
	
	@FilterSimpleExpression(name="id.idDatosBancarios")
	private Long idDatoBancario;

	public Long getIdDatoBancario() {
		return idDatoBancario;
	}

	public void setIdDatoBancario(Long idDatoBancario) {
		this.idDatoBancario = idDatoBancario;
	}
	
}
