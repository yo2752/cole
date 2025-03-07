package escrituras.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.core.model.enumerados.EstadoCivil;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPresentante;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.utiles.enumerados.SubtipoPersona;
import escrituras.utiles.enumerados.TipoDocumentoAlternativo;
import general.beans.daos.pq_personas2.ExpedienteCursor;
import utilidades.estructuras.Fecha;

/**
 * Bean que almacena datos de la tabla PERSONA
 *
 */
/*SELECT p.NIF, p.APELLIDO1_RAZON_SOCIAL, p.ESTADO, p.APELLIDO2,
                      p.NOMBRE, p.TELEFONOS, p.TIPO_PERSONA,p.SUBTIPO, p.ESTADO_CIVIL,
                      p.CORREO_ELECTRONICO, p.ANAGRAMA, p.FECHA_NACIMIENTO, p.SEXO, p.ENTIDAD_BANCARIA, p.SUCURSAL_BANCARIA, p.DIGITO_CONTROL, p.NUMERO_CUENTA, 
                      d.ID_DIRECCION, d.ID_PROVINCIA, d.ID_MUNICIPIO, 
                      d.ID_TIPO_VIA, d.NOMBRE_VIA, d.NUMERO, d.COD_POSTAL, d.ESCALERA, d.PLANTA, d.PUERTA, d.NUM_LOCAL, pd.FECHA_INICIO
                  FROM PERSONA P, PERSONA_DIRECCION PD, direccion d'
                  */
@SuppressWarnings("serial")
public class Persona implements Serializable {

	private String nif;
	private String apellido1RazonSocial;// razón social
	private String apellido2;
	private Estado estado;// IN (1,2,3)
	private String nombre;
	private String telefonos;
	private String numColegiado;
	private TipoPersona tipoPersona; // IN ('FÍSICA','JURÍDICA')
	// subtipoPersona debe tomar valor obligatoriamente se el tipo de persona es 'JURIDICA'
	private TipoPresentante tipoPresentante;
	private EstadoCivil estadoCivil;// IN ('CASADO(A)','SOLTERO(A)','VIUODO(A)','DIVORCIADO(A)','SEPARADO(A)'))
	private Direccion direccion;
//	private CamposRespuestaPLSQL camposRespuestaPLSQL;
	
	private String correoElectronico;
	private String ncorpme;
	private String usuarioCorpme;
	private String passwordCorpme;
	private BigDecimal pirpf;
	private BigDecimal seccion;
	private BigDecimal hoja;
	private String  hojaBis;
	private BigDecimal ius;
	//Añadimos nuevos campos para la parte de Envío de Trámites de escritura
	private String iban;
//	private String entidadBancaria;//4
//	private String sucursalBancaria;//4
//	private String digitoControl;//2
//	private String numeroCuenta;//10
	private String anagrama;
	private String sexo;
	private SubtipoPersona subtipoPersona;
	// Identificador de las fuerzas armadas:
	private String fa;
	
	private Timestamp fechaNacimiento;
	private Fecha fechaNacimientoBean;
	private ArrayList <ExpedienteCursor> expediente;
	private List <String> direcciones;
	
	// Campos para Yerbabuena / Gestión Documental
	private String codigoMandato;
	private BigDecimal poderesEnFicha;
	
	private TipoDocumentoAlternativo tipoDocumentoAlternativo; 
	private Fecha fechaCaducidadAlternativo; 
	private Fecha fechaCaducidadNif;

	private Boolean otroDocumentoIdentidad = null;
	
	private Boolean indefinido;
	
	private String imprimirPersonaEscrituras;
	
	@Autowired
	UtilesFecha utilesFecha;
	
	public Persona(String nombre, String apellido1, String apellido2){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		this.nombre = nombre;
		this.apellido1RazonSocial = apellido1;
		this.apellido2 = apellido2;
	}
	
	public List <String> getDirecciones() {
		return direcciones;
	}

	public void setDirecciones(List <String> direcciones) {
		this.direcciones = direcciones;
	}

	public ArrayList <ExpedienteCursor> getExpediente() {
		return expediente;
	}

	public void setExpediente(ArrayList <ExpedienteCursor> expediente) {
		this.expediente = expediente;
	}

	public Persona() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public Persona(boolean inicializar) {
		this();
		if (inicializar){
			direccion = new Direccion(true);
			
		}
	}
	
	public String imprimir() {
		String buffer="";
		buffer = "nif: "+ nif+
		",apellido1RazonSocial: " +apellido1RazonSocial+
		",apellido2: "+ apellido2+
		",estado: "+estado.getValorEnum()+
		",nombre: "+nombre+
		",telefonos: "+telefonos;
		if (tipoPersona != null){
			buffer=buffer+",tipoPersona: "+tipoPersona.getValorEnum();
		}else{
			buffer=buffer+",tipoPersona: "+null;
		}
		if (estadoCivil != null){
			buffer=buffer+",estadoCivil: "+estadoCivil.getValorEnum();
		}else{
			buffer=buffer+",estadoCivil: "+null;
		}
		
		buffer=buffer+",direccion: ["+direccion.imprimir()+"]";
//		if (camposRespuestaPLSQL != null){
//			buffer=buffer+",camposRespuestaPLSQL: "+camposRespuestaPLSQL.imprimir();
//		}else{
			buffer=buffer+",camposRespuestaPLSQL: "+null;
//		}
		buffer=buffer+"]";
		
		return buffer.toString();
	}

	public String getIban() {
		return iban;
	}
	public void setIban(String iban){
		this.iban=iban;
	}
//	public String getDigitoControl() {
//		return digitoControl;
//	}
//
//	public void setDigitoControl(String digitoControl) {
//		this.digitoControl = digitoControl;
//	}
//
//	public String getNumeroCuenta() {
//		return numeroCuenta;
//	}
//
//	public void setNumeroCuenta(String numeroCuenta) {
//		this.numeroCuenta = numeroCuenta;
//	}


	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		if (nif != null){
			this.nif = nif.toUpperCase();
		}
		
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = Estado.convertir(estado);
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getApellido1RazonSocial() {
		return apellido1RazonSocial;
	}

	public void setApellido1RazonSocial(String apellido1RazonSocial) {
		this.apellido1RazonSocial = apellido1RazonSocial;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = EstadoCivil.convertir(estadoCivil);
	}
	
	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = TipoPersona.convertir(tipoPersona);
	}
	
	public TipoPresentante getTipoPresentante() {
		return tipoPresentante;
	}

	public void setTipoPresentante(String tipoPresentante) {
		this.tipoPresentante = TipoPresentante.convertir(tipoPresentante);
	}

//	public CamposRespuestaPLSQL getCamposRespuestaPLSQL() {
//		return camposRespuestaPLSQL;
//	}
//
//	public void setCamposRespuestaPLSQL(CamposRespuestaPLSQL camposRespuestaPLSQL) {
//		this.camposRespuestaPLSQL = camposRespuestaPLSQL;
//	}
	
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

//	public String getEntidadBancaria() {
//		return entidadBancaria;
//	}
//
//	public void setEntidadBancaria(String entidadBancaria) {
//		this.entidadBancaria = entidadBancaria;
//	}
//
//	public String getSucursalBancaria() {
//		return sucursalBancaria;
//	}
//
//	public void setSucursalBancaria(String sucursalBancaria) {
//		this.sucursalBancaria = sucursalBancaria;
//	}

	public String getAnagrama() {
		return anagrama;
	}

	public void setAnagrama(String anagrama) {
		this.anagrama = anagrama;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Timestamp getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Timestamp fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		this.fechaNacimientoBean = utilesFecha.getFechaFracionada(fechaNacimiento);
	}

	public String getNcorpme() {
		return ncorpme;
	}

	public void setNcorpme(String ncorpme) {
		this.ncorpme = ncorpme;
	}

	public BigDecimal getPirpf() {
		return pirpf;
	}

	public void setPirpf(BigDecimal pirpf) {
		this.pirpf = pirpf;
	}

	public void setPirpf(Object o){
		try {
			if (o instanceof Object[]) {
				setPirpf(new BigDecimal (((Object[]) o)[0].toString()));
			} else {
				setPirpf(new BigDecimal(o.toString()));
				
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	public BigDecimal getSeccion() {
		return seccion;
	}

	public void setSeccion(BigDecimal seccion) {
		this.seccion = seccion;
	}

	public BigDecimal getHoja() {
		return hoja;
	}

	public void setHoja(BigDecimal hoja) {
		this.hoja = hoja;
	}

	public String getHojaBis() {
		return hojaBis;
	}

	public void setHojaBis(String hojaBis) {
		this.hojaBis = hojaBis;
	}

	public BigDecimal getIus() {
		return ius;
	}

	public void setIus(BigDecimal ius) {
		this.ius = ius;
	}

	public SubtipoPersona getSubtipoPersona() {
		return subtipoPersona;
	}

	public void setSubtipoPersona(String subtipoPersona) {
		this.subtipoPersona = SubtipoPersona.convertir(subtipoPersona);
	}

	public Fecha getFechaNacimientoBean() {
		return fechaNacimientoBean;
	}

	public void setFechaNacimientoBean(Fecha fechaNacimientoBean) {
		this.fechaNacimientoBean = fechaNacimientoBean;
	}
   
	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getFa() {
		return fa;
	}

	public void setFa(String fa) {
		this.fa = fa;
	}

	public String getCodigoMandato() {
		return codigoMandato;
	}

	public void setCodigoMandato(String codigoMandato) {
		this.codigoMandato = codigoMandato;
	}

	public BigDecimal getPoderesEnFicha() {
		return poderesEnFicha;
	}

	public void setPoderesEnFicha(BigDecimal poderesEnFicha) {
		this.poderesEnFicha = poderesEnFicha;
	}

	public void setTipoDocumentoAlternativo(String tipoDocumentoAlternativo) {
		this.tipoDocumentoAlternativo = TipoDocumentoAlternativo.convertir(tipoDocumentoAlternativo);
	}

	public TipoDocumentoAlternativo getTipoDocumentoAlternativo() {
		return tipoDocumentoAlternativo;
	}

	public void setFechaCaducidadAlternativo(Fecha fechaCaducidadAlternativo) {
		this.fechaCaducidadAlternativo = fechaCaducidadAlternativo;
	}

	public Fecha getFechaCaducidadAlternativo() {
		return fechaCaducidadAlternativo;
	}

	public void setFechaCaducidadNif(Fecha fechaCaducidadNif) {
		this.fechaCaducidadNif = fechaCaducidadNif;
	}

	public Fecha getFechaCaducidadNif() {
		return fechaCaducidadNif;
	}

	public boolean isOtroDocumentoIdentidad() {
		if (otroDocumentoIdentidad == null) {
			otroDocumentoIdentidad = getFechaCaducidadAlternativo()!=null && getTipoDocumentoAlternativo()!=null;
		}
		return otroDocumentoIdentidad;
	}
	
	public void setOtroDocumentoIdentidad(Boolean otroDocumentoIdentidad){
		this.otroDocumentoIdentidad = otroDocumentoIdentidad;
	}

	public void setOtroDocumentoIdentidad(Object o){
		try {
			if (o instanceof Object[]) {
				setOtroDocumentoIdentidad(new Boolean (((Object[]) o)[0].toString()));
			} else {
				setOtroDocumentoIdentidad(new Boolean(o.toString()));
				
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	public Boolean getIndefinido() {
		return indefinido;
	}

	public void setIndefinido(Boolean indefinido) {
		this.indefinido = indefinido;
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
	
	public String getImprimirPersonaEscrituras() {
		String datos="";
		if (nif != null){
			datos = "Nif: "+ nif + "  -  ";
		}
		
		if (nombre != null){
			datos = datos + nombre + " ";
		}
		
		if (apellido1RazonSocial != null){
			datos = datos + apellido1RazonSocial + " ";
		}
		
		if (apellido2 != null){
			datos = datos + apellido2;
		}
		
		imprimirPersonaEscrituras = datos;
		
		return imprimirPersonaEscrituras;
	}
}