package org.gestoresmadrid.oegamBajas.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Datos_Vehiculo", propOrder = {
    "datosMatriculacion",
    "bastidor"
})
public class DatosVehiculo {

	 @XmlElement(name = "Datos_Matriculacion", required = true)
	 protected DatosVehiculo.DatosMatriculacion datosMatriculacion;
	 @XmlElement(name="Bastidor",required=true)
	 protected String bastidor;
	
	 
	 public DatosVehiculo.DatosMatriculacion getDatosMatriculacion() {
		return datosMatriculacion;
	}

	public void setDatosMatriculacion(
			DatosVehiculo.DatosMatriculacion datosMatriculacion) {
		this.datosMatriculacion = datosMatriculacion;
	}

	

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}




	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
	        "matricula"
	})
	public static class DatosMatriculacion {

		@XmlElement(name = "Matricula", required = true)
	    protected String matricula;
		
	
		
		public String getMatricula() {
			return matricula;
		}

		public void setMatricula(String matricula) {
			this.matricula = matricula;
		}

	

	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
	        "cambioServicio","servicio"
	})
	public static class DatosServicio {

		@XmlElement(name = "Cambio Servicio", required = true)
	    protected String cambioServicio;
		
		@XmlElement(name="Servicio",required=true)
		protected String servicio;

		public String getCambioServicio() {
			return cambioServicio;
		}

		public void setCambioServicio(String cambioServicio) {
			this.cambioServicio = cambioServicio;
		}

		public String getServicio() {
			return servicio;
		}

		public void setServicio(String servicio) {
			this.servicio = servicio;
		}
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
	        "resultadoItv","fechaRealizacionItv","fechaCaducidadItv","estacionItv",
	        "provinciaItv","motivoItv"
	})
	public static class DatosItv {

		@XmlElement(name = "Resultado ITV", required = true)
	    protected String resultadoItv;
		
		@XmlElement(name="Fecha realización ITV",required=true)
		protected String fechaRealizacionItv;
		
		@XmlElement(name="Fecha Caducidad ITV",required=true)
		protected String fechaCaducidadItv;
		
		@XmlElement(name="Estación ITV",required=true)
		protected String estacionItv;
		
		@XmlElement(name="Provincia de ITV",required=true)
		protected String provinciaItv;
		
		@XmlElement(name="Motivo de la ITV",required=true)
		protected String motivoItv;

		public String getResultadoItv() {
			return resultadoItv;
		}

		public void setResultadoItv(String resultadoItv) {
			this.resultadoItv = resultadoItv;
		}

		public String getFechaRealizacionItv() {
			return fechaRealizacionItv;
		}

		public void setFechaRealizacionItv(String fechaRealizacionItv) {
			this.fechaRealizacionItv = fechaRealizacionItv;
		}

		public String getFechaCaducidadItv() {
			return fechaCaducidadItv;
		}

		public void setFechaCaducidadItv(String fechaCaducidadItv) {
			this.fechaCaducidadItv = fechaCaducidadItv;
		}

		public String getEstacionItv() {
			return estacionItv;
		}

		public void setEstacionItv(String estacionItv) {
			this.estacionItv = estacionItv;
		}

		public String getProvinciaItv() {
			return provinciaItv;
		}

		public void setProvinciaItv(String provinciaItv) {
			this.provinciaItv = provinciaItv;
		}

		public String getMotivoItv() {
			return motivoItv;
		}

		public void setMotivoItv(String motivoItv) {
			this.motivoItv = motivoItv;
		}
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
	        "municipio","localidad","provincia","codPostal",
	        "tipoVia","nombreVia", "numero", "km", "hm", "bloque", "portal", "escalera", 
	        "planta", "puerta", "pais"
	})
	public static class Domicilio {

		@XmlElement(name = "Municipio", required = true)
	    protected String municipio;
		
		@XmlElement(name="Localidad",required=false)
		protected String localidad;
		
		@XmlElement(name="Provincia",required=true)
		protected String provincia;
		
		@XmlElement(name="C�digo Postal",required=true)
		protected String codPostal;
		
		@XmlElement(name="Tipo V�a",required=true)
		protected String tipoVia;
		
		@XmlElement(name="Nombre V�a",required=true)
		protected String nombreVia;
		
		@XmlElement(name = "N�mero", required = true)
	    protected String numero;
		
		@XmlElement(name="Kil�metro",required=false)
		protected String km;
		
		@XmlElement(name="Hect�metro",required=false)
		protected String hm;
		
		@XmlElement(name = "Bloque", required = false)
	    protected String bloque;
		
		@XmlElement(name="Portal",required=false)
		protected String portal;
		
		@XmlElement(name="Escalera",required=false)
		protected String escalera;
		
		@XmlElement(name = "Planta", required = false)
	    protected String planta;
		
		@XmlElement(name="Puerta",required=false)
		protected String puerta;
		
		@XmlElement(name="Pa�s",required=true)
		protected String pais;

		public String getMunicipio() {
			return municipio;
		}

		public void setMunicipio(String municipio) {
			this.municipio = municipio;
		}

		public String getLocalidad() {
			return localidad;
		}

		public void setLocalidad(String localidad) {
			this.localidad = localidad;
		}

		public String getProvincia() {
			return provincia;
		}

		public void setProvincia(String provincia) {
			this.provincia = provincia;
		}

		public String getCodPostal() {
			return codPostal;
		}

		public void setCodPostal(String codPostal) {
			this.codPostal = codPostal;
		}

		public String getTipoVia() {
			return tipoVia;
		}

		public void setTipoVia(String tipoVia) {
			this.tipoVia = tipoVia;
		}

		public String getNombreVia() {
			return nombreVia;
		}

		public void setNombreVia(String nombreVia) {
			this.nombreVia = nombreVia;
		}

		public String getNumero() {
			return numero;
		}

		public void setNumero(String numero) {
			this.numero = numero;
		}

		public String getKm() {
			return km;
		}

		public void setKm(String km) {
			this.km = km;
		}

		public String getHm() {
			return hm;
		}

		public void setHm(String hm) {
			this.hm = hm;
		}

		public String getBloque() {
			return bloque;
		}

		public void setBloque(String bloque) {
			this.bloque = bloque;
		}

		public String getPortal() {
			return portal;
		}

		public void setPortal(String portal) {
			this.portal = portal;
		}

		public String getEscalera() {
			return escalera;
		}

		public void setEscalera(String escalera) {
			this.escalera = escalera;
		}

		public String getPlanta() {
			return planta;
		}

		public void setPlanta(String planta) {
			this.planta = planta;
		}

		public String getPuerta() {
			return puerta;
		}

		public void setPuerta(String puerta) {
			this.puerta = puerta;
		}

		public String getPais() {
			return pais;
		}

		public void setPais(String pais) {
			this.pais = pais;
		}
		
	}
}
