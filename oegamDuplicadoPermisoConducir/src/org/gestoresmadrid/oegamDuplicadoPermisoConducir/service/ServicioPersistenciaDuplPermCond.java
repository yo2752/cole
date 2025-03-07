package org.gestoresmadrid.oegamDuplicadoPermisoConducir.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.DuplicadoPermisoConducirVO;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ResultadoDuplPermCondBean;

public interface ServicioPersistenciaDuplPermCond extends Serializable {

	DuplicadoPermisoConducirVO getDuplPermCondPorNumExpediente(BigDecimal numExpediente);

	ResultadoDuplPermCondBean guardarPermisoCond(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO, BigDecimal idUsuario);

	DuplicadoPermisoConducirVO getDuplicadoPermisoConducir(Long idDuplicadoPermisoCond);

	void actualizarEstado(Long idDuplicadoPermisoCond, String estadoNuevo);

	Boolean existeTramiteDoiPendienteDgt(String doiTitular, String estado);

	void actualizarEstadoImpresion(Long idDuplicadoPermisoCond, String estadoNuevo);

	void eliminarDuplPermConducir(Long idDuplicadoPermisoCond);

	void actualizar(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO);

	List<DuplicadoPermisoConducirVO> consultarDuplicadosPermConducir();

	void actualizarEstadosYRespuesta(Long idDuplicadoPermCond, String estadoNuevo, String estadoImpNuevo, String respuesta);
}
