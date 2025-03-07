package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.service.ServicioFacturacionDistintivo;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.bean.FacturacionDstvFilterBean;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.bean.ResultadoFactDstvBean;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.dto.FacturacionDistintivoDto;
import org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.dto.FacturacionDstvIncDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaFacturacionDstvAction extends AbstractPaginatedListAction {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaFacturacionDstvAction.class);

	private static final long serialVersionUID = -3562213895359295918L;

	private static final String[] fetchList = { "contrato", "tramiteTraficoMatr", "vehiculo", "duplicadoDistintivo",
			"docDistintivo", "listadoIncidencias"};

	private static final String POP_UP_NOTIFICAR = "popUpNotificarIncidencia";
	private static final String DESCARGAR_EXCEL = "descargarExcelFactDstv";
	private static final String INTERVINIENTE_TITULAR = "004";
	private String codSeleccionados;
	private Boolean esDescargable;
	private Date fechaGen;
	private String nombreFichero;
	private InputStream inputStream; 	    // Flujo de bytes del fichero a imprimir en PDF del action
	private String      fileName;			// Nombre del fichero a imprimir
	private FacturacionDistintivoDto facturacionDistintivoDto;
	
	private FacturacionDstvIncDto facturacionDstvInc;

	private FacturacionDstvFilterBean facturacionDstv;

	@Resource
	ModelPagination modeloFacturacionDstvPaginated;

	@Autowired
	ServicioFacturacionDistintivo servicioFacturacionDistintivo;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String guardar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			try {
				ResultadoFactDstvBean resultado = servicioFacturacionDistintivo.guardarIncidenciaDstv(codSeleccionados,facturacionDstvInc, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					addActionMessage("La incidencia de distintivos se ha guardado correctamente.");
				}
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de guardar la incidencia, error: ",e);
				addActionError("Ha sucedido un error a la hora de guardar la incidencia.");
			}
		} else {
			addActionError("Debe seleccionar un listado para poder guardar la incidencia de facturación.");
		}
		return actualizarPaginatedList();
	}
	
	public String generarExcel(){
		try {
			ResultadoFactDstvBean resultado = servicioFacturacionDistintivo.generarExcel(facturacionDstv);
			if(!resultado.getError()){
				nombreFichero = resultado.getNombreFichero();
				fechaGen = resultado.getFechaGenExcel();
				esDescargable = resultado.getEsDescargable();
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el excel, error: ",e);
			addActionError("Ha sucedido un error a la hora de generar el excel.");
		}
		return actualizarPaginatedList();
	}
	
	public String generarExcelDetallado(){
		try {
			ResultadoFactDstvBean resultado = servicioFacturacionDistintivo.generarExcelDetallado(facturacionDstv);
			if(!resultado.getError()){
				nombreFichero = resultado.getNombreFichero();
				fechaGen = resultado.getFechaGenExcel();
				esDescargable = resultado.getEsDescargable();
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el excel, error: ",e);
			addActionError("Ha sucedido un error a la hora de generar el excel.");
		}
		return actualizarPaginatedList();
	}
	
	public String descargar(){
		try {
			ResultadoFactDstvBean resultado = servicioFacturacionDistintivo.descargarExcel(nombreFichero, fechaGen);
			if(!resultado.getError()){
				try{
					inputStream = new FileInputStream(resultado.getFichero());
					fileName = nombreFichero + ".xls";
					return DESCARGAR_EXCEL;
				}catch(FileNotFoundException e) {
					log.error("No existe el fichero a descargar,error:", e);
					addActionError("No existe el fichero a descargar");
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descargar el excel, error: ",e);
			addActionError("Ha sucedido un error a la hora de descargar el excel.");
		}
		return actualizarPaginatedList();
	}
	

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloFacturacionDstvPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			facturacionDstv.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		if (facturacionDstv.getMatricula() != null && !facturacionDstv.getMatricula().isEmpty()) {
			facturacionDstv.setMatriculaDup(facturacionDstv.getMatricula());
			facturacionDstv.setMatriculaTramite(facturacionDstv.getMatricula());
		} else {
			facturacionDstv.setMatriculaDup(null);
			facturacionDstv.setMatriculaTramite(null);
		}
		if (StringUtils.isNotBlank(facturacionDstv.getNif())) {
			facturacionDstv.setTipoInterviniente(INTERVINIENTE_TITULAR);
		} else {
			facturacionDstv.setTipoInterviniente(null);
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (facturacionDstv == null) {
			facturacionDstv = new FacturacionDstvFilterBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			facturacionDstv.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		if (facturacionDstv.getFecha() == null) {
			facturacionDstv.setFecha(utilesFecha.getFechaFracionadaActual());
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return facturacionDstv;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		facturacionDstv = (FacturacionDstvFilterBean) object;
	}

	public FacturacionDstvFilterBean getFacturacionDstv() {
		return facturacionDstv;
	}

	public void setFacturacionDstv(FacturacionDstvFilterBean facturacionDstv) {
		this.facturacionDstv = facturacionDstv;
	}

	public ModelPagination getModeloFacturacionDstvPaginated() {
		return modeloFacturacionDstvPaginated;
	}

	public void setModeloFacturacionDstvPaginated(ModelPagination modeloFacturacionDstvPaginated) {
		this.modeloFacturacionDstvPaginated = modeloFacturacionDstvPaginated;
	}

	public ServicioFacturacionDistintivo getServicioFacturacionDistintivo() {
		return servicioFacturacionDistintivo;
	}

	public void setServicioFacturacionDistintivo(ServicioFacturacionDistintivo servicioFacturacionDistintivo) {
		this.servicioFacturacionDistintivo = servicioFacturacionDistintivo;
	}

	public static String[] getFetchlist() {
		return fetchList;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public static String cargarPopUpNotificaIncidencia() {
		return POP_UP_NOTIFICAR;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public FacturacionDistintivoDto getFacturacionDistintivoDto() {
		return facturacionDistintivoDto;
	}

	public void setFacturacionDistintivoDto(FacturacionDistintivoDto facturacionDistintivoDto) {
		this.facturacionDistintivoDto = facturacionDistintivoDto;
	}

	public FacturacionDstvIncDto getFacturacionDstvInc() {
		return facturacionDstvInc;
	}

	public void setFacturacionDstvInc(FacturacionDstvIncDto facturacionDstvInc) {
		this.facturacionDstvInc = facturacionDstvInc;
	}

	public Boolean getEsDescargable() {
		return esDescargable;
	}

	public void setEsDescargable(Boolean esDescargable) {
		this.esDescargable = esDescargable;
	}

	public Date getFechaGen() {
		return fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
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

}
