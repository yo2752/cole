package es.globaltms.gestorDocumentos.util;

import java.math.BigDecimal;

import utilidades.estructuras.Fecha;

public class Utilidades {
	
	public static Fecha transformExpedienteFecha(BigDecimal numExpediente){
		Fecha fecha=null;
		fecha = transformExpedienteFecha(numExpediente.toString());
		return fecha;
	}
	
	public static Fecha transformExpedienteFecha(String numExpediente){
		Fecha fecha=null;
		String dia, mes, anio;
		 
		while(numExpediente.length()<15){
			numExpediente="0"+numExpediente;
		}
			dia = numExpediente.substring(4,6);
			mes = numExpediente.toString().substring(6, 8);
			anio = "20"+numExpediente.toString().substring(8, 10);
			StringBuffer fechaBarras= new StringBuffer();
			fechaBarras.append(dia);
			fechaBarras.append("/");
			fechaBarras.append(mes);
			fechaBarras.append("/");
			fechaBarras.append(anio);
			
			fecha = new Fecha(fechaBarras.toString());
		return fecha;
	}

	public static String transformarExpedienteNumColegiado(BigDecimal numExpediente) {
		if(numExpediente != null){
			return transformarExpedienteNumColegiado(numExpediente.toString());
		}
		return null;
	}
	
	public static String transformarExpedienteNumColegiado(String numExpediente) {
		String numColegiado = null;
		if(numExpediente != null && !numExpediente.isEmpty()){
			numColegiado = numExpediente.substring(0, 4);
		}
		return numColegiado;
	}
}
