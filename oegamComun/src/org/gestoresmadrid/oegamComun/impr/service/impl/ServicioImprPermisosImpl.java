package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.enumerados.TipoImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioDocImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioComunDocPrmDstvFicha;
import org.gestoresmadrid.oegamComun.impr.service.ServicioImprPermisos;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoImprBean;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioImprPermisosImpl implements ServicioImprPermisos{

	private static final long serialVersionUID = -854948802754357726L;

	private static final Logger log = LoggerFactory.getLogger(ServicioImprPermisosImpl.class);

	@Autowired
	ServicioDocImpr servicioDocImpr;

	@Autowired
	ServicioComunDocPrmDstvFicha servicioDocPrmDstvFicha;

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Override
	public void generarColaExpedienteImprNocturno(List<BigDecimal> listaExpedientesImprPC, Long idContrato, String tipoTramite, Long idUsuario,
			String jefatura, String referenciaDocumento, Boolean esEntornoAm, ResultadoImprBean resultado) {
		try {
			if (listaExpedientesImprPC != null && !listaExpedientesImprPC.isEmpty()) {
				Date fechaImpr = new Date();
				ResultadoDocImprBean resultadoGenDocImpr = servicioDocPrmDstvFicha.generarDocImpr(idContrato, fechaImpr, TipoImpr.Permiso_Circulacion.getValorEnum(), jefatura,
						idUsuario, tipoTramite, referenciaDocumento);
				if (!resultadoGenDocImpr.getError()) {
					ResultadoBean resultadoDocImpr = null;
					try {
						resultadoDocImpr = servicioTramiteTrafico.generarDocImprNocturno(listaExpedientesImprPC, resultadoGenDocImpr.getDocId(),
								resultadoGenDocImpr.getsDocId(), idUsuario, TipoImpr.Permiso_Circulacion.getValorEnum(), esEntornoAm, idContrato);
						if (resultadoDocImpr.getError()) {
							resultado.addListaMensajeError(resultadoDocImpr.getMensaje());
						} else {
							resultado.addListaMensajeOk("Documento con referencia Propia: " + referenciaDocumento + " solicitando sus Permisos de Circulacion.");
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de cambiar los estados y generar la cola para los IMPR_NOCTURNOS del contrato: " + idContrato + " de la fecha: "+ fechaImpr + " del tipo PC y tipoTramite: " + tipoTramite + ", error: ",e);
						resultado.addListaMensajeError("Ha sucedido un error a la hora de cambiar los estados y generar la cola para los IMPR_NOCTURNOS del contrato: " + idContrato + " de la fecha: "+ fechaImpr + " del tipo PC y tipoTramite: " + tipoTramite);
					}
					if(resultadoDocImpr != null && resultadoDocImpr.getError()){
						servicioDocImpr.borrarDocImpr(resultadoGenDocImpr.getDocId());
					}
				}
			} else {
				resultado.addListaMensajeError("No existen datos de IMPR para generar la cola para el IMPR_NOCTURNO para el contrato: " + idContrato + ", tipoTramite: " + tipoTramite );
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la cola para el IMPR_NOCTURNO para el contrato: " + idContrato + ", tipoTramite: " + tipoTramite +", error: ",e);
			resultado.addListaMensajeError("Ha sucedido un error a la hora de generar la cola para el IMPR_NOCTURNO para el contrato: " + idContrato + ", tipoTramite: " + tipoTramite);
		}
	}

}