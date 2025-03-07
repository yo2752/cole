package org.gestoresmadrid.utilidades.utilesCorreo;

import java.io.Serializable;

public class FicheroAdjunto implements Serializable {

	private static final long serialVersionUID = 1008621470662936545L;

	private String nombreDocumento;
	private byte[] ficheroByte;

	/**
	 * @return the nombreDocumento
	 */
	public String getNombreDocumento() {
		return nombreDocumento;
	}

	/**
	 * @param nombreDocumento the nombreDocumento to set
	 */
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	/**
	 * @return the ficheroByte
	 */
	public byte[] getFicheroByte() {
		return ficheroByte;
	}

	/**
	 * @param ficheroByte the ficheroByte to set
	 */
	public void setFicheroByte(byte[] ficheroByte) {
		this.ficheroByte = ficheroByte;
	}

}