package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the LC_OBRA database table.
 */
@Entity
@Table(name = "LC_OBRA")
public class LcObraVO implements Serializable {

	private static final long serialVersionUID = -8516770212591655166L;

	@Id
	@SequenceGenerator(name = "lc_obra", sequenceName = "LC_OBRA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_obra")
	@Column(name = "ID_DATOS_OBRA")
	private Long idDatosObra;

	@Column(name="ANDAMIOS")
	private String andamios;

	@Column(name = "CONTENEDOR_VIA_PUBLICA")
	private String contenedorViaPublica;

	@Column(name = "DESCRIPCION_OBRA")
	private String descripcionObra;

	@Column(name = "DURACION_ANDAMIO_MESES")
	private BigDecimal duracionAndamioMeses;

	@Column(name = "DURACION_GRUA_MESES")
	private BigDecimal duracionGruaMeses;

	@Column(name = "DURACION_VALLAS_MESES")
	private BigDecimal duracionVallaMeses;

	@Column(name = "DURACION_CONTENEDOR_MESES")
	private BigDecimal duracionContenedorMeses;

	@Column(name = "INSTALACION_GRUA")
	private String instalacionGrua;

	@Column(name = "PLAZO_EJECUCION")
	private BigDecimal plazoEjecucion;

	@Column(name = "PLAZO_INICIO")
	private BigDecimal plazoInicio;

	@Column(name = "PRESUPUESTO_TOTAL")
	private BigDecimal presupuestoTotal;

	@Column(name = "SUPERFICIE_AFECTADA")
	private BigDecimal superficieAfectada;

	@Column(name = "TIPO_OBRA")
	private String tipoObra;

	@Column(name="VALLAS")
	private String vallas;

	@Column(name = "OCUPACION_VIA_PUBLICA")
	private String ocupacionViaPublica;

	// bi-directional One-to-many association to LcParteAutonoma
	@OneToMany(mappedBy = "lcObra")
	private Set<LcParteAutonomaVO> partesAutonomas;

	public List<LcParteAutonomaVO> getPartesAutonomasAsList() {
		List<LcParteAutonomaVO> lista;
		if (partesAutonomas != null && partesAutonomas.isEmpty()) {
			lista = new ArrayList<LcParteAutonomaVO>(partesAutonomas);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	public Long getIdDatosObra() {
		return this.idDatosObra;
	}

	public void setIdDatosObra(Long idDatosObra) {
		this.idDatosObra = idDatosObra;
	}

	public String getAndamios() {
		return this.andamios;
	}

	public void setAndamios(String andamios) {
		this.andamios = andamios;
	}

	public String getContenedorViaPublica() {
		return this.contenedorViaPublica;
	}

	public void setContenedorViaPublica(String contenedorViaPublica) {
		this.contenedorViaPublica = contenedorViaPublica;
	}

	public String getDescripcionObra() {
		return this.descripcionObra;
	}

	public void setDescripcionObra(String descripcionObra) {
		this.descripcionObra = descripcionObra;
	}

	public String getInstalacionGrua() {
		return this.instalacionGrua;
	}

	public void setInstalacionGrua(String instalacionGrua) {
		this.instalacionGrua = instalacionGrua;
	}

	public BigDecimal getPresupuestoTotal() {
		return this.presupuestoTotal;
	}

	public void setPresupuestoTotal(BigDecimal presupuestoTotal) {
		this.presupuestoTotal = presupuestoTotal;
	}

	public String getTipoObra() {
		return this.tipoObra;
	}

	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}

	public String getVallas() {
		return this.vallas;
	}

	public void setVallas(String vallas) {
		this.vallas = vallas;
	}

	public String getOcupacionViaPublica() {
		return ocupacionViaPublica;
	}

	public void setOcupacionViaPublica(String ocupacionViaPublica) {
		this.ocupacionViaPublica = ocupacionViaPublica;
	}

	public Set<LcParteAutonomaVO> getPartesAutonomas() {
		return partesAutonomas;
	}

	public void setPartesAutonomas(Set<LcParteAutonomaVO> partesAutonomas) {
		this.partesAutonomas = partesAutonomas;
	}

	public BigDecimal getDuracionAndamioMeses() {
		return duracionAndamioMeses;
	}

	public void setDuracionAndamioMeses(BigDecimal duracionAndamioMeses) {
		this.duracionAndamioMeses = duracionAndamioMeses;
	}

	public BigDecimal getDuracionGruaMeses() {
		return duracionGruaMeses;
	}

	public void setDuracionGruaMeses(BigDecimal duracionGruaMeses) {
		this.duracionGruaMeses = duracionGruaMeses;
	}

	public BigDecimal getDuracionVallaMeses() {
		return duracionVallaMeses;
	}

	public void setDuracionVallaMeses(BigDecimal duracionVallaMeses) {
		this.duracionVallaMeses = duracionVallaMeses;
	}

	public BigDecimal getDuracionContenedorMeses() {
		return duracionContenedorMeses;
	}

	public void setDuracionContenedorMeses(BigDecimal duracionContenedorMeses) {
		this.duracionContenedorMeses = duracionContenedorMeses;
	}

	public BigDecimal getPlazoEjecucion() {
		return plazoEjecucion;
	}

	public void setPlazoEjecucion(BigDecimal plazoEjecucion) {
		this.plazoEjecucion = plazoEjecucion;
	}

	public BigDecimal getPlazoInicio() {
		return plazoInicio;
	}

	public void setPlazoInicio(BigDecimal plazoInicio) {
		this.plazoInicio = plazoInicio;
	}

	public BigDecimal getSuperficieAfectada() {
		return superficieAfectada;
	}

	public void setSuperficieAfectada(BigDecimal superficieAfectada) {
		this.superficieAfectada = superficieAfectada;
	}

}