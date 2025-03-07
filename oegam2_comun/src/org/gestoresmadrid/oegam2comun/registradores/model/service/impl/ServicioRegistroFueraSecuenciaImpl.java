package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.registradores.model.dao.RegistroFueraSecuenciaDao;
import org.gestoresmadrid.core.registradores.model.vo.RegistroFueraSecuenciaVO;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistroFueraSecuencia;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroFueraSecuenciaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioRegistroFueraSecuenciaImpl implements ServicioRegistroFueraSecuencia {

	private static final long serialVersionUID = -75425248396748674L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioRegistroFueraSecuenciaImpl.class);

	@Autowired
	private RegistroFueraSecuenciaDao registroFueraSecuenciaDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public List<RegistroFueraSecuenciaDto> getRegistrosFueraSecuencia(BigDecimal idTramiteRegistro) {
		List<RegistroFueraSecuenciaDto> result = null;
		try {
			if (idTramiteRegistro != null) {
				List<RegistroFueraSecuenciaVO> list = registroFueraSecuenciaDao.getRegistrosFueraSecuencia(idTramiteRegistro);
				if (list != null && !list.isEmpty()) {
					result = conversor.transform(list, RegistroFueraSecuenciaDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar los registros fuera de secuencia: " + idTramiteRegistro, e, idTramiteRegistro.toString());
		}
		return result;
	}

	@Override
	@Transactional
	public RegistroFueraSecuenciaDto getRegistroFueraSecuencia(Long idRegistroFueraSecuencia) {
		RegistroFueraSecuenciaDto result = null;
		try {
			if (idRegistroFueraSecuencia != null) {
				RegistroFueraSecuenciaVO vo = registroFueraSecuenciaDao.getRegistroFueraSecuencia(idRegistroFueraSecuencia);
				if (vo != null) {
					result = conversor.transform(vo, RegistroFueraSecuenciaDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar los registros fuera de secuencia: " + idRegistroFueraSecuencia, e);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarRegistroFueraSecuencia(RegistroFueraSecuenciaDto registroFueraSecuencia, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		try {
			if (registroFueraSecuencia.getIdRegistroFueraSecuencia() != null) {
				registroFueraSecuencia.setIdUsuario(idUsuario);
				registroFueraSecuencia.setFechaEnvio(utilesFecha.getFechaHoraActualLEG());
			} else {
				registroFueraSecuencia.setFechaAlta(utilesFecha.getFechaHoraActualLEG());
			}
			RegistroFueraSecuenciaVO registroFueraSecuenciaVO = conversor.transform(registroFueraSecuencia, RegistroFueraSecuenciaVO.class);
			registroFueraSecuenciaDao.guardarOActualizar(registroFueraSecuenciaVO);
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			log.error("Error al guardar el registro fuera de secuencia", e.getMessage());
			result.setMensaje("Error al guardar el registro fuera de secuencia" + e.getMessage());
		}
		return result;
	}
}
