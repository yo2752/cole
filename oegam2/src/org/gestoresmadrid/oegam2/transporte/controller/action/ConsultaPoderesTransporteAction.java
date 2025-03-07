package org.gestoresmadrid.oegam2.transporte.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.transporte.model.service.ServicioConsultaPoderesTransporte;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ConsultaPoderesTransFilterBean;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ResultadoTransporteBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaPoderesTransporteAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = 1404509962813400028L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaPoderesTransporteAction.class);
	
	private static final String DESCARGAR_PDF = "descargarPdf";
	
	private ConsultaPoderesTransFilterBean consultaPoderTransporte;
	
	private static final String[] fetchList = {"contrato","provincia"};
	
	private String codSeleccionados;
	private InputStream inputStream;
	private String fileName;
	
	@Autowired
	ServicioConsultaPoderesTransporte servicioConsultaPoderesTransporte;

	@Resource
	ModelPagination modeloConsultaPoderesTransportePaginated;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	@Autowired
	private Utiles utiles;
	
	public String descargar(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoTransporteBean resultado = servicioConsultaPoderesTransporte.descargarPoderesBloque(codSeleccionados);
			if(!resultado.getError()){
				try {
					inputStream = new FileInputStream((File) resultado.getFichero());
					fileName = resultado.getNombreFichero();
					if(resultado.getEsZip()){
						servicioConsultaPoderesTransporte.borrarZip(resultado.getFichero());
					}
					return DESCARGAR_PDF;
				} catch (FileNotFoundException e) {
					log.error("Ha sucedido un error a la hora de descargar el fichero, error:",e);
				}
			} else {
				if (resultado.getListaErrores() != null && !resultado.getListaErrores().isEmpty()) {
					for (String validacion : resultado.getListaErrores()) {
						addActionError(validacion);
					}
				} else {
					addActionError(resultado.getMensaje());
				}
			}
		} else {
			addActionError("Debe de seleccionar algún poder para poder descargarlo.");
		}
		return actualizarPaginatedList();
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		if(utiles.esUsuarioOegam3(utilesColegiado.getIdUsuarioSessionBigDecimal().toString())) {
			return "redireccionOegam3";
		}else {
			return SUCCESS;
		}
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaPoderesTransportePaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaPoderTransporte != null) {
			consultaPoderTransporte.setIdProvincia("28");
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			consultaPoderTransporte.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(consultaPoderTransporte == null){
			consultaPoderTransporte = new ConsultaPoderesTransFilterBean();
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			consultaPoderTransporte.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		consultaPoderTransporte.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaPoderTransporte;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaPoderTransporte = (ConsultaPoderesTransFilterBean) object;
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

	public ConsultaPoderesTransFilterBean getConsultaPoderTransporte() {
		return consultaPoderTransporte;
	}

	public void setConsultaPoderTransporte(
			ConsultaPoderesTransFilterBean consultaPoderTransporte) {
		this.consultaPoderTransporte = consultaPoderTransporte;
	}

}
