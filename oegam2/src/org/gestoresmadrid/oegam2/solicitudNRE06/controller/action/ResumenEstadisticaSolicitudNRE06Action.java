package org.gestoresmadrid.oegam2.solicitudNRE06.controller.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gestoresmadrid.oegam2comun.solicitudNRE06.model.view.dto.ResumenEstadisticaSolicitudNRE06Dto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ResumenEstadisticaSolicitudNRE06Action extends ActionBase{

	private static final long serialVersionUID = 7273333415371375769L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ResumenEstadisticaSolicitudNRE06Action.class);

	private ResumenEstadisticaSolicitudNRE06Dto resumenNRE06Dto;
	private List<ResumenEstadisticaSolicitudNRE06Dto> lista;
	private String fileName;
	private InputStream inputStream;
	
	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	public String inicio(){
		
		return SUCCESS;
	}
	
	public String generarExcel(){
		String pagina = SUCCESS;
		try {
			ResultBean resultado =  servicioTramiteTrafico.getExcelResumenNRE06PorFecha(resumenNRE06Dto.getFecha().getFechaInicio(),
					resumenNRE06Dto.getFecha().getFechaFin());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
					XSSFWorkbook workbook = (XSSFWorkbook) resultado.getAttachment("excel");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					workbook.write(baos);
					inputStream = new ByteArrayInputStream(baos.toByteArray());
					fileName = "ResumenEstadisticaNRE06.xlsx";
					pagina = "generarExcel";
					addActionMessage("El excel se ha generado correctamente");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el excel, error: ", e);
			addActionError("Ha sucedido un error a la hora de generar el excel.");
		}
		return pagina;
	}

	public ResumenEstadisticaSolicitudNRE06Dto getResumenNRE06Dto() {
		return resumenNRE06Dto;
	}

	public void setResumenNRE06Dto(ResumenEstadisticaSolicitudNRE06Dto resumenNRE06Dto) {
		this.resumenNRE06Dto = resumenNRE06Dto;
	}

	public List<ResumenEstadisticaSolicitudNRE06Dto> getLista() {
		return lista;
	}

	public void setLista(List<ResumenEstadisticaSolicitudNRE06Dto> lista) {
		this.lista = lista;
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

	public ServicioTramiteTrafico getServicioTramiteTrafico() {
		return servicioTramiteTrafico;
	}

	public void setServicioTramiteTrafico(ServicioTramiteTrafico servicioTramiteTrafico) {
		this.servicioTramiteTrafico = servicioTramiteTrafico;
	}
}
