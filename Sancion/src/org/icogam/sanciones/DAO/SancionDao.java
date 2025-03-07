package org.icogam.sanciones.DAO;

import java.util.List;

import org.icogam.sanciones.beans.Sancion;

import trafico.dao.GenericDao;

public interface SancionDao extends GenericDao<Sancion> {
	public Sancion getSancionId(Integer idSancion, String numColegiado);
	public List<Sancion> listado(Sancion sancion);
}
