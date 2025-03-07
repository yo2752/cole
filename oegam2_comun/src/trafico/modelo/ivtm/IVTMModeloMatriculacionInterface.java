package trafico.modelo.ivtm;
//TODO MPC. Cambio IVTM. Clase nueva.
import hibernate.entities.trafico.IvtmMatriculacion;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmDatosEntradaMatriculasWS;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmDatosSalidaMatriculasWS;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;

import escrituras.beans.ResultBean;
import trafico.beans.jaxb.matriculacion.DatosIvtm;
import trafico.beans.jaxb.matw.FORMATOGA;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION;
import trafico.dto.TramiteTraficoDto;
import trafico.utiles.enumerados.EstadoIVTM;
import trafico.utiles.enumerados.TipoVehiculoIvtm;
import utilidades.web.OegamExcepcion;

public interface IVTMModeloMatriculacionInterface extends IVTMModeloInterface{

	/**
	 * Guarda un Ivtm
	 * @param ivtmDto
	 * @return
	 * @throws OegamExcepcion
	 */
	public IvtmMatriculacionDto guardar(IvtmMatriculacionDto ivtmDto) throws OegamExcepcion;

	public IvtmMatriculacionDto recuperarPorNumExp(BigDecimal numExpediente);

	public IvtmMatriculacionDto actualizarEstado(IvtmMatriculacionDto ivtmDto);

	public es.map.scsp.esquemas.v2.peticion.altaivtm.Peticion damePeticionAlta(TramiteTraficoDto tramiteTrafico);

	public es.map.scsp.esquemas.v2.peticion.modificacionivtm.Peticion damePeticionModificacion(
			TramiteTraficoDto tramiteTrafico);

	/**
	 * 
	 * @param numExpediente
	 * @return
	 */
	public IvtmMatriculacionDto buscarIvtmNumExp(BigDecimal numExpediente);
	
	/**
	 * Transforma un Entity en un tramite DTO
	 * @param ivtm
	 * @return
	 */
	public IvtmMatriculacionDto convertEntityToDto(IvtmMatriculacion ivtm);

	/**
	 * Transforma un DTO en un tramite entity
	 * @param ivtm
	 * @return
	 * @throws ParseException
	 */
	public IvtmMatriculacion convertDtoToEntity(IvtmMatriculacionDto ivtm) throws ParseException;

	public IvtmMatriculacionDto actualizar(IvtmMatriculacionDto ivtmDto) throws OegamExcepcion;

	public TipoVehiculoIvtm obtenerTipoVehiculo(String tipo);

	/**
	 * @param numExpediente
	 * @param ivtmMatriculacionDto
	 * @return
	 */
	boolean solicitarIVTM(BigDecimal numExpediente, IvtmMatriculacionDto ivtmMatriculacionDto);

	/**
	 * @param ga
	 * @param numExp
	 * @return
	 */
	public ResultBean guardarDatosImportados(MATRICULACION matriculacionGA, BigDecimal numExp, boolean tienePermisoIVTM);

	/**
	 * @param traficoTramiteBean
	 * @param ivtmMatriculacionDto
	 * @return
	 */
	public List<String> validarAlta(TramiteTraficoDto traficoTramiteBean, IvtmMatriculacionDto ivtmMatriculacionDto);

	/**
	 * @param traficoTramitenBean
	 * @param ivtmMatriculacionDto
	 * @return
	 */
	public List<String> validarModificacion(TramiteTraficoDto traficoTramitenBean, IvtmMatriculacionDto ivtmMatriculacionDto);

	/**
	 * @param traficoTramiteBean
	 * @param ivtmMatriculacionDto
	 * @return
	 */
	public List<String> validarPago(TramiteTraficoDto tramiteDTO, IvtmMatriculacionDto ivtmMatriculacionDto);

	/**
	 * Método para validar los datos IVTM, para Mate, MatW, y MatAntigua
	 * @param ga
	 * @return
	 */
	public ResultBean validarFORMATOIVTMGA(FORMATOGA ga, boolean tienePermisoIVTM);
	/**
	 * 
	 * @param numExpediente
	 * @return
	 */
	public List<String> validarMatriculacion(BigDecimal numExpediente);

	/**
	 * Método para validar los datos IVTM, para Matw
	 * @param ga
	 * @return
	 */

	public IvtmDatosSalidaMatriculasWS recuperarMatriculasWS (IvtmDatosEntradaMatriculasWS datosEntrada);

	public void duplicar(BigDecimal numExpedientePrevio, BigDecimal numExpedienteNuevo);

	public DatosIvtm obtenerDatosParaImportacionMatw(BigDecimal numExpediente, boolean tienePermisoIVTM);

	public DatosIvtm obtenerDatosParaImportacion(BigDecimal numExpediente, boolean tienePermisoIVTM);

	public boolean estaEnEstadoValidoParaAutoliquidar(EstadoIVTM estado);

	/**
	 * Devuelve el IBAN del titular de un trámite de matriculación. Este dato se encuentra en la persona que se corresponde con el interviniente titular.
	 * @return
	 */
	public String getIbanTitular(BigDecimal numExpediente);

	/**
	 * Devuelve el IBAN de la persona indicada.
	 * @return
	 */
	public String getIbanTitular(String nifTitular, String numColegiado);

}