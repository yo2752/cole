package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.IntervinienteRegistroVO;
import org.gestoresmadrid.core.registradores.model.vo.TramiteRegRbmVO;

public interface IntervinienteRegistroDao extends GenericDao<IntervinienteRegistroVO>, Serializable {
	public List<IntervinienteRegistroVO> getRepresentantes(long id);

	public long getIdIntervinientePorNifColegiadoTipo(IntervinienteRegistroVO interviniente);

	public IntervinienteRegistroVO getInterviniente(String id);

	public List<IntervinienteRegistroVO> getFinanciadores();

	public boolean hasTramites(String id);

	public IntervinienteRegistroVO getIntervinientePorNifColegiadoTipo(IntervinienteRegistroVO interviniente);

	public IntervinienteRegistroVO getIntervinienteTramiteRegRbmNif(TramiteRegRbmVO tramiteRegRbmVO, String nif);

	public List<IntervinienteRegistroVO> getIntervinientesTramiteRegRbmTipo(BigDecimal idTramiteRegRbm, String tipo);

	public List<IntervinienteRegistroVO> getFinanciadoresColegiado(String numColegiado);

	public List<TramiteRegRbmVO> getTramitesInterviniente(long idInterviniente);

	public List<IntervinienteRegistroVO> getIntervinientesPorDireccion(String nif, String numColegiado, Long idDireccion);
}
