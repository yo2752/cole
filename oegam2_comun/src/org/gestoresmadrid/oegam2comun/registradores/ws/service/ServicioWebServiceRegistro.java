package org.gestoresmadrid.oegam2comun.registradores.ws.service;

import java.io.IOException;
import java.math.BigDecimal;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;

import net.consejogestores.sercon.core.model.integracion.dto.RegistroResponse;
import utilidades.web.OegamExcepcion;

public interface ServicioWebServiceRegistro {

	public RegistroResponse procesarEnvioTramiteSercon(ColaDto cola) throws OegamExcepcion, IOException, ServiceException, JAXBException, Exception;

	public void enviarCorreoJustificantePago(BigDecimal idTramite);

}
