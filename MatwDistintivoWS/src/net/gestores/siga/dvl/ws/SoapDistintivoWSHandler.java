package net.gestores.siga.dvl.ws;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.rpc.handler.HandlerInfo;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.w3c.dom.Document;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.ws.GenericWSHandler;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class SoapDistintivoWSHandler extends GenericWSHandler{
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(SoapDistintivoWSHandler.class);
	
	public static final String PROPERTY_KEY_NUMEXPEDIENTE = "numExpediente";
	public static final String PROPERTY_KEY_ID = "idVhNotMatOegam";
	public static final String PROPERTY_KEY_MATRICULA = "matricula";
	
	private final String PREF_NOMBRE_FICHERO = "DSTV_";
	private final String SUF_NOMBRE_FICHERO_REQ = "_REQ";
	private final String SUF_NOMBRE_FICHERO_RESP = "_RESP";
	
	private final String TIPO_DOCUMENTO = ConstantesGestorFicheros.MATE;
	private final String SUB_TIPO_DOCUMENTO = ConstantesGestorFicheros.MATE_PERMISO_DISTINTIVO_ENVIO;
	
	private BigDecimal numExpediente;
	private String idVhNotMatOegam;
	private String matricula;
	private String fecha;
	
	@Autowired
	UtilesFecha utilesFecha;

	public SoapDistintivoWSHandler() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public void init(HandlerInfo arg0) {
		super.init(arg0);
		if(arg0.getHandlerConfig() != null){
			if(arg0.getHandlerConfig().get(PROPERTY_KEY_NUMEXPEDIENTE) != null){
				numExpediente = (BigDecimal) arg0.getHandlerConfig().get(PROPERTY_KEY_NUMEXPEDIENTE);
			}
			if(arg0.getHandlerConfig().get(PROPERTY_KEY_ID) != null){
				idVhNotMatOegam = (String) arg0.getHandlerConfig().get(PROPERTY_KEY_ID);
			}
			if(arg0.getHandlerConfig().get(PROPERTY_KEY_MATRICULA) != null){
				matricula = (String) arg0.getHandlerConfig().get(PROPERTY_KEY_MATRICULA);
			}
			fecha = utilesFecha.formatoFecha("yyyyMMdd HHmmss", new Date()).replace(" ", "");
		}
	}

	@Override
	public String getTipoDocumentoRequest() {
		return TIPO_DOCUMENTO;
	}

	@Override
	public String getTipoDocumentoResponse() {
		return TIPO_DOCUMENTO;
	}

	@Override
	public String getSubtipoDocumentoRequest() {
		return SUB_TIPO_DOCUMENTO;
	}

	@Override
	public String getSubtipoDocumentoResponse() {
		return SUB_TIPO_DOCUMENTO;
	}

	@Override
	public String getNombreFicheroRequest() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO);
		if(numExpediente != null){
			sb.append(numExpediente).append("_"+fecha).append(SUF_NOMBRE_FICHERO_REQ);
		} else {
			sb.append(idVhNotMatOegam).append("_"+matricula).append("_"+fecha).append(SUF_NOMBRE_FICHERO_REQ);
		}
		return sb.toString();
	}

	@Override
	public String getNombreFicheroResponse() {
		StringBuilder sb = new StringBuilder(PREF_NOMBRE_FICHERO);
		if(numExpediente != null){
			sb.append(numExpediente).append("_"+fecha).append(SUF_NOMBRE_FICHERO_RESP);
		} else {
			sb.append(idVhNotMatOegam).append("_"+matricula).append("_"+fecha).append(SUF_NOMBRE_FICHERO_RESP);
		}
		return sb.toString();
	}

	@Override
	public void setDOMRequest(Document doc) {
		if (log.isDebugEnabled()) {
			log.debug("tratando peticion " + doc.getTextContent());
		}
	}

	@Override
	public void setDOMResponse(Document doc) {
		if (log.isDebugEnabled()) {
			log.debug("tratando respuesta " + doc.getTextContent());
		}
	}

	@Override
	public ILoggerOegam getLogger() {
		return log;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getIdVhNotMatOegam() {
		return idVhNotMatOegam;
	}

	public void setIdVhNotMatOegam(String idVhNotMatOegam) {
		this.idVhNotMatOegam = idVhNotMatOegam;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
}
