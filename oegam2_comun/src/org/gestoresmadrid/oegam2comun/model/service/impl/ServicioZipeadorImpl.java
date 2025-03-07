package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.oegam2comun.model.service.ServicioZipeador;
import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioZipeadorImpl implements ServicioZipeador {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6881226689895877396L;
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioZipeadorImpl.class);

	/**
	 * @see ServicioZipeador#zipear(File)
	 */
	@Override
	public byte[] zipear(File entry) {
		return zipear(Collections.singleton(entry));
	}

	/**
	 * @see ServicioZipeador#zipear(Collection)
	 */
	@Override
	public byte[] zipear(Collection<File> entries) {
		byte[] contenido = null;
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			zipear(entries, out);
			contenido = out.toByteArray();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LOG.error("Error cerrando el recurso ByteArrayOutputStream", e);
				}
			}
		}
		return contenido;
	}

	/**
	 * @see ServicioZipeador#zipear(File, File)
	 */
	@Override
	public boolean zipear(File destiny, File entry) {
		return zipear(destiny, Collections.singleton(entry));
	}

	/**
	 * @see ServicioZipeador#zipear(File, Collection)
	 */
	@Override
	public boolean zipear(File destiny, Collection<File> entries) {
		boolean result;
		OutputStream out = null;
		try {
			out = new FileOutputStream(destiny);
			result = zipear(entries, out);
		} catch (FileNotFoundException e) {
			Log.error("Destino no encontrado", e);
			result = false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LOG.error("Error cerrando el recurso ByteArrayOutputStream", e);
				}
			}
		}
		return result;
	}

	/**
	 * @see ServicioZipeador#zipear(String, File)
	 */
	@Override
	public boolean zipear(String destiny, File entry) {
		return zipear(destiny, Collections.singleton(entry));
	}

	/**
	 * @see ServicioZipeador#zipear(String, Collection)
	 */
	@Override
	public boolean zipear(String destiny, Collection<File> entries) {
		boolean result;
		OutputStream out = null;
		try {
			out = new FileOutputStream(destiny);
			result = zipear(entries, out);
		} catch (FileNotFoundException e) {
			Log.error("Destino no encontrado", e);
			result = false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LOG.error("Error cerrando el recurso ByteArrayOutputStream", e);
				}
			}
		}
		return result;
	}

	/**
	 * Zipea todos los ficheros, y envia el resultado al outputstream pasado por parametro
	 * @param entries
	 * @param out
	 * @return
	 */
	private boolean zipear(Collection<File> entries, OutputStream out) {
		boolean allAdded = true;
		if (entries == null || out == null) {
			allAdded = false;
		} else {
			ZipOutputStream zos = null;
			try {
				zos = new ZipOutputStream(out);
				for (File file : entries) {
					allAdded |= addEntry(zos, file);
				}
			} finally {
				if (zos != null) {
					try {
						zos.close();
					} catch (IOException e) {
						LOG.error("Error cerrarndo el recurso ZipOutputStream", e);
					}
				}
			}
		}
		return allAdded;
	}

	/**
	 * Aniade un fichero al zip
	 * @param zos
	 * @param file
	 * @return
	 */
	private boolean addEntry(ZipOutputStream zos, File file) {
		boolean added = false;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			addEntry(zos, file.getName(), is);
			added = true;
		} catch (FileNotFoundException e) {
			LOG.error("No existe el fichero", e);
		} catch (IOException e) {
			LOG.error("Error al incluir entrada al zip", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					LOG.error("Error cerrando FileInputStream", e);
				}
			}
		}
		return added;
	}

	/**
	 * Aniade una entrada al zip
	 * @param zipOutputStream
	 * @param entryName
	 * @param is
	 * @throws IOException
	 */
	private void addEntry(ZipOutputStream zipOutputStream, String entryName, InputStream is) throws IOException {
		ZipEntry zipEntry = new ZipEntry(entryName);
		zipOutputStream.putNextEntry(zipEntry);
		byte[] buffer = new byte[2048];
		int byteCount;
		while (-1 != (byteCount = is.read(buffer))) {
			zipOutputStream.write(buffer, 0, byteCount);
		}
		zipOutputStream.closeEntry();
	}

}
