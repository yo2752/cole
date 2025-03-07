package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name="Titular_Transmitente",propOrder = {
	    "datosTransmitente","datosRepresentante",
	    "autonomoTransmitente"
	})
	public class TitularTransmitente {
		@XmlElement(name = "Datos_Transmitente", required = true)
		protected TitularTransmitente.DatosTransmitente datosTransmitente;
		
		@XmlElement(name = "Datos_Representante", required = false)
		protected TitularTransmitente.DatosRepresentante datosRepresentante;
		 
		@XmlElement(name = "Autonomo_Transmitente", required = false)
		protected String autonomoTransmitente;
		
		public TitularTransmitente.DatosTransmitente getDatosTransmitente() {
			return datosTransmitente;
		}
		public void setDatosTransmitente(
				TitularTransmitente.DatosTransmitente datosTransmitente) {
			this.datosTransmitente = datosTransmitente;
		}

		public String getAutonomoTransmitente() {
			return autonomoTransmitente;
		}
		public void setAutonomoTransmitente(String autonomoTransmitente) {
			this.autonomoTransmitente = autonomoTransmitente;
		}
		
		public TitularTransmitente.DatosRepresentante getDatosRepresentante() {
			return datosRepresentante;
		}
		public void setDatosRepresentante(
				TitularTransmitente.DatosRepresentante datosRepresentante) {
			this.datosRepresentante = datosRepresentante;
		}


		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"datosFiliacion","sexo","doi"
		})
		public static class DatosTransmitente {
			@XmlElement(name = "Datos_Filiacion", required = true)
			protected TitularTransmitente.DatosTransmitente.DatosFiliacion datosFiliacion;
			
			@XmlElement(name = "Sexo", required = false)
			protected String sexo;
			
			@XmlElement(name = "DOI", required = false)
			protected String doi;
			
			public TitularTransmitente.DatosTransmitente.DatosFiliacion getDatosFiliacion() {
				return datosFiliacion;
			}

			public void setDatosFiliacion(
					TitularTransmitente.DatosTransmitente.DatosFiliacion datosFiliacion) {
				this.datosFiliacion = datosFiliacion;
			}

			public String getSexo() {
				return sexo;
			}

			public void setSexo(String sexo) {
				this.sexo = sexo;
			}

			public String getDoi() {
				return doi;
			}

			public void setDoi(String doi) {
				this.doi = doi;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"personaFisica","personaJuridica"
			})
			public static class DatosFiliacion {
				@XmlElement(name = "Persona_Fisica", required = false)
				protected TitularTransmitente.DatosTransmitente.DatosFiliacion.PersonaFisica personaFisica;
				
				@XmlElement(name = "Persona_Juridica", required = false)
				protected TitularTransmitente.DatosTransmitente.DatosFiliacion.PersonaJuridica personaJuridica;
				
				public TitularTransmitente.DatosTransmitente.DatosFiliacion.PersonaFisica getPersonaFisica() {
					return personaFisica;
				}

				public void setPersonaFisica(
						TitularTransmitente.DatosTransmitente.DatosFiliacion.PersonaFisica personaFisica) {
					this.personaFisica = personaFisica;
				}

				public TitularTransmitente.DatosTransmitente.DatosFiliacion.PersonaJuridica getPersonaJuridica() {
					return personaJuridica;
				}

				public void setPersonaJuridica(
						TitularTransmitente.DatosTransmitente.DatosFiliacion.PersonaJuridica personaJuridica) {
					this.personaJuridica = personaJuridica;
				}

				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = {
						"nombre","primerApellido","segundoApellido","fechaNacimiento"
				})
				public static class PersonaFisica {

					@XmlElement(name = "Nombre", required = false)
					protected String nombre;
					@XmlElement(name = "Primer_Apellido", required = true)
				    protected String primerApellido;
					@XmlElement(name = "Segundo_Apellido", required = false)
					protected String segundoApellido;
					@XmlElement(name = "Fecha_Nacimiento", required = true)
				    protected String fechaNacimiento;
					
					public String getNombre() {
						return nombre;
					}
					public void setNombre(String nombre) {
						this.nombre = nombre;
					}
					public String getPrimerApellido() {
						return primerApellido;
					}
					public void setPrimerApellido(String primerApellido) {
						this.primerApellido = primerApellido;
					}
					public String getSegundoApellido() {
						return segundoApellido;
					}
					public void setSegundoApellido(String segundoApellido) {
						this.segundoApellido = segundoApellido;
					}
					public String getFechaNacimiento() {
						return fechaNacimiento;
					}
					public void setFechaNacimiento(String fechaNacimiento) {
						this.fechaNacimiento = fechaNacimiento;
					}
				}
				
				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = {
						"razonSocial"
				})
				public static class PersonaJuridica {

					@XmlElement(name = "Razon_Social", required = false)
					protected String razonSocial;

					public String getRazonSocial() {
						return razonSocial;
					}

					public void setRazonSocial(String razonSocial) {
						this.razonSocial = razonSocial;
					}
					
				}
			}
		}
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"doi"
		})
		public static class DatosRepresentante {

			@XmlElement(name = "DOI", required = true)
			protected String doi;

			public String getDoi() {
				return doi;
			}

			public void setDoi(String doi) {
				this.doi = doi;
			}
			
		}

}
