package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.registradores.model.dao.MedioConvocatoriaDao;
import org.gestoresmadrid.core.registradores.model.vo.MedioConvocatoriaVO;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioMedio;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioMedioConvocatoria;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.MedioConvocatoriaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.beans.ResultBean;

@Service
public class ServicioMedioConvocatoriaImpl implements ServicioMedioConvocatoria {

	private static final long serialVersionUID = 3030197383473572816L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioMedioConvocatoriaImpl.class);

	@Autowired
	private MedioConvocatoriaDao medioConvocatoriaDao;

	@Autowired
	private ServicioMedio servicioMedio;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public MedioConvocatoriaVO getMediosConvocatoria(Long idMedio, BigDecimal idTramiteRegistro, Long idReunion) {
		try {
			return medioConvocatoriaDao.getMediosConvocatoria(idMedio, idTramiteRegistro, idReunion);
		} catch (Exception e) {
			log.error("Error al recuperar el medio: " + idTramiteRegistro, e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<MedioConvocatoriaDto> getMediosConvocatorias(BigDecimal idTramiteRegistro) {
		List<MedioConvocatoriaDto> result = null;
		try {
			if (idTramiteRegistro != null) {
				List<MedioConvocatoriaVO> list = medioConvocatoriaDao.getMediosConvocatorias(idTramiteRegistro);
				if (list != null && !list.isEmpty()) {
					result = conversor.transform(list, MedioConvocatoriaDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar los medios: " + idTramiteRegistro, e);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarMedioConvocatoria(MedioConvocatoriaDto medioConvocatoriaDto, BigDecimal idTramiteRegistro, Long idReunion) {
		ResultBean result = new ResultBean();
		try {
			medioConvocatoriaDto.setIdReunion(idReunion);
			medioConvocatoriaDto.setIdTramiteRegistro(idTramiteRegistro);
			if (medioConvocatoriaDto.getMedio().getIdMedio() != null) {
				medioConvocatoriaDto.setIdMedio(medioConvocatoriaDto.getMedio().getIdMedio());
			} else {
				result = servicioMedio.guardarMedio(medioConvocatoriaDto.getMedio());
				medioConvocatoriaDto.setIdMedio((Long) result.getAttachment(ServicioMedio.ID_MEDIO));
			}
			MedioConvocatoriaVO medioConvocatoriaVO = conversor.transform(medioConvocatoriaDto, MedioConvocatoriaVO.class);
			medioConvocatoriaDao.guardarOActualizar(medioConvocatoriaVO);
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Error al guardar el medio: " + e.getMessage());
			log.error("Error al guardar el medio", e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional
	public void eliminarMedio(Long idMedio, BigDecimal idTramiteRegistro, Long idReunion) {
		MedioConvocatoriaVO vo = getMediosConvocatoria(idMedio, idTramiteRegistro, idReunion);
		if (vo != null) {
			medioConvocatoriaDao.borrar(vo);
		}
	}
}
