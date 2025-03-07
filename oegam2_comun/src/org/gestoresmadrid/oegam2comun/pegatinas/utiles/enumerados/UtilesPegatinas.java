package org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados;

import java.util.Arrays;

import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;

public class UtilesPegatinas {
	
	public static EstadoPegatinas[] getEstadoPegatinas() {
		return EstadoPegatinas.values();
	}
	
	public static TipoPegatinas[] getTipoPegatinas(){
		return TipoPegatinas.values();
	}
	
	public static EstadoPeticiones[] getEstadoPeticiones() {
		return EstadoPeticiones.values();
	}
	
	public static TipoPegatinas[] getTipoPegatinasPedirStock() {
		TipoPegatinas[] todos = TipoPegatinas.values();
		TipoPegatinas[] tipoPegatinas = Arrays.copyOfRange(todos, 1, todos.length);
		return tipoPegatinas;
	}
	
	public static String[] getMotivosInvalidos() {
		String[] salida= new String[2];
		salida[0] = "ROTURA DE LA PEGATINA";
		salida[1] = "IMPRESIÓN ERRONEA";
		
		String [] prueba = Arrays.copyOfRange(salida, 0, salida.length);
		return prueba;
	}
	
	public static AccionPegatinas[] getAccionPegatinas() {
		return AccionPegatinas.values();
	}
	
	public static JefaturasJPTEnum[] getJefaturas(){
		return JefaturasJPTEnum.values();
	}
	
}