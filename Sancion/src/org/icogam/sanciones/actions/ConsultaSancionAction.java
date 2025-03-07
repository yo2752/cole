package org.icogam.sanciones.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.icogam.sanciones.DTO.BeanCriteriosSancion;
import org.icogam.sanciones.DTO.SancionDto;
import org.icogam.sanciones.DTO.SancionListadosMotivosDto;
import org.icogam.sanciones.Modelo.ModeloSancion;
import org.icogam.sanciones.ModeloImpl.ModeloSancionImpl;
import org.icogam.sanciones.Utiles.ConstantesSancion;
import org.icogam.sanciones.Utiles.Motivo;
import org.icogam.sanciones.factoria.FactoriaSancion;
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

public class ConsultaSancionAction extends PaginatedListActionSkeleton {
	
	private static final long serialVersionUID = -2621888710831123463L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaSancionAction.class);

	private ModeloSancion modeloSancion = null;

	private String idSancion;
	
	//Parámetros de las peticiones del displayTag
	private static final String COLUMDEFECT = "idSancion";
	
	private String cambioEstado;
	private String idSanciones;
	
	//DESCARGAR FICHEROS
	private InputStream inputStream; 	
	private String fileName;			
	private String fileSize;	
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public String cambiarEstado(){
		recargarPaginatedList();
		if(utilesColegiado.tienePermisoAdmin()){
			String[] idsSacion = idSanciones.split(",");
			getModeloSancion().cambiarEstado(idsSacion, cambioEstado, null);
			addActionMessage("Se han actualizado los estados");
		}
		return SUCCESS;
	}
	
	public String borrar(){
		recargarPaginatedList();
		String indices = getIdSancion();
		String[] codSeleccionados = indices.split("-");
		ResultBean resultadoUnitarioBorrar = new ResultBean();
		String numColegiado = null;
		if (!utilesColegiado.tienePermisoAdmin()){
			numColegiado = utilesColegiado.getNumColegiadoSession();
		}
		try{
			for(String idSancion : codSeleccionados){
				resultadoUnitarioBorrar = getModeloSancion().borrarSancion(Integer.parseInt(idSancion), numColegiado);
				if (resultadoUnitarioBorrar.getError()!=null && resultadoUnitarioBorrar.getError()==true){
					addActionError(resultadoUnitarioBorrar.getMensaje());
				}else{
					addActionMessage("La sanción fue borrada correctamente.");
				}
			}
			
		}catch(Exception e){
			log.error("Se ha producido un error no experado al borrar las sanciones del colegiado: " + utilesColegiado.getNumColegiadoSession(), e);
			addActionError("Se ha producido un error no experado al borrar las sanciones");
		}
		
		return SUCCESS;
	}
	
	public String listado() {
		recargarPaginatedList();
		String indices = getIdSancion();
		String[] codSeleccionados = indices.split("-");
		SancionListadosMotivosDto sancionListadosMotivosDto = new SancionListadosMotivosDto();
		Fecha compararFecha = null;
		String compararNumColegiado = null;
		SancionDto sancionDto;
		String numColegiado = null;
		if (!utilesColegiado.tienePermisoAdmin()){
			numColegiado = utilesColegiado.getNumColegiadoSession();
		}
		
		// Busqueda por ids
		try{
			for(String codigo : codSeleccionados){
				sancionDto = getModeloSancion().getSancionPorId(Integer.parseInt(codigo), numColegiado);
				if (sancionDto==null){
					addActionError ("No existe la sanción");
					return SUCCESS;
				}
				if (sancionDto.getFechaPresentacion().getDate().before(utilesFecha.getFechaActual().getDate())) {
					addActionError("No puede imprimir un listado de días posteriores al de hoy");
					return SUCCESS;
				}
				
				if (compararFecha == null) {
					compararFecha = sancionDto.getFechaPresentacion();
				} else if (!sancionDto.getFechaPresentacion().getFecha().equals(compararFecha.getFecha())) {
					addActionError("Todos los trámites seleccionados deben tener la misma fecha de presentación");
					return SUCCESS;
				} 
				
				if (compararNumColegiado == null) {
					compararNumColegiado = sancionDto.getNumColegiado();
				} else if (!sancionDto.getNumColegiado().equals(compararNumColegiado)) {
					addActionError("Todos los trámites seleccionados deben ser del mismo colegiado");
					return SUCCESS;
				} 
				if (Motivo.ALEGACION.getValorEnum().equals(sancionDto.getMotivo())) {
					sancionListadosMotivosDto.getListaSancionMotivoAle().add(sancionDto);
				} else if (Motivo.RECURSO.getValorEnum().equals(sancionDto.getMotivo())) {
					sancionListadosMotivosDto.getListaSancionMotivoRec().add(sancionDto);
				}
			}
			
		}catch(Exception e){
			log.error("Se ha producido un error no experado al borrar las sanciones del colegiado: " + utilesColegiado.getNumColegiadoSession(), e);
			addActionError("Se ha producido un error no experado al borrar las sanciones");
		}
		
		try {
			
			String xml="";
			byte[] byteFinal = null;
			
			//ruta donde esta el informe.
			String ruta = gestorPropiedades.valorPropertie(ConstantesSancion.DIR_PLANTILLAS);
			String nombreInforme="";
			// rellenamos el mapa de los parametros del informe
			Map<String, Object> params = new HashMap<String, Object>();
			
			nombreInforme = gestorPropiedades.valorPropertie(ConstantesSancion.PLANTILLA_COLEGIO);
			
			params.put("FECHA_PRESENTACION", compararFecha.getFecha());
			params.put("LISTADOALE", sancionListadosMotivosDto.getListaSancionMotivoAle());
			params.put("LISTADOREC", sancionListadosMotivosDto.getListaSancionMotivoRec());
			params.put("LISTADORES", sancionListadosMotivosDto.getListaSancionMotivoRes());
			
			xml = getModeloSancion().transformToXML(sancionListadosMotivosDto);
			
			params.put("IMG_DIR", ruta);
			params.put("SUBREPORT_DIR", ruta);
			// llamamos al informe para generar el informe
			ReportExporter re = new ReportExporter();			
			
			try {
				byteFinal = re.generarInforme(ruta, nombreInforme, "pdf", xml, "Sancion", params, null);
				ByteArrayInputStream stream = new ByteArrayInputStream(byteFinal);
				setInputStream(stream);
				setFileName("InformeSanciones_"+compararFecha.toString()+ConstantesGestorFicheros.EXTENSION_PDF);
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


	public ModeloSancion getModeloSancion() {
		if (modeloSancion==null){
			modeloSancion = new ModeloSancionImpl();
		}
		return modeloSancion;
	}

	public void setModeloSancion(ModeloSancion modeloSancion) {
		this.modeloSancion = modeloSancion;
	}

	public String getCambioEstado() {
		return cambioEstado;
	}

	public void setCambioEstado(String cambioEstado) {
		this.cambioEstado = cambioEstado;
	}

	public String getIdSanciones() {
		return idSanciones;
	}

	public void setIdSanciones(String idSanciones) {
		this.idSanciones = idSanciones;
	}

	public String getIdSancion() {
		return idSancion;
	}

	public void setIdSancion(String idSancion) {
		this.idSancion = idSancion;
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
	public AbstractFactoryPaginatedList getFactory() {
		return new FactoriaSancion();
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
		return "criterioPaginatedListSancion";
	}

	@Override
	public String getCriteriosSession() {
		return "criteriosSessionSancion";
	}

	@Override
	public String getResultadosPorPaginaSession() {
		return "resultadosPorPaginaSancion";
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(!utilesColegiado.tienePermisoAdmin()){
			((BeanCriteriosSancion)beanCriterios).setNumColegiado(utilesColegiado.getNumColegiadoSession());		
		}
	}
}
