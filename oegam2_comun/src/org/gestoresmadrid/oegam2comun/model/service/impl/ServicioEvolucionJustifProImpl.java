package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.general.model.dao.EvolucionJustifProfDao;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.EvolucionJustifProfesionalesPK;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.EvolucionJustifProfesionalesVO;
import org.gestoresmadrid.oegam2comun.model.service.ServicioEvolucionJustifProInt;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hibernate.entities.trafico.JustificanteProf;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service(value="servicioEvolucionJustifPro")
@Transactional
public class ServicioEvolucionJustifProImpl implements ServicioEvolucionJustifProInt{

	private static final long serialVersionUID = 1L;
	protected static final ILoggerOegam log = LoggerOegam.getLogger(GenericDaoImplHibernate.class);
	
	@Autowired
	private EvolucionJustifProfDao evolucionJustifProfDaoImpl;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Override
	public EvolucionJustifProfesionalesVO convertirBeantoEntity(JustificanteProf justificanteProfesional, BigDecimal estado) {
		EvolucionJustifProfesionalesVO evolucionJustifProfVO = new EvolucionJustifProfesionalesVO();
		EvolucionJustifProfesionalesPK evJustificanteProfPK = new EvolucionJustifProfesionalesPK();

		if (justificanteProfesional.getTramiteTrafico().getNumExpediente() != null) {
			evJustificanteProfPK.setNumExpediente(new BigDecimal(justificanteProfesional.getTramiteTrafico().getNumExpediente()));
		}
		
		evJustificanteProfPK.setEstadoAnterior(justificanteProfesional.getEstado());
		evJustificanteProfPK.setEstado(estado);
		evJustificanteProfPK.setIdJustificanteInterno(justificanteProfesional.getIdJustificanteInterno());
		evJustificanteProfPK.setFechaCambio(utilesFecha.getFechaActualDesfaseBBDD());
				
		evolucionJustifProfVO.setId(evJustificanteProfPK);
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(utilesColegiado.getIdUsuarioSession());
		evolucionJustifProfVO.setUsuario(usuario);
		if(justificanteProfesional.getIdJustificante() != null){
			evolucionJustifProfVO.setIdJustificante(String.valueOf(justificanteProfesional.getIdJustificante().longValue()));
		}else {
			evolucionJustifProfVO.setIdJustificante(null);
		}
		
		return evolucionJustifProfVO;
	}

	@Override
	public void guardarEvolucion(EvolucionJustifProfesionalesVO evolucionJustificanteProfVO) {
		evolucionJustifProfDaoImpl.guardarEvolucion(evolucionJustificanteProfVO);
		
	}
}
