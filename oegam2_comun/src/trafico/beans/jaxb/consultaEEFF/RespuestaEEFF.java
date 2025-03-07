package trafico.beans.jaxb.consultaEEFF;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.thoughtworks.xstream.annotations.XStreamAlias;



/**
 * @author ext_amt
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO", propOrder = { 
		"infoErrores",
		"____hashCodeCalc",
		"datossimpleeitv",
		"accionesEITV"
})

@XmlRootElement(name = "es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO")
@XStreamAlias("es.trafico.servicios.vehiculos.custodiaitv.beans.ConsultaEITVRespuestaDTO")
public class RespuestaEEFF {

	@XmlElement(name="infoErrores")
	@XStreamAlias("infoErrores")
	protected RespuestaEEFF.infoErrores infoErrores;
	
	@XmlElement(name="____hashCodeCalc")
	@XStreamAlias("____hashCodeCalc")
	protected String ____hashCodeCalc;
	
	@XmlElement(name="datossimpleeitv")
	@XStreamAlias("datossimpleeitv")
	protected RespuestaEEFF.datossimpleeitv datossimpleeitv;
	
	@XmlElement(name = "accionesEITV")
	@XStreamAlias("accionesEITV")
	protected RespuestaEEFF.accionesEITV accionesEITV;
	
	/**
	 * @return the infoErrores
	 */
	public RespuestaEEFF.infoErrores getInfoErrores() {
		return infoErrores;
	}

	/**
	 * @param infoErrores the infoErrores to set
	 */
	public void setInfoErrores(RespuestaEEFF.infoErrores infoErrores) {
		this.infoErrores = infoErrores;
	}

	/**
	 * @return the ____hashCodeCalc
	 */
	public String get____hashCodeCalc() {
		return ____hashCodeCalc;
	}

	/**
	 * @param ____hashCodeCalc the ____hashCodeCalc to set
	 */
	public void set____hashCodeCalc(String ____hashCodeCalc) {
		this.____hashCodeCalc = ____hashCodeCalc;
	}

	/**
	 * @return the datossimpleeitv
	 */
	public RespuestaEEFF.datossimpleeitv getDatossimpleeitv() {
		return datossimpleeitv;
	}

	/**
	 * @param datossimpleeitv the datossimpleeitv to set
	 */
	public void setDatossimpleeitv(RespuestaEEFF.datossimpleeitv datossimpleeitv) {
		this.datossimpleeitv = datossimpleeitv;
	}

	/**
	 * @return the accionesEITV
	 */
	public RespuestaEEFF.accionesEITV getAccionesEITV() {
		return accionesEITV;
	}

	/**
	 * @param accionesEITV the accionesEITV to set
	 */
	public void setAccionesEITV(RespuestaEEFF.accionesEITV accionesEITV) {
		this.accionesEITV = accionesEITV;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			
			
	})
	@XStreamAlias("infoErrores")
	public static class infoErrores {

		@XmlElement(name = "es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO")
		@XStreamAlias("es.trafico.servicios.vehiculos.custodiaitv.beans.InfoErrorDTO")
		protected RespuestaEEFF.infoErrores.infoErrorDTO infoErrorDTO;
		
		
		/**
		 * @return the infoErrorDTO
		 */
		public RespuestaEEFF.infoErrores.infoErrorDTO getInfoErrorDTO() {
			return infoErrorDTO;
		}


		/**
		 * @param infoErrorDTO the infoErrorDTO to set
		 */
		public void setInfoErrorDTO(RespuestaEEFF.infoErrores.infoErrorDTO infoErrorDTO) {
			this.infoErrorDTO = infoErrorDTO;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"codigoError",
				"descripcionError",
				"____hashCodeCalc"
		})
		@XStreamAlias("infoErrorDTO")
		public static class infoErrorDTO {
			@XmlElement(name = "codigoError", required = true)
			@XStreamAlias("codigoError")
			protected String codigoError;
			@XmlElement(name = "descripcionError")
			@XStreamAlias("descripcionError")
			protected String descripcionError;
			@XmlElement(name = "____hashCodeCalc")
			@XStreamAlias("____hashCodeCalc")
			protected String ____hashCodeCalc;
			/**
			 * @return the codigoError
			 */
			public String getCodigoError() {
				return codigoError;
			}
			/**
			 * @param codigoError the codigoError to set
			 */
			public void setCodigoError(String codigoError) {
				this.codigoError = codigoError;
			}
			/**
			 * @return the descripcionError
			 */
			public String getDescripcionError() {
				return descripcionError;
			}
			/**
			 * @param descripcionError the descripcionError to set
			 */
			public void setDescripcionError(String descripcionError) {
				this.descripcionError = descripcionError;
			}
			/**
			 * @return the ____hashCodeCalc
			 */
			public String get____hashCodeCalc() {
				return ____hashCodeCalc;
			}
			/**
			 * @param ____hashCodeCalc the ____hashCodeCalc to set
			 */
			public void set____hashCodeCalc(String ____hashCodeCalc) {
				this.____hashCodeCalc = ____hashCodeCalc;
			}
			
		}

	}

	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"baseeitvdto",
			"concesionarioClienteComercial",
			"custodioActual",
			"custodioSiguiente",
			"denominacionEstadoFinanciero",
			"dninifNieClienteFinal",
			"estadoBastidor",
			"fechaFacturaFinal",
			"firCif",
			"FIRMarca",
			"importeFacturaFinal",
			"numeroFacturaFinal",
			"custodioAnterior",
			"custodioFinal",
			"entidadCredito",
			"datosHistoricosITV",
			"nombreApellidosClienteFinal",
			"direccionCliente",
			"____hashCodeCalc"
	})
	@XStreamAlias("datossimpleeitv")
	public static class datossimpleeitv {

		@XmlElement(name = "baseeitvdto", required = true)
		@XStreamAlias("baseeitvdto")
		protected RespuestaEEFF.datossimpleeitv.baseeitvdto baseeitvdto;
		@XmlElement(name = "concesionarioClienteComercial")
		@XStreamAlias("concesionarioClienteComercial")
		protected String concesionarioClienteComercial;
		@XmlElement(name = "custodioActual")
		@XStreamAlias("custodioActual")
		protected String custodioActual;
		@XmlElement(name = "custodioSiguiente")
		@XStreamAlias("custodioSiguiente")
		protected String custodioSiguiente;
		@XmlElement(name = "denominacionEstadoFinanciero")
		@XStreamAlias("denominacionEstadoFinanciero")
		protected String denominacionEstadoFinanciero;
		@XmlElement(name = "dninifNieClienteFinal")
		@XStreamAlias("dninifNieClienteFinal")
		protected String dninifNieClienteFinal;
		@XmlElement(name = "estadoBastidor")
		@XStreamAlias("estadoBastidor")
		protected String estadoBastidor;
		@XmlElement(name = "fechaFacturaFinal")
		@XStreamAlias("fechaFacturaFinal")
		protected String fechaFacturaFinal;
		@XmlElement(name = "firCif", required = true)
		@XStreamAlias("firCif")
		protected String firCif;
		@XmlElement(name = "FIRMarca", required = true)
		@XStreamAlias("FIRMarca")
		protected String FIRMarca;
		@XmlElement(name = "importeFacturaFinal")
		@XStreamAlias("importeFacturaFinal")
		protected String importeFacturaFinal;
		@XmlElement(name = "numeroFacturaFinal")
		@XStreamAlias("numeroFacturaFinal")
		protected String numeroFacturaFinal;
		@XmlElement(name = "custodioAnterior")
		@XStreamAlias("custodioAnterior")
		protected String custodioAnterior;
		@XmlElement(name = "custodioFinal")
		@XStreamAlias("custodioFinal")
		protected String custodioFinal;
		@XmlElement(name = "entidadCredito")
		@XStreamAlias("entidadCredito")
		protected String entidadCredito;
		@XmlElement(name = "datosHistoricosITV")
		@XStreamAlias("datosHistoricosITV")
		protected RespuestaEEFF.datossimpleeitv.datosHistoricosITV datosHistoricosITV;
		@XmlElement(name = "nombreApellidosClienteFinal")
		@XStreamAlias("nombreApellidosClienteFinal")
		protected RespuestaEEFF.datossimpleeitv.nombreApellidosClienteFinal nombreApellidosClienteFinal;
		@XmlElement(name = "direccionCliente")
		@XStreamAlias("direccionCliente")
		protected RespuestaEEFF.datossimpleeitv.direccionCliente direccionCliente;
		@XmlElement(name = "____hashCodeCalc")
		@XStreamAlias("____hashCodeCalc")
		private String ____hashCodeCalc;
		
		
		/**
		 * @return the dninifNieClienteFinal
		 */
		public String getDninifNieClienteFinal() {
			return dninifNieClienteFinal;
		}

		/**
		 * @param dninifNieClienteFinal the dninifNieClienteFinal to set
		 */
		public void setDninifNieClienteFinal(String dninifNieClienteFinal) {
			this.dninifNieClienteFinal = dninifNieClienteFinal;
		}

		/**
		 * @return the ____hashCodeCalc
		 */
		public String get____hashCodeCalc() {
			return ____hashCodeCalc;
		}

		/**
		 * @param ____hashCodeCalc the ____hashCodeCalc to set
		 */
		public void set____hashCodeCalc(String ____hashCodeCalc) {
			this.____hashCodeCalc = ____hashCodeCalc;
		}

		/**
		 * @return the baseeitvdto
		 */
		public RespuestaEEFF.datossimpleeitv.baseeitvdto getBaseeitvdto() {
			return baseeitvdto;
		}

		/**
		 * @param baseitvdto the baseitvdto to set
		 */
		public void setBaseeitvdto(RespuestaEEFF.datossimpleeitv.baseeitvdto baseeitvdto) {
			this.baseeitvdto = baseeitvdto;
		}

		/**
		 * @return the concesionarioClienteComercial
		 */
		public String getConcesionarioClienteComercial() {
			return concesionarioClienteComercial;
		}

		/**
		 * @param concesionarioClienteComercial the concesionarioClienteComercial to set
		 */
		public void setConcesionarioClienteComercial(
				String concesionarioClienteComercial) {
			this.concesionarioClienteComercial = concesionarioClienteComercial;
		}

		/**
		 * @return the custodioActual
		 */
		public String getCustodioActual() {
			return custodioActual;
		}

		/**
		 * @param custodioActual the custodioActual to set
		 */
		public void setCustodioActual(String custodioActual) {
			this.custodioActual = custodioActual;
		}

		/**
		 * @return the custodioSiguiente
		 */
		public String getCustodioSiguiente() {
			return custodioSiguiente;
		}

		/**
		 * @param custodioSiguiente the custodioSiguiente to set
		 */
		public void setCustodioSiguiente(String custodioSiguiente) {
			this.custodioSiguiente = custodioSiguiente;
		}

		/**
		 * @return the denominacionEstadoFinanciero
		 */
		public String getDenominacionEstadoFinanciero() {
			return denominacionEstadoFinanciero;
		}

		/**
		 * @param denominacionEstadoFinanciero the denominacionEstadoFinanciero to set
		 */
		public void setDenominacionEstadoFinanciero(String denominacionEstadoFinanciero) {
			this.denominacionEstadoFinanciero = denominacionEstadoFinanciero;
		}

		/**
		 * @return the estadoBastidor
		 */
		public String getEstadoBastidor() {
			return estadoBastidor;
		}

		/**
		 * @param estadoBastidor the estadoBastidor to set
		 */
		public void setEstadoBastidor(String estadoBastidor) {
			this.estadoBastidor = estadoBastidor;
		}

		/**
		 * @return the fechaFacturaFinal
		 */
		public String getFechaFacturaFinal() {
			return fechaFacturaFinal;
		}

		/**
		 * @param fechaFacturaFinal the fechaFacturaFinal to set
		 */
		public void setFechaFacturaFinal(String fechaFacturaFinal) {
			this.fechaFacturaFinal = fechaFacturaFinal;
		}

		/**
		 * @return the firCif
		 */
		public String getFirCif() {
			return firCif;
		}

		/**
		 * @param firCif the firCif to set
		 */
		public void setFirCif(String firCif) {
			this.firCif = firCif;
		}

		/**
		 * @return the fIRMarca
		 */
		public String getFIRMarca() {
			return FIRMarca;
		}

		/**
		 * @param fIRMarca the fIRMarca to set
		 */
		public void setFIRMarca(String fIRMarca) {
			FIRMarca = fIRMarca;
		}

		/**
		 * @return the importeFacturaFinal
		 */
		public String getImporteFacturaFinal() {
			return importeFacturaFinal;
		}

		/**
		 * @param importeFacturaFinal the importeFacturaFinal to set
		 */
		public void setImporteFacturaFinal(String importeFacturaFinal) {
			this.importeFacturaFinal = importeFacturaFinal;
		}

		/**
		 * @return the numeroFacturaFinal
		 */
		public String getNumeroFacturaFinal() {
			return numeroFacturaFinal;
		}

		/**
		 * @param numeroFacturaFinal the numeroFacturaFinal to set
		 */
		public void setNumeroFacturaFinal(String numeroFacturaFinal) {
			this.numeroFacturaFinal = numeroFacturaFinal;
		}

		/**
		 * @return the custodioAnterior
		 */
		public String getCustodioAnterior() {
			return custodioAnterior;
		}

		/**
		 * @param custodioAnterior the custodioAnterior to set
		 */
		public void setCustodioAnterior(String custodioAnterior) {
			this.custodioAnterior = custodioAnterior;
		}

		/**
		 * @return the custodioFinal
		 */
		public String getCustodioFinal() {
			return custodioFinal;
		}

		/**
		 * @param custodioFinal the custodioFinal to set
		 */
		public void setCustodioFinal(String custodioFinal) {
			this.custodioFinal = custodioFinal;
		}

		/**
		 * @return the entidadCredito
		 */
		public String getEntidadCredito() {
			return entidadCredito;
		}

		/**
		 * @param entidadCredito the entidadCredito to set
		 */
		public void setEntidadCredito(String entidadCredito) {
			this.entidadCredito = entidadCredito;
		}

		/**
		 * @return the datosHistoricosITV
		 */
		public RespuestaEEFF.datossimpleeitv.datosHistoricosITV getDatosHistoricosITV() {
			return datosHistoricosITV;
		}

		/**
		 * @param datosHistoricosITV the datosHistoricosITV to set
		 */
		public void setDatosHistoricosITV(
				RespuestaEEFF.datossimpleeitv.datosHistoricosITV datosHistoricosITV) {
			this.datosHistoricosITV = datosHistoricosITV;
		}

		/**
		 * @return the nombreApellidosClienteFinal
		 */
		public RespuestaEEFF.datossimpleeitv.nombreApellidosClienteFinal getNombreApellidosClienteFinal() {
			return nombreApellidosClienteFinal;
		}

		/**
		 * @param nombreApellidosClienteFinal the nombreApellidosClienteFinal to set
		 */
		public void setNombreApellidosClienteFinal(
				RespuestaEEFF.datossimpleeitv.nombreApellidosClienteFinal nombreApellidosClienteFinal) {
			this.nombreApellidosClienteFinal = nombreApellidosClienteFinal;
		}

		/**
		 * @return the direccionCliente
		 */
		public RespuestaEEFF.datossimpleeitv.direccionCliente getDireccionCliente() {
			return direccionCliente;
		}

		/**
		 * @param direccionCliente the direccionCliente to set
		 */
		public void setDireccionCliente(
				RespuestaEEFF.datossimpleeitv.direccionCliente direccionCliente) {
			this.direccionCliente = direccionCliente;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {})
		@XStreamAlias("direccionCliente")
		public static class direccionCliente {
			@XmlElement(name = "string")
			@XStreamAlias("string")
			protected List<String> string;

			/**
			 * @return the string
			 */
			public List<String> getString() {
				return string;
			}

			/**
			 * @param string the string to set
			 */
			public void setString(List<String> string) {
				this.string = string;
			}

		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
		})
		@XStreamAlias("nombreApellidosClienteFinal")
		public static class nombreApellidosClienteFinal {
			
			@XmlElement(name = "string")
			@XStreamAlias("string")
			protected List<String> string;

			/**
			 * @return the string
			 */
			public List<String> getString() {
				return string;
			}

			/**
			 * @param string the string to set
			 */
			public void setString(List<String> string) {
				this.string = string;
			}
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { 
				"custodioActualAnterior",
				"custodioAnteriorAnterior",
				"custodioFinalAnterior",
				"custodioSiguienteAnterior",
				"denominacionEstadoFinancieroAnterior",
				"____hashCodeCalc"
		})
		@XStreamAlias("datosHistoricosITV")
		public static class datosHistoricosITV {

			@XmlElement(name = "custodioActualAnterior")
			@XStreamAlias("custodioActualAnterior")
			protected String custodioActualAnterior;
			@XmlElement(name = "custodioAnteriorAnterior")
			@XStreamAlias("custodioAnteriorAnterior")
			protected String custodioAnteriorAnterior;
			@XmlElement(name = "custodioFinalAnterior")
			@XStreamAlias("custodioFinalAnterior")
			protected String custodioFinalAnterior;
			@XmlElement(name = "custodioSiguienteAnterior")
			@XStreamAlias("custodioSiguienteAnterior")
			protected String custodioSiguienteAnterior;
			@XmlElement(name = "denominacionEstadoFinancieroAnterior")
			@XStreamAlias("denominacionEstadoFinancieroAnterior")
			protected String denominacionEstadoFinancieroAnterior;
			@XmlElement(name = "____hashCodeCalc")
			@XStreamAlias("____hashCodeCalc")
			protected String ____hashCodeCalc;
			
			/**
			 * @return the ____hashCodeCalc
			 */
			public String get____hashCodeCalc() {
				return ____hashCodeCalc;
			}

			/**
			 * @param ____hashCodeCalc the ____hashCodeCalc to set
			 */
			public void set____hashCodeCalc(String ____hashCodeCalc) {
				this.____hashCodeCalc = ____hashCodeCalc;
			}

			/**
			 * @return the custodioActualAnterior
			 */
			public String getCustodioActualAnterior() {
				return custodioActualAnterior;
			}

			/**
			 * @param custodioActualAnterior
			 *            the custodioActualAnterior to set
			 */
			public void setCustodioActualAnterior(String custodioActualAnterior) {
				this.custodioActualAnterior = custodioActualAnterior;
			}

			/**
			 * @return the custodioAnteriorAnterior
			 */
			public String getCustodioAnteriorAnterior() {
				return custodioAnteriorAnterior;
			}

			/**
			 * @param custodioAnteriorAnterior
			 *            the custodioAnteriorAnterior to set
			 */
			public void setCustodioAnteriorAnterior(String custodioAnteriorAnterior) {
				this.custodioAnteriorAnterior = custodioAnteriorAnterior;
			}

			/**
			 * @return the custodioFinalAnterior
			 */
			public String getCustodioFinalAnterior() {
				return custodioFinalAnterior;
			}

			/**
			 * @param custodioFinalAnterior
			 *            the custodioFinalAnterior to set
			 */
			public void setCustodioFinalAnterior(String custodioFinalAnterior) {
				this.custodioFinalAnterior = custodioFinalAnterior;
			}

			/**
			 * @return the custodioSiguienteAnterior
			 */
			public String getCustodioSiguienteAnterior() {
				return custodioSiguienteAnterior;
			}

			/**
			 * @param custodioSiguienteAnterior
			 *            the custodioSiguienteAnterior to set
			 */
			public void setCustodioSiguienteAnterior(
					String custodioSiguienteAnterior) {
				this.custodioSiguienteAnterior = custodioSiguienteAnterior;
			}

			/**
			 * @return the denominacionEstadoFinancieroAnterior
			 */
			public String getDenominacionEstadoFinancieroAnterior() {
				return denominacionEstadoFinancieroAnterior;
			}

			/**
			 * @param denominacionEstadoFinancieroAnterior
			 *            the denominacionEstadoFinancieroAnterior to set
			 */
			public void setDenominacionEstadoFinancieroAnterior(
					String denominacionEstadoFinancieroAnterior) {
				this.denominacionEstadoFinancieroAnterior = denominacionEstadoFinancieroAnterior;
			}

		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { 
				"bastidor",
				"nive",
				"____hashCodeCalc"
		})
		@XStreamAlias("baseeitvdto")
		public static class baseeitvdto {

			@XmlElement(name = "bastidor")
			@XStreamAlias("bastidor")
			private String bastidor;
			@XmlElement(name = "nive")
			@XStreamAlias("nive")
			private String nive;
			@XmlElement(name = "____hashCodeCalc")
			@XStreamAlias("____hashCodeCalc")
			private String ____hashCodeCalc;
			
			/**
			 * @return the ____hashCodeCalc
			 */
			public String get____hashCodeCalc() {
				return ____hashCodeCalc;
			}

			/**
			 * @param ____hashCodeCalc the ____hashCodeCalc to set
			 */
			public void set____hashCodeCalc(String ____hashCodeCalc) {
				this.____hashCodeCalc = ____hashCodeCalc;
			}

			/**
			 * @return the bastidor
			 */
			public String getBastidor() {
				return bastidor;
			}

			/**
			 * @param bastidor
			 *            the bastidor to set
			 */
			public void setBastidor(String bastidor) {
				this.bastidor = bastidor;
			}

			/**
			 * @return the nive
			 */
			public String getNive() {
				return nive;
			}

			/**
			 * @param nive
			 *            the nive to set
			 */
			public void setNive(String nive) {
				this.nive = nive;
			}
		}
	}
	


	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { 
	})
	@XStreamAlias("accionesEITV")
	public static class accionesEITV {

		@XmlElement(name = "string")
		@XStreamAlias("string")
		private List<String> string;

		/**
		 * @param string the string to set
		 */
		public void setString(List<String> string) {
			this.string = string;
		}

	}
}
