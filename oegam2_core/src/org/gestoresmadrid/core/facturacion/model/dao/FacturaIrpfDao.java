package org.gestoresmadrid.core.facturacion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;

import hibernate.entities.facturacion.FacturaIrpf;

public interface FacturaIrpfDao extends GenericDao<FacturaIrpf>, Serializable {

	List<FacturaIrpf> listaIrpfs();
}
