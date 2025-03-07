package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.GenKoBean;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ResultadoConsultaKoBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.docPermDistItv.view.bean.TramitesPermDistItvBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.DetalleDocPrmDstvFichaDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.NumeroSerieBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.FacturacionStockBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.ResultadoFacturacionStockBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;

public interface ServicioDocPrmDstvFicha extends Serializable {

	public static final int longitudSecuencialDocPermDstvEitv = 9;
	public static String RUTA_DIRECTORIO_DATOS = "rutaDirectorioDatos";
	public static final String SUBJECT_PERMISOS = "subject.generar.permisos";
	public static final String SUBJECT_FICHA = "subject.generar.eitv";
	public static final String SUBJECT_FICHA_PERMISOS = "subject.generar.fichaPermisos";
	public static final String NOMBRE_FICH_IMPRESION = "DocumentosImpresos";
	public static final String SUBJECT_PERMISO_IMPRESO = "subject.generar.permiso.impreso";
	public static final String SUBJECT_DISTINTIVO_IMPRESO = "subject.generar.distintivo.impreso";
	public static final String NOMBRE_HOST = "nombreHostProceso";

	ResultadoPermisoDistintivoItvBean generarDoc(BigDecimal idUsuario, Date fecha, TipoDocumentoImprimirEnum tipoDocumento, TipoDistintivo tipoDistintivo, Long idContrato, String jefaturaProvincial,
			Boolean esDemanda);

	ResultadoPermisoDistintivoItvBean generarDocGestoriaPrmFicha(List<TramiteTraficoVO> listaTramites, TipoDocumentoImprimirEnum tipoDocumento, String docId, Date fecha);

	ResultadoDistintivoDgtBean generarDocGestoriaDistintivo(List<TramiteTrafMatrVO> listaTramites, DocPermDistItvVO docPermDistItvVO);

	ResultadoPermisoDistintivoItvBean imprimirDocFichas(DocPermDistItvVO docPermDistFichaVO, BigDecimal idUsuario, Boolean esAuto, Boolean esColegio, String ipConexion);

	ResultadoPermisoDistintivoItvBean validarDocMismoTipo(String[] sDocId);

	DocPermDistItvVO getDocPermDistFicha(String docId, Boolean completo);

	ResultadoPermisoDistintivoItvBean imprimirDocDistintivo(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr, String ipConexion);

	ResultadoPermisoDistintivoItvBean imprimirDocPermiso(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr, String ipConexion);

	ResultadoPermisoDistintivoItvBean descargarPdf(DocPermDistItvVO docPermDistItvVO);

	ResultadoPermisoDistintivoItvBean descargarPdfFicha(DocPermDistItvVO docPermDistItvVO, int cont);

	ResultadoPermisoDistintivoItvBean enviarMailImpresionDocumento(DocPermDistItvVO docPermDistItvVO);

	void actualizarNumDescargarFicha(DocPermDistItvVO docPermDistItvVO) throws Exception;

	List<TramiteTrafMatrDto> getListaTramitesDistintivos(Long idDocPermDistItv);

	List<TramiteTrafDto> getListaTramitesPermisos(Long idDocPermDistItv);

	List<TramiteTrafDto> getListaTramitesFichasTecnicas(Long idDocPermDistItv);

	ResultadoPermisoDistintivoItvBean actualizarNumSerie(DocPermDistItvVO docPermDistItvVO, NumeroSerieBean numeroSerieBean);

	ResultadoPermisoDistintivoItvBean descargarPdfFichasPermisos(DocPermDistItvVO docPermDistItvVO);

	ResultadoPermisoDistintivoItvBean reactivarDoc(String docId, BigDecimal idUsuario);

	ResultadoPermisoDistintivoItvBean imprimirDocFichaPermiso(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, Boolean esAuto, NumeroSerieBean numeroSeriePermiso, Boolean esColegio,
			String ipConexion);

	ResultadoPermisoDistintivoItvBean generarDocGestoriaPrmFichaProceso(List<TramitesPermDistItvBean> listaTramitesPdf, DocPermDistItvVO docPermDistItvVO, ContratoVO contrato, Boolean esDesdeError,
			String esConNive, String tipoTramite);

	DocPermDistItvVO getDocPorId(Long idDoc, Boolean completo);

	ResultadoPermisoDistintivoItvBean generarDocGestoriaErroresTramites(List<TramiteTraficoVO> listaTramites, DocPermDistItvVO docPermDistItvVO, TipoDocumentoImprimirEnum tipoDocumento,
			String tipoTramite, ContratoVO contrato, String esSinNive);

	void actualizarEstado(DocPermDistItvVO docPermDistItvVO, EstadoPermisoDistintivoItv estadoNuevo, BigDecimal idUsuario, OperacionPrmDstvFicha operacion);

	void borrarDoc(DocPermDistItvVO docPermDistItvNuevo);

	List<TramiteTrafMatrVO> getListaTramitesDistintivosVO(Long idDocPermDistItv);

	List<VehNoMatOegamVO> getListaTramitesDuplicadosDstv(Long idDocPermDistItv);

	ResultadoDistintivoDgtBean generarDocGestoriaDistintivoDuplicado(List<VehNoMatOegamVO> listaVehiculos, DocPermDistItvVO docPermDistItvVO);

	ResultadoDistintivoDgtBean generarDocGestoriaDstvProceso(List<TramiteTrafMatrVO> listaTramites, List<VehNoMatOegamVO> listaDuplicados, DocPermDistItvVO docPermDistItvVO, String tipoSolicitud);

	ResultadoFacturacionStockBean obtenerFacturacionDocumentos(FacturacionStockBean facturacionStock);

	List<DetalleDocPrmDstvFichaDgtBean> getListaDetalleDocDstv(Long idDoc);

	List<DetalleDocPrmDstvFichaDgtBean> getListaDetalleDocFichaPermiso(Long idDocPermDistItv);

	ResultadoPermisoDistintivoItvBean generarDocGestoriaPrmFichaDemandaKO(List<TramitesPermDistItvBean> listaTramitesBean, DocPermDistItvVO docPermDistItvVO, TramiteTraficoVO tramiteTraficoVO);

	List<String> getListaJefaturasImpresionDia(Date fecha);

	ResultadoPermisoDistintivoItvBean cambiarEstado(String docId, BigDecimal estadoNuevo, BigDecimal idUsuario);

	void finalizarImpresionGestor(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean imprimirDocDistintivoJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr);

	ResultadoPermisoDistintivoItvBean imprimirDocPermisoJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr);

	ResultadoPermisoDistintivoItvBean imprimirDocFichasJefatura(DocPermDistItvVO docPermDistItvVO, BigDecimal idUsuario, String jefaturaImpr);

	ResultadoPermisoDistintivoItvBean reactivarDocJefatura(String docId, BigDecimal idUsuario, String jefaturaImpr);

	ResultadoPermisoDistintivoItvBean anular(String docId, BigDecimal idUsuario, String ipConexion);

	ResultadoConsultaKoBean generarDocGestoriaKO(GenKoBean genKoBean, Long idUsuario, String tipo, String tipoTramite, String jefatura, Date fechaGenExcel, ContratoVO contrato);

	ResultadoPermisoDistintivoItvBean reiniciarDocImpr(String docId, BigDecimal idUsuario);

}
