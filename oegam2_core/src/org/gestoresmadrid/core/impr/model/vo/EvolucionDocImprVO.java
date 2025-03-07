package org.gestoresmadrid.core.impr.model.vo;

import java.io.Serializable;
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
@Table(name="EVOLUCION_DOC_IMPR")
public class EvolucionDocImprVO implements Serializable{

	private static final long serialVersionUID = 3282788801354626675L;

	@Id
	@SequenceGenerator(name = "evolucion_doc_impr_secuencia", sequenceName = "ID_EVOL_DOC_IMPR_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "evolucion_doc_impr_secuencia")
	@Column(name="ID")
	Long id;
	
	@Column(name="TIPO_DOCUMENTO")
	String tipoDocumento;
	
	@Column(name="OPERACION")
	String operacion;
	
	@Column(name="FECHA_CAMBIO")
	Date fechaCambio;
	
	@Column(name="ESTADO_ANT")
	String estadoAnterior;
	
	@Column(name="ESTADO_NUEVO")
	String estadoNuevo;
	
	@Column(name="ID_USUARIO")
	Long idUsuario;
	
	@Column(name="DOC_ID")
	Long docId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	UsuarioVO usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOC_ID",referencedColumnName = "ID", insertable=false,updatable=false)
	DocImprVO docImpr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public DocImprVO getDocImpr() {
		return docImpr;
	}

	public void setDocImpr(DocImprVO docImpr) {
		this.docImpr = docImpr;
	}

}
