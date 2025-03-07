package org.gestoresmadrid.core.evolucionDatosBancariosFavoritos.modelo.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name="EVOLUCION_DATOS_BANCARIOS")
public class EvolucionDatosBancariosFavoritosVO implements Serializable{

	private static final long serialVersionUID = 350842600072272612L;

	@EmbeddedId
	private EvolucionDatosBancariosFavoritosPK id;
	
	@Column(name = "TIPO_ACTUACION")
	private String tipoActuacion;
	
	@Column(name = "CAMPOS_MODIFICACION")
	private String camposModificacion;
	
	@Column(name="ESTADO_ANT")
	private BigDecimal estadoAnt;
	
	@Column(name="ESTADO_NUEVO")
	private BigDecimal estadoNuevo;
	
	@Column(name="NIF_ANT")
	private String nifAnt;
	
	@Column(name="NIF_NUEVO")
	private String nifNuevo;
	
	@Column(name="FORMA_PAGO_ANT")
	private BigDecimal formaPagoAnt;
	
	@Column(name="FORMA_PAGO_NUEVA")
	private BigDecimal formaPagoNueva;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;

	public EvolucionDatosBancariosFavoritosPK getId() {
		return id;
	}

	public void setId(EvolucionDatosBancariosFavoritosPK id) {
		this.id = id;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getCamposModificacion() {
		return camposModificacion;
	}

	public void setCamposModificacion(String camposModificacion) {
		this.camposModificacion = camposModificacion;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getEstadoAnt() {
		return estadoAnt;
	}

	public void setEstadoAnt(BigDecimal estadoAnt) {
		this.estadoAnt = estadoAnt;
	}

	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public String getNifAnt() {
		return nifAnt;
	}

	public void setNifAnt(String nifAnt) {
		this.nifAnt = nifAnt;
	}

	public String getNifNuevo() {
		return nifNuevo;
	}

	public void setNifNuevo(String nifNuevo) {
		this.nifNuevo = nifNuevo;
	}

	public BigDecimal getFormaPagoAnt() {
		return formaPagoAnt;
	}

	public void setFormaPagoAnt(BigDecimal formaPagoAnt) {
		this.formaPagoAnt = formaPagoAnt;
	}

	public BigDecimal getFormaPagoNueva() {
		return formaPagoNueva;
	}

	public void setFormaPagoNueva(BigDecimal formaPagoNueva) {
		this.formaPagoNueva = formaPagoNueva;
	}
	
}
