package org.gestoresmadrid.oegamSanciones.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.sancion.model.vo.SancionVO;

public interface ServicioPersistenciaSanciones extends Serializable {

	public List<SancionVO> obtenerListadoPorMotivo(SancionVO sancion) throws Throwable;

	public SancionVO getSancionPorId(Integer idSancion);

	public SancionVO guardarOActualizar(SancionVO sancionVO);

}
