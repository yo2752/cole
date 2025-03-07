package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the BUQUE_REGISTRO database table.
 * 
 */
@Entity
@Table(name="BUQUE_REGISTRO")
public class BuqueRegistroVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6725109362293787786L;

	@Id
	@SequenceGenerator(name = "buque_registro_secuencia", sequenceName = "BUQUE_REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "buque_registro_secuencia")
	@Column(name="ID_BUQUE")
	private long idBuque;

	@Column(name="ANIO_CONSTRUCCION")
	private String anioConstruccion;

	@Column(name="ANIO_INSCRIPCION")
	private String anioInscripcion;

	private String astillero;

	private String bruto;

	@Column(name="CALADO_MAXIMO")
	private String caladoMaximo;

	@Column(name="CAPITANIA_MARITIMA")
	private String capitaniaMaritima;

	@Column(name="CARGA_MAXIMA")
	private String cargaMaxima;

	private String desplazamiento;

	@Column(name="DISTRITO_MARITIMO")
	private String distritoMaritimo;

	private String eslora;

	private String estado;

	@Column(name="FOLIO_INSCRIPCION")
	private String folioInscripcion;

	private String manga;

	private String marca;

	private String material;

	@Column(name="MATRICULA_FLUVIAL")
	private String matriculaFluvial;

	private String modelo;

	private String nib;

	@Column(name="NOMBRE_BUQUE")
	private String nombreBuque;

	@Column(name="NUM_LISTA")
	private String numLista;

	@Column(name="NUM_SERIE")
	private String numSerie;

	private String omi;

	@Column(name="OTROS_DATOS")
	private String otrosDatos;

	private String pabellon;

	private String pais;

	@Column(name="PESO_MUERTO")
	private String pesoMuerto;

	@Column(name="PROVINCIA_MARITIMA")
	private String provinciaMaritima;

	private String puntal;

	@Column(name="REGISTRADO_BRUTO")
	private String registradoBruto;

	@Column(name="REGISTRADO_NETO")
	private String registradoNeto;

	@Column(name="SUB_TIPO_EMBARCACION")
	private String subTipoEmbarcacion;

	@Column(name="TIPO_EMBARCACION")
	private String tipoEmbarcacion;
	
	private String neto;


	@Column(name = "ID_PROPIEDAD")
	private BigDecimal idPropiedad;
	
	@OneToOne
	@JoinColumn(name="ID_PROPIEDAD", insertable=false, updatable=false)
	private PropiedadVO propiedad;

	//bi-directional many-to-one association to MotorBuque
	@OneToMany(mappedBy="buqueRegistro")
	private Set<MotorBuqueVO> motorBuques;

	public BuqueRegistroVO() {
	}

	public long getIdBuque() {
		return this.idBuque;
	}

	public void setIdBuque(long idBuque) {
		this.idBuque = idBuque;
	}

	public String getAnioConstruccion() {
		return this.anioConstruccion;
	}

	public void setAnioConstruccion(String anioConstruccion) {
		this.anioConstruccion = anioConstruccion;
	}

	public String getAnioInscripcion() {
		return this.anioInscripcion;
	}

	public void setAnioInscripcion(String anioInscripcion) {
		this.anioInscripcion = anioInscripcion;
	}

	public String getAstillero() {
		return this.astillero;
	}

	public void setAstillero(String astillero) {
		this.astillero = astillero;
	}

	public String getBruto() {
		return this.bruto;
	}

	public void setBruto(String bruto) {
		this.bruto = bruto;
	}

	public String getCaladoMaximo() {
		return this.caladoMaximo;
	}

	public void setCaladoMaximo(String caladoMaximo) {
		this.caladoMaximo = caladoMaximo;
	}

	public String getCapitaniaMaritima() {
		return this.capitaniaMaritima;
	}

	public void setCapitaniaMaritima(String capitaniaMaritima) {
		this.capitaniaMaritima = capitaniaMaritima;
	}

	public String getCargaMaxima() {
		return this.cargaMaxima;
	}

	public void setCargaMaxima(String cargaMaxima) {
		this.cargaMaxima = cargaMaxima;
	}

	public String getDesplazamiento() {
		return this.desplazamiento;
	}

	public void setDesplazamiento(String desplazamiento) {
		this.desplazamiento = desplazamiento;
	}

	public String getDistritoMaritimo() {
		return this.distritoMaritimo;
	}

	public void setDistritoMaritimo(String distritoMaritimo) {
		this.distritoMaritimo = distritoMaritimo;
	}

	public String getEslora() {
		return this.eslora;
	}

	public void setEslora(String eslora) {
		this.eslora = eslora;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFolioInscripcion() {
		return this.folioInscripcion;
	}

	public void setFolioInscripcion(String folioInscripcion) {
		this.folioInscripcion = folioInscripcion;
	}

	public String getManga() {
		return this.manga;
	}

	public void setManga(String manga) {
		this.manga = manga;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMaterial() {
		return this.material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getMatriculaFluvial() {
		return this.matriculaFluvial;
	}

	public void setMatriculaFluvial(String matriculaFluvial) {
		this.matriculaFluvial = matriculaFluvial;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNib() {
		return this.nib;
	}

	public void setNib(String nib) {
		this.nib = nib;
	}

	public String getNombreBuque() {
		return this.nombreBuque;
	}

	public void setNombreBuque(String nombreBuque) {
		this.nombreBuque = nombreBuque;
	}

	public String getNumLista() {
		return this.numLista;
	}

	public void setNumLista(String numLista) {
		this.numLista = numLista;
	}

	public String getNumSerie() {
		return this.numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getOmi() {
		return this.omi;
	}

	public void setOmi(String omi) {
		this.omi = omi;
	}

	public String getOtrosDatos() {
		return this.otrosDatos;
	}

	public void setOtrosDatos(String otrosDatos) {
		this.otrosDatos = otrosDatos;
	}

	public String getPabellon() {
		return this.pabellon;
	}

	public void setPabellon(String pabellon) {
		this.pabellon = pabellon;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPesoMuerto() {
		return this.pesoMuerto;
	}

	public void setPesoMuerto(String pesoMuerto) {
		this.pesoMuerto = pesoMuerto;
	}

	public String getProvinciaMaritima() {
		return this.provinciaMaritima;
	}

	public void setProvinciaMaritima(String provinciaMaritima) {
		this.provinciaMaritima = provinciaMaritima;
	}

	public String getPuntal() {
		return this.puntal;
	}

	public void setPuntal(String puntal) {
		this.puntal = puntal;
	}

	public String getRegistradoBruto() {
		return this.registradoBruto;
	}

	public void setRegistradoBruto(String registradoBruto) {
		this.registradoBruto = registradoBruto;
	}

	public String getRegistradoNeto() {
		return this.registradoNeto;
	}

	public void setRegistradoNeto(String registradoNeto) {
		this.registradoNeto = registradoNeto;
	}

	public String getSubTipoEmbarcacion() {
		return this.subTipoEmbarcacion;
	}

	public void setSubTipoEmbarcacion(String subTipoEmbarcacion) {
		this.subTipoEmbarcacion = subTipoEmbarcacion;
	}

	public String getTipoEmbarcacion() {
		return this.tipoEmbarcacion;
	}

	public void setTipoEmbarcacion(String tipoEmbarcacion) {
		this.tipoEmbarcacion = tipoEmbarcacion;
	}

	public PropiedadVO getPropiedad() {
		return this.propiedad;
	}

	public void setPropiedad(PropiedadVO propiedad) {
		this.propiedad = propiedad;
	}

	public Set<MotorBuqueVO> getMotorBuques() {
		return this.motorBuques;
	}

	public void setMotorBuques(Set<MotorBuqueVO> motorBuques) {
		this.motorBuques = motorBuques;
	}

	public MotorBuqueVO addMotorBuque(MotorBuqueVO motorBuque) {
		getMotorBuques().add(motorBuque);
		motorBuque.setBuqueRegistro(this);

		return motorBuque;
	}

	public MotorBuqueVO removeMotorBuque(MotorBuqueVO motorBuque) {
		getMotorBuques().remove(motorBuque);
		motorBuque.setBuqueRegistro(null);

		return motorBuque;
	}

	public String getNeto() {
		return neto;
	}

	public void setNeto(String neto) {
		this.neto = neto;
	}

	public BigDecimal getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(BigDecimal idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

}