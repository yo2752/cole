package trafico.modelo.ivtm;
//TODO MPC. Cambio IVTM. Clase nueva.
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmConsultaMatriculacionDto;

import utilidades.web.CrearSolicitudExcepcion;
import utilidades.web.OegamExcepcion;

public interface IVTMConsModeloMatriculacionInterface extends IVTMModeloInterface {

	/**
	 * Guarda los datos asociados a la consulta
	 * @param eeffConsulta
	 */
	public BigDecimal guardarDatosConsulta(IvtmConsultaMatriculacionDto ivtmConsultaDto) throws Exception;

	/**
	 * 
	 * @param eeffConsulta
	 * @return
	 */
	public boolean solicitarConsulta(IvtmConsultaMatriculacionDto ivtmConsultaDto) throws CrearSolicitudExcepcion;

	/**
	 * 
	 * @param eeff
	 * @return
	 */
	public List<String> validarDatos(IvtmConsultaMatriculacionDto ivtmConsultaDto) throws Exception;

	/**
	 * 
	 * @param ivtmConsulta
	 * @return
	 */
	public es.map.scsp.esquemas.v2.peticion.consultaivtm.Peticion damePeticionConsulta(IvtmConsultaMatriculacionDto ivtmConsultaMatriculacionDto);

	public IvtmConsultaMatriculacionDto buscarIvtmConsultaIdPeticion(BigDecimal idPeticion);

	public IvtmConsultaMatriculacionDto actualizar(IvtmConsultaMatriculacionDto ivtmDto) throws OegamExcepcion;

}