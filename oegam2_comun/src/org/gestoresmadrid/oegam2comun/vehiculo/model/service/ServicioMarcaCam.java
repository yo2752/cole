package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.vo.MarcaCamVO;

public interface ServicioMarcaCam extends Serializable {

	List<MarcaCamVO> listaMarcaCam(String tipoVehiculo, String codigoMarca, String fechaDesde);
}
