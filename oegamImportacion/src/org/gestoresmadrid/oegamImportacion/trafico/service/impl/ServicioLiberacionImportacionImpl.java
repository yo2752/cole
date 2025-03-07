package org.gestoresmadrid.oegamImportacion.trafico.service.impl;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.trafico.eeff.model.dao.LiberacionEEFFDao;
import org.gestoresmadrid.core.trafico.eeff.model.vo.LiberacionEEFFVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioLiberacionImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLiberacionImportacionImpl implements ServicioLiberacionImportacion {

	private static final long serialVersionUID = 7469217716523050460L;

	public static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLiberacionImportacionImpl.class);

	@Autowired
	LiberacionEEFFDao liberacionEEFFDao;

	@Override
	@Transactional
	public String guardarDatosLiberacion(TramiteTrafMatrVO tramiteMatrVO) {
		String mensajeResultado = "";
		try {
			if (tramiteMatrVO != null && tramiteMatrVO.getVehiculo() != null && tramiteMatrVO.getVehiculo().getNive() != null && !tramiteMatrVO.getVehiculo().getNive().isEmpty()) {
				tratarDatosLiberacion(tramiteMatrVO.getLiberacionEEFF(), tramiteMatrVO);
				liberacionEEFFDao.guardarOActualizar(tramiteMatrVO.getLiberacionEEFF());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de liberación, error: ", e);
			mensajeResultado = "Ha sucedido un error a la hora de guardar los datos de liberación.";
		}
		return mensajeResultado;
	}

	private void tratarDatosLiberacion(LiberacionEEFFVO liberacionEEFFVO, TramiteTrafMatrVO tramiteMatrVO) {
		if (tramiteMatrVO.getVehiculo() != null) {
			if (tramiteMatrVO.getVehiculo().getBastidor() != null && !tramiteMatrVO.getVehiculo().getBastidor().isEmpty()) {
				liberacionEEFFVO.setTarjetaBastidor(tramiteMatrVO.getVehiculo().getBastidor());
			}
			if (tramiteMatrVO.getVehiculo().getNive() != null && !tramiteMatrVO.getVehiculo().getNive().isEmpty()) {
				liberacionEEFFVO.setTarjetaNive(tramiteMatrVO.getVehiculo().getNive());
			}
		}

		for (IntervinienteTraficoVO intervTrafVO : tramiteMatrVO.getIntervinienteTraficos()) {
			if (TipoInterviniente.Titular.getValorEnum().equals(intervTrafVO.getId().getTipoInterviniente())) {
				liberacionEEFFVO.setNif(intervTrafVO.getId().getNif());
			}
			break;
		}

		liberacionEEFFVO.setNumExpediente(tramiteMatrVO.getNumExpediente());
		liberacionEEFFVO.setNumColegiado(tramiteMatrVO.getNumColegiado());
		liberacionEEFFVO.setRealizado(Boolean.FALSE);
	}

}
