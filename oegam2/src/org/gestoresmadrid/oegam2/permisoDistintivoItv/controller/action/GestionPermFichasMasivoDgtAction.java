package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionImpr;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermFichaMasivoFilterBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionPermFichasMasivoDgtAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = -1831841319277237053L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionPermFichasMasivoDgtAction.class);
	
	private static final String[] fetchList = {"contrato","docPermisoVO","docFichaTecnicaVO"};
	
	private GestionPermFichaMasivoFilterBean gestionPermFicha;
	
	@Resource
	ModelPagination modeloGestionPermFichaMasivoPaginated;
	
	@Autowired
	ServicioGestionImpr servicioGestionImpr;
	
	public String solicitarPermisos(){
		try {
			if(gestionPermFicha != null){
				ResultadoPermisoDistintivoItvBean resultado = servicioGestionImpr.crearSolicitudImprPantalla(gestionPermFicha.getIdContrato(),
						gestionPermFicha.getTipoTramite(), gestionPermFicha.getFechaPresentacion().getFechaInicio(), getIpConexion()); 
				if(resultado.getError()){
					addActionError(resultado.getMensaje());
				} else {
					addActionMessage(resultado.getMensaje());
				}
			} else {
				addActionError("Debe de seleccionar el tipo de tramites, contrato y fecha de presentación para poder obtener su documentación.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la solicitud de impresion de los documentos, error: ",e);
			addActionError("Ha sucedido un error a la hora de generar la solicitud de impresion de los documentos.");
		}
		return actualizarPaginatedList();
	}
	
	private String getIpConexion() {
		String ipConexion = ServletActionContext.getRequest().getRemoteAddr();
		if(ServletActionContext.getRequest().getHeader("client-ip") != null){
			ipConexion = ServletActionContext.getRequest().getHeader("client-ip"); 
		}
		 
		return ipConexion;
	}
	
	public String solicitarFichas(){
		try {
			if(gestionPermFicha != null){
				ResultadoPermisoDistintivoItvBean resultado = servicioGestionImpr.crearSolicitudImprFichasPantalla(gestionPermFicha.getIdContrato(),
						gestionPermFicha.getTipoTramite(), gestionPermFicha.getFechaPresentacion().getFechaInicio(), getIpConexion()); 
				if(resultado.getError()){
					addActionError(resultado.getMensaje());
				} else {
					if(resultado.getListaOk() != null && !resultado.getListaOk().isEmpty()){
						for(String mensajes : resultado.getListaOk()){
							addActionMessage(mensajes);
						}
					}
					if(resultado.getListaError() != null && !resultado.getListaError().isEmpty()){
						for(String mensajes : resultado.getListaError()){
							addActionError(mensajes);
						}
					}
				}
			} else {
				addActionError("Debe de seleccionar el tipo de tramites, contrato y fecha de presentación para poder obtener su documentación.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la solicitud de impresion de los documentos, error: ",e);
			addActionError("Ha sucedido un error a la hora de generar la solicitud de impresion de los documentos.");
		}
		return actualizarPaginatedList();
	}
	
	@Override
	protected boolean isBuscarInicio() {
		return Boolean.FALSE;
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloGestionPermFichaMasivoPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(gestionPermFicha == null){
			gestionPermFicha = new GestionPermFichaMasivoFilterBean();
		}
		
		List<String> listaTiposTramites = null;
		if(gestionPermFicha.getTipoTramite() != null && !gestionPermFicha.getTipoTramite().isEmpty()){
			gestionPermFicha.setTiposTramites(null);
		} else {
			listaTiposTramites = new ArrayList<String>();
			listaTiposTramites.add(TipoTramiteTrafico.Matriculacion.getValorEnum());
			listaTiposTramites.add(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
			listaTiposTramites.add(TipoTramiteTrafico.Baja.getValorEnum());
			listaTiposTramites.add(TipoTramiteTrafico.Duplicado.getValorEnum());
			gestionPermFicha.setTiposTramites(listaTiposTramites);
		}
		
		if(gestionPermFicha.getFechaPresentacion() != null 
				&& !gestionPermFicha.getFechaPresentacion().isfechaInicioNula()){
			gestionPermFicha.getFechaPresentacion().setAnioFin(gestionPermFicha.getFechaPresentacion().getAnioInicio());
			gestionPermFicha.getFechaPresentacion().setMesFin(gestionPermFicha.getFechaPresentacion().getMesInicio());
			gestionPermFicha.getFechaPresentacion().setDiaFin(gestionPermFicha.getFechaPresentacion().getDiaInicio());
		}
		
		if(gestionPermFicha.getEstadoTramite() == null || gestionPermFicha.getEstadoTramite().isEmpty()){
			gestionPermFicha.setEstadoTramite(new ArrayList<BigDecimal>());
			gestionPermFicha.getEstadoTramite().add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
			gestionPermFicha.getEstadoTramite().add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
			gestionPermFicha.getEstadoTramite().add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));
		}
		
		if(gestionPermFicha.getTipoTramite() != null && !gestionPermFicha.getTipoTramite().isEmpty()){
			if(TipoTramiteTrafico.Matriculacion.getValorEnum().equals(gestionPermFicha.getTipoTramite())){
				gestionPermFicha.setbDocFicha(Boolean.TRUE);
				gestionPermFicha.setbDocPermiso(Boolean.TRUE);
			} else {
				gestionPermFicha.setImprPermisoCircu("SI");
				gestionPermFicha.setbDocPermiso(Boolean.TRUE);
			}
		} else {
			gestionPermFicha.setImprPermisoCircu(null);
			gestionPermFicha.setbDocFicha(null);
			gestionPermFicha.setbDocPermiso(null);
		}
	}

	@Override
	protected void cargarFiltroInicial() {
	}

	@Override
	protected Object getBeanCriterios() {
		return gestionPermFicha;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		gestionPermFicha = (GestionPermFichaMasivoFilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public GestionPermFichaMasivoFilterBean getGestionPermFicha() {
		return gestionPermFicha;
	}

	public void setGestionPermFicha(GestionPermFichaMasivoFilterBean gestionPermFicha) {
		this.gestionPermFicha = gestionPermFicha;
	}
}
