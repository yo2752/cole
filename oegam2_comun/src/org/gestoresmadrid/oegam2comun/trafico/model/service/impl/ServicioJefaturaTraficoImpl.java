package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.trafico.model.dao.JefaturaTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioJefaturaTraficoImpl implements ServicioJefaturaTrafico {

	private static final long serialVersionUID = -6818778598675063314L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioJefaturaTraficoImpl.class);
	private static final String ERROR_AL_RECUPERAR_JEFATURA = "Error al recuperar la jefatura";

	@Autowired
	private JefaturaTraficoDao jefaturaTraficoDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public JefaturaTraficoDto getJefatura(String jefatura) {
		try {
			JefaturaTraficoVO jefaturaVO = jefaturaTraficoDao.getJefatura(jefatura);
			if (jefaturaVO != null) {
				return (JefaturaTraficoDto) conversor.transform(jefaturaVO, JefaturaTraficoDto.class);
			}
		} catch (Exception e) {
			log.error(ERROR_AL_RECUPERAR_JEFATURA, e);
		}
		return null;
	}

	@Override
	@Transactional
	public JefaturaTraficoVO getJefaturaTrafico(String jefaturaProvincial) {
		try {
			JefaturaTraficoVO jefaturaVO = jefaturaTraficoDao.getJefatura(jefaturaProvincial);
			if (jefaturaVO != null) {
				return jefaturaVO;
			}
		} catch (Exception e) {
			log.error(ERROR_AL_RECUPERAR_JEFATURA, e);
		}
		return null;
	}

	@Override
	@Transactional
	public JefaturaTraficoDto getJefaturaPorDescripcion(String descripcion) {
		try {
			JefaturaTraficoVO jefaturaVO = jefaturaTraficoDao.getJefaturaPorDescripcion(descripcion);
			if (jefaturaVO != null) {
				return (JefaturaTraficoDto) conversor.transform(jefaturaVO, JefaturaTraficoDto.class);
			}
		} catch (Exception e) {
			log.error(ERROR_AL_RECUPERAR_JEFATURA, e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<JefaturaTraficoDto> listadoJefaturas(String idProvincia) {
		try {
			return conversor.transform(jefaturaTraficoDao.listadoJefaturas(idProvincia), JefaturaTraficoDto.class);
		} catch (Exception e) {
			log.error("Error al recuperar el listado jefaturas", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<JefaturaTraficoVO> getJefaturasPorContrato(Long idContrato) {
		return jefaturaTraficoDao.getJefaturasPorContrato(idContrato);
	}
}