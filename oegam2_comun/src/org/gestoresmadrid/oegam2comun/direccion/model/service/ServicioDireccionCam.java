package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.direccion.view.dto.MunicipioCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ProvinciaCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaCamDto;

public interface ServicioDireccionCam extends Serializable{

	String obtenerNombreProvincia(String idProvincia);

	String obtenerNombreMunicipio(String idProvincia, String ididMunicipio);

	ProvinciaCamDto getProvincia(String idProvincia);

	MunicipioCamDto getMunicipio(String idMunicipio, String idProvincia);

	String obtenerNombreTipoVia(String idTipoVia);

	TipoViaCamDto getTipoVia(String idTipoVia);

	String getIdMunicipioCam(String provincia, String municipio);

}
