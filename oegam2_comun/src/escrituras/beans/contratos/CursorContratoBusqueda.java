package escrituras.beans.contratos;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Clase para el cursor de la búsqueda de contratos. 
 * @author juan.gomez
 *
 *     -- Monto la select para el cursor
    	w_select := 'select Co.Num_Colegiado, C.Id_Contrato, C.Id_Tipo_Contrato, Tc.Tipo_Contrato,
            C.Estado_Contrato, C.Cif, C.Razon_Social, C.Anagrama_Contrato, C.Id_Tipo_Via, V.Nombre Tipo_Via,
            C.Via, C.Numero, C.Letra, C.Escalera, C.Piso, C.Puerta, C.Id_Provincia, P.Nombre Provincia,
            c.id_municipio, m.nombre municipio, c.cod_postal, c.telefono, c.correo_electronico, 
            c.fecha_inicio, c.fecha_fin
        from contrato c, contrato_colegiado co, tipo_contrato tc, tipo_via v, provincia p, municipio m';
 */
public class CursorContratoBusqueda {

	private String NUM_COLEGIADO;
	
	private String NUM_COLEGIADO_NACIONAL;
	
	// INCIDENCIA MANTIS 6019 : Se muestra el alias para la consulta desde el Administrador
	private String alias;
	
	private BigDecimal Id_Contrato;
	private BigDecimal Id_Tipo_Contrato;
	private String Tipo_Contrato;
	
	private String Estado_Contrato;
	private String Cif;
	private String Razon_Social;
	private String Anagrama_Contrato;
	private String Id_Tipo_Via;
	private String Tipo_Via;
	 
	private String Via;
	private String Numero;
	private String Letra;
	private String Escalera;
	private String Piso;
	private String Puerta;
	private String Id_Provincia;
	private String Provincia;
	private String id_municipio;
	private String municipio;
	private String cod_postal;
	private String telefono;
	private String correo_electronico; 
	private Timestamp fecha_inicio;
	private Timestamp fecha_fin;
	
	public CursorContratoBusqueda() {
	}

	public String getNUM_COLEGIADO() {
		return NUM_COLEGIADO;
	}

	public void setNUM_COLEGIADO(String nUMCOLEGIADO) {
		NUM_COLEGIADO = nUMCOLEGIADO;
	}
	
	public String getNUM_COLEGIADO_NACIONAL() {
		return NUM_COLEGIADO_NACIONAL;
	}

	public void setNUM_COLEGIADO_NACIONAL(String nUMCOLEGIADONACIONAL) {
		NUM_COLEGIADO_NACIONAL = nUMCOLEGIADONACIONAL;
	}

	public BigDecimal getId_Contrato() {
		return Id_Contrato;
	}

	public void setId_Contrato(BigDecimal idContrato) {
		Id_Contrato = idContrato;
	}

	public BigDecimal getId_Tipo_Contrato() {
		return Id_Tipo_Contrato;
	}

	public void setId_Tipo_Contrato(BigDecimal idTipoContrato) {
		Id_Tipo_Contrato = idTipoContrato;
	}

	public String getTipo_Contrato() {
		return Tipo_Contrato;
	}

	public void setTipo_Contrato(String tipoContrato) {
		Tipo_Contrato = tipoContrato;
	}

	public String getEstado_Contrato() {
		return Estado_Contrato;
	}

	public void setEstado_Contrato(String estadoContrato) {
		Estado_Contrato = estadoContrato;
	}

	public String getCif() {
		return Cif;
	}

	public void setCif(String cif) {
		Cif = cif;
	}

	public String getRazon_Social() {
		return Razon_Social;
	}

	public void setRazon_Social(String razonSocial) {
		Razon_Social = razonSocial;
	}

	public String getAnagrama_Contrato() {
		return Anagrama_Contrato;
	}

	public void setAnagrama_Contrato(String anagramaContrato) {
		Anagrama_Contrato = anagramaContrato;
	}

	public String getId_Tipo_Via() {
		return Id_Tipo_Via;
	}

	public void setId_Tipo_Via(String idTipoVia) {
		Id_Tipo_Via = idTipoVia;
	}

	public String getTipo_Via() {
		return Tipo_Via;
	}

	public void setTipo_Via(String tipoVia) {
		Tipo_Via = tipoVia;
	}

	public String getVia() {
		return Via;
	}

	public void setVia(String via) {
		Via = via;
	}

	public String getNumero() {
		return Numero;
	}

	public void setNumero(String numero) {
		Numero = numero;
	}

	public String getLetra() {
		return Letra;
	}

	public void setLetra(String letra) {
		Letra = letra;
	}

	public String getEscalera() {
		return Escalera;
	}

	public void setEscalera(String escalera) {
		Escalera = escalera;
	}

	public String getPiso() {
		return Piso;
	}

	public void setPiso(String piso) {
		Piso = piso;
	}

	public String getPuerta() {
		return Puerta;
	}

	public void setPuerta(String puerta) {
		Puerta = puerta;
	}



	public String getId_Provincia() {
		return Id_Provincia;
	}

	public void setId_Provincia(String idProvincia) {
		Id_Provincia = idProvincia;
	}

	public String getId_municipio() {
		return id_municipio;
	}

	public void setId_municipio(String idMunicipio) {
		id_municipio = idMunicipio;
	}

	public String getProvincia() {
		return Provincia;
	}

	public void setProvincia(String provincia) {
		Provincia = provincia;
	}


	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCod_postal() {
		return cod_postal;
	}

	public void setCod_postal(String codPostal) {
		cod_postal = codPostal;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo_electronico() {
		return correo_electronico;
	}

	public void setCorreo_electronico(String correoElectronico) {
		correo_electronico = correoElectronico;
	}

	public Timestamp getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Timestamp fechaInicio) {
		fecha_inicio = fechaInicio;
	}

	public Timestamp getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Timestamp fechaFin) {
		fecha_fin = fechaFin;
	}
	
	// INCIDENCIA MANTIS 6019 : Se muestra el alias para la consulta desde el Administrador

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	
	
	
}
