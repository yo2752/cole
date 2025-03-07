package utilidades.web;

import java.io.Serializable;
import java.math.BigDecimal;

/*select c.id_contrato,
c.id_tipo_contrato, tc.tipo_contrato,
c.estado_contrato, c.cif,
c.razon_social, c.anagrama_contrato, c.id_tipo_via, v.nombre as nombre_via,
c.via, c.numero, c.letra, c.escalera, c.piso, c.puerta, c.id_provincia, p.nombre as nombre_provincia,
c.id_municipio, m.nombre, c.cod_postal, c.telefono, c.correo_electronico, 
c.fecha_inicio, c.fecha_fin, co.num_colegiado */
public class Contrato implements Serializable{

	private static final long serialVersionUID = 1L;

	private String _num_colegiado = null;
	private String _anagrama = null;
	private String _cif = null;
	private BigDecimal _id_contrato = null;
	private java.sql.Date _ultimaConexion = null;
	private String _razon_social = null;
	private String _nombre_provincia = null;
	private String _colegio = null;
	private String jefatura_Provincial;
	private String correo_Electronico = null;
	private String id_Tipo_Via;
	private String nombre_Via;
	private String via;
	private String numero;
	private String letra;
	private String escalera;
	private String piso;
	private String puerta;
	private String nombre;
	private String cod_postal;
	private String direccionCompleta;
	private String id_provincia;
	private String id_municipio;

	public String get_num_colegiado() {
		return _num_colegiado;
	}

	public void set_num_colegiado(String numColegiado) {
		_num_colegiado = numColegiado;
	}

	public String get_anagrama() {
		return _anagrama;
	}

	public void set_anagrama(String anagrama) {
		_anagrama = anagrama;
	}

	public String get_cif() {
		return _cif;
	}

	public void set_cif(String cif) {
		_cif = cif;
	}

	public BigDecimal get_id_contrato() {
		return _id_contrato;
	}

	public void set_id_contrato(BigDecimal idContrato) {
		_id_contrato = idContrato;
	}

	public java.sql.Date get_ultimaConexion() {
		return _ultimaConexion;
	}

	public void set_ultimaConexion(java.sql.Date ultimaConexion) {
		_ultimaConexion = ultimaConexion;
	}

	public String get_razon_social() {
		return _razon_social;
	}

	public void set_razon_social(String razonSocial) {
		_razon_social = razonSocial;
	}

	public String get_nombre_provincia() {
		return _nombre_provincia;
	}

	public void set_nombre_provincia(String nombreProvincia) {
		_nombre_provincia = nombreProvincia;
	}

	public String get_colegio() {
		return _colegio;
	}

	public void set_colegio(String colegio) {
		_colegio = colegio;
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

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCod_postal() {
		return cod_postal;
	}

	public void setCod_postal(String codPostal) {
		cod_postal = codPostal;
	}

	public String getAnagrama() {
		return _anagrama;
	}

	public void setAnagrama(String anagrama) {
		_anagrama = anagrama;
	}

	public java.sql.Date getUltimaConexion() {
		return _ultimaConexion;
	}

	public void setUltimaConexion(java.sql.Date ultimaConexion) {
		_ultimaConexion = ultimaConexion;
	}

	public String getRazon_social() {
		return _razon_social;
	}

	public void setRazon_social(String razonSocial) {
		_razon_social = razonSocial;
	}
	
	public BigDecimal getId_contrato() {
		return _id_contrato;
	}
	
	public void setId_contrato(BigDecimal idContrato) {
		_id_contrato = idContrato;
	}
	
	public String getNombre_provincia() {
		return _nombre_provincia;
	}
	
	public void setNombre_provincia(String nombreProvincia) {
		_nombre_provincia = nombreProvincia;
	}

	public String getNum_colegiado() {
		return _num_colegiado;
	}

	public void setNum_colegiado(String numColegiado) {
		_num_colegiado = numColegiado;
	}

	public String getCif() {
		return _cif;
	}

	public void setCif(String cifContrato) {
		_cif = cifContrato;
	}

	public String getColegio() {
		return _colegio;
	}

	public void setColegio(String colegio) {
		_colegio = colegio;
	}

	public String getJefatura_Provincial() {
		return jefatura_Provincial;
	}

	public void setJefatura_Provincial(String jefatura_Provincial) {
		this.jefatura_Provincial = jefatura_Provincial;
	}

	public String getCorreo_Electronico() {
		return correo_Electronico;
	}

	public void setCorreo_Electronico(String correoElectronico) {
		correo_Electronico = correoElectronico;
	}

	public String getDireccionCompleta() {
		return via+","+nombre+","+_nombre_provincia;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}

	public String getId_provincia() {
		return id_provincia;
	}

	public void setId_provincia(String idProvincia) {
		id_provincia = idProvincia;
	}

	public String getId_municipio() {
		return id_municipio;
	}

	public void setId_municipio(String idMunicipio) {
		id_municipio = idMunicipio;
	}

}