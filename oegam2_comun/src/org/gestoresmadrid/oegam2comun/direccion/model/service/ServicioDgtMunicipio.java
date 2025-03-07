package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.direccion.model.vo.DgtMunicipioVO;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.DgtMunicipioDto;

public interface ServicioDgtMunicipio extends Serializable {

	DgtMunicipioVO getDgtMunicipio(String idProvincia, String municipio);

	DgtMunicipioVO getDgtMunicipioPorCodigoIne(String idProvincia, String codigoIne);

	DgtMunicipioDto getDgtMunicipioPorCodigoIneDto(String idProvincia, String codigoIne);
}
