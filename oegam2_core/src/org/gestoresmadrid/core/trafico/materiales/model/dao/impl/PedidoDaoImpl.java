package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;


import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.PedidoDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedido;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Repository
public class PedidoDaoImpl extends GenericDaoImplHibernate<PedidoVO> implements PedidoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4429488211613935766L;

	@SuppressWarnings("unused")
	private static final ILoggerOegam log = LoggerOegam.getLogger(PedidoDaoImpl.class);

	@Override
	public PedidoVO findPedidoForJefaturaMateria(String jefatura, Long material) {
		
		Long[] estadosPermitidos = new Long[] {
				new Long(EstadoPedido.Iniciado.getValorEnum()),
				new Long(EstadoPedido.Borrador.getValorEnum()),
				new Long(EstadoPedido.Solicitado_Consejo.getValorEnum()),
				new Long(EstadoPedido.Solicitado_FNMT.getValorEnum()),
				new Long(EstadoPedido.Recepcionado_Consejo.getValorEnum()),
				new Long(EstadoPedido.Enviado_Colegio.getValorEnum())
		};
		
		Criteria criteria = getCurrentSession().createCriteria(PedidoVO.class, "pedido");
		criteria.createAlias("pedido.jefaturaProvincial", "jefatura");
		criteria.createAlias("pedido.materialVO", "material");
		criteria.add(Restrictions.eq("jefatura.jefaturaProvincial", jefatura));
		criteria.add(Restrictions.eq("material.materialId", material));
		criteria.add(Restrictions.in("pedido.estado", estadosPermitidos));
		
		@SuppressWarnings("unchecked")
		List<PedidoVO> pedidos = criteria.list();
		if (pedidos.size() != 0) {
			return (PedidoVO) criteria.list().get(0);
		} else {
			return null;
		}
	}

	public PedidoVO findPedidoByPK(Long pedidoId) {
		Criteria criteria = getCurrentSession().createCriteria(PedidoVO.class, "pedido").
				add(Restrictions.eq("pedidoId", pedidoId));
		
		return (PedidoVO) criteria.uniqueResult();
	}

	@Override
	public PedidoVO findPedidoByInventario(String codigoInicial, String codigoFinal, String jefaturaProvincial) {
		Criteria criteria = getCurrentSession().createCriteria(PedidoVO.class, "pedido");
		criteria.createAlias("pedido.jefaturaProvincial", "jefatura");
		criteria.add(Restrictions.eq("jefatura.jefaturaProvincial", jefaturaProvincial));
		criteria.add(Restrictions.eq("codigoInicial", codigoInicial));
		criteria.add(Restrictions.eq("codigoFinal", codigoFinal));

		return (PedidoVO) criteria.uniqueResult();
	}

	@Override
	public PedidoVO findPedidoByJefaturaMaterialSerial(String jefatura, Long materialId, String serial) {
		Long estadoEntregado = new Long(EstadoPedido.Entregado.getValorEnum());
		
		Criteria criteria = getCurrentSession().createCriteria(PedidoVO.class, "pedido");
		criteria.createAlias("pedido.jefaturaProvincial", "jefatura");
		criteria.createAlias("pedido.detallePedidoVO", "detallePedido");
		criteria.add(Restrictions.eq("jefatura.jefaturaProvincial", jefatura));
		criteria.add(Restrictions.eq("estado", estadoEntregado));
		criteria.add(Restrictions.le("codigoInicial", serial)) 
				.add(Restrictions.ge("codigoFinal", serial));	

		return (PedidoVO) criteria.uniqueResult();
	}

	@Override
	public PedidoVO findPedidoCompleto(Long pedidoId) {
		Criteria criteria = getCurrentSession().createCriteria(PedidoVO.class, "pedido");
		criteria.createAlias("pedido.jefaturaProvincial", "jefatura");
		criteria.add(Restrictions.eq("pedidoId", pedidoId));

		return (PedidoVO) criteria.uniqueResult();
	}

	@Override
	public PedidoVO findPedidoByInventarioId(Long inventarioId, boolean completo) {
		Criteria criteria = getCurrentSession().createCriteria(PedidoVO.class, "pedido");
		if (completo) {
			criteria.createAlias("pedido.autorVO", "autor");
			criteria.createAlias("pedido.materialVO", "material");
			criteria.createAlias("pedido.jefaturaProvincial", "jefatura");
		}
		criteria.add(Restrictions.eq("pedidoInvId", inventarioId));
		
		return (PedidoVO) criteria.uniqueResult();
	}

	@Override
	public PedidoVO findPedidoByJefaturaSerial(String serial, String jefaturaProvincial) {
		String serialNum = serial.substring(5);
		
		Criteria criteria = getCurrentSession().createCriteria(PedidoVO.class, "pedido");
		criteria.createAlias("pedido.jefaturaProvincial", "jefatura");
		criteria.add(Restrictions.eq("jefatura.jefaturaProvincial", jefaturaProvincial));
		criteria.add(Restrictions.sqlRestriction("substr(codigo_Inicial,-8, 8) < ?", serialNum, Hibernate.STRING));  
		criteria.add(Restrictions.sqlRestriction("substr(codigo_Final,-8, 8) >= ?", serialNum, Hibernate.STRING));  
		
		@SuppressWarnings("unchecked")
		List<PedidoVO> results = criteria.list();
		if (results != null && results.size() == 1) {
			return results.get(0);
		}
		
		return null;
	}

}
