package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.registradores.model.dao.ConvocatoriaDao;
import org.gestoresmadrid.core.registradores.model.vo.ConvocatoriaVO;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioConvocatoria;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ConvocatoriaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConvocatoriaImpl implements ServicioConvocatoria {

	/**
	 * 
	 */
	private static final long serialVersionUID = 522750753126419466L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioConvocatoriaImpl.class);

	@Autowired
	private ConvocatoriaDao convocatoriaDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public ConvocatoriaDto getConvocatoria(BigDecimal idTramiteRegistro, Long idReunion) {
		try {
			ConvocatoriaVO vo = convocatoriaDao.getConvocatoria(idTramiteRegistro, idReunion);
			return conversor.transform(vo, ConvocatoriaDto.class);
		} catch (Exception e) {
			log.error("Error al recuperar el medio: " + idTramiteRegistro, e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean guardarConvocatoria(ConvocatoriaDto convocatoria, BigDecimal idTramiteRegistro, Long idReunion) {
		ResultBean result = new ResultBean();
		try {
			convocatoria.setIdReunion(idReunion);
			convocatoria.setIdTramiteRegistro(idTramiteRegistro);
			ConvocatoriaVO vo = conversor.transform(convocatoria, ConvocatoriaVO.class);
			convocatoriaDao.guardarOActualizar(vo);
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Error al guardar la convocatoria: " + e.getMessage());
			log.error("Error al guardar la convocatoria", e.getMessage());
		}
		return result;
	}
}
