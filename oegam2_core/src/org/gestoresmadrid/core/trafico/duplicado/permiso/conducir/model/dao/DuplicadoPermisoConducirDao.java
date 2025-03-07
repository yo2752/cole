package org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.DuplicadoPermisoConducirVO;

public interface DuplicadoPermisoConducirDao extends Serializable, GenericDao<DuplicadoPermisoConducirVO> {

	DuplicadoPermisoConducirVO getDuplPermCondPorNumExpediente(BigDecimal numExpediente);

	BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	DuplicadoPermisoConducirVO getDuplicadoPermisoConducir(Long idDuplicadoPermisoCond);

	List<DuplicadoPermisoConducirVO> getListaDuplicadoPermisosCondDoiPorEstado(String doiTitular, String estado);

	List<DuplicadoPermisoConducirVO> consultarDuplicadosPermConducir();
}
