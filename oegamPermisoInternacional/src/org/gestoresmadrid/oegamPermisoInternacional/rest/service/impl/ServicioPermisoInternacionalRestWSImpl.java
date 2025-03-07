package org.gestoresmadrid.oegamPermisoInternacional.rest.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.impresion.model.enumerados.TipoDocumentoImpresiones;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoDeclaracion;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.PermisoInternacionalVO;
import org.gestoresmadrid.oegamComun.credito.service.ServicioComunCredito;
import org.gestoresmadrid.oegamComun.utiles.UtilidadesNIFValidator;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamInterga.rest.service.ServicioIntergaRestWS;
import org.gestoresmadrid.oegamInterga.restWS.request.FileRequest;
import org.gestoresmadrid.oegamInterga.restWS.request.Files;
import org.gestoresmadrid.oegamInterga.restWS.request.SendFilesRequest;
import org.gestoresmadrid.oegamInterga.restWS.request.UpdateRequest;
import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;
import org.gestoresmadrid.oegamPermisoInternacional.rest.service.ServicioPermisoInternacionalRestWS;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioEvolucionPermisoInternacional;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioPermisoInternacional;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioPersistenciaPermInter;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPermisoInternacionalRestWSImpl implements ServicioPermisoInternacionalRestWS {

	private static final long serialVersionUID = -700508065321934785L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPermisoInternacionalRestWSImpl.class);

	@Autowired
	ServicioIntergaRestWS servicioIntergaRestWS;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	ServicioPersistenciaPermInter servicioPersistenciaPermInter;

	@Autowired
	ServicioEvolucionPermisoInternacional servicioEvolucionPermisoInternacional;

	@Autowired
	ServicioPermisoInternacional servicioPermisoInternacional;

	@Autowired
	ServicioComunCredito servicioComunCredito;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilidadesNIFValidator utilidadesNIFValidator;

	@Autowired
	ServicioImpresionDocumentos servicioImpresionDocumentos;

	@Override
	public ResultadoIntergaBean enviarRest(Long idPermiso, Long idUsuario) {
		ResultadoIntergaBean resultado = new ResultadoIntergaBean(Boolean.FALSE);
		boolean evolucion = false;
		try {
			PermisoInternacionalVO permiso = servicioPersistenciaPermInter.getPermisoInternacional(idPermiso);
			if (permiso != null) {
				String estadoAnterior = permiso.getEstado();
				SendFilesRequest request = crearRequestEnvio(permiso);

				resultado = servicioIntergaRestWS.enviar(request);
				if (resultado != null && !resultado.getError()) {
					permiso.setEstado(EstadoTramitesInterga.Pendiente_Repuesta_DGT.getValorEnum());
					evolucion = true;
					if(permiso.getFechaPresentacion() != null) {
						permiso.setFechaPresentacion(permiso.getFechaPresentacion());
					}else {
						permiso.setFechaPresentacion(new Date());	
					}
					
					String estadoDeclaracion = enviarDeclaracionResponsabilidad(permiso, permiso.getEstadoDeclaracion(), idUsuario);
					permiso.setEstadoDeclaracion(estadoDeclaracion);
				} else {
					resultado.setError(Boolean.TRUE);
				}

				permiso.setRespuesta(resultado.getMensaje());

				servicioPersistenciaPermInter.actualizar(permiso);
				if (evolucion) {
					servicioEvolucionPermisoInternacional.guardar(idPermiso, estadoAnterior, permiso.getEstado(), idUsuario, TipoActualizacion.MOD.getValorEnum());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se encuentra el permiso en base de datos");
			}
		} catch (Exception e) {
			log.error("Error al enviar la solicitud de permiso internacional, error:", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al enviar el permiso internacional");
		}
		return resultado;
	}

	@Override
	public String enviarDeclaracionResponsabilidad(PermisoInternacionalVO permiso, String estadoDeclaracion, Long idUsuario) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			permiso.setEstadoDeclaracion(EstadoDeclaracion.Enviados.getValorEnum());
			if (EstadoDeclaracion.No_Enviado.getValorEnum().equals(estadoDeclaracion)) {
				estadoDeclaracion = enviarDeclaracionGestor(permiso, idUsuario);
				permiso.setEstadoDeclaracion(estadoDeclaracion);
				estadoDeclaracion = enviarDeclaracionColegio(permiso);
				permiso.setEstadoDeclaracion(estadoDeclaracion);
			} else if (EstadoDeclaracion.No_Enviado_Colegiado.getValorEnum().equals(estadoDeclaracion)) {
				estadoDeclaracion = enviarDeclaracionGestor(permiso, idUsuario);
				permiso.setEstadoDeclaracion(estadoDeclaracion);
			} else if (EstadoDeclaracion.No_Enviado_Colegio.getValorEnum().equals(estadoDeclaracion)) {
				estadoDeclaracion = enviarDeclaracionColegio(permiso);
				permiso.setEstadoDeclaracion(estadoDeclaracion);
			}
		} catch (Exception e) {
			result.setMensaje("Error al enviar las declaraciones de responsabilidad.");
			result.setError(Boolean.TRUE);
			log.error("Error al enviar las declaraciones de responsabilidad", e);
		}
		return permiso.getEstadoDeclaracion();
	}

	private String enviarDeclaracionColegio(PermisoInternacionalVO permiso) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			String nuevasEspecificaciones = gestorPropiedades.valorPropertie("interga.nuevos.especificaciones");
			if (!"SI".equals(nuevasEspecificaciones)) {
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.PERMISO_INTERNACIONAL, ConstantesGestorFicheros.DECLARACION_COLEGIO, null,
						TipoImpreso.DeclaracionResponsabilidadColegio.getNombreEnum(), null);
				if (fichero != null && fichero.getFile() != null) {
					result = subidaDocumentacionDeclaracionColegio(permiso, fichero.getFile());
				}
			}
			if (result != null && result.getError()) {
				if (EstadoDeclaracion.No_Enviado_Colegiado.getValorEnum().equals(permiso.getEstadoDeclaracion())) {
					return EstadoDeclaracion.No_Enviado.getValorEnum();
				} else {
					return EstadoDeclaracion.No_Enviado_Colegio.getValorEnum();
				}
			}
		} catch (Throwable e) {
			result.setMensaje("Error al enviar la declaración de responsabilidad del colegio.");
			result.setError(Boolean.TRUE);
			log.error("Error al enviar la declaración de responsabilidad del colegio", e);
		}
		return permiso.getEstadoDeclaracion();
	}

	private String enviarDeclaracionGestor(PermisoInternacionalVO permiso, Long idUsuario) {
		ResultadoIntergaBean result = new ResultadoIntergaBean(Boolean.FALSE);
		File fichero = servicioImpresionDocumentos.descargarFichero(TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiado.getNombreDocumento() + "_" + permiso.getNumExpediente().toString(),
				idUsuario.longValue());
		if (fichero != null) {
			result = subidaDocumentacionDeclaracionGestor(permiso, fichero);
		}
		if (result != null && result.getError()) {
			return EstadoDeclaracion.No_Enviado_Colegiado.getValorEnum();
		}
		return permiso.getEstadoDeclaracion();
	}
	@Override
	public ResultadoIntergaBean consultarRest() {
		ResultadoIntergaBean resultadoProceso = new ResultadoIntergaBean(Boolean.FALSE);
		try {
			List<PermisoInternacionalVO> lista = servicioPersistenciaPermInter.consultarPermisosInternacionales();
			if (lista != null && !lista.isEmpty()) {
				int numeroEjecuciones = 0;
				for (PermisoInternacionalVO permiso : lista) {
					ResultadoIntergaBean resultado = new ResultadoIntergaBean(Boolean.FALSE);

					String estadoAnterior = permiso.getEstado();
					String estadoNuevo = permiso.getEstado();
					String estadoImpAnterior = permiso.getEstadoImpresion();
					String estadoImpNuevo = permiso.getEstadoImpresion();
					String respuesta = permiso.getRespuesta();

					try {
						FileRequest request = crearRequestConsultar(permiso);
						resultado = servicioIntergaRestWS.consultar(request, permiso.getNumExpediente(), ServicioIntergaRestWS.PDF_REDIMENSIONADO);
						if (resultado != null && !resultado.getError()) {
							if (StringUtils.isNotBlank(resultado.getEstado())) {
								estadoNuevo = resultado.getEstado();
							}
							if (EstadoTramitesInterga.Finalizado.getValorEnum().equals(estadoNuevo)) {
								ResultadoBean resultCredito = descontarCreditos(permiso);
								if (resultCredito != null && !resultCredito.getError()) {
									numeroEjecuciones++;
									if (EstadoDeclaracion.No_Enviado.getValorEnum().equals(permiso.getEstadoDeclaracion()) || EstadoDeclaracion.No_Enviado_Colegiado.getValorEnum().equals(permiso
											.getEstadoDeclaracion())) {
										estadoNuevo = EstadoTramitesInterga.Finalizado_Pdt_Declaracion.getValorEnum();
									}
									estadoImpNuevo = EstadoTramitesInterga.Doc_Recibida.getValorEnum();
								}
							}
						} else {
							if (StringUtils.isBlank(resultado.getEstado())) {
								estadoNuevo = EstadoTramitesInterga.Finalizado_Error.getValorEnum();
							} else {
								estadoNuevo = resultado.getEstado();
							}
						}
						respuesta = resultado.getMensaje();
					} catch (Exception e) {
						log.error("Error en la consulta del permiso internacional con numExpediente: " + permiso.getNumExpediente(), e);
						respuesta = "Error en la consulta del permiso internacional";
						estadoNuevo = EstadoTramitesInterga.Finalizado_Error.getValorEnum();
					}

					servicioPersistenciaPermInter.actualizarEstadosYRespuesta(permiso.getIdPermiso(), estadoNuevo, estadoImpNuevo, respuesta);

					if (StringUtils.isNotBlank(estadoNuevo) && !estadoNuevo.equals(estadoAnterior)) {
						servicioEvolucionPermisoInternacional.guardar(permiso.getIdPermiso(), estadoAnterior, estadoNuevo, permiso.getContrato().getColegiado().getIdUsuario(), TipoActualizacion.MOD
								.getValorEnum());
					}

					if (StringUtils.isNotBlank(estadoImpNuevo) && !estadoImpNuevo.equals(estadoImpAnterior)) {
						servicioEvolucionPermisoInternacional.guardar(permiso.getIdPermiso(), estadoImpAnterior, estadoImpNuevo, permiso.getContrato().getColegiado().getIdUsuario(),
								TipoActualizacion.MOD.getValorEnum());
					}

					if (EstadoTramitesInterga.Finalizado.getValorEnum().equals(estadoNuevo)) {
						servicioPermisoInternacional.generarDocDocumentacion(permiso.getIdPermiso(), estadoNuevo);
					}
				}
				resultadoProceso.setNumeroEjecuciones(numeroEjecuciones);
			} else {
				resultadoProceso.setMensaje("No se encuentra ningún permiso para ser consultado");
			}
		} catch (Exception e) {
			log.error("Error al consultar el permiso internacional error:", e);
			resultadoProceso.setError(Boolean.TRUE);
			resultadoProceso.setMensaje("Error el permiso internacional");
		}
		return resultadoProceso;
	}

	private ResultadoBean descontarCreditos(PermisoInternacionalVO permiso) {
		ResultadoBean resultado = new ResultadoBean(Boolean.TRUE);
		try {
			resultado = servicioComunCredito.descontarCreditos(TipoTramiteTrafico.PermisonInternacional.getValorEnum(), permiso.getIdContrato(), permiso.getContrato().getColegiado().getIdUsuario(),
					Collections.singletonList(permiso.getNumExpediente()));
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descontar el crédito para el Permiso Internacional");
		}
		return resultado;
	}

	@Override
	public ResultadoIntergaBean subidaDocumentacionEscaneada(PermisoInternacionalVO permiso, File fichero, String numReferencia) {
		UpdateRequest request = crearRequestSubirDocu(permiso, fichero, numReferencia);
		return servicioIntergaRestWS.subidaDocumentacion(request, ServicioIntergaRestWS.PDF_ESCANEADO);
	}

	@Override
	public ResultadoIntergaBean subidaDocumentacionDeclaracionColegio(PermisoInternacionalVO permiso, File fichero) {
		UpdateRequest request = crearRequestSubirDocu(permiso, fichero, null);
		return servicioIntergaRestWS.subidaDocumentacion(request, ServicioIntergaRestWS.PDF_DECLARACION_COLEGIO);
	}

	@Override
	public ResultadoIntergaBean subidaDocumentacionDeclaracionGestor(PermisoInternacionalVO permiso, File fichero) {
		UpdateRequest request = crearRequestSubirDocu(permiso, fichero, null);
		return servicioIntergaRestWS.subidaDocumentacion(request, ServicioIntergaRestWS.PDF_DECLARACION_GESTOR);
	}

	private SendFilesRequest crearRequestEnvio(PermisoInternacionalVO permiso) {
		SendFilesRequest request = new SendFilesRequest();

		request.setSocietyCif(permiso.getContrato().getColegio().getCif());
		request.setPlatformCif(permiso.getContrato().getColegio().getCif());

		List<Files> files = new ArrayList<Files>();
		Files file = new Files();
		file.setTipo(ServicioIntergaRestWS.TIPO_PERMISO_INTERNACIONAL);
		file.setNumReferencia("");
		file.setTipoTramite(permiso.getTipoTramite());
		file.setGestoria(84);
		file.setDoiTitular(permiso.getDoiTitular());
		file.setMatricula("");
		file.setTasa(permiso.getCodigoTasa());

		int tipo = utilidadesNIFValidator.getType(permiso.getDoiTitular());
		if (tipo == utilidadesNIFValidator.ES_NIE) {
			file.setDoiTipo("3");
		} else {
			file.setDoiTipo("1");
		}

		file.setDoiCotitular("");

		// TODO: quitamos el envío de la dirección hasta nuevo aviso de la DGT

		/*
		 * IntervinientePermInterVO titular = servicioIntervinientePermInter.getIntervinienteTrafico(permiso.getNumExpediente(), permiso.getDoiTitular(), permiso.getNumColegiado()); if (titular.getDireccion() != null) { String nombreVia =
		 * servicioComunDireccion.obtenerNombreTipoVia(titular.getDireccion().getIdTipoVia()); file.setDirTipoVia(nombreVia); file.setDirNombreVia(titular.getDireccion().getNombreVia()); file.setDirNumero(titular.getDireccion().getNumero()); file.setDirBloque(titular.getDireccion().getBloque());
		 * file.setDirPortal(titular.getDireccion().getPortal()); file.setDirEscalera(titular.getDireccion().getEscalera()); file.setDirPlanta(titular.getDireccion().getPlanta()); file.setDirPuerta(titular.getDireccion().getPuerta()); if (titular.getDireccion().getKm() != null) {
		 * file.setDirKilometro(titular.getDireccion().getKm().intValue()); } if (titular.getDireccion().getHm() != null) { file.setDirHectometro(titular.getDireccion().getHm().intValue()); } file.setDirCodigoPostal(titular.getDireccion().getCodPostal());
		 * file.setDirLocalidad(titular.getDireccion().getPueblo()); String nombreMunicipio = servicioComunDireccion.obtenerNombreMunicipioSitex(titular.getDireccion().getIdMunicipio(), titular.getDireccion().getIdProvincia()); if (StringUtils.isNotBlank(nombreMunicipio)) {
		 * file.setDirMunicipio(nombreMunicipio.toUpperCase()); } else { file.setDirMunicipio(""); } String nombreProvincia = servicioComunDireccion.obtenerNombreProvinciaSitex(titular.getDireccion().getIdProvincia()); file.setDirProvincia(nombreProvincia); } else { file.setDirTipoVia("");
		 * file.setDirNombreVia(""); file.setDirNumero(""); file.setDirBloque(""); file.setDirPortal(""); file.setDirEscalera(""); file.setDirPlanta(""); file.setDirPuerta(""); file.setDirCodigoPostal(""); file.setDirLocalidad(""); file.setDirMunicipio(""); file.setDirProvincia("");
		 * file.setDirKilometro(null); file.setDirHectometro(null); file.setDirProvincia(""); }
		 */

		file.setImpresion("NO");
		file.setVehiculoImportacion("NO");
		file.setEmailAute("");
		file.setObservaciones(permiso.getObservaciones());
		files.add(file);

		request.setFiles(files);

		return request;
	}

	private FileRequest crearRequestConsultar(PermisoInternacionalVO permiso) {
		FileRequest request = new FileRequest();

		request.setSocietyCif(permiso.getContrato().getColegio().getCif());
		request.setPlatformCif(permiso.getContrato().getColegio().getCif());

		request.setFileType(ServicioIntergaRestWS.TIPO_PERMISO_INTERNACIONAL);
		request.setFileDate(utilesFecha.formatoFecha(permiso.getFechaPresentacion()));
		request.setFileDoi(permiso.getDoiTitular());
		request.setFilePlate("");

		return request;
	}

	private UpdateRequest crearRequestSubirDocu(PermisoInternacionalVO permiso, File fichero, String numReferencia) {
		UpdateRequest request = new UpdateRequest();

		request.setSocietyCif(permiso.getContrato().getColegio().getCif());
		request.setPlatformCif(permiso.getContrato().getColegio().getCif());

		request.setFileType(ServicioIntergaRestWS.TIPO_PERMISO_INTERNACIONAL);
		request.setFileDate(utilesFecha.formatoFecha(permiso.getFechaPresentacion()));
		request.setFileDoi(permiso.getDoiTitular());
		request.setFilePlate("");

		request.setReference(numReferencia);
		byte[] bArray = readFileToByteArray(fichero);
		request.setBase64(new String(Base64.encodeBase64(bArray)));

		return request;
	}

	private byte[] readFileToByteArray(File file) {
		FileInputStream fis = null;
		byte[] bArray = new byte[(int) file.length()];
		try {
			fis = new FileInputStream(file);
			fis.read(bArray);
			fis.close();
		} catch (IOException ioExp) {
			ioExp.printStackTrace();
		}
		return bArray;
	}
}
