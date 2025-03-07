package utiles.correo;

import java.io.Serializable;

public class FicheroAdjunto implements Serializable{

	private static final long serialVersionUID = -8682991294270385556L;
	
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
