package hibernate.entities.personas;

import hibernate.entities.facturacion.Factura;
import hibernate.entities.trafico.SolicitudPlaca;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.UtilesFecha;

import utilidades.estructuras.Fecha;


/**
 * The persistent class for the PERSONA database table.
 * 
 */
@Entity
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PersonaPK id;

	private String anagrama;

	@Column(name="APELLIDO1_RAZON_SOCIAL")
	private String apellido1RazonSocial;

	private String apellido2;
	
	@Column(name="CODIGO_MANDATO")
	private String codigoMandato;

	@Column(name="CORREO_ELECTRONICO")
	private String correoElectronico;

	@Column(name="IBAN")
	private String iban;
//	@Column(name="DIGITO_CONTROL")
//	private String digitoControl;

//	@Column(name="ENTIDAD_BANCARIA")
//	private String entidadBancaria;

	private long estado;

	@Column(name="ESTADO_CIVIL")
	private String estadoCivil;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_NACIMIENTO")
	private Date fechaNacimiento;

	@Transient
    private Fecha fnacimiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_CAD_CARNET_COND")
	private Date fechaCaducidadCarnet;

	private BigDecimal hoja;

	@Column(name="HOJA_BIS")
	private String hojaBis;

	private BigDecimal ius;

	private String ncorpme;
	
	@Column(name = "USUARIO_CORPME")
	private String usuarioCorpme;

	@Column(name = "PASSWORD_CORPME")
	private String passwordCorpme;

	private String nombre;

//	@Column(name="NUMERO_CUENTA")
//	private String numeroCuenta;
	
	@Column(name="PODERES_EN_FICHA")
	private BigDecimal poderesEnFicha;

	private BigDecimal pirpf;

	private BigDecimal seccion;

	private String sexo;

	private String subtipo;

//	@Column(name="SUCURSAL_BANCARIA")
//	private String sucursalBancaria;

	private String telefonos;

	@Column(name="TIPO_PERSONA")
	private String tipoPersona;
	
	@Column(name="FECHA_CADUCIDAD_NIF")
	private Date fechaCaducidadNIF;
	
	@Column(name="FECHA_CADUCIDAD_ALTERNATIVO")
	private Date fechaCaducidadAlternativo;

	@Column(name="TIPO_DOCUMENTO_ALTERNATIVO")
	private String tipoDocumentoAlternativo;
	
	private String indefinido;
	
	//bi-directional many-to-one association to PersonaDireccion
	@OneToMany(mappedBy="persona")
	private List<PersonaDireccion> personaDireccions;
	
	//bi-directional many-to-one association to Factura
	@OneToMany(mappedBy="persona",cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<Factura> facturas;
	
	//bi-directional many-to-one association to EvolucionPersona
	@OneToMany(mappedBy="persona")
	private List<EvolucionPersona> evolucionPersonas;
	
	//bi-directional many-to-one association to SolicitudPlaca
	@OneToMany(mappedBy="titular")
	private List<SolicitudPlaca> solicitudPlacas;
	
	@Transient
	private Direccion direccionActual;

	public Persona() {
    }

	public PersonaPK getId() {
		return this.id;
	}

	public void setId(PersonaPK id) {
		this.id = id;
	}
	
	public String getAnagrama() {
		return this.anagrama;
	}

	public void setAnagrama(String anagrama) {
		this.anagrama = anagrama;
	}

	public String getApellido1RazonSocial() {
		return this.apellido1RazonSocial;
	}

	public void setApellido1RazonSocial(String apellido1RazonSocial) {
		this.apellido1RazonSocial = apellido1RazonSocial;
	}

	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	
	public String getCodigoMandato() {
		return this.codigoMandato;
	}

	public void setCodigoMandato(String codigoMandato) {
		this.codigoMandato = codigoMandato;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

//	public String getDigitoControl() {
//		return this.digitoControl;
//	}
//
//	public void setDigitoControl(String digitoControl) {
//		this.digitoControl = digitoControl;
//	}
//
//	public String getEntidadBancaria() {
//		return this.entidadBancaria;
//	}
//
//	public void setEntidadBancaria(String entidadBancaria) {
//		this.entidadBancaria = entidadBancaria;
//	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public long getEstado() {
		return this.estado;
	}

	public void setEstado(long estado) {
		this.estado = estado;
	}

	public String getEstadoCivil() {
		return this.estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}
	
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		UtilesFecha utilesFecha = ContextoSpring.getInstance().getBean(UtilesFecha.class);
		this.fnacimiento = utilesFecha.getFechaConDate(this.fechaNacimiento);
	}
	
	public Fecha getFnacimiento() {
		return this.fnacimiento;
	}

	public Date getFechaCaducidadCarnet() {
		return fechaCaducidadCarnet;
	}

	public void setFechaCaducidadCarnet(Date fechaCaducidadCarnet) {
		this.fechaCaducidadCarnet = fechaCaducidadCarnet;
	}

	public void setFnacimiento(Fecha fnacimiento) {
		this.fnacimiento = fnacimiento;
	}

	public BigDecimal getHoja() {
		return this.hoja;
	}

	public void setHoja(BigDecimal hoja) {
		this.hoja = hoja;
	}

	public String getHojaBis() {
		return this.hojaBis;
	}

	public void setHojaBis(String hojaBis) {
		this.hojaBis = hojaBis;
	}

	public BigDecimal getIus() {
		return this.ius;
	}

	public void setIus(BigDecimal ius) {
		this.ius = ius;
	}

	public String getNcorpme() {
		return this.ncorpme;
	}

	public void setNcorpme(String ncorpme) {
		this.ncorpme = ncorpme;
	}
	
	public String getUsuarioCorpme() {
		return usuarioCorpme;
	}

	public void setUsuarioCorpme(String usuarioCorpme) {
		this.usuarioCorpme = usuarioCorpme;
	}

	public String getPasswordCorpme() {
		return passwordCorpme;
	}

	public void setPasswordCorpme(String passwordCorpme) {
		this.passwordCorpme = passwordCorpme;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

//	public String getNumeroCuenta() {
//		return this.numeroCuenta;
//	}
//
//	public void setNumeroCuenta(String numeroCuenta) {
//		this.numeroCuenta = numeroCuenta;
//	}

	public BigDecimal getPirpf() {
		return this.pirpf;
	}

	public void setPirpf(BigDecimal pirpf) {
		this.pirpf = pirpf;
	}
	
	public BigDecimal getPoderesEnFicha() {
		return this.poderesEnFicha;
	}

	public void setPoderesEnFicha(BigDecimal poderesEnFicha) {
		this.poderesEnFicha = poderesEnFicha;
	}

	public BigDecimal getSeccion() {
		return this.seccion;
	}

	public void setSeccion(BigDecimal seccion) {
		this.seccion = seccion;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getSubtipo() {
		return this.subtipo;
	}

	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}

//	public String getSucursalBancaria() {
//		return this.sucursalBancaria;
//	}
//
//	public void setSucursalBancaria(String sucursalBancaria) {
//		this.sucursalBancaria = sucursalBancaria;
//	}

	public String getTelefonos() {
		return this.telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public String getTipoPersona() {
		return this.tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public List<PersonaDireccion> getPersonaDireccions() {
		return this.personaDireccions;
	}

	public void setPersonaDireccions(List<PersonaDireccion> personaDireccions) {
		this.personaDireccions = personaDireccions;
	}
	
	public List<Factura> getFacturas() {
		return this.facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	@Transient
	public Direccion getDireccionActual() {
		return direccionActual;
	}
	@Transient
	public void setDireccionActual(Direccion direccionActual) {
		this.direccionActual = direccionActual;
	}
	
	public List<EvolucionPersona> getEvolucionPersonas() {
		return this.evolucionPersonas;
	}

	public void setEvolucionPersonas(List<EvolucionPersona> evolucionPersonas) {
		this.evolucionPersonas = evolucionPersonas;
	}

	public List<SolicitudPlaca> getSolicitudPlacas() {
		return solicitudPlacas;
	}

	public void setSolicitudPlacas(List<SolicitudPlaca> solicitudPlacas) {
		this.solicitudPlacas = solicitudPlacas;
	}

	public Date getFechaCaducidadNIF() {
		return fechaCaducidadNIF;
	}

	public void setFechaCaducidadNIF(Date fechaCaducidadNIF) {
		this.fechaCaducidadNIF = fechaCaducidadNIF;
	}

	public Date getFechaCaducidadAlternativo() {
		return fechaCaducidadAlternativo;
	}

	public void setFechaCaducidadAlternativo(Date fechaCaducidadAlternativo) {
		this.fechaCaducidadAlternativo = fechaCaducidadAlternativo;
	}

	public String getTipoDocumentoAlternativo() {
		return tipoDocumentoAlternativo;
	}

	public void setTipoDocumentoAlternativo(String tipoDocumentoAlternativo) {
		this.tipoDocumentoAlternativo = tipoDocumentoAlternativo;
	}
	
	public String getIndefinido() {
		return indefinido;
	}

	public void setIndefinido(String indefinido) {
		this.indefinido = indefinido;
	}

	/**
	 * Necesitamos un metodo que setee los atributos de la Persona de BBDD a Cadena Vacia, ya que la comparacion
	 * es con el objeto de pantalla el cual no lleva nulos, mientas que este si 
	 * @param ob
	 * @return
	 */
	@Transient
	public void actualizaNulos(){
		
		this.setApellido1RazonSocial(this.getApellido1RazonSocial()!=null?this.getApellido1RazonSocial():"");
		this.setApellido2(this.getApellido2()!=null?this.getApellido2():"");
		this.setNombre(this.getNombre()!=null?this.getNombre():"");
		this.setTelefonos(this.getTelefonos()!=null?this.getTelefonos():"");
		this.setCorreoElectronico(this.getCorreoElectronico()!=null?this.getCorreoElectronico():"");
		this.getDireccionActual().setBloque(this.getDireccionActual().getBloque()!=null?this.getDireccionActual().getBloque():"");
		this.getDireccionActual().setCodPostal(this.getDireccionActual().getCodPostal()!=null?this.getDireccionActual().getCodPostal():"");
		this.getDireccionActual().setEscalera(this.getDireccionActual().getEscalera()!=null?this.getDireccionActual().getEscalera():"");
		//this.getDireccionActual().setHm(this.getDireccionActual().getHm()!=null?this.getDireccionActual().getHm():"");
		//this.getDireccionActual().setKm(this.getDireccionActual().getKm()!=null?this.getDireccionActual().getKm():"");
		this.getDireccionActual().setLetra(this.getDireccionActual().getLetra()!=null?this.getDireccionActual().getLetra():"");
		this.getDireccionActual().setNombreVia(this.getDireccionActual().getNombreVia()!=null?this.getDireccionActual().getNombreVia():"");
		//this.getDireccionActual().setNumLocal(this.getDireccionActual().getNumLocal()!=null?this.getDireccionActual().getNumLocal():"");
		this.getDireccionActual().setNumero(this.getDireccionActual().getNumero()!=null?this.getDireccionActual().getNumero():"");
		this.getDireccionActual().setPlanta(this.getDireccionActual().getPlanta()!=null?this.getDireccionActual().getPlanta():"");
		this.getDireccionActual().setPuerta(this.getDireccionActual().getPuerta()!=null?this.getDireccionActual().getPuerta():"");
		this.getDireccionActual().setPueblo(this.getDireccionActual().getPueblo()!=null?this.getDireccionActual().getPueblo():"");
		//this.getDireccionActual().setMunicipio(this.getDireccionActual().getMunicipio()!=null?this.getDireccionActual().getMunicipio():"");
		this.getDireccionActual().setViaSineditar(this.getDireccionActual().getViaSineditar()!=null?this.getDireccionActual().getViaSineditar():"");
		this.getDireccionActual().setIdTipoVia(this.getDireccionActual().getIdTipoVia()!=null?this.getDireccionActual().getIdTipoVia():"");
		this.setAnagrama(this.getAnagrama()!=null?this.getAnagrama():"");
		this.setTipoPersona(this.getTipoPersona()!=null?this.getTipoPersona():"");
		this.setEstadoCivil(this.getEstadoCivil()!=null?this.getEstadoCivil():"");
		this.setTipoDocumentoAlternativo(this.getTipoDocumentoAlternativo()!=null?this.getTipoDocumentoAlternativo():"");
		this.setIndefinido(this.getIndefinido()!=null?this.getIndefinido():"");
	}
	
	@Transient
	public boolean equals(Persona ob){
		
		
		if ((this.getApellido1RazonSocial().equals(ob.getApellido1RazonSocial())) && (this.getApellido2().equals(ob.getApellido2()))
			&& (this.getId().getNif().equals(ob.getId().getNif())) && (this.getNombre().equals(ob.getNombre()))
			&& ((this.getSexo()!=null && this.getSexo().equals(ob.getSexo())) || (this.getSexo()==null && ob.getSexo()==null))
			&& ((this.getAnagrama()!=null) && (this.getAnagrama().equals(ob.getAnagrama())) || this.getAnagrama()==null && ob.getAnagrama()==null)
			&& ((this.getTipoPersona()!=null) && this.getTipoPersona().equals(ob.getTipoPersona()) || this.getTipoPersona()==null && ob.getTipoPersona()==null)
			&& ((this.getEstadoCivil()!=null) && this.getEstadoCivil().equals(ob.getEstadoCivil()) || this.getEstadoCivil()==null && ob.getEstadoCivil()==null)
			&& ((this.getFechaNacimiento()!=null) && this.getFechaNacimiento().equals(ob.getFechaNacimiento()) || this.getFechaNacimiento()==null && ob.getFechaNacimiento()==null)
			//			&& ((this.getFechaNacimiento()!=null) && ob.getFechaNacimiento()!=null &&  this.getFechaNacimiento().compareTo(ob.getFechaNacimiento())<3600 || this.getFechaNacimiento()==null && ob.getFechaNacimiento()==null)
			//&& (this.getfNacimiento().equals(ob.getfNacimiento())) 	&& (this.getFechaNacimiento().equals(ob.getFechaNacimiento()))
			&& ((this.getTelefonos()!=null) && this.getTelefonos().equals(ob.getTelefonos()) || this.getTelefonos()==null && ob.getTelefonos()==null)	
			&& ((this.getCorreoElectronico()!=null) && this.getCorreoElectronico().equals(ob.getCorreoElectronico()) || this.getCorreoElectronico()==null && ob.getCorreoElectronico()==null)
			
		){
			return true;	
		}
			//&& (this.getDireccionActual().equals(ob.getDireccionActual())) 	
		else{
			
			return false;
			
		}
		
	}

}