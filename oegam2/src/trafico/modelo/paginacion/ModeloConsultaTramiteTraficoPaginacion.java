package trafico.modelo.paginacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.enumerados.EstadoPresentacionJPT;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffLiberacionVO;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import general.acciones.AbstractFactoryPaginatedList;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import general.modelo.ModeloSkeletonPaginatedListImpl;
import hibernate.entities.general.Contrato;
import hibernate.entities.tasas.Tasa;
import hibernate.entities.trafico.IntervinienteTrafico;
import hibernate.entities.trafico.TramiteTrafFacturacion;
import hibernate.entities.trafico.TramiteTrafSolInfo;
import hibernate.entities.trafico.Vehiculo;
import trafico.beans.paginacion.ConsultaTramiteTraficoBean;
import trafico.dao.implHibernate.AliasQueryBean;
import trafico.modelo.impl.PaginatedListImplTramiteTrafico;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloConsultaTramiteTraficoPaginacion extends ModeloSkeletonPaginatedListImpl{

	public ModeloConsultaTramiteTraficoPaginacion(AbstractFactoryPaginatedList factoria) {
		super(factoria);
	}

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloConsultaTramiteTraficoPaginacion.class);

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias(BeanCriteriosSkeletonPaginatedList beanCriterios) {
		ConsultaTramiteTraficoBean consultaTramiteTraficoFilterBean = null;
		try{
			consultaTramiteTraficoFilterBean = (ConsultaTramiteTraficoBean) beanCriterios;
		} catch (ClassCastException e) {
			log.error("No se puede convertir el bean");
			return null;
		}
		if (consultaTramiteTraficoFilterBean == null) {
			return null;
		}
		if (consultaTramiteTraficoFilterBean.getTipoTramite() != null && consultaTramiteTraficoFilterBean.getTipoTramite().equals(TipoTramiteTrafico.Solicitud.getValorEnum())) {
			return crearListaAlias(consultaTramiteTraficoFilterBean, true);
		}
		return crearListaAlias(consultaTramiteTraficoFilterBean, false);
	}

	private List<AliasQueryBean> crearListaAlias(ConsultaTramiteTraficoBean consultaTramiteTraficoFilterBean, boolean esTramiteSolicitud) {
		ArrayList<AliasQueryBean> listaAlias = new ArrayList<>();
		if (esTramiteSolicitud){
			listaAlias.add(new AliasQueryBean(TramiteTrafSolInfo.class,"tramiteTrafSolInfo", "tramiteTrafSolInfo", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(Vehiculo.class, "tramiteTrafSolInfo.vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(Tasa.class, "tramiteTrafSolInfo.tasa", "tasa", CriteriaSpecification.LEFT_JOIN));
		} else {
			listaAlias.add(new AliasQueryBean(Vehiculo.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(Tasa.class, "tasa", "tasa", CriteriaSpecification.LEFT_JOIN));
		}
		if (hayTitular(consultaTramiteTraficoFilterBean)){
			listaAlias.add(new AliasQueryBean(IntervinienteTrafico.class, "intervinienteTraficos", "intervinienteTraficos", CriteriaSpecification.LEFT_JOIN));
		}
		return listaAlias;
	}

	@Override
	protected List<Criterion> crearCriteriosBusqueda(BeanCriteriosSkeletonPaginatedList beanCriterios) {
		if (beanCriterios!=null){
			try{
				ConsultaTramiteTraficoBean bean = (ConsultaTramiteTraficoBean) beanCriterios;
				return crearCriterionsBusqueda(bean);
			} catch (ClassCastException c){
				log.error("No se ha podido convertir el beanCriterios");
			}
		}
		return null;
	}

	protected List<Criterion> crearCriterionsBusqueda(ConsultaTramiteTraficoBean consultaTramiteTraficoFilterBean){
		List<Criterion> criterionList= new ArrayList<>();
		if (consultaTramiteTraficoFilterBean == null) {
			return criterionList;
		}
		// Dependiendo de los permisos del usuario, se muestran todos los trámites, o solo los del colegio o contrato.
		if (!getUtilesColegiado().tienePermisoAdmin() && !getUtilesColegiado().tienePermisoEspecial()) {
			if (getUtilesColegiado().tienePermisoColegio()){
//	Esta es la mejor query a realizar, pero como no se está recuperando el colegio del contrato, habría que cambiar como se recupera el colegio.
//	Realmente no se está utilizando esta query, porque en PRO no hay nadie que tenga permiso de colegio y no permiso de Administrador, pero por si acaso, se deja una query que funciona.
//				DetachedCriteria subquery = DetachedCriteria.forClass(Contrato.class)
//					.add(Restrictions.eq("colegio", utilesColegiado.getContrato().getColegio()))
//					.setProjection(Projections.property("idContrato"));
//				criterionList.add(Subqueries.propertyIn("contrato.idContrato", subquery));
				DetachedCriteria subquery2 = DetachedCriteria.forClass(Contrato.class)
					.add(Restrictions.eq("idContrato", getUtilesColegiado().getIdContratoSession()))
					.setProjection(Projections.property("colegio"));	
				DetachedCriteria subquery = DetachedCriteria.forClass(Contrato.class)
					.add(Subqueries.propertyIn("colegio", subquery2))
					.setProjection(Projections.property("idContrato"));
				criterionList.add(Subqueries.propertyIn("contrato.idContrato", subquery));
			} else {
				criterionList.add(Restrictions.eq("contrato.idContrato",getUtilesColegiado().getIdContratoSession()));
			}
		}

		if (consultaTramiteTraficoFilterBean.getEstado() != null && consultaTramiteTraficoFilterBean.getEstado() > 0) {
			if (EstadoTramiteTrafico.Telematicos.getValorEnum().equals(consultaTramiteTraficoFilterBean.getEstado().toString())) {
				criterionList.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()), new BigDecimal(
						EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()) }));
			} else if (EstadoTramiteTrafico.TelematicosYPdf.getValorEnum().equals(consultaTramiteTraficoFilterBean.getEstado().toString())) {
				criterionList.add(Restrictions.in("estado", new BigDecimal[] { new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()), new BigDecimal(
						EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()), new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()), new BigDecimal(
								EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()) }));
			} else {
				criterionList.add(Restrictions.eq("estado", new BigDecimal(consultaTramiteTraficoFilterBean.getEstado())));
			}
		}
		/*} else {
			//Si no se indica estado del trámite, los trámites anulados no se muestran
			criterionList.add(Restrictions.ne("estado", new BigDecimal(EstadoTramiteTrafico.Anulado.getValorEnum())));*/
		//TODO MPC. La llamada a anadirCriterioFecha está en el modelo del paginatedList. Cuando se utilice para esto hay que modificar la llamada
		if (consultaTramiteTraficoFilterBean.getFechaAlta() != null) {
			FechaFraccionada fecha = consultaTramiteTraficoFilterBean.getFechaAlta();
			anadirCriterioFecha(criterionList, fecha, "fechaAlta");
		}
		if (consultaTramiteTraficoFilterBean.getFechaPresentacion() != null) {
			FechaFraccionada fecha = consultaTramiteTraficoFilterBean.getFechaPresentacion();
			anadirCriterioFecha(criterionList, fecha, "fechaPresentacion");
		}
		if (hayTipoTramite(consultaTramiteTraficoFilterBean)){
			criterionList.add(Restrictions.eq("tipoTramite", consultaTramiteTraficoFilterBean.getTipoTramite()));
		}
		if (consultaTramiteTraficoFilterBean.getNumColegiado() != null && !consultaTramiteTraficoFilterBean.getNumColegiado().equals("")) {
			criterionList.add(Restrictions.eq("numColegiado", consultaTramiteTraficoFilterBean.getNumColegiado()));
		}
		if (consultaTramiteTraficoFilterBean.getReferenciaPropia() != null && !consultaTramiteTraficoFilterBean.getReferenciaPropia().equals("")) {
			criterionList.add(Restrictions.ilike("refPropia", "%"+consultaTramiteTraficoFilterBean.getReferenciaPropia()+"%"));
		}
		if (consultaTramiteTraficoFilterBean.getNumExpediente() != null) {
			criterionList.add(Restrictions.eq("numExpediente", consultaTramiteTraficoFilterBean.getNumExpediente().longValue()));
		}
		if (hayTitular(consultaTramiteTraficoFilterBean)) {
			criterionList.add(Restrictions.eq("intervinienteTraficos.id.nif", consultaTramiteTraficoFilterBean.getTitular().getNif().toUpperCase().replaceAll(" ","")));
			if (hayTipoTramite(consultaTramiteTraficoFilterBean)){
				String tipoTramite = consultaTramiteTraficoFilterBean.getTipoTramite();
				if (tipoTramite.equals(TipoTramiteTrafico.Transmision.getValorEnum())|| tipoTramite.equals(TipoTramiteTrafico.TransmisionElectronica.getValorEnum())){
					criterionList.add(Restrictions.eq("intervinienteTraficos.tipoIntervinienteBean.tipoInterviniente", TipoInterviniente.Adquiriente.getValorEnum()));
				} else if (tipoTramite.equals(TipoTramiteTrafico.Solicitud.getValorEnum())){
					criterionList.add(Restrictions.eq("intervinienteTraficos.tipoIntervinienteBean.tipoInterviniente", TipoInterviniente.Solicitante.getValorEnum()));
				} else {
					criterionList.add(Restrictions.eq("intervinienteTraficos.tipoIntervinienteBean.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()));
				}
			} else {
				Criterion cTransmision = Restrictions.and(Restrictions.in("tipoTramite", new String[]{TipoTramiteTrafico.Transmision.getValorEnum(), TipoTramiteTrafico.TransmisionElectronica.getValorEnum()}),	Restrictions.eq("intervinienteTraficos.tipoIntervinienteBean.tipoInterviniente", TipoInterviniente.Adquiriente.getValorEnum()));
				Criterion cSolicitud = Restrictions.and(Restrictions.eq("tipoTramite",TipoTramiteTrafico.Solicitud.getValorEnum()),	Restrictions.eq("intervinienteTraficos.tipoIntervinienteBean.tipoInterviniente", TipoInterviniente.Solicitante.getValorEnum()));
				Criterion cOtros = Restrictions.and(Restrictions.not(Restrictions.in("tipoTramite", new String[]{TipoTramiteTrafico.Transmision.getValorEnum(), TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), TipoTramiteTrafico.Solicitud.getValorEnum()})), Restrictions.eq("intervinienteTraficos.tipoIntervinienteBean.tipoInterviniente", TipoInterviniente.Titular.getValorEnum()));
				criterionList.add(Restrictions.or(Restrictions.or(cTransmision, cSolicitud),cOtros));
			}
		}
		if (hayNifFacturacion(consultaTramiteTraficoFilterBean)){
			DetachedCriteria subquery = DetachedCriteria.forClass(TramiteTrafFacturacion.class)
				.add(Restrictions.eq("nif", consultaTramiteTraficoFilterBean.getNifFacturacion().toUpperCase().replaceAll(" ","")))
				.setProjection(Projections.property("id.numExpediente"));
			criterionList.add(Subqueries.propertyIn("numExpediente", subquery));
		}
		if (consultaTramiteTraficoFilterBean.getNumBastidor() != null && !consultaTramiteTraficoFilterBean.getNumBastidor().equals("")) {
			if (consultaTramiteTraficoFilterBean.getNumBastidor().length() >= 17) {
				criterionList.add(Restrictions.eq("vehiculo.bastidor", consultaTramiteTraficoFilterBean.getNumBastidor().toUpperCase()));
			} else {
				criterionList.add(Restrictions.like("vehiculo.bastidor", consultaTramiteTraficoFilterBean.getNumBastidor().toUpperCase()+"%"));
			}
		}
		if (consultaTramiteTraficoFilterBean.getMatricula()!=null && !consultaTramiteTraficoFilterBean.getMatricula().equals("")) {
			criterionList.add(Restrictions.eq("vehiculo.matricula", consultaTramiteTraficoFilterBean.getMatricula().toUpperCase().replaceAll(" ","")));
		}
		if (consultaTramiteTraficoFilterBean.getNive()!=null && !consultaTramiteTraficoFilterBean.getNive().equals("")) {
			criterionList.add(Restrictions.ilike("vehiculo.nive", "%"+consultaTramiteTraficoFilterBean.getNive()+"%"));
		}
		if (consultaTramiteTraficoFilterBean.getTipoTasa()!=null && !consultaTramiteTraficoFilterBean.getTipoTasa().equals("")) {
			criterionList.add(Restrictions.eq("tasa.tipoTasa", consultaTramiteTraficoFilterBean.getTipoTasa()));
		}
		if (consultaTramiteTraficoFilterBean.getPresentadoJPT()!=null){
			if (consultaTramiteTraficoFilterBean.getPresentadoJPT().equals(EstadoPresentacionJPT.No_Presentado.getValorEnum())){
				criterionList.add(Restrictions.or(Restrictions.isNull("presentadoJpt"), Restrictions.eq("presentadoJpt", consultaTramiteTraficoFilterBean.getPresentadoJPT())));
			} else {
				criterionList.add(Restrictions.eq("presentadoJpt", consultaTramiteTraficoFilterBean.getPresentadoJPT()));
			}
		}
		if (consultaTramiteTraficoFilterBean.getEsLiberableEEFF()!=null){
			DetachedCriteria subquery = null;
			if (consultaTramiteTraficoFilterBean.getEsLiberableEEFF().equals(EstadoPresentacionJPT.No_Presentado.getValorEnum())){
				subquery = DetachedCriteria.forClass(EeffLiberacionVO.class)
						.add(Restrictions.or(Restrictions.eq("realizado", true), Restrictions.eq("exento", true)))
						.setProjection(Projections.property("numExpediente"));	
			} else {
				subquery = DetachedCriteria.forClass(EeffLiberacionVO.class)
						.add(Restrictions.and(Restrictions.eq("realizado", false), Restrictions.eq("exento", false)))
						.setProjection(Projections.property("numExpediente"));	
			}
			criterionList.add(Subqueries.propertyIn("numExpediente", subquery));
		}
		if (consultaTramiteTraficoFilterBean.getConNive() != null){
			if (EstadoPresentacionJPT.No_Presentado.getValorEnum().equals(consultaTramiteTraficoFilterBean.getConNive())){
				criterionList.add(Restrictions.isNull("vehiculo.nive"));
			} else if (EstadoPresentacionJPT.Presentado.getValorEnum().equals(consultaTramiteTraficoFilterBean.getConNive())){
				criterionList.add(Restrictions.isNotNull("vehiculo.nive"));
			}
		}

		// Jefatura provincial
		if(consultaTramiteTraficoFilterBean.getJefaturaProvincial() != null && !consultaTramiteTraficoFilterBean.getJefaturaProvincial().equals("")){
			criterionList.add(Restrictions.eq("jefaturaTrafico.jefaturaProvincial", consultaTramiteTraficoFilterBean.getJefaturaProvincial()));
		}
		// Filtro para eliminar las solicitudes inteve3 de la lista de tramites
		criterionList.add(Restrictions.not(Restrictions.in("tipoTramite", new String[]{TipoTramiteTrafico.Solicitud_Inteve.getValorEnum(), TipoTramiteTrafico.Inteve.getValorEnum()})));

		return criterionList;
	}

	private boolean hayTitular(ConsultaTramiteTraficoBean consultaTramiteTraficoFilterBean) {
		return consultaTramiteTraficoFilterBean.getTitular() != null && consultaTramiteTraficoFilterBean.getTitular().getNif() != null && !consultaTramiteTraficoFilterBean.getTitular().getNif().equals("");
	}

	private boolean hayTipoTramite(ConsultaTramiteTraficoBean consultaTramiteTraficoFilterBean) {
		return consultaTramiteTraficoFilterBean.getTipoTramite() != null && !consultaTramiteTraficoFilterBean.getTipoTramite().equals("") && !consultaTramiteTraficoFilterBean.getTipoTramite().equals("-1");
	}

	private boolean hayNifFacturacion(ConsultaTramiteTraficoBean consultaTramiteTraficoFilterBean) {
		return consultaTramiteTraficoFilterBean.getNifFacturacion() != null && !consultaTramiteTraficoFilterBean.getNifFacturacion().equals("");
	}

	@Override
	// La consulta de trámites de tráfico es especial, y el paginatedList también, ya que hay que tratar el caso de solicitudes. Por eso se sobreescribe este método.
	protected PaginatedList getPaginatedList(Object entity, int resPag, int page, List<Criterion> criterion, String dir, String sort, List<AliasQueryBean> listaAlias, ProjectionList listaProyecciones, BeanResultTransformPaginatedList resultTransform) {
		return new PaginatedListImplTramiteTrafico(getConsultaInicial(), entity,resPag,page,criterion,dir, sort,listaAlias,listaProyecciones, resultTransform);
	}

	private UtilesColegiado getUtilesColegiado() {
		return ContextoSpring.getInstance().getBean(UtilesColegiado.class);
	}

}