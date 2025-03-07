package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BeanPQTramiteTraficoGuardarIntervinienteImport extends BeanPQGeneral {
	public BigDecimal P_ID_USUARIO;
	public BigDecimal P_NUM_EXPEDIENTE;
	public BigDecimal P_ID_CONTRATO;
	public BigDecimal P_ID_CONTRATO_SESSION;

	// Informacion del interviniente
	public String P_TIPO_INTERVINIENTE;
	public String P_CAMBIO_DOMICILIO;
	public String P_AUTONOMO;
	public String P_CODIGO_IAE;
	public Timestamp P_FECHA_INICIO;
	public Timestamp P_FECHA_FIN;
	public String P_CONCEPTO_REPRE;
	public String P_ID_MOTIVO_TUTELA;
	public String P_HORA_INICIO;
	public String P_HORA_FIN;
	public String P_DATOS_DOCUMENTO;

	// Información de la persona asociada al involucrado
	public String P_NUM_COLEGIADO;
	public String P_NIF;
	public BigDecimal P_ESTADO;
	public String P_TIPO_PERSONA;
	public String P_APELLIDO1_RAZON_SOCIAL;
	public String P_APELLIDO2;
	public String P_NOMBRE;
	public String P_ANAGRAMA;
	public Timestamp P_FECHA_NACIMIENTO;
	public String P_TELEFONOS;
	public String P_ESTADO_CIVIL;
	public String P_SEXO;
	public String P_CORREO_ELECTRONICO;
	private Timestamp P_FECHA_CADUCIDAD_NIF;
	private Timestamp P_FECHA_CADUCIDAD_ALTERNATIVO;
	private String P_TIPO_DOCUMENTO_ALTERNATIVO;
	private String P_INDEFINIDO;

	// Información de la direccion
	public BigDecimal P_ID_DIRECCION;
	public String P_ID_PROVINCIA;
	public String P_ID_MUNICIPIO;
	public String P_ID_TIPO_VIA;
	public String P_NOMBRE_VIA;
	public String P_NUMERO;
	public String P_COD_POSTAL;
	public String P_PUEBLO;
	public String P_LETRA;
	public String P_ESCALERA;
	public String P_BLOQUE;
	public String P_PLANTA;
	public String P_PUERTA;
	public BigDecimal P_NUM_LOCAL;
	public BigDecimal P_KM;
	public BigDecimal P_HM;
	// Mantis 17701. David Sierra
	public String P_DIRECCION_ACTIVA;

	// Parámetros para la importación del fichero
	public String P_ID_TIPO_DGT;
	public String P_MUNICIPIO;
	public String P_PUEBLO_LIT;
	public String P_VIA;
	public BigDecimal P_NUM_TITULARES;
	private BigDecimal P_NUM_INTERVINIENTE;

	//Información devuelta
	//public String P_INFORMACION; //Lo quitamos porque el BeanGeneral ya lo tiene, al igual que P_CODE y P_SQLERRM
	public BigDecimal P_PODERES_EN_FICHA;
	public String P_FA;
	public String P_CODIGO_MANDATO;

	// GET & SET
	public BeanPQTramiteTraficoGuardarIntervinienteImport() {
		//Por defecto que el número de Interviniente sea el 1
		this.setP_NUM_INTERVINIENTE(new BigDecimal(1));
	}
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
	public String getP_AUTONOMO() {
		return P_AUTONOMO;
	}
	public void setP_AUTONOMO(String pAUTONOMO) {
		P_AUTONOMO = pAUTONOMO;
	}
	public String getP_CODIGO_IAE() {
		return P_CODIGO_IAE;
	}
	public void setP_CODIGO_IAE(String pCODIGOIAE) {
		P_CODIGO_IAE = pCODIGOIAE;
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
	public String getP_MUNICIPIO() {
		return P_MUNICIPIO;
	}
	public void setP_MUNICIPIO(String pMUNICIPIO) {
		P_MUNICIPIO = pMUNICIPIO;
	}
	public String getP_ID_TIPO_DGT() {
		return P_ID_TIPO_DGT;
	}
	public void setP_ID_TIPO_DGT(String pIDTIPODGT) {
		P_ID_TIPO_DGT = pIDTIPODGT;
	}
	public String getP_PUEBLO_LIT() {
		return P_PUEBLO_LIT;
	}
	public void setP_PUEBLO_LIT(String pPUEBLOLIT) {
		P_PUEBLO_LIT = pPUEBLOLIT;
	}
	public String getP_VIA() {
		return P_VIA;
	}
	public void setP_VIA(String pVIA) {
		P_VIA = pVIA;
	}
/*	public String getP_INFORMACION() {
		return P_INFORMACION;
	}
	public void setP_INFORMACION(String pINFORMACION) {
		P_INFORMACION = pINFORMACION;
	}*/
	public BigDecimal getP_NUM_TITULARES() {
		return P_NUM_TITULARES;
	}
	public void setP_NUM_TITULARES(BigDecimal p_NUM_TITULARES) {
		P_NUM_TITULARES = p_NUM_TITULARES;
	}
	public BigDecimal getP_NUM_INTERVINIENTE() {
		return P_NUM_INTERVINIENTE;
	}
	public void setP_NUM_INTERVINIENTE(BigDecimal p_NUM_INTERVINIENTE) {
		P_NUM_INTERVINIENTE = p_NUM_INTERVINIENTE;
	}
	public BigDecimal getP_PODERES_EN_FICHA() {
		return P_PODERES_EN_FICHA;
	}
	public void setP_PODERES_EN_FICHA(BigDecimal pPODERESENFICHA) {
		P_PODERES_EN_FICHA = pPODERESENFICHA;
	}
	public String getP_FA() {
		return P_FA;
	}
	public void setP_FA(String pFA) {
		P_FA = pFA;
	}
	public String getP_CODIGO_MANDATO() {
		return P_CODIGO_MANDATO;
	}
	public void setP_CODIGO_MANDATO(String pCODIGOMANDATO) {
		P_CODIGO_MANDATO = pCODIGOMANDATO;
	}
	public String getP_DIRECCION_ACTIVA() {
		return P_DIRECCION_ACTIVA;
	}
	public void setP_DIRECCION_ACTIVA(String p_DIRECCION_ACTIVA) {
		P_DIRECCION_ACTIVA = p_DIRECCION_ACTIVA;
	}

}