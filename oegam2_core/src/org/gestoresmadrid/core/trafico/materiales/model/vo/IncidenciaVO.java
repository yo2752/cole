package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "GSM_INCIDENCIA")
public class IncidenciaVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8417364999598210003L;

	@Id
	@SequenceGenerator(name = "incidencia_secuencia", sequenceName = "GSM_ID_INCIDENCIA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "incidencia_secuencia")
	@Column(name = "INCIDENCIA_ID")
	private Long incidenciaId;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATERIAL_ID")
    private MaterialVO materialVO;
	
	@Column(name = "OBSERVACIONES")
	private String observaciones;
	
	@Column(name = "Tipo")
	private String tipo;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTOR_ID")
	@Cascade(CascadeType.SAVE_UPDATE)
    private AutorVO autorVO;
	
    @OneToMany(mappedBy="incidenciaVO", fetch=FetchType.LAZY)
    public List<IncidenciaSerialVO> listaSeriales;
    
    @OneToMany(mappedBy="incidenciaVO", fetch=FetchType.LAZY)
    public List<IncidenciaIntervaloVO> listaIntervalos;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="JEFATURA_PROVINCIAL")
	private JefaturaTraficoVO jefaturaProvincial;
	
	@Column(name = "FECHA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@Column(name = "ESTADO")
	private Long estado;

	public Long getIncidenciaId() {
		return incidenciaId;
	}

	public void setIncidenciaId(Long incidenciaId) {
		this.incidenciaId = incidenciaId;
	}

	public MaterialVO getMaterialVO() {
		return materialVO;
	}

	public void setMaterialVO(MaterialVO materialVO) {
		this.materialVO = materialVO;
	}

	public JefaturaTraficoVO getJefaturaProvincial() {
		return jefaturaProvincial;
	}

	public void setJefaturaProvincial(JefaturaTraficoVO jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFecha() {
		return fecha;
	}

	
	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public AutorVO getAutorVO() {
		return autorVO;
	}

	public void setAutorVO(AutorVO autorVO) {
		this.autorVO = autorVO;
	}

	public List<IncidenciaSerialVO> getListaSeriales() {
		return listaSeriales;
	}

	public void setListaSeriales(List<IncidenciaSerialVO> listaSeriales) {
		this.listaSeriales = listaSeriales;
	}

	public List<IncidenciaIntervaloVO> getListaIntervalos() {
		return listaIntervalos;
	}

	public void setListaIntervalos(List<IncidenciaIntervaloVO> listaIntervalos) {
		this.listaIntervalos = listaIntervalos;
	}

	@Override
	public String toString() {
		return "IncidenciaVO [incidenciaId=" + incidenciaId + ", materialVO=" + materialVO + ", observaciones="
				+ observaciones + ", tipo=" + tipo + ", fecha=" + fecha + ", estado=" + estado + "]";
	}
	
	
}
