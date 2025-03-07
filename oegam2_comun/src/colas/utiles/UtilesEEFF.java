package colas.utiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.handler.HandlerInfo;

import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.trafico.cliente.vehiculos.custodiaitv.webservice.SecurityClientHandlerEEFF;
import es.trafico.cliente.vehiculos.custodiaitv.webservice.SolicitudOperacionesITVWSService;

public class UtilesEEFF {

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	
	public UtilesEEFF() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}


	@SuppressWarnings("unchecked")
	public void getHandlerFirmado(String sAlias, SolicitudOperacionesITVWSService soOperacionesITVWSService) {
		
		// Añadimos los handlers
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_URL), "SolicitudOperacionesITVWS");
		List<HandlerInfo> list = null;
		if(soOperacionesITVWSService.getHandlerRegistry().getHandlerChain(portName) != null && soOperacionesITVWSService.getHandlerRegistry().getHandlerChain(portName).size() == 0){
			list = soOperacionesITVWSService.getHandlerRegistry().getHandlerChain(portName);
			
			Map<String, String> securityConfig = new HashMap<String, String>();
			securityConfig.put(SecurityClientHandlerEEFF.ALIAS_KEY, sAlias);
			// Handler de firmado
			HandlerInfo signerHandlerInfo = new HandlerInfo();
			signerHandlerInfo.setHandlerClass(SecurityClientHandlerEEFF.class);
			signerHandlerInfo.setHandlerConfig(securityConfig);
			list.add(signerHandlerInfo);
		}
	}
}
