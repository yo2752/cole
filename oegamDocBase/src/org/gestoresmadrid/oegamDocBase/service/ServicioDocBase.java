package org.gestoresmadrid.oegamDocBase.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docbase.model.vo.DocumentoBaseVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamDocBase.view.bean.DocBaseBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;

public interface ServicioDocBase extends Serializable {

	public static final int longitudSecuencialDocBase = 6;

	ResultadoDocBaseBean generarDocBase(List<TramiteTraficoVO> listaTramites, BigDecimal idUsuario, Boolean tienePermisoAdmin);

	ResultadoDocBaseBean crearDocBase(String tipoCarpeta, List<BigDecimal> listaExpedientes, Long idContrato, Long idUsuario, Date fechaPresentacion, Long idImpresion, Boolean esNocturno);

	ResultadoDocBaseBean getDocBase(Long idDocBase, Boolean docBaseCompleto);

	ResultadoDocBaseBean actualizarImpresionDocBase(Long idDocBase, DocBaseBean docBaseFinal);

	void actualizar(DocumentoBaseVO docBase);

	ResultadoDocBaseBean eliminar(Long idDocBase);

	ResultadoDocBaseBean borrarPDFDocBase(Long idDocBase);

	ResultadoDocBaseBean descargarDocBase(Long idDocBase);

	ResultadoDocBaseBean reimprimirDocBaseErroneo(DocumentoBaseVO docBase, Long idUsuario);

}
