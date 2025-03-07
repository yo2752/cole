package trafico.servicio.implementacion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.dao.MatriculaVehiculoDaoInt;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import oegam.constantes.ConstantesSession;
import trafico.modelo.impl.ModeloImprimirImpl;
import trafico.utiles.PdfMaker;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ImprimirTramitesServicio {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImprimirTramitesServicio.class);
	private static final String RUTA_ARCHIVOS_TEMP = "RUTA_ARCHIVOS_TEMP";
	private static final String ERROR_GENERANDO_DOCUMENTO = "Error generando documento de impresión masivo por proceso";

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private MatriculaVehiculoDaoInt matriculaVehiculoDao;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ResultBean recuperarDocumentosOficiales(String[] numExpedientes, String tipo, String tipoImpresion) {
		ResultBean resultadoMetodo = new ResultBean();
		byte[] byte1 = null;
		File fichero = null;
		String subtipo = null;
		String anadidoNombre = "";
		String extension = ConstantesGestorFicheros.EXTENSION_PDF;
		String url ="";
		ZipOutputStream zip = null;
		FileOutputStream out = null;

		if (tipoImpresion.equals(TipoImpreso.TransmisionDocumentosTelematicos.toString())) {
			String[] expedientes = new String[numExpedientes.length*2]; // Multiplico por 2 porque se necesita sacar el informe y el PTC
			for (int j = 0; j < numExpedientes.length; j++) {
				expedientes[j*2] = numExpedientes[j];
				expedientes[j*2+1] = "i_"+numExpedientes[j];
			}
			numExpedientes = expedientes;
		}
		if (numExpedientes.length > 1) {
			try {
				url = gestorPropiedades.valorPropertie(RUTA_ARCHIVOS_TEMP)+"zip"+System.currentTimeMillis();
				out = new FileOutputStream(url);
				zip = new ZipOutputStream(out);
				for (int i = 0; i < numExpedientes.length; i++) {
					if (tipoImpresion.equals(TipoImpreso.MatriculacionPermisoTemporalCirculacion.toString())) {
						subtipo = ConstantesGestorFicheros.PTC;
					} else if (tipoImpresion.equals(TipoImpreso.FichaTecnica.toString())) {
						subtipo = ConstantesGestorFicheros.FICHATECNICA;
						anadidoNombre="_"+ConstantesGestorFicheros.NOMBRE_FICHATECNICA;
					} else if (tipoImpresion.equals(TipoImpreso.PDFJustificantePresentacion576.toString())) {
						anadidoNombre="_"+ConstantesGestorFicheros.TIPO576;
					} else if (tipoImpresion.equals(TipoImpreso.TransmisionDocumentosTelematicos.toString())) {
						subtipo = numExpedientes[i].contains("_") ? ConstantesGestorFicheros.INFORMES : ConstantesGestorFicheros.PTC;
					} else if (tipoImpresion.equals(TipoImpreso.SolicitudAvpo.toString()) || tipoImpresion.equals(TipoImpreso.SolicitudATEM.toString())
							|| tipoImpresion.equals(TipoImpreso.SolicitudAvpoError.toString())) {
						extension = ConstantesGestorFicheros.EXTENSION_ZIP;
						// Si se le añade algo al nombre por delante si no lleva '_' va a dar problemas
						numExpedientes[i] = ConstantesGestorFicheros.NOMBRE_CONSULTA+numExpedientes[i];
					}

					Fecha fecha;
					fecha = Utilidades.transformExpedienteFecha(numExpedientes[i].contains("_") ? numExpedientes[i].split("_")[1] : numExpedientes[i]);

					FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(tipo, subtipo, fecha,
							numExpedientes[i]+anadidoNombre, extension);

					if (fileResultBean.getFile() != null && fileResultBean.getFile().exists()) {
						FileInputStream is = new FileInputStream(fileResultBean.getFile());
						// Mantis 14386. David Sierra: Se modifica el nombre del fichero agregando la matrícula
						// en función del número de colegiado del mismo (los colegiados se recuperan por properties de BBDD)
						String nombreFichero = fileResultBean.getFile().getName();
						// Ejecución método recuperación
						String resultadoConsulta = getNombreFichero(nombreFichero, subtipo);
						// Comprobación respuesta
						if (null != resultadoConsulta) {
							nombreFichero = resultadoConsulta;
						}
						// Se agrega al zip el nombre del fichero con su extensión
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
						resultadoMetodo.setError(true);
						List<String> listaMensajes = resultadoMetodo.getListaMensajes();
						listaMensajes.add("No se ha podido recuperar el documento tipo "+tipoImpresion+" para el número Expediente "+numExpedientes[i] + ". " + fileResultBean.getMessage());
						resultadoMetodo.setListaMensajes(listaMensajes);
					} else {
						resultadoMetodo.setError(true);
						List<String> listaMensajes = resultadoMetodo.getListaMensajes();
						listaMensajes.add("No se ha podido recuperar el documento tipo "+tipoImpresion+" para el número Expediente "+numExpedientes[i]);
						resultadoMetodo.setListaMensajes(listaMensajes);
					}

				}
				zip.close();
				fichero = new File(url);

				resultadoMetodo.addAttachment(ResultBean.NOMBRE_FICHERO, tipoImpresion+ConstantesGestorFicheros.EXTENSION_ZIP);

				byte1 = gestorDocumentos.transformFiletoByte(fichero);

				if (fichero.delete()) {
					log.info("Se ha eliminado correctamente el fichero");
				} else {
					log.info("No se ha eliminado el fichero");
				}
			} catch (OegamExcepcion e) {
				log.error(e);
				resultadoMetodo.setError(true);
				List<String> listaMensajes = resultadoMetodo.getListaMensajes();
				listaMensajes.add(e.getMessage());
				resultadoMetodo.setListaMensajes(listaMensajes);
			} catch (FileNotFoundException e) {
				log.error(e);
				resultadoMetodo.setError(true);
				List<String> listaMensajes = resultadoMetodo.getListaMensajes();
				listaMensajes.add("Error al guardar los archivos en zip");
				resultadoMetodo.setListaMensajes(listaMensajes);
			} catch (IOException e) {
				log.error(e);
				resultadoMetodo.setError(true);
				List<String> listaMensajes = resultadoMetodo.getListaMensajes();
				listaMensajes.add("Error al guardar los archivos en zip");
				resultadoMetodo.setListaMensajes(listaMensajes);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						log.error("No se pudo cerrar outputstream", e);
					}
				}
				File eliminarZip = new File(url);
				eliminarZip.delete();
			}
		} else {
			String nombre=tipoImpresion+ConstantesGestorFicheros.EXTENSION_PDF;
			if (tipoImpresion.equals(TipoImpreso.MatriculacionPermisoTemporalCirculacion.toString())) {
				subtipo = ConstantesGestorFicheros.PTC;
			} else if (tipoImpresion.equals(TipoImpreso.FichaTecnica.toString())) {
				subtipo = ConstantesGestorFicheros.FICHATECNICA;
				anadidoNombre="_"+ConstantesGestorFicheros.NOMBRE_FICHATECNICA;
			} else if (tipoImpresion.equals(TipoImpreso.PDFJustificantePresentacion576.toString())) {
				anadidoNombre="_"+ConstantesGestorFicheros.TIPO576;
			} else if (tipoImpresion.equals(TipoImpreso.TransmisionDocumentosTelematicos.toString())) {
				subtipo = numExpedientes[0].contains("_") ? ConstantesGestorFicheros.INFORMES : ConstantesGestorFicheros.PTC;
			} else if (tipoImpresion.equals(TipoImpreso.SolicitudAvpo.toString()) || tipoImpresion.equals(TipoImpreso.SolicitudATEM.toString())
					|| tipoImpresion.equals(TipoImpreso.SolicitudAvpoError.toString())) {
				extension = ConstantesGestorFicheros.EXTENSION_ZIP;
				// Si se le añade algo al nombre por delante si no lleva '_' va a dar problemas
				for (int k = 0; k < numExpedientes.length; k++) {
					numExpedientes[k] = ConstantesGestorFicheros.NOMBRE_CONSULTA+numExpedientes[k];
				}
				nombre = tipoImpresion+ConstantesGestorFicheros.EXTENSION_ZIP;
			}

			Fecha fecha;
			fecha = Utilidades.transformExpedienteFecha(numExpedientes[0].contains("_") ? numExpedientes[0].split("_")[1] : numExpedientes[0]);

			try {
				FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(tipo, subtipo, fecha,
						numExpedientes[0]+anadidoNombre, extension);
				if (fileResultBean.getFile() != null) {
					byte1 = gestorDocumentos.transformFiletoByte(fileResultBean.getFile());
					resultadoMetodo.addAttachment(ResultBean.NOMBRE_FICHERO, nombre);
				} else if (FileResultStatus.ON_DEMAND_FILE.equals(fileResultBean.getStatus())) {
					resultadoMetodo.setError(true);
					List<String> listaMensajes = resultadoMetodo.getListaMensajes();
					listaMensajes.add("No se ha podido recuperar el documento tipo "+tipoImpresion+" para el numero Expediente "+numExpedientes[0] + ". " + fileResultBean.getMessage());
					resultadoMetodo.setListaMensajes(listaMensajes);
				} else {
					resultadoMetodo.setError(true);
					List<String> listaMensajes = resultadoMetodo.getListaMensajes();
					listaMensajes.add("No se ha podido recuperar el documento tipo "+tipoImpresion+" para el numero Expediente "+numExpedientes[0]);
					resultadoMetodo.setListaMensajes(listaMensajes);
				}
			} catch (OegamExcepcion e) {
				log.error("Se ha producido un error al imprimir", e);
			} catch (IOException e) {
				log.error("Se ha producido un error al imprimir", e);
			}
		}

		if (byte1 != null) {
			resultadoMetodo.setError(false);
			//resultadoMetodo.setMensaje("");
			resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, byte1);
		} else {
			resultadoMetodo.setError(true);
		}

		return resultadoMetodo;
	}

	public ResultBean encriptarPDF(ResultBean result){
		if (result != null && result.getAttachment(ResultBean.TIPO_PDF) != null) {
			result.addAttachment(ResultBean.TIPO_PDF, encriptarPDF((byte[])result.getAttachment(ResultBean.TIPO_PDF)));
		}
		return result;
	}

	public byte[] encriptarPDF(byte[] archivo){
		return PdfMaker.encriptarPdf(archivo, "", "", true, false, false, false, false);
	}

	private String crearFicheroProceso(String[] numExpedientes, String tipoImpreso, TipoTramiteTrafico tipoTramite) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// Modificar
		String fecha = utilesFecha.formatoFecha("ddMMyyyyHHmmss", new Date());
		String nombreFicheroAEnviar = "ImprimirMasivo_" + fecha;

		String tImpreso = tipoImpreso + "\n";
		String tTramite = tipoTramite.getNombreEnum() + "\n";
		String numColegiado = utilesColegiado.getNumColegiadoSession() + "\n";
		try {
			bos.write(tImpreso.getBytes());
			bos.write(tTramite.getBytes());
			bos.write(numColegiado.getBytes());
			// Recorremos lista de trámites
			for (int i = 0; i < numExpedientes.length; i++) {
				String linea = numExpedientes[i] + "\n";
				bos.write(linea.getBytes());
			}
			guardarFicheroProceso(bos.toByteArray(), nombreFicheroAEnviar, ConstantesGestorFicheros.IMPRESION_MASIVA,
					ConstantesGestorFicheros.PETICIONES, ConstantesGestorFicheros.EXTENSION_TXT);
			return nombreFicheroAEnviar;
		} catch (IOException e) {
			log.error(ERROR_GENERANDO_DOCUMENTO);
			log.error(e);
		} catch (Exception e) {
			log.error(ERROR_GENERANDO_DOCUMENTO);
			log.error(e);
		} catch (OegamExcepcion e) {
			log.error(ERROR_GENERANDO_DOCUMENTO);
			log.error(e);
		}
		return null;
	}

	private boolean crearSolicitudProceso(String nombreFicheroAEnviar) {
		ModeloImprimirImpl modeloImprimirImpl = new ModeloImprimirImpl();
		ResultBean resultBean;
		// Modificada
		try {
			String fecha = utilesFecha.formatoFecha("ddMMyyHHmmss", new Date());
			String idTramite = utilesColegiado.getNumColegiadoSession() + fecha;
			resultBean = modeloImprimirImpl.crearSolicitud(nombreFicheroAEnviar, ConstantesSession.TIPO_TRAMITE_IMPRESION_MASIVA, idTramite);
			if (resultBean != null && !resultBean.getError()) {
				return true;
			}
		} catch (OegamExcepcion e) {
			log.error("Error creando la solicitud");
			log.error(e);
		}
		return false;
	}

	public ResultBean imprimirEnProceso(String[] numExpedientes, String tipoImpreso, TipoTramiteTrafico tipoTramite) {
		ResultBean result = new ResultBean();
		String nombreFichero = crearFicheroProceso(numExpedientes, tipoImpreso, tipoTramite);
		// Modificar
		if (!crearSolicitudProceso(nombreFichero)) {
			result.setError(true);
			result.addMensajeALista("No se ha podido crear la llamada al proceso");
		}
		return result;
	}

	public ResultBean imprimirEnProceso417(String[] numExpedientes, TipoTramiteTrafico tipoTramite) {
		ResultBean result = new ResultBean();
		String nombreFichero = crearFicheroProceso(numExpedientes, TipoImpreso.MatriculacionPDF417.toString(), tipoTramite);
		boolean solicitudCreada = crearSolicitudProceso(nombreFichero);
		// Modificar
		if (!solicitudCreada) {
			result.setError(true);
			result.addMensajeALista("No se ha podido crear la llamada al proceso");
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
	// Mantis 14386. David Sierra: Comprobación colegiados autorizados para incluir la matrícula en los nombres de los ficheros
	private String getNombreFichero(String numExpedienteFicheroExtension, String subtipo) throws OegamExcepcion {
		String nombreFichero = null;
		String numExpedienteFichero = null;
		// Se realiza un substring del número de expediente eliminando la extensión del fichero
		if (ConstantesGestorFicheros.PTC.equals(subtipo)) {
			numExpedienteFichero = numExpedienteFicheroExtension.substring(0, numExpedienteFicheroExtension.indexOf("."));
		} else { // Mantis 16060. David Sierra: Para el resto de números de expediente porque no sabemos si vienen con guiones o no en el nombre
			numExpedienteFichero = numExpedienteFicheroExtension.substring(0, 15);
		}
		// Recuperación de los colegiados por properties de BBDD
		String[] colegiadosAutorizados = gestorPropiedades.valorPropertie("colegiados.impresion.ptc").split("\\,");

		for (int x = 0; x < colegiadosAutorizados.length; x++) {
			// Si coincide el colegiado con el inicio del número de expediente y el suptipo es PTC
			if (colegiadosAutorizados[x].equals(numExpedienteFichero.substring(0, 4))
					&& subtipo.equals(ConstantesGestorFicheros.PTC)) {
				// Se recupera el nombre con la matrícula asociada al expediente
				nombreFichero = getMatriculaExpediente(numExpedienteFichero, subtipo);
			}
			// Mantis 16060. Si coincide el colegiado con el inicio del número de expediente y el suptipo es FICHATECNICA
			else if (colegiadosAutorizados[x].equals(numExpedienteFichero.substring(0,4)) && subtipo.equals(ConstantesGestorFicheros.FICHATECNICA)) {
				nombreFichero = getMatriculaExpediente(numExpedienteFichero, subtipo);
			// No coincide. No se realiza ninguna acción sobre el número de expediente
			} else {
				nombreFichero = null;
			}
		}
		return nombreFichero;
	}

	/**
	 * Devuelve en el nombe del fichero la matrícula asociada al expediente
	 * @param numExpediente
	 * @param subtipo
	 * @return String
	 * @throws OegamExcepcion
	 */
	// Mantis 14386. David Sierra: Se realizan las modificaciones del nombre de fichero tras recuperar de BBDD la 
	// matrícula asociada al expediente pasado como parámetro
	private String getMatriculaExpediente(String numExpediente, String subtipo) throws OegamExcepcion {
		// Conversión de String a Long ya que en la entidad numExpediente es de tipo Long
		Long numExpedienteLargo = Long.parseLong(numExpediente);
		// Recuperación consulta del DAO
		List<Object[]> listaMatriculas = matriculaVehiculoDao.getMatriculaExpedienteDao(numExpedienteLargo);
		String codigo = null;

		for (int i = 0; i < listaMatriculas.size(); i++) {
			// Si no se devuelven matrículas no se asigna al nombre del fichero la matrícula
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

	/**
	 * Se guarda el 417 para las solicitudes masivas
	 * @param bytesFichero
	 * @param nombreFichero
	 * @param tipoDocumento
	 * @param subTipo
	 * @param extension
	 * @throws Exception
	 * @throws IOException
	 * @throws OegamExcepcion
	 */
	public File guardarFicheroProceso(byte[] bytesFichero, String nombreFichero, String tipoDocumento, String subTipo, String extension) throws Exception, IOException, OegamExcepcion {
		log.info("Guardar Documentos del Proceso impresión masiva");

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

}