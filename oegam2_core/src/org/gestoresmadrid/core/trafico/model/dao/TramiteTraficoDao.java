package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.presentacion.jpt.model.beans.BeanPresentadoJpt;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.hibernate.criterion.ProjectionList;

import hibernate.entities.trafico.TramiteTrafico;

public interface TramiteTraficoDao extends GenericDao<TramiteTraficoVO>, Serializable {

	TramiteTraficoVO getTramite(BigDecimal numExpediente, boolean tramiteCompleto);

	TramiteTraficoSolInteveVO getTramiteInteve(BigDecimal numExpediente, String codigoTasa);

	List<TramiteTraficoVO> findAll();

	BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	BigDecimal generarNumExpedienteFacturacionTasa(String numColegiado) throws Exception;

	String getTipoTramite(BigDecimal numExp);

	List<BeanPresentadoJpt> getTramitesDocumentoBase(String idDoc);

	List<TramiteTraficoVO> obtenerNoPresentados();

	List<TramiteTraficoVO> obtenerPresentados(Date fechaPresentado, String carpeta, String jefaturaJpt) throws Exception;

	List<TramiteTraficoVO> obtenerPresentadosNive(Date fechaPresentado, String jefaturaJpt) throws Exception;

	int getCantidadTipoEstadistica(Date fechaPresentado, String carpeta, String jefaturaJpt) throws Exception;

	int getCantidadTipoEstadisticaNive(Date fechaPresentado, String jefaturaJpt) throws Exception;

	int getNumColegiadosDistintos(Date fechaPresentado, String carpeta, String jefaturaJpt) throws Exception;

	int getNumColegiadosDistintosNive(Date fechaPresentado, String jefaturaJpt) throws Exception;

	int getNumTramitePorVehiculo(BigDecimal numExpediente, Long idVehiculo);

	List<Long> getListaTramitesMismoContratoPorExpediente(BigDecimal[] numExpedientes);

	String getTipoTramiteTraficoPorExpedientes(BigDecimal[] numExpedientes);

	List<TramiteTraficoVO> getListaTramitesAComprobarDatosSensibles(BigDecimal[] numExpedientes);

	List<TramiteTraficoVO> getListaIntervTrafPorExpedientes(BigDecimal[] numExpedientes);

	List<TramiteTraficoVO> getListaTramitesTraficoPorExpedientesSinFechaPresentacion(BigDecimal[] numerosExpedientes);

	List<TramiteTraficoVO> existeNumExpedientesEstados(BigDecimal[] numExpedientes, BigDecimal[] estados);

	List<TramiteTraficoVO> noExisteNumExpedientesEstados(BigDecimal[] numExps, BigDecimal[] estados);

	List<TramiteTraficoVO> comprobarTramitesEnEstados(BigDecimal[] numExp, BigDecimal[] estadoTramite);

	TramiteTraficoVO actualizarRespuestaMateEitv(String respuesta, BigDecimal numExpediente);

	List<TramiteTraficoVO> recuperaTramitesBloqueantes();

	List<Object[]> getListaTramitesPorFechas(Date fecha1, Date fecha2) throws Exception;

	int comprobarRespuestaTipoErrorValidoImprimir(BigDecimal numExp, String[] listaErrores);

	String obtenerNumColegiadoByNumExpediente(BigDecimal numExpediente);

	// TramiteTraficoVO asociarExpediente(BigDecimal numExpediente, String numExpedienteAsociado);

	int usuarioTienePermisoSobreTramites(BigDecimal[] numExpediente, Long idContrato);

	Object[] listarTramitesFinalizadosPorTiempo(String tipoTramite, List<BigDecimal> estadosOk, List<BigDecimal> estadosKo, List<String> tipoTransferencia, int segundos);

	List<TramiteTraficoVO> noExisteNumExpedienteEstados(BigDecimal numExpediente, BigDecimal[] estados);

	List<TramiteTraficoVO> getListaExpedientesPorTasa(String codigoTasa);

	List<TramiteTraficoVO> getTramiteClonar(BigDecimal[] numsExpedientes, Boolean tramiteCompleto);

	List<TramiteTraficoVO> getListaExpedientesPorListNumExp(BigDecimal[] numExpedientes, Boolean tramiteCompleto);

	List<TramiteTraficoVO> getListaTramitesPermisosPorDocId(Long idDocPermisos);

	List<TramiteTraficoVO> getListaTramitesFichasTecnicasPorDocId(Long idDocFichaTecnica);

	Integer getCountNumTramitesPermisos(Long idDocPerm);

	List<Object[]> getCountNumTramitesPermisosPorJefatura(List<Long> idDocFichas);

	Integer getCountNumTramitesFichas(Long idDocFichas);

	List<Object[]> getCountNumTramitesFichasPorJefatura(List<Long> idDocFichas);

	List<Long> getListaIdsContratosFinalizadosTelematicamentePorFecha(Date fechaPresentacion, String[] tiposTramites, Boolean esDocBase);

	List<TramiteTraficoVO> getListaTramitesPorNifTipoIntervienteYFecha(String nifInterviniente, Long idContrato, String tipoInterviniente, String tipoTramite, Date fechaPresentacion);

	List<TramiteTraficoVO> getlistaTramitesCtitFinalizadosPorContratoYFecha(Long idContrato, Date fechaPresentacion);

	List<TramiteTraficoVO> getListaTramitesPermisosPorDocIdErroneos(Long docId);

	List<TramiteTraficoVO> getListaTramitesFichasTecnicasPorDocIdErroneas(Long docId);

	List<TramiteTraficoVO> getListaTramitesKOPendientesPorJefatura(String jefatura);

	TramiteTrafico getTramiteTrafico(String numColegiado, String matricula, String tipoTramite);

	TramiteTraficoVO getTramitePorTasa(String codigoTasa);

	List<TramiteTraficoVO> getListaTramitesWS(Long idContrato, Date fechaInicio, Date fechaFin, String matricula, String bastidor, String numExpediente, String nifTitular);

	Integer getPosibleDuplicado(String bastidor, String nif, String tipoTramite, String numColegiado);

	TramiteTraficoVO primeraMatricula(Date fechaActual) throws Exception;

	TramiteTraficoVO ultimaMatricula(Date fechaActual) throws Exception;

	List<TramiteTraficoVO> getListaExpedientesGenDocBase(BigDecimal[] numExpedientes);

	List<TramiteTraficoVO> getListaTramitesDocBaseNocturno(Long idContrato, Date fechaPresentacion, String tipoTramite);

	List<TramiteTraficoVO> getListaTramiteTraficoVO(BigDecimal[] numExpedientes, Boolean tramiteCompleto);

	int numeroElementosConsultaTramite(Long idContrato, String estado, Date fechaAltaDesde, Date fechaAltaHasta, Date fechaPresentacionDesde, Date fechaPresentacionHasta, String tipoTramite,
			String refPropia, BigDecimal numExpediente, String nifTitular, String nifFacturacion, String bastidor, String matricula, String tipoTasa, String nive, String presentadoJPT, String conNive)
			throws Exception;

	List<Object> buscarConsultaTramite(Long idContrato, String estado, Date fechaAltaDesde, Date fechaAltaHasta, Date fechaPresentacionDesde, Date fechaPresentacionHasta, String tipoTramite,
			String refPropia, BigDecimal numExpediente, String nifTitular, String nifFacturacion, String bastidor, String matricula, String tipoTasa, String nive, String presentadoJPT, String conNive,
			int firstResult, int maxResult, String dir, String sort) throws Exception;

	List<Object[]> getTramitePorNRE() throws Exception;

	Integer getNumTramitePorColegiado(String numColegiado);

	List<Object[]> getListaResumenEstadisticaNRE06(Date fechaInicio, Date fechaFin) throws Exception;

	List<TramiteTraficoVO> listadoPantallasEstadisticas(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones);

	// TramiteTraficoVO asociarExpediente(BigDecimal numExpediente, String numExpedienteAsociado);

	// TramiteTraficoVO desAsociarExpediente(BigDecimal numExpediente);

	// List<String> getListaTramitesAsociadosExpediente(BigDecimal numExpediente);

	String obtenerRespuestaTramiteTrafico(BigDecimal numExpediente);

}
