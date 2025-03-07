package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class IntervinientesCursor {

	/*
	 * i.TIPO_INTERVINIENTE, t.desc_tipo_interv descripcion, i.NIF,
      p.TIPO_PERSONA, p.APELLIDO1_RAZON_SOCIAL, p.APELLIDO2, p.NOMBRE, p.ANAGRAMA, p.FECHA_NACIMIENTO, p.TELEFONOS, p.ESTADO_CIVIL,
      p.SEXO, p.CORREO_ELECTRONICO, p.NACIONALIDAD,
      i.ID_DIRECCION, d.ID_PROVINCIA, d.ID_MUNICIPIO, d.ID_TIPO_VIA, d.NOMBRE_VIA, d.NUMERO, d.COD_POSTAL, d.PUEBLO, d.ESCALERA,
      d.BLOQUE, d.PLANTA, d.PUERTA, d.NUM_LOCAL, d.KM, d.HM, d.LETRA, 
      i.CAMBIO_DOMICILIO, i.AUTONOMO, i.CODIGO_IAE, i.FECHA_INICIO, i.FECHA_FIN, i.CONCEPTO_REPRE, i.ID_MOTIVO_TUTELA, i.HORA_INICIO,
      i.HORA_FIN, i.DATOS_DOCUMENTO*/
	private String TIPO_INTERVINIENTE;
	private String descripcion;
	private String TIPO_PERSONA;
	private String APELLIDO1_RAZON_SOCIAL;
	private String APELLIDO2;
	private String NOMBRE;
	private String ANAGRAMA;
	private Timestamp FECHA_NACIMIENTO;
	private String TELEFONOS;
	private String NUM_COLEGIADO;
	private String NIF;
	private BigDecimal ESTADO;
	private String ESTADO_CIVIL;
	private String SEXO;
	private String CORREO_ELECTRONICO;
	//Información de la dirección
	private BigDecimal ID_DIRECCION;
	private String ID_PROVINCIA;
	private String ID_MUNICIPIO;
	private String ID_TIPO_VIA;
	private String NOMBRE_VIA;
	private String NUMERO;
	private String COD_POSTAL;
	private String PUEBLO;
	private String COD_POSTAL_CORREOS;
	private String PUEBLO_CORREOS;
	private String LETRA;
	private String ESCALERA;
	private String BLOQUE;
	private String PLANTA;
	private String PUERTA;
	private BigDecimal NUM_LOCAL;
	private BigDecimal KM;
	private BigDecimal HM;
	private String  CAMBIO_DOMICILIO;
	private String AUTONOMO;
	private String CODIGO_IAE;
	private Timestamp FECHA_INICIO;
	private Timestamp FECHA_FIN;
	private String CONCEPTO_REPRE;
	private String ID_MOTIVO_TUTELA;
	private String HORA_INICIO;
	private String HORA_FIN;
	private String DATOS_DOCUMENTO;
	private String CODIGO_MANDATO;
	private BigDecimal PODERES_EN_FICHA;
	private BigDecimal NUM_INTERVINIENTE;
	private Date FECHA_CADUCIDAD_NIF;
	private Date FECHA_CADUCIDAD_ALTERNATIVO;
	private String TIPO_DOCUMENTO_ALTERNATIVO;
	private String INDEFINIDO;

	// Nuevos Campos Matw
	private String NACIONALIDAD;

	public BigDecimal getNUM_INTERVINIENTE() {
		return NUM_INTERVINIENTE;
	}
	public void setNUM_INTERVINIENTE(BigDecimal nUM_INTERVINIENTE) {
		NUM_INTERVINIENTE = nUM_INTERVINIENTE;
	}
	public String getTIPO_INTERVINIENTE() {
		return TIPO_INTERVINIENTE;
	}
	public void setTIPO_INTERVINIENTE(String tIPOINTERVINIENTE) {
		TIPO_INTERVINIENTE = tIPOINTERVINIENTE;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTIPO_PERSONA() {
		return TIPO_PERSONA;
	}
	public void setTIPO_PERSONA(String tIPOPERSONA) {
		TIPO_PERSONA = tIPOPERSONA;
	}
	public String getAPELLIDO1_RAZON_SOCIAL() {
		return APELLIDO1_RAZON_SOCIAL;
	}
	public void setAPELLIDO1_RAZON_SOCIAL(String aPELLIDO1RAZONSOCIAL) {
		APELLIDO1_RAZON_SOCIAL = aPELLIDO1RAZONSOCIAL;
	}
	public String getAPELLIDO2() {
		return APELLIDO2;
	}
	public void setAPELLIDO2(String aPELLIDO2) {
		APELLIDO2 = aPELLIDO2;
	}
	public String getNOMBRE() {
		return NOMBRE;
	}
	public void setNOMBRE(String nOMBRE) {
		NOMBRE = nOMBRE;
	}
	public String getANAGRAMA() {
		return ANAGRAMA;
	}
	public void setANAGRAMA(String aNAGRAMA) {
		ANAGRAMA = aNAGRAMA;
	}
	public Timestamp getFECHA_NACIMIENTO() {
		return FECHA_NACIMIENTO;
	}
	public void setFECHA_NACIMIENTO(Timestamp fECHANACIMIENTO) {
		FECHA_NACIMIENTO = fECHANACIMIENTO;
	}
	public String getTELEFONOS() {
		return TELEFONOS;
	}
	public void setTELEFONOS(String tELEFONOS) {
		TELEFONOS = tELEFONOS;
	}
	public String getNUM_COLEGIADO() {
		return NUM_COLEGIADO;
	}
	public void setNUM_COLEGIADO(String nUMCOLEGIADO) {
		NUM_COLEGIADO = nUMCOLEGIADO;
	}
	public String getNIF() {
		return NIF;
	}
	public void setNIF(String nIF) {
		NIF = nIF;
	}
	public BigDecimal getESTADO() {
		return ESTADO;
	}
	public void setESTADO(BigDecimal eSTADO) {
		ESTADO = eSTADO;
	}
	public String getESTADO_CIVIL() {
		return ESTADO_CIVIL;
	}
	public void setESTADO_CIVIL(String eSTADOCIVIL) {
		ESTADO_CIVIL = eSTADOCIVIL;
	}
	public String getSEXO() {
		return SEXO;
	}
	public void setSEXO(String sEXO) {
		SEXO = sEXO;
	}
	public String getCORREO_ELECTRONICO() {
		return CORREO_ELECTRONICO;
	}
	public void setCORREO_ELECTRONICO(String cORREOELECTRONICO) {
		CORREO_ELECTRONICO = cORREOELECTRONICO;
	}
	public BigDecimal getID_DIRECCION() {
		return ID_DIRECCION;
	}
	public void setID_DIRECCION(BigDecimal iDDIRECCION) {
		ID_DIRECCION = iDDIRECCION;
	}
	public String getID_PROVINCIA() {
		return ID_PROVINCIA;
	}
	public void setID_PROVINCIA(String iDPROVINCIA) {
		ID_PROVINCIA = iDPROVINCIA;
	}
	public String getID_MUNICIPIO() {
		return ID_MUNICIPIO;
	}
	public void setID_MUNICIPIO(String iDMUNICIPIO) {
		ID_MUNICIPIO = iDMUNICIPIO;
	}
	public String getID_TIPO_VIA() {
		return ID_TIPO_VIA;
	}
	public void setID_TIPO_VIA(String iDTIPOVIA) {
		ID_TIPO_VIA = iDTIPOVIA;
	}
	public String getNOMBRE_VIA() {
		return NOMBRE_VIA;
	}
	public void setNOMBRE_VIA(String nOMBREVIA) {
		NOMBRE_VIA = nOMBREVIA;
	}
	public String getNUMERO() {
		return NUMERO;
	}
	public void setNUMERO(String nUMERO) {
		NUMERO = nUMERO;
	}
	public String getCOD_POSTAL() {
		return COD_POSTAL;
	}
	public void setCOD_POSTAL(String cODPOSTAL) {
		COD_POSTAL = cODPOSTAL;
	}
	public String getPUEBLO() {
		return PUEBLO;
	}
	public void setPUEBLO(String pUEBLO) {
		PUEBLO = pUEBLO;
	}
	public String getLETRA() {
		return LETRA;
	}
	public void setLETRA(String lETRA) {
		LETRA = lETRA;
	}
	public String getESCALERA() {
		return ESCALERA;
	}
	public void setESCALERA(String eSCALERA) {
		ESCALERA = eSCALERA;
	}
	public String getBLOQUE() {
		return BLOQUE;
	}
	public void setBLOQUE(String bLOQUE) {
		BLOQUE = bLOQUE;
	}
	public String getPLANTA() {
		return PLANTA;
	}
	public void setPLANTA(String pLANTA) {
		PLANTA = pLANTA;
	}
	public String getPUERTA() {
		return PUERTA;
	}
	public void setPUERTA(String pUERTA) {
		PUERTA = pUERTA;
	}
	public BigDecimal getNUM_LOCAL() {
		return NUM_LOCAL;
	}
	public void setNUM_LOCAL(BigDecimal nUMLOCAL) {
		NUM_LOCAL = nUMLOCAL;
	}
	public BigDecimal getKM() {
		return KM;
	}
	public void setKM(BigDecimal kM) {
		KM = kM;
	}
	public BigDecimal getHM() {
		return HM;
	}
	public void setHM(BigDecimal hM) {
		HM = hM;
	}
	public String getCAMBIO_DOMICILIO() {
		return CAMBIO_DOMICILIO;
	}
	public void setCAMBIO_DOMICILIO(String cAMBIODOMICILIO) {
		CAMBIO_DOMICILIO = cAMBIODOMICILIO;
	}
	public String getAUTONOMO() {
		return AUTONOMO;
	}
	public void setAUTONOMO(String aUTONOMO) {
		AUTONOMO = aUTONOMO;
	}
	public String getCODIGO_IAE() {
		return CODIGO_IAE;
	}
	public void setCODIGO_IAE(String cODIGOIAE) {
		CODIGO_IAE = cODIGOIAE;
	}
	public Timestamp getFECHA_INICIO() {
		return FECHA_INICIO;
	}
	public void setFECHA_INICIO(Timestamp fECHAINICIO) {
		FECHA_INICIO = fECHAINICIO;
	}
	public Timestamp getFECHA_FIN() {
		return FECHA_FIN;
	}
	public void setFECHA_FIN(Timestamp fECHAFIN) {
		FECHA_FIN = fECHAFIN;
	}
	public String getCONCEPTO_REPRE() {
		return CONCEPTO_REPRE;
	}
	public void setCONCEPTO_REPRE(String cONCEPTOREPRE) {
		CONCEPTO_REPRE = cONCEPTOREPRE;
	}
	public String getID_MOTIVO_TUTELA() {
		return ID_MOTIVO_TUTELA;
	}
	public void setID_MOTIVO_TUTELA(String iDMOTIVOTUTELA) {
		ID_MOTIVO_TUTELA = iDMOTIVOTUTELA;
	}
	public String getHORA_INICIO() {
		return HORA_INICIO;
	}
	public void setHORA_INICIO(String hORAINICIO) {
		HORA_INICIO = hORAINICIO;
	}
	public String getHORA_FIN() {
		return HORA_FIN;
	}
	public void setHORA_FIN(String hORAFIN) {
		HORA_FIN = hORAFIN;
	}
	public String getDATOS_DOCUMENTO() {
		return DATOS_DOCUMENTO;
	}
	public void setDATOS_DOCUMENTO(String dATOSDOCUMENTO) {
		DATOS_DOCUMENTO = dATOSDOCUMENTO;
	}
	public String getCODIGO_MANDATO() {
		return CODIGO_MANDATO;
	}
	public void setCODIGO_MANDATO(String cODIGOMANDATO) {
		CODIGO_MANDATO = cODIGOMANDATO;
	}
	public BigDecimal getPODERES_EN_FICHA() {
		return PODERES_EN_FICHA;
	}
	public void setPODERES_EN_FICHA(BigDecimal pODERESENFICHA) {
		PODERES_EN_FICHA = pODERESENFICHA;
	}
	public Date getFECHA_CADUCIDAD_NIF() {
		return FECHA_CADUCIDAD_NIF;
	}
	public void setFECHA_CADUCIDAD_NIF(Date fECHA_CADUCIDAD_NIF) {
		FECHA_CADUCIDAD_NIF = fECHA_CADUCIDAD_NIF;
	}
	public Date getFECHA_CADUCIDAD_ALTERNATIVO() {
		return FECHA_CADUCIDAD_ALTERNATIVO;
	}
	public void setFECHA_CADUCIDAD_ALTERNATIVO(Date fECHA_CADUCIDAD_ALTERNATIVO) {
		FECHA_CADUCIDAD_ALTERNATIVO = fECHA_CADUCIDAD_ALTERNATIVO;
	}
	public String getTIPO_DOCUMENTO_ALTERNATIVO() {
		return TIPO_DOCUMENTO_ALTERNATIVO;
	}
	public void setTIPO_DOCUMENTO_ALTERNATIVO(String tIPO_DOCUMENTO_ALTERNATIVO) {
		TIPO_DOCUMENTO_ALTERNATIVO = tIPO_DOCUMENTO_ALTERNATIVO;
	}
	public String getINDEFINIDO() {
		return INDEFINIDO;
	}
	public void setINDEFINIDO(String iNDEFINIDO) {
		INDEFINIDO = iNDEFINIDO;
	}
	public String getNACIONALIDAD() {
		return NACIONALIDAD;
	}
	public void setNACIONALIDAD(String nACIONALIDAD) {
		NACIONALIDAD = nACIONALIDAD;
	}
	public String getCOD_POSTAL_CORREOS() {
		return COD_POSTAL_CORREOS;
	}
	public void setCOD_POSTAL_CORREOS(String cOD_POSTAL_CORREOS) {
		COD_POSTAL_CORREOS = cOD_POSTAL_CORREOS;
	}
	public String getPUEBLO_CORREOS() {
		return PUEBLO_CORREOS;
	}
	public void setPUEBLO_CORREOS(String pUEBLO_CORREOS) {
		PUEBLO_CORREOS = pUEBLO_CORREOS;
	}

}