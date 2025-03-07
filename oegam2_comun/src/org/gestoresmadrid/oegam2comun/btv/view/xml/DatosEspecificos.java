package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "datosColegio",
    "datosGestoria",
    "datosGestor",
    "numeroExpedienteGestor",
    "datosExpediente",
    "tasas",
    "datosVehiculo",
    "titular",
    "solicitante",
    "indicadores",
    "textoLegal",
    "firmaGestor"
})
@XmlRootElement(name = "Datos_Especificos")
public class DatosEspecificos {
	
	
    @XmlElement(name = "Datos_Colegio", required = true)
    protected DatosEspecificos.DatosColegio datosColegio;
    @XmlElement(name = "Datos_Gestoria", required = true)
    protected DatosEspecificos.DatosGestoria datosGestoria;
    @XmlElement(name = "Datos_Gestor", required = true)
    protected DatosEspecificos.DatosGestor datosGestor;
    @XmlElement(name = "Numero_Expediente_Gestor")
    protected String numeroExpedienteGestor;
    @XmlElement(name = "Datos_Expediente", required = true)
    protected DatosEspecificos.DatosExpediente datosExpediente;
    @XmlElement(name = "Tasas", required = true)
    protected DatosEspecificos.Tasas tasas;
    @XmlElement(name = "Datos_Vehiculo", required = true)
    protected DatosVehiculo datosVehiculo;
    @XmlElement(name = "Titular", required = true)
    protected DatosEspecificos.Titular titular;
   	@XmlElement(name = "Solicitante", required = false)
    protected  DatosEspecificos.Solicitante solicitante;
    @XmlElement(name = "Indicadores", required = false)
    protected  DatosEspecificos.Indicadores   indicadores;
    @XmlElement(name = "Texto_Legal", required = true)
    protected TipoTextoLegal textoLegal;
    @XmlElement(name = "Firma_Gestor", required = true)
    protected String firmaGestor;
    
    public DatosEspecificos.DatosColegio getDatosColegio() {
		return datosColegio;
	}

	public void setDatosColegio(DatosEspecificos.DatosColegio datosColegio) {
		this.datosColegio = datosColegio;
	}

	public DatosEspecificos.DatosGestoria getDatosGestoria() {
		return datosGestoria;
	}

	public void setDatosGestoria(DatosEspecificos.DatosGestoria datosGestoria) {
		this.datosGestoria = datosGestoria;
	}

	public DatosEspecificos.DatosGestor getDatosGestor() {
		return datosGestor;
	}

	public void setDatosGestor(DatosEspecificos.DatosGestor datosGestor) {
		this.datosGestor = datosGestor;
	}

	public String getNumeroExpedienteGestor() {
		return numeroExpedienteGestor;
	}

	public void setNumeroExpedienteGestor(String numeroExpedienteGestor) {
		this.numeroExpedienteGestor = numeroExpedienteGestor;
	}

	public DatosEspecificos.DatosExpediente getDatosExpediente() {
		return datosExpediente;
	}

	public void setDatosExpediente(DatosEspecificos.DatosExpediente datosExpediente) {
		this.datosExpediente = datosExpediente;
	}

	public DatosEspecificos.Tasas getTasas() {
		return tasas;
	}

	public void setTasas(DatosEspecificos.Tasas tasas) {
		this.tasas = tasas;
	}

	public DatosVehiculo getDatosVehiculo() {
		return datosVehiculo;
	}

	public void setDatosVehiculo(DatosVehiculo datosVehiculo) {
		this.datosVehiculo = datosVehiculo;
	}

		
	public DatosEspecificos.Titular getTitular() {
		return titular;
	}

	public void setTitular(DatosEspecificos.Titular titular) {
		this.titular = titular;
	}

	

	public DatosEspecificos.Solicitante getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(DatosEspecificos.Solicitante solicitante) {
		this.solicitante = solicitante;
	}

	public DatosEspecificos.Indicadores getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(DatosEspecificos.Indicadores indicadores) {
		this.indicadores = indicadores;
	}

	public TipoTextoLegal getTextoLegal() {
		return textoLegal;
	}

	public void setTextoLegal(TipoTextoLegal textoLegal) {
		this.textoLegal = textoLegal;
	}

	public String getFirmaGestor() {
		return firmaGestor;
	}

	public void setFirmaGestor(String firmaGestor) {
		this.firmaGestor = firmaGestor;
	}

	

	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "id"
    })
    public static class DatosColegio {

        @XmlElement(name = "Id", required = true)
        protected String id;

        public String getId() {
            return id;
        }

        public void setId(String value) {
            this.id = value;
        }

    }
	
	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "doi","fechaNacimiento"
    })
    public static class ConductorHabitual {

        @XmlElement(name = "DOI", required = true)
        protected String doi;
        
        @XmlElement(name = "Fecha_Nacimiento", required = true)
        protected String fechaNacimiento;

		public String getDoi() {
			return doi;
		}

		public void setDoi(String doi) {
			this.doi = doi;
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
	    "autonomo",
	    "codigoIAE"
	})
	public static class DatosAutonomo {

	    @XmlElement(name = "Autonomo", required = true)
	    protected String autonomo;
	    @XmlElement(name = "CodigoIAE", required = false)
	    protected String codigoIAE;
	    
		public String getAutonomo() {
			return autonomo;
		}
		public void setAutonomo(String autonomo) {
			this.autonomo = autonomo;
		}
		public String getCodigoIAE() {
			return codigoIAE;
		}
		public void setCodigoIAE(String codigoIAE) {
			this.codigoIAE = codigoIAE;
		}
	    
	}
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "id"
    })
    public static class DatosGestoria {

        @XmlElement(name = "Id", required = true)
        protected String id;

        public String getId() {
            return id;
        }

        public void setId(String value) {
            this.id = value;
        }

    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "filiacion",
        "doi"
    })
    public static class DatosGestor {

        @XmlElement(name = "Filiacion", required = true)
        protected String filiacion;
        @XmlElement(name = "DOI", required = true)
        protected String doi;

        public String getFiliacion() {
            return filiacion;
        }

        public void setFiliacion(String value) {
            this.filiacion = value;
        }

        public String getDOI() {
            return doi;
        }

        public void setDOI(String value) {
            this.doi = value;
        }

    }
    
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "tipoTramite",
        "motivoTransmision",
        "fechaTramite",
        "jefatura",
        "sucursal"
    })
    public static class DatosExpediente {

        @XmlElement(name = "Tipo_Tramite", required = true)
        protected TipoTramite tipoTramite;
        @XmlElement(name = "Motivo_Transmision", required = true)
        protected String motivoTransmision;
        @XmlElement(name = "Fecha_Tramite", required = true)
        protected String fechaTramite;
        @XmlElement(name = "Jefatura", required = true)
        protected String jefatura;
        @XmlElement(name = "Sucursal", required = true, defaultValue="")
        protected String sucursal;
      

        public String getMotivoTransmision() {
            return motivoTransmision;
        }

        public void setMotivoTransmision(String value) {
            this.motivoTransmision = value;
        }

        public String getFechaTramite() {
            return fechaTramite;
        }

        public void setFechaTramite(String value) {
            this.fechaTramite = value;
        }

        public String getJefatura() {
            return jefatura;
        }

        public void setJefatura(String value) {
            this.jefatura = value;
        }

        public String getSucursal() {
            return sucursal;
        }

        public void setSucursal(String value) {
            this.sucursal = value;
        }

		public TipoTramite getTipoTramite() {
			return tipoTramite;
		}

		public void setTipoTramite(TipoTramite tipoTramite) {
			this.tipoTramite = tipoTramite;
		}
	
        
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
  	@XmlType(name = "", propOrder = {
  			"doi","datosRepresentante"
  	})
  	public static class Titular {
	
    	@XmlElement(name = "DOI", required = true)
  		protected String doi;
  		@XmlElement(name = "Datos_Representante", required = true)
  		protected DatosEspecificos.DatosRepresentante  datosRepresentante;
  		
  		
  		
  		public String getDoi() {
  			return doi;
  		}

  		public void setDoi(String doi) {
  			this.doi = doi;
  		}

		public DatosEspecificos.DatosRepresentante getDatosRepresentante() {
			return datosRepresentante;
		}

		public void setDatosRepresentante(DatosEspecificos.DatosRepresentante datosRepresentante) {
			this.datosRepresentante = datosRepresentante;
		}
  		
		
  	}
      
    
    
    @XmlAccessorType(XmlAccessType.FIELD)
  	@XmlType(name = "", propOrder = {
  			"doi","datosRepresentante"
  	})
  	public static class Solicitante {
	
    	@XmlElement(name = "DOI", required = true)
  		protected String doi;
    	@XmlElement(name = "Datos_Representante", required = true)
  		protected DatosEspecificos.DatosRepresentante  datosRepresentante;
  		
  		public String getDoi() {
  			return doi;
  		}

  		public void setDoi(String doi) {
  			this.doi = doi;
  		}

		public DatosEspecificos.DatosRepresentante getDatosRepresentante() {
			return datosRepresentante;
		}

		public void setDatosRepresentante(DatosEspecificos.DatosRepresentante datosRepresentante) {
			this.datosRepresentante = datosRepresentante;
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
    
    

	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "tasaTramite" })
    public static class Tasas {
    	 @XmlElement(name = "Tasa_Tramite", required = false)
    	 protected String tasaTramite;
    	  
    	 public String getTasaTramite() {
    		 return tasaTramite;
    	 }
	
		public void setTasaTramite(String tasaTramite) {
			this.tasaTramite = tasaTramite;
		}

		
    	 
    }
    
	
	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "paisDestino","acreditacionPosesion" })
    public static class Indicadores {
    	 @XmlElement(name = "Pais_Destino", required = true)
    	 protected String paisDestino;
    	  
    	 @XmlElement(name = "Acreditacion_Posesion", required = false)
    	 protected String acreditacionPosesion;

		public String getPaisDestino() {
			return paisDestino;
		}

		public void setPaisDestino(String paisDestino) {
			this.paisDestino = paisDestino;
		}

		public String getAcreditacionPosesion() {
			return acreditacionPosesion;
		}

		public void setAcreditacionPosesion(String acreditacionPosesion) {
			this.acreditacionPosesion = acreditacionPosesion;
		}
    	 
    	 
    }
	
}
