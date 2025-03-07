package org.gestoresmadrid.oegamImpresion.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.view.bean.ConsultaImpresionBean;

public interface ServicioImpresionDocumentos extends Serializable {

	public final String NOMBRE_HOST = "nombreHostProceso";

	public final String GENERADO = "Generado";
	public final String ELIMINADO = "Eliminado";
	public final String IMPRESO = "Descargado";

	ImpresionVO getDatosImpresion(Long idImpresion);

	ImpresionVO getDatosImpresionPorNombreDocumento(String nombreDocumento);

	ResultadoImpresionBean crearImpresion(String[] numExpedientes, Long idContrato, Long idUsuario, String tipoDocumento, String tipoTramite, String tipoInterviniente);

	void generado(Long idImpresion, String nombreDocumento, List<BigDecimal> listaTramitesError, Long idUsuario);

	void imprimido(ImpresionVO impresion, Long idUsuario);

	void finalizarConError(Long idImpresion, Long idUsuario, String mensaje);

	ResultadoBean finalizarImpresion(Long idImpresion, ResultadoImpresionBean resultadoImpr, String tipoDocumento, Long idUsuario, ContratoVO contrato, String tipoTramite, String nombreFichero);

	File descargarFichero(String nombreDocumento, Long idUsuario);

	void eliminar(String nombreDocumento);

	ResultadoBean eliminarFichero(String nombreDocumento);

	ImpresionVO getDatosPorNombreDocumentoSinEliminado(String nombreDocumento);

	ImpresionVO getDatosPorNombreDocumentoGeneradoYDescargado(String nombreDocumento);

	ResultadoBean prepararFinalizarImpresion(List<BigDecimal> listaExpedientes, ResultadoImpresionBean resultadoImpr, String tipoDocumento, ContratoVO contrato, String tipoTramite,
			String nombreFichero, boolean firmarDocumento);

	List<ConsultaImpresionBean> convertirListaEnBeanPantallaConsulta(List<ImpresionVO> lista);

	ResultadoImpresionBean validarImpresionPantalla(String impreso, String tipoTramite, String[] numsExpedientes, BigDecimal idContrato, BigDecimal idUsuario);

	void gestionEnviarCorreo(ResultadoImpresionBean resultado, BigDecimal idContrato, String tipoTramite, String impreso);

	void gestionEnviarCorreoCau(ResultadoImpresionBean resultado, BigDecimal idImpresion);
}
