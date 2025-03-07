package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioFuncionSinAccesoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface UsuarioFuncionSinAccesoDao extends GenericDao<UsuarioFuncionSinAccesoVO>, Serializable {

	List<UsuarioFuncionSinAccesoVO> compruebaAcceso(String codigoFuncion);

	List<UsuarioFuncionSinAccesoVO> getUsuarioFuncionSinAcceso(String codigoAplicacion, String codigoFuncion, Long idContrato, Long idUsuario);

	void borrar(String codigoAplicacion, String codigoFuncion, Long idContrato, Long idUsuario);

	List<UsuarioFuncionSinAccesoVO> getListaFuncionSinAccesoContrato(Long idContrato);

	List<UsuarioFuncionSinAccesoVO> getListaFuncionSinAccesoPorContratoYUsuario(Long idContrato, Long idUsuario);
}
