package estadisticas.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import estadisticas.beans.consulta.ConsultaEstadisticasBean;
import hibernate.entities.general.RangoMatricula;
import hibernate.entities.tasas.EvolucionTasa;
import hibernate.entities.trafico.JustificanteProf;
import hibernate.entities.trafico.TramiteTrafico;
import hibernate.entities.trafico.Vehiculo;
import trafico.utiles.constantes.ConstantesEstadisticas;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Repository("EstadisticasDAO")
public class EstadisticasDAOImpl extends HibernateUtil {
	private static final ILoggerOegam log = LoggerOegam.getLogger(EstadisticasDAOImpl.class);

	@Autowired
	UtilesFecha utilesFecha;

	public void ejecutarTransacciones(Transaction tx) throws Exception {
		try {
			tx.commit();
		} catch (RuntimeException re) {
			try {
				tx.rollback();
				log.error("Se ha ejecutado el rollback: " + re);
				System.err.println("Se ha ejecutado el rollback: " + re);
			} catch (RuntimeException rbe) {
				log.error("No se ha podido ejecutar el rollback: " + rbe);
			}
		}
	}

	// Esta query hace un select count(*) de un campo y lo agrupa.
	public List<TramiteTrafico> buscaTramitesPorAgrupacion(
			ConsultaEstadisticasBean criterios) {
		List<TramiteTrafico> listaTramites = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria criteria = session.createCriteria(TramiteTrafico.class);

			ProjectionList projectionList = Projections.projectionList();

			Integer codMarca = -1;

			if (criterios.getMarcaBean() != null
					&& criterios.getMarcaBean().getCodigoMarca() != null) {

				codMarca = criterios.getMarcaBean().getCodigoMarca().intValue();
			}
			Integer agrupacion = criterios.getAgrupacion().intValue();

			// Comprobamos si hace falta crear el alias de vehiculo
			if (((!criterios.getTipoVehiculoBean().getTipoVehiculo().equals(""))
					&& (!"-1".equals(criterios.getTipoVehiculoBean().getTipoVehiculo())))
					|| (criterios.getMarcaBean() != null && criterios.getMarcaBean().getCodigoMarca() != null
							&& !codMarca.equals(Integer.valueOf(String.valueOf("-1"))))
					|| (!agrupacion.equals(Integer.valueOf(String.valueOf("-1"))))
							&& (agrupacion.equals(Integer.valueOf(String.valueOf("5"))))
					|| (!agrupacion.equals(Integer.valueOf(String.valueOf("-1"))))
							&& (agrupacion.equals(Integer.valueOf(String.valueOf("9"))))
					|| (!agrupacion.equals(Integer.valueOf(String.valueOf("-1"))))
							&& (agrupacion.equals(Integer.valueOf(String.valueOf("12"))))
					|| (!agrupacion.equals(Integer.valueOf(String.valueOf("-1"))))
							&& (agrupacion.equals(Integer.valueOf(String.valueOf("13"))))) {
				criteria.createAlias("vehiculo", "vehiculo");
			}

			// Comprobamos si hace falta crear el alias de Contrato

			if (((!criterios.getJefatura().getJefaturaProvincial().equals("")) && (!"-1"
					.equals(criterios.getJefatura().getJefaturaProvincial())))
					|| ((!criterios.getProvincia().getIdProvincia().equals("")) && (!"-1"
							.equals(criterios.getProvincia().getIdProvincia())))
					|| (!agrupacion
							.equals(Integer.valueOf(String.valueOf("-1"))))
					&& (agrupacion.equals(Integer.valueOf(String.valueOf("2"))))
					|| (!agrupacion
							.equals(Integer.valueOf(String.valueOf("-1"))))
					&& (agrupacion.equals(Integer.valueOf(String.valueOf("3"))))) {

				criteria.createAlias("contrato", "contrato");
			}

			if ((!criterios.getTipoTramite().equalsIgnoreCase(""))
					&& (!"-1".equals(criterios.getTipoTramite()))) {
				criteria.add(Restrictions.eq("tipoTramite",
						criterios.getTipoTramite()));
			}

			// Ejecuta una consulta Criteria basada en los valores seleccionados en la lista estadoMultiple
			if (criterios.getEstadoMultiple()!=null && !criterios.getEstadoMultiple().equals("") ) {
				List<BigDecimal> listaEstadosMultiple = new ArrayList<BigDecimal>();
				for (String estado : criterios.getEstadoMultiple()){
					if (!estado.equals("-1")){
						listaEstadosMultiple.add(new BigDecimal(estado));
					}
				}
				if(!listaEstadosMultiple.isEmpty()){
					criteria.add(Restrictions.in("estado", listaEstadosMultiple));
				}
			}

			if ((!criterios.getNumColegiado().equalsIgnoreCase(""))
					&& (!"-1".equals(criterios.getNumColegiado()))) {
				criteria.add(Restrictions.eq("numColegiado",
						criterios.getNumColegiado()));
			}

			Integer tipoCreacion = criterios.getIdTipoCreacion().intValue();
			if (!tipoCreacion.equals(Integer.valueOf(String.valueOf("-1")))) {
				criteria.createAlias("tipoCreacion", "tipoCreacion");
				criteria.add(Restrictions.eq("tipoCreacion.idTipoCreacion",
						criterios.getIdTipoCreacion()));
			}

			if ((!criterios.getJefatura().getJefaturaProvincial().equals(""))
					&& (!"-1".equals(criterios.getJefatura()
							.getJefaturaProvincial()))) {

				criteria.createAlias("contrato.jefaturaTrafico", "Jefatura");
				criteria.add(Restrictions.eq("Jefatura.jefaturaProvincial",
						criterios.getJefatura().getJefaturaProvincial()));
			}

			if ((!criterios.getTipoVehiculoBean().getTipoVehiculo().equals(""))
					&& (!"-1".equals(criterios.getTipoVehiculoBean()
							.getTipoVehiculo()))) {
				criteria.createAlias("vehiculo.tipoVehiculoBean", "tipoVehic");
				criteria.add(Restrictions.eq("tipoVehic.tipoVehiculo",
						criterios.getTipoVehiculoBean().getTipoVehiculo()));
			}

			if (criterios.getMarcaBean() != null
					&& criterios.getMarcaBean().getCodigoMarca() != null
					&& !codMarca.equals(Integer.valueOf(String.valueOf("-1")))) {
				criteria.createAlias("vehiculo.marcaDgt", "marcaDGT");
				criteria.add(Restrictions.eq("marcaDGT.codigoMarca",
						codMarca.longValue()));
			}

			if ((!criterios.getProvincia().getIdProvincia().equals(""))
					&& (!"-1".equals(criterios.getProvincia().getIdProvincia()))) {
				criteria.createAlias("contrato.provincia", "prov");
				criteria.add(Restrictions.eq("prov.idProvincia", criterios
						.getProvincia().getIdProvincia()));
			}

			if (!criterios.getFechaMatriculacion().isfechaNula()) {
				if (!criterios.getFechaMatriculacion().isfechaInicioNula()) {
					criteria.add(Restrictions.ge("fechaPresentacion", criterios
							.getFechaMatriculacion().getFechaInicio()));
				}
				if (!criterios.getFechaMatriculacion().isfechaFinNula()) {
					String diaOriginal = criterios.getFechaMatriculacion()
							.getDiaFin();
					Integer diaFin = Integer.parseInt(diaOriginal);
					diaFin += 1;
					// Seteamos un día más a la fecha Fin para que tenga en
					// cuenta todo el día
					criterios.getFechaMatriculacion().setDiaFin(
							diaFin.toString());
					criteria.add(Restrictions.lt("fechaPresentacion", criterios
							.getFechaMatriculacion().getFechaFin()));
					// Dejamos la fecha Original que puso el usuario
					criterios.getFechaMatriculacion().setDiaFin(diaOriginal);
				}
			}

			if (!agrupacion.equals(Integer.valueOf(String.valueOf("-1")))) {
				switch (agrupacion) {
				case 1:
					projectionList.add(
							Projections.groupProperty("numColegiado"),
							"numColegiado");
					projectionList.add(Projections.rowCount(), "numColegiado");
					criteria.setProjection(projectionList).addOrder(
							Order.asc("numColegiado"));
					break;
				case 2:
					criteria.createAlias("contrato.provincia", "provincia");
					projectionList.add(
							Projections.groupProperty("provincia.nombre"),
							"provincia.nombre");
					projectionList.add(Projections.rowCount(),
							"provincia.nombre");
					criteria.setProjection(projectionList).addOrder(
							Order.asc("provincia.nombre"));
					break;
				case 3:
					criteria.createAlias("contrato.jefaturaTrafico",
							"jefaturaTrafico");
					projectionList.add(Projections
							.groupProperty("jefaturaTrafico.descripcion"),
							"jefaturaTrafico.descripcion");
					projectionList.add(Projections.rowCount(),
							"jefaturaTrafico.descripcion");
					criteria.setProjection(projectionList).addOrder(
							Order.asc("jefaturaTrafico.descripcion"));
					break;
				case 4:
					projectionList.add(Projections.sqlGroupProjection(
							"TO_CHAR(fecha_presentacion,'HH24') as total",
							"TO_CHAR(fecha_presentacion,'HH24')",
							new String[] { "total" },
							new Type[] { Hibernate.STRING }));

					projectionList.add(Projections.rowCount());
					criteria.add(Restrictions.isNotNull("fechaPresentacion"));
					criteria.setProjection(projectionList);
					break;
				case 5:
					projectionList.add(
							Projections.groupProperty("vehiculo.idCarburante"),
							"vehiculo.idCarburante");
					projectionList.add(Projections.rowCount(),
							"vehiculo.idCarburante");
					criteria.setProjection(projectionList).addOrder(
							Order.asc("vehiculo.idCarburante"));
					break;
				case 6:
					criteria.createAlias("tramiteTrafTran", "tramiteTrafTran");
					projectionList
							.add(Projections
									.groupProperty("tramiteTrafTran.tipoTransferencia"),
									"tramiteTrafTran.tipoTransferencia");
					projectionList.add(Projections.rowCount(),
							"tramiteTrafTran.tipoTransferencia");
					criteria.setProjection(projectionList).addOrder(
							Order.asc("tramiteTrafTran.tipoTransferencia"));
					break;
				case 7:

					criteria.createAlias("intervinienteTraficos",
							"intervinienteTraficos");
					criteria.createAlias(
							"intervinienteTraficos.tipoIntervinienteBean",
							"tipoInterv");
					criteria.createAlias(
							"intervinienteTraficos.personaDireccion",
							"personaDireccion");
					criteria.createAlias(
							"intervinienteTraficos.personaDireccion.direccion",
							"direccion");
					criteria.createAlias(
							"intervinienteTraficos.personaDireccion.direccion.municipio",
							"municipio");

					criteria.add(Restrictions.eq("tipoTramite", "T1"));
					criteria.add(Restrictions.eq(
							"tipoInterv.tipoInterviniente", "004"));

					projectionList.add(
							Projections.groupProperty("municipio.nombre"),
							"municipio.nombre");
					projectionList.add(Projections.rowCount(),
							"municipio.nombre");
					criteria.setProjection(projectionList).addOrder(
							Order.asc("municipio.nombre"));
					break;
				case 8:
					projectionList.add(Projections.groupProperty("exentoCem"),
							"exentoCem");
					projectionList.add(Projections.rowCount(), "exentoCem");
					criteria.setProjection(projectionList).addOrder(
							Order.asc("exentoCem"));
					break;
				case 9:
					projectionList.add(
							Projections.groupProperty("vehiculo.idServicio"),
							"vehiculo.idServicio");
					projectionList.add(Projections.rowCount(),
							"vehiculo.idServicio");
					criteria.setProjection(projectionList).addOrder(
							Order.asc("vehiculo.idServicio"));
					break;
				case 10:
					projectionList.add(
							Projections.groupProperty("tipoTramite"),
							"tipoTramite");
					projectionList.add(Projections.rowCount(), "tipoTramite");
					criteria.setProjection(projectionList).addOrder(
							Order.asc("tipoTramite"));
					break;
				case 11:
					// Modificación realizada para permitir realizar una consulta por selección múltiple en el
					// combo Estado del trámite
					projectionList.add(Projections.sqlGroupProjection(
							"estado as estado",
							"estado",
							new String[] { "estado" },
							new Type[] { Hibernate.STRING }));
					projectionList.add(Projections.rowCount());
					criteria.setProjection(projectionList).addOrder(
							Order.asc("estado"));
					break;
				case 12:
					criteria.createAlias("vehiculo.marcaDgt", "marcaDGT");
					projectionList.add(
							Projections.groupProperty("marcaDGT.marca"),
							"marcaDGT.marca");
					projectionList
							.add(Projections.rowCount(), "marcaDGT.marca");
					criteria.setProjection(projectionList).addOrder(
							Order.asc("marcaDGT.marca"));

					break;
				case 13:
					criteria.createAlias("vehiculo.tipoVehiculoBean",
							"tipoVehic");
					projectionList.add(
							Projections.groupProperty("tipoVehic.descripcion"),
							"tipoVehic.descripcion");
					projectionList.add(Projections.rowCount(),
							"tipoVehic.descripcion");
					criteria.setProjection(projectionList).addOrder(
							Order.asc("tipoVehic.descripcion"));
					break;

				case 14:
					criteria.createAlias("tipoCreacion", "tipoCreacion");
					projectionList.add(Projections
							.groupProperty("tipoCreacion.descripcionCreacion"),
							"tipoCreacion.descripcionCreacion");
					projectionList.add(Projections.rowCount(),
							"tipoCreacion.descripcionCreacion");
					criteria.setProjection(projectionList).addOrder(
							Order.asc("tipoCreacion.descripcionCreacion"));
					break;
				}
			}

			listaTramites = criteria.list();
		} finally {
			session.close();
		}
		return listaTramites;
	}

	/**
	 * JMC: Metodo que busca los Justificantes Agrupados por Matricula
	 * 
	 * @param criterios
	 * @return
	 */
	public List<JustificanteProf> buscaJustificantesPorAgrupacion(
			ConsultaEstadisticasBean criterios) {
		List<JustificanteProf> listaJustificantes = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria criteria = session.createCriteria(JustificanteProf.class);

			ProjectionList projectionList = Projections.projectionList();

			criteria.createAlias("tramiteTrafico", "tramiteTrafico",
					CriteriaSpecification.INNER_JOIN);

			criteria.createAlias("tramiteTrafico.vehiculo", "vehiculo",
					CriteriaSpecification.INNER_JOIN);

			criteria.add(Restrictions.isNotNull("idJustificante"));

			criteria.add(Restrictions.eq("tramiteTrafico.numColegiado",
					criterios.getNumColegiado()));

			// Excluimos los estados Finalizado PDF (13) , Finalizado
			// Teleméticamente (12) y Finalizado Telemáticmante Impreso (14)
			criteria.add(Restrictions.ne("tramiteTrafico.estado", BigDecimal
					.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_PDF
							.getValorEnum()))));
			criteria.add(Restrictions.ne(
					"tramiteTrafico.estado",
					BigDecimal.valueOf(Long
							.parseLong(EstadoTramiteTrafico.Finalizado_Telematicamente
									.getValorEnum()))));
			criteria.add(Restrictions.ne(
					"tramiteTrafico.estado",
					BigDecimal.valueOf(Long
							.parseLong(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
									.getValorEnum()))));

			criteria.add(Restrictions.ge("fechaInicio", criterios
					.getFechaMatriculacion().getFechaInicio()));

			String diaOriginal = criterios.getFechaMatriculacion().getDiaFin();
			Integer diaFin = Integer.parseInt(diaOriginal);
			diaFin += 1;
			// Seteamos un dia mas a la fecha Fin para que tenga en cuenta todo
			// el dia
			criterios.getFechaMatriculacion().setDiaFin(diaFin.toString());
			criteria.add(Restrictions.lt("fechaInicio", criterios
					.getFechaMatriculacion().getFechaFin()));
			// Dejamos la fecha Original que puso el usuario
			criterios.getFechaMatriculacion().setDiaFin(diaOriginal);

			projectionList.add(Projections.groupProperty("vehiculo.matricula"),
					"vehiculo.matricula");
			projectionList.add(Projections.rowCount(), "vehiculo.matricula");
			criteria.setProjection(projectionList).addOrder(
					Order.asc("vehiculo.matricula"));

			listaJustificantes = criteria.list();
		} finally {
			session.close();
		}
		return listaJustificantes;
	}

	public List<HashMap> buscaTramitesPorAgrupacionParaExcel(
			ConsultaEstadisticasBean criterios) throws Exception {
		List<HashMap> listmapas = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria criteria = session.createCriteria(TramiteTrafico.class,
					"tramiteTrafico");
			// Criteria criteriaContrato =
			// session.createCriteria(Contrato.class);

			// Criteria criteria = session.createCriteria(Provincia.class);
			Integer codMarca = -1;

			criteria.createAlias("contrato", "contrato",
					CriteriaSpecification.INNER_JOIN);
			criteria.createAlias("contrato.provincia", "provincia",
					CriteriaSpecification.INNER_JOIN);
			criteria.createAlias("contrato.jefaturaTrafico", "jefaturaTrafico");
			criteria.createAlias("tipoCreacion", "tipoCreacion");

			Integer agrupacion = criterios.getAgrupacion().intValue();
			if (!agrupacion.equals(Integer.valueOf(String.valueOf("-1")))) {

				switch (agrupacion) {
				case 12:
					criteria.createAlias("vehiculo", "vehiculo",
							CriteriaSpecification.INNER_JOIN);
					criteria.createAlias("vehiculo.marcaDgt", "marcaDGT",
							CriteriaSpecification.INNER_JOIN);
					criteria.createAlias("vehiculo.tipoVehiculoBean",
							"tipoVehic", CriteriaSpecification.LEFT_JOIN);
					break;
				case 13:
					criteria.createAlias("vehiculo", "vehiculo",
							CriteriaSpecification.INNER_JOIN);
					criteria.createAlias("vehiculo.marcaDgt", "marcaDGT",
							CriteriaSpecification.LEFT_JOIN);
					criteria.createAlias("vehiculo.tipoVehiculoBean",
							"tipoVehic", CriteriaSpecification.INNER_JOIN);
					break;
				default:
					criteria.createAlias("vehiculo", "vehiculo",
							CriteriaSpecification.LEFT_JOIN);
					criteria.createAlias("vehiculo.marcaDgt", "marcaDGT",
							CriteriaSpecification.LEFT_JOIN);
					criteria.createAlias("vehiculo.tipoVehiculoBean",
							"tipoVehic", CriteriaSpecification.LEFT_JOIN);
				}
			} else {
				criteria.createAlias("vehiculo", "vehiculo",
						CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("vehiculo.marcaDgt", "marcaDGT",
						CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("vehiculo.tipoVehiculoBean", "tipoVehic",
						CriteriaSpecification.LEFT_JOIN);
			}

			if (criterios.getMarcaBean() != null
					&& criterios.getMarcaBean().getCodigoMarca() != null) {
				codMarca = criterios.getMarcaBean().getCodigoMarca().intValue();
			}

			if ((!criterios.getTipoTramite().equalsIgnoreCase(""))
					&& (!"-1".equals(criterios.getTipoTramite()))) {
				criteria.add(Restrictions.eq("tipoTramite",
						criterios.getTipoTramite()));
			}

			// Se ha modificado esta consulta porque ahora el campo de ventana es de selección múltiple
			List<BigDecimal> listaEstadosMultiple = new ArrayList<BigDecimal>();
			for (String estado : criterios.getEstadoMultiple()){
				if (!estado.equals("-1")){
					listaEstadosMultiple.add(new BigDecimal(estado));
				}
			}

			if (!listaEstadosMultiple.isEmpty()) {
				criteria.add(Restrictions.in("estado", listaEstadosMultiple));
			}

			if ((!criterios.getNumColegiado().equalsIgnoreCase(""))
					&& (!"-1".equals(criterios.getNumColegiado()))) {
				criteria.add(Restrictions.eq("numColegiado",
						criterios.getNumColegiado()));
			}

			Integer tipoCreacion = criterios.getIdTipoCreacion().intValue();
			if (!tipoCreacion.equals(Integer.valueOf(String.valueOf("-1")))) {
				criteria.add(Restrictions.eq("tipoCreacion.idTipoCreacion",
						criterios.getIdTipoCreacion()));
			}

			if ((!criterios.getJefatura().getJefaturaProvincial().equals(""))
					&& (!"-1".equals(criterios.getJefatura()
							.getJefaturaProvincial()))) {

				criteria.add(Restrictions.eq(
						"jefaturaTrafico.jefaturaProvincial", criterios
								.getJefatura().getJefaturaProvincial()));
			}

			if ((!criterios.getTipoVehiculoBean().getTipoVehiculo().equals(""))
					&& (!"-1".equals(criterios.getTipoVehiculoBean()
							.getTipoVehiculo()))) {

				criteria.add(Restrictions.eq("tipoVehic.tipoVehiculo",
						criterios.getTipoVehiculoBean().getTipoVehiculo()));
			}

			if (criterios.getMarcaBean() != null
					&& criterios.getMarcaBean().getCodigoMarca() != null
					&& !codMarca.equals(Integer.valueOf(String.valueOf("-1")))) {
				// criteria.createAlias("vehiculo.marcaDgt","marcaDGT");
				criteria.add(Restrictions.eq("marcaDGT.codigoMarca",
						codMarca.longValue()));
			}

			if ((!criterios.getProvincia().getIdProvincia().equals(""))
					&& (!"-1".equals(criterios.getProvincia().getIdProvincia()))) {
				// criteria.createAlias("contrato.provincia","prov");
				criteria.add(Restrictions.eq("provincia.idProvincia", criterios
						.getProvincia().getIdProvincia()));
			}

			if (!criterios.getFechaMatriculacion().isfechaNula()) {
				if (!criterios.getFechaMatriculacion().isfechaInicioNula()) {
					criteria.add(Restrictions.ge("fechaPresentacion", criterios
							.getFechaMatriculacion().getFechaInicio()));
				}
				if (!criterios.getFechaMatriculacion().isfechaFinNula()) {
					String diaOriginal = criterios.getFechaMatriculacion()
							.getDiaFin();
					Integer diaFin = Integer.parseInt(diaOriginal);
					diaFin += 1;
					// Seteamos un día más a la fecha Fin para que tenga en
					// cuenta todo el día
					criterios.getFechaMatriculacion().setDiaFin(
							diaFin.toString());
					criteria.add(Restrictions.lt("fechaPresentacion", criterios
							.getFechaMatriculacion().getFechaFin()));
					// Dejamos la fecha Original que puso el usuario
					criterios.getFechaMatriculacion().setDiaFin(diaOriginal);
				}
			}

			ProjectionList proList = Projections.projectionList();

			proList.add(Projections.property("numExpediente"));
			proList.add(Projections.property("numColegiado"));
			proList.add(Projections.property("provincia.nombre"), "nombre");
			proList.add(Projections.property("jefaturaTrafico.descripcion"),
					"jefaturaTrafico.descripcion");
			proList.add(Projections.property("vehiculo.idCarburante"),
					"vehiculo.idCarburante");
			proList.add(Projections.property("exentoCem"));
			proList.add(Projections.property("vehiculo.idServicio"),
					"vehiculo.idServicio");
			proList.add(Projections.property("tipoTramite"));
			proList.add(Projections.property("estado"));
			proList.add(Projections.property("marcaDGT.marca"),
					"marcaDGT.marca");
			proList.add(Projections.property("tipoVehic.descripcion"),
					"tipoVehic.descripcion");
			proList.add(Projections
					.property("tipoCreacion.descripcionCreacion"));

			if (!agrupacion.equals(Integer.valueOf(String.valueOf("-1")))) {

				switch (agrupacion) {
				case 1:
					criteria.addOrder(Order.asc("numColegiado"));
					break;
				case 2:
					criteria.addOrder(Order.asc("provincia.nombre"));
					break;
				case 3:
					criteria.addOrder(Order.asc("jefaturaTrafico.descripcion"));
					break;
				case 4:
					break;
				case 5:
					criteria.addOrder(Order.asc("vehiculo.idCarburante"));
					break;
				case 6:
					criteria.createAlias("tramiteTrafTran", "tramiteTrafTran");
					proList.add(Projections
							.property("tramiteTrafTran.tipoTransferencia"),
							"tipoTransferencia");
					criteria.addOrder(Order
							.asc("tramiteTrafTran.tipoTransferencia"));
					break;
				case 7:
					criteria.createAlias("intervinienteTraficos",
							"intervinienteTraficos");
					criteria.createAlias(
							"intervinienteTraficos.tipoIntervinienteBean",
							"tipoInterv");
					criteria.createAlias(
							"intervinienteTraficos.personaDireccion",
							"personaDireccion");
					criteria.createAlias(
							"intervinienteTraficos.personaDireccion.direccion",
							"direccion");
					criteria.createAlias(
							"intervinienteTraficos.personaDireccion.direccion.municipio",
							"municipio");
					criteria.addOrder(Order.asc("municipio.nombre"));
					break;
				case 8:
					criteria.addOrder(Order.asc("exentoCem"));
					break;
				case 9:
					criteria.addOrder(Order.asc("vehiculo.idServicio"));
					break;
				case 10:
					criteria.addOrder(Order.asc("tipoTramite"));
					break;
				case 11:
					criteria.addOrder(Order.asc("estado"));
					break;
				case 12:

					criteria.addOrder(Order.asc("marcaDGT.marca"));
					break;
				case 13:
					criteria.addOrder(Order.asc("tipoVehic.descripcion"));
					break;
				case 14:
					criteria.addOrder(Order
							.asc("tipoCreacion.descripcionCreacion"));
					break;
				}
			}

			// criteria.setProjection(proList).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			criteria.setProjection(proList);

			listmapas = criteria.list();
		} finally {
			session.close();
		}
		return listmapas;

	}

	/**
	 * JMC: Metodo que consulta el detalle de los Justificantes agrupados por
	 * Matriculas para ponerlos en un Excel
	 * 
	 * @param criterios
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> buscaJustificantesPorAgrupacionParaExcel(
			ConsultaEstadisticasBean criterios) throws Exception {
		List<HashMap> listaJustificantes = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria criteria = session.createCriteria(JustificanteProf.class,
					"justificanteProf");

			criteria.createAlias("tramiteTrafico", "tramiteTrafico",
					CriteriaSpecification.INNER_JOIN);
			criteria.createAlias("tramiteTrafico.vehiculo", "vehiculo",
					CriteriaSpecification.LEFT_JOIN);

			criteria.add(Restrictions.eq("tramiteTrafico.numColegiado",
					criterios.getNumColegiado()));

			criteria.add(Restrictions.isNotNull("idJustificante"));

			criteria.add(Restrictions.ge("fechaInicio", criterios
					.getFechaMatriculacion().getFechaInicio()));

			String diaOriginal = criterios.getFechaMatriculacion().getDiaFin();
			Integer diaFin = Integer.parseInt(diaOriginal);
			diaFin += 1;
			// Seteamos un día más a la fecha Fin para que tenga en cuenta todo
			// el día
			criterios.getFechaMatriculacion().setDiaFin(diaFin.toString());
			criteria.add(Restrictions.lt("fechaInicio", criterios
					.getFechaMatriculacion().getFechaFin()));
			// Dejamos la fecha Original que puso el usuario
			criterios.getFechaMatriculacion().setDiaFin(diaOriginal);

			// Excluimos los estados Finalizado PDF (13), Finalizado
			// Telemáticamente (12) y Finalizado Telemáticamente Impreso (14)
			criteria.add(Restrictions.ne("tramiteTrafico.estado", BigDecimal
					.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_PDF
							.getValorEnum()))));
			criteria.add(Restrictions.ne(
					"tramiteTrafico.estado",
					BigDecimal.valueOf(Long
							.parseLong(EstadoTramiteTrafico.Finalizado_Telematicamente
									.getValorEnum()))));
			criteria.add(Restrictions.ne(
					"tramiteTrafico.estado",
					BigDecimal.valueOf(Long
							.parseLong(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
									.getValorEnum()))));

			ProjectionList proList = Projections.projectionList();

			proList.add(Projections.property("tramiteTrafico.numColegiado"),
					"numColegiado");
			proList.add(Projections.property("vehiculo.matricula"),
					"vehiculo.matricula");
			proList.add(Projections.property("idJustificante"));
			proList.add(Projections.property("tramiteTrafico.numExpediente"),
					"tramiteTrafico.numExpediente");
			proList.add(Projections.property("tramiteTrafico.tipoTramite"),
					"tramiteTrafico.tipoTramite");
			proList.add(Projections.property("tramiteTrafico.estado"), "estado");
			proList.add(Projections.property("fechaInicio"));
			proList.add(Projections.property("fechaFin"));
			proList.add(Projections.property("diasValidez"));

			// Ordeno por Matricula
			criteria.addOrder(Order.asc("vehiculo.matricula"));

			// criteria.setProjection(proList).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			criteria.setProjection(proList);
			listaJustificantes = criteria.list();
		} finally {
			session.close();
		}
		return listaJustificantes;

	}

	/**
	 * JMC: Metodo que consulta el detalle de los Justificantes agrupados por
	 * Matriculas para ponerlos en un Excel
	 * 
	 * @param criterios
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> buscaVehiculosPorAgrupacionParaExcel(
			ConsultaEstadisticasBean criterios, Boolean Agrupacion)
			throws Exception {
		List<HashMap> tipoTramiteVehiculos = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			Criteria criteria = session.createCriteria(Vehiculo.class);

			if ((!criterios.getNumColegiado().equalsIgnoreCase(""))
					&& (!"-1".equals(criterios.getNumColegiado()))) {
				criteria.add(Restrictions.eq("id.numColegiado",
						criterios.getNumColegiado()));
			}

			if (((!criterios.getTipoTramite().equalsIgnoreCase("")) && (!"-1"
					.equals(criterios.getTipoTramite())))
					|| !criterios.getEstado().equals(
							Integer.valueOf(String.valueOf("-1"))))
				criteria.createAlias("tramiteTrafico", "tramiteTrafico",
						CriteriaSpecification.INNER_JOIN);

			if (!criterios.getFechaPresentacion().isfechaNula()) {
				if (!criterios.getFechaPresentacion().isfechaInicioNula()) {
					criteria.add(Restrictions.ge(
							"tramiteTrafico.fechaPresentacion", criterios
									.getFechaPresentacion().getFechaInicio()));
				}
				if (!criterios.getFechaPresentacion().isfechaFinNula()) {
					Calendar greg = Calendar.getInstance();

					Fecha nFecha = utilesFecha.getFechaFracionada(criterios
							.getFechaPresentacion().getFechaFin());

					greg.set(Integer.parseInt(nFecha.getAnio()),
							Integer.parseInt(nFecha.getMes()) - 1,
							Integer.parseInt(nFecha.getDia()),
							Integer.parseInt("23"), Integer.parseInt("59"),
							Integer.parseInt("59"));

					Timestamp fechaStamp = new Timestamp(greg.getTimeInMillis());
					criteria.add(Restrictions.le(
							"tramiteTrafico.fechaPresentacion",
							utilesFecha.getDate(fechaStamp)));
				}
			}

			if ((!criterios.getTipoTramite().equalsIgnoreCase(""))
					&& (!"-1".equals(criterios.getTipoTramite()))) {
				criteria.add(Restrictions.eq("tramiteTrafico.tipoTramite",
						criterios.getTipoTramite()));
			} else if (criterios.getTipoTramite().toString()
					.equalsIgnoreCase("-1")) {
				criteria.add(Restrictions.in(
						"tramiteTrafico.tipoTramite",
						new String[] {
								TipoTramiteTrafico.TransmisionElectronica
										.getValorEnum(),
								TipoTramiteTrafico.Transmision.getValorEnum() }));
			}

			if (!criterios.getEstado().toString().equals("")
					&& !criterios.getEstado().toString().equalsIgnoreCase("-1")) {
				criteria.add(Restrictions.eq("tramiteTrafico.estado",
						criterios.getEstado()));
			} else if (criterios.getEstado().toString().equalsIgnoreCase("-1")) {
				criteria.add(Restrictions
						.in("tramiteTrafico.estado",
								new BigDecimal[] {
										new BigDecimal(
												EstadoTramiteTrafico.Finalizado_PDF
														.getValorEnum()),
										new BigDecimal(
												EstadoTramiteTrafico.Finalizado_Telematicamente
														.getValorEnum()),
										new BigDecimal(
												EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
														.getValorEnum()) }));
			}

			if (Agrupacion) {
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.property("id.numColegiado"));
				projectionList.add(Projections
						.property("tramiteTrafico.tipoTramite"));
				projectionList.add(Projections
						.property("tramiteTrafico.estado"));
				projectionList.add(Projections.property("matricula"));
				projectionList.add(Projections.property("bastidor"));
				projectionList.add(Projections.property("fechaPrimMatri"));
				projectionList.add(Projections
						.property("tramiteTrafico.fechaPresentacion"));
				criteria.setProjection(projectionList);
				Integer agrupacion = criterios.getAgrupacionVehiculos()
						.intValue();

				switch (agrupacion) {
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
						criteria.addOrder(Order.desc("fechaPrimMatri"));
						break;
				}
			} else {
				ProjectionList projectionList = Projections.projectionList();
				criteria.setProjection(projectionList);
				criteria.setProjection(projectionList).addOrder(
						Order.desc("tramiteTrafico.fechaPresentacion"));
			}

			tipoTramiteVehiculos = criteria.list();
		} finally {
			session.close();
		}
		return tipoTramiteVehiculos;

	}

	public Integer getNumberOfFiles(ConsultaEstadisticasBean criterios)
			throws Exception {
		Integer total = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria criteria = session.createCriteria(TramiteTrafico.class,
					"tramiteTrafico");
			List<TramiteTrafico> listaTramites = null;

			Integer codMarca = -1;

			criteria.createAlias("contrato", "contrato",
					CriteriaSpecification.INNER_JOIN);
			criteria.createAlias("contrato.provincia", "provincia",
					CriteriaSpecification.INNER_JOIN);
			criteria.createAlias("contrato.jefaturaTrafico", "jefaturaTrafico");
			criteria.createAlias("tipoCreacion", "tipoCreacion");

			Integer agrupacion = criterios.getAgrupacion().intValue();
			if (!agrupacion.equals(Integer.valueOf(String.valueOf("-1")))) {

				switch (agrupacion) {
				case 12:
					criteria.createAlias("vehiculo", "vehiculo",
							CriteriaSpecification.INNER_JOIN);
					criteria.createAlias("vehiculo.marcaDgt", "marcaDGT",
							CriteriaSpecification.INNER_JOIN);
					criteria.createAlias("vehiculo.tipoVehiculoBean",
							"tipoVehic", CriteriaSpecification.LEFT_JOIN);
					break;
				case 13:
					criteria.createAlias("vehiculo", "vehiculo",
							CriteriaSpecification.INNER_JOIN);
					criteria.createAlias("vehiculo.marcaDgt", "marcaDGT",
							CriteriaSpecification.LEFT_JOIN);
					criteria.createAlias("vehiculo.tipoVehiculoBean",
							"tipoVehic", CriteriaSpecification.INNER_JOIN);
					break;
				default:
					criteria.createAlias("vehiculo", "vehiculo",
							CriteriaSpecification.LEFT_JOIN);
					criteria.createAlias("vehiculo.marcaDgt", "marcaDGT",
							CriteriaSpecification.LEFT_JOIN);
					criteria.createAlias("vehiculo.tipoVehiculoBean",
							"tipoVehic", CriteriaSpecification.LEFT_JOIN);
				}
			} else {
				criteria.createAlias("vehiculo", "vehiculo",
						CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("vehiculo.marcaDgt", "marcaDGT",
						CriteriaSpecification.LEFT_JOIN);
				criteria.createAlias("vehiculo.tipoVehiculoBean", "tipoVehic",
						CriteriaSpecification.LEFT_JOIN);
			}

			if (criterios.getMarcaBean() != null && criterios.getMarcaBean().getCodigoMarca() != null) {
				codMarca = criterios.getMarcaBean().getCodigoMarca().intValue();
			}

			if ((!criterios.getTipoTramite().equalsIgnoreCase("")) && (!"-1".equals(criterios.getTipoTramite()))) {
				criteria.add(Restrictions.eq("tipoTramite",
						criterios.getTipoTramite()));
			}

			// Ejecuta una consulta Criteria basada en los valores seleccionados en la lista estadoMultiple
			if ((criterios.getEstadoMultiple() != null)) {
				List<BigDecimal> listaEstadosMultiple = new ArrayList<>();
				for (String estado : criterios.getEstadoMultiple()) {
					if (!estado.equals("-1")) {
						listaEstadosMultiple.add(new BigDecimal(estado));
					}
				}
				if (!listaEstadosMultiple.isEmpty()) {
					criteria.add(Restrictions.in("estado", listaEstadosMultiple));
				}
			}

			if ((!criterios.getNumColegiado().equalsIgnoreCase("")) && (!"-1".equals(criterios.getNumColegiado()))) {
				criteria.add(Restrictions.eq("numColegiado",
						criterios.getNumColegiado()));
			}

			Integer tipoCreacion = criterios.getIdTipoCreacion().intValue();
			if (!tipoCreacion.equals(Integer.valueOf(String.valueOf("-1")))) {
				criteria.add(Restrictions.eq("tipoCreacion.idTipoCreacion",
						criterios.getIdTipoCreacion()));
			}

			if ((!criterios.getJefatura().getJefaturaProvincial().equals(""))
					&& (!"-1".equals(criterios.getJefatura().getJefaturaProvincial()))) {
				// criteria.add(Restrictions.eq("Jefatura.jefaturaProvincial",
				// criterios.getJefatura().getJefaturaProvincial()));
				criteria.add(Restrictions.eq(
						"jefaturaTrafico.jefaturaProvincial", criterios
								.getJefatura().getJefaturaProvincial()));
			}

			if ((!criterios.getTipoVehiculoBean().getTipoVehiculo().equals(""))
					&& (!"-1".equals(criterios.getTipoVehiculoBean()
							.getTipoVehiculo()))) {

				criteria.add(Restrictions.eq("tipoVehic.tipoVehiculo",
						criterios.getTipoVehiculoBean().getTipoVehiculo()));

			}

			if (criterios.getMarcaBean() != null
					&& criterios.getMarcaBean().getCodigoMarca() != null
					&& !codMarca.equals(Integer.valueOf(String.valueOf("-1")))) {
				criteria.add(Restrictions.eq("marcaDGT.codigoMarca",
						codMarca.longValue()));
			}

			if ((!criterios.getProvincia().getIdProvincia().equals(""))
					&& (!"-1".equals(criterios.getProvincia().getIdProvincia()))) {

				criteria.add(Restrictions.eq("provincia.idProvincia", criterios
						.getProvincia().getIdProvincia()));
			}

			if (!criterios.getFechaMatriculacion().isfechaNula()) {
				if (!criterios.getFechaMatriculacion().isfechaInicioNula()) {
					criteria.add(Restrictions.ge("fechaPresentacion", criterios
							.getFechaMatriculacion().getFechaInicio()));
				}
				if (!criterios.getFechaMatriculacion().isfechaFinNula()) {
					String diaOriginal = criterios.getFechaMatriculacion()
							.getDiaFin();
					Integer diaFin = Integer.parseInt(diaOriginal);
					diaFin += 1;
					// Seteamos un día más a la fecha Fin para que tenga en
					// cuenta todo el día
					criterios.getFechaMatriculacion().setDiaFin(
							diaFin.toString());
					criteria.add(Restrictions.lt("fechaPresentacion", criterios
							.getFechaMatriculacion().getFechaFin()));
					// Dejamos la fecha Original que puso el usuario
					criterios.getFechaMatriculacion().setDiaFin(diaOriginal);
				}
			}

			ProjectionList proList = Projections.projectionList();

			if (!agrupacion.equals(Integer.valueOf(String.valueOf("-1")))) {

				switch (agrupacion) {
					case 6:
						criteria.createAlias("tramiteTrafTran", "tramiteTrafTran");
						break;
					case 7:
						criteria.createAlias("intervinienteTraficos",
								"intervinienteTraficos");
						criteria.createAlias(
								"intervinienteTraficos.tipoIntervinienteBean",
								"tipoInterv");
						criteria.createAlias(
								"intervinienteTraficos.personaDireccion",
								"personaDireccion");
						criteria.createAlias(
								"intervinienteTraficos.personaDireccion.direccion",
								"direccion");
						criteria.createAlias(
								"intervinienteTraficos.personaDireccion.direccion.municipio",
								"municipio");
						break;
				}
			}

			criteria.setProjection(Projections.rowCount());
			List lista = criteria.list();
			total = (Integer) lista.get(0);
		} finally {
			session.close();
		}
		return total;
	}

	public List<TramiteTrafico> buscaTramites(ConsultaEstadisticasBean criterios) {
		List<TramiteTrafico> listaTramites = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria criteria = session.createCriteria(TramiteTrafico.class);

			Integer codMarca = -1;

			if (criterios.getMarcaBean() != null
					&& criterios.getMarcaBean().getCodigoMarca() != null) {
				codMarca = criterios.getMarcaBean().getCodigoMarca().intValue();
			}

			// Comprobamos si hace falta crear el alias de vehículo

			if (((!criterios.getTipoVehiculoBean().getTipoVehiculo().equals(""))
					&& (!"-1".equals(criterios.getTipoVehiculoBean().getTipoVehiculo())))
					|| (criterios.getMarcaBean() != null && criterios.getMarcaBean().getCodigoMarca() != null
							&& !codMarca.equals(Integer.valueOf(String.valueOf("-1"))))) {
				criteria.createAlias("vehiculo", "vehiculo");
			}

			// Comprobamos si hace falta crear el alias de Contrato

			if (((!criterios.getJefatura().getJefaturaProvincial().equals(""))
					&& (!"-1".equals(criterios.getJefatura().getJefaturaProvincial())))
					|| ((!criterios.getProvincia().getIdProvincia().equals(""))
							&& (!"-1".equals(criterios.getProvincia().getIdProvincia())))) {
				criteria.createAlias("contrato", "contrato");
			}

			if ((!criterios.getTipoTramite().equalsIgnoreCase("")) && (!"-1".equals(criterios.getTipoTramite()))) {
				criteria.add(Restrictions.eq("tipoTramite", criterios.getTipoTramite()));
			}

			// Ejecuta una consulta Criteria basada en los valores seleccionados en la lista estadoMultiple
			if (criterios.getEstadoMultiple() != null) {
				List<BigDecimal> listaEstadosMultiple = new ArrayList<>();
				for (String estado : criterios.getEstadoMultiple()){
					if (!estado.equals("-1")){
						listaEstadosMultiple.add(new BigDecimal(estado));
					}
				}
				if (!listaEstadosMultiple.isEmpty()) {
					criteria.add(Restrictions.in("estado", listaEstadosMultiple));
				}
			}
			
			if ((!criterios.getNumColegiado().equalsIgnoreCase(""))
					&& (!"-1".equals(criterios.getNumColegiado()))) {
				criteria.add(Restrictions.eq("numColegiado",
						criterios.getNumColegiado()));
			}

			Integer tipoCreacion = criterios.getIdTipoCreacion().intValue();
			if (!tipoCreacion.equals(Integer.valueOf(String.valueOf("-1")))) {
				criteria.createAlias("tipoCreacion", "tipoCreacion");
				criteria.add(Restrictions.eq("tipoCreacion.idTipoCreacion",
						criterios.getIdTipoCreacion()));
			}

			if ((!criterios.getJefatura().getJefaturaProvincial().equals(""))
					&& (!"-1".equals(criterios.getJefatura()
							.getJefaturaProvincial()))) {
				criteria.createAlias("contrato.jefaturaTrafico", "Jefatura");
				criteria.add(Restrictions.eq("Jefatura.jefaturaProvincial",
						criterios.getJefatura().getJefaturaProvincial()));
			}

			if ((!criterios.getTipoVehiculoBean().getTipoVehiculo().equals(""))
					&& (!"-1".equals(criterios.getTipoVehiculoBean()
							.getTipoVehiculo()))) {

				criteria.createAlias("vehiculo.tipoVehiculoBean", "tipoVehic");
				criteria.add(Restrictions.eq("tipoVehic.tipoVehiculo",
						criterios.getTipoVehiculoBean().getTipoVehiculo()));

			}

			if (criterios.getMarcaBean() != null
					&& criterios.getMarcaBean().getCodigoMarca() != null
					&& !codMarca.equals(Integer.valueOf(String.valueOf("-1")))) {
				criteria.createAlias("vehiculo.marcaDgt", "marcaDGT");
				criteria.add(Restrictions.eq("marcaDGT.codigoMarca",
						codMarca.longValue()));
			}

			if ((!criterios.getProvincia().getIdProvincia().equals(""))
					&& (!"-1".equals(criterios.getProvincia().getIdProvincia()))) {

				criteria.createAlias("contrato.provincia", "prov");
				criteria.add(Restrictions.eq("prov.idProvincia", criterios
						.getProvincia().getIdProvincia()));
			}

			if (!criterios.getFechaMatriculacion().isfechaNula()) {
				if (!criterios.getFechaMatriculacion().isfechaInicioNula()) {
					criteria.add(Restrictions.ge("fechaPresentacion", criterios
							.getFechaMatriculacion().getFechaInicio()));
				}
				if (!criterios.getFechaMatriculacion().isfechaFinNula()) {
					String diaOriginal = criterios.getFechaMatriculacion()
							.getDiaFin();
					Integer diaFin = Integer.parseInt(diaOriginal);
					diaFin += 1;
					// Seteamos un día más a la fecha Fin para que tenga en
					// cuenta todo el día
					criterios.getFechaMatriculacion().setDiaFin(
							diaFin.toString());
					criteria.add(Restrictions.lt("fechaPresentacion", criterios
							.getFechaMatriculacion().getFechaFin()));
					// Dejamos la fecha Original que puso el usuario
					criterios.getFechaMatriculacion().setDiaFin(diaOriginal);
				}
			}

			criteria.setProjection(Projections.rowCount());
			listaTramites = criteria.list();
		} finally {
			session.close();
		}
		return listaTramites;
	}


	/**
	 * JMC: Método que busca el total de Justificantes en un periodo para un
	 * Colegiado
	 * 
	 * @param criterios
	 * @return
	 */
	public String buscaTotalJustificantes(ConsultaEstadisticasBean criterios) {
		Number numeroJustificantes = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria criteria = session.createCriteria(JustificanteProf.class);

			criteria.createAlias("tramiteTrafico", "tramiteTrafico",
					CriteriaSpecification.INNER_JOIN);

			criteria.add(Restrictions.eq("tramiteTrafico.numColegiado",
					criterios.getNumColegiado()));

			criteria.add(Restrictions.isNotNull("idJustificante"));

			criteria.add(Restrictions.ge("fechaInicio", criterios
					.getFechaMatriculacion().getFechaInicio()));

			String diaOriginal = criterios.getFechaMatriculacion().getDiaFin();
			Integer diaFin = Integer.parseInt(diaOriginal);
			diaFin += 1;
			// Seteamos un día más a la fecha Fin para que tenga en cuenta todo
			// el día
			criterios.getFechaMatriculacion().setDiaFin(diaFin.toString());
			criteria.add(Restrictions.lt("fechaInicio", criterios
					.getFechaMatriculacion().getFechaFin()));
			// Dejamos la fecha Original que puso el usuario
			criterios.getFechaMatriculacion().setDiaFin(diaOriginal);

			// Excluimos los estados Finalizado PDF (13) , Finalizado
			// Teleméticamente (12) y Finalizado Telemáticmante Impreso (14)
			criteria.add(Restrictions.ne("tramiteTrafico.estado", BigDecimal
					.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_PDF
							.getValorEnum()))));
			criteria.add(Restrictions.ne(
					"tramiteTrafico.estado",
					BigDecimal.valueOf(Long
							.parseLong(EstadoTramiteTrafico.Finalizado_Telematicamente
									.getValorEnum()))));
			criteria.add(Restrictions.ne(
					"tramiteTrafico.estado",
					BigDecimal.valueOf(Long
							.parseLong(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
									.getValorEnum()))));

			// criteria.add(Restrictions.not(Restrictions.in("tramiteTrafico.estado",BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())),BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()))
			// )));

			numeroJustificantes = (Number) criteria.setProjection(
					Projections.rowCount()).uniqueResult();
		} finally {
			session.close();
		}
		if (numeroJustificantes != null) {
			return String.valueOf(numeroJustificantes);
		}
		return "";
	}


	@SuppressWarnings("unchecked")
	public List<Vehiculo> buscaVehiculosTipoTramite(
			ConsultaEstadisticasBean criterios, Boolean Agrupacion,
			String FechaPrimeraMatriculacion) {
		List<Vehiculo> tipoTramiteVehiculos = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria criteria = session.createCriteria(Vehiculo.class);

			if ((!criterios.getNumColegiado().equalsIgnoreCase(""))
					&& (!"-1".equals(criterios.getNumColegiado()))) {
				criteria.add(Restrictions.eq("id.numColegiado",
						criterios.getNumColegiado()));
			}

			criteria.createAlias("tramiteTrafico", "tramiteTrafico");
			if (!criterios.getFechaPresentacion().isfechaNula()) {
				if (!criterios.getFechaPresentacion().isfechaInicioNula()) {
					criteria.add(Restrictions.ge(
							"tramiteTrafico.fechaPresentacion", criterios
									.getFechaPresentacion().getFechaInicio()));
				}
				if (!criterios.getFechaPresentacion().isfechaFinNula()) {
					Calendar greg = Calendar.getInstance();

					Fecha nFecha = utilesFecha.getFechaFracionada(criterios
							.getFechaPresentacion().getFechaFin());

					greg.set(Integer.parseInt(nFecha.getAnio()),
							Integer.parseInt(nFecha.getMes()) - 1,
							Integer.parseInt(nFecha.getDia()),
							Integer.parseInt("23"), Integer.parseInt("59"),
							Integer.parseInt("59"));

					Timestamp fechaStamp = new Timestamp(greg.getTimeInMillis());
					criteria.add(Restrictions.le(
							"tramiteTrafico.fechaPresentacion",
							utilesFecha.getDate(fechaStamp)));
				}
			}

			if ((!criterios.getTipoTramite().equalsIgnoreCase(""))
					&& (!"-1".equals(criterios.getTipoTramite()))) {
				criteria.add(Restrictions.eq("tramiteTrafico.tipoTramite",
						criterios.getTipoTramite()));
			} else if (criterios.getTipoTramite().toString()
					.equalsIgnoreCase("-1")) {
				criteria.add(Restrictions.in(
						"tramiteTrafico.tipoTramite",
						new String[] {
								TipoTramiteTrafico.TransmisionElectronica
										.getValorEnum(),
								TipoTramiteTrafico.Transmision.getValorEnum() }));
			}

			if (criterios.getEstado()!=null && !criterios.getEstado().toString().equals("")
					&& !criterios.getEstado().toString().equalsIgnoreCase("-1")) {
				criteria.add(Restrictions.eq("tramiteTrafico.estado",
						new BigDecimal(criterios.getEstado())));
			} else {
				criteria.add(Restrictions
						.in("tramiteTrafico.estado",
								new BigDecimal[] {
										new BigDecimal(
												EstadoTramiteTrafico.Finalizado_PDF
														.getValorEnum()),
										new BigDecimal(
												EstadoTramiteTrafico.Finalizado_Telematicamente
														.getValorEnum()),
										new BigDecimal(
												EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
														.getValorEnum()) }));
			}

			if (Agrupacion) {
				ProjectionList projectionList = Projections.projectionList();
				Integer agrupacion = criterios.getAgrupacionVehiculos()
						.intValue();

				switch (agrupacion) {
				case 1:
					if (FechaPrimeraMatriculacion
							.equalsIgnoreCase(ConstantesEstadisticas.FILTRO_IS_NULL)) {
						projectionList.add(Projections.property("matricula"));
						criteria.add(Restrictions.isNull("fechaPrimMatri"));
					} else if (FechaPrimeraMatriculacion
							.equalsIgnoreCase(ConstantesEstadisticas.FILTRO_IS_NOT_NULL)) {
						projectionList.add(Projections.sqlGroupProjection(
								"TO_CHAR(FECHA_PRESENTACION,'YYYY') as total",
								"TO_CHAR(FECHA_PRESENTACION,'YYYY')",
								new String[] { "total" },
								new Type[] { Hibernate.STRING }));

						projectionList.add(Projections.rowCount());
						criteria.add(Restrictions.isNotNull("fechaPrimMatri"));
					} else {
						projectionList.add(Projections.sqlGroupProjection(
								"TO_CHAR(FECHA_PRESENTACION,'YYYY') as total",
								"TO_CHAR(FECHA_PRESENTACION,'YYYY')",
								new String[] { "total" },
								new Type[] { Hibernate.STRING }));

						projectionList.add(Projections.rowCount());
					}
					criteria.setProjection(projectionList);
					break;
				case 2:
					if (FechaPrimeraMatriculacion
							.equalsIgnoreCase(ConstantesEstadisticas.FILTRO_IS_NULL)) {
						projectionList.add(Projections.property("matricula"));
						criteria.add(Restrictions.isNull("fechaPrimMatri"));
					} else if (FechaPrimeraMatriculacion
							.equalsIgnoreCase(ConstantesEstadisticas.FILTRO_IS_NOT_NULL)) {
						projectionList
								.add(Projections
										.sqlGroupProjection(
												"TO_CHAR(FECHA_PRESENTACION,'MM/YYYY') as total",
												"TO_CHAR(FECHA_PRESENTACION,'MM/YYYY')",
												new String[] { "total" },
												new Type[] { Hibernate.STRING }));

						projectionList.add(Projections.rowCount());
						criteria.add(Restrictions.isNotNull("fechaPrimMatri"));
					} else {
						projectionList
								.add(Projections
										.sqlGroupProjection(
												"TO_CHAR(FECHA_PRESENTACION,'MM/YYYY') as total",
												"TO_CHAR(FECHA_PRESENTACION,'MM/YYYY')",
												new String[] { "total" },
												new Type[] { Hibernate.STRING }));

						projectionList.add(Projections.rowCount());
					}
					criteria.setProjection(projectionList);
					break;
				case 3:
					if (FechaPrimeraMatriculacion
							.equalsIgnoreCase(ConstantesEstadisticas.FILTRO_IS_NULL)) {
						projectionList.add(Projections.property("matricula"));
						criteria.add(Restrictions.isNull("fechaPrimMatri"));
					} else if (FechaPrimeraMatriculacion
							.equalsIgnoreCase(ConstantesEstadisticas.FILTRO_IS_NOT_NULL)) {
						projectionList.add(Projections
								.groupProperty("tramiteTrafico.estado"));
						projectionList.add(Projections.rowCount(), "numFilas");
						criteria.add(Restrictions.isNotNull("fechaPrimMatri"));
						criteria.setProjection(projectionList).addOrder(
								Order.desc("numFilas"));
					} else {
						projectionList.add(Projections
								.groupProperty("tramiteTrafico.estado"));
						projectionList.add(Projections.rowCount(), "numFilas");
						criteria.setProjection(projectionList).addOrder(
								Order.desc("numFilas"));
					}
					break;
				case 4:
					if (FechaPrimeraMatriculacion
							.equalsIgnoreCase(ConstantesEstadisticas.FILTRO_IS_NULL)) {
						projectionList.add(Projections.property("matricula"));
						criteria.add(Restrictions.isNull("fechaPrimMatri"));
					} else if (FechaPrimeraMatriculacion
							.equalsIgnoreCase(ConstantesEstadisticas.FILTRO_IS_NOT_NULL)) {
						projectionList.add(Projections
								.groupProperty("tramiteTrafico.tipoTramite"));
						projectionList.add(Projections.rowCount(), "numFilas");
						criteria.add(Restrictions.isNotNull("fechaPrimMatri"));
						criteria.setProjection(projectionList).addOrder(
								Order.desc("numFilas"));
					} else {
						projectionList.add(Projections
								.groupProperty("tramiteTrafico.tipoTramite"));
						projectionList.add(Projections.rowCount(), "numFilas");
						criteria.setProjection(projectionList).addOrder(
								Order.desc("numFilas"));
					}
					break;
				}
			} else {
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections
						.groupProperty("tramiteTrafico.fechaPresentacion"));
				projectionList.add(Projections.rowCount(), "numFilas");
				if (FechaPrimeraMatriculacion
						.equalsIgnoreCase(ConstantesEstadisticas.FILTRO_IS_NOT_NULL))
					criteria.add(Restrictions.isNotNull("fechaPrimMatri"));
				criteria.setProjection(projectionList);
			}

			tipoTramiteVehiculos = criteria.list();
		} finally {
			session.close();
		}
		return tipoTramiteVehiculos;
	}

	@SuppressWarnings("unchecked")
	public String buscaFechaMatriculacion(ConsultaEstadisticasBean criterios) {
		String fechaMatriculacion = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria criteria = session.createCriteria(Vehiculo.class);

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.property("fechaPrimMatri"));
			criteria.setProjection(projectionList);

			if ((criterios.getNumMatricula() != null
					&& !criterios.getNumMatricula().equalsIgnoreCase("")
					&& criterios.getLetraMatricula() != null && !criterios
					.getLetraMatricula().equalsIgnoreCase(""))) {
				criteria.add(Restrictions.eq(
						"matricula",
						criterios.getNumMatricula()
								+ criterios.getLetraMatricula()));
			}

			try {
				List list = criteria.list();
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				fechaMatriculacion = formato.format(list.get(0));
			} catch (Exception e) {
				fechaMatriculacion = null;
			}
		} finally {
			session.close();
		}
		return fechaMatriculacion;
	}

	@SuppressWarnings("unchecked")
	public String buscaRangoFechaMatriculacion(
			ConsultaEstadisticasBean criterios, String informacionDevuelta) {
		String fechaMatriculacion = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query query = session.createQuery("FROM RangoMatricula WHERE "
					+ "'" + criterios.getLetraMatricula().toUpperCase() + "'"
					+ " BETWEEN matricula_let_ini AND matricula_let_fin");
			try {
				List list = query.list();
				Iterator iter = list.iterator();
				RangoMatricula rangoMatricula = (RangoMatricula) iter.next();
				if (informacionDevuelta
						.equalsIgnoreCase(ConstantesEstadisticas.INFO_ANYO))
					fechaMatriculacion = rangoMatricula.getAnyo().toString();
				else if (informacionDevuelta
						.equalsIgnoreCase(ConstantesEstadisticas.INFO_MES_ANYO))
					fechaMatriculacion = rangoMatricula.getMes() + "/"
							+ rangoMatricula.getAnyo();
				if (list.size() > 1) {
					query = session
							.createQuery("FROM RangoMatricula WHERE "
									+ "'"
									+ criterios.getLetraMatricula()
											.toUpperCase()
									+ "'"
									+ " BETWEEN matricula_let_ini AND matricula_let_fin"
									+ " AND (MATRICULA_LET_INI = "
									+ "'"
									+ criterios.getLetraMatricula()
											.toUpperCase()
									+ "'"
									+ " AND MATRICULA_NUM_INI < "
									+ criterios.getNumMatricula()
									+ " ) OR (MATRICULA_LET_FIN = "
									+ "'"
									+ criterios.getLetraMatricula()
											.toUpperCase() + "'"
									+ " AND MATRICULA_NUM_FIN > "
									+ criterios.getNumMatricula() + " ))");
					list = query.list();
					iter = list.iterator();
					rangoMatricula = (RangoMatricula) iter.next();
					if (informacionDevuelta
							.equalsIgnoreCase(ConstantesEstadisticas.INFO_ANYO))
						fechaMatriculacion = rangoMatricula.getAnyo()
								.toString();
					else if (informacionDevuelta
							.equalsIgnoreCase(ConstantesEstadisticas.INFO_MES_ANYO))
						fechaMatriculacion = rangoMatricula.getMes() + "/"
								+ rangoMatricula.getAnyo();
				}
			} catch (Exception e) {
				fechaMatriculacion = null;
			}
		} finally {
			session.close();
		}
		return fechaMatriculacion;
	}

	public List<EvolucionTasa> buscaEvolucionTasa(String numExpediente,
			String codigoTasa) {
		List<EvolucionTasa> listaEvolucionTasa = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria criteria = session.createCriteria(EvolucionTasa.class);

			if ((!numExpediente.equalsIgnoreCase(""))
					&& (!"-1".equals(numExpediente))) {
				criteria.add(Restrictions.eq("numExpediente", new BigDecimal(
						numExpediente)));
			}

			if ((!codigoTasa.equalsIgnoreCase(""))
					&& (!"-1".equals(codigoTasa))) {
				criteria.add(Restrictions.eq("id.codigoTasa", codigoTasa));
			}

			criteria.createAlias("tasa", "tasa");
			criteria.createAlias("usuario", "usuario");

			// ProjectionList projectionList = Projections.projectionList();
			// criteria.setProjection(projectionList).addOrder(
			// Order.desc("id.fechaHora"));
			criteria.addOrder(Order.desc("id.fechaHora"));

			listaEvolucionTasa = criteria.list();
		} finally {
			session.close();
		}
		return listaEvolucionTasa;
	}
}