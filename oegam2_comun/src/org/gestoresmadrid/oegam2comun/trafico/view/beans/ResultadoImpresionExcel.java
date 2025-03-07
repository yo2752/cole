package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;

public class ResultadoImpresionExcel implements Serializable {

	private static final long serialVersionUID = 6161721988604332190L;

	private Boolean error;
	private List<String> listaErrores;
	private Long numOK;
	private Long numError;
	private String mensajeError;

	private File fichero;
	private List<File> ficheros;

	private Long numExcelMadrid;
	private Long numExcelAlcorcon;
	private Long numExcelAlcala;
	private Long numExcelAvila;
	private Long numExcelCiudadReal;
	private Long numExcelCuenca;
	private Long numExcelGuadalajara;
	private Long numExcelSegovia;

	private Long numExcelErrorMadrid;
	private Long numExcelErrorAlcorcon;
	private Long numExcelErrorAlcala;
	private Long numExcelErrorAvila;
	private Long numExcelErrorCiudadReal;
	private Long numExcelErrorCuenca;
	private Long numExcelErrorGuadalajara;
	private Long numExcelErrorSegovia;

	private Boolean errorMadrid;
	private Boolean errorAlcorcon;
	private Boolean errorAlcala;
	private Boolean errorAvila;
	private Boolean errorCiudadReal;
	private Boolean errorCuenca;
	private Boolean errorGuadalajara;
	private Boolean errorSegovia;

	private String mensajeErrorMadrid;
	private String mensajeErrorAlcorcon;
	private String mensajeErrorAlcala;
	private String mensajeErrorAvila;
	private String mensajeErrorCiudadReal;
	private String mensajeErrorCuenca;
	private String mensajeErrorGuadalajara;
	private String mensajeErrorSegovia;

	private List<String> listaResumenMadrid;
	private List<String> listaResumenAlcorcon;
	private List<String> listaResumenAlcala;
	private List<String> listaResumenAvila;
	private List<String> listaResumenCiudadReal;
	private List<String> listaResumenCuenca;
	private List<String> listaResumenGuadalajara;
	private List<String> listaResumenSegovia;

	private List<TramiteTrafDuplicadoDto> listaDuplicados;
	private List<String> listaDupli;

	public ResultadoImpresionExcel(Boolean error) {
		this.errorAlcala = Boolean.FALSE;
		this.errorAlcorcon = Boolean.FALSE;
		this.errorAvila = Boolean.FALSE;
		this.errorCiudadReal = Boolean.FALSE;
		this.errorCuenca = Boolean.FALSE;
		this.errorGuadalajara = Boolean.FALSE;
		this.errorMadrid = Boolean.FALSE;
		this.errorSegovia = Boolean.FALSE;

		this.numExcelAlcala = new Long(0);
		this.numExcelAlcorcon = new Long(0);
		this.numExcelAvila = new Long(0);
		this.numExcelCiudadReal = new Long(0);
		this.numExcelCuenca = new Long(0);
		this.numExcelGuadalajara = new Long(0);
		this.numExcelMadrid = new Long(0);
		this.numExcelSegovia = new Long(0);

		this.numExcelErrorMadrid = new Long(0);
		this.numExcelErrorAlcorcon = new Long(0);
		this.numExcelErrorAlcala = new Long(0);
		this.numExcelErrorAvila = new Long(0);
		this.numExcelErrorCiudadReal = new Long(0);
		this.numExcelErrorCuenca = new Long(0);
		this.numExcelErrorGuadalajara = new Long(0);
		this.numExcelErrorSegovia = new Long(0);

		this.numError = new Long(0);
		this.error = error;
	}

	public void addListaMensajeError(List<String> listaMensajes) {
		if(listaErrores == null || listaErrores.isEmpty()) {
			listaErrores = new ArrayList<String>();
		}
		for(String mens : listaMensajes) {
			listaErrores.add(mens);
		}
	}

	public void addListaDuplicados(TramiteTrafDuplicadoDto duplicados){
		if(listaDuplicados == null){
			listaDuplicados = new ArrayList<TramiteTrafDuplicadoDto>();
		}
		listaDuplicados.add(duplicados);
	}
	
	public void addMensajeError(String mensaje) {
		if (listaErrores == null) {
			listaErrores = new ArrayList<String>();
		}
		listaErrores.add(mensaje);
	}

	public void addNumOK() {
		if (numOK == null) {
			numOK = new Long(0);
		}
		numOK++;
	}

	public void addNumError() {
		if (numError == null) {
			numError = new Long(0);
		}
		numError++;
	}

	public Long getNumExcelMadrid() {
		return numExcelMadrid;
	}

	public void setNumExcelMadrid(Long numExcelMadrid) {
		this.numExcelMadrid = numExcelMadrid;
	}

	public Long getNumExcelAlcorcon() {
		return numExcelAlcorcon;
	}

	public void setNumExcelAlcorcon(Long numExcelAlcorcon) {
		this.numExcelAlcorcon = numExcelAlcorcon;
	}

	public Long getNumExcelAlcala() {
		return numExcelAlcala;
	}

	public void setNumExcelAlcala(Long numExcelAlcala) {
		this.numExcelAlcala = numExcelAlcala;
	}

	public Long getNumExcelAvila() {
		return numExcelAvila;
	}

	public void setNumExcelAvila(Long numExcelAvila) {
		this.numExcelAvila = numExcelAvila;
	}

	public Long getNumExcelCiudadReal() {
		return numExcelCiudadReal;
	}

	public void setNumExcelCiudadReal(Long numExcelCiudadReal) {
		this.numExcelCiudadReal = numExcelCiudadReal;
	}

	public Long getNumExcelCuenca() {
		return numExcelCuenca;
	}

	public void setNumExcelCuenca(Long numExcelCuenca) {
		this.numExcelCuenca = numExcelCuenca;
	}

	public Long getNumExcelGuadalajara() {
		return numExcelGuadalajara;
	}

	public void setNumExcelGuadalajara(Long numExcelGuadalajara) {
		this.numExcelGuadalajara = numExcelGuadalajara;
	}

	public Long getNumExcelSegovia() {
		return numExcelSegovia;
	}

	public void setNumExcelSegovia(Long numExcelSegovia) {
		this.numExcelSegovia = numExcelSegovia;
	}

	public Boolean getErrorMadrid() {
		return errorMadrid;
	}

	public void setErrorMadrid(Boolean errorMadrid) {
		this.errorMadrid = errorMadrid;
	}

	public Boolean getErrorAlcorcon() {
		return errorAlcorcon;
	}

	public void setErrorAlcorcon(Boolean errorAlcorcon) {
		this.errorAlcorcon = errorAlcorcon;
	}

	public Boolean getErrorAlcala() {
		return errorAlcala;
	}

	public void setErrorAlcala(Boolean errorAlcala) {
		this.errorAlcala = errorAlcala;
	}

	public Boolean getErrorAvila() {
		return errorAvila;
	}

	public void setErrorAvila(Boolean errorAvila) {
		this.errorAvila = errorAvila;
	}

	public Boolean getErrorCiudadReal() {
		return errorCiudadReal;
	}

	public void setErrorCiudadReal(Boolean errorCiudadReal) {
		this.errorCiudadReal = errorCiudadReal;
	}

	public Boolean getErrorCuenca() {
		return errorCuenca;
	}

	public void setErrorCuenca(Boolean errorCuenca) {
		this.errorCuenca = errorCuenca;
	}

	public Boolean getErrorGuadalajara() {
		return errorGuadalajara;
	}

	public void setErrorGuadalajara(Boolean errorGuadalajara) {
		this.errorGuadalajara = errorGuadalajara;
	}

	public Boolean getErrorSegovia() {
		return errorSegovia;
	}

	public void setErrorSegovia(Boolean errorSegovia) {
		this.errorSegovia = errorSegovia;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public List<String> getListaErrores() {
		return listaErrores;
	}

	public void setListaErrores(List<String> listaErrores) {
		this.listaErrores = listaErrores;
	}

	public Long getNumOK() {
		return numOK;
	}

	public void setNumOK(Long numOK) {
		this.numOK = numOK;
	}

	public Long getNumError() {
		return numError;
	}

	public void setNumError(Long numError) {
		this.numError = numError;
	}

	public Long getNumExcelErrorMadrid() {
		return numExcelErrorMadrid;
	}

	public void setNumExcelErrorMadrid(Long numExcelErrorMadrid) {
		this.numExcelErrorMadrid = numExcelErrorMadrid;
	}

	public Long getNumExcelErrorAlcorcon() {
		return numExcelErrorAlcorcon;
	}

	public void setNumExcelErrorAlcorcon(Long numExcelErrorAlcorcon) {
		this.numExcelErrorAlcorcon = numExcelErrorAlcorcon;
	}

	public Long getNumExcelErrorAlcala() {
		return numExcelErrorAlcala;
	}

	public void setNumExcelErrorAlcala(Long numExcelErrorAlcala) {
		this.numExcelErrorAlcala = numExcelErrorAlcala;
	}

	public Long getNumExcelErrorAvila() {
		return numExcelErrorAvila;
	}

	public void setNumExcelErrorAvila(Long numExcelErrorAvila) {
		this.numExcelErrorAvila = numExcelErrorAvila;
	}

	public Long getNumExcelErrorCiudadReal() {
		return numExcelErrorCiudadReal;
	}

	public void setNumExcelErrorCiudadReal(Long numExcelErrorCiudadReal) {
		this.numExcelErrorCiudadReal = numExcelErrorCiudadReal;
	}

	public Long getNumExcelErrorCuenca() {
		return numExcelErrorCuenca;
	}

	public void setNumExcelErrorCuenca(Long numExcelErrorCuenca) {
		this.numExcelErrorCuenca = numExcelErrorCuenca;
	}

	public Long getNumExcelErrorGuadalajara() {
		return numExcelErrorGuadalajara;
	}

	public void setNumExcelErrorGuadalajara(Long numExcelErrorGuadalajara) {
		this.numExcelErrorGuadalajara = numExcelErrorGuadalajara;
	}

	public Long getNumExcelErrorSegovia() {
		return numExcelErrorSegovia;
	}

	public void setNumExcelErrorSegovia(Long numExcelErrorSegovia) {
		this.numExcelErrorSegovia = numExcelErrorSegovia;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public String getMensajeErrorMadrid() {
		return mensajeErrorMadrid;
	}

	public void setMensajeErrorMadrid(String mensajeErrorMadrid) {
		this.mensajeErrorMadrid = mensajeErrorMadrid;
	}

	public String getMensajeErrorAlcorcon() {
		return mensajeErrorAlcorcon;
	}

	public void setMensajeErrorAlcorcon(String mensajeErrorAlcorcon) {
		this.mensajeErrorAlcorcon = mensajeErrorAlcorcon;
	}

	public String getMensajeErrorAlcala() {
		return mensajeErrorAlcala;
	}

	public void setMensajeErrorAlcala(String mensajeErrorAlcala) {
		this.mensajeErrorAlcala = mensajeErrorAlcala;
	}

	public String getMensajeErrorAvila() {
		return mensajeErrorAvila;
	}

	public void setMensajeErrorAvila(String mensajeErrorAvila) {
		this.mensajeErrorAvila = mensajeErrorAvila;
	}

	public String getMensajeErrorCiudadReal() {
		return mensajeErrorCiudadReal;
	}

	public void setMensajeErrorCiudadReal(String mensajeErrorCiudadReal) {
		this.mensajeErrorCiudadReal = mensajeErrorCiudadReal;
	}

	public String getMensajeErrorCuenca() {
		return mensajeErrorCuenca;
	}

	public void setMensajeErrorCuenca(String mensajeErrorCuenca) {
		this.mensajeErrorCuenca = mensajeErrorCuenca;
	}

	public String getMensajeErrorGuadalajara() {
		return mensajeErrorGuadalajara;
	}

	public void setMensajeErrorGuadalajara(String mensajeErrorGuadalajara) {
		this.mensajeErrorGuadalajara = mensajeErrorGuadalajara;
	}

	public String getMensajeErrorSegovia() {
		return mensajeErrorSegovia;
	}

	public void setMensajeErrorSegovia(String mensajeErrorSegovia) {
		this.mensajeErrorSegovia = mensajeErrorSegovia;
	}

	public List<String> getListaResumenMadrid() {
		return listaResumenMadrid;
	}

	public void setListaResumenMadrid(List<String> listaResumenMadrid) {
		this.listaResumenMadrid = listaResumenMadrid;
	}

	public List<String> getListaResumenAlcorcon() {
		return listaResumenAlcorcon;
	}

	public void setListaResumenAlcorcon(List<String> listaResumenAlcorcon) {
		this.listaResumenAlcorcon = listaResumenAlcorcon;
	}

	public List<String> getListaResumenAlcala() {
		return listaResumenAlcala;
	}

	public void setListaResumenAlcala(List<String> listaResumenAlcala) {
		this.listaResumenAlcala = listaResumenAlcala;
	}

	public List<String> getListaResumenAvila() {
		return listaResumenAvila;
	}

	public void setListaResumenAvila(List<String> listaResumenAvila) {
		this.listaResumenAvila = listaResumenAvila;
	}

	public List<String> getListaResumenCiudadReal() {
		return listaResumenCiudadReal;
	}

	public void setListaResumenCiudadReal(List<String> listaResumenCiudadReal) {
		this.listaResumenCiudadReal = listaResumenCiudadReal;
	}

	public List<String> getListaResumenCuenca() {
		return listaResumenCuenca;
	}

	public void setListaResumenCuenca(List<String> listaResumenCuenca) {
		this.listaResumenCuenca = listaResumenCuenca;
	}

	public List<String> getListaResumenGuadalajara() {
		return listaResumenGuadalajara;
	}

	public void setListaResumenGuadalajara(List<String> listaResumenGuadalajara) {
		this.listaResumenGuadalajara = listaResumenGuadalajara;
	}

	public List<File> getFicheros() {
		return ficheros;
	}

	public void setFicheros(List<File> ficheros) {
		this.ficheros = ficheros;
	}

	public void addFicheros(File fichero) {
		if (ficheros == null) {
			ficheros = new ArrayList<File>();
		}
		ficheros.add(fichero);
	}

	public List<String> getListaResumenSegovia() {
		return listaResumenSegovia;
	}

	public void setListaResumenSegovia(List<String> listaResumenSegovia) {
		this.listaResumenSegovia = listaResumenSegovia;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public List<TramiteTrafDuplicadoDto> getListaDuplicados() {
		return listaDuplicados;
	}

	public void setListaDuplicados(List<TramiteTrafDuplicadoDto> listaDuplicados) {
		this.listaDuplicados = listaDuplicados;
	}

	public List<String> getListaDupli() {
		return listaDupli;
	}

	public void setListaDupli(List<String> listaDupli) {
		this.listaDupli = listaDupli;
	}

}