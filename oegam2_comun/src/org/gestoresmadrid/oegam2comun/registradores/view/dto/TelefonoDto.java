package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class TelefonoDto implements Serializable {

	private static final long serialVersionUID = 3806919793887989010L;
	
	private long idTelefono;

	private Timestamp fecCreacion;

	private Timestamp fecModificacion;

	private String numero;

	private String tipo;

	private IntervinienteRegistroDto intervinienteRegistro;

	public long getIdTelefono() {
		return idTelefono;
	}

	public void setIdTelefono(long idTelefono) {
		this.idTelefono = idTelefono;
	}

	public Timestamp getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public IntervinienteRegistroDto getIntervinienteRegistro() {
		return intervinienteRegistro;
	}

	public void setIntervinienteRegistro(IntervinienteRegistroDto intervinienteRegistro) {
		this.intervinienteRegistro = intervinienteRegistro;
	}
}
