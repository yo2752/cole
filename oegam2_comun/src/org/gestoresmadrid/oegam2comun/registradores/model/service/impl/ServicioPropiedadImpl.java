package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.registradores.model.dao.PropiedadDao;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.vo.PropiedadVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioAeronaveRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioBuqueRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioEstablecimientoRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioIntervinienteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioMaquinariaRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioOtrosBienesRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioPropiedad;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioPropiedadIndustrialRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioPropiedadIntelectualRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioVehiculoRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PropiedadDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;

@Service
public class ServicioPropiedadImpl implements ServicioPropiedad {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private PropiedadDao propiedadDao;

	@Autowired
	private ServicioIntervinienteRegistro servicioIntervinienteRegistro;

	@Autowired
	private ServicioVehiculoRegistro servicioVehiculoRegistro;

	@Autowired
	private ServicioMaquinariaRegistro servicioMaquinariaRegistro;

	@Autowired
	private ServicioEstablecimientoRegistro servicioEstablecimientoRegistro;

	@Autowired
	private ServicioBuqueRegistro servicioBuqueRegistro;

	@Autowired
	private ServicioAeronaveRegistro servicioAeronaveRegistro;

	@Autowired
	private ServicioPropiedadIndustrialRegistro servicioPropiedadIndustrialRegistro;

	@Autowired
	private ServicioPropiedadIntelectualRegistro servicioPropiedadIntelectualRegistro;

	@Autowired
	private ServicioOtrosBienesRegistro servicioOtrosBienesRegistro;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultRegistro getPropiedad(String identificador) {
		ResultRegistro result = new ResultRegistro();

		PropiedadDto propiedadDto = conversor.transform(propiedadDao.getPropiedad(identificador), PropiedadDto.class);

		if (null != propiedadDto) {
			result.setObj(propiedadDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener Bien");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarPropiedad(TramiteRegRbmDto tramiteRegRbmDto) {
		ResultRegistro result;

		result = validarPropiedad(tramiteRegRbmDto);
		if (result.isError())
			return result;

		long idPropiedad = tramiteRegRbmDto.getPropiedadDto().getIdPropiedad();

		if (0 != tramiteRegRbmDto.getPropiedadDto().getIdPropiedad()) {
			tramiteRegRbmDto.getPropiedadDto().setFecModificacion(utilesFecha.getTimestampActual());
		} else {
			tramiteRegRbmDto.getPropiedadDto().setIdTramiteRegistro(tramiteRegRbmDto.getIdTramiteRegistro());
			tramiteRegRbmDto.getPropiedadDto().setFecCreacion(utilesFecha.getTimestampActual());
		}

		// Guardamos el proveedor y la dirección de utilización si el contrato es Leasing
		if (TipoTramiteRegistro.MODELO_8.getValorEnum().equals(tramiteRegRbmDto.getTipoTramite())) {
			// Proveedor
			tramiteRegRbmDto.getProveedor().setTipoInterviniente(TipoInterviniente.Proveedor.getValorEnum());
			result = servicioIntervinienteRegistro.guardarInterviniente(tramiteRegRbmDto.getProveedor(), tramiteRegRbmDto);
			if (result.isError()) {
				return result;
			}
			// Insertamos el proveedor en la propiedad
			tramiteRegRbmDto.getPropiedadDto().setIntervinienteRegistro((IntervinienteRegistroDto) result.getObj());

			// Dirección de utilización del bien
			// Bloque opcional, pero si rellenan algún campo ya deben llenar correctamente el bloque dirección
			DireccionDto direccionBien = tramiteRegRbmDto.getPropiedadDto().getDireccion();
			if (StringUtils.isNotBlank(direccionBien.getRegionExtranjera()) || StringUtils.isNotBlank(direccionBien.getPais()) || StringUtils.isNotBlank(direccionBien.getIdProvincia()) || StringUtils
					.isNotBlank(direccionBien.getMunicipio()) || StringUtils.isNotBlank(direccionBien.getPueblo()) || StringUtils.isNotBlank(direccionBien.getCodPostal()) || StringUtils.isNotBlank(
							direccionBien.getIdTipoVia()) || StringUtils.isNotBlank(direccionBien.getNombreVia()) || StringUtils.isNotBlank(direccionBien.getNumero()) || StringUtils.isNotBlank(
									direccionBien.getBloque()) || StringUtils.isNotBlank(direccionBien.getPlanta()) || StringUtils.isNotBlank(direccionBien.getPuerta()) || StringUtils.isNotBlank(
											direccionBien.getNumeroBis()) || StringUtils.isNotBlank(direccionBien.getEscalera()) || null != direccionBien.getKm() || StringUtils.isNotBlank(
													direccionBien.getPortal()) || StringUtils.isNotBlank(direccionBien.getLugarUbicacion())) {

				result = validarDireccionUtilizacionBien(direccionBien);
				if (result.isError())
					return result;

				ResultBean resultadoDireccion = servicioDireccion.guardarActualizar(direccionBien);
				DireccionDto direccion = (DireccionDto) resultadoDireccion.getAttachment(ServicioDireccion.DIRECCION);
				// Insertamos el id dirección en la propiedad
				tramiteRegRbmDto.getPropiedadDto().getDireccion().setIdDireccion(direccion.getIdDireccion());
			} else {
				tramiteRegRbmDto.getPropiedadDto().setDireccion(null);
			}
		}

		// Guardamos la propiedad
		PropiedadVO propiedadVO = propiedadDao.guardarOActualizar(conversor.transform(tramiteRegRbmDto.getPropiedadDto(), PropiedadVO.class));

		if (null == propiedadVO) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar la propiedad");
		} else {
			// Obtenemos el idPropiedad para insertar el bien
			tramiteRegRbmDto.getPropiedadDto().setIdPropiedad(propiedadVO.getIdPropiedad());
			// Guardamos el bien

			result = insertarBien(tramiteRegRbmDto);
		}

		if (result.isError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			tramiteRegRbmDto.getPropiedadDto().setIdPropiedad(idPropiedad);
		} else {
			result.setObj(tramiteRegRbmDto.getPropiedadDto());
		}
		return result;
	}

	@Override
	@Transactional

	public ResultRegistro borrarPropiedad(String id) {
		ResultRegistro result = new ResultRegistro();

		// Obtenermos la propiedad
		PropiedadVO propiedad = propiedadDao.getPropiedad(id);
		if (null == propiedad) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al recuperar la propiedad");
			return result;
		}

		// Borramos el vehículo si lo tuviera
		if (null != propiedad.getVehiculo() && 0 != propiedad.getVehiculo().getIdVehiculo()) {
			result = servicioVehiculoRegistro.borrarVehiculoRegistro(String.valueOf(propiedad.getVehiculo().getIdVehiculo()));
		}
		// Borramos la maquinaria si la tuviera
		if (null != propiedad.getMaquinaria() && 0 != propiedad.getMaquinaria().getIdMaquinaria()) {
			result = servicioMaquinariaRegistro.borrarMaquinariaRegistro(String.valueOf(propiedad.getMaquinaria().getIdMaquinaria()));
		}
		// Borramos el establecimiento si lo tuviera
		if (null != propiedad.getEstablecimiento() && 0 != propiedad.getEstablecimiento().getIdEstablecimiento()) {
			result = servicioEstablecimientoRegistro.borrarEstablecimientoRegistro(String.valueOf(propiedad.getEstablecimiento().getIdEstablecimiento()));
		}
		// Borramos el buque si lo tuviera
		if (null != propiedad.getBuque() && 0 != propiedad.getBuque().getIdBuque()) {
			result = servicioBuqueRegistro.borrarBuqueRegistro(String.valueOf(propiedad.getBuque().getIdBuque()));
		}
		// Borramos la aeronave si la tuviera
		if (null != propiedad.getAeronave() && 0 != propiedad.getAeronave().getIdAeronave()) {
			result = servicioAeronaveRegistro.borrarAeronaveRegistro(String.valueOf(propiedad.getAeronave().getIdAeronave()));
		}
		// Borramos la propiedad industrial si la tuviera
		if (null != propiedad.getPropiedadIndustrial() && 0 != propiedad.getPropiedadIndustrial().getIdPropiedadIndustrial()) {
			result = servicioPropiedadIndustrialRegistro.borrarPropiedadIndustrialRegistro(String.valueOf(propiedad.getPropiedadIndustrial().getIdPropiedadIndustrial()));
		}
		// Borramos la propiedad intelectual si la tuviera
		if (null != propiedad.getPropiedadIntelectual() && 0 != propiedad.getPropiedadIntelectual().getIdPropiedadIntelectual()) {
			result = servicioPropiedadIntelectualRegistro.borrarPropiedadIntelectualRegistro(String.valueOf(propiedad.getPropiedadIntelectual().getIdPropiedadIntelectual()));
		}
		// Borramos otros bienes si los tuviera
		if (null != propiedad.getOtrosBienes() && 0 != propiedad.getOtrosBienes().getIdOtrosBienes()) {
			result = servicioOtrosBienesRegistro.borrarOtrosBienesRegistro(String.valueOf(propiedad.getOtrosBienes().getIdOtrosBienes()));
		}

		if (result.isError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return result;
		}

		// Borramos el proveedor
		if (null != propiedad.getIntervinienteRegistro()) {
			result = servicioIntervinienteRegistro.borrarInterviniente(String.valueOf(propiedad.getIntervinienteRegistro().getIdInterviniente()));
			if (result.isError()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return result;
			}
		}

		// Finalmente borramos la propiedad
		if (propiedadDao.borrar(propiedad)) {
			result.setMensaje("Bien eliminado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando Bien");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	private ResultRegistro insertarBien(TramiteRegRbmDto tramiteRegRbm) {
		ResultRegistro result = new ResultRegistro();

		BigDecimal idPropiedad = new BigDecimal(tramiteRegRbm.getPropiedadDto().getIdPropiedad());
		PropiedadDto propiedad = tramiteRegRbm.getPropiedadDto();

		switch (propiedad.getCategoria()) {
			case "01":// Vehículo
				propiedad.setMaquinaria(null);
				propiedad.setEstablecimiento(null);
				propiedad.setBuque(null);
				propiedad.setAeronave(null);
				propiedad.setPropiedadIndustrial(null);
				propiedad.setPropiedadIntelectual(null);
				propiedad.setOtrosBienes(null);
				result = servicioVehiculoRegistro.guardarOActualizarVehiculoRegistro(propiedad.getVehiculo(), idPropiedad);
				if (null != result.getObj()) {
					propiedad.getVehiculo().setIdVehiculo((Long) result.getObj());
				}
				break;
			case "02":// Maquinaria
				propiedad.setVehiculo(null);
				propiedad.setEstablecimiento(null);
				propiedad.setBuque(null);
				propiedad.setAeronave(null);
				propiedad.setPropiedadIndustrial(null);
				propiedad.setPropiedadIntelectual(null);
				propiedad.setOtrosBienes(null);
				result = servicioMaquinariaRegistro.guardarOActualizarMaquinariaRegistro(propiedad.getMaquinaria(), idPropiedad);
				if (null != result.getObj()) {
					propiedad.getMaquinaria().setIdMaquinaria((Long) result.getObj());
				}
				break;
			case "03":// Establecimiento
				propiedad.setVehiculo(null);
				propiedad.setMaquinaria(null);
				propiedad.setBuque(null);
				propiedad.setAeronave(null);
				propiedad.setPropiedadIndustrial(null);
				propiedad.setPropiedadIntelectual(null);
				propiedad.setOtrosBienes(null);
				result = servicioEstablecimientoRegistro.guardarOActualizarEstablecimientoRegistro(propiedad.getEstablecimiento(), idPropiedad);
				if (null != result.getObj()) {
					propiedad.getEstablecimiento().setIdEstablecimiento((Long) result.getObj());
				}
				break;
			case "04":// Buque
				propiedad.setVehiculo(null);
				propiedad.setMaquinaria(null);
				propiedad.setEstablecimiento(null);
				propiedad.setAeronave(null);
				propiedad.setPropiedadIndustrial(null);
				propiedad.setPropiedadIntelectual(null);
				propiedad.setOtrosBienes(null);
				result = servicioBuqueRegistro.guardarOActualizarBuqueRegistro(propiedad.getBuque(), idPropiedad);
				if (null != result.getObj()) {
					propiedad.getBuque().setIdBuque((Long) result.getObj());
				}
				break;
			case "05":// Aeronave
				propiedad.setVehiculo(null);
				propiedad.setMaquinaria(null);
				propiedad.setEstablecimiento(null);
				propiedad.setBuque(null);
				propiedad.setPropiedadIndustrial(null);
				propiedad.setPropiedadIntelectual(null);
				propiedad.setOtrosBienes(null);
				result = servicioAeronaveRegistro.guardarOActualizarAeronaveRegistro(propiedad.getAeronave(), idPropiedad);
				if (null != result.getObj()) {
					propiedad.getAeronave().setIdAeronave((Long) result.getObj());
				}
				break;
			case "06":// Propiedad Industrial
				propiedad.setVehiculo(null);
				propiedad.setMaquinaria(null);
				propiedad.setEstablecimiento(null);
				propiedad.setBuque(null);
				propiedad.setAeronave(null);
				propiedad.setPropiedadIntelectual(null);
				propiedad.setOtrosBienes(null);
				result = servicioPropiedadIndustrialRegistro.guardarOActualizarPropiedadIndustrialRegistro(propiedad.getPropiedadIndustrial(), idPropiedad);
				if (null != result.getObj()) {
					propiedad.getPropiedadIndustrial().setIdPropiedadIndustrial((Long) result.getObj());
				}
				break;
			case "07":// Propiedad Intelectual
				propiedad.setVehiculo(null);
				propiedad.setMaquinaria(null);
				propiedad.setEstablecimiento(null);
				propiedad.setBuque(null);
				propiedad.setAeronave(null);
				propiedad.setPropiedadIndustrial(null);
				propiedad.setOtrosBienes(null);
				result = servicioPropiedadIntelectualRegistro.guardarOActualizarPropiedadIntelectualRegistro(propiedad.getPropiedadIntelectual(), idPropiedad);
				if (null != result.getObj()) {
					propiedad.getPropiedadIntelectual().setIdPropiedadIntelectual((Long) result.getObj());
				}
				break;
			case "08":// Otros Bienes
				propiedad.setVehiculo(null);
				propiedad.setMaquinaria(null);
				propiedad.setEstablecimiento(null);
				propiedad.setBuque(null);
				propiedad.setAeronave(null);
				propiedad.setPropiedadIndustrial(null);
				propiedad.setPropiedadIntelectual(null);
				result = servicioOtrosBienesRegistro.guardarOActualizarOtrosBienesRegistro(propiedad.getOtrosBienes(), idPropiedad);
				if (null != result.getObj()) {
					propiedad.getOtrosBienes().setIdOtrosBienes((Long) result.getObj());
				}
				break;
			default:// Propiedad no seleccionada
				break;
		}
		tramiteRegRbm.setPropiedadDto(propiedad);
		return result;
	}

	@Override
	public ResultRegistro validarPropiedad(TramiteRegRbmDto tramiteRegRbmDto) {
		ResultRegistro result = new ResultRegistro();

		PropiedadDto propiedadDto = tramiteRegRbmDto.getPropiedadDto();

		if (null == propiedadDto) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Debe rellenar los datos del bien");
			return result;
		}

		if (StringUtils.isBlank(propiedadDto.getCategoria())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La categoría del bien es obligatoria.");
		}

		// Si el contrato es Leasing se valida Impoerte base, Importe impuesto e importe total
		if (null != tramiteRegRbmDto.getTipoTramite() && tramiteRegRbmDto.getTipoTramite().equals(TipoTramiteRegistro.MODELO_8.getValorEnum())) {

			if (!UtilesValidaciones.validarObligatoriedad(propiedadDto.getImpBase())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El importe base del bien es obligatorio.");
			} else if (!UtilesValidaciones.validarImporte(propiedadDto.getImpBase())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El importe base del bien debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(propiedadDto.getImpImpuesto())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El importe impuesto del bien es obligatorio.");
			} else if (!UtilesValidaciones.validarImporte(propiedadDto.getImpImpuesto())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El importe impuesto del bien debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
			}

			if (!UtilesValidaciones.validarObligatoriedad(propiedadDto.getImpTotal())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("La suma de importes del bien es obligatoria.");
			} else if (!UtilesValidaciones.validarImporte(propiedadDto.getImpTotal())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("La suma de importes del bien debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
			}

			if (!UtilesValidaciones.validarImporte(propiedadDto.getImpuestoMatr())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El impuesto de matriculación del bien debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
			}
			// Si el contrato es Renting o Financiación se valida el Valor (importe base sin aplicar impuesto)
		} else if (null != tramiteRegRbmDto.getTipoTramite() && (tramiteRegRbmDto.getTipoTramite().equals(TipoTramiteRegistro.MODELO_7.getValorEnum()) || tramiteRegRbmDto.getTipoTramite().equals(
				TipoTramiteRegistro.MODELO_9.getValorEnum()))) {// Si el contrato es Renting o Financiación se valida el Valor (importe base sin aplicar impuesto)

			if (!UtilesValidaciones.validarObligatoriedad(propiedadDto.getValor())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El valor del bien es obligatorio.");
			} else if (!UtilesValidaciones.validarImporte(propiedadDto.getValor())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El valor contado del bien debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
			}
		}

		return result;
	}

	private ResultRegistro validarDireccionUtilizacionBien(DireccionDto direccion) {
		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(direccion.getPais())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El país del lugar de utilización es obligatorio.");
		}

		if (StringUtils.isNotBlank(direccion.getPais()) && !direccion.getPais().equals("ES") && StringUtils.isBlank(direccion.getRegionExtranjera())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Si el país del lugar de utilización es distinto de España la región es obligatoria.");
		}

		if (StringUtils.isBlank(direccion.getIdProvincia())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La provincia del lugar de utilización es obligatoria.");
		}

		if (StringUtils.isBlank(direccion.getIdMunicipio())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El municipio del lugar de utilización es obligatorio.");
		}

		if (StringUtils.isNotBlank(direccion.getPais()) && direccion.getPais().equals("ES") && StringUtils.isBlank(direccion.getIdTipoVia())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Si el país del lugar de utilización es España el tipo de vía es obligatorio.");
		}

		if (StringUtils.isBlank(direccion.getNombreVia())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Nombre calle del lugar de utilización es obligatorio.");
		}

		if (!UtilesValidaciones.validarNumero(direccion.getNumero())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Número calle del lugar de utilización no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarCodigoPostal(direccion.getCodPostal())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El código postal del lugar de utilización no tiene el formato correcto.");
		}

		return result;
	}

}
