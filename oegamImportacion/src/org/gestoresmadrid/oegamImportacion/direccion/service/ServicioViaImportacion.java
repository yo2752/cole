package org.gestoresmadrid.oegamImportacion.direccion.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.ViaVO;

public interface ServicioViaImportacion extends Serializable {

	ViaVO getVia(String idProvincia, String idMunicipio, String idTipoVia, String via);

	List<String> listadoViasUnicasPorTipoVia(String idProvincia);

}
