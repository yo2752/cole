package trafico.datosSensibles.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.EnvioDataPK;
import org.gestoresmadrid.core.general.model.vo.EnvioDataVO;
import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import colas.constantes.ConstantesProcesos;
import escrituras.beans.ResultBean;
import hibernate.entities.datosSensibles.DatosSensiblesBastidor;
import hibernate.entities.datosSensibles.DatosSensiblesBastidorPK;
import hibernate.entities.datosSensibles.DatosSensiblesMatricula;
import hibernate.entities.datosSensibles.DatosSensiblesMatriculaPK;
import hibernate.entities.datosSensibles.DatosSensiblesNif;
import hibernate.entities.datosSensibles.DatosSensiblesNifPK;
import hibernate.entities.general.Grupo;
import hibernate.entities.general.Usuario;
import trafico.datosSensibles.beans.DatosSensiblesBean;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.enumerados.TipoProceso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.SinDatosWSExcepcion;

public class DatosSensiblesDAOImpl extends HibernateUtil {
	private static final ILoggerOegam log = LoggerOegam.getLogger(DatosSensiblesDAOImpl.class);
	private static final String ALTA = "alta";
	private static final String BAJA = "baja";
	private static final String ENTIDADSFINANCIERAS = "FIN";
	private static final String ERROR_CIERRE_CONEXION_INSERTMATRICULA = "Error cerrando conexion en insertMatricula";
	private static final String ERROR_CIERRE_CONEXION_COMPROBARMATRICULA = "Error cerrando conexion en comprobarMatricula";
	private static final String ERROR_INSERTAR_MATRICULA_DATOSSENSIBLESMATRICULA = "Error al insertar La matricula en DatosSensiblesMatricula";

	@Autowired
	UtilesFecha utilesFecha;

	public DatosSensiblesDAOImpl() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public ResultBean insertMatricula(String matricula, String grupo, String numColegiado, BigDecimal idUsuario, String apellidosNombre, BigDecimal tiempoRestauracion) throws Exception{
		Session session = null;
		ResultBean resultBean = new ResultBean();
		try {
			resultBean.setError(false);
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();

			DatosSensiblesMatriculaPK id = new DatosSensiblesMatriculaPK();
			id.setMatricula(matricula.toUpperCase());
			id.setIdGrupo(grupo);

			DatosSensiblesMatricula datosSensibleMatricula = new DatosSensiblesMatricula();
			datosSensibleMatricula.setId(id);
			datosSensibleMatricula.setFechaAlta(new Date());
			datosSensibleMatricula.setNumColegiado(numColegiado);
			datosSensibleMatricula.setApellidosNombre(apellidosNombre);
			datosSensibleMatricula.setIdUsuario(idUsuario);
			datosSensibleMatricula.setEstado(new BigDecimal(1));
			// Agregado para controlar el tiempo que permanece el dato como sensible en BBDD
			datosSensibleMatricula.setTiempoRestauracion(tiempoRestauracion);

			//Comprobamos si el registro ya estaba en la tabla pero con borrado lógico (estado==0)
			DatosSensiblesMatricula datosSensibleMatriculaAux = null;
			datosSensibleMatriculaAux = getDetalleDatosSensiblesMatricula(matricula, grupo);
			if (datosSensibleMatriculaAux!=null){
				datosSensibleMatricula.setFechaBaja(datosSensibleMatriculaAux.getFechaBaja());
			}

			session.saveOrUpdate(datosSensibleMatricula);
			session.flush();

			ejecutarTransacciones(tx);
		} catch (Exception e) {
			log.error("Error al insertar La matricula: " + matricula + "en DatosSensiblesMatricula", e);
			if (session != null) {
				session.getTransaction().rollback();
			}
			resultBean.setError(true);
			resultBean.setMensaje("Se ha producido un error al insertar la matrícula: " + matricula);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error(ERROR_CIERRE_CONEXION_INSERTMATRICULA, he);
				}
			}
		}
		return resultBean;
	}

	public ResultBean insertBastidor (String bastidor , String grupo, String numColegiado, BigDecimal idUsuario, String apellidosNombre, BigDecimal tiempoRestauracion) throws Exception{
		Session session = null;
		ResultBean resultBean = new ResultBean();
		try {
			resultBean.setError(false);

			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();

			DatosSensiblesBastidorPK id = new DatosSensiblesBastidorPK();
			id.setBastidor(bastidor.toUpperCase());
			id.setIdGrupo(grupo);

			DatosSensiblesBastidor datosSensibleBastidor = new DatosSensiblesBastidor();
			datosSensibleBastidor.setId(id);
			datosSensibleBastidor.setFechaAlta(new Date());
			datosSensibleBastidor.setNumColegiado(numColegiado);
			datosSensibleBastidor.setApellidosNombre(apellidosNombre);
			datosSensibleBastidor.setIdUsuario(idUsuario);
			datosSensibleBastidor.setEstado(new BigDecimal(1));
			// Agregado para controlar el tiempo que permanece el dato como sensible en BBDD
			datosSensibleBastidor.setTiempoRestauracion(tiempoRestauracion);

			//Comprobamos si el registro ya estaba en la tabla pero con borrado lógico (estado==0)
			DatosSensiblesBastidor datosSensibleBastidorAux = null;
			datosSensibleBastidorAux = getDetalleDatosSensiblesBastidor(bastidor, grupo);
			if (datosSensibleBastidorAux!=null){
				datosSensibleBastidor.setFechaBaja(datosSensibleBastidorAux.getFechaBaja());
			}

			session.saveOrUpdate(datosSensibleBastidor);
			session.flush();
			ejecutarTransacciones(tx);
		} catch (Exception e) {
			log.error("Error al insertar el bastidor en DatosSensiblesBastidor", e);
			resultBean.setError(true);
			resultBean.setMensaje(e.getMessage());
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en insertBastidor", he);
				}
			}
		}
		return resultBean;
	}

	public ResultBean insertNif(String nif, String grupo, String numColegiado, BigDecimal idUsuario, String apellidosNombre, BigDecimal tiempoRestauracion) throws Exception {
		Session session = null;
		ResultBean resultBean = new ResultBean();
		try {
			resultBean.setError(false);
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();

			DatosSensiblesNifPK id = new DatosSensiblesNifPK();
			id.setNif(nif.toUpperCase());
			id.setIdGrupo(grupo);

			DatosSensiblesNif datosSensibleNif = new DatosSensiblesNif();
			datosSensibleNif.setId(id);
			datosSensibleNif.setFechaAlta(new Date());
			datosSensibleNif.setNumColegiado(numColegiado);
			datosSensibleNif.setApellidosNombre(apellidosNombre);
			datosSensibleNif.setIdUsuario(idUsuario);
			datosSensibleNif.setEstado(new BigDecimal(1));
			// Agregado para controlar el tiempo que permanece el dato como sensible en BBDD
			datosSensibleNif.setTiempoRestauracion(tiempoRestauracion);

			// Comprobamos si el registro ya estaba en la tabla pero con borrado
			// lógico (estado==0)
			DatosSensiblesNif datosSensibleNifAux = null;
			datosSensibleNifAux = getDetalleDatosSensiblesNif(nif, grupo);
			if (datosSensibleNifAux != null) {
				datosSensibleNif.setFechaBaja(datosSensibleNifAux.getFechaBaja());
			}

			session.saveOrUpdate(datosSensibleNif);
			session.flush();
			ejecutarTransacciones(tx);
		} catch (Exception e) {
			log.error("Error al insertar el nif en datosSensibleNif", e);
			if (session != null) {
				session.getTransaction().rollback();
			}

			resultBean.setError(true);
			resultBean.setMensaje("Se ha producido un error al insertar el NIF" + nif + "como dato sensible");
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en insertNif", he);
				}
			}
		}

		return resultBean;
	}

	@SuppressWarnings("unchecked")
	public List<DatosSensiblesBastidor> listBastidor(DatosSensiblesBean filtro, String grupoUsuario){
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesBastidor.class);
			// Filtros
			if (filtro != null) {
				DatosSensiblesBastidorPK id = new DatosSensiblesBastidorPK();
				id.setBastidor(filtro.getTextoAgrupacion().trim());

				// Si es el usuario Administrador podra ver todos los datos de todos los grupos
				if (!Constantes.DATOS_SENSIBLES_GRUPO_ADMINISTRADOR.equals(grupoUsuario)) {
					id.setIdGrupo(grupoUsuario);
				}

				criteria.add(Restrictions.like("id.bastidor", "%"+id.getBastidor().toUpperCase()+"%"));
				criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

				// Si es el usuario Administrador podra ver todos los datos de todos los grupos
				if (!Constantes.DATOS_SENSIBLES_GRUPO_ADMINISTRADOR.equals(grupoUsuario)) {
					criteria.add(Restrictions.eq("id.idGrupo", id.getIdGrupo()));
				}

				if (!filtro.getFecha().isfechaNula()) {
					if (!filtro.getFecha().isfechaInicioNula()) {
						criteria.add(Restrictions.ge("fechaAlta", filtro.getFecha().getFechaInicio()));
					}
					if (!filtro.getFecha().isfechaFinNula()) {
						String diaOriginal = filtro.getFecha().getDiaFin();
						Integer diaFin = Integer.parseInt(diaOriginal);
						diaFin += 1;
						// Seteamos un día más a la fecha Fin para que tenga en cuenta todo el día
						filtro.getFecha().setDiaFin(diaFin.toString());
						criteria.add(Restrictions.lt("fechaAlta", filtro.getFecha().getFechaFin()));
						// Dejamos la fecha Original que puso el usuario
						filtro.getFecha().setDiaFin(diaOriginal);
					}
				}
			}
			return criteria.list();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en listBastidor", he);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatosSensiblesBastidor> listaBastidorPorId(DatosSensiblesBastidor filtro){
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesBastidor.class);
			// Filtros
			if (filtro != null) {
				criteria.add(Restrictions.like("id.bastidor", filtro.getId().getBastidor().toUpperCase()));
				criteria.add(Restrictions.eq("estado", new BigDecimal(1)));
				criteria.createAlias("grupo", "grupo", CriteriaSpecification.LEFT_JOIN);
			}
			return criteria.list();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en listBastidor", he);
				}
			}
		}
	}

	public boolean existeBastidorGrupo(String bastidor, String grupoUsuario) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesBastidor.class);

			criteria.add(Restrictions.eq("estado", new BigDecimal(1)));
			criteria.add(Restrictions.eq("id.bastidor", bastidor));
			criteria.add(Restrictions.eq("id.idGrupo", grupoUsuario));

			return !criteria.list().isEmpty();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en existeBastidorGrupo", he);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatosSensiblesMatricula> listMatricula(DatosSensiblesBean filtro, String grupoUsuario) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesMatricula.class);
			// Filtros
			if (filtro != null) {

				DatosSensiblesMatriculaPK id = new DatosSensiblesMatriculaPK();
				id.setMatricula(filtro.getTextoAgrupacion().trim());

				// Si es el usuario Administrador podra ver todos los datos de todos los grupos
				if (!Constantes.DATOS_SENSIBLES_GRUPO_ADMINISTRADOR.equals(grupoUsuario)) {
					id.setIdGrupo(grupoUsuario);
				}

				criteria.add(Restrictions.like("id.matricula", "%"+id.getMatricula()+"%"));
				criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

				//Si es el usuario Administrador podra ver todos los datos de todos los grupos
				if (!Constantes.DATOS_SENSIBLES_GRUPO_ADMINISTRADOR.equals(grupoUsuario)) {
					criteria.add(Restrictions.eq("id.idGrupo", id.getIdGrupo()));
				}

				if (!filtro.getFecha().isfechaNula()) {
					if (!filtro.getFecha().isfechaInicioNula()) {
						criteria.add(Restrictions.ge("fechaAlta", filtro.getFecha().getFechaInicio()));
					}

					if (!filtro.getFecha().isfechaFinNula()) {
						String diaOriginal = filtro.getFecha().getDiaFin();
						Integer diaFin = Integer.parseInt(diaOriginal);
						diaFin += 1;
						// Seteamos un dia mas a la fecha Fin para que tenga en cuenta todo el día
						filtro.getFecha().setDiaFin(diaFin.toString());
						criteria.add(Restrictions.lt("fechaAlta", filtro.getFecha().getFechaFin()));
						// Dejamos la fecha Original que puso el usuario
						filtro.getFecha().setDiaFin(diaOriginal);
					}
				}
			}
			return criteria.list();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en listMatricula", he);
				}
			}
		}
	}

	public boolean existeMatriculaGrupo(String matricula, String grupoUsuario) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesMatricula.class);

			criteria.add(Restrictions.eq("estado", new BigDecimal(1)));
			criteria.add(Restrictions.eq("id.matricula", matricula));
			criteria.add(Restrictions.eq("id.idGrupo", grupoUsuario));

			return !criteria.list().isEmpty();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en existeMatriculaGrupo", he);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatosSensiblesNif> listNif(DatosSensiblesBean filtro, String grupoUsuario) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesNif.class);
			// Filtros
			if (filtro != null) {
				DatosSensiblesNifPK id = new DatosSensiblesNifPK();
				id.setNif(filtro.getTextoAgrupacion().trim());

				// Si es el usuario Administrador podra ver todos los datos de todos los grupos
				if (!Constantes.DATOS_SENSIBLES_GRUPO_ADMINISTRADOR.equals(grupoUsuario)) {
					id.setIdGrupo(grupoUsuario);
				}

				criteria.add(Restrictions.like("id.nif", "%"+id.getNif()+"%"));
				criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

				//Si es el usuario Administrador podra ver todos los datos de todos los grupos
				if(!Constantes.DATOS_SENSIBLES_GRUPO_ADMINISTRADOR.equals(grupoUsuario)){
					criteria.add(Restrictions.eq("id.idGrupo", id.getIdGrupo()));
				}

				if (!filtro.getFecha().isfechaNula()) {
					if (!filtro.getFecha().isfechaInicioNula()) {
						criteria.add(Restrictions.ge("fechaAlta", filtro.getFecha().getFechaInicio()));
					}
					if (!filtro.getFecha().isfechaFinNula()) {
						String diaOriginal = filtro.getFecha().getDiaFin();
						Integer diaFin = Integer.parseInt(diaOriginal);
						diaFin += 1;
						// Seteamos un día más a la fecha Fin para que tenga en cuenta todo el día
						filtro.getFecha().setDiaFin(diaFin.toString());
						criteria.add(Restrictions.lt("fechaAlta", filtro.getFecha().getFechaFin()));
						// Dejamos la fecha Original que puso el usuario
						filtro.getFecha().setDiaFin(diaOriginal);
					}
				}
			}
			return criteria.list();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en listNif", he);
				}
			}
		}
	}

	public boolean existeNifGrupo(String nif, String grupoUsuario){
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesNif.class);

			criteria.add(Restrictions.eq("estado", new BigDecimal(1)));
			criteria.add(Restrictions.eq("id.nif", nif));
			criteria.add(Restrictions.eq("id.idGrupo", grupoUsuario));

			return !criteria.list().isEmpty();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en existeNifGrupo", he);
				}
			}
		}
	}

	public void removeMatricula(String matricula , String grupoUsuario) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();

			DatosSensiblesMatricula datosSensiblesMatricula = new DatosSensiblesMatricula();
			datosSensiblesMatricula = getDetalleDatosSensiblesMatricula(matricula, grupoUsuario);

			datosSensiblesMatricula.setEstado(new BigDecimal(0));
			datosSensiblesMatricula.setFechaBaja(new Date());

			session.saveOrUpdate(datosSensiblesMatricula);
			session.flush();

			ejecutarTransacciones(tx);
		} catch (Exception e) {
			log.error("Error al borrar de datosSensibleMatricula la matricula: " + matricula + " para el grupo " + grupoUsuario, e);
			if (session != null) {
				session.getTransaction().rollback();;
			}
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en removeMatricula", he);
				}
			}
		}
	}

	public void removeBastidor(String bastidor, String grupoUsuario) throws Exception {
		Session session = null; 
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();

			DatosSensiblesBastidor datosSensiblesBastidor = new DatosSensiblesBastidor();
			datosSensiblesBastidor = getDetalleDatosSensiblesBastidor(bastidor, grupoUsuario);

			datosSensiblesBastidor.setEstado(new BigDecimal(0));
			datosSensiblesBastidor.setFechaBaja(new Date());

			session.saveOrUpdate(datosSensiblesBastidor);
			session.flush();

			ejecutarTransacciones(tx);
		} catch (Exception e) {
			log.error("Error al borrar de datosSensiblesBastidor el bastidor: " + bastidor + " para el grupo " + grupoUsuario, e);
			if (session != null) {
				session.getTransaction().rollback();;
			}
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en removeBastidor", he);
				}
			}
		}
	}

	public void removeNif(String nif, String grupoUsuario) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();

			DatosSensiblesNif datosSensiblesNif = new DatosSensiblesNif();
			datosSensiblesNif = getDetalleDatosSensiblesNif(nif, grupoUsuario);

			datosSensiblesNif.setEstado(new BigDecimal(0));
			datosSensiblesNif.setFechaBaja(new Date());

			session.saveOrUpdate(datosSensiblesNif);
			session.flush();
			ejecutarTransacciones(tx);
		} catch (Exception e) {
			log.error("Error al borrar de datosSensiblesNif el NIF: " + nif + " para el grupo " + grupoUsuario, e);
			if (session != null) {
				session.getTransaction().rollback();;
			}
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en removeNif", he);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> comprobarBastidor(String bastidor){
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesBastidor.class);
			criteria.add(Restrictions.eq("id.bastidor", bastidor.toUpperCase()));
			criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.distinct(Projections.property("id.idGrupo")));
			criteria.setProjection(projectionList);

			return criteria.list();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en comprobarBastidor", he);
				}
			}
		}
	}

	public String obtenerTipoGrupoBastidor(String idGrupo){
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(Grupo.class);
			criteria.add(Restrictions.eq("idGrupo", idGrupo));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.distinct(Projections.property("tipo")));
			criteria.setProjection(projectionList);

			return (String) criteria.uniqueResult();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en obtenerTipoGrupoBastidor", he);
				}
			}
		}
	}

	public DatosSensiblesMatricula getMatricula(String matricula) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesMatricula.class);

			criteria.add(Restrictions.eq("id.matricula", matricula.toUpperCase()));
			criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

			return (DatosSensiblesMatricula)criteria.list().get(0);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error(ERROR_CIERRE_CONEXION_COMPROBARMATRICULA, he);
				}
			}
		}
	}

	public DatosSensiblesBastidor getBastidor(String bastidor) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesBastidor.class);

			criteria.add(Restrictions.eq("id.bastidor", bastidor.toUpperCase()));
			criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

			return (DatosSensiblesBastidor)criteria.list().get(0);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error(ERROR_CIERRE_CONEXION_COMPROBARMATRICULA, he);
				}
			}
		}
	}

	public DatosSensiblesNif getNif(String nif){
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesNif.class);

			criteria.add(Restrictions.eq("id.nif", nif.toUpperCase()));
			criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

			return (DatosSensiblesNif)criteria.list().get(0);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error(ERROR_CIERRE_CONEXION_COMPROBARMATRICULA, he);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> comprobarMatricula(String matricula) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesMatricula.class);

			criteria.add(Restrictions.eq("id.matricula", matricula.toUpperCase()));
			criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.distinct(Projections.property("id.idGrupo")));
			criteria.setProjection(projectionList);

			return criteria.list();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error(ERROR_CIERRE_CONEXION_COMPROBARMATRICULA, he);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> comprobarNif(String nif) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesNif.class);

			criteria.add(Restrictions.eq("id.nif", nif.toUpperCase()));
			criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.distinct(Projections.property("id.idGrupo")));
			criteria.setProjection(projectionList);

			return criteria.list();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en comprobarNif", he);
				}
			}
		}
	}

	public String obtenerIdGrupo(BigDecimal idUsuario) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			Usuario usuario = (Usuario) session.createCriteria(Usuario.class).add(Restrictions.eq("idUsuario", idUsuario.longValue())).uniqueResult();

			return usuario.getGrupo() != null ? usuario.getGrupo().getIdGrupo() : null;

		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en obtenerIdGrupo", he);
				}
			}
		}
	}

	public String obtenerTipoGrupo(BigDecimal idUsuario) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();

			Usuario usuario = (Usuario) session.createCriteria(Usuario.class).add(Restrictions.eq("idUsuario", idUsuario.longValue())).uniqueResult();

			return usuario.getGrupo() != null ? usuario.getGrupo().getIdGrupo() : null;

		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en obtenerTipoGrupo", he);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> obtenerIdGrupoPorMatricula(String matricula) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesMatricula.class);
			criteria.add(Restrictions.eq("id.matricula", matricula));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.distinct(Projections.property("idGrupo")));
			criteria.setProjection(projectionList);

			return criteria.list();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en obtenerIdGrupoPorMatricula", he);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> obtenerIdGrupoPorBastidor(String bastidor) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesMatricula.class);
			criteria.add(Restrictions.eq("id.bastidor", bastidor));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.distinct(Projections.property("idGrupo")));
			criteria.setProjection(projectionList);

			return criteria.list();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en obtenerIdGrupoPorBastidor", he);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> obtenerIdGrupoPorNif(String nif) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Criteria criteria = session.createCriteria(DatosSensiblesMatricula.class);
			criteria.add(Restrictions.eq("id.nif", nif));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.distinct(Projections.property("idGrupo")));
			criteria.setProjection(projectionList);

			return criteria.list();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en obtenerIdGrupoPorNif", he);
				}
			}
		}
	}

	public String obtenerTipoGrupoPorNif(String nif) {
		Session session = null;
		Usuario usuario = null;
		try {
			session = getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(Usuario.class);
			criteria.createAlias("grupo", "grupo");
			criteria.add(Restrictions.eq("nif", nif));
			List lista = criteria.list();
			if (lista != null) {
				usuario = (Usuario) lista.get(0);
			}

//			Usuario usuario = (Usuario) session.createCriteria(Usuario.class).add(Restrictions.eq("nif", nif)).uniqueResult();
			if (usuario != null && usuario.getGrupo() != null) {
				return usuario.getGrupo().getIdGrupo();
			} else {
				return null;
			}
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en obtenerTipoGrupoPorNif", he);
				}
			}
		}
	}

	private void ejecutarTransacciones (Transaction tx) throws Exception {
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

	public DatosSensiblesBastidor getDetalleDatosSensiblesBastidor(String bastidor, String grupo) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(DatosSensiblesBastidor.class);

			criteria.add(Restrictions.eq("id.bastidor", bastidor));
			criteria.add(Restrictions.eq("id.idGrupo", grupo));

			@SuppressWarnings("unchecked")
			List<DatosSensiblesBastidor> listBastidores = criteria.list();
			if (!listBastidores.isEmpty()) {
				return (DatosSensiblesBastidor)listBastidores.get(0);
			} else {
				return null;
			}
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en getDetalleDatosSensiblesBastidor", he);
				}
			}
		}
	}

	public DatosSensiblesMatricula getDetalleDatosSensiblesMatricula(String matricula, String grupo) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(DatosSensiblesMatricula.class);

			criteria.add(Restrictions.eq("id.matricula", matricula));
			criteria.add(Restrictions.eq("id.idGrupo", grupo));

			@SuppressWarnings("unchecked")
			List<DatosSensiblesMatricula> listMatricula = criteria.list();
			if (!listMatricula.isEmpty()) {
				return (DatosSensiblesMatricula)listMatricula.get(0);
			} else {
				return null;
			}
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en getDetalleDatosSensiblesMatricula", he);
				}
			}
		}

	}

	public DatosSensiblesNif getDetalleDatosSensiblesNif(String bastidor, String grupo) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(DatosSensiblesNif.class);

			criteria.add(Restrictions.eq("id.nif", bastidor));
			criteria.add(Restrictions.eq("id.idGrupo", grupo));

			@SuppressWarnings("unchecked")
			List<DatosSensiblesNif> listNifs = criteria.list();
			if(!listNifs.isEmpty()) {
				return (DatosSensiblesNif)listNifs.get(0);
			} else {
				return null;
			}

		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en getDetalleDatosSensiblesNif", he);
				}
			}
		}
	}

	public DatosSensiblesBastidor obtenerBastidoresModificados(String bastidor) throws SinDatosWSExcepcion{
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			//Comprobar altas en el día de hoy
			Criteria criteria = session.createCriteria(DatosSensiblesBastidor.class);
			criteria.add(Restrictions.eq("id.bastidor", bastidor));

			@SuppressWarnings("unchecked")
			List<DatosSensiblesBastidor> lDatosSensiblesBastidor = criteria.list();
			if(!lDatosSensiblesBastidor.isEmpty()) {
				return (DatosSensiblesBastidor)lDatosSensiblesBastidor.get(0);
			} else {
				return null;
			}
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en getDetalleDatosSensiblesNif", he);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, List<DatosSensiblesBastidor>> obtenerBastidoresModificados2() {
		HashMap<String, List<DatosSensiblesBastidor>> mapaDatosSensibles = new HashMap<>();

		Session session = null;
		try {
			Date fechaDesde = obtenerEnvioData(TipoProceso.EntidadesFinancieras, ConstantesProcesos.EJECUCION_CORRECTA);
			session = HibernateUtil.getSessionFactory().openSession();
			Calendar calHasta = Calendar.getInstance();
			Timestamp fechaActual = new Timestamp(calHasta.getTimeInMillis());

			//Comprobar altas en el dia de hoy
			Criteria criteria = session.createCriteria(DatosSensiblesBastidor.class);
			criteria.add(Restrictions.le("fechaAlta", utilesFecha.getDate(fechaActual)));
			criteria.add(Restrictions.gt("fechaAlta", fechaDesde));
			criteria.add(Restrictions.eq("estado", 1));
			criteria.add(Restrictions.eq("grupo", ENTIDADSFINANCIERAS));

			if (!criteria.list().isEmpty()) {
				mapaDatosSensibles.put(ALTA,criteria.list());
			}

			//Comprobar bajas en el día de hoy
			Criteria criteria2 = session.createCriteria(DatosSensiblesBastidor.class);
			criteria2.add(Restrictions.le("fechaBaja", utilesFecha.getDate(fechaActual)));
			criteria2.add(Restrictions.gt("fechaBaja", fechaDesde));
			criteria2.add(Restrictions.eq("estado", 0));
			criteria2.add(Restrictions.eq("grupo", ENTIDADSFINANCIERAS));

			if (!criteria2.list().isEmpty()) {
				mapaDatosSensibles.put(BAJA,criteria2.list());
			}

			return mapaDatosSensibles;
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en obtenerBastidoresModificados2", he);
				}
			}
		}
	}

	public Date obtenerEnvioData(TipoProceso tipoProceso, String resultadoEjecucion) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(EnvioDataVO.class);
			EnvioDataPK id = new EnvioDataPK();

			id.setCorrecta(resultadoEjecucion);
			id.setProceso(tipoProceso.getValorEnum());

			criteria.add(Restrictions.eq("id.correcta", id.getCorrecta()));
			criteria.add(Restrictions.eq("id.proceso", id.getProceso()));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.distinct(Projections.property("fechaEnvio")));
			criteria.setProjection(projectionList);

			Timestamp fechaUltimaEjecucionCorrecta = (Timestamp) criteria.uniqueResult();

			Date fechaUltimaEjec = utilesFecha.getDate(fechaUltimaEjecucionCorrecta);

			return fechaUltimaEjec;
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en obtenerEnvioData", he);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getListadoMatriculasSensibles() {
		Session session = null;
		List<String> result = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(DatosSensiblesMatricula.class);
			criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.distinct(Projections.property("id.matricula")));

			criteria.setProjection(projectionList);

			result = criteria.list();
		} catch (HibernateException he) {
			log.error("Error recuperando lista de matriculas sensibles", he);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en getListadoMatriculasSensibles", he);
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<String> getListadoBastidoresSensibles() {
		Session session = null;
		List<String> result = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(DatosSensiblesBastidor.class);
			criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.distinct(Projections.property("id.bastidor")));

			criteria.setProjection(projectionList);

			result = criteria.list();
		} catch (HibernateException he) {
			log.error("Error recuperando lista de bastidores sensibles", he);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en getListadoBastidoresSensibles", he);
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<String> getListadoNifsSensibles() {
		Session session = null;
		List<String> result = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(DatosSensiblesNif.class);
			criteria.add(Restrictions.eq("estado", new BigDecimal(1)));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.distinct(Projections.property("id.nif")));

			criteria.setProjection(projectionList);

			result = criteria.list();
		} catch (HibernateException he) {
			log.error("Error recuperando lista de NIFs sensibles", he);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error("Error cerrando conexion en getListadoNifsSensibles", he);
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Grupo> getGrupos() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(Grupo.class);

			return criteria.list();
		} finally {
			if ( session != null) {
				try {
					session.close();
				} catch (HibernateException e) {
					log.error("No se pudo cerrar la sesion en getGrupos", e);
				}
			}
		}
	}

	public void actualizaFechaOperacion(DatosSensiblesMatricula datosSensiblesMatricula, BigDecimal usuarioDeSesion){
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();

			Date date = new Date();

			datosSensiblesMatricula.setFechaOperacion(new Date(date.getTime()));
			datosSensiblesMatricula.setUsuarioOperacion(usuarioDeSesion);

			session.saveOrUpdate(datosSensiblesMatricula);
			session.flush();

			ejecutarTransacciones(tx);
		} catch (Exception e) {
			log.error(ERROR_INSERTAR_MATRICULA_DATOSSENSIBLESMATRICULA, e);
			if (session != null) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error(ERROR_CIERRE_CONEXION_INSERTMATRICULA, he);
				}
			}
		}
	}

	public void actualizaFechaOperacion(DatosSensiblesBastidor datosSensiblesBastidor, BigDecimal usuarioDeSesion){
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();

			Date date = new Date();

			datosSensiblesBastidor.setFechaOperacion(new Date(date.getTime()));
			datosSensiblesBastidor.setUsuarioOperacion(usuarioDeSesion);

			session.saveOrUpdate(datosSensiblesBastidor);
			session.flush();

			ejecutarTransacciones(tx);
		} catch (Exception e) {
			log.error(ERROR_INSERTAR_MATRICULA_DATOSSENSIBLESMATRICULA, e);
			if (session != null) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error(ERROR_CIERRE_CONEXION_INSERTMATRICULA, he);
				}
			}
		}
	}

	public void actualizaFechaOperacion(DatosSensiblesNif datosSensiblesNif, BigDecimal usuarioDeSesion){
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();

			Date date = new Date();

			datosSensiblesNif.setFechaOperacion(new Date(date.getTime()));
			datosSensiblesNif.setUsuarioOperacion(usuarioDeSesion);

			session.saveOrUpdate(datosSensiblesNif);
			session.flush();

			ejecutarTransacciones(tx);
		} catch (Exception e) {
			log.error(ERROR_INSERTAR_MATRICULA_DATOSSENSIBLESMATRICULA, e);
			if (session != null) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException he) {
					log.error(ERROR_CIERRE_CONEXION_INSERTMATRICULA, he);
				}
			}
		}
	}
}