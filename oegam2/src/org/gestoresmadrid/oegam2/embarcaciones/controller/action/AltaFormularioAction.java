package org.gestoresmadrid.oegam2.embarcaciones.controller.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.oegam2comun.embarcaciones.model.service.ServicioEmbarcaciones;
import org.gestoresmadrid.oegam2comun.embarcaciones.view.dto.EmbarcacionDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaFormularioAction extends ActionBase implements SessionAware {
	
	private static final long serialVersionUID = -1883144374608563310L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaFormularioAction.class);

	private String TipoPermisos;
	private EmbarcacionDto embarcacion;
	private String nif;
	private String nifRepresentante;
	private InputStream inputStream;
	private String numExpediente;
	private String fileName;

	@Autowired
	private ServicioEmbarcaciones servicioEmbarcaciones;

	@Autowired
	UtilesColegiado utilesColegiado;

	private Map <String,Object> session;

	public String inicio() throws Exception {
		if (utilesColegiado.tienePermisoAdmin()) {
			TipoPermisos = UtilesColegiado.PERMISO_ADMINISTRACION;
		} else {
			TipoPermisos = "GESTOR";
		}
		embarcacion = new EmbarcacionDto();
		return SUCCESS;
	}

	public String limpiarCampos(){
		embarcacion = new EmbarcacionDto();
		return SUCCESS;
	}

	public String generarPdf(){
		String pagina = SUCCESS;
		try {
			if (validar()) {
				ResultBean resultado = servicioEmbarcaciones.generarSolicitudNRE06(embarcacion,utilesColegiado.getIdContratoSessionBigDecimal(),numExpediente);
				if (!resultado.getError()) {
					inputStream = new ByteArrayInputStream((byte[])resultado.getAttachment("pdf"));
					numExpediente = (String)resultado.getAttachment("numExpediente");
					fileName = numExpediente + ".pdf";
					pagina = "descargarPdf";
				} else {
					aniadirMensajeError(resultado);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el PDF, error: ", e);
			addActionError("Ha sucedido un error a la hora de generar el PDF.");
		}
		return pagina;
	}

	public String generarTxt(){
		String pagina = SUCCESS;
		try {
			if (validar()) {
				ResultBean resultado = servicioEmbarcaciones.generarTxt(embarcacion,utilesColegiado.getIdContratoSessionBigDecimal(),numExpediente);
				if (!resultado.getError()) {
					inputStream = new ByteArrayInputStream((byte[])resultado.getAttachment("txt"));
					numExpediente = (String)resultado.getAttachment("numExpediente");
					fileName = numExpediente + ".txt";
					pagina = "descargarTxt";
				} else {
					aniadirMensajeError(resultado);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el TXT, error: ", e);
			addActionError("Ha sucedido un error a la hora de generar el TXT.");
		}
		return pagina;
	}

	private boolean validar(){
		if(StringUtils.isEmpty(embarcacion.getMetrosEslora()) || Float.parseFloat(embarcacion.getMetrosEslora())>8){
			addActionError("Los metros de eslora no pueden ser superiores a 8 ni ser 0");
			return false;
		}
		return true;
	}

	public String guardarDatos(){
		return SUCCESS;
	}

	public String buscarPersona(){
		ResultBean result = servicioEmbarcaciones.buscarPersona(nif, utilesColegiado.getNumColegiadoSession());
		embarcacion.setTitular((PersonaDto) result.getAttachment("PERSONA"));
		if(embarcacion.getTitular()==null){
			embarcacion.setTitular(new PersonaDto());
			embarcacion.getTitular().setNif(nif);
		}
		return SUCCESS;
	}

	public String buscarRepresentante() {
		ResultBean result = servicioEmbarcaciones.buscarPersona(nifRepresentante, utilesColegiado.getNumColegiadoSession());
		embarcacion.setRepresentante((PersonaDto) result.getAttachment("PERSONA"));
		if (embarcacion.getRepresentante() == null) {
			embarcacion.setRepresentante(new PersonaDto());
			embarcacion.getRepresentante().setNif(nifRepresentante);
		}
		return SUCCESS;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getTipoPermisos() {
		return TipoPermisos;
	}

	public void setTipoPermisos(String tipoPermisos) {
		TipoPermisos = tipoPermisos;
	}

	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * @param nif the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * @return the nifRepresentante
	 */
	public String getNifRepresentante() {
		return nifRepresentante;
	}

	/**
	 * @param nifRepresentante the nifRepresentante to set
	 */
	public void setNifRepresentante(String nifRepresentante) {
		this.nifRepresentante = nifRepresentante;
	}

	/**
	 * @return the embarcacion
	 */
	public EmbarcacionDto getEmbarcacion() {
		return embarcacion;
	}

	/**
	 * @param embarcacion the embarcacion to set
	 */
	public void setEmbarcacion(EmbarcacionDto embarcacion) {
		this.embarcacion = embarcacion;
	}

	/**
	 * @return the servicioEmbarcaciones
	 */
	public ServicioEmbarcaciones getServicioEmbarcaciones() {
		return servicioEmbarcaciones;
	}

	/**
	 * @param servicioEmbarcaciones the servicioEmbarcaciones to set
	 */
	public void setServicioEmbarcaciones(ServicioEmbarcaciones servicioEmbarcaciones) {
		this.servicioEmbarcaciones = servicioEmbarcaciones;
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
	 * @return the numExpediente
	 */
	public String getNumExpediente() {
		return numExpediente;
	}

	/**
	 * @param numExpediente the numExpediente to set
	 */
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
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

}