package org.gestoresmadrid.oegamImportacion.direccion.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.direccion.model.vo.DgtMunicipioVO;

public interface ServicioDgtMunicipioImportacion extends Serializable {

	DgtMunicipioVO getDgtMunicipio(String idProvincia, String municipio);

	DgtMunicipioVO getDgtMunicipioPorCodigoIne(String idProvincia, String codigoIne);

	DgtMunicipioVO getDgtMunicipioPorIdDgt(String idProvincia, BigDecimal idDgtMunicipio);
}
