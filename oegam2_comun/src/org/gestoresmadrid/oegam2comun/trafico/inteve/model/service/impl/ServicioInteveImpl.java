package org.gestoresmadrid.oegam2comun.trafico.inteve.model.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteSolicitudInformacion;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoConsultaInteve;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.oegam2comun.trafico.inteve.model.service.ServicioInteve;
import org.gestoresmadrid.oegam2comun.trafico.inteve.model.service.ServicioInteveWeb;
import org.gestoresmadrid.oegam2comun.trafico.inteve.view.bean.InformeInteveBean;
import org.gestoresmadrid.oegam2comun.trafico.inteve.view.bean.ResultInteveBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoSolInfo;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.SolicitudInformeVehiculoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafSolInfoDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import oegam.constantes.ConstantesPQ;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.SolicitudVehiculoBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.VehiculoBean;
import trafico.inteve.ConstantesInteve;
import trafico.inteve.TipoInformeInteve;
import trafico.modelo.ModeloSolicitudDatos;
import trafico.utiles.UtilesVistaTrafico;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.validaciones.NIFValidator;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioInteveImpl implements ServicioInteve {

	private static final long serialVersionUID = -2535343399528030212L;

	private static final String PARAMETROS_OK = "ok";

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioInteveImpl.class);

	private static String PDF = "pdf";
	private static String XML = "xml";
	private static String DOCUMENTO_ANTECEDENTE = "documentoAntecedentes";
	private static String JUSTIFICANTE_REGISTRO_SALIDA = "justificanteRegistroSalida";
	private static String TYPE_1_DOCUMENTO_ANTECEDENTE_PDF = "1"; // 1 = Informe de antecedentes del vehículo en formato pdf.
	private static String TYPE_2_JUSTIFICANTE_REGISTRO_SALIDA_PDF = "2"; // 2 = Justificante Registro de Salida del informe de la dgt en formato pdf.
	private static String TYPE_3_JUSTIFICANTE_REGISTRO_SALIDA_XML = "3"; // 3 = Justificante Registro de Salida del informe de la dgt en formato xml.

	private ModeloSolicitudDatos modeloSolicitudDatos;
	
	@Autowired
	private ServicioTramiteTraficoSolInfo servicioTramiteTraficoSolInfo;

	@Autowired
	private ServicioInteveWeb servicioInteveWeb;

	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private ServicioContrato servicioContrato;
	
	@Autowired
	private ServicioTasa servicioTasa;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	Utiles utiles;
	
	@Override
	public ResultInteveBean solicitarInforme(TramiteTrafSolInfoDto tramiteTrafSolInfoDto, BigDecimal idUsuario) {
		ResultInteveBean result = new ResultInteveBean();
		for (SolicitudInformeVehiculoDto solicitudInforme : tramiteTrafSolInfoDto.getSolicitudes()) {
			try{
				if (EstadoTramiteSolicitudInformacion.Pendiente.getValorEnum().equals(solicitudInforme.getEstado())) {
					ResultInteveBean resultadoSolicitud = solicitarInforme(solicitudInforme, tramiteTrafSolInfoDto.getNumColegiado());
					if (resultadoSolicitud.getException() != null ) {
						String mensaje = resultadoSolicitud.getException().getMessage();
						mensaje = mensaje != null && mensaje.length() > 300 ? mensaje.substring(0, 300) : mensaje;
						result.setError(true);
						result.setMensaje(mensaje);
						result.addListaIdSolInfoError(mensaje, solicitudInforme.getIdTramiteSolInfo());
					} else if (resultadoSolicitud.isError()) {
						result.setError(true);
						result.setMensaje(resultadoSolicitud.getMensaje());
						result.addListaIdSolInfoError(resultadoSolicitud.getMensaje(), solicitudInforme.getIdTramiteSolInfo());
					} else {
						String nombreInforme = DOCUMENTO_ANTECEDENTE;
						if (solicitudInforme.getVehiculo().getMatricula() != null && !solicitudInforme.getVehiculo().getMatricula().isEmpty()) {
							nombreInforme += solicitudInforme.getVehiculo().getMatricula();
						}else if (solicitudInforme.getVehiculo().getBastidor() != null && !solicitudInforme.getVehiculo().getBastidor().isEmpty()) {
							nombreInforme += solicitudInforme.getVehiculo().getBastidor();
						}else if (solicitudInforme.getVehiculo().getNive() != null && !solicitudInforme.getVehiculo().getNive().isEmpty()) {
							nombreInforme += solicitudInforme.getVehiculo().getNive();
						}
						gestorDocumentos.guardarFichero(ConstantesGestorFicheros.SOLICITUD_INFORMACION, null, 
								Utilidades.transformExpedienteFecha(tramiteTrafSolInfoDto.getNumExpediente()), nombreInforme, ConstantesGestorFicheros.EXTENSION_PDF,  resultadoSolicitud.getInforme());
						result.addListaIdSolInfoOK("Informe Generado Correctamente", solicitudInforme.getIdTramiteSolInfo(),nombreInforme,  resultadoSolicitud.getInforme());
					}
				}
			}catch(Throwable e){
				LOG.error("No se ha podido recuperar el informe");			
				solicitudInforme.setResultado(e.getMessage() != null && e.getMessage().length() > 300 ? e.getMessage().substring(0, 300) : e.getMessage());
				result.setError(true);
				result.setMensaje(e.getMessage());
			}
		}
		ResultInteveBean resultActualizar = servicioTramiteTraficoSolInfo.actualizarDescargaInformes(result.getListaIdSolInfoOK(), result.getListaIdSolInfoError(), tramiteTrafSolInfoDto.getNumExpediente(), idUsuario);
		if(!resultActualizar.isError()){
			/*if(result.getListaIdSolInfoOK() != null && !result.getListaIdSolInfoOK().isEmpty()){
				result = guardarInformeInteve(result.getListaIdSolInfoOK(), tramiteTrafSolInfoDto.getNumExpediente());
			}
		} else {*/
			result.setError(Boolean.TRUE);
			result.setMensaje(resultActualizar.getMensaje());
		}
		return result;
	}

	private ResultInteveBean guardarInformeInteve(List<InformeInteveBean> listaInformes, BigDecimal  numExpediente) {
		ResultInteveBean resultado = new ResultInteveBean();
		resultado.setError(Boolean.TRUE);
		ZipOutputStream zip = null;
		FileOutputStream out = null;
		String url = null;
		try {
			String ruta = gestorDocumentos.obtenerRutaFichero(ConstantesGestorFicheros.SOLICITUD_INFORMACION, null, 
					null, utiles.transformExpedienteFecha(numExpediente));
			String nombreZip = "Consultas_" + numExpediente;
			url = ruta + nombreZip;
			out = new FileOutputStream(url);
			zip = new ZipOutputStream(out);
			for(InformeInteveBean informeInteveBean : listaInformes){
				ZipEntry zipEntry = new ZipEntry(informeInteveBean.getNombreFichero());
				zip.putNextEntry(zipEntry);
				zip.write(informeInteveBean.getFichero());
				zip.closeEntry();
			}
			
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de guardar los informes, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar los documento base.");
		} finally {
			try {
				zip.close();
			} catch (IOException e) {
				LOG.error("Ha sucedido un error a la hora de cerrar el zip, error: ",e);
			}
		}
		return resultado;
	}

	/**
	 * Actualiza el estado del tramite conforme a si se han recibido o no todos los informes solicitados
	 * 
	 * @param tramiteTrafSolInfoDto
	 * @param idUsuario
	 */
	@Override
	public void actualizarTramite(TramiteTrafSolInfoDto tramiteTrafSolInfoDto, BigDecimal idUsuario) {
		int recibidos = 0;
		for (SolicitudInformeVehiculoDto solicitudInforme : tramiteTrafSolInfoDto.getSolicitudes()) {
			if (EstadoTramiteSolicitudInformacion.Recibido.getValorEnum().equals(solicitudInforme.getEstado())) {
				recibidos++;
			}
		}
		if (tramiteTrafSolInfoDto.getSolicitudes().size() == recibidos) {
			// Todo recibido, finalizado ok
			tramiteTrafSolInfoDto.setEstado(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum());
		} else if (recibidos > 0) {
			// Finalizado parcial
			tramiteTrafSolInfoDto.setEstado(EstadoTramiteTrafico.Finalizado_Parcialmente.getValorEnum());
		} else{
			
			tramiteTrafSolInfoDto.setEstado(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum());
		}
		
		
		servicioTramiteTraficoSolInfo.actualizarTramiteConEvolucion(tramiteTrafSolInfoDto, EstadoTramiteTrafico.Pendiente_Envio, idUsuario);
	}
	
	
	public ResultInteveBean solicitarInforme(SolicitudInformeVehiculoDto solicitudInforme, String numColegiado) {
		ResultInteveBean resultBean = new ResultInteveBean();
		// ¿Están los parámetros requeridos?
		String mensajeParametros = parametrosObligatorios(solicitudInforme);
		if (!PARAMETROS_OK.equals(mensajeParametros)) {
			// No
			resultBean.setError(true);
			resultBean.setMensaje(mensajeParametros);
			return resultBean;
		} else {
			// Si
			resultBean = servicioInteveWeb.navegacionweb(solicitudInforme, numColegiado, resultBean);
		}
		return resultBean;
	}

	/**
	 * Guarda un zip con los documentos
	 * @param datos
	 * @param nombreFichero
	 * @throws Throwable
	 */
	private void guardaInforme(Map<String, byte[]> datos, String numExpediente) throws IOException, OegamExcepcion {
		LOG.trace("Entra en guardaInforme");

		FicheroBean fichero = new FicheroBean();
		fichero.setSobreescribir(false);
		fichero.setTipoDocumento(ConstantesGestorFicheros.SOLICITUD_INFORMACION);
		fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		fichero.setNombreZip("Consultas_" + numExpediente);
		fichero.setListaByte(datos);
		gestorDocumentos.empaquetarEnZipByte(fichero);
	}

	private String parametrosObligatorios(SolicitudInformeVehiculoDto solicitudInforme) {
		String respuesta = "Faltan los siguientes parámetros requeridos: ";
		boolean faltan = false;
		if (solicitudInforme.getTasa() == null || solicitudInforme.getTasa().getCodigoTasa() == null || solicitudInforme.getTasa().getCodigoTasa().isEmpty()) {
			respuesta += " tasa";
			faltan = true;
		}
		if ((solicitudInforme.getVehiculo().getMatricula() == null || solicitudInforme.getVehiculo().getMatricula().isEmpty())
				&& (solicitudInforme.getVehiculo().getBastidor() == null || solicitudInforme.getVehiculo().getBastidor().isEmpty())
				&& (solicitudInforme.getVehiculo().getNive() == null || solicitudInforme.getVehiculo().getNive().isEmpty())) {
			respuesta += " matricula";
			faltan = true;
		}
		if ((solicitudInforme.getMotivoInteve() == null || solicitudInforme.getMotivoInteve().isEmpty())) {
			respuesta += " motivo de la consulta";
			faltan = true;
		}
		if(solicitudInforme.getTipoInforme() == null || solicitudInforme.getTipoInforme().isEmpty()){
			respuesta += " tipo informe de la consulta";
			faltan = true;
		}
		if (faltan) {
			return respuesta;
		} else {
			return PARAMETROS_OK;
		}
	}

	@Override
	public byte[] descargarZip(byte[] bytesInforme) throws IOException {
		byte[] bytesReturn = null;
		String ficherosADescargas = "";
		ficherosADescargas = gestorPropiedades.valorPropertie(ConstantesInteve.REF_PROP_DESCARGA_INTEVE);

		// Leer el zip
		InputStream in = new ByteArrayInputStream(bytesInforme);
		ZipInputStream zis = new ZipInputStream(in);

		// Archivo temporal, el zip:
		OutputStream outPut = new ByteArrayOutputStream();
		ZipOutputStream zipOutput = new ZipOutputStream(outPut);

		ZipEntry entry = null;
		while ((entry = zis.getNextEntry()) != null) {
			boolean empaquetar = false;
			if (esDocumentoAntecentePDF(entry, ficherosADescargas)) {
				empaquetar = true;
			} else if (esJustificanteRegistroSalidaPDF(entry, ficherosADescargas)) {
				empaquetar = true;
			} else if (esJustificanteRegistroSalidaXML(entry, ficherosADescargas)) {
				empaquetar = true;
			}
			if (empaquetar) {

				ByteArrayOutputStream output = null;
				try {
					output = new ByteArrayOutputStream();
					int data = 0;
					while ((data = zis.read()) != -1) {
						output.write(data);
					}
					zipOutput.putNextEntry(entry);
					zipOutput.write(output.toByteArray());
					zipOutput.closeEntry();

				} catch (Exception e) {
					LOG.error("Error recuperando entradas antiguas", e);
				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (IOException ioe) {
							LOG.error("Error cerrando ByteArrayOutputStream", ioe);
						}
					}
				}

			}
		}
		zipOutput.flush();
		zipOutput.close();

		// Guarda los bytes del return:

		in.close();
		bytesReturn = ((ByteArrayOutputStream) outPut).toByteArray();
		outPut.close();
		return bytesReturn;

	}

	private boolean esJustificanteRegistroSalidaPDF(ZipEntry zip, String ficherosADescargas) {
		try {
			if (zip.getName().endsWith(PDF) && zip.getName().startsWith(JUSTIFICANTE_REGISTRO_SALIDA) && ficherosADescargas.contains(TYPE_2_JUSTIFICANTE_REGISTRO_SALIDA_PDF))
				return true;
		} catch (Exception e) {
			LOG.error("Error esJustificanteRegistroSalidaPDF: " + e);
		}
		return false;
	}

	private boolean esDocumentoAntecentePDF(ZipEntry zip, String ficherosADescargas) {
		try {
			if (zip.getName().endsWith(PDF) && zip.getName().startsWith(DOCUMENTO_ANTECEDENTE) && ficherosADescargas.contains(TYPE_1_DOCUMENTO_ANTECEDENTE_PDF))
				return true;
		} catch (Exception e) {
			LOG.error("Error esDocumentoAntecentePDF: " + e);
		}
		return false;
	}

	private boolean esJustificanteRegistroSalidaXML(ZipEntry zip, String ficherosADescargas) {
		try {
			if (zip.getName().endsWith(XML) && zip.getName().startsWith(JUSTIFICANTE_REGISTRO_SALIDA) && ficherosADescargas.contains(TYPE_3_JUSTIFICANTE_REGISTRO_SALIDA_XML))
				return true;
		} catch (Exception e) {
			LOG.error("Error esJustificanteRegistroSalidaXML: " + e);
		}
		return false;
	}
	
	@Override
	public ResultBean importarInteve(File fichero, BigDecimal idContrato, BigDecimal idUsuario) {
		//TODO en un futuro hay que cambiarlo a Hibernate
		ResultBean resultado = new ResultBean(false);
		try {
			List<String> lineasImportacion = utiles.obtenerLineasFicheroTexto(fichero);
			if(lineasImportacion != null && !lineasImportacion.isEmpty()){
				SolicitudDatosBean solicitud = crearSolicitudDatosBean(idContrato,idUsuario);
				Boolean esMatricula = false;
				Boolean esBastidor = false;
				Boolean esSolicitante = false;
				int lineasError = 0;
				int lineasOk = 0;
				ContratoVO contratoVO = servicioContrato.getContrato(idContrato);
				int contLineas = 0;
				for(String solImport : lineasImportacion){
					if(!esBastidor && !esMatricula && !esSolicitante){
						if(!"MATRICULA".equals(solImport) && !"BASTIDOR".equals(solImport)){
							IntervinienteTrafico solicitante = new IntervinienteTrafico();
							String mensaje = obtenerSolicitante(solImport,contratoVO,solicitante,contLineas);
							if(mensaje == null){
								if(solicitante != null){
									esSolicitante = true;
									solicitud.setSolicitante(solicitante);
								}
							}else{
								lineasError++;
								resultado.addMensajeALista(mensaje);
							}
						}else{
							if("MATRICULA".equals(solImport)){
								esMatricula = true;
							}else if("BASTIDOR".equals(solImport)){
								esBastidor = true;
							}else{
								resultado.setError(true);
								resultado.setMensaje("El fichero de importacion no tiene matriculas o bastidores que importar.");
								break;
							}
						}
					}else if(!esBastidor && !esMatricula){
						if("MATRICULA".equals(solImport)){
							esMatricula = true;
						}else if("BASTIDOR".equals(solImport)){
							esBastidor = true;
						}else{
							resultado.setError(true);
							resultado.setMensaje("El fichero de importacion no tiene matriculas o bastidores que importar.");
							break;
						}
					}else{
						SolicitudVehiculoBean solicitudVehiculo = new SolicitudVehiculoBean();
						String mensaje = rellenarSolicitudVehiculo(solImport,esMatricula,esBastidor,solicitudVehiculo,contLineas,idContrato);
						if(mensaje == null){
							solicitud.getSolicitudesVehiculos().add(solicitudVehiculo);
							lineasOk++;
						}else{
							lineasError++;
							resultado.addMensajeALista(mensaje);
						}
					}
					contLineas++;
				}
				if(!resultado.getError()){
					ResultBean resultNumSolMax = validarMaximoSolicitudes(solicitud);
					if(!resultNumSolMax.getError()){
						resultado.addAttachment("lineasError", lineasError);
						resultado.addAttachment("lineasOk", lineasOk);
						if(solicitud.getSolicitudesVehiculos() != null 
								&& !solicitud.getSolicitudesVehiculos().isEmpty() 
								&& solicitud.getSolicitudesVehiculos().size() > 0 ){
							HashMap<String, Object> resultGSol = getModeloSolicitudDatos().guardarSolicitudDatos(solicitud);
							ResultBean resultBean = (ResultBean) resultGSol.get(ConstantesPQ.RESULTBEAN);
							if (resultBean.getError()) {
								resultado.setError(true);
								resultado.setMensaje("Ha sucedido un error a la hora de guardar las solicitudes.");
							} else {
								resultado.setError(false);
								resultado.addAttachment("numExpediente", resultBean.getAttachment("numExpediente"));
								guardarTxtImportacion(fichero,idContrato,idUsuario);
							}
						}
					}else{
						resultado.setError(true);
						resultado.setMensaje(resultNumSolMax.getMensaje());
					}
				}
			}else{
				resultado.setError(true);
				resultado.setMensaje("El fichero no contiene datos que importar.");
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de importar el fichero de solicitud, error: ",e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de importar el fichero de solicitud");
		} catch (Throwable e) {
			LOG.error("Ha sucedido un error a la hora de leer las lineas del fichero de solicitud, error: ",e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de leer las lineas del fichero de solicitud");
		}
		return resultado;
	}

	private void guardarTxtImportacion(File fichero, BigDecimal idContrato, BigDecimal idUsuario) {
		// Gestor de Ficheros
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setFichero(fichero);
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.IMPORTACION);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.SUBTIPO_DOC_SOLICITUD);
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
		Fecha f = new Fecha(new Date());
		ficheroBean.setFecha(f);
		String fecha = f.getDia() + "_" + f.getHora() + f.getMinutos() + f.getSegundos();
		ficheroBean.setNombreDocumento(idUsuario + "_" + idContrato + "_" + fecha);
		try {
			gestorDocumentos.guardarFichero(ficheroBean);
		} catch (OegamExcepcion e) {
			LOG.error("Error al custodiar el importado TXT de Solicitud: " + idUsuario + "_" + idContrato + "_" + fecha);
		}
		
	}

	public boolean esSolicitudDeError(String mensaje) {
		if (mensaje.startsWith("- Error al guardar")){
			return true;
		} else {
			return false;
		}
	}
	
	public ResultBean validarMaximoSolicitudes(SolicitudDatosBean solicitud) {
		ResultBean resultado = new ResultBean(false);
		// Si está configurado AVPO o si el usuario NO es usuario de pruebas INTEVE
		// limitación de N solicitudes por trámite:
		int maximoNumSolicitudes = ConstantesTrafico.NUM_MAXIMO_SOLICITUDES_POR_DEFECTO;
		try {
			maximoNumSolicitudes = Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesTrafico.LIMITACION_NUMERO_SOLICITUDES));
		} catch (Exception e){
			LOG.error("No se puede recuperar el número máximo de solicitudes", e);
		}

		String servidor = UtilesVistaTrafico.getInstance().servidorInformesTecnicos(); 
		String usuarioPruebasInteve = UtilesVistaTrafico.getInstance().sesionUsuarioPruebasInteve();
		if((servidor == null || !servidor.equalsIgnoreCase("INTEVE")) &&
				(usuarioPruebasInteve == null || usuarioPruebasInteve.equalsIgnoreCase("NO"))){
			if (solicitud.getSolicitudesVehiculos()!= null && solicitud.getSolicitudesVehiculos().size()>= maximoNumSolicitudes){
				resultado.setError(true);
				resultado.setMensaje("No puede guardar más de " + maximoNumSolicitudes + " vehículos");
			}
		}
		return resultado;
	}

	public String rellenarSolicitudVehiculo(String solImport, Boolean esMatricula, Boolean esBastidor, SolicitudVehiculoBean solicitudVehiculo, int contLineas, BigDecimal idContrato) {
		String mensaje = null;
		String[] solInfoVeh = solImport.split(";");
		if(solInfoVeh.length == 4){
			mensaje = validarMotivo(solInfoVeh[2],contLineas);
			if(mensaje == null){
				mensaje = validarTipoInforme(solInfoVeh[3], contLineas);
				if(mensaje == null){
					solicitudVehiculo.setMotivoInteve(solInfoVeh[2]);
					solicitudVehiculo.setTipoInforme(solInfoVeh[3]);
					mensaje = validarTasa(solInfoVeh[1],contLineas,idContrato);
					if(mensaje == null){
						Tasa tasa = new Tasa();
						tasa.setCodigoTasa(solInfoVeh[1]);
						solicitudVehiculo.setTasa(tasa);
						VehiculoBean vehiculo = new VehiculoBean();
						if (esMatricula){
							vehiculo.setMatricula(solInfoVeh[0]);
						} else if (esBastidor) {
							vehiculo.setBastidor(solInfoVeh[0]);
						}
						solicitudVehiculo.setVehiculo(vehiculo);
					}
				}
			}
		}else{
			mensaje = "La linea " + contLineas + " del fichero no contiene los datos esperados.";
		}
		return mensaje;
	}

	public String validarTasa(String codTasa, int contLineas, BigDecimal idContrato) {
		String mensaje = null;
		if(codTasa == null || codTasa.isEmpty()){
			mensaje = "La linea " + contLineas + " del fichero no contiene una tasa para la solicitud.";
		}else if(codTasa.length() > 12){
			mensaje = "La linea " + contLineas + " del fichero no contiene una tasa con un formato valida para la solicitud.";
		}else{
			TasaDto tasaDto = servicioTasa.getTasaCodigoTasa(codTasa);
			if(tasaDto != null){
				if(tasaDto.getFechaAsignacion() != null){
					mensaje = "La linea " + contLineas + " del fichero contiene una tasa que ya esta asignada a otros trámites.";
				}else if(idContrato.compareTo(tasaDto.getContrato().getIdContrato()) != 0){
					mensaje = "La linea " + contLineas + " del fichero contiene una tasa que no esta asociada al contrato.";
				}else if(!TipoTasa.CuatroUno.getValorEnum().equals(tasaDto.getTipoTasa())){
					mensaje = "La linea " + contLineas + " del fichero no contiene una tasa del tipo correspondiente.";
				}
			}else{
				mensaje = "La linea " + contLineas + " del fichero no contiene una tasa valida para la solicitud.";
			}
		}
		return mensaje;
	}

	public String validarMotivo(String motivo, int contLineas) {
		String mensaje = null;
		if(motivo == null || motivo.isEmpty()){
			mensaje = "La linea " + contLineas + " del fichero no contiene el motivo de la solicitud.";
		}else if(!MotivoConsultaInteve.validarMotivo(motivo)){
			mensaje = "La linea " + contLineas + " del fichero no contiene un motivo valido para poder realizar la solicitud.";
		}
		return mensaje;
	}
	
	public String validarTipoInforme(String tipoInforme, int contLineas) {
		String mensaje = null;
		if(tipoInforme == null || tipoInforme.isEmpty()){
			mensaje = "La linea " + contLineas + " del fichero no contiene el tipo de informe de la solicitud.";
		}else if(!TipoInformeInteve.validarTipoInforme(tipoInforme)){
			mensaje = "La linea " + contLineas + " del fichero no contiene un tipo de informe valido para poder realizar la solicitud.";
		}
		return mensaje;
	}

	private SolicitudDatosBean crearSolicitudDatosBean(BigDecimal idContrato, BigDecimal idUsuario) {
		SolicitudDatosBean solicitud = new SolicitudDatosBean();
		List<SolicitudVehiculoBean> solicitudesVehiculos = new ArrayList<SolicitudVehiculoBean>();
		TramiteTraficoBean tramiteTrafico = new TramiteTraficoBean();
		tramiteTrafico.setIdContrato(idContrato);
		tramiteTrafico.setIdUsuario(idUsuario);
		solicitud.setTramiteTrafico(tramiteTrafico);
		solicitud.setSolicitudesVehiculos(solicitudesVehiculos);
		return solicitud;
	}

	public String obtenerSolicitante(String solImport, ContratoVO contratoVO, IntervinienteTrafico solicitante, int contLineas) {
		if (solImport != null) {
			try {
				// Si viene en ISO, tratar de convertir a UTF-8
				if (new String(solImport.getBytes(Claves.ENCODING_ISO88591), Claves.ENCODING_ISO88591).equals(solImport)) {
					solImport = new String(solImport.getBytes(Claves.ENCODING_ISO88591), Claves.ENCODING_UTF8);
				}
			} catch (UnsupportedEncodingException e) {
				LOG.error("Error tratando de convertir a UTF-8", e);
			}
			String[] token = solImport.split(";");
			if (token.length >= 2 && token.length <= 4) {
				String nif = token[0];
				// Si NIF no valido return null
				BigDecimal resultValida = NIFValidator.validarNif(nif);
				if (resultValida == null || resultValida.intValue() <= 0) {
					return "La linea " + contLineas + " del fichero no contiene Nif/Cif valida del solicitante para poder importar la solicitud.";
				}
				String apellido1RazonSocial = token[1];
				String apellido2 = null;
				String nombre = null;
				if (token.length > 2) {
					apellido2 = token[2];
					if (token.length > 3) {
						nombre = token[3];
					}
				}
				if(apellido1RazonSocial == null || apellido1RazonSocial.isEmpty()){
					return "La linea " + contLineas + " del fichero no contiene el apellido o razón social del solicitante para poder importar la solicitud.";
				}
				solicitante.setPersona(new Persona());
				solicitante.getPersona().setNif(nif);
				solicitante.getPersona().setApellido1RazonSocial(apellido1RazonSocial);
				solicitante.getPersona().setApellido2(apellido2);
				solicitante.getPersona().setNombre(nombre);
				solicitante.setTipoInterviniente(TipoInterviniente.Solicitante.getValorEnum());
				solicitante.setNumColegiado(contratoVO.getColegiado().getNumColegiado());
			}
		}
		return null;
	}
	
	@Override
	@Transactional
	public ResultBean solicitudInfoApp(BigDecimal numExpediente, BigDecimal idContrato, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			TramiteTrafSolInfoVO tramiteTrafSolInfoVO = servicioTramiteTraficoSolInfo.getTramiteTraficoSolInfoVO(numExpediente,Boolean.TRUE);
			resultado = validarSolInfoBean(tramiteTrafSolInfoVO);
			if(!resultado.getError()){
				for(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO : tramiteTrafSolInfoVO.getSolicitudes()){
					tramiteTrafSolInfoVehiculoVO.setEstado(new BigDecimal(EstadoTramiteSolicitudInformacion.Pendiente.getValorEnum()));
					resultado = servicioTramiteTraficoSolInfo.actualizarTramiteSolInfoVehiculo(tramiteTrafSolInfoVehiculoVO);
					if(resultado.getError()){
						break;
					}
				}
				if(!resultado.getError()){
					resultado = servicioTramiteTraficoSolInfo.actualizarTramiteVOConEvolucion(tramiteTrafSolInfoVO, EstadoTramiteTrafico.Pendiente_Respuesta_APP, idUsuario);
					if(!resultado.getError()){
						resultado = generarXmlSolInfoApp(tramiteTrafSolInfoVO,idContrato);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de generar las solicitudes de información, error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar las solicitudes de información.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	private ResultBean generarXmlSolInfoApp(TramiteTrafSolInfoVO tramiteTrafSolInfoVO, BigDecimal idContrato) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
//		try {
//			FormatoOegam2SolInfo formatoOegam2SolInfo = buildSolInformacionApp.obtenerFormatoOegam2SolInfo(tramiteTrafSolInfoVO, idContrato);
//			if(formatoOegam2SolInfo != null){
//				String xml = buildSolInformacionApp.generarXml(formatoOegam2SolInfo);
//				if(xml != null && !xml.isEmpty()){
//					try {
//						guardarDocumentos.guardarFichero(ConstantesGestorFicheros.SOLICITUD_INFORMACION, ConstantesGestorFicheros.APP_SOLINFO_EXPORTADO, 
//								Utilidades.transformExpedienteFecha(tramiteTrafSolInfoVO.getNumExpediente()),"Solicitud_Informacion_ "+ tramiteTrafSolInfoVO.getNumExpediente() , ConstantesGestorFicheros.EXTENSION_XML, xml.getBytes("UTF-8"));
//					} catch (OegamExcepcion e) {
//						LOG.error("Ha sucedido un error a la hora de guardar el xml generado, error: ",e);
//						resultado.setError(Boolean.TRUE);
//						resultado.setMensaje("Ha sucedido un error a la hora de guardar el xml generado.");
//					}
//				} else {
//					resultado.setError(Boolean.TRUE);
//					resultado.setMensaje("Ha sucedido un error a la hora de generar el xml para la aplicación.");
//				}
//			} else {
//				resultado.setError(Boolean.TRUE);
//				resultado.setMensaje("Ha sucedido un error a la hora de obtener el objeto raiz del xml para la aplicación.");
//			}
//		} catch (Exception e) {
//			LOG.error("Ha sucedido un error a la hora de generar el xml para la aplicación, error: ",e);
//			resultado.setError(Boolean.TRUE);
//			resultado.setMensaje("Ha sucedido un error a la hora de generar el xml para la aplicación.");
//		}
		return resultado;
	}

	private ResultBean validarSolInfoBean(TramiteTrafSolInfoVO tramiteTrafSolInfoVO) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		if(tramiteTrafSolInfoVO == null  
				|| tramiteTrafSolInfoVO.getSolicitudes() == null || tramiteTrafSolInfoVO.getSolicitudes().isEmpty()){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de rellenar los datos de alguna solicitud");
		} else {
			for(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO : tramiteTrafSolInfoVO.getSolicitudes()){
				if(tramiteTrafSolInfoVehiculoVO.getTasa() == null || tramiteTrafSolInfoVehiculoVO.getTasa().getCodigoTasa() == null 
						|| tramiteTrafSolInfoVehiculoVO.getTasa().getCodigoTasa().isEmpty()){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Todas las solicitudes de información deben de llevar una tasa asociada.");
					break;
				} else if(tramiteTrafSolInfoVehiculoVO.getVehiculo() == null || (tramiteTrafSolInfoVehiculoVO.getVehiculo().getMatricula() == null
						&& tramiteTrafSolInfoVehiculoVO.getVehiculo().getMatricula().isEmpty() && tramiteTrafSolInfoVehiculoVO.getVehiculo().getBastidor() == null
						&& tramiteTrafSolInfoVehiculoVO.getVehiculo().getBastidor().isEmpty())){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Para poder obtener el informe del vehiculo todas las solicitudes deben de llevar indicado la matricula o el bastidor.");
					break;
				} else if(tramiteTrafSolInfoVehiculoVO.getMotivoInteve() == null || tramiteTrafSolInfoVehiculoVO.getMotivoInteve().isEmpty()){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe de rellenar el motivo del informe.");
					break;
				} else if(tramiteTrafSolInfoVehiculoVO.getTipoInforme() == null || tramiteTrafSolInfoVehiculoVO.getTipoInforme().isEmpty()){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe de rellenar el tipo de informe.");
					break;
				}
			}
		}
		return resultado;
	}
	
	@Override
	public ResultBean descargarXmlApp(BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if(numExpediente != null){
				TramiteTrafSolInfoDto trafSolInfoDto = servicioTramiteTraficoSolInfo.getTramiteTraficoSolInfoDto(numExpediente);
				if(trafSolInfoDto != null){
					if(EstadoTramiteTrafico.Pendiente_Respuesta_APP.getValorEnum().equals(trafSolInfoDto.getEstado())){
						String nombreFichero =  "Solicitud_Informacion_ "+ trafSolInfoDto.getNumExpediente();
						FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.SOLICITUD_INFORMACION, ConstantesGestorFicheros.APP_SOLINFO_EXPORTADO, 
								Utilidades.transformExpedienteFecha(trafSolInfoDto.getNumExpediente()),nombreFichero , ConstantesGestorFicheros.EXTENSION_XML);
						if(fichero != null && fichero.getFile() != null){
							resultado.addAttachment("ficheroXml", fichero.getFile());
							resultado.addAttachment("nombreFicheroXml", nombreFichero);
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No existe el xml.");
						}
					} else{
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("El tramite no se encuentra " + EstadoTramiteTrafico.Pendiente_Respuesta_APP.getNombreEnum() + " por lo que no se podrá descargar el xml.");
					}
				} else{
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos del tramite para poder descargar el xml.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede obtener el xml de la solicitud.");
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error al descargar el xml generado para la aplicación, error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error al descargar el xml generado para la aplicación.");
		} catch (OegamExcepcion e) {
			LOG.error("Ha sucedido un error al descargar el xml generado para la aplicación, error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error al descargar el xml generado para la aplicación.");
		}
		return resultado;
	}

	public ModeloSolicitudDatos getModeloSolicitudDatos() {
		if (modeloSolicitudDatos == null) {
			modeloSolicitudDatos = new ModeloSolicitudDatos();
		}
		return modeloSolicitudDatos;
	}

	@Override
	public void actualizarTramiteConEstado(TramiteTrafSolInfoDto tramiteTrafSolInfoDto, BigDecimal idUsuario,
			String estado) {
		String estadoAnterior = tramiteTrafSolInfoDto.getEstado();
		tramiteTrafSolInfoDto.setEstado(estado);
		servicioTramiteTraficoSolInfo.actualizarTramiteConEvolucion(tramiteTrafSolInfoDto, EstadoTramiteTrafico.convertir(estadoAnterior), idUsuario);
	}


	
}
