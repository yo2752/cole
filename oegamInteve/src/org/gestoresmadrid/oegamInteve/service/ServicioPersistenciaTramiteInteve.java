package org.gestoresmadrid.oegamInteve.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;

public interface ServicioPersistenciaTramiteInteve extends Serializable {

	TramiteTraficoInteveVO getTramiteInteve(BigDecimal numExpediente, Boolean tramiteCompleto);

	void guardarOactualizarTramiteInteve(TramiteTraficoInteveVO tramiteInteveVO);

	void guardarEvolucionTramite(EvolucionTramiteTraficoVO evolucion);

	TramiteTraficoSolInteveVO getTramiteSolInteve(BigDecimal numExpediente, String matricula, String bastidor, String nive);

	void guardarOactualizarTramiteSolInteve(TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO);

	void actualizarSolicitudesNoFinalizadas(BigDecimal numExpediente, String estadoNuevo);

	void actualizarEstadoTramiteInteve(BigDecimal numExpediente, String estadoNuevo);

	void actualizarEstadoTramiteSolInteve(Long idTramiteSolInteve, String estadoNuevo);

	void actualizarCambioEstadoTramiteSolInteve(TramiteTraficoSolInteveVO tramiteTraficoSolInteveVO, String estadoAnteriorTramite, String estadoNuevo);

	TramiteTraficoSolInteveVO getTramiteSolIntevePorId(Long idTramiteSolInteve);

	void finalizarTramiteSolInteveWS(Long idTramiteInteve, String estado, String resultado);

	List<TramiteTraficoSolInteveVO> getListaTramitesSolInteve(BigDecimal numExpediente);

	void eliminarSolicitud(Long idTramiteInteve, Long idContrato, Long idUsuario);

	void guardar(TramiteTraficoInteveVO tramiteInteveVO);

	List<TramiteTraficoSolInteveVO> getDuplicados(Long idContrato, String matricula, String bastidor, String nive);
	
}
