package org.icogam.sanciones.ModeloImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.icogam.sanciones.DAO.SancionDao;
import org.icogam.sanciones.DAOImpl.SancionDaoImpl;
import org.icogam.sanciones.DTO.SancionDto;
import org.icogam.sanciones.DTO.SancionListadosMotivosDto;
import org.icogam.sanciones.Modelo.ModeloSancion;
import org.icogam.sanciones.Utiles.ConstantesSancion;
import org.icogam.sanciones.Utiles.Motivo;
import org.icogam.sanciones.beans.Sancion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.thoughtworks.xstream.XStream;

import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ModeloSancionImpl implements ModeloSancion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloSancionImpl.class);
	
	private SancionDao sancionDao;
	
	private static final int ESTADO_BAJA = 0;
	private static final int ESTADO = 1;
	
	private static final int ESTADO_INICIADO = 1;
	private static final int ESTADO_SOLICITADO = 2;
	private static final int ESTADO_FINALIZADO = 3;
	
	@Autowired
	UtilesFecha utilesFecha;

	public ModeloSancionImpl() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		sancionDao = new SancionDaoImpl();
	}
	
	@Override
	public SancionDto getSancion(SancionDto sancionInDto, String numColegiado)  throws ParseException{
		Sancion sancion = sancionDao.getSancionId(sancionInDto.getIdSancion(), numColegiado);
		
		SancionDto sancionOutDto = transformBeanDto(sancion);
		
		return sancionOutDto;
	}
	
	@Override
	public SancionDto getSancionPorId(Integer idSancion, String numColegiado)  throws ParseException{
		Sancion sancion = sancionDao.getSancionId(idSancion, numColegiado);
		
		SancionDto sancionOutDto = transformBeanDto(sancion);
		
		return sancionOutDto;
	}
	
	@Override
	public SancionDto actualizar(SancionDto sancionDto, String numColegiado) throws ParseException {	
		SancionDto resultado = null;
		
		SancionDto sancionActualizar = getSancion(sancionDto, numColegiado);
		
		if (sancionDto.getNombre() != null) {
			sancionActualizar.setNombre(sancionDto.getNombre().toUpperCase());
		}
		
		sancionActualizar.setApellidos(sancionDto.getApellidos().toUpperCase());
		sancionActualizar.setDni(sancionDto.getDni().toUpperCase());
		sancionActualizar.setMotivo(sancionDto.getMotivo());
		sancionActualizar.setEstado(ESTADO);
		
		//Si está en estado finalizado entonces no hay que cambiar el estado
		if(!sancionActualizar.getEstadoSancion().equals(ESTADO_FINALIZADO)){
			if (sancionDto.getFechaPresentacion()!=null && !sancionDto.getFechaPresentacion().equals("")){
				sancionActualizar.setEstadoSancion(ESTADO_SOLICITADO);
			}else{
				sancionActualizar.setEstadoSancion(ESTADO_INICIADO);
			}
		}
		
		sancionActualizar.setFechaPresentacion(sancionDto.getFechaPresentacion());
		
		Sancion sancion = transformDtoBean(sancionActualizar);
		
		if(sancionDao.actualizar(sancion)!=null){
			resultado = transformBeanDto(sancion);
		}
		
		return resultado;
	}
	
	@Override
	public SancionDto guardar(SancionDto sancionDto) throws ParseException, OegamExcepcion {
		SancionDto resultado = null;
		Sancion sancion = transformDtoBean(sancionDto);
		sancion.setFechaAlta(utilesFecha.getFechaHoraActualLEG().getFechaHora());
		sancion.setEstado(ESTADO);
		
		if (sancionDto.getNombre() != null) {
			sancion.setNombre(sancionDto.getNombre().toUpperCase());
		}
		sancion.setApellidos(sancionDto.getApellidos().toUpperCase());
		sancion.setDni(sancionDto.getDni().toUpperCase());
		
		if (sancionDto.getFechaPresentacion()!=null && !sancionDto.getFechaPresentacion().getDia().equals("")){
			sancion.setEstadoSancion(ESTADO_SOLICITADO);
			sancion.setFechaPresentacion(sancionDto.getFechaPresentacion().getFecha());
		}else{
			sancion.setEstadoSancion(ESTADO_INICIADO);
		}
		
		Integer id= (Integer) sancionDao.guardar(sancion);

		if(id!=null){
			sancion.setIdSancion(id);
			sancionDto.setIdSancion(id);
		}
			
		resultado = transformBeanDto(sancion);
		
		return resultado;
	}
	
	@Override
	public void cambiarEstado(String[] idsSancion, String cambioEstado, String numColegiado) {
		for (String idSancion : idsSancion ) {
			if(!idSancion.equals("")){
				Sancion sancion =  sancionDao.getSancionId(Integer.parseInt(idSancion), numColegiado);
				sancion.setEstadoSancion(Integer.parseInt(cambioEstado));
				sancionDao.actualizar(sancion);
			}
		}
	}
	
	@Override
	public ResultBean borrarSancion(int idSancion, String numColegiado) throws Exception{
		Sancion sancion = sancionDao.getSancionId(idSancion, numColegiado);
		ResultBean resultado = new ResultBean();
		resultado.setError(false);
		if (sancion!=null){
			try{
				sancion.setEstado(ESTADO_BAJA);
				sancionDao.actualizar(sancion);
			} catch(Exception e){
				throw new Exception(e);
			}
		} else {
			resultado.setError(true);
			resultado.setMensaje("No existe la sanción");
		}
		return resultado;
	}
	private Sancion transformDtoBean(SancionDto sancionDto) throws ParseException{
		Sancion sancion = new Sancion();
		if(sancionDto.getIdSancion()!=null && !sancionDto.getIdSancion().equals(""))
			sancion.setIdSancion(sancionDto.getIdSancion());
		
		if(sancionDto.getNombre()!=null && !sancionDto.getNombre().equals(""))
			sancion.setNombre(sancionDto.getNombre() );

		if(sancionDto.getApellidos()!=null && !sancionDto.getApellidos().equals(""))
			sancion.setApellidos(sancionDto.getApellidos() );
		
		if(sancionDto.getDni()!=null && !sancionDto.getDni().equals(""))
			sancion.setDni(sancionDto.getDni() );
		
		if(sancionDto.getNumColegiado()!=null && !sancionDto.getNumColegiado().equals(""))
			sancion.setNumColegiado(sancionDto.getNumColegiado() );
		
		if(sancionDto.getMotivo()!=null){
			sancion.setMotivo(sancionDto.getMotivo());
		}
		
		if(sancionDto.getFechaAlta()!=null) {
			// Mantis 13697 
			sancion.setFechaAlta(sancionDto.getFechaAlta().getFechaHora());
		}
					
		if(sancionDto.getFechaPresentacion()!=null){
			sancion.setFechaPresentacion(sancionDto.getFechaPresentacion().getFecha());
		}
		
		if(sancionDto.getEstado()!=null && !sancionDto.getEstado().equals("")) {
			sancion.setEstado(Integer.valueOf(sancionDto.getEstado()));
		}
		
		if(sancionDto.getEstadoSancion()!=null && !sancionDto.getEstadoSancion().equals(""))
			sancion.setEstadoSancion(Integer.valueOf(sancionDto.getEstadoSancion()));

		return sancion;
	}
	
	private SancionDto transformBeanDto(Sancion sancion) throws ParseException{
		SancionDto sancionDto = new SancionDto();
		if (sancion != null) {
			if(sancion.getIdSancion()!=null && !sancion.getIdSancion().equals(""))
				sancionDto.setIdSancion(sancion.getIdSancion());
			
			if(sancion.getNombre()!=null && !sancion.getNombre().equals(""))
				sancionDto.setNombre(sancion.getNombre());
			else
				sancionDto.setNombre(" ");

			if(sancion.getApellidos()!=null && !sancion.getApellidos().equals(""))
				sancionDto.setApellidos(sancion.getApellidos() );
			
			if(sancion.getDni()!=null && !sancion.getDni().equals(""))
				sancionDto.setDni(sancion.getDni());
			
			if(sancion.getNumColegiado()!=null && !sancion.getNumColegiado().equals(""))
				sancionDto.setNumColegiado(sancion.getNumColegiado());
			
			if(sancion.getMotivo()!=null){
				sancionDto.setMotivo(sancion.getMotivo());
				
				if (sancion.getMotivo().equals(Motivo.ALEGACION.getValorEnum())) {
					sancionDto.setMotivoDescripcion(ConstantesSancion.ALEGACIONES);
				} else if (sancion.getMotivo().equals(Motivo.RECURSO.getValorEnum())) {
					sancionDto.setMotivoDescripcion(ConstantesSancion.RECURSOS);
				} /*else if (sancion.getMotivo().equals(Motivo.RESGUARDO.getValorEnum())) {
					sancionDto.setMotivoDescripcion(ConstantesSancion.RESGUARDO);
				}*/
			}
			
			if(sancion.getFechaAlta()!=null)
				// Mantis 13697
				sancionDto.setFechaAlta(utilesFecha.getFechaTimeStampConDate(sancion.getFechaAlta()));
			
			if(sancion.getFechaPresentacion()!=null){
				sancionDto.setFechaPresentacion(utilesFecha.getFechaConDate(sancion.getFechaPresentacion()));
			}
			
			if(sancion.getEstado()!=null) {
				sancionDto.setEstado(sancion.getEstado().intValue());
			}
			
			if(sancion.getEstadoSancion()!=null)
				sancionDto.setEstadoSancion(sancion.getEstadoSancion().intValue());
		}
		return sancionDto;
	}
	
	@Override
	public SancionListadosMotivosDto getListado(String numColegiado, Fecha fechaListado) throws Throwable {
		Sancion sancion = new Sancion();
		
		SancionListadosMotivosDto sancionListadosMotivosDto = new SancionListadosMotivosDto();
		
		if(numColegiado!=null && numColegiado!=""){
			sancion.setNumColegiado(numColegiado);
			sancion.setFechaPresentacion(fechaListado.getFecha());
			
			List<SancionDto> listSancionDto = obtenerListadoPorMotivo(sancion, Motivo.ALEGACION.getValorEnum());
			sancionListadosMotivosDto.setListaSancionMotivoAle(listSancionDto);
			listSancionDto = obtenerListadoPorMotivo(sancion, Motivo.RECURSO.getValorEnum());
			sancionListadosMotivosDto.setListaSancionMotivoRec(listSancionDto);
			
		} else {
			sancion.setFechaPresentacion(fechaListado.getFecha());
			
			List<SancionDto> listSancionDto = obtenerListadoPorMotivo(sancion, Motivo.ALEGACION.getValorEnum());
			sancionListadosMotivosDto.setListaSancionMotivoAle(listSancionDto);
			listSancionDto = obtenerListadoPorMotivo(sancion, Motivo.RECURSO.getValorEnum());
			sancionListadosMotivosDto.setListaSancionMotivoRec(listSancionDto);
		}
		
		return sancionListadosMotivosDto;
	}
	
	private List<SancionDto> obtenerListadoPorMotivo(Sancion sancion, Integer motivo) throws Throwable {
		List<SancionDto> listSancionDto = new ArrayList<SancionDto>();
		
		sancion.setMotivo(motivo);
		List<Sancion> listSan = sancionDao.listado(sancion);
		
		for(Sancion san : listSan){
			SancionDto sancionDto = transformBeanDto(san);
			sancionDto.setMotivoDescripcion(Motivo.convertir(sancionDto.getMotivo()).getNombreEnum());
			listSancionDto.add(sancionDto);
		}
		
		return listSancionDto;
	}
	
	@Override
	public String transformToXML(SancionListadosMotivosDto sancionListadosMotivosDto) {
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
		
		XStream xStream = new XStream();
		xStream.processAnnotations(SancionDto.class);
		xml += xStream.toXML(sancionListadosMotivosDto);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");
		
		return xml;
	}

	public SancionDao getSancionDao() {
		return sancionDao;
	}

	public void setSancionDao(SancionDao sancionDao) {
		this.sancionDao = sancionDao;
	}
}

