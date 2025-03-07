package org.gestoresmadrid.oegam2comun.mandato.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.mandato.model.dao.EvolucionMandatoDao;
import org.gestoresmadrid.core.mandato.model.vo.EvolucionMandatoPK;
import org.gestoresmadrid.core.mandato.model.vo.EvolucionMandatoVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.oegam2comun.mandato.model.service.ServicioEvolucionMandato;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioEvolucionContrato;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionMandatoImpl implements ServicioEvolucionMandato {

	private static final long serialVersionUID = -1311090060009641612L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionContrato.class);

	@Autowired
	EvolucionMandatoDao evolucionMandatoDao;

	@Autowired
	Conversor conversor;

	@Override
	@Transactional
	public ResultBean guardarEvolucionMandato(Long idMandato, String codigoMandato, BigDecimal idUsuario, TipoActualizacion tipoActualizacion, String descripcion) {
		ResultBean resultado = new ResultBean();
		try {
			EvolucionMandatoVO evolucionMandatoVO = rellenarEvolucion(idMandato, codigoMandato, idUsuario, tipoActualizacion, descripcion);
			EvolucionMandatoPK evolucionPK = (EvolucionMandatoPK) evolucionMandatoDao.guardar(evolucionMandatoVO);
			resultado.setError(false);
			if (evolucionPK == null) {
				resultado.setError(true);
				resultado.addMensajeALista("Error al guardar la evolución del mandato");
				log.error("Error al guardar la evolución del mandato");
			}
		} catch (Exception e) {
			resultado.setError(true);
			resultado.addMensajeALista("Error al guardar la evolución del mandato");
			log.error("Error al guardar la evolución del mandato", e);
		}
		return resultado;
	}

	private EvolucionMandatoVO rellenarEvolucion(Long idMandato, String codigoMandato, BigDecimal idUsuario, TipoActualizacion tipoActualizacion, String descripcion) {
		EvolucionMandatoVO evolucionMandatoVO = new EvolucionMandatoVO();
		EvolucionMandatoPK id = new EvolucionMandatoPK();
		Date fechaCambio = new Date();
		id.setIdMandato(idMandato);
		id.setIdUsuario(idUsuario.longValue());
		id.setFechaCambio(fechaCambio);
		evolucionMandatoVO.setCodigoMandato(codigoMandato);
		evolucionMandatoVO.setId(id);
		evolucionMandatoVO.setTipoActuacion(tipoActualizacion.getValorEnum());
		return evolucionMandatoVO;
	}
}
