package org.gestoresmadrid.core.trafico.permiso.internacional.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.PermisoInternacionalVO;

public interface PermisoInternacionalDao extends Serializable, GenericDao<PermisoInternacionalVO> {

	PermisoInternacionalVO getPermisoInternacional(Long idPermiso);

	BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	List<PermisoInternacionalVO> consultarPermisosInternacionales();

	List<PermisoInternacionalVO> getListaPermisosDoiPorEstado(String doiTitular, String estado);

	PermisoInternacionalVO getPermisoInternacionalPorExpediente(BigDecimal numExpediente);
}
