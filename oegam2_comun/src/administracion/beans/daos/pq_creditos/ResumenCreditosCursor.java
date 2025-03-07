package administracion.beans.daos.pq_creditos;
 
import java.math.BigDecimal;

import utilidades.estructuras.FechaFraccionada;

public class ResumenCreditosCursor {

	private String num_colegiado;
	private String via;
	private BigDecimal id_contrato;
	private String razon_social;
	private String tipo_credito;
	private BigDecimal cantidad_sumada;
	private BigDecimal cantidad_restada;
	private String descripcion_credito;
	private BigDecimal creditos;
	private BigDecimal creditosDisponibles;
	private FechaFraccionada fecha;
	// Usado en resumenCreditosMes.jsp 
	private BigDecimal costeCredito;

	public String getNum_colegiado() {
		return num_colegiado;
	}
	public void setNum_colegiado(String numColegiado) {
		num_colegiado = numColegiado;
	}
	public BigDecimal getId_contrato() {
		return id_contrato;
	}
	public void setId_contrato(BigDecimal idContrato) {
		id_contrato = idContrato;
	}
	public String getRazon_social() {
		return razon_social;
	}
	public void setRazon_social(String razonSocial) {
		razon_social = razonSocial;
	}
	public String getTipo_credito() {
		//Map session = ActionContext.getContext().getSession();
		//FechaFraccionada fecha = (FechaFraccionada)session.get(ConstantesSession.FECHA_ALTA_RESUMEN);
		FechaFraccionada fecha = getFecha();
		if (null==fecha)
			fecha = new FechaFraccionada();
		String linkHistorico = "";
		linkHistorico += "<a href='";
		linkHistorico += "buscarDesdeResumHistorico.action?tipoCredito=";
		linkHistorico += this.tipo_credito;
		linkHistorico += "&idContrato=";
		//BigDecimal contrato = (BigDecimal)session.get(ConstantesSession.ID_CONTRATO_RESUMEN);
		linkHistorico += getId_contrato()!=null?getId_contrato().toString():"";
		linkHistorico += "&fechaAlta.diaInicio=";
		linkHistorico += fecha.getDiaInicio();
		linkHistorico += "&fechaAlta.mesInicio=";
		linkHistorico += fecha.getMesInicio();
		linkHistorico += "&fechaAlta.anioInicio=";
		linkHistorico += fecha.getAnioInicio();
		linkHistorico += "&fechaAlta.diaFin=";
		linkHistorico += fecha.getDiaFin();
		linkHistorico += "&fechaAlta.mesFin=";
		linkHistorico += fecha.getMesFin();
		linkHistorico += "&fechaAlta.anioFin=";
		linkHistorico += fecha.getAnioFin();
		linkHistorico += "'>";
		linkHistorico += this.tipo_credito;
		linkHistorico += "</a>";
		return linkHistorico;
	}
	public void setTipo_credito(String tipoCredito) {
		tipo_credito = tipoCredito;
	}
	public BigDecimal getCantidad_sumada() {
		return cantidad_sumada;
	}
	public void setCantidad_sumada(BigDecimal cantidadSumada) {
		cantidad_sumada = cantidadSumada;
	}
	public BigDecimal getCantidad_restada() {
		return cantidad_restada;
	}
	public void setCantidad_restada(BigDecimal cantidadRestada) {
		cantidad_restada = cantidadRestada;
	}
	public String getDescripcion_credito() {
		return descripcion_credito;
	}
	public void setDescripcion_credito(String descripcionCredito) {
		descripcion_credito = descripcionCredito;
	}
	public BigDecimal getCreditos() {
		return creditos;
	}
	public void setCreditos(BigDecimal creditos) {
		this.creditos = creditos;
	}
	public BigDecimal getCreditosDisponibles() {
		return creditosDisponibles;
	}
	public void setCreditosDisponibles(BigDecimal creditosDisponibles) {
		this.creditosDisponibles = creditosDisponibles;
	}
	public FechaFraccionada getFecha() {
		return fecha;
	}
	public void setFecha(FechaFraccionada fecha) {
		this.fecha = fecha;
	}
	public BigDecimal getCosteCredito() {
		return costeCredito;
	}
	public void setCosteCredito(BigDecimal costeCredito) {
		this.costeCredito = costeCredito;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}

	
	
	
	
	
}
