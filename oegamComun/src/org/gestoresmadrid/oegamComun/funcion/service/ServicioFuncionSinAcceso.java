package org.gestoresmadrid.oegamComun.funcion.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioFuncionSinAccesoVO;

public interface ServicioFuncionSinAcceso extends Serializable{

	List<UsuarioFuncionSinAccesoVO> getListaFuncionSinAccesoContrato(Long idContrato);

	List<UsuarioFuncionSinAccesoVO> getListaFuncionSinAccesoPorContratoYUsuario(Long idContrato, Long idUsuario);

}
