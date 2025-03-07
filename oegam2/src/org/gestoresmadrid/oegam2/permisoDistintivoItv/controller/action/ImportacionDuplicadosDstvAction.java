package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import java.io.File;

import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioImportacionDstvDup;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultImportacionDstvDupBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResumenImportacionDstvBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ImportacionDuplicadosDstvAction extends ActionBase{

	private static final long serialVersionUID = 2918830340868732385L;

	private Long idContrato;
	private ResumenImportacionDstvBean resumen;
	private File fichero;
	private String ficheroFileName; 
	private String ficheroContentType;
	private String ficheroFileSize;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ImportacionDuplicadosDstvAction.class);
	
	@Autowired
	ServicioImportacionDstvDup servicioImportacionDstvDup;
	
	@Autowired
	Utiles utiles;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio(){
		if(!utilesColegiado.tienePermisoAdmin()){
			idContrato = utilesColegiado.getIdContratoSession();
		}
		return SUCCESS;
	}
	
	public String importar(){
		try {
			if(!utiles.esNombreFicheroValido(ficheroFileName)) {
				addActionError("El nombre del fichero es erroneo");
			}else {
				ResultImportacionDstvDupBean resultado = servicioImportacionDstvDup.importarDuplicados(idContrato, fichero, ficheroFileName, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if(resultado.getError()){
					addActionError(resultado.getMensaje());
				} else {
					resumen = resultado.getResumen();
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la importacion, error: ",e);
			addActionError("Ha sucedido un error a la hora de realizar la importación.");
		}
		return SUCCESS;
	}
	

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public ResumenImportacionDstvBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenImportacionDstvBean resumen) {
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
	
	
}
