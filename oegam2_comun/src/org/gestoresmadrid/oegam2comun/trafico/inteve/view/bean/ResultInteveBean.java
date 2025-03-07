package org.gestoresmadrid.oegam2comun.trafico.inteve.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ResultInteveBean implements Serializable {

	private static final long serialVersionUID = -5782101908295097353L;

	private boolean error = false;
	private String mensaje;
	private byte[] informe;
	private Throwable exception;
	private List<InformeInteveBean> listaIdSolInfoOK;
	private List<InformeInteveBean> listaIdSolInfoError;
	
	public void addListaIdSolInfoError(String resultado, Long idSolInfo){
		if(listaIdSolInfoError == null || listaIdSolInfoError.isEmpty()){
			listaIdSolInfoError = new ArrayList<InformeInteveBean>();
		}
		listaIdSolInfoError.add(new InformeInteveBean(null, null, resultado, idSolInfo));
	}
	
	public void addListaIdSolInfoOK(String resultado, Long idSolInfo, String nombreInforme, byte[] fichero){
		if(listaIdSolInfoOK == null || listaIdSolInfoOK.isEmpty()){
			listaIdSolInfoOK = new ArrayList<InformeInteveBean>();
		}
		listaIdSolInfoOK.add(new InformeInteveBean(fichero, nombreInforme, resultado, idSolInfo));
	}

	public ResultInteveBean() {
		super();
	}

	public ResultInteveBean(boolean error) {
		super();
		this.error = error;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public byte[] getInforme() {
		return informe;
	}

	public void setInforme(byte[] informe) {
		this.informe = informe;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "ResultInteveBean [error=" + error + ", mensaje=" + mensaje + ", informe=" + Arrays.toString(informe) + ", exception=" + exception + "]";
	}

	public List<InformeInteveBean> getListaIdSolInfoOK() {
		return listaIdSolInfoOK;
	}

	public void setListaIdSolInfoOK(List<InformeInteveBean> listaIdSolInfoOK) {
		this.listaIdSolInfoOK = listaIdSolInfoOK;
	}

	public List<InformeInteveBean> getListaIdSolInfoError() {
		return listaIdSolInfoError;
	}

	public void setListaIdSolInfoError(List<InformeInteveBean> listaIdSolInfoError) {
		this.listaIdSolInfoError = listaIdSolInfoError;
	}

}
