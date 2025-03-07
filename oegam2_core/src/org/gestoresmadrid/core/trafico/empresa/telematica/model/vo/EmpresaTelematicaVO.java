package org.gestoresmadrid.core.trafico.empresa.telematica.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;

@Entity
@Table(name = "EMPRESA_TELEMATICA_MATR")
public class EmpresaTelematicaVO implements Serializable {
	
	

	private static final long serialVersionUID = -1597574563103980531L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ID_EMPRESA_TELEMATICA_SEQ_GEN")
	@SequenceGenerator(name = "ID_EMPRESA_TELEMATICA_SEQ_GEN", sequenceName = "ID_EMPRESA_TELEMATICA")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;
	
	@Column(name = "NUM_CONTRATO")
	private Long idContrato;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "NUM_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contratoVO;
	
	@Column(name = "MUNICIPIO")
	private String municipio;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumns({@JoinColumn(name = "PROVINCIA", referencedColumnName = "ID_PROVINCIA", insertable = false, updatable = false), 
		  @JoinColumn( name = "MUNICIPIO", referencedColumnName = "ID_MUNICIPIO", insertable = false, updatable = false)
	})
	private MunicipioVO municipioVO;
	
	@Column(name = "PROVINCIA")
	private String provincia;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "PROVINCIA", insertable = false, updatable = false)
	private ProvinciaVO provinciaVO;
	
	@Column(name = "CODIGO_POSTAL")
	private String codigoPostal;
	
	@Column(name = "EMPRESA")
	private String empresa;
	
	@Column(name = "CIF_EMPRESA")
	private String cifEmpresa;
	
	@Column(name = "ESTADO")
	private Long estado;
	
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;
	
	@Column(name = "FECHA_BAJA")
	private Date fechaBaja;
	
	@Column(name = "ID_USUARIO_MOD")
	private Long idUsuarioMod;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}


	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCifEmpresa() {
		return cifEmpresa;
	}

	public void setCifEmpresa(String cifEmpresa) {
		this.cifEmpresa = cifEmpresa;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Long getIdUsuarioMod() {
		return idUsuarioMod;
	}

	public void setIdUsuarioMod(Long idUsuarioMod) {
		this.idUsuarioMod = idUsuarioMod;
	}





	public ContratoVO getContratoVO() {
		return contratoVO;
	}

	public void setContratoVO(ContratoVO contratoVO) {
		this.contratoVO = contratoVO;
	}

	public MunicipioVO getMunicipioVO() {
		return municipioVO;
	}

	public void setMunicipioVO(MunicipioVO municipioVO) {
		this.municipioVO = municipioVO;
	}

	public ProvinciaVO getProvinciaVO() {
		return provinciaVO;
	}

	public void setProvinciaVO(ProvinciaVO provinciaVO) {
		this.provinciaVO = provinciaVO;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	

}
