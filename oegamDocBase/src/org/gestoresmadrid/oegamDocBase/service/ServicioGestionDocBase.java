package org.gestoresmadrid.oegamDocBase.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.docbase.model.vo.DocumentoBaseVO;
import org.gestoresmadrid.oegamDocBase.view.bean.DocBaseCarpetaTramiteBean;
import org.gestoresmadrid.oegamDocBase.view.bean.GestionDocBaseBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;

public interface ServicioGestionDocBase extends Serializable {

	public final String RUTA_FICH_TEMP = "RUTA_ARCHIVOS_TEMP";
	public final String NOMBRE_ZIP = "DOCUMENTOS_BASE";

	ResultadoDocBaseBean procesarPeticionDocBase(Long idImpresion, Long idContrato, String xmlEnviar, Long idUsuario);

	ResultadoDocBaseBean imprimirDocBase(Long idDocBase);

	ResultadoDocBaseBean gestionDocBaseNocturno();

	ResultadoDocBaseBean eliminarDocBase(String idDocBase, Boolean tienePermisoAdmin, Boolean esUsuarioTrafico);

	void borrarFichero(File fichero);

	ResultadoDocBaseBean descargarDocBase(String idDocBase);

	List<GestionDocBaseBean> convertirListaEnBeanPantallaConsulta(List<DocumentoBaseVO> listaBBDD);

	void borrarImpresionDocBase(Long idImpresion, Long idUsuario);

	void enviarMailError(Long idImpresion, Long idContrato);

	DocBaseCarpetaTramiteBean getDocBaseParaTramite(BigDecimal numExpediente);

	ResultadoDocBaseBean reimprimirDocBaseErroneo(String idDocBase, boolean tienePermisoAdmin, boolean esUsuarioTrafico);

	ResultadoDocBaseBean cambiarEstadoDocBase(String idDocBase, String estadoNuevo, boolean tienePermisoAdmin, boolean esUsuarioTrafico);
}
