package trafico.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

/*
 * Cursor de busqueda de tramites de duplicados pendientes envio Excel
 * 'SELECT t.NUM_COLEGIADO, t.NUM_EXPEDIENTE, d.MOTIVO_DUPLICADO, t.CODIGO_TASA, 
      decode(p.tipo_persona,''JURIDICA'',''CIF'',''NIF/NIE'') TIPO_DOCUMENTO, p.nif, ct.nif nif_COTITULAR,
      d.nombre_via, d.numero, d.letra, d.escalera, d.bloque, d.planta, d.puerta, d.num_local, d.km, d.hm, 
      d.id_municipio, m.nombre municipio, d.id_provincia, pr.nombre provincia, d.cod_postal
      v.matricula, v.fecha_matriculacion, v.fecha_itv,t.fecha_presentacion 
      nvl(d.impr_permiso_circu,''NO'') IMPR_PERMISO_CIRCU, nvl(d.importacion,''NO'') IMPORTACION, d.tasa_importacion, t.anotaciones
      FROM tramite_trafico t, tramite_traf_duplicado d, vehiculo v, persona p, interviniente_trafico i, persona ct, interviniente_trafico ic, 
      direccion d, municipio m, provincia pr '; 
 */


public class ExcelDuplicadosCursor {
	
	String NUM_COLEGIADO;
	BigDecimal NUM_EXPEDIENTE;
	String MOTIVO_DUPLICADO;
	String CODIGO_TASA;
	String TIPO_DOCUMENTO;
	String nif;
	String nif_COTITULAR;
	String id_tipo_via;
	String nombre_via;
	String numero;
	String letra;
	String escalera;
	String bloque;
	String planta;
	String puerta;
	String num_local;
	String km;
	String hm;
	String id_municipio;
	String municipio;
	String localidad;
	String id_provincia;
	String provincia;
	String cod_postal;
	String matricula;
	Timestamp fecha_matriculacion;
	Timestamp fecha_itv;
	Timestamp fecha_presentacion;
	String IMPR_PERMISO_CIRCU;
	String IMPORTACION;
	String tasa_importacion;
	String anotaciones;
	String JEFATURA_PROVINCIAL;
	
	public String getNUM_COLEGIADO() {
		return NUM_COLEGIADO;
	}
	public void setNUM_COLEGIADO(String nUMCOLEGIADO) {
		NUM_COLEGIADO = nUMCOLEGIADO;
	}
	public BigDecimal getNUM_EXPEDIENTE() {
		return NUM_EXPEDIENTE;
	}
	public void setNUM_EXPEDIENTE(BigDecimal nUMEXPEDIENTE) {
		NUM_EXPEDIENTE = nUMEXPEDIENTE;
	}
	public String getCODIGO_TASA() {
		return CODIGO_TASA;
	}
	public void setCODIGO_TASA(String cODIGOTASA) {
		CODIGO_TASA = cODIGOTASA;
	}
	public String getTIPO_DOCUMENTO() {
		return TIPO_DOCUMENTO;
	}
	public void setTIPO_DOCUMENTO(String tIPODOCUMENTO) {
		TIPO_DOCUMENTO = tIPODOCUMENTO;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNif_COTITULAR() {
		return nif_COTITULAR;
	}
	public void setNif_COTITULAR(String nifCOTITULAR) {
		nif_COTITULAR = nifCOTITULAR;
	}
	
	public String getId_Tipo_via() {
		return id_tipo_via;
	}
	public void setId_Tipo_via(String id_tipo_via) {
		this.id_tipo_via = id_tipo_via;
	}
		
	public String getNombre_via() {
		return nombre_via;
	}
	public void setNombre_via(String nombreVia) {
		nombre_via = nombreVia;
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
	public String getBloque() {
		return bloque;
	}
	public void setBloque(String bloque) {
		this.bloque = bloque;
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
	public String getNum_local() {
		return num_local;
	}
	public void setNum_local(String numLocal) {
		num_local = numLocal;
	}
	public String getKm() {
		return km;
	}
	public void setKm(String km) {
		this.km = km;
	}
	public String getHm() {
		return hm;
	}
	public void setHm(String hm) {
		this.hm = hm;
	}
	public String getId_municipio() {
		return id_municipio;
	}
	public void setId_municipio(String idMunicipio) {
		id_municipio = idMunicipio;
	}
	
	public String getId_provincia() {
		return id_provincia;
	}
	public void setId_provincia(String idProvincia) {
		id_provincia = idProvincia;
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public Timestamp getFecha_matriculacion() {
		return fecha_matriculacion;
	}
	public void setFecha_matriculacion(Timestamp fechaMatriculacion) {
		fecha_matriculacion = fechaMatriculacion;
	}
	public Timestamp getFecha_itv() {
		return fecha_itv;
	}
	
	// mofidicacion sitex 20/11/2012 atc
	public Timestamp getFecha_presentacion() {
		return fecha_presentacion;
	}
	public void setFecha_presentacion(Timestamp fechaPresentacion) {
		fecha_presentacion = fechaPresentacion;
	}
	
	//fin modificacion
	
	public void setFecha_itv(Timestamp fechaItv) {
		fecha_itv = fechaItv;
	}
	public String getIMPR_PERMISO_CIRCU() {
		return IMPR_PERMISO_CIRCU;
	}
	public void setIMPR_PERMISO_CIRCU(String iMPRPERMISOCIRCU) {
		IMPR_PERMISO_CIRCU = iMPRPERMISOCIRCU;
	}
	public String getIMPORTACION() {
		return IMPORTACION;
	}
	public void setIMPORTACION(String iMPORTACION) {
		IMPORTACION = iMPORTACION;
	}
	public String getTasa_importacion() {
		return tasa_importacion;
	}
	public void setTasa_importacion(String tasaImportacion) {
		tasa_importacion = tasaImportacion;
	}
	public String getAnotaciones() {
		return anotaciones;
	}
	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	
	}
	public String getMOTIVO_DUPLICADO() {
		return MOTIVO_DUPLICADO;
	}
	public void setMOTIVO_DUPLICADO(String mOTIVODUPLICADO) {
		MOTIVO_DUPLICADO = mOTIVODUPLICADO;
	}
	public String getCod_postal() {
		return cod_postal;
	}
	public void setCod_postal(String codPostal) {
		cod_postal = codPostal;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public String getJEFATURA_PROVINCIAL() {
		return JEFATURA_PROVINCIAL;
	}
	public void setJEFATURA_PROVINCIAL(String jEFATURA_PROVINCIAL) {
		this.JEFATURA_PROVINCIAL = jEFATURA_PROVINCIAL;
	}
	
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	

}
