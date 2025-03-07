package org.gestoresmadrid.oegam2.trafico.mf.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.actualizacionMF.model.service.ServicioActualizacionMF;
import org.gestoresmadrid.oegam2comun.actualizacionMF.view.beans.ActualizacionMFFilterBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaActualizacionMFAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 589170867271557995L;

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ConsultaActualizacionMFAction.class);

	@Resource
	private ModelPagination modeloConsultaActualizacionMF;

	@Autowired
	ServicioActualizacionMF servicioActualizacion;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private ActualizacionMFFilterBean filterBean;

	private BigDecimal idActualizacion;

	private String codigos;

	private InputStream inputStream;
	private String fileName;

	public String generarPeticion(){
		ResultBean salida = servicioActualizacion.realizarActualizacion(getIdActualizacion(), utilesColegiado.getIdUsuarioSessionBigDecimal());

		if (salida.getError()) {
			addActionError(salida.getMensaje());
		} else {
			addActionMessage(salida.getMensaje());
		}
		actualizarPaginatedList();
		return SUCCESS;
	}

	public String descargarFicheros() {
		ResultBean salida = servicioActualizacion.descargarFicheros(codigos);

		if (salida.getError()) {
			addActionError(salida.getMensaje());
		} else {
			try {
				fileName = "prueba.zip";
				addActionMessage("Se han generado correctamente los informes de los tramites " + codigos);
				inputStream =  new FileInputStream((File) salida.getAttachment("zip"));
				return "descargar";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		actualizarPaginatedList();

		return "descargar";
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaActualizacionMF;
	}

	@Override
	protected ILoggerOegam getLog() {
		return LOG;
	}

	@Override
	protected void cargaRestricciones() {
		if (filterBean == null) {
			filterBean = new ActualizacionMFFilterBean();
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (filterBean == null) {
			filterBean = new ActualizacionMFFilterBean();
		}
		filterBean.setFechaAlta(new FechaFraccionada());
	}

	@Override
	protected Object getBeanCriterios() {
		return filterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.filterBean = (ActualizacionMFFilterBean) object;
	}

	public ActualizacionMFFilterBean getFilterBean() {
		return filterBean;
	}

	public void setFilterBean(ActualizacionMFFilterBean filterBean) {
		this.filterBean = filterBean;
	}

	public BigDecimal getIdActualizacion() {
		return idActualizacion;
	}

	public void setIdActualizacion(BigDecimal idActualizacion) {
		this.idActualizacion = idActualizacion;
	}

	public String getCodigos() {
		return codigos;
	}

	public void setCodigos(String codigos) {
		this.codigos = codigos;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStreamXML) {
		this.inputStream = inputStreamXML;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}