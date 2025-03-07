package org.gestoresmadrid.oegam2.docBase.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamDocBase.service.ServicioDocBase;
import org.gestoresmadrid.oegamDocBase.service.ServicioGestionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.GestionDocBaseFilterBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResumenDocBaseBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionDocBaseAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = -3931915994041117764L;
	private static final String[] fetchList = {"contrato","contrato.colegiado"};
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionDocBaseAction.class);
	
	String idDocBase;
	private String estadoNuevo;
	GestionDocBaseFilterBean gestionDocBase;
	ResumenDocBaseBean resumen;
	InputStream inputStream;
	String fileName;
	private static final String DESCARGAR_DOC = "descargarDocBase";
	private static final String POP_UP_SELECCION_ESTADO = "popUpSeleccionEstado";
	
	@Resource
	ModelPagination modeloGestionDocBasePaginated;
	
	@Autowired
	ServicioGestionDocBase servicioGestionDocBase;

	@Autowired
	ServicioDocBase servicioDocBase;

	@Autowired
	UtilesFecha utilesFecha;
	
	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String eliminar() {
		try {
			ResultadoDocBaseBean resultado = servicioGestionDocBase.eliminarDocBase(idDocBase,utilesColegiado.tienePermisoAdmin(),utilesColegiado.usuariosTrafico());
			if(!resultado.getError()){
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los documento base, error: ",e);
			addActionError("Ha sucedido un error a la hora de eliminar los documento base.");
		}
		return actualizarPaginatedList();
	}
	
	public String descargar() {
		try {
			ResultadoDocBaseBean resultado = servicioGestionDocBase.descargarDocBase(idDocBase);
			if(!resultado.getError()){
				try{
					inputStream = new FileInputStream((File) resultado.getFichero());
					fileName = (String) resultado.getNombreFichero();
					if(resultado.getEsZip()){
						servicioGestionDocBase.borrarFichero(resultado.getFichero());
					}
					return DESCARGAR_DOC;
				}catch(FileNotFoundException e) {
					log.error("No existe el fichero a descargar,error:", e);
					addActionError("No existe el fichero a descargar");
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descargar los documento base, error: ",e);
			addActionError("Ha sucedido un error a la hora de descargar los documento base.");
		}
		return actualizarPaginatedList();
	}

	public String reimprimirErroneos() {
		try {
			ResultadoDocBaseBean resultado = servicioGestionDocBase.reimprimirDocBaseErroneo(idDocBase, utilesColegiado.tienePermisoAdmin(), utilesColegiado.usuariosTrafico());
			if (!resultado.getError()) {
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir los documento base, error: ", e);
			addActionError("Ha sucedido un error a la hora de imprimir los documento base.");
		}
		return actualizarPaginatedList();
	}

	@Override
	public Boolean validarFechas(Object object) {
		Boolean esRangoValido = Boolean.FALSE;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		gestionDocBase = (GestionDocBaseFilterBean) object;
		if(StringUtils.isBlank(gestionDocBase.getDocId()) && StringUtils.isBlank(gestionDocBase.getMatricula())
		 && gestionDocBase.getNumExpediente() == null){
			if(gestionDocBase != null && gestionDocBase.getFechaPresentacion() != null && !gestionDocBase.getFechaPresentacion().isfechaNula() && 
					gestionDocBase.getFechaPresentacion().getFechaInicio() != null &&
							gestionDocBase.getFechaPresentacion().getFechaFin() != null){
				if(StringUtils.isNotBlank(valorRangoFechas)){
					esRangoValido = utilesFecha.comprobarRangoFechas(gestionDocBase.getFechaPresentacion().getFechaInicio(), gestionDocBase.getFechaPresentacion().getFechaFin(), Integer.parseInt(valorRangoFechas));
				}
			}
			if(!esRangoValido){
				addActionError("Debe indicar un rango de fechas no mayor a " + valorRangoFechas + " dias para poder obtener los justificantes.");
			}
		} else {
			esRangoValido = Boolean.TRUE;
		}
		return esRangoValido;
	}
	
	public String cambiarEstado() {
		try {
			ResultadoDocBaseBean resultado = servicioGestionDocBase.cambiarEstadoDocBase(idDocBase, estadoNuevo, utilesColegiado.tienePermisoAdmin(), utilesColegiado.usuariosTrafico());
			if (!resultado.getError()) {
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de los documentos base, error: ", e);
			addActionError("Ha sucedido un error a la hora de cambiar el estado de los documentos base.");
		}
		return actualizarPaginatedList();
	}
	
	public String abrirSeleccionEstado() {
		return POP_UP_SELECCION_ESTADO;
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloGestionDocBasePaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(gestionDocBase == null) {
			gestionDocBase = new GestionDocBaseFilterBean();
		}
		if(gestionDocBase.getMatricula() != null && !gestionDocBase.getMatricula().isEmpty()){
			gestionDocBase.setMatricula(gestionDocBase.getMatricula().trim().toUpperCase());
		}
		if(gestionDocBase.getDocId() != null && !gestionDocBase.getDocId().isEmpty()){
			gestionDocBase.setDocId(gestionDocBase.getDocId().trim());
		}
		if(gestionDocBase.getNumExpediente() != null){
			gestionDocBase.setDocId(gestionDocBase.getDocId().trim());
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			gestionDocBase.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(gestionDocBase == null){
			gestionDocBase = new GestionDocBaseFilterBean();
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			gestionDocBase.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		gestionDocBase.setFechaPresentacion(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}
	
	@Override
	protected Object getBeanCriterios() {
		return gestionDocBase;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		gestionDocBase = (GestionDocBaseFilterBean) object;
	}

	public String getIdDocBase() {
		return idDocBase;
	}

	public void setIdDocBase(String idDocBase) {
		this.idDocBase = idDocBase;
	}

	public ResumenDocBaseBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenDocBaseBean resumen) {
		this.resumen = resumen;
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

	public GestionDocBaseFilterBean getGestionDocBase() {
		return gestionDocBase;
	}

	public void setGestionDocBase(GestionDocBaseFilterBean gestionDocBase) {
		this.gestionDocBase = gestionDocBase;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

}
