package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import java.util.ArrayList;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;

public class TramiteTrafDuplicadoDto extends TramiteTrafDto {

	private static final long serialVersionUID = -8712394478214170018L;

	private String motivoDuplicado;

	private Boolean imprPermisoCirculacion;

	private Boolean importacion;

	private String tasaImportacion;

	private String tasaFichaTecnica;

	private String tasaPermiso;

	private IntervinienteTraficoDto titular;

	private IntervinienteTraficoDto representanteTitular;

	private IntervinienteTraficoDto cotitular;

	private ArrayList<FicheroInfo> ficherosSubidos;

	private String tipoDocumento;

	private String resCheckDupl;

	public TramiteTrafDuplicadoDto() {
	}

	public TramiteTrafDuplicadoDto(TramiteTrafDto tramite) {
		setRefPropia(tramite.getRefPropia());
		setVehiculoDto(tramite.getVehiculoDto());
		setJefaturaTraficoDto(tramite.getJefaturaTraficoDto());
		setNumColegiado(tramite.getNumColegiado());
		setNumExpediente(tramite.getNumExpediente());
	}

	public String getMotivoDuplicado() {
		return motivoDuplicado;
	}

	public void setMotivoDuplicado(String motivoDuplicado) {
		this.motivoDuplicado = motivoDuplicado;
	}

	public Boolean getImprPermisoCirculacion() {
		return imprPermisoCirculacion;
	}

	public void setImprPermisoCirculacion(Boolean imprPermisoCirculacion) {
		this.imprPermisoCirculacion = imprPermisoCirculacion;
	}

	public Boolean getImportacion() {
		return importacion;
	}

	public void setImportacion(Boolean importacion) {
		this.importacion = importacion;
	}

	public String getTasaImportacion() {
		return tasaImportacion;
	}

	public void setTasaImportacion(String tasaImportacion) {
		this.tasaImportacion = tasaImportacion;
	}

	public IntervinienteTraficoDto getTitular() {
		return titular;
	}

	public void setTitular(IntervinienteTraficoDto titular) {
		this.titular = titular;
	}

	public IntervinienteTraficoDto getRepresentanteTitular() {
		return representanteTitular;
	}

	public void setRepresentanteTitular(IntervinienteTraficoDto representanteTitular) {
		this.representanteTitular = representanteTitular;
	}

	public IntervinienteTraficoDto getCotitular() {
		return cotitular;
	}

	public void setCotitular(IntervinienteTraficoDto cotitular) {
		this.cotitular = cotitular;
	}

	public ArrayList<FicheroInfo> getFicherosSubidos() {
		return ficherosSubidos;
	}

	public void setFicherosSubidos(ArrayList<FicheroInfo> ficherosSubidos) {
		this.ficherosSubidos = ficherosSubidos;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTasaFichaTecnica() {
		return tasaFichaTecnica;
	}

	public void setTasaFichaTecnica(String tasaFichaTecnica) {
		this.tasaFichaTecnica = tasaFichaTecnica;
	}

	public String getTasaPermiso() {
		return tasaPermiso;
	}

	public void setTasaPermiso(String tasaPermiso) {
		this.tasaPermiso = tasaPermiso;
	}

	public String getResCheckDupl() {
		return resCheckDupl;
	}

	public void setResCheckDupl(String resCheckDupl) {
		this.resCheckDupl = resCheckDupl;
	}

}