package escrituras.beans.dao;

import java.math.BigDecimal;


/**
 * Bean que almacena datos de colegiado
 *
 */
/*SELECT c.num_colegiado, co.id_contrato, co.id_provincia, p.nombre provincia,
                          u.NIF, u.apellidos_nombre, u.anagrama, u.correo_electronico
                  FROM colegiado c, usuario u, contrato_colegiado cc, contrato co, provincia p*/

public class ColegiadosDao {

	private String num_Colegiado;
	private String nif;
	private String apellidos_Nombre;
	private String  anagrama;
	private String correo_Electronico;
	private BigDecimal id_Contrato;
	private String provincia;
	private String idContratoProvincia;
	private String via;
	
	public String getNum_Colegiado() {
		return num_Colegiado;
	}
	public void setNum_Colegiado(String numColegiado) {
		num_Colegiado = numColegiado;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getApellidos_Nombre() {
		return apellidos_Nombre;
	}
	public void setApellidos_Nombre(String apellidosNombre) {
		apellidos_Nombre = apellidosNombre;
	}
	public String getAnagrama() {
		return anagrama;
	}
	public void setAnagrama(String anagrama) {
		this.anagrama = anagrama;
	}
	public String getCorreo_Electronico() {
		return correo_Electronico;
	}
	public void setCorreo_Electronico(String correoElectronico) {
		correo_Electronico = correoElectronico;
	}
	public BigDecimal getId_Contrato() {
		return id_Contrato;
	}
	public void setId_Contrato(BigDecimal idContrato) {
		id_Contrato = idContrato;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getIdContratoProvincia() {
		return id_Contrato+"_"+provincia;
	}
	public void setIdContratoProvincia(String idContratoProvincia) {
		this.idContratoProvincia = idContratoProvincia;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	
	
	
	
}