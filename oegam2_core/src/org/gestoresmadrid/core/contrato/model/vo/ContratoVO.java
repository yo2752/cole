package org.gestoresmadrid.core.contrato.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.docbase.model.vo.DocumentoBaseVO;
import org.gestoresmadrid.core.general.model.vo.AplicacionVO;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.general.model.vo.ColegioVO;
import org.gestoresmadrid.core.general.model.vo.ContratoPreferenciaVO;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.utilidades.listas.ListsOperator;

/**
 * The persistent class for the CONTRATO database table.
 */
@Entity
@Table(name = "CONTRATO")
public class ContratoVO implements Serializable {

	private static final long serialVersionUID = -6388189538722007343L;

	@Id
	@SequenceGenerator(name = "contratos_secuencia", sequenceName = "CONTRATO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "contratos_secuencia")
	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@Column(name = "ANAGRAMA_CONTRATO")
	private String anagramaContrato;

	private String cif;

	@Column(name = "COD_POSTAL")
	private String codPostal;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COLEGIO")
	private ColegioVO colegio;

	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;

	private String escalera;

	@Column(name = "ESTADO_CONTRATO")
	private BigDecimal estadoContrato;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;

	@Column(name = "ID_PROVINCIA")
	private String idProvincia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROVINCIA", referencedColumnName = "ID_PROVINCIA", insertable = false, updatable = false)
	private ProvinciaVO provincia;

	@Column(name = "ID_MUNICIPIO")
	private String idMunicipio;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ID_PROVINCIA", referencedColumnName = "ID_PROVINCIA", insertable = false, updatable = false),
			@JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID_MUNICIPIO", insertable = false, updatable = false) })
	private MunicipioVO municipio;

	@Column(name = "ID_TIPO_CONTRATO")
	private BigDecimal idTipoContrato;

	@Column(name = "ID_TIPO_VIA")
	private String idTipoVia;

	// bi-directional many-to-one association to JefaturaTrafico
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JEFATURA_PROVINCIAL")
	private JefaturaTraficoVO jefaturaTrafico;

	@ManyToMany(mappedBy = "contratos")
	private Set<AplicacionVO> aplicaciones;

	private String letra;

	private String numero;

	private String piso;

	private String puerta;

	@Column(name = "RAZON_SOCIAL")
	private String razonSocial;

	private BigDecimal telefono;

	private String via;

	@Column(name = "OBSERVACIONES")
	private String observaciones;
	
	@Column(name = "MOBILE_GEST")
	private String mobileG;

	@ManyToOne
	@JoinTable(name = "CONTRATO_COLEGIADO", joinColumns = { @JoinColumn(name = "ID_CONTRATO") }, inverseJoinColumns = { @JoinColumn(name = "NUM_COLEGIADO") })
	private ColegiadoVO colegiado;

	// bi-directional one-to-one association to ContratoPreferencia
	@OneToMany(mappedBy = "contrato")
	private Set<ContratoPreferenciaVO> contratoPreferencia;

	/* INICIO Mantis 0011883: Generación Docs Base Oegam - Parte Física (Impresión PDF) */
	// bi-directional many-to-one association to DocBase
	@OneToMany(mappedBy = "contrato")
	private List<DocumentoBaseVO> documentosBase;
	
	/* FIN Mantis 0011883 */

	// bi-directional One-to-many association to Correo_Contrato_Tramite
	@OneToMany(mappedBy = "contrato")
	private Set<CorreoContratoTramiteVO> correosTramites;

	public ContratoVO() {}

	public Long getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getAnagramaContrato() {
		return this.anagramaContrato;
	}

	public void setAnagramaContrato(String anagramaContrato) {
		this.anagramaContrato = anagramaContrato;
	}

	public String getCif() {
		return this.cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCodPostal() {
		return this.codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public ColegioVO getColegio() {
		return this.colegio;
	}

	public void setColegio(ColegioVO colegio) {
		this.colegio = colegio;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getEscalera() {
		return this.escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public BigDecimal getEstadoContrato() {
		return this.estadoContrato;
	}

	public void setEstadoContrato(BigDecimal estadoContrato) {
		this.estadoContrato = estadoContrato;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public BigDecimal getIdTipoContrato() {
		return this.idTipoContrato;
	}

	public void setIdTipoContrato(BigDecimal idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getIdTipoVia() {
		return idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public JefaturaTraficoVO getJefaturaTrafico() {
		return this.jefaturaTrafico;
	}

	public void setJefaturaTrafico(JefaturaTraficoVO jefaturaTrafico) {
		this.jefaturaTrafico = jefaturaTrafico;
	}

	public String getLetra() {
		return this.letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPiso() {
		return this.piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getPuerta() {
		return this.puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public BigDecimal getTelefono() {
		return this.telefono;
	}

	public void setTelefono(BigDecimal telefono) {
		this.telefono = telefono;
	}

	public String getVia() {
		return this.via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public ColegiadoVO getColegiado() {
		return colegiado;
	}

	public void setColegiado(ColegiadoVO colegiado) {
		this.colegiado = colegiado;
	}

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

	public Set<AplicacionVO> getAplicaciones() {
		return aplicaciones;
	}

	public List<AplicacionVO> getAplicacionesAsList() {
		// Map from Set to List
		List<AplicacionVO> lista;
		if (aplicaciones != null) {
			lista = new ArrayList<AplicacionVO>(aplicaciones);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}

	public AplicacionVO getFirstElementAplicacion() {
		ListsOperator<AplicacionVO> listOperator = new ListsOperator<AplicacionVO>();
		return listOperator.getFirstElement(getAplicaciones());
	}

	public void setAplicaciones(Set<AplicacionVO> aplicaciones) {
		this.aplicaciones = aplicaciones;
	}

	public void setAplicaciones(List<AplicacionVO> aplicaciones) {
		if (aplicaciones == null) {
			this.aplicaciones = null;
		} else {
			// Map from List to Set
			this.aplicaciones = new HashSet<AplicacionVO>(aplicaciones);
		}
	}

	public Set<ContratoPreferenciaVO> getContratoPreferencia() {
		return contratoPreferencia;
	}

	public List<ContratoPreferenciaVO> getContratoPreferenciaAsList() {
		// Map from Set to List
		List<ContratoPreferenciaVO> lista;
		if (contratoPreferencia != null) {
			lista = new ArrayList<ContratoPreferenciaVO>(contratoPreferencia);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}

	public ContratoPreferenciaVO getFirstElementContratoPreferencia() {
		ListsOperator<ContratoPreferenciaVO> listOperator = new ListsOperator<ContratoPreferenciaVO>();
		return listOperator.getFirstElement(getContratoPreferencia());
	}

	public void setContratoPreferencia(Set<ContratoPreferenciaVO> contratoPreferencia) {
		this.contratoPreferencia = contratoPreferencia;
	}

	public void setContratoPreferencia(List<ContratoPreferenciaVO> contratoPreferencia) {
		if (contratoPreferencia == null) {
			this.contratoPreferencia = null;
		} else {
			// Map from List to Set
			this.contratoPreferencia = new HashSet<ContratoPreferenciaVO>(contratoPreferencia);
		}
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/* INICIO Mantis 0011883: Generación Docs Base Oegam - Parte Física (Impresión PDF) */
	public List<DocumentoBaseVO> getDocumentosBase() {
		return documentosBase;
	}

	public void setDocumentosBase(List<DocumentoBaseVO> documentosBase) {
		this.documentosBase = documentosBase;
	}
	/* FIN Mantis 0011883 */

	public MunicipioVO getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioVO municipio) {
		this.municipio = municipio;
	}

	public Set<CorreoContratoTramiteVO> getCorreosTramites() {
		return correosTramites;
	}

	public void setCorreosTramites(Set<CorreoContratoTramiteVO> correosTramites) {
		this.correosTramites = correosTramites;
	}

	public String getMobileG() {
		return mobileG;
	}

	public void setMobileG(String mobileG) {
		this.mobileG = mobileG;
	}


}