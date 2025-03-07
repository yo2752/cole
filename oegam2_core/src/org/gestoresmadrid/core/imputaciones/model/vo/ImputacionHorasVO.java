package org.gestoresmadrid.core.imputaciones.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;


@Entity
@Table(name="IMPUTACION_HORAS")
public class ImputacionHorasVO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "imputacion_horas_secuencia", sequenceName = "ID_IMPUTACION_HORAS_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "imputacion_horas_secuencia")
	@Column(name="ID_IMPUTACION_HORAS")
	private Long idImputacionHoras;
	
	@Column(name="FECHA")
	private Date fechaImputacion;
	
	@Column(name="HORAS_IMPUTADAS")
	private BigDecimal horas;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;
	
	@Column(name="DESC_IMPUTACION")
	private String descImputacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_IMPUTACION")
	private TipoImputacionVO tipoImputacionVO;

	public Long getIdImputacionHoras() {
		return idImputacionHoras;
	}

	public void setIdImputacionHoras(Long idImputacionHoras) {
		this.idImputacionHoras = idImputacionHoras;
	}

	public Date getFechaImputacion() {
		return fechaImputacion;
	}

	public void setFechaImputacion(Date fechaImputacion) {
		this.fechaImputacion = fechaImputacion;
	}

	public BigDecimal getHoras() {
		return horas;
	}

	public void setHoras(BigDecimal horas) {
		this.horas = horas;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public String getDescImputacion() {
		return descImputacion;
	}

	public void setDescImputacion(String descImputacion) {
		this.descImputacion = descImputacion;
	}

	public TipoImputacionVO getTipoImputacionVO() {
		return tipoImputacionVO;
	}

	public void setTipoImputacionVO(TipoImputacionVO tipoImputacionVO) {
		this.tipoImputacionVO = tipoImputacionVO;
	}
	
	
}
