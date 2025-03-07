package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.impr.model.dao.DocPermDistvItvDao;
import org.gestoresmadrid.core.impr.model.dao.EvoDocPrmDstvFichaDao;
import org.gestoresmadrid.core.impr.model.vo.EvoDocPrmDstvFichaVO;
import org.gestoresmadrid.oegamComun.impr.service.ServicioPersistenciaDocPrmDstvFicha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaDocPrmDstvFichaImpl implements ServicioPersistenciaDocPrmDstvFicha {

	private static final long serialVersionUID = -8931840385359166124L;

	private static final Logger log = LoggerFactory.getLogger(ServicioPersistenciaDocPrmDstvFichaImpl.class);

	@Autowired
	DocPermDistvItvDao docPermDistItvDao;

	@Autowired
	EvoDocPrmDstvFichaDao evoDocPrmDstvFichaDao;

	@Override
	@Transactional
	public Long guardar(DocPermDistItvVO docImpr) {
		return (Long) docPermDistItvDao.guardar(docImpr);
	}

	@Override
	@Transactional
	public void guardarDocId(DocPermDistItvVO docImpr, Long idUsuario) {
		docPermDistItvDao.actualizar(docImpr);
		evoDocPrmDstvFichaDao.guardar(rellenarEvolucionDoc(docImpr.getDocIdPerm(), docImpr.getTipo(), idUsuario, EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), EstadoPermisoDistintivoItv.Iniciado
				.getValorEnum(), OperacionPrmDstvFicha.CREACION.getValorEnum()));
	}

	private EvoDocPrmDstvFichaVO rellenarEvolucionDoc(String docId, String tipo, Long idUsuario, String estadoNuevo, String estadoAnt, String operacion) {
		EvoDocPrmDstvFichaVO evoDocPrmDstvFichaVO = new EvoDocPrmDstvFichaVO();
		if (estadoAnt != null && !estadoAnt.isEmpty()) {
			evoDocPrmDstvFichaVO.setEstadoAnterior(new BigDecimal(estadoAnt));
		}
		if (estadoNuevo != null && !estadoNuevo.isEmpty()) {
			evoDocPrmDstvFichaVO.setEstadoNuevo(new BigDecimal(estadoNuevo));
		}
		evoDocPrmDstvFichaVO.setDocID(docId);
		evoDocPrmDstvFichaVO.setFechaCambio(new Date());
		evoDocPrmDstvFichaVO.setIdUsuario(new BigDecimal(idUsuario));
		evoDocPrmDstvFichaVO.setOperacion(operacion);
		evoDocPrmDstvFichaVO.setTipoDocumento(tipo);
		return evoDocPrmDstvFichaVO;
	}

}