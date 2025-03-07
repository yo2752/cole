package org.gestoresmadrid.oegam2.impr.controller.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.impr.service.ServicioGestionarDocImpr;
import org.gestoresmadrid.oegamComun.impr.view.bean.GestionDocImprFilterBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResumenImprBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class GestionDocImprAction extends AbstractPaginatedListAction{
	
	private static final long serialVersionUID = 1109011847189423918L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionDocImprAction.class);
	
	private static final String POP_UP_ESTADOS = "popUpCambioEstadoDocImpr";

	private String codSeleccionados;
	private GestionDocImprFilterBean consultaDocImpr;	
	private ResumenImprBean resumen;
	private String estadoNuevo;
	private InputStream inputStream;
	private String fileName;
	private static final String IMP_DESCARGAR = "impDescarga";
	
	
	@Resource
	ModelPagination modeloDocImprPaginated;
	
	@Autowired
	UtilesFecha utilesFecha;
	
	@Autowired
	ServicioGestionarDocImpr servicioGestionDocImpr;
	
	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesColegiado utilesColegiado;
	
	public String solicitarImprManual() {
		try {
			ResultadoDocImprBean resultado = servicioGestionDocImpr.impresionManual(codSeleccionados,utilesColegiado.getIdUsuarioSession(),utilesColegiado.getIdJefaturaSesion());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar el IMPR manual, error: ", e);
			addActionError("Ha sucedido un error a la hora de solicitar el IMPR manual.");
		}
		return actualizarPaginatedList();
	}
	
	public String descargaManual() throws OegamExcepcion {
		try {
			ResultadoDocImprBean resultado = servicioGestionDocImpr.descargaManual(codSeleccionados,esEntornoAm());
			if (resultado.getError()) {
				try{
					inputStream = new FileInputStream((File) resultado.getFichero());
					fileName = (String) resultado.getNombreFichero();
					if(resultado.getEsZip()){
						servicioGestionDocImpr.borrarFichero(resultado.getFichero());
					}
					return IMP_DESCARGAR;
				}catch(FileNotFoundException e) {
					log.error("No existe el fichero a descargar,error:", e);
					addActionError("No existe el fichero a descargar");
				}
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descargar manual el IMPR, error: ", e);
			addActionError("Ha sucedido un error a la hora de descargar manual el IMPR.");
		}
		return actualizarPaginatedList();
	}
	
	public String cambiarEstado() {
		try {
			ResultadoDocImprBean resultado = servicioGestionDocImpr.cambiarEstado(codSeleccionados,estadoNuevo,utilesColegiado.getIdUsuarioSession());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de  cambiar el estado IMPR, error: ", e);
			addActionError("Ha sucedido un error a la hora de cambiar el estado IMPR");
		}
		return actualizarPaginatedList();
	}
		
	public String reactivar() {
		try {
			ResultadoDocImprBean resultado = servicioGestionDocImpr.reactivar(codSeleccionados,utilesColegiado.getIdUsuarioSession(),esEntornoAm());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de reactivar el documento, error: ", e);
			addActionError("Ha sucedido un error a la hora de reactivar el documento");
		}
		return actualizarPaginatedList();
	}
	
	public String imprimirAuto() {
		try {
			ResultadoDocImprBean resultado = servicioGestionDocImpr.imprimir(codSeleccionados,utilesColegiado.getIdUsuarioSession(),utilesColegiado.getIdJefaturaSesion());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el documento, error: ", e);
			addActionError("Ha sucedido un error a la hora de imprimir el documento.");
		}
		return actualizarPaginatedList();
	}
	
	protected Boolean esEntornoAm() {
		String esEntornoAm = gestorPropiedades.valorPropertie("esEntornoAm");
		if(StringUtils.isNotBlank(esEntornoAm) && "SI".equals(esEntornoAm.toUpperCase())) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public String inicio() {
		cargarFiltroInicial();
		return SUCCESS;
	}
	
	public String cargarPopUpCambioEstado() {
		return POP_UP_ESTADOS;
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloDocImprPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaDocImpr == null) {
			consultaDocImpr = new GestionDocImprFilterBean();
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaDocImpr == null) {
			consultaDocImpr = new GestionDocImprFilterBean();
		}
		consultaDocImpr.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaDocImpr;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaDocImpr = (GestionDocImprFilterBean) object;
		
	}



	public String getCodSeleccionados() {
		return codSeleccionados;
	}


	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}


	public ResumenImprBean getResumen() {
		return resumen;
	}


	public void setResumen(ResumenImprBean resumen) {
		this.resumen = resumen;
	}


	public String getEstadoNuevo() {
		return estadoNuevo;
	}


	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public ModelPagination getModeloDocImprPaginated() {
		return modeloDocImprPaginated;
	}

	public void setModeloDocImprPaginated(ModelPagination modeloDocImprPaginated) {
		this.modeloDocImprPaginated = modeloDocImprPaginated;
	}

	public GestionDocImprFilterBean getConsultaDocImpr() {
		return consultaDocImpr;
	}

	public void setConsultaDocImpr(GestionDocImprFilterBean consultaDocImpr) {
		this.consultaDocImpr = consultaDocImpr;
	}

	public UtilesFecha getUtilesFecha() {
		return utilesFecha;
	}

	public void setUtilesFecha(UtilesFecha utilesFecha) {
		this.utilesFecha = utilesFecha;
	}

	public ServicioGestionarDocImpr getServicioGestionDocImpr() {
		return servicioGestionDocImpr;
	}

	public void setServicioGestionDocImpr(ServicioGestionarDocImpr servicioGestionDocImpr) {
		this.servicioGestionDocImpr = servicioGestionDocImpr;
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

}
