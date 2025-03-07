package org.gestoresmadrid.core.model.security.utils;

import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class Cryptographer {
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(Cryptographer.class);

	@Autowired
	private CryptoProvider cryptoProvider;

	/**
	 * Encripta
	 * 
	 * @return
	 */
	public String encrypt(String texto) {
		String secret = null;
		if (texto != null) {
			SecretKey key = cryptoProvider.getSecretKey();
			Cipher cipher = cryptoProvider.getCypher();
			if (key != null && cipher != null) {
				try {
					cipher.init(Cipher.ENCRYPT_MODE, key);
					secret = Base64.encode(cipher.doFinal(texto.getBytes()));
				} catch (InvalidKeyException e) {
					LOG.error("Error, clave no valida");
				} catch (IllegalBlockSizeException e) {
					LOG.error("Error 1");
				} catch (BadPaddingException e) {
					LOG.error("Error 2");
				}
			}
		}
		return secret;
	}

	/**
	 * Desencripta
	 * 
	 * @return
	 */
	public String decrypt(String secret) {
		String texto = null;
		if (secret != null) {
			SecretKey clave = cryptoProvider.getSecretKey();
			Cipher cipher = cryptoProvider.getCypher();
			if (clave != null && cipher != null) {
				try {
					cipher.init(Cipher.DECRYPT_MODE, clave);
					texto = new String(cipher.doFinal(Base64.decode(secret)));
				} catch (InvalidKeyException e) {
					LOG.error("Error, clave no valida");
				} catch (IllegalBlockSizeException e) {
					LOG.error("Error 1");
				} catch (BadPaddingException e) {
					LOG.error("Error 2");
				} catch (Base64DecodingException e) {
					LOG.error("Error 3");
				}
			}
		}
		return texto;
	}

	
	public CryptoProvider getCryptoProvider() {
		return cryptoProvider;
	}

	public void setCryptoProvider(CryptoProvider cryptoProvider) {
		this.cryptoProvider = cryptoProvider;
	}

}
