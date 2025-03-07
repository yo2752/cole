package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.impl;

import java.io.ByteArrayInputStream;
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

import org.gestoresmadrid.core.trafico.justificante.profesional.model.dao.JustificanteProfDao;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesionalImprimir;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import trafico.utiles.ConstantesPDF;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioJustificanteProfesionalImprimirImpl implements ServicioJustificanteProfesionalImprimir {

	private static final long serialVersionUID = 7764062153951659019L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioJustificanteProfesionalImprimirImpl.class);

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private JustificanteProfDao justificanteProfDao;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional(readOnly = true)
	public ByteArrayInputStreamBean imprimirJustificantes(Long[] listaIdJustificantesInternos) {
		FileResultBean fileResultBean = new FileResultBean();
		if (listaIdJustificantesInternos.length > 1) {
			FileOutputStream out = null;
			ZipOutputStream zip = null;
			try {
				List<File> files = new ArrayList<File>();

				String url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + System.currentTimeMillis() + ".zip";
				File ficheroDestino = new File(url);
				out = new FileOutputStream(ficheroDestino);
				zip = new ZipOutputStream(out);
				int idJustificantesInternosZip = 0;

				for (Long idJustificanteInterno : listaIdJustificantesInternos) {
					List<JustificanteProfVO> listJust = justificanteProfDao.getJustificante(idJustificanteInterno, null, null);
					if (listJust != null && listJust.size() > 0) {
						File filesAux = obtenerFileJustificantes(listJust.get(0));
						if (filesAux != null) {
							FileResultBean fileResultBeanAux = crearFileResultBeanFromFiles(listJust.get(0).getNumExpediente(), filesAux);
							if (!FileResultStatus.FILE_NOT_FOUND.equals(fileResultBeanAux.getStatus())) {
								addZipEntryFromFile(zip, fileResultBeanAux.getFile());
								idJustificantesInternosZip++;
								files.add(fileResultBeanAux.getFile());
							}
						}
					}
				}

				if (idJustificantesInternosZip == 0) {
					fileResultBean.setStatus(FileResultStatus.FILE_NOT_FOUND);
				} else if (idJustificantesInternosZip == 1) {
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
		} else if (listaIdJustificantesInternos.length == 1) {
			Long idJustificanteInterno = listaIdJustificantesInternos[0];
			List<JustificanteProfVO> listJust = justificanteProfDao.getJustificante(idJustificanteInterno, null, null);
			if (listJust != null && listJust.size() > 0) {
				File fileAux = obtenerFileJustificantes(listJust.get(0));
				if (fileAux != null) {
					fileResultBean = crearFileResultBeanFromFiles(listJust.get(0).getNumExpediente(), fileAux);
				} else {
					fileResultBean.setStatus(FileResultStatus.FILE_NOT_FOUND);
				}
			}
		}

		ByteArrayInputStreamBean byteArrayInputStreamBean = mapFromFileResultBeanToByteArrayInputStreamBean(fileResultBean);

		borrarFicherosZipTemporales(fileResultBean);

		return byteArrayInputStreamBean;
	}

	@Override
	@Transactional(readOnly = true)
	public ByteArrayInputStreamBean imprimirJustificantesNumExpedientes(String[] listaNumExpedientes) {
		FileResultBean fileResultBean = new FileResultBean();
		if (listaNumExpedientes.length > 1) {
			FileOutputStream out = null;
			ZipOutputStream zip = null;
			try {
				List<File> files = new ArrayList<File>();
				String url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + System.currentTimeMillis() + ".zip";
				File ficheroDestino = new File(url);
				out = new FileOutputStream(ficheroDestino);
				zip = new ZipOutputStream(out);
				int idJustificantesInternosZip = 0;
				for (String numExpediente : listaNumExpedientes) {
					List<JustificanteProfVO> listJust = justificanteProfDao.getJustificante(null, new BigDecimal(numExpediente), null);
					if (listJust != null && listJust.size() > 0) {
						for (JustificanteProfVO justificante : listJust) {
							File filesAux = obtenerFileJustificantes(justificante);
							if (filesAux != null) {
								FileResultBean fileResultBeanAux = crearFileResultBeanFromFiles(justificante.getNumExpediente(), filesAux);
								if (!FileResultStatus.FILE_NOT_FOUND.equals(fileResultBeanAux.getStatus())) {
									addZipEntryFromFile(zip, fileResultBeanAux.getFile());
									idJustificantesInternosZip++;
									files.add(fileResultBeanAux.getFile());
								}
							}
						}
					}
				}
				if (idJustificantesInternosZip == 0) {
					fileResultBean.setStatus(FileResultStatus.FILE_NOT_FOUND);
				} else if (idJustificantesInternosZip == 1) {
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
		} else if (listaNumExpedientes.length == 1) {
			String numExpediente = listaNumExpedientes[0];
			List<JustificanteProfVO> listJust = justificanteProfDao.getJustificante(null, new BigDecimal(numExpediente), null);
			if (listJust != null && listJust.size() > 0) {
				if (listJust.size() == 1) {
					File fileAux = obtenerFileJustificantes(listJust.get(0));
					if (fileAux != null) {
						fileResultBean = crearFileResultBeanFromFiles(listJust.get(0).getNumExpediente(), fileAux);
					} else {
						fileResultBean.setStatus(FileResultStatus.FILE_NOT_FOUND);
					}
				// Si existe mas de un justificante profesional para el mismo número de expediente
				// se crea un zip
				} else {
					ZipOutputStream zip = null;
					FileOutputStream out = null;
					int idJustificantesInternosZip = 0;
					try {
						List<File> files = new ArrayList<File>();
						String url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + System.currentTimeMillis() + ".zip";
						File ficheroDestino = new File(url);
						out = new FileOutputStream(ficheroDestino);
						zip = new ZipOutputStream(out);
						for (JustificanteProfVO justificante : listJust) {
							File filesAux = obtenerFileJustificantes(justificante);
							if (filesAux != null) {
								FileResultBean fileResultBeanAux = crearFileResultBeanFromFiles(justificante.getNumExpediente(), filesAux);
								if (!FileResultStatus.FILE_NOT_FOUND.equals(fileResultBeanAux.getStatus())) {
									addZipEntryFromFile(zip, fileResultBeanAux.getFile());
									idJustificantesInternosZip++;
									files.add(fileResultBeanAux.getFile());
								}
							}
						}
						if (idJustificantesInternosZip == 0) {
							fileResultBean.setStatus(FileResultStatus.FILE_NOT_FOUND);
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
				}
			}
		}
		ByteArrayInputStreamBean byteArrayInputStreamBean = mapFromFileResultBeanToByteArrayInputStreamBean(fileResultBean);
		borrarFicherosZipTemporales(fileResultBean);
		return byteArrayInputStreamBean;
	}

	@Override
	public File obtenerFileJustificantes(JustificanteProfVO justificante) {
		String nombre = generarNombre(justificante);
		Fecha fecha = null;
		try {

			if (justificante.getNumExpediente() != null) {
				fecha = Utilidades.transformExpedienteFecha(justificante.getNumExpediente());
			} else {
				fecha = utilesFecha.getFechaConDate(justificante.getFechaInicio());
			}

			FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.JUSTIFICANTES_RESPUESTA, fecha, nombre,
					ConstantesGestorFicheros.EXTENSION_PDF);
			if (fileResultBean.getFile() == null || !fileResultBean.getFile().exists()) {
				fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.JUSTIFICANTES_RESPUESTA, fecha, justificante
						.getNumExpediente().toString(), ConstantesGestorFicheros.EXTENSION_PDF);
			}
			if (fileResultBean.getFile() != null && fileResultBean.getFile().exists()) {
				return fileResultBean.getFile();
			}

		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el Justificante para el Expediente:" + justificante.getNumExpediente());
		}
		return null;
	}

	private void borrarFicherosZipTemporales(FileResultBean fileResultBean) {
		if (fileResultBean != null) {
			if (fileResultBean.getFile() != null) {
				String extension = obtenerExtensionFichero(fileResultBean.getFile());
				if (".zip".equalsIgnoreCase(extension)) {
					fileResultBean.getFile().delete();
				}
			}
			if (fileResultBean.getFiles() != null) {
				for (File file : fileResultBean.getFiles()) {
					String extension = obtenerExtensionFichero(file);
					if (".zip".equalsIgnoreCase(extension)) {
						file.delete();
					}
				}
			}
		}
	}

	private String obtenerExtensionFichero(File file) {
		String extension = null;
		if (file != null && file.getPath() != null) {
			extension = file.getPath().substring(file.getPath().lastIndexOf("."));
		}
		return extension;
	}

	@Override
	public String generarNombre(JustificanteProfVO justificante) {
		if (justificante.getNumExpediente() != null) {
			return generarNombre(justificante.getNumExpediente(), justificante.getIdJustificante());
		} else {
			return generarNombre(BigDecimal.valueOf(justificante.getIdJustificanteInterno()), justificante.getIdJustificante());
		}
	}

	@Override
	public String generarNombre(BigDecimal numExpediente, BigDecimal numJustificante) {
		String nombre = "";
		if (null != numJustificante) {
			String anadido = "0000000000";
			String id = numJustificante.toString();
			nombre = anadido.substring(0, anadido.length() - id.length()) + id;
		} else {
			nombre = numExpediente.toString();
		}

		return numExpediente.toString() + "_" + nombre;
	}

	@Override
	public boolean guardarDocumento(JustificanteProfDto justificanteProfDto, String numJustificante, byte[] documento) {
		File file = null;
		if (justificanteProfDto != null && numJustificante != null && documento != null) {
			if (log.isInfoEnabled()) {
				log.info("Obtenido contenido del PDF del justificante profesional para la peticion " + justificanteProfDto.getIdJustificanteInterno());
			}
			String nombre = null;
			Fecha fecha = null;
			if (justificanteProfDto.getNumExpediente() != null) {
				fecha = Utilidades.transformExpedienteFecha(justificanteProfDto.getNumExpediente());
				nombre = generarNombre(justificanteProfDto.getNumExpediente(), new BigDecimal(numJustificante));
			} else if (justificanteProfDto.getFechaInicio() != null) {
				// Es una verificacion y no tenemos numExpediente
				fecha = utilesFecha.getFechaConDate(justificanteProfDto.getFechaInicio());
				nombre = generarNombre(BigDecimal.valueOf(justificanteProfDto.getIdJustificanteInterno()), new BigDecimal(numJustificante));
			}
			if (nombre != null && fecha != null) {
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.JUSTIFICANTES);
				fichero.setSubTipo(ConstantesGestorFicheros.JUSTIFICANTES_RESPUESTA);
				fichero.setSobreescribir(true);
				fichero.setNombreDocumento(nombre);
				fichero.setFecha(fecha);
				fichero.setFicheroByte(documento);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);

				try {
					file = gestorDocumentos.guardarByte(fichero);
				} catch (OegamExcepcion e) {
					log.error("Error al guardar el justificante profesional", e);
				}
			}
		}
		return file != null && !file.exists();
	}
	
	@Override
	public boolean guardarDocumento(JustificanteProfVO justificanteProfVO, String numJustificante, byte[] documento) {
		File file = null;
		if (justificanteProfVO != null && numJustificante != null && documento != null) {
			if (log.isInfoEnabled()) {
				log.info("Obtenido contenido del PDF del justificante profesional para la peticion " + justificanteProfVO.getIdJustificanteInterno());
			}
			String nombre = null;
			Fecha fecha = null;
			if (justificanteProfVO.getNumExpediente() != null) {
				fecha = Utilidades.transformExpedienteFecha(justificanteProfVO.getNumExpediente());
				nombre = generarNombre(justificanteProfVO.getNumExpediente(), new BigDecimal(numJustificante));
			} else if (justificanteProfVO.getFechaInicio() != null) {
				// Es una verificacion y no tenemos numExpediente
				fecha = utilesFecha.getFechaConDate(justificanteProfVO.getFechaInicio());
				nombre = generarNombre(BigDecimal.valueOf(justificanteProfVO.getIdJustificanteInterno()), new BigDecimal(numJustificante));
			}
			if (nombre != null && fecha != null) {
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.JUSTIFICANTES);
				fichero.setSubTipo(ConstantesGestorFicheros.JUSTIFICANTES_RESPUESTA);
				fichero.setSobreescribir(true);
				fichero.setNombreDocumento(nombre);
				fichero.setFecha(fecha);
				fichero.setFicheroByte(documento);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);

				try {
					file = gestorDocumentos.guardarByte(fichero);
				} catch (OegamExcepcion e) {
					log.error("Error al guardar el justificante profesional", e);
				}
			}
		}
		return file != null && !file.exists();
	}

	/**
	 * Crea un FileResultBean a partir de una lista de ficheros.
	 * @param numExpediente número de expediente al que están asociados los ficheros.
	 * @param files lista de ficheros.
	 * @return FileResultBean - FileResultBean.
	 */
	private FileResultBean crearFileResultBeanFromFiles(BigDecimal numExpediente, File file) {
		FileResultBean fileResultBean = new FileResultBean();
		fileResultBean.setStatus(FileResultStatus.UNIQUE_FILE_FOUND);
		fileResultBean.setFile(file);
		return fileResultBean;
	}

	@Override
	public void addZipEntryFromFile(ZipOutputStream zip, File file) {
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

	private ByteArrayInputStreamBean mapFromFileResultBeanToByteArrayInputStreamBean(FileResultBean fileResultBean) {
		ByteArrayInputStreamBean byteArrayInputStreamBean = null;
		if (fileResultBean != null) {
			byteArrayInputStreamBean = new ByteArrayInputStreamBean();
			byteArrayInputStreamBean.setByteArrayInputStream(transformFiletoByteArrayInputStream(fileResultBean.getFile()));
			byteArrayInputStreamBean.setStatus(fileResultBean.getStatus());
			byteArrayInputStreamBean.setMessage(fileResultBean.getMessage());
			if (FileResultStatus.LIST_FILE_FOUND.equals(fileResultBean.getStatus())) {
				byteArrayInputStreamBean.setFileName(ConstantesGestorFicheros.JUSTIFICANTES + "_" + System.currentTimeMillis() + ConstantesPDF.EXTENSION_ZIP);
			} else if (FileResultStatus.UNIQUE_FILE_FOUND.equals(fileResultBean.getStatus())) {
				byteArrayInputStreamBean.setFileName(ConstantesGestorFicheros.JUSTIFICANTES + "_" + System.currentTimeMillis() + ConstantesPDF.EXTENSION_PDF);

			}
		}
		return byteArrayInputStreamBean;
	}

	private ByteArrayInputStream transformFiletoByteArrayInputStream(File file) {
		ByteArrayInputStream byteArrayInputStream = null;
		try {
			if (file != null) {
				byte[] resultado = new byte[(int) file.length()];
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

	public JustificanteProfDao getJustificanteProfDao() {
		return justificanteProfDao;
	}

	public void setJustificanteProfDao(JustificanteProfDao justificanteProfDao) {
		this.justificanteProfDao = justificanteProfDao;
	}

}
