package trafico.servicio.implementacion;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.core.trafico.justificante.profesional.model.dao.JustificanteProfDao;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import trafico.modelo.ModeloJustificanteProfesional;
import trafico.servicio.interfaz.ServicioJustificanteProInt;
import trafico.utiles.ConstantesPDF;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * @author ext_ihgl
 *
 */
@Service
@Transactional(readOnly = true)
public class ServicioJustificanteProImpl implements ServicioJustificanteProInt {

	private static final long serialVersionUID = 2949286468952507092L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioJustificanteProImpl.class);

	@Autowired
	private JustificanteProfDao justificanteProfDaoImpl;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	protected static final String RUTA_ARCHIVOS_TEMP = "RUTA_ARCHIVOS_TEMP";

	@Autowired
	private GestorDocumentos gestorDocumentos;

	// INICIO MANTIS 0011927: impresión de justificantes profesionales

	private ModeloJustificanteProfesional modeloJustificanteProfesional;

	public ModeloJustificanteProfesional getModeloJustificanteProfesional() {
		if (modeloJustificanteProfesional == null) {
			modeloJustificanteProfesional = new ModeloJustificanteProfesional();
		}
		return modeloJustificanteProfesional;
	}

	public void setModeloJustificanteProfesional(ModeloJustificanteProfesional modeloJustificanteProfesional) {
		this.modeloJustificanteProfesional = modeloJustificanteProfesional;
	}

	/**
	 * @see trafico.servicio.interfaz.ServicioJustificanteProInt#obtenerExpedientesSinJustificantesEnEstado(java.lang.String[], trafico.utiles.enumerados.EstadoJustificante)
	 */
	@Override
	public List<String> obtenerExpedientesSinJustificantesEnEstado(String[] listaNumExpedientes, EstadoJustificante estadoJustificantes) {
		List<String> listaNumExpedientesSinJustificantes = new ArrayList<>();

		if (listaNumExpedientes != null) {
			for (String numExpediente : listaNumExpedientes) {
				if (!getModeloJustificanteProfesional().hayJustificantesEnEstado(numExpediente, estadoJustificantes)) {
					listaNumExpedientesSinJustificantes.add(numExpediente);
				}
			}
		}

		return listaNumExpedientesSinJustificantes;
	}

	/**
	 * @see trafico.servicio.interfaz.ServicioJustificanteProInt#imprimirJustificantes(java.lang.String[])
	 */
	@Override
	public ByteArrayInputStreamBean imprimirJustificantes(String[] listaNumExpedientes) {
		FileResultBean fileResultBean = new FileResultBean();

		/* MÁS DE UN EXPEDIENTE: HACEMOS UN ZIP CON LOS JUSTIFICANTES DE CADA UNO */

		if (listaNumExpedientes.length > 1) {
			FileOutputStream out = null;
			ZipOutputStream zip = null;

			try {
				List<File> files = new ArrayList<>();

				String url = gestorPropiedades.valorPropertie(RUTA_ARCHIVOS_TEMP) + System.currentTimeMillis() + ".zip";
				File ficheroDestino = new File(url);
				out = new FileOutputStream(ficheroDestino);
				zip = new ZipOutputStream(out);
				int numExpedientesZip = 0;

				// Obtenemos y empaquetamos los justificantes de cada expediente
				for (String numExpediente : listaNumExpedientes) {
					List<File> filesAux = obtenerJustificantes(numExpediente);

					FileResultBean fileResultBeanAux = crearFileResultBeanFromFiles(numExpediente, filesAux);

					if (!FileResultStatus.FILE_NOT_FOUND.equals(fileResultBeanAux.getStatus())) {
						addZipEntryFromFile(zip, fileResultBeanAux.getFile());
						numExpedientesZip++;
						files.add(fileResultBeanAux.getFile());
					}
				}

				// Informamos el estado
				if (numExpedientesZip == 0) {
					fileResultBean.setStatus(FileResultStatus.FILE_NOT_FOUND);
				} else if (numExpedientesZip == 1) {
					fileResultBean.setStatus(FileResultStatus.UNIQUE_FILE_FOUND);
				} else {
					fileResultBean.setStatus(FileResultStatus.LIST_FILE_FOUND);
				}
				fileResultBean.setFile(ficheroDestino);
				fileResultBean.setFiles(files);

			} catch (FileNotFoundException e) {
				log.error(e);
			} finally {
				cerrarZip(out, zip);
			}

		/* UN EXPEDIENTE: DEVOLVEMOS LOS JUSTIFICANTES DEL EXPEDIENTE RECIBIDO (EN FORMA DE PDF O DE ZIP, SEGÚN SU NÚMERO) */
		} else if (listaNumExpedientes.length == 1) {
			String numExpediente = listaNumExpedientes[0];
			List<File> files = obtenerJustificantes(numExpediente);

			// Creamos el FileResultBean a partir de la lista de ficheros
			fileResultBean = crearFileResultBeanFromFiles(numExpediente, files);
		}

		// TRANSFORMACIÓN A ByteArrayInputStreamBean
		ByteArrayInputStreamBean byteArrayInputStreamBean = mapFromFileResultBeanToByteArrayInputStreamBean(fileResultBean);

		// BORRADO DE FICHEROS TEMPORALES
		borrarFicherosZipTemporales(fileResultBean);

		return byteArrayInputStreamBean;
	}

	/**
	 * Mapeo de un FileResultBean a un ByteArrayInputStreamBean.
	 *
	 * @param fileResultBean FileResultBean
	 * @return ByteArrayInputStreamBean - ByteArrayInputStreamBean.
	 */
	private ByteArrayInputStreamBean mapFromFileResultBeanToByteArrayInputStreamBean(FileResultBean fileResultBean) {
		ByteArrayInputStreamBean byteArrayInputStreamBean = null;

		if (fileResultBean != null) {
			byteArrayInputStreamBean = new ByteArrayInputStreamBean();
			byteArrayInputStreamBean.setByteArrayInputStream(transformFiletoByteArrayInputStream(fileResultBean.getFile()));
			byteArrayInputStreamBean.setStatus(fileResultBean.getStatus());
			byteArrayInputStreamBean.setMessage(fileResultBean.getMessage());
			if (FileResultStatus.LIST_FILE_FOUND.equals(fileResultBean.getStatus())) {
				byteArrayInputStreamBean.setFileName(ConstantesGestorFicheros.JUSTIFICANTES+"_"+System.currentTimeMillis() + ConstantesPDF.EXTENSION_ZIP);
			} else if (FileResultStatus.UNIQUE_FILE_FOUND.equals(fileResultBean.getStatus())) {
				byteArrayInputStreamBean.setFileName(ConstantesGestorFicheros.JUSTIFICANTES+"_"+System.currentTimeMillis() + ConstantesPDF.EXTENSION_PDF);
			}
		}
		return byteArrayInputStreamBean;
	}

	/**
	 * Transforma un File en un ByteArrayInputStream.
	 *
	 * @param file
	 * @return ByteArrayInputStream - ByteArrayInputStream.
	 * @throws IOException
	 */
	private ByteArrayInputStream transformFiletoByteArrayInputStream(File file) {
		ByteArrayInputStream byteArrayInputStream = null;
		try {
			if (file != null) {
				byte[] resultado = new byte[(int)file.length()];
				FileInputStream docu = new FileInputStream(file);
				docu.read(resultado);
				docu.close();
				byteArrayInputStream = new ByteArrayInputStream(resultado);
			}
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return byteArrayInputStream;
	}

	/**
	 * Borra el fichero recibido y sus ficheros asociados.
	 * 
	 * @param fileResultBean
	 */
	private void borrarFicherosZipTemporales(FileResultBean fileResultBean) {
		if (fileResultBean != null) {
			// Borrado del fichero
			if (fileResultBean.getFile() != null) {
				String extension = obtenerExtensionFichero(fileResultBean.getFile());
				// Solo lo borramos si es .zip
				if (".zip".equalsIgnoreCase(extension)) {
					fileResultBean.getFile().delete();
				}
			}

			// Borrado de ficheros asociados
			if (fileResultBean.getFiles() != null) {
				for (File file : fileResultBean.getFiles()) {
					String extension = obtenerExtensionFichero(file);
					// Solo lo borramos si es .zip
					if (".zip".equalsIgnoreCase(extension)) {
						file.delete();
					}
				}
			}
		}
	}

	/**
	 * Obtiene la extensión de un fichero.
	 * 
	 * @param file file.
	 * @return String - extensión.
	 */
	private String obtenerExtensionFichero(File file) {
		String extension = null;
		if (file != null && file.getPath() != null) {
			extension = file.getPath().substring(file.getPath().lastIndexOf("."));
		}
		return extension;
	}

	/**
	 * Crea un FileResultBean a partir de una lista de ficheros.
	 *
	 * @param numExpediente número de expediente al que están asociados los ficheros.
	 * @param files lista de ficheros.
	 * @return FileResultBean - FileResultBean.
	 */
	private FileResultBean crearFileResultBeanFromFiles(String numExpediente, List<File> files) {
		FileResultBean fileResultBean = new FileResultBean();

		if (files == null || files.isEmpty()) {
			// No se ha encontrado ningún fichero
			fileResultBean.setStatus(FileResultStatus.FILE_NOT_FOUND);

		} else if (files.size() > 1) {
			// Se han encontrado varios justificantes, se crea un zip con ellos

			FileOutputStream out = null;
			ZipOutputStream zip = null;

			try {
				String url = gestorPropiedades.valorPropertie(RUTA_ARCHIVOS_TEMP) + numExpediente + "_" + System.currentTimeMillis() + ".zip";
				File ficheroDestino = new File(url);
				out = new FileOutputStream(ficheroDestino);
				zip = new ZipOutputStream(out);

				// Por cada justificante, creamos una entrada en el zip
				for (File file : files) {
					addZipEntryFromFile(zip, file);
				}

				fileResultBean.setStatus(FileResultStatus.LIST_FILE_FOUND);
				fileResultBean.setFile(ficheroDestino);
				fileResultBean.setFiles(files);

			} catch (FileNotFoundException e) {
				log.error("Error al recuperar el Justificante para el Expediente:" + numExpediente, e, numExpediente);
			} finally {
				cerrarZip(out, zip);
			}
		} else {
			// Se ha encontrado un único fichero
			fileResultBean.setStatus(FileResultStatus.UNIQUE_FILE_FOUND);
			fileResultBean.setFile(files.iterator().next());
		}

		return fileResultBean;
	}

	/**
	 * Método para cerrar un zip.
	 * 
	 * @param out FileOutputStream.
	 * @param zip ZipOutputStream.
	 */
	private void cerrarZip(FileOutputStream out, ZipOutputStream zip) {
		if (zip != null) {
			try {
				zip.close();
			} catch (IOException e) {
				log.error("Error cerrando ZipOutputStream", e);
			}
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				log.error("Error cerrando FileOutputStream", e);
			}
		}
	}

	/**
	 * 
	 * @param zip
	 * @param file
	 */
	private void addZipEntryFromFile(ZipOutputStream zip, File file) {
		if (file != null && file.exists()) {
			FileInputStream is = null;
			try {
				is = new FileInputStream(file);
				ZipEntry zipEntry = new ZipEntry(file.getName());
				zip.putNextEntry(zipEntry);
				byte[] buffer = new byte[2048];
				int byteCount;
				while (-1 != (byteCount = is.read(buffer))) {
					zip.write(buffer, 0, byteCount);
				}
				zip.closeEntry();
				if (file.lastModified() > 0) {
					zipEntry.setTime(file.lastModified());
				}
			} catch (IOException e) {
				log.error("Error al añadir una entrada al zip del fichero: " + file.getName(), e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						log.error("Error cerrando FileInputStream", e);
					}
				}
			}
		}
	}
	// FIN MANTIS 0011927

	private List<File> obtenerJustificantes(String numExpediente) {
		List<File> files = new ArrayList<>();

		// Primero se mira el número de justificantes generados que tiene.
		List<JustificanteProfVO> listJust = justificanteProfDaoImpl.getJustificante(null, new BigDecimal(numExpediente), null);

		if (listJust != null) {
			for (JustificanteProfVO justi : listJust) {
				String nombre = generarNombre(justi);
				nombre = numExpediente+"_"+nombre;
				try {
					FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.JUSTIFICANTES_RESPUESTA,
							Utilidades.transformExpedienteFecha(numExpediente), nombre, ConstantesGestorFicheros.EXTENSION_PDF);
					if (fileResultBean.getFile() == null || !fileResultBean.getFile().exists()) {
						fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.JUSTIFICANTES_RESPUESTA,
								Utilidades.transformExpedienteFecha(numExpediente), numExpediente, ConstantesGestorFicheros.EXTENSION_PDF);
					}
					if (fileResultBean.getFile() != null && fileResultBean.getFile().exists()) {
						files.add(fileResultBean.getFile());
					}
				} catch (OegamExcepcion e) {
					log.error("Error al recuperar el Justificante para el Expediente:" + numExpediente);
				}
			}
		}
		return files;
	}

	@Override
	public long getObtenerIdJustifcanteInternoPorIdJustificante(String idJustificante) {
		JustificanteProfVO justificanteProfVO = justificanteProfDaoImpl.getIdJustificanteInternoPorIdJustificante(idJustificante);
		return justificanteProfVO.getIdJustificanteInterno();
	}

	@Override
	public long getObtenerIdJustificanteInternoPorNumExp(String numExpediente, String fechaInicio) {
		List<JustificanteProfVO> listJustificanteProfVO = justificanteProfDaoImpl.getJustificante(null, new BigDecimal(numExpediente), null);

		Timestamp fecha = utilesFecha.DDMMAAAAToTimestamp(fechaInicio);
		long lIdJustificanteInterno = 0;

		for (JustificanteProfVO justificanteProfVO : listJustificanteProfVO) {
			if (justificanteProfVO.getIdJustificante() == null && justificanteProfVO.getFechaInicio().equals(fecha)) {
				lIdJustificanteInterno = justificanteProfVO.getIdJustificanteInterno();
				break;
			}
		}
		return lIdJustificanteInterno;
	}

	@Override
	public String generarNombre(JustificanteProfVO justificante) {
		String nombre = "";
		if (null != justificante.getIdJustificante()) {
			String anadido = "0000000000";
			String id = justificante.getIdJustificante().toString();
			nombre = anadido.substring(0, anadido.length()-id.length())+id;
		} else {
			nombre = justificante.getTramiteTrafico().getNumExpediente().toString();
		}
		return nombre;
	}

}