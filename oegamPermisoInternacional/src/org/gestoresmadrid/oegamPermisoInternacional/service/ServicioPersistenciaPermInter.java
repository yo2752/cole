package org.gestoresmadrid.oegamPermisoInternacional.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.PermisoInternacionalVO;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ResultadoPermInterBean;

public interface ServicioPersistenciaPermInter extends Serializable {

	ResultadoPermInterBean guardarPermiso(PermisoInternacionalVO permisoInternacionalVO, BigDecimal idUsuario);

	PermisoInternacionalVO getPermisoInternacional(Long idPermiso);

	List<PermisoInternacionalVO> consultarPermisosInternacionales();

	Boolean existeTramiteDoiPendienteDgt(String doiTitular, String estado);

	void actualizar(PermisoInternacionalVO permisoInternacionalVO);

	void actualizarEstadosYRespuesta(Long idPermisoIntern, String estadoNuevo, String estadoImpNuevo, String respuesta);

	void actualizarEstado(Long idPermisoIntern, String estadoNuevo);

	void eliminarPermisoIntern(Long idPermiso);

	void actualizarEstadoImpresion(Long idPermisoIntern, String estadoNuevo);

	void actualizarSubidaDoc(Long idPermiso, String numReferencia);

	PermisoInternacionalVO getPermisoInternacionPorExpediente(BigDecimal numExpediente);
}