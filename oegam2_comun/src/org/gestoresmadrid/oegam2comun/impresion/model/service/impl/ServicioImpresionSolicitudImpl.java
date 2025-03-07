package org.gestoresmadrid.oegam2comun.impresion.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.oegam2comun.impresion.ImpresionTramitesSolicitud;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresion;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionSolicitud;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.ResultBean;
import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import trafico.utiles.ConstantesPDF;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioImpresionSolicitudImpl implements ServicioImpresionSolicitud {

	private static final long serialVersionUID = 7960615530101488528L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionSolicitud.class);

	@Autowired
	private ServicioImpresion servicioImpresion;

	@Autowired
	Utiles utiles;

	@Override
	public ResultBean imprimirSolicitudPorExpedientes(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, BigDecimal idUsuario) {
		ResultBean result = null;
		if (criteriosImprimir.getTipoImpreso().equals(ConstantesPDF.MANDATO_GENERICO)) {
			result = imprimirMandatoPorExpedientes(numsExpedientes, TipoImpreso.MatriculacionMandatoGenerico);
		} else if (criteriosImprimir.getTipoImpreso().equals(ConstantesPDF.MANDATO_ESPECIFICO)) {
			result = imprimirMandatoPorExpedientes(numsExpedientes, TipoImpreso.MatriculacionMandatoEspecifico);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.SolicitudAvpo.toString())) {
			result = servicioImpresion.recuperarDocumentosOficiales(numsExpedientes, ConstantesGestorFicheros.SOLICITUD_INFORMACION, TipoImpreso.SolicitudAvpo.toString());
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.SolicitudATEM.toString())) {
			result = servicioImpresion.recuperarDocumentosOficiales(numsExpedientes, ConstantesGestorFicheros.SOLICITUD_INFORMACION, TipoImpreso.SolicitudATEM.toString());
		}
		return servicioImpresion.tratarResultSiNulo(result);
	}

	private ResultBean imprimirMandatoPorExpedientes(String[] numsExpedientes, TipoImpreso tipoImpreso) {
		BigDecimal[] numExp = utiles.convertirStringArrayToBigDecimal(numsExpedientes);
		ResultBean result = new ResultBean();
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		ImpresionTramitesSolicitud impresionTramitesSolicitud = new ImpresionTramitesSolicitud();
		for (int i = 0; !result.getError() && i < numExp.length; i++) {
			ResultBean rs = null;
			try {
				rs = impresionTramitesSolicitud.imprimirMandatoPorExpediente(numExp[i], tipoImpreso.toString());
			} catch (OegamExcepcion e) {
				result = new ResultBean(true, "Ha sucedido un error a la hora de imprimir el tramite");
				log.error("Ha sucedido un error a la hora de imprimir el tramite: " + e.getMessage(), e);
			} catch (Throwable e) {
				log.error("Error a la hora de imprimir: " + e.getMessage(), e);
				result = new ResultBean(true, "El tipo indicado no es un tipo válido para imprimir");
			}
			if (rs == null || rs.getError()) {
				result.setError(true);
				result.addMensajeALista(numExp[i] + ": " + rs.getListaMensajes());
			} else {
				byte[] byte1 = (byte[]) rs.getAttachment(ResultBean.TIPO_PDF);
				listaByte.add(byte1);
			}
		}

		if (result != null && !result.getError()) {
			result = servicioImpresion.generarResultBeanConArrayByteFinal(listaByte, tipoImpreso.toString() + ".pdf");
		}
		return result;
	}
}
