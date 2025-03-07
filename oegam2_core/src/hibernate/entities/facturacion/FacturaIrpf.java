package hibernate.entities.facturacion;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the FACTURA_IRPF database table.
 * 
 */
@Entity
@Table(name="FACTURA_IRPF")
public class FacturaIrpf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_IRPF")
	private String idIrpf;

	private String irpf;

	public FacturaIrpf() {
	}

	public String getIdIrpf() {
		return this.idIrpf;
	}

	public void setIdIrpf(String idIrpf) {
		this.idIrpf = idIrpf;
	}

	public String getIrpf() {
		return this.irpf;
	}

	public void setIrpf(String irpf) {
		this.irpf = irpf;
	}

}