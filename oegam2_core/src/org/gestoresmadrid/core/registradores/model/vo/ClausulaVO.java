package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the CLAUSULA database table.
 * 
 */
@Entity
@Table(name="CLAUSULA")
public class ClausulaVO implements Serializable {

	private static final long serialVersionUID = 6213882810141974755L;

	@Id
	@SequenceGenerator(name = "clausula_secuencia", sequenceName = "CLAUSULA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "clausula_secuencia")
	@Column(name="ID_CLAUSULA")
	private long idClausula;

	private String descripcion;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	private String nombre;

	private BigDecimal numero;

	@Column(name="TIPO_CLAUSULA")
	private String tipoClausula;

	@Column(name="ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TRAMITE_REGISTRO",referencedColumnName="ID_TRAMITE_REGISTRO", insertable=false, updatable=false)
	private TramiteRegRbmVO tramiteRegRbm;

	public ClausulaVO() {
	}

	public long getIdClausula() {
		return this.idClausula;
	}

	public void setIdClausula(long idClausula) {
		this.idClausula = idClausula;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getNumero() {
		return this.numero;
	}

	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}

	public String getTipoClausula() {
		return this.tipoClausula;
	}

	public void setTipoClausula(String tipoClausula) {
		this.tipoClausula = tipoClausula;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

}