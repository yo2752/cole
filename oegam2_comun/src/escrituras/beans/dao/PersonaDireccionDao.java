package escrituras.beans.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * Bean que almacena datos de la tabla PERSONA
 *
 */
public class PersonaDireccionDao {

	/*p.NIF, p.APELLIDO1_RAZON_SOCIAL, p.ESTADO, p.APELLIDO2,
                      p.NOMBRE, p.TELEFONOS, p.TIPO_PERSONA, 
                      p.ESTADO_CIVIL, d.ID_DIRECCION, d.ID_PROVINCIA, d.ID_MUNICIPIO, 
                      d.ID_TIPO_VIA, d.NOMBRE_VIA, d.NUMERO, d.COD_POSTAL, 
                      d.ESCALERA, d.PLANTA, d.PUERTA, d.NUM_LOCAL, pd.FECHA_INICIO*/	
	private String nif;
	private String apellido1_Razon_Social;// razón social
	private String apellido2;
	private BigDecimal estado;// IN (1,2,3)
	private String nombre;
	private String telefonos;
	private String tipo_Persona; // IN ('FÍSICA','JURÍDICA')
	private String estado_Civil;// IN ('CASADO(A)','SOLTERO(A)','VIUODO(A)','DIVORCIADO(A)','SEPARADO(A)'))
	/*d.ID_DIRECCION, d.ID_PROVINCIA, d.ID_MUNICIPIO, 
                      d.ID_TIPO_VIA, d.NOMBRE_VIA, d.NUMERO, 
                      d.COD_POSTAL, d.ESCALERA, d.PLANTA, d.PUERTA, 
                      d.NUM_LOCAL, pd.FECHA_INICIO*/
	private BigDecimal id_Direccion;
	private String id_Provincia;
	private String id_Municipio;
	private String id_Tipo_Via;
	private String nombre_Via;
	private String numero;
	private String cod_Postal;
	private String escalera;
	private String planta;
	private String puerta;
	private BigDecimal num_Local;
	private Timestamp fecha_Inicio;
	private BigDecimal p_Code;
	private String p_Sqlerrm;
	
	
	
	public PersonaDireccionDao() {
		
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
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

	public String getApellido1_razon_social() {
		return apellido1_Razon_Social;
	}

	public void setApellido1_razon_social(String apellido1RazonSocial) {
		apellido1_Razon_Social = apellido1RazonSocial;
	}

	public String getEstado_Civil() {
		return estado_Civil;
	}

	public void setEstado_Civil(String estadoCivil) {
		estado_Civil = estadoCivil;
	}

	public String getTipo_Persona() {
		return tipo_Persona;
	}

	public void setTipo_Persona(String tipoPersona) {
		tipo_Persona = tipoPersona;
	}

	public BigDecimal getP_code() {
		return p_Code;
	}

	public void setP_code(BigDecimal pCode) {
		p_Code = pCode;
	}

	public String getP_sqlerrm() {
		return p_Sqlerrm;
	}

	public void setP_sqlerrm(String pSqlerrm) {
		p_Sqlerrm = pSqlerrm;
	}


	public String getApellido1_Razon_Social() {
		return apellido1_Razon_Social;
	}

	public void setApellido1_Razon_Social(String apellido1RazonSocial) {
		apellido1_Razon_Social = apellido1RazonSocial;
	}

	public BigDecimal getId_Direccion() {
		return id_Direccion;
	}

	public void setId_Direccion(BigDecimal idDireccion) {
		id_Direccion = idDireccion;
	}

	public String getId_Provincia() {
		return id_Provincia;
	}

	public void setId_Provincia(String idProvincia) {
		id_Provincia = idProvincia;
	}

	public String getId_Municipio() {
		return id_Municipio;
	}

	public void setId_Municipio(String idMunicipio) {
		id_Municipio = idMunicipio;
	}

	public String getId_Tipo_Via() {
		return id_Tipo_Via;
	}

	public void setId_Tipo_Via(String idTipoVia) {
		id_Tipo_Via = idTipoVia;
	}

	public String getNombre_Via() {
		return nombre_Via;
	}

	public void setNombre_Via(String nombreVia) {
		nombre_Via = nombreVia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCod_Postal() {
		return cod_Postal;
	}

	public void setCod_Postal(String codPostal) {
		cod_Postal = codPostal;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public BigDecimal getNum_Local() {
		return num_Local;
	}

	public void setNum_Local(BigDecimal numLocal) {
		num_Local = numLocal;
	}

	public Timestamp getFecha_Inicio() {
		return fecha_Inicio;
	}

	public void setFecha_Inicio(Timestamp fechaInicio) {
		fecha_Inicio = fechaInicio;
	}

	public BigDecimal getP_Code() {
		return p_Code;
	}

	public void setP_Code(BigDecimal pCode) {
		p_Code = pCode;
	}

	public String getP_Sqlerrm() {
		return p_Sqlerrm;
	}

	public void setP_Sqlerrm(String pSqlerrm) {
		p_Sqlerrm = pSqlerrm;
	}

	
}