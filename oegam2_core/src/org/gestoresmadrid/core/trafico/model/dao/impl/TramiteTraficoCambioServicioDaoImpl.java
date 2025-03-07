package org.gestoresmadrid.core.trafico.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoCambioServicioDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafCambioServicioVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;

@Repository
public class TramiteTraficoCambioServicioDaoImpl extends GenericDaoImplHibernate<TramiteTrafCambioServicioVO> implements TramiteTraficoCambioServicioDao {

	private static final long serialVersionUID = 1649450229789176436L;

	private static final String horaFinDia = "23:59";
	private static final int N_SEGUNDOS_59 = 59;
	private static final int N_MILISEGUNDOS_999 = 999;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public TramiteTrafCambioServicioVO getTramiteCambServ(BigDecimal numExpediente, boolean tramiteCompleto) {
		List<Criterion> listCriterion = new ArrayList<>();
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		String[] listaFetchModeJoins = null;
		if (numExpediente != null) {
			listCriterion.add(Restrictions.eq("numExpediente", numExpediente));
		}

		if (tramiteCompleto) {
			listaAlias.add(new AliasQueryBean(VehiculoVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(ContratoVO.class, "contrato", "contrato", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(DireccionVO.class, "vehiculo.direccion", "vehiculo.direccion", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(JefaturaTraficoVO.class, "jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(PersonaVO.class, "intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(DireccionVO.class, "intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(TasaVO.class, "tasa", "tasa", CriteriaSpecification.LEFT_JOIN));

			List<String> listFetchModeJoins = new ArrayList<>();
			listFetchModeJoins.add("intervinienteTraficos");

			listaFetchModeJoins = listFetchModeJoins.toArray(new String[0]);
		}

		List<TramiteTrafCambioServicioVO> lista = buscarPorCriteria(-1, -1, listCriterion, null, null, listaAlias, null, null, listaFetchModeJoins);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<TramiteTrafCambioServicioVO> cambioServicioExcel(String jefatura) throws Exception {
		Criteria criteria = getCurrentSession().createCriteria(TramiteTrafCambioServicioVO.class);

		Date fechaHasta = new Date();
		utilesFecha.setHoraEnDate(fechaHasta, horaFinDia);
		utilesFecha.setSegundosEnDate(fechaHasta, N_SEGUNDOS_59);
		utilesFecha.setMilisegundosEnDate(fechaHasta, N_MILISEGUNDOS_999);

		criteria.add(Restrictions.le("fechaPresentacion", fechaHasta));
		criteria.add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.CambioServicio.getValorEnum()));
		criteria.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio_Excel.getValorEnum())));
		criteria.add(Restrictions.eq("jefaturaTrafico.jefaturaProvincial", jefatura));

		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("jefaturaTrafico", "jefaturaTrafico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("tasa", "tasa", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("intervinienteTraficos.direccion", "intervinienteTraficos.direccion", CriteriaSpecification.LEFT_JOIN);

		criteria.setFetchMode("intervinienteTraficos", FetchMode.JOIN);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		List<TramiteTrafCambioServicioVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		String textNumExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFinDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS_59);
		textNumExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2);

		Criteria criteria = getCurrentSession().createCriteria(RemesaVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaAlta", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("numExpediente"));

		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(textNumExpediente + "00000");
		}
		return new BigDecimal(maximoExistente.longValue() + 1);
	}
}