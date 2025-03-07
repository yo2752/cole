package org.gestoresmadrid.core.empresaDIRe.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.empresaDIRe.model.dao.EmpresaDIReDao;
import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIReVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.Fecha;

@Repository
public class EmpresaDIReDaoImpl extends GenericDaoImplHibernate<EmpresaDIReVO>  implements EmpresaDIReDao {

	private static final long serialVersionUID = 5652114744878490740L;
	private static final String horaFInDia = "23:59";
	private static final int N_SEGUNDOS = 59;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public BigDecimal generarNumExpediente(String numColegiado,String tipoOperacion) throws Exception {
		
		String textNumExpediente = numColegiado;
		Fecha fecha = utilesFecha.getFechaActual();
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, horaFInDia);
		utilesFecha.setSegundosEnDate(fin, N_SEGUNDOS);
		textNumExpediente += fecha.getDia() + fecha.getMes() + fecha.getAnio().substring(2) + tipoOperacion /*TipoOperacionCaycEnum.Alta_Arrendatario.getTipo()*/	;

		Criteria criteria = getCurrentSession().createCriteria(EmpresaDIReVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.between("fechaCreacion", fecha.getFecha(), fin));
		criteria.setProjection(Projections.max("numExpediente"));

		BigDecimal maximoExistente = (BigDecimal) criteria.uniqueResult();
		if (maximoExistente == null) {
			maximoExistente = new BigDecimal(textNumExpediente + "0000");
		}
		return new BigDecimal(maximoExistente.longValue() + 1);
	}

	
	public String Generar_Numero_DIRe(String  Codigo_DIRe) {
		Integer contador=0;
	
		
		String cadena="";
		do {
		cadena="";
		int relleno=4-contador.toString().length();	
		for (int bucle=0;bucle<relleno;bucle++){
			cadena=cadena + "0";
			
		}	
		cadena=cadena+contador.toString();
			
		Criterion c = Restrictions.eq("codigoDire", Codigo_DIRe + cadena);
		
		List<Criterion> criterion = new ArrayList<Criterion>();
		criterion.add(c);
		List<EmpresaDIReVO> l = buscarPorCriteria(-1, -1, criterion, null, null);
		if (l == null || l.size() == 0) {
			contador=-1;
		} else contador++;
		} while (contador!=-1);
		return  Codigo_DIRe + cadena;
	}
	
	
	
	
	

	@Override
	public EmpresaDIReVO getEmpresaDIRePorExpediente(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(EmpresaDIReVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		
		return (EmpresaDIReVO) criteria.uniqueResult();
	}


	@Override
	public EmpresaDIReVO getEmpresaDIRePorExpediente(BigDecimal numExpediente, Boolean tramiteCompleto) {
		Criteria criteria = getCurrentSession().createCriteria(EmpresaDIReVO.class);
		criteria.add(Restrictions.eq("numExpediente", numExpediente));
		
		return (EmpresaDIReVO) criteria.uniqueResult();
	}




	@Override
	public EmpresaDIReVO getEmpresaDIRePorId(long longValue, Boolean tramiteCompleto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
