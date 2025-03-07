package general.utiles;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import utilidades.logger.ILoggerOegam;

public final class TripleDES {

	public static final String CIPHER_TRANSFORMATION = TripleDesKey.ALGORITHM_NAME;
	//public static final String CIPHER_TRANSFORMATION = TripleDesKey.ALGORITHM_NAME+"/CBC/PKCS5Padding";
    public static final String CRYPTO_PROVIDER = "SunJCE";
    public static final String CRYPTO_PROVIDER_CLASS_NAME = "com.sun.crypto.provider.SunJCE";

    private static final Object lock = new Object();

	//////////////////////////
	// MÉTODOS PARA EL CIFRADO
	//////////////////////////

	/**
	 * Cifra una entrada utilizando TripleDES. Utiliza la transformación por defecto "DESede".
	 *
	 * @param key		cadena con la clave de cifrado utilizada
	 * @param input		cadena con el texto en claro
	 * @param log		la instancia de log para la escritura de trazas
	 *
	 * @return la cadena cifrada (en formato legible)
	 */
	public static String encrypt(String key, String input, ILoggerOegam log) {
		return encrypt(key,(String)null,input,log);
	}

	/**
	 * Cifra una entrada utilizando TripleDES. Utiliza la transformación por defecto "DESede".
	 *
	 * @param key		cadena con la clave de cifrado utilizada
	 * @param iv		cadena de inicialización para el cifrado (puede ser nulo)
	 * @param input		cadena con el texto en claro
	 * @param log		la instancia de log para la escritura de trazas
	 *
	 * @return la cadena cifrada (en formato legible)
	 */
	public static String encrypt(String key, String iv, String input, ILoggerOegam log) {
		return encrypt(CIPHER_TRANSFORMATION,key,iv,input,log);
	}

	/**
	 * Cifra una entrada utilizando TripleDES. Utiliza la transformación por defecto "DESede".
	 *
	 * @param cipherTrans	Transformación utilizada: "DESede/CBC/PKCS5Padding", "DESede", ...
	 * @param key			cadena con la clave de cifrado utilizada
	 * @param iv			cadena de inicialización para el cifrado (puede ser nulo)
	 * @param input			cadena con el texto en claro
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return la cadena cifrada (en formato legible)
	 */
	public static String encrypt(String cipherTrans, String key, String iv, String input, ILoggerOegam log) {
		return encrypt(cipherTrans,key,iv,input,BasicText.CHARACTER_ENCODING,log);
	}

	/**
	 * Cifra una entrada utilizando TripleDES. Utiliza la transformación por defecto "DESede".
	 *
	 * @param cipherTrans	Transformación utilizada: "DESede/CBC/PKCS5Padding", "DESede", ...
	 * @param key			cadena con la clave de cifrado utilizada
	 * @param iv			cadena de inicialización para el cifrado (puede ser nulo)
	 * @param input			cadena con el texto en claro
	 * @param encoding		la codificación de las cadenas; en caso de ser nulo se utiliza la de por defecto de la plataforma
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return la cadena cifrada (en formato legible)
	 */
	public static String encrypt(String cipherTrans, String key, String iv, String input, String encoding, ILoggerOegam log) {
		try {
			byte[] keyBytes		= null;
			byte[] ivBytes		= null;
			byte[] inputBytes	= null;
			if (encoding==null) {
				keyBytes = key.getBytes();
				ivBytes = (iv==null) ? null : iv.getBytes();
				inputBytes = input.getBytes();
			}
			else {
				keyBytes = key.getBytes(encoding);
				ivBytes = (iv==null) ? null : iv.getBytes(encoding);
				inputBytes = input.getBytes(encoding);
			}
			byte[] resultBytes = encrypt(cipherTrans, keyBytes, ivBytes, inputBytes, log);
			return BasicText.digestToString(resultBytes);
		}
		catch (Exception e) {
			//log.writeTrace(Logger.ERROR, "TripleDES", "encrypt", BasicText.getStackTrace(e));
			log.error(e);
			return null;
		}
	}

	/**
	 * Cifra una entrada utilizando TripleDES. Utiliza la transformación por defecto "DESede".
	 *
	 * @param keyBytes	array de bytes con la clave de cifrado utilizada
	 * @param input		cadena con el texto en claro, codificada en ISO-8859-1
	 * @param log		la instancia de log para la escritura de trazas
	 *
	 * @return la cadena cifrada (en formato legible)
	 */
	public static String encrypt(byte[] keyBytes, String input, ILoggerOegam log) {
		return encrypt(keyBytes,null,input,log);
	}

	/**
	 * Cifra una entrada utilizando TripleDES. Utiliza la transformación por defecto "DESede".
	 *
	 * @param keyBytes	array de bytes con la clave de cifrado utilizada
	 * @param ivBytes	el vector de inicialización para el cifrado (puede ser nulo)
	 * @param input		cadena con el texto en claro, codificada en ISO-8859-1
	 * @param log		la instancia de log para la escritura de trazas
	 *
	 * @return la cadena cifrada (en formato legible)
	 */
	public static String encrypt(byte[] keyBytes, byte[] ivBytes, String input, ILoggerOegam log) {
		return encrypt(keyBytes,ivBytes,input,BasicText.CHARACTER_ENCODING,log);
	}

	/**
	 * Cifra una entrada utilizando TripleDES. Utiliza la transformación por defecto "DESede".
	 *
	 * @param keyBytes	array de bytes con la clave de cifrado utilizada
	 * @param ivBytes	el vector de inicialización para el cifrado (puede ser nulo)
	 * @param input		cadena con el texto en claro
	 * @param encoding	la codificación de la cadena; en caso de ser nulo se utiliza la de por defecto de la plataforma
	 * @param log		la instancia de log para la escritura de trazas
	 *
	 * @return la cadena cifrada (en formato legible)
	 */
	public static String encrypt(byte[] keyBytes, byte[] ivBytes, String input, String encoding, ILoggerOegam log) {
		try {
			byte[] inputBytes = (encoding==null) ? input.getBytes() : input.getBytes(encoding);
			byte[] resultBytes = encrypt(keyBytes, ivBytes, inputBytes, log);
			return BasicText.digestToString(resultBytes);
		}
		catch (Exception e) {
			//log.writeTrace(Logger.ERROR, "TripleDES", "encrypt", BasicText.getStackTrace(e));
			log.error(e);
			return null;
		}
	}

	/**
	 * Cifra una entrada utilizando TripleDES. Utiliza la transformación por defecto "DESede".
	 *
	 * @param keyBytes		array de bytes con la clave de cifrado utilizada
	 * @param ivBytes		el vector de inicialización para el cifrado (puede ser nulo)
	 * @param inputBytes	array de bytes con la entrada en claro
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return array de bytes cifrado
	 */
	public static byte[] encrypt(byte[] keyBytes, byte[] ivBytes, byte[] inputBytes, ILoggerOegam log) {
		return encrypt(CIPHER_TRANSFORMATION, keyBytes, ivBytes, inputBytes, log);
	}


	/**
	 * Cifra una entrada utilizando TripleDES.
	 *
	 * @param cipherTrans	Transformación utilizada: "DESede/CBC/PKCS5Padding", "DESede", ...
	 * @param keyBytes		array de bytes con la clave de cifrado utilizada
	 * @param input			cadena con el texto en claro, codificada en ISO-8859-1
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return la cadena cifrada (en formato legible)
	 */
	public static String encrypt(String cipherTrans, byte[] keyBytes, String input, ILoggerOegam log) {
		return encrypt(cipherTrans,keyBytes,null,input,log);
	}

	/**
	 * Cifra una entrada utilizando TripleDES.
	 *
	 * @param cipherTrans	Transformación utilizada: "DESede/CBC/PKCS5Padding", "DESede", ...
	 * @param keyBytes		array de bytes con la clave de cifrado utilizada
	 * @param ivBytes		el vector de inicialización para el cifrado (puede ser nulo)
	 * @param input			cadena con el texto en claro, codificada en ISO-8859-1
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return la cadena cifrada (en formato legible)
	 */
	public static String encrypt(String cipherTrans, byte[] keyBytes, byte[] ivBytes, String input, ILoggerOegam log) {
		return encrypt(cipherTrans,keyBytes,ivBytes,input,BasicText.CHARACTER_ENCODING,log);
	}

	/**
	 * Cifra una entrada utilizando TripleDES.
	 *
	 * @param cipherTrans	Transformación utilizada: "DESede/CBC/PKCS5Padding", "DESede", ...
	 * @param keyBytes		array de bytes con la clave de cifrado utilizada
	 * @param ivBytes		el vector de inicialización para el cifrado (puede ser nulo)
	 * @param input			cadena con el texto en claro
	 * @param encoding		la codificación de las cadenas; en caso de ser nulo se utiliza la de por defecto de la plataforma
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return la cadena cifrada (en formato legible)
	 */
	public static String encrypt(String cipherTrans, byte[] keyBytes, byte[] ivBytes, String input, String encoding, ILoggerOegam log) {
		try {
			byte[] inputBytes = (encoding==null) ? input.getBytes() : input.getBytes(encoding);
			byte[] resultBytes = encrypt(cipherTrans, keyBytes, ivBytes, inputBytes, log);
			return BasicText.digestToString(resultBytes);
		}
		catch (Exception e) {
			//log.writeTrace(Logger.ERROR, "TripleDES", "encrypt", BasicText.getStackTrace(e));ç
			log.error(e);
			return null;
		}
	}

	/**
	 * Cifra una entrada utilizando TripleDES.
	 *
	 * @param cipherTrans	Transformación utilizada: "DESede/CBC/PKCS5Padding", "DESede", ...
	 * @param keyBytes		array de bytes con la clave de cifrado utilizada
	 * @param ivBytes		el vector de inicialización para el cifrado (puede ser nulo)
	 * @param inputBytes	array de bytes con la entrada en claro
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return array de bytes cifrado
	 */
	public static byte[] encrypt(String cipherTrans, byte[] keyBytes, byte[] ivBytes, byte[] inputBytes, ILoggerOegam log) {
		try {
			Cipher cipher = initCipher(cipherTrans, Cipher.ENCRYPT_MODE, keyBytes, ivBytes, log);
			return cipher.doFinal(inputBytes, 0, inputBytes.length);
		}
		catch (Exception e) {
			//log.writeTrace(Logger.ERROR, "TripleDES", "encrypt", BasicText.getStackTrace(e));
			log.error(e);
			return null;
		}
	}


	/////////////////////////////
	// MÉTODOS PARA EL DESCIFRADO
	/////////////////////////////

	/**
	 * Descifra una entrada utilizando TripleDES. Utiliza la transformación "DESede/CBC/PKCS5Padding".
	 *
	 * @param key		array de bytes con la clave de cifrado utilizada
	 * @param input		cadena con la entrada cifrada (pero en formato legible)
	 * @param log		la instancia de log para la escritura de trazas
	 *
	 * @return cadena con la salida descifrada y codificada en la codificación especificada
	 */
	public static String decrypt(String key, String input, ILoggerOegam log) {
		return decrypt(key,(String)null,input,log);
	}

	/**
	 * Descifra una entrada utilizando TripleDES. Utiliza la transformación "DESede/CBC/PKCS5Padding".
	 *
	 * @param key		array de bytes con la clave de cifrado utilizada
	 * @param iv		el vector de inicialización para el cifrado (puede ser nulo)
	 * @param input		cadena con la entrada cifrada (pero en formato legible)
	 * @param log		la instancia de log para la escritura de trazas
	 *
	 * @return cadena con la salida descifrada y codificada en la codificación especificada
	 */
	public static String decrypt(String key, String iv, String input, ILoggerOegam log) {
		return decrypt(CIPHER_TRANSFORMATION,key,iv,input,log);
	}

	/**
	 * Descifra una entrada utilizando TripleDES. Utiliza la transformación "DESede/CBC/PKCS5Padding".
	 *
	 * @param cipherTrans	Transformación utilizada: "DESede/CBC/PKCS5Padding", "DESede", ...
	 * @param key			cadena con la clave de cifrado utilizada
	 * @param iv			el vector de inicialización para el cifrado (puede ser nulo)
	 * @param input			cadena con la entrada cifrada (pero en formato legible)
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return cadena con la salida descifrada y codificada en la codificación especificada
	 */
	public static String decrypt(String cipherTrans, String key, String iv, String input, ILoggerOegam log) {
		return decrypt(cipherTrans,key,iv,input,BasicText.CHARACTER_ENCODING,log);
	}

	/**
	 * Descifra una entrada utilizando TripleDES.
	 *
	 * @param cipherTrans	Transformación utilizada [Algoritmo/ModoCifrado/Padding]: "DESede/CBC/PKCS5Padding", "DESede", ...
	 * @param key			cadena con la clave de cifrado utilizada
	 * @param iv			el vector de inicialización para el cifrado (puede ser nulo)
	 * @param input			cadena con la entrada cifrada (pero en formato legible)
	 * @param encoding		la codificación utilizada; en caso de ser nulo se utiliza la de por defecto de la plataforma
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return cadena con la salida descifrada y codificada en la codificación especificada
	 */
	public static String decrypt(String cipherTrans, String key, String iv, String input, String encoding, ILoggerOegam log) {
		try {
			byte[] keyBytes		= null;
			byte[] ivBytes		= null;
			byte[] inputBytes	= null;
			inputBytes = BasicText.stringToDigest(input);
			if (encoding==null) {
				keyBytes = key.getBytes();
				ivBytes = (iv==null) ? null : iv.getBytes();
			}
			else {
				keyBytes = key.getBytes(encoding);
				ivBytes = (iv==null) ? null : iv.getBytes(encoding);
			}
			byte[] resultBytes = decrypt(cipherTrans, keyBytes, ivBytes, inputBytes, log);
			return ((encoding==null) ? new String(resultBytes) : new String(resultBytes,encoding));
		}
		catch (Exception e) {
			//log.writeTrace(Logger.ERROR, "TripleDES", "decrypt", BasicText.getStackTrace(e));
			log.error(e);
			return null;
		}
	}

	/**
	 * Descifra una entrada utilizando TripleDES. Utiliza la transformación "DESede/CBC/PKCS5Padding".
	 *
	 * @param keyBytes			array de bytes con la clave de cifrado utilizada
	 * @param input				cadena con la entrada cifrada (pero en formato legible)
	 * @param log				la instancia de log para la escritura de trazas
	 *
	 * @return cadena con la salida descifrada y codificada en la codificación especificada
	 */
	public static String decrypt(byte[] keyBytes, String input, ILoggerOegam log) {
		return decrypt(keyBytes,null,input,log);
	}

	/**
	 * Descifra una entrada utilizando TripleDES. Utiliza la transformación "DESede/CBC/PKCS5Padding".
	 *
	 * @param keyBytes			array de bytes con la clave de cifrado utilizada
	 * @param ivBytes			el vector de inicialización para el cifrado (puede ser nulo)
	 * @param input				cadena con la entrada cifrada (pero en formato legible)
	 * @param log				la instancia de log para la escritura de trazas
	 *
	 * @return cadena con la salida descifrada y codificada en la codificación especificada
	 */
	public static String decrypt(byte[] keyBytes, byte[] ivBytes, String input, ILoggerOegam log) {
		return decrypt(keyBytes,ivBytes,input,BasicText.CHARACTER_ENCODING,log);
	}

	/**
	 * Descifra una entrada utilizando TripleDES. Utiliza la transformación "DESede/CBC/PKCS5Padding".
	 *
	 * @param keyBytes		array de bytes con la clave de cifrado utilizada
	 * @param ivBytes		el vector de inicialización para el cifrado (puede ser nulo)
	 * @param input			cadena con la entrada cifrada (pero en formato legible)
	 * @param encoding		la codificación de la cadena respuesta; en caso de ser nulo se utiliza la de por defecto de la plataforma
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return cadena con la salida descifrada y codificada en la codificación especificada
	 */
	public static String decrypt(byte[] keyBytes, byte[] ivBytes, String input, String encoding, ILoggerOegam log) {
		try {
			byte[] inputBytes = BasicText.stringToDigest(input);
			byte[] resultBytes = decrypt(keyBytes, ivBytes, inputBytes, log);
			return ((encoding==null) ? new String(resultBytes) : new String(resultBytes,encoding));
		}
		catch (Exception e) {
			//log.writeTrace(Logger.ERROR, "TripleDES", "decrypt", BasicText.getStackTrace(e));
			log.error(e);
			return null;
		}
	}

	/**
	 * Descifra una entrada utilizando TripleDES. Utiliza la transformación "DESede/CBC/PKCS5Padding".
	 *
	 * @param keyBytes		array de bytes con la clave de cifrado utilizada
	 * @param ivBytes		el vector de inicialización para el cifrado (puede ser nulo)
	 * @param inputBytes	array de bytes con la entrada cifrada
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return array de bytes descifrado
	 */
	public static byte[] decrypt(byte[] keyBytes, byte[] ivBytes, byte[] inputBytes, ILoggerOegam log) {
		return decrypt(CIPHER_TRANSFORMATION, keyBytes, ivBytes, inputBytes, log);
	}

	/**
	 * Descifra una entrada utilizando TripleDES.
	 *
	 * @param cipherTrans		Transformación utilizada [Algoritmo/ModoCifrado/Padding]: "DESede/CBC/PKCS5Padding", "DESede", ...
	 * @param keyBytes			array de bytes con la clave de cifrado utilizada
	 * @param input				cadena con la entrada cifrada (pero en formato legible)
	 * @param log				la instancia de log para la escritura de trazas
	 *
	 * @return cadena con la salida descifrada y codificada en la codificación especificada
	 */
	public static String decrypt(String cipherTrans, byte[] keyBytes, String input, ILoggerOegam log) {
		return decrypt(cipherTrans,keyBytes,null,input,log);
	}

	/**
	 * Descifra una entrada utilizando TripleDES.
	 *
	 * @param cipherTrans		Transformación utilizada [Algoritmo/ModoCifrado/Padding]: "DESede/CBC/PKCS5Padding", "DESede", ...
	 * @param keyBytes			array de bytes con la clave de cifrado utilizada
	 * @param ivBytes			el vector de inicialización para el cifrado (puede ser nulo)
	 * @param input				cadena con la entrada cifrada (pero en formato legible)
	 * @param log				la instancia de log para la escritura de trazas
	 *
	 * @return cadena con la salida descifrada y codificada en la codificación especificada
	 */
	public static String decrypt(String cipherTrans, byte[] keyBytes, byte[] ivBytes, String input, ILoggerOegam log) {
		return decrypt(cipherTrans,keyBytes,ivBytes,input,BasicText.CHARACTER_ENCODING,log);
	}

	/**
	 * Descifra una entrada utilizando TripleDES.
	 *
	 * @param cipherTrans	Transformación utilizada [Algoritmo/ModoCifrado/Padding]: "DESede/CBC/PKCS5Padding", "DESede", ...
	 * @param keyBytes		array de bytes con la clave de cifrado utilizada
	 * @param ivBytes		el vector de inicialización para el cifrado (puede ser nulo)
	 * @param input			cadena con la entrada cifrada (pero en formato legible)
	 * @param encoding		la codificación de la cadena respuesta; en caso de ser nulo se utiliza la de por defecto de la plataforma
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return cadena con la salida descifrada y codificada en la codificación especificada
	 */
	public static String decrypt(String cipherTrans, byte[] keyBytes, byte[] ivBytes, String input, String encoding, ILoggerOegam log) {
		try {
			byte[] inputBytes = BasicText.stringToDigest(input);
			byte[] resultBytes = decrypt(cipherTrans, keyBytes, ivBytes, inputBytes, log);
			return ((encoding==null) ? new String(resultBytes) : new String(resultBytes,encoding));
		}
		catch (Exception e) {
			//log.writeTrace(Logger.ERROR, "TripleDES", "decrypt", BasicText.getStackTrace(e));
			log.error(e);
			return null;
		}
	}

	/**
	 * Descifra una entrada utilizando TripleDES.
	 *
	 * @param cipherTrans	Transformación utilizada [Algoritmo/ModoCifrado/Padding]: "DESede/CBC/PKCS5Padding", "DESede", ...
	 * @param keyBytes		array de bytes con la clave de cifrado utilizada
	 * @param ivBytes		el vector de inicialización para el cifrado (puede ser nulo)
	 * @param inputBytes	array de bytes con la entrada cifrada
	 * @param log			la instancia de log para la escritura de trazas
	 *
	 * @return array de bytes descifrado
	 */
	public static byte[] decrypt(String cipherTrans, byte[] keyBytes, byte[] ivBytes, byte[] inputBytes, ILoggerOegam log) {
		try {
			Cipher cipher = initCipher(cipherTrans, Cipher.DECRYPT_MODE, keyBytes, ivBytes, log);
			return cipher.doFinal(inputBytes);
		}
		catch (Exception e) {
			//log.writeTrace(Logger.ERROR, "TripleDES", "decrypt", BasicText.getStackTrace(e));
			log.error(e);
			return null;
		}
	}


	///////////////////
	// MÉTODOS PRIVADOS
	///////////////////

	private static Cipher initCipher(String cipherTrans, int mode, byte[] keyBytes, byte[] ivBytes, ILoggerOegam log) throws Exception {
		//Key key = new TripleDesKey(keyBytes);

		// Cambiado porque el tamaño de keyBytes debe ser 24 en vez de 32
		// EAC
		byte[] newKeyBytes = new byte[24];
		int keyLen = keyBytes.length;
		if (keyLen>24) {
			keyLen = 24;
		}
		System.arraycopy(keyBytes, 0, newKeyBytes, 0, keyLen);

		Key key = new SecretKeySpec(newKeyBytes, CIPHER_TRANSFORMATION);

		Cipher cipher = getCipher(cipherTrans, log);
		if(ivBytes == null) {
			cipher.init(mode, key);
 		} else {
			IvParameterSpec iv = new IvParameterSpec(ivBytes, 0, ivBytes.length);
			cipher.init(mode, key, iv);
		}
		return cipher;
	}

	private static boolean firstTime = true;
	private static Cipher getCipher(String cipherTrans, ILoggerOegam log) {
		if (cipherTrans==null)
			cipherTrans = CIPHER_TRANSFORMATION;

		Cipher cipher = null;
		// Check to see whether there is a provider that can do TripleDES
		// encryption.  If not, explicitly install the SunJCE provider.
		try {
			cipher = Cipher.getInstance(cipherTrans);
		} catch(Throwable e) {
			// An exception here probably means the JCE provider hasn't
			// been permanently installed on this system by listing it
			// in the $JAVA_HOME/jre/lib/security/java.security file.
			// Therefore, we have to install the JCE provider explicitly.
			try {
				synchronized(lock) {
					log.debug("Installing " + CRYPTO_PROVIDER + " Crypto Provider...");
					Provider provider = (Provider)Class.forName(CRYPTO_PROVIDER_CLASS_NAME).newInstance();
					Security.addProvider(provider);
					cipher = Cipher.getInstance(cipherTrans,CRYPTO_PROVIDER);
				}
			}
			catch (Throwable t) {
				//log.writeTrace(Logger.ERROR, "TripleDES", "getCipher", BasicText.getStackTrace(t));
				log.error(t);
			}
		}
		if (firstTime && cipher!= null) {
			try{
				Provider provider = cipher.getProvider();
				Properties sp = System.getProperties();
	
				StringBuffer sb = new StringBuffer(256);
				sb.append("Crypto properties in use:\n");
				sb.append("\tOperative System Name        = ").append(sp.getProperty("os.name","[Unknown]")).append("\n");
				sb.append("\tOperative System Version     = ").append(sp.getProperty("os.version","[Unknown]")).append("\n");
				sb.append("\tJava Runtime Name            = ").append(sp.getProperty("java.runtime.name","[Unknown]")).append("\n");
				sb.append("\tJava Runtime Version         = ").append(sp.getProperty("java.runtime.version",sp.getProperty("java.version","[Unknown]"))).append("\n");
				sb.append("\tJava Vendor                  = ").append(sp.getProperty("java.vendor","[Unknown]")).append("\n");
				sb.append("\tJava Virtual Machine Name    = ").append(sp.getProperty("java.vm.name","[Unknown]")).append("\n");
				sb.append("\tJava Virtual Machine Version = ").append(sp.getProperty("java.vm.version","[Unknown]")).append("\n");
				sb.append("\tJava Virtual Machine Vendor  = ").append(sp.getProperty("java.vm.vendor","[Unknown]")).append("\n");
				sb.append("\tJava Virtual Machine Info    = ").append(sp.getProperty("java.vm.info","[Unknown]")).append("\n");
				sb.append("\tCrypto Provider Name         = ").append(provider.getName()).append("\n");
				sb.append("\tCrypto Provider Version      = ").append(provider.getVersion()).append("\n");
				sb.append("\tCrypto Provider Info         = ").append(provider.getInfo()).append("\n");
				sb.append("\tCrypto Provider Class        = ").append(provider.getClass().getName()).append("\n");
				//log.writeTrace(Logger.TRACE, "TripleDES", "getCipher", sb.toString());
				log.debug(sb.toString());
			}catch( Throwable t) {
				//log.writeTrace(Logger.TRACE, "TripleDES", "getCipher", "No se pueden mostrar las propiedades del sistema");
				log.error(t);
			}
			firstTime = false;
		}
		return cipher;
	}
}


/**
 * Ya no se utiliza esta clase como clave; en su lugar se utiliza javax.crypto.spec.SecretKeySpec
 * La mantengo por no quitarla y por cariño :)
 * --JVB
 */
final class TripleDesKey implements SecretKey, Serializable {

	public  static final String ALGORITHM_NAME = "DESede";
	private static final String ALTERNATE_ALGORITHM_NAME = "TripleDES";
	private static final int ALGORITHM_HASH_CODE = ALGORITHM_NAME.hashCode();

	private static final String FORMAT = "RAW";

	private byte[] key = new byte[24];
	private int keyHashCode = -1;


    TripleDesKey(byte[] initBytes) throws InvalidKeyException {

        if (initBytes == null || initBytes.length < 24) {
            throw new InvalidKeyException("Wrong key size");
        }
        else {
            a(initBytes, 0);
            a(initBytes, 8);
            a(initBytes, 16);
            System.arraycopy(initBytes, 0, key, 0, 24);
            return;
		}
	}

    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof SecretKey))
            return false;
        String s = ((SecretKey)obj).getAlgorithm();
        if(!s.equalsIgnoreCase(ALGORITHM_NAME) && !s.equalsIgnoreCase(ALTERNATE_ALGORITHM_NAME)) {
            return false;
        }
        else {
            byte abyte0[] = ((SecretKey)obj).getEncoded();
            boolean flag = Arrays.equals(key, abyte0);
            Arrays.fill(abyte0, (byte)0);
            return flag;
        }
    }

    protected void finalize() throws Throwable {
        if(this.key != null) {
            Arrays.fill(this.key, (byte)0);
            this.key = null;
        }
        this.keyHashCode = -1;
        super.finalize();
    }

    public String getAlgorithm() {
        return ALGORITHM_NAME;
    }

    public byte[] getEncoded() {
        return (byte[])key.clone();
    }

    public String getFormat() {
        return FORMAT;
    }

    public int hashCode() {
    	if (this.keyHashCode==-1) {
	        int i = 0;
	        for(int j = 1; j < key.length; j++)
	            i += key[j] * j;

	        this.keyHashCode = (i ^= ALGORITHM_HASH_CODE);
	     }
	     return this.keyHashCode;
    }

    static void a(byte keyBytes[], int i)
    {
        if(keyBytes == null)
            return;
        for(int j = 0; j < 8; j++)
        {
            int k = 0;
            for(int l = 0; l < B.length; l++)
                if((keyBytes[j + i] & B[l]) == B[l])
                    k++;

            if((k & 1) == 1)
                keyBytes[j + i] = (byte)(keyBytes[j + i] & -2);
            else
                keyBytes[j + i] = (byte)(keyBytes[j + i] | 1);
        }

    }
    private static final byte B[] = { -128, 64, 32, 16, 8, 4, 2 };
}
