package hibernate.dao.trafico;

import java.util.List;

import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import hibernate.entities.trafico.CifsRenting;

public class VerifyRentingDAO extends HibernateUtil{

	/**
	 * Busca concordancia de un CIF de transmitente en la tabla CIFS_RENTING
	 * @param cif del transmitente del trámite
	 * @return boolean de concordancia en la tabla
	 * @throws Exception
	 */
	public static boolean cifEnTabla(String cif) throws Exception{

		if(cif == null){
			return false;
		}

		Session session = getSessionFactory().openSession();
		List<CifsRenting> listaConcordancias = null;

		try{
			Criteria criteria = session.createCriteria(CifsRenting.class, "cifsRenting");
			criteria.add(Restrictions.eq("cifsRenting.cif", cif));

			listaConcordancias = criteria.list();
		} finally {
			session.close();
		}

		if(listaConcordancias != null && listaConcordancias.size() == 1){
			return true;
		}
		return false;
	}

}