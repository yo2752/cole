package org.gestoresmadrid.procesos.model.jobs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.administracion.model.enumerados.TipoRecargaCacheEnum;
import org.gestoresmadrid.core.model.enumerados.TipoFicheroImportacionBastidores;
import org.gestoresmadrid.oegam2comun.administracion.service.ServicioRecargaCache;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioProcesoImportacionSantander;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.DatosSensiblesBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.ResultadoAltaBastidorBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.ResultadoImportacionSantanderBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.ResultadoDatosSensibles;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import trafico.datosSensibles.utiles.enumerados.TiempoBajaDatosSensibles;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoImportacionSantander extends AbstractProcesoBase {

	private ILoggerOegam log = LoggerOegam.getLogger(this.getClass());

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	private static final String TIPO_BASTIDOR = "VN_VO_FI_LE_RE_FR";
	private static final String DM = "DM";

	@Autowired
	private ServicioDatosSensibles servicioDatosSensibles;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioRecargaCache servicioRecargaCache;

	@Autowired
	ServicioProcesoImportacionSantander servicioProcesoImportacionSantander;

	@Override
	protected void doExecute() throws JobExecutionException {
		log.error("Inicio ProcesoImportacionDatosSensibles");

		Boolean esLaborable = true;
		// Mantis 18007. David Sierra: Para agregar el resultado de la ejecucion del proceso a Envio_Data
		String respuesta = null;
		String resultadoEjecucion = null;
		boolean error = false;
		try {
			esLaborable = utilesFecha.esFechaLaborable(false);
		} catch (OegamExcepcion oe) {
			log.error(oe.getMessage(), oe);
		}
		if (esLaborable) {
			String nuevoFormatosFicheros = gestorPropiedades.valorPropertie("nuevos.formatos.ficheros.ftp");
			if ("SI".equalsIgnoreCase(nuevoFormatosFicheros)) {
				try {
					ResultadoImportacionSantanderBean resultado = servicioProcesoImportacionSantander.procesarImportacionSantander();
					servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.DATOS_SENSIBLES);
					if (resultado != null && resultado.getExcepcion() != null) {
						throw new Exception(resultado.getExcepcion());
					} else if (resultado != null && resultado.getError()) {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
						respuesta = resultado.getMensajeError();
					} else {
						resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
						respuesta = resultado.getMensajeError();
					}
				} catch (Exception e) {
					log.error("Ocurrio un error no controlado en el proceso de Importacion de Santader, error: ", e);
					resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
					respuesta =e.getMessage() != null ? e.getMessage() : e.toString();
				}
			}
			/* *** 1.Nos conectamos al FTP para ver si hay archivos nuevos *** */
			Map<String, List<FicheroBean>> listasImportacion = null;
			try {
				listasImportacion = recuperarFicherosBastidores();
			} catch (Exception | OegamExcepcion e1) {
				String recipentErrorConexion = gestorPropiedades.valorPropertie("proceso.santander.email.error.conexion");
				servicioDatosSensibles.enviarCorreoError(e1.getMessage(), getProceso(), recipentErrorConexion, null);
				peticionRecuperable();
				// Mantis 18007
				resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
				respuesta = ConstantesProcesos.ERROR_RECUPERACION_FICHEROS + (e1.getMessage() != null ? e1.getMessage() : e1.toString());
				error = true;
			}

			/* *** Si hay archivos en el FTP *** */
			if (listasImportacion != null) {
				log.error("ProcesoImportacionDatosSensibles: encontrados " + listasImportacion.size() + " archivos");

				/* *** 2.Invocamos al servicio para guardar los datos *** */
				if (!listasImportacion.isEmpty()) {
					try {
						procesarFicherosBastidores(listasImportacion);
						servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.DATOS_SENSIBLES);
						log.error("Creada peticion para refresco de cache.");
					} catch (Exception e2) {
						log.error(e2.getMessage(), e2);
						// Mantis 18007
						resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
						respuesta = ConstantesProcesos.ERROR_GUARDADO_DATOS + (e2.getMessage() != null ? e2.getMessage() : e2.toString());
						error = true;
					}
				} else {
					// Mantis 18007
					resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
					respuesta = ConstantesProcesos.SIN_FICHEROS;
					error = true;
				}

				/* *** 3.Custodiamos los ficheros *** */
				try {
					custodiarFichero(listasImportacion);
				} catch (Exception e3) {
					log.error(e3.getMessage(), e3);
					// Mantis 18007
					resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
					respuesta = ConstantesProcesos.ERROR_CUSTODIA_FICHEROS + (e3.getMessage() != null ? e3.getMessage() : e3.toString());
					error = true;
				}

				/* 4.Borramos los archivos del FTP */
				try {
					borrarFicherosBastidores(listasImportacion);
				} catch (Exception e4) {
					log.error(e4.getMessage(), e4);
					// Mantis 18007
					resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
					respuesta = ConstantesProcesos.ERROR_BORRADO_FICHEROS + (e4.getMessage() != null ? e4.getMessage() : e4.toString());
					error = true;
				}

			} else {
				// Mantis 18007
				resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
				respuesta = ConstantesProcesos.SIN_FICHEROS;
				error = true;
			}

			actualizarEjecucion(getProceso(), resultadoEjecucion, respuesta, "0");
		} else {
			log.error("No se ha ejecutado el proceso de importacion de datos sensibles por no ser dia laborable");
		}
		peticionCorrecta();
		// Mantis 18007
		if (!error) {
			resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
			respuesta = ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO;
			actualizarEjecucion(getProceso(), resultadoEjecucion, respuesta, "0");
		}
	}

	/* *****************************************************************************************************
	 * ****************************************** MÉTODOS PRIVADOS *******************************************************************************************************************************************
	 */

	private Map<String, List<FicheroBean>> recuperarFicherosBastidores() throws Exception, OegamExcepcion {
		Map<String, List<FicheroBean>> listasDeFicheros = new HashMap<>();
		List<String> listadoDeArchivos;
		listadoDeArchivos = servicioProcesoImportacionSantander.listarFicherosSantander();

		// Ordenar los archivos 1º VN, 2º VO, 3º Demo
		List<String> listaVN;
		List<String> listaVO;
		List<String> listaDM;
		List<String> listaFI;
		List<String> listaLE;
		List<String> listaRE;
		List<String> listaFR;
		listaVN = listaVO = listaDM = listaFI = listaLE = listaRE = listaFR = null;

		for (String aux : listadoDeArchivos) {
			if (aux.contains("VN")) {
				if (listaVN == null) {
					listaVN = new ArrayList<>();
				}
				listaVN.add(aux);
			} else if (aux.contains("VO")) {
				if (listaVO == null) {
					listaVO = new ArrayList<>();
				}
				listaVO.add(aux);
			} else if (aux.contains("DM") || aux.contains("DEMO")) {
				if (listaDM == null) {
					listaDM = new ArrayList<>();
				}
				listaDM.add(aux);
			}else if (aux.contains("FI") || aux.contains("FINANCIACION")) {
				if (listaFI == null) {
					listaFI = new ArrayList<>();
				}
				listaFI.add(aux);
			}else if (aux.contains("LE") || aux.contains("LEASING")) {
				if (listaLE == null) {
					listaLE = new ArrayList<>();
				}
				listaLE.add(aux);
			}else if (aux.contains("RE") || aux.contains("RENTING")) {
				if (listaRE == null) {
					listaRE = new ArrayList<>();
				}
				listaRE.add(aux);
			} else if (aux.contains("FR") || aux.contains("FINANCIACION RETAIL")) {
				if (listaFR == null) {
					listaFR = new ArrayList<>();
				}
				listaFR.add(aux);
			}
		}

		if (listaVN != null && !listaVN.isEmpty()) {
			List<FicheroBean> listadoDeFicheros = new ArrayList<FicheroBean>();
			Collections.sort(listaVN);
			for (String archivo : listaVN) {
				listadoDeFicheros.add(servicioProcesoImportacionSantander.descargar(archivo));
			}
			listasDeFicheros.put(TIPO_BASTIDOR, listadoDeFicheros);
		}
		if (listaVO != null && !listaVO.isEmpty()) {
			List<FicheroBean> listadoDeFicheros = new ArrayList<FicheroBean>();
			Collections.sort(listaVO);
			for (String archivo : listaVO) {
				listadoDeFicheros.add(servicioProcesoImportacionSantander.descargar(archivo));
			}
			if (listasDeFicheros.containsKey(TIPO_BASTIDOR)) {
				listasDeFicheros.get(TIPO_BASTIDOR).addAll(listadoDeFicheros);
			} else {
				listasDeFicheros.put(TIPO_BASTIDOR, listadoDeFicheros);
			}
		}
		if (listaFI != null && !listaFI.isEmpty()) {
			List<FicheroBean> listadoDeFicheros = new ArrayList<FicheroBean>();
			Collections.sort(listaFI);
			for (String archivo : listaFI) {
				listadoDeFicheros.add(servicioProcesoImportacionSantander.descargar(archivo));
			}
			if (listasDeFicheros.containsKey(TIPO_BASTIDOR)) {
				listasDeFicheros.get(TIPO_BASTIDOR).addAll(listadoDeFicheros);
			} else {
				listasDeFicheros.put(TIPO_BASTIDOR, listadoDeFicheros);
			}
		}
		if (listaLE != null && !listaLE.isEmpty()) {
			List<FicheroBean> listadoDeFicheros = new ArrayList<FicheroBean>();
			Collections.sort(listaLE);
			for (String archivo : listaLE) {
				listadoDeFicheros.add(servicioProcesoImportacionSantander.descargar(archivo));
			}
			if (listasDeFicheros.containsKey(TIPO_BASTIDOR)) {
				listasDeFicheros.get(TIPO_BASTIDOR).addAll(listadoDeFicheros);
			} else {
				listasDeFicheros.put(TIPO_BASTIDOR, listadoDeFicheros);
			}
		}
		if (listaRE != null && !listaRE.isEmpty()) {
			List<FicheroBean> listadoDeFicheros = new ArrayList<FicheroBean>();
			Collections.sort(listaRE);
			for (String archivo : listaRE) {
				listadoDeFicheros.add(servicioProcesoImportacionSantander.descargar(archivo));
			}
			if (listasDeFicheros.containsKey(TIPO_BASTIDOR)) {
				listasDeFicheros.get(TIPO_BASTIDOR).addAll(listadoDeFicheros);
			} else {
				listasDeFicheros.put(TIPO_BASTIDOR, listadoDeFicheros);
			}
		}
		if (listaFR != null && !listaFR.isEmpty()) {
			List<FicheroBean> listadoDeFicheros = new ArrayList<FicheroBean>();
			Collections.sort(listaFR);
			for (String archivo : listaFR) {
				listadoDeFicheros.add(servicioProcesoImportacionSantander.descargar(archivo));
			}
			if (listasDeFicheros.containsKey(TIPO_BASTIDOR)) {
				listasDeFicheros.get(TIPO_BASTIDOR).addAll(listadoDeFicheros);
			} else {
				listasDeFicheros.put(TIPO_BASTIDOR, listadoDeFicheros);
			}
		}

		if (listaDM != null && !listaDM.isEmpty()) {
			List<FicheroBean> listadoDeFicheros = new ArrayList<FicheroBean>();
			Collections.sort(listaDM);
			for (String archivo : listaDM) {
				listadoDeFicheros.add(servicioProcesoImportacionSantander.descargar(archivo));
			}
			listasDeFicheros.put(DM, listadoDeFicheros);
		}
		return listasDeFicheros;
	}

	private void procesarFicherosBastidores(Map<String, List<FicheroBean>> listasImportacion) {
		String enviarCorreoNoHayFicherosVOVN = gestorPropiedades.valorPropertie("ftp.importacion.datossensibles.enviar.correo.no.ficheros");
		String recipentVOVN = gestorPropiedades.valorPropertie("proceso.santander.email.informe.destinatario");
		String direccionesOcultasVOVN = gestorPropiedades.valorPropertie("proceso.santander.email.informe.cco");
		String subjectVOVN = gestorPropiedades.valorPropertie("proceso.santander.email.informe.asunto");

		// DATOS DEL USUARIO QUE REALIZA LA IMPORTACION
		String grupoDM = gestorPropiedades.valorPropertie("importacion.datossensibles.grupo");

		// SE ENVIARA UN EMAIL SI EL FTP ESTA VACIO
		String enviarCorreoNoHayFicherosDM = gestorPropiedades.valorPropertie("proceso.demo.enviar.correo.no.ficheros");
		String recipentDM = gestorPropiedades.valorPropertie("proceso.demo.email.informe.destinatario");
		String direccionesOcultasDM = gestorPropiedades.valorPropertie("proceso.demo.email.informe.cco");
		String subjectDM = gestorPropiedades.valorPropertie("proceso.demo.email.informe.asunto");

		if (listasImportacion.containsKey(TIPO_BASTIDOR)) {
			String grupoVOVN = gestorPropiedades.valorPropertie("importacion.datossensibles.usuario.grupo");
			Map<String, List<ResultadoAltaBastidorBean>> resultadoVNVO = procesarFicherosBastidores(grupoVOVN, listasImportacion.get(TIPO_BASTIDOR));
			/* Envio el excel */
			try {
				servicioDatosSensibles.generarYEnviarExcel(resultadoVNVO, getProceso(), listasImportacion.get(TIPO_BASTIDOR), recipentVOVN, direccionesOcultasVOVN, subjectVOVN);
			} catch (Throwable e) {
				log.error("Ha ocurrido un error al general el excel", e);
			}
		} else {
			log.error("ProcesoImportacionDatosSensibles: no se han encontrado datos para importar");
			// Mantis #18011 Se controla por property el envio de correos cuando no hay archivos
			Boolean enviarCorreoNoHayFicheros = Boolean.FALSE;
			try {
				enviarCorreoNoHayFicheros = new Boolean(enviarCorreoNoHayFicherosVOVN);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (enviarCorreoNoHayFicheros) {
				log.error("Se va a enviar un email para avisar de que no hay archivos");
				servicioDatosSensibles.enviarCorreoNoSeEncontraronFicherosFTP(getProceso(), TIPO_BASTIDOR, recipentVOVN, direccionesOcultasVOVN);
			} else {
				log.error("No se encontraron archivos, pero no se enviara el email por estar la property a " + enviarCorreoNoHayFicheros);
			}
		}

		if (listasImportacion.containsKey(DM)) {
			Map<String, List<ResultadoAltaBastidorBean>> resultadoDM = procesarFicherosBastidores(grupoDM, listasImportacion.get(DM));

			/* Envio el excel */
			try {
				servicioDatosSensibles.generarYEnviarExcel(resultadoDM, getProceso(), listasImportacion.get(DM), recipentDM, direccionesOcultasDM, subjectDM);
			} catch (Throwable e) {
				log.error("Ha ocurrido un error al general el excel", e);
			}
		} else {
			log.error("ProcesoImportacionDatosSensibles: no se han encontrado datos para importar");
			// Mantis #18011 Se controla por property el envio de correos cuando no hay archivos
			Boolean enviarCorreoNoHayFicheros = Boolean.FALSE;
			try {
				enviarCorreoNoHayFicheros = new Boolean(enviarCorreoNoHayFicherosDM);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (enviarCorreoNoHayFicheros) {
				log.error("Se va a enviar un email para avisar de que no hay archivos");
				servicioDatosSensibles.enviarCorreoNoSeEncontraronFicherosFTP(getProceso(), DM, recipentDM, direccionesOcultasDM);
			} else {
				log.error("No se encontraron archivos, pero no se enviara el email por estar la property a " + enviarCorreoNoHayFicheros);
			}
		}
	}

	private Map<String, List<ResultadoAltaBastidorBean>> procesarFicherosBastidores(String grupo, List<FicheroBean> listasImportacion) {
		Map<String, List<ResultadoAltaBastidorBean>> resultadoEjecucionComleta = new LinkedHashMap<String, List<ResultadoAltaBastidorBean>>();

		for (FicheroBean fichero : listasImportacion) {
			try {
				List<ResultadoAltaBastidorBean> listaResultadoAltaBastidor = new ArrayList<ResultadoAltaBastidorBean>();
				/* Importamos bastidores */
				DatosSensiblesBean datosSensiblesBean = new DatosSensiblesBean();
				datosSensiblesBean.setGrupo(grupo);
				datosSensiblesBean.setTiempoDatosSensibles(new BigDecimal(TiempoBajaDatosSensibles.No_caduca.toString()));
				TipoFicheroImportacionBastidores tipoFich = TipoFicheroImportacionBastidores.convertirTexto(fichero.getExtension());
				if (tipoFich != null) {
					datosSensiblesBean.setTipoFichero(String.valueOf(tipoFich.getCodigo()));
				} else {
					datosSensiblesBean.setTipoFichero("" + TipoFicheroImportacionBastidores.DAT.getCodigo());
				}

				ResultadoDatosSensibles result = servicioDatosSensibles.importarBastidor(datosSensiblesBean, fichero, new BigDecimal(gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_CONTRATO)), gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_NUM_COLEGIADO), new BigDecimal(
						gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_USUARIO)),
						gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_APELLIDOS_NOMBRE), listaResultadoAltaBastidor, "SANTANDER");

				if (result.isError()) {
					if (result.getListaMensajes() == null && !result.getMensajeError().isEmpty()) {
						log.error(result.getMensajeError());
					} else if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
						for (String mensaje : result.getListaMensajes()) {
							log.error(mensaje);
						}
					}
				}
				resultadoEjecucionComleta.put(fichero.getNombreDocumento() + fichero.getExtension(), listaResultadoAltaBastidor);

			} catch (OegamExcepcion e) {
				log.error("Se ha producido un error al importar los datos sensibles, Error: " + e);
			} catch (Exception e) {
				log.error("Ocurrio un error no controlado", e);
			}
		}
		return resultadoEjecucionComleta;
	}

	private void custodiarFichero(Map<String, List<FicheroBean>> listasImportacion) {
		for (List<FicheroBean> ficheros : listasImportacion.values()) {
			for (FicheroBean ficheroBean : ficheros) {
				ficheroBean.setTipoDocumento(ConstantesGestorFicheros.TIPO_DOC_IMPORTACION_DATOS_SENSIBLES);
				ficheroBean.setFecha(utilesFecha.getFechaActual());
				ficheroBean.setNombreDocumento(ficheroBean.getNombreDocumento().replace(gestorPropiedades.valorPropertie("documentos.ftp.santander"), ""));
				ficheroBean.setSubTipo(getProceso());
				try {
					gestorDocumentos.guardarByte(ficheroBean);
					if (log.isInfoEnabled()) {
						log.info("Archivo " + ficheroBean.getNombreDocumento() + " custodiado correctamente.");
					}
				} catch (OegamExcepcion e) {
					log.error("Ha ocurrido un error custodiando el archivo " + ficheroBean.getNombreDocumento() + " de importacion de datos sensibles", e);
				}
			}
		}
	}

	private void borrarFicherosBastidores(Map<String, List<FicheroBean>> listasImportacion) {
		try {
			for (List<FicheroBean> ficheros : listasImportacion.values()) {
				for (FicheroBean ficheroBean : ficheros) {
					servicioProcesoImportacionSantander.borrar(ficheroBean.getNombreYExtension());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.SANTANDER.toString();
	}

}