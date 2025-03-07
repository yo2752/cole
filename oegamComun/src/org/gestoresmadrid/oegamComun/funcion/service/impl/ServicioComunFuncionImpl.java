package org.gestoresmadrid.oegamComun.funcion.service.impl;

import java.util.List;

import org.gestoresmadrid.core.general.model.dao.ContratoAplicacionDao;
import org.gestoresmadrid.core.general.model.dao.FuncionDao;
import org.gestoresmadrid.core.general.model.vo.ContratoAplicacionVO;
import org.gestoresmadrid.core.general.model.vo.FuncionVO;
import org.gestoresmadrid.oegamComun.funcion.service.ServicioComunFuncion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioComunFuncionImpl implements ServicioComunFuncion {

	private static final long serialVersionUID = -3534206772504958537L;

	@Autowired
	ContratoAplicacionDao contratoAplicacionDao;

	@Autowired
	FuncionDao funcionDao;

	@Override
	@Transactional(readOnly = true)
	public List<ContratoAplicacionVO> obtenerListadoAplicacionesContrato(Long idContrato) {
		return contratoAplicacionDao.getAplicacionesPorContrato(idContrato);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FuncionVO> obtenerListadoFuncionesMenuPorAplicacion(String codigoAplicacion) {
		return funcionDao.obtenerListadoFuncionesMenuPorAplicacion(codigoAplicacion);
	}
}
