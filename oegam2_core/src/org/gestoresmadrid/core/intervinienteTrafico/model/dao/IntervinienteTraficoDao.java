package org.gestoresmadrid.core.intervinienteTrafico.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface IntervinienteTraficoDao extends GenericDao<IntervinienteTraficoVO>, Serializable {

	IntervinienteTraficoVO getIntervinienteTrafico(BigDecimal numExpediente, String tipoInterviniente, String nif, String numColegiado);

	List<IntervinienteTraficoVO> getExpedientesDireccion(String nif, String numColegiado, Long idDireccion);

	IntervinienteTraficoVO getIntervinientePorNifColegiadoExpedienteYTipo(String nif, String numColegiado, BigDecimal numExpediente, String tipoInterviniente);

	Integer comprobarcomprobarTramitesEmpresaST(Long idContrato, Date fecha, String nifInterviniente,
			String codigoPostal, String idMunicipio, String idProvincia, String tipoTramiteSolicitud);
	
	List<IntervinienteTraficoVO> getListaTramitesPorIntervieniente(String nif, BigDecimal idContrato,
			String tipoInterviniente, Date fecha, String tipoTramite, String codigoPostal, String idMunicipio, String idProvincia, String tipoTramiteSolicitud);

	List<IntervinienteTraficoVO> getListaIntervinientesTramite(BigDecimal numExpediente);
}
