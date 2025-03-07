package org.gestoresmadrid.oegamBajas.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name="Acreditacion_Actividad",propOrder = {
	    "vehiculosAgricolas","vehiculosTransporte"
	})
	public class AcreditacionActividad {
		@XmlElement(name = "Vehiculos_Agricolas", required = true)
		protected String vehiculosAgricolas;
		
		@XmlElement(name = "Vehiculos_Transporte", required = true)
		protected String vehiculosTransporte;

		public String getVehiculosAgricolas() {
			return vehiculosAgricolas;
		}

		public void setVehiculosAgricolas(String vehiculosAgricolas) {
			this.vehiculosAgricolas = vehiculosAgricolas;
		}

		public String getVehiculosTransporte() {
			return vehiculosTransporte;
		}

		public void setVehiculosTransporte(String vehiculosTransporte) {
			this.vehiculosTransporte = vehiculosTransporte;
		}

	}
