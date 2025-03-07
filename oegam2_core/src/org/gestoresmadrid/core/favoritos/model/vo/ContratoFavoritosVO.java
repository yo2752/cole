package org.gestoresmadrid.core.favoritos.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.general.model.vo.FuncionVO;

/**
 * The persistent class for the CONTRATO_FAVORITOS database table.
 */
@Entity
@Table(name = "CONTRATO_FAVORITOS")
public class ContratoFavoritosVO implements Serializable {

	private static final long serialVersionUID = 1726097438416727680L;

	@Id
	@SequenceGenerator(name = "favorito_secuencia", sequenceName = "ID_FAVORITO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "favorito_secuencia")
	@Column(name = "ID_FAVORITO")
	private Long idFavorito;

	@Column(name = "CONTRATO")
	private BigDecimal contrato;

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "CODIGO_APLICACION", referencedColumnName = "CODIGO_APLICACION"),
		@JoinColumn(name = "CODIGO_FUNCION", referencedColumnName = "CODIGO_FUNCION") 
	})
	private FuncionVO funcion;

	public ContratoFavoritosVO() {}

	public BigDecimal getContrato() {
		return contrato;
	}

	public void setContrato(BigDecimal contrato) {
		this.contrato = contrato;
	}

	public Long getIdFavorito() {
		return idFavorito;
	}

	public void setIdFavorito(Long idFavorito) {
		this.idFavorito = idFavorito;
	}

	public FuncionVO getFuncion() {
		return funcion;
	}

	public void setFuncion(FuncionVO funcion) {
		this.funcion = funcion;
	}

}