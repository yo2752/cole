package org.gestoresmadrid.oegamComun.acceso.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface ServicioUrl extends Serializable{

	List<String> getListaUrlsSecuralized();

	List<String> getListaPatronUrlsContrato(BigDecimal idContrato, BigDecimal idUsuario);

	List<String> getListaPatronUrlsAplicacion(String codigoAplicacion);

}
