package org.gestoresmadrid.oegam2comun.santanderWS.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.santanderWS.views.beans.BastidorWSBean;
import org.gestoresmadrid.oegam2comun.santanderWS.views.beans.ResultadoSantanderWS;

public interface ServicioWebServiceSantander extends Serializable{

	public static Integer TAMAÑO_BASTIDOR = 21;
	
	ResultadoSantanderWS procesarWSSantander(List<BastidorWSBean> convertirBastidorToListBastidorBean, List<BastidorWSBean> convertirBastidorToListBastidorBean2);

}
