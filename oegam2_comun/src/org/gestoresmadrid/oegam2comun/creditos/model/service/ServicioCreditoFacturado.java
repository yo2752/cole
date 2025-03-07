package org.gestoresmadrid.oegam2comun.creditos.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;

/**
 * Servicio destinado a CreditoFacturadoVO
 */
public interface ServicioCreditoFacturado extends Serializable{

	/**
	 * Guarda en bbdd el numero de creditos gastados en una operacion, indicando los tramites involucrados y el concepto
	 * @param numCreditos Integer, numero de creditos que se gastan, debe ser mayor que 0
	 * @param tipoCredito String, tipo de crédito que se gasta
	 * @param concepto ConceptoCreditoFacturado, Descripcion de la operacion realizada para gastar creditos
	 * @param idContrato Long, identificador del contrato que consume creditos
	 * @param tipoTramite String, tipo de los tramites involucrados en la operacion
	 * @param tramites String..., Identificador de los tramites involucrados en la operacion
	 * @return
	 */
	boolean anotarGasto(Integer numCreditos, ConceptoCreditoFacturado concepto, Long idContrato, String tipoTramite, String... tramites);

	/**
	 * Guarda un nuevo CreditoFacturadoVO y devuelve su Id, si ha ido bien
	 * @param creditoFacturadoVO
	 * @return
	 */
	Long guardar(CreditoFacturadoVO creditoFacturadoVO);

	/**
	 * Actualiza la entidad CreditoFacturadoVO pasada
	 * @param creditoFacturadoVO
	 * @return
	 */
	CreditoFacturadoVO actualizar(CreditoFacturadoVO creditoFacturadoVO);

	/**
	 * Borra la entidad CreditoFacturadoVO pasada
	 * @param creditoFacturadoVO
	 * @return
	 */
	boolean borrar(CreditoFacturadoVO creditoFacturadoVO);

	/**
	 * Recupera el CreditoFacturadoVO con identificador pasado
	 * @param id
	 * @param initialized
	 * @return
	 */
	CreditoFacturadoVO getCreditoFacturadoVO(Long id, String... initialized);

	/**
	 * Busca los CreditoFacturadoVOs que cumplen los criterios pasados en el filtro
	 * @param filter
	 * @return
	 */
	List<CreditoFacturadoVO> buscar(Object filter);

	void eliminarGasto(SolicitudPlacaVO solicitud);

	/**
	 * Devuelve el concepto credito facturado impresión
	 * @param tipoTramite String, tipo de trámite
	 * @return
	 */
	ConceptoCreditoFacturado getConceptoCreditoImpresion(TipoTramiteTrafico tipoTramite);

	/**
	 * Devuelve el movimiento de creditos que se hayan anotado para ese tramite
	 * 
	 * @param idTramite
	 * @return
	 */
	List<CreditoFacturadoVO> movimientoCreditosTramite(String idTramite);

}
