package escrituras.beans.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Bean que almacena datos de la tabla DIRECCION.
 *
 */
public class DireccionDao {
	
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
	private String letra;
	private BigDecimal num_Local;
	private Timestamp fecha_Inicio;
	
	
	public DireccionDao() {
	
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

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
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


}
