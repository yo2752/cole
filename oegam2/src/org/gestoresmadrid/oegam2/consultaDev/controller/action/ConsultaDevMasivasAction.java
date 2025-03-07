package org.gestoresmadrid.oegam2.consultaDev.controller.action;

import java.io.File;

import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ResultadoConsultaDev;
import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ResumenConsultaDevBean;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioConsultaDev;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;

public class ConsultaDevMasivasAction extends ActionBase{

	private static final long serialVersionUID = 5165900075482135340L;
	
	private File fichero; // Fichero a importar desde la pagina JSP
	private String ficheroFileName; // nombre del fichero importado
	private String ficheroContentType; // tipo del fichero importado
	
	private String idContrato;
	private ResumenConsultaDevBean resumen;
	
	@Autowired
	ServicioConsultaDev servicioConsultaDev;
	
	@Autowired
	private Utiles utiles;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio(){
		if(utiles.esUsuarioOegam3(utilesColegiado.getIdUsuarioSessionBigDecimal().toString())) {
			return "redireccionOegam3";
		}else {
			if(!utilesColegiado.tienePermisoAdmin()){
				idContrato = utilesColegiado.getIdContratoSessionBigDecimal().toString();
			}
			return SUCCESS;
		}
	}
	
	public String altas(){
		if(!utiles.esNombreFicheroValido(ficheroFileName)) {
			addActionError("El nombre del fichero es erroneo");
		}else {
			ResultadoConsultaDev resultado = servicioConsultaDev.altaMasivaConsultasDev(fichero,ficheroFileName,idContrato,utilesColegiado.getIdUsuarioSessionBigDecimal());
			resumen = new ResumenConsultaDevBean();
			if(resultado.getError()){
				resumen.setNumError(1);
				resumen.setListaErrores(resultado.getListaErrores());
			}else{
				resumen.setNumError(resultado.getNumError());
				resumen.setNumOk(resultado.getNumOk());
				resumen.setListaErrores(resultado.getListaErrores());
				resumen.setListaOk(resultado.getListaOK());
			}
		}
		return SUCCESS;
	}
	
	public File getFichero() {
		return fichero;
	}
	
	public void setFichero(File fichero) {
		this.fichero = fichero;
	}
	
	public String getFicheroFileName() {
		return ficheroFileName;
	}
	
	public void setFicheroFileName(String ficheroFileName) {
		this.ficheroFileName = ficheroFileName;
	}
	
	public String getIdContrato() {
		return idContrato;
	}
	
	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}
	
	public ResumenConsultaDevBean getResumen() {
		return resumen;
	}
	
	public void setResumen(ResumenConsultaDevBean resumen) {
		this.resumen = resumen;
	}

	public String getFicheroContentType() {
		return ficheroContentType;
	}

	public void setFicheroContentType(String ficheroContentType) {
		this.ficheroContentType = ficheroContentType;
	}

}
