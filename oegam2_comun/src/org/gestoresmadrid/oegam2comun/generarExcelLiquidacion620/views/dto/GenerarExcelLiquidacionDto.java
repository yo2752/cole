package org.gestoresmadrid.oegam2comun.generarExcelLiquidacion620.views.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import utilidades.estructuras.FechaFraccionada;


public class GenerarExcelLiquidacionDto implements Serializable {


	private static final long serialVersionUID = -1298598146658638173L;
	private FechaFraccionada fechaLiquidacion;
	private ContratoDto contrato;
	
	
	
	public ContratoDto getContrato() {
		return contrato;
	}
	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}
	public FechaFraccionada getFechaLiquidacion() {
		return fechaLiquidacion;
	}
	public void setFechaLiquidacion(FechaFraccionada fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}


}