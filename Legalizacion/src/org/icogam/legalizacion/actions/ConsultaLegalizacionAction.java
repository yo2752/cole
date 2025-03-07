package org.icogam.legalizacion.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.icogam.legalizacion.Modelo.ModeloLegalizacionInt;
import org.icogam.legalizacion.ModeloImpl.ModeloLegalizacionImpl;
import org.icogam.legalizacion.beans.paginacion.LegalizacionBean;
import org.icogam.legalizacion.dto.LegalizacionCitaDto;
import org.icogam.legalizacion.factoria.FactoriaLegalizacion;
import org.icogam.legalizacion.utiles.ConstantesLegalizacion;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.ResultBean;
import general.acciones.AbstractFactoryPaginatedList;
import general.acciones.PaginatedListActionSkeleton;
import net.sf.jasperreports.engine.JRException;
import utilidades.estructuras.Fecha;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ConsultaLegalizacionAction extends PaginatedListActionSkeleton implements SessionAware{

	private static final long serialVersionUID = -8518866369020619052L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaLegalizacionAction.class);
	
	//Parámetros de las peticiones del displayTag
	private static final String COLUMDEFECT = "idPeticion";
	
	private static final int ESTADO_FINALIZADO = 4;
	
	private String TipoPermisos;
	private LegalizacionCitaDto legDto;
	private String idPeticion;
	
	private ModeloLegalizacionInt modeloLegalizacion= new ModeloLegalizacionImpl();
	
	//DESCARGAR FICHEROS
	private InputStream inputStream; 	
	private String fileName;			
	private String fileSize;	
	private String varFicheroAdjunto;
	private String varSolicitud;
	private String varEstadoPeticion;
	private String cambioEstado;
	private String idPeticiones;
	
	private String numColegiado;
	
	private Fecha fechaListado = new Fecha();
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private UtilesFecha utilesFecha;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	public String solicitarDocumentacion(){
		recargarPaginatedList();
		try{
			String indices=getIdPeticion();
			String[] codSeleccionados = indices.split("-");
			
			for(String idPeticion : codSeleccionados){
				ResultBean resultBean = modeloLegalizacion.solicitarDocumentacion(Integer.parseInt(idPeticion));
				if (!resultBean.getError()){
					addActionMessage(resultBean.getMensaje());
				}else{
					addActionError(resultBean.getMensaje());
				}
			}
		}catch(OegamExcepcion e){
			log.error("Se ha producido un error al solicitar la documentación. ", e);
			addActionError("Se ha producido un error al solicitar la documentación. " + e.getMessage());
		}
		
		return SUCCESS;
	}
	
	public String borrar(){
		recargarPaginatedList();
		
		String indices=getIdPeticion();
		String[] codSeleccionados = indices.split("-");
		ResultBean resultadoUnitarioBorrar = new ResultBean();
		
		try{
			for(String idPeticion : codSeleccionados){
				resultadoUnitarioBorrar = modeloLegalizacion.borrarPeticion(Integer.parseInt(idPeticion));
				if (resultadoUnitarioBorrar.getError()!=null && resultadoUnitarioBorrar.getError()==true){
					addActionError(resultadoUnitarioBorrar.getMensaje());
				}else{
					addActionMessage("La petición fue borrada correctamente.");
				}
			}
		}catch(Exception e){
			log.error("Se ha producido un error no experado al borrar las peticiones del colegiado: " + utilesColegiado.getNumColegiadoSession(), e);
			addActionError("Se ha producido un error no experado al borrar las peticiones");
		}
		return SUCCESS;
	}
	
//	
	public String descargar(){
		recargarPaginatedList();
		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_LEGALIZACION_DOCUMENTOS_ALTA)) {
			addActionError("No tiene permiso para modificar una peticion.");
			return SUCCESS;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			legDto.setIdPeticion(Integer.parseInt(getIdPeticion()));
			List<File> listaFicheros =modeloLegalizacion.getDocumentacion(legDto);
			
			legDto = modeloLegalizacion.getPeticion(legDto);
			
			ZipOutputStream zip = new ZipOutputStream(baos);
			for(File file:listaFicheros){
				FileInputStream is = new FileInputStream(file);
				ZipEntry zipEntry = new ZipEntry(file.getName());
				zip.putNextEntry(zipEntry);
				byte[] buffer = new byte[2048];
				int byteCount;
				while (-1 != (byteCount = is.read(buffer))) {
					zip.write(buffer, 0, byteCount);
				}
				zip.closeEntry();
				is.close();
				if (file.lastModified() > 0) {
					zipEntry.setTime(file.lastModified());
				} 
			}
			zip.close();
		
			ByteArrayInputStream stream = new ByteArrayInputStream(baos.toByteArray());
			setInputStream(stream);
			setFileName(legDto.getNombre()+ConstantesGestorFicheros.EXTENSION_ZIP);
			log.debug("Enviando el ZIP");
			
		} catch (ParseException e) {
			addActionError("Se ha producido un error al descargar el fichero. Por favor contacte con el colegio");
			log.error("Se ha producido un error al descargar el fichero. ",e);
		} catch (OegamExcepcion e) {
			addActionError("Se ha producido un error al descargar el fichero. Por favor contacte con el colegio");
			log.error("Se ha producido un error al descargar el fichero. ",e);
		} catch (FileNotFoundException e) {
			addActionError("Se ha producido un error al descargar el fichero. Por favor contacte con el colegio");
			log.error("Se ha producido un error al descargar el fichero. ",e);
		} catch (IOException e) {
			addActionError("Se ha producido un error al descargar el fichero. Por favor contacte con el colegio");
			log.error("Se ha producido un error al descargar el fichero. ",e);
		} 
		
		return "descargarDocumentos";
	}
	
	public String cambiarEstado(){
		recargarPaginatedList();
		if(utilesColegiado.tienePermisoAdmin()){
			String[] idsPeticion = idPeticiones.split(",");
			modeloLegalizacion.cambiarEstado(idsPeticion,cambioEstado);
			addActionMessage("Se han actualizado los estados");
		}
		return SUCCESS;
	}
	
	
	/*
	 * Método que limitará la fecha hasta al día anterior al actual independientemente de la fecha que ponga el usuario.
	 * Esto se usará cuando el usuario sea del ministerio.
	 */
	private void limitaFechaMinisterio(){
		try {
			
			Fecha fechaPantalla = new Fecha();
			Fecha fechaAyer = utilesFecha.getDiaMesAnterior();
			
			//Primero comprobar que la fecha de fin no es nulo.
			if (((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().getFechaFin()!=null){
				fechaPantalla.setDia(((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().getDiaFin());
				fechaPantalla.setMes(((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().getMesFin());
				fechaPantalla.setAnio(((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().getAnioFin());
				if (utilesFecha.compararFechaMayor(fechaPantalla, fechaAyer) == 1){
					((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().setDiaFin(fechaAyer.getDia());
					((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().setMesFin(fechaAyer.getMes());
					((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().setAnioFin(fechaAyer.getAnio());
				}
			}else{
				((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().setDiaFin(fechaAyer.getDia());
				((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().setMesFin(fechaAyer.getMes());
				((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().setAnioFin(fechaAyer.getAnio());
			}
			if (((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().getFechaInicio()!=null){
				fechaPantalla.setDia(((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().getDiaInicio());
				fechaPantalla.setMes(((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().getMesInicio());
				fechaPantalla.setAnio(((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().getAnioInicio());
				if (utilesFecha.compararFechaMayor(fechaPantalla, fechaAyer) == 1){
					((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().setDiaInicio(fechaAyer.getDia());
					((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().setMesInicio(fechaAyer.getMes());
					((LegalizacionBean)beanCriterios).getFechaFiltradoLegalizacion().setAnioInicio(fechaAyer.getAnio());
				}
			}
			
		} catch (Throwable e) {
			log.error("Se ha producido un error al limitar la fecha de legalización para el usuario ministerio", e);
		}
	}
	
	public String confirmarLegalizacion(){
		recargarPaginatedList();
		
		String indices=getIdPeticion();
		String[] codSeleccionados = indices.split("-");
		ResultBean resultadoUnitarioConfirmarLegalizacion = new ResultBean();
		
		modeloLegalizacion = new ModeloLegalizacionImpl();
		try{
			for(String idPeticion : codSeleccionados){
				resultadoUnitarioConfirmarLegalizacion=modeloLegalizacion.confirmarFechaLegalizacion(idPeticion, legDto.getFechaLegalizacion());
				if (resultadoUnitarioConfirmarLegalizacion.getError()){
					addActionError(resultadoUnitarioConfirmarLegalizacion.getMensaje());
				}else{
					addActionMessage(resultadoUnitarioConfirmarLegalizacion.getMensaje());
				}
			}
		}catch(Exception e){
			log.error("Se ha producido un error no controlado al establecer legalizaciones masivas: " + utilesColegiado.getNumColegiadoSession(), e);
			addActionError("Se ha producido un error establecer legalizaciones masivas");
		}
		
		
		return SUCCESS;
	}
	
	public String listadoDiario (){
		recargarPaginatedList();
		
		try {
			List<LegalizacionCitaDto> listLegal=null;
			String xml="";
			byte[] byteFinal = null;
			
			if(fechaListado.getDia().equals("") || fechaListado.getMes().equals("") || fechaListado.getAnio().equals("") 
					|| !utilesFecha.esFechaLaborableConsiderandoFestivos(fechaListado)){
				addActionError("La fecha seleccionada para el listado no es valida");
				return SUCCESS;
			}
			
			//ruta donde esta el informe.
			String ruta = gestorPropiedades.valorPropertie(ConstantesLegalizacion.DIR_PLANTILLAS);
			String nombreInforme="";
			// rellenamos el mapa de los parametros del informe
			Map<String, Object> params = new HashMap<String, Object>();	
			if(utilesColegiado.tienePermisoAdmin() && numColegiado.equals("")){
				
				listLegal = modeloLegalizacion.getListadoDiario("",fechaListado);
				xml = modeloLegalizacion.transformToXML(listLegal);
			   nombreInforme = gestorPropiedades.valorPropertie(ConstantesLegalizacion.PLANTILLA_COLEGIO);
			   
			   params.put("FECHA_LEGALIZACION", fechaListado.getFecha());
			   
			   
			}
			else{
				if(!utilesColegiado.tienePermisoAdmin()){
					numColegiado = utilesColegiado.getNumColegiadoSession();
				}else{
					//Si es administrador y ha rellenado el campo num_colegiado se concatena un 0 por delante en caso de que 
					//el número de colegiado sea de 3 dígitos.
					if (numColegiado.length() < 4){
						numColegiado = "0" + numColegiado;
					}
				}
				listLegal = modeloLegalizacion.getListadoDiario(numColegiado,fechaListado);
				xml = modeloLegalizacion.transformToXML(listLegal);
				nombreInforme = gestorPropiedades.valorPropertie(ConstantesLegalizacion.PLANTILLA_COLEGIADO);
				
				Fecha fecha = utilesFecha.getPrimerLaborableAnterior(fechaListado);
				
				params.put("COLEGIADO", numColegiado );
				params.put("FECHA_LEGALIZACION", fecha.getFecha());
				
			}
			
			params.put("IMG_DIR", ruta);
			params.put("SUBREPORT_DIR", ruta);
			// llamamos al informe para generar el informe
			ReportExporter re = new ReportExporter();			
			
			try {
				byteFinal = re.generarInforme(ruta, nombreInforme, "pdf", xml, "Legalizacion", params, null);
				ByteArrayInputStream stream = new ByteArrayInputStream(byteFinal);
				setInputStream(stream);
				setFileName("Informe_"+fechaListado.toString()+ConstantesGestorFicheros.EXTENSION_PDF);
			} catch (JRException e) {
				log.error(e);				
			} catch (ParserConfigurationException e) {
				log.error(e);			
			}	
			
			
		} catch (Throwable e) {
			log.error(e);
		}
		return "descargarPDF";
	}

	
	public String getTipoPermisos() {
		return TipoPermisos;
	}

	public void setTipoPermisos(String tipoPermisos) {
		TipoPermisos = tipoPermisos;
	}

	public LegalizacionCitaDto getLegDto() {
		return legDto;
	}

	public void setLegDto(LegalizacionCitaDto legDto) {
		this.legDto = legDto;
	}
	
	public String getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(String idPeticion) {
		this.idPeticion = idPeticion;
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

	public String getVarFicheroAdjunto() {
		return varFicheroAdjunto;
	}

	public void setVarFicheroAdjunto(String varFicheroAdjunto) {
		this.varFicheroAdjunto = varFicheroAdjunto;
	}

	public String getVarSolicitud() {
		return varSolicitud;
	}

	public void setVarSolicitud(String varSolicitud) {
		this.varSolicitud = varSolicitud;
	}

	public String getCambioEstado() {
		return cambioEstado;
	}

	public void setCambioEstado(String cambioEstado) {
		this.cambioEstado = cambioEstado;
	}

	public String getIdPeticiones() {
		return idPeticiones;
	}

	public void setIdPeticiones(String idPeticiones) {
		this.idPeticiones = idPeticiones;
	}

	public String getVarEstadoPeticion() {
		return varEstadoPeticion;
	}

	public void setVarEstadoPeticion(String varEstadoPeticion) {
		this.varEstadoPeticion = varEstadoPeticion;
	}

	public Fecha getFechaListado() {
		return fechaListado;
	}

	public void setFechaListado(Fecha fechaListado) {
		this.fechaListado = fechaListado;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	@Override
	public AbstractFactoryPaginatedList getFactory() {
		return new FactoriaLegalizacion(); 
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	@Override
	public String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	public String getCriterioPaginatedList() {
		return "legalizacionBean";
	}

	@Override
	public String getCriteriosSession() {
		return "legalizacionSession";
	}

	@Override
	protected void cargaRestricciones() {
		if(!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoMinisterio()){
			/* Es un colegiado */
			((LegalizacionBean)beanCriterios).setNumColegiado(utilesColegiado.getNumColegiadoSession());
		} else if (!utilesColegiado.tienePermisoAdmin() && utilesColegiado.tienePermisoMinisterio()){
			/* Es el ministerio */
			((LegalizacionBean)beanCriterios).setEstadoPeticion(ESTADO_FINALIZADO);
			limitaFechaMinisterio();
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	public String getResultadosPorPaginaSession() {
		return "resultadosPorPaginaLegalizacion";
	}

}
