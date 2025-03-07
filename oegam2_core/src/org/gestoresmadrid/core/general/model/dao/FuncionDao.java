package org.gestoresmadrid.core.general.model.dao;

import java.util.List;

import org.gestoresmadrid.core.general.model.vo.FuncionVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface FuncionDao extends GenericDao<FuncionVO> {

	List<FuncionVO> getFunciones();

	List<FuncionVO> getFuncionesPorAplicacion(String codigoAplicacion);

	List<FuncionVO> getFuncionesHijos(String codigoAplicacion, String codigoFuncion);

	FuncionVO getFuncion(String codigoAplicacion, String codigoFuncion);

	List<FuncionVO> getFuncionesHijosYPadre(String codigoAplicacion, String codigoFuncion);

	List<FuncionVO> obtenerListadoFuncionesMenuPorAplicacion(String codigoAplicacion);

}
