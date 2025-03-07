package escrituras.beans.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * Bean que almacena datos del contrato
 *
 */
/*Select C.Id_Contrato, C.Id_Tipo_Contrato,
Tc.Tipo_Contrato, C.Estado_Contrato, C.Cif,
C.Razon_Social, C.Anagrama_Contrato, C.Id_Tipo_Via,
Tv.Nombre, C.Via, C.Numero, C.Letra,
C.Escalera, C.Piso, C.Puerta, C.Id_Provincia,
P.nombre, C.Id_Municipio, M.nombre,
C.Cod_Postal, C.Telefono, C.Correo_Electronico,
C.Fecha_Inicio, C.Fecha_Fin*/

public class ContratoListaDetalleDao {

	
	private BigDecimal id_Contrato;
	private BigDecimal id_Tipo_Contrato;
	private String  tipo_Contrato;
	private BigDecimal  estado_Contrato;
	private String cif;
	private String razon_Social;
	private String  anagrama_Contrato;
	private String id_Tipo_Via;	
	private String nombre;
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
	private String num_Colegiado;
	private BigDecimal id_Usuario;
	private BigDecimal estado_Usuario;
	private String nif;
	private String apellidos_Nombre;
	private String anagrama;
	private String correo_Electronico_Usu;
	private Timestamp ultima_Conexion;
	private  Timestamp fecha_Renovacion;
	private String codigo_Aplicacion;
	private String desc_Aplicacion;
	private Integer asignada;

	public String getNum_Colegiado() {
		return num_Colegiado;
	}


	public void setNum_Colegiado(String numColegiado) {
		num_Colegiado = numColegiado;
	}


	public BigDecimal getId_Usuario() {
		return id_Usuario;
	}


	public void setId_Usuario(BigDecimal idUsuario) {
		id_Usuario = idUsuario;
	}


	public BigDecimal getEstado_Usuario() {
		return estado_Usuario;
	}


	public void setEstado_Usuario(BigDecimal estadoUsuario) {
		estado_Usuario = estadoUsuario;
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


	public String getCorreo_Electronico_Usu() {
		return correo_Electronico_Usu;
	}


	public void setCorreo_Electronico_Usu(String correoElectronicoUsu) {
		correo_Electronico_Usu = correoElectronicoUsu;
	}


	public Timestamp getUltima_Conexion() {
		return ultima_Conexion;
	}


	public void setUltima_Conexion(Timestamp ultimaConexion) {
		ultima_Conexion = ultimaConexion;
	}


	public Timestamp getFecha_Renovacion() {
		return fecha_Renovacion;
	}


	public void setFecha_Renovacion(Timestamp fechaRenovacion) {
		fecha_Renovacion = fechaRenovacion;
	}


	public String getCodigo_Aplicacion() {
		return codigo_Aplicacion;
	}


	public void setCodigo_Aplicacion(String codigoAplicacion) {
		codigo_Aplicacion = codigoAplicacion;
	}


	public String getDesc_Aplicacion() {
		return desc_Aplicacion;
	}


	public void setDesc_Aplicacion(String descAplicacion) {
		desc_Aplicacion = descAplicacion;
	}


	public Integer getAsignada() {
		return asignada;
	}


	public void setAsignada(Integer asignada) {
		this.asignada = asignada;
	}


	
	
	public String getId_Tipo_Via() {
		return id_Tipo_Via;
	}


	public void setId_Tipo_Via(String idTipoVia) {
		id_Tipo_Via = idTipoVia;
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


	

	public String getId_Municipio() {
		return id_Municipio;
	}


	public void setId_Municipio(String idMunicipio) {
		id_Municipio = idMunicipio;
	}

	public BigDecimal getCod_Postal() {
		return cod_Postal;
	}


	public void setCod_Postal(BigDecimal codPostal) {
		cod_Postal = codPostal;
	}
	
	public ContratoListaDetalleDao() {
	}

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getProvincia() {
		return provincia;
	}


	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	public String getMunicipio() {
		return municipio;
	}


	public void setMunicipio(String municipio) {
		this.municipio = municipio;
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