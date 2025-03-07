package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.general.model.dao.TipoTramiteDao;
import org.gestoresmadrid.core.general.model.vo.TipoTramiteVO;
import org.gestoresmadrid.oegam2comun.model.service.ServicioAplicacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioTipoTramite;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TipoTramiteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoTramiteImpl implements ServicioTipoTramite {

	private static final long serialVersionUID = 942841007729288912L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoTramiteImpl.class);

	@Autowired
	private Conversor conversor;

	@Autowired
	private TipoTramiteDao tipoTramiteDao;

	@Autowired
	private ServicioAplicacion servicioAplicacion;

	@Override
	@Transactional(readOnly = true)
	public List<String> busquedaTiposCreditosPorContrato(Long idContrato) {
		List<String> codigosAplicaciones = servicioAplicacion.getCodigosAplicacionPorContrato(idContrato);
		if (codigosAplicaciones != null && !codigosAplicaciones.isEmpty()) {
			return tipoTramiteDao.busquedaTiposCreditosPorCodigoAplicacion(codigosAplicaciones);
		}
		return null;
	}

	@Override
	@Transactional
	public List<TipoTramiteDto> getTipoTramitePorAplicacion(String aplicacion) {
		try {
			List<TipoTramiteVO> listaTramites = tipoTramiteDao.getTipoTramitePorAplicacion(aplicacion);
			if (listaTramites != null && !listaTramites.isEmpty()) {
				return conversor.transform(listaTramites, TipoTramiteDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la lista de tipo trámites", e);
		}
		return null;
	}
}
