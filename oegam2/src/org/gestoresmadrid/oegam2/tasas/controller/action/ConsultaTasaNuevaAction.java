package org.gestoresmadrid.oegam2.tasas.controller.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioConsultaTasasNueva;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ConsultaTasaNuevaFilterBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoConsultaTasasBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenTasasBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ConsultaTasaNuevaAction extends AbstractPaginatedListAction{

	private static final long serialVersionUID = 7910470889672738736L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaTasaNuevaAction.class);
	
	private static final String[] fetchList = {"tramiteTrafico","contrato","colegiado"};
	
	private String idCodigoTasa;
	private String idCodigoTasaPegatina;
	private ResumenTasasBean resumen;
	
	private TasaDto detalle;
	private InputStream ficheroTasas;
	
	@Resource
	ModelPagination modeloConsultaTasasPaginatedImpl;
	
	@Autowired
	ServicioConsultaTasasNueva servicioConsultaTasa;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	ConsultaTasaNuevaFilterBean consultaTasa;
	
	public String eliminarBloque() throws Throwable {
		if(idCodigoTasa != null && !idCodigoTasa.isEmpty()){
			ResultadoConsultaTasasBean resultado = servicioConsultaTasa.eliminarTasaB(idCodigoTasa,utilesColegiado.getIdUsuarioSessionBigDecimal(),utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
			if(resultado.getError()){
				addActionMessage(resultado.getMensaje());
			}else{
				rellenarResumen(resultado);
			}
		}else{
			addActionError("Debe seleccionar alguna tasa para poder eliminar");
		}
		return actualizarPaginatedList();
	
	}
	
	public String exportar() throws Throwable{
		if(idCodigoTasa != null && !idCodigoTasa.isEmpty()){
			ResultadoConsultaTasasBean resultado = servicioConsultaTasa.exportar(idCodigoTasa,utilesColegiado.getIdUsuarioSessionBigDecimal(),utilesColegiado.getIdContratoSessionBigDecimal(),utilesColegiado.tienePermisoAdmin());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				rellenarResumen(resultado);
			}
		}else{
			addActionError("Las tasas exportadas no son del mismo contrato");
		}
		return actualizarPaginatedList();
	}
	public String descargarFichero() {
		// String ruta = "C:/temp/importacion/";
		String idSession = ServletActionContext.getRequest().getSession().getId();

		try {
			FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.EXPORTAR, ConstantesGestorFicheros.TASAS, utilesFecha.getFechaActual(),
					ConstantesGestorFicheros.NOMBRE_FICHERO_TASA + idSession, ConstantesGestorFicheros.EXTENSION_TXT);
			if (FileResultStatus.ON_DEMAND_FILE.equals(fileResultBean.getStatus())) {
				addActionError(fileResultBean.getMessage());
			} else {
				ficheroTasas = new FileInputStream(fileResultBean.getFile());
			}

		} catch (FileNotFoundException e) {
			log.error(e);
			return navegar();
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el archivo", e);
			return navegar();
		}
		return "ficheroDownload";
	}
	
	
	public String generarCertificadoBloque() throws Throwable {
		if(idCodigoTasa != null && !idCodigoTasa.isEmpty()){
			ResultadoConsultaTasasBean resultado = servicioConsultaTasa.generarCertificado(idCodigoTasa,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				rellenarResumen(resultado);
			}
		}else{
			addActionError("No se ha podido generar el certificado para las tasas seleccionadas");
		}
		
		return actualizarPaginatedList();
	}
	
	public String desasignarBloque() throws Throwable{
		if(idCodigoTasa != null && !idCodigoTasa.isEmpty()){
			ResultadoConsultaTasasBean resultado = servicioConsultaTasa.desasignarTasaB(idCodigoTasa,consultaTasa.getNumExpediente(),utilesColegiado.getIdUsuarioSessionBigDecimal(),utilesColegiado.getIdContratoSessionBigDecimal(),utilesColegiado.tienePermisoAdmin());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				rellenarResumen(resultado);
			}
		}else{
			addActionError("Se ha producido un error al desasignar la tasa");
		}
		
		return actualizarPaginatedList();
	}

	public String generarTasasPegatinas(){
		if(idCodigoTasa != null && !idCodigoTasa.isEmpty()){
		ResultadoConsultaTasasBean resultado = servicioConsultaTasa.comprobarDatosObligatoriosGeneracionTasasEtiqueta(idCodigoTasa, idCodigoTasaPegatina);
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			}else{
				rellenarResumen(resultado);
			}
		}else{
				addActionError("No se ha podido comprobar los datos para la generación de las tasas de etiqueta");
			}
		return actualizarPaginatedList();
	}

	private void rellenarResumen(ResultadoConsultaTasasBean resultado) {
		resumen = new ResumenTasasBean();
		resumen.setNumError(resultado.getNumError());
		resumen.setListaErrores(resultado.getListaError());
		resumen.setNumOk(resultado.getNumOk());
		resumen.setListaOk(resultado.getListaOk());
	}
	
	
	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaTasasPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaTasa.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(consultaTasa == null){
			consultaTasa = new ConsultaTasaNuevaFilterBean();
		}
		if(!utilesColegiado.tienePermisoAdmin()){
			consultaTasa.setIdContrato(utilesColegiado.getIdContratoSession());
			consultaTasa.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
			consultaTasa.setFechaAlta(utilesFecha.getFechaFracionadaActual());
			consultaTasa.setFormato(FormatoTasa.ELECTRONICO.getCodigo());
		}

	@Override
	protected Object getBeanCriterios() {
		return consultaTasa;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaTasa = (ConsultaTasaNuevaFilterBean) object;
		
	}
	
	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorTasas";
	}

	public String getIdCodigoTasa() {
		return idCodigoTasa;
	}

	public void setIdCodigoTasa(String idCodigoTasa) {
		this.idCodigoTasa = idCodigoTasa;
	}

	public ResumenTasasBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenTasasBean resumen) {
		this.resumen = resumen;
	}


	public TasaDto getDetalle() {
		return detalle;
	}


	public void setDetalle(TasaDto detalle) {
		this.detalle = detalle;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	public ModelPagination getModeloConsultaTasasPaginatedImpl() {
		return modeloConsultaTasasPaginatedImpl;
	}

	public void setModeloConsultaTasasPaginatedImpl(ModelPagination modeloConsultaTasasPaginatedImpl) {
		this.modeloConsultaTasasPaginatedImpl = modeloConsultaTasasPaginatedImpl;
	}

	public String getIdCodigoTasaPegatina() {
		return idCodigoTasaPegatina;
	}

	public void setIdCodigoTasaPegatina(String idCodigoTasaPegatina) {
		this.idCodigoTasaPegatina = idCodigoTasaPegatina;
	}

	public ServicioConsultaTasasNueva getServicioConsultaTasa() {
		return servicioConsultaTasa;
	}

	public void setServicioConsultaTasa(ServicioConsultaTasasNueva servicioConsultaTasa) {
		this.servicioConsultaTasa = servicioConsultaTasa;
	}

	public ConsultaTasaNuevaFilterBean getConsultaTasa() {
		return consultaTasa;
	}

	public void setConsultaTasa(ConsultaTasaNuevaFilterBean consultaTasa) {
		this.consultaTasa = consultaTasa;
	}

	public InputStream getFicheroTasas() {
		return ficheroTasas;
	}

	public void setFicheroTasas(InputStream ficheroTasas) {
		this.ficheroTasas = ficheroTasas;
	}

}


