package org.gestoresmadrid.oegamImpresion.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.impresion.model.dao.ImpresionDao;
import org.gestoresmadrid.core.impresion.model.enumerados.EstadosImprimir;
import org.gestoresmadrid.core.impresion.model.enumerados.TipoDocumentoImpresiones;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.TipoTramiteInterga;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.correo.service.ServicioComunCorreo;
import org.gestoresmadrid.oegamComun.credito.service.ServicioComunCredito;
import org.gestoresmadrid.oegamComun.datosSensibles.service.ServicioComunDatosSensibles;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioComunTasa;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.service.ServicioEvolucionImpresion;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionTramiteTrafico;
import org.gestoresmadrid.oegamImpresion.view.bean.ConsultaImpresionBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioImpresionDocumentosImpl implements ServicioImpresionDocumentos {

	private static final long serialVersionUID = 4765159623450302355L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionDocumentosImpl.class);

	@Autowired
	ServicioImpresionTramiteTrafico servicioImpresionTramiteTrafico;

	@Autowired
	ServicioEvolucionImpresion servicioEvolucionImpresion;

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioComunCredito servicioCredito;

	@Autowired
	ServicioComunCola servicioCola;

	@Autowired
	ServicioComunContrato servicioContrato;

	@Autowired
	ServicioComunDatosSensibles servicioComunDatosSensibles;

	@Autowired
	ServicioComunCorreo servicioCorreo;

	@Autowired
	ImpresionDao impresionDao;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ServicioComunTasa servicioComunTasa;

	@Override
	@Transactional
	public ImpresionVO getDatosImpresion(Long idImpresion) {
		try {
			return impresionDao.getImpresion(idImpresion);
		} catch (Exception e) {
			log.error("Error al obtener los datos de la impresion con id:" + idImpresion, e);
		}
		return null;
	}

	@Override
	@Transactional
	public ImpresionVO getDatosImpresionPorNombreDocumento(String nombreDocumento) {
		try {
			return impresionDao.getImpresionPorNombreDocumento(nombreDocumento);
		} catch (Exception e) {
			log.error("Error al obtener los datos de la impresion con nombre documento:" + nombreDocumento, e);
		}
		return null;
	}

	@Override
	@Transactional
	public ImpresionVO getDatosPorNombreDocumentoGeneradoYDescargado(String nombreDocumento) {
		try {
			List<String> estados = new ArrayList<>();
			estados.add(EstadosImprimir.Generado.getValorEnum());
			estados.add(EstadosImprimir.Descargado.getValorEnum());
			return impresionDao.getImpresionPorNombreDocumentoConEstados(nombreDocumento, estados);
		} catch (Exception e) {
			log.error("Error al obtener los datos de la impresion con nombre documento:" + nombreDocumento, e);
		}
		return null;
	}

	@Override
	@Transactional
	public ImpresionVO getDatosPorNombreDocumentoSinEliminado(String nombreDocumento) {
		try {
			List<String> estados = new ArrayList<>();
			estados.add(EstadosImprimir.Creacion.getValorEnum());
			estados.add(EstadosImprimir.Generado.getValorEnum());
			estados.add(EstadosImprimir.Descargado.getValorEnum());
			return impresionDao.getImpresionPorNombreDocumentoConEstados(nombreDocumento, estados);
		} catch (Exception e) {
			log.error("Error al obtener los datos de la impresion con nombre documento:" + nombreDocumento, e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoImpresionBean crearImpresion(String[] numExpedientes, Long idContrato, Long idUsuario, String tipoDocumento, String tipoTramite, String tipoInterviniente) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		try {
			ImpresionVO impresion = new ImpresionVO();
			impresion.setEstado(EstadosImprimir.Creacion.getValorEnum());
			impresion.setFechaCreacion(new Date());
			impresion.setIdContrato(idContrato);
			impresion.setIdUsuario(idUsuario);
			impresion.setTipoDocumento(tipoDocumento);
			impresion.setTipoInterviniente(tipoInterviniente);
			impresion.setTipoTramite(tipoTramite);
			Long idImpresion = (Long) impresionDao.guardar(impresion);

			resultado = servicioImpresionTramiteTrafico.crearImpresionTramiteTrafico(numExpedientes, idImpresion);

			if (resultado != null && !resultado.getError()) {
				servicioEvolucionImpresion.guardarEvolucion(idImpresion, EstadosImprimir.Creacion.getNombreEnum(), null, null, idUsuario);

				servicioCola.crearSolicitud(idImpresion, obtenerNombreProceso(tipoDocumento), gestorPropiedades.valorPropertie(NOMBRE_HOST), tipoTramite, new BigDecimal(idUsuario), new BigDecimal(
						idContrato), null);

				String nombreDocumento = ponerNombreDocumento(tipoTramite, tipoDocumento, idImpresion, numExpedientes, tipoInterviniente);
				impresion.setNombreDocumento(nombreDocumento);
				impresionDao.actualizar(impresion);
				resultado.setNombreDocumento(nombreDocumento);
				resultado.setIdImpresion(idImpresion);
			}
		} catch (Throwable e) {
			log.error("Error al guardar los datos de la impresion", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Fallo al crear la petición de impresión");
		}
		return resultado;
	}

	private String ponerNombreDocumento(String tipoTramite, String tipoDocumento, Long idImpresion, String[] numExpedientes, String tipoInterviniente) {
		String nombreDocumento = "";
		if (TipoImpreso.DeclaracionResponsabilidadColegiado.toString().equals(tipoDocumento)) {
			nombreDocumento = TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiado.getNombreDocumento() + "_" + numExpedientes[0];
		} else if (TipoImpreso.DeclaracionResponsabilidadColegiadoConducir.toString().equals(tipoDocumento)) {
			nombreDocumento = TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiadoConducir.getNombreDocumento() + "_" + numExpedientes[0];
		} else if (TipoImpreso.SolicitudDuplicadoPermisoConducir.toString().equals(tipoDocumento)) {
			nombreDocumento = TipoDocumentoImpresiones.SolicitudDuplicadoPermisoConducir.getNombreDocumento() + "_" + numExpedientes[0];
		} else {
			if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equals(tipoTramite) || TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum().equals(tipoTramite)) {
				if (TipoImpreso.MatriculacionMandatoEspecifico.toString().equals(tipoDocumento)) {
					nombreDocumento = TipoDocumentoImpresiones.MandatoEspecifico.getNombreDocumento() + "_";
				} else {
					nombreDocumento = TipoDocumentoImpresiones.MandatoGenerico.getNombreDocumento() + "_";
				}

				if (TipoTramiteTrafico.PermisonInternacional.getValorEnum().equals(tipoTramite)) {
					nombreDocumento += TipoTramiteInterga.Permiso_Internacional.getValorEnum();
				} else if (TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum().equals(tipoTramite)) {
					nombreDocumento += TipoTramiteInterga.Duplicado_Permiso_Conducir.getValorEnum();
				}
				nombreDocumento += "_" + numExpedientes[0];
			} else {
				if (TipoImpreso.MatriculacionMandatoGenerico.toString().equals(tipoDocumento) && StringUtils.isNotBlank(tipoInterviniente)) {
					nombreDocumento = idImpresion + "_" + TipoDocumentoImpresiones.MandatoGenerico.getNombreDocumento() + "_" + tipoInterviniente;
				} else {
					nombreDocumento = idImpresion + "_" + obtenerNombreDocumento(tipoDocumento);
				}
			}
		}
		return nombreDocumento;
	}

	private String obtenerNombreProceso(String tipoDocumento) {
		if (TipoImpreso.MatriculacionMandatoEspecifico.toString().equals(tipoDocumento)) {
			return ProcesosEnum.IMP_MANDATO_ESPECIFICO.getNombreEnum();
		} else if (TipoImpreso.MatriculacionMandatoGenerico.toString().equals(tipoDocumento)) {
			return ProcesosEnum.IMP_MANDATO_GENERICO.getNombreEnum();
		} else if (TipoImpreso.MatriculacionBorradorPDF417.toString().equals(tipoDocumento)) {
			return ProcesosEnum.IMP_BORRADOR_PDF_417.getNombreEnum();
		} else if (TipoImpreso.MatriculacionPDF417.toString().equals(tipoDocumento)) {
			return ProcesosEnum.IMP_PDF_417.getNombreEnum();
		} else if (TipoImpreso.MatriculacionPDFPresentacionTelematica.toString().equals(tipoDocumento)) {
			return ProcesosEnum.IMP_PRESENTACION_TELEMATICA.getNombreEnum();
		} else if (TipoImpreso.MatriculacionListadoBastidores_2.toString().equals(tipoDocumento)) {
			return ProcesosEnum.IMP_RELACION_MATRICULAS.getNombreEnum();
		} else if (TipoImpreso.TransmisionModelo430.toString().equals(tipoDocumento)) {
			return ProcesosEnum.IMP_MODELO_430.getNombreEnum();
		} else if (TipoImpreso.DeclaracionJuradaExportacionTransito.toString().equals(tipoDocumento) || TipoImpreso.DeclaracionJuradaExtravioFicha.toString().equals(tipoDocumento)
				|| TipoImpreso.DeclaracionJuradaExtravioPermisoFicha.toString().equals(tipoDocumento) || TipoImpreso.DeclaracionJuradaExtravioPermisoLicencia.toString().equals(tipoDocumento)
				|| TipoImpreso.DeclaracionJuradaEntregaAnteriorPermiso.toString().equals(tipoDocumento) || TipoImpreso.DeclaracionResponsabilidadColegiado.toString().equals(tipoDocumento)
				|| TipoImpreso.DeclaracionResponsabilidadColegiadoConducir.toString().equals(tipoDocumento)) {
			return ProcesosEnum.IMP_DECLARACIONES.getNombreEnum();
		} else if (TipoImpreso.SolicitudDuplicadoPermisoConducir.toString().equals(tipoDocumento)) {
			return ProcesosEnum.IMP_SOLICITUDES.getNombreEnum();
		} else if (TipoImpreso.TxtNre.toString().equals(tipoDocumento)) {
			return ProcesosEnum.IMP_NRE.getNombreEnum();
		}
		return "";
	}

	private String obtenerNombreDocumento(String tipoDocumento) {
		if (TipoImpreso.MatriculacionMandatoEspecifico.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.MandatoEspecifico.getNombreDocumento();
		} else if (TipoImpreso.MatriculacionMandatoGenerico.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.MandatoGenerico.getNombreDocumento();
		} else if (TipoImpreso.MatriculacionBorradorPDF417.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.BorradorPDF417.getNombreDocumento();
		} else if (TipoImpreso.MatriculacionPDF417.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.PDF417.getNombreDocumento();
		} else if (TipoImpreso.MatriculacionPDFPresentacionTelematica.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.PDFPresentacionTelematica.getNombreDocumento();
		} else if (TipoImpreso.MatriculacionListadoBastidores_2.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.ListadoBastidores.getNombreDocumento();
		} else if (TipoImpreso.TransmisionModelo430.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.TransmisionModelo430.getNombreDocumento();
		} else if (TipoImpreso.DeclaracionJuradaExportacionTransito.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.DeclaracionJuradaExportacionTransito.getNombreDocumento();
		} else if (TipoImpreso.DeclaracionJuradaExtravioFicha.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.DeclaracionJuradaExtravioFicha.getNombreDocumento();
		} else if (TipoImpreso.DeclaracionJuradaExtravioPermisoFicha.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.DeclaracionJuradaExtravioPermisoFicha.getNombreDocumento();
		} else if (TipoImpreso.DeclaracionJuradaExtravioPermisoLicencia.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.DeclaracionJuradaExtravioPermisoLicencia.getNombreDocumento();
		} else if (TipoImpreso.DeclaracionJuradaEntregaAnteriorPermiso.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.DeclaracionJuradaEntregaAnteriorPermiso.getNombreDocumento();
		} else if (TipoImpreso.DeclaracionResponsabilidadColegiado.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiado.getNombreDocumento();
		} else if (TipoImpreso.DeclaracionResponsabilidadColegiadoConducir.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiadoConducir.getNombreDocumento();
		} else if (TipoImpreso.TxtNre.toString().equals(tipoDocumento)) {
			return TipoDocumentoImpresiones.TXTNRE.getNombreDocumento();
		}
		return "";
	}

	@Override
	@Transactional
	public void generado(Long idImpresion, String nombreDocumento, List<BigDecimal> listaTramitesError, Long idUsuario) {
		try {
			ImpresionVO impresion = impresionDao.getImpresion(idImpresion);
			impresion.setNombreDocumento(nombreDocumento);
			impresion.setEstado(EstadosImprimir.Generado.getValorEnum());
			impresion.setFechaModificacion(new Date());
			impresion.setFechaGenerado(new Date());
			impresion.setMensaje(GENERADO);
			impresionDao.actualizar(impresion);
			servicioEvolucionImpresion.guardarEvolucion(idImpresion, EstadosImprimir.Generado.getNombreEnum(), nombreDocumento, listaTramitesError, idUsuario);
		} catch (Exception e) {
			log.error("Error al obtener los datos de la impresion con id:" + idImpresion, e);
		}
	}

	@Override
	@Transactional
	public void eliminar(String nombreDocumento) {
		try {
			ImpresionVO impresion = getDatosPorNombreDocumentoSinEliminado(nombreDocumento);
			if (impresion != null) {
				impresion.setEstado(EstadosImprimir.Eliminado.getValorEnum());
				impresion.setMensaje(ELIMINADO);
				impresionDao.actualizar(impresion);
				servicioEvolucionImpresion.guardarEvolucion(impresion.getIdImpresion(), EstadosImprimir.Eliminado.getNombreEnum(), nombreDocumento, null, null);
				servicioImpresionTramiteTrafico.eliminarImpresionTramiteTrafPorIdImpresion(impresion.getIdImpresion());
			}
		} catch (Exception e) {
			log.error("Error al eliminar la impresion con nombre documento:" + nombreDocumento, e);
		}
	}

	@Override
	@Transactional
	public void imprimido(ImpresionVO impresion, Long idUsuario) {
		try {
			if (EstadosImprimir.Generado.getValorEnum().equals(impresion.getEstado())) {
				impresion.setEstado(EstadosImprimir.Descargado.getValorEnum());
				impresion.setFechaModificacion(new Date());
				impresion.setMensaje(IMPRESO);
				impresionDao.actualizar(impresion);
			}
			servicioEvolucionImpresion.guardarEvolucion(impresion.getIdImpresion(), EstadosImprimir.Descargado.getNombreEnum(), null, null, idUsuario);
		} catch (Exception e) {
			log.error("Error al obtener los datos de la impresion con id:" + impresion.getIdImpresion(), e);
		}
	}

	@Override
	@Transactional
	public void finalizarConError(Long idImpresion, Long idUsuario, String mensaje) {
		try {
			ImpresionVO impresion = getDatosImpresion(idImpresion);
			if (EstadosImprimir.Creacion.getValorEnum().equals(impresion.getEstado())) {
				impresion.setEstado(EstadosImprimir.Finalizado_Error.getValorEnum());
				impresion.setFechaModificacion(new Date());
				impresion.setMensaje(mensaje);
				impresionDao.actualizar(impresion);
				servicioEvolucionImpresion.guardarEvolucion(impresion.getIdImpresion(), EstadosImprimir.Finalizado_Error.getNombreEnum(), null, null, idUsuario);
			}
		} catch (Exception e) {
			log.error("Error al obtener los datos de la impresion para Finalizar con Error", e);
		}
	}

	@Override
	public ResultadoBean prepararFinalizarImpresion(List<BigDecimal> listaExpedientes, ResultadoImpresionBean resultadoImpr, String tipoDocumento, ContratoVO contrato, String tipoTramite,
			String nombreFichero, boolean firmarDocumento) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		if (firmarDocumento) {
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			byte[] pdfFirmado = utilesViafirma.firmarPdfLicenciasCam(resultadoImpr.getPdf(), contrato.getColegiado().getAlias());
			if (pdfFirmado != null) {
				resultadoImpr.setPdf(pdfFirmado);
			}
		}
		if (resultado != null && !resultado.getError()) {
			resultado = guardarFichero(resultadoImpr.getPdf(), nombreFichero, ConstantesGestorFicheros.IMPRESION_PROCESO, tipoDocumento, ConstantesGestorFicheros.EXTENSION_PDF);
			if (resultado != null && !resultado.getError()) {
				List<BigDecimal> expedienteImpr = expedientesImprimidos(listaExpedientes, resultadoImpr.getListaTramitesError());
				resultadoImpr.setListaTramitesOk(expedienteImpr);
				if (StringUtils.isNotBlank(resultadoImpr.getNube())) {
					guardarTxt(resultadoImpr.getNube(), nombreFichero, ConstantesGestorFicheros.IMPRESION_PROCESO, tipoDocumento);
				}
				if (StringUtils.isNotBlank(resultadoImpr.getNubeConN())) {
					guardarTxt(resultadoImpr.getNubeConN(), nombreFichero + "_N", ConstantesGestorFicheros.IMPRESION_PROCESO, tipoDocumento);
				}
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoBean finalizarImpresion(Long idImpresion, ResultadoImpresionBean resultadoImpr, String tipoDocumento, Long idUsuario, ContratoVO contrato, String tipoTramite,
			String nombreFichero) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		if (TipoImpreso.MatriculacionPDF417.toString().equals(tipoDocumento)) {
			String tipoTramiteCreditos = getTipoCreditosACobrar(tipoTramite, resultadoImpr.getTipoTransferencia());
			resultado = servicioCredito.creditosDisponibles(Long.valueOf(resultadoImpr.getListaTramitesOk().size()), contrato.getIdContrato(), tipoTramiteCreditos);
			if (resultado != null && !resultado.getError()) {
				resultado = servicioCredito.descontarCreditos(tipoTramiteCreditos, contrato.getIdContrato(), idUsuario, resultadoImpr.getListaTramitesOk());
			}
		}
		if (resultado != null && !resultado.getError()) {
			cambiarEstadoTramite(resultadoImpr.getListaTramitesOk(), tipoDocumento, tipoTramite, idUsuario);
			//bloquearComboTasa(resultadoImpr.getListaTramitesOk(), tipoDocumento, tipoTramite, idUsuario);
			generado(idImpresion, nombreFichero, resultadoImpr.getListaTramitesError(), idUsuario);
		} else {
			finalizarConError(idImpresion, idUsuario, resultado.getMensaje());
		}
		return resultado;
	}

	private String getTipoCreditosACobrar(String tipoTramite, String tipoTransferencia) {
		String tipoTramiteCreditos = tipoTramite;
		if (StringUtils.isNotBlank(tipoTransferencia) &&
			TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramiteCreditos) && (TipoTransferencia.tipo4.getValorEnum().equals(tipoTransferencia) || TipoTransferencia.tipo5
					.getValorEnum().equals(tipoTransferencia))) {
			tipoTramiteCreditos = TipoTramiteTrafico.Baja.getValorEnum();
		}
		return tipoTramiteCreditos;
	}

	private StringBuffer crearCorreo(ResultadoImpresionBean resultado, String nombreDocumento, String tipoImpreso) {
		StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
		if (resultado != null && resultado.getError()) {
			texto.append(textoErroneo(resultado.getListaTramites(), tipoImpreso, resultado.getTipoTramite()));
		} else {
			if (resultado.getListaTramitesError() != null && !resultado.getListaTramitesError().isEmpty()) {
				texto.append(textoSemiCorrecto(resultado.getListaTramites(), resultado.getListaTramitesError(), nombreDocumento, tipoImpreso, resultado.getTipoTramite()));
			} else {
				texto.append(textoCorrecto(resultado.getListaTramites(), nombreDocumento, tipoImpreso, resultado.getTipoTramite()));
			}
		}
		texto.append("</span>");
		return texto;
	}

	@Override
	public void gestionEnviarCorreo(ResultadoImpresionBean resultado, BigDecimal idContrato, String tipoTramite, String impreso) {
		boolean enviarCorreo = true;
		ContratoVO contratoVO = servicioContrato.getContrato(idContrato.longValue());
		if (contratoVO.getCorreosTramites() != null && !contratoVO.getCorreosTramites().isEmpty()) {
			for (CorreoContratoTramiteVO correoContratoTramite : contratoVO.getCorreosTramites()) {
				if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite()) && impreso.equalsIgnoreCase(correoContratoTramite.getTipoImpresion()) && "NO"
						.equalsIgnoreCase(correoContratoTramite.getEnviarCorreoImpresion())) {
					enviarCorreo = false;
					break;
				}
			}
		}

		// boolean enviarCorreo = true;
		// String contratosNoEnviar = gestorPropiedades.valorPropertie("contrato.no.enviar.impresion");
		// if (StringUtils.isNotBlank(contratosNoEnviar)) {
		// String[] contratos = contratosNoEnviar.split(",");
		// if (contratos != null && contratos.length > 0) {
		// for (String contrato : contratos) {
		// if (idContrato.equals(contrato)) {
		// enviarCorreo = false;
		// break;
		// }
		// }
		// }
		// }

		if (enviarCorreo) {
			enviarCorreo(resultado, tipoTramite, impreso, contratoVO);
		}
	}

	@Override
	@Transactional
	public void gestionEnviarCorreoCau(ResultadoImpresionBean resultado, BigDecimal idImpresion) {
		enviarCorreoCau(resultado, idImpresion);
	}

	private void enviarCorreoCau(ResultadoImpresionBean resultado, BigDecimal idImpresion) {
		try {
			TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(resultado.getListaTramites().get(0), Boolean.TRUE);
			if (tramite != null) {
				ImpresionVO impresionVO = getDatosImpresion(idImpresion.longValue());
				if (impresionVO != null) {
					// String recipent = "incidencias.oegam@gestoresmadrid.org";
					String recipent = "jose.rumbo@globaltms.es";
					String subject = "Impresión PDF 417 - " + tramite.getVehiculo().getMatricula() + " - " + tramite.getNumExpediente();
					String texto = "Ficheros PDF y TXT de la impresión PDF 417 del vehículo " + tramite.getVehiculo().getMatricula() + "con número de expediente " + tramite.getNumExpediente()
							+ " por parte del CAU.";
					String nombreZip = impresionVO.getNombreDocumento();
					if (StringUtils.isNotBlank(tramite.getVehiculo().getMatricula())) {
						nombreZip = tramite.getVehiculo().getMatricula();
					}
					FicheroBean zip = obtenerZip(impresionVO, nombreZip);
					servicioCorreo.enviarCorreo(texto, null, null, subject, recipent, null, null, null, zip);
					gestorDocumentos.borraFicheroSiExisteConExtension(ConstantesGestorFicheros.IMPRESION_PROCESO, impresionVO.getTipoDocumento(), null, nombreZip,
							ConstantesGestorFicheros.EXTENSION_ZIP);
				}
			}
		} catch (Throwable e) {
			log.error("Error al enviar el correo de impresion", e);
		}
	}

	private FicheroBean obtenerZip(ImpresionVO impresion, String nombreZip) {
		FicheroBean fichero = new FicheroBean();
		HashMap<String, byte[]> map = new HashMap<String, byte[]>();
		try {
			File ficheroTxt = gestorDocumentos.buscarFicheroPorNombreTipoYDia(ConstantesGestorFicheros.IMPRESION_PROCESO, impresion.getTipoDocumento(), utilesFecha.getFechaFracionada(impresion
					.getFechaGenerado()), impresion.getNombreDocumento(), ConstantesGestorFicheros.EXTENSION_TXT);
			File ficheroTxtConN = gestorDocumentos.buscarFicheroPorNombreTipoYDia(ConstantesGestorFicheros.IMPRESION_PROCESO, impresion.getTipoDocumento(), utilesFecha.getFechaFracionada(impresion
					.getFechaGenerado()), impresion.getNombreDocumento() + "_N", ConstantesGestorFicheros.EXTENSION_TXT);
			File ficheroPdf = gestorDocumentos.buscarFicheroPorNombreTipoYDia(ConstantesGestorFicheros.IMPRESION_PROCESO, impresion.getTipoDocumento(), utilesFecha.getFechaFracionada(impresion
					.getFechaGenerado()), impresion.getNombreDocumento(), ConstantesGestorFicheros.EXTENSION_PDF);

			map.put(nombreZip + ConstantesGestorFicheros.EXTENSION_TXT, gestorDocumentos.transformFiletoByte(ficheroTxt));
			map.put(nombreZip + ConstantesGestorFicheros.EXTENSION_PDF, gestorDocumentos.transformFiletoByte(ficheroPdf));
			if (ficheroTxtConN != null) {
				map.put(nombreZip + "_N" + ConstantesGestorFicheros.EXTENSION_TXT, gestorDocumentos.transformFiletoByte(ficheroTxtConN));
			}

			File zip = empaquetarZIPCau(map, nombreZip);
			fichero.setFichero(zip);
			fichero.setNombreYExtension(nombreZip + ConstantesGestorFicheros.EXTENSION_ZIP);
		} catch (Throwable e) {
			log.error("Error al enviar el correo de impresion", e);
		}
		return fichero;
	}

	private void enviarCorreo(ResultadoImpresionBean resultado, String tipoTramite, String impreso, ContratoVO contratoVO) {
		String subject = "";
		String recipent = "";
		try {
			if (resultado != null && resultado.getError()) {
				subject = "Impresión generada incorrectamente";
			} else {
				if (resultado.getListaTramitesError() != null && !resultado.getListaTramitesError().isEmpty()) {
					subject = "Impresión generada parcialmente";
				} else {
					subject = "Impresión generada correctamente";
				}
			}

			StringBuffer texto = crearCorreo(resultado, resultado.getNombreDocumento(), impreso);

			recipent = contratoVO.getCorreoElectronico();
			if (contratoVO.getCorreosTramites() != null && !contratoVO.getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : contratoVO.getCorreosTramites()) {
					if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())) {
						recipent = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}

			servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipent, null, null, null, null);
		} catch (Throwable e) {
			log.error("Error al enviar el correo de impresion", e);
		}
	}

	@Override
	@Transactional
	public File descargarFichero(String nombreDocumento, Long idUsuario) {
		try {
			ImpresionVO impresion = getDatosImpresionPorNombreDocumento(nombreDocumento);
			if (impresion != null) {
				if (TipoDocumentoImpresiones.TXTNRE.getValorEnum().equals(impresion.getTipoDocumento())) {
					File fichero = gestorDocumentos.buscarFicheroPorNombreTipoYDia(ConstantesGestorFicheros.IMPRESION_PROCESO, impresion.getTipoDocumento(), utilesFecha.getFechaFracionada(impresion
							.getFechaGenerado()), nombreDocumento, ConstantesGestorFicheros.EXTENSION_TXT);
					imprimido(impresion, idUsuario);
					return fichero;
				} else {
					File fichero = gestorDocumentos.buscarFicheroPorNombreTipoYDia(ConstantesGestorFicheros.IMPRESION_PROCESO, impresion.getTipoDocumento(), utilesFecha.getFechaFracionada(impresion
							.getFechaGenerado()), nombreDocumento, ConstantesGestorFicheros.EXTENSION_PDF);
					if (fichero != null) {
						String descargarTxt = gestorPropiedades.valorPropertie("descargar.txt.pdf");
						String usuarioDescargarTxt = gestorPropiedades.valorPropertie("descargar.txt.pdf.usuarios");
						if (("SI".equals(descargarTxt) && TipoDocumentoImpresiones.PDF417.getValorEnum().equals(impresion.getTipoDocumento())) || usuarioDescargarPdf(usuarioDescargarTxt, idUsuario)) {
							File ficheroTxt = gestorDocumentos.buscarFicheroPorNombreTipoYDia(ConstantesGestorFicheros.IMPRESION_PROCESO, impresion.getTipoDocumento(), utilesFecha.getFechaFracionada(
									impresion.getFechaGenerado()), nombreDocumento, ConstantesGestorFicheros.EXTENSION_TXT);
							if (ficheroTxt != null) {
								ArrayList<File> listaFicheros = new ArrayList<>();
								listaFicheros.add(fichero);
								listaFicheros.add(ficheroTxt);
								File ficheroTxtConN = gestorDocumentos.buscarFicheroPorNombreTipoYDia(ConstantesGestorFicheros.IMPRESION_PROCESO, impresion.getTipoDocumento(), utilesFecha
										.getFechaFracionada(impresion.getFechaGenerado()), nombreDocumento + "_N", ConstantesGestorFicheros.EXTENSION_TXT);
								if (ficheroTxtConN != null) {
									listaFicheros.add(ficheroTxtConN);
								}
								fichero = empaquetarZIP(listaFicheros, nombreDocumento);
							}
						}
						imprimido(impresion, idUsuario);
						return fichero;
					}
				}
			}
		} catch (Throwable e) {
			log.error("Error a la hora de descargar el fichero con nombre de documento " + nombreDocumento, e);
		}
		return null;
	}

	private boolean usuarioDescargarPdf(String usuarios, Long idUsuario) {
		if (StringUtils.isNotBlank(usuarios)) {
			String[] listaUsuarios = usuarios.split(",");
			if (listaUsuarios != null && listaUsuarios.length > 0) {
				for (String usuario : listaUsuarios) {
					if (usuario.equals(idUsuario.toString())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private File empaquetarZIP(ArrayList<File> listaFicheros, String nombreDocumento) {
		File zipGenerado = null;
		FicheroBean ficherozip = new FicheroBean();
		ficherozip.setListaFicheros(listaFicheros);
		ficherozip.setTipoDocumento(ConstantesGestorFicheros.IMPRESION_PROCESO);
		ficherozip.setSubTipo(TipoDocumentoImpresiones.PDF417.getValorEnum());
		ficherozip.setNombreZip(nombreDocumento);
		try {
			zipGenerado = gestorDocumentos.empaquetarEnZipFile(ficherozip);
		} catch (IOException | OegamExcepcion e) {
			e.printStackTrace();
		}
		return zipGenerado;
	}

	private File empaquetarZIPCau(HashMap<String, byte[]> listaFicheros, String nombreDocumento) {
		File zipGenerado = null;
		try {
			FicheroBean ficherozip = new FicheroBean();
			ficherozip.setListaByte(listaFicheros);
			ficherozip.setTipoDocumento(ConstantesGestorFicheros.IMPRESION_PROCESO);
			ficherozip.setSubTipo(TipoDocumentoImpresiones.PDF417.getValorEnum());
			ficherozip.setNombreZip(nombreDocumento);
			zipGenerado = gestorDocumentos.empaquetarEnZipByte(ficherozip);
		} catch (IOException | OegamExcepcion e) {
			e.printStackTrace();
		}
		return zipGenerado;
	}

	@Override
	@Transactional
	public ResultadoBean eliminarFichero(String nombreDocumento) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			ImpresionVO impresion = getDatosPorNombreDocumentoGeneradoYDescargado(nombreDocumento);
			if (impresion != null) {
				File fichero = gestorDocumentos.buscarFicheroPorNombreTipoYDia(ConstantesGestorFicheros.IMPRESION_PROCESO, impresion.getTipoDocumento(), utilesFecha.getFechaFracionada(impresion
						.getFechaGenerado()), nombreDocumento, ConstantesGestorFicheros.EXTENSION_PDF);
				if (fichero != null) {
					gestorDocumentos.borradoRecursivo(fichero);
					eliminar(nombreDocumento);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se encuentra el fichero.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se encuentra la referencia del fichero.");
			}
		} catch (Throwable e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error a la hora de eliminar el fichero");
			log.error("Error a la hora de eliminar el fichero con nombre de documento " + nombreDocumento, e);
		}
		return resultado;
	}

	private ResultadoBean guardarFichero(byte[] bytesFichero, String nombreFichero, String tipoDocumento, String subTipo, String extension) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(tipoDocumento);
			fichero.setSubTipo(subTipo);
			fichero.setSobreescribir(true);
			fichero.setSubCarpetaDia(true);

			fichero.setFecha(utilesFecha.getFechaActual());
			fichero.setNombreDocumento(nombreFichero);

			fichero.setFicheroByte(bytesFichero);
			fichero.setExtension(extension);
			gestorDocumentos.guardarByte(fichero);
		} catch (Throwable e) {
			log.error("Error al guardar el documento de impresion con nombre: " + nombreFichero, e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al guardar el documento generado de la impresion con nombre: " + nombreFichero);
		}
		return resultado;
	}

	private void guardarTxt(String nube, String nombreFichero, String tipoDocumento, String subTipo) {
		try {
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(tipoDocumento);
			fichero.setSubTipo(subTipo);
			fichero.setSobreescribir(true);
			fichero.setSubCarpetaDia(true);

			fichero.setFecha(utilesFecha.getFechaActual());
			fichero.setNombreDocumento(nombreFichero);

			fichero.setFicheroByte(nube.getBytes(StandardCharsets.UTF_8));
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
			gestorDocumentos.guardarByte(fichero);
		} catch (Throwable e) {
			log.error("Error al guardar el txt de la nube de impresion con nombre: " + nombreFichero, e);
		}
	}

	private void cambiarEstadoTramite(List<BigDecimal> listaExpedientes, String tipoDocumento, String tipoTramite, Long idUsuario) {
		for (BigDecimal numExpediente : listaExpedientes) {
			if (TipoImpreso.MatriculacionPDF417Matw.toString().equals(tipoDocumento)) {
				servicioTramiteTrafico.cambiarEstado(numExpediente, new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()), idUsuario);
			} else if (TipoImpreso.MatriculacionPDFPresentacionTelematica_MATEW.toString().equals(tipoDocumento)) {
				servicioTramiteTrafico.cambiarEstado(numExpediente, new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()), idUsuario);
			} else if (TipoImpreso.MatriculacionListadoBastidores.toString().equals(tipoDocumento) && (TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite) || TipoTramiteTrafico.Duplicado
					.getValorEnum().equals(tipoTramite))) {
				servicioTramiteTrafico.cambiarEstadoBajaDuplRelacionMatriculas(numExpediente, idUsuario);
			}
		}
	}

	/*private void bloquearComboTasa(List<BigDecimal> listaExpedientes, String tipoDocumento, String tipoTramite, Long idUsuario) {
		for (BigDecimal numExpediente : listaExpedientes) {
			TasaVO tasaVo = servicioComunTasa.getTasa(null, numExpediente, null);
			TramiteTrafTranVO tramiteTrafTran = servicioTramiteTrafico.getTramiteTransmisionVO(numExpediente, Boolean.FALSE);
			if(TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)
					&& TipoImpreso.MatriculacionPDF417Matw.toString().equals(tipoDocumento)
					&& EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTrafTran.getEstado().toString())
					&& !EstadoComboTasa.Bloqueado.getValorEnum().equals(tasaVo.getComboTasaBloqueado())) {
				servicioComunTasa.bloquearComboTasa(tasaVo.getCodigoTasa(), idUsuario);
			}
		}
	}*/

	private List<BigDecimal> expedientesImprimidos(List<BigDecimal> listaExpedientes, List<BigDecimal> listaExpedientesErroneos) {
		if (listaExpedientesErroneos != null && !listaExpedientesErroneos.isEmpty()) {
			List<BigDecimal> expedienteImprimidos = new ArrayList<>();
			for (BigDecimal numExpediente : listaExpedientes) {
				if (!listaExpedientesErroneos.contains(numExpediente)) {
					expedienteImprimidos.add(numExpediente);
				}
			}
			return expedienteImprimidos;
		}
		return listaExpedientes;
	}

	private String textoDocumento(String tipoDocumento) {
		String textoDocumento = "";
		if (TipoImpreso.MatriculacionPDFPresentacionTelematica_2.toString().equals(tipoDocumento)) {
			textoDocumento = "PDF Telemáticos";
		} else if (TipoImpreso.MatriculacionMandatoEspecifico.toString().equals(tipoDocumento)) {
			textoDocumento = "Mandatos Específicos";
		} else if (TipoImpreso.MatriculacionMandatoGenerico.toString().equals(tipoDocumento)) {
			textoDocumento = "Mandatos Genéricos";
		} else if (TipoImpreso.MatriculacionBorradorPDF417.toString().equals(tipoDocumento)) {
			textoDocumento = "PDF Borradores";
		} else if (TipoImpreso.MatriculacionPDF417.toString().equals(tipoDocumento)) {
			textoDocumento = "PDF 417";
		} else if (TipoImpreso.MatriculacionListadoBastidores_2.toString().equals(tipoDocumento)) {
			textoDocumento = "Modelo 430";
		} else if (TipoImpreso.TransmisionModelo430.toString().equals(tipoDocumento)) {} else {
			textoDocumento = tipoDocumento;
		}
		return textoDocumento;
	}

	private StringBuffer textoErroneo(List<BigDecimal> listaExpedientes, String tipoDocumento, String tipoTramite) {
		String tipoTramiteTexto = "";
		if (StringUtils.isNotBlank(tipoTramite)) {
			tipoTramiteTexto = " de " + TipoTramiteTrafico.convertirTexto(tipoTramite);
		}
		StringBuffer texto = new StringBuffer("La documentación de " + textoDocumento(tipoDocumento) + tipoTramiteTexto + " prevista para ser impresa no se ha generado.");
		texto.append("<br><br><u>Expediente no generados:</u><br>");
		for (BigDecimal numExpediente : listaExpedientes) {
			texto.append(" - " + numExpediente + "<br>");
		}
		texto.append("<br>Inténtelo de nuevo.");
		return texto;
	}

	private StringBuffer textoSemiCorrecto(List<BigDecimal> listaExpedientes, List<BigDecimal> listaErroneos, String nombreDocumento, String tipoDocumento, String tipoTramite) {
		String tipoTramiteTexto = "";
		if (StringUtils.isNotBlank(tipoTramite)) {
			tipoTramiteTexto = " de " + TipoTramiteTrafico.convertirTexto(tipoTramite);
		}
		StringBuffer texto = new StringBuffer("Disponible documentación de " + textoDocumento(tipoDocumento) + tipoTramiteTexto + " para ser descargada.");
		int listaTotales = listaExpedientes.size() - listaErroneos.size();
		texto.append("<br><br><u>Expediente generados " + listaTotales + " de " + listaExpedientes.size() + ":</u><br>");

		List<BigDecimal> expedienteImpr = expedientesImprimidos(listaExpedientes, listaErroneos);

		for (BigDecimal numExpediente : expedienteImpr) {
			texto.append(" - " + numExpediente + "<br>");
		}

		texto.append("<br><br><u>Expediente erróneos:</u><br>");
		for (BigDecimal numExpediente : listaErroneos) {
			texto.append(" - " + numExpediente + "<br>");
		}
		texto.append("<br>Nombre del documento para descargar: <b>" + nombreDocumento + "</b>");
		return texto;
	}

	private StringBuffer textoCorrecto(List<BigDecimal> listaExpedientes, String nombreDocumento, String tipoDocumento, String tipoTramite) {
		String tipoTramiteTexto = "";
		if (StringUtils.isNotBlank(tipoTramite)) {
			tipoTramiteTexto = " de " + TipoTramiteTrafico.convertirTexto(tipoTramite);
		}
		StringBuffer texto = new StringBuffer("Disponible documentación de " + textoDocumento(tipoDocumento) + tipoTramiteTexto + " para ser descargada.");
		if (listaExpedientes != null && !listaExpedientes.isEmpty()) {
			texto.append("<br><br><u>Expediente generados:</u><br>");
			for (BigDecimal numExpediente : listaExpedientes) {
				texto.append(" - " + numExpediente + "<br>");
			}
		}
		texto.append("<br>Nombre del documento para descargar: <b>" + nombreDocumento + "</b>");
		return texto;
	}

	@Override
	public List<ConsultaImpresionBean> convertirListaEnBeanPantallaConsulta(List<ImpresionVO> lista) {
		List<ConsultaImpresionBean> listaConsulta = new ArrayList<>();
		int posicion = 0;
		for (ImpresionVO impresion : lista) {
			ConsultaImpresionBean bean = new ConsultaImpresionBean();
			bean.setIdImpresion(impresion.getIdImpresion());
			bean.setNombreDocumento(impresion.getNombreDocumento());
			bean.setMensaje(impresion.getMensaje());
			bean.setTipoDocumento(impresion.getTipoDocumento());
			bean.setTipoTramite(TipoTramiteTrafico.convertirTexto(impresion.getTipoTramite()));
			bean.setFechaCreacion(impresion.getFechaCreacion());
			bean.setFechaGenerado(impresion.getFechaGenerado());
			bean.setNumColegiado(impresion.getContrato().getColegiado().getNumColegiado());
			bean.setEstado(EstadosImprimir.convertirTexto(impresion.getEstado()));
			bean.setIndice(posicion);
			listaConsulta.add(bean);
			posicion++;
		}
		return listaConsulta;
	}

	@Override
	@Transactional
	public ResultadoImpresionBean validarImpresionPantalla(String impreso, String tipoTramite, String[] numsExpedientes, BigDecimal idContrato, BigDecimal idUsuario) {
		ResultadoImpresionBean result = new ResultadoImpresionBean(Boolean.FALSE);
		TramiteTrafTranVO tramiteTran = null;
		String tipoCreditoACobrar = null;
		String tipoTransferencia = null;
		try {
			try {
				result = limitacionBorrador(impreso);
				if (result != null && result.getError()) {
					return result;
				}
				for (String numExpediente : numsExpedientes) {
					TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(new BigDecimal(numExpediente), Boolean.FALSE);
					if (tramite != null) {
						if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
							tramiteTran = servicioTramiteTrafico.getTramiteTransmisionVO(new BigDecimal(numExpediente), Boolean.FALSE);
							tipoTransferencia = tramiteTran.getTipoTransferencia();
						}

						if (tipoCreditoACobrar == null) {
							tipoCreditoACobrar = getTipoCreditosACobrar(tipoTramite, tipoTransferencia);
						}

						// No se pueden imprimir pdfs telemáticos para algunas jefaturas
						result = limitacionPdfTelematico(impreso, tipoTramite, tramite, tramiteTran);
						if (result != null && result.getError()) {
							return result;
						}

						if (TipoDocumentoImpresiones.PDF417.getValorEnum().equals(impreso)) {
							// Ver si cobran el mismo tipo de crédito para las CTIT
							if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
								if (tipoCreditoACobrar != null && !tipoCreditoACobrar.equals(getTipoCreditosACobrar(tipoTramite, tramiteTran.getTipoTransferencia()))) {
									result.setError(Boolean.TRUE);
									result.setMensaje("Los trámites de transmision seleccionados no cobran el mismo tipo de Créditos. Deben serlo para realizar impresiones en bloque.");
									return result;
								}

							}
						} else if (TipoImpreso.InformeBajaTelematica.getValorEnum().equals(impreso)) {
							// Valdiaciones para descargar los informes de bajas telemáticas
							TramiteTrafBajaVO tramiteBaja = servicioTramiteTrafico.getTramiteBajaVO(new BigDecimal(numExpediente), Boolean.FALSE);
							if (tramiteBaja.getAutoInformeBtv() == null || !"SI".equals(tramiteBaja.getAutoInformeBtv())) {
								result.setError(Boolean.TRUE);
								result.setMensaje("El tramite con número: " + numExpediente
										+ " no tiene autorizado la impresión del informe telemático. Debe de ir a su jefatura correspondiente y presentar la documentación.");
								return result;
							}

							if (!MotivoBaja.DefE.getValorEnum().equals(tramiteBaja.getMotivoBaja()) && !MotivoBaja.DefTC.getValorEnum().equals(tramiteBaja.getMotivoBaja())) {
								result.setError(Boolean.TRUE);
								result.setMensaje("El tramite con numero: " + numExpediente + " no corresponde a una baja telemática");
								return result;
							}
						}

						// Comprobamos que está en un estado válido para imprimir ese tipo de documento
						if (!estaEnEstadoValidoParaImprimirEsteTipo(tramite, impreso)) {
							result.setError(Boolean.TRUE);
							result.setMensaje("No se puede imprimir el impreso " + impreso + " para los trámites seleccionados, por no estar en un estado válido para este impreso");
							return result;
						}

						// Comprobamos si tiene datos sensibles
						if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite) || TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
							boolean existeDatoSensible = servicioComunDatosSensibles.existeDatosSensible(tramite, idUsuario);
							if (existeDatoSensible) {
								log.error("Hay datos sensibles en el trámite: " + tramite.getNumExpediente());
								result.setError(Boolean.TRUE);
								result.setMensaje("Existen problemas con la impresión. Contacte urgentemente con el Colegio");
								return result;
							}
						}

					} else {
						result.setError(Boolean.TRUE);
						result.setMensaje("No han encontrado todos los expedientes.");
						return result;
					}
				}

				// Comprobamos que hay créditos suficientes para realizar la operación
				if (TipoDocumentoImpresiones.PDF417.getValorEnum().equals(impreso)) {
					ResultadoBean resultado = servicioCredito.creditosDisponibles(Long.valueOf(numsExpedientes.length), idContrato.longValue(), tipoCreditoACobrar);
					if (resultado != null && resultado.getError()) {
						result.setError(Boolean.TRUE);
						result.setMensaje("No dispone de suficientes créditos para realizar esta operación.");
						return result;
					}

				}
			} catch (Exception e) {
				log.error("Error al validar las impresiones.", e);
				result.setError(Boolean.TRUE);
				result.setMensaje("Error a la hora de validar las impresiones.");
			}
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Ha sucedido un error a la ahora de validar la impresión.");
			log.error("Ha sucedido un error a la ahora de validar la impresión.", e);
			return result;
		}
		return result;
	}

	private boolean estaEnEstadoValidoParaImprimirEsteTipo(TramiteTraficoVO tramite, String impreso) {
		if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramite.getTipoTramite())) {
			if (TipoImpreso.MatriculacionPDF417.toString().equals(impreso)) {
				if (!new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Validado_PDF.getValorEnum()).equals(tramite
						.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()).equals(tramite.getEstado())
						&& !new BigDecimal(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum()).equals(tramite.getEstado())) {
					return false;
				}
			} else if (TipoImpreso.MatriculacionPDFPresentacionTelematica.toString().equals(impreso) || TipoImpreso.MatriculacionPermisoTemporalCirculacion.toString().equals(impreso)
					|| TipoImpreso.FichaTecnica.toString().equals(impreso)) {
				if (!new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(
						EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado
								.getValorEnum()).equals(tramite.getEstado())) {
					return false;
				}
			}
		} else {
			if (TipoImpreso.MatriculacionPDFPresentacionTelematica.toString().equals(impreso) || TipoImpreso.MatriculacionPermisoTemporalCirculacion.toString().equals(impreso)
					|| TipoImpreso.FichaTecnica.toString().equals(impreso)) {
				if (!new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(
						EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado
								.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum()).equals(tramite.getEstado())) {
					return false;
				}
			} else if (TipoImpreso.TransmisionDocumentosTelematicos.toString().equals(impreso)) {
				if (!new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(
						EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Informe_Telematico.getValorEnum())
								.equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()).equals(tramite.getEstado())) {
					return false;
				}
			} else if (TipoImpreso.ConsultaEITV.toString().equals(impreso)) {
				if (!new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()).equals(tramite.getEstado())) {
					return false;
				}
			} else if (TipoImpreso.InformeBajaTelematica.toString().equals(impreso)) {
				if (!new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(
						EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado
								.getValorEnum()).equals(tramite.getEstado())) {
					return false;
				}
			} else if (TipoImpreso.MatriculacionListadoBastidores.toString().equals(impreso)) {
				if (!new BigDecimal(EstadoTramiteTrafico.Finalizado_Excel.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Finalizado_Excel_Impreso.getValorEnum())
						.equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(
								EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
										.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()).equals(tramite
												.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum()).equals(tramite
														.getEstado())) {
					return false;
				}
			}
		}
		return true;
	}

	private ResultadoImpresionBean limitacionBorrador(String impreso) {
		ResultadoImpresionBean result = new ResultadoImpresionBean(Boolean.FALSE);
		if (TipoDocumentoImpresiones.BorradorPDF417.getValorEnum().equals(impreso)) {
			result.setError(Boolean.TRUE);
			result.setMensaje("No se permite la impresión de borradores de pdf.");
		}
		return result;
	}

	private ResultadoImpresionBean limitacionPdfTelematico(String impreso, String tipoTramite, TramiteTraficoVO tramite, TramiteTrafTranVO tramiteTra) {
		ResultadoImpresionBean result = new ResultadoImpresionBean(Boolean.FALSE);
		String imprimirPdfTelemtaico = gestorPropiedades.valorPropertie("imprimir.pdf.telematico");
		if (imprimirPdfTelemtaico == null || !"SI".equals(imprimirPdfTelemtaico)) {
			if (TipoDocumentoImpresiones.PDFPresentacionTelematica.getValorEnum().equals(impreso)) {
				if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
					if (comprobarSiEsJefatura(tramiteTra.getJefaturaTrafico().getJefaturaProvincial(), "jefauras.imprimir.todos.impr.ctit") && "SI".equals(tramiteTra.getImprPermisoCircu())) {
						result.setError(Boolean.TRUE);
						result.setMensaje("No se permite la impresión de pdf telemáticos para la jefatura de alguno de los trámites.");
						return result;
					}
				} else if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite) &&
						comprobarSiEsJefatura(tramite.getJefaturaTrafico().getJefaturaProvincial(), "jefauras.imprimir.todos.impr.matw")) {
					result.setError(Boolean.TRUE);
					result.setMensaje("No se permite la impresión de pdf telemáticos para la jefatura de alguno de los trámites.");
					return result;
				}
			}
		}
		return result;
	}

	private boolean comprobarSiEsJefatura(String jefatura, String nombrePropertie) {
		String sJefaturasAuto = gestorPropiedades.valorPropertie(nombrePropertie);
		if (sJefaturasAuto != null && !sJefaturasAuto.isEmpty()) {
			String[] jefaturasAuto = sJefaturasAuto.split(",");
			for (String jefaturoProv : jefaturasAuto) {
				if (jefaturoProv.equals(jefatura)) {
					return true;
				}
			}
		}
		return false;
	}
}