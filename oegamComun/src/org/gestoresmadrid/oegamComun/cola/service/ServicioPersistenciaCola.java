package org.gestoresmadrid.oegamComun.cola.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.cola.model.vo.ColaVO;

public interface ServicioPersistenciaCola extends Serializable {

	Boolean existeColaTramiteProceso(BigDecimal idTramite, String proceso);

	String getHilo(String nodo, String proceso);

	void guardarCola(ColaVO cola);

	Serializable guardarColaReturnId(ColaVO cola);

	Long getNumColasProceso(String proceso, String nodo, Long idContrato);

	void actualizar(ColaVO colaVO);

	ColaVO getColaSolicitudProceso(String proceso, String cola, String nodo);

	void eliminarCola(ColaVO colaVO);

	ColaVO getColaPorIdEnvio(Long idEnvio);

	ColaVO getColaIdTramite(BigDecimal idTramite, String proceso);

	List<ColaVO> getColasActivasProceso(String proceso, String nodo);

	ColaVO getColaPrincipal(String proceso, String cola, String nodo);

	String obtenerMaxCola();
}
