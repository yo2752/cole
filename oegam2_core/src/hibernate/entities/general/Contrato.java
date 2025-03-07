package hibernate.entities.general;

import hibernate.entities.trafico.TramiteTrafico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CONTRATO database table.
 * 
 */
@Entity
public class Contrato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_CONTRATO")
	private Long idContrato;

	//bi-directional many-to-one association to TramiteTrafico
	@OneToMany(mappedBy="contrato")
	private List<TramiteTrafico> tramiteTraficos;

	@Column(name="ANAGRAMA_CONTRATO")
	private String anagramaContrato;

	private String cif;

	@Column(name="COD_POSTAL")
	private String codPostal;

	private String colegio;

	@Column(name="CORREO_ELECTRONICO")
	private String correoElectronico;

	private String escalera;

	@Column(name="ESTADO_CONTRATO")
	private BigDecimal estadoContrato;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_FIN")
	private Date fechaFin;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;

	@Column(name="ID_MUNICIPIO")
	private String idMunicipio;

	//bi-directional many-to-one association to Provincia
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROVINCIA")
	private Provincia provincia;

	@Column(name="ID_TIPO_CONTRATO")
	private BigDecimal idTipoContrato;

	@Column(name="ID_TIPO_VIA")
	private String idTipoVia;

	//bi-directional many-to-one association to JefaturaTrafico
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="JEFATURA_PROVINCIAL")
	private JefaturaTrafico jefaturaTrafico;

	@ManyToMany(mappedBy="contratos", fetch=FetchType.LAZY)
	private List<Aplicacion> aplicaciones;

	private String letra;

	private String numero;

	private String piso;

	private String puerta;

	@Column(name="RAZON_SOCIAL")
	private String razonSocial;

	private BigDecimal telefono;

	private String via;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinTable(
			name="CONTRATO_COLEGIADO",
			joinColumns={
					@JoinColumn(name="ID_CONTRATO")
			},
			inverseJoinColumns={
					@JoinColumn(name="NUM_COLEGIADO")
			}
	)
	private Colegiado colegiado;
	
	//bi-directional one-to-one association to ContratoPreferencia
	@OneToMany(mappedBy="contrato")
	private List<ContratoPreferencia> contratoPreferencia;
	
    public Contrato() {
    }

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

	public String getColegio() {
		return this.colegio;
	}

	public void setColegio(String colegio) {
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

	public String getIdMunicipio() {
		return this.idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Provincia getProvincia() {
		return this.provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public BigDecimal getIdTipoContrato() {
		return this.idTipoContrato;
	}

	public void setIdTipoContrato(BigDecimal idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}

	public String getIdTipoVia() {
		return this.idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public JefaturaTrafico getJefaturaTrafico() {
		return this.jefaturaTrafico;
	}

	public void setJefaturaTrafico(JefaturaTrafico jefaturaTrafico) {
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

	public List<TramiteTrafico> getTramiteTraficos() {
		return this.tramiteTraficos;
	}

	public void setTramiteTraficos(List<TramiteTrafico> tramiteTraficos) {
		this.tramiteTraficos = tramiteTraficos;
	}

	public Colegiado getColegiado() {
		return colegiado;
	}

	public void setColegiado(Colegiado colegiado) {
		this.colegiado = colegiado;
	}

	public List<Aplicacion> getAplicaciones() {
		return aplicaciones;
	}

	public void setAplicaciones(List<Aplicacion> aplicaciones) {
		this.aplicaciones = aplicaciones;
	}
	
	public List<ContratoPreferencia> getContratoPreferencia() {
		return this.contratoPreferencia;
	}

	public void setContratoPreferencia(List<ContratoPreferencia> contratoPreferencia) {
		this.contratoPreferencia = contratoPreferencia;
	}
	
}