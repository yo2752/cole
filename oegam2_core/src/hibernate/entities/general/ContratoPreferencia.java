package hibernate.entities.general;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the CONTRATO_PREFERENCIAS database table.
 * 
 */
@Entity
@Table(name="CONTRATO_PREFERENCIAS")
public class ContratoPreferencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_CONTRATO")
	private long idContrato;

	@Column(name="ORDEN_DOCBASE_YB")
	private BigDecimal ordenDocbaseYb;

	//bi-directional many-to-one association to Contrato
	@ManyToOne(fetch=FetchType.LAZY, optional = true)
	@JoinColumn(name="ID_CONTRATO", insertable=false, updatable=false)
	private Contrato contrato;

    public ContratoPreferencia() {
    }

	public long getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(long idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getOrdenDocbaseYb() {
		return this.ordenDocbaseYb;
	}

	public void setOrdenDocbaseYb(BigDecimal ordenDocbaseYb) {
		this.ordenDocbaseYb = ordenDocbaseYb;
	}

	public Contrato getContrato() {
		return this.contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
}