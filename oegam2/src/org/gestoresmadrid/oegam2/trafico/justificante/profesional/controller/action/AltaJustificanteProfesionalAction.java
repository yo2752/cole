package org.gestoresmadrid.oegam2.trafico.justificante.profesional.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaJustificanteProfesionalAction extends ActionBase{

	private static final long serialVersionUID = -6623033417547988314L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaJustificanteProfesionalAction.class);
	private static final String IMPRIMIR_JP = "imprimirJp";

	private JustificanteProfDto justificanteProfDto;
	private IntervinienteTraficoDto titular;
	private boolean checkIdFuerzasArmadas;
	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private boolean vieneDeResumen = false;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public String inicio() {
		rellenarDatosMinimos();
		return SUCCESS;
	}

	public String detalle(){
		if (justificanteProfDto != null && justificanteProfDto.getIdJustificanteInterno() != null) {
			obtenerJustificante(justificanteProfDto.getIdJustificanteInterno(), Boolean.TRUE, null);
		} else {
			rellenarDatosMinimos();
		}
		return SUCCESS;
	}

	public String generarJustificante() throws Throwable {
		ResultBean result = servicioJustificanteProfesional.generarJP(justificanteProfDto, titular,
				checkIdFuerzasArmadas, utilesColegiado.getIdUsuarioSessionBigDecimal(),
				utilesColegiado.tienePermisoAdmin());
		if (!result.getError()) {
			Long idJustificanteInterno = (Long) result.getAttachment(ServicioJustificanteProfesional.ID_JUSTIFICANTE_INTERNO);
			obtenerJustificante(idJustificanteInterno, Boolean.FALSE,result.getMensaje());
		} else {
			aniadirMensajeError(result);
		}
		return SUCCESS;
	}

	public String forzarJustificante(){
		if (!utilesColegiado.tienePermisoAdmin()) {
			addActionError("No tiene permisos de administrador para realizar esta acción sobre el justificante.");
		} else {
			ResultBean resultado = servicioJustificanteProfesional.forzarJP(justificanteProfDto.getIdJustificanteInterno(), utilesColegiado.getIdUsuarioSessionBigDecimal(), false);
			if (!resultado.getError()) {
				Long idJustificanteInterno = (Long) resultado.getAttachment(ServicioJustificanteProfesional.ID_JUSTIFICANTE_INTERNO);
				obtenerJustificante(idJustificanteInterno, Boolean.FALSE, resultado.getMensaje());
			} else {
				obtenerJustificante(justificanteProfDto.getIdJustificanteInterno(), Boolean.FALSE,null);
				aniadirMensajeError(resultado);
			}
		}
		return SUCCESS;
	}

	public String anularJustificante() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			addActionError("No tiene permisos de administrador para realizar esta acción sobre el justificante.");
		} else {
			ResultBean resultado = servicioJustificanteProfesional.anularJP(justificanteProfDto.getIdJustificanteInterno(), utilesColegiado.getIdUsuarioSessionBigDecimal(), false);
			if (!resultado.getError()) {
				Long idJustificanteInterno = (Long) resultado.getAttachment(ServicioJustificanteProfesional.ID_JUSTIFICANTE_INTERNO);
				obtenerJustificante(idJustificanteInterno, Boolean.FALSE,resultado.getMensaje());
			} else {
				aniadirMensajeError(resultado);
			}
		}
		return SUCCESS;
	}

	public String pteAutoColegioJustificante() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			addActionError("No tiene permisos de administrador para realizar esta acción sobre el justificante.");
		} else {
			ResultBean resultado = servicioJustificanteProfesional.pendienteAutorizacionColegioJP(
					justificanteProfDto.getIdJustificanteInterno(), utilesColegiado.getIdUsuarioSessionBigDecimal(),
					false);
			if (!resultado.getError()) {
				Long idJustificanteInterno = (Long) resultado.getAttachment(ServicioJustificanteProfesional.ID_JUSTIFICANTE_INTERNO);
				obtenerJustificante(idJustificanteInterno, Boolean.FALSE,resultado.getMensaje());
			} else {
				aniadirMensajeError(resultado);
			}
		}
		return SUCCESS;
	}

	public String imprimirJustificante() {
		ResultBean resultado = servicioJustificanteProfesional.imprimirJP(justificanteProfDto.getIdJustificanteInterno());
		if (!resultado.getError()) {
			try {
				inputStream = new FileInputStream((File)resultado.getAttachment(ServicioJustificanteProfesional.FICHERO_JP));
				fileName = (String)resultado.getAttachment(ServicioJustificanteProfesional.NOMBRE_FICHERO_JP);
				return IMPRIMIR_JP;
			} catch (FileNotFoundException e) {
				log.error("Ha sucedido un error a la hora de imprimir el Justificante, error: ", e);
				addActionError("Ha sucedido un error a la hora de imprimir el Justificante.");
			}
		} else {
			addActionError(resultado.getMensaje());
		}
		return SUCCESS;
	}

	private void obtenerJustificante(Long idJustificanteInterno,Boolean esDetalle, String mensajeOk) {
		ResultBean resultado = servicioJustificanteProfesional.getJustificanteProfesionalPantalla(idJustificanteInterno);
		if (!resultado.getError()) {
			justificanteProfDto = (JustificanteProfDto) resultado.getAttachment(ServicioJustificanteProfesional.DTO_JUSTIFICANTE);
			titular = (IntervinienteTraficoDto) resultado.getAttachment(ServicioJustificanteProfesional.TITULAR_JUST_PROF);
			if (mensajeOk != null && !mensajeOk.isEmpty()) {
				addActionMessage(mensajeOk);
			}
		} else {
			if (esDetalle) {
				rellenarDatosMinimos();
			}
			aniadirMensajeError(resultado);
		}
	}

	private void rellenarDatosMinimos() {
		if (justificanteProfDto == null) {
			justificanteProfDto = new JustificanteProfDto();
			justificanteProfDto.setTramiteTrafico(new TramiteTrafDto());
		}
		if (justificanteProfDto.getTramiteTrafico().getJefaturaTraficoDto() == null) {
			justificanteProfDto.getTramiteTrafico().setJefaturaTraficoDto(new JefaturaTraficoDto());
		}
		justificanteProfDto.getTramiteTrafico().getJefaturaTraficoDto().setJefaturaProvincial(utilesColegiado.getIdJefaturaSesion());
		justificanteProfDto.getTramiteTrafico().setNumColegiado(utilesColegiado.getNumColegiadoSession());
		justificanteProfDto.getTramiteTrafico().setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
	}

	public JustificanteProfDto getJustificanteProfDto() {
		return justificanteProfDto;
	}

	public void setJustificanteProfDto(JustificanteProfDto justificanteProfDto) {
		this.justificanteProfDto = justificanteProfDto;
	}

	public IntervinienteTraficoDto getTitular() {
		return titular;
	}

	public void setTitular(IntervinienteTraficoDto titular) {
		this.titular = titular;
	}

	public boolean isCheckIdFuerzasArmadas() {
		return checkIdFuerzasArmadas;
	}

	public void setCheckIdFuerzasArmadas(boolean checkIdFuerzasArmadas) {
		this.checkIdFuerzasArmadas = checkIdFuerzasArmadas;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public boolean isVieneDeResumen() {
		return vieneDeResumen;
	}

	public void setVieneDeResumen(boolean vieneDeResumen) {
		this.vieneDeResumen = vieneDeResumen;
	}

}