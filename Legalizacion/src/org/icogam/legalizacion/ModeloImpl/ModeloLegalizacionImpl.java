package org.icogam.legalizacion.ModeloImpl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.icogam.legalizacion.DAO.LegalizacionDao;
import org.icogam.legalizacion.DAOImpl.LegalizacionDaoImplHibernate;
import org.icogam.legalizacion.Modelo.ModeloLegalizacionInt;
import org.icogam.legalizacion.beans.LegalizacionCita;
import org.icogam.legalizacion.dto.LegalizacionCitaDto;
import org.icogam.legalizacion.utiles.ClaseDocumento;
import org.icogam.legalizacion.utiles.ConstantesLegalizacion;
import org.icogam.legalizacion.utiles.TipoDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.thoughtworks.xstream.XStream;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import general.utiles.MD5;
import trafico.modelo.impl.PaginatedListImpl;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ModeloLegalizacionImpl implements ModeloLegalizacionInt{
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloLegalizacionImpl.class);
	
	private static final String TEXT_DOCUMENTACION_SUBIDA = "El Colegio de Gestores de Madrid informa que se ha subido la documentación de la siguiente petición: ";
	private static final String TEXTO_ACUSE_SOLICITUD = "Se ha recibido por parte del Colegio de Gestores Administrativos de Madrid la solicitud de documentación de la siguiente petición:";
	private static final String TEXTO_SOLICITUD_DOCUMENTACION = "Se ha solicitado por parte del Ministerio de Asuntos exteriores y Comercio que se suba la documentación referida a la siguiente petición: ";
	
	private LegalizacionDao legalizacionDao;
	
	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private UtilesFecha utilesFecha;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	public ModeloLegalizacionImpl() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		legalizacionDao = new LegalizacionDaoImplHibernate(new LegalizacionCita());
	}
	
	public ServicioCorreo getServicioCorreo() {
		if(servicioCorreo == null){
			servicioCorreo = ContextoSpring.getInstance().getBean(ServicioCorreo.class);
		}
		return servicioCorreo;
	}
	
	private static final int ESTADO = 1;
	private static final int ESTADO_BAJA = 0;
	private static final int ESTADO_SOLICITADO = 2;
	private static final int ESTADO_INICIADO = 1;
	private static final int ESTADO_FINZALIZADO = 4;
	
	PaginatedListImpl listaLegalizacionCitaP;
			
	
	@Override
	public List<LegalizacionCita> listarPeticiones(LegalizacionCitaDto legalizacion) throws ParseException {
		List<LegalizacionCita> lista = null;
		
		LegalizacionCita legBean = transformDtoBean(legalizacion);
		
		lista = legalizacionDao.buscar(legBean);
		
		return lista;
	}

	@Override
	public LegalizacionCitaDto getPeticion(LegalizacionCitaDto legalizacion)  throws ParseException{
		
		LegalizacionCita legBean = legalizacionDao.getLegalizacionId(legalizacion.getIdPeticion());
		
		LegalizacionCitaDto legDto = transformBeanDto(legBean);
		
		return legDto;
	}
	
	@Override
	public LegalizacionCitaDto actualizar(LegalizacionCitaDto legDto) throws ParseException {	
		LegalizacionCitaDto resultado = null;
		
		LegalizacionCitaDto legActualizar = getPeticion(legDto);
		
		legActualizar.setNombre(legDto.getNombre().toUpperCase());
		legActualizar.setTipoDocumento(legDto.getTipoDocumento());
		legActualizar.setClaseDocumento(legDto.getClaseDocumento());
		legActualizar.setPais(legDto.getPais().toUpperCase());
		legActualizar.setEstado(ESTADO);
		//Si está en estado finalizado entonces no hay que cambiar el estado. La modificación viene porque estarán adjuntando el fichero.
		if(!legActualizar.getEstadoPeticion().equals(ESTADO_FINZALIZADO)){
			if (legDto.getFechaLegalizacion()!=null && !legDto.getFechaLegalizacion().equals("")){
				legActualizar.setEstadoPeticion(ESTADO_SOLICITADO);
			}else{
				legActualizar.setEstadoPeticion(ESTADO_INICIADO);
			}
		}
		if (legDto.getOrden()!=null && !legDto.getOrden().equals("")){
			legActualizar.setOrden(legDto.getOrden());
		}
		legActualizar.setFechaLegalizacion(legDto.getFechaLegalizacion());
		legActualizar.setFechaDocumentacion(legDto.getFechaDocumentacion());
		
		LegalizacionCita legBean = transformDtoBean(legActualizar);
		
		if(legalizacionDao.actualizar(legBean)!=null){
			resultado = transformBeanDto(legBean);
		}
		
		return resultado;
	}
	
	public void actualizarSolicitado(int idPetcion) {
		
		LegalizacionCita legBean = legalizacionDao.getLegalizacionId(idPetcion);
		
		legBean.setSolicitado(1);
		
		legalizacionDao.actualizar(legBean);
	}

	@Override
	public LegalizacionCitaDto guardar(LegalizacionCitaDto legDto) throws ParseException, OegamExcepcion {
		LegalizacionCitaDto resultado = null;
		
		if(legalizacionDao.esPosiblePeticion(legDto.getNumColegiado())){
			LegalizacionCita legBean = transformDtoBean(legDto);
			// Mantis 13697. David Sierra: Se agrega hh:mm:ss
			legBean.setFecha(utilesFecha.getFechaHoraActualLEG().getFechaHora());
			
			legBean.setReferencia(generarHash(legBean));
			legBean.setEstado(ESTADO);
			legBean.setNombre(legBean.getNombre().toUpperCase());
			if (legDto.getFechaLegalizacion()!=null && !legDto.getFechaLegalizacion().getDia().equals("")){
				legBean.setEstadoPeticion(ESTADO_SOLICITADO);
				legBean.setFechaLegalizacion(legDto.getFechaLegalizacion().getFecha());									
			}else{
				legBean.setEstadoPeticion(ESTADO_INICIADO);
			}
			legBean.setFechaDocumento(legDto.getFechaDocumentacion().getFecha());
			Integer id= (Integer) legalizacionDao.guardar(legBean);
			if (legDto.getOrden()!=null && !legDto.getOrden().equals("")){
				legBean.setOrden(legDto.getOrden());
			}
			if(id!=null){
				legBean.setIdPeticion(id);
				//Se comenta esta línea para que siempre se guarde la petición y no se actualice. Así es como lo quieren
				//legDto.setIdPeticion(id);
			}
			else{
				throw new OegamExcepcion("Error al guardar el documento");
			}
			
			resultado = transformBeanDto(legBean);
		}
		else{
			throw new OegamExcepcion("");
		}
		return resultado;
	}
	
	@Override
	public boolean guardarDocumentacion(LegalizacionCitaDto legDto, File fileUpload, String extension) throws OegamExcepcion, ParseException {
		
		LegalizacionCitaDto legActualizar = getPeticion(legDto);
		FicheroBean fichero = new FicheroBean();
		fichero.setExtension(extension);
		fichero.setTipoDocumento(ConstantesGestorFicheros.LEGALIZACION);
		fichero.setNombreDocumento(legActualizar.getReferencia());
		fichero.setFichero(fileUpload);
		fichero.setSobreescribir(true);
		if(TipoDocumento.TITULO.equals(TipoDocumento.convertir(legActualizar.getTipoDocumento()))){
			fichero.setSubTipo(ConstantesGestorFicheros.LEGALIZACION_TITULO);
		}
		else if(TipoDocumento.DOCUMENTOS.equals(TipoDocumento.convertir(legActualizar.getTipoDocumento()))) {
			fichero.setSubTipo(ConstantesGestorFicheros.LEGALIZACION_DOCUMENTO);
		}
		fichero.setFecha(legActualizar.getFechaLegalizacion());
		
		gestorDocumentos.guardarFichero(fichero);
		
		legActualizar.setFicheroAdjunto(true);
		LegalizacionCita legBean = transformDtoBean(legActualizar);
		legalizacionDao.actualizar(legBean);
		
		return true;
	}
	
	@Override
	public List<File> getDocumentacion(LegalizacionCitaDto legDto)
			throws OegamExcepcion, ParseException {
		
		LegalizacionCita legbean = legalizacionDao.getLegalizacionId(legDto.getIdPeticion());
		legDto = transformBeanDto(legbean);
		String subTipo = "";
		if(TipoDocumento.TITULO.equals(TipoDocumento.convertir(legbean.getTipoDocumento()))){
			subTipo = ConstantesGestorFicheros.LEGALIZACION_TITULO;
		}
		else if(TipoDocumento.DOCUMENTOS.equals(TipoDocumento.convertir(legbean.getTipoDocumento()))) {
			subTipo = ConstantesGestorFicheros.LEGALIZACION_DOCUMENTO;
		}
		
		FileResultBean result = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.LEGALIZACION, subTipo, legDto.getFechaLegalizacion(), legbean.getReferencia());
		
		
		return result != null ? result.getFiles() : null;
	}
	
	private String generarHash(LegalizacionCita legBean){
		String resultado="";
		
		resultado = MD5.getHash(legBean.getNombre()+utilesFecha.getTimestampActual());
		
		return resultado;
	}
	
	@Override
	public void cambiarEstado(String[] idsPeticion, String cambioEstado) {
		
		for (String idPeticion : idsPeticion ){
			if(!idPeticion.equals("")){
				LegalizacionCita legBean =  legalizacionDao.getLegalizacionId(Integer.parseInt(idPeticion));
				legBean.setEstadoPeticion(Integer.parseInt(cambioEstado));
				legalizacionDao.actualizar(legBean);
			}
		}
	}
	
	private LegalizacionCita transformDtoBean(LegalizacionCitaDto legDto) throws ParseException{
		LegalizacionCita legBean = new LegalizacionCita();
		if (legDto.isFicheroAdjunto()!=null && legDto.isFicheroAdjunto()==true){
			legBean.setFicheroAdjunto(1);
		}else{
			legBean.setFicheroAdjunto(0);
		}
		if (legDto.isSolicitado()!=null && legDto.isSolicitado()==true){
			legBean.setSolicitado(1);
		}else{
			legBean.setSolicitado(0);
		}		
		if(legDto.getIdPeticion()!=null && !legDto.getIdPeticion().equals(""))
			legBean.setIdPeticion(legDto.getIdPeticion());
		if(legDto.getNombre()!=null && !legDto.getNombre().equals(""))
			legBean.setNombre(legDto.getNombre() );
		if(legDto.getNumColegiado()!=null){
			if(legDto.getNumColegiado().length()<4){
				legDto.setNumColegiado("0"+legDto.getNumColegiado());
			}
			legBean.setNumColegiado(legDto.getNumColegiado() );
		}
		if(legDto.getReferencia()!=null && !legDto.getReferencia().equals(""))
			legBean.setReferencia(legDto.getReferencia());
		if(legDto.getTipoDocumento()!=null && !legDto.getTipoDocumento().equals("")){
			legBean.setTipoDocumento(legDto.getTipoDocumento());
		}
		if(legDto.getFecha()!=null) {
			// Mantis 13697
			legBean.setFecha(legDto.getFecha().getFechaHora());
		}			
		if(legDto.getFechaDocumentacion()!=null){
			legBean.setFechaDocumento(legDto.getFechaDocumentacion().getFecha());
		}
		if(legDto.getFechaLegalizacion()!=null)
			legBean.setFechaLegalizacion(legDto.getFechaLegalizacion().getFecha());
		if(legDto.getEstado()!=null)
			legBean.setEstado(legDto.getEstado());
		if(legDto.getEstadoPeticion()!=null)
			legBean.setEstadoPeticion(legDto.getEstadoPeticion());
		if (legDto.getOrden()!=null && !legDto.getOrden().equals(""))
			legBean.setOrden(legDto.getOrden());
		if(legDto.getClaseDocumento()!=null && !legDto.getClaseDocumento().equals(""))
			legBean.setClaseDocumento(legDto.getClaseDocumento());
		if(legDto.getPais()!=null && !legDto.getPais().equals(""))
			legBean.setPais(legDto.getPais());
		return legBean;
	}
	
	private LegalizacionCitaDto transformBeanDto(LegalizacionCita legBn) throws ParseException{
		LegalizacionCitaDto legDto = new LegalizacionCitaDto();
		
		if (legBn.getFicheroAdjunto()==1){
			legDto.setFicheroAdjunto(true);
		}else{
			legDto.setFicheroAdjunto(false);
		}
		if (legBn.getSolicitado()==1){
			legDto.setSolicitado(true);
		}else{
			legDto.setSolicitado(false);
		}
		if(legBn.getNombre()!=null && !legBn.getNombre().equals(""))
			legDto.setNombre(legBn.getNombre() );
		if(legBn.getNumColegiado()!=null)
			legDto.setNumColegiado(legBn.getNumColegiado() );
		if(legBn.getReferencia()!=null && !legBn.getReferencia().equals(""))
			legDto.setReferencia(legBn.getReferencia());
		if(legBn.getFecha()!=null) {
			// Mantis 13697
			legDto.setFecha(utilesFecha.getFechaTimeStampConDate(legBn.getFecha()));
		}			
		if(legBn.getFechaLegalizacion()!=null)
			legDto.setFechaLegalizacion(utilesFecha.getFechaConDate(legBn.getFechaLegalizacion()));
		if(legBn.getFechaDocumento()!=null){
			legDto.setFechaDocumentacion(utilesFecha.getFechaConDate(legBn.getFechaDocumento()));
		}
		if(legBn.getTipoDocumento()!=null && !legBn.getTipoDocumento().equals(""))
			legDto.setTipoDocumento(legBn.getTipoDocumento());
		if(legBn.getIdPeticion()!=null){
			legDto.setIdPeticion(legBn.getIdPeticion());
		}
		if(legBn.getEstado()!=null){
			legDto.setEstado(legBn.getEstado());
		}
		if(legBn.getEstadoPeticion()!=null){
			legDto.setEstadoPeticion(legBn.getEstadoPeticion());
		}
		if(legBn.getOrden()!=null && !legBn.getOrden().equals(""))
			legDto.setOrden(legBn.getOrden());
		if(legBn.getClaseDocumento()!=null && !legBn.getClaseDocumento().equals(""))
			legDto.setClaseDocumento(legBn.getClaseDocumento());
		if(legBn.getPais()!=null && !legBn.getPais().equals(""))
			legDto.setPais(legBn.getPais().toUpperCase());
		return legDto;
	}
	
	public ResultBean borrarPeticion (int idPeticion) throws Exception{
		LegalizacionCita legalizacionCita = legalizacionDao.getLegalizacionId(idPeticion);
		ResultBean resultado = new ResultBean();
		resultado.setError(false);
		
			try{
			//Si es usuario Administrador tiene permiso para borrar cualquier petición sin importar el día.
			//Si es un colegiado solo podrá borrar la petición hecha en el día de hoy.
			if (utilesColegiado.tienePermisoAdmin()){
				legalizacionCita.setEstado(ESTADO_BAJA);
				legalizacionDao.actualizar(legalizacionCita);
			}else{
				if(legalizacionCita.getFechaLegalizacion()!=null){
					resultado = permiteBorrar(legalizacionCita);
				}
				if (resultado.getError()==false){
					legalizacionCita.setEstado(ESTADO_BAJA);
					legalizacionDao.actualizar(legalizacionCita);
				}
			}
			
		}catch(Exception e){
			throw new Exception(e);
		}
			
		return resultado;
	}
	
	private ResultBean permiteBorrar(LegalizacionCita legalizacionCita)throws Exception{
		ResultBean result = new ResultBean();
		//Si no tiene fecha de legalización se puede borrar siempre.
		result.setError(false);
		try{
			int fechasComparadas = utilesFecha.compararFechaMayor(utilesFecha.getFechaHoraActual(), 
			utilesFecha.getFechaConDate(legalizacionCita.getFechaLegalizacion()));
			
			if (fechasComparadas == 0){
					result.setError(true);
					result.setMensaje("No se puede eliminar la petición. Hoy será entregada al MAEC.");
			}
			
			if (fechasComparadas == 1){
					result.setError(true);
					result.setMensaje("No se puede eliminar la petición. La fecha de legalización ya ha pasado.");
			}
			
			if (fechasComparadas == 2){
				//La fecha de legalización es mayor que la actual, pero si es el día siguiente al actual y son más de la hora limite y no se puede borrar.
				// La hora limite normalmente son las 16h pero en agosto muchas veces son las 14h
				int fechasCompadasAux = utilesFecha.compararFechaMayor(utilesFecha.getPrimerLaborablePosterior(utilesFecha.getFechaHoraActual()), 
						utilesFecha.getFechaConDate(legalizacionCita.getFechaLegalizacion()));
				if (fechasCompadasAux == 0){
					if (fueradeHorario()){
						result.setError(true);
						result.setMensaje("No se puede eliminar la petición. La fecha es el próximo día laborable y " +
						"son más de las"+ horaFin()+"h.");
					}
				}
			}
			
		}catch(ParseException e){
			throw new Exception(e);
		}catch(Throwable e){
			throw new Exception(e);
		}
		
		return result;
	}
	

	
	public ResultBean solicitarDocumentacion(int idPeticion) throws OegamExcepcion{
		ResultBean resultBean = new ResultBean();
		LegalizacionCita legalizacionCita = legalizacionDao.getLegalizacionId(idPeticion);
		
		try{
			if (esPosibleSolicitud(legalizacionCita)){
				envioMailInformandoColegio(idPeticion);
				envioMailAcuseMAEC(idPeticion);
				actualizarSolicitado(idPeticion);
				
				resultBean.setError(false);
				resultBean.setMensaje("Se ha solicitado correctamente la documentación para la petición: " + legalizacionCita.getReferencia());
				return resultBean;
			}else{
				resultBean.setError(true);
				resultBean.setMensaje("No se puede solicitar la documentación del trámite " + legalizacionCita.getReferencia() + 
					" porque ha pasado más de 1 semana desde que se entregó en el Ministerio");
				return resultBean;
			}
		}catch (OegamExcepcion e){
			throw new OegamExcepcion("Se ha producido un error al solicitar la documentación de la petición: " + legalizacionCita.getReferencia());
		}
	}
	
	private boolean esPosibleSolicitud(LegalizacionCita legalizacionCita){
		boolean sePuede = true;
		
		try {
			//Se tiene que comprobar que no han pasado 7 días desde que se entregó la documentación.
			Timestamp fecha7Dias = utilesFecha.sumaDiasAFecha(utilesFecha.getFechaConDate(legalizacionCita.getFechaLegalizacion()),"7");
			int fechasComparadas = utilesFecha.compararFechaMayor(utilesFecha.getFechaHoraActual(),utilesFecha.getFechaFracionada(fecha7Dias));
			
			if (fechasComparadas == 1){
				sePuede=false;
			}
			
		} catch (ParseException e) {
			log.error("Se ha producido un error al obtener los datos de la petición: " + legalizacionCita.getIdPeticion() + " al comprobar si es posible hacer"
					+ "una solicitud y no ha pasado más de 1 semana");
		}
		return sePuede;
	}
	
	@Override
	public List<LegalizacionCitaDto> getListadoDiario(String numColegiado,Fecha fechaListado) throws Throwable {
		List<LegalizacionCitaDto> listLegDto = new ArrayList<LegalizacionCitaDto>();
		LegalizacionCita legBean = new LegalizacionCita();
		if(numColegiado!=null && numColegiado!=""){
			legBean.setNumColegiado(numColegiado);
			
			legBean.setFechaLegalizacion(fechaListado.getFecha());
			
			List<LegalizacionCita> listLeg = legalizacionDao.listadoDiarioColegiado(legBean);
			for(LegalizacionCita leg : listLeg){
				LegalizacionCitaDto legDto= transformBeanDto(leg);
				legDto.setTipoDocumento(TipoDocumento.convertir(legDto.getTipoDocumento()).getNombreEnum());
				if(legDto.getClaseDocumento() != null) {
					legDto.setClaseDocumento(ClaseDocumento.convertir(legDto.getClaseDocumento()).getNombreEnum());
				}
				listLegDto.add(legDto);
			}
		}else{
			legBean.setFechaLegalizacion(fechaListado.getFecha());
			
			List<LegalizacionCita> listLeg = legalizacionDao.listadoDiario(legBean);
			for(LegalizacionCita leg : listLeg){
				LegalizacionCitaDto legDto= transformBeanDto(leg);
				legDto.setTipoDocumento(TipoDocumento.convertir(legDto.getTipoDocumento()).getNombreEnum());
				if(legDto.getClaseDocumento() != null) {
					legDto.setClaseDocumento(ClaseDocumento.convertir(legDto.getClaseDocumento()).getNombreEnum());
				}
				listLegDto.add(legDto);
			}
		}
		return listLegDto;
	}
	
	/**
	 * Método que comprueba la fecha de legalización que se quiere asignar a una petición
	 * 1- Después de la hora fin /limite del día anterior a la fecha de legalización no se puede modificar
	 * Dicha hora suelen ser las 16h, aunque en agosto puede que sean las 14h
	 * 2- Si la fecha de legalización es menor al día actual no se permite modificar
	 * 3- Si la fecha de legalización a insertar no es laborable no es válida
	 */
	public ResultBean permiteModificarPeticion(int idPeticion, Fecha fechaLegalizacion)throws Exception{
		ResultBean result = new ResultBean();
		result.setError(false);
		
		try{
			LegalizacionCita legalizacion = legalizacionDao.getLegalizacionId(idPeticion);
			//Comprueba si la petición es modificable
			result = peticionModificable(legalizacion); 
			if (result.getError()){
				return result;
			}
			
			Fecha FechaLimitePresentacionEnColegio = utilesFecha.getPrimerLaborableAnterior(fechaLegalizacion);
			//Comprueba si la fecha de legalización a introducir es válida
			result = fechaLegalizacionValida(fechaLegalizacion, FechaLimitePresentacionEnColegio);
			if (result.getError()){
				return result;
			}
		} catch(Exception e){
			throw new Exception(e);
			
		} catch(Throwable e){
			throw new Exception(e);
		}
		
		return result;
	}
	
	private ResultBean peticionModificable(LegalizacionCita legalizacion)throws Exception{
		ResultBean result = new ResultBean();
		result.setError(false);
		
		try {
			if (legalizacion.getFechaLegalizacion()!=null){
				int resultadoComparacion = utilesFecha.compararFechaMayor(utilesFecha.getFechaActual(), 
						utilesFecha.getFechaConDate(legalizacion.getFechaLegalizacion()));
				
				if (resultadoComparacion == 0 || resultadoComparacion == 1){
					result.setError(true);
					result.setMensaje("La petición con nombre: " + legalizacion.getNombre() + " ha sido entregada en el Ministerio y No puede modificarse");
				}
			}
		} catch (ParseException e) {
			throw new Exception(e);
		}
		
		return result;
	}
	
	private ResultBean fechaLegalizacionValida(Fecha fechaLegalizacion, Fecha FechaLimitePresentacionEnColegio)throws Exception{
		ResultBean result = new ResultBean();
		result.setError(false);
		
		try{
			int resultadoComparacion = utilesFecha.compararFechaMayor(utilesFecha.getFechaActual(), FechaLimitePresentacionEnColegio);
			
			if (resultadoComparacion == 0){
				if (fueradeHorario()){
					result.setError(true);
					result.setMensaje("No se puede modificar o guardar la petición porque ya han pasado las" + horaFin()+"h del día anterior a legalización.");
					return result;
				}
			}
			
			if (resultadoComparacion == 1){
				result.setError(true);
				result.setMensaje("No se puede modificar o guardar la petición porque ya han pasado las " + horaFin()+"h del día laborable anterior a la fecha de legalización introducida.");
				return result;
			}
			
			if (!utilesFecha.esFechaLaborableConsiderandoFestivos(fechaLegalizacion)){
				result.setError(true);
				result.setMensaje("La fecha de Legalización tiene que ser un día laborable.");
				return result;
			}
		}catch (OegamExcepcion e){
			throw new Exception(e);
		}catch (Exception e){
			throw new Exception(e);
		}
		return result;
	}
	
	/**
	 * Método que comprueba si la fecha de legalización es válida para dar de alta una petición.
	 */
	public ResultBean permiteGuardarPeticion(LegalizacionCitaDto legalizacionCitaDto) throws ParseException, Throwable{
		ResultBean result = new ResultBean();
		result.setError(false);
		
		if (legalizacionCitaDto.getFechaLegalizacion() == null || legalizacionCitaDto.getFechaLegalizacion().getAnio().equals("")){
			result.setError(false);
		}else{
			
			Fecha FechaLimitePresentacionEnColegio = utilesFecha.getPrimerLaborableAnterior(legalizacionCitaDto.getFechaLegalizacion());
			//Comprueba si la fecha de legalización a introducir es válida
			result = fechaLegalizacionValida(legalizacionCitaDto.getFechaLegalizacion(), FechaLimitePresentacionEnColegio);
			
		}
		
		return result;
	}
	
	/**
	 * Método para dar valor a la fecha de legalización y cambio el estado a solicitado.
	 */
	public ResultBean confirmarFechaLegalizacion(String idPeticion, Fecha fechaLegalizacion) throws Exception{
		LegalizacionCita legalizacionCita = legalizacionDao.getLegalizacionId(Integer.parseInt(idPeticion));
		ResultBean resultBean = new ResultBean();
		try {
			if (!utilesColegiado.tienePermisoAdmin()){
				if (legalizacionCita.getFechaLegalizacion()!=null){
					Fecha FechaLimitePresentacionEnColegio = utilesFecha.getPrimerLaborableAnterior(utilesFecha.getFechaConDate(legalizacionCita.getFechaLegalizacion()));
					resultBean = fechaLegalizacionValida(fechaLegalizacion, FechaLimitePresentacionEnColegio);
					if (resultBean.getError()){
						return resultBean;
					}
				}
				ResultBean resultAux = permiteSolicitarLegalizacion(fechaLegalizacion);
				if (resultAux.getError()){
					resultBean.setError(true);
					resultBean.setMensaje(resultAux.getMensaje());
					return resultBean;
				}
			}
			
			legalizacionCita.setFechaLegalizacion(fechaLegalizacion.getDate());
			legalizacionCita.setEstadoPeticion(ESTADO_SOLICITADO);
			legalizacionDao.actualizar(legalizacionCita);
			resultBean.setError(false);
			resultBean.setMensaje("Se ha establecido la fecha de legalización correctamente para la petición con nombre: " + legalizacionCita.getNombre());
			return resultBean;
		} catch (ParseException e) {
			throw new Exception("Se ha producido un error asignar fecha de legalizacion para la petición con nombre " + legalizacionCita.getNombre());
		} catch (Exception e){
			throw new Exception("Se ha producido un error asignar fecha de legalizacion para la petición con nombre " + legalizacionCita.getNombre());
		} catch (Throwable t){
			throw new Exception("Se ha producido un error asignar fecha de legalizacion para la petición con nombre " + legalizacionCita.getNombre());
		}
	}
	
	/**
	 * Método que comprueba la fecha de legalización para ver si es válida.
	 * 1- No puede ser menor o igual a la fecha actual
	 * 2- No puede ser en fin de semana o festivo nacional
	 * 3- Si es después de las hora limite / fin no puede ser mañana
	 * Esta hora normalmente son las 16h aunque en agosto pueden ser las 14h 
	 */
	private ResultBean permiteSolicitarLegalizacion(Fecha fechaLegalizacion) throws Exception{
		ResultBean result = new ResultBean();
		result.setError(false);
		
		try {
			if (!utilesFecha.esFechaLaborableConsiderandoFestivos(fechaLegalizacion)){
				result.setError(true);
				result.setMensaje("La fecha de legalización tiene que ser un día laborable.");
				return result;
			}
			
			int resultadoComparacion = utilesFecha.compararFechaMayor(utilesFecha.getFechaActual(),	fechaLegalizacion);
			
			if (resultadoComparacion == 1 || resultadoComparacion == 0){
				result.setError(true);
				result.setMensaje("La fecha de legalización tiene que ser mayor al día actual.");
				return result;
			}
			
			if (resultadoComparacion == 2){
				int fechasCompadasAux = utilesFecha.compararFechaMayor(utilesFecha.getPrimerLaborablePosterior(utilesFecha.getFechaHoraActual()), 
						fechaLegalizacion);
				
				if (fechasCompadasAux == 0 && fueradeHorario()){
					result.setError(true);
					result.setMensaje("Han pasado las " + horaFin()+"h. "
							+ "La fecha de legalización tiene que ser mayor que el siguiente dia laborable.");
					return result;
				}
			}
			
		} catch (OegamExcepcion e) {
			throw new Exception(e);
		} catch (Throwable e) {
			throw new Exception(e);
		}
		
		return result;
	}
	
	public String transformToXML(List<LegalizacionCitaDto> listaLeg){
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
		
		XStream xStream = new XStream();
		xStream.processAnnotations(LegalizacionCitaDto.class);
		xml += xStream.toXML(listaLeg);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");
		
		return xml;
	}
	
	
	public void envioMailInformandoColegio(int idPeticion) throws OegamExcepcion{
		try{
			//Obtener el objeto del idPeticion
			LegalizacionCita legalizacionCita = legalizacionDao.getLegalizacionId(idPeticion);
			String textoMensaje = "";
			
			String asuntoMail = gestorPropiedades.valorPropertie("asunto.mail.solicitud.documentacion");
			String recipient = gestorPropiedades.valorPropertie("direccion.solicitud.documentacion.MAEC");
			String bcc = gestorPropiedades.valorPropertie("copia.envio.solicitud.documentacion");
			textoMensaje = TEXTO_SOLICITUD_DOCUMENTACION;
			textoMensaje = textoMensaje + "<br></br>";
			textoMensaje = textoMensaje + "Nombre de la peticion: " +  legalizacionCita.getNombre();
			textoMensaje = textoMensaje + "<br></br>";
			textoMensaje = textoMensaje + "Referencia de la peticion: " + legalizacionCita.getReferencia();
			textoMensaje = textoMensaje + "<br></br>";
			textoMensaje = textoMensaje + "Número de colegiado: " + legalizacionCita.getNumColegiado();
			
			ResultBean resultado;
			
			resultado = getServicioCorreo().enviarCorreo(textoMensaje, null, null, asuntoMail, recipient, bcc, null, null);
			if(resultado==null || resultado.getError()){
				throw new OegamExcepcion("Se ha producido un error al enviar mail al colegio pidiendo la documentación de la petición: " + idPeticion);
			}
			
		}catch(OegamExcepcion | IOException e){
			throw new OegamExcepcion("Se ha producido un error al enviar mail al colegio pidiendo la documentación de la petición: " + idPeticion);
		}
	}
	
	public void envioMailAcuseMAEC(int idPeticion) throws OegamExcepcion{
		try{
			//Obtener el objeto del idPeticion
			LegalizacionCita legalizacionCita = legalizacionDao.getLegalizacionId(idPeticion);
			String textoMensaje = "";
			
			String asuntoMail = gestorPropiedades.valorPropertie("asunto.mail.acuse.solicitud");
			String recipient = gestorPropiedades.valorPropertie("direccion.email.MAEC");
			String bcc = gestorPropiedades.valorPropertie("copia.envio.solicitud.documentacion");
			textoMensaje = TEXTO_ACUSE_SOLICITUD;
			textoMensaje = textoMensaje + "<br></br>";
			textoMensaje = textoMensaje + "Nombre de la peticion: " +  legalizacionCita.getNombre();
			textoMensaje = textoMensaje + "<br></br>";
			textoMensaje = textoMensaje + "Referencia de la peticion: " + legalizacionCita.getReferencia();
			textoMensaje = textoMensaje + "<br></br>";
			textoMensaje = textoMensaje + "Número de colegiado: " + legalizacionCita.getNumColegiado();

			ResultBean resultado;			
			resultado = getServicioCorreo().enviarCorreo(textoMensaje, null, null, asuntoMail, recipient, bcc, null, null);
			if(resultado==null || resultado.getError()){
				throw new OegamExcepcion("Se ha producido un error al enviar mail al colegio pidiendo la documentación de la petición: " + idPeticion);
			}			
			
		}catch (OegamExcepcion | IOException e){
			throw new OegamExcepcion("Se ha producido un error al enviar mail de acuse de recibo al Ministerio de la petición: " + idPeticion);
		}
	}
	
	public void envioMailInformandoMAECDocumentacionSubida(LegalizacionCitaDto legDto) throws OegamExcepcion{
		try{
			LegalizacionCita legBean = legalizacionDao.getLegalizacionId(legDto.getIdPeticion());
			String textoMensaje = "";
			
			String asuntoMail = gestorPropiedades.valorPropertie("asunto.mail.documentacion.subida");
			String recipient = gestorPropiedades.valorPropertie("direccion.email.MAEC");
			String bcc = gestorPropiedades.valorPropertie("copia.envio.solicitud.documentacion");
			textoMensaje = TEXT_DOCUMENTACION_SUBIDA;
			textoMensaje = textoMensaje + "<br></br>";
			textoMensaje = textoMensaje + "Nombre de la peticion: " +  legBean.getNombre();
			textoMensaje = textoMensaje + "<br></br>";
			textoMensaje = textoMensaje + "Referencia de la peticion: " + legBean.getReferencia();
			textoMensaje = textoMensaje + "<br></br>";
			textoMensaje = textoMensaje + "Número de colegiado: " + legBean.getNumColegiado();

			ResultBean resultado;			
			resultado = getServicioCorreo().enviarCorreo(textoMensaje, null, null, asuntoMail, recipient, bcc, null, null);
			if(resultado==null || resultado.getError()){
				throw new OegamExcepcion("Se ha producido un error al enviar mail al Ministerio informando que se ha subido la documentacion "
						+ "de la petición con referencia: " + legDto.getReferencia());
			}
					
		}catch(OegamExcepcion | IOException e){
			throw new OegamExcepcion("Se ha producido un error al enviar mail al Ministerio informando que se ha subido la documentacion "
					+ "de la petición con referencia: " + legDto.getReferencia());
		}
	}
	
	public boolean fueradeHorario() throws NumberFormatException, OegamExcepcion{
		boolean resultado = false;
		Fecha fecha = utilesFecha.getFechaHoraActualLEG();
		
		if(!utilesColegiado.tienePermisoAdmin()){
			if(Integer.parseInt(fecha.getHora())<horaInicio() 
					|| Integer.parseInt(fecha.getHora())>=horaFin()){
				resultado = true;
			}
		}	
		return resultado;
	}
	
	public boolean permiteModificarPeticion(){
		LegalizacionDao legalizacionDao = new LegalizacionDaoImplHibernate(new LegalizacionCita());
		boolean resultado=true;
		
		try{
			LegalizacionCita legalizacion = legalizacionDao.getLegalizacionId(Integer.parseInt("32"));
			Fecha FechaLimitePresentacionEnColegio = utilesFecha.getPrimerLaborableAnterior(utilesFecha.getFechaConDate(legalizacion.getFechaLegalizacion()));
			int resultadoComparacion = utilesFecha.compararFechaMayor(utilesFecha.getFechaActual(), FechaLimitePresentacionEnColegio);
			
			if (resultadoComparacion == 0){
				if (fueradeHorario()){
					resultado = false;
				}
			}
			
			if (resultadoComparacion == 1){
				resultado = false;
			}
			
		} catch(Throwable e){
			
		}
		
		return resultado;
	}

	private Integer horaFin(){
		return Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesLegalizacion.HORA_FIN));
	}

	public Integer numMaximoColegiado(){
		return Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesLegalizacion.MAXIMO_GESTOR));
	}
	
	public Integer numMaximoDia(){
		return Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesLegalizacion.MAXIMO_DIA));
	}
	
	public Integer horaInicio(){
		return Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesLegalizacion.HORA_INICIO));
	}
	
}
