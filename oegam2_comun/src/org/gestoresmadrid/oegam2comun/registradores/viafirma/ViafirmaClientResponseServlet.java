package org.gestoresmadrid.oegam2comun.registradores.viafirma;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSException;
import org.gestoresmadrid.oegam2comun.registradores.constantes.Constantes;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoContrato;
import org.gestoresmadrid.oegam2comun.registradores.utiles.Firmable;
import org.viafirma.cliente.ViafirmaClientServlet;
import org.viafirma.cliente.exception.CodigoError;
import org.viafirma.cliente.exception.InternalException;
import org.viafirma.cliente.vo.FirmaInfoViafirma;
import org.viafirma.cliente.vo.UsuarioGenericoViafirma;

/**
 * Invocado desde la página del applet
 */
public class ViafirmaClientResponseServlet extends ViafirmaClientServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8911176759163698301L;
	private static Logger log = Logger.getLogger(ViafirmaClientResponseServlet.class);

	public ViafirmaClientResponseServlet() {
		super();
	}

	@Override
	public void authenticateOK(UsuarioGenericoViafirma usuario,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getSession().setAttribute(Constantes.SESSION_KEY_CERTIFICATE, usuario);
			response.sendRedirect("Registrar.action");
		} catch (Exception e) {
			log.error("Error reading certificate", e);
			try {
				response.sendRedirect("Error.action");
			} catch (IOException e1) {
				log.error("Imposible to redirect to discriminatorError", e1);
			}
		}

	}

	@Override
	public void cancel(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getSession().setAttribute("mensajeError","ERROR: " + "Operación cancelada");
			response.sendRedirect("Error.action");
			return;
		} catch (Exception e) {
			try{
				log.error("SERVLET VIAFIRMA : cancel ", e);
				response.sendRedirect("Error.action");
				return;
			}catch(IOException ex){
				log.error("SERVLET VIAFIRMA : cancel ",  ex);
			}
		}
	}

	@Override
	public void error(CodigoError codError, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getSession().setAttribute("mensajeError","ERROR: " + codError.getMensaje());
			response.sendRedirect("Error.action");
			return;
		} catch (Exception e) {
			try{
				log.error("SERVLET VIAFIRMA : error ", e);
				response.sendRedirect("Error.action");
				return;
			}catch(IOException ex){
				log.error("SERVLET VIAFIRMA : error " + ex);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void signOK(FirmaInfoViafirma fiv, HttpServletRequest request, HttpServletResponse response) {
		
		log.info("SERVLET VIAFIRMA : Inicio - signOK");
		try {
			// Se recupera el documento de sesión
			Object firmable = request.getSession().getAttribute(Constantes.ID_SIGNABLE_ELEM);
			TipoContrato contractType = (TipoContrato) request.getSession().getAttribute(Constantes.ID_CONTRACT_TYPE);
			
			String destino = null;
			if (firmable instanceof Firmable) {
				destino = signDossier(fiv, (Firmable) firmable, contractType);	
			} else {
				destino = signFiles(fiv, (List<Firmable>) firmable, contractType);
			}

			if (log.isDebugEnabled()) {
				log.debug("Destination: " + destino);
			}
			if (destino!=null) {
				response.sendRedirect(destino);
			}
		} catch (Exception e) {
			try{
				log.error("SERVLET VIAFIRMA : Error [" + e.getMessage() + "]", e);
				request.getSession().setAttribute("mensajeError",e);
				response.sendRedirect("Error.action");
			}catch(IOException ex){
				log.error("SERVLET VIAFIRMA : Error [" + ex.getMessage() + "]", ex);
			}
		}
		
	}

	private String signFiles(FirmaInfoViafirma fiv, List<Firmable> firmables, TipoContrato contractType) throws InternalException, CMSException, IOException {
		for (Firmable firmable: firmables) {
			// Buscamos el primero que no tenga firma
			if (firmable.getSign() == null) {
				String firma = UtilesViafirma.getCMSSignatureWithOutTimeStamp(fiv.getSignId());
				firmable.setSign(firma);
				break;
			}
		}

		if (TipoContrato.L1.equals(contractType) || TipoContrato.L2.equals(contractType) )  {
			return "sign_SendLeasingDossierApplet.action";
		} if (TipoContrato.R1.equals(contractType) || TipoContrato.R2.equals(contractType) )  {
			return "sign_SendRentingDossierApplet.action";
		} else {
			return "sign_SendFinancingDossierApplet.action";
		}
			
	}

	private String signDossier(FirmaInfoViafirma fiv, Firmable firmable, TipoContrato contractType) throws InternalException, CMSException, IOException {
		String firma = UtilesViafirma.getCMSSignatureWithOutTimeStamp(fiv.getSignId());
		firmable.setSign(firma);

		if (TipoContrato.L1.equals(contractType) || TipoContrato.L2.equals(contractType) )  {
			return "send_SendLeasingDossierApplet.action";
		} if (TipoContrato.R1.equals(contractType) || TipoContrato.R2.equals(contractType) )  {
			return "send_SendRentingDossierApplet.action";
		} else {
			return "send_SendFinancingDossierApplet.action";
		}
	}

}
