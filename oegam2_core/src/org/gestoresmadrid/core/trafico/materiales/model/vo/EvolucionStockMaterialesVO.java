package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name = "EVOLUCION_STOCK_MATERIALES")
public class EvolucionStockMaterialesVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3847005663305354784L;
	
	@Id
	@SequenceGenerator(name = "EVOLUCION_STOCK_MATERIALES_SEQUENCE", sequenceName = "SEQ_ID_EVOLUCION_STOCK")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "EVOLUCION_STOCK_MATERIALES_SEQUENCE")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ID_STOCK")
	private Long idStock;
	
	@Column(name= "USUARIO")
	private Long usuario;
	
	@Column(name =  "UNIDADES_ANTERIORES")
	private BigDecimal unidadesAnteriores;
	
	@Column(name = "UNIDADES_ACTUALES")
	private BigDecimal unidadesActuales;
	
	@Column(name="FECHA")
	private Date fecha_recarga;
	
	@Column(name = "OPERACION")
	private String operacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USUARIO", insertable=false, updatable=false)
	private UsuarioVO usuarioVO;
	
	public Date getFecha_recarga() {
		return fecha_recarga;
	}

	public void setFecha_recarga(Date fecha_recarga) {
		this.fecha_recarga = fecha_recarga;
	}

	public BigDecimal getUnidadesAnteriores() {
		return unidadesAnteriores;
	}

	public void setUnidadesAnteriores(BigDecimal unidadesAnteriores) {
		this.unidadesAnteriores = unidadesAnteriores;
	}

	public BigDecimal getUnidadesActuales() {
		return unidadesActuales;
	}

	public void setUnidadesActuales(BigDecimal unidadesActuales) {
		this.unidadesActuales = unidadesActuales;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdStock() {
		return idStock;
	}

	public void setIdStock(Long idStock) {
		this.idStock = idStock;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public UsuarioVO getUsuarioVO() {
		return usuarioVO;
	}

	public void setUsuarioVO(UsuarioVO usuarioVO) {
		this.usuarioVO = usuarioVO;
	}

}
