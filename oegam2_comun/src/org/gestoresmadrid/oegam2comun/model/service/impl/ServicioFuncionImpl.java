package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.general.model.dao.FuncionDao;
import org.gestoresmadrid.core.general.model.vo.FuncionVO;
import org.gestoresmadrid.oegam2comun.model.service.ServicioFuncion;
import org.gestoresmadrid.oegamComun.accesos.view.dto.FuncionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioFuncionImpl implements ServicioFuncion {

	private static final long serialVersionUID = -1700758494739829445L;

	@Autowired
	private FuncionDao funcionDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional(readOnly = true)
	public FuncionVO getFuncion(String codigoAplicacion, String codigoFuncion) {
		return funcionDao.getFuncion(codigoAplicacion, codigoFuncion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FuncionDto> getFuncionesPorAplicacion(String codigoAplicacion) {
		List<FuncionVO> funciones = funcionDao.getFuncionesPorAplicacion(codigoAplicacion);
		if (funciones != null && !funciones.isEmpty()) {
			return conversor.transform(funciones, FuncionDto.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<FuncionVO> getFuncionesHijos(String codigoAplicacion, String codigoFuncion) {
		List<FuncionVO> funciones = funcionDao.getFuncionesHijos(codigoAplicacion, codigoFuncion);
		if (funciones != null && !funciones.isEmpty()) {
			return funciones;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<FuncionVO> getFuncionesHijosYPadre(String codigoAplicacion, String codigoFuncion) {
		List<FuncionVO> funciones = funcionDao.getFuncionesHijosYPadre(codigoAplicacion, codigoFuncion);
		if (funciones != null && !funciones.isEmpty()) {
			return funciones;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<FuncionDto> getFunciones() {
		List<FuncionVO> funciones = funcionDao.getFunciones();
		if (funciones != null && !funciones.isEmpty()) {
			return conversor.transform(funciones, FuncionDto.class);
		}
		return null;
	}
}
