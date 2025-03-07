
package trafico.beans;

import java.math.BigDecimal;

import trafico.utiles.enumerados.MotivoDuplicado;
import utilidades.estructuras.Fecha;

public class TramiteTraficoDuplicadoBean { 

	//atributos
	private TramiteTraficoBean tramiteTrafico; 
	private IntervinienteTrafico titular;
	private IntervinienteTrafico cotitular;
	private IntervinienteTrafico representante;
	private Fecha fechaPresentacion;
	private MotivoDuplicado motivoDuplicado;
	private Boolean importacion;
	private Boolean imprPermisoCircu;
	private String tasaDuplicado;
	private String anotaciones;
	private String tipoDuplicado;
	private BigDecimal idTipoCreacion;	

	///////////////////////// CONSTRUCTORS
	public TramiteTraficoDuplicadoBean() {
	
	}
	
	public TramiteTraficoDuplicadoBean(boolean inicial) {
		tramiteTrafico= new TramiteTraficoBean(true);
		titular= new IntervinienteTrafico(true);
		cotitular = new IntervinienteTrafico(true);
		representante= new IntervinienteTrafico(true);
		fechaPresentacion = new Fecha();		
	}
	
	public Boolean getImportacion() {
		return importacion;
	}

	public void setImportacion(Boolean importacion) {
		this.importacion = importacion;
	}

	/////////////// GETTERS & SETTERS
	public TramiteTraficoBean getTramiteTrafico() {
		return tramiteTrafico;
	}
	public void setTramiteTrafico(TramiteTraficoBean tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}
	
	public Boolean getImprPermisoCircu() {
		return imprPermisoCircu;
	}

	public void setImprPermisoCircu(Boolean imprPermisoCircu) {
		this.imprPermisoCircu = imprPermisoCircu;
	}

	public String getTasaDuplicado() {
		return tasaDuplicado;
	}

	public void setTasaDuplicado(String tasaDuplicado) {
		this.tasaDuplicado = tasaDuplicado;
	}

	public IntervinienteTrafico getTitular() {
		return titular;
	}
	public void setTitular(IntervinienteTrafico titular) {
		this.titular = titular;
	}
	public IntervinienteTrafico getRepresentante() {
		return representante;
	}
	public void setRepresentante(IntervinienteTrafico representante) {
		this.representante = representante;
	}
	
	public IntervinienteTrafico getCotitular() {
		return cotitular;
	}

	public void setCotitular(IntervinienteTrafico cotitular) {
		this.cotitular = cotitular;
	}

	public Fecha getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(Fecha fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public MotivoDuplicado getMotivoDuplicado() {
		return motivoDuplicado;
	}

	public void setMotivoDuplicado(String motivoDuplicado) {
		this.motivoDuplicado = MotivoDuplicado.convertir(motivoDuplicado);
	}

	public String getAnotaciones() {
		return anotaciones;
	}
	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}	
	public String getTipoDuplicado() {
		return tipoDuplicado;
	}

	public void setTipoDuplicado(String tipoDuplicado) {
		this.tipoDuplicado = tipoDuplicado;
	}
	public BigDecimal getIdTipoCreacion() {
		return idTipoCreacion;
	}
	public void setIdTipoCreacion(BigDecimal idTipoCreacion) {
		this.idTipoCreacion = idTipoCreacion;
	}
}