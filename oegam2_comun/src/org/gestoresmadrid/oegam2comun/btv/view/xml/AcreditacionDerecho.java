package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name="Acreditacion_Derecho",propOrder = {
	    "solicitud","consentimiento",
	    "motivoTransmision", "adjudicacionSubasta", "fallecimientoDonacion"
	})
	public class AcreditacionDerecho {
		@XmlElement(name = "Solicitud", required = true)
		protected String solicitud;
		
		@XmlElement(name = "Consentimiento", required = false)
		protected String consentimiento;
		
		@XmlElement(name = "Motivo_Transmision", required = false)
		protected AcreditacionDerecho.MotivoTransmision motivoTransmision;
		 
		@XmlElement(name = "Adjudicacion_Subasta", required = false)
		protected AcreditacionDerecho.AdjudicacionSubasta adjudicacionSubasta;
		
		@XmlElement(name = "Fallecimiento_Donacion", required = false)
		protected AcreditacionDerecho.FallecimientoDonacion fallecimientoDonacion;
		
		
		
		public String getSolicitud() {
			return solicitud;
		}

		public void setSolicitud(String solicitud) {
			this.solicitud = solicitud;
		}

		public String getConsentimiento() {
			return consentimiento;
		}

		public void setConsentimiento(String consentimiento) {
			this.consentimiento = consentimiento;
		}

		public AcreditacionDerecho.MotivoTransmision getMotivoTransmision() {
			return motivoTransmision;
		}

		public void setMotivoTransmision(
				AcreditacionDerecho.MotivoTransmision motivoTransmision) {
			this.motivoTransmision = motivoTransmision;
		}

		public AcreditacionDerecho.AdjudicacionSubasta getAdjudicacionSubasta() {
			return adjudicacionSubasta;
		}

		public void setAdjudicacionSubasta(
				AcreditacionDerecho.AdjudicacionSubasta adjudicacionSubasta) {
			this.adjudicacionSubasta = adjudicacionSubasta;
		}

		public AcreditacionDerecho.FallecimientoDonacion getFallecimientoDonacion() {
			return fallecimientoDonacion;
		}

		public void setFallecimientoDonacion(
				AcreditacionDerecho.FallecimientoDonacion fallecimientoDonacion) {
			this.fallecimientoDonacion = fallecimientoDonacion;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"contrato","factura"
		})
		public static class MotivoTransmision {

			@XmlElement(name = "Acreditacion_Posesion", required = false)
			protected String contrato;
			
			@XmlElement(name = "Factura", required = false)
			protected String factura;

			public String getContrato() {
				return contrato;
			}

			public void setContrato(String contrato) {
				this.contrato = contrato;
			}

			public String getFactura() {
				return factura;
			}

			public void setFactura(String factura) {
				this.factura = factura;
			}

		}
		
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"acta","sentencia"
		})
		public static class AdjudicacionSubasta {

			@XmlElement(name = "Acta_Adjudicacion_Subasta", required = false)
			protected String acta;
			
			@XmlElement(name = "Sentencia_Judicial_Adjudicacion", required = false)
			protected String sentencia;

			public String getActa() {
				return acta;
			}

			public void setActa(String acta) {
				this.acta = acta;
			}

			public String getSentencia() {
				return sentencia;
			}

			public void setSentencia(String sentencia) {
				this.sentencia = sentencia;
			}

		}
		
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"acreditacion","factura"
		})
		public static class FallecimientoDonacion {

			@XmlElement(name = "Acreditacion_Posesion", required = false)
			protected String acreditacionPosesion;
			
			@XmlElement(name = "Acreditacion_Herencia", required = false)
			protected String acreditacionHerencia;

			public String getAcreditacionPosesion() {
				return acreditacionPosesion;
			}

			public void setAcreditacionPosesion(String acreditacionPosesion) {
				this.acreditacionPosesion = acreditacionPosesion;
			}

			public String getAcreditacionHerencia() {
				return acreditacionHerencia;
			}

			public void setAcreditacionHerencia(String acreditacionHerencia) {
				this.acreditacionHerencia = acreditacionHerencia;
			}

		}

}
