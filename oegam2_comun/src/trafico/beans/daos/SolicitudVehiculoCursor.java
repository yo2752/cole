package trafico.beans.daos;

import java.math.BigDecimal;

/**
 * Cursos para la lista de solicitudes de vehiculos para la pestaña de vehículos de la pantalla
 * @author rocio.martin
 */
public class SolicitudVehiculoCursor {

	private String MATRICULA;
	private String BASTIDOR;
	private BigDecimal NUM_EXPEDIENTE;
	private String NUM_COLEGIADO;
	private BigDecimal ID_VEHICULO;
	private String CODIGO_TASA;
	private String RESULTADO;
	private BigDecimal ESTADO;
	private String REFERENCIA_ATEM;
	private String MOTIVO_INTEVE;
	private String NIVE;
	private String TIPO_INFORME;

	public BigDecimal getNUM_EXPEDIENTE() {
		return NUM_EXPEDIENTE;
	}

	public void setNUM_EXPEDIENTE(BigDecimal nUMEXPEDIENTE) {
		NUM_EXPEDIENTE = nUMEXPEDIENTE;
	}

	public String getNUM_COLEGIADO() {
		return NUM_COLEGIADO;
	}

	public void setNUM_COLEGIADO(String nUMCOLEGIADO) {
		NUM_COLEGIADO = nUMCOLEGIADO;
	}

	public BigDecimal getID_VEHICULO() {
		return ID_VEHICULO;
	}

	public void setID_VEHICULO(BigDecimal iDVEHICULO) {
		ID_VEHICULO = iDVEHICULO;
	}

	public String getCODIGO_TASA() {
		return CODIGO_TASA;
	}

	public void setCODIGO_TASA(String cODIGOTASA) {
		CODIGO_TASA = cODIGOTASA;
	}

	public String getRESULTADO() {
		return RESULTADO;
	}

	public void setRESULTADO(String rESULTADO) {
		RESULTADO = rESULTADO;
	}

	public String getMATRICULA() {
		return MATRICULA;
	}

	public void setMATRICULA(String mATRICULA) {
		MATRICULA = mATRICULA;
	}

	public String getBASTIDOR() {
		return BASTIDOR;
	}

	public void setBASTIDOR(String bASTIDOR) {
		BASTIDOR = bASTIDOR;
	}

	public BigDecimal getESTADO() {
		return ESTADO;
	}

	public void setESTADO(BigDecimal eSTADO) {
		ESTADO = eSTADO;
	}

	public String getREFERENCIA_ATEM() {
		return REFERENCIA_ATEM;
	}

	public void setREFERENCIA_ATEM(String rEFERENCIA_ATEM) {
		REFERENCIA_ATEM = rEFERENCIA_ATEM;
	}

	public String getMOTIVO_INTEVE() {
		return MOTIVO_INTEVE;
	}

	public void setMOTIVO_INTEVE(String mOTIVO_INTEVE) {
		MOTIVO_INTEVE = mOTIVO_INTEVE;
	}

	public String getNIVE() {
		return NIVE;
	}

	public void setNIVE(String nIVE) {
		NIVE = nIVE;
	}

	public String getTIPO_INFORME() {
		return TIPO_INFORME;
	}

	public void setTIPO_INFORME(String tIPO_INFORME) {
		TIPO_INFORME = tIPO_INFORME;
	}
}