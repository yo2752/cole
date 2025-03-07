package grupoUsuarios.DAOImpl;

import grupoUsuarios.DAO.GrupoUsuariosDAOInterfaz;
import hibernate.entities.general.Grupo;
import trafico.dao.implHibernate.GenericDaoImplHibernate;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GrupoUsuariosDaoImpl extends GenericDaoImplHibernate<Grupo> implements GrupoUsuariosDAOInterfaz {

	private static final ILoggerOegam log = LoggerOegam.getLogger(GrupoUsuariosDaoImpl.class);

	public GrupoUsuariosDaoImpl(Grupo t) {
		super(t);
	}

	public Object guardarGrupo(Grupo grupo) {
		return guardarOActualizar(grupo);
	}

	public boolean eliminarGrupo(Grupo grupo) {
		return borrar(grupo);
	}
}