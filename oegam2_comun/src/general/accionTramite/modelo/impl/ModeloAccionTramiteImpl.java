package general.accionTramite.modelo.impl;

import java.math.BigDecimal;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import general.accionTramite.dao.AccionTramiteDAO;
import general.accionTramite.dto.AccionTramiteDto;
import general.accionTramite.modelo.interfaz.ModeloAccionTramiteIntz;
import hibernate.entities.general.AccionTramite;
import hibernate.entities.general.AccionTramitePK;
import hibernate.entities.general.Usuario;

public class ModeloAccionTramiteImpl implements ModeloAccionTramiteIntz{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloAccionTramiteImpl.class);
	
	@Override
	public AccionTramite convertirDtoToEntity(AccionTramiteDto accionTramite) {
		AccionTramite accion = new AccionTramite();
		accion.setId(new AccionTramitePK());
		accion.getId().setAccion(accionTramite.getAccion());
		accion.setFechaFin(accionTramite.getFechaFin());
		accion.getId().setFechaInicio(accionTramite.getFechaInicio());
		if (accionTramite.getIdUsuario()!=null){
			Usuario u = new Usuario();
			u.setIdUsuario(accionTramite.getIdUsuario().longValue());
			accion.setUsuario(u);
		}
		accion.getId().setNumExpediente(accionTramite.getNumExpediente());
		accion.setRespuesta(accionTramite.getRespuesta());
		return accion;
	}

	@Override
	public AccionTramiteDto convertirEntityToDto(AccionTramite accionTramite) {
		AccionTramiteDto accion = new AccionTramiteDto();
		if (accionTramite.getId()!=null){
			accion.setAccion(accionTramite.getId().getAccion());
			accion.setFechaInicio(accionTramite.getId().getFechaInicio());
			accion.setNumExpediente(accionTramite.getId().getNumExpediente());
		}
		accion.setFechaFin(accionTramite.getFechaFin());
		if (accionTramite.getUsuario()!=null){
			accion.setIdUsuario(new BigDecimal(accionTramite.getUsuario().getIdUsuario()));
		}
		accion.setRespuesta(accionTramite.getRespuesta());
		return accion;
	}

	@Override
	public boolean crearAccion(AccionTramiteDto accion) {
		AccionTramite accionTramite = convertirDtoToEntity(accion);
		return new AccionTramiteDAO().guardar(accionTramite)!=null;
	}

	@Override
	public boolean crearAcciones(AccionTramiteDto accion, String[] numsExpedientes) {
		AccionTramite accionTramite = convertirDtoToEntity(accion);
		AccionTramiteDAO dao = new AccionTramiteDAO();
		try{
			if (numsExpedientes!=null){
				for (String expediente : numsExpedientes){
						accionTramite.getId().setNumExpediente(Long.parseLong(expediente));
					dao.guardar(accionTramite);
				}
			}
		} catch (NumberFormatException e){
			log.error("No se han podido guardar las acciones");
			return false;
		}
		return true;
	}
	
	

}
