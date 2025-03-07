package org.gestoresmadrid.oegam2comun.distintivo.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.trafico.materiales.model.values.ConsumoMaterialValue;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.dto.VehiculoNoMatriculadoOegamDto;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.DuplicadoDstvBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

public interface ServicioDistintivoVehNoMat extends Serializable {

	public static final String SOLICITUD_DUPLICADO_DSTV = "DUPLICADODSTV";
	public final String NOMBRE_HOST_SOLICITUD_2 = "nombreHostSolicitudProcesos2";

	ResultadoDistintivoDgtBean guardarVehNoMatOegam(VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoDto,BigDecimal idUsuario);

	ResultadoDistintivoDgtBean getVehNoMatrPantalla(Long idVehNotMatOegam);

	ResultadoDistintivoDgtBean generarDemandaDistintivos(String[] idsVehNotMatOegam, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean solicitarDstv(VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoDto, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean solicitarImpresionDstv(VehiculoNoMatriculadoOegamDto vehiculoNoMatrOegamDto, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean revertirDistintivo(Long idVehNotMatOegam, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean cambiarEstadoDstv(Long idVehNotMatOegam, String estadoNuevo, BigDecimal idUsuario);

	VehNoMatOegamVO getVehNoMatOegamVO(Long idVehNotMatOegam);

	ResultadoDistintivoDgtBean guardarPdfDstv(byte[] distintivoPdf, VehNoMatOegamVO vehNoMatOegamVO);

	ResultadoDistintivoDgtBean actualizarRecepcionDstv(VehNoMatOegamVO vehNoMatOegamVO, Boolean tieneDstv, String tipoDistintivo, BigDecimal idUsuario);

	void actualizarEstadoProceso(Long idVehNotMatOegam, EstadoPermisoDistintivoItv estadoNuevo, String respuesta, BigDecimal idUsuario);

	List<VehNoMatOegamVO> getListaVehiculoDistintivosPorDocId(Long idDocPermDistItv);

	List<VehNoMatOegamVO> getListaVehiculosPdteImpresionPorContrato(Long idContrato);

	ResultadoDistintivoDgtBean indicarDocIdVehiculos(List<Long> listaDuplicados, Long idDocPermDstvEitv, BigDecimal idUsuario, Date fecha, String docId);

	Integer getCountNumVehNotMatOegamDstv(Long idDocPermDistItv);

	ResultadoPermisoDistintivoItvBean actualizarEstadosImpresionDstv(Long idDocPermDistItv,	EstadoPermisoDistintivoItv estado, BigDecimal idUsuario);

	List<VehiculoNoMatriculadoOegamDto> getListaVehiculoDistintivosDtoPorDocId(Long idDoc);

	List<Long> getListaIdsContratosConDistintivosDuplicados();

	int comprobarMatriculaDoc(String matricula);

	String getIdDocPorMatricula(String matricula);

	HashMap<String, List<ConsumoMaterialValue>> getMaterialesImpresos(HashMap<String, List<Long>> docDistintivos);

	List<ConsumoMaterialValue> getListaConsumoDstvDuplicadosJefaturaPorDia(String jefatura, Date fecha);

	ResultadoDistintivoDgtBean autorizarImpresionDstv(VehiculoNoMatriculadoOegamDto vehiculoNoMatriculadoDto, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean guardarImportacionDstv(DuplicadoDstvBean duplicadoDstvBean, ContratoDto contrato, BigDecimal idUsuario);

	void actualizarEstadoImpresionDstv(Long idVehNotMat, String estadoAnt, String estadoNuevo, BigDecimal idUsuario, Date fecha, String docId);

}