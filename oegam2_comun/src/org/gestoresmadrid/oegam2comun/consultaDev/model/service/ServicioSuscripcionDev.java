package org.gestoresmadrid.oegam2comun.consultaDev.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.consultaDev.model.vo.SuscripcionDevVO;

import es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.Suscripcion;
import escrituras.beans.ResultBean;

public interface ServicioSuscripcionDev extends Serializable{
	
	public final String suscripcionDevDto = "suscripcionDevDto";

	ResultBean guardarSuscripcionesWSConsultaDev(Suscripcion[] suscripciones, Long idConsultaDev);
	ResultBean getSuscripcionesConsultaDev(Long idConsultaDev);
	List<SuscripcionDevVO> getSuscripcionDevVO(Long idConsultaDev, Boolean completo);

}
