package org.gestoresmadrid.core.model.security.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.KeyRep;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class CryptoProviderDESImpl implements CryptoProvider {
	private static final String ALGORITHM = "DES";
	private static final int ALGORITH_LENGHT = 56;
	private static final String ALGORITHM_MODE_PADDING = "DES/ECB/PKCS5Padding";
	private static final String KEY_FILE_PATH = "crypto.secretkey.path";
	private ILoggerOegam log = LoggerOegam.getLogger(CryptoProviderDESImpl.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	private SecretKey secretKey;

	@Override
	public SecretKey getSecretKey() {
		if (secretKey == null) {
			initializeKey();
		}
		return secretKey;
	}

	@Override
	public Cipher getCypher() {
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
		} catch (NoSuchAlgorithmException e) {
			log.error("Error, no se conoce el algoritmo empleado", e);
		} catch (NoSuchPaddingException e) {
			log.error("Error, no se conoce el componente padding", e);
		}
		return cipher;
	}

	
	/**
	* Un método para reemplazar el método ObjectInputStream.readObject () inseguro integrado en Java. Este método verifica que las clases a las que se hace referencia sean seguras,
	* el número de objetos está limitado a algo sensato, y el número de bytes está limitado a un número razonable. El objeto devuelto también se lanza
	* al tipo especificado.
	* @param type Clase que representa el tipo de objeto que se espera devolver
	* @param safeClasses Lista de clases permitidas en objetos serializados que se leen
	* @param maxObjects long representando el número máximo de objetos permitidos dentro del objeto serializado que se está leyendo
	* @param maxBytes long representando el número máximo de bytes permitidos para ser leídos desde InputStream
	* @param en InputStream que contiene un objeto serializado no confiable
	* @return Object leído de la secuencia (emitido a la clase del parámetro type)
	* @throws IOException
	* @throws ClassNotFoundException
	*/
	
	public  Object safeReadObject(final Class<?> type, final List<Class<? extends Serializable>> safeClasses, final long maxObjects, final long maxBytes, InputStream in) throws IOException, ClassNotFoundException {
		// crear una secuencia de entrada limitada a un cierto número de bytes
		InputStream lis = new FilterInputStream(in) {
			private long len = 0;

			public int read() throws IOException {
				int val = super.read();
				if (val != -1) {
					len++;
					checkLength();
				}
				return val;
			}

			public int read(byte[] b, int off, int len) throws IOException {
				int val = super.read(b, off, len);
				if (val > 0) {
					len += val;
					checkLength();
				}
				return val;
			}

			private void checkLength() throws IOException {
				if (len > maxBytes) {
					throw new SecurityException("Infracción de seguridad: intenta deserializar demasiados bytes de la transmisión. El límite es " + maxBytes);
				}
			}
		};
		// crear una secuencia de entrada de objetos que verifique las clases y limite el número de objetos para leer
		ObjectInputStream ois = new ObjectInputStream(lis) {
			private int objCount = 0;

			protected Object resolveObject(Object obj) throws IOException {
				if (objCount++ > maxObjects)
					throw new SecurityException("Infracción de seguridad: intenta deserializar demasiados objetos del flujo. El límite es " + maxObjects);
				Object object = super.resolveObject(obj);
				return object;
			}

			protected Class<?> resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
				Class<?> clazz = super.resolveClass(osc);
				if ( clazz.isArray() ||
		                clazz.equals(type) ||
		                clazz.equals(String.class) ||
		                Number.class.isAssignableFrom(clazz) ||
		                safeClasses.contains(clazz))
					return clazz;
				throw new SecurityException("Violación de seguridad: intento de deserialización no autorizada " + clazz);
			}
		};
		// use el ObjectInputStream protegido para leer objetos de forma segura y convertirlos en T
		return ois.readObject();
	}
	
	
	private void initializeKey() {
		Object result;
		InputStream is = null;
		try {
			is = new FileInputStream(new File(gestorPropiedades.valorPropertie(KEY_FILE_PATH)));
			
			List<Class<? extends Serializable>> safeClasses = Arrays.asList(SecretKey.class, KeyRep.class, KeyRep.Type.class, Enum.class);
			
			result = safeReadObject(SecretKey.class, safeClasses, 10, 1000, is );
			
			secretKey = (SecretKey) result;
		} catch (FileNotFoundException e) {
			log.error("Error, no se pudó crear el fichero", e);
		} catch (IOException e) {
			log.error("Error, al guardar el fichero", e);
		} catch (ClassNotFoundException e) {
			log.error("clase del objeto serializado no es la esperada", e);
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

	/**
	 * Genera fichero con la clave
	 * 
	 * @param path
	 * 			Ruta donde deja el fichero con la clave
	 */
	public void generateKey(String path) {
		OutputStream ouputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			generator.init(ALGORITH_LENGHT);
			SecretKey clave = generator.generateKey();

			ouputStream = new FileOutputStream(new File(path));
			objectOutputStream = new ObjectOutputStream(ouputStream );
			objectOutputStream.writeObject(clave);

		} catch (NoSuchAlgorithmException e) {
			log.error("Error, no se conoce el algoritmo empleado", e);
		} catch (FileNotFoundException e) {
			log.error("Error, no se pudó crear el fichero", e);
		} catch (IOException e) {
			log.error("Error, al guardar el fichero", e);
		} finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					log.error("Error cerrando objectOutputStream");
				}
			}
			if (ouputStream != null) {
				try {
					ouputStream.close();
				} catch (IOException e) {
					log.error("Error cerrando ouputStream");
				}
			}
			
		}
	}

}
