package hibernate.entities.personas;

import hibernate.entities.trafico.Vehiculo;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the DIRECCION database table.
 * 
 */
@Entity
public class Direccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@SequenceGenerator(name = "direccion_secuencia", sequenceName = "ID_DIRECCION_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "direccion_secuencia")
	@Column(name="ID_DIRECCION",unique=true, nullable=false, precision=22)	
	private long idDireccion;

	private String bloque;

	@Column(name="COD_POSTAL")
	private String codPostal;
	
	@Column(name="COD_POSTAL_CORREOS")
	private String codPostalCorreos;

	private String escalera;

	private BigDecimal hm;

	@Column(name="ID_TIPO_VIA")
	private String idTipoVia;

	private BigDecimal km;

	private String letra;

	@Column(name="NOMBRE_VIA")
	private String nombreVia;

	@Column(name="NUM_LOCAL")
	private BigDecimal numLocal;

	private String numero;

	private String planta;

	private String pueblo;

	@Column(name="PUEBLO_CORREOS")
	private String puebloCorreos;
	

	private String puerta;

	@Column(name="VIA_SINEDITAR")
	private String viaSineditar;

	//bi-directional many-to-one association to Municipio
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="ID_PROVINCIA", referencedColumnName="ID_PROVINCIA"),
		@JoinColumn(name="ID_MUNICIPIO", referencedColumnName="ID_MUNICIPIO")
		})
	private Municipio municipio;

	//bi-directional many-to-one association to PersonaDireccion	
	@OneToMany(mappedBy="direccion")	
	private List<PersonaDireccion> personaDireccions;

	//bi-directional many-to-one association to Vehiculo
	@OneToMany(mappedBy="direccion")
	private List<Vehiculo> vehiculos;

    public Direccion() {
    }

	public long getIdDireccion() {
		return this.idDireccion;
	}

	public void setIdDireccion(long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getBloque() {
		return this.bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getCodPostal() {
		return this.codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getEscalera() {
		return this.escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public BigDecimal getHm() {
		return this.hm;
	}

	public void setHm(BigDecimal hm) {
		this.hm = hm;
	}

	public String getIdTipoVia() {
		return this.idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public BigDecimal getKm() {
		return this.km;
	}

	public void setKm(BigDecimal km) {
		this.km = km;
	}

	public String getLetra() {
		return this.letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getNombreVia() {
		return this.nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public BigDecimal getNumLocal() {
		return this.numLocal;
	}

	public void setNumLocal(BigDecimal numLocal) {
		this.numLocal = numLocal;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPlanta() {
		return this.planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getPueblo() {
		return this.pueblo;
	}

	public void setPueblo(String pueblo) {
		this.pueblo = pueblo;
	}

	public String getPuerta() {
		return this.puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getViaSineditar() {
		return this.viaSineditar;
	}

	public void setViaSineditar(String viaSineditar) {
		this.viaSineditar = viaSineditar;
	}

	public Municipio getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
	
	public List<PersonaDireccion> getPersonaDireccions() {
		return this.personaDireccions;
	}

	public void setPersonaDireccions(List<PersonaDireccion> personaDireccions) {
		this.personaDireccions = personaDireccions;
	}
	
	public List<Vehiculo> getVehiculos() {
		return this.vehiculos;
	}

	public void setVehiculos(List<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}
	

	public String getCodPostalCorreos() {
		return codPostalCorreos;
	}

	public void setCodPostalCorreos(String codPostalCorreos) {
		this.codPostalCorreos = codPostalCorreos;
	}

	public String getPuebloCorreos() {
		return puebloCorreos;
	}

	public void setPuebloCorreos(String puebloCorreos) {
		this.puebloCorreos = puebloCorreos;
	}
	
	public boolean equals(Direccion ob){
	
	if ((this.getBloque().equals(ob.getBloque()))
			&& (this.getCodPostal()!=null && this.getCodPostal().equals(ob.getCodPostal()) || this.getCodPostal()==null && ob.getCodPostal()==null) 
			&& (this.getEscalera()!=null && (this.getEscalera().equals(ob.getEscalera())))
			&& (this.getHm()!=null && this.getHm().equals(ob.getHm()) || (this.getHm()==null && ob.getHm()==null))
			&& (this.getKm()!=null && this.getKm().equals(ob.getKm()) || (this.getKm()==null && ob.getKm()==null))
			&& (this.getLetra()!=null && this.getLetra().equals(ob.getLetra()) || (this.getLetra()==null && ob.getLetra()==null))
			&& (this.getNombreVia()!=null && this.getNombreVia().equals(ob.getNombreVia()) || (this.getNombreVia()==null && ob.getNombreVia()==null))
			&& (this.getNumero()!=null && this.getNumero().equals(ob.getNumero()) || (this.getNumero()==null && ob.getNumero()==null))
			&& (this.getPlanta()!=null && this.getPlanta().equals(ob.getPlanta()) || (this.getPlanta()==null && ob.getPlanta()==null))
			&& (this.getPuerta()!=null && this.getPuerta().equals(ob.getPuerta()) || (this.getPuerta()==null && ob.getPuerta()==null))
			&& (this.getPueblo()!=null && this.getPueblo().equals(ob.getPueblo()) || (this.getPueblo()==null && ob.getPueblo()==null))
			&& (this.getMunicipio()!=null && this.getMunicipio().equals(ob.getMunicipio()) || (this.getMunicipio()==null && ob.getMunicipio()==null))
			&& (this.getIdTipoVia()!=null && this.getIdTipoVia().equals(ob.getIdTipoVia()) || (this.getIdTipoVia()==null && ob.getIdTipoVia()==null))
			){		
			
			return true;
			
		} else{
			return false;
		}
	}

}