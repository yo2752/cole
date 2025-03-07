package org.gestoresmadrid.oegam2.estadisticas.impresion.controller.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gestoresmadrid.oegam2comun.estadisticas.impresion.service.ServicioEstadisticaImpresion;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.estructuras.FechaFraccionada;

public class EstadisticaImpresionAction extends ActionBase {

	
	@Autowired
	ServicioEstadisticaImpresion servicioEstadistica;

	private static final long serialVersionUID = -2152721904326216321L;
	
	private static final String GENERAR_EXCEL="generarExcel";
	
	private FechaFraccionada fechaBusqueda;
	
	private String jefatura;
	
	private String tipoDocumento;
	
	private String fileName;
	
	private InputStream inputStream;
	
	public String inicio(){
		
		if(null==fechaBusqueda){
			fechaBusqueda = new FechaFraccionada();
		}
		
		return SUCCESS;
	}

	public String generarExcel(){
		try {
		
		ResultBean result = servicioEstadistica.generarExcel(fechaBusqueda.getFechaInicio(), fechaBusqueda.getFechaFin(), jefatura, tipoDocumento);
		
		if(result.getError()){
			addActionError(result.getMensaje());
			return SUCCESS;	
		}else{
			XSSFWorkbook workbook = (XSSFWorkbook) result.getAttachment("excel");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			workbook.write(baos);
			
			inputStream = new ByteArrayInputStream(baos.toByteArray());
			fileName = "EstadisticaImpresion.xlsx";
			addActionMessage("El excel se ha generado correctamente");
			return GENERAR_EXCEL;
		}
		
		} catch (Exception e) {
			addActionError(e.getMessage());
		}
		
		return SUCCESS;
	}

	public FechaFraccionada getFechaBusqueda() {
		return fechaBusqueda;
	}



	public void setFechaBusqueda(FechaFraccionada fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}



	public String getJefatura() {
		return jefatura;
	}



	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}



	public String getTipoDocumento() {
		return tipoDocumento;
	}



	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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
