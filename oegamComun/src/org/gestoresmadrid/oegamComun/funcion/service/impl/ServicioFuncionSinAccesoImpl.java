package org.gestoresmadrid.oegamComun.funcion.service.impl;

import java.util.List;

import org.gestoresmadrid.core.general.model.dao.UsuarioFuncionSinAccesoDao;
import org.gestoresmadrid.core.general.model.vo.UsuarioFuncionSinAccesoVO;
import org.gestoresmadrid.oegamComun.funcion.service.ServicioFuncionSinAcceso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioFuncionSinAccesoImpl implements ServicioFuncionSinAcceso{

	private static final long serialVersionUID = 3748614927648806680L;
	
	@Autowired
	UsuarioFuncionSinAccesoDao usuarioFuncionSinAccesoDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<UsuarioFuncionSinAccesoVO> getListaFuncionSinAccesoContrato(Long idContrato) {
		return usuarioFuncionSinAccesoDao.getListaFuncionSinAccesoContrato(idContrato);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<UsuarioFuncionSinAccesoVO> getListaFuncionSinAccesoPorContratoYUsuario(Long idContrato, Long idUsuario) {
		return usuarioFuncionSinAccesoDao.getListaFuncionSinAccesoPorContratoYUsuario(idContrato, idUsuario);
	}

}
