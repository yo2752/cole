package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.core.registradores.model.dao.DatoFirmaDao;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.vo.DatoFirmaVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioDatoFirma;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.DatoFirmaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioDatoFirmaImpl implements ServicioDatoFirma {

	private static final long serialVersionUID = 7305605957238024710L;

	private static Logger log = Logger.getLogger(ServicioDatoFirmaImpl.class);

	@Autowired
	private DatoFirmaDao datoFirmaDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioProvincia servicioProvincia;

	@Autowired
	private ServicioMunicipio servicioMunicipio;

	@Autowired
	private ServicioTipoVia servicioTipoVia;

	@Autowired
	UtilesFecha utilesFecha;

	@Transactional
	@Override
	public ResultRegistro getDatoFirma(String identificador) {
		ResultRegistro result = new ResultRegistro();

		DatoFirmaDto datoFirmaDto = conversor.transform(datoFirmaDao.getDatoFirma(identificador), DatoFirmaDto.class);

		if (null != datoFirmaDto) {
			result.setObj(datoFirmaDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener los datos de la firma");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultRegistro guardarOActualizarDatosFirma(DatoFirmaDto datoFirmaDto, BigDecimal idTramiteRegistro, String tipoTramite) {
		ResultRegistro result;

		result = validarDatosFirma(datoFirmaDto, tipoTramite);
		if (result.isError())
			return result;

		// Si tenemos el identificador de firma entonces actualizamos, si no añadimos
		if (0 != datoFirmaDto.getIdDatoFirma()) {
			datoFirmaDto.setFecModificacion(utilesFecha.getTimestampActual());
		} else {
			datoFirmaDto.setIdTramiteRegistro(idTramiteRegistro);
			datoFirmaDto.setFecCreacion(utilesFecha.getTimestampActual());
		}

		if (null != datoFirmaDto.getFecFirmaDatFirma()) {
			try {
				datoFirmaDto.setFecFirma(datoFirmaDto.getFecFirmaDatFirma().getDate());
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}

		datoFirmaDto.setLugar(construirLugarFirma(datoFirmaDto.getDireccion()));

		DatoFirmaVO datoFirmaVO = datoFirmaDao.guardarOActualizar(conversor.transform(datoFirmaDto, DatoFirmaVO.class));

		if (null != datoFirmaVO) {
			result.setMensaje("Datos de la firma actualizados correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar los datos de la firma");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultRegistro borrarDatosFirma(String id) {
		ResultRegistro result = new ResultRegistro();
		if (datoFirmaDao.borrar(datoFirmaDao.getDatoFirma(id))) {
			result.setMensaje("Datos de la firma eliminados correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando los datos de la firma");
		}
		return result;
	}

	@Override
	public ResultRegistro validarDatosFirma(DatoFirmaDto datoFirmaDto, String tipoTramite) {
		ResultRegistro result = new ResultRegistro();

		if (!TipoTramiteRegistro.MODELO_10.getValorEnum().equals(tipoTramite) && !TipoTramiteRegistro.MODELO_13.getValorEnum().equals(tipoTramite)) {
			if (StringUtils.isBlank(datoFirmaDto.getTipoIntervencion())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El tipo de intervención es obligatorio.");
			}
		}

		// Lugar de la firma
		if (StringUtils.isBlank(datoFirmaDto.getDireccion().getIdProvincia()) && StringUtils.isBlank(datoFirmaDto.getDireccion().getIdMunicipio()) && StringUtils.isBlank(datoFirmaDto.getDireccion()
				.getCodPostal()) && StringUtils.isBlank(datoFirmaDto.getDireccion().getIdTipoVia()) && StringUtils.isBlank(datoFirmaDto.getDireccion().getNombreVia()) && StringUtils.isBlank(
						datoFirmaDto.getDireccion().getNumero()) && StringUtils.isBlank(datoFirmaDto.getDireccion().getBloque()) && StringUtils.isBlank(datoFirmaDto.getDireccion().getPlanta())
				&& StringUtils.isBlank(datoFirmaDto.getDireccion().getPuerta()) && StringUtils.isBlank(datoFirmaDto.getDireccion().getNumeroBis()) && StringUtils.isBlank(datoFirmaDto.getDireccion()
						.getEscalera()) && null == datoFirmaDto.getDireccion().getKm() && StringUtils.isBlank(datoFirmaDto.getDireccion().getPortal())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El lugar de la firma es obligatorio.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(datoFirmaDto.getFecFirmaDatFirma())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La fecha de la firma es obligatoria.");
		} else if (!UtilesValidaciones.validarFecha(datoFirmaDto.getFecFirmaDatFirma())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La fecha de la firma no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarCamposSiNo(datoFirmaDto.getDerechoDes())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El campo derecho desestimiento de la firma no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarCamposSiNo(datoFirmaDto.getConsumidor())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El campo consumidor de la firma no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarCamposSiNo(datoFirmaDto.getConsumidor())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El campo informador de la firma no tiene el formato correcto.");
		}

		return result;
	}

	private String construirLugarFirma(DireccionDto direccion) {
		StringBuffer lugarFirma = new StringBuffer();

		if (StringUtils.isNotBlank(direccion.getIdTipoVia())) {
			TipoViaVO tipoVia = servicioTipoVia.getTipoVia(direccion.getIdTipoVia());
			if (null != tipoVia)
				lugarFirma.append("Tipo de vía:" + tipoVia.getNombre() + ",");
		}
		if (StringUtils.isNotBlank(direccion.getNombreVia())) {
			lugarFirma.append(" Nombre de vía:" + direccion.getNombreVia() + ",");
		}

		if (StringUtils.isNotBlank(direccion.getNumero())) {
			lugarFirma.append(" Número:" + direccion.getNumero() + ",");
		}
		if (StringUtils.isNotBlank(direccion.getBloque())) {
			lugarFirma.append(" Bloque:" + direccion.getBloque() + ",");
		}
		if (StringUtils.isNotBlank(direccion.getPlanta())) {
			lugarFirma.append(" Planta:" + direccion.getBloque() + ",");
		}
		if (StringUtils.isNotBlank(direccion.getPuerta())) {
			lugarFirma.append(" Puerta:" + direccion.getBloque() + ",");
		}
		if (StringUtils.isNotBlank(direccion.getNumeroBis())) {
			lugarFirma.append(" Número bis:" + direccion.getNumeroBis() + ",");
		}
		if (StringUtils.isNotBlank(direccion.getEscalera())) {
			lugarFirma.append(" Escalera:" + direccion.getEscalera() + ",");
		}
		if (null != direccion.getKm()) {
			lugarFirma.append(" Punto km:" + direccion.getKm() + ",");
		}
		if (StringUtils.isNotBlank(direccion.getPortal())) {
			lugarFirma.append(" Portal:" + direccion.getPortal() + ",");
		}

		if (StringUtils.isNotBlank(direccion.getCodPostal())) {
			lugarFirma.append(" Código postal:" + direccion.getCodPostal() + ",");
		}

		if (StringUtils.isNotBlank(direccion.getIdMunicipio())) {
			MunicipioVO municipio = servicioMunicipio.getMunicipio(direccion.getIdMunicipio(), direccion.getIdProvincia());
			if (null != municipio)
				lugarFirma.append(" Municipio:" + municipio.getNombre() + ",");
		}

		if (StringUtils.isNotBlank(direccion.getIdProvincia())) {
			ProvinciaVO provincia = servicioProvincia.getProvincia(direccion.getIdProvincia());
			if (null != provincia)
				lugarFirma.append(" Provincia:" + provincia.getNombre() + ",");
		}

		// Quitamos la coma del final
		return lugarFirma.substring(0, lugarFirma.length() - 1);

	}

	@Override
	public DireccionDto convertirLugarFirma(String direccionFirma) {

		DireccionDto direccion = new DireccionDto();
		ProvinciaDto provincia = null;

		String[] datos = direccionFirma.split(",");
		for (int i = 0; i < datos.length; i++) {
			String[] campos = datos[i].split(":");
			if (campos[0].equalsIgnoreCase("Tipo de vía")) {
				TipoViaVO tipoVia = servicioTipoVia.getIdTipoViaPorDesc(campos[1]);
				if (null != tipoVia)
					direccion.setIdTipoVia(tipoVia.getIdTipoVia());
			} else if (campos[0].equalsIgnoreCase(" Nombre de vía")) {
				direccion.setNombreVia(campos[1]);
			} else if (campos[0].equalsIgnoreCase(" Número")) {
				direccion.setNumero(campos[1]);
			} else if (campos[0].equalsIgnoreCase(" Bloque")) {
				direccion.setBloque(campos[1]);
			} else if (campos[0].equalsIgnoreCase(" Planta")) {
				direccion.setPlanta(campos[1]);
			} else if (campos[0].equalsIgnoreCase(" Puerta")) {
				direccion.setPuerta(campos[1]);
			} else if (campos[0].equalsIgnoreCase(" Número bis")) {
				direccion.setNumeroBis(campos[1]);
			} else if (campos[0].equalsIgnoreCase(" Escalera")) {
				direccion.setEscalera(campos[1]);
			} else if (campos[0].equalsIgnoreCase(" Punto km")) {
				direccion.setKm(new BigDecimal(campos[1]));
			} else if (campos[0].equalsIgnoreCase(" Portal")) {
				direccion.setPortal(campos[1]);
			} else if (campos[0].equalsIgnoreCase(" Código postal")) {
				direccion.setCodPostal(campos[1]);
			} else if (campos[0].equalsIgnoreCase(" Provincia")) {
				provincia = servicioProvincia.getProvinciaPorNombre(campos[1]);
				if (null != provincia)
					direccion.setIdProvincia(provincia.getIdProvincia());
			} else if (campos[0].equalsIgnoreCase(" Municipio")) {
				MunicipioDto municipio = servicioMunicipio.getMunicipioPorNombre(campos[1], direccion.getIdProvincia());
				if (null != municipio)
					direccion.setIdMunicipio(municipio.getIdMunicipio());
			}
		}

		return direccion;
	}

}
