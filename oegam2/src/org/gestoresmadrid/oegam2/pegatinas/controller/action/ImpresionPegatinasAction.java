package org.gestoresmadrid.oegam2.pegatinas.controller.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasEvolucionVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasNotificaVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasVO;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioPegatinasTransactional;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.ConstantesPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.EstadoPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.TipoPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.view.beans.ImpresionPegatinasBean;
import org.gestoresmadrid.oegam2comun.pegatinas.view.beans.PegatinasNotificaBean;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.model.service.ServicioEstadisticasJPT;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ImpresionPegatinasAction extends AbstractPaginatedListAction implements SessionAware {

	private static final long serialVersionUID = 7068221511089304431L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImpresionPegatinasAction.class);

	private static final String KO = "KO";
	private static final String IMPRESA = "Impresa";
	private static final String IMPRIMIR_PEGATINAS = "imprimirPegatinas";
	private static final String EVOLUCION = "evolucion";
	private static final String PETICION_PDF = "peticionPDF";
	private static final String IDENTIFICADORES = "identificadores";
	private static final String NOTIFICA_PEGATINA = "notificaPegatina";

	@Resource
	private ModelPagination modeloImpresionPegatinasPaginatedImpl;

	@Autowired
	private ServicioPegatinasTransactional servicioPegatinasTransactional;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioEstadisticasJPT servicioEstadisticasJPT;

	@Autowired
	UtilesColegiado utilesColegiado;

	private ImpresionPegatinasBean impresionPegatinasBean;

	private PegatinasNotificaBean pegatinasNotificaBean;

	private String listaPrueba;

	private String listaIdentificadores;

	private String jefaturaJpt;

	private InputStream inputStream; // Flujo de bytes del fichero a imprimir
	private String fileName; // Nombre del fichero a imprimir
	private String fileSize; // Tamaño del fichero a imprimir

	private List<PegatinasEvolucionVO> listaRespuestas;

	private List<PegatinasVO> listaNotificacion;

	private List<PegatinasStockVO> listaStock;

	private String idPegatina;

	private String codigo;

	private String tipoNotificacion;

	private int numeroPegatinas;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloImpresionPegatinasPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	public String buscar() {

		if (impresionPegatinasBean != null) {
			if (jefaturaJpt != null) {
				impresionPegatinasBean.setJefatura(jefaturaJpt);
			}
			if (EstadoPegatinas.TODOS.getValorEnum().equals(impresionPegatinasBean.getEstado())) {
				impresionPegatinasBean.setEstado(null);
			}
			if (TipoPegatinas.TODOS.getNombreEnum().equals(impresionPegatinasBean.getTipo())) {
				impresionPegatinasBean.setTipo(null);

			}
		}
		return super.buscar();
	}

	@Override
	public String inicio() {
		if (!utilesColegiado.getTipoUsuarioAdminJefaturaJpt()) {
			jefaturaJpt = servicioEstadisticasJPT
					.getJefaturaProvincialPorUsuario(utilesColegiado.getIdUsuarioSession());
		}
		if (impresionPegatinasBean == null) {
			impresionPegatinasBean = new ImpresionPegatinasBean();
			impresionPegatinasBean.setJefatura(jefaturaJpt);
		}
		return super.buscar();
	}

	@Override
	protected void cargaRestricciones() {
	}

	@Override
	protected void cargarFiltroInicial() {
		if (impresionPegatinasBean == null) {
			impresionPegatinasBean = new ImpresionPegatinasBean();
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return impresionPegatinasBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.impresionPegatinasBean = (ImpresionPegatinasBean) object;

	}

	public String verEvolucion() {
		listaRespuestas = servicioPegatinasTransactional.getPegatinasEvolucionByIdPegatina(Integer.parseInt(codigo));
		int size = listaRespuestas.size();
		if (size == 0) {
			addActionError("No se ha realizado ninguna acción con esta pegatina");
			return buscar();
		}
		return EVOLUCION;
	}

	public String imprimir() {
		impresionPegatinasBean = (ImpresionPegatinasBean) getSession().get(getKeyCriteriosSession());
		List<File> listaFicheros = new ArrayList<>();
		try {
			String[] pegatinas = idPegatina.split("-");
			for (int i = 0; i < pegatinas.length; i++) {
				String[] peg = pegatinas[i].split(",");
				File fichero = recuperarPdf(peg[1].trim());
				if (fichero != null) {
					listaFicheros.add(fichero);
					insertaEvolucion(peg[0].trim());
				}
			}

			if (!listaFicheros.isEmpty()) {
				creaDescargaZip(listaFicheros);
			} else {
				addActionError("Se ha producido un error a la hora de recuperar el pdf.");
				return inicio();
			}
		} catch (Exception e) {
			log.error("Se ha producido un error a la hora de recuperar el pdf: " + e);
			addActionError("Se ha producido un error a la hora de recuperar el pdf.");

		}
		return PETICION_PDF;
	}

	public String identificadores() {
		/*
		 * De la lista de expedientes notificados como erroneos, hay que añadirle los
		 * identificadores de las pegatinas
		 * 
		 */
		String[] tramites = listaPrueba.replaceAll("(\\r|\\n|\\t)", "").split("-");
		String[] peticiones;
		BigDecimal bd = null;
		if (listaNotificacion == null) {
			listaNotificacion = new ArrayList<>();
		}
		for (int i = 0; i < tramites.length; i++) {
			peticiones = tramites[i].split(",");
			bd = new BigDecimal(peticiones[0].trim());
			listaNotificacion.add(servicioPegatinasTransactional.getPegatinaByExpediente(bd));
		}
		return IDENTIFICADORES;
	}

	public String confirmarInvalidos() {

		// una vez introducidos los identificadores de cada peticion, pasamos a
		// notificarlos como invalidos
		// Recorremos los identificadores, y realizamos la notificacion
		String[] identificadores = listaPrueba.split(",");

		for (int i = 0; i < identificadores.length; i++) {

			BigDecimal expediente = listaNotificacion.get(i).getNumExpediente();
			PegatinasVO pegatina = servicioPegatinasTransactional.getPegatinaByExpediente(expediente);
			// Si la peticion esta como impresa, no dejamos imprimirlo mas
			if ((IMPRESA).equals(pegatina.getDescrEstado())) {
				addActionError("No se pueden marcar pegatinas en estado impresa");
				return mostrarVista();
			} else {
				// Resta Stock
				int stockRestante = servicioPegatinasTransactional.restarStockByTipoyJefatura(pegatina.getTipo(),
						pegatina.getJefatura());
				// Recupero del stock el correspondiente a la peticion a tratar
				PegatinasStockVO pegatinasStock = servicioPegatinasTransactional
						.getStockByJefaturayTipoPegatina(pegatina.getTipo(), pegatina.getJefatura());
				// Insertamos en historico
				servicioPegatinasTransactional.insertarHistorico(pegatinasStock.getIdStock(), tipoNotificacion,
						stockRestante, pegatina.getTipo(), pegatina.getMatricula());
				if (KO.equals(tipoNotificacion)) {
					// Grabamos en la tabla de notificaciones, que luego recorrera el proceso para
					// llamar al WS
					insertaPegatinasNotificacionNoValida(identificadores[i], ConstantesPegatinas.IMPRESION_ERRONEA,
							pegatina.getMatricula(), pegatina.getIdPegatina(), pegatina.getJefatura());
				} else {
					insertaPegatinasNotificacionNoValida(identificadores[i], ConstantesPegatinas.ROTURA,
							pegatina.getMatricula(), pegatina.getIdPegatina(), pegatina.getJefatura());
				}
			}
		}
		addActionMessage("Se han marcado como impresión errónea correctamente.");
		return IDENTIFICADORES;

	}

	public String abrirPopupNotifica() {

		if (impresionPegatinasBean != null) {
			if (EstadoPegatinas.TODOS.getValorEnum().equals(impresionPegatinasBean.getEstado())) {
				impresionPegatinasBean.setEstado(null);
			}
			if (TipoPegatinas.TODOS.getNombreEnum().equals(impresionPegatinasBean.getTipo())) {
				impresionPegatinasBean.setTipo(null);
			}
		}
		return NOTIFICA_PEGATINA;
	}

	public String mostrarVista() {

		if (listaStock == null) {
			listaStock = new ArrayList<>();
		}

		listaNotificacion = new ArrayList<>();
		String[] pegatinas = idPegatina.split("-");
		String[] peticiones;

		for (int i = 0; i < pegatinas.length; i++) {

			peticiones = pegatinas[i].split(",");
			PegatinasVO pegatina = servicioPegatinasTransactional
					.getPegatinaByIdPegatina(Integer.parseInt(peticiones[0]));

			if (peticiones[3] != null && ("1").equals(peticiones[3].trim())) {
				addActionError("No se pueden marcar pegatinas en estado Impresa");
				return buscar();
			}

			listaNotificacion.add(servicioPegatinasTransactional.getPegatinaByIdPegatina(pegatina.getIdPegatina()));
			PegatinasStockVO stock = servicioPegatinasTransactional.getStockByJefaturayTipoPegatina(peticiones[2],
					pegatina.getJefatura());
			if (stock == null) {
				addActionError(
						"No posee stock del tipo " + peticiones[2] + " para la jefatura " + pegatina.getJefatura());
				return buscar();
			}
			listaStock.add(stock);
			numeroPegatinas++;
		}

		return IMPRIMIR_PEGATINAS;
	}

	public String notificar() {
		/*
		 * Notificar 1.- Recorremos el listado de expedientes 2.- Notificamos impresion
		 * OK 3.- Actualizamos el historico 4.- Cambiamos el estado de la pegatina a
		 * IMPRESION CORRECTA
		 */

		String[] tramites = listaPrueba.replaceAll("(\\r|\\n|\\t)", "").split("-");

		for (int i = 0; i < tramites.length; i++) {
			BigDecimal expediente = listaNotificacion.get(i).getNumExpediente();
			PegatinasVO pegatina = servicioPegatinasTransactional.getPegatinaByExpediente(expediente);
			if ((IMPRESA).equals(pegatina.getDescrEstado())) {
				addActionError("No se pueden marcar pegatinas en estado impresa");
				return mostrarVista();
			}
			// Resta Stock
			int stockRestante = servicioPegatinasTransactional.restarStockByTipoyJefatura(pegatina.getTipo(),
					pegatina.getJefatura());
			// Recupero del stock el correspondiente a la peticion a tratar
			PegatinasStockVO pegatinasStock = servicioPegatinasTransactional
					.getStockByJefaturayTipoPegatina(pegatina.getTipo(), pegatina.getJefatura());
			// Insertamos en historico
			servicioPegatinasTransactional.insertarHistorico(pegatinasStock.getIdStock(),
					ConstantesPegatinas.IMPRESION_CORRECTA, stockRestante, pegatina.getTipo(), pegatina.getMatricula());
			// Grabamos en la tabla de notificaciones, que luego recorrera el proceso para
			// llamar al WS
			insertaPegatinasNotificacion(pegatina.getIdPegatina().toString(), pegatina.getMatricula(),
					pegatina.getJefatura());
		}

		addActionMessage("Se han notificado como impresas las peticiones solicitadas");

		return mostrarVista();
	}

	private void creaDescargaZip(List<File> listaFicheros) {
		try {
			// Si tenemos sólo un fichero no generamos el zip
			if (listaFicheros.size() == 1) {
				inputStream = new FileInputStream(listaFicheros.get(0));
				fileName = listaFicheros.get(0).getName();
				fileSize = "Unknown";
			} else {
				ByteArrayOutputStream fos = new ByteArrayOutputStream();
				ZipOutputStream zos = new ZipOutputStream(fos);
				for (File file : listaFicheros) {
					addToZipFile(file, zos);
				}
				zos.close();
				fos.close();
				setInputStream(new ByteArrayInputStream(fos.toByteArray()));
				setFileName("Pegatinas.zip");
			}
		} catch (FileNotFoundException e) {
			log.error("No es posible encontrar el fichero: ", e);
		} catch (IOException e) {
			log.error("Existe algún error imprimiendo la notificación: ", e);
		}
	}

	private void addToZipFile(File file, ZipOutputStream zos) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ZipEntry zipEntry = new ZipEntry(file.getName());
			zos.putNextEntry(zipEntry);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}

			zos.closeEntry();
			fis.close();

		} catch (FileNotFoundException e) {
			log.error("No es posible encontrar el fichero: ", e);
		} catch (IOException e) {
			log.error("Existe algún error imprimiendo la notificación: ", e);
		}

	}

	private File recuperarPdf(String matricula) {
		File fichero = null;
		String numExpediente = servicioPegatinasTransactional.getNumExpedienteByMatricula(matricula);
		try {
			fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE,
					ConstantesGestorFicheros.PDF_PEGATINA, Utilidades.transformExpedienteFecha(numExpediente),
					matricula, ConstantesGestorFicheros.EXTENSION_PDF).getFile();
		} catch (OegamExcepcion e) {
			log.error("Existe algún error recuperando el PDF asociado a la pegatina: ", e);
		}
		return fichero;
	}

	private void insertaEvolucion(String idPegatina) {
		String numColegiado = utilesColegiado.getNumColegiadoSession();
		PegatinasEvolucionVO evo = new PegatinasEvolucionVO();
		evo.setIdPegatina(Integer.parseInt(idPegatina));
		evo.setEstado("Descarga PDF");
		evo.setFecha(new Date());
		evo.setNumColegiado(numColegiado);
		servicioPegatinasTransactional.insertarPegatinasEvolucion(evo);
	}

	private void insertaPegatinasNotificacion(String idPegatina, String matricula, String jefatura) {
		String numColegiado = utilesColegiado.getNumColegiadoSession();
		Date fechaHoy = new Date();
		PegatinasNotificaVO notificacion = new PegatinasNotificaVO();
		notificacion.setIdPegatina(Integer.parseInt(idPegatina));
		notificacion.setMatricula(matricula);
		notificacion.setAccion(ConstantesPegatinas.IMPRESION_CORRECTA);
		notificacion.setFecha(fechaHoy);
		notificacion.setJefatura(jefatura);
		servicioPegatinasTransactional.insertarPegatinasNotificacion(notificacion);

		PegatinasEvolucionVO evo = new PegatinasEvolucionVO();
		evo.setIdPegatina(Integer.parseInt(idPegatina));
		evo.setEstado("Marcado Impreso");
		evo.setFecha(fechaHoy);
		evo.setNumColegiado(numColegiado);
		servicioPegatinasTransactional.insertarPegatinasEvolucion(evo);

		servicioPegatinasTransactional.cambiarEstadoPegatina(idPegatina, EstadoPegatinas.IMPRESA_OK.getValorEnum(),
				EstadoPegatinas.IMPRESA_OK.getNombreEnum());
	}

	private void insertaPegatinasNotificacionNoValida(String identificador, String motivo, String matricula,
			int idPegatina, String jefatura) {
		PegatinasNotificaVO notificacion = new PegatinasNotificaVO();
		notificacion.setAccion("NO VALIDO");
		notificacion.setFecha(new Date());
		notificacion.setIdentificador(identificador);
		notificacion.setMotivo(motivo);
		notificacion.setMatricula(matricula);
		notificacion.setIdPegatina(idPegatina);
		notificacion.setJefatura(jefatura);
		servicioPegatinasTransactional.insertarPegatinasNotificacion(notificacion);

		PegatinasEvolucionVO evo = new PegatinasEvolucionVO();
		evo.setIdPegatina(idPegatina);
		evo.setEstado("Impresión errónea");
		evo.setFecha(new Date());
		evo.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		servicioPegatinasTransactional.insertarPegatinasEvolucion(evo);
	}

	public String getListaPrueba() {
		return listaPrueba;
	}

	public void setListaPrueba(String listaPrueba) {
		this.listaPrueba = listaPrueba;
	}

	public String getTipoNotificacion() {
		return tipoNotificacion;
	}

	public void setTipoNotificacion(String tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}

	public int getNumeroPegatinas() {
		return numeroPegatinas;
	}

	public void setNumeroPegatinas(int numeroPegatinas) {
		this.numeroPegatinas = numeroPegatinas;
	}

	public List<PegatinasStockVO> getListaStock() {
		return listaStock;
	}

	public void setListaStock(List<PegatinasStockVO> listaStock) {
		this.listaStock = listaStock;
	}

	public List<PegatinasVO> getListaNotificacion() {
		return listaNotificacion;
	}

	public void setListaNotificacion(List<PegatinasVO> listaNotificacion) {
		this.listaNotificacion = listaNotificacion;
	}

	public String getIdPegatina() {
		return idPegatina;
	}

	public void setIdPegatina(String idPegatina) {
		this.idPegatina = idPegatina;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<PegatinasEvolucionVO> getListaRespuestas() {
		return listaRespuestas;
	}

	public void setListaRespuestas(List<PegatinasEvolucionVO> listaRespuestas) {
		this.listaRespuestas = listaRespuestas;
	}

	public ImpresionPegatinasBean getImpresionPegatinasBean() {
		return impresionPegatinasBean;
	}

	public void setImpresionPegatinasBean(ImpresionPegatinasBean impresionPegatinasBean) {
		this.impresionPegatinasBean = impresionPegatinasBean;
	}

	public PegatinasNotificaBean getPegatinasNotificaBean() {
		return pegatinasNotificaBean;
	}

	public void setPegatinasNotificaBean(PegatinasNotificaBean pegatinasNotificaBean) {
		this.pegatinasNotificaBean = pegatinasNotificaBean;
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

	public String getListaIdentificadores() {
		return listaIdentificadores;
	}

	public void setListaIdentificadores(String listaIdentificadores) {
		this.listaIdentificadores = listaIdentificadores;
	}

	public String getJefaturaJpt() {
		return jefaturaJpt;
	}

	public void setJefaturaJpt(String jefaturaJpt) {
		this.jefaturaJpt = jefaturaJpt;
	}

}