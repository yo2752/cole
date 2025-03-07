package trafico.beans.utiles;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.beans.Provincia;
import escrituras.utiles.enumerados.DecisionTrafico;
import general.beans.RespuestaGenerica;
import trafico.beans.CategoriaElectricaBean;
import trafico.beans.FabricacionBean;
import trafico.beans.PaisFabricacionBean;
import trafico.beans.PaisImportacionBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.VehiculoBean;
import trafico.beans.daos.BeanPQVehiculosGuardar;
import trafico.beans.daos.pq_vehiculos.BeanPQGUARDAR;
import trafico.beans.daos.pq_vehiculos.BeanPQGUARDAR_MATW;
import trafico.beans.jaxb.matw.dgt.tipos.TipoSN;
import trafico.utiles.enumerados.Alimentacion;
import trafico.utiles.enumerados.Carroceria;
import trafico.utiles.enumerados.CategoriaElectrica;
import trafico.utiles.enumerados.Combustible;
import trafico.utiles.enumerados.Fabricacion;
import trafico.utiles.enumerados.PaisFabricacion;
import trafico.utiles.enumerados.PaisImportacion;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class VehiculoBeanPQConversion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(VehiculoBeanPQConversion.class);

	private static final String TRUE = "true";
	private static final String FALSE = "false";

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public BeanPQVehiculosGuardar beanVehiculoPreverConvertirBeanPQ(VehiculoBean vehi,VehiculoBean prever, TramiteTraficoBean tramiteTrafico, BigDecimal idUsuario, BigDecimal idContrato, String numColegiado) {

		BeanPQVehiculosGuardar bean = convertirBeanToPQ(vehi,tramiteTrafico, idUsuario, idContrato, numColegiado);
		//MODIFICAR CUANDO EXISTAN LOS CAMPOS EN VehiculoBean
		bean.setP_PREV_MATRICULA(prever.getMatricula() != null && !prever.getMatricula().equals("")?prever.getMatricula() : null);
		bean.setP_PREV_BASTIDOR(prever.getBastidor() != null && !prever.getBastidor().equals("")?prever.getBastidor() : null);
		if (null!=prever.getMarcaBean()) {
			bean.setP_PREV_CODIGO_MARCA(prever.getMarcaBean()!=null && prever.getMarcaBean().getCodigoMarca()!=null?prever.getMarcaBean().getCodigoMarca():null);
		}

		if (prever.getModelo()!=null){
			bean.setP_PREV_MODELO(prever.getModelo()!=null && !prever.getModelo().equals("") && !prever.getModelo().equals("-1") ? prever.getModelo() : null);
		}else{
			bean.setP_PREV_MODELO(null);
		}

		if (null != prever.getCriterioConstruccionBean() && null!=prever.getCriterioConstruccionBean().getIdCriterioConstruccion()
				&& !"-1".equals(prever.getCriterioConstruccionBean().getIdCriterioConstruccion())) {
			bean.setP_PREV_ID_CONSTRUCCION(prever.getCriterioConstruccionBean().getIdCriterioConstruccion());
		}

		if (null != prever.getCriterioUtilizacionBean()&& null!=prever.getCriterioUtilizacionBean().getIdCriterioUtilizacion()
				&&!"-1".equals(prever.getCriterioUtilizacionBean().getIdCriterioUtilizacion())) {
			bean.setP_PREV_ID_UTILIZACION(prever.getCriterioUtilizacionBean().getIdCriterioUtilizacion());
		}

		bean.setP_PREV_CLASIFICACION_ITV(prever.getClasificacionItv()!=null && !prever.getClasificacionItv().equals("") && !prever.getClasificacionItv().equals("-1")?prever.getClasificacionItv():null);

		if (prever.getTipoTarjetaItvBean() != null) {
			bean.setP_PREV_TIPO_ITV(prever.getTipoItv());
		}

		return bean;
	}

	/*
	 * Método para convertir un VehiculoBean en un BeanPQVehiculosGuardar, para la BD
	 */
	public BeanPQVehiculosGuardar convertirBeanToPQ(VehiculoBean vehiculo, TramiteTraficoBean tramiteTrafico, BigDecimal idUsuario, BigDecimal idContrato, String numColegiado) {
		BeanPQVehiculosGuardar vehiculoPQ = new BeanPQVehiculosGuardar();

		vehiculoPQ.setP_ID_USUARIO(idUsuario);
		vehiculoPQ.setP_ID_CONTRATO_SESSION(idContrato);
		vehiculoPQ.setP_NUM_EXPEDIENTE(tramiteTrafico.getNumExpediente()!=null?tramiteTrafico.getNumExpediente():null);
		vehiculoPQ.setP_ID_CONTRATO(tramiteTrafico.getIdContrato()!=null?tramiteTrafico.getIdContrato():idContrato);
		vehiculoPQ.setP_TIPO_TRAMITE(tramiteTrafico.getTipoTramite()!=null?tramiteTrafico.getTipoTramite().getValorEnum():null);

		// Si viene el numColegiado lo guardo, si no viene lo guardo de sesion
		vehiculoPQ.setP_NUM_COLEGIADO(tramiteTrafico.getNumColegiado()!=null && !tramiteTrafico.getNumColegiado().equals("")?tramiteTrafico.getNumColegiado():numColegiado);

		vehiculoPQ.setP_ID_VEHICULO(vehiculo.getIdVehiculo());
		vehiculoPQ.setP_BASTIDOR(vehiculo.getBastidor()!=null && !vehiculo.getBastidor().equals("")?vehiculo.getBastidor():null);
		vehiculoPQ.setP_NIVE(vehiculo.getNive()!=null && !vehiculo.getNive().equals("")?vehiculo.getNive().toUpperCase():null);
		vehiculoPQ.setP_MATRICULA(vehiculo.getMatricula()!=null && !vehiculo.getMatricula().equals("")?vehiculo.getMatricula():null);

		// Controlar para que no haya NullPointerException
		vehiculoPQ.setP_CODIGO_MARCA(null);
		if (vehiculo.getMarcaBean()!=null && vehiculo.getMarcaBean().getCodigoMarca()!=null){
			vehiculoPQ.setP_CODIGO_MARCA(!vehiculo.getMarcaBean().getCodigoMarca().equals(new BigDecimal("-1"))?vehiculo.getMarcaBean().getCodigoMarca():null);
		}

		vehiculoPQ.setP_MODELO(null);
		if (vehiculo.getModelo()!=null){
			vehiculoPQ.setP_MODELO(vehiculo.getModelo() != null && !vehiculo.getModelo().equals("") && !vehiculo.getModelo().equals("-1") ? vehiculo.getModelo() : null);
		}

		vehiculoPQ.setP_TIPVEHI(vehiculo.getTipVehi()!=null ? vehiculo.getTipVehi().getValorEnum():null);
		vehiculoPQ.setP_CDMARCA(vehiculo.getCdMarca()!=null && !vehiculo.getCdMarca().equals("") && !vehiculo.getCdMarca().equals("-1") ? vehiculo.getCdMarca() : null);
		vehiculoPQ.setP_CDMODVEH(vehiculo.getCdModVeh()!=null && !vehiculo.getCdModVeh().equals("") && !vehiculo.getCdModVeh().equals("-1") ? vehiculo.getCdModVeh() : null);

		vehiculoPQ.setP_TIPO_VEHICULO(null);
		if (vehiculo.getTipoVehiculoBean() != null
				&& vehiculo.getTipoVehiculoBean().getTipoVehiculo() != null && !vehiculo.getTipoVehiculoBean().getTipoVehiculo().equals("") && !vehiculo.getTipoVehiculoBean().getTipoVehiculo().equals("-1")){
			vehiculoPQ.setP_TIPO_VEHICULO(vehiculo.getTipoVehiculoBean().getTipoVehiculo());
		}

		vehiculoPQ.setP_ID_SERVICIO(null);
		if (vehiculo.getServicioTraficoBean()!=null){
			vehiculoPQ.setP_ID_SERVICIO(vehiculo.getServicioTraficoBean().getIdServicio()!=null && !vehiculo.getServicioTraficoBean().getIdServicio().equals("") && !vehiculo.getServicioTraficoBean().getIdServicio().equals("-1") ? vehiculo.getServicioTraficoBean().getIdServicio() : null);
		}

		vehiculoPQ.setP_ID_SERVICIO_ANTERIOR(null);
		if (vehiculo.getServicioTraficoAnteriorBean()!=null){
			vehiculoPQ.setP_ID_SERVICIO_ANTERIOR(vehiculo.getServicioTraficoAnteriorBean().getIdServicio()!=null && !vehiculo.getServicioTraficoAnteriorBean().getIdServicio().equals("") && !vehiculo.getServicioTraficoAnteriorBean().getIdServicio().equals("-1")?vehiculo.getServicioTraficoAnteriorBean().getIdServicio():null);
		}

		vehiculoPQ.setP_VEHICULO_AGRICOLA(null!=vehiculo.isVehiculoAgricola() ? (vehiculo.isVehiculoAgricola() ? DecisionTrafico.Si.getValorEnum():DecisionTrafico.No.getValorEnum()) : null);
		vehiculoPQ.setP_VEHICULO_TRANSPORTE(null!=vehiculo.isVehiculoTransporte() ? (vehiculo.isVehiculoTransporte() ? DecisionTrafico.Si.getValorEnum():DecisionTrafico.No.getValorEnum()) : null);

		vehiculoPQ.setP_ID_COLOR(null);
		if (vehiculo.getColorBean()!=null){
			vehiculoPQ.setP_ID_COLOR(vehiculo.getColorBean().getValorEnum()!=null && !vehiculo.getColorBean().getValorEnum().equals("") && !vehiculo.getColorBean().getValorEnum().equals("-1")?vehiculo.getColorBean().getValorEnum():null);
		}

		vehiculoPQ.setP_ID_CARBURANTE(null);
		if (vehiculo.getCarburanteBean() != null && vehiculo.getCarburanteBean().getIdCarburante() != null
				&& !vehiculo.getCarburanteBean().getIdCarburante().equals("")
				&& !vehiculo.getCarburanteBean().getIdCarburante().equals("-1")) {
			vehiculoPQ.setP_ID_CARBURANTE(vehiculo.getCarburanteBean().getIdCarburante());
		}

		vehiculoPQ.setP_ID_LUGAR_ADQUISICION(null);
		if (vehiculo.getLugarAdquisicionBean()!=null){
			vehiculoPQ.setP_ID_LUGAR_ADQUISICION(vehiculo.getLugarAdquisicionBean().getIdAdquisicion()!=null && !vehiculo.getLugarAdquisicionBean().getIdAdquisicion().equals("") && !vehiculo.getLugarAdquisicionBean().getIdAdquisicion().equals("-1")?vehiculo.getLugarAdquisicionBean().getIdAdquisicion():null);
		}

		vehiculoPQ.setP_ID_CRITERIO_CONSTRUCCION(null);
		if (vehiculo.getCriterioConstruccionBean()!=null){
			vehiculoPQ.setP_ID_CRITERIO_CONSTRUCCION(vehiculo.getCriterioConstruccionBean().getIdCriterioConstruccion()!=null && !vehiculo.getCriterioConstruccionBean().getIdCriterioConstruccion().equals("") && !vehiculo.getCriterioConstruccionBean().getIdCriterioConstruccion().equals("-1")?vehiculo.getCriterioConstruccionBean().getIdCriterioConstruccion():null);
		}

		vehiculoPQ.setP_ID_CRITERIO_UTILIZACION(null);
		if (vehiculo.getCriterioUtilizacionBean()!=null){
			vehiculoPQ.setP_ID_CRITERIO_UTILIZACION(vehiculo.getCriterioUtilizacionBean().getIdCriterioUtilizacion()!=null && !vehiculo.getCriterioUtilizacionBean().getIdCriterioUtilizacion().equals("") && !vehiculo.getCriterioUtilizacionBean().getIdCriterioUtilizacion().equals("-1")?vehiculo.getCriterioUtilizacionBean().getIdCriterioUtilizacion():null);
		}

		vehiculoPQ.setP_ID_DIRECTIVA_CEE(null);
		if (vehiculo.getHomologacionBean()!=null){
			vehiculoPQ.setP_ID_DIRECTIVA_CEE(vehiculo.getHomologacionBean().getIdHomologacion() != null
					&& !vehiculo.getHomologacionBean().getIdHomologacion().equals("")
					&& !vehiculo.getHomologacionBean().getIdHomologacion().equals("-1") ?
							vehiculo.getHomologacionBean().getIdHomologacion() : null);
		}

		vehiculoPQ.setP_DIPLOMATICO(vehiculo.getDiplomatico()!=null && !vehiculo.getDiplomatico().equals("") ? (vehiculo.getDiplomatico().equals(TRUE)?"S":"N"):null);
		vehiculoPQ.setP_CODITV(vehiculo.getCodItv()!=null && !vehiculo.getCodItv().equals("")?vehiculo.getCodItv():null);
		vehiculoPQ.setP_POTENCIA_FISCAL(vehiculo.getPotenciaFiscal()!=null?vehiculo.getPotenciaFiscal():null);
		vehiculoPQ.setP_POTENCIA_NETA(vehiculo.getPotenciaNeta()!=null?vehiculo.getPotenciaNeta():null);
		vehiculoPQ.setP_POTENCIA_PESO(vehiculo.getPotenciaPeso()!=null?vehiculo.getPotenciaPeso():null);
		vehiculoPQ.setP_CILINDRADA(vehiculo.getCilindrada()!=null && !vehiculo.getCilindrada().equals("")?vehiculo.getCilindrada():null);
		vehiculoPQ.setP_CO2(vehiculo.getCo2()!=null && !vehiculo.getCo2().equals("")?vehiculo.getCo2():null);
		vehiculoPQ.setP_EXCESO_PESO(vehiculo.getExcesoPeso()!=null && !vehiculo.getExcesoPeso().equals("") ? (vehiculo.getExcesoPeso().equals(TRUE)?"S":"N"):null);
		vehiculoPQ.setP_TIPO_INDUSTRIA(vehiculo.getTipoIndustria()!=null && !vehiculo.getTipoIndustria().equals("") ? vehiculo.getTipoIndustria() : null);
		vehiculoPQ.setP_MTMA_ITV((vehiculo.getMtmaItv()!=null && !vehiculo.getMtmaItv().equals("") ? vehiculo.getMtmaItv() : null));
		vehiculoPQ.setP_VERSION(vehiculo.getVersion()!=null && !vehiculo.getVersion().equals("") ? vehiculo.getVersion() : null);
		vehiculoPQ.setP_VARIANTE(vehiculo.getVariante()!=null && !vehiculo.getVariante().equals("") ? vehiculo.getVariante() : null);

		// DRC@25-09-2012 Incidencia: 2358
		vehiculoPQ.setP_PLAZAS(vehiculo.getPlazas()!=null ? vehiculo.getPlazas() : null);
		vehiculoPQ.setP_TARA(vehiculo.getTara()!=null && !vehiculo.getTara().equals("") ? vehiculo.getTara() : null);
		vehiculoPQ.setP_PESO_MMA(vehiculo.getPesoMma()!=null && !vehiculo.getPesoMma().equals("") ? vehiculo.getPesoMma():null);

		vehiculoPQ.setP_ID_MOTIVO_ITV(null);
		if (vehiculo.getMotivoItv() != null) {
			vehiculoPQ.setP_ID_MOTIVO_ITV(vehiculo.getMotivoItv().getIdMotivo()!=null && !vehiculo.getMotivoItv().getIdMotivo().equals("") && !vehiculo.getMotivoItv().getIdMotivo().equals("-1")?vehiculo.getMotivoItv().getIdMotivo():null);
		}

		vehiculoPQ.setP_CLASIFICACION_ITV(vehiculo.getClasificacionItv()!=null && !vehiculo.getClasificacionItv().equals("")?vehiculo.getClasificacionItv():null);

		vehiculoPQ.setP_ID_TIPO_TARJETA_ITV(null);
		if (vehiculo.getTipoTarjetaItvBean() != null) {
			vehiculoPQ.setP_ID_TIPO_TARJETA_ITV(vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv()!=null && !vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals("") && !vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals("-1")?vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv():null);
		}

		vehiculoPQ.setP_CONCEPTO_ITV(vehiculo.getConceptoItv()!=null && !vehiculo.getConceptoItv().equals("") && !vehiculo.getConceptoItv().equals("-1")?vehiculo.getConceptoItv().toUpperCase():null);
		vehiculoPQ.setP_ESTACION_ITV(vehiculo.getEstacionItv()!=null && !vehiculo.getEstacionItv().equals("") && !vehiculo.getEstacionItv().equals("-1")?vehiculo.getEstacionItv():null);
		vehiculoPQ.setP_N_PLAZAS_PIE(vehiculo.getNumPlazasPie());

		vehiculoPQ.setP_N_HOMOLOGACION(null);
		if (vehiculo.getNumHomologacion() != null) {
			vehiculoPQ.setP_N_HOMOLOGACION(null == vehiculo.getNumHomologacion() ? null : vehiculo.getNumHomologacion());
		}
		// JRG: número de ruedas ya no es obligatorio en MateW. Hay que comprobar que no tenga null cuando se trate de una matriculación quien llame al método.
		if (vehiculo.getNumRuedas() != null) {
			vehiculoPQ.setP_N_RUEDAS(vehiculo.getNumRuedas());
		}
		vehiculoPQ.setP_N_SERIE(null);
		if (vehiculo.getNumSerie() != null) {
			vehiculoPQ.setP_N_SERIE(null == vehiculo.getNumSerie() ? null : vehiculo.getNumSerie());
		}

		vehiculoPQ.setP_ID_EPIGRAFE(null);
		if (vehiculo.getEpigrafeBean() != null) {
			vehiculoPQ.setP_ID_EPIGRAFE(vehiculo.getEpigrafeBean().getIdEpigrafe()!=null && !vehiculo.getEpigrafeBean().getIdEpigrafe().equals("")&&!vehiculo.getEpigrafeBean().getIdEpigrafe().equals("-1")?vehiculo.getEpigrafeBean().getIdEpigrafe():null);
		}

		if (vehiculo.getIntegradorBean() != null){
			if (vehiculo.getIntegradorBean().getTipoPersona()!=null){
				vehiculoPQ.setP_TIPO_PERSONA_INTE(vehiculo.getIntegradorBean().getTipoPersona().getValorEnum());
			}
			vehiculoPQ.setP_APELLIDO1_RAZON_SOCIAL_INTE(null==vehiculo.getIntegradorBean().getApellido1RazonSocial()?null:!vehiculo.getIntegradorBean().getApellido1RazonSocial().equals("")?vehiculo.getIntegradorBean().getApellido1RazonSocial():null);
			vehiculoPQ.setP_APELLIDO2_INTE(null==vehiculo.getIntegradorBean().getApellido2()?null:!vehiculo.getIntegradorBean().getApellido2().equals("")?vehiculo.getIntegradorBean().getApellido2():null);
			vehiculoPQ.setP_NOMBRE_INTE(null==vehiculo.getIntegradorBean().getNombre()?null:!vehiculo.getIntegradorBean().getNombre().equals("")?vehiculo.getIntegradorBean().getNombre():null);
			vehiculoPQ.setP_NIF_INTEGRADOR(vehiculo.getIntegradorBean().getNif()!=null && !vehiculo.getIntegradorBean().getNif().equals("")?vehiculo.getIntegradorBean().getNif():null);
		}

		vehiculoPQ.setP_VEHI_USADO(null);
		if (vehiculo.getVehiUsado()!=null){
			if (vehiculo.getVehiUsado().equals(TRUE)){
				vehiculoPQ.setP_VEHI_USADO("SI");
			} else if (vehiculo.getVehiUsado().equals(FALSE)){
				vehiculoPQ.setP_VEHI_USADO("NO");
			}
		}

		vehiculoPQ.setP_MATRI_AYUNTAMIENTO(vehiculo.getMatriAyuntamiento()!=null && !vehiculo.getMatriAyuntamiento().equals("")?vehiculo.getMatriAyuntamiento():null);

		vehiculoPQ.setP_TIPO_ITV(vehiculo.getTipoItv());
		try {
			vehiculoPQ.setP_LIMITE_MATR_TURIS(null);
			if (vehiculo.getLimiteMatrTuris()!=null){
				vehiculoPQ.setP_LIMITE_MATR_TURIS(vehiculo.getLimiteMatrTuris().getTimestamp());
			}

			vehiculoPQ.setP_FECHA_PRIM_MATRI(null);
			if (vehiculo.getFechaPrimMatri()!=null && !vehiculo.getFechaPrimMatri().isfechaNula()){
				vehiculoPQ.setP_FECHA_PRIM_MATRI(vehiculo.getFechaPrimMatri().getTimestamp());
			}

			vehiculoPQ.setP_FECHA_INSPECCION(null);
			if (vehiculo.getFechaInspeccion() != null) {
				vehiculoPQ.setP_FECHA_INSPECCION(vehiculo.getFechaInspeccion().getTimestamp());
			}

			vehiculoPQ.setP_FECHA_ITV(null);
			if (vehiculo.getFechaItv()!=null){
				vehiculoPQ.setP_FECHA_ITV(vehiculo.getFechaItv().getTimestamp());
			}

			vehiculoPQ.setP_FECDESDE(null);
			if (vehiculo.getFecDesde()!=null){
				vehiculoPQ.setP_FECDESDE(vehiculo.getFecDesde().getTimestamp());
			}

			vehiculoPQ.setP_FECHA_MATRICULACION(null);
			if (vehiculo.getFechaMatriculacion()!=null){
				vehiculoPQ.setP_FECHA_MATRICULACION(vehiculo.getFechaMatriculacion().getTimestamp());
			}

			vehiculoPQ.setP_FECHA_LECTURA_KM(null);
			if (vehiculo.getFechaLecturaKm()!=null){
				vehiculoPQ.setP_FECHA_LECTURA_KM(vehiculo.getFechaLecturaKm().getTimestamp());
			}
		} catch (ParseException e) {
			// Mantis 12982. David Sierra
			log.error("La fecha introducida no tiene un formato valido");
			// Fin Mantis 12982
		}

		vehiculoPQ.setP_ID_LUGAR_MATRICULACION(vehiculo.getLugarPrimeraMatriculacionBean()!=null
				&& vehiculo.getLugarPrimeraMatriculacionBean().getIdLugarPrimeraMatriculacion()!=null
				&& !vehiculo.getLugarPrimeraMatriculacionBean().getIdLugarPrimeraMatriculacion().equals("")
				? vehiculo.getLugarPrimeraMatriculacionBean().getIdLugarPrimeraMatriculacion()
						: null);

		vehiculoPQ.setP_KM_USO(null);
		if (vehiculo.getKmUso()!=null && (vehiculo.getVehiUsado()!=null && vehiculo.getVehiUsado().equals(TRUE)==Boolean.TRUE)){
			vehiculoPQ.setP_KM_USO(vehiculo.getKmUso());
		}

		vehiculoPQ.setP_HORAS_USO(null);
		if (vehiculo.getHorasUso()!=null && (vehiculo.getVehiUsado()!=null && vehiculo.getVehiUsado().equals(TRUE)==Boolean.TRUE)){
			vehiculoPQ.setP_HORAS_USO(vehiculo.getHorasUso());
		}

		vehiculoPQ.setP_N_CILINDROS(vehiculo.getNumCilindros());
		vehiculoPQ.setP_CARACTERISTICAS(vehiculo.getCaracteristicas()!=null && !vehiculo.getCaracteristicas().equals("")?vehiculo.getCaracteristicas():null);
		vehiculoPQ.setP_ANIO_FABRICA(vehiculo.getAnioFabrica()!=null && !vehiculo.getAnioFabrica().equals("")?vehiculo.getAnioFabrica():null);
		// -- Datos de la dirección del vehículo

		if (vehiculo.getDireccionBean()!=null){
			vehiculoPQ.setP_ID_DIRECCION(null==vehiculo.getDireccionBean().getIdDireccion()?null:!vehiculo.getDireccionBean().getIdDireccion().equals("")?new BigDecimal (vehiculo.getDireccionBean().getIdDireccion()):null);

			vehiculoPQ.setP_ID_PROVINCIA(null);
			if (vehiculo.getDireccionBean().getMunicipio()!=null && vehiculo.getDireccionBean().getMunicipio().getProvincia()!=null){
				vehiculoPQ.setP_ID_PROVINCIA(vehiculo.getDireccionBean().getMunicipio().getProvincia().getIdProvincia()!=null && !vehiculo.getDireccionBean().getMunicipio().getProvincia().getIdProvincia().equals("") && !vehiculo.getDireccionBean().getMunicipio().getProvincia().getIdProvincia().equals("-1")?vehiculo.getDireccionBean().getMunicipio().getProvincia().getIdProvincia():null);
			}

			vehiculoPQ.setP_ID_MUNICIPIO(null);
			if (vehiculo.getDireccionBean().getMunicipio()!=null){
				vehiculoPQ.setP_ID_MUNICIPIO(vehiculo.getDireccionBean().getMunicipio().getIdMunicipio()!=null && !vehiculo.getDireccionBean().getMunicipio().getIdMunicipio().equals("") && !vehiculo.getDireccionBean().getMunicipio().getIdMunicipio().equals("-1")?vehiculo.getDireccionBean().getMunicipio().getIdMunicipio():null);
			}

			vehiculoPQ.setP_PROVINCIA_PRIMERA_MATRICULA(null);
			if (vehiculo.getProvinciaPrimeraMatricula()!=null){
				vehiculoPQ.setP_PROVINCIA_PRIMERA_MATRICULA(new BigDecimal(vehiculo.getProvinciaPrimeraMatricula().getIdProvincia()));
			}

			vehiculoPQ.setP_ID_TIPO_VIA(null);
			if (vehiculo.getDireccionBean().getTipoVia()!=null){
				vehiculoPQ.setP_ID_TIPO_VIA(vehiculo.getDireccionBean().getTipoVia().getIdTipoVia()!=null && !vehiculo.getDireccionBean().getTipoVia().getIdTipoVia().equals("") && !vehiculo.getDireccionBean().getTipoVia().getIdTipoVia().equals("-1")?vehiculo.getDireccionBean().getTipoVia().getIdTipoVia():null);
			}

			vehiculoPQ.setP_NOMBRE_VIA(vehiculo.getDireccionBean().getNombreVia()!=null && !vehiculo.getDireccionBean().getNombreVia().equals("")?vehiculo.getDireccionBean().getNombreVia():null);
			vehiculoPQ.setP_NUMERO(vehiculo.getDireccionBean().getNumero()!=null && !vehiculo.getDireccionBean().getNumero().equals("")?vehiculo.getDireccionBean().getNumero():null);
			vehiculoPQ.setP_COD_POSTAL(vehiculo.getDireccionBean().getCodPostal()!=null && !vehiculo.getDireccionBean().getCodPostal().equals("")?vehiculo.getDireccionBean().getCodPostal():null);
			vehiculoPQ.setP_PUEBLO(vehiculo.getDireccionBean().getPueblo()!=null && !vehiculo.getDireccionBean().getPueblo().equals("") && !vehiculo.getDireccionBean().getPueblo().equals("-1")?vehiculo.getDireccionBean().getPueblo():null);
			vehiculoPQ.setP_LETRA(vehiculo.getDireccionBean().getLetra()!=null && !vehiculo.getDireccionBean().getLetra().equals("")?vehiculo.getDireccionBean().getLetra():null);
			vehiculoPQ.setP_ESCALERA(vehiculo.getDireccionBean().getEscalera()!=null && !vehiculo.getDireccionBean().getEscalera().equals("")?vehiculo.getDireccionBean().getEscalera():null);
			vehiculoPQ.setP_BLOQUE(vehiculo.getDireccionBean().getBloque()!=null && !vehiculo.getDireccionBean().getBloque().equals("")?vehiculo.getDireccionBean().getBloque():null);
			vehiculoPQ.setP_PLANTA(vehiculo.getDireccionBean().getPlanta()!=null && !vehiculo.getDireccionBean().getPlanta().equals("")?vehiculo.getDireccionBean().getPlanta():null);
			vehiculoPQ.setP_PUERTA(vehiculo.getDireccionBean().getPuerta()!=null && !vehiculo.getDireccionBean().getPuerta().equals("")?vehiculo.getDireccionBean().getPuerta():null);
			vehiculoPQ.setP_NUM_LOCAL(null==vehiculo.getDireccionBean().getNumLocal()?null:!vehiculo.getDireccionBean().getNumLocal().equals("")?new BigDecimal (vehiculo.getDireccionBean().getNumLocal()):null);

			// CONTROLAR LA CONVERSIÓN CORRECTA DE ESTOS ATRIBUTOS
			if (vehiculo.getDireccionBean().getPuntoKilometrico() == null){
				vehiculoPQ.setP_KM(null);
			}else{
				if ("".equals(vehiculo.getDireccionBean().getPuntoKilometrico())){
					vehiculoPQ.setP_KM(null);
				}else{
					vehiculoPQ.setP_KM(new BigDecimal(vehiculo.getDireccionBean().getPuntoKilometrico()));
				}
			}

			if (vehiculo.getDireccionBean().getHm() == null){
				vehiculoPQ.setP_HM(null);
			}else{
				if ("".equals(vehiculo.getDireccionBean().getHm())){
					vehiculoPQ.setP_HM(null);
				} else {
					vehiculoPQ.setP_HM(new BigDecimal(vehiculo.getDireccionBean().getHm()));
				}
			}
		}

		// MATE 2.5
		if(vehiculo.getNivelEmisiones() != null && !vehiculo.getNivelEmisiones().equals("")){
			vehiculoPQ.setP_EMISIONES(vehiculo.getNivelEmisiones());
		}
		if(vehiculo.getCodigoEco() != null && !vehiculo.getCodigoEco().equals("")){
			vehiculoPQ.setP_CODIGO_ECO(vehiculo.getCodigoEco());
		}
		if(vehiculo.getEcoInnovacion() != null && !vehiculo.getEcoInnovacion().equals("")){
			vehiculoPQ.setP_ECO_INNOVACION(vehiculo.getEcoInnovacion());
		}
		if(vehiculo.getReduccionEco() != null){
			vehiculoPQ.setP_REDUCCION_ECO(vehiculo.getReduccionEco());
		}
		if(vehiculo.getHomologacionBean() != null && vehiculo.getHomologacionBean().getIdHomologacion() != null
				&& !vehiculo.getHomologacionBean().getIdHomologacion().equals("")){
			vehiculoPQ.setP_ID_DIRECTIVA_CEE(vehiculo.getHomologacionBean().getIdHomologacion());
		}
		if(vehiculo.getFabricante() != null && !vehiculo.getFabricante().equals("")){
			vehiculoPQ.setP_FABRICANTE(vehiculo.getFabricante());
		}
		if(vehiculo.getAlimentacionBean() != null && vehiculo.getAlimentacionBean().getIdAlimentacion() != null
				&& !vehiculo.getAlimentacionBean().getIdAlimentacion().equals("")){
			vehiculoPQ.setP_ALIMENTACION(vehiculo.getAlimentacionBean().getIdAlimentacion());
		}
		if(vehiculo.getConsumo() != null){
			vehiculoPQ.setP_CONSUMO(vehiculo.getConsumo());
		}
		if(vehiculo.getDistanciaEntreEjes() != null){
			vehiculoPQ.setP_DIST_EJES(vehiculo.getDistanciaEntreEjes());
		}
		if(vehiculo.getViaAnterior() != null){
			vehiculoPQ.setP_VIA_ANT(vehiculo.getViaAnterior());
		}
		if(vehiculo.getViaPosterior() != null){
			vehiculoPQ.setP_VIA_POS(vehiculo.getViaPosterior());
		}
		if(vehiculo.getCarburanteBean() != null && vehiculo.getCarburanteBean().getIdCarburante() != null
				&& !vehiculo.getCarburanteBean().getIdCarburante().equals("")
				&& !vehiculo.getCarburanteBean().getIdCarburante().equals("-1")){
			vehiculoPQ.setP_ID_CARBURANTE(vehiculo.getCarburanteBean().getIdCarburante());
		}
		if(vehiculo.getImportado() != null){
			if(vehiculo.getImportado()){
				vehiculoPQ.setP_IMPORTADO(TipoSN.S.value());
			}else{
				vehiculoPQ.setP_IMPORTADO(TipoSN.N.value());
			}
		}
		if(vehiculo.getSubasta() != null){
			if(vehiculo.getSubasta()){
				vehiculoPQ.setP_SUBASTADO(TipoSN.S.value());
			}else{
				vehiculoPQ.setP_SUBASTADO(TipoSN.N.value());
			}
		}
		if(vehiculo.getCarroceriaBean() != null && vehiculo.getCarroceriaBean().getIdCarroceria() != null &&
				!vehiculo.getCarroceriaBean().getIdCarroceria().equals("")){
			vehiculoPQ.setP_CARROCERIA(vehiculo.getCarroceriaBean().getIdCarroceria());
		}
		if(vehiculo.getCarroceriaBean() != null && vehiculo.getCarroceriaBean().getIdCarroceria() != null
				&& !vehiculo.getCarroceriaBean().getIdCarroceria().equals("")){
			vehiculoPQ.setP_CARROCERIA(vehiculo.getCarroceriaBean().getIdCarroceria());
		}
		if(vehiculo.getMom() != null){
			vehiculoPQ.setP_MOM(vehiculo.getMom());
		}

		vehiculoPQ.setP_CHECK_FECHA_CADUCIDAD_ITV(vehiculo.getCheckFechaCaducidadITV()!=null && !vehiculo.getCheckFechaCaducidadITV().equals("") ? (vehiculo.getCheckFechaCaducidadITV().equals(TRUE)?"SI":"NO"):null);

		vehiculoPQ.setP_MATRICULA_ORIGEN(vehiculo.getMatriculaOrigen()!=null && !vehiculo.getMatriculaOrigen().equals("")?vehiculo.getMatriculaOrigen():null);

		// MATE. No existe en MATW. Seteo del P_ID_PROCEDENCIA. Viene del fabricacionBean del vehiculoBean:
		if (vehiculo.getFabricacionBean()!=null){
			vehiculoPQ.setP_ID_PROCEDENCIA(vehiculo.getFabricacionBean().getIdFabricacion()!=null && !vehiculo.getFabricacionBean().getIdFabricacion().equals("") && !vehiculo.getFabricacionBean().getIdFabricacion().equals("-1")?vehiculo.getFabricacionBean().getIdFabricacion():null);
			vehiculoPQ.setP_FABRICACION(vehiculo.getFabricacionBean().getIdFabricacion()!=null && !vehiculo.getFabricacionBean().getIdFabricacion().equals("") && !vehiculo.getFabricacionBean().getIdFabricacion().equals("-1")?vehiculo.getFabricacionBean().getIdFabricacion():null);
		}else{
			vehiculoPQ.setP_ID_PROCEDENCIA(null);
		}

		// MATE. No existe en MATW. Seteo del P_PAIS_IMPORTACION. Viene del paisImportacionBean del vehiculoBean:
		if (vehiculo.getPaisImportacionBean() != null && vehiculo.getPaisImportacionBean().getIdPaisImportacion() != null
				&& !vehiculo.getPaisImportacionBean().getIdPaisImportacion().equals("")) {
			vehiculoPQ.setP_PAIS_IMPORTACION(vehiculo.getPaisImportacionBean().getIdPaisImportacion());
		}

		// MATRÍCULA ORIGEN ENTRANJERO
		vehiculoPQ.setP_MATRICULA_ORIG_EXTR(vehiculo.getMatriculaOrigExtr()!=null &&
				!vehiculo.getMatriculaOrigExtr().equals("") ? vehiculo.getMatriculaOrigExtr() : null);

		// MATW. No existe en MATE. Seteo del P_PAIS_FABRICACION. Viene del paisFabricacionBean del vehiculoBean:
		if (null != vehiculo.getPaisFabricacionBean()){
			vehiculoPQ.setP_PAIS_FABRICACION(vehiculo.getPaisFabricacionBean().getIdPaisFabricacion());
		}

		// MATW. No existe en MATE. Seteo del P_PROCEDENCIA. Viene de la propiedad procedencia del vehiculoBean:
		if(null != vehiculo.getProcedencia() && !"".equals(vehiculo.getProcedencia())){
			vehiculoPQ.setP_PROCEDENCIA(vehiculo.getProcedencia());
		}

		// Nuevo atributo que indica si la ficha técnica se ajusta al RD 750/2010
		vehiculoPQ.setP_FICHA_TECNICA_RD750(new BigDecimal(0));
		if (vehiculo.getFichaTecnicaRD750()!=null && vehiculo.getFichaTecnicaRD750()){
			vehiculoPQ.setP_FICHA_TECNICA_RD750(new BigDecimal(1));
		}

		// VEHÍCULOS MULTIFÁSICOS
		// Controlar para que no haya nullPointerException
		vehiculoPQ.setP_CODIGO_MARCA_BASE(null);
		if (vehiculo.getMarcaBaseBean()!=null && vehiculo.getMarcaBaseBean().getCodigoMarca()!=null && !vehiculo.getMarcaBaseBean().getCodigoMarca().equals(new BigDecimal("-1"))){
			vehiculoPQ.setP_CODIGO_MARCA_BASE(vehiculo.getMarcaBaseBean().getCodigoMarca());
		}
		vehiculoPQ.setP_FABRICANTE_BASE(vehiculo.getFabricanteBase() != null && !vehiculo.getFabricanteBase().isEmpty() ? vehiculo.getFabricanteBase() : null);
		vehiculoPQ.setP_TIPO_BASE(vehiculo.getTipoItvBase());
		vehiculoPQ.setP_VARIANTE_BASE(vehiculo.getVarianteBase() != null && !vehiculo.getVarianteBase().isEmpty() ? vehiculo.getVarianteBase() : null);
		vehiculoPQ.setP_VERSION_BASE(vehiculo.getVersionBase() != null && !vehiculo.getVersionBase().isEmpty() ? vehiculo.getVersionBase() : null);
		vehiculoPQ.setP_N_HOMOLOGACION_BASE(vehiculo.getNumHomologacionBase());
		vehiculoPQ.setP_MOM_BASE(vehiculo.getMomBase());
		vehiculoPQ.setP_CATEGORIA_ELECTRICA(vehiculo.getCategoriaElectricaBean()!=null ? vehiculo.getCategoriaElectricaBean().getIdCategoriaElectrica():null);
		vehiculoPQ.setP_AUTONOMIA_ELECTRICA(vehiculo.getAutonomiaElectrica());

		vehiculoPQ.setP_BASTIDOR_MATRICULADO(null);
		if(vehiculo.getBastidorMatriculado()!=null){
			vehiculoPQ.setP_BASTIDOR_MATRICULADO(vehiculo.getBastidorMatriculado());
		}

		if(vehiculo.getDoiResponsableKm() != null && !vehiculo.getDoiResponsableKm().isEmpty()){
			vehiculoPQ.setP_DOI_RESPONSABLE_LECTURA_KM(vehiculo.getDoiResponsableKm());
		}

		//DVV
		vehiculoPQ.setP_ES_SINIESTRO(Boolean.TRUE.equals(vehiculo.getEsSiniestro()) ? new BigDecimal(1): new BigDecimal(0));
//		vehiculoPQ.setP_TIENE_CARGA_FINANCIERA(Boolean.TRUE.equals(vehiculo.getTieneCargaFinanciera()) ? new BigDecimal(1) : new BigDecimal(0));

		return vehiculoPQ;
	}

	/**
	 * Método para convertir vehículos al formato aceptado por el paquete.
	 * @param vehiculo
	 * @param tramiteTrafico
	 * @return
	 */
	public BeanPQGUARDAR_MATW convertirBeanToPQNuevo(VehiculoBean vehiculoPrever, VehiculoBean vehiculo) {

		BeanPQGUARDAR_MATW vehiculoPQ = new BeanPQGUARDAR_MATW();

		vehiculoPQ.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		vehiculoPQ.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		vehiculoPQ.setP_ID_CONTRATO(utilesColegiado.getIdContratoSessionBigDecimal());

		// Si viene el numColegiado lo guado, si no viene lo guado de sesion
		vehiculoPQ.setP_NUM_COLEGIADO(vehiculo.getNumColegiado()!=null && !vehiculo.getNumColegiado().equals("")?vehiculo.getNumColegiado():utilesColegiado.getNumColegiadoSession());

		vehiculoPQ.setP_ID_VEHICULO(vehiculo.getIdVehiculo());
		vehiculoPQ.setP_BASTIDOR(vehiculo.getBastidor()!=null && !vehiculo.getBastidor().equals("")?vehiculo.getBastidor():null);
		vehiculoPQ.setP_NIVE(vehiculo.getNive()!=null && !vehiculo.getNive().equals("")?vehiculo.getNive():null);
		vehiculoPQ.setP_MATRICULA(vehiculo.getMatricula()!=null && !vehiculo.getMatricula().equals("")?vehiculo.getMatricula():null);

		//Controlar para que no haya nullPointerException
		vehiculoPQ.setP_CODIGO_MARCA(null);
		if (vehiculo.getMarcaBean()!=null && vehiculo.getMarcaBean().getCodigoMarca()!=null){
			vehiculoPQ.setP_CODIGO_MARCA(!vehiculo.getMarcaBean().getCodigoMarca().equals(new BigDecimal("-1")) ? vehiculo.getMarcaBean().getCodigoMarca():null);
		}

		vehiculoPQ.setP_MODELO(null);
		if (vehiculo.getModelo()!=null){
			vehiculoPQ.setP_MODELO(vehiculo.getModelo()!=null && !vehiculo.getModelo().equals("") && !vehiculo.getModelo().equals("-1")?vehiculo.getModelo():null);
		}

		vehiculoPQ.setP_TIPVEHI(vehiculo.getTipVehi()!=null?vehiculo.getTipVehi().getValorEnum():null);
		vehiculoPQ.setP_CDMARCA(vehiculo.getCdMarca()!=null && !vehiculo.getCdMarca().equals("") && !vehiculo.getCdMarca().equals("-1")?vehiculo.getCdMarca():null);
		vehiculoPQ.setP_CDMODVEH(vehiculo.getCdModVeh()!=null && !vehiculo.getCdModVeh().equals("") && !vehiculo.getCdModVeh().equals("-1")?vehiculo.getCdModVeh():null);

		vehiculoPQ.setP_TIPO_VEHICULO(null);
		if (vehiculo.getTipoVehiculoBean()!=null){
			vehiculoPQ.setP_TIPO_VEHICULO(vehiculo.getTipoVehiculoBean().getTipoVehiculo()!=null && !vehiculo.getTipoVehiculoBean().getTipoVehiculo().equals("") && !vehiculo.getTipoVehiculoBean().getTipoVehiculo().equals("-1")?vehiculo.getTipoVehiculoBean().getTipoVehiculo():null);
		}

		vehiculoPQ.setP_ID_SERVICIO(null);
		if (vehiculo.getServicioTraficoBean()!=null){
			vehiculoPQ.setP_ID_SERVICIO(vehiculo.getServicioTraficoBean().getIdServicio()!=null && !vehiculo.getServicioTraficoBean().getIdServicio().equals("") && !vehiculo.getServicioTraficoBean().getIdServicio().equals("-1")?vehiculo.getServicioTraficoBean().getIdServicio():null);
		}

		vehiculoPQ.setP_ID_SERVICIO_ANTERIOR(null);
		if (vehiculo.getServicioTraficoAnteriorBean()!=null){
			vehiculoPQ.setP_ID_SERVICIO_ANTERIOR(vehiculo.getServicioTraficoAnteriorBean().getIdServicio()!=null && !vehiculo.getServicioTraficoAnteriorBean().getIdServicio().equals("") && !vehiculo.getServicioTraficoAnteriorBean().getIdServicio().equals("-1")?vehiculo.getServicioTraficoAnteriorBean().getIdServicio():null);
		}

		vehiculoPQ.setP_VEHICULO_AGRICOLA(null!=vehiculo.isVehiculoAgricola()?(true==vehiculo.isVehiculoAgricola()?DecisionTrafico.Si.getValorEnum():DecisionTrafico.No.getValorEnum()):null);
		vehiculoPQ.setP_VEHICULO_TRANSPORTE(null!=vehiculo.isVehiculoTransporte()?(true==vehiculo.isVehiculoTransporte()?DecisionTrafico.Si.getValorEnum():DecisionTrafico.No.getValorEnum()):null);

		vehiculoPQ.setP_ID_COLOR(null);
		if (vehiculo.getColorBean()!=null){
			vehiculoPQ.setP_ID_COLOR(vehiculo.getColorBean().getValorEnum()!=null && !vehiculo.getColorBean().getValorEnum().equals("") && !vehiculo.getColorBean().getValorEnum().equals("-1")?vehiculo.getColorBean().getValorEnum():null);
		}

		vehiculoPQ.setP_ID_CARBURANTE(null);
		if (vehiculo.getCarburanteBean()!=null){
			vehiculoPQ.setP_ID_CARBURANTE(vehiculo.getCarburanteBean().getIdCarburante());
		}

		vehiculoPQ.setP_ID_LUGAR_ADQUISICION(null);
		if (vehiculo.getLugarAdquisicionBean()!=null){
			vehiculoPQ.setP_ID_LUGAR_ADQUISICION(vehiculo.getLugarAdquisicionBean().getIdAdquisicion()!=null && !vehiculo.getLugarAdquisicionBean().getIdAdquisicion().equals("") && !vehiculo.getLugarAdquisicionBean().getIdAdquisicion().equals("-1")?vehiculo.getLugarAdquisicionBean().getIdAdquisicion():null);
		}

		vehiculoPQ.setP_ID_CRITERIO_CONSTRUCCION(null);
		if (vehiculo.getCriterioConstruccionBean()!=null){
			vehiculoPQ.setP_ID_CRITERIO_CONSTRUCCION(vehiculo.getCriterioConstruccionBean().getIdCriterioConstruccion()!=null && !vehiculo.getCriterioConstruccionBean().getIdCriterioConstruccion().equals("") && !vehiculo.getCriterioConstruccionBean().getIdCriterioConstruccion().equals("-1")?vehiculo.getCriterioConstruccionBean().getIdCriterioConstruccion():null);
		}

		vehiculoPQ.setP_ID_CRITERIO_UTILIZACION(null);
		if (vehiculo.getCriterioUtilizacionBean()!=null){
			vehiculoPQ.setP_ID_CRITERIO_UTILIZACION(vehiculo.getCriterioUtilizacionBean().getIdCriterioUtilizacion()!=null && !vehiculo.getCriterioUtilizacionBean().getIdCriterioUtilizacion().equals("") && !vehiculo.getCriterioUtilizacionBean().getIdCriterioUtilizacion().equals("-1")?vehiculo.getCriterioUtilizacionBean().getIdCriterioUtilizacion():null);
		}

		vehiculoPQ.setP_ID_DIRECTIVA_CEE(null);
		if (vehiculo.getHomologacionBean()!=null){
			vehiculoPQ.setP_ID_DIRECTIVA_CEE(vehiculo.getHomologacionBean().getIdHomologacion() != null
					&& !vehiculo.getHomologacionBean().getIdHomologacion().equals("")
					&& !vehiculo.getHomologacionBean().getIdHomologacion().equals("-1") ?
							vehiculo.getHomologacionBean().getIdHomologacion() : null);
		}

		// MATE 2.5
		if(vehiculo.getImportado() != null){
			if(vehiculo.getImportado())
				vehiculoPQ.setP_IMPORTADO(TipoSN.S.value());
			else
				vehiculoPQ.setP_IMPORTADO(TipoSN.N.value());
		}
		if(vehiculo.getSubasta() != null){
			if(vehiculo.getSubasta())
				vehiculoPQ.setP_SUBASTADO(TipoSN.S.value());
			else
				vehiculoPQ.setP_SUBASTADO(TipoSN.N.value());
		}
		if(vehiculo.getFabricante() != null && !vehiculo.getFabricante().equals("")){
			vehiculoPQ.setP_FABRICANTE(vehiculo.getFabricante());
		}else{
			vehiculoPQ.setP_FABRICANTE(null);
		}
		vehiculoPQ.setP_CARROCERIA(null);
		if(vehiculo.getCarroceriaBean() != null && vehiculo.getCarroceriaBean().getIdCarroceria() != null
				&& !vehiculo.getCarroceriaBean().getIdCarroceria().equals("")){
			vehiculoPQ.setP_CARROCERIA(vehiculo.getCarroceriaBean().getIdCarroceria());
		}
		vehiculoPQ.setP_CONSUMO(null);
		if(vehiculo.getConsumo() != null){
			vehiculoPQ.setP_CONSUMO(vehiculo.getConsumo());
		}
		vehiculoPQ.setP_DIST_EJES(null);
		if(vehiculo.getDistanciaEntreEjes() != null){
			vehiculoPQ.setP_DIST_EJES(vehiculo.getDistanciaEntreEjes());
		}
		vehiculoPQ.setP_VIA_ANT(null);
		if(vehiculo.getViaAnterior() != null){
			vehiculoPQ.setP_VIA_ANT(vehiculo.getViaAnterior());
		}
		vehiculoPQ.setP_VIA_POS(null);
		if(vehiculo.getViaPosterior() != null){
			vehiculoPQ.setP_VIA_POS(vehiculo.getViaPosterior());
		}
		vehiculoPQ.setP_ALIMENTACION(null);
		if(vehiculo.getAlimentacionBean() != null && vehiculo.getAlimentacionBean().getIdAlimentacion() != null
				&& !vehiculo.getAlimentacionBean().getIdAlimentacion().equals("")){
			vehiculoPQ.setP_ALIMENTACION(vehiculo.getAlimentacionBean().getIdAlimentacion());
		}
		vehiculoPQ.setP_EMISIONES(null);
		if(vehiculo.getNivelEmisiones() != null && !vehiculo.getNivelEmisiones().equals("")){
			vehiculoPQ.setP_EMISIONES(vehiculo.getNivelEmisiones());
		}
		vehiculoPQ.setP_ECO_INNOVACION(null);
		if(vehiculo.getEcoInnovacion() != null && !vehiculo.getEcoInnovacion().equals("")){
			vehiculoPQ.setP_ECO_INNOVACION(vehiculo.getEcoInnovacion());
		}
		if(vehiculo.getReduccionEco() != null){
			vehiculoPQ.setP_REDUCCION_ECO(vehiculo.getReduccionEco());
		}else{
			vehiculoPQ.setP_REDUCCION_ECO(null);
		}
		vehiculoPQ.setP_CODIGO_ECO(null);
		if(vehiculo.getCodigoEco() != null && !vehiculo.getCodigoEco().equals("")){
			vehiculoPQ.setP_CODIGO_ECO(vehiculo.getCodigoEco());
		}
		vehiculoPQ.setP_MOM(null);
		if(vehiculo.getMom() != null){
			vehiculoPQ.setP_MOM(vehiculo.getMom());
		}
		// FIN MATE 2.5

		vehiculoPQ.setP_DIPLOMATICO(vehiculo.getDiplomatico()!=null && !vehiculo.getDiplomatico().equals("") ? (vehiculo.getDiplomatico().equals(TRUE)?"S":"N"):null);

		vehiculoPQ.setP_PLAZAS(vehiculo.getPlazas());
		vehiculoPQ.setP_CODITV(vehiculo.getCodItv()!=null && !vehiculo.getCodItv().equals("")?vehiculo.getCodItv():null);
		vehiculoPQ.setP_POTENCIA_FISCAL(vehiculo.getPotenciaFiscal()!=null?vehiculo.getPotenciaFiscal():null);
		vehiculoPQ.setP_POTENCIA_NETA(vehiculo.getPotenciaNeta()!=null?vehiculo.getPotenciaNeta():null);
		vehiculoPQ.setP_POTENCIA_PESO(vehiculo.getPotenciaPeso()!=null?vehiculo.getPotenciaPeso():null);
		vehiculoPQ.setP_CILINDRADA(vehiculo.getCilindrada()!=null && !vehiculo.getCilindrada().equals("")?vehiculo.getCilindrada():null);
		vehiculoPQ.setP_CO2(vehiculo.getCo2()!=null && !vehiculo.getCo2().equals("")?vehiculo.getCo2():null);
		vehiculoPQ.setP_TARA(vehiculo.getTara()!=null && !vehiculo.getTara().equals("")?vehiculo.getTara():null);
		vehiculoPQ.setP_PESO_MMA(vehiculo.getPesoMma()!=null && !vehiculo.getTara().equals("")?vehiculo.getPesoMma():null);
		vehiculoPQ.setP_EXCESO_PESO(vehiculo.getExcesoPeso()!=null && !vehiculo.getExcesoPeso().equals("") ? (vehiculo.getExcesoPeso().equals(TRUE)?"S":"N"):null);
		vehiculoPQ.setP_TIPO_INDUSTRIA(vehiculo.getTipoIndustria()!=null && !vehiculo.getTipoIndustria().equals("")?vehiculo.getTipoIndustria():null);
		vehiculoPQ.setP_MTMA_ITV((vehiculo.getMtmaItv()!=null && !vehiculo.getMtmaItv().equals("")?vehiculo.getMtmaItv():null));
		vehiculoPQ.setP_VERSION(vehiculo.getVersion()!=null && !vehiculo.getVersion().equals("")?vehiculo.getVersion():null);
		vehiculoPQ.setP_VARIANTE(vehiculo.getVariante()!=null && !vehiculo.getVariante().equals("")?vehiculo.getVariante():null);

		vehiculoPQ.setP_ID_MOTIVO_ITV(null);
		if (vehiculo.getMotivoItv()!=null){
			vehiculoPQ.setP_ID_MOTIVO_ITV(vehiculo.getMotivoItv().getIdMotivo()!=null && !vehiculo.getMotivoItv().getIdMotivo().equals("") && !vehiculo.getMotivoItv().getIdMotivo().equals("-1")?vehiculo.getMotivoItv().getIdMotivo():null);
		}

		vehiculoPQ.setP_CLASIFICACION_ITV(vehiculo.getClasificacionItv()!=null && !vehiculo.getClasificacionItv().equals("")?vehiculo.getClasificacionItv():null);

		vehiculoPQ.setP_ID_TIPO_TARJETA_ITV(null);
		if (vehiculo.getTipoTarjetaItvBean()!=null){
			vehiculoPQ.setP_ID_TIPO_TARJETA_ITV(vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv()!=null && !vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals("") && !vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals("-1")?vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv():null);
		}

		vehiculoPQ.setP_CONCEPTO_ITV(vehiculo.getConceptoItv()!=null && !vehiculo.getConceptoItv().equals("") && !vehiculo.getConceptoItv().equals("-1")?vehiculo.getConceptoItv().toUpperCase():null);
		vehiculoPQ.setP_ESTACION_ITV(vehiculo.getEstacionItv()!=null && !vehiculo.getEstacionItv().equals("") && !vehiculo.getEstacionItv().equals("-1")?vehiculo.getEstacionItv():null);
		vehiculoPQ.setP_N_PLAZAS_PIE(vehiculo.getNumPlazasPie());

		vehiculoPQ.setP_N_HOMOLOGACION(null);
		if (vehiculo.getNumHomologacion()!=null){
			vehiculoPQ.setP_N_HOMOLOGACION(null==vehiculo.getNumHomologacion() ? null:vehiculo.getNumHomologacion());
		}
		if (vehiculo.getNumRuedas()!=null){
			vehiculoPQ.setP_N_RUEDAS(vehiculo.getNumRuedas());
		}
		vehiculoPQ.setP_N_SERIE(null);
		if (vehiculo.getNumSerie()!=null){
			vehiculoPQ.setP_N_SERIE(null==vehiculo.getNumSerie()?null:vehiculo.getNumSerie());
		}

		vehiculoPQ.setP_ID_EPIGRAFE(null);
		if (vehiculo.getEpigrafeBean()!=null){
			vehiculoPQ.setP_ID_EPIGRAFE(vehiculo.getEpigrafeBean().getIdEpigrafe()!=null && !vehiculo.getEpigrafeBean().getIdEpigrafe().equals("")?vehiculo.getEpigrafeBean().getIdEpigrafe():null);
		}

		if (vehiculo.getIntegradorBean() != null){
			if (vehiculo.getIntegradorBean().getTipoPersona()!=null){
				vehiculoPQ.setP_TIPO_PERSONA_INTE(vehiculo.getIntegradorBean().getTipoPersona().getValorEnum());
			}
			vehiculoPQ.setP_APELLIDO1_RAZON_SOCIAL_INTE(null==vehiculo.getIntegradorBean().getApellido1RazonSocial()?null:!vehiculo.getIntegradorBean().getApellido1RazonSocial().equals("")?vehiculo.getIntegradorBean().getApellido1RazonSocial():null);
			vehiculoPQ.setP_APELLIDO2_INTE(null==vehiculo.getIntegradorBean().getApellido2()?null:!vehiculo.getIntegradorBean().getApellido2().equals("")?vehiculo.getIntegradorBean().getApellido2():null);
			vehiculoPQ.setP_NOMBRE_INTE(null==vehiculo.getIntegradorBean().getNombre() ? null:!vehiculo.getIntegradorBean().getNombre().equals("")?vehiculo.getIntegradorBean().getNombre():null);
			vehiculoPQ.setP_NIF_INTEGRADOR(vehiculo.getIntegradorBean().getNif()!=null && !vehiculo.getIntegradorBean().getNif().equals("")?vehiculo.getIntegradorBean().getNif():null);
		}

		vehiculoPQ.setP_VEHI_USADO(vehiculo.getVehiUsado()!=null && vehiculo.getVehiUsado().equals(TRUE)?"SI":"NO");
		vehiculoPQ.setP_MATRI_AYUNTAMIENTO(vehiculo.getMatriAyuntamiento()!=null && !vehiculo.getMatriAyuntamiento().equals("")?vehiculo.getMatriAyuntamiento():null);

		vehiculoPQ.setP_TIPO_ITV(vehiculo.getTipoItv());

		// Para la consulta de vehículos, que cargue el vehiculo prever.
		if (vehiculoPrever!=null && vehiculoPrever.getBastidor()!=null) {
			String idVehiculoPrever = vehiculo.getVehiculoPreverBean().getIdVehiculoPrever();

			if (idVehiculoPrever!=null && !idVehiculoPrever.equals("")) {
				vehiculoPQ.setP_ID_VEHICULO_PREVER(new BigDecimal(idVehiculoPrever));
			}

			vehiculoPQ.setP_PREV_BASTIDOR(vehiculoPrever.getBastidor());

			// DRC@27-09-2012 Incidencia: 02444 
			if (vehiculoPrever.getMarcaBean() !=null && vehiculoPrever.getMarcaBean().getCodigoMarca() != null) {
				vehiculoPQ.setP_PREV_CODIGO_MARCA(vehiculoPrever.getMarcaBean().getCodigoMarca());
			}

			if (vehiculoPrever.getCriterioConstruccionBean() != null
					&& vehiculoPrever.getCriterioConstruccionBean().getIdCriterioConstruccion() != null
					&& !"-1".equals(vehiculoPrever.getCriterioConstruccionBean().getIdCriterioConstruccion())){
				vehiculoPQ.setP_PREV_ID_CONSTRUCCION(vehiculoPrever.getCriterioConstruccionBean().getIdCriterioConstruccion());
			}

			if (vehiculoPrever.getCriterioUtilizacionBean() != null
					&& vehiculoPrever.getCriterioUtilizacionBean().getIdCriterioUtilizacion() != null
					&& !"-1".equals(vehiculoPrever.getCriterioUtilizacionBean().getIdCriterioUtilizacion())){
				vehiculoPQ.setP_PREV_ID_UTILIZACION(vehiculoPrever.getCriterioUtilizacionBean().getIdCriterioUtilizacion());
			}

			//Este dato se genera a partir de los dos anteriores criterios.
			vehiculoPQ.setP_PREV_CLASIFICACION_ITV(vehiculoPrever.getClasificacionItv());

			vehiculoPQ.setP_PREV_MATRICULA(vehiculoPrever.getMatricula());
			vehiculoPQ.setP_PREV_MODELO(vehiculoPrever.getModelo());
			vehiculoPQ.setP_PREV_TIPO_ITV(vehiculoPrever.getTipoItv());
		}

		try {
			if (vehiculo.getLimiteMatrTuris()!=null) {
				vehiculoPQ.setP_LIMITE_MATR_TURIS(vehiculo.getLimiteMatrTuris().getTimestamp());
			} else {
				vehiculoPQ.setP_LIMITE_MATR_TURIS(null);
			}

			if (vehiculo.getFechaPrimMatri()!=null && vehiculo.getVehiUsado() != null && vehiculo.getVehiUsado().equals(TRUE) == Boolean.TRUE){
				vehiculoPQ.setP_FECHA_PRIM_MATRI(vehiculo.getFechaPrimMatri().getTimestamp());
			} else {
				vehiculoPQ.setP_FECHA_PRIM_MATRI(null);
			}

			if (vehiculo.getFechaInspeccion()!=null){
				vehiculoPQ.setP_FECHA_INSPECCION(vehiculo.getFechaInspeccion().getTimestamp());
			} else {
				vehiculoPQ.setP_FECHA_INSPECCION(null);
			}

			if (vehiculo.getFechaItv()!=null){
				vehiculoPQ.setP_FECHA_ITV(vehiculo.getFechaItv().getTimestamp());
			} else {
				vehiculoPQ.setP_FECHA_ITV(null);
			}

			if (vehiculo.getFecDesde()!=null){
				vehiculoPQ.setP_FECDESDE(vehiculo.getFecDesde().getTimestamp());
			} else {
				vehiculoPQ.setP_FECDESDE(null);
			}

			if (vehiculo.getFechaMatriculacion()!=null){
				vehiculoPQ.setP_FECHA_MATRICULACION(vehiculo.getFechaMatriculacion().getTimestamp());
			} else {
				vehiculoPQ.setP_FECHA_MATRICULACION(null);
			}
		} catch (ParseException e) {
			log.error(e);
		}

		vehiculoPQ.setP_ID_LUGAR_MATRICULACION(vehiculo.getLugarPrimeraMatriculacionBean()!=null && vehiculo.getLugarPrimeraMatriculacionBean().getIdLugarPrimeraMatriculacion()!=null && !vehiculo.getLugarPrimeraMatriculacionBean().getIdLugarPrimeraMatriculacion().equals("")?vehiculo.getLugarPrimeraMatriculacionBean().getIdLugarPrimeraMatriculacion():null);
		vehiculoPQ.setP_KM_USO(vehiculo.getKmUso());
		vehiculoPQ.setP_HORAS_USO(vehiculo.getHorasUso());

		vehiculoPQ.setP_N_CILINDROS(vehiculo.getNumCilindros());
		vehiculoPQ.setP_CARACTERISTICAS(vehiculo.getCaracteristicas()!=null && !vehiculo.getCaracteristicas().equals("")?vehiculo.getCaracteristicas():null);
		vehiculoPQ.setP_ANIO_FABRICA(vehiculo.getAnioFabrica()!=null && !vehiculo.getAnioFabrica().equals("")?vehiculo.getAnioFabrica():null);
		// -- Datos de la direccion del vehículo

		if (vehiculo.getDireccionBean()!=null){
			vehiculoPQ.setP_ID_DIRECCION(null == vehiculo.getDireccionBean().getIdDireccion() ? null : !vehiculo.getDireccionBean().getIdDireccion().equals("")?new BigDecimal (vehiculo.getDireccionBean().getIdDireccion()) : null);

			//Se comprueba que todos los datos de la dirección vengan a null, y en ese caso se pasará la dirección a null.
			if(		(vehiculo.getDireccionBean().getMunicipio().getProvincia().getIdProvincia()==null ||
					vehiculo.getDireccionBean().getMunicipio().getProvincia().getIdProvincia().equals("-1"))
					&& (vehiculo.getDireccionBean().getMunicipio().getIdMunicipio() ==null ||
					vehiculo.getDireccionBean().getMunicipio().getIdMunicipio().equals("-1"))
					&& (vehiculo.getDireccionBean().getCodPostal() ==null ||
					vehiculo.getDireccionBean().getCodPostal().equals(""))
					&& (vehiculo.getDireccionBean().getTipoVia().getIdTipoVia() ==null ||
					vehiculo.getDireccionBean().getTipoVia().getIdTipoVia().equals("-1"))
					&& (vehiculo.getDireccionBean().getNombreVia() ==null ||
					vehiculo.getDireccionBean().getNombreVia().equals(""))
					&& (vehiculo.getDireccionBean().getNumero() ==null ||
					vehiculo.getDireccionBean().getNumero().equals(""))
					&& (vehiculo.getDireccionBean().getLetra() ==null ||
					vehiculo.getDireccionBean().getLetra().equals(""))
					&& (vehiculo.getDireccionBean().getEscalera() ==null ||
					vehiculo.getDireccionBean().getEscalera().equals(""))
					&& (vehiculo.getDireccionBean().getPlanta() ==null ||
					vehiculo.getDireccionBean().getPlanta().equals(""))
					&& (vehiculo.getDireccionBean().getPuerta() == null ||
					vehiculo.getDireccionBean().getPuerta().equals(""))
					&& (vehiculo.getDireccionBean().getBloque() == null ||
					vehiculo.getDireccionBean().getBloque().equals(""))
					&& (vehiculo.getDireccionBean().getPuntoKilometrico() == null ||
					vehiculo.getDireccionBean().getPuntoKilometrico().equals(""))
					&& (vehiculo.getDireccionBean().getHm() == null ||
					vehiculo.getDireccionBean().getHm().equals(""))){
				vehiculoPQ.setP_ID_DIRECCION(null);
			} else{
				if (vehiculo.getDireccionBean().getMunicipio()!=null && vehiculo.getDireccionBean().getMunicipio().getProvincia()!=null){
					vehiculoPQ.setP_ID_PROVINCIA(vehiculo.getDireccionBean().getMunicipio().getProvincia().getIdProvincia()!=null && !vehiculo.getDireccionBean().getMunicipio().getProvincia().getIdProvincia().equals("") && !vehiculo.getDireccionBean().getMunicipio().getProvincia().getIdProvincia().equals("-1")?vehiculo.getDireccionBean().getMunicipio().getProvincia().getIdProvincia():null);
				}else{
					vehiculoPQ.setP_ID_PROVINCIA(null);
				}

				if (vehiculo.getDireccionBean().getMunicipio()!=null){
					vehiculoPQ.setP_ID_MUNICIPIO(vehiculo.getDireccionBean().getMunicipio().getIdMunicipio()!=null && !vehiculo.getDireccionBean().getMunicipio().getIdMunicipio().equals("") && !vehiculo.getDireccionBean().getMunicipio().getIdMunicipio().equals("-1")?vehiculo.getDireccionBean().getMunicipio().getIdMunicipio():null);
				}else{
					vehiculoPQ.setP_ID_MUNICIPIO(null);
				}

				if (vehiculo.getDireccionBean().getTipoVia()!=null){
					vehiculoPQ.setP_ID_TIPO_VIA(vehiculo.getDireccionBean().getTipoVia().getIdTipoVia()!=null && !vehiculo.getDireccionBean().getTipoVia().getIdTipoVia().equals("") && !vehiculo.getDireccionBean().getTipoVia().getIdTipoVia().equals("-1")?vehiculo.getDireccionBean().getTipoVia().getIdTipoVia():null);
				}else{
					vehiculoPQ.setP_ID_TIPO_VIA(null);
				}

				vehiculoPQ.setP_NOMBRE_VIA(vehiculo.getDireccionBean().getNombreVia()!=null && !vehiculo.getDireccionBean().getNombreVia().equals("")?vehiculo.getDireccionBean().getNombreVia():null);
				vehiculoPQ.setP_NUMERO(vehiculo.getDireccionBean().getNumero()!=null && !vehiculo.getDireccionBean().getNumero().equals("")?vehiculo.getDireccionBean().getNumero():null);
				vehiculoPQ.setP_COD_POSTAL(vehiculo.getDireccionBean().getCodPostal()!=null && !vehiculo.getDireccionBean().getCodPostal().equals("")?vehiculo.getDireccionBean().getCodPostal():null);
				vehiculoPQ.setP_PUEBLO(vehiculo.getDireccionBean().getPueblo()!=null && !vehiculo.getDireccionBean().getPueblo().equals("") && !vehiculo.getDireccionBean().getPueblo().equals("-1")?vehiculo.getDireccionBean().getPueblo():null);
				vehiculoPQ.setP_LETRA(vehiculo.getDireccionBean().getLetra()!=null && !vehiculo.getDireccionBean().getLetra().equals("")?vehiculo.getDireccionBean().getLetra():null);
				vehiculoPQ.setP_ESCALERA(vehiculo.getDireccionBean().getEscalera()!=null && !vehiculo.getDireccionBean().getEscalera().equals("")?vehiculo.getDireccionBean().getEscalera():null);
				vehiculoPQ.setP_BLOQUE(vehiculo.getDireccionBean().getBloque()!=null && !vehiculo.getDireccionBean().getBloque().equals("")?vehiculo.getDireccionBean().getBloque():null);
				vehiculoPQ.setP_PLANTA(vehiculo.getDireccionBean().getPlanta()!=null && !vehiculo.getDireccionBean().getPlanta().equals("")?vehiculo.getDireccionBean().getPlanta():null);
				vehiculoPQ.setP_PUERTA(vehiculo.getDireccionBean().getPuerta()!=null && !vehiculo.getDireccionBean().getPuerta().equals("")?vehiculo.getDireccionBean().getPuerta():null);
				vehiculoPQ.setP_NUM_LOCAL(null==vehiculo.getDireccionBean().getNumLocal() ? null:!vehiculo.getDireccionBean().getNumLocal().equals("")?new BigDecimal (vehiculo.getDireccionBean().getNumLocal()):null);

				//CONTROLAR LA CONVERSION CORRECTA DE ESTOS ATRIBUTOS
				if (vehiculo.getDireccionBean().getPuntoKilometrico() == null){
					vehiculoPQ.setP_KM(null);
				}else{
					if ("".equals(vehiculo.getDireccionBean().getPuntoKilometrico())){
						vehiculoPQ.setP_KM(null);
					}else{
						vehiculoPQ.setP_KM(new BigDecimal (vehiculo.getDireccionBean().getPuntoKilometrico()));
					}
				}

				if (vehiculo.getDireccionBean().getHm() == null){
					vehiculoPQ.setP_HM(null);
				}else{
					if ("".equals(vehiculo.getDireccionBean().getHm())){
						vehiculoPQ.setP_HM(null);
					}else{
						vehiculoPQ.setP_HM(new BigDecimal (vehiculo.getDireccionBean().getHm()));
					}
				}
			}
		} else {
			vehiculoPQ.setP_ID_DIRECCION(null);
		}
		// DRC@06-02-2013 Incidencia Mantis: 3019
		vehiculoPQ.setP_CHECK_FECHA_CADUCIDAD_ITV(vehiculo.getCheckFechaCaducidadITV()!=null && !vehiculo.getCheckFechaCaducidadITV().equals("") ? (vehiculo.getCheckFechaCaducidadITV().equals(TRUE)?"SI":"NO"):null);

		vehiculoPQ.setP_MATRICULA_ORIGEN(vehiculo.getMatriculaOrigen()!=null && !vehiculo.getMatriculaOrigen().equals("")?vehiculo.getMatriculaOrigen():null);

		// MATE. No existe en MATW. Seteo del P_ID_PROCEDENCIA. Viene del fabricacionBean del vehiculoBean:
		if(null != vehiculo.getFabricacionBean()){
			vehiculoPQ.setP_ID_PROCEDENCIA(vehiculo.getFabricacionBean().getIdFabricacion());
		}

		// MATE. No existe en MATW. Seteo del P_PAIS_IMPORTACION. Viene del paisImportacionBean del vehiculoBean:
		if (vehiculo.getPaisImportacionBean() != null && vehiculo.getPaisImportacionBean().getIdPaisImportacion() != null
				&& !vehiculo.getPaisImportacionBean().getIdPaisImportacion().equals("")){
			vehiculoPQ.setP_PAIS_IMPORTACION(vehiculo.getPaisImportacionBean().getIdPaisImportacion());
		}else{
			vehiculoPQ.setP_PAIS_IMPORTACION(null);
		}

		// MATW. No existe en MATE. Seteo del P_PROCEDENCIA. Viene de la propiedad procedencia del vehiculoBean:
		if (vehiculo.getProcedencia()!=null){
			vehiculoPQ.setP_PROCEDENCIA(vehiculo.getProcedencia());
		}else{
			vehiculoPQ.setP_PROCEDENCIA(null);
		}

		// MATW. No existe en MATE. Seteo del P_PAIS_FABRICACION. Viene del paisFabricacionBean del vehiculoBean:
		if(null != vehiculo.getPaisFabricacionBean()){
			vehiculoPQ.setP_PAIS_FABRICACION(vehiculo.getPaisFabricacionBean().getIdPaisFabricacion());
		}

		//Nuevo atributo que indica si la ficha técnica se ajusta al RD 750/2010
		if (vehiculo.getFichaTecnicaRD750()!=null && vehiculo.getFichaTecnicaRD750()){
			vehiculoPQ.setP_FICHA_TECNICA_RD750(new BigDecimal(1));
		}else{
			vehiculoPQ.setP_FICHA_TECNICA_RD750(new BigDecimal(0));
		}

		// VEHICULOS MULTIFASICOS
		//Controlar para que no haya nullPointerException
		if (vehiculo.getMarcaBaseBean()!=null && vehiculo.getMarcaBaseBean().getCodigoMarca()!=null && !vehiculo.getMarcaBaseBean().getCodigoMarca().equals(new BigDecimal("-1"))){
			vehiculoPQ.setP_CODIGO_MARCA_BASE(vehiculo.getMarcaBaseBean().getCodigoMarca());
		}else{
			vehiculoPQ.setP_CODIGO_MARCA_BASE(null);
		}
		vehiculoPQ.setP_FABRICANTE_BASE(vehiculo.getFabricanteBase() != null && !vehiculo.getFabricanteBase().isEmpty() ? vehiculo.getFabricanteBase() : null);
		vehiculoPQ.setP_TIPO_BASE(vehiculo.getTipoItvBase());
		vehiculoPQ.setP_VARIANTE_BASE(vehiculo.getVarianteBase() != null && !vehiculo.getVarianteBase().isEmpty() ? vehiculo.getVarianteBase() : null);
		vehiculoPQ.setP_VERSION_BASE(vehiculo.getVersionBase() != null && !vehiculo.getVersionBase().isEmpty() ? vehiculo.getVersionBase() : null);
		vehiculoPQ.setP_N_HOMOLOGACION_BASE(vehiculo.getNumHomologacionBase());
		vehiculoPQ.setP_MOM_BASE(vehiculo.getMomBase());
		vehiculoPQ.setP_CATEGORIA_ELECTRICA(vehiculo.getCategoriaElectricaBean()!=null?vehiculo.getCategoriaElectricaBean().getIdCategoriaElectrica():null);
		vehiculoPQ.setP_AUTONOMIA_ELECTRICA(vehiculo.getAutonomiaElectrica());

		if(vehiculo.getBastidorMatriculado()!=null){
			vehiculoPQ.setP_BASTIDOR_MATRICULADO(vehiculo.getBastidorMatriculado());
		} else {
			vehiculoPQ.setP_BASTIDOR_MATRICULADO(null);
		}

		return vehiculoPQ;
	}

	public VehiculoBean convertirPQtoBean(BeanPQVehiculosGuardar vehiculo) {
		RespuestaGenerica respuesta = new RespuestaGenerica();
		HashMap<String,Object> map = new HashMap<>();

		//Datos de control
		map.put("P_ID_USUARIO",vehiculo.getP_ID_USUARIO());
		map.put("P_ID_CONTRATO_SESSION",vehiculo.getP_ID_CONTRATO_SESSION());
		map.put("P_NUM_EXPEDIENTE",vehiculo.getP_NUM_EXPEDIENTE());
		map.put("P_ID_CONTRATO",vehiculo.getP_ID_CONTRATO());
		map.put("P_TIPO_TRAMITE",vehiculo.getP_TIPO_TRAMITE());
		//Datos del Vehiculo
		map.put("P_NUM_COLEGIADO",vehiculo.getP_NUM_COLEGIADO());
		map.put("P_ID_VEHICULO",vehiculo.getP_ID_VEHICULO());
		map.put("P_BASTIDOR",vehiculo.getP_BASTIDOR());
		map.put("P_NIVE",vehiculo.getP_NIVE());
		map.put("P_MATRICULA",vehiculo.getP_MATRICULA());
		map.put("P_CODIGO_MARCA",vehiculo.getP_CODIGO_MARCA());
		map.put("P_MODELO",vehiculo.getP_MODELO());
		map.put("P_TIPVEHI",vehiculo.getP_TIPVEHI());
		map.put("P_CDMARCA",vehiculo.getP_CDMARCA());
		map.put("P_CDMODVEH",vehiculo.getP_CDMODVEH());
		map.put("P_FECDESDE",vehiculo.getP_FECDESDE());
		map.put("P_TIPO_VEHICULO",vehiculo.getP_TIPO_VEHICULO());
		map.put("P_ID_SERVICIO",vehiculo.getP_ID_SERVICIO());
		map.put("P_ID_SERVICIO_ANTERIOR",vehiculo.getP_ID_SERVICIO_ANTERIOR());
		map.put("P_FECHA_MATRICULACION",vehiculo.getP_FECHA_MATRICULACION());
		map.put("P_VEHICULO_AGRICOLA",vehiculo.getP_VEHICULO_AGRICOLA());
		map.put("P_VEHICULO_TRANSPORTE",vehiculo.getP_VEHICULO_TRANSPORTE());
		map.put("P_ID_COLOR",vehiculo.getP_ID_COLOR());
		map.put("P_ID_CARBURANTE",vehiculo.getP_ID_CARBURANTE());
		map.put("P_ID_PROCEDENCIA",vehiculo.getP_ID_PROCEDENCIA());
		map.put("P_ID_LUGAR_ADQUISICION",vehiculo.getP_ID_LUGAR_ADQUISICION());
		map.put("P_ID_CRITERIO_CONSTRUCCION",vehiculo.getP_ID_CRITERIO_CONSTRUCCION());
		map.put("P_ID_CRITERIO_UTILIZACION",vehiculo.getP_ID_CRITERIO_UTILIZACION());
		map.put("P_ID_DIRECTIVA_CEE",vehiculo.getP_ID_DIRECTIVA_CEE());
		map.put("P_DIPLOMATICO",vehiculo.getP_DIPLOMATICO());

		map.put("P_CODITV",vehiculo.getP_CODITV());
		map.put("P_POTENCIA_FISCAL",vehiculo.getP_POTENCIA_FISCAL());
		map.put("P_POTENCIA_NETA",vehiculo.getP_POTENCIA_NETA());
		map.put("P_POTENCIA_PESO",vehiculo.getP_POTENCIA_PESO());
		map.put("P_CILINDRADA",vehiculo.getP_CILINDRADA());
		map.put("P_CO2", vehiculo.getP_CO2());

		// DRC@21-09-2012 Incidencia: 2358
		map.put("P_TARA",		vehiculo.getP_TARA());
		map.put("P_PESO_MMA",	vehiculo.getP_PESO_MMA());
		map.put("P_PLAZAS",		vehiculo.getP_PLAZAS());
		map.put("P_PROVINCIA_PRIMERA_MATRICULA", vehiculo.getP_PROVINCIA_PRIMERA_MATRICULA());

		map.put("P_MTMA_ITV",vehiculo.getP_MTMA_ITV());
		map.put("P_VARIANTE",vehiculo.getP_VARIANTE());
		map.put("P_VERSION",vehiculo.getP_VERSION());
		map.put("P_EXCESO_PESO",vehiculo.getP_EXCESO_PESO());
		map.put("P_TIPO_INDUSTRIA",vehiculo.getP_TIPO_INDUSTRIA());
		map.put("P_ID_MOTIVO_ITV",vehiculo.getP_ID_MOTIVO_ITV());
		map.put("P_FECHA_ITV",vehiculo.getP_FECHA_ITV());
		map.put("P_CLASIFICACION_ITV",vehiculo.getP_CLASIFICACION_ITV());
		map.put("P_ID_TIPO_TARJETA_ITV",vehiculo.getP_ID_TIPO_TARJETA_ITV());
		map.put("P_CONCEPTO_ITV",vehiculo.getP_CONCEPTO_ITV());
		map.put("P_ESTACION_ITV",vehiculo.getP_ESTACION_ITV());
		map.put("P_FECHA_INSPECCION",vehiculo.getP_FECHA_INSPECCION());
		map.put("P_N_PLAZAS_PIE",vehiculo.getP_N_PLAZAS_PIE());
		map.put("P_N_HOMOLOGACION",vehiculo.getP_N_HOMOLOGACION());
		map.put("P_N_RUEDAS",vehiculo.getP_N_RUEDAS());
		map.put("P_N_SERIE",vehiculo.getP_N_SERIE());
		map.put("P_ID_EPIGRAFE",vehiculo.getP_ID_EPIGRAFE());
		map.put("P_NIF_INTEGRADOR",vehiculo.getP_NIF_INTEGRADOR());
		map.put("P_VEHI_USADO",vehiculo.getP_VEHI_USADO());
		map.put("P_MATRI_AYUNTAMIENTO",vehiculo.getP_MATRI_AYUNTAMIENTO());
		map.put("P_LIMITE_MATR_TURIS",vehiculo.getP_LIMITE_MATR_TURIS());
		map.put("P_FECHA_PRIM_MATRI",vehiculo.getP_FECHA_PRIM_MATRI());
		map.put("P_ID_LUGAR_MATRICULACION",vehiculo.getP_ID_LUGAR_MATRICULACION());
		map.put("P_KM_USO",vehiculo.getP_KM_USO());
		map.put("P_HORAS_USO",vehiculo.getP_HORAS_USO());
		map.put("P_ID_VEHICULO_PREVER",vehiculo.getP_ID_VEHICULO_PREVER());
		map.put("P_N_CILINDROS",vehiculo.getP_N_CILINDROS());
		map.put("P_CARACTERISTICAS",vehiculo.getP_CARACTERISTICAS());
		map.put("P_ANIO_FABRICA",vehiculo.getP_ANIO_FABRICA());
		//DATOS DE LA DIRECCION DEL VEHÍCULO
		map.put("P_ID_DIRECCION",vehiculo.getP_ID_DIRECCION());
		map.put("P_ID_PROVINCIA",vehiculo.getP_ID_PROVINCIA());
		map.put("P_ID_MUNICIPIO",vehiculo.getP_ID_MUNICIPIO());
		map.put("P_ID_TIPO_VIA",vehiculo.getP_ID_TIPO_VIA());
		map.put("P_NOMBRE_VIA",vehiculo.getP_NOMBRE_VIA());
		map.put("P_NUMERO",vehiculo.getP_NUMERO());
		map.put("P_COD_POSTAL",vehiculo.getP_COD_POSTAL());
		map.put("P_PUEBLO",vehiculo.getP_PUEBLO());
		map.put("P_LETRA",vehiculo.getP_LETRA());
		map.put("P_ESCALERA",vehiculo.getP_ESCALERA());
		map.put("P_BLOQUE",vehiculo.getP_BLOQUE());
		map.put("P_PLANTA",vehiculo.getP_PLANTA());
		map.put("P_PUERTA",vehiculo.getP_PUERTA());
		map.put("P_NUM_LOCAL",vehiculo.getP_NUM_LOCAL());
		map.put("P_KM",vehiculo.getP_KM());
		map.put("P_HM",vehiculo.getP_HM());
		//DATOS DEL VEHICULO PREVER
		map.put("P_PREV_MATRICULA",vehiculo.getP_PREV_MATRICULA());
		map.put("P_PREV_BASTIDOR",vehiculo.getP_PREV_BASTIDOR());
		map.put("P_PREV_CODIGO_MARCA",vehiculo.getP_PREV_CODIGO_MARCA());
		map.put("P_PREV_MODELO",vehiculo.getP_PREV_MODELO());
		map.put("P_PREV_ID_CONSTRUCCION", vehiculo.getP_PREV_ID_CONSTRUCCION());
		map.put("P_PREV_ID_UTILIZACION", vehiculo.getP_PREV_ID_UTILIZACION());
		map.put("P_PREV_CLASIFICACION_ITV",vehiculo.getP_PREV_CLASIFICACION_ITV());
		map.put("P_PREV_TIPO_ITV",vehiculo.getP_PREV_TIPO_ITV());
		//DATOS DEL INTEGRADOR
		map.put("P_TIPO_PERSONA_INTE",vehiculo.getP_TIPO_PERSONA_INTE());
		map.put("P_APELLIDO1_RAZON_SOCIAL_INTE",vehiculo.getP_APELLIDO1_RAZON_SOCIAL_INTE());
		map.put("P_APELLIDO2_INTE",vehiculo.getP_APELLIDO2_INTE());
		map.put("P_NOMBRE_INTE",vehiculo.getP_NOMBRE_INTE());
		map.put("P_TIPO_ITV",vehiculo.getP_TIPO_ITV());
		// MATE 2.5
		map.put("P_IMPORTADO",vehiculo.getP_IMPORTADO());
		map.put("P_SUBASTADO",vehiculo.getP_SUBASTADO());
		map.put("P_PAIS_IMPORTACION",vehiculo.getP_PAIS_IMPORTACION());
		map.put("P_FABRICANTE",vehiculo.getP_FABRICANTE());
		map.put("P_CARROCERIA",vehiculo.getP_CARROCERIA());
		map.put("P_CONSUMO",vehiculo.getP_CONSUMO());
		map.put("P_DIST_EJES",vehiculo.getP_DIST_EJES());
		map.put("P_VIA_ANT",vehiculo.getP_VIA_ANT());
		map.put("P_VIA_POS",vehiculo.getP_VIA_POS());
		map.put("P_ALIMENTACION",vehiculo.getP_ALIMENTACION());
		map.put("P_EMISIONES",vehiculo.getP_EMISIONES());
		map.put("P_ECO_INNOVACION",vehiculo.getP_ECO_INNOVACION());
		map.put("P_REDUCCION_ECO",vehiculo.getP_REDUCCION_ECO());
		map.put("P_CODIGO_ECO",vehiculo.getP_CODIGO_ECO());
		map.put("P_MOM", vehiculo.getP_MOM());

		//Nuevos Campos Matw
		map.put("P_MATRICULA_ORIGEN",vehiculo.getP_MATRICULA_ORIGEN());
		map.put("P_PAIS_FABRICACION", vehiculo.getP_PAIS_FABRICACION());
		map.put("P_PROCEDENCIA", vehiculo.getP_PROCEDENCIA());

		respuesta.setMapaParametros(map);
		return convertirPQToBean(respuesta);
	}

	public VehiculoBean convertirPQToBean(RespuestaGenerica resultadoVehiculo) {
		VehiculoBean vehiculo = new VehiculoBean(true);

		try {
			vehiculo.setNumColegiado(resultadoVehiculo.getParametro("P_NUM_COLEGIADO")!=null?(String)resultadoVehiculo.getParametro("P_NUM_COLEGIADO"):utilesColegiado.getNumColegiadoSession());
		}
		catch (Exception e) {
			log.info("Proceso Imprimir Trámites, no obtenemos el número de colegiado para el vehículo");
		}

		vehiculo.setIdVehiculo((BigDecimal)resultadoVehiculo.getParametro("P_ID_VEHICULO"));
		vehiculo.setBastidor((String)resultadoVehiculo.getParametro("P_BASTIDOR"));
		vehiculo.setNive((String)resultadoVehiculo.getParametro("P_NIVE"));
		vehiculo.setMatricula((String)resultadoVehiculo.getParametro("P_MATRICULA"));
		vehiculo.getMarcaBean().setCodigoMarca((BigDecimal)resultadoVehiculo.getParametro("P_CODIGO_MARCA"));
		vehiculo.setModelo((String)resultadoVehiculo.getParametro("P_MODELO"));
		// TODO Ver que hacemos con esto vehiculo.getTipoVehiculoBean().setTipoTramite(tipoTramite)
		vehiculo.setTipVehi((String)resultadoVehiculo.getParametro("P_TIPVEHI"));

		vehiculo.setCdMarca((String)resultadoVehiculo.getParametro("P_CDMARCA"));
		vehiculo.setCdModVeh((String)resultadoVehiculo.getParametro("P_CDMODVEH"));
		vehiculo.getTipoVehiculoBean().setTipoVehiculo((String)resultadoVehiculo.getParametro("P_TIPO_VEHICULO"));
		vehiculo.getServicioTraficoBean().setIdServicio((String)resultadoVehiculo.getParametro("P_ID_SERVICIO"));
		vehiculo.getServicioTraficoAnteriorBean().setIdServicio((String)resultadoVehiculo.getParametro("P_ID_SERVICIO_ANTERIOR"));
		if (resultadoVehiculo.getParametro("P_VEHICULO_AGRICOLA")!=null){
			vehiculo.setVehiculoAgricola(resultadoVehiculo.getParametro("P_VEHICULO_AGRICOLA").equals(DecisionTrafico.Si.getValorEnum())?true:false);
		}else{
			vehiculo.setVehiculoAgricola(null);
		}
		if (resultadoVehiculo.getParametro("P_VEHICULO_TRANSPORTE")!=null){
			vehiculo.setVehiculoTransporte(resultadoVehiculo.getParametro("P_VEHICULO_TRANSPORTE").equals(DecisionTrafico.Si.getValorEnum())?true:false);
		}else{
			vehiculo.setVehiculoTransporte(null);
		}
		vehiculo.setColorBean((String)resultadoVehiculo.getParametro("P_ID_COLOR"));
		vehiculo.setCarburanteBean(Combustible.convertirCarburanteBean((String)resultadoVehiculo.getParametro("P_ID_CARBURANTE")));
		vehiculo.getLugarAdquisicionBean().setIdAdquisicion((String)resultadoVehiculo.getParametro("P_ID_LUGAR_ADQUISICION"));
		vehiculo.getCriterioConstruccionBean().setIdCriterioConstruccion((String)resultadoVehiculo.getParametro("P_ID_CRITERIO_CONSTRUCCION"));
		vehiculo.getCriterioUtilizacionBean().setIdCriterioUtilizacion((String)resultadoVehiculo.getParametro("P_ID_CRITERIO_UTILIZACION"));

		if (resultadoVehiculo.getParametro("P_DIPLOMATICO")!=null){
			if(((String)resultadoVehiculo.getParametro("P_DIPLOMATICO")).equals("S")){
				vehiculo.setDiplomatico(TRUE);
			}else{
				vehiculo.setDiplomatico(FALSE);
			}
		}

		vehiculo.setCodItv((String)resultadoVehiculo.getParametro("P_CODITV"));
		vehiculo.setPotenciaFiscal((BigDecimal)resultadoVehiculo.getParametro("P_POTENCIA_FISCAL"));
		vehiculo.setPotenciaNeta((BigDecimal)resultadoVehiculo.getParametro("P_POTENCIA_NETA"));
		vehiculo.setPotenciaPeso((BigDecimal)resultadoVehiculo.getParametro("P_POTENCIA_PESO"));
		String cilindrada = (String)resultadoVehiculo.getParametro("P_CILINDRADA");
		try {
			vehiculo.setCilindrada((new BigDecimal(cilindrada)).toString());
		} catch(Exception e){
			vehiculo.setCilindrada(null);
		}
		vehiculo.setCo2((String)resultadoVehiculo.getParametro("P_CO2"));

		// DRC@21-09-2012 Incidencia: 2358
		String tara = (String)resultadoVehiculo.getParametro("P_TARA");
		try {
			vehiculo.setTara((new BigDecimal(tara)).toString());
		}catch(Exception e){
			vehiculo.setTara(null);
		}
		String pesoMma = (String)resultadoVehiculo.getParametro("P_PESO_MMA");
		try {
			vehiculo.setPesoMma((new BigDecimal(pesoMma)).toString());
		}catch(Exception e){
			vehiculo.setPesoMma(null);
		}
		vehiculo.setPlazas((BigDecimal)resultadoVehiculo.getParametro("P_PLAZAS"));

		if(resultadoVehiculo.getParametro("P_PROVINCIA_PRIMERA_MATRICULA")!=null){
			Provincia p = new Provincia();
			p.setIdProvincia(resultadoVehiculo.getParametro("P_PROVINCIA_PRIMERA_MATRICULA").toString());
			vehiculo.setProvinciaPrimeraMatricula(p);
		}else{
			Provincia p = new Provincia();
			p.setIdProvincia("-1");
			vehiculo.setProvinciaPrimeraMatricula(p);
		}

		vehiculo.setMtmaItv((String)resultadoVehiculo.getParametro("P_MTMA_ITV"));
		vehiculo.setVariante((String)resultadoVehiculo.getParametro("P_VARIANTE"));
		vehiculo.setVersion((String)resultadoVehiculo.getParametro("P_VERSION"));

		if (resultadoVehiculo.getParametro("P_EXCESO_PESO")!=null){
			if (((String)resultadoVehiculo.getParametro("P_EXCESO_PESO")).equals("S")){
				vehiculo.setExcesoPeso(TRUE);
			}else{
				vehiculo.setExcesoPeso(FALSE);
			}
		}

		vehiculo.setTipoIndustria((String)resultadoVehiculo.getParametro("P_TIPO_INDUSTRIA"));
		vehiculo.getMotivoItv().setIdMotivo((String)resultadoVehiculo.getParametro("P_ID_MOTIVO_ITV"));
		vehiculo.setClasificacionItv((String)resultadoVehiculo.getParametro("P_CLASIFICACION_ITV"));
		vehiculo.getTipoTarjetaItvBean().setIdTipoTarjetaItv((String)resultadoVehiculo.getParametro("P_ID_TIPO_TARJETA_ITV"));
		vehiculo.setConceptoItv(null!=(String)resultadoVehiculo.getParametro("P_CONCEPTO_ITV")?((String)resultadoVehiculo.getParametro("P_CONCEPTO_ITV")).toUpperCase():null);
		if (resultadoVehiculo.getParametro("P_ESTACION_ITV")!=null){
			vehiculo.setEstacionItv((String)resultadoVehiculo.getParametro("P_ESTACION_ITV"));
		}
		vehiculo.setNumPlazasPie((BigDecimal)resultadoVehiculo.getParametro("P_N_PLAZAS_PIE"));
		vehiculo.setNumHomologacion((String)resultadoVehiculo.getParametro("P_N_HOMOLOGACION"));
		//JRG. Número de ruedas ya no es obligatorio en MateW. Hay que comprobar que no tenga null cuando se trate de una matriculación quien llame al método.
		if (resultadoVehiculo.getParametro("P_N_RUEDAS")!=null){
			vehiculo.setNumRuedas((BigDecimal)resultadoVehiculo.getParametro("P_N_RUEDAS"));
		}
		vehiculo.setNumSerie((String)resultadoVehiculo.getParametro("P_N_SERIE"));
		vehiculo.getEpigrafeBean().setIdEpigrafe(utiles.quitarCerosIzquierda((String)resultadoVehiculo.getParametro("P_ID_EPIGRAFE")));
		vehiculo.getIntegradorBean().setNif((String)resultadoVehiculo.getParametro("P_NIF_INTEGRADOR"));
		vehiculo.setVehiUsado((String)resultadoVehiculo.getParametro("P_VEHI_USADO")!=null && ("SI".equals((String)resultadoVehiculo.getParametro("P_VEHI_USADO")) ||
				TipoSN.S.value().equals((String)resultadoVehiculo.getParametro("P_VEHI_USADO")))?TRUE:FALSE);
		vehiculo.setMatriAyuntamiento((String)resultadoVehiculo.getParametro("P_MATRI_AYUNTAMIENTO"));

		if (resultadoVehiculo.getParametro("P_LIMITE_MATR_TURIS")!=null){
			vehiculo.setLimiteMatrTuris(utilesFecha.getFechaFracionada((Timestamp)resultadoVehiculo.getParametro("P_LIMITE_MATR_TURIS")));
		}

		if (resultadoVehiculo.getParametro("P_FECHA_PRIM_MATRI")!=null){
			vehiculo.setFechaPrimMatri(utilesFecha.getFechaFracionada((Timestamp)resultadoVehiculo.getParametro("P_FECHA_PRIM_MATRI")));
		}
		if (resultadoVehiculo.getParametro("P_FECHA_INSPECCION")!=null){
			vehiculo.setFechaInspeccion(utilesFecha.getFechaFracionada((Timestamp)resultadoVehiculo.getParametro("P_FECHA_INSPECCION")));
		}	
		if (resultadoVehiculo.getParametro("P_FECHA_ITV")!=null){
			vehiculo.setFechaItv(utilesFecha.getFechaFracionada((Timestamp)resultadoVehiculo.getParametro("P_FECHA_ITV")));
		}
		if (resultadoVehiculo.getParametro("P_FECDESDE")!=null){
			vehiculo.setFecDesde(utilesFecha.getFechaFracionada((Timestamp)resultadoVehiculo.getParametro("P_FECDESDE")));
		}
		if (resultadoVehiculo.getParametro("P_FECHA_MATRICULACION")!=null){
			vehiculo.setFechaMatriculacion(utilesFecha.getFechaFracionada((Timestamp)resultadoVehiculo.getParametro("P_FECHA_MATRICULACION")));
		}

		vehiculo.getLugarPrimeraMatriculacionBean().setIdLugarPrimeraMatriculacion((String)resultadoVehiculo.getParametro("P_ID_LUGAR_MATRICULACION"));
		vehiculo.setKmUso((BigDecimal)resultadoVehiculo.getParametro("P_KM_USO"));
		vehiculo.setHorasUso((BigDecimal)resultadoVehiculo.getParametro("P_HORAS_USO"));
		BigDecimal idVehiPrever = (BigDecimal) resultadoVehiculo.getParametro("P_ID_VEHICULO_PREVER");
		if(idVehiPrever!=null){
			vehiculo.getVehiculoPreverBean().setIdVehiculoPrever(idVehiPrever.toString());
		}
		vehiculo.setNumCilindros((BigDecimal)resultadoVehiculo.getParametro("P_N_CILINDROS"));
		vehiculo.setCaracteristicas((String)resultadoVehiculo.getParametro("P_CARACTERISTICAS"));
		vehiculo.setAnioFabrica((String)resultadoVehiculo.getParametro("P_ANIO_FABRICA"));
		vehiculo.setTipoItv((String)resultadoVehiculo.getParametro("P_TIPO_ITV"));

		if (resultadoVehiculo.getParametro("P_ID_DIRECCION")!=null){
			vehiculo.getDireccionBean().setIdDireccion(resultadoVehiculo.getParametro("P_ID_DIRECCION")!= null?Integer.valueOf((((BigDecimal)resultadoVehiculo.getParametro("P_ID_DIRECCION")).toString())):null);
			vehiculo.getDireccionBean().getMunicipio().getProvincia().setIdProvincia((String)resultadoVehiculo.getParametro("P_ID_PROVINCIA"));
			vehiculo.getDireccionBean().getMunicipio().setIdMunicipio((String)resultadoVehiculo.getParametro("P_ID_MUNICIPIO"));
			vehiculo.getDireccionBean().getTipoVia().setIdTipoVia((String)resultadoVehiculo.getParametro("P_ID_TIPO_VIA"));
			vehiculo.getDireccionBean().setNombreVia((String)resultadoVehiculo.getParametro("P_NOMBRE_VIA"));
			vehiculo.getDireccionBean().setNumero((String)resultadoVehiculo.getParametro("P_NUMERO"));
			vehiculo.getDireccionBean().setCodPostal((String)resultadoVehiculo.getParametro("P_COD_POSTAL"));
			vehiculo.getDireccionBean().setCodPostalCorreos((String)resultadoVehiculo.getParametro("P_COD_POSTAL"));
			vehiculo.getDireccionBean().setPueblo((String)resultadoVehiculo.getParametro("P_PUEBLO"));
			vehiculo.getDireccionBean().setPuebloCorreos((String)resultadoVehiculo.getParametro("P_PUEBLO"));
			vehiculo.getDireccionBean().setLetra((String)resultadoVehiculo.getParametro("P_LETRA"));
			vehiculo.getDireccionBean().setEscalera((String)resultadoVehiculo.getParametro("P_ESCALERA"));
			vehiculo.getDireccionBean().setBloque((String)resultadoVehiculo.getParametro("P_BLOQUE"));
			vehiculo.getDireccionBean().setPlanta((String)resultadoVehiculo.getParametro("P_PLANTA"));
			vehiculo.getDireccionBean().setPuerta((String)resultadoVehiculo.getParametro("P_PUERTA"));
			vehiculo.getDireccionBean().setNumLocal((resultadoVehiculo.getParametro("P_NUM_LOCAL")!= null?new Integer((String)resultadoVehiculo.getParametro("P_NUM_LOCAL")):null));
			vehiculo.getDireccionBean().setPuntoKilometrico(resultadoVehiculo.getParametro("P_KM")!=null? (((BigDecimal)resultadoVehiculo.getParametro("P_KM")).toString()):null);
			vehiculo.getDireccionBean().setHm(resultadoVehiculo.getParametro("P_HM")==null?null:(String)resultadoVehiculo.getParametro("P_HM").toString());
		}

		if (resultadoVehiculo.getParametro("P_TIPO_PERSONA_INTE") != null){
			vehiculo.getIntegradorBean().setTipoPersona((String)resultadoVehiculo.getParametro("P_TIPO_PERSONA_INTE"));
		}

		vehiculo.getIntegradorBean().setApellido1RazonSocial((String)resultadoVehiculo.getParametro("P_APELLIDO1_RAZON_SOCIAL_INTE"));
		vehiculo.getIntegradorBean().setApellido2((String)resultadoVehiculo.getParametro("P_APELLIDO2_INTE"));
		vehiculo.getIntegradorBean().setNombre((String)resultadoVehiculo.getParametro("P_NOMBRE_INTE"));
		vehiculo.getIntegradorBean().setNif((String)resultadoVehiculo.getParametro("P_NIF_INTEGRADOR"));

		// MATE 2.5
		vehiculo.setCarburanteBean(Combustible.convertirCarburanteBean((String)
				resultadoVehiculo.getParametro("P_ID_CARBURANTE")));
		vehiculo.setAlimentacionBean(Alimentacion.convertir((String)
				resultadoVehiculo.getParametro("P_ALIMENTACION")));
		vehiculo.setCarroceriaBean(Carroceria.convertir((String)
				resultadoVehiculo.getParametro("P_CARROCERIA")));
		vehiculo.setCodigoEco((String)resultadoVehiculo.getParametro("P_CODIGO_ECO"));
		vehiculo.setConsumo((BigDecimal)resultadoVehiculo.getParametro("P_CONSUMO"));
		vehiculo.setDistanciaEntreEjes((BigDecimal)resultadoVehiculo.getParametro("P_DIST_EJES"));
		vehiculo.setEcoInnovacion((String)resultadoVehiculo.getParametro("P_ECO_INNOVACION"));
		vehiculo.setFabricante((String)resultadoVehiculo.getParametro("P_FABRICANTE"));

		String importado = (String) resultadoVehiculo.getParametro("P_IMPORTADO");
		if(importado != null && (importado.equalsIgnoreCase(TipoSN.S.value()) || importado.equalsIgnoreCase("SI"))){
			vehiculo.setImportado(true);
		}else{
			vehiculo.setImportado(false);
		}
		vehiculo.setNivelEmisiones((String) resultadoVehiculo.getParametro("P_EMISIONES"));
		vehiculo.setReduccionEco((BigDecimal)resultadoVehiculo.getParametro("P_REDUCCION_ECO"));
		vehiculo.setViaAnterior((BigDecimal)resultadoVehiculo.getParametro("P_VIA_ANT"));
		vehiculo.setViaPosterior((BigDecimal)resultadoVehiculo.getParametro("P_VIA_POS"));
		vehiculo.setMom((BigDecimal)resultadoVehiculo.getParametro("P_MOM"));

		// DRC@06-02-2013 Incidencia Mantis: 3019
		vehiculo.setCheckFechaCaducidadITV(resultadoVehiculo.getParametro("P_CHECK_FECHA_CADUCIDAD_ITV")!=null && resultadoVehiculo.getParametro("P_CHECK_FECHA_CADUCIDAD_ITV").equals("SI")?TRUE:FALSE);

		vehiculo.setMatriculaOrigen((String)resultadoVehiculo.getParametro("P_MATRICULA_ORIGEN"));
		// MATE. No existe en MATW. Recuperación del campo P_ID_PROCEDENCIA. Setea el fabricacionBean del vehiculoBean:
		String idProcedencia = (String) resultadoVehiculo.getParametro("P_ID_PROCEDENCIA");
		if (idProcedencia != null && !idProcedencia.equals("")) {
			Fabricacion fabricacion = Fabricacion.convertir(idProcedencia);
			if (fabricacion == null) {
				// Solo se puede dar este caso si se recupera como idProcedencia un "2" antigua Procedencia.Subasta anterior a mate 2.5
				// Da valor al nuevo campo 'subasta' y deja a null la 'procedencia' puesto que no tiene correspondencia en MATW:
				vehiculo.setSubasta(true);
			} else {
				FabricacionBean fabricacionBean = new FabricacionBean();
				fabricacionBean.setDescripcion(fabricacion.getNombreEnum());
				fabricacionBean.setIdFabricacion(fabricacion.getValorEnum());
				vehiculo.setFabricacionBean(fabricacionBean);
			}
		}
		// MATE. No existe en MATW. Recuperación del campo P_PAIS_IMPORTACION. Setea el paisImportacionBean del vehiculoBean:
		String idPaisImportacion = (String) resultadoVehiculo.getParametro("P_PAIS_IMPORTACION");
		if(idPaisImportacion != null && !idPaisImportacion.equals("")){
			PaisImportacionBean paisImportacionBean = PaisImportacion.convertirABean(idPaisImportacion);
			vehiculo.setPaisImportacionBean(paisImportacionBean);
		}
		// Poner propertie

		vehiculo.setMatriculaOrigExtr((String)resultadoVehiculo.getParametro("P_MATRICULA_ORIG_EXTR"));

		// MATW. No existe en MATE. Recuperación del campo P_PAIS_FABRICACION. Setea el paisFabricacionBean del vehiculoBean:
		String idPaisFabricacion = (String) resultadoVehiculo.getParametro("P_PAIS_FABRICACION");
		if(idPaisFabricacion != null && !idPaisFabricacion.equals("")){
			// Recuperado país de fabricación
			PaisFabricacion paisFabricacion = PaisFabricacion.getPaisFabricacion(idPaisFabricacion);
			if(paisFabricacion != null){
				PaisFabricacionBean paisFabricacionBean = new PaisFabricacionBean();
				paisFabricacionBean.setIdPaisFabricacion(paisFabricacion.getValorEnum());
				paisFabricacionBean.setDescripcion(paisFabricacion.getNombreEnum());
				vehiculo.setPaisFabricacionBean(paisFabricacionBean);
			}
		}

		// MATW. No existe en MATE. Recuperación del campo P_PROCEDENCIA. Setea la procedencia del vehiculoBean:
		vehiculo.setProcedencia((String)resultadoVehiculo.getParametro("P_PROCEDENCIA"));

		String subastado = (String) resultadoVehiculo.getParametro("P_SUBASTADO");
		if(subastado != null && (subastado.equalsIgnoreCase(TipoSN.S.value()) || subastado.equalsIgnoreCase("SI"))){
			vehiculo.setSubasta(true);
		// Puede que la recuperación de un antiguo idProcedencia = 2 haya dado valor
		// ya a subastado. Si tiene valor no se establece a false.
		}else if(vehiculo.getSubasta() == null){
			vehiculo.setSubasta(false);
		}

		vehiculo.setFichaTecnicaRD750(resultadoVehiculo.getParametro("P_FICHA_TECNICA_RD750")!=null && !resultadoVehiculo.getParametro("P_FICHA_TECNICA_RD750").equals("") && 0 == (new BigDecimal(1).compareTo((BigDecimal)resultadoVehiculo.getParametro("P_FICHA_TECNICA_RD750")))?true:false);

		// Vehiculos multifasicos
		vehiculo.getMarcaBaseBean().setCodigoMarca((BigDecimal)resultadoVehiculo.getParametro("P_CODIGO_MARCA_BASE"));
		vehiculo.setFabricanteBase((String)resultadoVehiculo.getParametro("P_FABRICANTE_BASE"));
		vehiculo.setTipoItvBase((String)resultadoVehiculo.getParametro("P_TIPO_BASE"));
		vehiculo.setVarianteBase((String)resultadoVehiculo.getParametro("P_VARIANTE_BASE"));
		vehiculo.setVersionBase((String)resultadoVehiculo.getParametro("P_VERSION_BASE"));
		vehiculo.setNumHomologacionBase((String)resultadoVehiculo.getParametro("P_N_HOMOLOGACION_BASE"));
		vehiculo.setMomBase((BigDecimal)resultadoVehiculo.getParametro("P_MOM_BASE"));
		CategoriaElectrica categoriaElectrica = CategoriaElectrica.convertir((String)resultadoVehiculo.getParametro("P_CATEGORIA_ELECTRICA"));
		if (categoriaElectrica!=null) {
			vehiculo.setCategoriaElectricaBean(new CategoriaElectricaBean(categoriaElectrica.getValorEnum(), categoriaElectrica.getNombreEnum()));
		}
		vehiculo.setAutonomiaElectrica((BigDecimal)resultadoVehiculo.getParametro("P_AUTONOMIA_ELECTRICA"));

		vehiculo.setBastidorMatriculado(resultadoVehiculo.getParametro("P_BASTIDOR_MATRICULADO")!=null
				&& !resultadoVehiculo.getParametro("P_BASTIDOR_MATRICULADO").equals("") ? (BigDecimal) resultadoVehiculo.getParametro("P_BASTIDOR_MATRICULADO") : null);

		vehiculo.setDoiResponsableKm((String) resultadoVehiculo.getParametro("P_DOI_RESPONSABLE_LECTURA_KM"));

		if (resultadoVehiculo.getParametro("P_FECHA_LECTURA_KM") != null) {
			Timestamp fecha = (Timestamp) resultadoVehiculo.getParametro("P_FECHA_LECTURA_KM");
			vehiculo.setFechaLecturaKm(utilesFecha.getFechaFracionada(fecha));
		}

		return vehiculo;
	}

	public VehiculoBean convertirPQToBeanPrever(RespuestaGenerica resultadoVehiculo) {
		VehiculoBean vehiculoPrever = new VehiculoBean(true);
		vehiculoPrever.setMatricula((String)resultadoVehiculo.getParametro("P_PREV_MATRICULA"));
		vehiculoPrever.setBastidor((String)resultadoVehiculo.getParametro("P_PREV_BASTIDOR"));
		vehiculoPrever.getMarcaBean().setCodigoMarca((BigDecimal)resultadoVehiculo.getParametro("P_PREV_CODIGO_MARCA"));
		vehiculoPrever.setModelo((String)resultadoVehiculo.getParametro("P_PREV_MODELO"));
		vehiculoPrever.setNive((String)resultadoVehiculo.getParametro("P_NIVE"));
		vehiculoPrever.getCriterioConstruccionBean().setIdCriterioConstruccion((String)resultadoVehiculo.getParametro("P_PREV_ID_CONSTRUCCION"));
		vehiculoPrever.getCriterioUtilizacionBean().setIdCriterioUtilizacion((String)resultadoVehiculo.getParametro("P_PREV_ID_UTILIZACION"));
		vehiculoPrever.setClasificacionItv((String)resultadoVehiculo.getParametro("P_PREV_CLASIFICACION_ITV"));
		vehiculoPrever.getTipoTarjetaItvBean().setIdTipoTarjetaItv((String)resultadoVehiculo.getParametro("P_PREV_ID_TIPO_TARJETA_ITV"));
		vehiculoPrever.setTipoItv((String)resultadoVehiculo.getParametro("P_PREV_TIPO_ITV"));
		return vehiculoPrever;
	}

	public VehiculoBean convertirPQToBeanPrever(BeanPQVehiculosGuardar vehiculo){
		VehiculoBean vehiculoPrever = new VehiculoBean(true);
		vehiculoPrever.setMatricula(vehiculo.getP_PREV_MATRICULA());
		vehiculoPrever.setBastidor(vehiculo.getP_PREV_BASTIDOR());
		vehiculoPrever.getMarcaBean().setCodigoMarca(vehiculo.getP_PREV_CODIGO_MARCA());
		vehiculoPrever.setModelo(vehiculo.getP_PREV_MODELO());
		vehiculoPrever.setNive(vehiculo.getP_NIVE());
		vehiculoPrever.getCriterioConstruccionBean().setIdCriterioConstruccion(vehiculo.getP_PREV_ID_CONSTRUCCION());
		vehiculoPrever.getCriterioUtilizacionBean().setIdCriterioUtilizacion(vehiculo.getP_PREV_ID_UTILIZACION());
		vehiculoPrever.setClasificacionItv(vehiculo.getP_PREV_CLASIFICACION_ITV());
		vehiculoPrever.setTipoItv(vehiculo.getP_PREV_TIPO_ITV());
		return vehiculoPrever;
	}

	/**
	 * Método para convertir vehículos al formato aceptado por el paquete.
	 * @param vehiculo
	 * @param tramiteTrafico
	 * @return
	 */
	public BeanPQGUARDAR convertirBeanToPQNuevo(VehiculoBean vehiculo) {

		BeanPQGUARDAR vehiculoPQ = new BeanPQGUARDAR();

		// Si viene el numColegiado lo guardo, si no viene lo guardo de sesion

		vehiculoPQ.setP_ID_VEHICULO(vehiculo.getIdVehiculo());
		vehiculoPQ.setP_BASTIDOR(vehiculo.getBastidor()!=null && !vehiculo.getBastidor().equals("")?vehiculo.getBastidor():null);
		vehiculoPQ.setP_NIVE(vehiculo.getNive()!=null && !vehiculo.getNive().equals("")?vehiculo.getNive():null);
		vehiculoPQ.setP_MATRICULA(vehiculo.getMatricula()!=null && !vehiculo.getMatricula().equals("")?vehiculo.getMatricula():null);

		//Controlar para que no haya nullPointerException
		if (vehiculo.getMarcaBean()!=null){
			if (vehiculo.getMarcaBean().getCodigoMarca()!=null){
				vehiculoPQ.setP_CODIGO_MARCA(!vehiculo.getMarcaBean().getCodigoMarca().equals(new BigDecimal("-1"))?vehiculo.getMarcaBean().getCodigoMarca():null);
			}else{
				vehiculoPQ.setP_CODIGO_MARCA(null);
			}
		}else{
			vehiculoPQ.setP_CODIGO_MARCA(null);
		}

		if (vehiculo.getModelo()!=null){
			vehiculoPQ.setP_MODELO(vehiculo.getModelo()!=null && !vehiculo.getModelo().equals("") && !vehiculo.getModelo().equals("-1")?vehiculo.getModelo():null);
		}else{
			vehiculoPQ.setP_MODELO(null);
		}

		vehiculoPQ.setP_TIPVEHI(vehiculo.getTipVehi()!=null ? vehiculo.getTipVehi().getValorEnum():null);
		vehiculoPQ.setP_CDMARCA(vehiculo.getCdMarca()!=null && !vehiculo.getCdMarca().equals("") && !vehiculo.getCdMarca().equals("-1")?vehiculo.getCdMarca():null);
		vehiculoPQ.setP_CDMODVEH(vehiculo.getCdModVeh()!=null && !vehiculo.getCdModVeh().equals("") && !vehiculo.getCdModVeh().equals("-1")?vehiculo.getCdModVeh():null);

		if (vehiculo.getTipoVehiculoBean()!=null){
			vehiculoPQ.setP_TIPO_VEHICULO(vehiculo.getTipoVehiculoBean().getTipoVehiculo()!=null && !vehiculo.getTipoVehiculoBean().getTipoVehiculo().equals("") && !vehiculo.getTipoVehiculoBean().getTipoVehiculo().equals("-1")?vehiculo.getTipoVehiculoBean().getTipoVehiculo():null);
		}else{
			vehiculoPQ.setP_TIPO_VEHICULO(null);
		}

		if (vehiculo.getServicioTraficoBean()!=null){
			vehiculoPQ.setP_ID_SERVICIO(vehiculo.getServicioTraficoBean().getIdServicio()!=null && !vehiculo.getServicioTraficoBean().getIdServicio().equals("") && !vehiculo.getServicioTraficoBean().getIdServicio().equals("-1")?vehiculo.getServicioTraficoBean().getIdServicio():null);
		}else{
			vehiculoPQ.setP_ID_SERVICIO(null);
		}

		if (vehiculo.getServicioTraficoAnteriorBean()!=null){
			vehiculoPQ.setP_ID_SERVICIO_ANTERIOR(vehiculo.getServicioTraficoAnteriorBean().getIdServicio()!=null && !vehiculo.getServicioTraficoAnteriorBean().getIdServicio().equals("") && !vehiculo.getServicioTraficoAnteriorBean().getIdServicio().equals("-1")?vehiculo.getServicioTraficoAnteriorBean().getIdServicio():null);
		}else{
			vehiculoPQ.setP_ID_SERVICIO_ANTERIOR(null);
		}

		vehiculoPQ.setP_VEHICULO_AGRICOLA(null!=vehiculo.isVehiculoAgricola()?(true==vehiculo.isVehiculoAgricola()?DecisionTrafico.Si.getValorEnum():DecisionTrafico.No.getValorEnum()):null);
		vehiculoPQ.setP_VEHICULO_TRANSPORTE(null!=vehiculo.isVehiculoTransporte()?(true==vehiculo.isVehiculoTransporte()?DecisionTrafico.Si.getValorEnum():DecisionTrafico.No.getValorEnum()):null);

		if (vehiculo.getColorBean()!=null){
			vehiculoPQ.setP_ID_COLOR(vehiculo.getColorBean().getValorEnum()!=null && !vehiculo.getColorBean().getValorEnum().equals("") && !vehiculo.getColorBean().getValorEnum().equals("-1")?vehiculo.getColorBean().getValorEnum():null);
		}else{
			vehiculoPQ.setP_ID_COLOR(null);
		}

		if (vehiculo.getCarburanteBean()!=null){
			vehiculoPQ.setP_ID_CARBURANTE(vehiculo.getCarburanteBean().getIdCarburante());
		}else{
			vehiculoPQ.setP_ID_CARBURANTE(null);
		}

		if (vehiculo.getLugarAdquisicionBean()!=null){
			vehiculoPQ.setP_ID_LUGAR_ADQUISICION(vehiculo.getLugarAdquisicionBean().getIdAdquisicion()!=null && !vehiculo.getLugarAdquisicionBean().getIdAdquisicion().equals("") && !vehiculo.getLugarAdquisicionBean().getIdAdquisicion().equals("-1")?vehiculo.getLugarAdquisicionBean().getIdAdquisicion():null);
		}else{
			vehiculoPQ.setP_ID_LUGAR_ADQUISICION(null);
		}

		if (vehiculo.getCriterioConstruccionBean()!=null){
			vehiculoPQ.setP_ID_CRITERIO_CONSTRUCCION(vehiculo.getCriterioConstruccionBean().getIdCriterioConstruccion()!=null && !vehiculo.getCriterioConstruccionBean().getIdCriterioConstruccion().equals("") && !vehiculo.getCriterioConstruccionBean().getIdCriterioConstruccion().equals("-1")?vehiculo.getCriterioConstruccionBean().getIdCriterioConstruccion():null);
		}else{
			vehiculoPQ.setP_ID_CRITERIO_CONSTRUCCION(null);
		}

		if (vehiculo.getCriterioUtilizacionBean()!=null){
			vehiculoPQ.setP_ID_CRITERIO_UTILIZACION(vehiculo.getCriterioUtilizacionBean().getIdCriterioUtilizacion()!=null && !vehiculo.getCriterioUtilizacionBean().getIdCriterioUtilizacion().equals("") && !vehiculo.getCriterioUtilizacionBean().getIdCriterioUtilizacion().equals("-1")?vehiculo.getCriterioUtilizacionBean().getIdCriterioUtilizacion():null);
		}else{
			vehiculoPQ.setP_ID_CRITERIO_UTILIZACION(null);
		}

		if (vehiculo.getDirectivaCeeBean()!=null){
			vehiculoPQ.setP_ID_DIRECTIVA_CEE(vehiculo.getDirectivaCeeBean().getIdDirectivaCEE()!=null &&
					!vehiculo.getDirectivaCeeBean().getIdDirectivaCEE().isEmpty() && 
					!vehiculo.getDirectivaCeeBean().getIdDirectivaCEE().equals("-1") ?
							vehiculo.getDirectivaCeeBean().getIdDirectivaCEE() : null);
		} else {
			vehiculoPQ.setP_ID_DIRECTIVA_CEE(null);
		}

		vehiculoPQ.setP_DIPLOMATICO(vehiculo.getDiplomatico());

		vehiculoPQ.setP_PLAZAS(vehiculo.getPlazas());
		vehiculoPQ.setP_CODITV(vehiculo.getCodItv()!=null && !vehiculo.getCodItv().equals("")?vehiculo.getCodItv():null);
		vehiculoPQ.setP_POTENCIA_FISCAL(vehiculo.getPotenciaFiscal()!=null?vehiculo.getPotenciaFiscal():null);
		vehiculoPQ.setP_POTENCIA_NETA(vehiculo.getPotenciaNeta()!=null?vehiculo.getPotenciaNeta():null);
		vehiculoPQ.setP_POTENCIA_PESO(vehiculo.getPotenciaPeso()!=null?vehiculo.getPotenciaPeso():null);
		vehiculoPQ.setP_CILINDRADA(vehiculo.getCilindrada()!=null && !vehiculo.getCilindrada().equals("")?vehiculo.getCilindrada():null);
		vehiculoPQ.setP_CO2(vehiculo.getCo2()!=null && !vehiculo.getCo2().equals("")?vehiculo.getCo2():null);
		vehiculoPQ.setP_TARA(vehiculo.getTara()!=null && !vehiculo.getTara().equals("")?vehiculo.getTara():null);
		vehiculoPQ.setP_PESO_MMA(vehiculo.getPesoMma()!=null && !vehiculo.getPesoMma().equals("")?vehiculo.getPesoMma():null);
		vehiculoPQ.setP_EXCESO_PESO(vehiculo.getExcesoPeso());
		vehiculoPQ.setP_TIPO_INDUSTRIA(vehiculo.getTipoIndustria()!=null && !vehiculo.getTipoIndustria().equals("")?vehiculo.getTipoIndustria():null);
		vehiculoPQ.setP_MTMA_ITV((vehiculo.getMtmaItv()!=null && !vehiculo.getMtmaItv().equals("")?vehiculo.getMtmaItv():null));
		vehiculoPQ.setP_VERSION(vehiculo.getVersion()!=null && !vehiculo.getVersion().equals("")?vehiculo.getVersion():null);
		vehiculoPQ.setP_VARIANTE(vehiculo.getVariante()!=null && !vehiculo.getVariante().equals("")?vehiculo.getVariante():null);

		if (vehiculo.getMotivoItv()!=null){
			vehiculoPQ.setP_ID_MOTIVO_ITV(vehiculo.getMotivoItv().getIdMotivo()!=null && !vehiculo.getMotivoItv().getIdMotivo().equals("") && !vehiculo.getMotivoItv().getIdMotivo().equals("-1")?vehiculo.getMotivoItv().getIdMotivo():null);
		}else{
			vehiculoPQ.setP_ID_MOTIVO_ITV(null);
		}

		vehiculoPQ.setP_CLASIFICACION_ITV(vehiculo.getClasificacionItv()!=null && !vehiculo.getClasificacionItv().equals("")?vehiculo.getClasificacionItv():null);

		if (vehiculo.getTipoTarjetaItvBean()!=null){
			vehiculoPQ.setP_ID_TIPO_TARJETA_ITV(vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv()!=null && !vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals("") && !vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals("-1")?vehiculo.getTipoTarjetaItvBean().getIdTipoTarjetaItv():null);
		}else{
			vehiculoPQ.setP_ID_TIPO_TARJETA_ITV(null);
		}

		vehiculoPQ.setP_CONCEPTO_ITV(vehiculo.getConceptoItv()!=null && !vehiculo.getConceptoItv().equals("") && !vehiculo.getConceptoItv().equals("-1")?vehiculo.getConceptoItv().toUpperCase():null);
		vehiculoPQ.setP_ESTACION_ITV(vehiculo.getEstacionItv()!=null && !vehiculo.getEstacionItv().equals("") && !vehiculo.getEstacionItv().equals("-1")?vehiculo.getEstacionItv():null);
		vehiculoPQ.setP_N_PLAZAS_PIE(vehiculo.getNumPlazasPie());

		if (vehiculo.getNumHomologacion()!=null){
			vehiculoPQ.setP_N_HOMOLOGACION(null==vehiculo.getNumHomologacion() ? null:vehiculo.getNumHomologacion());
		}else{
			vehiculoPQ.setP_N_HOMOLOGACION(null);
		}

		if (vehiculo.getNumRuedas()!=null){
			vehiculoPQ.setP_N_RUEDAS(vehiculo.getNumRuedas());
		}

		if (vehiculo.getNumSerie()!=null){
			vehiculoPQ.setP_N_SERIE(null==vehiculo.getNumSerie()?null:vehiculo.getNumSerie());
		}else{
			vehiculoPQ.setP_N_SERIE(null);
		}

		if (vehiculo.getEpigrafeBean()!=null){
			vehiculoPQ.setP_ID_EPIGRAFE(vehiculo.getEpigrafeBean().getIdEpigrafe()!=null && !vehiculo.getEpigrafeBean().getIdEpigrafe().equals("")?vehiculo.getEpigrafeBean().getIdEpigrafe():null);
		}else{
			vehiculoPQ.setP_ID_EPIGRAFE(null);
		}

		if (vehiculo.getIntegradorBean() != null){
			if (vehiculo.getIntegradorBean().getTipoPersona()!=null){
				vehiculoPQ.setP_TIPO_PERSONA_INTE(vehiculo.getIntegradorBean().getTipoPersona().getValorEnum());
			}
			vehiculoPQ.setP_APELLIDO1_RAZON_SOCIAL_INTE(null==vehiculo.getIntegradorBean().getApellido1RazonSocial()?null:!vehiculo.getIntegradorBean().getApellido1RazonSocial().equals("")?vehiculo.getIntegradorBean().getApellido1RazonSocial():null);
			vehiculoPQ.setP_APELLIDO2_INTE(null==vehiculo.getIntegradorBean().getApellido2()?null:!vehiculo.getIntegradorBean().getApellido2().equals("")?vehiculo.getIntegradorBean().getApellido2():null);
			vehiculoPQ.setP_NOMBRE_INTE(null==vehiculo.getIntegradorBean().getNombre()?null:!vehiculo.getIntegradorBean().getNombre().equals("")?vehiculo.getIntegradorBean().getNombre():null);
			vehiculoPQ.setP_NIF_INTEGRADOR(vehiculo.getIntegradorBean().getNif()!=null && !vehiculo.getIntegradorBean().getNif().equals("")?vehiculo.getIntegradorBean().getNif():null);
		}

		vehiculoPQ.setP_VEHI_USADO(vehiculo.getVehiUsado()!=null && vehiculo.getVehiUsado().equals(TRUE) ? "SI":"NO");
		vehiculoPQ.setP_MATRI_AYUNTAMIENTO(vehiculo.getMatriAyuntamiento()!=null && !vehiculo.getMatriAyuntamiento().equals("")?vehiculo.getMatriAyuntamiento():null);

		vehiculoPQ.setP_TIPO_ITV(vehiculo.getTipoItv());

		// MATE 2.5
		if(vehiculo.getImportado() != null){
			vehiculoPQ.setP_IMPORTADO(TipoSN.S.value());
		}else{
			vehiculoPQ.setP_IMPORTADO(TipoSN.N.value());
		}
		if(vehiculo.getSubasta() != null){
			vehiculoPQ.setP_SUBASTADO(TipoSN.S.value());
		}else{
			vehiculoPQ.setP_SUBASTADO(TipoSN.N.value());
		}
		if(vehiculo.getFabricante() != null && !vehiculo.getFabricante().equals("")){
			vehiculoPQ.setP_FABRICANTE(vehiculo.getFabricante());
		}else{
			vehiculoPQ.setP_FABRICANTE(null);
		}
		if(vehiculo.getCarroceriaBean() != null && vehiculo.getCarroceriaBean().getIdCarroceria() != null
				&& !vehiculo.getCarroceriaBean().getIdCarroceria().equals("")){
			vehiculoPQ.setP_CARROCERIA(vehiculo.getCarroceriaBean().getIdCarroceria());
		}else{
			vehiculoPQ.setP_CARROCERIA(null);
		}
		if(vehiculo.getConsumo() != null){
			vehiculoPQ.setP_CONSUMO(vehiculo.getConsumo());
		}else{
			vehiculoPQ.setP_CONSUMO(null);
		}
		if(vehiculo.getDistanciaEntreEjes() != null){
			vehiculoPQ.setP_DIST_EJES(vehiculo.getDistanciaEntreEjes());
		}else{
			vehiculoPQ.setP_DIST_EJES(null);
		}
		if(vehiculo.getViaAnterior() != null){
			vehiculoPQ.setP_VIA_ANT(vehiculo.getViaAnterior());
		}else{
			vehiculoPQ.setP_VIA_ANT(null);
		}
		if(vehiculo.getViaPosterior() != null){
			vehiculoPQ.setP_VIA_POS(vehiculo.getViaPosterior());
		}else{
			vehiculoPQ.setP_VIA_POS(null);
		}
		if(vehiculo.getAlimentacionBean() != null && vehiculo.getAlimentacionBean().getIdAlimentacion() != null
				&& !vehiculo.getAlimentacionBean().getIdAlimentacion().equals("")){
			vehiculoPQ.setP_ALIMENTACION(vehiculo.getAlimentacionBean().getIdAlimentacion());
		}else{
			vehiculoPQ.setP_ALIMENTACION(null);
		}
		if(vehiculo.getNivelEmisiones() != null && !vehiculo.getNivelEmisiones().equals("")){
			vehiculoPQ.setP_EMISIONES(vehiculo.getNivelEmisiones());
		}else{
			vehiculoPQ.setP_EMISIONES(null);
		}
		if(vehiculo.getEcoInnovacion() != null && !vehiculo.getEcoInnovacion().equals("")){
			vehiculoPQ.setP_ECO_INNOVACION(vehiculo.getEcoInnovacion());
		}else{
			vehiculoPQ.setP_ECO_INNOVACION(null);
		}
		if(vehiculo.getReduccionEco() != null){
			vehiculoPQ.setP_REDUCCION_ECO(vehiculo.getReduccionEco());
		}else{
			vehiculoPQ.setP_REDUCCION_ECO(null);
		}
		if(vehiculo.getCodigoEco() != null && !vehiculo.getCodigoEco().equals("")){
			vehiculoPQ.setP_CODIGO_ECO(vehiculo.getCodigoEco());
		}else{
			vehiculoPQ.setP_CODIGO_ECO(null);
		}
		if(vehiculo.getMom() != null){
			vehiculoPQ.setP_MOM(vehiculo.getMom());
		}else{
			vehiculoPQ.setP_CODIGO_ECO(null);
		}
		// FIN MATE 2.5

		// MATE. No existen en MATW. Seteo P_ID_PROCEDENCIA y P_FABRICACION. Viene del fabricacionBean del vehiculoBean:
		if(null != vehiculo.getFabricacionBean()){
			vehiculoPQ.setP_ID_PROCEDENCIA(vehiculo.getFabricacionBean().getIdFabricacion());
			vehiculoPQ.setP_FABRICACION(vehiculo.getFabricacionBean().getIdFabricacion());
		}

		// MATE. No existe en MATW. Seteo del P_PAIS_IMPORTACION. Viene del paisImportacionBean del vehiculoBean:
		if (vehiculo.getPaisImportacionBean() != null && vehiculo.getPaisImportacionBean().getIdPaisImportacion() != null
				&& !vehiculo.getPaisImportacionBean().getIdPaisImportacion().equals("")) {
			vehiculoPQ.setP_PAIS_IMPORTACION(vehiculo.getPaisImportacionBean().getIdPaisImportacion());
		} else {
			vehiculoPQ.setP_PAIS_IMPORTACION(null);
		}

		// MATW. No existe en MATE. Seteo del P_PROCEDENCIA. Viene de la propiedad procedencia del vehiculoBean:
		if (vehiculo.getProcedencia()!=null){
			vehiculoPQ.setP_PROCEDENCIA(vehiculo.getProcedencia());
		}else{
			vehiculoPQ.setP_PROCEDENCIA(null);
		}

		// MATW. No existe en MATE. Seteo del P_PAIS_FABRICACION. Viene del paisFabricacionBean del vehiculoBean:
		if(null != vehiculo.getPaisFabricacionBean()){
			vehiculoPQ.setP_PAIS_FABRICACION(vehiculo.getPaisFabricacionBean().getIdPaisFabricacion());
		}

		return vehiculoPQ;
	}

}