package trafico.beans.utiles;

import java.sql.Timestamp;

/**
 * SELECT a.NUM_EXPEDIENTE, a.ACCION, a.FECHA_INICIO, a.FECHA_FIN, a.RESPUESTA,  u.apellidos_nombre
                      FROM ACCION_TRAMITE a, usuario u'
 * @author juan.gomez
 *
 */
public class beanAccionCursor {

	private String ACCION;
	private Timestamp FECHA_INICIO;
	private Timestamp FECHA_FIN;
	private String RESPUESTA;
	private String apellidos_nombre;

	public beanAccionCursor() {
		super();
	}

	public String getACCION() {
		return ACCION;
	}

	public void setACCION(String aCCION) {
		ACCION = aCCION;
	}

	public Timestamp getFECHA_INICIO() {
		return FECHA_INICIO;
	}

	public void setFECHA_INICIO(Timestamp fECHAINICIO) {
		FECHA_INICIO = fECHAINICIO;
	}

	public Timestamp getFECHA_FIN() {
		return FECHA_FIN;
	}

	public void setFECHA_FIN(Timestamp fECHAFIN) {
		FECHA_FIN = fECHAFIN;
	}

	public String getRESPUESTA() {
		return RESPUESTA;
	}

	public void setRESPUESTA(String rESPUESTA) {
		RESPUESTA = rESPUESTA;
	}

	public String getApellidos_nombre() {
		return apellidos_nombre;
	}

	public void setApellidos_nombre(String apellidosNombre) {
		apellidos_nombre = apellidosNombre;
	}

}