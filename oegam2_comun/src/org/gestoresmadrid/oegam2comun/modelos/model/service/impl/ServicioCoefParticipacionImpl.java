package org.gestoresmadrid.oegam2comun.modelos.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioCoefParticipacion;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.CoefParticipacionDto;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;

@Service
public class ServicioCoefParticipacionImpl implements ServicioCoefParticipacion{

	private static final long serialVersionUID = -2148086488692231239L;
	
	@Override
	public ResultBean getListasCoefParticipacion(RemesaDto remesa) {
		ResultBean resultado = null;
		List<CoefParticipacionDto> listaPorctSujPasivo = null;
		List<CoefParticipacionDto> listaPorctTransmitentes = null;
		if(remesa != null && remesa.getModelo() != null && remesa.getModelo().getModelo() != null){
			if(Modelo.Modelo600.equals(remesa.getModelo().getModelo() )){
				
			}else if(Modelo.Modelo601.equals(remesa.getModelo().getModelo())){
			//	if(remesa.getBien() != null){
					if(remesa.getListaSujetosPasivos() != null && remesa.getListaSujetosPasivos().isEmpty()){
						
					}
					if(remesa.getListaTransmitentes() != null && remesa.getListaTransmitentes().isEmpty()){
						
					}
				//}
			}
		}else{
			resultado = new ResultBean(true, "No se han podido calcular los coeficientes de participación porque la remesa es nula.");
		}
		return resultado;
	}

}
