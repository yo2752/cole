package org.icogam.legalizacion.DAO;

import java.util.List;

import org.icogam.legalizacion.beans.LegalizacionCita;

import trafico.dao.GenericDao;

public interface LegalizacionDao extends GenericDao<LegalizacionCita>{

	public LegalizacionCita getLegalizacionId(Integer idPeticion);

	public boolean esPosiblePeticion(String numColegiado);

	public List<LegalizacionCita> listadoDiario(LegalizacionCita legBean);

	public List<LegalizacionCita> listadoDiarioColegiado(LegalizacionCita legBean);

//	public PaginatedList buscarporCriteriaConsultaPag(LegalizacionBean legalizacionCita, int resPag, int numPag, String dir, String campoOrden);
}