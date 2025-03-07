package facturacion.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.gestoresmadrid.core.hibernate.util.HibernateBean;
import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import facturacion.beans.ConsultaFacturaBean;
import hibernate.entities.facturacion.Factura;
import hibernate.entities.facturacion.FacturaColegiadoConcepto;
import hibernate.entities.facturacion.FacturaConcepto;
import hibernate.entities.facturacion.FacturaPK;
import hibernate.entities.general.Colegiado;
import hibernate.entities.personas.Direccion;
import hibernate.entities.personas.EvolucionPersona;
import hibernate.entities.personas.Persona;
import hibernate.entities.personas.PersonaDireccion;
import hibernate.entities.personas.PersonaDireccionPK;
import hibernate.entities.personas.PersonaPK;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class DatosDAO extends HibernateUtil {

	// log
	private static final ILoggerOegam log = LoggerOegam.getLogger(DatosDAO.class);

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public DatosDAO() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public ResultBean add(Factura datos) {
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		session.save(datos);
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);
		} catch (Exception e) {
			System.err.println("1.- Error: " + e);
			log.error("Error al insertar Factura: " + e);
		}
		session.close();
		return resultado;
	}
	
	public ResultBean addDireccion(Direccion datos) {
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		session.saveOrUpdate(datos);
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);
		} catch (Exception e) {
			System.err.println("1.- Error: " + e);
			log.error("Error al insertar Factura: " + e);
		}
		session.close();
		return resultado;
	}
	
	public ResultBean saveOrUpdatePersona(Persona datos) {
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		session.saveOrUpdate(datos);
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);
		} catch (Exception e) {
			System.err.println("1.- Error: " + e);
			log.error("Error al insertar Factura: " + e);
		}
		session.close();
		return resultado;
	}
	
	public ResultBean addEvolucionPersona(EvolucionPersona datos) {
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		session.saveOrUpdate(datos);
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);
		} catch (Exception e) {
			System.err.println("1.- Error: " + e);
			log.error("Error al insertar Factura: " + e);
		}
		session.close();
		return resultado;
	}
	
	public ResultBean addPersonaDireccion(PersonaDireccion datos) {
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		session.saveOrUpdate(datos);
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);
		} catch (Exception e) {
			System.err.println("1.- Error: " + e);
			log.error("Error al insertar Factura: " + e);
		}
		session.close();
		return resultado;
	}
	
	public ResultBean saveColegiadoConcepto(String numColegiado, String descripcionConcepto){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		ResultBean resultado = new ResultBean();
		FacturaColegiadoConcepto concepto = new FacturaColegiadoConcepto();
		
		concepto.setNumColegiado(numColegiado);
		concepto.setNombreColegiadoConcepto(descripcionConcepto.toUpperCase());
		session.saveOrUpdate(concepto);
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);
		} catch (Exception e) {
			resultado.setMensaje("Error al insertar Factura Colegiado Concepto: " + e.getCause().toString());
			log.error("Error al insertar Factura Colegiado Concepto: " + e);
		}
		
		session.close();
		return resultado;
	}

	public ResultBean updateColegiadoConcepto(String numColegiado, String descripcionConcepto, String idConcepto){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		ResultBean resultado = new ResultBean();
		FacturaColegiadoConcepto concepto = new FacturaColegiadoConcepto();
		
		concepto.setNumColegiado(numColegiado);
		concepto.setNombreColegiadoConcepto(descripcionConcepto);
		concepto.setIdColegiadoConcepto(Long.parseLong(idConcepto));
		session.saveOrUpdate(concepto);
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);
		} catch (Exception e) {
			resultado.setMensaje("Error al insertar Factura Colegiado Concepto: " + e.getCause().toString());
			log.error("Error al insertar Factura Colegiado Concepto: " + e);
		}
		
		session.close();
		return resultado;
	}
	public ResultBean addColegiadoConcepto(List<FacturaColegiadoConcepto> lFacturaColegiadoConcepto)
			throws Exception {		
		FacturaColegiadoConcepto datos = new FacturaColegiadoConcepto();
		ResultBean resultado = new ResultBean();
		int tam = lFacturaColegiadoConcepto.size();
		resultado.setError(Boolean.FALSE);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		 
		for (int i=0; i < tam; i++) {
			datos = new FacturaColegiadoConcepto();
			tx = session.beginTransaction();
			datos = lFacturaColegiadoConcepto.get(i);
			session.saveOrUpdate(datos);
			// session.flush();
			try {
				resultado = ejecutarTransacciones(tx, resultado);
			} catch (Exception e) {
				resultado.setMensaje("Error al insertar Factura Colegiado Concepto: " + e.getCause().toString());
				log.error("Error al insertar Factura Colegiado Concepto: " + e);
			}
		}
		session.close();
		return resultado;
	}
	
	public List<String> guardarClaveColegiado(Colegiado colegiado, List<String> mensajeSQL) throws Exception, OegamExcepcion {
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		session.saveOrUpdate(colegiado);
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);				
			mensajeSQL.add("Se ha actualiza la clave correctamente.");
		} catch (Exception e) {
			System.err.println("7.- Error: " + e);
			log.error("Error al actualizar Factura: " + e);
		}
		return mensajeSQL;
		// session.close();
	}
	
	public List<String> listaExpedientes(String numFactura) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Criteria criteria = session.createCriteria(FacturaConcepto.class);		
		List<String> listaExpedientes = null;	

		
		if ((!numFactura.equalsIgnoreCase("")) && (!"-1".equals(numFactura))) {
			criteria.add(Restrictions.eq("numFactura", numFactura));
		}			
		
		criteria.add(Restrictions.ne("numExpediente", "-1"));
	
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.distinct(Projections.property("numExpediente")));
		criteria.setProjection(projectionList);
		
		listaExpedientes = criteria.list();
		
		session.close();
		return listaExpedientes;
	}

	
	public ResultBean saveOrUpdate(Factura datos) throws Exception {
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);
		
		Session session = HibernateUtil.getSessionFactory().openSession(); 
		Transaction tx = session.beginTransaction();

		//JRG: Hay que arreglar esto. Es cuando se crea la factura cuando hay que dar valor a otroCheckDescuento.
		if (datos.getFacturaOtro().getOtroCheckDescuento() == null)
			datos.getFacturaOtro().setOtroCheckDescuento(new BigDecimal(0));
		///////////
		try {
			session.saveOrUpdate(datos);
		} catch (Exception e1) {
			System.err.println("3.- Error: " + e1);
			log.error("Error al actualizar Factura: " + e1);
		}
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);
		} catch (Exception e) {
			System.err.println("3.- Error: " + e);
			log.error("Error al actualizar Factura: " + e);
		}
		session.close();
		return resultado;
	}

	public void deleteSuplido(long idConcepto) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		try {
			String hql = "DELETE FROM Factura_Suplido where id_concepto in ('"
					+ idConcepto + "')";
			Query query = session.createQuery(hql);
			int row = query.executeUpdate();

			if (row == 0) {
				log.error("Doesn't deleted any row!");
			} else {
				log.error("Deleted Row: " + row);
			}
		} catch (Exception e) {
			log.error("Doesn't deleted any row!");
		}

		session.close();
	}

	public ResultBean borrarFactura(FacturaPK id) throws Exception {
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Factura factura = (Factura) session.createCriteria(Factura.class).add(
				Restrictions.eq("id", id)).uniqueResult();
		factura.setVisible(new BigDecimal(0));
		Transaction tx = session.beginTransaction();

		session.update(factura);
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);
		} catch (Exception e) {
			System.err.println("6- Error: " + e);
			log.error("Error al borrar Factura: " + e);
		}
		// session.close();
		return resultado;
	}

	// DRC@13-08-2012 Este metodo anula la factura seleccionada, para
	// posteriormente crear una rectificativa
	public ResultBean anularFactura(FacturaPK id, String estado) throws Exception {
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Factura factura = (Factura) session.createCriteria(Factura.class).add(Restrictions.eq("id", id)).uniqueResult();
		factura.setCheckPdf(estado);
		Transaction tx = session.beginTransaction();

		session.update(factura);
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);
		} catch (Exception e) {
			System.err.println("6- Error: " + e);
			log.error("Error al borrar Factura: " + e);
		}
		session.close();
		return resultado;
	}
	
	// JMC @13-11-2012 Este metodo anula la persona que tenia la factura anteriormente ya que se ha modificado
	public ResultBean anularPersonaDireccion(String nif, String numColegiado, long idDireccion) throws Exception {
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Calendar calendar =  Calendar.getInstance();
		Fecha nFecha = utilesFecha.getFechaActual();		
    	
		calendar.set(Integer.parseInt(nFecha.getAnio()), 
    			Integer.parseInt(nFecha.getMes())-1, 
    			Integer.parseInt(nFecha.getDia()),
    			Integer.parseInt("00"),
    			Integer.parseInt("00"),
    			Integer.parseInt("00"));    	
    			    	
    	Timestamp fechaStampIni = new Timestamp(calendar.getTimeInMillis());
		
    	PersonaDireccionPK id = new PersonaDireccionPK();
		id.setNif(nif);
		id.setNumColegiado(numColegiado);
		id.setIdDireccion(idDireccion);
    	
		PersonaDireccion persDir = (PersonaDireccion) session.createCriteria(PersonaDireccion.class).add(Restrictions.eq("id", id)).uniqueResult();
		persDir.setFechaFin(utilesFecha.getDate(fechaStampIni));
		Transaction tx = session.beginTransaction();

		session.update(persDir);
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);
		} catch (Exception e) {
			System.err.println("6- Error: " + e);
			log.error("Error al borrar Factura: " + e);
		}
		session.close();
		return resultado;
	}

	public ResultBean checkPDF(FacturaPK id, String estado) throws Exception {
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Factura factura = (Factura) session.createCriteria(Factura.class).add(
				Restrictions.eq("id", id)).uniqueResult();
		factura.setCheckPdf(estado);
		Transaction tx = session.beginTransaction();
		session.update(factura);
		session.flush();
		try {
			resultado = ejecutarTransacciones(tx, resultado);
		} catch (Exception e) {
			System.err.println("7.- Error: " + e);
			log.error("Error al actualizar Factura: " + e);
		}
		return resultado;
		// session.close();
	}

	// JMC@07-08-2012 Buscar la siguiente numeracion para un num serie, numero de codigo y fecha
	public int buscaMaxFactura(String numCodigo) {
		Session session = HibernateUtil.getSessionFactory().openSession();		
		Criteria criteria = session.createCriteria(Factura.class);		
		//criteria.add(Restrictions.eq("numSerie", numSerie));
		criteria.add(Restrictions.eq("numCodigo", numCodigo));
		
		Calendar gregInicio =  Calendar.getInstance();
		Calendar gregFin =  Calendar.getInstance();
		
		Fecha nFecha = utilesFecha.getFechaActual();		
    	
		gregInicio.set(Integer.parseInt(nFecha.getAnio()), 
    			Integer.parseInt("00"), 
    			Integer.parseInt("01"),
    			Integer.parseInt("00"),
    			Integer.parseInt("00"),
    			Integer.parseInt("00"));
		
		gregFin.set(Integer.parseInt(nFecha.getAnio())+1, 
    			Integer.parseInt("00"), 
    			Integer.parseInt("01"),
    			Integer.parseInt("00"),
    			Integer.parseInt("00"),
    			Integer.parseInt("00"));    	
    			    	
    	Timestamp fechaStampIni = new Timestamp(gregInicio.getTimeInMillis());
    	Timestamp fechaStampFin = new Timestamp(gregFin.getTimeInMillis());
    	
		criteria.add(Restrictions.ge("fechaAlta", utilesFecha.getDate(fechaStampIni)));
		criteria.add(Restrictions.lt("fechaAlta", utilesFecha.getDate(fechaStampFin)));
			
		//Number numeroFacturas = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		Number numeroFacturas = (Number) criteria.setProjection( Projections.max("numeracion")).uniqueResult();
		
		if (numeroFacturas != null){
			return numeroFacturas.intValue();
		} else{
			return 0;
		}
		
		
	}
	
	// JMC@07-08-2012 Buscar la Persona asociada al tramite
	public Persona buscarPersona(String nif, String numColegiado) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();	
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Persona.class);	
		PersonaPK id = new PersonaPK();
		id.setNif(nif);
		id.setNumColegiado(numColegiado);
		criteria.add(Restrictions.eq("id.nif", nif));		
		criteria.add(Restrictions.eq("id.numColegiado", numColegiado));
			
		Persona pers = null;
		try {
			pers = (Persona) criteria.uniqueResult();			
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			log.error("Se ha producido un error al buscar una persona asociada al tramite. ", e);
		}
		session.close();
		return pers;
		
		
	}
	
	// JMC@07-08-2012 Buscar la direccion Actual de la persona asociada al tramite
	public Direccion buscarDireccionActual(String nif, String numColegiado) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();	
		session.beginTransaction();
		Criteria criteria = session.createCriteria(PersonaDireccion.class);	
		PersonaDireccionPK id = new PersonaDireccionPK();
		id.setNif(nif);
		id.setNumColegiado(numColegiado);
		criteria.add(Restrictions.eq("id.nif", nif));		
		criteria.add(Restrictions.eq("id.numColegiado", numColegiado));
		criteria.add(Restrictions.isNull("fechaFin"));
			
		PersonaDireccion persDir = null;
		try {
			persDir = (PersonaDireccion) criteria.uniqueResult();			
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			log.error("Se ha producido un error al buscar una persona una direccion actual de una persona "
					+ "asociada al tramite.", e);
		}
		session.close();
		return persDir!=null?persDir.getDireccion():null;
		
		
	}

	// DRC@07-08-2012 Buscar en siguiente numero de serie, para las facturas
	@SuppressWarnings("unchecked")
	public String checkNumFactura(String numFactura) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		FacturaPK id = new FacturaPK();
		id.setNumFactura(numFactura);
		Criteria criteria = session.createCriteria(Factura.class);
		criteria.add(Restrictions.like("id.numFactura", numFactura));

		Number numeroFacturas = (Number) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		if (numeroFacturas == null || numeroFacturas.intValue() == 0)
			return "OK";
		else
			return null;
	}

	// DRC@07-08-2012 Buscar en siguiente numero de serie, para las facturas
	// Rectificativas
	public int buscaNumFacturaRectificativa(String numFactura, String tipo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		FacturaPK id = new FacturaPK();
		id.setNumFactura(numFactura);
		Criteria criteria = session.createCriteria(Factura.class);
		criteria.add(Restrictions.like("id.numFactura", numFactura));
//		criteria.add(Restrictions.eq("checkPdf", estado));
		criteria.add(Restrictions.eq("numSerie", tipo));
		Number numeroFacturas = (Number) criteria.setProjection(
				Projections.rowCount()).uniqueResult();

		session.close();
		return numeroFacturas.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Factura> listaFacturas(ConsultaFacturaBean criterios,
			String estado, String tipo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Factura.class);
		FacturaPK id = new FacturaPK();
		List<Factura> facturas = null;
		
		if (criterios.getNumFactura()!=null && !criterios.getNumFactura().equals("")){
			criteria.add(Restrictions.eq("id.numFactura", criterios.getNumFactura()));
		}
		if (criterios.getNumColegiado()!=null && !criterios.getNumColegiado().equals("")){
			criteria.add(Restrictions.eq("id.numColegiado", criterios.getNumColegiado()));
		}
		
		criteria.add(Restrictions.ne("visible", new BigDecimal(0)));
		if (tipo != null) {
			if (tipo.equalsIgnoreCase("Factura Rectificativa"))
				criteria.add(Restrictions.eq("checkPdf", estado));
			else if (tipo.equalsIgnoreCase("Factura Generada"))
				criteria.add(Restrictions.ne("checkPdf", estado));
			else if (tipo.equalsIgnoreCase("factura abono"))
				criteria.add(Restrictions.eq("checkPdf", estado));
		}
		if (!criterios.getFecha().isfechaNula()) {
			if (!criterios.getFecha().isfechaInicioNula()) {
				criteria.add(Restrictions.ge("fechaFactura", criterios
						.getFecha().getFechaInicio()));
			}
			if (!criterios.getFecha().isfechaFinNula()) {
				criteria.add(Restrictions.le("fechaFactura", criterios
						.getFecha().getFechaFin()));
			}
		}
		facturas = criteria.list();
		
		return facturas;
	}

	@SuppressWarnings("unchecked")
	public List<FacturaColegiadoConcepto> listaConceptos(String numColegiado) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(FacturaColegiadoConcepto.class);
		List<FacturaColegiadoConcepto> facturaColegiadoConcepto = null;

		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		
		facturaColegiadoConcepto = criteria.list();		
		return facturaColegiadoConcepto;
	}
	
	public List<FacturaColegiadoConcepto> lRecuperarConceptoColegiado (String numColegiado) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<FacturaColegiadoConcepto> facturaColegiadoConcepto = null;
		
		Criteria criteria = session.createCriteria(FacturaColegiadoConcepto.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.addOrder(Order.asc("idColegiadoConcepto"));
		
		facturaColegiadoConcepto = criteria.list();
		return facturaColegiadoConcepto;
	}
	
	public FacturaConcepto recuperarConcepto (long idConcepto) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		FacturaConcepto facturaConcepto = null;
		
		Criteria criteria = session.createCriteria(FacturaConcepto.class);
		criteria.add(Restrictions.eq("idConcepto", idConcepto));
		
		
		facturaConcepto = (FacturaConcepto) criteria.list();
		return facturaConcepto;
	}
	
	@SuppressWarnings("unchecked")
	public String getClaveColegiado(String numColegiado) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Colegiado colegiado = null;
		
		Criteria criteria = session.createCriteria(Colegiado.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));

		colegiado = (Colegiado) criteria.uniqueResult();
		
		return colegiado.getClaveFacturacion();
	}

	@SuppressWarnings("unchecked")
	public List<Factura> listaFacturasPDF(ConsultaFacturaBean criterios,
			String estado, String tipo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Factura> facturas = null;

		FacturaPK id = new FacturaPK();
		id.setNumFactura(criterios.getNumFactura());
		Criteria criteria = session.createCriteria(Factura.class);

		criteria.add(Restrictions.like("id.numFactura", criterios
				.getNumFactura()));
		criteria.add(Restrictions.like("id.numColegiado", criterios
				.getNumColegiado()));
		criteria.add(Restrictions.ne("visible", new BigDecimal(0)));
		if (tipo != null) {
			if (tipo.equalsIgnoreCase("Factura Rectificativa"))
				criteria.add(Restrictions.eq("checkPdf", estado));
			else if (tipo.equalsIgnoreCase("Factura Generada"))
				criteria.add(Restrictions.ne("checkPdf", estado));
		}
		if (!criterios.getFecha().isfechaNula()) {
			if (!criterios.getFecha().isfechaInicioNula()) {
				criteria.add(Restrictions.ge("fechaFactura", criterios
						.getFecha().getFechaInicio()));
			}
			if (!criterios.getFecha().isfechaFinNula()) {
				criteria.add(Restrictions.le("fechaFactura", criterios
						.getFecha().getFechaFin()));
			}
		}

		criteria.addOrder(Order.desc("id.numFactura"));
		facturas = criteria.list();

		return facturas;
	}

	@SuppressWarnings("unchecked")
	public List<Factura> buscaFactura(ConsultaFacturaBean criterios,
			String estado) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Criteria criteria = session.createCriteria(Factura.class);
		criteria.createAlias("persona","persona");
		FacturaPK id = new FacturaPK();
		PersonaPK idP = new PersonaPK();
	
		List<Factura> facturas = null;
		if (criterios != null) {
			criteria.add(Restrictions.eq("visible", new BigDecimal(1)));

			if (!criterios.getCheckPDF().equalsIgnoreCase("-1"))
				criteria.add(Restrictions.eq("checkPdf", criterios.getCheckPDF()));

			if (!criterios.getNumFactura().equalsIgnoreCase("")){
				id.setNumFactura(criterios.getNumFactura());
				criteria.add(Restrictions.like("id.numFactura", '%' + criterios 
						.getNumFactura().trim() + '%'));
			}
			if (!utilesColegiado.tienePermisoAdmin()) {
				id.setNumColegiado(criterios.getNumCodigo());
				criteria.add(Restrictions.eq("id.numColegiado", utilesColegiado
						.getNumColegiadoSession()));
			} else {
				if (!criterios.getNumColegiado().equalsIgnoreCase("")) {
					id.setNumColegiado(criterios.getNumCodigo());
					criteria.add(Restrictions.eq("id.numColegiado", criterios
							.getNumColegiado()));
				}
			}
			if (!criterios.getNif().equalsIgnoreCase("")){
				criteria.add(Restrictions.eq("persona.id.nif", criterios
						.getNif()));
			}
			if (!criterios.getNumSerie().equalsIgnoreCase("-1"))
				criteria.add(Restrictions.like("numSerie", criterios
						.getNumSerie()
						+ "%"));

			if (!criterios.getNumCodigo().equalsIgnoreCase(""))
				criteria.add(Restrictions.eq("numCodigo", criterios
						.getNumCodigo()));

			if (!criterios.getNumExpediente().equalsIgnoreCase(""))
				criteria.add(Restrictions.eq("numExpediente", criterios
						.getNumExpediente()));

			if (!criterios.getFecha().isfechaNula()) {
				if (!criterios.getFecha().isfechaInicioNula()) {
					criteria.add(Restrictions.ge("fechaFactura", criterios
							.getFecha().getFechaInicio()));
				}
				if (!criterios.getFecha().isfechaFinNula()) {
					criteria.add(Restrictions.le("fechaFactura", criterios
							.getFecha().getFechaFin()));
				}
			}
			criteria.addOrder(Order.desc("id.numFactura"));
			facturas = criteria.list();
		}
		
		return facturas;
	}

	public List<Factura> buscaFacturaOrdenadasNif (String[] facturas){
				
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Factura> listaFacturas = null;
		
		//FacturaPK id = new FacturaPK();
		//id.setNumFactura(numFactura);
		//id.setNumColegiado(numColegiado);
		
		Criteria criteria = session.createCriteria(Factura.class);
		
		criteria.add(Restrictions.in("id.numFactura", facturas));
		criteria.addOrder(Order.asc("nif"));
		listaFacturas = criteria.list();
		
		session.close();
		
		return listaFacturas;
	}
	
	/**
	 * Metodo de detalle de Factura que incluye los conceptos
	 * @param numFactura
	 * @param numColegiado
	 * @return
	 */
	public Factura detalleFacturaCompleto(String numFactura, String numColegiado) {
		
		Factura factura = new Factura();
		factura.setId(new FacturaPK());
		factura.getId().setNumColegiado(numColegiado);
		factura.getId().setNumFactura(numFactura);
		
		HibernateBean hb = HibernateUtil.createCriteria(Factura.class);
		hb.getCriteria().createAlias("facturaConceptos", "facturaConceptos", CriteriaSpecification.LEFT_JOIN);	

		hb.getCriteria().add(Restrictions.eq("id", factura.getId()));
		
				
		factura = (Factura) hb.getCriteria().uniqueResult();
		
		if(factura != null && factura.getFacturaConceptos() != null) {
			for (FacturaConcepto fc: factura.getFacturaConceptos()){
	
				Hibernate.initialize(fc.getFacturaHonorarios());
				Hibernate.initialize(fc.getFacturaSuplidos());
				Hibernate.initialize(fc.getFacturaGastos());
				
			}
		}
		if (factura != null && factura.getPersona() != null && factura.getPersona().getPersonaDireccions()!=null){
			Hibernate.initialize(factura.getPersona().getPersonaDireccions());
		}
		
		hb.getSession().close();
		
		return factura;
		
		
	}
	
	
	/**
	 * Metodo de detalle de Factura que no incluye los conceptos
	 * @param numFactura
	 * @param numColegiado
	 * @return
	 */
	public Factura detalleFactura(String numFactura, String numColegiado) {
		Factura factura = new Factura();
		factura.setId(new FacturaPK());
		factura.getId().setNumColegiado(numColegiado);
		factura.getId().setNumFactura(numFactura);
		
		HibernateBean hb = HibernateUtil.createCriteria(Factura.class);

		hb.getCriteria().add(Restrictions.eq("id", factura.getId()));
		
				
		factura = (Factura) hb.getCriteria().uniqueResult();
		
				
		hb.getSession().close();
		
		return factura;
	}

	public String checkPDF(String numFactura, String numColegiado) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		FacturaPK id = new FacturaPK();
		id.setNumFactura(numFactura);
		id.setNumColegiado(numColegiado);

		Factura factura = (Factura) session.createCriteria(Factura.class).add(
				Restrictions.eq("id", id)).uniqueResult();

		return factura.getCheckPdf();
	}
	
	// DRC@31-07-2012 Metodo que se encarga de gestionar las transacciones a la
	// BBDD, si todo es correcto realiza el commit y sino rollback.
	public ResultBean ejecutarTransacciones(Transaction tx, ResultBean resultado)
			throws Exception {
		try {
			tx.commit();
			resultado.setError(Boolean.FALSE);
		} catch (RuntimeException re) {
			try {
				tx.rollback();
				resultado.setMensaje(re.getLocalizedMessage().toString());
				resultado.setError(Boolean.TRUE);
				log.error("Se ha ejecutado el rollback: " + re + "\n"
						+ re.getCause().toString());
				System.err.println("Se ha ejecutado el rollback: " + re + "\n"
						+ re.getCause().toString());
			} catch (RuntimeException rbe) {
				resultado.setError(Boolean.TRUE);
				log.error("No se ha podido ejecutar el rollback: " + rbe + "\n"
						+ rbe.getCause().toString());
			}
		}
		return resultado;
	}

	public void borrarTransacciones(String BBDD, String idBBDD, String id)
			throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			String hql = "DELETE FROM " + BBDD + " WHERE " + idBBDD + " IN ("
					+ id + ")";
			Query query = session.createQuery(hql);
			int rowCount = query.executeUpdate();
			log.debug("Filas ID = (" + id + ") Borradas: " + rowCount);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
	}
	
	public void borrarTransaccionesDosCampos(String numColegiado, List<String> listaIdConceptos)	throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		for (String idConcepto : listaIdConceptos){
			Long idConceptoL = Long.parseLong(idConcepto);
			String hql = "DELETE FROM FacturaColegiadoConcepto WHERE " + "numColegiado = :numColegiado AND idColegiadoConcepto = :idConceptoL";
			Query query = session.createQuery(hql);
			query.setParameter("numColegiado", numColegiado);
			query.setParameter("idConceptoL", idConceptoL);
			int rowCount = query.executeUpdate();
			session.flush();
		}
		try {
				ejecutarTransacciones(tx);
			} catch (Exception e) {
				log.error("Se ha producido un error al borrar.", e);
			}
		session.close();
	
	}

	public void ejecutarTransacciones (Transaction tx) throws Exception {
		try {
			tx.commit();
		} catch(RuntimeException re){
			try {
				tx.rollback();
				log.error("Se ha ejecutado el rollback: " + re);
				System.err.println("Se ha ejecutado el rollback: " + re);
			} catch (RuntimeException rbe) {
				log.error("No se ha podido ejecutar el rollback: " + rbe);
			}
		} 
	}
	
	public String existeFactura(String numFactura) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		FacturaPK id = new FacturaPK();
		id.setNumFactura(numFactura);
		Criteria criteria = session.createCriteria(Factura.class);
		criteria.add(Restrictions.like("id.numFactura", numFactura));

		Number numeroFacturas = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		
		if (numeroFacturas != null){
			//Ya existe una factura con esa numeracion por lo que NO se puede crear
			return numeroFacturas.toString();
		}else{
			//No existe una factura con esa numeracion por lo que SI se puede crear
			return "0";	
		}		
	}
	
	public boolean isFacturaAnulada (String numFactura){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Factura.class);
		FacturaPK id = new FacturaPK();
		id.setNumFactura(numFactura);
		criteria.add(Restrictions.eq("id.numFactura", numFactura));
		
		List<Factura> factura = criteria.list();
		if (factura.get(0).getCheckPdf().equals("3"))
			return true;
		else
			return false;
	}
	
	public boolean isFacturaAbono (String numFactura){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Factura.class);
		FacturaPK id = new FacturaPK();
		id.setNumFactura(numFactura);
		criteria.add(Restrictions.eq("id.numFactura", numFactura));
		
		List<Factura> factura = criteria.list();
		if (factura.get(0).getNumSerie().equals("ABON"))
			return true;
		else
			return false;
	}
	
	public boolean isFacturaRectificativa (String numFactura){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Factura.class);
		FacturaPK id = new FacturaPK();
		id.setNumFactura(numFactura);
		criteria.add(Restrictions.eq("id.numFactura", numFactura));
		
		List<Factura> factura = criteria.list();
		if (factura.get(0).getNumSerie().equals("RECT"))
			return true;
		else
			return false;
	}
	// DRC@07-08-2012 Buscar en siguiente numero de serie, para las facturas
	// que son Abonos
	public int buscaNumFacturaAbono(String numFactura, String tipo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		FacturaPK id = new FacturaPK();
		id.setNumFactura(numFactura);
		Criteria criteria = session.createCriteria(Factura.class);
		criteria.add(Restrictions.like("id.numFactura", numFactura));
//		criteria.add(Restrictions.eq("checkPdf", estado));
		//El numero de serie es el tipo.
		criteria.add(Restrictions.eq("numSerie", tipo));
		Number numeroFacturas = (Number) criteria.setProjection(
				Projections.rowCount()).uniqueResult();

		session.close();
		return numeroFacturas.intValue();
	}
}
