package trafico.dao.implHibernate.ivtm;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

//TODO MPC. Cambio IVTM. Clase añadida.
import hibernate.entities.trafico.IvtmConsultaMatriculacion;
import hibernate.entities.trafico.IvtmMatriculacion;
import trafico.dao.ivtm.IvtmDaoInterface;
import trafico.utiles.constantes.ConstantesIVTM;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class IvtmDaoImplHibernate  extends HibernateUtil  implements IvtmDaoInterface{

	private static final ILoggerOegam log = LoggerOegam.getLogger(IvtmDaoImplHibernate.class);

	/**
	 * @see IvtmDaoInterface
	 */
	public  BigDecimal generarIdPeticion(String numColegiado) {

		String peticion = numColegiado;
		Fecha fecha = new Fecha(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		peticion += fecha.getDia()+fecha.getMes()+fecha.getAnio().substring(2);
		peticion += ConstantesIVTM.TIPO_ID_PETICION_IVTM;

		Session session = createSession();

		//Máximo de las peticiones de alta o modificación
		Criteria criteria = session.createCriteria(IvtmMatriculacion.class);
		criteria.add(Restrictions.sqlRestriction( " ID_PETICION like " + " '"+peticion+"%'"));
		criteria.setProjection(Projections.max("idPeticion"));

		//Máximo de las peticiones de consulta
		Criteria criteria2 = session.createCriteria(IvtmConsultaMatriculacion.class);
		criteria2.add(Restrictions.sqlRestriction( " ID_PETICION  like " + "'"+peticion+"%'"));
		criteria2.setProjection(Projections.max("idPeticion"));

		BigDecimal resultadoAlta=null;
		BigDecimal resultadoConsulta=null;
		BigDecimal resultadoMaximo=null;

		//Máximo entre las dos
		try{
			resultadoAlta = (BigDecimal) criteria.uniqueResult();
			resultadoConsulta = (BigDecimal)(criteria2.uniqueResult());
			if (resultadoAlta!=null && resultadoConsulta!=null) {
				resultadoMaximo= resultadoAlta.max(resultadoConsulta);
			}else if (resultadoConsulta!=null){
				resultadoMaximo= resultadoConsulta;
			}else{
				resultadoMaximo= resultadoAlta;
			}
			return resultadoMaximo;

		}catch(HibernateException e){
			log.error("Un error ha ocurrido al generar un id de petición");
			log.error(e.getMessage());
			throw e;
		}
	}

}