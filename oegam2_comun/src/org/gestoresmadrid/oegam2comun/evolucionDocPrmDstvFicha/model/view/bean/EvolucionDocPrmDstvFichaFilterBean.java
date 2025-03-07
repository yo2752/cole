package org.gestoresmadrid.oegam2comun.evolucionDocPrmDstvFicha.model.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionDocPrmDstvFichaFilterBean implements Serializable{

	private static final long serialVersionUID = -1960731397056384994L;
	
	@FilterSimpleExpression(name="docID")
	private String docId;
	@FilterSimpleExpression(name="tipoDocumento")
	private String tipoDocumento;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

}
