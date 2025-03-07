package grupoUsuarios.beans.paginacion;

import general.beans.BeanCriteriosSkeletonPaginatedList;

public class GruposUsuariosBean implements BeanCriteriosSkeletonPaginatedList {
	private String idGrupo;
	private String descGrupo;
	private String correoElectronico;
	private String tipo;

	@Override
	public BeanCriteriosSkeletonPaginatedList iniciarBean() {
		return this;
	}

	public String getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}
	public String getDescGrupo() {
		return descGrupo;
	}
	public void setDescGrupo(String descGrupo) {
		this.descGrupo = descGrupo;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}