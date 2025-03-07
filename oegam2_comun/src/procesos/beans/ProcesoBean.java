package procesos.beans;

import java.math.BigDecimal;

/**
 * Representación de un proceso monitorizado
 */
public class ProcesoBean {

	/**
	 * Número instancias definidas
	 */
	private Integer	numero;
	/**
	 * Número de instancias activas.
	 * Las instancias de un proceso sólo podrán estar inactivas si han 
	 * comprobado que no tienen datos para ejecutarse
	 */
	private Integer	numActivos;
	/**
	 * Estado del proceso
	 */
	private boolean activo;
	/**
	 * timestamp de la última vez que se ejecutó una instancia del proceso
	 */
	private String ultimaEjecucion;
	/**
	 * Tipo del proyecto.
	 * Este valor debe coincidir con alguno de los valores definidos en colas.contantes.Proceso
	 * y con los que van en el fichero de configuración (procesos.xml)
	 */
	private Integer tipo;
	/**
	 * Descripción del proceso para su visualización en el monitor online
	 */
	private String descripcion;
	/**
	 * Clase Java que representa a este proceso(con package)
	 */
	private Class clase;
	/**
	 * Literal para la visualización online del estado de las instancias
	 */
	private String estadoInstancias;

	// Número de intentos máximo de envíos.
	private BigDecimal intentosMax;

	// Nombre del proceso
	private String nombre;

	private Integer orden;

	// Tiempos que pasa en un proceso entre una ejecución y la siguiente dependiendo de cómo se termina.
	private BigDecimal tiempoCorrecto;
	private BigDecimal tiempoRecuperable;
	private BigDecimal tiempoNoRecuperable;
	private BigDecimal tiempoSinDatos;

	private String horaInicio;
	private String intervalo;
	private String horaFin;

	private String nodo;

	public ProcesoBean(){}

	public ProcesoBean(int tipo, String descripcion, Class clase) throws ClassNotFoundException {
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.clase = clase;
		this.activo = false;
		this.numero = 0;
		this.intentosMax = new BigDecimal(0);
	}

	public BigDecimal getTiempoCorrecto() {
		return tiempoCorrecto;
	}

	public String getNodo() {
		return nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public void setTiempoCorrecto(BigDecimal tiempoCorrecto) {
		this.tiempoCorrecto = tiempoCorrecto;
	}

	public BigDecimal getTiempoRecuperable() {
		return tiempoRecuperable;
	}

	public void setTiempoRecuperable(BigDecimal tiempoRecuperable) {
		this.tiempoRecuperable = tiempoRecuperable;
	}

	public BigDecimal getTiempoNoRecuperable() {
		return tiempoNoRecuperable;
	}

	public void setTiempoNoRecuperable(BigDecimal tiempoNoRecuperable) {
		this.tiempoNoRecuperable = tiempoNoRecuperable;
	}

	public BigDecimal getTiempoSinDatos() {
		return tiempoSinDatos;
	}

	public void setTiempoSinDatos(BigDecimal tiempoSinDatos) {
		this.tiempoSinDatos = tiempoSinDatos;
	}

	public int getTipo() {
		return tipo;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public Class getClase() {
		return this.clase;
	}
	public String getDescripcion()  {
		return descripcion;
	}
	public String getEstado() {
		return activo ? "Activo" : "Parado";
	}
	public String getUltimaEjecucion() {
		return ultimaEjecucion;
	}
	public void setUltimaEjecucion(String ultimaEjecucion) {
		this.ultimaEjecucion = ultimaEjecucion;
	}
	public Integer getNumActivos() {
		return numActivos;
	}
	public void setNumActivos(Integer numActivos) {
		this.numActivos = numActivos;
	}
	public String getEstadoInstancias() {
		return estadoInstancias == null ? "" : estadoInstancias;
	}
	public void setEstadoInstancias(String estadoInstancias) {
		this.estadoInstancias = estadoInstancias;
	}

	public BigDecimal getIntentosMax() {
		return intentosMax;
	}

	public void setIntentosMax(BigDecimal intentosMax) {
		this.intentosMax = intentosMax;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public void setClase(Class clase) {
		this.clase = clase;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getIntervalo() {
		return intervalo;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public void setIntervalo(String intervalo) {
		this.intervalo = intervalo;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

}