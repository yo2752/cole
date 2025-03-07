package escrituras.beans.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * Bean que almacena datos de la tabla PERSONA
 *
 */
/*Select Co.Num_Colegiado, C.Id_Contrato, C.Id_Tipo_Contrato, Tc.Tipo_Contrato,
C.Estado_Contrato, C.Cif, C.Razon_Social, C.Anagrama_Contrato, C.Id_Tipo_Via, V.Nombre Tipo_Via,
C.Via, C.Numero, C.Letra, C.Escalera, C.Piso, C.Puerta, C.Id_Provincia, P.Nombre Provincia,
c.id_municipio, m.nombre municipio, c.cod_postal, c.telefono, c.correo_electronico, 
c.fecha_inicio, c.fecha_fin*/

public class ContratoDao {
	
	private String num_Colegiado;
	private BigDecimal id_Contrato;
	private BigDecimal id_Tipo_Contrato;
	private String  tipo_Contrato;
	private BigDecimal  estado_Contrato;
	private String cif;
	private String razon_Social;
	private String  anagrama_Contrato;
	private String id_Tipo_Via;	
	private String tipo_Via;
	private String via;
	private BigDecimal numero;
	private String letra;	
	private String escalera;
	private String piso;
	private String puerta;
	private String id_Provincia;
	private  String provincia;
	private String id_Municipio;
	private String municipio;
	private BigDecimal cod_Postal;
	private String telefono;
	private String correo_Electronico;
	private Timestamp fecha_Inicio;
	private Timestamp fecha_Fin;
	
	public String getId_Tipo_Via() {
		return id_Tipo_Via;
	}


	public void setId_Tipo_Via(String idTipoVia) {
		id_Tipo_Via = idTipoVia;
	}


	public String getTipo_Via() {
		return tipo_Via;
	}


	public void setTipo_Via(String tipoVia) {
		tipo_Via = tipoVia;
	}


	public String getVia() {
		return via;
	}


	public void setVia(String via) {
		this.via = via;
	}


	public BigDecimal getNumero() {
		return numero;
	}


	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}


	public String getLetra() {
		return letra;
	}


	public void setLetra(String letra) {
		this.letra = letra;
	}


	public String getEscalera() {
		return escalera;
	}


	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}


	public String getPiso() {
		return piso;
	}


	public void setPiso(String piso) {
		this.piso = piso;
	}


	public String getPuerta() {
		return puerta;
	}


	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}


	public String getId_Provincia() {
		return id_Provincia;
	}


	public void setId_Provincia(String idProvincia) {
		id_Provincia = idProvincia;
	}


	public String getProvincia() {
		return provincia;
	}


	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	public String getId_Municipio() {
		return id_Municipio;
	}


	public void setId_Municipio(String idMunicipio) {
		id_Municipio = idMunicipio;
	}


	public String getMunicipio() {
		return municipio;
	}


	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}


	public BigDecimal getCod_Postal() {
		return cod_Postal;
	}


	public void setCod_Postal(BigDecimal codPostal) {
		cod_Postal = codPostal;
	}

	
	
	
	
	
	public ContratoDao() {
	}

	
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


	public String getTipo_Contrato() {
		return tipo_Contrato;
	}


	public void setTipo_Contrato(String tipoContrato) {
		tipo_Contrato = tipoContrato;
	}


	


	public BigDecimal getEstado_Contrato() {
		return estado_Contrato;
	}


	public void setEstado_Contrato(BigDecimal estadoContrato) {
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