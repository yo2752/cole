package org.gestoresmadrid.oegam2.arrendatarios.controller.action;

import java.math.BigDecimal;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum;
import org.gestoresmadrid.oegam2comun.arrendatarios.model.service.ServicioArrendatario;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultConsultaArrendatarioBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.dto.ArrendatarioDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;

public class AltaArrendatariosAction extends ActionBase {
	private static final long serialVersionUID = 1L;
	private static final String ALTA_ARRENDATARIO = "altaArrendatario";

	@Autowired
	private ServicioArrendatario servicioArrendatario;

	@Autowired
	UtilesColegiado utilesColegiado;

	private ArrendatarioDto arrendatarioDto;
	private BigDecimal numExpediente;

	public String alta() {
		if (numExpediente != null) {
			getArrendatarioDto(numExpediente, "Cargado correctamente");
		} else {
			cargarValoresIniciales();
		}
		return ALTA_ARRENDATARIO;
	}

	public String guardar() {
		ResultConsultaArrendatarioBean resultado = servicioArrendatario.guardarArrendatario(arrendatarioDto,
				utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getArrendatarioDto(resultado.getNumExpediente(), "Arrendatario guardado correctamente");
		}
		return ALTA_ARRENDATARIO;
	}

	public String validar() {
		ResultConsultaArrendatarioBean resultado = servicioArrendatario.validarArrendatario(arrendatarioDto,
				utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getArrendatarioDto(resultado.getNumExpediente(), "Arrendatario validado correctamente");
		}
		return ALTA_ARRENDATARIO;
	}

	public String consultar() {
		if (arrendatarioDto == null) {
			addActionError("");
		}
		ResultConsultaArrendatarioBean resultado = servicioArrendatario.consultarArrendatario(
				arrendatarioDto.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getArrendatarioDto(resultado.getNumExpediente(), "Consulta arrendatario generada.");
		}
		return ALTA_ARRENDATARIO;
	}

	private void cargarValoresIniciales() {
		arrendatarioDto = new ArrendatarioDto();
		if (!utilesColegiado.tienePermisoAdmin()) {
			ContratoDto contrato = new ContratoDto();
			contrato.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			arrendatarioDto.setContrato(contrato);
			arrendatarioDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
		arrendatarioDto.setOperacion(TipoOperacionCaycEnum.Alta_Arrendatario.toString());
	}

	private void getArrendatarioDto(BigDecimal numExpedienteDto, String mensajeOk) {
		numExpediente = numExpedienteDto;
		ResultConsultaArrendatarioBean resultado = servicioArrendatario.getArrendatarioDto(numExpediente);
		if (resultado.getError()) {
			addActionError("Error al recuperar el arrendatario guardado con numExpediente:" + numExpediente);
		} else {
			arrendatarioDto = resultado.getArrendatarioDto();
			addActionMessage(mensajeOk);
		}
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public ArrendatarioDto getArrendatarioDto() {
		return arrendatarioDto;
	}

	public void setArrendatarioDto(ArrendatarioDto arrendatarioDto) {
		this.arrendatarioDto = arrendatarioDto;
	}

}