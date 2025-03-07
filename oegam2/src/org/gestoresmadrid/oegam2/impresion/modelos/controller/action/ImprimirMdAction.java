package org.gestoresmadrid.oegam2.impresion.modelos.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.enumerados.TipoFicheros;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.impresion.modelos.service.ServicioImpresionModelos600601;
import org.gestoresmadrid.oegam2comun.impresion.modelos.view.bean.ImpresionModelo600601FilterBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ImprimirMdAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 6315527084581153405L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImprimirMdAction.class);

	private static final String DESCARGA_DOC = "descargarDoc";

	private String codSeleccionados;
	private ImpresionModelo600601FilterBean impresionModelo600601FilterBean;
	private String impreso;

	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	private static final String[] fetchList = {"modelo"};

	@Resource
	private ModelPagination modeloImpresionModelo600601PaginatedImpl;

	@Autowired
	private ServicioImpresionModelos600601 servicioImpresionModelos600601;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() {
		cargarFiltroInicial();
		if (impresionModelo600601FilterBean != null && impresionModelo600601FilterBean.getNumExpedientes() != null) {
			actualizarPaginatedList();
		} else {
			addActionError("Error al obtener los modelos para imprimir.");
		}
		return SUCCESS;
	}

	public String obtenerFichero() {
		String pagina = "";
		Boolean error = false;
		Boolean tienePermisoAdmin = utilesColegiado.tienePermisoAdmin();
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		impresionModelo600601FilterBean = (ImpresionModelo600601FilterBean) getSession().get(getKeyCriteriosSession());

		ResultBean resultValidar = servicioImpresionModelos600601.comprobaDatosObligatorios(codSeleccionados,impreso);
		if (!resultValidar.getError()) {
			ResultBean resultPermisos = servicioImpresionModelos600601.comprobarPermisosModelos(codSeleccionados, tienePermisoAdmin, idContrato.longValue());
			if (!resultPermisos.getError()) {
				ResultBean resultValModelos = servicioImpresionModelos600601.comprobarImpresion(codSeleccionados);
				if (!resultValModelos.getError() || TipoFicheros.Escritura.getValorEnum().equals(impreso)) {
					ResultBean resultImprimir = servicioImpresionModelos600601.imprimir(codSeleccionados, impreso);
					if (resultImprimir.getError()) {
						aniadirMensajeError(resultImprimir);
					}
					if (resultImprimir.getAttachment("fichero") == null) {
						addActionError("No existe el fichero a descargar");
						error = true;
					} else {
						try {
							inputStream = new FileInputStream((File)resultImprimir.getAttachment("fichero"));
							fileName = (String)resultImprimir.getAttachment("nombreFichero");
							addActionMessage("La impresión se ha realizado correctamente");
						} catch (FileNotFoundException e) {
							log.error("No existe el fichero a descargar,error:", e);
						}
					}
				} else {
					aniadirMensajeError(resultValModelos);
					error = true;
				}
			} else {
				aniadirMensajeError(resultPermisos);
				error = true;
			}
		} else {
			aniadirMensajeError(resultValidar);
			error = true;
		}
		if (error) {
			actualizarPaginatedList();
			pagina = SUCCESS;
		}else{
			pagina = DESCARGA_DOC;
		}
		return pagina;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloImpresionModelo600601PaginatedImpl;
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
		if (impresionModelo600601FilterBean == null) {
			impresionModelo600601FilterBean = new ImpresionModelo600601FilterBean();
		}
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			BigDecimal[] bNumExpedientes = new BigDecimal[sNumExpedientes.length];
			int i = 0;
			for (String numExp : sNumExpedientes){
				bNumExpedientes[i++] = new BigDecimal(numExp);
			}
			impresionModelo600601FilterBean.setNumExpedientes(bNumExpedientes);
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return impresionModelo600601FilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		impresionModelo600601FilterBean = (ImpresionModelo600601FilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public String getImpreso() {
		return impreso;
	}

	public void setImpreso(String impreso) {
		this.impreso = impreso;
	}

	public ImpresionModelo600601FilterBean getImpresionModelo600601FilterBean() {
		return impresionModelo600601FilterBean;
	}

	public void setImpresionModelo600601FilterBean(
			ImpresionModelo600601FilterBean impresionModelo600601FilterBean) {
		this.impresionModelo600601FilterBean = impresionModelo600601FilterBean;
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