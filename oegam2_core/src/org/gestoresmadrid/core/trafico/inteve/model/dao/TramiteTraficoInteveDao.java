package org.gestoresmadrid.core.trafico.inteve.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;

import utilidades.estructuras.FechaFraccionada;

public interface TramiteTraficoInteveDao  extends GenericDao<TramiteTraficoInteveVO>, Serializable {

	TramiteTraficoInteveVO getTramiteInteve(BigDecimal numExpediente, Boolean tramiteCompleto);
	
	public int numeroElementos(String bastidor, String codigoTasa, BigDecimal estado, FechaFraccionada fechaAlta,
			FechaFraccionada fechaPresentacion, Long idContrato, String matricula, String nive,
			BigDecimal numExpediente, String tipoInforme, String tipoTramite);

	List<TramiteTraficoInteveVO> buscarPorCriteria(int firstResult, int maxResults, String dir, String sort, String bastidor,
			String codigoTasa, BigDecimal estado, FechaFraccionada fechaAlta, FechaFraccionada fechaPresentacion,
			Long idContrato, String matricula, String nive, BigDecimal numExpediente, String tipoInforme,
			String tipoTramite);


}
