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
	* Un m�todo para reemplazar el m�todo ObjectInputStream.readObject () inseguro integrado en Java. Este m�todo verifica que las clases a las que se hace referencia sean seguras,
	* el n�mero de objetos est� limitado a algo sensato, y el n�mero de bytes est� limitado a un n�mero razonable. El objeto devuelto tambi�n se lanza
	* al tipo especificado.
	* @param type Clase que representa el tipo de objeto que se espera devolver
	* @param safeClasses Lista de clases permitidas en objetos serializados que se leen
	* @param maxObjects long representando el n�mero m�ximo de objetos permitidos dentro del objeto serializado que se est� leyendo
	* @param maxBytes long representando el n�mero m�ximo de bytes permitidos para ser le�dos desde InputStream
	* @param en InputStream que contiene un objeto serializado no confiable
	* @return Object le�do de la secuencia (emitido a la clase del par�metro type)
	* @throws IOException
	* @throws ClassNotFoundException
	*/
	
	public  Object safeReadObject(final Class<?> type, final List<Class<? extends Serializable>> safeClasses, final long maxObjects, final long maxBytes, InputStream in) throws IOException, ClassNotFoundException {
		// crear una secuencia de entrada limitada a un cierto n�mero de bytes
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
					throw new SecurityException("Infracci�n de seguridad: intenta deserializar demasiados bytes de la transmisi�n. El l�mite es " + maxBytes);
				}
			}
		};
		// crear una secuencia de entrada de objetos que verifique las clases y limite el n�mero de objetos para leer
		ObjectInputStream ois = new ObjectInputStream(lis) {
			private int objCount = 0;

			protected Object resolveObject(Object obj) throws IOException {
				if (objCount++ > maxObjects)
					throw new SecurityException("Infracci�n de seguridad: intenta deserializar demasiados objetos del flujo. El l�mite es " + maxObjects);
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
				throw new SecurityException("Violaci�n de seguridad: intento de deserializaci�n no autorizada " + clazz);
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
			log.error("Error, no se pud� crear el fichero", e);
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
			log.error("Error, no se pud� crear el fichero", e);
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
