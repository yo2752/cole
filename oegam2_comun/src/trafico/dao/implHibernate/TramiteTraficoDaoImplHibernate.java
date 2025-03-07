package trafico.dao.implHibernate;

import hibernate.entities.trafico.TramiteTrafico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import trafico.beans.resultTransformer.ConsultaTramiteTraficoNumExpEstadoResultadoBean;
import trafico.dao.TramiteTraficoDaoInterfaz;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class TramiteTraficoDaoImplHibernate extends GenericDaoImplHibernate<TramiteTrafico> implements TramiteTraficoDaoInterfaz {

	private static final ILoggerOegam log = LoggerOegam.getLogger(TramiteTraficoDaoImplHibernate.class);

	public TramiteTraficoDaoImplHibernate(){
		super(new TramiteTrafico());
	}

	@Override
	public BigDecimal guardar(TramiteTrafico tramiteTrafico) {
		return (BigDecimal) guardar(tramiteTrafico);
	}

	@Override
	public List noExisteNumExpedienteEstados(Long[] numExpedientes, List<BigDecimal> estadosCorrectos) {
		List lista = null;
		ProjectionList listaProyecciones = null;
		ArrayList<AliasQueryBean> listaAlias = new ArrayList<>();
		List<Criterion> criterionList= new ArrayList<>();

		for (BigDecimal estado : estadosCorrectos) {
			criterionList.add(Restrictions.ne("estado", estado));
		}

		criterionList.add(Restrictions.in("numExpediente", numExpedientes));

		ConsultaTramiteTraficoNumExpEstadoResultadoBean consultaTramiteTraficoNumExpEstadoResultadoBean = new ConsultaTramiteTraficoNumExpEstadoResultadoBean();
		listaProyecciones = crearListaProyecciones(consultaTramiteTraficoNumExpEstadoResultadoBean);
		lista = buscarPorCriteria(-1, -1, criterionList, null, null, listaAlias, listaProyecciones, consultaTramiteTraficoNumExpEstadoResultadoBean);

		return lista;
	}

	@Override
	public List existeNumExpedienteEstados(Long[] numExpedientes, List<BigDecimal> estadosCorrectos) {
		List lista = null;
		ProjectionList listaProyecciones = null;
		ArrayList<AliasQueryBean> listaAlias = new ArrayList<>();
		List<Criterion> criterionList= new ArrayList<>();

		criterionList.add(Restrictions.in("estado", estadosCorrectos));
		criterionList.add(Restrictions.in("numExpediente", numExpedientes));

		ConsultaTramiteTraficoNumExpEstadoResultadoBean consultaTramiteTraficoNumExpEstadoResultadoBean = new ConsultaTramiteTraficoNumExpEstadoResultadoBean();
		listaProyecciones = crearListaProyecciones(consultaTramiteTraficoNumExpEstadoResultadoBean);

		lista = buscarPorCriteria(-1, -1, criterionList, null, null, listaAlias, listaProyecciones, consultaTramiteTraficoNumExpEstadoResultadoBean);

		return lista;
	}

	private ProjectionList crearListaProyecciones(ConsultaTramiteTraficoNumExpEstadoResultadoBean consultaTramiteTraficoNumExpEstadoResultadoBean){
		Vector<String> proyecciones = consultaTramiteTraficoNumExpEstadoResultadoBean.crearProyecciones();
		return crearListaProyecciones(proyecciones);
	}

	private ProjectionList crearListaProyecciones(Vector<String> proyecciones){
		ProjectionList lista = Projections.projectionList();
		for (String campo: proyecciones){
			lista.add(Projections.property(campo));
		}
		return lista;
	}
}