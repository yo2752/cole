package trafico.modelo.interfaz;

import general.creditos.resultTransform.HistoricoCreditosResultTransform;

import java.util.List;

import utilidades.estructuras.FechaFraccionada;

public interface ModeloHistorioCreditosInt {

	/**
	 * M�todo para listar los cr�ditos que solicita Juan Pablo para imprimir
	 * @param fechaAlta 
	 * @return
	 */
	public List<HistoricoCreditosResultTransform> listadoGBJA(FechaFraccionada fechaAlta);

}