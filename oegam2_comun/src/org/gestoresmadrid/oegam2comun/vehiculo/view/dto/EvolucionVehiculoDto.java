package org.gestoresmadrid.oegam2comun.vehiculo.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import utilidades.estructuras.Fecha;

public class EvolucionVehiculoDto implements Serializable {

	private static final long serialVersionUID = 6752817020874775225L;

	private String numColegiado;

	private BigDecimal idVehiculo;

	private Fecha fechaHora;

	private String bastidorAnt;

	private String bastidorNue;

	private String codigoItvAnt;

	private String codigoItvNue;

	private String matriculaAnt;

	private String matriculaNue;

	private BigDecimal numExpediente;

	private String nive;

	private String otros;

	private String tipoActualizacion;

	private String tipoTramite;

	private String tipoVehiculoAnt;

	private String tipoVehiculoNue;

	private UsuarioDto usuario;

	private BigDecimal idVehiculoOrigen;

	// Para la pantalla

	private String tipoActualizacionMas;

	private String resumenCambio;

	private int indice;

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(BigDecimal idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public Fecha getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Fecha fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getBastidorAnt() {
		return bastidorAnt;
	}

	public void setBastidorAnt(String bastidorAnt) {
		this.bastidorAnt = bastidorAnt;
	}

	public String getBastidorNue() {
		return bastidorNue;
	}

	public void setBastidorNue(String bastidorNue) {
		this.bastidorNue = bastidorNue;
	}

	public String getCodigoItvAnt() {
		return codigoItvAnt;
	}

	public void setCodigoItvAnt(String codigoItvAnt) {
		this.codigoItvAnt = codigoItvAnt;
	}

	public String getCodigoItvNue() {
		return codigoItvNue;
	}

	public void setCodigoItvNue(String codigoItvNue) {
		this.codigoItvNue = codigoItvNue;
	}

	public String getMatriculaAnt() {
		return matriculaAnt;
	}

	public void setMatriculaAnt(String matriculaAnt) {
		this.matriculaAnt = matriculaAnt;
	}

	public String getMatriculaNue() {
		return matriculaNue;
	}

	public void setMatriculaNue(String matriculaNue) {
		this.matriculaNue = matriculaNue;
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

	public String getTipoActualizacion() {
		return tipoActualizacion;
	}

	public void setTipoActualizacion(String tipoActualizacion) {
		this.tipoActualizacion = tipoActualizacion;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getTipoVehiculoAnt() {
		return tipoVehiculoAnt;
	}

	public void setTipoVehiculoAnt(String tipoVehiculoAnt) {
		this.tipoVehiculoAnt = tipoVehiculoAnt;
	}

	public String getTipoVehiculoNue() {
		return tipoVehiculoNue;
	}

	public void setTipoVehiculoNue(String tipoVehiculoNue) {
		this.tipoVehiculoNue = tipoVehiculoNue;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getIdVehiculoOrigen() {
		return idVehiculoOrigen;
	}

	public void setIdVehiculoOrigen(BigDecimal idVehiculoOrigen) {
		this.idVehiculoOrigen = idVehiculoOrigen;
	}

	public String getTipoActualizacionMas() {
		String mensaje = this.getTipoActualizacion();
		if ("MODIFICACIÓN".equals(mensaje)) {
			mensaje += " <img style='height:20px;width:20px;cursor:pointer;' src='img/mostrar.gif' onclick='mostrarVentana(event, " + this.getIndice() + ",1)' onmouseout='ocultarVentana()'>";
			mensaje += this.getResumenCambio();
		}
		this.setTipoActualizacionMas(mensaje);
		return tipoActualizacionMas;
	}

	public void setTipoActualizacionMas(String tipoActualizacionMas) {
		this.tipoActualizacionMas = tipoActualizacionMas;
	}

	public String getResumenCambio() {
		// Comprueba si solo hay cambios de tipo 'otros':
		if (soloOtros()) {
			// Devuelve mensaje especifico para solo otros:
			this.setResumenCambio(resumenSoloOtros());
			return resumenCambio;
		}

		String colorFondoTitulo = "#A52642";
		String colorFondoNormal = "#F5E9EB";
		String colorTextoTitulo = "white";
		String colorTextoNormal = "black";

		String mensaje = "<span id='detalles" + this.getIndice() + "' style='display:none;'>";
		if ((null == this.getBastidorAnt() || "".equals(this.getBastidorAnt())) && (null == this.getBastidorNue() || "".equals(this.getBastidorNue()))
				&& (null == this.getCodigoItvAnt() || "".equals(this.getCodigoItvAnt())) && (null == this.getCodigoItvNue() || "".equals(this.getCodigoItvNue()))
				&& (null == this.getMatriculaAnt() || "".equals(this.getMatriculaAnt())) && (null == this.getMatriculaNue() || "".equals(this.getMatriculaNue()))
				&& (null == this.getTipoVehiculoAnt() || "".equals(this.getTipoVehiculoAnt())) && (null == this.getTipoVehiculoNue() || "".equals(this.getTipoVehiculoNue()))
				&& (null == this.getOtros() || "".equals(this.getOtros()))) {
			mensaje += "No hay cambios";
		} else {
			mensaje += "<table>";
			mensaje += "<tr>";
			mensaje += "<td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:70px;'>Atributo</td>";
			mensaje += "<td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:128px;'>Valor anterior</td>";
			mensaje += "<td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:128px;'>Valor nuevo</td>";
			mensaje += "</tr>";
			if ((null != this.getBastidorAnt() && !"".equals(this.getBastidorAnt())) || (null != this.getBastidorNue() && !"".equals(this.getBastidorNue()))) {
				if ((this.getBastidorNue() == null && this.getBastidorAnt() != null) || (!this.getBastidorNue().equals(this.getBastidorAnt()))) {
					mensaje += "<tr>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>Bastidor</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getBastidorAnt() ? this.getBastidorAnt() : "") + "</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getBastidorNue() ? this.getBastidorNue() : "") + "</td>";
					mensaje += "</tr>";
				}
			}
			if (null != this.getCodigoItvAnt() && !"".equals(this.getCodigoItvAnt()) || null != this.getCodigoItvNue() && !"".equals(this.getCodigoItvNue())) {
				if ((this.getCodigoItvNue() == null && this.getCodigoItvAnt() != null) || (!this.getCodigoItvNue().equals(this.getCodigoItvAnt()))) {
					mensaje += "<tr>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>Codigo ITV</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getCodigoItvAnt() ? this.getCodigoItvAnt() : "") + "</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getCodigoItvNue() ? this.getCodigoItvNue() : "") + "</td>";
					mensaje += "</tr>";
				}
			}
			if (null != this.getMatriculaAnt() && !"".equals(this.getMatriculaAnt()) || null != this.getMatriculaNue() && !"".equals(this.getMatriculaNue())) {
				if ((this.getMatriculaNue() == null && this.getMatriculaAnt() != null) || (!this.getMatriculaNue().equals(this.getMatriculaAnt()))) {
					mensaje += "<tr>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>Matrícula</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getMatriculaAnt() ? this.getMatriculaAnt() : "") + "</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getMatriculaNue() ? this.getMatriculaNue() : "") + "</td>";
					mensaje += "</tr>";
				}
			}
			if (null != this.getTipoVehiculoAnt() && !"".equals(this.getTipoVehiculoAnt()) || null != this.getTipoVehiculoNue() && !"".equals(this.getTipoVehiculoNue())) {
				if ((this.getTipoVehiculoNue() == null && this.getTipoVehiculoAnt() != null) || (!this.getTipoVehiculoNue().equals(this.getTipoVehiculoAnt()))) {
					mensaje += "<tr>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>Tipo Vehículo</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getTipoVehiculoAnt() ? this.getTipoVehiculoAnt() : "")
							+ "</td>";
					mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + (null != this.getTipoVehiculoNue() ? this.getTipoVehiculoNue() : "")
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

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	private boolean soloOtros() {
		if ((bastidorAnt == null && bastidorNue == null && codigoItvAnt == null && codigoItvNue == null && matriculaAnt == null && matriculaNue == null && tipoVehiculoAnt == null && tipoVehiculoNue == null)
				&& otros != null) {
			return true;
		}
		return false;
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

	public String getOtrosTabulado() {
		String otros = this.getOtros();
		String[] aux = otros.split(",");
		ArrayList<String> lineas = new ArrayList<>();
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
}