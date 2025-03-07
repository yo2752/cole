package org.gestoresmadrid.oegamBajas.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name="Acreditacion_Fiscal",propOrder = {
	    "itp","isd",
	    "iva", "iedmt", "dua","ivtm"
	})
	public class AcreditacionFiscal {
		@XmlElement(name = "ITP", required = false)
		protected AcreditacionFiscal.Itp itp;
		
		@XmlElement(name = "ISD", required = false)
		protected AcreditacionFiscal.Isd isd;
		
		@XmlElement(name = "IVA", required = false)
		protected String iva;
		
		@XmlElement(name = "IEDMT", required = false)
		protected AcreditacionFiscal.Iedmt iedmt;
		
		@XmlElement(name = "DUA", required = false)
		protected String dua;
		
		@XmlElement(name = "IVTM", required = false)
		protected AcreditacionFiscal.Ivtm ivtm;
		
		public AcreditacionFiscal.Itp getItp() {
			return itp;
		}

		public void setItp(AcreditacionFiscal.Itp itp) {
			this.itp = itp;
		}

		public AcreditacionFiscal.Isd getIsd() {
			return isd;
		}

		public void setIsd(AcreditacionFiscal.Isd isd) {
			this.isd = isd;
		}

		public String getIva() {
			return iva;
		}

		public void setIva(String iva) {
			this.iva = iva;
		}

		public AcreditacionFiscal.Iedmt getIedmt() {
			return iedmt;
		}

		public void setIedmt(AcreditacionFiscal.Iedmt iedmt) {
			this.iedmt = iedmt;
		}

		public String getDua() {
			return dua;
		}

		public void setDua(String dua) {
			this.dua = dua;
		}

		public AcreditacionFiscal.Ivtm getIvtm() {
			return ivtm;
		}

		public void setIvtm(AcreditacionFiscal.Ivtm ivtm) {
			this.ivtm = ivtm;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"altaIvtm"
		})
		public static class Ivtm {
			@XmlElement(name = "Alta_IVTM", required = false)
			protected String altaIvtm;

			public String getAltaIvtm() {
				return altaIvtm;
			}

			public void setAltaIvtm(String altaIvtm) {
				this.altaIvtm = altaIvtm;
			}
			
		}
		
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"acreditacionPago","acreditacionExencion","acreditacionNoSujeccion"
		})
		public static class Isd {

			@XmlElement(name = "Acreditacion_Pago", required = false)
			protected Isd.AcreditacionPago acreditacionPago;
			
			@XmlElement(name = "Acreditacion_Exencion", required = false)
			protected Isd.AcreditacionExencion acreditacionExencion;
			
			@XmlElement(name = "Acreditacion_No_Sujecion", required = false)
			protected Isd.AcreditacionNoSujeccion acreditacionNoSujeccion;
			
			public Isd.AcreditacionPago getAcreditacionPago() {
				return acreditacionPago;
			}

			public void setAcreditacionPago(Isd.AcreditacionPago acreditacionPago) {
				this.acreditacionPago = acreditacionPago;
			}

			public Isd.AcreditacionExencion getAcreditacionExencion() {
				return acreditacionExencion;
			}

			public void setAcreditacionExencion(
					Isd.AcreditacionExencion acreditacionExencion) {
				this.acreditacionExencion = acreditacionExencion;
			}

			public Isd.AcreditacionNoSujeccion getAcreditacionNoSujeccion() {
				return acreditacionNoSujeccion;
			}

			public void setAcreditacionNoSujeccion(
					Isd.AcreditacionNoSujeccion acreditacionNoSujeccion) {
				this.acreditacionNoSujeccion = acreditacionNoSujeccion;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"modelo","numAutoliquidacion"
			})
			public static class AcreditacionPago {
				@XmlElement(name = "Modelo", required = true)
				protected String modelo;
				
				@XmlElement(name = "Numero_Autoliquidacion", required = true)
				protected String numAutoliquidacion;
				
				public String getModelo() {
					return modelo;
				}

				public void setModelo(String modelo) {
					this.modelo = modelo;
				}

				public String getNumAutoliquidacion() {
					return numAutoliquidacion;
				}

				public void setNumAutoliquidacion(String numAutoliquidacion) {
					this.numAutoliquidacion = numAutoliquidacion;
				}
			}
			
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"modelo","exencion"
			})
			public static class AcreditacionExencion {
				@XmlElement(name = "Modelo", required = true)
				protected String modelo;
				
				@XmlElement(name = "Exencion", required = true)
				protected String exencion;
				
				public String getModelo() {
					return modelo;
				}

				public void setModelo(String modelo) {
					this.modelo = modelo;
				}

				public String getExencion() {
					return exencion;
				}

				public void setExencion(String exencion) {
					this.exencion = exencion;
				}

			}
			
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"modelo","noSujeccion"
			})
			public static class AcreditacionNoSujeccion {
				@XmlElement(name = "Modelo", required = true)
				protected String modelo;
				
				@XmlElement(name = "No_Sujecion", required = true)
				protected String noSujeccion;
				
				public String getModelo() {
					return modelo;
				}

				public void setModelo(String modelo) {
					this.modelo = modelo;
				}

				public String getNoSujeccion() {
					return noSujeccion;
				}

				public void setNoSujeccion(String noSujeccion) {
					this.noSujeccion = noSujeccion;
				}

			}

		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"acreditacionPago","acreditacionExencion","acreditacionNoSujeccion"
		})
		public static class Itp {

			@XmlElement(name = "Acreditacion_Pago", required = false)
			protected Itp.Acreditacion acreditacionPago;
			
			@XmlElement(name = "Acreditacion_Exencion", required = false)
			protected Itp.Acreditacion acreditacionExencion;
			
			@XmlElement(name = "Acreditacion_No_Sujecion", required = false)
			protected Itp.Acreditacion acreditacionNoSujeccion;
			
			public Itp.Acreditacion getAcreditacionPago() {
				return acreditacionPago;
			}

			public void setAcreditacionPago(Itp.Acreditacion acreditacionPago) {
				this.acreditacionPago = acreditacionPago;
			}

			public Itp.Acreditacion getAcreditacionExencion() {
				return acreditacionExencion;
			}

			public void setAcreditacionExencion(Itp.Acreditacion acreditacionExencion) {
				this.acreditacionExencion = acreditacionExencion;
			}

			public Itp.Acreditacion getAcreditacionNoSujeccion() {
				return acreditacionNoSujeccion;
			}

			public void setAcreditacionNoSujeccion(Itp.Acreditacion acreditacionNoSujeccion) {
				this.acreditacionNoSujeccion = acreditacionNoSujeccion;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"modelo","numAutoliquidacion","cet"
			})
			public static class Acreditacion {
				@XmlElement(name = "Modelo", required = true)
				protected String modelo;
				
				@XmlElement(name = "Numero_Autoliquidacion", required = false)
				protected String numAutoliquidacion;
				
				@XmlElement(name = "CET", required = false)
				protected Itp.Acreditacion.Cet cet;
				
				public String getModelo() {
					return modelo;
				}

				public void setModelo(String modelo) {
					this.modelo = modelo;
				}

				public String getNumAutoliquidacion() {
					return numAutoliquidacion;
				}

				public void setNumAutoliquidacion(String numAutoliquidacion) {
					this.numAutoliquidacion = numAutoliquidacion;
				}

				public Itp.Acreditacion.Cet getCet() {
					return cet;
				}

				public void setCet(Itp.Acreditacion.Cet cet) {
					this.cet = cet;
				}

				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = {
						"codElectronico","codProvincia"
				})
				public static class Cet {
					@XmlElement(name = "Codigo_Electronico", required = true)
					protected String codElectronico;
					
					@XmlElement(name = "Codigo_Provincia", required = true)
					protected String codProvincia;

					public String getCodElectronico() {
						return codElectronico;
					}

					public void setCodElectronico(String codElectronico) {
						this.codElectronico = codElectronico;
					}

					public String getCodProvincia() {
						return codProvincia;
					}

					public void setCodProvincia(String codProvincia) {
						this.codProvincia = codProvincia;
					}
					
				}
			}

		}
		
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"acreditacionPago","acreditacionExencion","acreditacionNoSujeccion"
		})
		public static class Iedmt {

			@XmlElement(name = "Acreditacion_Pago", required = false)
			protected Iedmt.Acreditacion acreditacionPago;
			
			@XmlElement(name = "Acreditacion_Exencion", required = false)
			protected Iedmt.Acreditacion acreditacionExencion;
			
			@XmlElement(name = "Acreditacion_No_Sujecion", required = false)
			protected Iedmt.AcreditacionNoSujeccion acreditacionNoSujeccion;
			
			public Iedmt.Acreditacion getAcreditacionPago() {
				return acreditacionPago;
			}

			public void setAcreditacionPago(Iedmt.Acreditacion acreditacionPago) {
				this.acreditacionPago = acreditacionPago;
			}

			public Iedmt.Acreditacion getAcreditacionExencion() {
				return acreditacionExencion;
			}

			public void setAcreditacionExencion(Iedmt.Acreditacion acreditacionExencion) {
				this.acreditacionExencion = acreditacionExencion;
			}

			public Iedmt.AcreditacionNoSujeccion getAcreditacionNoSujeccion() {
				return acreditacionNoSujeccion;
			}

			public void setAcreditacionNoSujeccion(
					Iedmt.AcreditacionNoSujeccion acreditacionNoSujeccion) {
				this.acreditacionNoSujeccion = acreditacionNoSujeccion;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"modelo","noSujeccion","cem"
			})
			public static class AcreditacionNoSujeccion {
				@XmlElement(name = "Modelo", required = true)
				protected String modelo;
				
				@XmlElement(name = "No_Sujecion", required = true)
				protected String noSujeccion;
				
				@XmlElement(name = "CEM", required = false)
				protected Iedmt.Acreditacion.Cem cem;
				
				public String getModelo() {
					return modelo;
				}

				public void setModelo(String modelo) {
					this.modelo = modelo;
				}

				public String getNoSujeccion() {
					return noSujeccion;
				}

				public void setNoSujeccion(String noSujeccion) {
					this.noSujeccion = noSujeccion;
				}

				public Iedmt.Acreditacion.Cem getCem() {
					return cem;
				}

				public void setCem(Iedmt.Acreditacion.Cem cem) {
					this.cem = cem;
				}

				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = {
						"codElectronico","codProvincia"
				})
				public static class Cem {
					@XmlElement(name = "Codigo_Electronico", required = true)
					protected String codElectronico;
					
					@XmlElement(name = "Codigo_Provincia", required = true)
					protected String codProvincia;

					public String getCodElectronico() {
						return codElectronico;
					}

					public void setCodElectronico(String codElectronico) {
						this.codElectronico = codElectronico;
					}

					public String getCodProvincia() {
						return codProvincia;
					}

					public void setCodProvincia(String codProvincia) {
						this.codProvincia = codProvincia;
					}
					
				}
			}
			
			
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"modelo","cem"
			})
			public static class Acreditacion {
				@XmlElement(name = "Modelo", required = true)
				protected String modelo;
				
				@XmlElement(name = "CEM", required = false)
				protected Iedmt.Acreditacion.Cem cem;
				
				public String getModelo() {
					return modelo;
				}

				public void setModelo(String modelo) {
					this.modelo = modelo;
				}

				public Iedmt.Acreditacion.Cem getCem() {
					return cem;
				}

				public void setCem(Iedmt.Acreditacion.Cem cem) {
					this.cem = cem;
				}


				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = {
						"codElectronico","codProvincia"
				})
				public static class Cem {
					@XmlElement(name = "Codigo_Electronico", required = true)
					protected String codElectronico;
					
					@XmlElement(name = "Codigo_Provincia", required = true)
					protected String codProvincia;

					public String getCodElectronico() {
						return codElectronico;
					}

					public void setCodElectronico(String codElectronico) {
						this.codElectronico = codElectronico;
					}

					public String getCodProvincia() {
						return codProvincia;
					}

					public void setCodProvincia(String codProvincia) {
						this.codProvincia = codProvincia;
					}
					
				}
			}

		}
}
