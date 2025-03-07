package org.gestoresmadrid.oegamComun.credito.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.credito.service.ServicioComunCredito;
import org.gestoresmadrid.oegamComun.credito.service.ServicioGestionCredito;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioGestionCreditoImpl implements ServicioGestionCredito {

	private static final long serialVersionUID = -4008563284131301992L;

	private static final Logger log = LoggerFactory.getLogger(ServicioGestionCreditoImpl.class);

	private static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";

	@Autowired
	private ServicioComunCredito servicioCredito;

	@Autowired
	private ServicioComunCola servicioCola;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoBean crearSolicitudCreditos(BigDecimal numExpediente, Long idContrato, String tipoTramite, String tipoTramiteCredito, String concepto, String accion, BigDecimal idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			if (idContrato != null && StringUtils.isNotBlank(tipoTramite) && StringUtils.isNotBlank(tipoTramiteCredito) && StringUtils.isNotBlank(concepto)) {
				ResultadoBean resultadoCreditos = servicioCredito.validarTotalCreditos(idContrato, tipoTramiteCredito);
				if (resultadoCreditos != null && !resultadoCreditos.getError()) {
					String datosGestion = tipoTramite + "-" + resultadoCreditos.getTipoCredito() + "-" + tipoTramiteCredito + "-" + concepto + "-" + accion;
					resultado = servicioCola.crearSolicitud(numExpediente.longValue(), ProcesosAmEnum.GESTION_CREDITOS.getValorEnum(), gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD),
							tipoTramite, idUsuario, new BigDecimal(idContrato), datosGestion);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resultadoCreditos.getMensaje());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos obligatorios para realizar la gestión de créditos.");
			}
		} catch (Throwable e) {
			log.debug("Ha sucedido un error a la hora de crear la solicitud para gestionar los creditos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de crear la solicitud para gestionar los créditos.");
		}
		return resultado;
	}
}