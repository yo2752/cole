package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;
import java.util.Date;

import org.gestoresmadrid.core.impr.model.vo.DocImprVO;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;

public interface ServicioDocImpr extends Serializable{

	public static final int longitudSecuencialDocPermDstvEitv = 9;

	public void borrarDocImpr(Long docId);

	public ResultadoDocImprBean generarDocImpr(Long idContrato, Date fechaImpr, String tipoImpr, String jefatura,
			Long idUsuario, String tipoTramite, String referenciaDocumento, String carpeta);

	public ResultadoDocImprBean impresionManual(Long id, Long idUsuario, String jefatura);

	public DocImprVO getDocImprPorId(Long id, Boolean completo);

	public ResultadoDocImprBean cambiarEstado(Long id, String estadoNuevo, Long idUsuario);

	public ResultadoDocImprBean reactivar(Long id, Long idUsuario, Boolean esEntornoAm);

	public ResultadoDocImprBean imprimir(Long id, Long idUsuario, String jefatura);
	
	

}
