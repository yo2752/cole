package com.matriculasWS.utiles;

import java.util.ArrayList;
import java.util.List;

import com.matriculasWS.beans.ResultadoWS;

public class UtilesConvertir {

	/**
	 * Convierte una lista de String en un array de String 
	 * @param lista String
	 * @return String[]
	 */
	public String[] convertirArraydeStringToList(List <String> lista){
		return (String[]) lista.toArray();
	}
	
	/**
	 * Convierte un array de objetos ResultadoWS en una lista de dichos objetos.
	 * @param resultadosArray
	 * @return
	 */
	public List <ResultadoWS> convertirArraydeResultadoWSToList (ResultadoWS[] resultadosArray){
		List <ResultadoWS> listaResultados = new ArrayList<ResultadoWS>();
		for (int i = 0; i < resultadosArray.length; i++) {
			listaResultados.add(resultadosArray[i]);
		}
		return listaResultados;
	}

	
}
