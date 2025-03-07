package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BeanPQTramiteTraficoGuardarInterviniente extends BeanPQGeneral {
	private BigDecimal P_ID_USUARIO;
	private BigDecimal P_NUM_EXPEDIENTE;
	private BigDecimal P_ID_CONTRATO;
	private BigDecimal P_ID_CONTRATO_SESSION;
	//Informacion del interviniente
	private String P_TIPO_INTERVINIENTE;
	private String P_CAMBIO_DOMICILIO;
	private String P_AUTONOMO;
	private BigDecimal P_ESTADO_TRAM;
	private String P_CODIGO_IAE;
	private Timestamp P_FECHA_INICIO;
	private Timestamp P_FECHA_FIN;
	private String P_CONCEPTO_REPRE;
	private String P_ID_MOTIVO_TUTELA;
	private String P_HORA_INICIO;
	private String P_HORA_FIN;
	private String P_DATOS_DOCUMENTO;
	//Información de la persona asociada al involucrado
	private String P_NUM_COLEGIADO;
	private String P_NIF;
	private BigDecimal P_ESTADO;
	private String P_TIPO_PERSONA;
	private String P_APELLIDO1_RAZON_SOCIAL;
	private String P_APELLIDO2;
	private String P_NOMBRE;
	private String P_ANAGRAMA;
	private String P_CODIGO_MANDATO;
	private BigDecimal P_PODERES_EN_FICHA;
	private Timestamp P_FECHA_NACIMIENTO;
	private String P_TELEFONOS;
	private String P_ESTADO_CIVIL;
	private String P_SEXO;
	private String P_CORREO_ELECTRONICO;
	private String P_FA;
	private Timestamp P_FECHA_CADUCIDAD_NIF;
	private Timestamp P_FECHA_CADUCIDAD_ALTERNATIVO;
	private String P_TIPO_DOCUMENTO_ALTERNATIVO;
	private String P_INDEFINIDO;
	//Información de la direccion
	private BigDecimal P_ID_DIRECCION;
	private String P_ID_PROVINCIA;
	private String P_ID_MUNICIPIO;
	private String P_ID_TIPO_VIA;
	private String P_NOMBRE_VIA;
	private String P_NUMERO;
	private String P_COD_POSTAL;
	private String P_PUEBLO;
	private String P_LETRA;
	private String P_ESCALERA;
	private String P_BLOQUE;
	private String P_PLANTA;
	private String P_PUERTA;
	private BigDecimal P_NUM_LOCAL;
	private BigDecimal P_KM;
	private BigDecimal P_HM;
	private String P_DIRECCION_ACTIVA;

	//Información devuelta

	private BigDecimal P_NUM_INTERVINIENTE;

	// CONSTRUCTORES
	public BeanPQTramiteTraficoGuardarInterviniente() {
		//Por defecto que el número de Interviniente sea el 1
		this.setP_NUM_INTERVINIENTE(new BigDecimal(1));
	}

	// SETTERS & GETTERS

	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}
	public void setP_ID_USUARIO(BigDecimal pIDUSUARIO) {
		P_ID_USUARIO = pIDUSUARIO;
	}
	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}
	public void setP_NUM_EXPEDIENTE(BigDecimal pNUMEXPEDIENTE) {
		P_NUM_EXPEDIENTE = pNUMEXPEDIENTE;
	}
	public BigDecimal getP_ID_CONTRATO() {
		return P_ID_CONTRATO;
	}
	public void setP_ID_CONTRATO(BigDecimal pIDCONTRATO) {
		P_ID_CONTRATO = pIDCONTRATO;
	}
	public BigDecimal getP_ID_CONTRATO_SESSION() {
		return P_ID_CONTRATO_SESSION;
	}
	public void setP_ID_CONTRATO_SESSION(BigDecimal pIDCONTRATO_SESSION) {
		P_ID_CONTRATO_SESSION = pIDCONTRATO_SESSION;
	}
	public String getP_TIPO_INTERVINIENTE() {
		return P_TIPO_INTERVINIENTE;
	}
	public void setP_TIPO_INTERVINIENTE(String pTIPOINTERVINIENTE) {
		P_TIPO_INTERVINIENTE = pTIPOINTERVINIENTE;
	}
	public String getP_CAMBIO_DOMICILIO() {
		return P_CAMBIO_DOMICILIO;
	}
	public void setP_CAMBIO_DOMICILIO(String pCAMBIODOMICILIO) {
		P_CAMBIO_DOMICILIO = pCAMBIODOMICILIO;
	}

	public String getP_CODIGO_IAE() {
		return P_CODIGO_IAE;
	}
	public void setP_CODIGO_IAE(String pCODIGOIAE) {
		P_CODIGO_IAE = pCODIGOIAE;
	}
	public String getP_AUTONOMO() {
		return P_AUTONOMO;
	}

	public void setP_AUTONOMO(String pAUTONOMO) {
		P_AUTONOMO = pAUTONOMO;
	}
	public Timestamp getP_FECHA_INICIO() {
		return P_FECHA_INICIO;
	}
	public void setP_FECHA_INICIO(Timestamp pFECHAINICIO) {
		P_FECHA_INICIO = pFECHAINICIO;
	}
	public Timestamp getP_FECHA_FIN() {
		return P_FECHA_FIN;
	}
	public void setP_FECHA_FIN(Timestamp pFECHAFIN) {
		P_FECHA_FIN = pFECHAFIN;
	}
	public String getP_CONCEPTO_REPRE() {
		return P_CONCEPTO_REPRE;
	}
	public void setP_CONCEPTO_REPRE(String pCONCEPTOREPRE) {
		P_CONCEPTO_REPRE = pCONCEPTOREPRE;
	}
	public String getP_ID_MOTIVO_TUTELA() {
		return P_ID_MOTIVO_TUTELA;
	}
	public void setP_ID_MOTIVO_TUTELA(String pIDMOTIVOTUTELA) {
		P_ID_MOTIVO_TUTELA = pIDMOTIVOTUTELA;
	}
	public String getP_HORA_INICIO() {
		return P_HORA_INICIO;
	}
	public void setP_HORA_INICIO(String pHORAINICIO) {
		P_HORA_INICIO = pHORAINICIO;
	}
	public String getP_HORA_FIN() {
		return P_HORA_FIN;
	}
	public void setP_HORA_FIN(String pHORAFIN) {
		P_HORA_FIN = pHORAFIN;
	}
	public String getP_DATOS_DOCUMENTO() {
		return P_DATOS_DOCUMENTO;
	}
	public void setP_DATOS_DOCUMENTO(String pDATOSDOCUMENTO) {
		P_DATOS_DOCUMENTO = pDATOSDOCUMENTO;
	}
	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}
	public void setP_NUM_COLEGIADO(String pNUMCOLEGIADO) {
		P_NUM_COLEGIADO = pNUMCOLEGIADO;
	}
	public String getP_NIF() {
		return P_NIF;
	}
	public void setP_NIF(String pNIF) {
		P_NIF = pNIF;
	}
	public BigDecimal getP_ESTADO() {
		return P_ESTADO;
	}
	public void setP_ESTADO(BigDecimal pESTADO) {
		P_ESTADO = pESTADO;
	}
	public String getP_TIPO_PERSONA() {
		return P_TIPO_PERSONA;
	}
	public void setP_TIPO_PERSONA(String pTIPOPERSONA) {
		P_TIPO_PERSONA = pTIPOPERSONA;
	}
	public String getP_APELLIDO1_RAZON_SOCIAL() {
		return P_APELLIDO1_RAZON_SOCIAL;
	}
	public void setP_APELLIDO1_RAZON_SOCIAL(String pAPELLIDO1RAZONSOCIAL) {
		P_APELLIDO1_RAZON_SOCIAL = pAPELLIDO1RAZONSOCIAL;
	}
	public String getP_APELLIDO2() {
		return P_APELLIDO2;
	}
	public void setP_APELLIDO2(String pAPELLIDO2) {
		P_APELLIDO2 = pAPELLIDO2;
	}
	public String getP_NOMBRE() {
		return P_NOMBRE;
	}
	public void setP_NOMBRE(String pNOMBRE) {
		P_NOMBRE = pNOMBRE;
	}
	public String getP_ANAGRAMA() {
		return P_ANAGRAMA;
	}
	public void setP_ANAGRAMA(String pANAGRAMA) {
		P_ANAGRAMA = pANAGRAMA;
	}

	public String getP_CODIGO_MANDATO() {
		return P_CODIGO_MANDATO;
	}

	public void setP_CODIGO_MANDATO(String pCODIGOMANDATO) {
		P_CODIGO_MANDATO = pCODIGOMANDATO;
	}

	public BigDecimal getP_PODERES_EN_FICHA() {
		return P_PODERES_EN_FICHA;
	}

	public void setP_PODERES_EN_FICHA(BigDecimal pPODERESENFICHA) {
		P_PODERES_EN_FICHA = pPODERESENFICHA;
	}

	public Timestamp getP_FECHA_NACIMIENTO() {
		return P_FECHA_NACIMIENTO;
	}
	public void setP_FECHA_NACIMIENTO(Timestamp pFECHANACIMIENTO) {
		P_FECHA_NACIMIENTO = pFECHANACIMIENTO;
	}
	public String getP_TELEFONOS() {
		return P_TELEFONOS;
	}
	public void setP_TELEFONOS(String pTELEFONOS) {
		P_TELEFONOS = pTELEFONOS;
	}
	public String getP_ESTADO_CIVIL() {
		return P_ESTADO_CIVIL;
	}
	public void setP_ESTADO_CIVIL(String pESTADOCIVIL) {
		P_ESTADO_CIVIL = pESTADOCIVIL;
	}
	public String getP_SEXO() {
		return P_SEXO;
	}
	public void setP_SEXO(String pSEXO) {
		P_SEXO = pSEXO;
	}
	public String getP_CORREO_ELECTRONICO() {
		return P_CORREO_ELECTRONICO;
	}
	public void setP_CORREO_ELECTRONICO(String pCORREOELECTRONICO) {
		P_CORREO_ELECTRONICO = pCORREOELECTRONICO;
	}
	public BigDecimal getP_ID_DIRECCION() {
		return P_ID_DIRECCION;
	}
	public void setP_ID_DIRECCION(BigDecimal pIDDIRECCION) {
		P_ID_DIRECCION = pIDDIRECCION;
	}
	public String getP_ID_PROVINCIA() {
		return P_ID_PROVINCIA;
	}
	public void setP_ID_PROVINCIA(String pIDPROVINCIA) {
		P_ID_PROVINCIA = pIDPROVINCIA;
	}
	public String getP_ID_MUNICIPIO() {
		return P_ID_MUNICIPIO;
	}
	public void setP_ID_MUNICIPIO(String pIDMUNICIPIO) {
		P_ID_MUNICIPIO = pIDMUNICIPIO;
	}
	public String getP_ID_TIPO_VIA() {
		return P_ID_TIPO_VIA;
	}
	public void setP_ID_TIPO_VIA(String pIDTIPOVIA) {
		P_ID_TIPO_VIA = pIDTIPOVIA;
	}
	public String getP_NOMBRE_VIA() {
		return P_NOMBRE_VIA;
	}
	public void setP_NOMBRE_VIA(String pNOMBREVIA) {
		P_NOMBRE_VIA = pNOMBREVIA;
	}
	public String getP_NUMERO() {
		return P_NUMERO;
	}
	public void setP_NUMERO(String pNUMERO) {
		P_NUMERO = pNUMERO;
	}
	public String getP_COD_POSTAL() {
		return P_COD_POSTAL;
	}
	public void setP_COD_POSTAL(String pCODPOSTAL) {
		P_COD_POSTAL = pCODPOSTAL;
	}
	public String getP_PUEBLO() {
		return P_PUEBLO;
	}
	public void setP_PUEBLO(String pPUEBLO) {
		P_PUEBLO = pPUEBLO;
	}
	public String getP_LETRA() {
		return P_LETRA;
	}
	public void setP_LETRA(String pLETRA) {
		P_LETRA = pLETRA;
	}
	public String getP_ESCALERA() {
		return P_ESCALERA;
	}
	public void setP_ESCALERA(String pESCALERA) {
		P_ESCALERA = pESCALERA;
	}
	public String getP_BLOQUE() {
		return P_BLOQUE;
	}
	public void setP_BLOQUE(String pBLOQUE) {
		P_BLOQUE = pBLOQUE;
	}
	public String getP_PLANTA() {
		return P_PLANTA;
	}
	public void setP_PLANTA(String pPLANTA) {
		P_PLANTA = pPLANTA;
	}
	public String getP_PUERTA() {
		return P_PUERTA;
	}
	public void setP_PUERTA(String pPUERTA) {
		P_PUERTA = pPUERTA;
	}
	public BigDecimal getP_NUM_LOCAL() {
		return P_NUM_LOCAL;
	}
	public void setP_NUM_LOCAL(BigDecimal pNUMLOCAL) {
		P_NUM_LOCAL = pNUMLOCAL;
	}
	public BigDecimal getP_KM() {
		return P_KM;
	}
	public void setP_KM(BigDecimal pKM) {
		P_KM = pKM;
	}
	public BigDecimal getP_HM() {
		return P_HM;
	}
	public void setP_HM(BigDecimal pHM) {
		P_HM = pHM;
	}

	public BigDecimal getP_ESTADO_TRAM() {
		return P_ESTADO_TRAM;
	}

	public void setP_ESTADO_TRAM(BigDecimal pESTADOTRAM) {
		P_ESTADO_TRAM = pESTADOTRAM;
	}

	public String getP_FA() {
		return P_FA;
	}

	public void setP_FA(String p_FA) {
		P_FA = p_FA;
	}

	public Timestamp getP_FECHA_CADUCIDAD_NIF() {
		return P_FECHA_CADUCIDAD_NIF;
	}

	public void setP_FECHA_CADUCIDAD_NIF(Timestamp p_FECHA_CADUCIDAD_NIF) {
		P_FECHA_CADUCIDAD_NIF = p_FECHA_CADUCIDAD_NIF;
	}

	public Timestamp getP_FECHA_CADUCIDAD_ALTERNATIVO() {
		return P_FECHA_CADUCIDAD_ALTERNATIVO;
	}

	public void setP_FECHA_CADUCIDAD_ALTERNATIVO(
			Timestamp p_FECHA_CADUCIDAD_ALTERNATIVO) {
		P_FECHA_CADUCIDAD_ALTERNATIVO = p_FECHA_CADUCIDAD_ALTERNATIVO;
	}

	public String getP_TIPO_DOCUMENTO_ALTERNATIVO() {
		return P_TIPO_DOCUMENTO_ALTERNATIVO;
	}

	public void setP_TIPO_DOCUMENTO_ALTERNATIVO(String p_TIPO_DOCUMENTO_ALTERNATIVO) {
		P_TIPO_DOCUMENTO_ALTERNATIVO = p_TIPO_DOCUMENTO_ALTERNATIVO;
	}

	public String getP_INDEFINIDO() {
		return P_INDEFINIDO;
	}

	public void setP_INDEFINIDO(String p_INDEFINIDO) {
		P_INDEFINIDO = p_INDEFINIDO;
	}

	public BigDecimal getP_NUM_INTERVINIENTE() {
		return P_NUM_INTERVINIENTE;
	}

	public void setP_NUM_INTERVINIENTE(BigDecimal p_NUM_INTERVINIENTE) {
		P_NUM_INTERVINIENTE = p_NUM_INTERVINIENTE;
	}

	public String getP_DIRECCION_ACTIVA() {
		return P_DIRECCION_ACTIVA;
	}

	public void setP_DIRECCION_ACTIVA(String p_DIRECCION_ACTIVA) {
		P_DIRECCION_ACTIVA = p_DIRECCION_ACTIVA;
	}

}