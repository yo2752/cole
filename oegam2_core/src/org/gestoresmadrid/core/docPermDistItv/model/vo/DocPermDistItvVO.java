package org.gestoresmadrid.core.docPermDistItv.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

@Entity
@Table(name="DOC_PERM_DIST_ITV")
public class DocPermDistItvVO implements Serializable{
	
	
	private static final long serialVersionUID = 872183853751455139L;

	@Id
	@SequenceGenerator(name = "permDisItv_secuencia", sequenceName = "DOC_PERM_DIST_ITV_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "permDisItv_secuencia")
	@Column(name="ID")
	private Long idDocPermDistItv;
	
	@Column(name="DOC_PERMDSTVEITV_ID")
	private String docIdPerm;
	
	@Column(name = "ID_USUARIO")
	private Long idUsuario;
	
	@Column(name = "ID_CONTRATO")
	private Long idContrato;
	
	@Column(name = "ESTADO")
	private BigDecimal estado;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name= "TIPO_DISTINTIVO")
	private String tipoDistintivo;
	
	@Column(name= "JEFATURA")
	private String jefatura;
	
	@Column(name="NUM_DESCARGA")
	private Long numDescarga;
	
	@Column(name="FECHA_ALTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;
	
	@Column(name="FECHA_IMPRESION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaImpresion;
	
	@Column(name="FECHA_MODIFICACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;
	
	@Column(name="DEMANDA")
	private String esDemanda;
	
	@Column(name="REF_DOCUMENTO")
	private String refDocumento;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",referencedColumnName="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO",referencedColumnName="ID_CONTRATO",insertable=false,updatable=false)
	private ContratoVO contrato;
	
	@OneToMany(mappedBy="docPermisoVO",fetch= FetchType.LAZY)
	private Set<TramiteTraficoVO> listaTramitesPermiso;
	
	@OneToMany(mappedBy="docDistintivoVO",fetch= FetchType.LAZY)
	private Set<TramiteTrafMatrVO> listaTramitesDistintivo;
	
	@OneToMany(mappedBy="docFichaTecnicaVO",fetch= FetchType.LAZY)
	private Set<TramiteTraficoVO> listaTramitesEitv;
	
	@OneToMany(mappedBy="docDistintivoVO",fetch= FetchType.LAZY)
	private Set<VehNoMatOegamVO> listaDuplicadoDistintivos;
	
	public List<TramiteTraficoVO> getListaTramitesPermisoAsList() {
		List<TramiteTraficoVO> lista;
		if (listaTramitesPermiso != null) {
			// Map from Set to List
			lista = new ArrayList<TramiteTraficoVO>(listaTramitesPermiso);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	public List<TramiteTrafMatrVO> getListaTramitesDistintivoAsList() {
		List<TramiteTrafMatrVO> lista;
		if (listaTramitesDistintivo != null) {
			// Map from Set to List
			lista = new ArrayList<TramiteTrafMatrVO>(listaTramitesDistintivo);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	public List<VehNoMatOegamVO> getListaDuplicadosDstvAsList() {
		List<VehNoMatOegamVO> lista;
		if (listaDuplicadoDistintivos != null) {
			// Map from Set to List
			lista = new ArrayList<VehNoMatOegamVO>(listaDuplicadoDistintivos);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	
	public List<TramiteTraficoVO> getListaTramitesEitvAsList() {
		List<TramiteTraficoVO> lista;
		if (listaTramitesEitv != null) {
			// Map from Set to List
			lista = new ArrayList<TramiteTraficoVO>(listaTramitesEitv);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(Date fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public Long getIdDocPermDistItv() {
		return idDocPermDistItv;
	}

	public void setIdDocPermDistItv(Long idDocPermDistItv) {
		this.idDocPermDistItv = idDocPermDistItv;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDocIdPerm() {
		return docIdPerm;
	}

	public void setDocIdPerm(String docIdPerm) {
		this.docIdPerm = docIdPerm;
	}

	public Set<TramiteTraficoVO> getListaTramitesPermiso() {
		return listaTramitesPermiso;
	}

	public void setListaTramitesPermiso(Set<TramiteTraficoVO> listaTramitesPermiso) {
		this.listaTramitesPermiso = listaTramitesPermiso;
	}

	public Set<TramiteTrafMatrVO> getListaTramitesDistintivo() {
		return listaTramitesDistintivo;
	}

	public void setListaTramitesDistintivo(Set<TramiteTrafMatrVO> listaTramitesDistintivo) {
		this.listaTramitesDistintivo = listaTramitesDistintivo;
	}

	public Set<TramiteTraficoVO> getListaTramitesEitv() {
		return listaTramitesEitv;
	}

	public void setListaTramitesEitv(Set<TramiteTraficoVO> listaTramitesEitv) {
		this.listaTramitesEitv = listaTramitesEitv;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public Long getNumDescarga() {
		return numDescarga;
	}

	public void setNumDescarga(Long numDescarga) {
		this.numDescarga = numDescarga;
	}

	public String getEsDemanda() {
		return esDemanda;
	}

	public void setEsDemanda(String esDemanda) {
		this.esDemanda = esDemanda;
	}

	public Set<VehNoMatOegamVO> getListaDuplicadoDistintivos() {
		return listaDuplicadoDistintivos;
	}

	public void setListaDuplicadoDistintivos(Set<VehNoMatOegamVO> listaDuplicadoDistintivos) {
		this.listaDuplicadoDistintivos = listaDuplicadoDistintivos;
	}

	public String getRefDocumento() {
		return refDocumento;
	}

	public void setRefDocumento(String refDocumento) {
		this.refDocumento = refDocumento;
	}
}