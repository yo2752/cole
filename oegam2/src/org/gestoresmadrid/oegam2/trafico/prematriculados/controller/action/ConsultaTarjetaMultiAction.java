package org.gestoresmadrid.oegam2.trafico.prematriculados.controller.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.ServicioVehiculosPrematriculados;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto.ResumenImportacion;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto.VehiculoPrematriculadoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaTarjetaMultiAction extends ActionBase{

	private static final long serialVersionUID = 1835832829977329118L;
	private static ILoggerOegam log = LoggerOegam.getLogger(ConsultaTarjetaMultiAction.class);

	private static final String EXTENSION_PERMITIDA_FICHERO_IMPORTACION = "csv";
	private static final String SEPARADOR_EN_EL_FICHERO = ";";

	private VehiculoPrematriculadoDto vehiculoPrematriculado;
	private String fileUploadFileName;
	private File fileUpload;
	private Boolean resumenImportacionFicheroEitvFlag = false;
	private List<ResumenImportacion> resumenImportacion = new ArrayList<ResumenImportacion>();

	@Autowired
	private ServicioVehiculosPrematriculados servicioVehiculosPrematriculados;

	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public String nuevo(){
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String importar() throws Throwable{
		try {
			if (!esValidoFichero()) {
				return SUCCESS;
			}else{
				List<VehiculoPrematriculadoDto> listaVehiculos = obtenerVehiculos(utiles.obtenerLineasFicheroTexto(fileUpload));
				if (listaVehiculos==null){
					addActionError("No ha seleccionado ningún vehículo");
					return SUCCESS;
				}
				ResultBean rs = servicioVehiculosPrematriculados.guardarSolicitudes(listaVehiculos);
				resumenImportacionFicheroEitvFlag = true;
				List<String> listaErroneos = (List<String>) rs.getAttachment("listaErroneos");
				List<String> listaCorrectos = (List<String>) rs.getAttachment("listaOK");		
				resumenImportacion.add((ResumenImportacion) rs.getAttachment("resumenImportacionTotal"));
				
				if (listaErroneos != null && !listaErroneos.isEmpty()){
					for (String error: listaErroneos){
						addActionError(error);
					}
				}
				if (listaCorrectos != null && !listaCorrectos.isEmpty()) {
					for (String mensaje: listaCorrectos){
						addActionMessage(mensaje);
					}
				}
			}
		} catch (Throwable th) {
			log.error(th.toString(), th);
			addActionError(th.toString());
		}
		return SUCCESS;
	}

	private List<VehiculoPrematriculadoDto> obtenerVehiculos(List<String> lineas) {
		if (lineas == null) {
			return null;
		}
		List<VehiculoPrematriculadoDto> listaVehiculos = new ArrayList<VehiculoPrematriculadoDto>();
		for (String linea: lineas){
			VehiculoPrematriculadoDto vehiculo = new VehiculoPrematriculadoDto();
			try {
				vehiculo.setBastidor(linea.split(SEPARADOR_EN_EL_FICHERO)[0]);
				vehiculo.setNive(linea.split(SEPARADOR_EN_EL_FICHERO)[1]);
			} catch (ArrayIndexOutOfBoundsException e) {
				log.error("El fichero no tiene el formato correcto");
			}
			completarVehiculo(vehiculo);
			listaVehiculos.add(vehiculo);
		}
		return listaVehiculos;
	}

	private void completarVehiculo(VehiculoPrematriculadoDto vehiculo) {
		if (vehiculoPrematriculado!=null){
			vehiculo.setDatosTecnicos(vehiculoPrematriculado.isDatosTecnicos());
			vehiculo.setFichaTecnica(vehiculoPrematriculado.isFichaTecnica());
		}
		vehiculo.setNumColegiado(utilesColegiado.getNumColegiadoSession());
	}

	private boolean esValidoFichero(){
		if (fileUpload == null) {
			addActionError("No se ha seleccionado ningún fichero");
			return false;
		}
		int ultimoPunto = fileUploadFileName.lastIndexOf(".");
		if(ultimoPunto == -1 || !EXTENSION_PERMITIDA_FICHERO_IMPORTACION.equalsIgnoreCase(fileUploadFileName.substring(ultimoPunto + 1, fileUploadFileName.length()))){
			addActionError("El fichero seleccionado no tiene extensión 'csv'");
			return false;
		}
		if(!utiles.esNombreFicheroValido(fileUploadFileName)) {
			addActionError("El nombre del fichero es erroneo");
			return false;
		}

		return true;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public Boolean getResumenImportacionFicheroEitvFlag() {
		return resumenImportacionFicheroEitvFlag;
	}

	public void setResumenImportacionFicheroEitvFlag(Boolean resumenImportacionFicheroEitvFlag) {
		this.resumenImportacionFicheroEitvFlag = resumenImportacionFicheroEitvFlag;
	}

	public ServicioVehiculosPrematriculados getServicioVehiculosPrematriculados() {
		return servicioVehiculosPrematriculados;
	}

	public void setServicioVehiculosPrematriculados(ServicioVehiculosPrematriculados servicioVehiculosPrematriculados) {
		this.servicioVehiculosPrematriculados = servicioVehiculosPrematriculados;
	}

	public List<ResumenImportacion> getResumenImportacion() {
		return resumenImportacion;
	}

	public void setResumenImportacion(List<ResumenImportacion> resumenImportacion) {
		this.resumenImportacion = resumenImportacion;
	}

	public VehiculoPrematriculadoDto getVehiculoPrematriculado() {
		return vehiculoPrematriculado;
	}

	public void setVehiculoPrematriculado(VehiculoPrematriculadoDto vehiculoPrematriculado) {
		this.vehiculoPrematriculado = vehiculoPrematriculado;
	}

}