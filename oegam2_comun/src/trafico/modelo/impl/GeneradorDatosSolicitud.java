package trafico.modelo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import hibernate.entities.tasas.Tasa;
import hibernate.entities.trafico.TramiteTrafSolInfo;
import hibernate.entities.trafico.Vehiculo;
import trafico.beans.ConsultaTramiteTraficoFilaTablaResultadoBean;
import trafico.beans.ConsultaTramiteTraficoSolicitudFilaTablaResultadoBean;
import trafico.dao.implHibernate.AliasQueryBean;
import trafico.dao.implHibernate.GenericDaoImplHibernate;
import trafico.utiles.enumerados.TipoTramiteTrafico;

public class GeneradorDatosSolicitud {

	public void generarDatosSolicitud(List<ConsultaTramiteTraficoFilaTablaResultadoBean> listaPeticiones) {
		HashMap<Long, ConsultaTramiteTraficoFilaTablaResultadoBean> tramitesSolicitud = obtenerTramitesSolicitud(listaPeticiones);
		if (!tramitesSolicitud.isEmpty()) {
//			SELECT t.num_Expediente, v.bastidor, v.matricula, ta.codigo_tasa, ta.tipo_tasa from tramite_traf_solinfo t
//			left join vehiculo v on t.id_vehiculo= v.id_vehiculo
//			left join tasa t on t.codigo_tasa = ta.codigo_tasa
//			where t.num_expediente in (EXPEDIENTES DE TRAMITESSOLICITUD)

			List<AliasQueryBean> listaAliasSolicitud = crearAliasSolicitud();
			ProjectionList listaProyeccionesSolicitud = crearProyeccionesSolicitudes();

			List<Criterion> criterionSolicitud = new ArrayList<>();
			Long[] numerosExpediente = new Long[tramitesSolicitud.size()];
			Iterator<Long> keys = tramitesSolicitud.keySet().iterator();

			for (int i=0; keys.hasNext(); i++) {
				numerosExpediente[i]=tramitesSolicitud.get(keys.next()).getNumExpediente();
			}
			criterionSolicitud.add(Restrictions.in("id.numExpediente", numerosExpediente));

			List<ConsultaTramiteTraficoSolicitudFilaTablaResultadoBean> listaSolicitudes = new GenericDaoImplHibernate(new TramiteTrafSolInfo()).buscarPorCriteria(-1,  -1, criterionSolicitud, null, null, listaAliasSolicitud, listaProyeccionesSolicitud, new ConsultaTramiteTraficoSolicitudFilaTablaResultadoBean());
			for (ConsultaTramiteTraficoSolicitudFilaTablaResultadoBean cSolicitud : listaSolicitudes){
				Long expediente = cSolicitud.getNumExpediente();
				if (expediente != null) {
					String matricula = "";
					String bastidor = "";
					String codigoTasa = "";
					String tipoTasa = "";
					if (cSolicitud.getVehiculo() != null) {
						Vehiculo v = cSolicitud.getVehiculo();
						if (v.getMatricula() != null && !v.getMatricula().equals("")) {
							matricula = v.getMatricula();
						}
						if (v.getBastidor() != null && !v.getBastidor().equals("")){
							bastidor = v.getBastidor();
						}
					}
					if (cSolicitud.getTasa() != null) {
						org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa t = cSolicitud.getTasa();
						if (t.getCodigoTasa() != null && !t.getCodigoTasa().equals("")) {
							codigoTasa = t.getCodigoTasa();
						}
						if (t.getTipoTasa() != null && !t.getTipoTasa().equals("")) {
							tipoTasa = t.getTipoTasa();
						}
					}
					ConsultaTramiteTraficoFilaTablaResultadoBean fila = tramitesSolicitud.get(expediente);
					if (fila != null) {
						if (fila.getVehiculo() == null) {
							fila.setVehiculo(new Vector<Vehiculo>());
						}

						if (fila.getTasa() == null) {
							fila.setTasa(new Vector<org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa>());
						}
						Vehiculo v = new Vehiculo();
						v.setBastidor(bastidor);
						v.setMatricula(matricula);
						fila.getVehiculo().add(v);
						org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa t = new org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa();
						t.setCodigoTasa(codigoTasa);
						t.setTipoTasa(tipoTasa);
						fila.getTasa().add(t);
					}
				}
			}
		}
	}

	private ProjectionList crearProyeccionesSolicitudes() {
		ProjectionList listaProyeccionesSolicitud = Projections.projectionList();
		listaProyeccionesSolicitud.add(Projections.property("id.numExpediente"));
		listaProyeccionesSolicitud.add(Projections.property("vehiculo.bastidor"));
		listaProyeccionesSolicitud.add(Projections.property("vehiculo.matricula"));
		listaProyeccionesSolicitud.add(Projections.property("tasa.tipoTasa"));
		listaProyeccionesSolicitud.add(Projections.property("tasa.codigoTasa"));
		listaProyeccionesSolicitud.add(Projections.property("matricula"));
		listaProyeccionesSolicitud.add(Projections.property("bastidor"));
		return listaProyeccionesSolicitud;
	}

	private List<AliasQueryBean> crearAliasSolicitud() {
		List<AliasQueryBean> listaAliasSolicitud = new ArrayList<>();
		listaAliasSolicitud.add(new AliasQueryBean(Vehiculo.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
		listaAliasSolicitud.add(new AliasQueryBean(Tasa.class, "tasa", "tasa", CriteriaSpecification.LEFT_JOIN));
		return listaAliasSolicitud;
	}

	private HashMap<Long, ConsultaTramiteTraficoFilaTablaResultadoBean> obtenerTramitesSolicitud(List<ConsultaTramiteTraficoFilaTablaResultadoBean> lista) {
		HashMap<Long, ConsultaTramiteTraficoFilaTablaResultadoBean> tramitesSolicitud = new HashMap<>();
		if (lista != null) {
			for (ConsultaTramiteTraficoFilaTablaResultadoBean c : lista) {
				if (c.getTipoTramite() != null && c.getTipoTramite().equals(TipoTramiteTrafico.Solicitud.getNombreEnum())) {
					tramitesSolicitud.put(c.getNumExpediente(),c);
				}
			}
		}
		return tramitesSolicitud;
	}

}