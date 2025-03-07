package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.TramiteRegistroVO;

public interface TramiteRegistroDao extends GenericDao<TramiteRegistroVO>, Serializable {

	TramiteRegistroVO getTramite(BigDecimal idTramiteRegistro);

	TramiteRegistroVO getTramiteIdCorpme(String idTramiteCorpme);

	List<TramiteRegistroVO> getTramiteIdCorpmeConCodigoRegistro(String idTramiteCorpme, String codigoRegistro);

	BigDecimal generarIdTramiteRegistro(String numColegiado, String tipoTramiteRegistro) throws Exception;

	String generarIdTramiteCorpme(String numColegiado, String cif) throws Exception;

	TramiteRegistroVO cambiarEstado(BigDecimal idTramiteRegistro, BigDecimal estadoNuevo);

	TramiteRegistroVO getTramiteJustificante(String id);
}
