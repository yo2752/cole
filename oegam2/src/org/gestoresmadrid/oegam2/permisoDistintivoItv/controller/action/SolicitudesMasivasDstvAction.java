package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import java.io.File;

import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioSolicitudesMasivasDstv;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResumenDistintivoDgtBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;

public class SolicitudesMasivasDstvAction extends ActionBase{

	private static final long serialVersionUID = -5951364409629689483L;

	private ResumenDistintivoDgtBean resumen;
	private File fichero;
	private String ficheroFileName; 
	private String ficheroContentType;
	private String ficheroFileSize;
	private Long idContrato;
	private String tipoSolicitud;
	
	@Autowired
	ServicioSolicitudesMasivasDstv servicioSolicitudesMasivasDstv;
	
	@Autowired
	private Utiles utiles;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio(){
		if(!utilesColegiado.tienePermisoAdmin()){
			idContrato = utilesColegiado.getIdContratoSession();
		}
		return SUCCESS;
	}
	
	public String alta(){
		if(!utiles.esNombreFicheroValido(ficheroFileName)) {
			addActionError("El nombre del fichero es erroneo");
		}else {
			ResultadoDistintivoDgtBean resultado = servicioSolicitudesMasivasDstv.tratarSolicitudesMasivas(fichero,ficheroFileName,tipoSolicitud, idContrato, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			} else {
				if("2".equals(tipoSolicitud)){
					addActionMessage(resultado.getMensaje());
				} else {
					resumen = resultado.getResumen();
				}
			}
		}		
		return SUCCESS;
	}
	

	public ResumenDistintivoDgtBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenDistintivoDgtBean resumen) {
		this.resumen = resumen;
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

	public String getFicheroContentType() {
		return ficheroContentType;
	}

	public void setFicheroContentType(String ficheroContentType) {
		this.ficheroContentType = ficheroContentType;
	}

	public String getFicheroFileSize() {
		return ficheroFileSize;
	}

	public void setFicheroFileSize(String ficheroFileSize) {
		this.ficheroFileSize = ficheroFileSize;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
}
