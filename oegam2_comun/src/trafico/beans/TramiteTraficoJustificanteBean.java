package trafico.beans;

import trafico.utiles.enumerados.TipoTramiteTraficoJustificante;

public class TramiteTraficoJustificanteBean { 

	//atributos
	private TramiteTraficoBean tramiteTrafico; 
	private IntervinienteTrafico titular;
	private TipoTramiteTraficoJustificante tipoTramiteJustificante;
	
	///////////////////////// CONSTRUCTORS
	public TramiteTraficoJustificanteBean() {
	}

	public TramiteTraficoJustificanteBean(boolean inicial) {
		tramiteTrafico= new TramiteTraficoBean(true);
		titular= new IntervinienteTrafico(true);
	}

	public TramiteTraficoBean getTramiteTrafico() {
		return tramiteTrafico;
	}


	public void setTramiteTrafico(TramiteTraficoBean tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}


	public IntervinienteTrafico getTitular() {
		return titular;
	}


	public void setTitular(IntervinienteTrafico titular) {
		this.titular = titular;
	}
	
	public TipoTramiteTraficoJustificante getTipoTramiteJustificante() {
		return tipoTramiteJustificante;
	}

	public void setTipoTramiteJustificante(String tipoTramiteJustificante) {
		this.tipoTramiteJustificante = TipoTramiteTraficoJustificante.convertir(tipoTramiteJustificante);
	}
	
}