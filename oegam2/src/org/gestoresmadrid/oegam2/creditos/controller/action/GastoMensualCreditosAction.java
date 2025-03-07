package org.gestoresmadrid.oegam2.creditos.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoVO;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamCreditos.service.ServicioGastoMensualCreditos;
import org.gestoresmadrid.oegamCreditos.view.bean.GastoMensualCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.bean.ResultCreditosBean;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.estructuras.FechaFraccionada;
import utilidades.ficheros.BorrarFicherosThread;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GastoMensualCreditosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -8321716375177160124L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(GastoMensualCreditosAction.class);

	@Resource
	private ModelPagination modeloCreditoFacturadoPaginated;

	@Autowired
	private ServicioGastoMensualCreditos servicioCreditosFacturados;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	// Fichero de resultados a descargar
	private InputStream ficheroResultado;

	// Parámetros de las peticiones del displayTag
	private static final String COLUMDEFECT = "idCreditoFacturado";

	private GastoMensualCreditosBean gastoMensualCreditosBean;
	private CreditoFacturadoVO creditoFacturado;
	private Long idCreditoFacturado;

	// Flujo de bytes del fichero a imprimir en PDF del action
	private InputStream inputStream;
	// Nombre del fichero a imprimir
	private String fileName;
	// Tamaño del fichero a imprimir
	private String fileSize;

	private static final String[] fetchList = { "tipoTramite", "tipoTramite.tipoCredito" };

	// lista con los anios
	private List<Integer> anios = null;

	public String consultaTramites() {
		if (idCreditoFacturado != null) {
			creditoFacturado = servicioCreditosFacturados.getCreditoFacturadoVO(idCreditoFacturado, "creditoFacturadoTramites");
		}
		return "detalleTramites";
	}

	public List<Integer> getAnios() {
		if (anios == null) {
			anios = new ArrayList<Integer>();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			while (year >= 2014) {
				anios.add(Integer.valueOf(year));
				year--;
			}
		}
		return anios;
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (gastoMensualCreditosBean == null) {
			gastoMensualCreditosBean = new GastoMensualCreditosBean();
		}

		if (gastoMensualCreditosBean.getFechaCreditos() != null) {

			Calendar calIni = Calendar.getInstance();
			calIni.setTime(gastoMensualCreditosBean.getFechaCreditos().getFechaMinInicio());
			gastoMensualCreditosBean.getFechaCreditos().setDiaInicio(String.valueOf(calIni.getActualMinimum(Calendar.DATE)));

			Calendar calFin = Calendar.getInstance();
			calFin.setTime(gastoMensualCreditosBean.getFechaCreditos().getFechaMaxFin());
			gastoMensualCreditosBean.getFechaCreditos().setDiaFin(String.valueOf(calFin.getActualMaximum(Calendar.DATE)));

		}

		if (!utilesColegiado.tienePermisoAdmin()) {
			gastoMensualCreditosBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}

	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloCreditoFacturadoPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return gastoMensualCreditosBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.gastoMensualCreditosBean = (GastoMensualCreditosBean) object;
	}

	public GastoMensualCreditosBean getCreditoFacturadoBean() {
		return gastoMensualCreditosBean;
	}

	public void setCreditoFacturadoBean(GastoMensualCreditosBean creditoFacturadoBean) {
		this.gastoMensualCreditosBean = creditoFacturadoBean;
	}

	public CreditoFacturadoVO getCreditoFacturado() {
		return creditoFacturado;
	}

	public void setCreditoFacturado(CreditoFacturadoVO creditoFacturado) {
		this.creditoFacturado = creditoFacturado;
	}

	public Long getIdCreditoFacturado() {
		return idCreditoFacturado;
	}

	public void setIdCreditoFacturado(Long idCreditoFacturado) {
		this.idCreditoFacturado = idCreditoFacturado;
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

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	protected void cargarFiltroInicial() {
		Calendar cal = Calendar.getInstance();

		gastoMensualCreditosBean = new GastoMensualCreditosBean();
		gastoMensualCreditosBean.setFechaCreditos(new FechaFraccionada());
		gastoMensualCreditosBean.getFechaCreditos().setAnioInicio(Integer.toString(cal.get(Calendar.YEAR)));
		gastoMensualCreditosBean.getFechaCreditos().setMesInicio(Integer.toString(cal.get(Calendar.MONTH) + 1));
		gastoMensualCreditosBean.getFechaCreditos().setAnioFin(Integer.toString(cal.get(Calendar.YEAR)));
		gastoMensualCreditosBean.getFechaCreditos().setMesFin(Integer.toString(cal.get(Calendar.MONTH) + 1));

	}

	@SuppressWarnings("unchecked")
	public String exportarResultados() {

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return SUCCESS;
		}

		ResultCreditosBean result = servicioCreditosFacturados.listarTablaCompleta(gastoMensualCreditosBean);

		if (!result.getError()) {

			List<String> lineasExport;

			lineasExport = (List<String>) result.getAttachment("contenidoFichero");

			File file = null;
			if (lineasExport != null && !lineasExport.isEmpty()) {
				try {
					String idSession = ServletActionContext.getRequest().getSession().getId();

					FicheroBean fichero = new FicheroBean();
					fichero.setTipoDocumento(ConstantesGestorFicheros.EXPORTAR);
					fichero.setSubTipo(ConstantesGestorFicheros.RESUMEN_CREDITOS);
					fichero.setExtension(ConstantesGestorFicheros.EXTENSION_CSV);
					fichero.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_EXPORTAR + idSession);
					fichero.setFecha(utilesFecha.getFechaActual());
					fichero.setSobreescribir(true);
					file = gestorDocumentos.guardaFicheroEnviandoStrings(fichero, lineasExport);

					// y a los 5 minutos, lo borraremos
					BorrarFicherosThread hiloBorrar = new BorrarFicherosThread(file.getAbsolutePath());
					hiloBorrar.start();
					log.debug("Se lanza el hilo para borrar el fichero creado, pasados 5 minutos");
				} catch (Exception e) {
					log.error(e);
					addActionError("Error al crear el fichero");
				}

				try {
					setFicheroResultado(new FileInputStream(file.getAbsoluteFile()));
				} catch (FileNotFoundException e) {
					log.error("Resumen de Créditos Mes ha lanzado la siguiente excepción: ", e);
					addActionMessage("Fichero no encontrado");

					return SUCCESS;
				}
			} else { // La lista está vacía
				addActionError("No hay resultados para la exportación");
				return SUCCESS;
			}

		} else { // Hay errores en el ResultBean devuelto.
			for (String mensaje : result.getListaMensajes()) {
				addActionError(mensaje);
			}
			return SUCCESS;
		}

		return "ficheroDownload";
	}

	public InputStream getFicheroResultado() {
		return ficheroResultado;
	}

	public void setFicheroResultado(InputStream ficheroResultado) {
		this.ficheroResultado = ficheroResultado;
	}

	public ModelPagination getModeloCreditoFacturadoPaginated() {
		return modeloCreditoFacturadoPaginated;
	}

	public void setModeloCreditoFacturadoPaginated(ModelPagination modeloCreditoFacturadoPaginated) {
		this.modeloCreditoFacturadoPaginated = modeloCreditoFacturadoPaginated;
	}

	public GastoMensualCreditosBean getGastoMensualCreditosBean() {
		return gastoMensualCreditosBean;
	}

	public void setGastoMensualCreditosBean(GastoMensualCreditosBean gastoMensualCreditosBean) {
		this.gastoMensualCreditosBean = gastoMensualCreditosBean;
	}

	public void setAnios(List<Integer> anios) {
		this.anios = anios;
	}
}
