package general.accionTramite.modelo.interfaz;

import general.accionTramite.dto.AccionTramiteDto;
import hibernate.entities.general.AccionTramite;

public interface ModeloAccionTramiteIntz {

	
	public AccionTramite convertirDtoToEntity (AccionTramiteDto accionTramite);
	public AccionTramiteDto convertirEntityToDto (AccionTramite accionTramite);
	
	public boolean crearAccion(AccionTramiteDto accion);
	public boolean crearAcciones(AccionTramiteDto accion, String [] numsExpedientes);
	
}
