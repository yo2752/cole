package org.gestoresmadrid.core.model.security.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public interface CryptoProvider {
	/**
	 * Obtiene la clave para encriptar/desencriptar
	 * 
	 * @return
	 */
	SecretKey getSecretKey();

	/**
	 * Obtiene el Cipher configurado
	 * 
	 * @return
	 */
	Cipher getCypher();

	/**
	 * Genera una nueva clave
	 * 
	 * @param path
	 */
	void generateKey(String path);
}
