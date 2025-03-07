package trafico.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Cursor de busqueda de tramites de baja pendientes envio excel
 * @author javier.folgueras Open C_Usuarios For SELECT t.NUM_COLEGIADO, t.NUM_EXPEDIENTE, b.MOTIVO_BAJA, t.CODIGO_TASA,t.JEFATURA_PROVINCIAL, t.ID_USUARIO, decode(p.tipo_persona,''JURIDICA'',''CIF'',''NIF/NIE'') tipo_documento, p.nif, cv.nif nif_COMPRAVENTA, v.matricula, v.fecha_prim_matri, v.fecha_itv,
 *         decode(b.impr_permiso_circu,''SI'',''PERMISO CIRCULACIÓN'',''AVIO'') permiso_informe, b.declaracion_residuo declaracion_no_residuo, b.declaracion_no_entrega_catv, b.carta_dgt_vehiculo_mas_diez_an b.tasa_duplicado, t.anotaciones,b.dni_cotitulares dni_cotitulares,t.fecha_presentacion
 */

public class ExcelBajasCursor {

	String NUM_COLEGIADO;
	
	BigDecimal NUM_EXPEDIENTE;
	
	BigDecimal ID_USUARIO;
	
	String MOTIVO_BAJA;
	
	String CODIGO_TASA;
	
	String JEFATURA_PROVINCIAL;
	
	String tipo_documento;
	
	String nif;
	
	String nif_COMPRAVENTA;
	
	String matricula;
	
	Timestamp fecha_matriculacion;
	
	Timestamp fecha_itv;
	
	String permiso_informe;
	String declaracion_no_residuo;
	
	String declaracion_no_entrega_catv;
	
	String carta_dgt_vehiculo_mas_diez_an;
	
	String certificado_medioambiental;
	
	String dni_Cotitulares;
	
	String tasa_duplicado;
	
	String anotaciones;
	
	Timestamp fecha_presentacion;

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

	public BigDecimal getID_USUARIO() {
		return ID_USUARIO;
	}

	public void setID_USUARIO(BigDecimal iD_USUARIO) {
		ID_USUARIO = iD_USUARIO;
	}

	public String getMOTIVO_BAJA() {
		return MOTIVO_BAJA;
	}

	public void setMOTIVO_BAJA(String mOTIVOBAJA) {
		MOTIVO_BAJA = mOTIVOBAJA;
	}

	public String getCODIGO_TASA() {
		return CODIGO_TASA;
	}

	public void setCODIGO_TASA(String cODIGOTASA) {
		CODIGO_TASA = cODIGOTASA;
	}

	public String getJEFATURA_PROVINCIAL() {
		return JEFATURA_PROVINCIAL;
	}

	public void setJEFATURA_PROVINCIAL(String jEFATURA_PROVINCIAL) {
		JEFATURA_PROVINCIAL = jEFATURA_PROVINCIAL;
	}

	public String getTipo_documento() {
		return tipo_documento;
	}

	public void setTipo_documento(String tipoDocumento) {
		tipo_documento = tipoDocumento;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
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

	public void setFecha_matriculacion(Timestamp fecha_matriculacion) {
		this.fecha_matriculacion = fecha_matriculacion;
	}

	public Timestamp getFecha_itv() {
		return fecha_itv;
	}

	public void setFecha_itv(Timestamp fechaItv) {
		fecha_itv = fechaItv;
	}

	public String getDeclaracion_no_residuo() {
		return declaracion_no_residuo;
	}

	public void setDeclaracion_no_residuo(String declaracionNoResiduo) {
		declaracion_no_residuo = declaracionNoResiduo;
	}

	public String getDeclaracion_no_entrega_catv() {
		return declaracion_no_entrega_catv;
	}

	public void setDeclaracion_no_entrega_catv(String declaracion_no_entrega_catv) {
		this.declaracion_no_entrega_catv = declaracion_no_entrega_catv;
	}

	public String getCarta_dgt_vehiculo_mas_diez_an() {
		return carta_dgt_vehiculo_mas_diez_an;
	}

	public void setCarta_dgt_vehiculo_mas_diez_an(String carta_dgt_vehiculo_mas_diez_an) {
		this.carta_dgt_vehiculo_mas_diez_an = carta_dgt_vehiculo_mas_diez_an;
	}

	public String getCertificado_medioambiental() {
		return certificado_medioambiental;
	}

	public void setCertificado_medioambiental(String certificado_medioambiental) {
		this.certificado_medioambiental = certificado_medioambiental;
	}

	public String getdniCotitulares() {
		return dni_Cotitulares;
	}

	public void setdniCotitulares(String dnicotitulares) {
		this.dni_Cotitulares = dnicotitulares;
	}

	public String getPermiso_informe() {
		return permiso_informe;
	}

	public void setPermiso_informe(String permisoInforme) {
		permiso_informe = permisoInforme;
	}

	public String getTasa_duplicado() {
		return tasa_duplicado;
	}

	public void setTasa_duplicado(String tasaDuplicado) {
		tasa_duplicado = tasaDuplicado;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public String getNif_COMPRAVENTA() {
		return nif_COMPRAVENTA;
	}

	public void setNif_COMPRAVENTA(String nifCOMPRAVENTA) {
		nif_COMPRAVENTA = nifCOMPRAVENTA;
	}

	public Timestamp getFecha_presentacion() {
		return fecha_presentacion;
	}

	public void setFecha_presentacion(Timestamp fechaPresentacion) {
		fecha_presentacion = fechaPresentacion;
	}
}
