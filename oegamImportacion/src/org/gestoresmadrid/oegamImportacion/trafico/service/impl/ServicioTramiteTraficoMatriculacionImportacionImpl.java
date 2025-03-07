package org.gestoresmadrid.oegamImportacion.trafico.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegamConversiones.conversion.Conversion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaDireccionImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoMatriculacionImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioVehiculoImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTramiteTraficoMatriculacionImportacionImpl implements ServicioTramiteTraficoMatriculacionImportacion {

	private static final long serialVersionUID = 6675212509089229299L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteTraficoMatriculacionImportacionImpl.class);

	@Autowired
	TramiteTraficoMatrDao tramiteTraficoMatrDao;

	@Autowired
	ServicioVehiculoImportacion servicioVehiculo;

	@Autowired
	ServicioPersonaImportacion servicioPersona;

	@Autowired
	ServicioPersonaDireccionImportacion servicioPersonaDireccion;

	@Autowired
	Conversion conversor;


	@Override
	@Transactional
	public TramiteTrafMatrVO getTramiteMatriculacionVO(BigDecimal numExpediente, boolean permisoLiberacion, boolean tramiteCompleto) {
		try {
			return tramiteTraficoMatrDao.getTramiteTrafMatr(numExpediente, tramiteCompleto, permisoLiberacion);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el trámite de Matriculación ,error: ", e, numExpediente.toString());
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getListaTramitesMatriculacion(BigDecimal[] listaNumsExpedientes, Boolean tramiteCompleto) {
		try {
			if (listaNumsExpedientes != null) {
				List<TramiteTrafMatrVO> listaTramitesBBDD = tramiteTraficoMatrDao.getListaTramitesPorExpediente(listaNumsExpedientes, tramiteCompleto);
				if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
					return listaTramitesBBDD;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de traer la lista de expedientes, error: ", e);

		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getTramitePorMatriculaContrato(String matricula, Long idContrato) {
		return tramiteTraficoMatrDao.getTramitePorMatriculaContrato(matricula, idContrato);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getListaTramitesPorMatricula(String matricula) {
		return tramiteTraficoMatrDao.getListaTramitesPorMatricula(matricula);
	}


}