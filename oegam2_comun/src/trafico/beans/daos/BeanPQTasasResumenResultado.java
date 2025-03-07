package trafico.beans.daos;



public class BeanPQTasasResumenResultado extends BeanPQGeneral{
	private String tipo_tasa;
	private Integer total; 
	private Integer asignadas;
	private Integer desasignadas;
	
	public BeanPQTasasResumenResultado() {
		super();
	}
	public String getTipo_tasa() {
		return tipo_tasa;
	}
	public void setTipo_tasa(String tipoTasa) {
		tipo_tasa = tipoTasa;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getAsignadas() {
		return asignadas;
	}
	public void setAsignadas(Integer asignadas) {
		this.asignadas = asignadas;
	}
	public Integer getDesasignadas() {
		return desasignadas;
	}
	public void setDesasignadas(Integer desasignadas) {
		this.desasignadas = desasignadas;
	}
}
