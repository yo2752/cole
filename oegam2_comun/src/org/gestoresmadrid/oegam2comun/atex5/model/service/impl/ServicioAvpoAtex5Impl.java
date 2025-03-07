package org.gestoresmadrid.oegam2comun.atex5.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.atex5.model.dao.ConsultaVehiculoAtex5Dao;
import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.atex5.model.enumerados.TipoTramiteAtex5;
import org.gestoresmadrid.core.atex5.model.vo.ConsultaVehiculoAtex5VO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioAvpoAtex5;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioAvpoAtex5Impl implements ServicioAvpoAtex5 {

	private static final long serialVersionUID = 1314154244909083331L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioAvpoAtex5Impl.class);

	@Autowired
	private ConsultaVehiculoAtex5Dao consultaVehiculoAtex5Dao;

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultBean avpo(BigDecimal numExpediente, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario, int numeroSolicitudes) {
		ResultBean resultado = new ResultBean();
		ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO = new ConsultaVehiculoAtex5VO();
		consultaVehiculoAtex5VO.setIdContrato(idContrato.longValue());
		consultaVehiculoAtex5VO.setNumColegiado(numColegiado);
		consultaVehiculoAtex5VO.setNumExpediente(numExpediente);
		consultaVehiculoAtex5VO.setEstado(new BigDecimal(EstadoAtex5.Pdte_Respuesta_DGT.getValorEnum()));
		consultaVehiculoAtex5VO.setFechaAlta(new Date());

		consultaVehiculoAtex5Dao.guardarOActualizar(consultaVehiculoAtex5VO);

		ResultBean resultCobrarCreditos = tratarCobrarCreditos(consultaVehiculoAtex5VO, idUsuario, TipoTramiteTrafico.Solicitud.getValorEnum(), ConceptoCreditoFacturado.AVPO, numeroSolicitudes);

		if (!resultCobrarCreditos.getError()) {
			try {
				ResultBean resultCola = servicioCola.crearSolicitud(ProcesosEnum.AVPO.getNombreEnum(), null, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD_NODO_2), TipoTramiteAtex5.Consulta_Vehiculo
						.getValorEnum(), consultaVehiculoAtex5VO.getNumExpediente().toString(), idUsuario, null, new BigDecimal(consultaVehiculoAtex5VO.getIdContrato()));
				if (resultCola.getError()) {
					resultado = resultCola;
				}
			} catch (OegamExcepcion e) {
				resultado.setError(true);
				resultado.setMensaje("Error al crear la solicitud para obtener la matricula");
				log.error("Error al crear la solicitud para obtener la matricula", e);
			}
		} else {
			resultado = resultCobrarCreditos;
		}
		return resultado;
	}

	private ResultBean tratarCobrarCreditos(ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO, BigDecimal idUsuario, String tipoTramite, ConceptoCreditoFacturado conceptoCreditoFacturado, int numeroSolicitudes) {
		ResultBean resultado = new ResultBean();
		try {
			if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(COBRAR_CREDITOS))) {
				resultado = servicioCredito.validarCreditos(tipoTramite, new BigDecimal(consultaVehiculoAtex5VO.getIdContrato()), new BigDecimal(numeroSolicitudes));
				if (!resultado.getError()) {
					resultado = servicioCredito.descontarCreditos(tipoTramite, new BigDecimal(consultaVehiculoAtex5VO.getIdContrato()), new BigDecimal(numeroSolicitudes), idUsuario, conceptoCreditoFacturado,
							consultaVehiculoAtex5VO.getIdConsultaVehiculoAtex5().toString());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cobrar los creditos para realizar la obtencion de matricula, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de cobrar los creditos.");
		}
		return resultado;
	}

	
}
