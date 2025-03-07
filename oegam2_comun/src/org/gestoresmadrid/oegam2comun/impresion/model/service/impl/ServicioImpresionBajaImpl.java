package org.gestoresmadrid.oegam2comun.impresion.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.impresion.ImpresionTramitesBaja;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresion;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionBaja;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.ResultBean;
import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioImpresionBajaImpl implements ServicioImpresionBaja {

	private static final long serialVersionUID = -8961776099987253154L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionBajaImpl.class);

	@Autowired
	private ServicioImpresion servicioImpresion;

	@Autowired
	private ServicioImpresion servicioImpresionImpl;

	@Autowired
	Utiles utiles;

	@Override
	public ResultBean imprimirBajaPorExpedientes(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, BigDecimal idUsuario) throws Throwable {
		ResultBean result = null;
		ImpresionTramitesBaja impresionTramitesBaja = new ImpresionTramitesBaja();
		if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.BajasBorradorPDF417.toString())) {
			result = imprimirBaja(numsExpedientes, idUsuario, TipoImpreso.BajasBorradorPDF417, false, impresionTramitesBaja);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.BajasPDF417.toString())) {
			result = imprimirBaja(numsExpedientes, idUsuario, TipoImpreso.BajasPDF417, false);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.BajasTelematicas.toString())) {
			result = imprimirBaja(numsExpedientes, idUsuario, TipoImpreso.BajasTelematicas, false);
		} else if (criteriosImprimir.getTipoImpreso().equals(ConstantesPDF.MANDATO_GENERICO)) {
			result = imprimirMandatos(numsExpedientes, TipoImpreso.MatriculacionMandatoGenerico, impresionTramitesBaja);
		} else if (criteriosImprimir.getTipoImpreso().equals(ConstantesPDF.MANDATO_ESPECIFICO)) {
			result = imprimirMandatos(numsExpedientes, TipoImpreso.MatriculacionMandatoEspecifico, impresionTramitesBaja);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
			result = imprimirListadoBastidores(numsExpedientes, idUsuario, impresionTramitesBaja);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.InformeBajaTelematica.toString())) {
			result = imprimirInformeBaja(numsExpedientes, idUsuario, impresionTramitesBaja);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.DeclaracionJuradaExportacionTransito.toString())) {
			result = imprimirDeclaracionJurada(numsExpedientes, idUsuario, criteriosImprimir, TipoImpreso.DeclaracionJuradaExportacionTransito, impresionTramitesBaja);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.DeclaracionJuradaExtravioFicha.toString())) {
			result = imprimirDeclaracionJurada(numsExpedientes, idUsuario, criteriosImprimir, TipoImpreso.DeclaracionJuradaExtravioFicha, impresionTramitesBaja);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.DeclaracionJuradaExtravioPermisoFicha.toString())) {
			result = imprimirDeclaracionJurada(numsExpedientes, idUsuario, criteriosImprimir, TipoImpreso.DeclaracionJuradaExtravioPermisoFicha, impresionTramitesBaja);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.DeclaracionJuradaExtravioPermisoLicencia.toString())) {
			result = imprimirDeclaracionJurada(numsExpedientes, idUsuario, criteriosImprimir, TipoImpreso.DeclaracionJuradaExtravioPermisoLicencia, impresionTramitesBaja);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.DeclaracionJuradaEntregaAnteriorPermiso.toString())) {
			result = imprimirDeclaracionJurada(numsExpedientes, idUsuario, criteriosImprimir, TipoImpreso.DeclaracionJuradaEntregaAnteriorPermiso, impresionTramitesBaja);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.JustifRegEntradaBajaSive.toString())) {
			result = servicioImpresion.recuperarDocumentosOficiales(numsExpedientes, ConstantesGestorFicheros.TRAMITAR_BTV, criteriosImprimir.getTipoImpreso());
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.InformeBajaSive.toString())) {
			result = servicioImpresion.recuperarDocumentosOficiales(numsExpedientes, ConstantesGestorFicheros.TRAMITAR_BTV, criteriosImprimir.getTipoImpreso());
		}
		return servicioImpresionImpl.tratarResultSiNulo(result);
	}

	private ResultBean imprimirInformeBaja(String[] numsExpedientes, BigDecimal idUsuario, ImpresionTramitesBaja impresionTramitesBaja) {
		ResultBean result = null;
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result = new ResultBean(true, "No hay trámites para imprimir");
			return result;
		}
		if (numsExpedientes.length > 1) {
			result = impresionTramitesBaja.imprimirInformeBajaPorExpedientes(numsExpedientes, TipoTramiteTrafico.Baja);
		} else {
			result = impresionTramitesBaja.imprimirInformeBajaPorExpediente(numsExpedientes[0], TipoTramiteTrafico.Baja);
		}
		return result;
	}

	private ResultBean imprimirListadoBastidores(String[] numsExpedientes, BigDecimal idUsuario, ImpresionTramitesBaja impresionTramitesBaja) {
		ResultBean result = new ResultBean();
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result = new ResultBean(true, "No hay trámites para imprimir");
			return result;
		}
		try {
			result = impresionTramitesBaja.impresionListadoBastidoresBaja(numsExpedientes, idUsuario);
			if (result == null) {
				result = new ResultBean();
				result.setError(true);
				result.addMensajeALista("No se ha podido realizar la impresión");
			} else if (!result.getError()) {
				byte[] byte1 = (byte[]) result.getAttachment(ResultBean.TIPO_PDF);
				if (null == byte1) {
					result.addMensajeALista("No se ha cargado el fichero PDF");
					result.setError(true);
				} else {
					result = servicioImpresionImpl.generarResultBeanConByteFinal(byte1, "ListadoMatriculas.pdf");
				}
			}
		} catch (OegamExcepcion e) {
			log.error("Error a la hora de imprimir: " + e.getMessage(), e);
			result = new ResultBean(true, "El tipo indicado no es un tipo válido para imprimir");
		} catch (Throwable e) {
			log.error("Error a la hora de imprimir: " + e.getMessage(), e);
			result = new ResultBean(true, "El tipo indicado no es un tipo válido para imprimir");
		}
		return result;
	}

	private ResultBean imprimirMandatos(String[] numsExpedientes, TipoImpreso tipoImpreso, ImpresionTramitesBaja impresionTramitesBaja) {
		ResultBean result = new ResultBean();
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result = new ResultBean(true, "No hay trámites para imprimir");
			return result;
		}
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (int i = 0; !result.getError() && i < numsExpedientes.length; i++) {
				ResultBean rs = impresionTramitesBaja.impresionMandatosBaja(numsExpedientes[i], tipoImpreso.toString());
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
			result = servicioImpresionImpl.generarResultBeanConArrayByteFinal(listaByte, tipoImpreso.toString() + ".pdf");
		}
		return result;
	}

	@Override
	public ResultBean imprimirBaja(String[] numsExpedientes, BigDecimal idUsuario, TipoImpreso tipoImpreso, Boolean esProceso) {
		return imprimirBaja(numsExpedientes, idUsuario, tipoImpreso, esProceso, new ImpresionTramitesBaja());
	}

	private ResultBean imprimirBaja(String[] numsExpedientes, BigDecimal idUsuario, TipoImpreso tipoImpreso, Boolean esProceso, ImpresionTramitesBaja impresionTramitesBaja) {
		ResultBean result = new ResultBean();
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result = new ResultBean(true, "No hay trámites para imprimir");
			return result;
		}
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (int i = 0; !result.getError() && i < numsExpedientes.length; i++) {
				ResultBean rs = impresionTramitesBaja.impresionGeneralBaja(numsExpedientes[i], tipoImpreso.toString(), idUsuario, TipoTramiteTrafico.Baja);

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
			if (esProceso) {
				result = servicioImpresionImpl.generarResultBeanByteProceso(listaByte);
			} else {
				result = servicioImpresionImpl.generarResultBeanConArrayByteFinal(listaByte, tipoImpreso.toString() + ".pdf");
			}
		}
		return result;
	}

	@Override
	public ResultBean impresionListadoBastidoresBaja(String[] numExpedientes, BigDecimal idUsuario) throws OegamExcepcion, Throwable {
		return new ImpresionTramitesBaja().impresionListadoBastidoresBaja(numExpedientes, idUsuario);
	}

	private ResultBean imprimirDeclaracionJurada(String[] numsExpedientes, BigDecimal idUsuario, CriteriosImprimirTramiteTraficoBean criteriosImprimir, TipoImpreso tipoImpreso,
			ImpresionTramitesBaja impresionTramitesBaja) throws Throwable {
		BigDecimal[] numExp = utiles.convertirStringArrayToBigDecimal(numsExpedientes);
		ResultBean result = new ResultBean();
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		for (int i = 0; !result.getError() && i < numExp.length; i++) {
			ResultBean rs = null;
			try {
				rs = impresionTramitesBaja.imprimirDeclaracionJurada(numExp[i], idUsuario, criteriosImprimir.getTipoRepresentacion(), tipoImpreso.getNombreEnum());
			} catch (Exception e) {
				rs = new ResultBean();
				rs.setError(true);
				rs.setMensaje("Ha sucedido un error a la hora de imprimir el tramite");
				log.error("Ha sucedido un error a la hora de imprimir el tramite: " + e.getMessage(), numExp[i].toString());
			}
			if (rs == null || rs.getError()) {
				result.setError(true);
				result.addMensajeALista(numExp[i] + ": " + rs.getMensaje());
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
