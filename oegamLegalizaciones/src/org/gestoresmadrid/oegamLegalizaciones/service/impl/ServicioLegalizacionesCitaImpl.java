package org.gestoresmadrid.oegamLegalizaciones.service.impl;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.legalizacion.model.enumerados.TipoDocumento;
import org.gestoresmadrid.core.legalizacion.model.vo.LegalizacionCitaVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.CorreoContratoTramiteDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.correo.service.ServicioComunCorreo;
import org.gestoresmadrid.oegamComun.utiles.MD5;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamLegalizaciones.service.ServicioLegalizacionesCita;
import org.gestoresmadrid.oegamLegalizaciones.service.ServicioPersistenciaLegalizaciones;
import org.gestoresmadrid.oegamLegalizaciones.service.ServicioValidacionLegalizaciones;
import org.gestoresmadrid.oegamLegalizaciones.view.beans.ResultadoLegalizacionesBean;
import org.gestoresmadrid.oegamLegalizaciones.view.dto.LegalizacionCitaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioLegalizacionesCitaImpl implements ServicioLegalizacionesCita {

	private static final long serialVersionUID = -2821453199891382215L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLegalizacionesCitaImpl.class);

	private static final String TEXT_DOCUMENTACION_SUBIDA = "El Colegio de Gestores de Madrid informa que se ha subido la documentación de la siguiente petición: ";
	private static final String TEXTO_ACUSE_SOLICITUD = "Se ha recibido por parte del Colegio de Gestores Administrativos de Madrid la solicitud de documentación de la siguiente petición:";
	private static final String TEXTO_SOLICITUD_DOCUMENTACION = "Se ha solicitado por parte del Ministerio de Asuntos exteriores y Comercio que se suba la documentación referida a la siguiente petición: ";

	private static final int ESTADO = 1;
	private static final int ESTADO_BAJA = 0;
	private static final int ESTADO_SOLICITADO = 2;
	private static final int ESTADO_INICIADO = 1;
	private static final int ESTADO_FINALIZADO = 4;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private Conversor conversor;

	@Autowired
	private Utiles utiles;

	@Autowired
	private ServicioPersistenciaLegalizaciones servicioPersistencia;

	@Autowired
	private ServicioValidacionLegalizaciones servicioValidaciones;
	
	@Autowired
	private ServicioComunContrato servicioComunContrato;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private ServicioComunCorreo servicioCorreo;

	@Override
	public LegalizacionCitaDto getPeticion(LegalizacionCitaDto legalizacion) throws ParseException {

		LegalizacionCitaVO legBean = servicioPersistencia.obtenerLegalizacionPorId(legalizacion.getIdPeticion());

		return conversor.transform(legBean, LegalizacionCitaDto.class);

	}

	@Override
	public List<File> getDocumentacion(LegalizacionCitaDto legDto) throws OegamExcepcion, ParseException {

		String subTipo = "";
		if (TipoDocumento.TITULO.equals(TipoDocumento.convertir(legDto.getTipoDocumento()))) {
			subTipo = ConstantesGestorFicheros.LEGALIZACION_TITULO;
		} else if (TipoDocumento.DOCUMENTOS.equals(TipoDocumento.convertir(legDto.getTipoDocumento()))) {
			subTipo = ConstantesGestorFicheros.LEGALIZACION_DOCUMENTO;
		}

		FileResultBean result = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.LEGALIZACION, subTipo, legDto.getFechaLegalizacion(), legDto.getReferencia());

		return result != null ? result.getFiles() : null;
	}

	@Override
	public List<LegalizacionCitaDto> getListadoDiario(String numColegiado, Fecha fechaListado) throws Throwable {
		List<LegalizacionCitaDto> listLegDto = new ArrayList<LegalizacionCitaDto>();

		List<LegalizacionCitaVO> listLeg = servicioPersistencia.listadoDiario(numColegiado, fechaListado.getFecha());
		for (LegalizacionCitaVO leg : listLeg) {
			LegalizacionCitaDto legDto = conversor.transform(leg, LegalizacionCitaDto.class);
			legDto.setTipoDocumento(TipoDocumento.convertir(legDto.getTipoDocumento()).getNombreEnum());
			listLegDto.add(legDto);
		}
		return listLegDto;
	}

	public String transformToXML(List<LegalizacionCitaDto> listaLeg) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";

		XStream xStream = new XStream();
		xStream.processAnnotations(LegalizacionCitaDto.class);
		xml += xStream.toXML(listaLeg);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");

		return xml;
	}

	private void envioMailInformandoMAECDocumentacionSubida(LegalizacionCitaDto legDto) {

		String textoMensaje = "";

		String asuntoMail = gestorPropiedades.valorPropertie("asunto.mail.documentacion.subida");
//		String recipient = gestorPropiedades.valorPropertie("direccion.email.MAEC");

		ContratoDto contrato = legDto.getContrato();

		if (contrato != null) {

			String recipient = contrato.getCorreoElectronico();
			if (contrato.getCorreosTramites() != null && !contrato.getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteDto correoContratoTramite : contrato.getCorreosTramites()) {
					if (TipoTramiteTrafico.Legalizacion.getValorEnum().equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())) {
						recipient = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}

			String bcc = gestorPropiedades.valorPropertie("copia.envio.solicitud.documentacion");
			if (StringUtils.isNotBlank(recipient)) {
				textoMensaje = TEXT_DOCUMENTACION_SUBIDA;
				textoMensaje = textoMensaje + "<br></br>";
				textoMensaje = textoMensaje + "Nombre de la peticion: " + legDto.getNombre();
				textoMensaje = textoMensaje + "<br></br>";
				textoMensaje = textoMensaje + "Referencia de la petición: " + legDto.getReferencia();
				textoMensaje = textoMensaje + "<br></br>";
				textoMensaje = textoMensaje + "Número de colegiado: " + legDto.getNumColegiado();

				ResultadoBean resultado;
				resultado = servicioCorreo.enviarCorreo(textoMensaje, null, null, asuntoMail, recipient, bcc, null, null);
				if (resultado == null || resultado.getError()) {
					Log.error("Se ha producido un error al enviar mail al Ministerio informando que se ha subido la documentación " + "de la petición con referencia: " + legDto.getReferencia());
				}
			}
		}

	}

	@Override
	public ResultadoLegalizacionesBean borrarPeticiones(String idPeticion, boolean esAdmin) throws Exception {
		ResultadoLegalizacionesBean resultadoBorrar = new ResultadoLegalizacionesBean();
		List<LegalizacionCitaVO> legalizacionCitaVO = obtenerSeleccionados(idPeticion.split("-"));
		for (LegalizacionCitaVO legBean : legalizacionCitaVO) {
			ResultadoLegalizacionesBean resultadoUnitarioBorrar = borrarPeticion(legBean, esAdmin);
			if (resultadoUnitarioBorrar.getError() != null && resultadoUnitarioBorrar.getError()) {
				if (resultadoBorrar.getListaMensajesError() == null) {
					resultadoBorrar.setError(true);
					resultadoBorrar.setListaMensajesError(new ArrayList<String>(1));
				}
				resultadoBorrar.getListaMensajesError().add(resultadoUnitarioBorrar.getMensaje());
			} else {
				if (resultadoBorrar.getListaMensajesAvisos() == null) {
					resultadoBorrar.setListaMensajesAvisos(new ArrayList<String>(1));
				}
				resultadoBorrar.getListaMensajesAvisos().add("La petición con referencia " + legBean.getReferencia() + " fue borrada correctamente.");
			}
		}

		return resultadoBorrar;
	}

	private ResultadoLegalizacionesBean borrarPeticion(LegalizacionCitaVO legalizacionCitaVO, boolean esAdmin) {
		ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
		resultado.setError(false);

		if (legalizacionCitaVO.getEstado() == ESTADO_BAJA) {
			resultado.setMensaje("No se puede eliminar la petición con referencia " + legalizacionCitaVO.getReferencia() + " porque ya fue eliminada.");
			resultado.setError(true);
		} else {
			// Si es usuario Administrador tiene permiso para borrar cualquier petición sin importar el día.
			// Si es un colegiado solo podrá borrar la petición hecha en el día de hoy.
			if (esAdmin) {
				legalizacionCitaVO.setEstado(ESTADO_BAJA);
				LegalizacionCitaVO legBeanAct = servicioPersistencia.guardarOActualizar(legalizacionCitaVO);
				if (legBeanAct == null) {
					log.error("Se ha producido un error al intentar borrar la petición: " + legalizacionCitaVO.getIdPeticion());
					resultado.setError(true);
					resultado.setMensaje("Se ha producido un error al intentar borrar la petición: " + legalizacionCitaVO.getIdPeticion());
				} else {
					resultado.setMensaje("Se ha solicitado correctamente la documentación para la petición: " + legalizacionCitaVO.getReferencia());
				}
			} else {
				if (legalizacionCitaVO.getFechaLegalizacion() != null) {
					resultado = servicioValidaciones.permiteBorrar(legalizacionCitaVO, esAdmin);
					if (!resultado.getError()) {
						legalizacionCitaVO.setEstado(ESTADO_BAJA);
						servicioPersistencia.guardarOActualizar(legalizacionCitaVO);
					}
				}
			}
		}

		return resultado;
	}

	private ResultadoLegalizacionesBean solicitarDocumentacion(LegalizacionCitaVO legalizacionCitaVO) {
		ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
		resultado.setError(false);

		try {
			if (servicioValidaciones.esPosibleSolicitud(legalizacionCitaVO).getError()) {
				resultado.setError(true);
				resultado.setMensaje("No se puede solicitar la documentación del trámite " + legalizacionCitaVO.getReferencia()
						+ " porque ha pasado más de 1 semana desde que se entregó en el Ministerio.");
				return resultado;
			}

			envioMailInformandoColegio(legalizacionCitaVO);
			envioMailAcuseMAEC(legalizacionCitaVO);

			legalizacionCitaVO.setSolicitado(1);
			LegalizacionCitaVO legBeanAct = servicioPersistencia.guardarOActualizar(legalizacionCitaVO);
			if (legBeanAct == null) {
				log.error("Se ha producido un error al intentar solicitar documentación de la petición: " + legalizacionCitaVO.getIdPeticion());
				resultado.setError(true);
				resultado.setMensaje("Se ha producido un error al intentar solicitar documentación de la petición: " + legalizacionCitaVO.getIdPeticion());
			} else {
				resultado.setMensaje("Se ha solicitado correctamente la documentación para la petición: " + legalizacionCitaVO.getReferencia());
			}
		} catch (OegamExcepcion e) {
			log.error("Se ha producido un error al solicitar la documentación de la petición: " + legalizacionCitaVO.getReferencia(), e);
			resultado.setError(false);
			resultado.setMensaje("Se ha producido un error al solicitar la documentación de la petición: " + legalizacionCitaVO.getReferencia());
		}

		return resultado;
	}

	private void envioMailInformandoColegio(LegalizacionCitaVO legalizacionCita) throws OegamExcepcion {

		String textoMensaje = "";

		String asuntoMail = gestorPropiedades.valorPropertie("asunto.mail.solicitud.documentacion");
		String recipient = gestorPropiedades.valorPropertie("direccion.solicitud.documentacion.MAEC");
		String bcc = gestorPropiedades.valorPropertie("copia.envio.solicitud.documentacion");
		if (StringUtils.isNotBlank(recipient)) {
			textoMensaje = TEXTO_SOLICITUD_DOCUMENTACION;
			textoMensaje = textoMensaje + "<br></br>";
			textoMensaje = textoMensaje + "Nombre de la petición: " + legalizacionCita.getNombre();
			textoMensaje = textoMensaje + "<br></br>";
			textoMensaje = textoMensaje + "Referencia de la petición: " + legalizacionCita.getReferencia();
			textoMensaje = textoMensaje + "<br></br>";
			textoMensaje = textoMensaje + "Número de colegiado: " + legalizacionCita.getNumColegiado();

			ResultadoBean resultado;

			resultado = servicioCorreo.enviarCorreo(textoMensaje, null, null, asuntoMail, recipient, bcc, null, null);
			if (resultado == null || resultado.getError()) {
				log.error("Se ha producido un error al enviar mail al colegio pidiendo la documentación de la petición: " + legalizacionCita.getIdPeticion());
			}
		}

	}

	private void envioMailAcuseMAEC(LegalizacionCitaVO legalizacionCita) throws OegamExcepcion {

		String textoMensaje = "";

		String asuntoMail = gestorPropiedades.valorPropertie("asunto.mail.acuse.solicitud");
		// String recipient = gestorPropiedades.valorPropertie("direccion.email.MAEC");
		ContratoVO contrato = legalizacionCita.getContrato();

		if (contrato != null) {

			String recipient = contrato.getCorreoElectronico();
			if (contrato.getCorreosTramites() != null && !contrato.getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : contrato.getCorreosTramites()) {
					if (TipoTramiteTrafico.Legalizacion.getValorEnum().equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())) {
						recipient = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}

			String bcc = gestorPropiedades.valorPropertie("copia.envio.solicitud.documentacion");

			if (StringUtils.isNotBlank(recipient)) {
				textoMensaje = TEXTO_ACUSE_SOLICITUD;
				textoMensaje = textoMensaje + "<br></br>";
				textoMensaje = textoMensaje + "Nombre de la petición: " + legalizacionCita.getNombre();
				textoMensaje = textoMensaje + "<br></br>";
				textoMensaje = textoMensaje + "Referencia de la petición: " + legalizacionCita.getReferencia();
				textoMensaje = textoMensaje + "<br></br>";
				textoMensaje = textoMensaje + "Número de colegiado: " + legalizacionCita.getNumColegiado();

				ResultadoBean resultado;
				resultado = servicioCorreo.enviarCorreo(textoMensaje, null, null, asuntoMail, recipient, bcc, null, null);
				if (resultado == null || resultado.getError()) {
					log.error("Se ha producido un error al enviar mail al colegio pidiendo la documentación de la petición: " + legalizacionCita.getIdPeticion());
				}
			}
		}
	}

	@Override
	public ResultadoLegalizacionesBean solDocumentaciones(List<LegalizacionCitaVO> listaSeleccionados) throws Exception {
		ResultadoLegalizacionesBean resultadoSolDoc = new ResultadoLegalizacionesBean();

		for (LegalizacionCitaVO legalizacionCitaVO : listaSeleccionados) {
			ResultadoLegalizacionesBean resultBean = solicitarDocumentacion(legalizacionCitaVO);
			if (!resultBean.getError()) {
				if (resultadoSolDoc.getListaMensajesAvisos() == null) {
					resultadoSolDoc.setListaMensajesAvisos(new ArrayList<String>(1));
				}
				resultadoSolDoc.getListaMensajesAvisos().add(resultBean.getMensaje());
			} else {
				if (resultadoSolDoc.getListaMensajesError() == null) {
					resultadoSolDoc.setError(true);
					resultadoSolDoc.setListaMensajesError(new ArrayList<String>(1));
				}
				resultadoSolDoc.getListaMensajesError().add(resultBean.getMensaje());
			}
		}

		return resultadoSolDoc;
	}

	@Override
	public ResultadoLegalizacionesBean solLegalizaciones(List<LegalizacionCitaVO> listaSeleccionados, Fecha fechaLegalizacion, boolean esAdmin) throws Exception {
		ResultadoLegalizacionesBean resultadoSolLeg = new ResultadoLegalizacionesBean();

		for (LegalizacionCitaVO legBean : listaSeleccionados) {
			ResultadoLegalizacionesBean resultadoUnitarioConfirmarLegalizacion = confirmarFechaLegalizacion(legBean, fechaLegalizacion, esAdmin);
			if (resultadoUnitarioConfirmarLegalizacion.getError()) {
				if (resultadoSolLeg.getListaMensajesError() == null) {
					resultadoSolLeg.setError(true);
					resultadoSolLeg.setListaMensajesError(new ArrayList<String>(1));
				}
				resultadoSolLeg.getListaMensajesError().add(resultadoUnitarioConfirmarLegalizacion.getMensaje());
			} else {
				if (resultadoSolLeg.getListaMensajesAvisos() == null) {
					resultadoSolLeg.setListaMensajesAvisos(new ArrayList<String>(1));
				}
				resultadoSolLeg.getListaMensajesAvisos().add(resultadoUnitarioConfirmarLegalizacion.getMensaje());
			}
		}

		return resultadoSolLeg;
	}

	private ResultadoLegalizacionesBean confirmarFechaLegalizacion(LegalizacionCitaVO legalizacion, Fecha fechaLegalizacion, boolean esAdmin) {
		ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
		resultado.setError(false);
		try {
			if (!esAdmin) {
				if (legalizacion.getFechaLegalizacion() != null) {
					Fecha fechaLimitePresentacionEnColegio = utilesFecha.getPrimerLaborableAnterior(utilesFecha.getFechaConDate(legalizacion.getFechaLegalizacion()));
					resultado = servicioValidaciones.fechaLegalizacionValida(fechaLegalizacion, fechaLimitePresentacionEnColegio, esAdmin);
					if (resultado.getError()) {
						return resultado;
					}
				}
				ResultadoLegalizacionesBean resultAux = servicioValidaciones.permiteSolicitarLegalizacion(fechaLegalizacion, esAdmin);
				if (resultAux.getError()) {
					resultado.setError(true);
					resultado.setMensaje(resultAux.getMensaje());
					return resultado;
				}
			}

			legalizacion.setFechaLegalizacion(fechaLegalizacion.getDate());
			legalizacion.setEstadoPeticion(ESTADO_SOLICITADO);
			LegalizacionCitaVO legBeanAct = servicioPersistencia.guardarOActualizar(legalizacion);
			if (legBeanAct == null) {
				log.error("Se ha producido un error asignar fecha de legalización para la petición con nombre: " + legalizacion.getNombre());
				resultado.setError(true);
				resultado.setMensaje("Se ha producido un error asignar fecha de legalización para la petición con nombre: " + legalizacion.getIdPeticion());
			} else {
				resultado.setMensaje("Se ha establecido la fecha de legalización correctamente para la petición con nombre: " + legalizacion.getNombre());
			}

		} catch (Throwable e) {
			log.error("Se ha producido un error asignar fecha de legalización para la petición con nombre: " + legalizacion.getNombre(), e);
			resultado.setError(true);
			resultado.setMensaje("Se ha producido un error asignar fecha de legalización para la petición con nombre: " + legalizacion.getNombre());
		}

		return resultado;
	}

	@Override
	public ResultadoLegalizacionesBean cambiarEstados(List<LegalizacionCitaVO> listaSeleccionados, String cambiarEstado) throws Exception {
		ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
		resultado.setError(false);

		for (LegalizacionCitaVO legBean : listaSeleccionados) {
			ResultadoLegalizacionesBean resultadoCambiarEstado = cambiarEstado(legBean, cambiarEstado);
			if (!resultadoCambiarEstado.getError()) {
				if (resultado.getListaMensajesAvisos() == null) {
					resultado.setListaMensajesAvisos(new ArrayList<String>(1));
				}
				resultado.getListaMensajesAvisos().add(resultadoCambiarEstado.getMensaje());
			} else {
				if (resultado.getListaMensajesError() == null) {
					resultado.setError(true);
					resultado.setListaMensajesError(new ArrayList<String>(1));
				}
				resultado.getListaMensajesError().add(resultadoCambiarEstado.getMensaje());
			}
		}

		return resultado;
	}

	private ResultadoLegalizacionesBean cambiarEstado(LegalizacionCitaVO legBean, String cambioEstado) {

		ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
		resultado.setError(false);

		legBean.setEstadoPeticion(Integer.parseInt(cambioEstado));
		LegalizacionCitaVO legBeanAct = servicioPersistencia.guardarOActualizar(legBean);
		if (legBeanAct == null) {
			log.error("Se ha producido un error al intentar cambiar el estado a la petición: " + legBean.getIdPeticion());
			resultado.setError(true);
			resultado.setMensaje("Se ha producido un error al intentar cambiar el estado a la petición: " + legBean.getIdPeticion());
		} else {
			resultado.setMensaje("Peticion: " + legBean.getIdPeticion() + " cambiada de estado");
		}

		return resultado;
	}

	@Override
	public List<LegalizacionCitaVO> obtenerSeleccionados(String[] codSeleccionados) throws Exception {
		List<LegalizacionCitaVO> listaSeleccionados = new ArrayList<LegalizacionCitaVO>(1);

		for (String idPeticion : codSeleccionados) {
			LegalizacionCitaVO legBean = servicioPersistencia.obtenerLegalizacionPorId(Integer.parseInt(idPeticion));
			if (legBean == null) {
				Log.error("La legalización con id Petición " + idPeticion + " no existe");
				throw new Exception();
			}
			listaSeleccionados.add(legBean);
		}

		return listaSeleccionados;
	}

	@Override
	public ResultadoLegalizacionesBean guardarPeticion(LegalizacionCitaDto legDto, File fileUpload, String fileUploadFileName, boolean esAdmin) {
		ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
		resultado.setError(false);

		if (!utiles.esNombreFicheroValido(fileUploadFileName)) {
			resultado.setError(true);
			resultado.setMensaje("El nombre del fichero es erróneo.");
			return resultado;
		}

		ResultadoLegalizacionesBean resultadoValidarGuardar = servicioValidaciones.validarGuardar(legDto, esAdmin);
		if (resultadoValidarGuardar.getError()) {
			resultado.setError(true);
			resultado.setListaMensajesError(resultadoValidarGuardar.getListaMensajesError());
			return resultado;
		}
		
		if (legDto.getIdContrato() != null) {
			ContratoVO contratoVO = servicioComunContrato.getContrato(legDto.getIdContrato().longValue());
			legDto.setContrato(conversor.transform(contratoVO, ContratoDto.class));
		}
		
		resultado = guardar(legDto);
		if (!resultado.getError()) {
			legDto = resultado.getLegalizacionCitaDto();
			resultado.setMensaje("Petición guardada");

			if (esAdmin && fileUpload != null) {

				if (legDto.getFicheroAdjunto() == 0) {
					legDto.setFicheroAdjunto(1);
				}

				String extension = utiles.dameExtension(fileUploadFileName, true);
				ResultadoLegalizacionesBean resultadoFileUpload = guardarDocumentacion(legDto, fileUpload, extension);
				if (!resultadoFileUpload.getError()) {
					// Despúes de subir la documentación se envía un e-mail a MAEC informándole.
					envioMailInformandoMAECDocumentacionSubida(legDto);
				} else {
					resultado.setError(true);
					resultado.setMensaje(resultadoFileUpload.getMensaje());
				}
			}

		} else {
			resultado.setError(true);
			resultado.setMensaje(resultado.getMensaje());
		}

		return resultado;
	}

	private String generarHash(LegalizacionCitaVO legBean) {
		String resultado = "";

		resultado = MD5.getHash(legBean.getNombre() + utilesFecha.getTimestampActual());

		return resultado;
	}

	private ResultadoLegalizacionesBean guardar(LegalizacionCitaDto legDto) {
		ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
		resultado.setError(false);

		LegalizacionCitaVO legBean = conversor.transform(legDto, LegalizacionCitaVO.class);
		try {
			if (servicioPersistencia.esPosiblePeticion(legDto.getNumColegiado())) {

				if (legBean.getIdPeticion() == null) {
					legBean.setReferencia(generarHash(legBean));
					legBean.setFecha(utilesFecha.getFechaHoraActualLEG().getFechaHora());
				}
				legBean.setEstado(ESTADO);
				legBean.setNombre(legBean.getNombre().toUpperCase());

				// Si está en estado finalizado entonces no hay que cambiar el estado. La modificación viene porque estarán adjuntando el fichero.
				if (legDto.getEstadoPeticion() == null || ESTADO_FINALIZADO != legDto.getEstadoPeticion()) {
					if (!legDto.getFechaLegalizacion().isfechaNula()) {
						legBean.setEstadoPeticion(ESTADO_SOLICITADO);
						legBean.setFechaLegalizacion(legDto.getFechaLegalizacion().getFecha());
					} else {
						legBean.setEstadoPeticion(ESTADO_INICIADO);
					}
				}

				legBean.setFechaDocumento(legDto.getFechaDocumentacion().getFecha());
				legBean = servicioPersistencia.guardarOActualizar(legBean);

				if (legBean == null) {
					log.error("Se ha producido un error al intentar guardar la petición: " + legDto.getNombre());
					resultado.setError(true);
					resultado.setMensaje("Se ha producido un error al intentar guardar la petición: " + legDto.getNombre());
				}

				resultado.setLegalizacionCitaDto(conversor.transform(legBean, LegalizacionCitaDto.class));
			}
		} catch (ParseException e) {
			log.error("Se ha producido un error al solicitar la documentación de la petición: " + legBean.getNombre(), e);
			resultado.setError(true);
			resultado.setMensaje("Se ha producido un error al solicitar la documentación de la petición: " + legBean.getNombre());
		}

		return resultado;
	}

	private ResultadoLegalizacionesBean guardarDocumentacion(LegalizacionCitaDto legDto, File fileUpload, String extension) {
		ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
		resultado.setError(false);

		try {
			FicheroBean fichero = new FicheroBean();
			fichero.setExtension(extension);
			fichero.setTipoDocumento(ConstantesGestorFicheros.LEGALIZACION);
			fichero.setNombreDocumento(legDto.getReferencia());
			fichero.setFichero(fileUpload);
			fichero.setSobreescribir(true);
			if (TipoDocumento.TITULO.equals(TipoDocumento.convertir(legDto.getTipoDocumento()))) {
				fichero.setSubTipo(ConstantesGestorFicheros.LEGALIZACION_TITULO);
			} else if (TipoDocumento.DOCUMENTOS.equals(TipoDocumento.convertir(legDto.getTipoDocumento()))) {
				fichero.setSubTipo(ConstantesGestorFicheros.LEGALIZACION_DOCUMENTO);
			}
			fichero.setFecha(legDto.getFechaLegalizacion());

			gestorDocumentos.guardarFichero(fichero);
		} catch (OegamExcepcion e) {
			log.error("Se ha producido un error al intentar guardar la documentación", e);
			resultado.setError(false);
			resultado.setMensaje("Se ha producido un error al intentar guardar la documentación");
		}

		return resultado;
	}

}
