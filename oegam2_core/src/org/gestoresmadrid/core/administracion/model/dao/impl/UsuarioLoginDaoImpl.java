package org.gestoresmadrid.core.administracion.model.dao.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.gestoresmadrid.core.administracion.model.dao.UsuarioLoginDao;
import org.gestoresmadrid.core.administracion.model.vo.UsuarioLoginVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.FechaFraccionada;

@Repository
public class UsuarioLoginDaoImpl extends GenericDaoImplHibernate<UsuarioLoginVO> implements UsuarioLoginDao {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3851203265166836860L;

	// Realiza una consulta a la BBDB mediante la API Criteria sobre la tabla USUARIO_LOGIN.
	// Ejecuta la siguiente sentencia SQL: select distinct(frontal) from usuario_login where fecha_fin is null group by frontal order by frontal;
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getFrontalesActivos() {
		List<Criterion> criterion = new ArrayList<Criterion>();
		criterion.add(Restrictions.isNull("fechaFin"));
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.distinct(Projections.property("frontal")));
		ArrayList<String> orden = new ArrayList<String>();
		orden.add("frontal");
		List<?> result = buscarPorCriteria(criterion, GenericDaoImplHibernate.ordenAsc, orden, null, listaProyecciones);
		return (List<Integer>) result;
	}

	// Realiza una consulta a la BBDB mediante la API Criteria sobre la tabla USUARIO_LOGIN.
	// Ejecuta la siguiente sentencia SQL: select frontal, count(*) from usuario_login where fecha_fin is null group by frontal order by frontal;
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getUsuariosFrontal() {
		List<Criterion> criterion = new ArrayList<Criterion>();
		criterion.add(Restrictions.isNull("fechaFin"));
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.groupProperty("frontal"));
		listaProyecciones.add(Projections.rowCount()).hashCode();
		ArrayList<String> orden = new ArrayList<String>();
		orden.add("frontal");
		List<?> result = buscarPorCriteria(criterion, GenericDaoImplHibernate.ordenAsc, orden, null, listaProyecciones);
		return (List<Object[]>) result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DatoMaestroBean> getUsuariosPorFrontal() {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioLoginVO.class);
		criteria.add(Restrictions.isNull("fechaFin"));
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.groupProperty("frontal"));
		listaProyecciones.add(Projections.rowCount()).hashCode();
		criteria.setProjection(listaProyecciones);
		criteria.addOrder(Order.asc("frontal"));
		criteria.setResultTransformer(new ResultTransformer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2961364363576267313L;

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				return new DatoMaestroBean(tuple[0].toString(), tuple[1].toString());
			}

			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUsuariosRepetidosPlataforma() {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioLoginVO.class);
		criteria.add(Restrictions.isNull("fechaFin"));
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.groupProperty("numColegiado"));
		listaProyecciones.add(Projections.groupProperty("idUsuario"));
		listaProyecciones.add(Projections.groupProperty("apellidosNombre"));
		listaProyecciones.add(Projections.rowCount()).hashCode();
		criteria.setProjection(listaProyecciones);
		criteria.setResultTransformer(new ResultTransformer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2961364363576267313L;

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				String mensaje = "El usuario con numColegiado " + tuple[0].toString() + " e idUsuario " 
						+ tuple[1].toString() + " (" + tuple[2].toString() +  ") está conectado " 
						+ tuple[3].toString() + " veces";
				return mensaje;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
		return criteria.list();
	}

	// Realiza una consulta a la BBDB mediante la API Criteria sobre la tabla USUARIO_LOGIN.
	// Ejecuta la siguiente sentencia SQL: sselect num_colegiado, id_usuario, apellidos_nombre, count(*) from usuario_login where fecha_fin is null 
	// group by num_colegiado,id_usuario,apellidos_nombre order by num_colegiado, id_usuario;
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getUsuariosRepetidos() {

		List<Criterion> criterion = new ArrayList<Criterion>();
		criterion.add(Restrictions.isNull("fechaFin"));
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.groupProperty("numColegiado"));
		listaProyecciones.add(Projections.groupProperty("idUsuario"));
		listaProyecciones.add(Projections.groupProperty("apellidosNombre"));
		listaProyecciones.add(Projections.rowCount()).hashCode();
		List<?> result = buscarPorCriteria(criterion, null, listaProyecciones);
		return (List<Object[]>) result;
	}

	// Realiza una consulta sobre la tabla USUARIO_LOGIN que recupera el total de usuarios que hay en los frontales
	// Ejecuta la siguiente sentencia: select count(*) from usuario_login where fecha_fin is null;
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getUsuariosTotalesFrontales() {
		
		List<Criterion> criterion = new ArrayList<Criterion>();
		criterion.add(Restrictions.isNull("fechaFin"));
		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.rowCount()).hashCode();
		List<?> result = buscarPorCriteria(criterion, null, null, null, listaProyecciones);
		return (List<Object[]>) result;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioLoginVO> getSesionesAbiertas(BigDecimal idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioLoginVO.class);
		criteria.add(Restrictions.isNull("fechaFin"));
		criteria.add(Restrictions.eq("idUsuario", idUsuario));
		return criteria.list();
	}

	@Override
	public UsuarioLoginVO getUsuarioLoginVO(String idSesion) {
		return (UsuarioLoginVO) getCurrentSession().get(UsuarioLoginVO.class, idSesion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> buscaIPMasRepetida(String numColegiado, FechaFraccionada fechaLogin) {
		
		Criteria criteria = getCurrentSession().createCriteria(UsuarioLoginVO.class);

		if (numColegiado != null && !numColegiado.isEmpty() && !"-1".equals(numColegiado)) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
		}

		if (fechaLogin!=null && !fechaLogin.isfechaNula()) {
			if (!fechaLogin.isfechaInicioNula()) {
				criteria.add(Restrictions.ge("fechaLogin", fechaLogin.getFechaInicio()));
			}
			if (!fechaLogin.isfechaFinNula()) {
				criteria.add(Restrictions.le("fechaLogin",fechaLogin.getFechaMaxFin()));
			}
		}

		ProjectionList projectionList = Projections.projectionList();

		projectionList.add(Projections.count("ip"), "countItems");
		projectionList.add(Projections.groupProperty("ip"));
		criteria.setProjection(projectionList);
		criteria.setMaxResults(1);
		criteria.addOrder(Order.desc("countItems"));

		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioLoginVO> buscaUsuarioLogin(String numColegiado, int frontal, FechaFraccionada fechaLogin) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioLoginVO.class);
		if (numColegiado != null && !numColegiado.isEmpty() && !"-1".equals(numColegiado)) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
		}
		if (-1 != frontal) {
			criteria.add(Restrictions.eq("frontal", frontal));
		}
		if (fechaLogin != null && !fechaLogin.isfechaNula()) {
			if (!fechaLogin.isfechaInicioNula()) {
				criteria.add(Restrictions.ge("fechaLogin", fechaLogin.getFechaInicio()));
			}
			if (!fechaLogin.isfechaFinNula()) {
				criteria.add(Restrictions.le("fechaLogin", fechaLogin.getFechaMaxFin()));
			}
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioLoginVO> buscaUsuariosActivosLogin(String numColegiado, int frontal, Collection<Object> listIdUsuarios, String ordenBusqueda) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioLoginVO.class);
		criteria.add(Restrictions.isNull("fechaFin"));
		if (-1 != frontal) {
			criteria.add(Restrictions.eq("frontal", frontal));
		}		
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
		}

		if (listIdUsuarios != null && !listIdUsuarios.isEmpty()) {
			criteria.add(Restrictions.in("idUsuario", listIdUsuarios));
		}
		if (ordenBusqueda != null && !ordenBusqueda.isEmpty()) {
			criteria.addOrder(Order.asc(ordenBusqueda));
		}
		return criteria.list();
	}

	@Override
	public void insert(String idSesion, String numColegiado, String ipAddr, String ipFrontal, BigDecimal id_user, String apellidosNombre, String servidoNtp) {

		UsuarioLoginVO usuarioLogin = new UsuarioLoginVO();

		usuarioLogin.setIdUsuario(id_user);
		usuarioLogin.setApellidosNombre(apellidosNombre);
		usuarioLogin.setNumColegiado(numColegiado);
		usuarioLogin.setIp(ipAddr);
		StringTokenizer st = new StringTokenizer(ipFrontal, ".");
		int contador = 1;
		while (st.hasMoreElements()) {
			if (contador == 3) {
				String frontal = (String) st.nextElement();
				if ("6".equals(frontal)) {
					usuarioLogin.setFrontal(Integer.parseInt("16"));
				} else {
					usuarioLogin.setFrontal(Integer.parseInt(frontal));
				}
			} else {
				contador++;
				st.nextElement();
			}
		}
		usuarioLogin.setFechaFin(null);
		usuarioLogin.setIdSesion(idSesion);

		if ("ORACLE".equals(servidoNtp)) {
			// Recupera el timestamp de BBDD para que no haya desfase entre distintos frontales. Esto es Oracle dependiente, ojo.
			
			//Mantis 0022619 Se modifica para añadir la hora, ya que solo se estaba añadiendo la fecha
			String resultado = (String) getCurrentSession().createSQLQuery("SELECT TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:ss') FROM DUAL").uniqueResult(); 
			Date date=null;
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			try {
				date = formatter.parse(resultado);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			usuarioLogin.setFechaLogin(date);
		} else {
			usuarioLogin.setFechaLogin(new Date());
		}

		getCurrentSession().save(usuarioLogin);
	}

}
