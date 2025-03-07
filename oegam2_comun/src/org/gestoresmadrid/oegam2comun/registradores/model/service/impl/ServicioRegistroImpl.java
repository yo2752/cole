package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.registradores.model.dao.RegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.RegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioRegistroImpl implements ServicioRegistro {

	private static final long serialVersionUID = -46514940603275728L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioRegistroImpl.class);

	@Autowired
	private RegistroDao registroDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public RegistroDto getRegistroPorId(long idRegistro) {
		try {
			RegistroVO registroVO = registroDao.getRegistroPorId(idRegistro);
			if (registroVO != null) {
				return conversor.transform(registroVO, RegistroDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el registro", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<RegistroDto> getRegistro(String idProvincia, String tipo) {
		try {
			List<RegistroVO> listaVos = registroDao.getRegistro(idProvincia, tipo);
			if (listaVos != null && !listaVos.isEmpty()) {
				return conversor.transform(listaVos, RegistroDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el registro", e);
		}
		return null;
	}
	
	@Override
	@Transactional
	public RegistroDto getRegistroPorNombre(String nombre, String tipo) {
		try {
			RegistroVO registroVO = registroDao.getRegistroPorNombre(nombre, tipo);
			if (registroVO != null) {
				return conversor.transform(registroVO, RegistroDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el registro", e);
		}
		return null;
	}

	@Override
	@Transactional
	public Long getIdRegistro(String idRegistro, String idProvincia,String tipo) {
		try {
			return registroDao.getIdRegistro(idRegistro, idProvincia, tipo);
		} catch (Exception e) {
			log.error("Error al recuperar el registro", e);
		}
		return null;
	}

}
