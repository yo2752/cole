package hibernate.entities.facturacion;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the FACTURA_SLP database table.
 * 
 */
@Entity
@Table(name="FACTURA_SLP")
public class FacturaSlp implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cif;

	@Column(name="CODIGO_POSTAL")
	private String codigoPostal;

	@Column(name="NOMBRE_PROVINCIA")
	private String nombreProvincia;

	@Column(name="NOMBRE_VIA")
	private String nombreVia;

	@Id
	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="RAZON_SOCIAL")
	private String razonSocial;

	private String telefono;

	@Column(name="TEXTO_REGISTRO_MERCANTIL")
	private String textoRegistroMercantil;

	@Column(name="TIPO_VIA")
	private String tipoVia;

	public FacturaSlp() {
	}

	public String getCif() {
		return this.cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getNombreProvincia() {
		return this.nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}

	public String getNombreVia() {
		return this.nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTextoRegistroMercantil() {
		return this.textoRegistroMercantil;
	}

	public void setTextoRegistroMercantil(String textoRegistroMercantil) {
		this.textoRegistroMercantil = textoRegistroMercantil;
	}

	public String getTipoVia() {
		return this.tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

}