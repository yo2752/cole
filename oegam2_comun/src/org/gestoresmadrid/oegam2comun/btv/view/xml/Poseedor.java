package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "Poseedor",propOrder = {
	    "datosPoseedor","codigoIAE",
	    "datosRepresentante"
	})
	public class Poseedor {
		@XmlElement(name = "Datos_Poseedor", required = true)
		protected Poseedor.DatosPossedor datosPoseedor;
		
		@XmlElement(name = "Codigo_IAE", required = false)
		protected String codigoIAE;
		
		@XmlElement(name = "Datos_Representante", required = false)
		protected Poseedor.DatosRepresentante datosRepresentante;
		 
		public Poseedor.DatosPossedor getDatosPoseedor() {
			return datosPoseedor;
		}
		public void setDatosPoseedor(Poseedor.DatosPossedor datosPoseedor) {
			this.datosPoseedor = datosPoseedor;
		}
		
		public String getCodigoIAE() {
			return codigoIAE;
		}
		public void setCodigoIAE(String codigoIAE) {
			this.codigoIAE = codigoIAE;
		}
		public Poseedor.DatosRepresentante getDatosRepresentante() {
			return datosRepresentante;
		}
		public void setDatosRepresentante(
				Poseedor.DatosRepresentante datosRepresentante) {
			this.datosRepresentante = datosRepresentante;
		}


		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"datosFiliacion","sexo","doi"
		})
		public static class DatosPossedor {
			@XmlElement(name = "Datos_Filiacion", required = true)
			protected Poseedor.DatosPossedor.DatosFiliacion datosFiliacion;
			
			@XmlElement(name = "Sexo", required = false)
			protected String sexo;
			
			@XmlElement(name = "DOI", required = false)
			protected String doi;
			
			public Poseedor.DatosPossedor.DatosFiliacion getDatosFiliacion() {
				return datosFiliacion;
			}

			public void setDatosFiliacion(
					Poseedor.DatosPossedor.DatosFiliacion datosFiliacion) {
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
				protected Poseedor.DatosPossedor.DatosFiliacion.PersonaFisica personaFisica;
				
				@XmlElement(name = "Persona_Juridica", required = false)
				protected Poseedor.DatosPossedor.DatosFiliacion.PersonaJuridica personaJuridica;
				
				public Poseedor.DatosPossedor.DatosFiliacion.PersonaFisica getPersonaFisica() {
					return personaFisica;
				}

				public void setPersonaFisica(
						Poseedor.DatosPossedor.DatosFiliacion.PersonaFisica personaFisica) {
					this.personaFisica = personaFisica;
				}

				public Poseedor.DatosPossedor.DatosFiliacion.PersonaJuridica getPersonaJuridica() {
					return personaJuridica;
				}

				public void setPersonaJuridica(
						Poseedor.DatosPossedor.DatosFiliacion.PersonaJuridica personaJuridica) {
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

			@XmlElement(name = "Actualizacion_Domicilio", required = true)
			protected String actualizacionDomicilio;
			
			@XmlElement(name = "Domicilio", required = false)
			protected Poseedor.DatosRepresentante.Domicilio domicilio;
			
			public String getDoi() {
				return doi;
			}

			public void setDoi(String doi) {
				this.doi = doi;
			}
			
			public String getActualizacionDomicilio() {
				return actualizacionDomicilio;
			}

			public void setActualizacionDomicilio(String actualizacionDomicilio) {
				this.actualizacionDomicilio = actualizacionDomicilio;
			}

			public Poseedor.DatosRepresentante.Domicilio getDomicilio() {
				return domicilio;
			}

			public void setDomicilio(Poseedor.DatosRepresentante.Domicilio domicilio) {
				this.domicilio = domicilio;
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
				
				@XmlElement(name="Localidad",required=true)
				protected String localidad;
				
				@XmlElement(name="Provincia",required=true)
				protected String provincia;
				
				@XmlElement(name="Código Postal",required=true)
				protected String codPostal;
				
				@XmlElement(name="Tipo Vía",required=true)
				protected String tipoVia;
				
				@XmlElement(name="Nombre Vía",required=true)
				protected String nombreVia;
				
				@XmlElement(name = "Número", required = true)
			    protected String numero;
				
				@XmlElement(name="Kilómetro",required=false)
				protected String km;
				
				@XmlElement(name="Hectómetro",required=false)
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
				
				@XmlElement(name="País",required=true)
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

}
