package org.gestoresmadrid.oegam2.generarExcelLiquidacion620.controller.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gestoresmadrid.oegam2comun.generarExcelLiquidacion620.views.dto.GenerarExcelLiquidacionDto;
import org.gestoresmadrid.oegam2comun.trafico.liquidacion620.model.service.ServicioLiquidacion620;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GenerarExcelLiquidacion620Action extends ActionBase {

	private static final long serialVersionUID = 5823672799239573657L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(GenerarExcelLiquidacion620Action.class);
	private GenerarExcelLiquidacionDto generarExcelLiquidacionDto;
	private List<GenerarExcelLiquidacionDto> lista;
	private String fileName;
	private InputStream inputStream;

	@Autowired
	ServicioLiquidacion620 servicioLiquidacion620;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() {
		return SUCCESS;
	}

	public String generarExcel() {
		String pagina = SUCCESS;
		try {
			Date fechaInicio = generarExcelLiquidacionDto.getFechaLiquidacion().getFechaInicio();
			Date fechaFin = generarExcelLiquidacionDto.getFechaLiquidacion().getFechaFin();
			if (fechaInicio != null && fechaFin != null) {

				ResultBean resultado = servicioLiquidacion620.getExcelLiquidacion620PorFecha(
						generarExcelLiquidacionDto.getFechaLiquidacion().getFechaInicio(),
						generarExcelLiquidacionDto.getFechaLiquidacion().getFechaFin(), utilesColegiado
								.getNumColegiadoByIdContrato(generarExcelLiquidacionDto.getContrato().getIdContrato()));
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					XSSFWorkbook workbook = (XSSFWorkbook) resultado.getAttachment("excel");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					workbook.write(baos);
					inputStream = new ByteArrayInputStream(baos.toByteArray());
					fileName = "LiquidacionManual620.xlsx";
					pagina = "generarExcel";
					addActionMessage("El excel se ha generado correctamente");
				}
			} else {
				addActionError("Debe indicar la fecha de alta desde y la fecha de alta hasta");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el excel, error: ", e);
			addActionError("Ha sucedido un error a la hora de generar el excel.");
		}
		return pagina;
	}

	public GenerarExcelLiquidacionDto getGenerarExcelLiquidacionDto() {
		return generarExcelLiquidacionDto;
	}

	public void setGenerarExcelLiquidacionDto(GenerarExcelLiquidacionDto generarExcelLiquidacionDto) {
		this.generarExcelLiquidacionDto = generarExcelLiquidacionDto;
	}

	public List<GenerarExcelLiquidacionDto> getLista() {
		return lista;
	}

	public void setLista(List<GenerarExcelLiquidacionDto> lista) {
		this.lista = lista;
	}

	public ServicioLiquidacion620 getServicioLiquidacion620() {
		return servicioLiquidacion620;
	}

	public void setServicioLiquidacion620(ServicioLiquidacion620 servicioLiquidacion620) {
		this.servicioLiquidacion620 = servicioLiquidacion620;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}