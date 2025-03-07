package org.gestoresmadrid.oegam2.transporte.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.transporte.model.enumeration.EstadosNotificacionesTransporte;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.transporte.model.service.ServicioConsultaNotificacionesTransporte;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ConsultaNotificacionesTransFilterBean;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ResultadoTransporteBean;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ResumenNotificacionesTransBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class NotificacionesTransporteAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = -486625005930109700L;
	
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(NotificacionesTransporteAction.class);
	
	private static final String[] fetchList = {"contrato","provincia"};

	private static final String DESCARGAR_PDF = "descargarPdf";
	
	private ConsultaNotificacionesTransFilterBean consultaNotificacionTransporte;
	
	private ResumenNotificacionesTransBean resumen;
	
	private String codSeleccionados;
	private InputStream inputStream;
	private String fileName;
	private String nombreFichero;
	
	@Resource
	ModelPagination modeloNotificacionTransportePaginated;
	
	@Autowired
	ServicioConsultaNotificacionesTransporte servicioConsultaNotificacionesTransporte;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	@Autowired
	private Utiles utiles;
	
	public String rechazarNotificaciones(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoTransporteBean resultado = servicioConsultaNotificacionesTransporte.rechazarNotificacionesBloque(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				if(resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()){
					aniadirListaErrores(resultado.getListaMensajes());
				} else {
					addActionError(resultado.getMensaje());
				}
			} else {
				rellenarResumen(resultado, Boolean.FALSE, Boolean.TRUE);
			}
		} else {
			addActionError("Debe seleccionar alguna notificación para poder rechazarla.");
		}
		return actualizarPaginatedList();
	}
	
	
	public String imprimirNotificaciones(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoTransporteBean resultado = servicioConsultaNotificacionesTransporte.imprimirNotificacionesBloque(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
			if(resultado.getError()){
				if(resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()){
					aniadirListaErrores(resultado.getListaMensajes());
				} else {
					addActionError(resultado.getMensaje());
				}
			} else {
				rellenarResumen(resultado, Boolean.TRUE,Boolean.FALSE);
			}
		} else {
			addActionError("Debe seleccionar alguna notificación para su impresión.");
		}
		return actualizarPaginatedList();
	}
	
	public String obtenerFichero(){
		try {
			ResultadoTransporteBean resultado = servicioConsultaNotificacionesTransporte.descargarPdf(nombreFichero);
			if(!resultado.getError()){
				inputStream = new FileInputStream((File) resultado.getFichero());
				fileName = resultado.getNombreFichero();
				return DESCARGAR_PDF;
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (FileNotFoundException e) {
			LOG.error("Ha sucedido un error a la hora de descargar el pdf de notificaciones, error: ",e);
			addActionError("Ha sucedido un error a la hora de descargar el pdf de notificaciones");
		}
		return actualizarPaginatedList();
	}
	
	public String descargarPdfBloque(){
		try {
			ResultadoTransporteBean resultado = servicioConsultaNotificacionesTransporte.descargarNotificacionesBloque(codSeleccionados);
			if(!resultado.getError()){
				inputStream = new FileInputStream((File) resultado.getFichero());
				fileName = resultado.getNombreFichero();
				if(resultado.getEsZip()){
					servicioConsultaNotificacionesTransporte.borrarZip(resultado.getFichero());
				}
				return DESCARGAR_PDF;
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (FileNotFoundException e) {
			LOG.error("Ha sucedido un error a la hora de descargar las notificaciones en bloque, error: ",e);
			addActionError("Ha sucedido un error a la hora de descargar  las notificaciones.");
		}
		return actualizarPaginatedList();
	}
	
	private void rellenarResumen(ResultadoTransporteBean resultado, Boolean esImprimir, Boolean esRechazar) {
		resumen = new ResumenNotificacionesTransBean();
		resumen.setEsImprimir(esImprimir);
		resumen.setEsRechazar(esRechazar);
		if(resultado.getNombreFichero() != null && !resultado.getNombreFichero().isEmpty()){
			nombreFichero = resultado.getNombreFichero();
			resumen.setExisteFichero(Boolean.TRUE);
		}
		resumen.setListaMensajesError(resultado.getListaErrores());
		resumen.setListaMensajesOk(resultado.getListaOK());
		resumen.setNumError(resultado.getNumError());
		resumen.setNumOK(resultado.getNumOk());
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
		return modeloNotificacionTransportePaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return LOG;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaNotificacionTransporte != null) {
			if (consultaNotificacionTransporte.getNombreEmpresa() != null && !consultaNotificacionTransporte.getNombreEmpresa().isEmpty()) {
				consultaNotificacionTransporte.setNombreEmpresa("%" + consultaNotificacionTransporte.getNombreEmpresa().toUpperCase() + "%");
			}
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			consultaNotificacionTransporte.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(consultaNotificacionTransporte == null){
			consultaNotificacionTransporte =  new ConsultaNotificacionesTransFilterBean();
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			consultaNotificacionTransporte.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		consultaNotificacionTransporte.setEstado(EstadosNotificacionesTransporte.Por_Defecto.getValorEnum());
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaNotificacionTransporte;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaNotificacionTransporte = (ConsultaNotificacionesTransFilterBean) object;
	}


	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public ConsultaNotificacionesTransFilterBean getConsultaNotificacionTransporte() {
		return consultaNotificacionTransporte;
	}


	public void setConsultaNotificacionTransporte(
			ConsultaNotificacionesTransFilterBean consultaNotificacionTransporte) {
		this.consultaNotificacionTransporte = consultaNotificacionTransporte;
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


	public ResumenNotificacionesTransBean getResumen() {
		return resumen;
	}


	public void setResumen(ResumenNotificacionesTransBean resumen) {
		this.resumen = resumen;
	}


	public String getNombreFichero() {
		return nombreFichero;
	}


	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

}
