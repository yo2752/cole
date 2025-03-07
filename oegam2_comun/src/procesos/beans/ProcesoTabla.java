package procesos.beans;

import java.math.BigDecimal;

public class ProcesoTabla {

	private String proceso;

	private String descripcion;

	private BigDecimal estado;

	private BigDecimal hilos_colas;

	private String clase;

	private BigDecimal n_intentos_max;

	private String hora_inicio;

	private String intervalo;

	private BigDecimal tipo;

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String Proceso) {
		this.proceso = Proceso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String Descripcion) {
		this.descripcion = Descripcion;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal Estado) {
		this.estado = Estado;
	}

	public BigDecimal getHilos_colas() {
		return hilos_colas;
	}

	public void setHilos_colas(BigDecimal Hilos_colas) {
		this.hilos_colas = Hilos_colas;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String Clase) {
		this.clase = Clase;
	}

	public BigDecimal getN_intentos_max() {
		return n_intentos_max;
	}

	public void setN_intentos_max(BigDecimal N_intentos_max) {
		this.n_intentos_max = N_intentos_max;
	}

	public String getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(String Hora_inicio) {
		this.hora_inicio = Hora_inicio;
	}

	public String getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(String Intervalo) {
		this.intervalo = Intervalo;
	}

	public BigDecimal getTipo() {
		return tipo;
	}

	public void setTipo(BigDecimal Tipo) {
		this.tipo = Tipo;
	}

}