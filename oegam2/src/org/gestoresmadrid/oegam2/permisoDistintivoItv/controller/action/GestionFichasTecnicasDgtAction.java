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
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionFichasTecnicasFilterBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResumenPermisoDistintivoItvBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionFichasTecnicasDgtAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = 7903680380446575129L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionFichasTecnicasDgtAction.class);

	private static final String[] fetchList = {"contrato", "vehiculo","docFichaTecnicaVO", "jefaturaTrafico"};
	private static final String POP_UP_ESTADOS = "popUpCambioEstado";
	private String codSeleccionados;
	private BigDecimal estadoNuevo;
	private GestionFichasTecnicasFilterBean gestionFichasTecnicas;
	private ResumenPermisoDistintivoItvBean resumen;

	@Resource
	ModelPagination modeloGestionPermisoFichaDgtPaginated;

	@Autowired
	ServicioGestionPermisosFichasDgt servicioGestionFichasDgt;

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

	public String generarKO(){
		try {
			if(utilesColegiado.tienePermisoAdmin()){
				ResultadoPermisoDistintivoItvBean resultado = servicioGestionFichasDgt.
						generarDocKOFichas(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), getIpConexion());
				if(resultado.getError()){
					addActionError(resultado.getMensaje());
				} else {
					resumen = resultado.getResumen();
				}
			} else {
				addActionError("No tiene permisos para realizar dicha accion.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el documento con las fichas KO, error: ",e);
			addActionError("Ha sucedido un error a la hora de generar el documento con las fichas KO.");
		}
		return actualizarPaginatedList();
	}
	
	public String solicitar(){
		try {
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionFichasDgt.solicitarFichas(codSeleccionados,
					utilesColegiado.getIdUsuarioSessionBigDecimal(), getIpConexion());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar las fichas técnicas, error: ",e);
			addActionError("Ha sucedido un error a la hora de solicitar las fichas técnicas.");
		}
		return actualizarPaginatedList();
	}

	public String imprimir(){
		try {
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionFichasDgt.imprimirFichas(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), getIpConexion());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage("Solicitud de impresion generada correctamente." + resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar la impresion de las fichas técnicas, error: ",e);
			addActionError("Ha sucedido un error a la hora de solicitar la impresión las fichas técnicas.");
		}
		return actualizarPaginatedList();
	}

	public String revertir(){
		try {
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionFichasDgt.revertirFichas(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(),
					utilesColegiado.tienePermisoAdmin(), getIpConexion());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar la impresion de las fichas técnicas, error: ",e);
			addActionError("Ha sucedido un error a la hora de solicitar la impresión las fichas técnicas.");
		}
		return actualizarPaginatedList();
	}

	public String cambiarEstado(){
		try {
			if(utilesColegiado.tienePermisoAdmin()){
				ResultadoPermisoDistintivoItvBean resultado = servicioGestionFichasDgt.cambiarEstadoFichas(codSeleccionados, estadoNuevo,
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
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionFichasDgt.desasignarFichas(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), getIpConexion());
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

	public String cargarPopUpCambioEstado() {
		return POP_UP_ESTADOS;
	}

	@Override
	public Boolean validarFechas(Object object) {
		Boolean esRangoValido = Boolean.FALSE;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		gestionFichasTecnicas = (GestionFichasTecnicasFilterBean) object;
		if(StringUtils.isBlank(gestionFichasTecnicas.getMatricula()) && StringUtils.isBlank(gestionFichasTecnicas.getBastidor())
				&& gestionFichasTecnicas.getNumExpediente() == null && StringUtils.isBlank(gestionFichasTecnicas.getTipoTramite())
				&& StringUtils.isBlank(gestionFichasTecnicas.getNive())
				&& StringUtils.isBlank(gestionFichasTecnicas.getEstadoSolicitudFicha())
				&& StringUtils.isBlank(gestionFichasTecnicas.getNif())){
			if(gestionFichasTecnicas.getFechaPresentacion() != null && !gestionFichasTecnicas.getFechaPresentacion().isfechaNula()
					&& gestionFichasTecnicas.getFechaPresentacion().getFechaInicio() != null
					&& gestionFichasTecnicas.getFechaPresentacion().getFechaFin() != null){
				if(StringUtils.isNotBlank(valorRangoFechas)){
					esRangoValido = utilesFecha.comprobarRangoFechas(gestionFichasTecnicas.getFechaPresentacion().getFechaInicio(),
							gestionFichasTecnicas.getFechaPresentacion().getFechaFin(), Integer.parseInt(valorRangoFechas));
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
		gestionFichasTecnicas.setEstadoTramite(listaEstadosTram);
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			gestionFichasTecnicas.setIdContrato(utilesColegiado.getIdContratoSession());
		}

		List<String> listaEstados = null;
		if(EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(gestionFichasTecnicas.getEstadoSolicitud())){
			listaEstados = new ArrayList<String>();
			listaEstados.add(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
			gestionFichasTecnicas.setEstadosSolicitud(listaEstados);
			gestionFichasTecnicas.setEstadoSolicitudFicha(null);
		} else {
			gestionFichasTecnicas.setEstadoSolicitudFicha(gestionFichasTecnicas.getEstadoSolicitud());
			gestionFichasTecnicas.setEstadosSolicitud(null);
		}

		if(EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(gestionFichasTecnicas.getEstadoImpresion())){
			listaEstados = new ArrayList<String>();
			listaEstados.add(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
			gestionFichasTecnicas.setEstadosImpresion(listaEstados);
			gestionFichasTecnicas.setEstadoImpresionFicha(null);
		} else{
			gestionFichasTecnicas.setEstadoImpresionFicha(gestionFichasTecnicas.getEstadoImpresion());
			gestionFichasTecnicas.setEstadosImpresion(null);
		}
		
		List<String> listaTiposTramites = null;
		if(gestionFichasTecnicas.getTipoTramite() != null && !gestionFichasTecnicas.getTipoTramite().isEmpty()){
			gestionFichasTecnicas.setTiposTramites(null);
			gestionFichasTecnicas.setTipoTramiteFicha(gestionFichasTecnicas.getTipoTramite());
		} else {
			listaTiposTramites = new ArrayList<String>();
			listaTiposTramites.add(TipoTramiteTrafico.Matriculacion.getValorEnum());
			listaTiposTramites.add(TipoTramiteTrafico.Duplicado.getValorEnum());
			gestionFichasTecnicas.setTiposTramites(listaTiposTramites);
			gestionFichasTecnicas.setTipoTramiteFicha(null);
		}
		if(gestionFichasTecnicas.getNif() != null && !gestionFichasTecnicas.getNif().isEmpty()){
			gestionFichasTecnicas.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
		}

		if(gestionFichasTecnicas.getNif() != null && gestionFichasTecnicas.getNif().isEmpty()){
			gestionFichasTecnicas.setNif(gestionFichasTecnicas.getNif().trim().replace("-", "").toUpperCase());
		}

		if(gestionFichasTecnicas.getMatricula() != null && !gestionFichasTecnicas.getMatricula().isEmpty()){
			gestionFichasTecnicas.setMatricula(gestionFichasTecnicas.getMatricula().trim().replace("-", "").toUpperCase());
		}
		if(utilesColegiado.tienePermisosCiudadReal_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.CIUDADREAL.getJefatura());
		} else if(utilesColegiado.tienePermisosAlcala_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.ALCALADEHENARES.getJefatura());
		} else if(utilesColegiado.tienePermisosSegovia_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.SEGOVIA.getJefatura());
		} else if(utilesColegiado.tienePermisosCuenca_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.CUENCA.getJefatura());
		} else if(utilesColegiado.tienePermisosGuadalajara_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.GUADALAJARA.getJefatura());
		} else if(utilesColegiado.tienePermisosAvila_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.AVILA.getJefatura());
		} else if(utilesColegiado.tienePermisosMadrid_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.MADRID.getJefatura());
		} else if(utilesColegiado.tienePermisosAlcorcon_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.ALCORCON.getJefatura());
		}

		if(StringUtils.isNotBlank(gestionFichasTecnicas.getNive()) && EstadoConSinNive.SI.getValorEnum().equals(gestionFichasTecnicas.getNive())) {
			gestionFichasTecnicas.setConNive(Boolean.TRUE);
			gestionFichasTecnicas.setSinNive(null);
		} else if(StringUtils.isNotBlank(gestionFichasTecnicas.getNive()) && EstadoConSinNive.NO.getValorEnum().equals(gestionFichasTecnicas.getNive())) {
			gestionFichasTecnicas.setConNive(null);
			gestionFichasTecnicas.setSinNive(Boolean.TRUE);
		} else {
			gestionFichasTecnicas.setConNive(null);
			gestionFichasTecnicas.setSinNive(null);
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(gestionFichasTecnicas == null){
			gestionFichasTecnicas = new GestionFichasTecnicasFilterBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			gestionFichasTecnicas.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		gestionFichasTecnicas.setFechaPresentacion(utilesFecha.getFechaFracionadaActual());
		if(utilesColegiado.tienePermisosCiudadReal_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.CIUDADREAL.getJefatura());
		} else if(utilesColegiado.tienePermisosAlcala_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.ALCALADEHENARES.getJefatura());
		} else if(utilesColegiado.tienePermisosSegovia_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.SEGOVIA.getJefatura());
		} else if(utilesColegiado.tienePermisosCuenca_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.CUENCA.getJefatura());
		} else if(utilesColegiado.tienePermisosGuadalajara_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.GUADALAJARA.getJefatura());
		} else if(utilesColegiado.tienePermisosAvila_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.AVILA.getJefatura());
		} else if(utilesColegiado.tienePermisosMadrid_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.MADRID.getJefatura());
		} else if(utilesColegiado.tienePermisosAlcorcon_IMPR()){
			gestionFichasTecnicas.setJefaturaTrafico(JefaturasJPTEnum.ALCORCON.getJefatura());
		}
	}

	private String getIpConexion() {
		String ipConexion = ServletActionContext.getRequest().getRemoteAddr();
		if(ServletActionContext.getRequest().getHeader("client-ip") != null){
			ipConexion = ServletActionContext.getRequest().getHeader("client-ip");
		}
		return ipConexion;
	}

	@Override
	protected Object getBeanCriterios() {
		return gestionFichasTecnicas;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		gestionFichasTecnicas = (GestionFichasTecnicasFilterBean) object;
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

	public ResumenPermisoDistintivoItvBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenPermisoDistintivoItvBean resumen) {
		this.resumen = resumen;
	}

	public GestionFichasTecnicasFilterBean getGestionFichasTecnicas() {
		return gestionFichasTecnicas;
	}

	public void setGestionFichasTecnicas(GestionFichasTecnicasFilterBean gestionFichasTecnicas) {
		this.gestionFichasTecnicas = gestionFichasTecnicas;
	}

	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

}