package escrituras.beans;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Bean que almacena datos de la tabla DIRECCION.
 *
 */
public class Direccion implements Serializable {

	private static final long serialVersionUID = -2310248824163954232L;

	private Integer idDireccion;
	private Municipio municipio;
	private TipoVia tipoVia;
	private String nombreVia;
	private String numero;
	private String codPostal;
	private String codPostalCorreos; // MATW
	private String escalera;
	private String letra;
	private String planta;
	private String puerta;
	private Integer numLocal;
	private Timestamp fechaInicio;
	private String pueblo;//entidad_singular
	private String puebloCorreos;//entidad_singular MATW
	private String puntoKilometrico;
	private String hm; 
	private String portal;
	private String bloque;
	private String asignarPrincipal;

	public Direccion() {
		super();
	}
	public Direccion(boolean inicializar) {
		this();
		if (inicializar){
			tipoVia = new TipoVia();
			municipio = new Municipio(true);
		}
	}
	public Municipio getMunicipio() {
		return municipio;
	}

	public String getHm() {
		return hm;
	}
	public void setHm(String hm) {
		this.hm = hm;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public TipoVia getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(TipoVia tipoVia) {
		this.tipoVia = tipoVia;
	}

	public Integer getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Integer idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getCodPostalCorreos() {
		return codPostalCorreos;
	}

	public void setCodPostalCorreos(String codPostalCorreos) {
		this.codPostalCorreos = codPostalCorreos;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public Integer getNumLocal() {
		return numLocal;
	}

	public void setNumLocal(Integer numLocal) {
		this.numLocal = numLocal;
	}

	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getPueblo() {
		return pueblo;
	}
	public void setPueblo(String pueblo) {
		this.pueblo = pueblo;
	}

	public String getPuebloCorreos() {
		return puebloCorreos;
	}
	public void setPuebloCorreos(String puebloCorreos) {
		this.puebloCorreos = puebloCorreos;
	}

	public String getPuntoKilometrico() {
		return puntoKilometrico;
	}
	public void setPuntoKilometrico(String puntoKilometrico) {
		this.puntoKilometrico = puntoKilometrico;
	}
	public String getPortal() {
		return portal;
	}
	public void setPortal(String portal) {
		this.portal = portal;
	}
	public String getBloque() {
		return bloque;
	}
	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getLetra() {
		return letra;
	}
	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getAsignarPrincipal() {
		return asignarPrincipal;
	}
	public void setAsignarPrincipal(String asignarPrincipal) {
		this.asignarPrincipal = asignarPrincipal;
	}
	public String imprimir() {

		if (municipio == null){
			setMunicipio(new Municipio());
		}
		if (tipoVia == null){
			setTipoVia(new TipoVia());
		}
		return "Direccion [codPostal=" + codPostal + ", escalera=" + escalera
				+ ", fechaInicio=" + fechaInicio + ", idDireccion="
				+ idDireccion + ", municipio=" + municipio.imprimir() + ", nombreVia="
				+ nombreVia + ", numLocal=" + numLocal + ", numero=" + numero
				+ ", planta=" + planta + ", puerta=" + puerta + ", tipoVia="
				+ tipoVia.imprimir() +"]";
	}

}