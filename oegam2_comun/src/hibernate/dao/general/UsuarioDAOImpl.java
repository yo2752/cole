package hibernate.dao.general;

import java.util.List;

import org.gestoresmadrid.core.hibernate.util.HibernateBean;
import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hibernate.entities.general.Usuario;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

	@Override
	public Usuario getUsuario(Long idUsuario) {

		Usuario usuario = null;

		HibernateBean hb = HibernateUtil.createCriteria(Usuario.class);

		hb.getCriteria().add(Restrictions.eq("idUsuario", idUsuario.longValue()));

		usuario = (Usuario) hb.getCriteria().uniqueResult();

		hb.getSession().close();

		return usuario;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getListUsuarios(Usuario usuario, String... initialized) {
		Session session = null;
		List<Usuario> result = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(Usuario.class);
			if (usuario != null) {
				if (usuario.getNif() != null && !usuario.getNif().isEmpty()) {
					criteria.add(Restrictions.eq("nif", usuario.getNif()));
				}
				if (usuario.getEstadoUsuario() != null) {
					criteria.add(Restrictions.eq("estadoUsuario", usuario.getEstadoUsuario()));
				}
			}
			for (String s : initialized) {
				if (!s.contains(".")) {
					criteria.setFetchMode(s, FetchMode.JOIN);
				}
			}
			result = criteria.list();
			for (String s : initialized) {
				if (s.contains(".")) {
					for (String sToken : s.split("\\.")) {
						for (Usuario u : result) {
							HibernateUtil.initialize(u, sToken);
						}
					}
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}
}