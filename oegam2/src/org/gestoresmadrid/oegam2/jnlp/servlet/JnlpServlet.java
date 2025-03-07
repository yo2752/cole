package org.gestoresmadrid.oegam2.jnlp.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;

public class JnlpServlet extends HttpServlet {

	private static final long serialVersionUID = 1446354443463314740L;

	private static final String NOMBRE_FICH_IMPRESION = "DocumentosImpresos";

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	ServicioDistintivoVehNoMat servicioDistintivoVehNoMat;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	public JnlpServlet() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String docIds = (String) request.getParameter("docId");
		String docIdSeleccionado = obtenerExpedienteSeleccionado(docIds);
		DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPermDistFicha(docIdSeleccionado, Boolean.TRUE);

		if (EstadoPermisoDistintivoItv.Generado.getValorEnum().equals(docPermDistItvVO.getEstado().toString())) {
			File fichero = recuperarFichero(docIdSeleccionado, docPermDistItvVO);
			if (fichero != null) {
				finalizarImpresion(docPermDistItvVO, docIdSeleccionado);
				byte[] bArray = readFileToByteArray(fichero);
				String arg = new String(Base64.encodeBase64(bArray));
				String jnlp = getJnlp(arg, getUrl(request));
				try {
					response.setContentType("application/x-java-jnlp-file");
					out.print(jnlp);
				} finally {
					out.close();
				}
			}
		}
	}

	private void finalizarImpresion(DocPermDistItvVO docPermDistItvVO, String docId) {
		servicioDocPrmDstvFicha.actualizarEstado(docPermDistItvVO, EstadoPermisoDistintivoItv.IMPRESO_GESTORIA, utilesColegiado.getIdUsuarioSessionBigDecimal(), OperacionPrmDstvFicha.IMPRESO_GESTORIA);
		if (docPermDistItvVO != null && docPermDistItvVO.getListaDuplicadosDstvAsList() != null && !docPermDistItvVO.getListaDuplicadoDistintivos().isEmpty()) {
			servicioDistintivoVehNoMat.actualizarEstadosImpresionDstv(docPermDistItvVO.getIdDocPermDistItv(), EstadoPermisoDistintivoItv.IMPRESO_GESTORIA, utilesColegiado.getIdUsuarioSessionBigDecimal());
		} else {
			servicioTramiteTraficoMatriculacion.actualizarEstadoImpresionDocDistintivos(docPermDistItvVO.getIdDocPermDistItv(), EstadoPermisoDistintivoItv.IMPRESO_GESTORIA, utilesColegiado.getIdUsuarioSessionBigDecimal(), docPermDistItvVO.getDocIdPerm(), getIpConexion());
		}
	}

	private String getIpConexion() {
		String ipConexion = ServletActionContext.getRequest().getRemoteAddr();
		
	  if(ServletActionContext.getRequest().getHeader("client-ip") != null){
		  ipConexion = ServletActionContext.getRequest().getHeader("client-ip"); 
	  }
		 
		return ipConexion;
	}
	
	private String getJnlp(String valor, String url) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n" + "<jnlp spec=\"1.0+\" codebase=\"" + url + "\"> \n" + "<information> \n" + "<title>Jnlp Oegam Distintivos</title> \n" + "</information> \n"
				+ "<security> \n" + "<all-permissions/> \n" + "</security> \n"
				// + "<jnlp spec=\"1.0+\" codebase=\"\"> \n" + "<information> \n" + "<title>Jnlp Prueba</title> \n" + "</information> \n" + "<security> \n" + "<all-permissions/> \n" + "</security> \n"
				+ "<resources> \n" + "<j2se version=\"1.6+\" /> \n" + "<jar href=\"oegamDistintivos.jar\"/> \n" + "</resources> \n"
				+ "<application-desc main-class=\"org.gestoresmadrid.oegamDistintivos.main.Main\"> \n" + "<argument>" + valor + "</argument>  \n" + "</application-desc> \n" + "</jnlp>";
	}

	private String obtenerExpedienteSeleccionado(String valor) {
		String codSelecc = null;
		if (valor != null && !valor.isEmpty()) {
			return valor.split(";")[0];
		}
		return codSelecc;
	}

	private byte[] readFileToByteArray(File file) {
		FileInputStream fis = null;
		byte[] bArray = new byte[(int) file.length()];
		try {
			fis = new FileInputStream(file);
			fis.read(bArray);
			fis.close();
		} catch (IOException ioExp) {
			ioExp.printStackTrace();
		}
		return bArray;
	}

	private File recuperarFichero(String codigoSeleccionado, DocPermDistItvVO docPermDistItvVO) {
		String rutaFichero = "";
		if (StringUtils.isNotBlank(codigoSeleccionado)) {
			rutaFichero = NOMBRE_FICH_IMPRESION + "_" + docPermDistItvVO.getIdDocPermDistItv() + "_" + docPermDistItvVO.getContrato().getColegiado().getNumColegiado();
			try {
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.DISTINTIVOS, utilesFecha.getFechaConDate(docPermDistItvVO
						.getFechaAlta()), rutaFichero, ConstantesGestorFicheros.EXTENSION_PDF);
				return fichero.getFile();
			} catch (Throwable th) {

			}
		}
		return null;
	}

	private String getUrl(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
	}
}
