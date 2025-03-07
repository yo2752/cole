package org.gestoresmadrid.oegamBajas.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Datos_Tutela", propOrder = {
    "tutelaTransmitente","tutelaAdquiriente"
})
public class DatosTutela {

	@XmlElement(name = "Tutela_Transmitente", required = true)
	protected DatosTutela.TutelaTransmitente tutelaTransmitente;
	@XmlElement(name="Tutela adquirente",required=true)
	protected DatosTutela.TutelaAdquiriente tutelaAdquiriente;
	 
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"doiTutor"
	})
	public static class TutelaTransmitente {

		@XmlElement(name = "DOI tutor", required = true)
	    protected String doiTutor;

		public String getDoiTutor() {
			return doiTutor;
		}

		public void setDoiTutor(String doiTutor) {
			this.doiTutor = doiTutor;
		}

	}
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"tipoTutela","doiTutor"
	})
	public static class TutelaAdquiriente {

		@XmlElement(name = "Tipo de tutela", required = true)
        protected String tipoTutela;
		
		@XmlElement(name = "DOI tutor", required = true)
	    protected String doiTutor;

		public String getDoiTutor() {
			return doiTutor;
		}

		public void setDoiTutor(String doiTutor) {
			this.doiTutor = doiTutor;
		}

		public String getTipoTutela() {
			return tipoTutela;
		}

		public void setTipoTutela(String tipoTutela) {
			this.tipoTutela = tipoTutela;
		}
		
	}
}
