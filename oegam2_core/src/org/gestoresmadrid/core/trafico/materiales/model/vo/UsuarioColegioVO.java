package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "GSM_USUARIO_COLEGIO")
public class UsuarioColegioVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4391973304784486383L;
	
	@Id
	@Column(name = "USUARIO_ID")
	private Long   usuarioId;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ROL")
	private Long   rol;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COLEGIO_ID")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private ColegioConsejoVO colegioVO;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELEGACION_ID")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private DelegacionVO delegacionVO;
	
	
	@Column(name = "FECHA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date   fecha;
	
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getRol() {
		return rol;
	}
	public void setRol(Long rol) {
		this.rol = rol;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public ColegioConsejoVO getColegioVO() {
		return colegioVO;
	}
	public void setColegioVO(ColegioConsejoVO colegioVO) {
		this.colegioVO = colegioVO;
	}
	public DelegacionVO getDelegacionVO() {
		return delegacionVO;
	}
	public void setDelegacionVO(DelegacionVO delegacionVO) {
		this.delegacionVO = delegacionVO;
	}
}
