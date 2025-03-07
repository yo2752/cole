package org.gestoresmadrid.oegamBajas.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "Cotitulares_Adquirentes",propOrder = {
	    "adquiriente",
	})
	public class CoTitularAdquiriente {
		@XmlElement(name = "Adquirente", required = true)
		protected TitularAdquiriente adquiriente;

		public TitularAdquiriente getAdquiriente() {
			return adquiriente;
		}

		public void setAdquiriente(TitularAdquiriente adquiriente) {
			this.adquiriente = adquiriente;
		}
		
		
}
