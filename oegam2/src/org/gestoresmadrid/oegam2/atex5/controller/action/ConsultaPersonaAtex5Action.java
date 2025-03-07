package org.gestoresmadrid.oegam2.atex5.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioConsultaPersonaAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ConsultaPersonaAtex5FilterBean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResumenAtex5Bean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaPersonaAtex5Action extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 1883326044132523444L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaPersonaAtex5Action.class);

	private static final String[] fetchList = { "contrato", "provincia" };

	private static final String POP_UP_ESTADOS = "popPupCambioEstado";
	private static final String DESCARGAR = "descargarPdf";

	private String codSeleccionados;
	private String estadoNuevo;
	private ResumenAtex5Bean resumen;

	ConsultaPersonaAtex5FilterBean consultaPersona;

	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	@Resource
	ModelPagination modeloConsultaPersonaAtex5PaginatedImpl;

	@Autowired
	ServicioConsultaPersonaAtex5 servicioConsultaPersonaAtex5;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String cambiarEstado() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultadoAtex5Bean resultado = servicioConsultaPersonaAtex5.cambiarEstado(codSeleccionados, estadoNuevo,
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumen(resultado);
			}
		} else {
			addActionError("Debe seleccionar alguna consulta para poder cambiar su estado.");
		}
		return actualizarPaginatedList();
	}

	public String consultar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultadoAtex5Bean resultado = servicioConsultaPersonaAtex5.consultar(codSeleccionados,
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumen(resultado);
			}
		} else {
			addActionError("Debe seleccionar alguna consulta para poder realizar su consulta.");
		}
		return actualizarPaginatedList();
	}

	public String eliminar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultadoAtex5Bean resultado = servicioConsultaPersonaAtex5.eliminar(codSeleccionados,
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumen(resultado);
			}
		} else {
			addActionError("Debe seleccionar alguna consulta para poder eliminarla.");
		}
		return actualizarPaginatedList();
	}

	public String imprimir() {
		ResultadoAtex5Bean resultado = servicioConsultaPersonaAtex5.imprimirPdfs(codSeleccionados);
		if (!resultado.getError()) {
			try {
				inputStream = new FileInputStream((File) resultado.getFicheroDescarga());
				fileName = (String) resultado.getNombreFichero();
				return DESCARGAR;
			} catch (FileNotFoundException e) {
				log.error("No existe el fichero a descargar,error:", e);
				addActionError("No existe el fichero a descargar");
			}
		} else {
			addActionError(resultado.getMensaje());
		}
		return actualizarPaginatedList();
	}

	private void rellenarResumen(ResultadoAtex5Bean resultado) {
		resumen = new ResumenAtex5Bean();
		resumen.setNumError(resultado.getNumError());
		resumen.setListaErrores(resultado.getListaErrores());
		resumen.setNumOk(resultado.getNumOk());
		resumen.setListaOk(resultado.getListaOK());
		resumen.setEsConsultaPersona(Boolean.TRUE);
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
		return modeloConsultaPersonaAtex5PaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaPersona.getNif() != null && !consultaPersona.getNif().isEmpty()) {
			consultaPersona.setNif(consultaPersona.getNif().toUpperCase());
		}
		if (consultaPersona.getRazonSocial() != null && !consultaPersona.getRazonSocial().isEmpty()) {
			consultaPersona.setRazonSocial("%" + consultaPersona.getRazonSocial().toUpperCase() + "%");
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaPersona.setIdContrato(utilesColegiado.getIdContratoSession());
			consultaPersona.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaPersona == null) {
			consultaPersona = new ConsultaPersonaAtex5FilterBean();
		}
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaPersona.setIdContrato(utilesColegiado.getIdContratoSession());
			consultaPersona.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
		consultaPersona.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaPersona;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaPersona = (ConsultaPersonaAtex5FilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorAtex5";
	}

	public ConsultaPersonaAtex5FilterBean getConsultaPersona() {
		return consultaPersona;
	}

	public void setConsultaPersona(ConsultaPersonaAtex5FilterBean consultaPersona) {
		this.consultaPersona = consultaPersona;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public ResumenAtex5Bean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenAtex5Bean resumen) {
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

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

}