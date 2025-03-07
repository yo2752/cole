package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.core.model.enumerados.EstadoConSinNive;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionPermisosFichasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermisoFilterBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResumenPermisoDistintivoItvBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionPermisosDgtAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = -1774324684114344465L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionPermisosDgtAction.class);

	private static final String[] fetchList = {"contrato", "vehiculo","jefaturaTrafico"};
	private static final String POP_UP_ESTADOS = "popUpCambioEstado";
	private String codSeleccionados;
	private BigDecimal estadoNuevo;
	private GestionPermisoFilterBean gestionPermisos;
	private ResumenPermisoDistintivoItvBean resumen;
	private static final String COLUMDEFECT = "numExpediente";
	
	@Resource
	ModelPagination modeloGestionPermisoFichaDgtPaginated;
	
	@Autowired
	ServicioGestionPermisosFichasDgt servicioGestionPermisosDgt;
	
	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;
	
	@Override
	public String inicio() {
		cargarFiltroInicial();
		return SUCCESS;
	}

	public String solicitar(){
		try {
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionPermisosDgt.solicitarPermisos(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), getIpConexion());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los permisos, error: ",e);
			addActionError("Ha sucedido un error a la hora de solicitar los permisos.");
		}
		return actualizarPaginatedList();
	}
	
	public String imprimir(){
		try {
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionPermisosDgt.imprimirPermisos(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), getIpConexion());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage("Solicitud de impresion generada correctamente." + resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar la impresion de los permisos, error: ",e);
			addActionError("Ha sucedido un error a la hora de solicitar la impresión los permisos.");
		}
		return actualizarPaginatedList();
	}
	
	public String revertir(){
		try {
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionPermisosDgt.revertirPermisos(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), 
					utilesColegiado.tienePermisoAdmin(), getIpConexion());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de revertir los permisos, error: ",e);
			addActionError("Ha sucedido un error a la hora de los permisos.");
		}
		return actualizarPaginatedList();
	}
	
	public String generarKO(){
		try {
			if(utilesColegiado.tienePermisoAdmin()){
				ResultadoPermisoDistintivoItvBean resultado = servicioGestionPermisosDgt.generarDocKOPermisos(codSeleccionados,
						utilesColegiado.getIdUsuarioSessionBigDecimal(), getIpConexion());
				if(resultado.getError()){
					addActionError(resultado.getMensaje());
				} else {
					resumen = resultado.getResumen();
				}
			} else {
				addActionError("No tiene permisos para realizar dicha accion.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el documento con los permisos KO, error: ",e);
			addActionError("Ha sucedido un error a la hora de generar el documento con los permisos KO.");
		}
		return actualizarPaginatedList();
	}
	
	public String cambiarEstado(){
		try {
			if(utilesColegiado.tienePermisoAdmin()){
				ResultadoPermisoDistintivoItvBean resultado = servicioGestionPermisosDgt.cambiarEstadoPermisos(codSeleccionados, estadoNuevo,
						utilesColegiado.getIdUsuarioSessionBigDecimal(), getIpConexion());
				if (!resultado.getError()) {
					resumen = resultado.getResumen();
				} else {
					addActionError(resultado.getMensaje());
				}
			} else {
				addActionError("No tiene permisos para realizar dicha accion.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de los permisos, error: ",e);
			addActionError("Ha sucedido un error a la hora de cambiar el estado de los permisos");
		}
		return actualizarPaginatedList();
	}
	public String desasignar(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionPermisosDgt.desasignarPermisos(codSeleccionados, 
					utilesColegiado.getIdUsuarioSessionBigDecimal(), getIpConexion());
			if (!resultado.getError()) {
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		}else{
			addActionError("Debe seleccionar algun documento para desasignar.");
		}
		return actualizarPaginatedList();
	}
	
	@Override
	public Boolean validarFechas(Object object) {
		Boolean esRangoValido = Boolean.FALSE;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		gestionPermisos = (GestionPermisoFilterBean) object;
		if(StringUtils.isBlank(gestionPermisos.getMatricula()) && StringUtils.isBlank(gestionPermisos.getBastidor())
				&& gestionPermisos.getNumExpediente() == null && StringUtils.isBlank(gestionPermisos.getTipoTramite())
				&& StringUtils.isBlank(gestionPermisos.getNumSeriePermiso()) && StringUtils.isBlank(gestionPermisos.getNive())
				&& StringUtils.isBlank(gestionPermisos.getNif())){
			if(gestionPermisos.getFechaPresentacion() != null && !gestionPermisos.getFechaPresentacion().isfechaNula() && 
					gestionPermisos.getFechaPresentacion().getFechaInicio() != null &&
							gestionPermisos.getFechaPresentacion().getFechaFin() != null){
				if(StringUtils.isNotBlank(valorRangoFechas)){
					esRangoValido = utilesFecha.comprobarRangoFechas(gestionPermisos.getFechaPresentacion().getFechaInicio(), 
							gestionPermisos.getFechaPresentacion().getFechaFin(), Integer.parseInt(valorRangoFechas));
					
				}
			}
			if(!esRangoValido){
				addActionError("Debe indicar un rango de fechas no mayor a " + valorRangoFechas + " dias para poder obtener los datos de los tramites.");
			}
		} else {
			esRangoValido = Boolean.TRUE;
		}
		return esRangoValido;
	}
	
	private String getIpConexion() {
		String ipConexion = ServletActionContext.getRequest().getRemoteAddr();
		
		if(ServletActionContext.getRequest().getHeader("client-ip") != null){
		  ipConexion = ServletActionContext.getRequest().getHeader("client-ip");
	    }
		 
		return ipConexion;
	}
	
	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	
	public String cargarPopUpCambioEstado() {
		return POP_UP_ESTADOS;
	}
	
	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorPermisosFichasDgt";
	}
	
	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloGestionPermisoFichaDgtPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		List<BigDecimal> listaEstadosTram = new ArrayList<BigDecimal>();
		listaEstadosTram.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
		listaEstadosTram.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()));
		listaEstadosTram.add(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()));
		gestionPermisos.setEstadoTramite(listaEstadosTram);
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			gestionPermisos.setIdContrato(utilesColegiado.getIdContratoSession());
		} 
		List<String> listaEstados = null;
		if(EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(gestionPermisos.getEstadoSolicitud())){
			listaEstados = new ArrayList<String>();
			listaEstados.add(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
			gestionPermisos.setEstadosSolicitud(listaEstados);
			gestionPermisos.setEstadoSolicitudPrm(null);
		} else {
			gestionPermisos.setEstadoSolicitudPrm(gestionPermisos.getEstadoSolicitud());
			gestionPermisos.setEstadosSolicitud(null);
		}
		
		if(EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(gestionPermisos.getEstadoImpresion())){
			listaEstados = new ArrayList<String>();
			listaEstados.add(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
			gestionPermisos.setEstadosImpresion(listaEstados);
			gestionPermisos.setEstadoImpresionPrm(null);
		} else{
			gestionPermisos.setEstadoImpresionPrm(gestionPermisos.getEstadoImpresion());
			gestionPermisos.setEstadosImpresion(null);
		}
		
		List<String> listaTiposTramites = null;
		if(gestionPermisos.getTipoTramite() != null && !gestionPermisos.getTipoTramite().isEmpty()){
			gestionPermisos.setTiposTramites(null);
			gestionPermisos.setTipoTramitePermisos(gestionPermisos.getTipoTramite());
		} else {
			listaTiposTramites = new ArrayList<String>();
			listaTiposTramites.add(TipoTramiteTrafico.Matriculacion.getValorEnum());
			listaTiposTramites.add(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
			listaTiposTramites.add(TipoTramiteTrafico.Duplicado.getValorEnum());
			gestionPermisos.setTiposTramites(listaTiposTramites);
			gestionPermisos.setTipoTramitePermisos(null);
		}

		
		if(gestionPermisos.getNif() != null && !gestionPermisos.getNif().isEmpty()){
			if(TipoTramiteTrafico.Matriculacion.getValorEnum().equals(gestionPermisos.getTipoTramite())){
				gestionPermisos.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
			} else if(TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(gestionPermisos.getTipoTramite())){
				gestionPermisos.setTipoInterviniente(TipoInterviniente.Adquiriente.getValorEnum());
			}
		}
		
		if(gestionPermisos.getNif() != null && gestionPermisos.getNif().isEmpty()){
			gestionPermisos.setNif(gestionPermisos.getNif().trim().replace("-", "").toUpperCase());
		}
		
		if(gestionPermisos.getMatricula() != null && !gestionPermisos.getMatricula().isEmpty()){
			gestionPermisos.setMatricula(gestionPermisos.getMatricula().trim().replace("-", "").toUpperCase());
		}
		
		if(utilesColegiado.tienePermisosCiudadReal_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.CIUDADREAL.getJefatura());
		} else if(utilesColegiado.tienePermisosAlcala_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.ALCALADEHENARES.getJefatura());
		} else if(utilesColegiado.tienePermisosSegovia_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.SEGOVIA.getJefatura());
		} else if(utilesColegiado.tienePermisosCuenca_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.CUENCA.getJefatura());
		} else if(utilesColegiado.tienePermisosGuadalajara_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.GUADALAJARA.getJefatura());
		} else if(utilesColegiado.tienePermisosAvila_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.AVILA.getJefatura());
		} else if(utilesColegiado.tienePermisosMadrid_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.MADRID.getJefatura());
		} else if(utilesColegiado.tienePermisosAlcorcon_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.ALCORCON.getJefatura());
		}
		
		if(StringUtils.isNotBlank(gestionPermisos.getNive()) && EstadoConSinNive.SI.getValorEnum().equals(gestionPermisos.getNive())) {
			gestionPermisos.setConNive(Boolean.TRUE);
			gestionPermisos.setSinNive(null);
		} else if(StringUtils.isNotBlank(gestionPermisos.getNive()) && EstadoConSinNive.NO.getValorEnum().equals(gestionPermisos.getNive())) {
			gestionPermisos.setConNive(null);
			gestionPermisos.setSinNive(Boolean.TRUE);
		} else {
			gestionPermisos.setConNive(null);
			gestionPermisos.setSinNive(null);
		}
		
	}

	@Override
	protected void cargarFiltroInicial() {
		if(gestionPermisos == null){
			gestionPermisos = new GestionPermisoFilterBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			gestionPermisos.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		gestionPermisos.setFechaPresentacion(utilesFecha.getFechaFracionadaActual());
		
		if(utilesColegiado.tienePermisosCiudadReal_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.CIUDADREAL.getJefatura());
		} else if(utilesColegiado.tienePermisosAlcala_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.ALCALADEHENARES.getJefatura());
		} else if(utilesColegiado.tienePermisosSegovia_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.SEGOVIA.getJefatura());
		} else if(utilesColegiado.tienePermisosCuenca_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.CUENCA.getJefatura());
		} else if(utilesColegiado.tienePermisosGuadalajara_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.GUADALAJARA.getJefatura());
		} else if(utilesColegiado.tienePermisosAvila_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.AVILA.getJefatura());
		} else if(utilesColegiado.tienePermisosMadrid_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.MADRID.getJefatura());
		} else if(utilesColegiado.tienePermisosAlcorcon_IMPR()){
			gestionPermisos.setJefaturaTrafico(JefaturasJPTEnum.ALCORCON.getJefatura());
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return gestionPermisos;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		gestionPermisos = (GestionPermisoFilterBean) object;
	}
	
	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public GestionPermisoFilterBean getGestionPermisos() {
		return gestionPermisos;
	}

	public void setGestionPermisos(GestionPermisoFilterBean gestionPermisos) {
		this.gestionPermisos = gestionPermisos;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ResumenPermisoDistintivoItvBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenPermisoDistintivoItvBean resumen) {
		this.resumen = resumen;
	}

	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

}
