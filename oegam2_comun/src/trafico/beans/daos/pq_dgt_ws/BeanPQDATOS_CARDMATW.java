package trafico.beans.daos.pq_dgt_ws;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQDATOS_CARDMATW extends BeanPQGeneral {

	public static final String PROCEDURE = "DATOS_CARDMATE";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_DGT_WS, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_DGT_WS, PROCEDURE, null, true);
	}

	private BigDecimal P_NUM_EXPEDIENTE;
	private String P_HAS_FORM_576;
	private String P_HAS_FORM_05;
	private String P_HAS_FORM_06;
	private String P_FORM_05_KEY;
	private String P_FORM_06_KEY;
	private String P_VEHICLE_SERIAL;
	private BigDecimal P_NEW_ITV_CARD;
	// private String P_VEHICLEKIND;
	// private Timestamp P_FISTMATRICULATION;
	private String P_ITV_CARD_TYPE;
//	private BigDecimal P_KMUSED; 
//	private Timestamp P_FISCALREPRESE; 
	private String P_AGENT_FISCAL_ID;
	private String P_EXTERNAL_SYSTEM_FISCAL_ID;
	private String P_CODITV;

	private String P_SERIAL_CARD_ITV;

	// ----------------- GET & SET -----------------
	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE) {
		this.P_NUM_EXPEDIENTE = P_NUM_EXPEDIENTE;
	}

	public String getP_HAS_FORM_576() {
		return P_HAS_FORM_576;
	}

	public void setP_HAS_FORM_576(String P_HASFORM576) {
		this.P_HAS_FORM_576 = P_HASFORM576;
	}

	public String getP_HAS_FORM_05() {
		return P_HAS_FORM_05;
	}

	public void setP_HAS_FORM_05(String P_HASFORM05) {
		this.P_HAS_FORM_05 = P_HASFORM05;
	}

	public String getP_HAS_FORM_06() {
		return P_HAS_FORM_06;
	}

	public void setP_HAS_FORM_06(String P_HASFORM06) {
		this.P_HAS_FORM_06 = P_HASFORM06;
	}

	public String getP_FORM_05_KEY() {
		return P_FORM_05_KEY;
	}

	public void setP_FORM_05_KEY(String P_FORM05KEY) {
		this.P_FORM_05_KEY = P_FORM05KEY;
	}

	public String getP_FORM_06_KEY() {
		return P_FORM_06_KEY;
	}

	public void setP_FORM_06_KEY(String P_FORM06KEY) {
		this.P_FORM_06_KEY = P_FORM06KEY;
	}

	public String getP_VEHICLE_SERIAL() {
		return P_VEHICLE_SERIAL;
	}

	public void setP_VEHICLE_SERIAL(String P_VEHICLESERIAL) {
		this.P_VEHICLE_SERIAL = P_VEHICLESERIAL;
	}

	// public String getP_VEHICLEKIND(){
	// return P_VEHICLEKIND;}

	// public void setP_VEHICLEKIND(String P_VEHICLEKIND){
//		this.P_VEHICLEKIND=P_VEHICLEKIND;}

//	public Timestamp getP_FISTMATRICULATION(){
//		return P_FISTMATRICULATION;}

//	public void setP_FISTMATRICULATION(Timestamp P_FISTMATRICULATION){
//		this.P_FISTMATRICULATION=P_FISTMATRICULATION;}

	public BigDecimal getP_NEW_ITV_CARD() {
		return P_NEW_ITV_CARD;
	}

	public void setP_NEW_ITV_CARD(BigDecimal p_NEW_ITV_CARD) {
		P_NEW_ITV_CARD = p_NEW_ITV_CARD;
	}

	public String getP_ITV_CARD_TYPE() {
		return P_ITV_CARD_TYPE;
	}

	public void setP_ITV_CARD_TYPE(String P_ITVCARDTYPE) {
		this.P_ITV_CARD_TYPE = P_ITVCARDTYPE;
	}

	// public BigDecimal getP_KMUSED(){
	// return P_KMUSED;}

//	public void setP_KMUSED(BigDecimal P_KMUSED){
//		this.P_KMUSED=P_KMUSED;}

//	public Timestamp getP_FISCALREPRESE(){
//		return P_FISCALREPRESE;}

//	public void setP_FISCALREPRESE(Timestamp P_FISCALREPRESE){
//		this.P_FISCALREPRESE=P_FISCALREPRESE;}

	public String getP_AGENT_FISCAL_ID() {
		return P_AGENT_FISCAL_ID;
	}

	public void setP_AGENT_FISCAL_ID(String P_AGENCYFISCALID) {
		this.P_AGENT_FISCAL_ID = P_AGENCYFISCALID;
	}

	public String getP_EXTERNAL_SYSTEM_FISCAL_ID() {
		return P_EXTERNAL_SYSTEM_FISCAL_ID;
	}

	public void setP_EXTERNAL_SYSTEM_FISCAL_ID(String P_EXTERNALSYSTEMFISCALL) {
		this.P_EXTERNAL_SYSTEM_FISCAL_ID = P_EXTERNALSYSTEMFISCALL;
	}

	public String getP_CODITV() {
		return P_CODITV;
	}

	public void setP_CODITV(String P_CODITV) {
		this.P_CODITV = P_CODITV;
	}

	public String getP_SERIAL_CARD_ITV() {
		return P_SERIAL_CARD_ITV;
	}

	public void setP_SERIAL_CARD_ITV(String pSERIALCARDITV) {
		P_SERIAL_CARD_ITV = pSERIALCARDITV;
	}

}