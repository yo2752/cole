package escrituras.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

import escrituras.beans.dao.DireccionDao;


/**
 * Bean que almacena datos de la tabla PERSONA
 *
 */
/*Select Co.Num_Colegiado, C.Id_Contrato, C.Id_Tipo_Contrato, Tc.Tipo_Contrato,
C.Estado_Contrato, C.Cif, C.Razon_Social, C.Anagrama_Contrato, C.Id_Tipo_Via, V.Nombre Tipo_Via,
C.Via, C.Numero, C.Letra, C.Escalera, C.Piso, C.Puerta, C.Id_Provincia, P.Nombre Provincia,
c.id_municipio, m.nombre municipio, c.cod_postal, c.telefono, c.correo_electronico, 
c.fecha_inicio, c.fecha_fin*/
public class Contrato1 {

	private String num_Colegiado;
	private BigDecimal id_Contrato;
	private BigDecimal id_Tipo_Contrato;
	private BigDecimal tipo_Contrato;
	private Integer  estado_Contrato;
	private String cif;
	private String razon_Social;
	private String  anagrama_Contrato;
	private DireccionDao direccionDao;
	private String telefono;
	private String correo_Electronico;
	private Timestamp fecha_Inicio;
	private Timestamp fecha_Fin;
	
	public String getNum_Colegiado() {
		return num_Colegiado;
	}
	public void setNum_Colegiado(String numColegiado) {
		num_Colegiado = numColegiado;
	}
	public BigDecimal getId_Contrato() {
		return id_Contrato;
	}
	public void setId_Contrato(BigDecimal idContrato) {
		id_Contrato = idContrato;
	}
	public BigDecimal getId_Tipo_Contrato() {
		return id_Tipo_Contrato;
	}
	public void setId_Tipo_Contrato(BigDecimal idTipoContrato) {
		id_Tipo_Contrato = idTipoContrato;
	}
	public BigDecimal getTipo_Contrato() {
		return tipo_Contrato;
	}
	public void setTipo_Contrato(BigDecimal tipoContrato) {
		tipo_Contrato = tipoContrato;
	}
	public Integer getEstado_Contrato() {
		return estado_Contrato;
	}
	public void setEstado_Contrato(Integer estadoContrato) {
		estado_Contrato = estadoContrato;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getRazon_Social() {
		return razon_Social;
	}
	public void setRazon_Social(String razonSocial) {
		razon_Social = razonSocial;
	}
	public String getAnagrama_Contrato() {
		return anagrama_Contrato;
	}
	public void setAnagrama_Contrato(String anagramaContrato) {
		anagrama_Contrato = anagramaContrato;
	}
	public DireccionDao getDireccionDao() {
		return direccionDao;
	}
	public void setDireccionDao(DireccionDao direccionDao) {
		this.direccionDao = direccionDao;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo_Electronico() {
		return correo_Electronico;
	}
	public void setCorreo_Electronico(String correoElectronico) {
		correo_Electronico = correoElectronico;
	}
	public Timestamp getFecha_Inicio() {
		return fecha_Inicio;
	}
	public void setFecha_Inicio(Timestamp fechaInicio) {
		fecha_Inicio = fechaInicio;
	}
	public Timestamp getFecha_Fin() {
		return fecha_Fin;
	}
	public void setFecha_Fin(Timestamp fechaFin) {
		fecha_Fin = fechaFin;
	}
	
	
	
	
	
	
	
}