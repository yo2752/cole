package org.gestoresmadrid.oegam2comun.impresion.model.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.dao.MatriculaVehiculoDaoInt;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.accionTramite.model.service.ServicioAccionTramite;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresion;
import org.gestoresmadrid.oegam2comun.impresion.view.dto.ImprimirTramiteTraficoDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.BorrarFicherosRecursivoThread;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import escrituras.beans.ResultValidarTramitesImprimir;
import trafico.beans.utiles.ParametrosPegatinaMatriculacion;
import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import trafico.servicio.interfaz.ServicioImprimirTraficoInt;
import trafico.utiles.PdfMaker;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoImpreso;
import trafico.utiles.enumerados.TipoTramiteMatriculacion;
import trafico.utiles.enumerados.TipoTransferencia;
import trafico.utiles.enumerados.TipoTransferenciaActual;
import trafico.utiles.imprimir.PreferenciasEtiquetas;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.propiedades.PropertiesConstantes;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service
public class ServicioImpresionImpl implements ServicioImpresion {

	private static final long serialVersionUID = -6911316678520521606L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresion.class);

	@Autowired
	private ServicioCredito servicioCreditoImpl;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTraficoImpl;

	@Autowired
	private ServicioImprimirTraficoInt servicioImprimirTraficoImpl;

	@Autowired
	private ServicioDatosSensibles servicioDatosSensiblesImpl;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioAccionTramite servicioAccionTramiteImpl;

	@Autowired
	private MatriculaVehiculoDaoInt matriculaVehiculoDao;

	@Autowired
	private ServicioTramiteTraficoBaja servicioTramiteTraficoBaja;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Override
	public ResultBean tratarResultSiNulo(ResultBean result) {
		if (result == null) {
			result = new ResultBean();
			result.setError(true);
			result.addMensajeALista("El tipo indicado no es un tipo válido para imprimir");
		}
		return result;
	}

	@Override
	public ParametrosPegatinaMatriculacion getEtiquetasParametros(TipoTramiteTrafico tipoTramite, String numColegiado) {
		ParametrosPegatinaMatriculacion preferenciasEtiquetas = null;
		if (tipoTramite == TipoTramiteTrafico.Matriculacion) {

			ResultBean resultadoPreferencias = null;
			try {
				resultadoPreferencias = PreferenciasEtiquetas.cargarPreferencias(utilesColegiado.getNumColegiadoSession());
			} catch (OegamExcepcion e) {
				log.error("No se han podido recuperar las preferencias para las etiquetas de matriculación");
				return null;
			}

			if (resultadoPreferencias != null && resultadoPreferencias.getError()) {
				preferenciasEtiquetas = new ParametrosPegatinaMatriculacion();
			} else {
				preferenciasEtiquetas = (ParametrosPegatinaMatriculacion) resultadoPreferencias.getAttachment(ConstantesTrafico.PREFERENCIAS);
			}
		}
		return preferenciasEtiquetas;
	}

	@Override
	public ResultBean generarSituacionCreditos(ImprimirTramiteTraficoDto imprimirTramiteTraficoDto, BigDecimal idContrato) {
		TipoTramiteTrafico tipoTramite = servicioCreditoImpl.getTipoCreditosACobrar(imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite(), imprimirTramiteTraficoDto
				.getResultBeanImprimir().getTipoTransmisionActual(), imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTransmision());

		return servicioCreditoImpl.validarCreditos(tipoTramite.getValorEnum(), idContrato, new BigDecimal(1));
	}

	@Override
	public Boolean esTransmisionElectronica(TipoTramiteTrafico tipoTramite) {
		if (tipoTramite == TipoTramiteTrafico.TransmisionElectronica) {
			return true;
		}
		return false;
	}

	@Override
	public ResultBean comprobarDatosObligatorios(String listaChecksConsultaTramite, String impreso) {
		ResultBean result = null;
		if (listaChecksConsultaTramite != null && listaChecksConsultaTramite.equals("")) {
			result = new ResultBean(true, "No existen expedientes seleccionados");
			return result;
		}

		if (impreso == null || impreso.equals("")) {
			result = new ResultBean(true, "No se ha seleccionado el tipo de documento a imprimir");
			return result;
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	public ResultBean comprobarImpresion(String impreso, ImprimirTramiteTraficoDto imprimirTramiteTraficoDto, String[] numsExpedientes, BigDecimal idContrato, BigDecimal idUsuario) {
		BigDecimal[] numerosExpedientes = utiles.convertirStringArrayToBigDecimal(numsExpedientes);
		ResultBean result = null;
		try {
			// Mantis 13510: esta comprobacion se hacia para cualquier tipo de impreso y en la consulta
			if (impreso.equals("PDF417") && imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite() == TipoTramiteTrafico.TransmisionElectronica) {
				TipoTransferencia tipoTransferencia = TipoTransferencia.convertir(servicioTramiteTraficoImpl.getMismoTipoCreditoTramiteTransmision(numerosExpedientes, imprimirTramiteTraficoDto
						.getResultBeanImprimir().getTipoTramite()));
				if (tipoTransferencia == null) {
					result = new ResultBean(true, "Los trámites de transmision seleccionados no cobran el mismo tipo de Créditos. Deben serlo para realizar impresiones en bloque.");
					return result;
				}
			}

			// Comprobamos que hay créditos suficientes para realizar la operación
			if (impreso.equals("PDF417")) {
				TipoTramiteTrafico tipoTramiteCreditos = servicioCreditoImpl.getTipoCreditosACobrar(imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite(), imprimirTramiteTraficoDto
						.getResultBeanImprimir().getTipoTransmisionActual(), imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTransmision());

				ResultBean rb = servicioCreditoImpl.validarCreditos(tipoTramiteCreditos.toString(), idContrato, new BigDecimal(numsExpedientes.length));
				if (rb.getError()) {
					result = new ResultBean(true, "No dispone de suficientes créditos para realizar esta operación.");
					return result;
				}

			}

			// Comprobamos que está en un estado válido para imprimir ese tipo de documento
			if (!servicioImprimirTraficoImpl.estaEnEstadoValidoParaImprimirEsteTipo(numsExpedientes, impreso)) {
				result = new ResultBean(true, "No se puede imprimir el impreso " + impreso + " para los trámites seleccionados, por no estar en un estado válido para este impreso");
				return result;
			}

			// Comprobamos que si es un trámite de matriculación y se quiere sacar el PDF 417, está finalizado PDF.
			if (imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite() == TipoTramiteTrafico.Matriculacion && imprimirTramiteTraficoDto.getResultBeanImprimir()
					.getTipoTramiteMatriculacion() == null && !servicioImprimirTraficoImpl.estaEnEstadoValidoParaImprimirMATE(numsExpedientes, impreso)) {
				result = new ResultBean(true, "No se puede imprimir el impreso " + impreso + " para los trámites de MATE seleccionados, por no estar en un estado válido para este impreso");
				return result;
			}

			// Comprobamos si tiene datos sensibles
			if (imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite() == TipoTramiteTrafico.Matriculacion || imprimirTramiteTraficoDto.getResultBeanImprimir()
					.getTipoTramite() == TipoTramiteTrafico.Transmision || imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite() == TipoTramiteTrafico.TransmisionElectronica) {
				List<String> expedientesSensibles = servicioDatosSensiblesImpl.existenDatosSensiblesPorExpedientes(numerosExpedientes, idUsuario);
				if (expedientesSensibles != null && !expedientesSensibles.isEmpty()) {
					for (String exp : expedientesSensibles) {
						log.error("Hay datos sensibles en el trámite: " + exp);
					}
					result = new ResultBean(true, "Existen problemas con la impresión. Contacte urgentemente con el Colegio");
					return result;
				}
			}

			// Comprobamos si es un tramite de baja telematica sea de los motivos correctos
			if (TipoImpreso.InformeBajaTelematica.toString().equals(impreso)) {
				result = servicioTramiteTraficoBaja.esMotivoCorrectoBajasTelematicas(numsExpedientes);
				if (result != null && result.getError()) {
					return result;
				}
			}
			// Comprobamos que si es un tramite de baja telematica y quiere imprimir el inform todos los tramites estan autorizados
			if (TipoImpreso.InformeBajaTelematica.toString().equals(impreso)) {
				result = servicioTramiteTraficoBaja.estanAutorizadosExpImpresionInformeBTV(numsExpedientes);
				if (result.getError()) {
					return result;
				}
			}

		} catch (OegamExcepcion e) {
			result = new ResultBean(true, e.getMensajeError1());
			return result;
		}
		return null;
	}

	@Override
	public ResultValidarTramitesImprimir validarTramitesPrevioAPermitirImprimir(String[] numExpedientes, BigDecimal idContrato, Boolean tienePermisoAdmin) {
		ResultValidarTramitesImprimir resultValidar = null;
		BigDecimal[] numerosExpedientes = null;
		if (numExpedientes != null) {
			numerosExpedientes = utiles.convertirStringArrayToBigDecimal(numExpedientes);
			if (numerosExpedientes == null) {
				resultValidar = new ResultValidarTramitesImprimir(true, "Existen errores buscando los trámites.");
				return resultValidar;
			}
		} else {
			resultValidar = new ResultValidarTramitesImprimir(true, "No ha seleccionado ningun expediente.");
			return resultValidar;
		}
		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Imprimir.");
		List<Long> listaContratos = servicioTramiteTraficoImpl.getListaTramitesMismoContratoPorExpedientes(numerosExpedientes);
		if (listaContratos != null && !listaContratos.isEmpty() && listaContratos.size() == 1) {
			if (!tienePermisoAdmin && listaContratos.get(0) != null) {
				BigDecimal idContratoExp = new BigDecimal(listaContratos.get(0));
				if (!idContratoExp.equals(idContrato)) {
					resultValidar = new ResultValidarTramitesImprimir(true, "Los trámites seleccionado no tienen el mismo Contrato. Deben serlo para realizar impresiones en bloque.");
					return resultValidar;
				}
			}
		}

		TipoTramiteTrafico tipoTramiteTrafico = servicioTramiteTraficoImpl.recuperarTipoTramiteSiEsElMismo(numerosExpedientes);
		resultValidar = new ResultValidarTramitesImprimir();
		resultValidar.setTipoTramite(tipoTramiteTrafico);
		if (!esTipoValidoParaImprimir(tipoTramiteTrafico)) {
			resultValidar = new ResultValidarTramitesImprimir(true,
					"Los trámites seleccionado o no existen, o no son del mismo tipo, o son de un tipo que no se puede imprimir. Deben serlo para realizar impresiones en bloque.");
			return resultValidar;
		} else if (tipoTramiteTrafico == TipoTramiteTrafico.Matriculacion) {
			int tipo = servicioTramiteTraficoImpl.recuperarTipoTramiteMatriculacionSiEsElMismo(numerosExpedientes);
			if (tipo == -1) {
				resultValidar = new ResultValidarTramitesImprimir(true, "Los trámites seleccionados no son de la misma versión de matriculación. Deben serlo para realizar impresiones en bloque.");
				return resultValidar;
			}
			TipoTramiteMatriculacion tipoTramiteMatriculacion = TipoTramiteMatriculacion.convertir(tipo + "");
			resultValidar.setTipoTramiteMatriculacion(tipoTramiteMatriculacion);
		} else if (tipoTramiteTrafico == TipoTramiteTrafico.Transmision) {
			TipoTransferenciaActual tipoTransfeActual = servicioTramiteTraficoImpl.getTipoTransferenciaActual(numerosExpedientes, tipoTramiteTrafico);
			if (tipoTransfeActual == null) {
				resultValidar = new ResultValidarTramitesImprimir(true,
						"Los trámites de transmision seleccionados no cobran el mismo tipo de Créditos. Deben serlo para realizar impresiones en bloque.");
				return resultValidar;
			} else {
				resultValidar.setTipoTransmisionActual(tipoTransfeActual);
			}
		} else if (tipoTramiteTrafico == TipoTramiteTrafico.TransmisionElectronica) {
			resultValidar.setTipoTransmision(servicioTramiteTraficoImpl.getTipoTransferencia(numerosExpedientes, tipoTramiteTrafico));
		}
		// comprobar que los tramites no esten anulados
		Boolean existeAnulado = servicioTramiteTraficoImpl.comprobarEstadosTramitesAnulados(numerosExpedientes);
		if (existeAnulado) {
			resultValidar = new ResultValidarTramitesImprimir(true, "No se pueden imprimir tramites anulados.");
		}
		return resultValidar;
	}

	@Override
	public ResultValidarTramitesImprimir validarImpresion(String[] numExpedientes, boolean admin) {
		ResultValidarTramitesImprimir resultValidar = new ResultValidarTramitesImprimir();
		if (numExpedientes != null) {
			Long idContratoSel = null;
			String tipoTramiteSel = null;
			try {
				for (String numExpediente : numExpedientes) {
					TramiteTraficoVO tramite = servicioTramiteTraficoImpl.getTramite(new BigDecimal(numExpediente), Boolean.FALSE);
					if (tramite != null) {
						if (idContratoSel != null && StringUtils.isNotBlank(tipoTramiteSel)) {
							if (!idContratoSel.equals(tramite.getContrato().getIdContrato())) {
								resultValidar = new ResultValidarTramitesImprimir(true, "Los trámites seleccionado no tienen el mismo Contrato. Deben serlo para realizar impresiones en bloque.");
								return resultValidar;
							}

							if (tipoTramiteSel != null && !tipoTramiteSel.equals(tramite.getTipoTramite())) {
								resultValidar = new ResultValidarTramitesImprimir(true,
										"Los trámites seleccionado no tienen el mismo Tipo de Trámite. Deben serlo para realizar impresiones en bloque.");
								return resultValidar;
							}
						} else {
							idContratoSel = tramite.getContrato().getIdContrato();
							tipoTramiteSel = tramite.getTipoTramite();
						}

						if (new BigDecimal(EstadoTramiteTrafico.Anulado.getValorEnum()).equals(tramite.getEstado())) {
							resultValidar = new ResultValidarTramitesImprimir(true, "No se pueden imprimir tramites anulados.");
							return resultValidar;
						}
					} else {
						resultValidar = new ResultValidarTramitesImprimir(true, "No han encontrado todos los expedientes.");
						return resultValidar;
					}
				}
				resultValidar.setTipoTramite(TipoTramiteTrafico.convertir(tipoTramiteSel));
			} catch (Exception e) {
				log.error("Existe un error a la hora de recuperar los trámites.", e);
				resultValidar = new ResultValidarTramitesImprimir(true, "Existe un error a la hora de recuperar los trámites.");
			}
		} else {
			resultValidar = new ResultValidarTramitesImprimir(true, "No ha seleccionado ningun expediente.");
			return resultValidar;
		}
		return resultValidar;
	}

	private boolean esTipoValidoParaImprimir(TipoTramiteTrafico tipoTramiteTrafico) {
		if (tipoTramiteTrafico == null) {
			return false;
		}
		if (tipoTramiteTrafico == TipoTramiteTrafico.Matriculacion || tipoTramiteTrafico == TipoTramiteTrafico.Transmision || tipoTramiteTrafico == TipoTramiteTrafico.TransmisionElectronica
				|| tipoTramiteTrafico == TipoTramiteTrafico.Baja || tipoTramiteTrafico == TipoTramiteTrafico.Solicitud || tipoTramiteTrafico == TipoTramiteTrafico.Duplicado) {
			return true;
		}
		return false;
	}

	@Override
	public CriteriosImprimirTramiteTraficoBean generarCriteriosImprimirTramite(String impreso, ImprimirTramiteTraficoDto imprimirTramiteTraficoDto, ParametrosPegatinaMatriculacion etiquetaParametros,
			String tipoRepresentacion) {
		CriteriosImprimirTramiteTraficoBean criteriosImprimir = new CriteriosImprimirTramiteTraficoBean();
		criteriosImprimir.setTipoImpreso(impreso);
		criteriosImprimir.setTipoTramite(imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite());
		criteriosImprimir.setTipoTramiteMatriculacion(imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramiteMatriculacion());
		criteriosImprimir.setParametrosEtiquetasMatriculacion(etiquetaParametros);
		criteriosImprimir.setTipoRepresentacion(tipoRepresentacion);
		return criteriosImprimir;
	}

	@Override
	public boolean impresionMasiva(int numeroExpedientes) throws OegamExcepcion {
		try {
			String habilitado = gestorPropiedades.valorPropertie("impresionMasiva.habilitado");
			String maxTramites = gestorPropiedades.valorPropertie(PropertiesConstantes.NUMERO_MINIMO_TRAMITES_PARA_IMPRESION_MASIVA);

			if (habilitado != null && !"".equals(habilitado) && habilitado.equals("SI") && numeroExpedientes >= Integer.valueOf(maxTramites)) {
				return true;
			}
		} catch (NumberFormatException e) {
			log.error("Error recuperando parámetros en properties de impresión masiva");
			throw new OegamExcepcion(EnumError.error_00001);
		}
		return false;
	}

	@Override
	public byte[] encriptarPDF(byte[] archivo) {
		return PdfMaker.encriptarPdf(archivo, "", "", true, false, false, false, false);
	}

	@Override
	public byte[] concatenarPDF(ArrayList<byte[]> listaByte) {
		return PdfMaker.concatenarPdf(listaByte);
	}

	@Override
	public boolean guardarFichero(byte[] archivo, String fileName, String tipoImpreso) {
		boolean resultado = false;
		if (archivo != null) {
			FicheroBean fichero = new FicheroBean();
			fichero.setFicheroByte(archivo);
			fichero.setTipoDocumento(ConstantesGestorFicheros.IMPRESION);
			fichero.setNombreDocumento(fileName);
			fichero.setExtension("");

			try {
				File f = gestorDocumentos.guardarByte(fichero);
				resultado = f != null && f.exists();
			} catch (OegamExcepcion e) {
				log.error("Error guardando el fichero. No se podrá recuperar", e);
			}
		}
		return resultado;
	}

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	public ResultBean impresionPDF417(String[] numsExpedientes, ImprimirTramiteTraficoDto imprimirTramiteTraficoDto, BigDecimal idContrato, BigDecimal idUsuario, String impreso)
			throws OegamExcepcion {
		ResultBean resultadoFinal = null;
		ResultBean rs = null;
		try {
			rs = servicioCreditoImpl.descontarCreditosPorExpedientes(numsExpedientes, imprimirTramiteTraficoDto, idContrato, idUsuario);
			if (rs == null) {
				resultadoFinal = new ResultBean(true, "Existen problemas al descontar los créditos. Inténtelo más tarde.");
				log.error("Existen problemas al descontar créditos");
				return resultadoFinal;
			} else if (rs.getError()) {
				log.error("Ha habido problemas con los créditos");
				return rs;
			}
			rs = servicioImprimirTraficoImpl.cambiarEstadoTramiteImprimir(imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite().getValorEnum(), numsExpedientes, impreso, idUsuario);
			if (rs == null) {
				resultadoFinal = new ResultBean(true, "Existen problemas al cambiar el estado del trámite. Inténtelo más tarde");
				log.error("Exsiten problemas al cambiar el estado del trámite");
			} else if (rs.getError()) {
				log.error("Ha habido problemas al cambiar el estado de los trámites");
				resultadoFinal = rs;
				rs = servicioCreditoImpl.descontarCreditosPorExpedientes(numsExpedientes, imprimirTramiteTraficoDto, idContrato, idUsuario);
				if (rs == null) {
					List<String> listaResult = resultadoFinal.getListaMensajes();
					listaResult.add("Existen problemas técnicos. Se le ha descontado los créditos, pero no se ha podido generar el documento. Contacte con el Colegio.");
					resultadoFinal.setListaMensajes(listaResult);
					log.error("ERROR MUY GRAVE. Se han descontado " + numsExpedientes.length + " créditos y no se ha generado el documento del usuario " + idUsuario.toString());
				} else if (rs.getError()) {
					List<String> listaResult = resultadoFinal.getListaMensajes();
					listaResult.add("Existen problemas técnicos. Se le ha descontado los créditos, pero no se ha podido generar el documento. Contacte con el Colegio.");
					for (String mensaje : rs.getListaMensajes()) {
						listaResult.add(mensaje);
					}
					resultadoFinal.setListaMensajes(listaResult);
					log.error("ERROR MUY GRAVE. Se han descontado " + numsExpedientes.length + " créditos y no se ha generado el documento del usuario " + idUsuario.toString());
				} else {
					List<String> listaResult = resultadoFinal.getListaMensajes();
					listaResult.add("Existen problemas al imprimir. Inténtelo más tarde");
					resultadoFinal.setListaMensajes(listaResult);
				}
			} else {
				resultadoFinal = new ResultBean(false, "La impresión se realizó correctamente");
				log.info("Se ha generado correctamente los impreso de tipo " + impreso + " para los trámites " + numsExpedientes + " solicitados por " + idUsuario.toString());
			}
		} catch (Exception e) {
			throw new OegamExcepcion(EnumError.error_00001, e.getMessage());
		}

		return resultadoFinal;
	}

	@Override
	public Boolean borrarDocumentoGuardado(String fileName, String idSession) {
		try {
			gestorDocumentos.borraFicheroSiExiste(ConstantesGestorFicheros.IMPRESION, idSession, null, fileName);
			return true;
		} catch (OegamExcepcion e) {
			log.error("No se ha podido borrar el fichero que debía estar guardado");
		}
		return false;
	}

	@Override
	public void crearAccionSiNecesaria(boolean terminadoCorrectamente, String[] numsExpedientes, String impreso, BigDecimal idUsuario) {
		String respuesta = terminadoCorrectamente ? "IMPRESIÓN CORRECTA" : "IMPRESIÓN FALLIDA";
		String accion = null;
		BigDecimal[] bgdNumExp = utiles.convertirStringArrayToBigDecimal(numsExpedientes);
		if (impreso.equals(TipoImpreso.MatriculacionPDF417.toString())) {
			accion = "IMPRESION417";
		} else if (impreso.equals(TipoImpreso.TransmisionModelo430.toString())) {
			accion = "IMPRESION430";
		}
		for (BigDecimal numExp : bgdNumExp) {
			try {
				servicioAccionTramiteImpl.crearAccionTramite(idUsuario, numExp, accion, utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()), respuesta);
			} catch (OegamExcepcion e) {
				log.error("No se han podido guardar las acciones");
			}
		}
	}

	@Override
	public File recuperarFichero(String nombre) {
		File f = null;
		if (nombre != null && !nombre.isEmpty()) {
			try {
				f = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.IMPRESION, null, null, nombre, "").getFile();
				if (f != null && f.exists()) {
					BorrarFicherosRecursivoThread hiloBorrar = new BorrarFicherosRecursivoThread(f.getAbsolutePath(), 30000);
					hiloBorrar.start();
				}
			} catch (OegamExcepcion e) {
				log.error("Error recuperando el fichero.");
			}
		}
		return f;
	}

	@Override
	public ResultBean recuperarDocumentosOficiales(String[] numExpedientes, String tipo, String tipoImpresion) {
		ResultBean result = null;

		if (tipoImpresion.equals(TipoImpreso.TransmisionDocumentosTelematicos.toString())) {
			String[] expedientes = new String[numExpedientes.length * 2]; // Multiplico por 2 porque se necesita sacar el informe y el ptc
			for (int j = 0; j < numExpedientes.length; j++) {
				expedientes[j * 2] = numExpedientes[j];
				expedientes[j * 2 + 1] = "i_" + numExpedientes[j];
			}
			numExpedientes = expedientes;
		}

		if (numExpedientes.length > 1) {
			result = recuperarDocumentosOficialesPorExpedientes(numExpedientes, tipo, tipoImpresion);
		} else {
			result = recuperarDocumentosOficialesPorExpediente(numExpedientes[0], tipo, tipoImpresion);
		}

		return result;
	}

	private ResultBean recuperarDocumentosOficialesPorExpediente(String numExp, String tipo, String tipoImpresion) {
		ResultBean result = new ResultBean();
		String subtipo = null;
		String anadidoNombre = "";
		String nombre = tipoImpresion + ConstantesGestorFicheros.EXTENSION_PDF;
		String extension = ConstantesGestorFicheros.EXTENSION_PDF;
		byte[] byte1 = null;

		if (tipoImpresion.equals(TipoImpreso.MatriculacionPermisoTemporalCirculacion.toString())) {
			subtipo = ConstantesGestorFicheros.PTC;
		} else if (tipoImpresion.equals(TipoImpreso.FichaTecnica.toString())) {
			subtipo = ConstantesGestorFicheros.FICHATECNICA;
			anadidoNombre = "_" + ConstantesGestorFicheros.NOMBRE_FICHATECNICA;
		} else if (tipoImpresion.equals(TipoImpreso.PDFJustificantePresentacion576.toString())) {
			anadidoNombre = "_" + ConstantesGestorFicheros.TIPO576;
		} else if (tipoImpresion.equals(TipoImpreso.TransmisionDocumentosTelematicos.toString())) {
			if (numExp.contains("_")) {
				subtipo = ConstantesGestorFicheros.INFORMES;
			} else {
				subtipo = ConstantesGestorFicheros.PTC;
			}
		} else if (tipoImpresion.equals(TipoImpreso.SolicitudAvpo.toString()) || tipoImpresion.equals(TipoImpreso.SolicitudATEM.toString()) || tipoImpresion.equals(TipoImpreso.SolicitudAvpoError
				.toString())) {
			extension = ConstantesGestorFicheros.EXTENSION_ZIP;
			// si se le añade algo al nombre por delante si no lleva '_' va a dar problemas
			numExp = ConstantesGestorFicheros.NOMBRE_CONSULTA + numExp;
			nombre = tipoImpresion + ConstantesGestorFicheros.EXTENSION_ZIP;
		}else if(TipoImpreso.PermisoTemporalDuplicado.toString().equals(tipoImpresion)) {
			subtipo = ConstantesGestorFicheros.PTC_DUP;
		}else if(TipoImpreso.JustifRegEntrada.toString().equals(tipoImpresion)) {
			subtipo = ConstantesGestorFicheros.JUSTIFICANTE_REG_ENTRADA;
		}else if(TipoImpreso.JustificanteDuplicado.toString().equals(tipoImpresion)) {
			subtipo = ConstantesGestorFicheros.JUSTIFICANTE_DUP;
		}else if(TipoImpreso.CertificadoRevisionColegial.toString().equals(tipoImpresion)) {
			subtipo = ConstantesGestorFicheros.CERTIFICADO_AUTORIZACION;
		}else if(TipoImpreso.JustifRegEntradaBajaSive.toString().equals(tipoImpresion)) {
			subtipo = ConstantesGestorFicheros.JUSTIFICANTE_REG_ENTRADA_BAJA_SIVE;
		}else if(TipoImpreso.InformeBajaSive.toString().equals(tipoImpresion)) {
			subtipo = ConstantesGestorFicheros.INFORME_BAJA_SIVE;
		}

		Fecha fecha;
		if (numExp.contains("_")) {
			fecha = Utilidades.transformExpedienteFecha(numExp.split("_")[1]);
		} else {
			fecha = Utilidades.transformExpedienteFecha(numExp);
		}

		String nombreFichero;
		String tipoFichero;
		if (TipoImpreso.ConsultaEITV.toString().equals(tipoImpresion)) {
			nombreFichero = TipoImpreso.ConsultaEITV.toString() + "_" + numExp;
			tipoFichero = ConstantesGestorFicheros.MATE;
		} else {
			nombreFichero = numExp + anadidoNombre;
			tipoFichero = tipo;
		}
		if(TipoImpreso.CertificadoRevisionColegial.toString().equals(tipoImpresion)) {
			nombreFichero = numExp+"_CertificadoRevisionColegialInicial_Firma_OK";
		}

		try {
			FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(tipoFichero, subtipo, fecha, nombreFichero, extension);

			if (fileResultBean.getFile() != null) {
				byte1 = gestorDocumentos.transformFiletoByte(fileResultBean.getFile());

				result.addAttachment(ResultBean.NOMBRE_FICHERO, nombre);
			} else if (FileResultStatus.ON_DEMAND_FILE.equals(fileResultBean.getStatus())) {
				result.setError(true);
				List<String> listaMensajes = result.getListaMensajes();
				listaMensajes.add("No se ha podido recuperar el documento tipo  " + tipoImpresion + " para el numero Expediente " + numExp + ". " + fileResultBean.getMessage());
				result.setListaMensajes(listaMensajes);
			} else {
				result.setError(true);
				List<String> listaMensajes = result.getListaMensajes();
				listaMensajes.add("No se ha podido recuperar el documento tipo  " + tipoImpresion + " para el numero Expediente " + numExp);
				result.setListaMensajes(listaMensajes);
			}
		} catch (FileNotFoundException e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add("Error al guardar los archivos en pdf");
			result.setListaMensajes(listaMensajes);
		} catch (OegamExcepcion e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add(e.getMessage());
			result.setListaMensajes(listaMensajes);
		} catch (IOException e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add("Error al guardar los archivos en pdf");
			result.setListaMensajes(listaMensajes);
		}

		if (byte1 != null) {
			result.setError(false);
			// resultadoMetodo.setMensaje("");
			result.addAttachment(ResultBean.TIPO_PDF, byte1);
		} else {
			result.setError(true);
		}

		return result;
	}

	private ResultBean recuperarDocumentosOficialesPorExpedientes(String[] numExpedientes, String tipo, String tipoImpresion) {
		ResultBean result = new ResultBean();
		ZipOutputStream zip = null;
		FileOutputStream out = null;
		String url = null;
		String subtipo = null;
		String anadidoNombre = "";
		String extension = ConstantesGestorFicheros.EXTENSION_PDF;
		byte[] byte1 = null;
		try {
			url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + "zip" + System.currentTimeMillis();
			out = new FileOutputStream(url);
			zip = new ZipOutputStream(out);

			for (int i = 0; i < numExpedientes.length; i++) {
				if (tipoImpresion.equals(TipoImpreso.MatriculacionPermisoTemporalCirculacion.toString())) {
					subtipo = ConstantesGestorFicheros.PTC;
				} else if (tipoImpresion.equals(TipoImpreso.FichaTecnica.toString())) {
					subtipo = ConstantesGestorFicheros.FICHATECNICA;
					anadidoNombre = "_" + ConstantesGestorFicheros.NOMBRE_FICHATECNICA;
				} else if (tipoImpresion.equals(TipoImpreso.PDFJustificantePresentacion576.toString())) {
					anadidoNombre = "_" + ConstantesGestorFicheros.TIPO576;
				} else if (tipoImpresion.equals(TipoImpreso.TransmisionDocumentosTelematicos.toString())) {
					if (numExpedientes[i].contains("_")) {
						subtipo = ConstantesGestorFicheros.INFORMES;
					} else {
						subtipo = ConstantesGestorFicheros.PTC;
					}
				} else if (tipoImpresion.equals(TipoImpreso.SolicitudAvpo.toString()) || tipoImpresion.equals(TipoImpreso.SolicitudATEM.toString()) || tipoImpresion.equals(
						TipoImpreso.SolicitudAvpoError.toString())) {
					extension = ConstantesGestorFicheros.EXTENSION_ZIP;
					// si se le añade algo al nombre por delante si no lleva '_' va a dar problemas
					numExpedientes[i] = ConstantesGestorFicheros.NOMBRE_CONSULTA + numExpedientes[i];
				} else if(TipoImpreso.PermisoTemporalDuplicado.toString().equals(tipoImpresion)) {
					subtipo = ConstantesGestorFicheros.PTC_DUP;
				}else if(TipoImpreso.JustifRegEntrada.toString().equals(tipoImpresion)) {
					subtipo = ConstantesGestorFicheros.JUSTIFICANTE_REG_ENTRADA;
				}else if(TipoImpreso.JustificanteDuplicado.toString().equals(tipoImpresion)) {
					subtipo = ConstantesGestorFicheros.JUSTIFICANTE_DUP;
				}else if(TipoImpreso.CertificadoRevisionColegial.toString().equals(tipoImpresion)) {
					subtipo = ConstantesGestorFicheros.CERTIFICADO_AUTORIZACION;
				}else if(TipoImpreso.JustifRegEntradaBajaSive.toString().equals(tipoImpresion)) {
					subtipo = ConstantesGestorFicheros.JUSTIFICANTE_REG_ENTRADA_BAJA_SIVE;
				}else if(TipoImpreso.InformeBajaSive.toString().equals(tipoImpresion)) {
					subtipo = ConstantesGestorFicheros.INFORME_BAJA_SIVE;
				}

				Fecha fecha = null;
				if (numExpedientes[i].contains("_")) {
					fecha = Utilidades.transformExpedienteFecha(numExpedientes[i].split("_")[1]);
				} else {
					fecha = Utilidades.transformExpedienteFecha(numExpedientes[i]);
				}

				String nombreFichero = numExpedientes[i] + anadidoNombre;

				FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(tipo, subtipo, fecha, nombreFichero, extension);

				if (fileResultBean.getFile() != null && fileResultBean.getFile().exists()) {
					FileInputStream is = new FileInputStream(fileResultBean.getFile());
					// Mantis 14386. David Sierra: Se modifica el nombre del fichero agregando la matricula
					// en funcion del numero de colegiado del mismo (los colegiados se recuperan por properties de BBDD)
					nombreFichero = fileResultBean.getFile().getName();
					// Ejecucion metodo recuperacion
					String resultadoConsulta = getNombreFichero(nombreFichero, subtipo);
					// Comprobacion respuesta
					if (null != resultadoConsulta) {
						nombreFichero = resultadoConsulta;
					}
					// Se agrega al zip el nombre del fichero con su extension
					ZipEntry zipEntry = new ZipEntry(nombreFichero);
					// Fin Mantis 14386
					zip.putNextEntry(zipEntry);
					byte[] buffer = new byte[2048];
					int byteCount;
					while (-1 != (byteCount = is.read(buffer))) {
						zip.write(buffer, 0, byteCount);
					}
					zip.closeEntry();
					is.close();
					if (fileResultBean.getFile().lastModified() > 0) {
						zipEntry.setTime(fileResultBean.getFile().lastModified());
					}
				} else if (FileResultStatus.ON_DEMAND_FILE.equals(fileResultBean.getStatus())) {
					result.setError(true);
					List<String> listaMensajes = result.getListaMensajes();
					listaMensajes.add("No se ha podido recuperar el documento tipo  " + tipoImpresion + " para el numero Expediente " + numExpedientes[i] + ". " + fileResultBean.getMessage());
					result.setListaMensajes(listaMensajes);
				} else {
					result.setError(true);
					List<String> listaMensajes = result.getListaMensajes();
					listaMensajes.add("No se ha podido recuperar el documento tipo  " + tipoImpresion + " para el numero Expediente " + numExpedientes[i]);
					result.setListaMensajes(listaMensajes);
				}
			}
			zip.close();
			File fichero = new File(url);
			result.addAttachment(ResultBean.NOMBRE_FICHERO, tipoImpresion + ConstantesGestorFicheros.EXTENSION_ZIP);

			byte1 = gestorDocumentos.transformFiletoByte(fichero);

			if (fichero.delete()) {
				log.info("Se ha eliminado correctamente el fichero");
			} else {
				log.info("No se ha eliminado el fichero");
			}

		} catch (FileNotFoundException e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add("Error al guardar los archivos en zip");
			result.setListaMensajes(listaMensajes);
		} catch (OegamExcepcion e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add(e.getMessage());
			result.setListaMensajes(listaMensajes);
		} catch (IOException e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add("Error al guardar los archivos en zip");
			result.setListaMensajes(listaMensajes);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			File eliminarZip = new File(url);
			eliminarZip.delete();
		}

		if (byte1 != null) {
			result.setError(false);
			// resultadoMetodo.setMensaje("");
			result.addAttachment(ResultBean.TIPO_PDF, byte1);
		} else {
			result.setError(true);
		}

		return result;
	}

	/**
	 * Devuelve el nombre del fichero
	 * @param numExpedienteFicheroExtension
	 * @param subtipo
	 * @return String
	 * @throws OegamExcepcion
	 */
	// Mantis 14386. David Sierra: Comprobacion colegiados autorizados para incluir la matricula en los nombres de los ficheros
	private String getNombreFichero(String numExpedienteFicheroExtension, String subtipo) throws OegamExcepcion {
		String nombreFichero = null;
		String numExpedienteFichero = null;
		// Se realiza un substring del numero de expediente eliminando la extension del fichero
		if (ConstantesGestorFicheros.PTC.equals(subtipo)) {
			numExpedienteFichero = numExpedienteFicheroExtension.substring(0, numExpedienteFicheroExtension.indexOf("."));
		}
		// Mantis 16060.David Sierra: Para el resto de numeros de expediente porque no sabemos si vienen con guiones o no en el nombre
		else {
			numExpedienteFichero = numExpedienteFicheroExtension.substring(0, 15);
		}
		// Recuperacion de los colegiados por properties de BBDD
		String[] colegiadosAutorizados = gestorPropiedades.valorPropertie("colegiados.impresion.ptc").split("\\,");

		for (int x = 0; x < colegiadosAutorizados.length; x++) {
			// Si coincide el colegiado con el inicio del numero de expediente y el suptipo es PTC
			if (colegiadosAutorizados[x].equals(numExpedienteFichero.substring(0, 4)) && subtipo.equals(ConstantesGestorFicheros.PTC)) {
				// Se recupera el nombre con la matricula asociada al expediente
				nombreFichero = getMatriculaExpediente(numExpedienteFichero, subtipo);
			}
			// Mantis 16060. Si coincide el colegiado con el inicio del numero de expediente y el suptipo es FICHATECNICA
			else if (colegiadosAutorizados[x].equals(numExpedienteFichero.substring(0, 4)) && subtipo.equals(ConstantesGestorFicheros.FICHATECNICA)) {
				nombreFichero = getMatriculaExpediente(numExpedienteFichero, subtipo);
				// No coincide. No se realiza ninguna accion sobre el numero de expediente
			} else {
				nombreFichero = null;
			}
		}
		return nombreFichero;
	}

	/**
	 * Devuelve en el nombe del fichero la matricula asociada al expediente
	 * @param numExpediente
	 * @return String
	 * @throws OegamExcepcion
	 */
	// Mantis 14386. David Sierra: Se realizan las modificaciones del nombre de fichero tras recuperar de BBDD la
	// matricula asociada al expediente pasado como parametro
	private String getMatriculaExpediente(String numExpediente, String subtipo) throws OegamExcepcion {
		// Conversion de String a Long ya que en la entidad numExpediente es de tipo Long
		Long numExpedienteLargo = Long.parseLong(numExpediente);
		// Recuperacion consulta del DAO

		List<Object[]> listaMatriculas = matriculaVehiculoDao.getMatriculaExpedienteDao(numExpedienteLargo);
		String codigo = null;

		for (int i = 0; i < listaMatriculas.size(); i++) {
			// Si no se devuelven matriculas no se asigna al nombre del fichero la matricula
			if (null == listaMatriculas.get(i)[1]) {
				// Se asigna al nombre del fichero: matricula_expediente_PTC.pdf
				if (subtipo.equals(ConstantesGestorFicheros.PTC)) {
					codigo = listaMatriculas.get(i)[0].toString() + "_" + "PTC" + ".pdf";
					// Se asigna al nombre del fichero: matricula_expediente_ficha_tecnica.pdf
				} else if (subtipo.equals(ConstantesGestorFicheros.FICHATECNICA)) {
					codigo = listaMatriculas.get(i)[0].toString() + "_" + "ficha_tecnica" + ".pdf";
				}
			} else {
				// Se asigna al nombre del fichero: matricula_expediente_PTC.pdf
				if (subtipo.equals(ConstantesGestorFicheros.PTC)) {
					codigo = listaMatriculas.get(i)[1].toString() + "_" + listaMatriculas.get(i)[0].toString() + "_" + "PTC" + ".pdf";
					// Se asigna al nombre del fichero: matricula_expediente_ficha_tecnica.pdf
				} else if (subtipo.equals(ConstantesGestorFicheros.FICHATECNICA)) {
					codigo = listaMatriculas.get(i)[1].toString() + "_" + listaMatriculas.get(i)[0].toString() + "_" + "ficha_tecnica" + ".pdf";
				}
			}
		}
		return codigo;
	}

	@Override
	public ResultBean generarResultBeanConArrayByteFinal(ArrayList<byte[]> listaByte, String nombreFichero) {
		ResultBean result = new ResultBean();
		byte[] byte1 = PdfMaker.concatenarPdf(listaByte);
		result.addAttachment(ResultBean.TIPO_PDF, encriptarPDF(byte1));
		result.addAttachment(ResultBean.NOMBRE_FICHERO, nombreFichero);
		return result;
	}

	@Override
	public ResultBean generarResultBeanConByteFinal(byte[] byte1, String nombreFichero) {
		ResultBean result = new ResultBean();
		result.addAttachment(ResultBean.TIPO_PDF, encriptarPDF(byte1));
		result.addAttachment(ResultBean.NOMBRE_FICHERO, nombreFichero);
		return result;
	}

	@Override
	public ResultBean generarResultBeanByteProceso(ArrayList<byte[]> listaByte) {
		ResultBean result = new ResultBean(false);
		byte[] byte1 = concatenarPDF(listaByte);
		byte[] byteFinal = encriptarPDF(byte1);
		result.addAttachment(ResultBean.TIPO_PDF, byteFinal);
		return result;
	}

	/**
	 * Se guarda el 417 para las solicitudes masivas
	 * @param bytesFichero
	 * @param nombreFichero
	 * @throws Exception
	 * @throws IOException
	 * @throws OegamExcepcion
	 */
	@Override
	public File guardarFicheroProceso(byte[] bytesFichero, String nombreFichero, String tipoDocumento, String subTipo, String extension) throws Exception, IOException, OegamExcepcion {

		log.info(" Guardar Documentos del Proceso impresión masiva");

		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(tipoDocumento);
		fichero.setSubTipo(subTipo);
		fichero.setSobreescribir(true);
		fichero.setSubCarpetaDia(true);

		fichero.setFecha(utilesFecha.getFechaActual());
		fichero.setNombreDocumento(nombreFichero);

		fichero.setFicheroByte(bytesFichero);
		fichero.setExtension(extension);
		return gestorDocumentos.guardarByte(fichero);
	}

	@Override
	public ResultBean imprimirPdfSolicitudNRE06(String[] numsExpedientes, String tipo, String tipoImpreso) {
		ResultBean resultado = new ResultBean();
		String nombreFichero = "NRE06";
		String subTipo = ConstantesGestorFicheros.SOLICITUDES_NRE06;
		String extension = ConstantesGestorFicheros.EXTENSION_PDF;
		String nombre = tipoImpreso + ConstantesGestorFicheros.EXTENSION_PDF;
		byte[] byte1 = null;
		try {
			for (String numExp : numsExpedientes) {
				TramiteTraficoVO tramiteVo = servicioTramiteTraficoImpl.getTramite(new BigDecimal(numExp), Boolean.TRUE);
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(tipo, subTipo,  utilesFecha.getFechaConDate(tramiteVo.getFechaPresentacion()), nombreFichero +"_"+numExp , extension);
				if (fichero.getFile() != null) {
					byte1 = gestorDocumentos.transformFiletoByte(fichero.getFile());

					resultado.addAttachment(ResultBean.NOMBRE_FICHERO, nombre);
				}else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Para imprimir la Solicitud NRE06, previamente debe Tramitar NRE06 del expediente.");
				}
			}
		} catch (Exception | OegamExcepcion e) {
			log.error(e);
			resultado.setError(Boolean.TRUE);
			List<String> listaMensajes = resultado.getListaMensajes();
			listaMensajes.add(e.getMessage());
			resultado.setListaMensajes(listaMensajes);
		}
		if (byte1 != null) {
			resultado.setError(Boolean.FALSE);
			// resultadoMetodo.setMensaje("");
			resultado.addAttachment(ResultBean.TIPO_PDF, byte1);
		} else {
			resultado.setError(Boolean.TRUE);
		}

		return resultado;
	}
}
