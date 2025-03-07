package escrituras.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;

/**
 * Bean que almacena datos de la tabla INTERVINIENTE.
 *
 */
public class Interviniente {
	
	 private Persona persona;
	 private TipoInterviniente tipoInterviniente;
	 private BigDecimal idInterviniente;
	 private BigDecimal porcentaje;
	 
	 public Interviniente() {
		
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public TipoInterviniente getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = TipoInterviniente.convertir(tipoInterviniente);
	}

	public BigDecimal getIdInterviniente() {
		return idInterviniente;
	}

	public void setIdInterviniente(BigDecimal idInterviniente) {
		this.idInterviniente = idInterviniente;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

}
