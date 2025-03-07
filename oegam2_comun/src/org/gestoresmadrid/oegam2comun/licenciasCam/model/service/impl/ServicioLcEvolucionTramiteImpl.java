package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import java.util.Date;

import org.gestoresmadrid.core.licencias.model.dao.LcEvolucionTramiteDao;
import org.gestoresmadrid.core.licencias.model.vo.LcEvolucionTramiteVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcEvolucionTramite;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcEvolucionTramiteDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcEvolucionTramiteImpl implements ServicioLcEvolucionTramite {

	private static final long serialVersionUID = 7305605957238024710L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcEvolucionTramiteImpl.class);

	@Autowired
	LcEvolucionTramiteDao evolucionTramiteDao;

	@Autowired
	Conversor conversor;

	@Override
	@Transactional
	public void guardar(LcEvolucionTramiteDto evolucion) {
		try {
			LcEvolucionTramiteVO evolucionVO = conversor.transform(evolucion, LcEvolucionTramiteVO.class);
			evolucionVO.setFechaCambio(new Date());
			evolucionTramiteDao.guardarOActualizar(evolucionVO);
		} catch (Exception e) {
			log.error("Error al guardar la evolución del trámite de licencia: " + evolucion.getNumExpediente(), e, evolucion.getNumExpediente().toString());
		}
	}

}
