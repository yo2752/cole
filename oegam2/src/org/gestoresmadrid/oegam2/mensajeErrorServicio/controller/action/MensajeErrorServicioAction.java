package org.gestoresmadrid.oegam2.mensajeErrorServicio.controller.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.model.service.ServicioMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.view.bean.ResultadoMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.views.dto.MensajeErrorServicioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.io.Files;

import general.acciones.ActionBase;
import trafico.utiles.constantes.ConstantesEstadisticas;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class MensajeErrorServicioAction extends ActionBase implements SessionAware {

	private static final long serialVersionUID = -7584597320842891290L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(MensajeErrorServicioAction.class);
	public static final String LISTADO_MENSAJE_ERROR_SERVICIO = "listadoMensajeErrorServicio";
	public static final String PASSWORD_CAMPO = "password";
	public static final boolean PASSWORD_INCORRECTO = false;
	public static final boolean PASSWORD_CORRECTO = true;

	private MensajeErrorServicioDto mensajeErrorServicioDto;
	private List<MensajeErrorServicioDto> lista;
	private boolean passValidado;
	private String password;
	private String fileName;
	private InputStream inputStream;
	private Fecha fecha;

	@Autowired
	private ServicioMensajeErrorServicio servicioMensajeErrorServicio;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public String comprobarPassword() {
		String passwordPropiedades = gestorPropiedades.valorPropertie(PASSWORD_CAMPO);

		if (passwordPropiedades != null && passwordPropiedades.equals(getPassword())) {
			setPassValidado(PASSWORD_CORRECTO);
		} else {
			setPassValidado(PASSWORD_INCORRECTO);
			addActionError(ConstantesEstadisticas.PASSWORD_INCORRECTO);
		}
		return LISTADO_MENSAJE_ERROR_SERVICIO;
	}

	public String inicio(){
		setPassValidado(false);
		return LISTADO_MENSAJE_ERROR_SERVICIO;
	}

	public String obtenerExcel(){
		String pagina = LISTADO_MENSAJE_ERROR_SERVICIO;
		try {
			ResultadoMensajeErrorServicio resultado = servicioMensajeErrorServicio.obtenerExcel(fecha);
			if (!resultado.isError()) {
//				XSSFWorkbook workbook = resultado.getFichero();
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				workbook.write(baos);
				//byte[] adjunto = {};
				//baos.write(adjunto);
				inputStream = new ByteArrayInputStream(Files.toByteArray(resultado.getFile()));
				fileName = resultado.getNombreFichero();
				pagina = "obtenerExcel";
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el fichero, error: ", e);
			addActionError("Ha sucedido un error a la hora de obtener el fichero.");
		}
		return pagina;
	}

	public String generarExcel(){
		String pagina = LISTADO_MENSAJE_ERROR_SERVICIO;
		try {
			List<MensajeErrorServicioDto> lista = servicioMensajeErrorServicio.getListaMensajeErrorServicio(null);
			if (lista.isEmpty()) {
				addActionError("No se ha recuperado ningún mensaje");
			} else {
				ResultadoMensajeErrorServicio resultado = servicioMensajeErrorServicio.generarExcel(lista);
				if(!resultado.isError()){
					XSSFWorkbook workbook = resultado.getFichero();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					workbook.write(baos);
					//byte[] adjunto = {};
					//baos.write(adjunto);
					inputStream = new ByteArrayInputStream(baos.toByteArray());
					fileName = "MensajeErrorServicio.xlsx";
					pagina = "generarExcel";
				} else {
					addActionError(resultado.getMensaje());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el excel, error: ", e);
			addActionError("Ha sucedido un error a la hora de generar el excel.");
		}
		return pagina;
	}

	/**
	 * @return the mensajeErrorServicioDto
	 */
	public MensajeErrorServicioDto getMensajeErrorServicioDto() {
		return mensajeErrorServicioDto;
	}

	/**
	 * @param mensajeErrorServicioDto the mensajeErrorServicioDto to set
	 */
	public void setMensajeErrorServicioDto(MensajeErrorServicioDto mensajeErrorServicioDto) {
		this.mensajeErrorServicioDto = mensajeErrorServicioDto;
	}

	/**
	 * @return the lista
	 */
	public List<MensajeErrorServicioDto> getLista() {
		return lista;
	}

	/**
	 * @param lista the lista to set
	 */
	public void setLista(List<MensajeErrorServicioDto> lista) {
		this.lista = lista;
	}

	/**
	 * @return the passValidado
	 */
	public boolean isPassValidado() {
		return passValidado;
	}

	/**
	 * @param passValidado the passValidado to set
	 */
	public void setPassValidado(boolean passValidado) {
		this.passValidado = passValidado;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the fecha
	 */
	public Fecha getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

}