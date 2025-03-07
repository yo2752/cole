package trafico.servicio.interfaz;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;

import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;

public interface ServicioJustificanteProInt extends Serializable {

	// INICIO MANTIS 0011927: impresi�n de justificantes profesionales 
	/**
	 * Imprimir los justificantes profesionales de varios espedientes.
	 * - Si se recibe una lista con un expediente:
	 * 		- Si el expediente tiene un solo justificante, se devolver� el pdf asociado.
	 * 		- Si el expediente tiene varios justificantes, se devolver� un zip con los pdf asociados.
	 * - Si se recibe una lista con varios expedientes:
	 * 		- Se devolver� un zip compuesto por pdf's o zip's a su vez, dependiendo de si cada expediente tiene uno o varios justificantes.
	 * 
	 * @param listaNumExpedientes lista de n�meros de expediente.
	 * @return ByteArrayInputStream ByteArrayInputStream con los justificantes.
	 */
	public ByteArrayInputStreamBean imprimirJustificantes(String[] listaNumExpedientes);
	
	/**
	 * Obtiene los expedientes que no tienen ning�n justificante en el estado recibido.
	 * 
	 * @param listaNumExpedientes lista de n�mero de expediente.
	 * @param estadoJustificantes estado de los justificantes.
	 * 
	 * @return List<String> - expedientes que no tienen ning�n justificante en el estado recibido.
	 */
	public List<String> obtenerExpedientesSinJustificantesEnEstado(String[] listaNumExpedientes, EstadoJustificante estadoJustificantes);

	// FIN MANTIS 0011927

	public long getObtenerIdJustifcanteInternoPorIdJustificante(String idJustificante);

	public long getObtenerIdJustificanteInternoPorNumExp(String numExpediente, String fechaInicio);

	String generarNombre(JustificanteProfVO justificante);

}
