package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionDocPrmDstvFichas;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermDstvItvFilterBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.NumeroSerieBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResumenPermisoDistintivoItvBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionPermDstvItvAction extends AbstractPaginatedListAction{
	
	private static final long serialVersionUID = 3627948074243626067L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionPermDstvItvAction.class);
	
	private GestionPermDstvItvFilterBean gestionPermDstvItvFilterBean;
	private static final String POP_UP_NUM_SERIE = "popUpNumSerie";
	private static final String POP_UP_ESTADOS = "popUpCambioEstado";
	private String codSeleccionados;
	private String numExpediente;
	
	private InputStream inputStream;
	private String fileName;
	private Boolean esDocPermisos = Boolean.FALSE;
	private static final String[] fetchList = {"contrato","contrato.colegiado"};
	private ResumenPermisoDistintivoItvBean resumen;
	private static final String IMP_DESCARGAR = "impDescarga";
	private NumeroSerieBean numeroSeriePermiso;
	private BigDecimal estadoNuevo;
	private String jefaturaImpr;
	
	private String donde;
	
	@Resource
	ModelPagination modeloPermDstvItvPaginated;
	
	@Autowired
	ServicioGestionDocPrmDstvFichas servicioGestionDocPrmDstvFichas;
	
	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String cargarPopUpNumSerie() {
		esDocPermisos = servicioGestionDocPrmDstvFichas.esDocPermisos(codSeleccionados);
		return POP_UP_NUM_SERIE;
	}
	
	public String reiniciarImpr(){
		ResultadoPermisoDistintivoItvBean resultado = servicioGestionDocPrmDstvFichas.reiniciarDoc(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (!resultado.getError()) {
			resumen = resultado.getResumen();
		} else {
			addActionError(resultado.getMensaje());
		}
		return actualizarPaginatedList();
	}
	
	public String imprimir(){
		if(comprobarPermisosImpresionImpr()){
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionDocPrmDstvFichas.imprimirDoc(codSeleccionados,jefaturaImpr,
					utilesColegiado.getIdUsuarioSessionBigDecimal(), getIpConexion());
			if (!resultado.getError()) {
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionError("No tiene permisos para realizar dicha accion");
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
	
	public String reactivar(){
		ResultadoPermisoDistintivoItvBean resultado = servicioGestionDocPrmDstvFichas.reactivarDoc(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (!resultado.getError()) {
			resumen = resultado.getResumen();
		} else {
			addActionError(resultado.getMensaje());
		}
		return actualizarPaginatedList();
	}
	
	public String reactivarJefatura(){
		if(comprobarPermisosImpresionImpr()){
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionDocPrmDstvFichas.reactivarDocJefatura(codSeleccionados, jefaturaImpr, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (!resultado.getError()) {
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionError("No tiene permisos para realizar dicha accion");
		}
		return actualizarPaginatedList();
	}
	
	public String descargar(){
		if(comprobarPermisosImpresionImpr()){
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionDocPrmDstvFichas.descargarDoc(codSeleccionados);
			if (!resultado.getError()) {
				try{
					inputStream = new FileInputStream((File) resultado.getFichero());
					fileName = (String) resultado.getNombreFichero();
					if(resultado.getEsZip()){
						servicioGestionDocPrmDstvFichas.borrarFichero(resultado.getFichero());
					}
					return IMP_DESCARGAR;
				}catch(FileNotFoundException e) {
					log.error("No existe el fichero a descargar,error:", e);
					addActionError("No existe el fichero a descargar");
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		}else {
			addActionError("No tiene permisos para realizar dicha accion");
		}
		return actualizarPaginatedList();
	}
	
	public String cambiarEstado(){
		ResultadoPermisoDistintivoItvBean resultado = servicioGestionDocPrmDstvFichas.cambiarEstado(codSeleccionados, estadoNuevo,utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (!resultado.getError()) {
			resumen = resultado.getResumen();
		} else {
			addActionError(resultado.getMensaje());
		}
		return actualizarPaginatedList();
	}
	
	public String impresionAuto(){
		if(comprobarPermisosImpresionImpr()){
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionDocPrmDstvFichas.impresionAutoImpr(codSeleccionados, jefaturaImpr, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (!resultado.getError()) {
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionError("No tiene permisos para realizar dicha accion");
		}
		return actualizarPaginatedList();
	}
	
	public String anular(){
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			ResultadoPermisoDistintivoItvBean resultado = servicioGestionDocPrmDstvFichas.anular(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), getIpConexion());
			if (!resultado.getError()) {
				resumen = resultado.getResumen();
			} else {
				addActionError(resultado.getMensaje());
			}
		}else{
			addActionError("Debe seleccionar alguna consulta de documentos para anular.");
		}
		return actualizarPaginatedList();
	}
	
	private Boolean comprobarPermisosImpresionImpr() {
		if("CO".equals(jefaturaImpr) && utilesColegiado.tienePermisoAdmin()){
			return Boolean.TRUE;
		} else if(JefaturasJPTEnum.ALCALADEHENARES.getJefatura().equals(jefaturaImpr) && utilesColegiado.tienePermisosAlcala_IMPR()){
			return Boolean.TRUE;
		} else if(JefaturasJPTEnum.ALCORCON.getJefatura().equals(jefaturaImpr) && utilesColegiado.tienePermisosAlcorcon_IMPR()){
			return Boolean.TRUE;
		} else if(JefaturasJPTEnum.MADRID.getJefatura().equals(jefaturaImpr) && utilesColegiado.tienePermisosMadrid_IMPR()){
			return Boolean.TRUE;
		} else if(JefaturasJPTEnum.CIUDADREAL.getJefatura().equals(jefaturaImpr) && utilesColegiado.tienePermisosCiudadReal_IMPR()){
			return Boolean.TRUE;
		} else if(JefaturasJPTEnum.CUENCA.getJefatura().equals(jefaturaImpr) && utilesColegiado.tienePermisosCuenca_IMPR()){
			return Boolean.TRUE;
		} else if(JefaturasJPTEnum.AVILA.getJefatura().equals(jefaturaImpr) && utilesColegiado.tienePermisosAvila_IMPR()){
			return Boolean.TRUE;
		} else if(JefaturasJPTEnum.SEGOVIA.getJefatura().equals(jefaturaImpr) && utilesColegiado.tienePermisosSegovia_IMPR()){
			return Boolean.TRUE;
		} else if(JefaturasJPTEnum.GUADALAJARA.getJefatura().equals(jefaturaImpr) && utilesColegiado.tienePermisosGuadalajara_IMPR()){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@Override
	public Boolean validarFechas(Object object) {
		Boolean esRangoValido = Boolean.FALSE;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		gestionPermDstvItvFilterBean = (GestionPermDstvItvFilterBean) object;
		if(StringUtils.isBlank(gestionPermDstvItvFilterBean.getDocIdPerm()) 
				&& StringUtils.isBlank(gestionPermDstvItvFilterBean.getMatricula())
				&& gestionPermDstvItvFilterBean.getNumExpediente() == null ){
			if(gestionPermDstvItvFilterBean.getFechaAlta() != null && !gestionPermDstvItvFilterBean.getFechaAlta().isfechaNula() && 
					gestionPermDstvItvFilterBean.getFechaAlta().getFechaInicio() != null &&
					gestionPermDstvItvFilterBean.getFechaAlta().getFechaFin() != null){
				if(StringUtils.isNotBlank(valorRangoFechas)){
					esRangoValido = utilesFecha.comprobarRangoFechas(gestionPermDstvItvFilterBean.getFechaAlta().getFechaInicio(), 
							gestionPermDstvItvFilterBean.getFechaAlta().getFechaFin(), Integer.parseInt(valorRangoFechas));
				}
			}
			if(!esRangoValido){
				addActionError("Debe indicar un rango de fechas no mayor a " + valorRangoFechas + " dias para poder obtener los datos de los documentos.");
			}
		} else {
			esRangoValido = Boolean.TRUE;
		}
		return esRangoValido;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloPermDstvItvPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(gestionPermDstvItvFilterBean == null){
			cargarFiltroInicial();
		}
		if(utilesColegiado.tienePermisosCiudadReal_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.CIUDADREAL.getJefatura());
		} else if(utilesColegiado.tienePermisosAlcala_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.ALCALADEHENARES.getJefatura());
		} else if(utilesColegiado.tienePermisosSegovia_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.SEGOVIA.getJefatura());
		} else if(utilesColegiado.tienePermisosCuenca_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.CUENCA.getJefatura());
		} else if(utilesColegiado.tienePermisosGuadalajara_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.GUADALAJARA.getJefatura());
		} else if(utilesColegiado.tienePermisosAvila_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.AVILA.getJefatura());
		} else if(utilesColegiado.tienePermisosMadrid_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.MADRID.getJefatura());
		} else if(utilesColegiado.tienePermisosAlcorcon_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.ALCORCON.getJefatura());
		}else if(!utilesColegiado.tienePermisoAdmin()) {
			if(utilesColegiado.tienePermisoImpresionDstvGestor()){
				gestionPermDstvItvFilterBean.setIdContrato(utilesColegiado.getIdContratoSession());
				gestionPermDstvItvFilterBean.setJefatura(utilesColegiado.getIdJefaturaSesion());
				gestionPermDstvItvFilterBean.setTipoDocumento(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum());
			}
		}
		if(gestionPermDstvItvFilterBean.getMatricula() != null && !gestionPermDstvItvFilterBean.getMatricula().isEmpty()){
			gestionPermDstvItvFilterBean.setMatricula(gestionPermDstvItvFilterBean.getMatricula().toUpperCase().trim());
			if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(gestionPermDstvItvFilterBean.getTipoDocumento())){
				gestionPermDstvItvFilterBean.setMatriculaPerm(gestionPermDstvItvFilterBean.getMatricula());
			} else if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(gestionPermDstvItvFilterBean.getTipoDocumento())){
				gestionPermDstvItvFilterBean.setMatriculaDstv(gestionPermDstvItvFilterBean.getMatricula());
			} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(gestionPermDstvItvFilterBean.getTipoDocumento())){
				gestionPermDstvItvFilterBean.setMatriculaEitv(gestionPermDstvItvFilterBean.getMatricula());
			}
		}
		if(gestionPermDstvItvFilterBean.getNumExpediente() != null){
			if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(gestionPermDstvItvFilterBean.getTipoDocumento())){
				gestionPermDstvItvFilterBean.setNumExpedientePerm(gestionPermDstvItvFilterBean.getNumExpediente());
			} else if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(gestionPermDstvItvFilterBean.getTipoDocumento())){
				gestionPermDstvItvFilterBean.setNumExpedienteDstv(gestionPermDstvItvFilterBean.getNumExpediente());
			} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(gestionPermDstvItvFilterBean.getTipoDocumento())){
				gestionPermDstvItvFilterBean.setNumExpedienteEitv(gestionPermDstvItvFilterBean.getNumExpediente());
			}
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(gestionPermDstvItvFilterBean == null){
			gestionPermDstvItvFilterBean = new GestionPermDstvItvFilterBean();
		}
		if(utilesColegiado.tienePermisosCiudadReal_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.CIUDADREAL.getJefatura());
		} else if(utilesColegiado.tienePermisosAlcala_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.ALCALADEHENARES.getJefatura());
		} else if(utilesColegiado.tienePermisosSegovia_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.SEGOVIA.getJefatura());
		} else if(utilesColegiado.tienePermisosCuenca_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.CUENCA.getJefatura());
		} else if(utilesColegiado.tienePermisosGuadalajara_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.GUADALAJARA.getJefatura());
		} else if(utilesColegiado.tienePermisosAvila_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.AVILA.getJefatura());
		} else if(utilesColegiado.tienePermisosMadrid_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.MADRID.getJefatura());
		} else if(utilesColegiado.tienePermisosAlcorcon_IMPR()){
			gestionPermDstvItvFilterBean.setJefatura(JefaturasJPTEnum.ALCORCON.getJefatura());
		}else if(!utilesColegiado.tienePermisoAdmin()) {
			if(utilesColegiado.tienePermisoImpresionDstvGestor()){
				gestionPermDstvItvFilterBean.setIdContrato(utilesColegiado.getIdContratoSession());
				gestionPermDstvItvFilterBean.setJefatura(utilesColegiado.getIdJefaturaSesion());
				gestionPermDstvItvFilterBean.setTipoDocumento(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum());
			}
		}
		gestionPermDstvItvFilterBean.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		if(gestionPermDstvItvFilterBean != null && gestionPermDstvItvFilterBean.getMatricula()!= null) {
			gestionPermDstvItvFilterBean.setMatricula(gestionPermDstvItvFilterBean.getMatricula().toUpperCase());
		}
		return gestionPermDstvItvFilterBean;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}
	
	public String cargarPopUpCambioEstado() {
		return POP_UP_ESTADOS;
	}
	
	@Override
	protected void setBeanCriterios(Object object) {
		gestionPermDstvItvFilterBean = (GestionPermDstvItvFilterBean) object;
		
	}

	public GestionPermDstvItvFilterBean getGestionPermDstvItvFilterBean() {
		return gestionPermDstvItvFilterBean;
	}

	public void setGestionPermDstvItvFilterBean(GestionPermDstvItvFilterBean gestionPermDstvItvFilterBean) {
		this.gestionPermDstvItvFilterBean = gestionPermDstvItvFilterBean;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ModelPagination getModeloPermDstvItvPaginated() {
		return modeloPermDstvItvPaginated;
	}

	public void setModeloPermDstvItvPaginated(ModelPagination modeloPermDstvItvPaginated) {
		this.modeloPermDstvItvPaginated = modeloPermDstvItvPaginated;
	}

	public String getNumExpediente() {
		return numExpediente;
	}


	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
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

	public ResumenPermisoDistintivoItvBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenPermisoDistintivoItvBean resumen) {
		this.resumen = resumen;
	}

	public NumeroSerieBean getNumeroSeriePermiso() {
		return numeroSeriePermiso;
	}

	public void setNumeroSeriePermiso(NumeroSerieBean numeroSeriePermiso) {
		this.numeroSeriePermiso = numeroSeriePermiso;
	}

	public Boolean getEsDocPermisos() {
		return esDocPermisos;
	}

	public void setEsDocPermisos(Boolean esDocPermisos) {
		this.esDocPermisos = esDocPermisos;
	}

	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public String getJefaturaImpr() {
		return jefaturaImpr;
	}

	public void setJefaturaImpr(String jefaturaImpr) {
		this.jefaturaImpr = jefaturaImpr;
	}

	public String getDonde() {
		return donde;
	}

	public void setDonde(String donde) {
		this.donde = donde;
	}
}