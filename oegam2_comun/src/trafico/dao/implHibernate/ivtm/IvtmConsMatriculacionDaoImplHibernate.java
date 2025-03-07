package trafico.dao.implHibernate.ivtm;
//TODO MPC. Cambio IVTM. Clase añadida.
import java.math.BigDecimal;
import hibernate.entities.trafico.IvtmConsultaMatriculacion;
import trafico.dao.implHibernate.GenericDaoImplHibernate;
import trafico.dao.ivtm.IvtmConsMatriculacionDaoInterface;

public class IvtmConsMatriculacionDaoImplHibernate extends GenericDaoImplHibernate<IvtmConsultaMatriculacion> implements IvtmConsMatriculacionDaoInterface {

	public IvtmConsMatriculacionDaoImplHibernate(IvtmConsultaMatriculacion t) {
		super(t);
	}

	@Override
	public BigDecimal guardarConsulta(IvtmConsultaMatriculacion ivtmConsulta) {	
		return (BigDecimal) guardar(ivtmConsulta);
	}

}