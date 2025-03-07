package escrituras.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class UsuarioAlta implements Serializable {

	private BigDecimal idUsuario;
	private String numColegiado;
	private BigDecimal estadoUsuario;
	private String nif;
	private String apellidosNombre;
	private String anagrama;
	private String correoElectronico;

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getEstadoUsuario() {
		return estadoUsuario;
	}

	public void setEstadoUsuario(BigDecimal estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getApellidosNombre() {
		return apellidosNombre;
	}

	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}

	public String getAnagrama() {
		return anagrama;
	}

	public void setAnagrama(String anagrama) {
		this.anagrama = anagrama;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

}