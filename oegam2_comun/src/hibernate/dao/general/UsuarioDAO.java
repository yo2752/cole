package hibernate.dao.general;

import java.util.List;

import hibernate.entities.general.Usuario;

public interface UsuarioDAO {

	Usuario getUsuario(Long idUsuario);

	List<Usuario> getListUsuarios(Usuario usuario, String... initialized);
}
