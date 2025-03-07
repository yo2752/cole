package org.gestoresmadrid.oegam2.DIRe.controller.action;

import java.math.BigDecimal;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.ServicioEmpresaDIRe;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans.ResultConsultaEmpresaDIReBean;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.dto.EmpresaDIReDto;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.dto.EmpresaDIRe_ContactoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;

public class ModificarEmpresaDIReAction extends ActionBase {

	private static final long serialVersionUID = 790992486170737819L;
	private static final String MODIFICAR_EMPRESA = "modificar_ModificarEmpresaDIRe";

	private EmpresaDIReDto empresaDIReDto;
	private BigDecimal numExpediente;
	private EmpresaDIRe_ContactoDto empresaDIRe_ContactoDto;

	// Contactos
	private String Contacto_nombre;
	private String Contacto_apellido1;
	private String Contacto_apellido2;
	private String Contacto_descripcion;

	@Autowired
	private ServicioEmpresaDIRe servicioEmpresaDIRe;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String modificar() {
		empresaDIRe_ContactoDto= new EmpresaDIRe_ContactoDto();
		if (numExpediente != null) {
			getConductorDto(numExpediente, "Cargado correctamente");
		} else {
			cargarValoresIniciales();
		}
		return MODIFICAR_EMPRESA;
	}

	public String guardar(){
		ResultConsultaEmpresaDIReBean resultado = servicioEmpresaDIRe.guardarEmpresaDIRe(empresaDIReDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()){
			addActionError(resultado.getMensaje());
		} else {
			getConductorDto(resultado.getNumExpediente(), "Conductor guardado correctamente");
		}
		return MODIFICAR_EMPRESA;
	}

	public String validar() {
		ResultConsultaEmpresaDIReBean resultado = servicioEmpresaDIRe.validarEmpresaDIRe(empresaDIReDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getConductorDto(resultado.getNumExpediente(), "Conductor validado correctamente");
		}
		return MODIFICAR_EMPRESA;
	}

	public String consultar(){
		ResultConsultaEmpresaDIReBean resultado = servicioEmpresaDIRe.consultarEmpresaDIRe(empresaDIReDto.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getConductorDto(resultado.getNumExpediente(), "Consultar de conductor generada.");
		}

		return MODIFICAR_EMPRESA;
	}

	private void cargarValoresIniciales() {
		empresaDIReDto = new EmpresaDIReDto();

		if (!utilesColegiado.tienePermisoAdmin()) {
			ContratoDto contrato = new ContratoDto();
			contrato.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			empresaDIReDto.setContrato(contrato);
			empresaDIReDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
		empresaDIReDto.setOperacion(TipoOperacionCaycEnum.Modif_Conductor.getValorEnum());
	}

	private void getConductorDto(BigDecimal numExpedienteDto, String mensajeOk) {
		numExpediente = (BigDecimal) numExpedienteDto;
		ResultConsultaEmpresaDIReBean resultado = servicioEmpresaDIRe.getEmpresaDIReDto(numExpediente);

		if (resultado.getError()) {
			addActionError("Error al recuperar el conductor habitual guardado con numExpediente:" + numExpediente);
		} else {
			empresaDIReDto = (EmpresaDIReDto) resultado.getEmpresaDIReDto();
			addActionMessage(mensajeOk);
		}
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getContacto_nombre() {
		return Contacto_nombre;
	}

	public void setContacto_nombre(String contacto_nombre) {
		Contacto_nombre = contacto_nombre;
	}

	public String getContacto_apellido1() {
		return Contacto_apellido1;
	}

	public void setContacto_apellido1(String contacto_apellido1) {
		Contacto_apellido1 = contacto_apellido1;
	}

	public String getContacto_apellido2() {
		return Contacto_apellido2;
	}

	public void setContacto_apellido2(String contacto_apellido2) {
		Contacto_apellido2 = contacto_apellido2;
	}

	public String getContacto_descripcion() {
		return Contacto_descripcion;
	}

	public void setContacto_descripcion(String contacto_descripcion) {
		Contacto_descripcion = contacto_descripcion;
	}

}