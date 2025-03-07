package org.gestoresmadrid.oegam2comun.modelos.view.dto;

import java.io.Serializable;
import java.util.List;

public class TipoDocumentoEscrDto implements Serializable{

	private static final long serialVersionUID = 3386607878362033749L;
	private String tipoDocumento;
	private String descTipoDocEscr;
	private List<ModeloDto> modelo;
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getDescTipoDocEscr() {
		return descTipoDocEscr;
	}
	public void setDescTipoDocEscr(String descTipoDocEscr) {
		this.descTipoDocEscr = descTipoDocEscr;
	}
	public List<ModeloDto> getModelo() {
		return modelo;
	}
	public void setModelo(List<ModeloDto> modelo) {
		this.modelo = modelo;
	}
	
	
}
