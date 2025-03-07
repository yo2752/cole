package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.util.List;

public class LcInfoEdificioAltaDto implements Serializable {

	private static final long serialVersionUID = 620132663144019311L;

	private Long idInfoEdificioAlta;

	private Long idLcDirEdificacionAlta;

	private LcDireccionDto lcDirEdificacionAlta;

	private List<LcDatosPortalAltaDto> lcDatosPortalesAlta;

	private Long idEdificacion;

	// Objetos de pantalla
	private LcDatosPortalAltaDto lcDatosPortalAlta;

	public LcInfoEdificioAltaDto() {}

	public Long getIdInfoEdificioAlta() {
		return idInfoEdificioAlta;
	}

	public void setIdInfoEdificioAlta(Long idInfoEdificioAlta) {
		this.idInfoEdificioAlta = idInfoEdificioAlta;
	}

	public Long getIdLcDirEdificacionAlta() {
		return idLcDirEdificacionAlta;
	}

	public void setIdLcDirEdificacionAlta(Long idLcDirEdificacionAlta) {
		this.idLcDirEdificacionAlta = idLcDirEdificacionAlta;
	}

	public LcDireccionDto getLcDirEdificacionAlta() {
		return lcDirEdificacionAlta;
	}

	public void setLcDirEdificacionAlta(LcDireccionDto lcDirEdificacionAlta) {
		this.lcDirEdificacionAlta = lcDirEdificacionAlta;
	}

	public List<LcDatosPortalAltaDto> getLcDatosPortalesAlta() {
		return lcDatosPortalesAlta;
	}

	public void setLcDatosPortalesAlta(List<LcDatosPortalAltaDto> lcDatosPortalesAlta) {
		this.lcDatosPortalesAlta = lcDatosPortalesAlta;
	}

	public Long getIdEdificacion() {
		return idEdificacion;
	}

	public void setIdEdificacion(Long idEdificacion) {
		this.idEdificacion = idEdificacion;
	}

	public LcDatosPortalAltaDto getLcDatosPortalAlta() {
		return lcDatosPortalAlta;
	}

	public void setLcDatosPortalAlta(LcDatosPortalAltaDto lcDatosPortalAlta) {
		this.lcDatosPortalAlta = lcDatosPortalAlta;
	}

}