package org.gestoresmadrid.oegam2.atex5.controller.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioVehiculoAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaVehiculoAtex5Dto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaConsultaVehiculoAtex5Action extends ActionBase {

	private static final long serialVersionUID = 4745470136528617768L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaConsultaVehiculoAtex5Action.class);

	private static final String ALTA_CONSULTA_VEHICULO_ATEX5 = "altaVehiculoAtex5";
	private static final String DESCARGA_PDF = "descargarPdf";

	private BigDecimal numExpediente;
	private ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto;

	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	@Autowired
	private ServicioVehiculoAtex5 servicioVehiculoAtex5;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String alta() {
		if (numExpediente != null) {
			ResultadoAtex5Bean resultado = servicioVehiculoAtex5.getConsultaVehiculoAtex5(numExpediente);
			if (!resultado.getError()) {
				consultaVehiculoAtex5Dto = resultado.getConsultaVehiculoAtex5Dto();
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			cargarValoresIniciales();
		}
		return ALTA_CONSULTA_VEHICULO_ATEX5;
	}

	public String guardar() {
		ResultadoAtex5Bean resultado = servicioVehiculoAtex5.guardarConsultaVehiculoAtex5(consultaVehiculoAtex5Dto,
				utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getConsultaVehiculoAtex5Dto(resultado, "Consulta Vehiculo Atex5 guardada.");
		}
		return ALTA_CONSULTA_VEHICULO_ATEX5;
	}

	public String consultar() {
		ResultadoAtex5Bean resultado = servicioVehiculoAtex5.consultarVehiculoAtex5(consultaVehiculoAtex5Dto,
				utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getConsultaVehiculoAtex5Dto(resultado, "Consulta Vehiculo Atex5 generada.");
		}
		return ALTA_CONSULTA_VEHICULO_ATEX5;
	}

	public String imprimirPdf() {
		String pagina = "";
		ResultadoAtex5Bean resultado = servicioVehiculoAtex5.descargarPdf(consultaVehiculoAtex5Dto.getNumExpediente());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			resultado.setNumExpediente(consultaVehiculoAtex5Dto.getNumExpediente());
			getConsultaVehiculoAtex5Dto(resultado, null);
			pagina = ALTA_CONSULTA_VEHICULO_ATEX5;
		} else {
			try {
				inputStream = new FileInputStream(resultado.getFicheroDescarga());
				fileName = resultado.getNombreFichero();
				addActionMessage("La impresion se ha realizado correctamente");
				pagina = DESCARGA_PDF;
			} catch (FileNotFoundException e) {
				log.error("No existe el fichero a imprimir,error:", e);
				addActionError("No existe el fichero a imprimir");
				pagina = ALTA_CONSULTA_VEHICULO_ATEX5;
			}
		}
		return pagina;
	}

	private void cargarValoresIniciales() {
		consultaVehiculoAtex5Dto = new ConsultaVehiculoAtex5Dto();
		if (!utilesColegiado.tienePermisoAdmin()) {
			ContratoDto contrato = new ContratoDto();
			contrato.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			consultaVehiculoAtex5Dto.setContrato(contrato);
			consultaVehiculoAtex5Dto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	private void getConsultaVehiculoAtex5Dto(ResultadoAtex5Bean resultado, String mensajeOk) {
		numExpediente = resultado.getNumExpediente();
		List<String> listaMensajesAvisos = null;
		if (resultado.getListaErrores() != null && !resultado.getListaErrores().isEmpty()) {
			listaMensajesAvisos = resultado.getListaErrores();
		}
		resultado = servicioVehiculoAtex5.getConsultaVehiculoAtex5(numExpediente);
		if (!resultado.getError()) {
			consultaVehiculoAtex5Dto = resultado.getConsultaVehiculoAtex5Dto();
			if (mensajeOk != null && !mensajeOk.isEmpty()) {
				addActionMessage(mensajeOk);
			}
			aniadirMensajeAviso(null, listaMensajesAvisos);
		} else {
			addActionError(resultado.getMensaje());
		}
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public ConsultaVehiculoAtex5Dto getConsultaVehiculoAtex5Dto() {
		return consultaVehiculoAtex5Dto;
	}

	public void setConsultaVehiculoAtex5Dto(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto) {
		this.consultaVehiculoAtex5Dto = consultaVehiculoAtex5Dto;
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
}