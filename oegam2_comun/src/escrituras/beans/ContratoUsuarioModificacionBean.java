package escrituras.beans;



import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;



/**
 * Data Object.
 * Clase que mapea los atributos de una entidad de base de datos.  
 * Tabla gestionada: CONTRATO

 */

  
    @SuppressWarnings("serial")
	public class ContratoUsuarioModificacionBean
	 implements Serializable{
       
      private BigDecimal idTipoContrato;          
      private String cif; 
      private String razonSocial; 
      private String anagramaContrato;     
      private String idTipoVia;       
      private String via; 
      private String numero; 
      private String letra; 
      private String escalera; 
      private String piso; 
      private String puerta; 
      private String idProvincia;      
      private String idMunicipio;      
      private BigDecimal codPostal; 
      private BigDecimal telefono; 
      private String correoElectronico; 
      private Timestamp fechaInicio; 
      private Timestamp fechaFin;       
     
      
      private String nifNieUsuarioPpal;         
      private String apellidosNombreUsuarioPpal; 
      private String anagrama;
      private String correoElectronicoUsu; 
      private Timestamp ultimaConexion; 
      private Timestamp fechaRenovacion;
	
	public BigDecimal getIdTipoContrato() {
		return idTipoContrato;
	}
	public void setIdTipoContrato(BigDecimal idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}

	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getAnagramaContrato() {
		return anagramaContrato;
	}
	public void setAnagramaContrato(String anagramaContrato) {
		this.anagramaContrato = anagramaContrato;
	}
	public String getIdTipoVia() {
		return idTipoVia;
	}
	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
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
	public String getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	
	
	public BigDecimal getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(BigDecimal codPostal) {
		this.codPostal = codPostal;
	}
	public BigDecimal getTelefono() {
		return telefono;
	}
	public void setTelefono(BigDecimal telefono) {
		this.telefono = telefono;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public Timestamp getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Timestamp getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
	public String getNifNieUsuarioPpal() {
		return nifNieUsuarioPpal;
	}
	public void setNifNieUsuarioPpal(String nifNieUsuarioPpal) {
		this.nifNieUsuarioPpal = nifNieUsuarioPpal;
	}
	public String getApellidosNombreUsuarioPpal() {
		return apellidosNombreUsuarioPpal;
	}
	public void setApellidosNombreUsuarioPpal(String apellidosNombreUsuarioPpal) {
		this.apellidosNombreUsuarioPpal = apellidosNombreUsuarioPpal;
	}
	public String getAnagrama() {
		return anagrama;
	}
	public void setAnagrama(String anagrama) {
		this.anagrama = anagrama;
	}
	public String getCorreoElectronicoUsu() {
		return correoElectronicoUsu;
	}
	public void setCorreoElectronicoUsu(String correoElectronicoUsu) {
		this.correoElectronicoUsu = correoElectronicoUsu;
	}
	public Timestamp getUltimaConexion() {
		return ultimaConexion;
	}
	public void setUltimaConexion(Timestamp ultimaConexion) {
		this.ultimaConexion = ultimaConexion;
	}
	public Timestamp getFechaRenovacion() {
		return fechaRenovacion;
	}
	public void setFechaRenovacion(Timestamp fechaRenovacion) {
		this.fechaRenovacion = fechaRenovacion;
	}
        
         
     

}

