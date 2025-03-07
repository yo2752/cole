package org.gestoresmadrid.core.bien.model.vo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.sistemaExplotacion.model.vo.SistemaExplotacionVO;
import org.gestoresmadrid.core.usoRustico.model.vo.UsoRusticoVO;

@Entity
@Table(name="BIEN_RUSTICO")
@DiscriminatorValue(value = "RUSTICO")
public class BienRusticoVO extends BienVO{
	
	private static final long serialVersionUID = -6533363400352549320L;
	
	@Column(name="SUPERFICIE")
	private BigDecimal superficie;
	
	@Column(name="PARAJE")
	private String paraje;
	
	@Column(name="POLIGONO")
	private String poligono;
	
	@Column(name="PARCELA")
	private String parcela;
	
	@Column(name="SUBPARCELA")
	private String subParcela;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="UNIDAD_METRICA")
	private UnidadMetricaVO unidadMetrica;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USO_RUSTICO")
	private UsoRusticoVO usoRustico;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SISTEMA_EXPLOTACION")
	private SistemaExplotacionVO sistemaExplotacion;

	public BigDecimal getSuperficie() {
		return superficie;
	}

	public void setSuperficie(BigDecimal superficie) {
		this.superficie = superficie;
	}

	public String getParaje() {
		return paraje;
	}

	public void setParaje(String paraje) {
		this.paraje = paraje;
	}

	public String getPoligono() {
		return poligono;
	}

	public void setPoligono(String poligono) {
		this.poligono = poligono;
	}

	public String getParcela() {
		return parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	public String getSubParcela() {
		return subParcela;
	}

	public void setSubParcela(String subParcela) {
		this.subParcela = subParcela;
	}

	public UnidadMetricaVO getUnidadMetrica() {
		return unidadMetrica;
	}

	public void setUnidadMetrica(UnidadMetricaVO unidadMetrica) {
		this.unidadMetrica = unidadMetrica;
	}

	public UsoRusticoVO getUsoRustico() {
		return usoRustico;
	}

	public void setUsoRustico(UsoRusticoVO usoRustico) {
		this.usoRustico = usoRustico;
	}

	public SistemaExplotacionVO getSistemaExplotacion() {
		return sistemaExplotacion;
	}

	public void setSistemaExplotacion(SistemaExplotacionVO sistemaExplotacion) {
		this.sistemaExplotacion = sistemaExplotacion;
	}
	
}
