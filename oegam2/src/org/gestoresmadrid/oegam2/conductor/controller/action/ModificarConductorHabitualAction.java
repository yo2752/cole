package org.gestoresmadrid.oegam2.conductor.controller.action;

import java.math.BigDecimal;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum;
import org.gestoresmadrid.oegam2comun.conductor.model.service.ServicioConductor;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ResultConsultaConductorBean;
import org.gestoresmadrid.oegam2comun.conductor.view.dto.ConductorDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;

public class ModificarConductorHabitualAction extends ActionBase {

	private static final long serialVersionUID = 790992486170737819L;
	private static final String MODIFICAR_CONDUCTOR = "altaConductorHabitual";

	private ConductorDto conductorDto;
	private BigDecimal numExpediente;

	@Autowired
	private ServicioConductor servicioConductor;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String modificar() {
		if (numExpediente != null) {
			getConductorDto(numExpediente, "Cargado correctamente");
		} else {
			cargarValoresIniciales();
		}
		return MODIFICAR_CONDUCTOR;
	}

	public String guardar() {
		ResultConsultaConductorBean resultado = servicioConductor.guardarConductor(conductorDto,
				utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getConductorDto(resultado.getNumExpediente(), "Conductor guardado correctamente");
		}
		return MODIFICAR_CONDUCTOR;
	}

	public String validar() {
		ResultConsultaConductorBean resultado = servicioConductor.validarConductor(conductorDto,
				utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getConductorDto(resultado.getNumExpediente(), "Conductor validado correctamente");
		}
		return MODIFICAR_CONDUCTOR;
	}

	public String consultar() {
		ResultConsultaConductorBean resultado = servicioConductor.consultarConductor(conductorDto.getNumExpediente(),
				utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getConductorDto(resultado.getNumExpediente(), "Consultar de conductor generada.");
		}

		return MODIFICAR_CONDUCTOR;
	}

	private void cargarValoresIniciales() {
		conductorDto = new ConductorDto();
		if (!utilesColegiado.tienePermisoAdmin()) {
			ContratoDto contrato = new ContratoDto();
			contrato.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			conductorDto.setContrato(contrato);
			conductorDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
		conductorDto.setOperacion(TipoOperacionCaycEnum.Modif_Conductor.getValorEnum());
	}

	private void getConductorDto(BigDecimal numExpedienteDto, String mensajeOk) {
		numExpediente = (BigDecimal) numExpedienteDto;
		ResultConsultaConductorBean resultado = servicioConductor.getConductorDto(numExpediente);

		if (resultado.getError()) {
			addActionError("Error al recuperar el conductor habitual guardado con numExpediente:" + numExpediente);
		} else {
			conductorDto = (ConductorDto) resultado.getConductorDto();
			addActionMessage(mensajeOk);
		}
	}

	public ConductorDto getConductorDto() {
		return conductorDto;
	}

	public void setConductorDto(ConductorDto conductorDto) {
		this.conductorDto = conductorDto;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

}