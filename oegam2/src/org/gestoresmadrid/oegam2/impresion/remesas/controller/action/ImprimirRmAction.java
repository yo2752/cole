package org.gestoresmadrid.oegam2.impresion.remesas.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.impresion.remesas.service.ServicioImpresionRemesas;
import org.gestoresmadrid.oegam2comun.impresion.remesas.view.bean.ImpresionRemesasFilterBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ImprimirRmAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = 5969014263876289260L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ImprimirRmAction.class);
	
	private static final String DESCARGA_DOC = "descargarDoc";
	
	private ImpresionRemesasFilterBean impresionRemesasFilterBean;
	private String codSeleccionados;
	private String impreso;
	
	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	
	private static final String[] fetchList = {"modelo"};
	
	@Resource
	private ModelPagination modeloImpresionRemesasModelPaginatedImpl;
	
	@Autowired
	private ServicioImpresionRemesas servicioImpresionRemesas;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() {
		cargarFiltroInicial();
		if(impresionRemesasFilterBean != null && impresionRemesasFilterBean.getNumExpedientes() != null){
			actualizarPaginatedList();
		}else{
			addActionError("Error al obtener las remesas para imprimir.");
		}
		return SUCCESS;
	}
	
	public String obtenerFichero(){
		String pagina = "";
		Boolean error = false;
		Boolean tienePermisoAdmin = utilesColegiado.tienePermisoAdmin();
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		impresionRemesasFilterBean = (ImpresionRemesasFilterBean) getSession().get(getKeyCriteriosSession());
		
		ResultBean resultValidar = servicioImpresionRemesas.comprobarDatosMinimos(codSeleccionados,impreso);
		if(!resultValidar.getError()){
			ResultBean resultPermisos = servicioImpresionRemesas.comprobarPermisosRemesas(codSeleccionados,tienePermisoAdmin,idContrato.longValue());
			if(!resultPermisos.getError()){
				ResultBean resultValModelos = servicioImpresionRemesas.comprobarImpresion(codSeleccionados);
				if(!resultValModelos.getError()){
					ResultBean resultImprimir = servicioImpresionRemesas.imprimir(codSeleccionados,impreso);
					if(resultImprimir.getError()){
						aniadirMensajeError(resultImprimir);
					}
					try {
						inputStream = new FileInputStream((File)resultImprimir.getAttachment("fichero"));
						fileName = (String)resultImprimir.getAttachment("nombreFichero");
					} catch (FileNotFoundException e) {
						log.error("No existe el fichero a descargar,error:",e);
					}
				}else{
					aniadirMensajeError(resultValModelos);
					error = true;
				}
			}else{
				aniadirMensajeError(resultPermisos);
				error = true;
			}
		}else{
			aniadirMensajeError(resultValidar);
			error = true;
		}
		if(error){
			actualizarPaginatedList();
			pagina = SUCCESS;
		}else{
			pagina = DESCARGA_DOC;
		}
		codSeleccionados = null;
		return pagina;
	}
	
	
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloImpresionRemesasModelPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void cargarFiltroInicial() {
		impresionRemesasFilterBean = new ImpresionRemesasFilterBean();
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			String[] sNumExpedientes = codSeleccionados.split("-");
			BigDecimal[] bNumExpedientes = new BigDecimal[sNumExpedientes.length];
			int i = 0;
			for(String numExp : sNumExpedientes){
				bNumExpedientes[i++] = new BigDecimal(numExp);
			}
			impresionRemesasFilterBean.setNumExpedientes(bNumExpedientes);
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return impresionRemesasFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		impresionRemesasFilterBean = (ImpresionRemesasFilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}
	
	public ImpresionRemesasFilterBean getImpresionRemesasFilterBean() {
		return impresionRemesasFilterBean;
	}

	public void setImpresionRemesasFilterBean(
			ImpresionRemesasFilterBean impresionRemesasFilterBean) {
		this.impresionRemesasFilterBean = impresionRemesasFilterBean;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ModelPagination getModeloImpresionRemesasModelPaginatedImpl() {
		return modeloImpresionRemesasModelPaginatedImpl;
	}

	public void setModeloImpresionRemesasModelPaginatedImpl(
			ModelPagination modeloImpresionRemesasModelPaginatedImpl) {
		this.modeloImpresionRemesasModelPaginatedImpl = modeloImpresionRemesasModelPaginatedImpl;
	}

	public String getImpreso() {
		return impreso;
	}

	public void setImpreso(String impreso) {
		this.impreso = impreso;
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

}
