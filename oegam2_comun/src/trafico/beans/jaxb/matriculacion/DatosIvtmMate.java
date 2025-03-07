package trafico.beans.jaxb.matriculacion;
//TODO MPC. Cambio IVTM. Clase añadida.
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.apache.commons.beanutils.PropertyUtils;
import org.jfree.util.Log;

import trafico.beans.jaxb.matriculacion.FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOSIMVTM;

public class DatosIvtmMate extends DATOSIMVTM implements DatosIvtm {

	public DatosIvtmMate (){
	}

	public DatosIvtmMate (DATOSIMVTM d){
		try {
			PropertyUtils.copyProperties(this, d);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			Log.error("No se han podido transformar los datos del IVTM.");
		}
	}

	@Override
	public String getBONOMEDIOAMBIENTE() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBONOMEDIOAMBIENTE(String value) {
		// TODO Auto-generated method stub
	}

	@Override
	public BigDecimal getPORCBONOMEDIOAMBIENTE() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPORCBONOMEDIOAMBIENTE(BigDecimal value) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getCODIGOGESTOR() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCODIGOGESTOR(String value) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getDIGITOCONTROL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDIGITOCONTROL(String value) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getEMISOR() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEMISOR(String value) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getEXENTOPAGO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEXENTOPAGO(String value) {
		// TODO Auto-generated method stub
	}

	@Override
	public BigDecimal getIMPORTEANUAL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIMPORTEANUAL(BigDecimal value) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getIBAN() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIBAN(String value) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getNRC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNRC(String value) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getCODIGORESPUESTAPAGO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCODIGORESPUESTAPAGO(String value) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getIDPETICIONIVTM() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIDPETICIONIVTM(String value) {
		// TODO Auto-generated method stub
	}
}