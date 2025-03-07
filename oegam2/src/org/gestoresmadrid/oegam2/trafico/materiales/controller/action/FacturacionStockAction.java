package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioFacturacionMaterial;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGenerarExcel;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.FacturacionStockBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.ResultadoFacturacionStockBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.FacturacionStockDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class FacturacionStockAction extends ActionBase {

	private static final long serialVersionUID = 4745470136528617768L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(FacturacionStockAction.class);

	private static final String SUCCESS			= "success";
	private static final String GENERAR_EXCEL	= "GenerarExcelStock";

	@Autowired ServicioFacturacionMaterial	servicioFacturacionMaterial;
	@Autowired ServicioGenerarExcel			servicioGenerarExcel;

	private Long				contratoColegiado;
	private Long				tipoDocumento;
	private Long				tipoDistintivo;
	private FechaFraccionada	fechaAlta;

	private InputStream inputStream;	// Flujo de bytes del fichero a imprimir en PDF del action
	private String		fileName;		// Nombre del fichero a imprimir
	private String		fileSize;		// Tamaño del fichero a imprimir

	private FacturacionStockBean facturacionStock;

	private List<FacturacionStockDto> datosFact;

	public String inicio() {
		log.info("Inicio");
		facturacionStock = new FacturacionStockBean();
		return SUCCESS;
	}

	public String generarExcel(){
		try {
			ResultadoFacturacionStockBean resultado = servicioFacturacionMaterial.generarExcelFacturacion(facturacionStock);
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				facturacionStock = resultado.getFacturacionStock();
				addActionMessage("Excel generado correctamente, por favor pulse el botón de descargar para poder realizar la descarga.");
			}
		} catch (Exception e) {
			addActionError("Ha sucedido un error a la hora de generar el excel de facturacion de stock.");
		}
		return SUCCESS;
	}

	public String descargar(){
		try {
			ResultadoFacturacionStockBean resultado = servicioFacturacionMaterial.descargarExcelFacturacion(facturacionStock);
			if(!resultado.getError()){
				try{
					inputStream = new FileInputStream(resultado.getExcel());
					fileName = facturacionStock.getNombreFichero() + ".xls";
					return GENERAR_EXCEL;
				}catch(FileNotFoundException e) {
					log.error("No existe el fichero a descargar,error:", e);
					addActionError("No existe el fichero a descargar");
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			addActionError("Ha sucedido un error a la hora de obtener el excel de facturación de stock para su descarga.");
		}
		return SUCCESS;
	}

	public Long getContratoColegiado() {
		return contratoColegiado;
	}

	public void setContratoColegiado(Long contratoColegiado) {
		this.contratoColegiado = contratoColegiado;
	}
	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Long tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(Long tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public List<FacturacionStockDto> getDatosFact() {
		return datosFact;
	}

	public void setDatosFact(List<FacturacionStockDto> datosFact) {
		this.datosFact = datosFact;
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

	public FacturacionStockBean getFacturacionStock() {
		return facturacionStock;
	}

	public void setFacturacionStock(FacturacionStockBean facturacionStock) {
		this.facturacionStock = facturacionStock;
	}

}