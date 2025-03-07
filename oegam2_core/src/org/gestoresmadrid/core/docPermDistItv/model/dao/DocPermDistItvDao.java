package org.gestoresmadrid.core.docPermDistItv.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.beans.FacturacionDocBean;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.values.CantidadDistintivoValue;

public interface DocPermDistItvDao extends GenericDao<DocPermDistItvVO>, Serializable {

	DocPermDistItvVO getPermDistItvPorId(Long idDocPermDstvEitv, Boolean completo);

	String getLastDocId(Date fecha);

	DocPermDistItvVO getDocPermPorDoc(String docIdPerm, Boolean completo);

	List<DocPermDistItvVO> getPermDistItvPorIds(List<Long> listaIdDocPermDstvEitv, Boolean completo);

	List<DocPermDistItvVO> getImpresionesPorFecha(Date fecha);

	List<Long> getIdsImpresionesPorFecha(Date fecha);

	List<DocPermDistItvVO> findDocPermByEstado(BigDecimal estado);

	List<DocPermDistItvVO> findDocByJefaturaDistintivoFechaImpr(String jefaturaProvincial, Long tipoDocumento, Date fecDesde, Date fecHasta);

	//HashMap<String, HashMap<String, Long>> obtenerFactuacionDistintivo(Long contrato, String tipoDistintivo, Date fecDesde, Date fecHasta,
		//	HashMap<String, HashMap<String, Long>> lineasFacturacionMaterial);

	//HashMap<String, HashMap<String, Long>> obtenerFactuacionPermiso(Long contrato, Date fecDesde, Date fecHasta, HashMap<String, HashMap<String, Long>> lineasFacturacionMaterial);

	//HashMap<String, HashMap<String, Long>> obtenerFactuacionFichaTecnica(Long contrato, Date fecDesde, Date fecHasta, HashMap<String, HashMap<String, Long>> lineasFacturacionMaterial);

	List<DocPermDistItvVO> getListaDocs(String[] sDocId, Boolean tramiteCompleto);

	List<FacturacionDocBean> getListaDocumentosFacturacionStcok(Long idContrato, String tipoDoc, String tipoDistintivo, Date fechaInicio, Date fechaFin);

	List<Long> getContratosConImpresionesPorDia(String tipoDocumento, String tipoDistintivo, Date fechaInicio, Date fechaFin);

	List<CantidadDistintivoValue> getListaFacturacionPorContratoDstvTrafico(Long idContrato, String tipoDistintivo, Date fechaInicio, Date fechaFin);

	List<CantidadDistintivoValue> getListaFacturacionPorContratoDstvDuplicado(Long idContrato, String tipoDistintivo, Date fechaInicio, Date fechaFin);

	List<String> getListaJefaturasImpresionDia(Date fecha);

	List<String> obtenerIdDocPermDistItvMax(Date fecha);
}
