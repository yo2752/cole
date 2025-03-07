package org.gestoresmadrid.oegamBajas.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name="Cotitulares_Transmitentes",propOrder = {
	    "transmitente",
	})
	public class CoTitularTransmitente {
		@XmlElement(name = "Transmitente", required = true)
		protected CoTitularTransmitente.Transmitente transmitente;
		
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"datosTransmitente"
		})
		public static class Transmitente {
			@XmlElement(name = "Datos_Transmitente", required = false)
			protected TitularTransmitente.DatosTransmitente datosTransmitente;

			public TitularTransmitente.DatosTransmitente getDatosTransmitente() {
				return datosTransmitente;
			}

			public void setDatosTransmitente(
					TitularTransmitente.DatosTransmitente datosTransmitente) {
				this.datosTransmitente = datosTransmitente;
			}
			
		}

		public CoTitularTransmitente.Transmitente getTransmitente() {
			return transmitente;
		}

		public void setTransmitente(CoTitularTransmitente.Transmitente transmitente) {
			this.transmitente = transmitente;
		}
		
}
