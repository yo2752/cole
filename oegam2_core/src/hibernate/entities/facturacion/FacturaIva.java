package hibernate.entities.facturacion;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the FACTURA_IVA database table.
 * 
 */
@Entity
@Table(name="FACTURA_IVA")
public class FacturaIva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_IVA")
	private String idIva;

	private String iva;

	public FacturaIva() {
	}

	public String getIdIva() {
		return this.idIva;
	}

	public void setIdIva(String idIva) {
		this.idIva = idIva;
	}

	public String getIva() {
		return this.iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

}