package org.gestoresmadrid.oegam2.creditos.controller.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamCreditos.service.ServicioResumenCargaCreditos;
import org.gestoresmadrid.oegamCreditos.view.bean.CreditosDisponiblesBean;
import org.gestoresmadrid.oegamCreditos.view.bean.ResultCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.bean.ResumenCargaCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.dto.HistoricoCreditoDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.estructuras.FechaFraccionada;
import utilidades.ficheros.BorrarFicherosThread;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ResumenCargaCreditosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -5209257698986054441L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ResumenCargaCreditosAction.class);

	private ResumenCargaCreditosBean resumenCargaCreditosBean;

	private BigDecimal cantidadSumadaTotal;

	private BigDecimal cantidadRestadaTotal;

	// Lista con los años
	List<Integer> anios = null;

	public String RESUMEN_CREDITOS_MES = "resumenCreditosMes";
	public String CREDITOS_DISPONIBLES = "creditosDisponibles";

	public String idContratoCreditosDisponibles;
	public List<CreditosDisponiblesBean> listaCreditosDisponiblesBean = new ArrayList<>();
	public List<HistoricoCreditoDto> listaCreditosAcumulados = new ArrayList<>();

	// Fichero de resultados a descargar
	private InputStream ficheroResultado;
	private String fileName;

	@Resource
	private ModelPagination modeloResumenCargaCreditosPaginated;

	@Autowired
	private ServicioResumenCargaCreditos servicioResumenCargaCreditos;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Override
	public String inicio() {
		if (resumenCargaCreditosBean == null) {
			resumenCargaCreditosBean = new ResumenCargaCreditosBean();
		}
		resumenCargaCreditosBean.setFechaAlta(utilesFecha.getFechaFracionadaActual());
		return buscar();
	}

	public String inicioMes() throws Throwable {
		if (resumenCargaCreditosBean == null) {
			resumenCargaCreditosBean = new ResumenCargaCreditosBean();
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);

		resumenCargaCreditosBean.setFechaAlta(new FechaFraccionada());
		resumenCargaCreditosBean.getFechaAlta().setAnioInicio(Integer.toString(cal.get(Calendar.YEAR)));
		resumenCargaCreditosBean.getFechaAlta().setMesInicio(Integer.toString(cal.get(Calendar.MONTH) + 1));
		return RESUMEN_CREDITOS_MES;
	}

	private void obtenerCantidadesTotales() {
		Date fechaDesde = null;
		Date fechaHasta = null;
		if (resumenCargaCreditosBean != null) {
			if (resumenCargaCreditosBean.getFechaAlta() != null) {
				fechaDesde = resumenCargaCreditosBean.getFechaAlta().getFechaInicio();
				fechaHasta = resumenCargaCreditosBean.getFechaAlta().getFechaFin();
			}
			try {
				ResultCreditosBean result = servicioResumenCargaCreditos.cantidadesTotalesResumen(
						resumenCargaCreditosBean.getIdContrato(), resumenCargaCreditosBean.getTipoCredito(), fechaDesde,
						fechaHasta);
				if (result != null && !result.getError()) {
					cantidadSumadaTotal = (BigDecimal) result.getAttachment(ServicioResumenCargaCreditos.CANTIDAD_SUMADA_TOTAL);
					cantidadRestadaTotal = (BigDecimal) result.getAttachment(ServicioResumenCargaCreditos.CANTIDAD_RESTADA_TOTAL);
				}
			} catch (Exception e) {
				log.error("Error al obtener las cantidades totales de los créditos", e);
			}
		}
	}

	public String buscarCreditosDisponibles() {
		Long numContrato;

		numContrato = StringUtils.isNotBlank(getIdContratoCreditosDisponibles())
				? Long.parseLong(getIdContratoCreditosDisponibles())
				: utilesColegiado.getIdContratoSession();

		listaCreditosDisponiblesBean = servicioResumenCargaCreditos.creditosDisponiblesPorColegiado(numContrato);

		listaCreditosAcumulados = servicioResumenCargaCreditos.consultaCreditosAcumuladosMesPorColegiado(numContrato);

		return CREDITOS_DISPONIBLES;
	}

	public String generarGBJA() {
		if (utilesColegiado.tienePermisoAdmin()) {
			String xml = servicioResumenCargaCreditos.obtenerCreditosGBJA(resumenCargaCreditosBean.getFechaAlta());

			ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
			setFicheroResultado(stream);
			setFileName("ListadoCreditos.xml");

			return "descargarFichero";
		}

		obtenerCantidadesTotales();
		return super.buscar();
	}

	@SuppressWarnings("unchecked")
	public String exportarResultados() throws Throwable {
		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return SUCCESS;
		}

		Date fechaDesde = null;
		Date fechaHasta = null;

		if (resumenCargaCreditosBean.getFechaAlta() != null) {
			fechaDesde = resumenCargaCreditosBean.getFechaAlta().getFechaInicio();
			fechaHasta = resumenCargaCreditosBean.getFechaAlta().getFechaFin();
		}

		ResultCreditosBean result = servicioResumenCargaCreditos.exportarTablaCompleta(resumenCargaCreditosBean.getIdContrato(), resumenCargaCreditosBean.getTipoCredito(), fechaDesde, fechaHasta);

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
				addActionError("No hay resultados para la exportacion");
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

	@SuppressWarnings("unchecked")
	public String exportarResultadosMes() throws Throwable {

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return SUCCESS;
		}

		Date fechaDesde = null;
		Date fechaHasta = null;

		if (resumenCargaCreditosBean.getFechaAlta() != null) {
			fechaDesde = resumenCargaCreditosBean.getFechaAlta().getFechaInicio();

			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaDesde);
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DATE), 23, 59, 59);
			fechaHasta = cal.getTime();
		}

		ResultCreditosBean result = servicioResumenCargaCreditos.exportarTablaCompletaMes(resumenCargaCreditosBean.getIdContrato(), resumenCargaCreditosBean.getTipoCredito(), fechaDesde, fechaHasta,
				resumenCargaCreditosBean.getPrecioCredito());

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
				addActionError("No hay resultados para la exportacion");
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

	@Override
	public String buscar() {
		obtenerCantidadesTotales();
		return super.buscar();
	}

	@Override
	public String navegar() {
		super.navegar();
		obtenerCantidadesTotales();
		return SUCCESS;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (resumenCargaCreditosBean == null) {
			resumenCargaCreditosBean = new ResumenCargaCreditosBean();
		}

		if (!utilesColegiado.tienePermisoAdmin()) {
			resumenCargaCreditosBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloResumenCargaCreditosPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return resumenCargaCreditosBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.resumenCargaCreditosBean = (ResumenCargaCreditosBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {}

	public ResumenCargaCreditosBean getResumenCargaCreditosBean() {
		return resumenCargaCreditosBean;
	}

	public void setResumenCargaCreditosBean(ResumenCargaCreditosBean resumenCargaCreditosBean) {
		this.resumenCargaCreditosBean = resumenCargaCreditosBean;
	}

	public BigDecimal getCantidadSumadaTotal() {
		return cantidadSumadaTotal;
	}

	public void setCantidadSumadaTotal(BigDecimal cantidadSumadaTotal) {
		this.cantidadSumadaTotal = cantidadSumadaTotal;
	}

	public BigDecimal getCantidadRestadaTotal() {
		return cantidadRestadaTotal;
	}

	public void setCantidadRestadaTotal(BigDecimal cantidadRestadaTotal) {
		this.cantidadRestadaTotal = cantidadRestadaTotal;
	}

	public InputStream getFicheroResultado() {
		return ficheroResultado;
	}

	public void setFicheroResultado(InputStream ficheroResultado) {
		this.ficheroResultado = ficheroResultado;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setAnios(List<Integer> anios) {
		this.anios = anios;
	}

	public List<Integer> getAnios() {
		if (anios == null) {
			anios = new ArrayList<Integer>();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			while (year >= 2012) {
				anios.add(Integer.valueOf(year));
				year--;
			}
		}
		return anios;
	}

	public String getIdContratoCreditosDisponibles() {
		return idContratoCreditosDisponibles;
	}

	public void setIdContratoCreditosDisponibles(String idContratoCreditosDisponibles) {
		this.idContratoCreditosDisponibles = idContratoCreditosDisponibles;
	}

	public List<CreditosDisponiblesBean> getListaCreditosDisponiblesBean() {
		return listaCreditosDisponiblesBean;
	}

	public void setListaCreditosDisponiblesBean(List<CreditosDisponiblesBean> listaCreditosDisponiblesBean) {
		this.listaCreditosDisponiblesBean = listaCreditosDisponiblesBean;
	}

	public List<HistoricoCreditoDto> getListaCreditosAcumulados() {
		return listaCreditosAcumulados;
	}

	public void setListaCreditosAcumulados(List<HistoricoCreditoDto> listaCreditosAcumulados) {
		this.listaCreditosAcumulados = listaCreditosAcumulados;
	}

}