package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.values.ConsumoMaterialValue;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;

public interface TramiteTraficoMatrDao extends GenericDao<TramiteTrafMatrVO>, Serializable {

	public static final String TOTALES = "TOTALES";
	public static final String TOTAL_DELEGACIONES = "TODASDELEGACIONES";

	public static final String PROVINCIA_AVILA = "05";

	int recuperarTipoTramiteMatriculacionSiEsElMismo(BigDecimal[] numExpedientes);

	TramiteTrafMatrVO getTramiteTrafMatr(BigDecimal numExpediente, Boolean tramiteCompleto, Boolean permisoLiberacion);

	TramiteTrafMatrVO datosCardMatw(BigDecimal numExpediente);

	List<TramiteTrafMatrVO> getListaTramitesPorExpediente(BigDecimal[] listaNumsExpedientes, Boolean tramiteCompleto);

	HashMap<String, List<ConsumoMaterialValue>> getMaterialesImpresos(HashMap<String, List<Long>> docDistintivos);

	List<TramiteTrafMatrVO> getMatriculacionesPorFecha(Date fecha);

	List<TramiteTrafMatrVO> getMatriculacionPorCifContratoYFecha(String cif, Date fecha);

	List<TramiteTrafMatrVO> getMatriculacionPorIdContratoFecha(Long idContrato, Date fecha);

	List<Long> getListaIdsContratosConDistintivos();

	List<TramiteTrafMatrVO> getListaTramitesContratoDistintivos(Long idContrato);

	List<Long> getListaIDContratosConMatriculacionesPorFecha(Date fechaPresentacion);

	List<TramiteTrafMatrVO> getDistintivosPendientesImpresionSinSolicitar(Date fechaPresentacion);

	List<Long> getListaIdsContratosSinDstvFinalizados(Date fechaPresentacion);

	List<TramiteTrafMatrVO> getListaTramitesContratoDistintivosId(Long IdDocPermDistItv);

	List<TramiteTrafMatrVO> getListaTramitesPorDocId(Long docId);

	Integer getCountNumTramitesDstv(Long idDocPermDistItv);

	Integer comprobarTramitesEmpresaST(Long idContrato, Date fecha, String cifEmpresa, String codigoPostal, String municipio, String provincia);

	Integer comprobarTramitesST(Long idContrato, Date fecha, String tipoTramiteSolicitud);

	List<TramiteTrafMatrVO> getlistaTramiteSTPorContratoYFecha(Long idContrato, Date fechaPresentacion, String tipoTramiteSolicitud0);

	List<ConsumoMaterialValue> getListaConsumoDstvJefaturaPorDia(String jefatura, Date fecha);

	List<TramiteTrafMatrVO> getTramitePorMatriculaContrato(String matricula, Long idContrato);

	List<TramiteTrafMatrVO> getListaTramitesPorMatricula(String matricula);

	List<String> listaMatriculasPorDocDisintintivo(Long docDistintivo);

	// Estadísticas

	Integer obtenerTotalesTramitesMATE(boolean esTelematico, String delegacion, Date fechaDesde, Date fechaHasta);

	List<Object[]> obtenerTramitesMATETelJefatura(String provincia, Date fechaDesde, Date fechaHasta);

	List<Object[]> obtenerTramitesMATETelTipoVehiculo(String provincia, Date fechaDesde, Date fechaHasta);

	List<Object[]> obtenerTramitesMATETelCarburante(String provincia, Date fechaDesde, Date fechaHasta);

	Integer obtenerTramitesMATEFinalizadoPdfVehiculosSiFichaTecnica(String provincia, String jefatura, Date fechaDesde, Date fechaHasta, boolean fichaTecnica);

	List<Object[]> obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos(String provincia, String jefatura, Date fechaDesde, Date fechaHasta);

	Integer comprobarTramitesFinalizadosTelematicamente(Long idContrato, Date fechaPresentacion, String tipoTramiteSolicitud);

	List<TramiteTrafMatrVO> getListaTramiteFinalizadosTelematicamenteSinImpr(Long idContrato, Date fechaPresentacion, String tipoTramiteSolicitud);

	List<TramiteTrafMatrVO> listaTramitesFinalizadosTelemPorContrato(Long idContrato, Date fecha, String tipoSolicitud, Boolean esMoto);
	
	int getNumElementosMasivos(Long idContrato, Date fecha);

	List<TramiteTrafMatrVO> getTramitesMasivos(Long idContrato, Date fecha);

	List<TramiteTrafMatrVO> getListaTramitesDistintivosWS(Long idContrato, Date fechaPresentacionInicio, Date fechaPresentacionFin,
			String matricula, String bastidor, String nive, String tipoDistintivo, String nifTitular, BigDecimal[] numExpedientes);
}
