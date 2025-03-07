package org.gestoresmadrid.core.facturacionDistintivo.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.facturacionDistintivo.model.vo.FacturacionDistintivoVO;
import org.gestoresmadrid.core.facturacionDistintivo.model.vo.ResultFacturacionDstv;
import org.gestoresmadrid.core.model.dao.GenericDao;

import utilidades.estructuras.FechaFraccionada;

public interface FacturacionDistintivoDao extends Serializable, GenericDao<FacturacionDistintivoVO> {

	FacturacionDistintivoVO getFacturacionPorDocId(Long idDoc);

	List<ResultFacturacionDstv> getListaFacturacionExcel(FechaFraccionada fecha, Long idContrato, String docDistintivo, String tipoDistintivo,String estado);

	List<FacturacionDistintivoVO> getListaFacturacionExcelDetallado(FechaFraccionada fecha, Long idContrato, String docDistintivo, String tipoDistintivo);

	FacturacionDistintivoVO getFacturacionPorId(Long idFact);

	List<Long> getListaDocIds(String numColegiado, Date fecha);
}
