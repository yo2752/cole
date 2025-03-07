package org.gestoresmadrid.oegam2comun.impresion.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.oegam2comun.impresion.ImpresionTramitesDuplicado;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresion;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionDuplicados;
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
public class ServicioImpresionDuplicadosImpl implements ServicioImpresionDuplicados {

	private static final long serialVersionUID = 611967048762148711L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionDuplicados.class);

	@Autowired
	private ServicioImpresion servicioImpresion;

	@Override
	public ResultBean imprimirDuplicadosPorExpedientes(String[] numExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, BigDecimal idUsuario)  {
		ResultBean result = null;
		try{
			ImpresionTramitesDuplicado impresionTramitesDuplicado = new ImpresionTramitesDuplicado();
			if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionListadoBastidores.toString())){
				result = imprimirListadoMatriculas(numExpedientes, idUsuario, impresionTramitesDuplicado);
			} else if(criteriosImprimir.getTipoImpreso().equals(ConstantesPDF.MANDATO_GENERICO)){
				result = imprimirMandatoPorExpedientes(numExpedientes, TipoImpreso.MatriculacionMandatoGenerico, impresionTramitesDuplicado);
			} else if(criteriosImprimir.getTipoImpreso().equals(ConstantesPDF.MANDATO_ESPECIFICO)){
				result = imprimirMandatoPorExpedientes(numExpedientes, TipoImpreso.MatriculacionMandatoEspecifico, impresionTramitesDuplicado);
			}else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.PermisoTemporalDuplicado.toString())) {
				result = servicioImpresion.recuperarDocumentosOficiales(numExpedientes, ConstantesGestorFicheros.FULLDUPLICADO, criteriosImprimir.getTipoImpreso());
			}else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.JustifRegEntrada.toString())) {
				result = servicioImpresion.recuperarDocumentosOficiales(numExpedientes, ConstantesGestorFicheros.FULLDUPLICADO, criteriosImprimir.getTipoImpreso());
			}else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.JustificanteDuplicado.toString())) {
				result = servicioImpresion.recuperarDocumentosOficiales(numExpedientes, ConstantesGestorFicheros.FULLDUPLICADO, criteriosImprimir.getTipoImpreso());
			}
		}catch(Exception e){
			result = new ResultBean(true, "Se ha producido un error a la hora de imprimir.");
			log.error("Se ha producido un error a la hora de imprimir: " + e.getMessage(), e);
		}
		return servicioImpresion.tratarResultSiNulo(result);
	}

	private ResultBean imprimirMandatoPorExpedientes(String[] numsExpedientes, TipoImpreso tipoImpreso,
			ImpresionTramitesDuplicado impresionTramitesDuplicado) {
		ResultBean result = new ResultBean();
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result = new ResultBean(true, "No hay trámites para imprimir");
			return result;
		}
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (int i = 0; !result.getError() && i < numsExpedientes.length; i++) {
				ResultBean rs = impresionTramitesDuplicado.impresionMandatosDuplicado(numsExpedientes[i], tipoImpreso.toString());
				if (rs == null || rs.getError()) {
					result.setError(true);
					result.addMensajeALista(numsExpedientes[i] + ": " + rs.getMensaje());
				} else {
					byte[] byte1 = (byte[]) rs.getAttachment(ResultBean.TIPO_PDF);
					listaByte.add(byte1);
				}
			}
		} catch (OegamExcepcion e) {
			log.error("Error a la hora de imprimir: " + e.getMessage(), e);
			result = new ResultBean(true, "El tipo indicado no es un tipo válido para imprimir");
		} catch (Throwable e) {
			log.error("Error a la hora de imprimir: " + e.getMessage(), e);
			result = new ResultBean(true, "El tipo indicado no es un tipo válido para imprimir");
		}
		if (result != null && !result.getError()) {
			result = servicioImpresion.generarResultBeanConArrayByteFinal(listaByte, tipoImpreso.toString() + ".pdf");
		}
		return result;
	}

	@Override
	public ResultBean imprimirListadoMatriculas(String[] numExpedientes, BigDecimal idUsuario){
		return imprimirListadoMatriculas(numExpedientes, idUsuario, new ImpresionTramitesDuplicado());
	}

	private ResultBean imprimirListadoMatriculas(String[] numExpedientes, BigDecimal idUsuario, ImpresionTramitesDuplicado impresionTramitesDuplicado){
		ResultBean resultado = null; 
		try {
			List<String> listaExpedientes = new ArrayList<String>(Arrays.asList(numExpedientes));
			resultado = impresionTramitesDuplicado.matriculacionListadoBastidoresDuplicado(listaExpedientes, idUsuario);
			if(resultado != null && !resultado.getError()){
				byte[] byte1 = (byte[]) resultado.getAttachment(ResultBean.TIPO_PDF);
				if (null == byte1) {
					resultado.addMensajeALista("No se ha cargado el fichero PDF");
					resultado.setError(true);
				} else {
					resultado = servicioImpresion.generarResultBeanConByteFinal(byte1, "ListadoMatriculas.pdf");
				}
			}
		} catch (OegamExcepcion e) {
			resultado = new ResultBean(true,"Ha sucedido un error a la hora de imprimir el tramite.");
			log.error("Ha sucedido un error a la hora de imprimir el tramite.: " + e.getMessage(), e);
		} catch (Throwable e) {
			resultado = new ResultBean(true,"Ha sucedido un error a la hora de imprimir el tramite.");
			log.error("Ha sucedido un error a la hora de imprimir el tramite.: " + e.getMessage(), e);
		}
		return resultado;
	}
}