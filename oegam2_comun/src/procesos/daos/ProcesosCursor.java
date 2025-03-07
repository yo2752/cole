package procesos.daos;

import java.math.BigDecimal;

import org.displaytag.properties.SortOrderEnum;

import escrituras.modelo.ModeloBase;
import utilidades.estructuras.ListaRegistros;

public class ProcesosCursor extends ModeloBase {

	/*-- Monto la select para el cursor
	 w_select := 'SELECT PROCESO, DESCRIPCION, ESTADO, HILOS_COLAS, CLASE, 
	  N_INTENTOS_MAX, TIPO, HORA_INICIO, INTERVALO FROM PROCESO'; 
	 */

	private Object PROCESO;
	private Object DESCRIPCION;
	private Object ESTADO;
	private Object HILOS_COLAS;
	private Object CLASE;
	private BigDecimal N_INTENTOS_MAX;
	private Object TIPO;
	private Object HORA_INICIO;
	private Object INTERVALO;
	private Object HORA_FIN;
	private Object TIEMPO_CORRECTO; 
	private Object TIEMPO_ERRONEO_RECUPERABLE;
	private Object TIEMPO_ERRONEO_NO_RECUPERABLE;
	private Object TIEMPO_SIN_DATOS;
	private String NODO;
	private Object ORDEN;

	public String getNODO() {
		return NODO;
	}

	public void setNODO(String nODO) {
		NODO = nODO;
	}

	public Object getTIEMPO_CORRECTO() {
		return TIEMPO_CORRECTO;
	}

	public void setTIEMPO_CORRECTO(Object tIEMPOCORRECTO) {
		TIEMPO_CORRECTO = tIEMPOCORRECTO;
	}


	public Object getTIEMPO_ERRONEO_RECUPERABLE() {
		return TIEMPO_ERRONEO_RECUPERABLE;
	}

	public void setTIEMPO_ERRONEO_RECUPERABLE(Object tIEMPOERRONEORECUPERABLE) {
		TIEMPO_ERRONEO_RECUPERABLE = tIEMPOERRONEORECUPERABLE;
	}

	public Object getTIEMPO_ERRONEO_NO_RECUPERABLE() {
		return TIEMPO_ERRONEO_NO_RECUPERABLE;
	}

	public void setTIEMPO_ERRONEO_NO_RECUPERABLE(Object tIEMPOERRONEONORECUPERABLE) {
		TIEMPO_ERRONEO_NO_RECUPERABLE = tIEMPOERRONEONORECUPERABLE;
	}

	public Object getTIEMPO_SIN_DATOS() {
		return TIEMPO_SIN_DATOS;
	}

	public void setTIEMPO_SIN_DATOS(Object tIEMPOSINDATOS) {
		TIEMPO_SIN_DATOS = tIEMPOSINDATOS;
	}

	public Object getPROCESO() {
		return PROCESO;
	}

	public void setPROCESO(Object pROCESO) {
		PROCESO = pROCESO;
	}

	public Object getHORA_FIN() {
		return HORA_FIN;
	}

	public void setHORA_FIN(Object hORAFIN) {
		HORA_FIN = hORAFIN;
	}

	public Object getDESCRIPCION() {
		return DESCRIPCION;
	}

	public void setDESCRIPCION(Object dESCRIPCION) {
		DESCRIPCION = dESCRIPCION;
	}

	public Object getESTADO() {
		return ESTADO;
	}

	public void setESTADO(Object eSTADO) {
		ESTADO = eSTADO;
	}

	public Object getHILOS_COLAS() {
		return HILOS_COLAS;
	}

	public void setHILOS_COLAS(Object hILOSCOLAS) {
		HILOS_COLAS = hILOSCOLAS;
	}

	public Object getCLASE() {
		return CLASE;
	}

	public void setCLASE(Object cLASE) {
		CLASE = cLASE;
	}

	public BigDecimal getN_INTENTOS_MAX() {
		return N_INTENTOS_MAX;
	}

	public void setN_INTENTOS_MAX(BigDecimal nINTENTOSMAX) {
		N_INTENTOS_MAX = nINTENTOSMAX;
	}

	public Object getTIPO() {
		return TIPO;
	}

	public void setTIPO(Object tIPO) {
		TIPO = tIPO;
	}

	public Object getHORA_INICIO() {
		return HORA_INICIO;
	}

	public void setHORA_INICIO(Object hORAINICIO) {
		HORA_INICIO = hORAINICIO;
	}

	public Object getINTERVALO() {
		return INTERVALO;
	}

	public void setINTERVALO(Object iNTERVALO) {
		INTERVALO = iNTERVALO;
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		return null;
	}

	public Object getORDEN() {
		return ORDEN;
	}

	public void setORDEN(Object oRDEN) {
		ORDEN = oRDEN;
	}

}