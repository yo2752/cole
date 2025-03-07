package escrituras.beans.dao;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;

public class IntervinienteDao {

	private TipoInterviniente tipo_Interviniente;
	private String nif;
	private BigDecimal id_Interviniente;
	private BigDecimal porcentaje;
	
	public TipoInterviniente getTipo_Interviniente() {
		return tipo_Interviniente;
	}
	public void setTipo_Interviniente(String tipoInterviniente) {
		tipo_Interviniente = TipoInterviniente.convertir(tipoInterviniente);
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public BigDecimal getId_Interviniente() {
		return id_Interviniente;
	}
	public void setId_Interviniente(BigDecimal id_Interviniente) {
		this.id_Interviniente = id_Interviniente;
	}
	public BigDecimal getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}
	
}