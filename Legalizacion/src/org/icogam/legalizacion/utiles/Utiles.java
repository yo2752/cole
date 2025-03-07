package org.icogam.legalizacion.utiles;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.icogam.legalizacion.DAO.LegalizacionDao;
import org.icogam.legalizacion.DAOImpl.LegalizacionDaoImplHibernate;
import org.icogam.legalizacion.beans.LegalizacionCita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.estructuras.Fecha;
import utilidades.web.OegamExcepcion;

public class Utiles {

	@Autowired
	UtilesFecha utilesFecha;
	
	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public Utiles() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public boolean fueradeHorario() throws NumberFormatException, OegamExcepcion{
		boolean resultado = false;
		Fecha fecha = utilesFecha.getFechaHoraActualLEG();
		
		if(!utilesColegiado.tienePermisoAdmin()){
			if(Integer.parseInt(fecha.getHora())<Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesLegalizacion.HORA_INICIO)) 
					|| Integer.parseInt(fecha.getHora())>=Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesLegalizacion.HORA_FIN))){
				resultado = true;
			}
		}	
		return resultado;
	}
	
	public boolean permiteModificarPeticion(){
		LegalizacionDao legalizacionDao = new LegalizacionDaoImplHibernate(new LegalizacionCita());
		boolean resultado=true;
		
		try{
			LegalizacionCita legalizacion = legalizacionDao.getLegalizacionId(Integer.parseInt("32"));
			Fecha FechaLimitePresentacionEnColegio = utilesFecha.getPrimerLaborableAnterior(utilesFecha.getFechaConDate(legalizacion.getFechaLegalizacion()));
			int resultadoComparacion = utilesFecha.compararFechaMayor(utilesFecha.getFechaActual(), FechaLimitePresentacionEnColegio);
			
			if (resultadoComparacion == 0){
				if (fueradeHorario()){
					resultado = false;
				}
			}
			
			if (resultadoComparacion == 1){
				resultado = false;
			}
			
		} catch(Throwable e){
			
		}
		
		return resultado;
	}
	
}
