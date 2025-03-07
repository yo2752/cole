package escrituras.beans.dao;

import java.math.BigDecimal;


/**
 * Bean que almacena datos de la tabla PERSONA
 *
 */
public class PersonaDao {
	
	private String nif;
	private String apellido1_Razon_Social;// razón social
	private String apellido2;
	private BigDecimal estado;// IN (1,2,3)
	private String nombre;
	private String telefonos;
	private String tipo_Persona; // IN ('FÍSICA','JURÍDICA')
	private String estado_Civil;// IN ('CASADO(A)','SOLTERO(A)','VIUODO(A)','DIVORCIADO(A)','SEPARADO(A)'))
	private DireccionDao direccion; 
	private BigDecimal p_Code;
	private String p_Sqlerrm;
	
	
	
	public PersonaDao() {
	
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

	public DireccionDao getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionDao direccion) {
		this.direccion = direccion;
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

	
	

	
}