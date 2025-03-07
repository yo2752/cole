package org.gestoresmadrid.oegam2.consultaKo.controller.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.consultaKo.model.service.ServicioConsultaKo;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ConsultaKoBean;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ConsultaKoFilterBean;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ResultadoConsultaKoBean;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ResumenConsultaKoBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaKoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 9183882692144455346L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaKoAction.class);

	private static final String POP_UP_ALTA_KO = "popUpAltaKo";

	private static final String DESCARGAR_EXCEL = "descargarExcelKo";
	private static final String POP_UP_ESTADOS = "popUpCambioEstado";

	private Boolean esDescargable;
	private Date fechaGen;
	private String nombreFichero;
	private InputStream inputStream;
	private String fileName;
	private String estadoNuevo;

	private ResumenConsultaKoBean resumen;

	private String codSeleccionados;

	private String jefatura;

	private ConsultaKoFilterBean consultaKo;

	private ConsultaKoBean consultaKoBean;

	@Resource
	ModelPagination modeloConsultaKoPaginated;

	@Autowired
	ServicioConsultaKo servicioConsultaKo;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String generarExcel() {
		try {
			ResultadoConsultaKoBean resultado = servicioConsultaKo.generarExcelDemanda(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				if(resultado.getResumen() != null && resultado.getResumen().getListaErrores() != null && !resultado.getResumen().getListaErrores().isEmpty()){
					addActionError("No se ha generado la solicitud por los siguientes errores:");
					resumen = resultado.getResumen();
				} else {
					addActionMessage("Se ha generado la solicitud correctamente.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora generar el excel, error: ", e);
			addActionError("Ha sucedido un error a la hora de generar el excel.");

		}
		return actualizarPaginatedList();
	}

	public String descargar() {
		try {
			ResultadoConsultaKoBean resultado = servicioConsultaKo.descargarExcelKO(codSeleccionados);
			if (!resultado.getError()) {
				try {
					inputStream = new FileInputStream(resultado.getFichero());
					fileName = resultado.getNombreFichero();
					if (resultado.getEsZip() != null) {
						servicioConsultaKo.borrarZipKO(resultado.getFichero());
					}
					return DESCARGAR_EXCEL;
				} catch (FileNotFoundException e) {
					log.error("No existe el fichero a descargar,error:", e);
					addActionError("No existe el fichero a descargar");
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descargar el excel, error: ", e);
			addActionError("Ha sucedido un error a la hora de descargar el excel.");
		}
		return actualizarPaginatedList();
	}

	public String validar() {
		try {
			ResultadoConsultaKoBean resultado = servicioConsultaKo.validar(codSeleccionados,
					utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdJefaturaSesion());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar, error: ", e);
			addActionError("Ha sucedido un error a la hora de validar.");
		}
		return actualizarPaginatedList();
	}
	
	public String cambiarEstado(){
		try {
			ResultadoConsultaKoBean resultado = servicioConsultaKo.cambiarEstado(codSeleccionados, estadoNuevo, utilesColegiado.getIdUsuarioSession());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumen = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar, error: ", e);
			addActionError("Ha sucedido un error a la hora de validar.");
		}
		return actualizarPaginatedList();
	}
	
	public String cargarPopUpCambioEstado(){
		return POP_UP_ESTADOS;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaKoPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {

	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaKo == null) {
			consultaKo = new ConsultaKoFilterBean();
		}

	}

	@Override
	protected Object getBeanCriterios() {
		return consultaKo;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaKo = (ConsultaKoFilterBean) object;
	}

	public ModelPagination getModeloConsultaKoPaginated() {
		return modeloConsultaKoPaginated;
	}

	public void setModeloConsultaKoPaginated(ModelPagination modeloConsultaKoPaginated) {
		this.modeloConsultaKoPaginated = modeloConsultaKoPaginated;
	}

	public ConsultaKoFilterBean getConsultaKo() {
		return consultaKo;
	}

	public void setConsultaKo(ConsultaKoFilterBean consultaKo) {
		this.consultaKo = consultaKo;
	}

	public ServicioConsultaKo getServicioConsultaKo() {
		return servicioConsultaKo;
	}

	public void setServicioConsultaKo(ServicioConsultaKo servicioConsultaKo) {
		this.servicioConsultaKo = servicioConsultaKo;
	}

	public static String cargarPopUpAltaKo() {
		return POP_UP_ALTA_KO;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ConsultaKoBean getConsultaKoBean() {
		return consultaKoBean;
	}

	public void setConsultaKoBean(ConsultaKoBean consultaKoBean) {
		this.consultaKoBean = consultaKoBean;
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

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public static String getPopUpAltaKo() {
		return POP_UP_ALTA_KO;
	}

	public static String getDescargarExcel() {
		return DESCARGAR_EXCEL;
	}

	public ResumenConsultaKoBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenConsultaKoBean resumen) {
		this.resumen = resumen;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}
}
