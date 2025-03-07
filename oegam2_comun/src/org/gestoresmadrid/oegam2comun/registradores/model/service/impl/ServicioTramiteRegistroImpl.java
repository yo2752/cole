package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados.EstadoDatosBancarios;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados.TipoCuentaBancaria;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.vo.DatosBancariosFavoritosVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.registradores.constantes.ConstantesRegistradores;
import org.gestoresmadrid.core.registradores.model.dao.InmuebleDao;
import org.gestoresmadrid.core.registradores.model.dao.TramiteRegistroDao;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.Inmatriculada;
import org.gestoresmadrid.core.registradores.model.enumerados.Presentante;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoAcuerdo;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoCargo;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoDocumento;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoEntrada;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoFormaPago;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonalidad;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoSubsanacion;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.vo.InmuebleVO;
import org.gestoresmadrid.core.registradores.model.vo.TramiteRegistroVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.model.service.ServicioDatosBancariosFavoritos;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.dpr.build.BuildDpr;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.BienEscrituraType;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.FORMATOGA.ESCRITURA;
import org.gestoresmadrid.oegam2comun.registradores.importExport.jaxb.RegistroType;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioAcuerdo;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioAsistente;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioCertifCargo;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioConvocatoria;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioEvolucionTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioFacturaRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioInmueble;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioIntervinienteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioLibroRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioMedioConvocatoria;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioOperacionRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistroFueraSecuencia;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioReunion;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegRbm;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.rm.build.BuildRm;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesRegistradores;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.AcuerdoDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.AsistenteDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.CertifCargoDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.EvolucionTramiteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FacturaRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.InmuebleDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.MedioConvocatoriaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.OperacionRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroFueraSecuenciaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ReunionDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.xml.estilos.StaxStreamSource;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import general.utiles.Anagrama;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.utiles.UtilesExcepciones;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioTramiteRegistroImpl implements ServicioTramiteRegistro {

	private static final long serialVersionUID = 4979656672526882742L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioTramiteRegistroImpl.class);

	private static final String REGISTRADORES_MAIL_SUBJECT = "Notificación de solicitud de firma";

	private static final String NOMBRE_HOST_SOLICITUD2 = "nombreHostSolicitudProcesos2";

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private TramiteRegistroDao tramiteRegistroDao;

	@Autowired
	private InmuebleDao inmuebleDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioNotificacion servicioNotificacion;

	@Autowired
	private ServicioEvolucionTramiteRegistro servicioEvolucionTramiteRegistro;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioRegistro servicioRegistro;

	@Autowired
	private ServicioCertifCargo servicioCertifCargo;

	@Autowired
	private ServicioReunion servicioReunion;

	@Autowired
	private ServicioAcuerdo servicioAcuerdo;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioIntervinienteRegistro servicioIntervinienteRegistro;

	@Autowired
	private ServicioTramiteRegRbm servicioTramiteRegRbm;

	@Autowired
	private ServicioEvolucionPersona servicioEvolucionPersona;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioRegistroFueraSecuencia servicioRegistroFueraSecuencia;

	@Autowired
	private ServicioMedioConvocatoria servicioMedioConvocatoria;

	@Autowired
	private ServicioConvocatoria servicioConvocatoria;

	@Autowired
	private ServicioAsistente servicioAsistente;

	@Autowired
	private ServicioOperacionRegistro servicioOperacionRegistro;

	@Autowired
	private ServicioInmueble servicioInmueble;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private BuildRm buildRm;

	@Autowired
	private ServicioDatosBancariosFavoritos servicioDatosBancariosFavoritos;

	@Autowired
	private ServicioFacturaRegistro servicioFacturaRegistro;

	@Autowired
	ServicioLibroRegistro servicioLibroRegistro;

	@Autowired
	private BuildDpr buildDpr;

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private Utiles utiles;

	boolean primerGuardado = false;

	@Override
	@Transactional
	public TramiteRegistroDto getTramite(BigDecimal idTramiteRegistro) {
		TramiteRegistroDto tramiteRegistroDto = null;
		try {
			if (idTramiteRegistro != null) {
				TramiteRegistroVO tramiteVO = tramiteRegistroDao.getTramite(idTramiteRegistro);
				if (tramiteVO != null) {
					tramiteRegistroDto = conversor.transform(tramiteVO, TramiteRegistroDto.class);
					incluirDatos(tramiteRegistroDto);
					if (!TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistroDto.getTipoTramite())) {
						incluirDatosMercantil(tramiteRegistroDto);
					} else {
						incluirDatosPropiedad(tramiteRegistroDto);
					}

					// En caso que la forma de pago sea por cargo a cuenta se recupera la cuenta
					if (null != tramiteRegistroDto.getFormaPago() && TipoFormaPago.CUENTA.getValorEnum().equals(tramiteRegistroDto.getFormaPago().toString())) {
						recuperarDatosFormaPagoCuenta(tramiteRegistroDto, tramiteVO);
					}

					ArrayList<FicheroInfo> ficherosSubidos = recuperarDocumentos(tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getTipoTramite());
					if (null != ficherosSubidos && !ficherosSubidos.isEmpty()) {
						tramiteRegistroDto.setFicherosSubidos(ficherosSubidos);
					}

					ArrayList<FicheroInfo> ficherosRecibidos = recuperarDocumentosRecibidos(tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getTipoTramite());
					if (null != ficherosRecibidos && !ficherosRecibidos.isEmpty()) {
						tramiteRegistroDto.setListaDocuRecibida(ficherosRecibidos);
					}

					tramiteRegistroDto.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistroDto.getNumColegiado(), tramiteRegistroDto.getIdContrato()));
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de registro: " + idTramiteRegistro, e, idTramiteRegistro.toString());
		}
		return tramiteRegistroDto;
	}

	@Override
	@Transactional
	public TramiteRegistroDto getTramiteIdCorpme(String idTramiteCorpme) {
		TramiteRegistroDto tramiteRegistroDto = null;
		try {
			if (idTramiteCorpme != null && !idTramiteCorpme.isEmpty()) {
				TramiteRegistroVO tramiteVO = tramiteRegistroDao.getTramiteIdCorpme(idTramiteCorpme);
				if (tramiteVO != null) {
					tramiteRegistroDto = conversor.transform(tramiteVO, TramiteRegistroDto.class);
					incluirDatos(tramiteRegistroDto);
					if (!TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistroDto.getTipoTramite())) {
						incluirDatosMercantil(tramiteRegistroDto);
					} else {
						incluirDatosPropiedad(tramiteRegistroDto);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de registro con identificador de Corpme: " + idTramiteCorpme, e);
		}
		return tramiteRegistroDto;
	}

	@Override
	@Transactional
	public List<TramiteRegistroDto> getTramiteIdCorpmeConCodigoRegistro(String idTramiteCorpme, String codigoRegistro) {
		List<TramiteRegistroDto> listaDto = new ArrayList<TramiteRegistroDto>();
		try {
			if (idTramiteCorpme != null && !idTramiteCorpme.isEmpty()) {
				List<TramiteRegistroVO> listaVOs = tramiteRegistroDao.getTramiteIdCorpmeConCodigoRegistro(idTramiteCorpme, codigoRegistro);
				if (listaVOs != null && !listaVOs.isEmpty()) {
					for (TramiteRegistroVO tramiteVO : listaVOs) {
						TramiteRegistroDto tramiteRegistroDto = conversor.transform(tramiteVO, TramiteRegistroDto.class);
						incluirDatos(tramiteRegistroDto);
						if (!TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistroDto.getTipoTramite())) {
							incluirDatosMercantil(tramiteRegistroDto);
						} else {
							incluirDatosPropiedad(tramiteRegistroDto);
						}
						listaDto.add(tramiteRegistroDto);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar el trámite de registro con identificador de Corpme: " + idTramiteCorpme, e);
		}
		return listaDto;
	}

	private void incluirDatos(TramiteRegistroDto tramiteRegistroDto) {
		if (StringUtils.isNotBlank(tramiteRegistroDto.getIdRegistro())) {
			RegistroDto registroDto = servicioRegistro.getRegistroPorId(Long.parseLong(tramiteRegistroDto.getIdRegistro()));
			tramiteRegistroDto.setRegistro(registroDto);
			tramiteRegistroDto.setIdRegistro(Long.toString(registroDto.getId()));
			tramiteRegistroDto.setRegistroSeleccionadoOculto(Long.toString(registroDto.getId()));
		}

		if (tramiteRegistroDto.getFicheroSubido() != null && "si".equalsIgnoreCase(tramiteRegistroDto.getFicheroSubido())) {
			tramiteRegistroDto.setFicherosSubidos(recuperarDocumentos(tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getTipoTramite()));
		}

		PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(tramiteRegistroDto.getNumColegiado(), tramiteRegistroDto.getIdContrato());
		if (colegiado != null) {
			tramiteRegistroDto.setNcorpme(colegiado.getNcorpme());
			tramiteRegistroDto.setUsuarioCorpme(colegiado.getUsuarioCorpme());
			tramiteRegistroDto.setPasswordCorpme(colegiado.getPasswordCorpme());
		}

		tramiteRegistroDto.setAcusesPendientes(servicioRegistroFueraSecuencia.getRegistrosFueraSecuencia(tramiteRegistroDto.getIdTramiteRegistro()));
		if (null != tramiteRegistroDto.getAcusesPendientes() && !tramiteRegistroDto.getAcusesPendientes().isEmpty()) {
			Collections.sort(tramiteRegistroDto.getAcusesPendientes(), new ComparadorAcuses());
		}

	}

	private class ComparadorAcuses implements Comparator<RegistroFueraSecuenciaDto> {

		@Override
		public int compare(RegistroFueraSecuenciaDto o1, RegistroFueraSecuenciaDto o2) {
			try {
				return (o1.getFechaAlta().getDate()).compareTo(o2.getFechaAlta().getDate());
			} catch (ParseException e) {
				log.error("Error al ordenar por fecha los acuses pendientes: ", e);
			}
			return 0;
		}

	}

	private void incluirDatosMercantil(TramiteRegistroDto tramiteRegistroDto) {
		if (tramiteRegistroDto.getSociedad() != null && tramiteRegistroDto.getSociedad().getNif() != null && !tramiteRegistroDto.getSociedad().getNif().isEmpty()) {
			tramiteRegistroDto.setSociedad(servicioPersona.getPersona(tramiteRegistroDto.getSociedad().getNif(), tramiteRegistroDto.getNumColegiado()));
			if (tramiteRegistroDto.getSociedad() != null && tramiteRegistroDto.getIdDireccionDestinatario() != null) {
				tramiteRegistroDto.getSociedad().setDireccionDto(servicioDireccion.getDireccionDto(tramiteRegistroDto.getIdDireccionDestinatario()));
			}
			tramiteRegistroDto.setSociedadEstablecida(true);
		}

		tramiteRegistroDto.setCertificantes(servicioCertifCargo.getCertificantes(tramiteRegistroDto.getIdTramiteRegistro()));

		tramiteRegistroDto.setReunion(servicioReunion.getReunion(tramiteRegistroDto.getIdTramiteRegistro()));
		if (tramiteRegistroDto.getReunion() != null) {
			tramiteRegistroDto.setReunionEstablecida(true);
			if (TipoTramiteRegistro.MODELO_3.getValorEnum().equals(tramiteRegistroDto.getTipoTramite()) || TipoTramiteRegistro.MODELO_4.getValorEnum().equals(tramiteRegistroDto.getTipoTramite())) {
				tramiteRegistroDto.getReunion().setMedios(servicioMedioConvocatoria.getMediosConvocatorias(tramiteRegistroDto.getIdTramiteRegistro()));
				tramiteRegistroDto.getReunion().setConvocatoria(servicioConvocatoria.getConvocatoria(tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getReunion().getIdReunion()));
			}
		}
		incluirCeseYNombramiento(tramiteRegistroDto);

		if (TipoTramiteRegistro.MODELO_5.getValorEnum().equals(tramiteRegistroDto.getTipoTramite())) {
			tramiteRegistroDto.setAsistentes(servicioAsistente.getAsistentes(tramiteRegistroDto.getIdTramiteRegistro()));
		}
		tramiteRegistroDto.setFacturasRegistro(servicioFacturaRegistro.getFacturasPorTramite(tramiteRegistroDto.getIdTramiteRegistro()));
	}

	private void incluirDatosPropiedad(TramiteRegistroDto tramiteRegistroDto) {
		tramiteRegistroDto.setEscrituraEstablecida(true);

		if (tramiteRegistroDto.getOperacion() != null && !tramiteRegistroDto.getOperacion().isEmpty()) {
			OperacionRegistroDto operacionRegistro = servicioOperacionRegistro.getOperacionRegistro(tramiteRegistroDto.getOperacion(), tramiteRegistroDto.getTipoTramite());
			if (operacionRegistro != null) {
				tramiteRegistroDto.setOperacionDes(operacionRegistro.getDescripcion());
			}
		}

		if (tramiteRegistroDto.getSociedad() != null && (tramiteRegistroDto.getSociedad().getNif() != null && !tramiteRegistroDto.getSociedad().getNif().isEmpty())) {
			tramiteRegistroDto.setSociedad(servicioPersona.getPersona(tramiteRegistroDto.getSociedad().getNif(), tramiteRegistroDto.getNumColegiado()));
			if (tramiteRegistroDto.getSociedad() != null && tramiteRegistroDto.getIdDireccionDestinatario() != null) {
				tramiteRegistroDto.getSociedad().setDireccionDto(servicioDireccion.getDireccionDto(tramiteRegistroDto.getIdDireccionDestinatario()));
			}
		}

		if (StringUtils.isNotBlank(tramiteRegistroDto.getSubsanacion())) {
			if (TipoEntrada.PRIMERA.getValorEnum().equals(tramiteRegistroDto.getSubsanacion())) {
				tramiteRegistroDto.setCheckPrimera(true);
			} else if (tramiteRegistroDto.getSubsanacion().contains(TipoEntrada.SUBSANACION.getValorEnum())) {
				tramiteRegistroDto.setCheckSubsanacion(true);
				if (TipoSubsanacion.DESDE_SUSPENDIDA_CALIFICACION.getValorEnum().equals(tramiteRegistroDto.getSubsanacion())) {
					tramiteRegistroDto.setSuspension(true);
				}
				if (StringUtils.isNotBlank(tramiteRegistroDto.getCodigoNotaria()) || StringUtils.isNotBlank(tramiteRegistroDto.getCodigoNotario()) || StringUtils.isNotBlank(tramiteRegistroDto
						.getProtocolo()) || tramiteRegistroDto.getAnioProtocolo() != null) {
					tramiteRegistroDto.setIdentificacionNotarial(true);
				} else if (tramiteRegistroDto.getNumRegSub() != null || tramiteRegistroDto.getAnioRegSub() != null) {
					tramiteRegistroDto.setIdentificacionNumeroEntrada(true);
				}
			}
		}

		incluirInmuebles(tramiteRegistroDto);
	}

	@Override
	@Transactional
	public ArrayList<FicheroInfo> recuperarDocumentos(BigDecimal idTramite, String tipoTramite) {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + " recuperarDocumentos");
		ArrayList<FicheroInfo> ficherosSubidos = new ArrayList<FicheroInfo>();

		if (null != idTramite && StringUtils.isNotBlank(tipoTramite)) {
			try {
				String carpeta = "";
				String subcarpeta = "";
				if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tipoTramite)) {
					carpeta = ConstantesGestorFicheros.ESCRITURAS;
					subcarpeta = ConstantesGestorFicheros.ESCRITURAS_DOCUMENTACION_ENVIADA;
				} else {
					carpeta = ConstantesGestorFicheros.REGISTRADORES;
					subcarpeta = ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION_ENVIADA;
				}

				FileResultBean ficheros = gestorDocumentos.buscarFicheroPorNumExpTipo(carpeta, subcarpeta, Utilidades.transformExpedienteFecha(idTramite), idTramite.toString());
				if (null != ficheros.getFiles() && !ficheros.getFiles().isEmpty()) {
					for (File temporal : ficheros.getFiles()) {
						FicheroInfo ficheroInfo = new FicheroInfo(temporal, 0);
						ficherosSubidos.add(ficheroInfo);
					}
				}
			} catch (OegamExcepcion e) {
				log.error("Error al recuperar los documentos " + e.getMessage(), e, idTramite.toString());
			}
		}
		return ficherosSubidos;
	}

	@Override
	@Transactional
	public ArrayList<FicheroInfo> recuperarDocumentosRecibidos(BigDecimal idTramite, String tipoTramite) {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + " recuperarDocumentos");
		ArrayList<FicheroInfo> ficherosSubidos = new ArrayList<FicheroInfo>();
		try {
			String carpeta = "";
			String subcarpeta = ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION;
			if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tipoTramite)) {
				carpeta = ConstantesGestorFicheros.ESCRITURAS;
			} else {
				carpeta = ConstantesGestorFicheros.REGISTRADORES;
			}

			FileResultBean ficheros = gestorDocumentos.buscarFicheroPorNumExpTipo(carpeta, subcarpeta, Utilidades.transformExpedienteFecha(idTramite), idTramite.toString());
			if (null != ficheros.getFiles() && !ficheros.getFiles().isEmpty()) {
				for (File temporal : ficheros.getFiles()) {
					FicheroInfo ficheroInfo = new FicheroInfo(temporal, 0);
					ficherosSubidos.add(ficheroInfo);
				}
			}
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar los documentos recibidos" + e.getMessage(), e, idTramite.toString());
		}
		return ficherosSubidos;
	}

	private void incluirCeseYNombramiento(TramiteRegistroDto tramiteRegistroDto) {
		List<AcuerdoDto> acuerdos = servicioAcuerdo.getAcuerdos(tramiteRegistroDto.getIdTramiteRegistro());
		if (acuerdos != null && !acuerdos.isEmpty()) {
			tramiteRegistroDto.setCeses(new ArrayList<AcuerdoDto>());
			tramiteRegistroDto.setNombramientos(new ArrayList<AcuerdoDto>());
			for (AcuerdoDto acuerdo : acuerdos) {
				String apellidos = acuerdo.getSociedadCargo().getPersonaCargo().getApellido1RazonSocial();
				if (acuerdo.getSociedadCargo().getPersonaCargo().getApellido2() != null && !acuerdo.getSociedadCargo().getPersonaCargo().getApellido2().isEmpty()) {
					apellidos += " " + acuerdo.getSociedadCargo().getPersonaCargo().getApellido2();
				}
				acuerdo.setApellidos(apellidos);
				acuerdo.setTipoDocumento(TipoDocumento.NIF.getValorEnum());
				acuerdo.setTipoPersonalidad(TipoPersonalidad.Fisica.getValorEnum());
				acuerdo.setDescCargo(TipoCargo.convertirTextoXml(acuerdo.getCodigoCargo()));
				if (TipoAcuerdo.Cese.getValorEnum().equalsIgnoreCase(acuerdo.getTipoAcuerdo())) {
					if (tramiteRegistroDto.getCeses() == null) {
						tramiteRegistroDto.setCeses(new ArrayList<AcuerdoDto>());
					}
					tramiteRegistroDto.getCeses().add(acuerdo);
				} else if (TipoAcuerdo.Nombramiento.getValorEnum().equals(acuerdo.getTipoAcuerdo())) {
					if (tramiteRegistroDto.getNombramientos() == null) {
						tramiteRegistroDto.setNombramientos(new ArrayList<AcuerdoDto>());
					}
					tramiteRegistroDto.getNombramientos().add(acuerdo);
				}
			}
		}
	}

	@Override
	public void incluirInmuebles(TramiteRegistroDto tramiteRegistroDto) {
		List<InmuebleDto> inmuebles = servicioInmueble.getInmuebles(tramiteRegistroDto.getIdTramiteRegistro());
		if (inmuebles != null && !inmuebles.isEmpty()) {
			tramiteRegistroDto.setInmuebles(new ArrayList<InmuebleDto>());
			for (InmuebleDto inmueble : inmuebles) {
				inmueble.setInmatriculada(Inmatriculada.S.getValorEnum());
				tramiteRegistroDto.getInmuebles().add(inmueble);
			}
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultBean guardarTramiteRegistro(TramiteRegistroDto tramiteRegistroDto, BigDecimal estadoAnterior, BigDecimal idUsuario) {
		ResultBean respuesta = new ResultBean();
		try {

			if (null != tramiteRegistroDto.getRegistro() && 0 != tramiteRegistroDto.getRegistro().getId()) {
				tramiteRegistroDto.setIdRegistro(Long.toString(tramiteRegistroDto.getRegistro().getId()));
			}

			if ("-1".equalsIgnoreCase(tramiteRegistroDto.getIdRegistro())) {
				tramiteRegistroDto.setIdRegistro(null);
			}
			ResultBean respuestaTramite = guardarTramite(tramiteRegistroDto, estadoAnterior, idUsuario);
			if (respuestaTramite.getError()) {
				log.error("Error al guardar el trámite de registro: " + tramiteRegistroDto.getIdTramiteRegistro() + ". Mensaje: " + respuestaTramite.getMensaje());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				if (primerGuardado)
					tramiteRegistroDto.setIdTramiteRegistro(null);
				respuesta.setError(Boolean.TRUE);
				respuesta.setListaMensajes(respuestaTramite.getListaMensajes());
			}

			respuesta.addAttachment(TRAMITE_REGISTRO, conversor.transform(tramiteRegistroDto, TramiteRegistroVO.class));

		} catch (Exception e) {
			log.error("Error al guardar el trámite de registro: " + tramiteRegistroDto.getIdTramiteRegistro() + ". Mensaje: " + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			if (primerGuardado)
				tramiteRegistroDto.setIdTramiteRegistro(null);
			respuesta.setError(Boolean.TRUE);
			respuesta.addMensajeALista(e.getMessage());
		}
		return respuesta;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultBean solicitarFirmas(TramiteRegistroDto tramiteRegistroDto, BigDecimal idUsuario) {
		ResultBean respuesta = new ResultBean();
		try {
			respuesta = guardarTramiteRegistro(tramiteRegistroDto, null, idUsuario);
			if (respuesta != null && !respuesta.getError()) {
				servicioCertifCargo.modificarIdFirma(tramiteRegistroDto.getIdTramiteRegistro(), null, null);
			} else {
				respuesta.setError(Boolean.TRUE);
				respuesta.addMensajeALista("Error al guardar el trámite de registro: " + tramiteRegistroDto.getIdTramiteRegistro());
			}
		} catch (Exception e) {
			log.error("Error al guardar el trámite de registro: " + tramiteRegistroDto.getIdTramiteRegistro() + ". Mensaje: " + e.getMessage());
			respuesta.setError(Boolean.TRUE);
			respuesta.addMensajeALista(e.getMessage());
		}
		return respuesta;
	}

	@Transactional
	private ResultBean guardarTramite(TramiteRegistroDto tramiteRegistroDto, BigDecimal estadoAnterior, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		primerGuardado = false;
		TramiteRegistroVO tramiteRegistroVO = null;

		try {
			if (tramiteRegistroDto.getIdTramiteRegistro() == null) {
				BigDecimal idTramiteRegistro = generarIdTramiteRegistro(tramiteRegistroDto.getNumColegiado(), tramiteRegistroDto.getTipoTramite());
				if (idTramiteRegistro == null) {
					log.error("No se genera el identificador del tramite");
				}
				tramiteRegistroDto.setIdTramiteRegistro(idTramiteRegistro);

				String idTramiteCorpme = generarIdTramiteCorpme(tramiteRegistroDto.getNumColegiado(), tramiteRegistroDto.getIdContrato());
				if (idTramiteCorpme == null) {
					log.error("No se genera el identificador del tramite para Coprme");
				}
				tramiteRegistroDto.setIdTramiteCorpme(idTramiteCorpme);

				Fecha fecha = utilesFecha.getFechaHoraActualLEG();

				tramiteRegistroDto.setIdUsuario(idUsuario);
				tramiteRegistroDto.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));

				tramiteRegistroVO = conversor.transform(tramiteRegistroDto, TramiteRegistroVO.class);
				tramiteRegistroVO.setFechaUltEstado(fecha.getFechaHora());
				tramiteRegistroVO.setFechaCreacion(new Date());
				tramiteRegistroDao.guardar(tramiteRegistroVO);
				log.debug("Creación trámite de registro: " + idTramiteRegistro);
				result.addAttachment(ID_TRAMITE_REGISTRO, idTramiteRegistro);
				guardarEvolucionTramite(idTramiteRegistro, BigDecimal.ZERO, tramiteRegistroDto.getEstado(), idUsuario);
				primerGuardado = true;
			} else {
				if (estadoAnterior != null && !tramiteRegistroDto.getEstado().equals(estadoAnterior)) {

					try {
						guardarEvolucionTramite(tramiteRegistroDto.getIdTramiteRegistro(), estadoAnterior, tramiteRegistroDto.getEstado(), idUsuario);
					} catch (Exception e) {
						log.error("Error al guardar la evolución del trámite de registro: " + tramiteRegistroDto.getIdTramiteRegistro() + ". Mensaje: " + e.getMessage());
					}
				}
				log.debug("Actualización trámite: " + tramiteRegistroDto.getIdTramiteRegistro());

				tramiteRegistroVO = conversor.transform(tramiteRegistroDto, TramiteRegistroVO.class);
				tramiteRegistroVO.setFechaUltEstado(utilesFecha.getFechaHoraActualLEG().getFechaHora());
				tramiteRegistroDao.actualizar(tramiteRegistroVO);
				result.addAttachment(ID_TRAMITE_REGISTRO, tramiteRegistroDto.getIdTramiteRegistro());
			}

			if (!TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistroDto.getTipoTramite()) && !TipoTramiteRegistro.MODELO_12.getValorEnum().equals(tramiteRegistroDto.getTipoTramite())
					&& null != tramiteRegistroDto.getSociedad() && StringUtils.isNotBlank(tramiteRegistroDto.getSociedad().getNif())) {
				result = servicioPersona.guardarSociedad(tramiteRegistroDto, idUsuario);
				if (result.getError() && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
					return result;
				} else {
					PersonaVO sociedadGuardada = (PersonaVO) result.getAttachment(ServicioPersona.PERSONA);
					tramiteRegistroDto.setCif(sociedadGuardada.getId().getNif());
				}
			}

			if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistroDto.getTipoTramite()) && null != tramiteRegistroDto.getSociedad() && StringUtils.isNotBlank(tramiteRegistroDto
					.getSociedad().getNif())) {

				result = servicioIntervinienteRegistro.guardarIntervinienteSociedad(tramiteRegistroDto.getSociedad(), tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getNumColegiado(),
						tramiteRegistroDto.getTipoTramite(), idUsuario);
				if (!result.getError()) {
					if (result.getAttachment(ID_DIRECCION_DESTINATARIO) != null) {
						tramiteRegistroVO.setIdDireccionDestinatario((Long) result.getAttachment(ID_DIRECCION_DESTINATARIO));
						tramiteRegistroDao.actualizar(tramiteRegistroVO);
					}
				} else {
					return result;
				}
			}

			if (null != tramiteRegistroDto.getReunion() && StringUtils.isNotBlank(tramiteRegistroDto.getReunion().getTipoReunion())) {
				if (TipoTramiteRegistro.MODELO_3.getValorEnum().equals(tramiteRegistroDto.getTipoTramite())) {
					tramiteRegistroDto.getReunion().setMedios(servicioMedioConvocatoria.getMediosConvocatorias(tramiteRegistroDto.getIdTramiteRegistro()));
					if (null == tramiteRegistroDto.getReunion().getMedios() || tramiteRegistroDto.getReunion().getMedios().isEmpty()) {

						result.addMensajeALista("Faltan datos obligatorios, añadir un medio de publicación al menos.");
						result.setError(Boolean.TRUE);
						log.info(Claves.CLAVE_LOG_REGISTRADORES_MODELO3_FIN + "seleccionar.");
						return result;
					}
				}

				ResultBean resultReunion = servicioReunion.guardarReunion(tramiteRegistroDto.getReunion(), tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getTipoTramite());
				if (resultReunion != null && !resultReunion.getError()) {
					servicioConvocatoria.guardarConvocatoria(tramiteRegistroDto.getReunion().getConvocatoria(), tramiteRegistroDto.getIdTramiteRegistro(), (Long) resultReunion.getAttachment(
							ServicioReunion.ID_REUNION));
				} else {
					result.setError(Boolean.TRUE);
					result.addListaMensajes(resultReunion.getListaMensajes());
				}
			}

		} catch (Exception e) {
			log.error("Error al guardar el trámite de registro: " + tramiteRegistroDto.getIdTramiteRegistro() + ". Mensaje: " + e.getMessage());
			result.setMensaje(e.getMessage());
			result.addMensajeALista(e.getMessage());
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	@Override
	@Transactional
	public BigDecimal generarIdTramiteRegistro(String numColegiado, String tipoTramiteRegistro) throws Exception {
		return tramiteRegistroDao.generarIdTramiteRegistro(numColegiado, tipoTramiteRegistro);
	}

	@Override
	@Transactional
	public String generarIdTramiteCorpme(String numColegiado, BigDecimal idContrato) throws Exception {
		ContratoVO contrato = servicioContrato.getContrato(idContrato);
		if (contrato != null && contrato.getColegio() != null && contrato.getColegio().getCif() != null && !contrato.getColegio().getCif().isEmpty()) {
			return tramiteRegistroDao.generarIdTramiteCorpme(numColegiado, contrato.getColegio().getCif());
		}
		return null;
	}

	@Override
	@Transactional
	public void actualizarFicheroSubido(BigDecimal idTramiteRegistro) {
		try {
			if (idTramiteRegistro != null) {
				TramiteRegistroVO tramiteVO = tramiteRegistroDao.getTramite(idTramiteRegistro);
				tramiteVO.setFicheroSubido("si");
			}
		} catch (Exception e) {
			log.error("Error al actualizar fichero subido a si: " + idTramiteRegistro, e, idTramiteRegistro.toString());
		}
	}

	@Override
	@Transactional
	public ResultBean cambiarEstado(boolean evolucion, BigDecimal numExpediente, BigDecimal antiguoEstado, BigDecimal nuevoEstado, boolean notificar, String textoNotificacion, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();

		if (nuevoEstado == null) {
			return new ResultBean(true, "No se ha indicado el estado para el cambio de estado.");
		}

		if (null != antiguoEstado && antiguoEstado.compareTo(nuevoEstado) != 0) {

			TramiteRegistroVO tramiteRegistro = tramiteRegistroDao.cambiarEstado(numExpediente, nuevoEstado);

			if (tramiteRegistro == null) {
				return new ResultBean(true, "Error durante el cambio de estado del trámite.");
			}

			if (evolucion) {
				guardarEvolucionTramite(tramiteRegistro.getIdTramiteRegistro(), antiguoEstado, nuevoEstado, idUsuario);
			}

			if (notificar) {
				NotificacionDto dto = new NotificacionDto();
				dto.setDescripcion(textoNotificacion);
				dto.setEstadoAnt(antiguoEstado);
				dto.setEstadoNue(nuevoEstado);
				dto.setIdTramite(tramiteRegistro.getIdTramiteRegistro());
				if (tramiteRegistro.getIdUsuario() != null) {
					dto.setIdUsuario(tramiteRegistro.getIdUsuario().longValue());
				}

				dto.setTipoTramite(tramiteRegistro.getTipoTramite());
				servicioNotificacion.crearNotificacion(dto);
			}

			result.addAttachment(TRAMITE_REGISTRO, tramiteRegistro);
			result.setError(false);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean firmar(BigDecimal idTramiteRegistro, String nifCertificante, String idFirma, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		try {
			result = servicioCertifCargo.modificarIdFirma(idTramiteRegistro, nifCertificante, idFirma);
			if (result != null && !result.getError()) {
				Boolean firmado = (Boolean) result.getAttachment(ServicioCertifCargo.FIRMADO);
				if (Boolean.TRUE.equals(firmado)) {
					result = cambiarEstado(true, idTramiteRegistro, new BigDecimal(EstadoTramiteRegistro.Pendiente.getValorEnum()), new BigDecimal(EstadoTramiteRegistro.Firmado.getValorEnum()), true,
							TextoNotificacion.Cambio_Estado.getNombreEnum(), null);
					result.addAttachment(ServicioCertifCargo.FIRMADO, Boolean.TRUE);
				}
			}
		} catch (Exception e) {
			log.error("Error al modificar la firma. Mensaje: " + e.getMessage());
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Error al modificar la firma. Mensaje: " + e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional
	public void guardarEvolucionTramite(BigDecimal idTramiteRegistro, BigDecimal antiguoEstado, BigDecimal nuevoEstado, BigDecimal idUsuario) {
		EvolucionTramiteRegistroDto evolucion = new EvolucionTramiteRegistroDto();
		if (antiguoEstado != null) {
			evolucion.setEstadoAnterior(antiguoEstado);
		} else {
			evolucion.setEstadoAnterior(BigDecimal.ZERO);
		}
		evolucion.setEstadoNuevo(nuevoEstado);
		evolucion.setFechaCambio(utilesFecha.getFechaHoraActualLEG());
		evolucion.setIdTramiteRegistro(idTramiteRegistro);

		if (idUsuario != null) {
			UsuarioDto usuario = new UsuarioDto();
			usuario.setIdUsuario(idUsuario);
			evolucion.setUsuario(usuario);
		}

		servicioEvolucionTramiteRegistro.guardar(evolucion);
	}

	@Override
	@Transactional
	public String comprobarCorpme(TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario) {
		String identificacionCorpme = null;
		try {
			PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato());

			boolean guardarColegiado = false;

			if (tramiteRegistro.getUsuarioCorpme() != null && !tramiteRegistro.getUsuarioCorpme().isEmpty() && tramiteRegistro.getPasswordCorpme() != null && !tramiteRegistro.getPasswordCorpme()
					.isEmpty()) {
				if (!tramiteRegistro.getUsuarioCorpme().equals(colegiado.getUsuarioCorpme()) || !tramiteRegistro.getPasswordCorpme().equals(colegiado.getPasswordCorpme())) {
					String passwordMd5 = UtilesRegistradores.hashPassword(tramiteRegistro.getPasswordCorpme());
					guardarColegiado = true;
					colegiado.setUsuarioCorpme(tramiteRegistro.getUsuarioCorpme());
					colegiado.setPasswordCorpme(passwordMd5);
					tramiteRegistro.setPasswordCorpme(passwordMd5);
				}
				identificacionCorpme = tramiteRegistro.getUsuarioCorpme() + "|" + tramiteRegistro.getPasswordCorpme();
			}

			if (guardarColegiado) {
				PersonaVO colegiadoVO = conversor.transform(colegiado, PersonaVO.class);
				UsuarioDto usuario = new UsuarioDto();
				usuario.setIdUsuario(idUsuario);
				servicioPersona.guardarActualizar(colegiadoVO, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite(), usuario, ServicioPersona.CONVERSION_PERSONA_CORPME);
			}

		} catch (Exception e) {
			log.error("Error al recuperar el registro", e);
		}
		return identificacionCorpme;
	}

	@Override
	@Transactional
	public ResultBean duplicar(TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		try {
			inicializarParametros(tramiteRegistro);

			result = guardarTramite(tramiteRegistro, BigDecimal.ZERO, idUsuario);

			if (!result.getError()) {
				result = guardarDuplicadoOSubsanacion(tramiteRegistro, idUsuario);
				result.addAttachment(ID_TRAMITE_REGISTRO, tramiteRegistro.getIdTramiteRegistro());
			}

		} catch (Exception e) {
			log.error("Error al duplicar el registro", e);
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Error al duplicar el registro: " + e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean subsanar(TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		try {
			if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
				tramiteRegistro.setSubsanacion("S");
				guardarTramiteRegistro(tramiteRegistro, null, idUsuario);
			}

			incluirDatosSubsanacion(tramiteRegistro);
			inicializarParametros(tramiteRegistro);

			// Si es una escritura borramos los datos de la identificación registral
			if (TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
				tramiteRegistro.setIdentificacionNotarial(false);
				tramiteRegistro.setCodigoNotario(null);
				tramiteRegistro.setCodigoNotaria(null);
				tramiteRegistro.setProtocolo(null);
				tramiteRegistro.setAnioProtocolo(null);

				tramiteRegistro.setIdentificacionNumeroEntrada(false);
				// tramiteRegistro.setNumRegSub(null);
				// tramiteRegistro.setAnioRegSub(null);
			}

			result = guardarTramite(tramiteRegistro, BigDecimal.ZERO, idUsuario);

			if (!result.getError()) {
				result = guardarDuplicadoOSubsanacion(tramiteRegistro, idUsuario);
				result.addAttachment(ID_TRAMITE_REGISTRO, tramiteRegistro.getIdTramiteRegistro());
			}
		} catch (Exception e) {
			log.error("Error al duplicar el registro", e);
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Error al duplicar el registro: " + e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarTramiteRegistroEscritura(TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		try {
			if (tramiteRegistro.isCheckSubsanacion()) {
				if (new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()).equals(tramiteRegistro.getEstado())) {
					tramiteRegistro.setSubsanacion(TipoSubsanacion.DESDE_INICIO.getValorEnum());
				} else if (tramiteRegistro.isSuspension()) {
					tramiteRegistro.setSubsanacion(TipoSubsanacion.DESDE_SUSPENDIDA_CALIFICACION.getValorEnum());
				} else {
					tramiteRegistro.setSubsanacion(TipoSubsanacion.DESDE_CALIFICACION.getValorEnum());
				}
			} else if (tramiteRegistro.isCheckPrimera()) {
				tramiteRegistro.setSubsanacion(TipoEntrada.PRIMERA.getValorEnum());
			}

			// Siempre se tramita la escritura como gestor
			tramiteRegistro.setPresentante(Presentante.GESTOR.getValorEnum());
			PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato());
			tramiteRegistro.setCifTitularCuenta(colegiado.getNif());

			if (null != tramiteRegistro.getRegistro() && 0 != tramiteRegistro.getRegistro().getId()) {
				tramiteRegistro.setIdRegistro(Long.toString(tramiteRegistro.getRegistro().getId()));
			}

			result = guardarTramiteRegistro(tramiteRegistro, tramiteRegistro.getEstado(), idUsuario);
			if (result != null && result.getError()) {
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Error al guardar la escritura.");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return result;
			}

			// ----------------------------------------------------------------------------------
			// Bien-Inmueble
			if (null != tramiteRegistro.getInmueble() && null != tramiteRegistro.getInmueble().getBien() && null != tramiteRegistro.getInmueble().getBien().getTipoInmueble() && StringUtils.isNotBlank(
					tramiteRegistro.getInmueble().getBien().getTipoInmueble().getIdTipoBien())) {

				result = servicioInmueble.guardarInmueble(tramiteRegistro.getInmueble(), tramiteRegistro.getIdTramiteRegistro());
				if (result != null && result.getError()) {
					result.addMensajeALista("Error al guardar el inmueble.");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return result;
				}
			}
			if (!Inmatriculada.S.getValorEnum().equalsIgnoreCase(tramiteRegistro.getInmatriculada())) {
				servicioInmueble.eliminarInmueblesPorExpediente(tramiteRegistro.getIdTramiteRegistro());
			}
			// -----------------------------------------------------------------------------------
			// Forma de pago
			if (null != tramiteRegistro.getFormaPago()) {
				result = guardarDatosFormaPago(tramiteRegistro, idUsuario);
				if (result != null && result.getError()) {
					result.setError(Boolean.TRUE);
					result.addMensajeALista("Error al guardar la forma de pago.");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return result;
				}

				// -----------------------------------------------------------------------------------
			}
		} catch (Exception e) {
			log.error("Error al guardar la escritura, error: ", e);
			result = new ResultBean();
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Error al guardar la escritura.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		}

		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarTramiteRegistroLibroCuenta(TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		try {

			result = guardarTramiteRegistro(tramiteRegistro, tramiteRegistro.getEstado(), idUsuario);
			if (result != null && result.getError()) {
				result.addMensajeALista("Error al guardar el trámite.");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return result;
			}

			// ----------------------------------------------------------------------------------
			// Sociedad
			if (tramiteRegistro.getSociedad() != null && StringUtils.isNotBlank(tramiteRegistro.getSociedad().getNif())) {
				result = servicioIntervinienteRegistro.guardarIntervinienteSociedad(tramiteRegistro.getSociedad(), tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getNumColegiado(),
						tramiteRegistro.getTipoTramite(), idUsuario);
				if (!result.getError()) {
					if (result.getAttachment(ID_DIRECCION_DESTINATARIO) != null) {
						tramiteRegistro.setIdDireccionDestinatario((Long) result.getAttachment(ID_DIRECCION_DESTINATARIO));
					}
				} else {
					result.addMensajeALista("Error al guardar el destinatario.");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return result;
				}
			}

			// -----------------------------------------------------------------------------------
		} catch (Exception e) {
			log.error("Error al guardar el trámite, error: ", e);
			result = new ResultBean();
			result.setError(Boolean.TRUE);
			result.addMensajeALista("Error al guardar el trámite.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		}

		return result;
	}

	private ResultBean guardarDuplicadoOSubsanacion(TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		if (tramiteRegistro.getCertificantes() != null && !tramiteRegistro.getCertificantes().isEmpty()) {
			for (CertifCargoDto cerDto : tramiteRegistro.getCertificantes()) {
				cerDto.setIdTramiteRegistro(tramiteRegistro.getIdTramiteRegistro());
				servicioCertifCargo.guardarCertifCargo(cerDto);
			}
		}

		// Inmuebles
		if (tramiteRegistro.getInmuebles() != null && !tramiteRegistro.getInmuebles().isEmpty()) {
			for (InmuebleDto inmuebleDto : tramiteRegistro.getInmuebles()) {
				inmuebleDto.setIdInmueble(null);
				result = servicioInmueble.guardarInmueble(inmuebleDto, tramiteRegistro.getIdTramiteRegistro());
				if (result != null && result.getError()) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return result;
				}

			}
		}

		if (tramiteRegistro.getReunion() != null) {
			ReunionDto reunion = tramiteRegistro.getReunion();
			reunion.setIdReunion(null);
			result = servicioReunion.guardarReunion(reunion, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
			if (result != null && !result.getError()) {
				tramiteRegistro.getReunion().setIdReunion((Long) result.getAttachment(ServicioReunion.ID_REUNION));
				if (reunion.getMedios() != null && !reunion.getMedios().isEmpty()) {
					for (MedioConvocatoriaDto medio : reunion.getMedios()) {
						servicioMedioConvocatoria.guardarMedioConvocatoria(medio, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getReunion().getIdReunion());
					}
				}
				if (reunion.getConvocatoria() != null) {
					servicioConvocatoria.guardarConvocatoria(reunion.getConvocatoria(), tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getReunion().getIdReunion());
				}
				if (tramiteRegistro.getCeses() != null && !tramiteRegistro.getCeses().isEmpty()) {
					for (AcuerdoDto acuerdoDto : tramiteRegistro.getCeses()) {
						acuerdoDto.setIdAcuerdo(null);
						servicioAcuerdo.guardarAcuerdo(acuerdoDto, tramiteRegistro, idUsuario);
					}
				}
				if (tramiteRegistro.getNombramientos() != null && !tramiteRegistro.getNombramientos().isEmpty()) {
					for (AcuerdoDto acuerdoDto : tramiteRegistro.getNombramientos()) {
						acuerdoDto.setIdAcuerdo(null);
						servicioAcuerdo.guardarAcuerdo(acuerdoDto, tramiteRegistro, idUsuario);
					}
				}
				if (tramiteRegistro.getAsistentes() != null && !tramiteRegistro.getAsistentes().isEmpty()) {
					for (AsistenteDto asistenteDto : tramiteRegistro.getAsistentes()) {
						asistenteDto.setIdTramiteRegistro(tramiteRegistro.getIdTramiteRegistro());
						asistenteDto.setIdReunion(tramiteRegistro.getReunion().getIdReunion());
						servicioAsistente.guardarAsistente(asistenteDto);
					}
				}
			}

		}
		return result;
	}

	@Override
	public void inicializarParametros(TramiteRegistroDto tramiteRegistro) {
		tramiteRegistro.setIdTramiteRegistro(null);
		tramiteRegistro.setIdTramiteCorpme(null);
		tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
		tramiteRegistro.setIdFirmaDoc(null);
		tramiteRegistro.setFechaEnvio(null);
		tramiteRegistro.setFechaCreacion(null);
		tramiteRegistro.setRespuesta(null);
		tramiteRegistro.setNEnvios(null);
		tramiteRegistro.setHoraEntradaReg(null);
		tramiteRegistro.setLibroReg(null);
		tramiteRegistro.setAnioReg(null);
		tramiteRegistro.setNumReg(null);
		tramiteRegistro.setLocalizadorReg(null);
	}

	@Override
	public void incluirDatosSubsanacion(TramiteRegistroDto tramiteRegistro) {
		tramiteRegistro.setLibroRegSub(tramiteRegistro.getLibroReg());
		tramiteRegistro.setAnioRegSub(tramiteRegistro.getAnioReg());
		tramiteRegistro.setNumRegSub(tramiteRegistro.getNumReg());
		tramiteRegistro.setSubsanacion("S");
	}

	public ServicioNotificacion getServicioNotificacion() {
		return servicioNotificacion;
	}

	public void setServicioNotificacion(ServicioNotificacion servicioNotificacion) {
		this.servicioNotificacion = servicioNotificacion;
	}

	@Override
	public ResultRegistro validarRegistro(TramiteRegistroDto tramiteRegistro) {
		ResultRegistro resultado = new ResultRegistro();
		if (tramiteRegistro.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_1.getValorEnum())) {
			resultado = validarTramiteCYNReunionJunta(tramiteRegistro);
		} else if (tramiteRegistro.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_2.getValorEnum())) {
			resultado = validarTramiteAYNReunionJunta(tramiteRegistro);
		} else if (tramiteRegistro.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_3.getValorEnum())) {
			resultado = validarTramiteCYNJuntaAccionistas(tramiteRegistro);
		} else if (tramiteRegistro.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_4.getValorEnum())) {
			resultado = validarTramiteCYNJuntaSocios(tramiteRegistro);
		} else if (tramiteRegistro.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_5.getValorEnum())) {
			resultado = validarTramiteDelegacionFacultades(tramiteRegistro);
		} else if (tramiteRegistro.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_6.getValorEnum())) {
			resultado = validarEscritura(tramiteRegistro);
		} else if (tramiteRegistro.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_11.getValorEnum())) {
			resultado = validarCuenta(tramiteRegistro);
		} else if (tramiteRegistro.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_12.getValorEnum())) {
			resultado = validarLibro(tramiteRegistro);
		}

		if ("S".equalsIgnoreCase(tramiteRegistro.getAplicarIrpf()) && StringUtils.isBlank(tramiteRegistro.getPorcentajeIrpf())) {
			resultado.addValidacion("El porcentaje IRPF es obligatorio si se aplica IRPF.");
		}

		if (null != tramiteRegistro.getPresentador() && StringUtils.isBlank(tramiteRegistro.getPresentador().getUsuarioCorpme()) || StringUtils.isBlank(tramiteRegistro.getPresentador()
				.getPasswordCorpme())) {
			resultado.addValidacion("Debe introducir la identificación Corpme en el apartado Contratos.");
		}

		return resultado;
	}

	// Trámite modelo 1
	public ResultRegistro validarTramiteCYNReunionJunta(TramiteRegistroDto tramiteRegistro) {
		ResultRegistro result = new ResultRegistro();

		if (null == tramiteRegistro.getCertificantes() || tramiteRegistro.getCertificantes().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se han establecido los certificantes del trámite.");
		} else {

			for (int i = 0; i < tramiteRegistro.getCertificantes().size(); i++) {
				if (null != tramiteRegistro.getCertificantes().get(i).getSociedadCargo() && null != tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo() && StringUtils
						.isBlank(tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getCorreoElectronico())) {
					result.setError(Boolean.TRUE);
					result.addValidacion("No se ha establecido correo electrónico para el certificante " + tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getNombre()
							+ " " + tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getApellido1RazonSocial() + " " + tramiteRegistro.getCertificantes().get(i)
									.getSociedadCargo().getPersonaCargo().getApellido2());
				}
			}
		}

		if (null == tramiteRegistro.getFechaCertif() || StringUtils.isBlank(tramiteRegistro.getLugar())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido el lugar y la fecha de la certificación del trámite.");
		}

		if (tramiteRegistro.getReunion() == null) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido la información relativa a la reunión de la junta.");
		}

		if ((tramiteRegistro.getCeses() == null || tramiteRegistro.getCeses().isEmpty()) && (tramiteRegistro.getNombramientos() == null || tramiteRegistro.getNombramientos().isEmpty())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido ningún acuerdo (cese o nombramiento).");
		}

		if (tramiteRegistro.getFicherosSubidos() == null || tramiteRegistro.getFicherosSubidos().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha adjuntado ninguna documentación.");
		}

		return result;
	}

	// Trámite modelo 2
	public ResultRegistro validarTramiteAYNReunionJunta(TramiteRegistroDto tramiteRegistro) {
		ResultRegistro result = new ResultRegistro();

		if (null == tramiteRegistro.getFechaCertif() || StringUtils.isBlank(tramiteRegistro.getLugar())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido el lugar y la fecha de la certificación del trámite.");
		}

		if (tramiteRegistro.getReunion() == null) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido la información relativa a la reunión de la junta.");
		}

		if ((tramiteRegistro.getCeses() == null || tramiteRegistro.getCeses().isEmpty()) && (tramiteRegistro.getNombramientos() == null || tramiteRegistro.getNombramientos().isEmpty())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido ningún acuerdo (cese o nombramiento).");
		}

		if (tramiteRegistro.getFicherosSubidos() == null || tramiteRegistro.getFicherosSubidos().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha adjuntado ninguna documentación.");
		}

		return result;
	}

	// Trámite modelo 3
	public ResultRegistro validarTramiteCYNJuntaAccionistas(TramiteRegistroDto tramiteRegistro) {
		ResultRegistro result = new ResultRegistro();

		if (null == tramiteRegistro.getCertificantes() || tramiteRegistro.getCertificantes().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se han establecido los certificantes del trámite.");
		} else {

			for (int i = 0; i < tramiteRegistro.getCertificantes().size(); i++) {
				if (null != tramiteRegistro.getCertificantes().get(i).getSociedadCargo() && null != tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo() && StringUtils
						.isBlank(tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getCorreoElectronico())) {
					result.setError(Boolean.TRUE);
					result.addValidacion("No se ha establecido correo electrónico para el certificante " + tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getNombre()
							+ " " + tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getApellido1RazonSocial() + " " + tramiteRegistro.getCertificantes().get(i)
									.getSociedadCargo().getPersonaCargo().getApellido2());
				}
			}
		}

		if (null == tramiteRegistro.getReunion()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido la información relativa a la reunión de la junta.");
		}
		if (tramiteRegistro.getFechaCertif() == null || StringUtils.isBlank(tramiteRegistro.getLugar())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido el lugar y la fecha de la certificación del trámite.");
		}

		if (null == tramiteRegistro.getCeses() || tramiteRegistro.getCeses().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido ningún acuerdo (cese).");
		}
		if (null == tramiteRegistro.getNombramientos() || tramiteRegistro.getNombramientos().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido ningún acuerdo (nombramiento).");
		}
		if (tramiteRegistro.getFicherosSubidos() == null || tramiteRegistro.getFicherosSubidos().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha adjuntado ninguna documentación.");
		}

		return result;
	}

	// Trámite modelo 4
	public ResultRegistro validarTramiteCYNJuntaSocios(TramiteRegistroDto tramiteRegistro) {
		ResultRegistro result = new ResultRegistro();

		if (null == tramiteRegistro.getCertificantes() || tramiteRegistro.getCertificantes().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se han establecido los certificantes del trámite.");
		} else {

			for (int i = 0; i < tramiteRegistro.getCertificantes().size(); i++) {
				if (null != tramiteRegistro.getCertificantes().get(i).getSociedadCargo() && null != tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo() && StringUtils
						.isBlank(tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getCorreoElectronico())) {
					result.setError(Boolean.TRUE);
					result.addValidacion("No se ha establecido correo electrónico para el certificante " + tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getNombre()
							+ " " + tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getApellido1RazonSocial() + " " + tramiteRegistro.getCertificantes().get(i)
									.getSociedadCargo().getPersonaCargo().getApellido2());
				}
			}
		}

		if (null == tramiteRegistro.getFechaCertif() || StringUtils.isBlank(tramiteRegistro.getLugar())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido el lugar y la fecha de la certificación del trámite.");
		}

		if (tramiteRegistro.getReunion() == null) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido la información relativa a la reunión de la junta.");
		}

		if (tramiteRegistro.getCeses() == null || tramiteRegistro.getCeses().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido ningún acuerdo (cese).");
		}
		if ((tramiteRegistro.getNombramientos() == null || tramiteRegistro.getNombramientos().isEmpty())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido ningún acuerdo (nombramiento).");
		}

		if (tramiteRegistro.getFicherosSubidos() == null || tramiteRegistro.getFicherosSubidos().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha adjuntado ninguna documentación.");
		}

		return result;
	}

	// Trámite modelo 5
	public ResultRegistro validarTramiteDelegacionFacultades(TramiteRegistroDto tramiteRegistro) {
		ResultRegistro result = new ResultRegistro();

		if (null == tramiteRegistro.getCertificantes() || tramiteRegistro.getCertificantes().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se han establecido los certificantes del trámite.");
		} else {

			for (int i = 0; i < tramiteRegistro.getCertificantes().size(); i++) {
				if (null != tramiteRegistro.getCertificantes().get(i).getSociedadCargo() && null != tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo() && StringUtils
						.isBlank(tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getCorreoElectronico())) {
					result.setError(Boolean.TRUE);
					result.addValidacion("No se ha establecido correo electrónico para el certificante " + tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getNombre()
							+ " " + tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getApellido1RazonSocial() + " " + tramiteRegistro.getCertificantes().get(i)
									.getSociedadCargo().getPersonaCargo().getApellido2());
				}
			}
		}

		if (null == tramiteRegistro.getFechaCertif() || StringUtils.isBlank(tramiteRegistro.getLugar())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido el lugar y la fecha de la certificación del trámite.");
		}

		if (tramiteRegistro.getAsistentes() == null || tramiteRegistro.getAsistentes().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se han establecido los cargos asistentes a la reunión.");
		}
		if (tramiteRegistro.getReunion() == null || tramiteRegistro.getReunion().getFecha() == null) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido la fecha de la asistencia del trámite.");
		}
		if (tramiteRegistro.getReunion() == null || tramiteRegistro.getReunion().getLugar() == null || tramiteRegistro.getReunion().getLugar().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido el lugar de la asistencia del trámite.");
		}

		if ((tramiteRegistro.getCeses() == null || tramiteRegistro.getCeses().isEmpty()) && (tramiteRegistro.getNombramientos() == null || tramiteRegistro.getNombramientos().isEmpty())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha establecido ningún acuerdo (cese o nombramiento).");
		}

		if (tramiteRegistro.getFicherosSubidos() == null || tramiteRegistro.getFicherosSubidos().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha adjuntado ninguna documentación.");
		}

		return result;
	}

	// Trámite modelo 6
	private ResultRegistro validarEscritura(TramiteRegistroDto tramiteRegistro) {

		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(tramiteRegistro.getOperacion())) {
			result.setError(Boolean.TRUE);
			result.addValidacion(" Especifique el tipo de operación registral de la escritura.");
		}

		if (tramiteRegistro.getFechaCertif() == null || tramiteRegistro.getFechaCertif().isfechaNula()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Debe especificar la fecha de la escritura.");
		}

		if (!tramiteRegistro.isCheckSubsanacion() && !tramiteRegistro.isCheckPrimera()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Seleccione si se trata de una primera entrada en registro o una subsanación.");
		}

		if (new BigDecimal(EstadoTramiteRegistro.Calificado_Defectos.getValorEnum()).equals(tramiteRegistro.getEstado()) || new BigDecimal(EstadoTramiteRegistro.Inscrito_Parcialmente.getValorEnum())
				.equals(tramiteRegistro.getEstado()) || new BigDecimal(EstadoTramiteRegistro.Confirmada_Presentacion.getValorEnum()).equals(tramiteRegistro.getEstado())) {
			if (!tramiteRegistro.isCheckSubsanacion() || tramiteRegistro.getAnioReg() == null || tramiteRegistro.getNumReg() == null) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Para modificar los datos guardados desde el estado '" + EstadoTramiteRegistro.convertirTexto(tramiteRegistro.getEstado().toString())
						+ "' es requerido marcar 'subsanación' e indicar el número de entrada.");
			}
		} else if (new BigDecimal(EstadoTramiteRegistro.Confirmada_Presentacion.getValorEnum()).equals(tramiteRegistro.getEstado())) {
			if (!tramiteRegistro.isSuspension()) {
				result.setError(Boolean.TRUE);
				result.addValidacion(
						"El estado actual del trámite solo permite la modificación de los datos guardados del mismo si se ha notificado desde el registro la suspensión de la calificación por faltar la presentación de la liquidación de impuestos.");
			}
		}

		if (tramiteRegistro.isCheckSubsanacion()) {
			if (!tramiteRegistro.isIdentificacionNotarial() && !tramiteRegistro.isIdentificacionNumeroEntrada()) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Faltan los datos de identificación en el registro de la escritura.");
			} else if (tramiteRegistro.isIdentificacionNumeroEntrada()) {
				if (tramiteRegistro.getNumRegSub() == null) {
					result.setError(Boolean.TRUE);
					result.addValidacion("La identificación por número de entrada requiere el número del mismo.");
				}
				if (tramiteRegistro.getAnioRegSub() == null) {
					result.setError(Boolean.TRUE);
					result.addValidacion("La identificación por número de entrada requiere el año del mismo.");
				} else if (4 != tramiteRegistro.getAnioRegSub().toString().length()) {
					result.setError(Boolean.TRUE);
					result.addValidacion("La identificación por número de entrada requiere un año válido.");
				}
			} else if (tramiteRegistro.isIdentificacionNotarial()) {
				if (tramiteRegistro.getCodigoNotaria() == null || tramiteRegistro.getCodigoNotaria().isEmpty()) {
					result.setError(Boolean.TRUE);
					result.addValidacion("La identificación notarial requiere el código de la notaria.");
				}
				if (tramiteRegistro.getCodigoNotario() == null || tramiteRegistro.getCodigoNotario().isEmpty()) {
					result.setError(Boolean.TRUE);
					result.addValidacion("La identificación notarial requiere el código del notario.");
				}
				if (tramiteRegistro.getProtocolo() == null || tramiteRegistro.getProtocolo().isEmpty()) {
					result.setError(Boolean.TRUE);
					result.addValidacion("La identificación notarial requiere la especificación del protocolo.");
				}
				if (null == tramiteRegistro.getAnioProtocolo()) {
					result.setError(Boolean.TRUE);
					result.addValidacion("La identificación notarial requiere la especificación del año del protocolo.");
				} else if (4 != tramiteRegistro.getAnioProtocolo().toString().length()) {
					result.setError(Boolean.TRUE);
					result.addValidacion("La identificación notarial requiere la especificación de un año de protocolo válido.");
				}
			}
		}

		if ("1000".equals(tramiteRegistro.getOperacion()) && !tramiteRegistro.isCheckSubsanacion()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Para el tipo de operación 'Carta de Pago' solo se permite la inscripción por subsanación.");
		}

		if (null == tramiteRegistro.getSociedad() || StringUtils.isBlank(tramiteRegistro.getSociedad().getNif()) || StringUtils.isBlank(tramiteRegistro.getSociedad().getApellido1RazonSocial())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Especifique un cliente o destinatario.");
		}

		if (StringUtils.isBlank(tramiteRegistro.getIdRegistro()) || "-1".equals(tramiteRegistro.getIdRegistro())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Especifique un código de registro.");
		}

		if (Inmatriculada.S.getValorEnum().equals(tramiteRegistro.getInmatriculada()) && (tramiteRegistro.getInmuebles() == null || tramiteRegistro.getInmuebles().isEmpty())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Si declara 'inmatriculada' especifique los datos registrales en la pestaña 'Bienes Inmuebles'.");
		}

		if (tramiteRegistro.getFicherosSubidos() == null || tramiteRegistro.getFicherosSubidos().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha adjuntado ninguna documentación.");
		}

		if (null == tramiteRegistro.getFormaPago()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Especifique una forma de pago.");
		} else if (TipoFormaPago.CUENTA.getValorEnum().equals(tramiteRegistro.getFormaPago().toString()) && null != tramiteRegistro.getDatosBancarios()) {
			if (StringUtils.isBlank(tramiteRegistro.getDatosBancarios().getNombreTitular())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar el nombre del titular.");
			}
			if (StringUtils.isBlank(tramiteRegistro.getDatosBancarios().getNombreTitular())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar el nombre del titular.");
			}

			if (StringUtils.isBlank(tramiteRegistro.getDatosBancarios().getIdProvincia())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar la provincia.");
			}

			if (StringUtils.isBlank(tramiteRegistro.getDatosBancarios().getIdMunicipio())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar el municipio.");
			}

			if (StringUtils.isBlank(tramiteRegistro.getDatosBancarios().getEntidad())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar la entidad.");
			} else if (tramiteRegistro.getDatosBancarios().getEntidad().length() != 4) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar la entidad con un número de cuatro dígitos.");
			}

			if (StringUtils.isBlank(tramiteRegistro.getDatosBancarios().getOficina())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar la oficina.");
			} else if (tramiteRegistro.getDatosBancarios().getOficina().length() != 4) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar la oficina con un número de cuatro dígitos.");
			}

			if (StringUtils.isBlank(tramiteRegistro.getDatosBancarios().getDc())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar el dígito de control.");
			} else if (tramiteRegistro.getDatosBancarios().getDc().length() != 2) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar el dígito de control con un número de dos dígitos.");
			}

			if (StringUtils.isBlank(tramiteRegistro.getDatosBancarios().getNumeroCuentaPago())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar el número de cuenta.");
			} else if (tramiteRegistro.getDatosBancarios().getNumeroCuentaPago().length() != 10) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Si la forma de pago es por cargo a cuenta debe indicar la cuenta con un número de diez dígitos.");
			}
		}

		return result;
	}

	// Trámite modelo 11 (Cuenta)
	public ResultRegistro validarCuenta(TramiteRegistroDto tramiteRegistro) {
		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(tramiteRegistro.getIdRegistro()) || "-1".equals(tramiteRegistro.getIdRegistro())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Especifique un código de registro.");
		}

		if (StringUtils.isBlank(tramiteRegistro.getEjercicioCuenta())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El ejercicio de la cuenta es obligatorio.");
		}

		if (StringUtils.isBlank(tramiteRegistro.getClaseCuenta())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Clase cuenta es obligatorio.");
		}

		if (tramiteRegistro.getFicherosSubidos() == null || tramiteRegistro.getFicherosSubidos().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha adjuntado ninguna documentación.");
		} else {
			// El tamaño máximo de fichero es de 20MB
			long tamanio = 20971520;
			for (int i = 0; i < tramiteRegistro.getFicherosSubidos().size(); i++) {
				File fichero = tramiteRegistro.getFicherosSubidos().get(i).getFichero();
				String fileName = tramiteRegistro.getFicherosSubidos().get(i).getNombre();
				if (!comprobarFicheroValido(fichero, fileName, tamanio)) {
					result.setError(Boolean.TRUE);
					result.addValidacion("El fichero " + fileName + " es superior a 20MB.");
				}
			}
		}

		if (StringUtils.isBlank(tramiteRegistro.getTipoDestinatario())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Indique a nombre de quién se establecerán los datos de facturación.");
		}

		return result;
	}

	// Trámite modelo 12 (Libro)
	public ResultRegistro validarLibro(TramiteRegistroDto tramiteRegistro) {
		ResultRegistro result = new ResultRegistro();

		if (tramiteRegistro.getFicherosSubidos() == null || tramiteRegistro.getFicherosSubidos().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("No se ha adjuntado ninguna documentación.");
		} else {
			// El tamaño máximo de fichero es de 100MB
			long tamanio = 104857600;
			for (int i = 0; i < tramiteRegistro.getFicherosSubidos().size(); i++) {
				File fichero = tramiteRegistro.getFicherosSubidos().get(i).getFichero();
				String fileName = tramiteRegistro.getFicherosSubidos().get(i).getNombre();
				if (!comprobarFicheroValido(fichero, fileName, tamanio)) {
					result.setError(Boolean.TRUE);
					result.addValidacion("El fichero " + fileName + " es superior a 100MB.");
				}
			}
		}

		if (StringUtils.isBlank(tramiteRegistro.getIdRegistro()) || "-1".equals(tramiteRegistro.getIdRegistro())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Especifique un código de registro.");
		}

		if (null == tramiteRegistro.getLibrosRegistro() || tramiteRegistro.getLibrosRegistro().isEmpty()) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Indique los datos de al menos un libro.");
		}

		if (StringUtils.isBlank(tramiteRegistro.getTipoDestinatario())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Indique a nombre de quién se establecerán los datos de facturación.");
		}

		return result;
	}

	// Valida el zip que se adjunta para la legalización de libros.
	@Override
	public ResultRegistro validarZIPLibros(String idTramiteRegistro, File ficheroZIP) {

		ResultRegistro result = new ResultRegistro();
		List<String> listaFicheroDatos = null;
		List<String> listaFicheroNombres = null;
		String nombreRegistro = null;
		String fechaDocumento = null;
		int numeroLibros = 0;

		try {

			// Se comprueba que se incluye el fichero con nombre "DATOS.TXT", y que no sea vacío.
			File ficheroDatos = extraerFicheroZipFile(ficheroZIP, "DATOS.TXT");
			if (null == ficheroDatos) {
				result.setError(Boolean.TRUE);
				result.addValidacion("No se ha incluido el fichero con nombre 'DATOS.TXT'.");
			} else {
				listaFicheroDatos = utiles.obtenerLineasFicheroTexto(ficheroDatos);
				if (null == listaFicheroDatos || listaFicheroDatos.isEmpty()) {
					result.setError(Boolean.TRUE);
					result.addValidacion("El fichero 'DATOS.TXT' no puede estar vacío.");
				}
			}

			// Se comprueba que se incluye el fichero con nombre "NOMBRES.TXT", y que no sea vacío.
			File ficheroNombres = extraerFicheroZipFile(ficheroZIP, "NOMBRES.TXT");
			if (null == ficheroNombres) {
				result.setError(Boolean.TRUE);
				result.addValidacion("No se ha incluido el fichero 'NOMBRES.TXT'.");
			} else {
				listaFicheroNombres = utiles.obtenerLineasFicheroTexto(ficheroNombres);
				if (null == listaFicheroNombres || listaFicheroNombres.isEmpty()) {
					result.setError(Boolean.TRUE);
					result.addValidacion("El fichero 'NOMBRES.TXT' no puede estar vacío.");
				}
			}

			if (null != ficheroDatos)
				ficheroDatos.delete();

			if (null != ficheroNombres)
				ficheroNombres.delete();

			// Si alguno de los dos ficheros no está o es vacío no se sigue comprobando.
			if (result.isError()) {
				return result;
			}

			// Se comprueba que se están enviando todos los ficheros indicados en el archivo "NOMBRES.TXT".
			for (String linea : listaFicheroNombres) {
				File fichero = extraerFicheroZipFile(ficheroZIP, linea);
				if (null == fichero) {
					result.setError(Boolean.TRUE);
					result.addValidacion("En el fichero ZIP debe incluirse el documento " + linea + " indicado en el archivo 'NOMBRES.TXT'.");
				}
				if (null != fichero)
					fichero.delete();
			}

		} catch (Throwable ex) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + ex);
			result.setError(Boolean.TRUE);
			result.addValidacion("Se ha lanzado una excepción Oegam relacionada con la lectura del fichero ZIP de Libros.");
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + " validar fichero ZIP trámite Libros.");
		}

		// En el fichero con "DATOS.TXT" se comprueba que todas las líneas tienen longitud mayor a 3, y que los tres primeros caracteres son númericos
		for (String linea : listaFicheroDatos) {
			if (StringUtils.isNotBlank(linea) && (linea.trim().length() < 3 || !UtilesValidaciones.validarNumero(linea.trim().substring(0, 3)))) {
				result.setError(Boolean.TRUE);
				result.addValidacion("En el fichero 'DATOS.TXT' todas las líneas deben tener longitud mayor a 3, y  los tres primeros caracteres deben ser númericos.");
				return result;
			}
		}

		// Se comprueba que en el fichero DATOS.TXT vienen informados los códigos de campo 100, 102, 105, 106, 107, 108, 109, y 206, 501.
		String mensajeError = "100 (Código de Registro), 102 (Razón Social Sociedad), 105 (NIF/CIF Sociedad), 106 (Nombre vía Sociedad), 107 (Municipio Sociedad), 108 (Código postal Sociedad), 109 (Provincia Sociedad), 206 (Hoja Sociedad), 501 (Número Libros a legalizar)";
		for (String linea : listaFicheroDatos) {

			switch (linea.trim().substring(0, 3)) {

				case "100": // Código de registro
					mensajeError = mensajeError.replace("100 (Código de Registro), ", "");

					// Obtenemos el Nombre de registro que lo necesitamos para próximas comprobaciones
					nombreRegistro = linea.substring(3);
					break;

				case "101": // Fecha de documento
					// Obtenemos la fecha del documento que la necesitamos para próximas comprobaciones
					fechaDocumento = linea.substring(3);
					break;

				case "102": // Razón social de sociedad y depositante
					mensajeError = mensajeError.replace("102 (Razón Social Sociedad), ", "");
					break;

				case "105": // Nif sociedad y depositante
					mensajeError = mensajeError.replace("105 (NIF/CIF Sociedad), ", "");
					break;

				case "106": // Nombre vía Sociedad (Datos facturación)
					mensajeError = mensajeError.replace("106 (Nombre vía Sociedad), ", "");
					break;

				case "107": // Municipio Sociedad (Datos facturación)
					mensajeError = mensajeError.replace("107 (Municipio Sociedad), ", "");
					break;

				case "108": // Código postal Sociedad (Datos facturación)
					mensajeError = mensajeError.replace("108 (Código postal Sociedad), ", "");
					break;

				case "109": // Provincia Sociedad (Datos facturación)
					mensajeError = mensajeError.replace("109 (Provincia Sociedad), ", "");
					break;

				// Se elimina campo 203 según petición del cliente
				// case "203": // Sección de la sociedad
				// mensajeError = mensajeError.replace("203 (Sección Sociedad), ", "");
				// break;

				case "206": // Hoja de la sociedad
					mensajeError = mensajeError.replace("206 (Hoja Sociedad), ", "");
					break;

				case "501": // Número de libros a legalizar
					mensajeError = mensajeError.replace("501 (Número Libros a legalizar)", "");

					// Obtenemos el número de libros que lo necesitamos para próximas comprobaciones
					numeroLibros = Integer.parseInt(linea.substring(3));

					break;

				default:
					break;
			}
		}
		if (StringUtils.isNotBlank(mensajeError)) {
			if (mensajeError.endsWith(", ")) {
				mensajeError = mensajeError.substring(0, mensajeError.length() - 2);
			}
			result.setError(Boolean.TRUE);
			result.addValidacion("En el fichero 'DATOS.TXT' se deben informar los campos: " + mensajeError + ".");
		}

		// Se comprueba que el valor del campo 100 del fichero DATOS.TXT se corresponde con un nombre de registro mercantil válido.
		if (StringUtils.isNotBlank(nombreRegistro)) {
			RegistroDto registro = servicioRegistro.getRegistroPorNombre(nombreRegistro.toUpperCase(), "RM");
			if (null == registro) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El valor del campo 100 del fichero DATOS.TXT no corresponde con un nombre de registro mercantil válido.");
			}
		}

		// Se comprueba que, si viene informada la fecha de solicitud (campo 101) en DATOS.TXT, consta de 8 dígitos.
		if (StringUtils.isNotBlank(fechaDocumento) && fechaDocumento.length() != 8) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El valor del campo 101 del fichero DATOS.TXT no corresponde con una fecha de solicitud válida.");
		}

		// Se comprueba que los 4 primeros campos de los libros vengan informados, teniendo en cuenta el número de libros indicados en el campo 501
		// del fichero DATOS.TXT
		String camposLibros = ""; // "00101, 00102, 00103, 00104, 00201, 00202, 00203, 00204, "
		for (int i = 1; i <= numeroLibros; i++) {
			camposLibros = camposLibros + StringUtils.leftPad(String.valueOf(i), 3, "0") + "01, ";
			camposLibros = camposLibros + StringUtils.leftPad(String.valueOf(i), 3, "0") + "02, ";
			camposLibros = camposLibros + StringUtils.leftPad(String.valueOf(i), 3, "0") + "03, ";
			camposLibros = camposLibros + StringUtils.leftPad(String.valueOf(i), 3, "0") + "04, ";

			for (String linea : listaFicheroDatos) {

				if (linea.trim().startsWith("0")) {

					if (linea.trim().substring(0, 5).startsWith(StringUtils.leftPad(String.valueOf(i), 3, "0") + "01")) {
						camposLibros = camposLibros.replace(StringUtils.leftPad(String.valueOf(i), 3, "0") + "01, ", "");
						if (StringUtils.isBlank(linea.trim().substring(5))) {
							result.setError(Boolean.TRUE);
							result.addValidacion("El valor del campo " + StringUtils.leftPad(String.valueOf(i), 3, "0") + "01 del fichero DATOS.TXT no puede estar vacío.");
						}
					} else if (linea.trim().substring(0, 5).startsWith(StringUtils.leftPad(String.valueOf(i), 3, "0") + "02")) {
						camposLibros = camposLibros.replace(StringUtils.leftPad(String.valueOf(i), 3, "0") + "02, ", "");
						if (StringUtils.isBlank(linea.trim().substring(5))) {
							result.setError(Boolean.TRUE);
							result.addValidacion("El valor del campo " + StringUtils.leftPad(String.valueOf(i), 3, "0") + "02 del fichero DATOS.TXT no puede estar vacío.");
						}
					} else if (linea.trim().substring(0, 5).startsWith(StringUtils.leftPad(String.valueOf(i), 3, "0") + "03")) {
						camposLibros = camposLibros.replace(StringUtils.leftPad(String.valueOf(i), 3, "0") + "03, ", "");
						if (StringUtils.isBlank(linea.trim().substring(5))) {
							result.setError(Boolean.TRUE);
							result.addValidacion("El valor del campo " + StringUtils.leftPad(String.valueOf(i), 3, "0") + "03 del fichero DATOS.TXT no puede estar vacío.");
						}
					} else if (linea.trim().substring(0, 5).startsWith(StringUtils.leftPad(String.valueOf(i), 3, "0") + "04")) {
						camposLibros = camposLibros.replace(StringUtils.leftPad(String.valueOf(i), 3, "0") + "04, ", "");
						if (StringUtils.isBlank(linea.trim().substring(5))) {
							result.setError(Boolean.TRUE);
							result.addValidacion("El valor del campo " + StringUtils.leftPad(String.valueOf(i), 3, "0") + "04 del fichero DATOS.TXT no puede estar vacío.");
						}
					}

				}
			}
		}

		if (StringUtils.isNotBlank(camposLibros)) {
			if (camposLibros.endsWith(", ")) {
				camposLibros = camposLibros.substring(0, camposLibros.length() - 2);
			}
			result.setError(Boolean.TRUE);
			result.addValidacion("En el fichero 'DATOS.TXT' se deben informar los campos: " + camposLibros + ".");
		}

		return result;
	}

	// Valida el zip que se adjunta para la presentación de cuentas
	@Override
	public ResultRegistro validarZIPCuentas(String idTramiteRegistro, File ficheroZIP, String nombreFicheroZIP) {

		ResultRegistro result = new ResultRegistro();

		try {

			// Se comprueba que el fichero ZIP sigue el patrón de presentación de cuentas Nuevo D2
			// Si el zip contiene alguno de los siguientes ficheros las cuentas estarían en el formato antiguo.
			List<String> ficheros = Arrays.asList("ACCIONES.ASC", "CERTIF.ASC", "INSTANCIA.ASC", "CAUSA.TXT", "MEMORIA.TXT", "GESTION.TXT", "ACCIONES.TXT", "AMBIENTA.TXT", "SIMCAV.TXT",
					"CERTAPRO.TXT");
			for (int i = 0; i < ficheros.size(); i++) {
				File fichero = extraerFicheroZipFile(ficheroZIP, ficheros.get(i));
				if (null != fichero) {
					result.setError(Boolean.TRUE);
					result.addValidacion("Desde la plataforma de OEGAM no se permite el envío de Cuentas en el formato antiguo D2.");
					return result;
				}
			}

			// Se comprueba que se incluye el fichero con nombre "DATOS.TXT", y que no sea vacío.
			File ficheroDeposito = extraerFicheroZipFile(ficheroZIP, "DEPOSITO.XML");
			if (null == ficheroDeposito) {
				result.setError(Boolean.TRUE);
				result.addValidacion("No se ha incluido el fichero con nombre 'DEPOSITO.XML'.");
				return result;
			}

			// Se obtiene el registro de los 5 primeros caracteres del nombre del ZIP. Comprobamos que es un registro RM válido
			if (StringUtils.isNotBlank(nombreFicheroZIP)) {
				// Se comprueba que es un Número
				if (!UtilesValidaciones.validarNumero(nombreFicheroZIP.trim().substring(0, 5))) {
					result.setError(Boolean.TRUE);
					result.addValidacion("Los cinco primeros caracteres del nombre del fichero ZIP deben corresponder a un código de Registro Mercantil válido.");
					return result;
				}

				// Se comprueba que es un registro RM válido
				Long idRegistro = servicioRegistro.getIdRegistro(nombreFicheroZIP.trim().substring(0, 5), null, "RM");
				if (null == idRegistro || 0 == idRegistro) {
					result.setError(Boolean.TRUE);
					result.addValidacion("Los cinco primeros caracteres del nombre del fichero ZIP no corresponden con un código de Registro Mercantil válido.");
					return result;
				}
			}

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			/*
			 * dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
			 */
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
			Document document = documentBuilder.parse(ficheroDeposito);
			document.getDocumentElement().normalize();

			// Se comprueba que vienen informados los nodos /Esquema/Cabecera/CIF y /Esquema/Cabecera/RazonSocial.
			NodeList cabecera = document.getElementsByTagName("Cabecera");
			for (int i = 0; i < cabecera.getLength(); i++) {
				Node nodoCabecera = cabecera.item(i);
				if (nodoCabecera.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nodoCabecera;

					if (null == element.getElementsByTagName("CIF") || null == element.getElementsByTagName("CIF").item(0) || StringUtils.isBlank(element.getElementsByTagName("CIF").item(0)
							.getTextContent())) {
						result.setError(Boolean.TRUE);
						result.addValidacion("En el fichero 'DEPOSITO.XML' se debe informar el nodo /Esquema/Cabecera/CIF.");
					}
					if (null == element.getElementsByTagName("RazonSocial") || null == element.getElementsByTagName("RazonSocial").item(0) || StringUtils.isBlank(element.getElementsByTagName(
							"RazonSocial").item(0).getTextContent())) {
						result.setError(Boolean.TRUE);
						result.addValidacion("En el fichero 'DEPOSITO.XML' se debe informar el nodo /Esquema/Cabecera/RazonSocial.");
					}
					if (null == element.getElementsByTagName("Ejercicio") || null == element.getElementsByTagName("Ejercicio").item(0) || StringUtils.isBlank(element.getElementsByTagName("Ejercicio")
							.item(0).getTextContent())) {
						result.setError(Boolean.TRUE);
						result.addValidacion("En el fichero 'DEPOSITO.XML' se debe informar el nodo /Esquema/Cabecera/Ejercicio.");
					}
				}
			}

			// Se comprueba que vienen informados los códigos 8081010, 8081201, 8081202, 8081206, 8081204, 8081203, 8081205, 1022
			// de los nodos "/Esquema/Claves/Clave"
			String mensajeError = "8081010 (Clase Cuenta), 1022 (Nombre vía Sociedad), 1023 (Municipio Sociedad), 1024 (Código postal Sociedad), 1025 (Provincia Sociedad)";
			NodeList listaClaves = document.getElementsByTagName("Clave");
			for (int i = 0; i < listaClaves.getLength(); i++) {
				Node nodoClave = listaClaves.item(i);
				if (nodoClave.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nodoClave;

					switch (element.getElementsByTagName("Codigo").item(0).getTextContent()) {

						case "8081010": // Identificacion_Documento/Tipo_Operacion/Mercantil/Datos_Operacion/Deposito_Cuentas/Clase_Cuentas
							mensajeError = mensajeError.replace("8081010 (Clase Cuenta), ", "");
							break;

						// Se comenta validación de campos del presentante porque es el colegiado
						// case "8081201": // Identificacion_Presentante/Presentante/Nombre
						// mensajeError = mensajeError.replace("8081201, ", "");
						// break;
						//
						// case "8081202": // Identificacion_Presentante/Presentante/Numero_Documento
						// mensajeError = mensajeError.replace("8081202, ", "");
						// break;
						//
						// case "8081206": // Identificacion_Presentante/Datos_Contacto/Domicilio/Cod_Provincia
						// mensajeError = mensajeError.replace("8081206, ", "");
						// break;
						//
						// case "8081204": // Identificacion_Presentante/Datos_Contacto/Domicilio/Cod_Municipio
						// mensajeError = mensajeError.replace("8081204, ", "");
						// break;
						//
						// case "8081203": // Identificacion_Presentante/Datos_Contacto/Domicilio/Nombre_Via
						// mensajeError = mensajeError.replace("8081203, ", "");
						// break;
						//
						// case "8081205": // Identificacion_Presentante/Datos_Contacto/Domicilio/Codigo_Postal
						// mensajeError = mensajeError.replace("8081205, ", "");
						// break;

						case "1022": // Datos_Facturacion/Domicilio/Nombre_Via
							mensajeError = mensajeError.replace("1022 (Nombre vía Sociedad), ", "");
							break;

						case "1023": // Datos_Facturacion/Domicilio/Cod_Municipio
							mensajeError = mensajeError.replace("1023 (Municipio Sociedad), ", "");
							break;

						case "1024": // Datos_Facturacion/Domicilio/Codigo_Postal
							mensajeError = mensajeError.replace("1024 (Código postal Sociedad), ", "");
							break;

						case "1025": // Datos_Facturacion/Domicilio/Cod_Provincia
							mensajeError = mensajeError.replace("1025 (Provincia Sociedad)", "");
							break;
						default:
							break;
					}
				}
			}

			if (StringUtils.isNotBlank(mensajeError)) {
				if (mensajeError.endsWith(", ")) {
					mensajeError = mensajeError.substring(0, mensajeError.length() - 2);
				}
				result.setError(Boolean.TRUE);
				result.addValidacion("En el fichero 'DEPOSITO.XML' se deben informar los campos: " + mensajeError + ".");
			}

		} catch (Exception | OegamExcepcion e) {
			result.setError(Boolean.TRUE);
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + e);
			result.addValidacion("Se ha lanzado una excepción Oegam relacionada con la lectura del fichero ZIP de Cuentas");
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + " validar fichero ZIP trámite Cuentas");
		}

		return result;
	}

	@SuppressWarnings("resource")
	@Override
	public File extraerFicheroZipFile(File zip, String nombreFicheroBuscar) throws IOException, OegamExcepcion {
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
		ZipEntry entrada = null;

		while (null != (entrada = zis.getNextEntry())) {
			if (entrada.getName().startsWith(nombreFicheroBuscar)) {

				File nuevoArchivo = new File(gestorPropiedades.valorPropertie("registradores.ruta.mensaje.recibido.temporal") + entrada.getName());
				FileOutputStream salida = new FileOutputStream(nuevoArchivo);

				byte[] buffer = new byte[1024];
				int leido;

				while ((leido = zis.read(buffer, 0, buffer.length)) != -1) {
					salida.write(buffer, 0, leido);
				}

				salida.flush();
				salida.close();

				zis.closeEntry();
				return nuevoArchivo;
			}
		}
		return null;
	}

	private boolean comprobarFicheroValido(File fichero, String ficheroFileName, long tamanio) {
		boolean esCorrecto = true;
		// String ext = FilenameUtils.getExtension(ficheroFileName);
		// Validamos solo .pdf)
		// if (!"pdf".equals(ext)){
		// esCorrecto = false;
		// }
		// El tamaño máximo de fichero
		if (fichero.length() > tamanio) {
			esCorrecto = false;
		}
		return esCorrecto;
	}

	@Override
	@Transactional
	public ResultRegistro construirRm(TramiteRegistroDto tramiteRegistro, boolean rmFirmar, String alias) throws Throwable {
		ResultRegistro resultado = new ResultRegistro();
		try {

			resultado = validarRegistro(tramiteRegistro);
			if (resultado.isError()) {
				return resultado;
			}

			String mensajeRM = buildRm.construirXML(tramiteRegistro);
			if (mensajeRM == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La cadena xml del rm no es válida según el esquema.");
				return resultado;
			}

			if (rmFirmar) {

				UtilesViafirma utilesViafirma = new UtilesViafirma();
				if (utilesViafirma.firmarPruebaCertificadoCaducidad(mensajeRM, alias) == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha ocurrido un error durante el proceso de firma en servidor del rm.");
					return resultado;
				}

				utilesViafirma = new UtilesViafirma();
				String idFirma = utilesViafirma.firmarMensajeRMenServidor(mensajeRM);
				if (idFirma.equals("ERROR")) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha ocurrido un error durante el proceso de firma en servidor del rm.");
					return resultado;
				}

				tramiteRegistro.setIdFirmaDoc(idFirma);
				ResultBean resultBean = solicitarFirmas(tramiteRegistro, tramiteRegistro.getIdUsuario());
				if (resultBean != null && resultBean.getError()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Error al construir el RM.");
					return resultado;
				}

				resultado = enviarCorreo(tramiteRegistro);

				if ((int) resultado.getObj() == tramiteRegistro.getCertificantes().size()) {
					log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INFO + " Correos enviados. Inicio cambio estado a Pendiente_Firmas.");
					if (!new BigDecimal(EstadoTramiteRegistro.Pendiente.getValorEnum()).equals(tramiteRegistro.getEstado())) {
						ResultBean respuesta = cambiarEstado(true, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getEstado(), new BigDecimal(EstadoTramiteRegistro.Pendiente.getValorEnum()),
								true, TextoNotificacion.Cambio_Estado.getNombreEnum(), tramiteRegistro.getIdUsuario());
						if (respuesta != null && respuesta.getError()) {
							log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "Ha ocurrido el siguiente error actualizando a 'Pendiente_Firmas' el tramite con identificador: " + tramiteRegistro
									.getIdTramiteRegistro() + " : " + respuesta.getMensaje());
						} else {
							log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Se ha actualizado el estado del tramite con identificador: " + tramiteRegistro.getIdTramiteRegistro() + " a 'Pendiente firmas'");
							resultado.setMensaje(resultado.getMensaje() + " Cuando los certificantes firmen el trámite recibirá una notificación.");
							tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Pendiente.getValorEnum()));
						}
					}
				}
			}

		} catch (Exception ex) {
			log.error("construirRm()" + ex + "\n" + UtilesExcepciones.stackTraceAcadena(ex, 3));
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error en la construcción del documento rm (registro mercantil).");
		}
		return resultado;
	}

	@Override
	public ResultRegistro enviarCorreo(TramiteRegistroDto tramiteRegistro) throws Throwable {
		ResultRegistro resultado = new ResultRegistro();
		resultado.setMensaje("Trámite " + tramiteRegistro.getIdTramiteRegistro() + ":<br/><br/>");
		int correosEnviados = 0;
		String enlaceFirma = gestorPropiedades.valorPropertie("registradores.mail.enlaceFirma");
		StringBuffer texto = new StringBuffer(
				"<br><span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Se solicita desde la Oficina Electrónica de Gestión Administrativa (OEGAM), su firma digital para la certificación del trámite adjunto.<br>Si está de acuerdo con dicha certificación puede iniciar el proceso de firma digital <br>con su certificado haciendo 'click' sobre el siguiente enlace: <b><a href=")
						.append(enlaceFirma).append("?signId=").append(tramiteRegistro.getIdFirmaDoc()).append("&processId=").append(tramiteRegistro.getIdTramiteRegistro()).append("&idc=").append(
								tramiteRegistro.getIdContrato()).append(" style=\"color:black;\">Firmar documento</a></b><br><br></span>");

		File path = new File(StaxStreamSource.crearFicheroHtml(tramiteRegistro.getIdTramiteRegistro().toString()));
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setFichero(path);
		ficheroBean.setNombreDocumento(path.getName());
		ficheroBean.setTipoDocumento("Content-Transfer-Encoding" + "||" + "quoted-printable");

		for (int i = 0; i < tramiteRegistro.getCertificantes().size(); i++) {
			String to = tramiteRegistro.getCertificantes().get(i).getSociedadCargo().getPersonaCargo().getCorreoElectronico();
			ResultBean resultBean = servicioCorreo.enviarCorreo(texto.toString(), null, null, REGISTRADORES_MAIL_SUBJECT, to, null, null, null, ficheroBean);
			String s = resultBean.getListaMensajes().get(0);
			int vsa = s != null ? Integer.parseInt(s) : 0;
			s = resultBean.getListaMensajes().get(1);
			int vusa = s != null ? Integer.parseInt(s) : 0;
			s = resultBean.getListaMensajes().get(2);
			int iva = s != null ? Integer.parseInt(s) : 0;
			if (vsa == 1) {
				resultado.setMensaje(resultado.getMensaje() + " Se ha enviado correctamente notificación de solicitud de firma a la dirección: " + to + " ");
				correosEnviados++;
				resultado.setObj(correosEnviados);
			} else if (vusa == 1) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido enviar notificación de solicitud de firma a la siguiente dirección: " + to + " ");
			} else if (iva == 1) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La siguiente dirección de correo no es válida: " + to + " ");
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean guardarEscrituraImportacion(ESCRITURA escritura, BigDecimal idUsuario, ContratoDto contrato) {
		ResultBean result = new ResultBean();
		try {
			TramiteRegistroVO tramite = conversor.transform(escritura, TramiteRegistroVO.class);
			DireccionVO direccion = conversor.transform(escritura.getDomicilio(), DireccionVO.class);
			if (tramite.getPresentante().equalsIgnoreCase(Presentante.GESTOR.getValorEnum())) {
				PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(contrato.getColegiadoDto().getNumColegiado(), contrato.getIdContrato());
				tramite.setCifTitularCuenta(colegiado.getNif());
			} else if (tramite.getPresentante().equalsIgnoreCase(Presentante.GESTORIA.getValorEnum())) {
				tramite.setCifTitularCuenta(contrato.getCif());
			}
			tramite.setIdContrato(contrato.getIdContrato());
			tramite.setNumColegiado(contrato.getColegiadoDto().getNumColegiado());
			tramite.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
			tramite.setTipoTramite(TipoTramiteRegistro.MODELO_6.getValorEnum());
			tramite.setFechaCreacion(new Date());
			tramite.setIdRegistro(dameCodigoRegistro(escritura.getRegistro(), TipoRegistro.REGISTRO_PROPIEDAD.getValorEnum()));

			if (tramite.getSociedad() != null && StringUtils.isNotBlank(tramite.getSociedad().getId().getNif())) {
				tramite.getSociedad().getId().setNumColegiado(tramite.getNumColegiado());
				tramite.getSociedad().getId().setNif(tramite.getSociedad().getId().getNif().toUpperCase());
				tramite.getSociedad().setEstado(new Long("1"));
				// Se crea el anagrama
				String anagrama = Anagrama.obtenerAnagramaFiscal(tramite.getSociedad().getApellido1RazonSocial(), tramite.getSociedad().getId().getNif());
				tramite.getSociedad().setAnagrama(anagrama);

				// Guardar persona
				UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
				ResultBean resultPersona = servicioPersona.guardarActualizar(tramite.getSociedad(), tramite.getIdTramiteRegistro(), tramite.getTipoTramite(), usuario,
						ServicioPersona.CONVERSION_PERSONA_SOCIEDAD);
				if (!resultPersona.getError()) {
					// Guardar direccion
					if (direccion != null && utiles.convertirCombo(direccion.getIdProvincia()) != null) {

						ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(direccion, tramite.getSociedad().getId().getNif(), tramite.getSociedad().getId().getNumColegiado(),
								tramite.getTipoTramite(), ServicioDireccion.CONVERSION_DIRECCION_REGISTRO);
						if (resultDireccion != null && !resultDireccion.getError()) {
							direccion = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
							boolean direccionNueva = (Boolean) resultDireccion.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
							servicioEvolucionPersona.guardarEvolucionPersonaDireccion(tramite.getSociedad().getId().getNif(), tramite.getIdTramiteRegistro(), tramite.getTipoTramite(), tramite
									.getSociedad().getId().getNumColegiado(), usuario, direccionNueva);
						}
						tramite.setIdDireccionDestinatario(direccion.getIdDireccion());
					}
				} else {
					result.setError(Boolean.TRUE);
					result.addMensajeALista(resultPersona.getMensaje());
				}
			} else {
				result.setError(Boolean.TRUE);
				result.addMensajeALista("No se ha guardado ningún interviniente.");
			}
			TramiteRegistroDto tramiteDto = conversor.transform(tramite, TramiteRegistroDto.class);
			ResultBean respuestaTramite = guardarTramite(tramiteDto, null, idUsuario);
			if (respuestaTramite.getError()) {
				result.setError(Boolean.TRUE);
				result.setMensaje("Error al guardar la escritura.");
			} else {
				result.addAttachment(ID_TRAMITE_REGISTRO, tramite.getIdTramiteRegistro());
				if (null != escritura.getBienes()) {
					ResultRegistro resultBienes = guardarInmuebles(escritura.getBienes().getBien(), tramite.getIdTramiteRegistro());
					if (resultBienes.isError()) {
						result.addMensajeALista(resultBienes.getMensaje());
					}
				}
			}

		} catch (Exception e) {
			log.error("Error al guardar la escritura, error: ", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al guardar la escritura.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		}
		return result;
	}

	private ResultRegistro guardarInmuebles(List<BienEscrituraType> bien, BigDecimal idTramiteRegistro) {
		ResultRegistro result = new ResultRegistro();
		try {
			if (null != bien && !bien.isEmpty()) {
				for (BienEscrituraType elemento : bien) {
					InmuebleVO inmuebleVO = conversor.transform(elemento, InmuebleVO.class);
					inmuebleVO.setIdTramiteRegistro(idTramiteRegistro);
					inmuebleDao.guardarOActualizar(inmuebleVO);
				}
			}
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al guardar el Bien.");
			log.error("Error al guardar el inmueble: " + idTramiteRegistro, e.getMessage());
		}
		return result;
	}

	private String dameCodigoRegistro(RegistroType registro, String tipo) {
		if (null != registro && null != registro.getCodProvincia() && null != registro.getCodRegistro()) {
			Long result = servicioRegistro.getIdRegistro(registro.getCodRegistro(), registro.getCodProvincia(), tipo);
			if (null != result) {
				return result.toString();
			}
		}
		return null;
	}

	private ResultBean guardarDatosFormaPago(TramiteRegistroDto tramiteRegistroDto, BigDecimal idUsuario) {
		ResultBean resultGDatosBancFav = new ResultBean(false);

		if (TipoFormaPago.CUENTA.getValorEnum().equals(tramiteRegistroDto.getFormaPago().toString())) {
			DatosBancariosFavoritosDto datosBancariosDto = tramiteRegistroDto.getDatosBancarios();

			if (null != datosBancariosDto) {

				datosBancariosDto.setFormaPago(String.valueOf(TipoFormaPago.CUENTA.getValorEnum()));
				if (TipoCuentaBancaria.NUEVA.getValorEnum().equals(datosBancariosDto.getTipoDatoBancario())) {

					// Se validan que los datos introducidos sean correctos
					ResultRegistro result = servicioDatosBancariosFavoritos.validarDatosBancariosCuentaPantallaRegistradores(datosBancariosDto);
					if (result.isError()) {
						resultGDatosBancFav.setListaMensajes(result.getValidaciones());
						resultGDatosBancFav.setError(Boolean.TRUE);
						return resultGDatosBancFav;
					}

					if (datosBancariosDto.getEsFavorita() != null && datosBancariosDto.getEsFavorita()) {
						DatosBancariosFavoritosVO datosBancariosFavoritosVO = conversor.transform(datosBancariosDto, DatosBancariosFavoritosVO.class);
						datosBancariosFavoritosVO.setIdContrato(tramiteRegistroDto.getIdContrato().longValue());
						datosBancariosFavoritosVO.setEstado(new BigDecimal(EstadoDatosBancarios.HABILITADO.getValorEnum()));
						datosBancariosFavoritosVO.setDatosBancarios(servicioDatosBancariosFavoritos.cifrarNumCuentaPantallaRegistradores(datosBancariosDto));
						resultGDatosBancFav = servicioDatosBancariosFavoritos.guardarDatosBancariosFavoritos(datosBancariosFavoritosVO, idUsuario);
						if (!resultGDatosBancFav.getError()) {
							tramiteRegistroDto.setIdDatosBancarios(new BigDecimal(datosBancariosFavoritosVO.getIdDatosBancarios()));
							tramiteRegistroDto.setNombreTitular(null);
							tramiteRegistroDto.setIdMunicipioCuenta(null);
							tramiteRegistroDto.setIdProvinciaCuenta(null);
							tramiteRegistroDto.setDatosBancarios(null);
						}
					} else {
						tramiteRegistroDto.setNumCuentaPago(servicioDatosBancariosFavoritos.cifrarNumCuentaPantallaRegistradores(datosBancariosDto));
						tramiteRegistroDto.setNombreTitular(datosBancariosDto.getNombreTitular());
						tramiteRegistroDto.setIdMunicipioCuenta(datosBancariosDto.getIdMunicipio());
						tramiteRegistroDto.setIdProvinciaCuenta(datosBancariosDto.getIdProvincia());
						tramiteRegistroDto.setIdDatosBancarios(null);
					}
				} else if (TipoCuentaBancaria.EXISTENTE.getValorEnum().equals(datosBancariosDto.getTipoDatoBancario())) {

					if (null != datosBancariosDto.getIdDatosBancarios() && 0 != datosBancariosDto.getIdDatosBancarios()) {
						tramiteRegistroDto.setIdDatosBancarios(new BigDecimal(datosBancariosDto.getIdDatosBancarios()));
					} else {
						resultGDatosBancFav.setError(Boolean.TRUE);
						resultGDatosBancFav.addMensajeALista("Debe seleccionar una cuenta bancaria favorita.");
					}
				}
			}

			// Borramos las facturas (si las hay)
			tramiteRegistroDto.setFacturasRegistro(servicioFacturaRegistro.getFacturasPorTramite(tramiteRegistroDto.getIdTramiteRegistro()));
			if (null != tramiteRegistroDto.getFacturasRegistro() && !tramiteRegistroDto.getFacturasRegistro().isEmpty()) {
				int numFacturas = tramiteRegistroDto.getFacturasRegistro().size();

				for (int i = 0; i < numFacturas; i++) {
					servicioFacturaRegistro.borrarFacturaRegistro(tramiteRegistroDto.getFacturasRegistro().get(i).getIdFactura());
				}

			}

		} else if (TipoFormaPago.TRANSFERENCIA.getValorEnum().equals(tramiteRegistroDto.getFormaPago().toString()) && null != tramiteRegistroDto.getFacturaRegistro() && (UtilesValidaciones
				.validarObligatoriedad(tramiteRegistroDto.getFacturaRegistro().getFechaPago()) || StringUtils.isNotBlank(tramiteRegistroDto.getFacturaRegistro().getIdTransferencia()) || StringUtils
						.isNotBlank(tramiteRegistroDto.getFacturaRegistro().getCifEmisor()) || StringUtils.isNotBlank(tramiteRegistroDto.getFacturaRegistro().getNumSerie()) || StringUtils.isNotBlank(
								tramiteRegistroDto.getFacturaRegistro().getEjercicio()) || StringUtils.isNotBlank(tramiteRegistroDto.getFacturaRegistro().getNumFactura()) || UtilesValidaciones
										.validarObligatoriedad(tramiteRegistroDto.getFacturaRegistro().getFechaFactura()))) {

			tramiteRegistroDto.getFacturaRegistro().setIdTramiteRegistro(tramiteRegistroDto.getIdTramiteRegistro());

			// Tabla factura Registro
			ResultRegistro resultado = servicioFacturaRegistro.guardarOActualizarFacturaRegistro(tramiteRegistroDto.getFacturaRegistro(), tramiteRegistroDto.getIdTramiteRegistro());
			if (resultado.isError()) {
				resultGDatosBancFav.setError(Boolean.TRUE);
				resultGDatosBancFav.setListaMensajes(resultado.getValidaciones());
				return resultGDatosBancFav;
			}

			// Borramos los datos de cuenta (si los hay)
			tramiteRegistroDto.setNumCuentaPago(null);
			tramiteRegistroDto.setNombreTitular(null);
			tramiteRegistroDto.setIdMunicipioCuenta(null);
			tramiteRegistroDto.setIdProvinciaCuenta(null);
			tramiteRegistroDto.setIdDatosBancarios(null);

		} else if (TipoFormaPago.USUARIO_ABONADO.getValorEnum().equals(tramiteRegistroDto.getFormaPago().toString())) {// Tipo forma pago por identificación Corpme
			// Borramos los datos que se hayan podido guardar al marcar otra forma de pago
			// Borramos los datos de cuenta (si los hay)
			tramiteRegistroDto.setNumCuentaPago(null);
			tramiteRegistroDto.setNombreTitular(null);
			tramiteRegistroDto.setIdMunicipioCuenta(null);
			tramiteRegistroDto.setIdProvinciaCuenta(null);
			tramiteRegistroDto.setIdDatosBancarios(null);

			// Borramos las facturas (si las hay)
			tramiteRegistroDto.setFacturasRegistro(servicioFacturaRegistro.getFacturasPorTramite(tramiteRegistroDto.getIdTramiteRegistro()));
			if (null != tramiteRegistroDto.getFacturasRegistro() && !tramiteRegistroDto.getFacturasRegistro().isEmpty()) {
				int numFacturas = tramiteRegistroDto.getFacturasRegistro().size();

				for (int i = 0; i < numFacturas; i++) {
					servicioFacturaRegistro.borrarFacturaRegistro(tramiteRegistroDto.getFacturasRegistro().get(i).getIdFactura());
				}
			}
		}

		return resultGDatosBancFav;
	}

	private ResultBean recuperarDatosFormaPagoCuenta(TramiteRegistroDto tramiteRegistroDto, TramiteRegistroVO tramiteRegistroVO) {
		ResultBean resultado = null;
		if (null != tramiteRegistroDto.getDatosBancarios() && null != tramiteRegistroDto.getIdDatosBancarios()) {
			servicioDatosBancariosFavoritos.desencriptarDatosBancariosRegistradoresAsterisco(tramiteRegistroVO.getDatosBancarios(), tramiteRegistroDto.getDatosBancarios());
			tramiteRegistroDto.getDatosBancarios().setTipoDatoBancario(TipoCuentaBancaria.EXISTENTE.getValorEnum());
			tramiteRegistroDto.getDatosBancarios().setEsFavorita(true);
		} else {
			DatosBancariosFavoritosDto datosBancariosFavoritosDto = new DatosBancariosFavoritosDto();
			if (StringUtils.isNotBlank(tramiteRegistroDto.getNumCuentaPago())) {
				String numCuenta = servicioDatosBancariosFavoritos.descifrarNumCuenta(tramiteRegistroDto.getNumCuentaPago());
				if (StringUtils.isNotBlank(numCuenta)) {
					datosBancariosFavoritosDto.setIban("****");
					datosBancariosFavoritosDto.setEntidad("****");
					datosBancariosFavoritosDto.setOficina("****");
					datosBancariosFavoritosDto.setDc("**");
					datosBancariosFavoritosDto.setNumeroCuentaPago("******" + numCuenta.substring(numCuenta.length() - 4, numCuenta.length()));
				}
			}

			if (StringUtils.isNotBlank(tramiteRegistroDto.getNombreTitular())) {
				datosBancariosFavoritosDto.setNombreTitular(tramiteRegistroDto.getNombreTitular());
			}
			if (StringUtils.isNotBlank(tramiteRegistroDto.getIdMunicipioCuenta())) {
				datosBancariosFavoritosDto.setIdMunicipio(tramiteRegistroDto.getIdMunicipioCuenta());
			}
			if (StringUtils.isNotBlank(tramiteRegistroDto.getIdProvinciaCuenta())) {
				datosBancariosFavoritosDto.setIdProvincia(tramiteRegistroDto.getIdProvinciaCuenta());
			}

			datosBancariosFavoritosDto.setTipoDatoBancario(TipoCuentaBancaria.NUEVA.getValorEnum());
			datosBancariosFavoritosDto.setEsFavorita(false);

			tramiteRegistroDto.setDatosBancarios(datosBancariosFavoritosDto);
		}

		tramiteRegistroDto.setFormaPago(new BigDecimal(TipoFormaPago.CUENTA.getValorEnum()));
		return resultado;
	}

	@Override
	@Transactional
	public ResultRegistro confirmarPagoFactura(TramiteRegistroDto tramiteRegistroDto, String alias) {

		ResultRegistro resultRegistro;

		FacturaRegistroDto factura;

		resultRegistro = servicioFacturaRegistro.getFacturaRegistro(tramiteRegistroDto.getFacturaRegistro().getIdFactura());
		if (resultRegistro.isError()) {
			return resultRegistro;
		} else {
			factura = (FacturaRegistroDto) resultRegistro.getObj();
		}

		if (tramiteRegistroDto.getEstado().equals(new BigDecimal(EstadoTramiteRegistro.Finalizado.getValorEnum()))) {
			resultRegistro.setError(Boolean.TRUE);
			resultRegistro.setMensaje("Ya se había confirmado la factura: " + factura.getNumFactura());
		} else if (!new BigDecimal(EstadoTramiteRegistro.Inscrito.getValorEnum()).equals(tramiteRegistroDto.getEstado()) && !new BigDecimal(EstadoTramiteRegistro.Finalizado.getValorEnum()).equals(
				tramiteRegistroDto.getEstado())) {
			resultRegistro.setError(Boolean.TRUE);
			resultRegistro.setMensaje("El estado actual del trámite  " + tramiteRegistroDto.getIdTramiteRegistro() + " no permite confirmar la factura.");
		} else {

			try {

				ResultBean result;

				result = buildDpr.construirConfirmacionFactura(factura, tramiteRegistroDto, alias);

				if (result.getError()) {
					resultRegistro.setError(Boolean.TRUE);
					resultRegistro.setMensaje(result.getMensaje());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultRegistro;
				}

				String nombreFichero = (String) result.getAttachment("nombreFichero");

				result = servicioCola.crearSolicitud(ProcesosEnum.WREG.getNombreEnum(), nombreFichero, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD2), tramiteRegistroDto.getTipoTramite(),
						tramiteRegistroDto.getIdTramiteRegistro().toString(), tramiteRegistroDto.getIdUsuario(), null, tramiteRegistroDto.getIdContrato());

				if (result != null && !result.getError()) {
					resultRegistro.setMensaje("La solicitud de envío se ha creado correctamente. Recibirá notificación del envío.");
					// Pendiente_Confirmacion_Pago
					EstadoTramiteRegistro nuevoEstado = EstadoTramiteRegistro.pendienteEnvioSegunEstado(tramiteRegistroDto.getEstado().toString(), EstadoTramiteRegistro.Finalizado.getValorEnum());

					if (nuevoEstado != null && !nuevoEstado.getValorEnum().equals(tramiteRegistroDto.getEstado().toString())) {
						ResultBean respuesta = cambiarEstado(true, tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getEstado(), new BigDecimal(nuevoEstado.getValorEnum()), true,
								TextoNotificacion.Cambio_Estado.getNombreEnum(), tramiteRegistroDto.getIdUsuario());

						if (respuesta != null && respuesta.getError()) {
							log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "Ha ocurrido el siguiente error actualizando el estado del trámite con identificador: " + tramiteRegistroDto
									.getIdTramiteRegistro() + " : " + respuesta.getMensaje());
							resultRegistro.setError(Boolean.TRUE);
							resultRegistro.setMensaje("Ha ocurrido un error actualizando el estado del trámite");
						} else {
							tramiteRegistroDto.setEstado(new BigDecimal(nuevoEstado.getValorEnum()));
						}
					}

				} else {
					resultRegistro.setError(Boolean.TRUE);
					resultRegistro.setMensaje(result.getMensaje());
					log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO1_ERROR + " construirDpr. " + result.getMensaje());
				}
			} catch (OegamExcepcion e) {
				resultRegistro.setError(Boolean.TRUE);
				resultRegistro.setMensaje(e.getMensajeError1());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return resultRegistro;
			} catch (Throwable e) {
				resultRegistro.setError(Boolean.TRUE);
				log.error("Error al firmar acuse " + tramiteRegistroDto.getIdTramiteRegistro() + ", error: ", e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return resultRegistro;
			}
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "construir");
		}
		return resultRegistro;
	}

	@Override
	@Transactional
	public ResultRegistro construirAcuse(String idRegistro, String alias, String estadoAcusePendiente, String idRegistroFueraSecuencia) {

		ResultRegistro resultRegistro = new ResultRegistro();

		TramiteRegistroDto tramiteRegistroDto = getTramite(new BigDecimal(idRegistro.trim()));

		if (tramiteRegistroDto.getEstado() == null) {
			resultRegistro.setError(Boolean.TRUE);
			resultRegistro.setMensaje("No se ha podido recuperar el trámite: " + idRegistro);
		} else if (StringUtils.isBlank(estadoAcusePendiente) && (tramiteRegistroDto.getEstado().equals(new BigDecimal(EstadoTramiteRegistro.Pendiente_Envio_AN_Defectos.getValorEnum()))
				|| tramiteRegistroDto.getEstado().equals(new BigDecimal(EstadoTramiteRegistro.Pendiente_Envio_AN_Denegacion.getValorEnum())) || tramiteRegistroDto.getEstado().equals(new BigDecimal(
						EstadoTramiteRegistro.Pendiente_Envio_AN_Parcial.getValorEnum())) || tramiteRegistroDto.getEstado().equals(new BigDecimal(
								EstadoTramiteRegistro.Pendiente_Envio_AN_Inscripcion_Total.getValorEnum())) || tramiteRegistroDto.getEstado().equals(new BigDecimal(
										EstadoTramiteRegistro.Pendiente_Envio_AN_Presentacion.getValorEnum())))) {
			resultRegistro.setError(Boolean.TRUE);
			resultRegistro.setMensaje("Ya se había firmado el acuse del trámite: " + idRegistro);
		} else if (StringUtils.isBlank(estadoAcusePendiente) && (!UtilesRegistradores.permitidoFirmarAcuse(tramiteRegistroDto.getEstado()))) {
			resultRegistro.setError(Boolean.TRUE);
			resultRegistro.setMensaje("El estado actual del trámite  " + idRegistro + " no permite firmar acuse.");
		} else {

			try {
				TramiteRegRbmDto tramiteRegRbmDto = null;

				if (TipoTramiteRegistro.MODELO_7.getValorEnum().equalsIgnoreCase(tramiteRegistroDto.getTipoTramite())) {
					tramiteRegRbmDto = (TramiteRegRbmDto) servicioTramiteRegRbm.getTramiteRegRbm(idRegistro.trim()).getObj();
				} else if (TipoTramiteRegistro.MODELO_8.getValorEnum().equalsIgnoreCase(tramiteRegistroDto.getTipoTramite())) {
					tramiteRegRbmDto = (TramiteRegRbmDto) servicioTramiteRegRbm.getTramiteRegRbm(idRegistro.trim()).getObj();
				} else if (TipoTramiteRegistro.MODELO_9.getValorEnum().equalsIgnoreCase(tramiteRegistroDto.getTipoTramite())) {
					tramiteRegRbmDto = (TramiteRegRbmDto) servicioTramiteRegRbm.getTramiteRegRbm(idRegistro.trim()).getObj();
				} else if (TipoTramiteRegistro.MODELO_10.getValorEnum().equalsIgnoreCase(tramiteRegistroDto.getTipoTramite())) {
					tramiteRegRbmDto = (TramiteRegRbmDto) servicioTramiteRegRbm.getTramiteRegRbmCancelDesist(idRegistro.trim()).getObj();
				} else if (TipoTramiteRegistro.MODELO_13.getValorEnum().equalsIgnoreCase(tramiteRegistroDto.getTipoTramite())) {
					tramiteRegRbmDto = (TramiteRegRbmDto) servicioTramiteRegRbm.getTramiteRegRbmCancelDesist(idRegistro.trim()).getObj();
				}

				BigDecimal estadoTramite = null;
				if (StringUtils.isNotBlank(estadoAcusePendiente)) {
					estadoTramite = new BigDecimal(estadoAcusePendiente);
				} else {
					estadoTramite = tramiteRegistroDto.getEstado();
				}

				String tipoMensaje = null;
				String tipoPdf = null;

				if (EstadoTramiteRegistro.Pendiente_Firma_Acuse_Denegacion.getValorEnum().equals(estadoTramite.toString())) {
					tipoMensaje = ConstantesRegistradores.TIPOMENSAJE_ASIENTO_DENEGACION_CONFIRMACION_ACUSE;
					tipoPdf = ConstantesRegistradores.TIPOMENSAJE_ASIENTO_DENEGACION_CONFIRMACION;
				} else if (EstadoTramiteRegistro.Pendiente_Firma_Acuse_Inscripcion_Total.getValorEnum().equals(estadoTramite.toString())) {
					tipoMensaje = ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_TOTAL_ACUSE;
					tipoPdf = ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_TOTAL;
				} else if (EstadoTramiteRegistro.Pendiente_Firma_Acuse_Inscripcion_Parcial.getValorEnum().equals(estadoTramite.toString())) {
					tipoMensaje = ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_PARCIAL_ACUSE;
					tipoPdf = ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_PARCIAL;
				} else if (EstadoTramiteRegistro.Pendiente_Firma_Acuse_Calificado_Defectos.getValorEnum().equals(estadoTramite.toString())) {
					tipoMensaje = ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_DEFECTOS_ACUSE;
					tipoPdf = ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_DEFECTOS;
				} else if (EstadoTramiteRegistro.Pendiente_Firma_Acuse_Presentacion.getValorEnum().equals(estadoTramite.toString())) {
					tipoMensaje = ConstantesRegistradores.TIPOMENSAJE_ASIENTO_ACEPTACION_CONFIRMACION_ACUSE;
					tipoPdf = ConstantesRegistradores.TIPOMENSAJE_ASIENTO_ACEPTACION_CONFIRMACION;
				}

				ResultBean result;
				if (!tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_7.getValorEnum()) && !tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(
						TipoTramiteRegistro.MODELO_8.getValorEnum()) && !tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_9.getValorEnum()) && !tramiteRegistroDto
								.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_10.getValorEnum()) && !tramiteRegistroDto.getTipoTramite().equalsIgnoreCase(TipoTramiteRegistro.MODELO_13
										.getValorEnum())) {

					result = buildDpr.construirAcuse(tramiteRegistroDto, tipoMensaje, tipoPdf, alias);
				} else {
					result = buildDpr.construirAcuse(tramiteRegRbmDto, tipoMensaje, tipoPdf, alias);
				}

				if (result.getError()) {
					resultRegistro.setError(Boolean.TRUE);
					resultRegistro.setMensaje(result.getMensaje());
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultRegistro;
				}

				String idTramite = null;
				String tipoTramite = null;
				if (StringUtils.isBlank(idRegistroFueraSecuencia)) {
					idTramite = tramiteRegistroDto.getIdTramiteRegistro().toString();
					tipoTramite = tramiteRegistroDto.getTipoTramite();
				} else {
					idTramite = idRegistroFueraSecuencia;
					tipoTramite = "RFS";
				}

				String nombreFichero = (String) result.getAttachment("nombreFichero");

				result = servicioCola.crearSolicitud(ProcesosEnum.WREG.getNombreEnum(), nombreFichero, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD2), tipoTramite, idTramite,
						tramiteRegistroDto.getIdUsuario(), null, tramiteRegistroDto.getIdContrato());

				if (result != null && !result.getError()) {
					resultRegistro.setMensaje("La solicitud de envío se ha creado correctamente. Recibirá notificación del envío.");

					if (StringUtils.isBlank(idRegistroFueraSecuencia) || ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_DEFECTOS_ACUSE.equalsIgnoreCase(tipoMensaje)
							|| ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_PARCIAL_ACUSE.equalsIgnoreCase(tipoMensaje) || ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_TOTAL_ACUSE
									.equalsIgnoreCase(tipoMensaje)) {

						EstadoTramiteRegistro nuevoEstado = EstadoTramiteRegistro.pendienteEnvioSegunEstado(tramiteRegistroDto.getEstado().toString(), tipoMensaje);

						if (nuevoEstado != null && !nuevoEstado.getValorEnum().equals(tramiteRegistroDto.getEstado().toString())) {
							ResultBean respuesta = cambiarEstado(true, tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getEstado(), new BigDecimal(nuevoEstado.getValorEnum()), true,
									TextoNotificacion.Cambio_Estado.getNombreEnum(), tramiteRegistroDto.getIdUsuario());

							if (respuesta != null && respuesta.getError()) {
								log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "Ha ocurrido el siguiente error actualizando el estado del trámite con identificador: " + tramiteRegistroDto
										.getIdTramiteRegistro() + " : " + respuesta.getMensaje());
								resultRegistro.setError(Boolean.TRUE);
								resultRegistro.setMensaje("Ha ocurrido un error actualizando el estado del trámite");
							} else {
								tramiteRegistroDto.setEstado(new BigDecimal(nuevoEstado.getValorEnum()));
							}
						}
					}

				} else {
					resultRegistro.setError(Boolean.TRUE);
					resultRegistro.setMensaje(result.getMensaje());
					log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO1_ERROR + " construirDpr. " + result.getMensaje());
				}
			} catch (OegamExcepcion e) {
				resultRegistro.setError(Boolean.TRUE);
				resultRegistro.setMensaje(e.getMensajeError1());
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return resultRegistro;
			} catch (Throwable e) {
				resultRegistro.setError(Boolean.TRUE);
				log.error("Error al firmar acuse " + idRegistro + ", error: ", e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return resultRegistro;
			}
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "construir");
		}
		return resultRegistro;
	}

	@Override
	@Transactional
	public TramiteRegistroDto getTramiteJustificante(String identificador) {
		TramiteRegistroDto tramiteRegistroDto = conversor.transform(tramiteRegistroDao.getTramiteJustificante(identificador), TramiteRegistroDto.class);
		if (null != tramiteRegistroDto) {
			ArrayList<FicheroInfo> ficherosSubidos = recuperarDocumentos(tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getTipoTramite());
			if (null != ficherosSubidos && !ficherosSubidos.isEmpty()) {
				tramiteRegistroDto.setFicherosSubidos(ficherosSubidos);
			}
		}

		return tramiteRegistroDto;
	}

	@Override
	public String cleanString(String texto) {
		if (StringUtils.isNotBlank(texto)) {
			texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
			texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		}
		return texto;
	}

}
