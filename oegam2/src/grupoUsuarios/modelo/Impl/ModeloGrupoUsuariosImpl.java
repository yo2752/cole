package grupoUsuarios.modelo.Impl;

import grupoUsuarios.DAO.GrupoUsuariosDAOInterfaz;
import grupoUsuarios.DAOImpl.GrupoUsuariosDaoImpl;
import grupoUsuarios.DTO.GrupoUsuariosDTO;
import hibernate.entities.general.Grupo;

public class ModeloGrupoUsuariosImpl {

	private GrupoUsuariosDAOInterfaz grupoUsuarios;

	public ModeloGrupoUsuariosImpl() {
		grupoUsuarios = new GrupoUsuariosDaoImpl(new Grupo());
	}

	public Object guardarOModificarGrupo(GrupoUsuariosDTO grupoUsuariosDTO) {
		Grupo grupoEntity = convertirDTOaDAO(grupoUsuariosDTO);
		return grupoUsuarios.guardarGrupo(grupoEntity);
	}

	public boolean eliminarGrupo(String idGrupo) {
		Grupo grupoEntity = new Grupo();
		grupoEntity.setIdGrupo(idGrupo);
		return grupoUsuarios.eliminarGrupo(grupoEntity);
	}

	private Grupo convertirDTOaDAO(GrupoUsuariosDTO grupoUsuariosDTO) {
		Grupo grupoEntity = new Grupo();
		grupoEntity.setCorreoElectronico(grupoUsuariosDTO.getCorreoElectronico());
		grupoEntity.setDescGrupo(grupoUsuariosDTO.getDescripcionGrupo());
		grupoEntity.setTipo(grupoUsuariosDTO.getTipo());
		grupoEntity.setIdGrupo(grupoUsuariosDTO.getIdGrupo());

		return grupoEntity;
	}
}