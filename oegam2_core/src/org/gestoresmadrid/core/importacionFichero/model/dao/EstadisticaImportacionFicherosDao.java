package org.gestoresmadrid.core.importacionFichero.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.importacionFichero.model.vo.EstadisticaImportacionFicherosVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EstadisticaImportacionFicherosDao extends Serializable, GenericDao<EstadisticaImportacionFicherosVO> {

	List<EstadisticaImportacionFicherosVO> getListaImportacionExcel(Date fechaInicio, Date fechaFin, String tipo, Long idContrato);

	List<EstadisticaImportacionFicherosVO> getListaImportacion(Date fechaInicio, Date fechaFin, String origen);

	List<EstadisticaImportacionFicherosVO> listaEstadisticasEjecutandose(Long idContrato, String tipo);

	Integer numeroPeticionesEjecutandose(String tipo);

	EstadisticaImportacionFicherosVO getEstadisticaImportacion(Long idImportacionFich);
}
