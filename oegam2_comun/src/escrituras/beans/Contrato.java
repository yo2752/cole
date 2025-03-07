package escrituras.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Bean que almacena datos de la tabla CONTRATO.
 *
 */
public class Contrato {
	private String numColegiado;	
	

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}


	private BigDecimal idContrato;
	private BigDecimal idTipoContrato;
	private BigDecimal estadoContrato;//  CHECK (ESTADO IN (1,2,3)),
	private String cif;
	private String razonSocial;
	private String anagramaContrato;
	private Direccion direccion;
	private String telefono;
	private String correoElectronico;
	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	
	
	public Contrato() {
	}

	public Contrato(boolean inicializar) {
		this();
		if (inicializar){
			direccion = new Direccion(true);
		}
	}
	public BigDecimal getIdContrato() {
		return idContrato;
	}


	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}


	public BigDecimal getIdTipoContrato() {
		return idTipoContrato;
	}


	public void setIdTipoContrato(BigDecimal idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}


	public BigDecimal getEstadoContrato() {
		return estadoContrato;
	}


	public void setEstadoContrato(BigDecimal estadoContrato) {
		this.estadoContrato = estadoContrato;
	}


	public String getCif() {
		return cif;
	}


	public void setCif(String cif) {
		this.cif = cif;
	}


	public String getRazonSocial() {
		return razonSocial;
	}


	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}


	public String getAnagramaContrato() {
		return anagramaContrato;
	}


	public void setAnagramaContrato(String anagramaContrato) {
		this.anagramaContrato = anagramaContrato;
	}


	public Direccion getDireccion() {
		return direccion;
	}


	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getCorreoElectronico() {
		return correoElectronico;
	}


	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}


	public Timestamp getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Timestamp getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anagramaContrato == null) ? 0 : anagramaContrato.hashCode());
		result = prime * result + ((cif == null) ? 0 : cif.hashCode());
		result = prime * result + ((correoElectronico == null) ? 0 : correoElectronico.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((estadoContrato == null) ? 0 : estadoContrato.hashCode());
		result = prime * result + ((fechaFin == null) ? 0 : fechaFin.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((idContrato == null) ? 0 : idContrato.hashCode());
		result = prime * result + ((idTipoContrato == null) ? 0 : idTipoContrato.hashCode());
		result = prime * result + ((numColegiado == null) ? 0 : numColegiado.hashCode());
		result = prime * result + ((razonSocial == null) ? 0 : razonSocial.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contrato other = (Contrato) obj;
		if (anagramaContrato == null) {
			if (other.anagramaContrato != null)
				return false;
		} else if (!anagramaContrato.equals(other.anagramaContrato))
			return false;
		if (cif == null) {
			if (other.cif != null)
				return false;
		} else if (!cif.equals(other.cif))
			return false;
		if (correoElectronico == null) {
			if (other.correoElectronico != null)
				return false;
		} else if (!correoElectronico.equals(other.correoElectronico))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (estadoContrato == null) {
			if (other.estadoContrato != null)
				return false;
		} else if (!estadoContrato.equals(other.estadoContrato))
			return false;
		if (fechaFin == null) {
			if (other.fechaFin != null)
				return false;
		} else if (!fechaFin.equals(other.fechaFin))
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (idContrato == null) {
			if (other.idContrato != null)
				return false;
		} else if (!idContrato.equals(other.idContrato))
			return false;
		if (idTipoContrato == null) {
			if (other.idTipoContrato != null)
				return false;
		} else if (!idTipoContrato.equals(other.idTipoContrato))
			return false;
		if (razonSocial == null) {
			if (other.razonSocial != null)
				return false;
		} else if (!razonSocial.equals(other.razonSocial))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}
	
	
	  
}
