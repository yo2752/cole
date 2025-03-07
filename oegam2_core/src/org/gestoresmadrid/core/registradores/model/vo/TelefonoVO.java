package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TELEFONO database table.
 * 
 */
@Entity
@Table(name="TELEFONO")
public class TelefonoVO implements Serializable {

	private static final long serialVersionUID = -4328413180291579435L;

	@Id
	@SequenceGenerator(name = "telefono_secuencia", sequenceName = "TELEFONO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "telefono_secuencia")
	@Column(name="ID_TELEFONO")
	private long idTelefono;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	private String numero;

	private String tipo;

	//bi-directional many-to-one association to IntervinienteRegistro
	@ManyToOne
	@JoinColumn(name="ID_INTERVINIENTE")
	private IntervinienteRegistroVO intervinienteRegistro;

	public TelefonoVO() {
	}

	public long getIdTelefono() {
		return this.idTelefono;
	}

	public void setIdTelefono(long idTelefono) {
		this.idTelefono = idTelefono;
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

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public IntervinienteRegistroVO getIntervinienteRegistro() {
		return this.intervinienteRegistro;
	}

	public void setIntervinienteRegistro(IntervinienteRegistroVO intervinienteRegistro) {
		this.intervinienteRegistro = intervinienteRegistro;
	}

}