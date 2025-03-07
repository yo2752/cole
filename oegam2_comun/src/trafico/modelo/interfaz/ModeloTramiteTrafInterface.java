package trafico.modelo.interfaz;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;

import hibernate.entities.trafico.TramiteTrafico;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.dto.TramiteTraficoDto;
import utilidades.web.OegamExcepcion;

public interface ModeloTramiteTrafInterface {

	public TramiteTraficoDto recuperarDtoPorNumExp(BigDecimal numExpediente);

	/**
	 * Transforma un entity en un DTO
	 * Si el tramite tiene IVTM vendra relleno en el DTO de matr
	 * @param tramite
	 * @return
	 */
	public TramiteTraficoDto convertTramiteTrafToDto(TramiteTrafico tramite);

	/**
	 * Transforma un DTO en un tramite entity
	 * @param tramiteDto
	 * @return
	 * @throws ParseException
	 */
	public TramiteTrafico convertDtoToTramiteTraf(TramiteTraficoDto tramiteDto) throws ParseException;

	public TramiteTraficoDto actualizar(TramiteTraficoDto tramiteDto) throws OegamExcepcion;

	public boolean cambiarEstado(BigDecimal numExpediente, EstadoTramiteTrafico estadoNuevo);

	public boolean cambiarEstado(BigDecimal[] numExpedientes, EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario);

	/**
	 * Método que te devuelve un si un tramite de bean de pantalla ha sido modificado.
	 * El resultado es un hashMap, de objeto y si esta o no modificado.
	 * Ahora mismo solo esta implementado el titular y el vehiculo.
	 * @return
	 */
	public HashMap<String , Boolean> tramiteModificado(TramiteTraficoMatriculacionBean tramiteBeanPantalla);

	public String validarRelacionMatriculas(String[] codSeleccionados) throws Exception, OegamExcepcion;

}