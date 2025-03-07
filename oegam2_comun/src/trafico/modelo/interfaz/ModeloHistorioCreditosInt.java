package trafico.modelo.interfaz;

import general.creditos.resultTransform.HistoricoCreditosResultTransform;

import java.util.List;

import utilidades.estructuras.FechaFraccionada;

public interface ModeloHistorioCreditosInt {

	/**
	 * Método para listar los créditos que solicita Juan Pablo para imprimir
	 * @param fechaAlta 
	 * @return
	 */
	public List<HistoricoCreditosResultTransform> listadoGBJA(FechaFraccionada fechaAlta);

}