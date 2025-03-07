package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

public interface ServicioFichasTecnicasDgt extends Serializable{

	public static final String NOMBRE_HOST = "nombreHostProceso";
	
	ResultadoPermisoDistintivoItvBean solicitarFichaTecnica(BigDecimal numExpediente, BigDecimal idUsuario, Date fechaSol, String ipConexion);

	ResultadoPermisoDistintivoItvBean impresionFichas(String numExpedientes, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean revertirFichas(BigDecimal numExpediente, BigDecimal idUsuario, Date fecha, String ipConexion);

	List<TramiteTraficoVO> getListaTramitesPorDocId(Long idDocFichaTecnica);

	void actualizarEstadoPeticion(BigDecimal numExpediente, EstadoPermisoDistintivoItv estadoNuevo, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario, Date fechaSol, BigDecimal estadoNuevo, String ipConexion);

	ResultadoPermisoDistintivoItvBean generarDocFichasContratoCoches(ContratoDto contratoDto, Date fecha, String ipConexion);

	ResultadoPermisoDistintivoItvBean generarDocFichasContratoMotos(ContratoDto contratoDto, Date fecha, String ipConexion);

	ResultadoDistintivoDgtBean desasignarFicha(BigDecimal numExpediente, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean generarKoTramite(BigDecimal numExpediente, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean generarDocFichasContratoCochesDupli(ContratoDto contratoDto, Date fechaPresentacion, String ipConexion);


}
