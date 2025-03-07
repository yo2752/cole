package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.ServicioTraficoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.ServicioTraficoVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioServicioTrafico;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.ServicioTraficoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioServicioTraficoImpl implements ServicioServicioTrafico {

	private static final long serialVersionUID = 5374026719482719164L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioServicioTraficoImpl.class);

	@Autowired
	private ServicioTraficoDao servicioTraficoDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public ServicioTraficoDto getServicioTrafico(String idServicio) {
		try {
			ServicioTraficoVO servicio = servicioTraficoDao.getServicioTrafico(idServicio);
			if (servicio != null) {
				return conversor.transform(servicio, ServicioTraficoDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el servicio trafico", e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<ServicioTraficoDto> getServicioTraficoPorTipoTramite(String tipoTramite) {
		try {
			List<ServicioTraficoVO> lista = servicioTraficoDao.getServicioTraficoPorTipoTramite(tipoTramite);
			if (lista != null && !lista.isEmpty()) {
				return conversor.transform(lista, ServicioTraficoDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el servicio trafico", e);
		}
		return null;
	}
}
