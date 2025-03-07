package servlets;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioCertifCargo;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.CertifCargoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import utilidades.web.OegamExcepcion;

public class SignComplete extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// log de errores
	private static final Logger log = Logger.getLogger(SignComplete.class);
	
	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public SignComplete() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	public ServicioCorreo getServicioCorreo() {
		if(servicioCorreo == null){
			servicioCorreo = ContextoSpring.getInstance().getBean(ServicioCorreo.class);
		}
		return servicioCorreo;
	}

	// Método que se ejecuta cuando el servlet de viafirma devuelve el control a la aplicación despues del proceso de firma
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Recupera de la sesión el nif del certificante que acaba de firmar y el identificador del proceso de firma:
		// así como el identificador del trámite:
		String nifCertificante = (String) request.getSession().getAttribute("nifCertificante");
		String idFirmaCertificante = (String) request.getSession().getAttribute("idFirmaCertificante");
		String idTramite = (String) request.getSession().getAttribute("idTramite");
		String idContrato = (String) request.getSession().getAttribute("idc");
		log.info("SIGCER : Se ha firmado correctamente el tramite con identificador : " + idTramite);
		// Guarda en base de datos el identificador de la firma asociado al certificante:
		try {
			ServicioCertifCargo servicioCertifCargo = ContextoSpring.getInstance().getBean(ServicioCertifCargo.class);
			CertifCargoDto cerCargo = servicioCertifCargo.getCertificanteDto(new BigDecimal(idTramite), null, nifCertificante, null, idFirmaCertificante);
			if (cerCargo == null) {
				ServicioTramiteRegistro servicioTramiteRegistro = ContextoSpring.getInstance().getBean(ServicioTramiteRegistro.class);
				ResultBean result = servicioTramiteRegistro.firmar(new BigDecimal(idTramite), nifCertificante, idFirmaCertificante, null);

				if (result != null && !result.getError()) {
					ContratoVO contrato = utilesColegiado.getContratoDelColegiado(new BigDecimal(idContrato));

					Boolean firmadoCompleto = (Boolean) result.getAttachment(ServicioCertifCargo.FIRMADO);
					enviarCorreo(contrato, idTramite, nifCertificante, firmadoCompleto);

					request.setAttribute("mensaje", "Se ha completado con éxito el proceso de firma");
					request.getRequestDispatcher("/result.jsp").forward(request, response);
					log.info("SIGCER : Se ha grabado correctamente el idFirmaCertificante del tramite : " + idTramite);
					return;
				} else {
					log.error("SIGCER : Se ha producido el siguiente error grabando el idFirmaCertificante : " + result.getMensaje());
					request.setAttribute("mensaje", result.getMensaje());
					if (result.getMensaje() != null && result.getMensaje().contains("El certificado con el que quiere firmar no corresponde " + "con ningun certificante del tramite")) {
						request.setAttribute("mensaje", "El certificado con el que quiere firmar no corresponde " + "con ningun certificante del tramite");
						request.setAttribute("recargar", "applet");
						request.setAttribute("botonSeleccionCertificado", true);
					}
					request.getRequestDispatcher("/result.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("mensaje", "El documento ya esta firmado, no se puede volver a firmar");
				request.getRequestDispatcher("/result.jsp").forward(request, response);
			}
			log.info("SIGCER : Fin del proceso de firma del documento");
		} catch (Exception e) {
			log.error("SIGCER : Se ha lanzado la siguiente excepcion : " + e);
		}
	}

	private void enviarCorreo(ContratoVO contrato, String idTramite, String nifCertificante, Boolean firmadoCompleto) {
		String subject = null;
		try {

			if (Boolean.TRUE.equals(firmadoCompleto)) {
				subject = "Proceso firmas certificantes completado para el trámite de registro " + idTramite;
			} else {
				subject = "Un certificante ha firmado el trámite de registro " + idTramite;
			}
			
			String entorno = ("Entorno");
			if(!"PRODUCCION".equals(entorno)){
				subject = entorno + ": " + subject;
			}

			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">El certificante con nif <b>" + nifCertificante + "</b>");
			texto.append(" ha firmado el trámite de registro con identificador <b>" + idTramite + "</b>.");

			if (Boolean.TRUE.equals(firmadoCompleto)) {
				texto.append("El proceso de firmas se ha completado. ");
				texto.append("El trámite de registro con identificador <b>" + idTramite + "</b> ha pasado a estado <b>Firmado</b>.");
			}

			texto.append("<br><br></span>");

			ResultBean resultado;			
			resultado = getServicioCorreo().enviarCorreo(texto.toString(), null, null, subject, contrato.getCorreoElectronico(), null, null, null, null);
			if(resultado==null || resultado.getError())
				throw new OegamExcepcion("Error en la notificacion de error servicio");
						
			if (resultado.getError()) {
				log.error("No se ha enviado el correo para los cambios de estado de registradores. Error: " + resultado.getMensaje());
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("No se enviaron correos dpara los cambios de estado de registradores", e);
		}
	}
}
