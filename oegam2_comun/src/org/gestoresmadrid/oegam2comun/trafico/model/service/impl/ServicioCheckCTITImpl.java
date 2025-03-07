package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.core.general.model.dao.ColegiadoDao;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioCheckCTIT;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import trafico.beans.schemas.generated.checkctit.DatosFirmados;
import trafico.beans.schemas.generated.checkctit.DatosFirmados.ListaAdquirientes;
import trafico.beans.schemas.generated.checkctit.DatosFirmados.ListaTransmitentes;
import trafico.beans.schemas.generated.checkctit.ObjectFactory;
import trafico.beans.schemas.generated.checkctit.SolicitudTramite;
import trafico.beans.schemas.generated.checkctit.TipoTramite;
import trafico.utiles.XmlCheckCTITFactory;
import trafico.utiles.enumerados.TipoTransferencia;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioCheckCTITImpl implements ServicioCheckCTIT, Serializable {

	private static final long serialVersionUID = 2784314449923897314L;

	private static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";
	private static final String ERROR = "ERROR";
	private static final String ERROR_AL_FIRMAR_CON_EL_COLEGIADO = "Error al firmar con el colegiado";
	private static final String VERSION_1_0 = "1.0";
	private static final String SOLICITUD_TRAMITE = "solicitud_tramite";
	private static final String FICHERO = "fichero";
	private static final String ERRORES = "errores";

	private final ILoggerOegam log = LoggerOegam.getLogger(ServicioCheckCTITImpl.class);

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ColegiadoDao colegiadoDao;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	@Transactional
	public ResultBean crearSolicitud(BigDecimal numExpediente, String nombreXml, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultBean resultadoMetodo = new ResultBean();
		resultadoMetodo.setError(false);

		EstadoTramiteTrafico nuevoEstado = EstadoTramiteTrafico.Pendiente_Check_Ctit;
		try {
			ResultBean resultBeanCambiarEstado = servicioTramiteTrafico.cambiarEstadoConEvolucion(numExpediente, org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico.Iniciado, nuevoEstado,
					false, null, utilesColegiado.getIdUsuarioSessionBigDecimal());

			if (resultBeanCambiarEstado.getError()) {
				resultadoMetodo.setError(true);
				resultadoMetodo.setMensaje("Error al cambiar el estado del trámite");
				resultadoMetodo.addListaMensajes(resultBeanCambiarEstado.getListaMensajes());
			} else {
				ResultBean resultBean = null;
				if("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("nuevas.url.sega.checkCtit"))){
					resultBean = servicioCola.crearSolicitud(ProcesosEnum.CHECK_CTIT_SEGA.getNombreEnum(), nombreXml, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD),
							TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), numExpediente.toString(), idUsuario, null, idContrato);
				} else{
					ContratoDto contrato = servicioContrato.getContratoDto(idContrato);
					String gestionarConAM = gestorPropiedades.valorPropertie("gestionar.conAm");
					if("2631".equals(contrato.getColegiadoDto().getNumColegiado()) && "SI".equals(gestionarConAM)) {
						resultBean = servicioCola.crearSolicitud(ProcesosAmEnum.CHECK_CTIT.toString(), null, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD),
								TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), numExpediente.toString(), idUsuario, null, idContrato);
					}else {
						resultBean = servicioCola.crearSolicitud(ConstantesProcesos.PROCESO_CHECKCTIT, nombreXml, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD),
								TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), numExpediente.toString(), idUsuario, null, idContrato);
					}
				}
				if (!resultBean.getError()) {
					resultadoMetodo.setMensaje("Solicitud creada correctamente");
				} else {
					// Dejamos realizar rollback para volver a dejar el estado del tramite en iniciado
					throw new TransactionalException("Error al crear la solicitud");
				}
			}
		} catch (OegamExcepcion oe) {
			log.error("Error al cambiar el estado del tramite", oe, numExpediente.toString());
			throw new TransactionalException(oe);
		}

		return resultadoMetodo;
	}

	// // Si se trata de una entrega a compraventa, el adquiriente será el poseedor.
	// if(!TipoTransferencia.tipo5.equals(tipoTransferencia)){
	// listaAdquirientes.getDOI().add(nifAdquiriente);
	// }else{
	// listaAdquirientes.getDOI().add(nifPoseedor);
	// }
	@Override
	@Transactional
	public Map<String, Object> generarXMLCheckCTIT(String numColegiado, BigDecimal numExpediente, TipoTransferencia tipoTransferencia, String matricula, String nifAdquiriente, String nifTransmitente,
			String nifPrimerCotitular, String nifSegundoCotitular) {
		Map<String, Object> resultado = new HashMap<>();

		List<String> errores = new ArrayList<>();

		// Creamos cada uno de los nodos del XML
		// Comenzamos por el Object Factory
		ObjectFactory objFactory = new ObjectFactory();

		// NODO PRINCIPAL: SolicitudTramite
		SolicitudTramite solTramite = objFactory.createSolicitudTramite();

		// NODO SECUNDARIO: DatosFirmados
		DatosFirmados datosFirmados = objFactory.createDatosFirmados();

		// Evitamos el acceso a sesión
		ColegiadoVO colegiado = colegiadoDao.getColegiado(numColegiado);

		datosFirmados.setNIFGestor(colegiado.getUsuario().getNif());
		datosFirmados.setMatricula(matricula);

		// Lista transmitentes
		ListaTransmitentes listaTransmitentes = objFactory.createDatosFirmadosListaTransmitentes();
		// Transmitente
		listaTransmitentes.getDOI().add(nifTransmitente);
		// Cotitulares transmitentes
		if (nifPrimerCotitular != null && !nifPrimerCotitular.isEmpty()) {
			listaTransmitentes.getDOI().add(nifPrimerCotitular);
		}
		if (nifSegundoCotitular != null && !nifSegundoCotitular.isEmpty()) {
			listaTransmitentes.getDOI().add(nifSegundoCotitular);
		}
		// Seteamos
		datosFirmados.setListaTransmitentes(listaTransmitentes);

		// Lista adquirientes
		ListaAdquirientes listaAdquirientes = objFactory.createDatosFirmadosListaAdquirientes();
		// Adquiriente

		listaAdquirientes.getDOI().add(nifAdquiriente);

		// Seteamos
		datosFirmados.setListaAdquirientes(listaAdquirientes);

		// Antes era tipo1
		if (TipoTransferencia.tipo1.equals(tipoTransferencia) || TipoTransferencia.tipo2.equals(tipoTransferencia) || TipoTransferencia.tipo3.equals(tipoTransferencia)) {
			datosFirmados.setTipoTramite(TipoTramite.CTI);
		} else if (TipoTransferencia.tipo4.equals(tipoTransferencia)) {
			datosFirmados.setTipoTramite(TipoTramite.NOT);
		} else if (TipoTransferencia.tipo5.equals(tipoTransferencia)) {
			datosFirmados.setTipoTramite(TipoTramite.ENT);
		}

		datosFirmados.setServicioDelVehiculo(null);
		// QUITAMOS SERVICIO A MANO
		datosFirmados.setTextoLegal("");

		// Seteamos los DATOS FIRMADOS
		solTramite.setDatosFirmados(datosFirmados);

		// Seteamos la FIRMA
		solTramite.setFirmaGestor(new byte[0]);

		// Seteamos la VERSION
		solTramite.setVersion(VERSION_1_0);

		ResultBean resultadoFirmasBean = new ResultBean();

		try {
			resultadoFirmasBean = anhadirFirmasCheckCTITHSM(solTramite, numColegiado);
		} catch (Exception e) {
			resultadoFirmasBean.setError(true);
			log.error(e);
		}

		// Se crea un fichero xml de envío

		/** Cambio Gestor de Documentos **/

		if (!resultadoFirmasBean.getError()) {
			XmlCheckCTITFactory xmlFactory = new XmlCheckCTITFactory();
			String xmlFirmado = xmlFactory.toXML(solTramite);
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.CHECKCTIT);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.CTITENVIO); 
			ficheroBean.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_CHECKCTIT + String.valueOf(numExpediente));
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
			ficheroBean.setSobreescribir(true);

			try {
				gestorDocumentos.crearFicheroXml(ficheroBean, XmlCheckCTITFactory.NAME_CONTEXT, solTramite, xmlFirmado, null);
			} catch (OegamExcepcion e) {
				errores.add("No se pudo generar el fichero");
				log.error("Error al guardar el fichero", e, numExpediente.toString());
			} catch (Throwable e) {
				errores.add("No se pudo generar el fichero XML");
				log.error("Error al guardar el XML de check CTIT en disco", e, numExpediente.toString());
			}

			String nombreFich = ficheroBean.getFichero().getPath();

			log.info("Incluimos " + nombreFich + " al hashmap de resultado de generar xml para el tramite: " + numExpediente);
			resultado.put(FICHERO, nombreFich);
			resultado.put(SOLICITUD_TRAMITE, solTramite);
		}
		if (!errores.isEmpty() || resultadoFirmasBean.getError()) {
			errores.add(resultadoFirmasBean.getMensaje());
			resultado.put(ERRORES, errores);
		}

		return resultado;
	}

	/**
	 * Método para añadir las firmas de colegio y colegiado al XML para la llamada a CTIT
	 * @param solicitudTramite
	 * @param numColegiado
	 * @return
	 * @throws Exception
	 */
	private ResultBean anhadirFirmasCheckCTITHSM(SolicitudTramite solicitudTramite, String numColegiado) throws Exception {
		ResultBean resultFirmasBean = new ResultBean();

		/**
		 * Firma del XML por el Colegiado
		 */
		ResultBean resultBeanFirmaColegiado = realizarFirmaDatosFirmadosCheckCTIT(solicitudTramite, numColegiado);
		if (!resultBeanFirmaColegiado.getError()) {
			solicitudTramite.setFirmaGestor(resultBeanFirmaColegiado.getMensaje().getBytes(StandardCharsets.UTF_8));
		} else {
			resultFirmasBean.setError(true);
			resultFirmasBean.setMensaje(ERROR_AL_FIRMAR_CON_EL_COLEGIADO);
		}

		return resultFirmasBean;
	}

	/**
	 * @param solicitudTramite
	 * @param numColegiado
	 * @return
	 */
	private ResultBean realizarFirmaDatosFirmadosCheckCTIT(SolicitudTramite solicitudTramite, String numColegiado) {
		XmlCheckCTITFactory xmlCheckCTITFactory = new XmlCheckCTITFactory();
		String xml = xmlCheckCTITFactory.toXML(solicitudTramite);
		// Quitamos los namespaces porque da error
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");

		// Obtenemos los datos que realmente se tienen que firmar
		int inicio = xml.indexOf("<Datos_Firmados>") + 16;
		int fin = xml.indexOf("</Datos_Firmados>");
		String datosFirmados = xml.substring(inicio, fin);
		if (log.isInfoEnabled()) {
			log.info("LOG_CHECKCTIT DATOS FIRMADOS " + datosFirmados);
			log.info("Datos a firmar:" + datosFirmados);
		}

		String encodedAFirmar = null;
		try {
			encodedAFirmar = utiles.doBase64Encode(datosFirmados.getBytes(StandardCharsets.UTF_8)).replaceAll("\n", "");
			if (log.isInfoEnabled()) {
				log.info("LOG_CHECKCTIT ENCODED A FIRMAR: " + encodedAFirmar);
			}
		} catch (Exception e) {
			log.error("Error al codificar en base 64 el UTF-8");
			// Para mantener el funcionamiento igual que en ModeloTransmision
			encodedAFirmar = "";
		}

		String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">" + encodedAFirmar + "</CONTENT></AFIRMA>";
		// Se construye el XML que contiene los datos a firmar
		if (log.isInfoEnabled()) {
			log.info("XML a firmar:" + xmlAFirmar);
		}
		return firmarCheckCTIT(xmlAFirmar, numColegiado);
	}

	private ResultBean firmarCheckCTIT(String cadenaXML, String numColegiado) {
		UtilesViafirma utilesViafirma = new UtilesViafirma();

		ResultBean resultBean = new ResultBean();

		String idFirma = null;
		try {
			if(utilesViafirma.firmarPruebaCertificadoCaducidad(cadenaXML, utilesColegiado.getAlias(numColegiado))==null
					|| "".equals(utilesViafirma.firmarPruebaCertificadoCaducidad(cadenaXML, utilesColegiado.getAlias(numColegiado)))){
				resultBean.setMensaje("Ha ocurrido un error durante la prueba de caducidad de certificado");
				resultBean.setError(true);
			}else{
				idFirma = utilesViafirma.firmarMensajeTransmisionServidor(cadenaXML, utilesColegiado.getAlias(numColegiado));
				resultBean.setMensaje(idFirma);
				log.info(idFirma);
			}
		} catch (Exception e) {
			resultBean.setMensaje("Ha ocurrido un error durante el proceso de firma en servidor del trámite");
			resultBean.setError(true);
			log.error(e);
		}

		if (ERROR.equals(idFirma)) {
			// Ha fallado el proceso de firma en servidor
			resultBean.setMensaje("Ha ocurrido un error durante el proceso de firma en servidor del trámite");
			resultBean.setError(true);
		} else if (idFirma != null) {
			// Firma correctamente
			try {
				if (!idFirma.isEmpty()) {
					// SI el idFirma es vacio es porque firmo el colegiado (según ModeloTransmision)
					String xmlFirmado = utilesViafirma.getDocumentoFirmado(idFirma);
					if (log.isInfoEnabled()) {
						log.info("XML custodiado recuperado a traves del idFirma=" + idFirma);
					}
					if (log.isDebugEnabled()) {
						log.debug("XML firmado : " + xmlFirmado);
					}
					resultBean.setMensaje(xmlFirmado);
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		return resultBean;
	}

	public ServicioTramiteTrafico getServicioTramiteTrafico() {
		return servicioTramiteTrafico;
	}

	public void setServicioTramiteTrafico(ServicioTramiteTrafico servicioTramiteTrafico) {
		this.servicioTramiteTrafico = servicioTramiteTrafico;
	}

	public ServicioCola getServicioCola() {
		return servicioCola;
	}

	public void setServicioCola(ServicioCola servicioCola) {
		this.servicioCola = servicioCola;
	}

	public ColegiadoDao getColegiadoDao() {
		return colegiadoDao;
	}

	public void setColegiadoDao(ColegiadoDao colegiadoDao) {
		this.colegiadoDao = colegiadoDao;
	}

}