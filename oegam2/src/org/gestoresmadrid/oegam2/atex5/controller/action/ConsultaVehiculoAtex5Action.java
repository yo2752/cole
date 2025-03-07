package org.gestoresmadrid.oegam2.atex5.controller.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioConsultaVehiculoAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ConsultaVehiculoAtex5FilterBean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResumenAtex5Bean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaVehiculoAtex5Action extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 6760708814234199187L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaVehiculoAtex5Action.class);

	private static final String[] fetchList = { "contrato", "provincia" };

	private static final String POP_UP_ESTADOS = "popUpCambioEstado";
	private static final String POP_UP_TASA = "popUpAsignarTasa";
	private static final String DESCARGAR = "descargar";

	private String codSeleccionados;
	private String tasaSeleccionada;
	private String estadoNuevo;
	private ResumenAtex5Bean resumen;

	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	ConsultaVehiculoAtex5FilterBean consultaVehiculo;

	@Resource
	private ModelPagination modeloConsultaVehiculoAtex5PaginatedImpl;

	@Autowired
	private ServicioConsultaVehiculoAtex5 servicioConsultaVehiculoAtex5;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String cambiarEstado() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultadoAtex5Bean resultado = servicioConsultaVehiculoAtex5.cambiarEstado(codSeleccionados, estadoNuevo,
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
			ResultadoAtex5Bean resultado = servicioConsultaVehiculoAtex5.consultar(codSeleccionados,
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
			ResultadoAtex5Bean resultado = servicioConsultaVehiculoAtex5.eliminar(codSeleccionados,
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
		ResultadoAtex5Bean resultado = servicioConsultaVehiculoAtex5.imprimirPdf(codSeleccionados);
		if (!resultado.getError()) {
			try {
				inputStream = new FileInputStream(resultado.getFicheroDescarga());
				fileName = resultado.getNombreFichero();
				if (resultado.getEsZip()) {
					servicioConsultaVehiculoAtex5.borrarFichero(resultado.getFicheroDescarga());
				}
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

	public String asignarTasa() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultadoAtex5Bean resultado = servicioConsultaVehiculoAtex5.asignarTasa(codSeleccionados, tasaSeleccionada,
					utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumen(resultado);
			}
		} else {
			addActionError("Debe seleccionar alguna consulta para poder asignar la tasa.");
		}
		return actualizarPaginatedList();
	}

	public String desasignarTasa() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			ResultadoAtex5Bean resultado = servicioConsultaVehiculoAtex5.desasignarTasa(codSeleccionados,
					consultaVehiculo.getCodigoTasa(), utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				rellenarResumen(resultado);
			}
		} else {
			addActionError("Debe seleccionar alguna consulta para poder desasignar la tasa.");
		}

		return actualizarPaginatedList();
	}

	private void rellenarResumen(ResultadoAtex5Bean resultado) {
		resumen = new ResumenAtex5Bean();
		resumen.setNumError(resultado.getNumError());
		resumen.setListaErrores(resultado.getListaErrores());
		resumen.setNumOk(resultado.getNumOk());
		resumen.setListaOk(resultado.getListaOK());
		resumen.setEsConsultaVehiculo(Boolean.TRUE);
	}

	public String cargarPopUpCambioEstado() {
		return POP_UP_ESTADOS;
	}

	public String cargarPopUpAsignarTasa() {
		return POP_UP_TASA;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaVehiculoAtex5PaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaVehiculo.getMatricula() != null && !consultaVehiculo.getMatricula().isEmpty()) {
			consultaVehiculo.setMatricula(consultaVehiculo.getMatricula().toUpperCase());
		}
		if (consultaVehiculo.getBastidor() != null && !consultaVehiculo.getBastidor().isEmpty()) {
			consultaVehiculo.setBastidor(consultaVehiculo.getBastidor().toUpperCase());
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaVehiculo.setIdContrato(utilesColegiado.getIdContratoSession());
			consultaVehiculo.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaVehiculo == null) {
			consultaVehiculo = new ConsultaVehiculoAtex5FilterBean();
		}
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaVehiculo.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		consultaVehiculo.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaVehiculo;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaVehiculo = (ConsultaVehiculoAtex5FilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorAtex5";
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

	public ConsultaVehiculoAtex5FilterBean getConsultaVehiculo() {
		return consultaVehiculo;
	}

	public void setConsultaVehiculo(ConsultaVehiculoAtex5FilterBean consultaVehiculo) {
		this.consultaVehiculo = consultaVehiculo;
	}

	public String getTasaSeleccionada() {
		return tasaSeleccionada;
	}

	public void setTasaSeleccionada(String tasaSeleccionada) {
		this.tasaSeleccionada = tasaSeleccionada;
	}
}