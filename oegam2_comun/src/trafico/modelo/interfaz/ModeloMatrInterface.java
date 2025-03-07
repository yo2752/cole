package trafico.modelo.interfaz;

import java.math.BigDecimal;
import java.text.ParseException;

import hibernate.entities.trafico.TramiteTrafMatr;
import trafico.dto.matriculacion.TramiteTrafMatrDto;
import utilidades.web.OegamExcepcion;

public interface ModeloMatrInterface {

	public TramiteTrafMatrDto actualizar(TramiteTrafMatrDto tramiteDto) throws OegamExcepcion;

	public TramiteTrafMatrDto recuperarDtoMatrPorNumExpediente(BigDecimal numExpediente);

//	public TramiteTraficoDto recuperarDtoTramiteTrafPorNumExp(BigDecimal numExpediente);

	/**
	 * Transforma un entity en un DTO
	 * Si el tramite tiene ivtm vendra relleno.
	 * @param tramite
	 * @return
	 */
	public TramiteTrafMatrDto convertTraTrafMatToDto(TramiteTrafMatr tramite);

	/**
	 * Transforma un DTO en un tramite entity
	 * Recordar que ivtm no esta en el entity de tramitetrafmatr, pero sí en el dto por si se necesita
	 * el entity de ivtm este tendrá que ser convertido en su modelo.
	 * @param tramiteTrafMatrDto
	 * @return
	 * @throws ParseException
	 */
	public TramiteTrafMatr convertDtoToTraTrafMat(TramiteTrafMatrDto tramiteTrafMatrDto) throws ParseException;
}