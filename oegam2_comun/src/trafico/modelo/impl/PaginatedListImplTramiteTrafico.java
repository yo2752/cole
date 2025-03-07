package trafico.modelo.impl;

import hibernate.entities.tasas.Tasa;
import hibernate.entities.trafico.IntervinienteTrafico;
import hibernate.entities.trafico.TramiteTrafSolInfo;
import hibernate.entities.trafico.Vehiculo;

import java.util.ArrayList;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.transform.ResultTransformer;

import trafico.beans.ConsultaTramiteTraficoFilaTablaResultadoBean;
import trafico.dao.implHibernate.AliasQueryBean;
import trafico.dao.implHibernate.GenericDaoImplHibernate;
import trafico.utiles.enumerados.TipoTramiteTrafico;

/**
 * 
 * @author Globaltms
 *
 */
public class PaginatedListImplTramiteTrafico extends PaginatedListImpl implements PaginatedList {

	public PaginatedListImplTramiteTrafico() {
	}

	public PaginatedListImplTramiteTrafico(boolean consultaInicial, Object objeto, int objectsPerPage, int currentPage,
			List<Criterion> criterion, String dirorden, String campoOrden, List<AliasQueryBean> listaAlias,
			ProjectionList listaProyecciones, ResultTransformer transformadorResultado) {
		// super(consultaInicial, objeto, objectsPerPage, currentPage, criterion,
		// dirorden, campoOrden, listaAlias, listaProyecciones, transformadorResultado);
		genericDao = new GenericDaoImplHibernate(objeto);
		this.objeto = objeto;
		this.criterion = criterion;
		this.objectsPerPage = objectsPerPage;
		this.currentPage = currentPage;
		this.campoOrden = campoOrden;
		this.dirorden = dirorden;
		this.listaAlias = listaAlias;
		this.listaProyecciones = listaProyecciones;
		this.transformadorResultado = transformadorResultado;
		if (consultaInicial) {
			obtenerNumElementosTotales();
			obtenerLista();
		}
	}

	private void obtenerNumElementosTotales() {
		List<AliasQueryBean> listaAliasCount = null;
		if (criterion != null && !criterion.isEmpty()) {
			Boolean aliasVehiculo = false;
			Boolean aliasTasa = false;
			Boolean aliasInterv = false;
			Boolean aliasTramiteSol = false;
			for (Criterion cri : criterion) {
				if (cri.toString().contains("vehiculo") && !aliasVehiculo) {
					aliasVehiculo = true;
				} else if (cri.toString().contains("tasa") && !aliasTasa) {
					aliasTasa = true;
				} else if (cri.toString().contains("intervinienteTraficos") && !aliasInterv) {
					aliasInterv = true;
					if (listaAliasCount == null) {
						listaAliasCount = new ArrayList<AliasQueryBean>();
					}
					listaAliasCount.add(new AliasQueryBean(IntervinienteTrafico.class, "intervinienteTraficos",
							"intervinienteTraficos", CriteriaSpecification.LEFT_JOIN));
				} else if (cri.toString().equalsIgnoreCase("tipoTramite=T4")) {
					aliasTramiteSol = true;
					if (listaAliasCount == null) {
						listaAliasCount = new ArrayList<AliasQueryBean>();
					}
					listaAliasCount.add(new AliasQueryBean(TramiteTrafSolInfo.class, "tramiteTrafSolInfo",
							"tramiteTrafSolInfo", CriteriaSpecification.LEFT_JOIN));
				}
			}

			if (aliasVehiculo) {
				if (listaAliasCount == null) {
					listaAliasCount = new ArrayList<AliasQueryBean>();
				}
				if (aliasTramiteSol) {
					listaAliasCount.add(new AliasQueryBean(Vehiculo.class, "tramiteTrafSolInfo.vehiculo", "vehiculo",
							CriteriaSpecification.LEFT_JOIN));
				} else {
					listaAliasCount.add(new AliasQueryBean(Vehiculo.class, "vehiculo", "vehiculo",
							CriteriaSpecification.LEFT_JOIN));
				}
			}

			if (aliasTasa) {
				if (listaAliasCount == null) {
					listaAliasCount = new ArrayList<AliasQueryBean>();
				}
				if (aliasTramiteSol) {
					listaAliasCount.add(new AliasQueryBean(Tasa.class, "tramiteTrafSolInfo.tasa", "tasa",
							CriteriaSpecification.LEFT_JOIN));
				} else {
					listaAliasCount
							.add(new AliasQueryBean(Tasa.class, "tasa", "tasa", CriteriaSpecification.LEFT_JOIN));
				}
			}
		}

		numElementosTotales = genericDao.numeroElementos(criterion, listaAliasCount);
	}

	public List getList() {
		if (lista == null) {
			if (numElementosTotales < 0) {
				obtenerNumElementosTotales();
			}
			obtenerLista();
		}
		return lista;
	}

	public int getFullListSize() {
		if (numElementosTotales < 0) {
			obtenerNumElementosTotales();
		}
		return numElementosTotales;
	}

	protected void obtenerLista() {
		if (lista == null) {
			super.obtenerLista();
			if (!contieneRestriccionSolicitud(criterion)) {
				obtenerDatosTramiteSolicitud();
			}
		}
	}

	private void obtenerDatosTramiteSolicitud() {
		List<ConsultaTramiteTraficoFilaTablaResultadoBean> l = (List<ConsultaTramiteTraficoFilaTablaResultadoBean>) lista;
		new GeneradorDatosSolicitud().generarDatosSolicitud(l);
	}

	private boolean contieneRestriccionSolicitud(List<Criterion> criterion) {
		SimpleExpression se = Restrictions.eq("tipoTramite", TipoTramiteTrafico.Solicitud.getValorEnum());
		for (Criterion c : criterion) {
			if (c.toString().equals(se.toString())) {
				return true;
			}
		}
		return false;
	}

}