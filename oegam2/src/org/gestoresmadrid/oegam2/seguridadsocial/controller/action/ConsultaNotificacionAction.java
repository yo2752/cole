package org.gestoresmadrid.oegam2.seguridadsocial.controller.action;

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
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.core.notificacion.model.vo.RespuestaSSVO;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.notificacion.model.service.ServicioConsultaNotificacion;
import org.gestoresmadrid.oegam2comun.notificacion.model.service.ServicioConsultaNotificacionTransactional;
import org.gestoresmadrid.oegam2comun.notificacion.view.beans.ConsultaNotificacionBean;
import org.gestoresmadrid.oegam2comun.wscn.utiles.enumerados.TipoEstadoNotificacion;
import org.gestoresmadrid.oegam2comun.wscn.utiles.enumerados.TipoRolNotificacion;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import oegam.constantes.ConstantesPQ;
import trafico.modelo.ModeloAccesos;
import trafico.modelo.ModeloCreditosTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

public class ConsultaNotificacionAction extends AbstractPaginatedListAction implements SessionAware {
	
	private static final long serialVersionUID = 1159646947772238045L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaNotificacionAction.class);
	
	@Resource
	private ModelPagination modeloConsultaNotificacionPaginated;
	
	@Autowired
	private ServicioConsultaNotificacion servicioConsultaNotificacion;
	
	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private ServicioConsultaNotificacionTransactional consultaNotificacionTransactional;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	@Autowired
	private Utiles utiles;
	
	private ConsultaNotificacionBean consultaNotificacionBean;
	
	private List<RespuestaSSVO> listaRespuestas;
	
	private String idNotificacion;
	
	private String codigo;
	private String numColegiado;
	private Boolean aceptaServicio;
	
	private InputStream inputStream; // Flujo de bytes del fichero a imprimir 
	private String fileName; // Nombre del fichero a imprimir
	private String fileSize; // Tamaño del fichero a imprimir
	private UtilesViafirma utilesViafirma;
	
	private static final String SEGSOL_CREDITOS = "segsol.creditos.consulta";
	
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
	protected String getResultadoPorDefecto() {
		if(utiles.esUsuarioOegam3(utilesColegiado.getIdUsuarioSessionBigDecimal().toString())) {
			return "redireccionOegam3";
		}else {
			return SUCCESS;
		}
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaNotificacionPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(consultaNotificacionBean == null){
			consultaNotificacionBean = new ConsultaNotificacionBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaNotificacionBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
		
		if (consultaNotificacionBean != null && consultaNotificacionBean.getDestinatario() != null && !("").equals
					(consultaNotificacionBean.getDestinatario())) {
			consultaNotificacionBean.setDestinatario(consultaNotificacionBean.getDestinatario().toUpperCase());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(consultaNotificacionBean == null){
			consultaNotificacionBean = new ConsultaNotificacionBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaNotificacionBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaNotificacionBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaNotificacionBean = (ConsultaNotificacionBean) object;
	}
	
	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorConsultaNotificacion";
	}

	public ConsultaNotificacionBean getConsultaNotificacionBean() {
		return consultaNotificacionBean;
	}

	public void setConsultaNotificacionBean(
			ConsultaNotificacionBean consultaNotificacionBean) {
		this.consultaNotificacionBean = consultaNotificacionBean;
	}
	
	public String actualizar() {
		consultaNotificacionBean = (ConsultaNotificacionBean) getSession().get(getKeyCriteriosSession());
		String creditos = gestorPropiedades.valorPropertie(SEGSOL_CREDITOS);
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
		if (validarCreditos(idContrato, creditos)){
			String alias = recuperaAlias();
			ResultBean result = null;
			String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">Prueba firma</CONTENT></AFIRMA>";
			String pruebaCaduCert = getUtilesViafirma().firmarPruebaCertificadoCaducidadSS(xmlAFirmar, alias);
			if(pruebaCaduCert != null){
				result = servicioConsultaNotificacion.creaSolicitudActualizar(alias, idContrato, idUsuario);
				if (!result.getError()) {
					addActionMessage("Descargando notificaciones...");
				} else {
					addActionError(result.getMensaje());
				}
			}else{
				addActionError("El certificado del colegiado está caducado o contiene errores.");
			}
		}
		
		return buscar();
	}
	
	public String getIdNotificacion() {
		return idNotificacion;
	}
	
	public void setIdNotificacion(String idNotificacion) {
		this.idNotificacion = idNotificacion;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public List<RespuestaSSVO> getListaRespuestas() {
		return listaRespuestas;
	}

	public void setListaRespuestas(List<RespuestaSSVO> listaRespuestas) {
		this.listaRespuestas = listaRespuestas;
	}

	public Boolean getAceptaServicio() {
		return aceptaServicio;
	}

	public void setAceptaServicio(Boolean aceptaServicio) {
		this.aceptaServicio = aceptaServicio;
	}

	@Override
	public String buscar() {
		// Si en el combo se elige la opción TODOS debemos setear a null los valores del bean para que no aparezcan
		// en la select
		if (consultaNotificacionBean != null) {
			if (TipoRolNotificacion.TODOS.getValorEnum().equals(consultaNotificacionBean.getRol())) {
				consultaNotificacionBean.setRol(null);
			}
			if (TipoEstadoNotificacion.TODOS.getValorEnum().equals(consultaNotificacionBean.getEstado())) {
				consultaNotificacionBean.setEstado(null);
			}
		}
		return super.buscar();
	}
	
	public String aceptar(){
		consultaNotificacionBean = (ConsultaNotificacionBean) getSession().get(getKeyCriteriosSession());
		String alias = recuperaAlias();
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
 		ResultBean result = null;
		//Este es el valor de la variable del formulario con los datos del checkbox
		String indices = getIdNotificacion();
		
		//Recorremos los indices para comprobar que los estados son coherentes 
		String[] notificacion = indices.split("-");
		int size = notificacion.length;
		String[] ids = new String[size];
		String[] colegiados = new String[size];
		boolean estadoNotificacion = true;
		for (int i = 0; i < size; i++) {
			String[] valores = notificacion[i].split(",");
			String id = valores[1];
			String colegiado = valores[3];
			ids[i] = id;
			colegiados[i] = colegiado;
			//Tienen que tener estado 0 (aun no notificada)
			if (!valores[2].equals("0")){
				estadoNotificacion = false;
				break;
			}
		}
		if (!estadoNotificacion){
			addActionError("Para solicitar acuse de aceptación de una notificación su estado ha de ser 'Sin Acuse'");
		}else{
			String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">Prueba firma</CONTENT></AFIRMA>";
			String pruebaCaduCert = getUtilesViafirma().firmarPruebaCertificadoCaducidadSS(xmlAFirmar, alias);
			if(pruebaCaduCert != null){
				result = servicioConsultaNotificacion.creaSolicitudAceptar(indices, alias, idUsuario);
				for (int i = 0; i < ids.length; i++){
					consultaNotificacionTransactional.cambiarEstadoNotificacion(Integer.parseInt(ids[i]), 1, colegiados[i]);
				}
			}else{
				addActionError("El certificado del colegiado está caducado o contiene errores.");
			}
		}
		if (result != null) {
			if (!result.getError()) {
				addActionMessage("Puede consultar el resultado de la consulta en la 'Evolución' de la notificación");
			} else if (result.getMensaje() != null && !result.getMensaje().isEmpty()) {
				addActionError(result.getMensaje());
			}
		}
		
		return buscar();
	}
	
	public String rechazar(){
		consultaNotificacionBean = (ConsultaNotificacionBean) getSession().get(getKeyCriteriosSession());
		String alias = recuperaAlias();
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
		ResultBean result = null;
		//Este es el valor de la variable del formulario con los datos del checkbox
		String indices = getIdNotificacion();
		
		//Recorremos los indices para comprobar que los estados son coherentes 
		String[] notificacion = indices.split("-");
		int size = notificacion.length;
		String[] ids = new String[size];
		String[] colegiados = new String[size];
		boolean estadoNotificacion = true;
		for (int i = 0; i < size; i++) {
			String[] valores = notificacion[i].split(",");
			String id = valores[1];
			String colegiado = valores[3];
			colegiados[i] = colegiado;
			ids[i] = id;
			//Tienen que tener estado 0 (aun no notificada)
			if (!valores[2].equals("0")){
				estadoNotificacion = false;
				break;
			}
		}
		
		if (!estadoNotificacion){
			addActionError("Para solicitar acuse de rechazo de una notificación su estado ha de ser 'Sin Acuse'");
		}else{
			String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">Prueba firma</CONTENT></AFIRMA>";
			String pruebaCaduCert = getUtilesViafirma().firmarPruebaCertificadoCaducidadSS(xmlAFirmar, alias);
			if(pruebaCaduCert != null){
				result = servicioConsultaNotificacion.creaSolicitudRechazar(indices, alias, idUsuario);
				for (int i = 0; i < ids.length; i++){
					consultaNotificacionTransactional.cambiarEstadoNotificacion(Integer.parseInt(ids[i]), 1, colegiados[i]);
				}
			}else{
				addActionError("El certificado del colegiado está caducado o contiene errores.");
			}
		}
		
		if (result != null) {
			if (!result.getError()) {
				addActionMessage("Puede consultar el resultado de la consulta en la 'Evolución' de la notificación");
			} else if (result.getMensaje() != null && !result.getMensaje().isEmpty()) {
				addActionError(result.getMensaje());
			}
		}
		
		return buscar();
	}
	
	public String imprimir(){
		consultaNotificacionBean = (ConsultaNotificacionBean) getSession().get(getKeyCriteriosSession());
		String alias = recuperaAlias();
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
		ResultBean result = null;
		//Este es el valor de la variable del formulario con los datos del checkbox
		String indices = getIdNotificacion();
		String nuevoIndice = null;
		boolean llamarWS = false;
		boolean download = false;
		List<File> listaFicheros = new ArrayList<File>();
		
		//Recorremos el xmlEnviar (split) primero comprobamos si tenemos el PDF custodiado, sino llamamos al WS
		//Solamente están disponibles para descargar las notificaciones por aceptación. Estado - 2
		String[] notificacion = indices.split("-");
		boolean estadoNotificacion = true;
		for (int i = 0; i < notificacion.length; i++) {
			String[] valores = notificacion[i].split(",");
			//Tienen que tener estado 2 
			if (!valores[2].equals("2")){
				estadoNotificacion = false;
			}
		}
		
		if (!estadoNotificacion){
			addActionError("Únicamente es posible imprimir notificaciones en estado 'Notificada por aceptación'");
		}else{
			//Si tenemos el PDF en el gestor de archivos lo mandamos a descargar directamente
			//En caso contrario llamamos al WS
			for (int i = 0; i < notificacion.length; i++) {
				String[] valores = notificacion[i].split(",");
				File fichero = recuperarPdf(Integer.parseInt(valores[1]), valores[3]);
				if (fichero == null){
					llamarWS = true;
					if (nuevoIndice == null){
						nuevoIndice = notificacion[i];
					}else{
						nuevoIndice = nuevoIndice + "-" + notificacion[i];
					}
				}else{
					//Insertamos en la tabla resultados
					consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(valores[1]), "Impresión de notificación", valores[3]);
					download = true;
					listaFicheros.add(fichero);
				}
			}
			
			if (llamarWS){
				String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">Prueba firma</CONTENT></AFIRMA>";
				String pruebaCaduCert = getUtilesViafirma().firmarPruebaCertificadoCaducidadSS(xmlAFirmar, alias);
				if(pruebaCaduCert != null){
					result = servicioConsultaNotificacion.creaSolicitudImprimir(nuevoIndice, alias, idUsuario);
				}else{
					addActionError("El certificado del colegiado está caducado o contiene errores.");
				}
			}
			
		}
		
		if (result != null) {
			if (!result.getError()) {
				addActionMessage("Puede consultar el resultado de la consulta en la 'Evolución' de la notificación");
			} else if (result.getMensaje() != null && !result.getMensaje().isEmpty()) {
				addActionError(result.getMensaje());
			}
		}
		
		if (download){
			creaDescargaZip(listaFicheros);
			return "peticionPDF";
		}
		
		return buscar();
	}
	
	public String verEvolucion(){
		listaRespuestas = consultaNotificacionTransactional.listadoRespuestasPorCodigoNumColegiado(Integer.parseInt(codigo), numColegiado);
		int size = listaRespuestas.size();
		if (size == 0){
			addActionError("No se ha realizado ninguna acción con esta notificación");
			return buscar();
		}
		
		return "evolucion";
	}

	@Override
	public String inicio() {
		if (!utilesColegiado.tienePermiso("SS2")) {
			if ("SI".equals(gestorPropiedades.valorPropertie("seguridad.social.forzar.aceptacion"))) {
				return "aceptacion";
			} else {
				return "forbidden";
			}
		} else {
			return super.inicio();
		}
	}

	public String aceptacionServicio() {
		if(aceptaServicio != null && aceptaServicio) {
			if (!BigDecimal.valueOf(utilesColegiado.getColegiado().getUsuario().getIdUsuario()).equals(utilesColegiado.getIdUsuarioSessionBigDecimal())) {
				addActionError("Solo puede aceptar el servicio el gestor asociado al contrato");
			} else {
				ResultBean result = servicioConsultaNotificacion.aceptarServicio(utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession());
				if (result.getError()) {
					addActionError(result.getMensaje());
				} else {
					 try {
						new ModeloAccesos().crearArbol(utilesColegiado.getNifUsuario(), utilesColegiado.getIdContratoSessionBigDecimal());
					} catch (Throwable e) {
						log.error("Ha ocurrido un error al recuperar los permisos", e);
					}
					addActionMessage("Ha sido dado de alta en el sistema de notificaciones de la seguridad social, para todos los usuarios asociados al contrato. Si no desea que algún usuario tenga acceso a la consulta de notificaciones, puede darlo de baja desde la consulta de contrato");
				}	
			}
			aceptaServicio=Boolean.FALSE;
		} else {
			addActionError("No ha dado su permiso para utilizar el sistema");
		}
		return inicio();
	}

	private void creaDescargaZip(List<File> listaFicheros) {
		try {
			
			//Si tenemos sólo un fichero no generamos el zip
			if (listaFicheros.size() == 1){
				inputStream = new FileInputStream(listaFicheros.get(0));
				fileName = listaFicheros.get(0).getName();
				fileSize = "Unknown";
			}else{
			
				ByteArrayOutputStream fos = new ByteArrayOutputStream();
				ZipOutputStream zos = new ZipOutputStream(fos);
				
				for (File file : listaFicheros) {
					addToZipFile(file, zos);
				}
				
				zos.close();
				fos.close();
				
				setInputStream(new ByteArrayInputStream(fos.toByteArray()));
				setFileName("Notificaciones.zip");
			}
		
		} catch (FileNotFoundException e) {
			log.error("No es posible encontrar el fichero: ", e);
		} catch (IOException e) {
			log.error("Existe algún error imprimiendo la notificación: ", e);
		}
	}
	
	private void addToZipFile(File file, ZipOutputStream zos) {

		try{
			
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

	private File recuperarPdf(int codigoNotificacion, String numColegiado){
		
		File fichero = null;
		List<RespuestaSSVO> listaRespuesta = consultaNotificacionTransactional.recuperarPDFrespuesta(codigoNotificacion, numColegiado);
		if (listaRespuesta.size() > 0){
		    try {
		    	RespuestaSSVO respuesta = listaRespuesta.get(0);
		    	fichero = gestorDocumentos.buscarFicheroPorNombreTipo("NOTIFICACIONES_SS", "PDF_ACUSE", 
		    			utilesFecha.getFechaConDate(new Date(respuesta.getFechaNotificacion().getTime())), "Notificacion_"+Integer.toString(codigoNotificacion)+"_"+numColegiado, ".pdf").getFile();
			} catch (OegamExcepcion e) {
				log.error("Existe algún error recuperando el acuse: ", e);
			}
		}else{
			return null;
		}
		return fichero;
	}
	
	private String recuperaAlias(){
		//Recupero alias del colegiado
		String numColegiado = utilesColegiado.getNumColegiadoSession();
		String alias = utilesColegiado.getAlias(numColegiado);
		
		return alias;
	}
	
	private boolean validarCreditos(BigDecimal idContrato, String creditos) {
		//Si la propertie está a NO, es que no hace falta comprobar creditos, por lo que TRUE. En caso contrario compruebo
		boolean tieneCreditos = true;
		if ("SI".equals(creditos)){
			ModeloCreditosTrafico modelo = new ModeloCreditosTrafico();
			HashMap<String, Object> resultado = modelo.validarCreditosPorNumColegiado(idContrato.toString(), new BigDecimal(1), "SSCN");
			ResultBean result = (ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);
			if (result.getError()) {
				tieneCreditos = false;
				addActionError("No tiene suficientes créditos, no se llevará a cabo la consulta de nuevas notificaciones");
			}
		}
		return tieneCreditos;
	}

	public UtilesViafirma getUtilesViafirma() {
		if(utilesViafirma == null){
			utilesViafirma = new UtilesViafirma();
		}
		return utilesViafirma;
	}

	public void setUtilesViafirma(UtilesViafirma utilesViafirma) {
		this.utilesViafirma = utilesViafirma;
	}

}
