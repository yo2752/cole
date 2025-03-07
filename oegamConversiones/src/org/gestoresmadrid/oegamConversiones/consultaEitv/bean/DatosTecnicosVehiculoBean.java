package org.gestoresmadrid.oegamConversiones.consultaEitv.bean;

import java.io.Serializable;

public class DatosTecnicosVehiculoBean implements Serializable{

	private static final long serialVersionUID = 7635708858544259431L;
	
	private DatosGeneralBean datosGeneral;
	private DatosCertificacionBean datosCertificacion;
	private DatosDiligenciaBean datosDiligencia;
	private DatosFabricanteBean datosFabricante;
	private DatosLimitacionesBean datosLimitaciones;
	private DatosObservacionesBean datosObservaciones;
	private DatosTraficoBean datosTrafico;
	private DatosCaracteristicasTecnicasBean datosCaracteristicasTecnicas;
	
	public DatosTecnicosVehiculoBean() {
		super();
	}

	public DatosGeneralBean getDatosGeneral() {
		return datosGeneral;
	}

	public void setDatosGeneral(DatosGeneralBean datosGeneral) {
		this.datosGeneral = datosGeneral;
	}

	public DatosCertificacionBean getDatosCertificacion() {
		return datosCertificacion;
	}

	public void setDatosCertificacion(DatosCertificacionBean datosCertificacion) {
		this.datosCertificacion = datosCertificacion;
	}

	public DatosDiligenciaBean getDatosDiligencia() {
		return datosDiligencia;
	}

	public void setDatosDiligencia(DatosDiligenciaBean datosDiligencia) {
		this.datosDiligencia = datosDiligencia;
	}

	public DatosFabricanteBean getDatosFabricante() {
		return datosFabricante;
	}

	public void setDatosFabricante(DatosFabricanteBean datosFabricante) {
		this.datosFabricante = datosFabricante;
	}

	public DatosLimitacionesBean getDatosLimitaciones() {
		return datosLimitaciones;
	}

	public void setDatosLimitaciones(DatosLimitacionesBean datosLimitaciones) {
		this.datosLimitaciones = datosLimitaciones;
	}

	public DatosObservacionesBean getDatosObservaciones() {
		return datosObservaciones;
	}

	public void setDatosObservaciones(DatosObservacionesBean datosObservaciones) {
		this.datosObservaciones = datosObservaciones;
	}

	public DatosTraficoBean getDatosTrafico() {
		return datosTrafico;
	}

	public void setDatosTrafico(DatosTraficoBean datosTrafico) {
		this.datosTrafico = datosTrafico;
	}

	public DatosCaracteristicasTecnicasBean getDatosCaracteristicasTecnicas() {
		return datosCaracteristicasTecnicas;
	}

	public void setDatosCaracteristicasTecnicas(DatosCaracteristicasTecnicasBean datosCaracteristicasTecnicas) {
		this.datosCaracteristicasTecnicas = datosCaracteristicasTecnicas;
	}

}
