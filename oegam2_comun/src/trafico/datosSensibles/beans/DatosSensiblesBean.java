package trafico.datosSensibles.beans;

import java.math.BigDecimal;

import utilidades.estructuras.FechaFraccionada;

public class DatosSensiblesBean {

	private FechaFraccionada fecha;
	private String tipoAgrupacion;
	private String textoAgrupacion;
	private BigDecimal tiempoDatosSensibles;

	public String getTextoAgrupacion() {
		return textoAgrupacion;
	}

	public void setTextoAgrupacion(String textoAgrupacion) {
		this.textoAgrupacion = textoAgrupacion;
	}

	public String getTipoAgrupacion() {
		return tipoAgrupacion;
	}

	public void setTipoAgrupacion(String tipoAgrupacion) {
		this.tipoAgrupacion = tipoAgrupacion;
	}

	public FechaFraccionada getFecha() {
		return fecha;
	}

	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getTiempoDatosSensibles() {
		return tiempoDatosSensibles;
	}

	public void setTiempoDatosSensibles(BigDecimal tiempoDatosSensibles) {
		this.tiempoDatosSensibles = tiempoDatosSensibles;
	}
}