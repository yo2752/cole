package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.TramiteRegRbmDao;
import org.gestoresmadrid.core.registradores.model.vo.TramiteRegRbmVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TramiteRegRbmDaoImpl extends GenericDaoImplHibernate<TramiteRegRbmVO> implements TramiteRegRbmDao {

	private static final long serialVersionUID = 3950103774674327952L;

	@Override
	public TramiteRegRbmVO getTramiteRegRbm(BigDecimal idTramiteRegistro, boolean completo) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteRegRbmVO.class);
		if (idTramiteRegistro !=  null) {
			criteria.add(Restrictions.eq("idTramiteRegistro", idTramiteRegistro));
		}
//		criteria.createAlias("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
//		criteria.createAlias("contrato.colegiado", "contratoColegiado", CriteriaSpecification.LEFT_JOIN);
//		criteria.createAlias("sociedad", "sociedad", CriteriaSpecification.LEFT_JOIN);
		if(completo){
			criteria.createAlias("clausulas", "clausulas", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("datoFirmas", "datoFirmas", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("datosFinanciero", "datosFinanciero", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("datosFinanciero.comisiones", "datosFinancieroComisiones", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("datosFinanciero.cuadrosAmortizacion", "datosFinancieroCuadrosAmortizacion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("datosFinanciero.otroImportes", "datosFinancieroOtroImportes", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("datosFinanciero.reconocimientoDeudas", "datosFinancieroReconocimientoDeudas", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("propiedades", "propiedades", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("propiedades.intervinienteRegistro", "propiedadesIntervinienteRegistro", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("propiedades.aeronave", "propiedadesAeronave", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("propiedades.buque", "propiedadesBuque", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("propiedades.buque.motorBuques", "propiedadesBuqueMotorBuques", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("propiedades.maquinaria", "propiedadesMaquinaria", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("propiedades.establecimiento", "propiedadesEstablecimiento", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("propiedades.propiedadIndustrial", "propiedadesPropiedadIndustrial", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("propiedades.propiedadIntelectual", "propiedadesPropiedadIntelectual", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("propiedades.otrosBienes", "propiedadesOtrosBienes", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("propiedades.vehiculo", "propiedadesVehiculo", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteRegistro", "intervinienteRegistro", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteRegistro.direccion", "intervinienteRegistroDireccion", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteRegistro.datRegMercantil", "intervinienteRegistroDatRegMercantil", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("intervinienteRegistro.notario", "intervinienteRegistroNotario", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("pactos", "pactos", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("entidadSuscriptora", "entidadSuscriptora", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("entidadSuscriptora.datRegMercantil", "entidadSusDatRegMercantil", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("entidadSucesora", "entidadSucesora", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("entidadSucesora.datRegMercantil", "entidadSucDatRegMercantil", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("datosInscripcion", "datosInscripcion", CriteriaSpecification.LEFT_JOIN);
		
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (TramiteRegRbmVO) criteria.uniqueResult();
	}
	
}
