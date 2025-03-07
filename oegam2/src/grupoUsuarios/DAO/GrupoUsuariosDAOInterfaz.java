package grupoUsuarios.DAO;

import hibernate.entities.general.Grupo;
import trafico.dao.GenericDao;

public interface GrupoUsuariosDAOInterfaz extends GenericDao<Grupo> {

	public Object guardarGrupo(Grupo grupo);
	public boolean eliminarGrupo(Grupo grupo);
}