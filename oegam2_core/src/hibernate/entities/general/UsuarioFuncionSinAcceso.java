package hibernate.entities.general;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the USUARIO_FUNCION_SIN_ACCESO database table.
 * 
 */
@Entity
@Table(name="USUARIO_FUNCION_SIN_ACCESO")
public class UsuarioFuncionSinAcceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuarioFuncionSinAccesoPK id;

//	//bi-directional many-to-one association to Funcion
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumns({
//		@JoinColumn(name="CODIGO_APLICACION", referencedColumnName="CODIGO_APLICACION"),
//		@JoinColumn(name="CODIGO_FUNCION", referencedColumnName="CODIGO_FUNCION")
//		})
//	private Funcion funcion;

	@Column(name="ID_CONTRATO", insertable=false, updatable=false)
	private long idContrato;

	@Column(name="ID_USUARIO", insertable=false, updatable=false)
	private long idUsuario;

	public UsuarioFuncionSinAcceso() {
	}

	public UsuarioFuncionSinAccesoPK getId() {
		return this.id;
	}

	public void setId(UsuarioFuncionSinAccesoPK id) {
		this.id = id;
	}

	public long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(long idContrato) {
		this.idContrato = idContrato;
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

//	public Funcion getFuncion() {
//		return this.funcion;
//	}
//
//	public void setFuncion(Funcion funcion) {
//		this.funcion = funcion;
//	}

}