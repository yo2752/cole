package grupoUsuarios.acciones;

import general.acciones.AbstractFactoryPaginatedList;
import general.acciones.PaginatedListActionSkeleton;
import grupoUsuarios.DTO.GrupoUsuariosDTO;
import grupoUsuarios.factoria.FactoriaGrupoUsuarios;
import grupoUsuarios.modelo.Impl.ModeloGrupoUsuariosImpl;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.core.administracion.model.enumerados.TipoRecargaCacheEnum;
import org.gestoresmadrid.oegam2comun.administracion.service.ServicioRecargaCache;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GrupoUsuariosAction extends PaginatedListActionSkeleton implements SessionAware {

	private static final long serialVersionUID = -8321716375177160124L;
	private static final String COLUMDEFECT = "descGrupo";
	private static final ILoggerOegam log = LoggerOegam.getLogger(GrupoUsuariosAction.class);

	private String idGrupo;
	private GrupoUsuariosDTO grupoDto;
	private ModeloGrupoUsuariosImpl modeloImpl;

	@Autowired
	private ServicioRecargaCache servicioRecargaCache;

	public void obtenerListaGrupos() {
	}

	public String guardarModificarGrupo() {
		recargarPaginatedList();
		Object result = getModeloImpl().guardarOModificarGrupo(grupoDto);
		if (result == null) {
			addActionError("Se ha producido un error al guardar el grupo.");
		} else {
			addActionMessage("El grupo " + grupoDto.getIdGrupo() + " se guardó correctamente");
			servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.DATOS_SENSIBLES);
			addActionMessage("Creada peticion para refresco de cache.");
		}
		return SUCCESS;
	}

	public String eliminarGrupo() {
		recargarPaginatedList();
		boolean result = getModeloImpl().eliminarGrupo(idGrupo);
		if (result) {
			addActionMessage("El grupo " + grupoDto.getIdGrupo() + " se eliminó correctamente");
		} else {
			addActionError("Se ha producido un error al eliminar el grupo.");
		}
		return SUCCESS;
	}
	@Override
	public AbstractFactoryPaginatedList getFactory() {
		return new FactoriaGrupoUsuarios(); 
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
		return "grupoBean";
	}

	@Override
	public String getCriteriosSession() {
		return "grupoSession";
	}

	@Override
	protected void cargaRestricciones() {
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	public String getResultadosPorPaginaSession() {
		return "resultadosPorPaginaGrupos";
	}

	public GrupoUsuariosDTO getGrupoDto() {
		return grupoDto;
	}

	public void setGrupoDto(GrupoUsuariosDTO grupoDto) {
		this.grupoDto = grupoDto;
	}

	public ModeloGrupoUsuariosImpl getModeloImpl() {
		if (modeloImpl == null) {
			modeloImpl = new ModeloGrupoUsuariosImpl();
		}
		return modeloImpl;
	}

	public void setModeloImpl(ModeloGrupoUsuariosImpl modeloImpl) {
		this.modeloImpl = modeloImpl;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

}