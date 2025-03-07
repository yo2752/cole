package org.gestoresmadrid.oegam2comun.personas.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import utilidades.estructuras.Fecha;

public class EvolucionPersonaDto implements Serializable {

	private static final long serialVersionUID = -5453985762498129931L;

	private String numColegiado;

	private String nif;

	private Fecha fechaHora;

	private String apellido1Ant;

	private String apellido1Nue;

	private String apellido2Ant;

	private String apellido2Nue;

	private Fecha fechaNacimientoAnt;

	private Fecha fechaNacimientoNue;

	private String nombreAnt;

	private String nombreNue;

	private BigDecimal numExpediente;

	private String otros;

	private String tipoActuacion;

	private String tipoTramite;

	private String tipoTramiteDesc;

	private PersonaDto personaDto;

	private UsuarioDto usuario;

	// Para la pantalla

	private String fechaHoraString;

	private String tipoActuacionMas;

	private String resumenCambio;

	private int indice;

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Fecha getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Fecha fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getApellido1Ant() {
		return apellido1Ant;
	}

	public void setApellido1Ant(String apellido1Ant) {
		this.apellido1Ant = apellido1Ant;
	}

	public String getApellido1Nue() {
		return apellido1Nue;
	}

	public void setApellido1Nue(String apellido1Nue) {
		this.apellido1Nue = apellido1Nue;
	}

	public String getApellido2Ant() {
		return apellido2Ant;
	}

	public void setApellido2Ant(String apellido2Ant) {
		this.apellido2Ant = apellido2Ant;
	}

	public String getApellido2Nue() {
		return apellido2Nue;
	}

	public void setApellido2Nue(String apellido2Nue) {
		this.apellido2Nue = apellido2Nue;
	}

	public Fecha getFechaNacimientoAnt() {
		return fechaNacimientoAnt;
	}

	public void setFechaNacimientoAnt(Fecha fechaNacimientoAnt) {
		this.fechaNacimientoAnt = fechaNacimientoAnt;
	}

	public Fecha getFechaNacimientoNue() {
		return fechaNacimientoNue;
	}

	public void setFechaNacimientoNue(Fecha fechaNacimientoNue) {
		this.fechaNacimientoNue = fechaNacimientoNue;
	}

	public String getNombreAnt() {
		return nombreAnt;
	}

	public void setNombreAnt(String nombreAnt) {
		this.nombreAnt = nombreAnt;
	}

	public String getNombreNue() {
		return nombreNue;
	}

	public void setNombreNue(String nombreNue) {
		this.nombreNue = nombreNue;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getOtros() {
		return otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getTipoTramiteDesc() {
		return tipoTramiteDesc;
	}

	public void setTipoTramiteDesc(String tipoTramiteDesc) {
		this.tipoTramiteDesc = tipoTramiteDesc;
	}

	public PersonaDto getPersonaDto() {
		return personaDto;
	}

	public void setPersonaDto(PersonaDto personaDto) {
		this.personaDto = personaDto;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public String getFechaHoraString() {
		return fechaHoraString;
	}

	public void setFechaHoraString(String fechaHoraString) {
		this.fechaHoraString = fechaHoraString;
	}

	public String getTipoActuacionMas() {
		String mensaje = this.getTipoActuacion();
		if ("MODIFICACIÓN".equals(mensaje)) {
			mensaje += " <img style='height:20px;width:20px;cursor:pointer;' src='img/mostrar.gif' onclick='mostrarVentana(event, " + this.getIndice() + ",1)' onmouseout='ocultarVentana()'>";
			mensaje += this.getResumenCambio();
		}
		this.setTipoActuacionMas(mensaje);
		return tipoActuacionMas;
	}

	public void setTipoActuacionMas(String tipoActuacionMas) {
		this.tipoActuacionMas = tipoActuacionMas;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public String getResumenCambio() {
		if (soloOtros()) {
			this.setResumenCambio(resumenSoloOtros());
			return resumenCambio;
		}

		String colorFondoTitulo = "#A52642";
		String colorFondoNormal = "#F5E9EB";
		String colorTextoTitulo = "white";
		String colorTextoNormal = "black";

		String mensaje = "<span id='detalles" + this.getIndice() + "' style='display:none;'>";
		if ((null == this.getNombreAnt() || "".equals(this.getNombreAnt())) && (null == this.getNombreNue() || "".equals(this.getNombreNue()))
				&& (null == this.getApellido1Ant() || "".equals(this.getApellido1Ant())) && (null == this.getApellido1Nue() || "".equals(this.getApellido1Nue()))
				&& (null == this.getApellido2Ant() || "".equals(this.getApellido2Ant())) && (null == this.getApellido2Nue() || "".equals(this.getApellido2Nue()))
				&& (null == this.getFechaNacimientoAnt() || "".equals(this.getFechaNacimientoAnt())) && (null == this.getFechaNacimientoNue() || "".equals(this.getFechaNacimientoNue()))
				&& (null == this.getOtros() || "".equals(this.getOtros()))) {
			mensaje += "No hay cambios";
		} else {
			mensaje += "<table>";
			mensaje += "<tr>";
			mensaje += "<td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:70px;'>Atributo</td>";
			mensaje += "<td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:128px;'>Valor anterior</td>";
			mensaje += "<td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:128px;'>Valor nuevo</td>";
			mensaje += "</tr>";
			if ((null != this.getNombreAnt() && !"".equals(this.getNombreAnt())) || (null != this.getNombreNue() && !"".equals(this.getNombreNue()))) {
				if ((this.getNombreNue() == null && this.getNombreAnt() != null) || (!this.getNombreNue().equals(this.getNombreAnt()))) {
					mensaje += "<tr>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>Nombre</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getNombreAnt() ? this.getNombreAnt() : "") + "</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getNombreNue() ? this.getNombreNue() : "") + "</td>";
					mensaje += "</tr>";
				}
			}
			if (null != this.getApellido1Ant() && !"".equals(this.getApellido1Ant()) || null != this.getApellido1Nue() && !"".equals(this.getApellido1Nue())) {
				if ((this.getApellido1Nue() == null && this.getApellido1Ant() != null) || (!this.getApellido1Nue().equals(this.getApellido1Ant()))) {
					mensaje += "<tr>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>Apellido1/Razón social</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getApellido1Ant() ? this.getApellido1Ant() : "") + "</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getApellido1Nue() ? this.getApellido1Nue() : "") + "</td>";
					mensaje += "</tr>";
				}
			}
			if (null != this.getApellido2Ant() && !"".equals(this.getApellido2Ant()) || null != this.getApellido2Nue() && !"".equals(this.getApellido2Nue())) {
				if ((this.getApellido2Nue() == null && this.getApellido2Ant() != null) || (!this.getApellido2Nue().equals(this.getApellido2Ant()))) {
					mensaje += "<tr>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>Apellido 2</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getApellido2Ant() ? this.getApellido2Ant() : "") + "</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getApellido2Nue() ? this.getApellido2Nue() : "") + "</td>";
					mensaje += "</tr>";
				}
			}
			if (null != this.getFechaNacimientoAnt() && !"".equals(this.getFechaNacimientoAnt()) || null != this.getFechaNacimientoNue() && !"".equals(this.getFechaNacimientoNue())) {
				if ((this.getFechaNacimientoNue() == null && this.getFechaNacimientoAnt() != null) || (!this.getFechaNacimientoNue().equals(this.getFechaNacimientoAnt()))) {
					mensaje += "<tr>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>Fecha Nacimiento</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getFechaNacimientoAnt() ? this.getFechaNacimientoAnt() : "")
							+ "</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getFechaNacimientoNue() ? this.getFechaNacimientoNue() : "")
							+ "</td>";
					mensaje += "</tr>";
				}
			}
			if (null != this.getOtros() && !"".equals(this.getOtros())) {
				mensaje += "<tr><td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:340px;' colspan='3'>Otros atributos modificados</td></tr>";
				mensaje += "<tr><td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";' colspan='3'>" + this.getOtrosTabulado() + "</td></tr>";
			}
			mensaje += "</table>";
		}
		mensaje += "</span>";
		this.setResumenCambio(mensaje);

		return resumenCambio;
	}

	public void setResumenCambio(String resumenCambio) {
		this.resumenCambio = resumenCambio;
	}

	public String getOtrosTabulado() {
		String otros = this.getOtros();
		String[] aux = otros.split(",");
		ArrayList<String> lineas = new ArrayList<String>();
		int max = 40;
		String lineaProvisional = "";
		for (int i = 0; i < aux.length; i++) {
			if ((lineaProvisional.length() + 2 + aux[i].length()) < max) {
				if (i < aux.length - 1)
					lineaProvisional += aux[i] + ", ";
				else
					lineaProvisional += aux[i];
				if (i == (aux.length - 1)) {
					lineas.add(lineaProvisional);
				}
			} else {
				lineas.add(new String(lineaProvisional));
				if (i < aux.length - 1)
					lineaProvisional = aux[i] + ", ";
				else
					lineaProvisional = aux[i];
				if (i == (aux.length - 1)) {
					lineas.add(lineaProvisional);
				}
			}
		}
		String respuesta = "";
		for (int i = 0; i < lineas.size(); i++) {
			if (i < (lineas.size() - 1))
				respuesta += lineas.get(i) + "<br>";
			else
				respuesta += lineas.get(i);
		}
		return respuesta;
	}

	public String resumenSoloOtros() {
		String colorFondoTitulo = "#A52642";
		String colorFondoNormal = "#F5E9EB";
		String colorTextoTitulo = "white";
		String colorTextoNormal = "black";

		String mensaje = "<span id='detalles" + this.getIndice() + "' style='display:none;'>";

		mensaje += "<table>";
		mensaje += "<tr>";
		mensaje += "<td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:340px;'>Atributos modificados</td>";
		mensaje += "</tr>";

		if (null != this.getOtros() && !"".equals(this.getOtros())) {
			mensaje += "<tr><td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + getOtrosTabulado() + "</td></tr>";
		}
		mensaje += "</table>";

		mensaje += "</span>";
		return mensaje;
	}

	private boolean soloOtros() {
		if ((apellido1Nue == null && apellido1Ant == null && apellido2Nue == null && apellido2Ant == null && nombreAnt == null && nombreNue == null && fechaNacimientoAnt == null && fechaNacimientoNue == null)
				&& otros != null) {
			return true;
		}
		return false;
	}
}