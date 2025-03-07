package escrituras.beans;



import java.io.Serializable;
import java.util.Date;



/**
 * Data Object.
 * Clase que mapea los atributos de una entidad de base de datos.  
 * Tabla gestionada: CONTRATO

 */

  
    @SuppressWarnings("serial")
	public class ContratoBean
	 implements Serializable{

      private Long idContrato; 
      private Long idTipoContrato; 
      private Long idTipoEstadoContrato; 
      private String tipoEstadoContrato; 
      private String cif; 
      private String razonSocial; 
      private String idTipoVia; 
      private String tipoVia; 
      private String via; 
      private String numero; 
      private String letra; 
      private String escalera; 
      private String piso; 
      private String puerta; 
      private String idProvincia; 
      private String provincia; 
      private String idMunicipio; 
      private String municipio; 
      private Long codPostal; 
      private Long telefono; 
      private String correoElectronico; 
      private String anagramaContrato; 
      private String nifNieUsuarioPpal; 
      private String apellidosNombreUsuarioPpal; 
      private String numColegiadoUsuarioPpal; 
      private Date fechaInicio; 
      private Date fechaFin;


    /**
     * Constructor vacio del Bean
     */
    public ContratoBean () {
        init();
    } 
  
    /** 
     * Inicializacion de variables (Si las hay)
     */
    protected void init() {
      // Valores iniciales
    }
  

  
    /**
     * Método Setter para el campo ID_CONTRATO
     * @param idContrato
     */
    public void  setIdContrato ( Long idContrato ) {
        this.idContrato = idContrato;
    }
  
    /**
     * Método Getter para el campo ID_CONTRATO
     * @return el valor
     */ 
    public Long getIdContrato() {
        return idContrato;
    }
  
    /**
     * Método Setter para el campo ID_TIPO_CONTRATO
     * @param idTipoContrato
     */
    public void  setIdTipoContrato ( Long idTipoContrato ) {
        this.idTipoContrato = idTipoContrato;
    }
  
    /**
     * Método Getter para el campo ID_TIPO_CONTRATO
     * @return el valor
     */ 
    public Long getIdTipoContrato() {
        return idTipoContrato;
    }
  
    /**
     * Método Setter para el campo ID_TIPO_ESTADO_CONTRATO
     * @param idTipoEstadoContrato
     */
    public void  setIdTipoEstadoContrato ( Long idTipoEstadoContrato ) {
        this.idTipoEstadoContrato = idTipoEstadoContrato;
    }
  
    /**
     * Método Getter para el campo ID_TIPO_ESTADO_CONTRATO
     * @return el valor
     */ 
    public Long getIdTipoEstadoContrato() {
        return idTipoEstadoContrato;
    }
  
    /**
     * Método Setter para el campo TIPO_ESTADO_CONTRATO
     * @param tipoEstadoContrato
     */
    public void  setTipoEstadoContrato ( String tipoEstadoContrato ) {
        this.tipoEstadoContrato = tipoEstadoContrato;
    }
  
    /**
     * Método Getter para el campo TIPO_ESTADO_CONTRATO
     * @return el valor
     */ 
    public String getTipoEstadoContrato() {
        return tipoEstadoContrato;
    }
  
    /**
     * Método Setter para el campo CIF
     * @param cif
     */
    public void  setCif ( String cif ) {
        this.cif = cif;
    }
  
    /**
     * Método Getter para el campo CIF
     * @return el valor
     */ 
    public String getCif() {
        return cif;
    }
  
    /**
     * Método Setter para el campo RAZON_SOCIAL
     * @param razonSocial
     */
    public void  setRazonSocial ( String razonSocial ) {
        this.razonSocial = razonSocial;
    }
  
    /**
     * Método Getter para el campo RAZON_SOCIAL
     * @return el valor
     */ 
    public String getRazonSocial() {
        return razonSocial;
    }
  
    /**
     * Método Setter para el campo ID_TIPO_VIA
     * @param idTipoVia
     */
    public void  setIdTipoVia ( String idTipoVia ) {
        this.idTipoVia = idTipoVia;
    }
  
    /**
     * Método Getter para el campo ID_TIPO_VIA
     * @return el valor
     */ 
    public String getIdTipoVia() {
        return idTipoVia;
    }
  
    /**
     * Método Setter para el campo TIPO_VIA
     * @param tipoVia
     */
    public void  setTipoVia ( String tipoVia ) {
        this.tipoVia = tipoVia;
    }
  
    /**
     * Método Getter para el campo TIPO_VIA
     * @return el valor
     */ 
    public String getTipoVia() {
        return tipoVia;
    }
  
    /**
     * Método Setter para el campo VIA
     * @param via
     */
    public void  setVia ( String via ) {
        this.via = via;
    }
  
    /**
     * Método Getter para el campo VIA
     * @return el valor
     */ 
    public String getVia() {
        return via;
    }
  
    /**
     * Método Setter para el campo NUMERO
     * @param numero
     */
    public void  setNumero ( String numero ) {
        this.numero = numero;
    }
  
    /**
     * Método Getter para el campo NUMERO
     * @return el valor
     */ 
    public String getNumero() {
        return numero;
    }
  
    /**
     * Método Setter para el campo LETRA
     * @param letra
     */
    public void  setLetra ( String letra ) {
        this.letra = letra;
    }
  
    /**
     * Método Getter para el campo LETRA
     * @return el valor
     */ 
    public String getLetra() {
        return letra;
    }
  
    /**
     * Método Setter para el campo ESCALERA
     * @param escalera
     */
    public void  setEscalera ( String escalera ) {
        this.escalera = escalera;
    }
  
    /**
     * Método Getter para el campo ESCALERA
     * @return el valor
     */ 
    public String getEscalera() {
        return escalera;
    }
  
    /**
     * Método Setter para el campo PISO
     * @param piso
     */
    public void  setPiso ( String piso ) {
        this.piso = piso;
    }
  
    /**
     * Método Getter para el campo PISO
     * @return el valor
     */ 
    public String getPiso() {
        return piso;
    }
  
    /**
     * Método Setter para el campo PUERTA
     * @param puerta
     */
    public void  setPuerta ( String puerta ) {
        this.puerta = puerta;
    }
  
    /**
     * Método Getter para el campo PUERTA
     * @return el valor
     */ 
    public String getPuerta() {
        return puerta;
    }
  
    /**
     * Método Setter para el campo ID_PROVINCIA
     * @param idProvincia
     */
    public void  setIdProvincia ( String idProvincia ) {
        this.idProvincia = idProvincia;
    }
  
    /**
     * Método Getter para el campo ID_PROVINCIA
     * @return el valor
     */ 
    public String getIdProvincia() {
        return idProvincia;
    }
  
    /**
     * Método Setter para el campo PROVINCIA
     * @param provincia
     */
    public void  setProvincia ( String provincia ) {
        this.provincia = provincia;
    }
  
    /**
     * Método Getter para el campo PROVINCIA
     * @return el valor
     */ 
    public String getProvincia() {
        return provincia;
    }
  
    /**
     * Método Setter para el campo ID_MUNICIPIO
     * @param idMunicipio
     */
    public void  setIdMunicipio ( String idMunicipio ) {
        this.idMunicipio = idMunicipio;
    }
  
    /**
     * Método Getter para el campo ID_MUNICIPIO
     * @return el valor
     */ 
    public String getIdMunicipio() {
        return idMunicipio;
    }
  
    /**
     * Método Setter para el campo MUNICIPIO
     * @param municipio
     */
    public void  setMunicipio ( String municipio ) {
        this.municipio = municipio;
    }
  
    /**
     * Método Getter para el campo MUNICIPIO
     * @return el valor
     */ 
    public String getMunicipio() {
        return municipio;
    }
  
    /**
     * Método Setter para el campo COD_POSTAL
     * @param codPostal
     */
    public void  setCodPostal ( Long codPostal ) {
        this.codPostal = codPostal;
    }
  
    /**
     * Método Getter para el campo COD_POSTAL
     * @return el valor
     */ 
    public Long getCodPostal() {
        return codPostal;
    }
  
    /**
     * Método Setter para el campo TELEFONO
     * @param telefono
     */
    public void  setTelefono ( Long telefono ) {
        this.telefono = telefono;
    }
  
    /**
     * Método Getter para el campo TELEFONO
     * @return el valor
     */ 
    public Long getTelefono() {
        return telefono;
    }
  
    /**
     * Método Setter para el campo CORREO_ELECTRONICO
     * @param correoElectronico
     */
    public void  setCorreoElectronico ( String correoElectronico ) {
        this.correoElectronico = correoElectronico;
    }
  
    /**
     * Método Getter para el campo CORREO_ELECTRONICO
     * @return el valor
     */ 
    public String getCorreoElectronico() {
        return correoElectronico;
    }
  
    /**
     * Método Setter para el campo ANAGRAMA_CONTRATO
     * @param anagramaContrato
     */
    public void  setAnagramaContrato ( String anagramaContrato ) {
        this.anagramaContrato = anagramaContrato;
    }
  
    /**
     * Método Getter para el campo ANAGRAMA_CONTRATO
     * @return el valor
     */ 
    public String getAnagramaContrato() {
        return anagramaContrato;
    }
  
    /**
     * Método Setter para el campo NIF_NIE_USUARIO_PPAL
     * @param nifNieUsuarioPpal
     */
    public void  setNifNieUsuarioPpal ( String nifNieUsuarioPpal ) {
        this.nifNieUsuarioPpal = nifNieUsuarioPpal;
    }
  
    /**
     * Método Getter para el campo NIF_NIE_USUARIO_PPAL
     * @return el valor
     */ 
    public String getNifNieUsuarioPpal() {
        return nifNieUsuarioPpal;
    }
  
    /**
     * Método Setter para el campo APELLIDOS_NOMBRE_USUARIO_PPAL
     * @param apellidosNombreUsuarioPpal
     */
    public void  setApellidosNombreUsuarioPpal ( String apellidosNombreUsuarioPpal ) {
        this.apellidosNombreUsuarioPpal = apellidosNombreUsuarioPpal;
    }
  
    /**
     * Método Getter para el campo APELLIDOS_NOMBRE_USUARIO_PPAL
     * @return el valor
     */ 
    public String getApellidosNombreUsuarioPpal() {
        return apellidosNombreUsuarioPpal;
    }
  
    /**
     * Método Setter para el campo NUM_COLEGIADO_USUARIO_PPAL
     * @param numColegiadoUsuarioPpal
     */
    public void  setNumColegiadoUsuarioPpal ( String numColegiadoUsuarioPpal ) {
        this.numColegiadoUsuarioPpal = numColegiadoUsuarioPpal;
    }
  
    /**
     * Método Getter para el campo NUM_COLEGIADO_USUARIO_PPAL
     * @return el valor
     */ 
    public String getNumColegiadoUsuarioPpal() {
        return numColegiadoUsuarioPpal;
    }
  
    /**
     * Método Setter para el campo FECHA_INICIO
     * @param fechaInicio
     */
    public void  setFechaInicio ( Date fechaInicio ) {
        this.fechaInicio = fechaInicio;
    }
  
    /**
     * Método Getter para el campo FECHA_INICIO
     * @return el valor
     */ 
    public Date getFechaInicio() {
        return fechaInicio;
    }
  
    /**
     * Método Setter para el campo FECHA_FIN
     * @param fechaFin
     */
    public void  setFechaFin ( Date fechaFin ) {
        this.fechaFin = fechaFin;
    }
  
    /**
     * Método Getter para el campo FECHA_FIN
     * @return el valor
     */ 
    public Date getFechaFin() {
        return fechaFin;
    }

}

