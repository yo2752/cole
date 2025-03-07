package utilidades.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

public class OegamRollingFileAppender extends RollingFileAppender {
	private static final String DATE_PATTERN = "'.'yyyyMMdd_HHmmss";

	private SimpleDateFormat sdf;
	private Pattern pattern;

	public OegamRollingFileAppender() {
	}

	public OegamRollingFileAppender(Layout layout, String filename, boolean append) throws IOException {
		super(layout, filename, append);
	}

	public OegamRollingFileAppender(Layout layout, String filename) throws IOException {
		super(layout, filename);
	}

	@Override
	public void rollOver() {
		// Log actual
		File file = new File(this.fileName);
		super.closeFile();
		// Backup del log actual
		if (sdf == null) {
			sdf = new SimpleDateFormat(DATE_PATTERN);
		}
		File target = new File(this.fileName + sdf.format(new Date()));

		// Comprobar el numero maximo de ficheros rotados
		checkMaxBackupIndex(file);

		// Copiar el log actual. Si Ok, no continuar con el fichero
		boolean append = !copyCurrentLog(file, target);

		// Establece el nuevo fichero de logs
		try {
			setFile(this.fileName, append, this.bufferedIO, this.bufferSize);
		} catch (IOException e) {
			LogLog.error("setFile(" + this.fileName + ", false) call failed.", e);
		}

	}

	/**
	 * Comprueba si existen ficheros de logs rotados que haya que borrar
	 * 
	 * @param file Fichero del que se realizan los rotados
	 */
	private void checkMaxBackupIndex(File file) {
		if (this.maxBackupIndex > 0 && file != null) {
			if (pattern == null) {
				pattern = Pattern.compile("^" + file.getName() + "\\.[0-9]{8}_[0-9]{6}");
			}
			File parent = file.getParentFile();
			if (parent != null && parent.exists()) {

				List<File> candidatos = new ArrayList<File>();
				for (File f : parent.listFiles()) {
					Matcher m = pattern.matcher(f.getName());
					if (m.find()) {
						candidatos.add(f);
					}
				}
				deleteOldBackups(candidatos);
			}
		}
	}

	/**
	 * Metodo que realiza el borrado de los ficheros de logs antiguos
	 * 
	 * @param candidatos Lista de ficheros rotados
	 */
	private void deleteOldBackups(List<File> candidatos) {
		if (candidatos.size() >= this.maxBackupIndex) {
			Collections.sort(candidatos);
			Collections.reverse(candidatos);
			for (int i = this.maxBackupIndex - 1; i < candidatos.size(); i++) {
				candidatos.get(i).delete();
			}
		}
	}

	/**
	 * Metodo que realiza la copia de un fichero origen a otro fichero destino
	 * 
	 * @param file Fichero origen de la copia
	 * @param target Fichero destino
	 * @return true la copia se realiza correctamente, false en otro caso
	 */
	private boolean copyCurrentLog(File file, File target) {
		LogLog.debug("Copiando fichero " + file + " a " + target);
		FileChannel sourceChannel = null;
		FileChannel destChannel = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(file);
			fos = new FileOutputStream(target);
			sourceChannel = fis.getChannel();
			destChannel = fos.getChannel();
			long size = destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
			if (size == file.length()) {
				// Si se ha realizado la copia correctamente, se borra el contenido del archivo de origen
				PrintWriter writer = new PrintWriter(file);
				writer.print("");
				writer.close();
				return true;
			}
		} catch (FileNotFoundException e) {
			LogLog.error("backupCurrentLog, call failed.", e);
		} catch (IOException e) {
			LogLog.error("backupCurrentLog, call failed.", e);
		} finally {
			if (sourceChannel != null) {
				try {
					sourceChannel.close();
				} catch (IOException e) {
					LogLog.error("backupCurrentLog,  sourceChannel.close() failed.", e);
				}
			}
			if (destChannel != null) {
				try {
					destChannel.close();
				} catch (IOException e) {
					LogLog.error("backupCurrentLog,  destChannel.close() failed.", e);
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					LogLog.error("backupCurrentLog,  fis.close() failed.", e);
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					LogLog.error("backupCurrentLog,  fos.close() failed.", e);
				}
			}
		}
		return false;
	}

	@Override
	protected void subAppend(LoggingEvent event) {
		super.subAppend(event);
		if ((this.fileName != null) && (this.qw != null)) {
			long size = ((CountingQuietWriter) this.qw).getCount();
			if (size >= this.maxFileSize) {
				rollOver();
			}
		}
	}
}
