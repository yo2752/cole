package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.FacturaRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.FacturaRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioFacturaRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FacturaRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.validaciones.NIFValidator;

@Service
public class ServicioFacturaRegistroImpl implements ServicioFacturaRegistro {

	private static final long serialVersionUID = -8905066704644930133L;

	@Autowired
	private FacturaRegistroDao facturaRegistroDao;

	@Autowired
	private Conversor conversor;

	@Transactional
	@Override
	public ResultRegistro getFacturaRegistro(Long identificador) {
		ResultRegistro result = new ResultRegistro();

		FacturaRegistroDto facturaRegistroDto = conversor.transform(facturaRegistroDao.getFacturaRegistro(identificador), FacturaRegistroDto.class);

		if (null != facturaRegistroDto) {
			result.setObj(facturaRegistroDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener los datos de la factura");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultRegistro guardarOActualizarFacturaRegistro(FacturaRegistroDto facturaRegistroDto, BigDecimal idTramiteRegistro) {
		ResultRegistro result;

		result = validarFacturaRegistro(facturaRegistroDto);
		if (result.isError())
			return result;

		// Si no tenemos el identificador de factura entonces añadimos
		if (0 == facturaRegistroDto.getIdFactura()) {
			facturaRegistroDto.setIdTramiteRegistro(idTramiteRegistro);
		}

		FacturaRegistroVO facturaRegistroVO = facturaRegistroDao.guardarOActualizar(conversor.transform(facturaRegistroDto, FacturaRegistroVO.class));

		if (null != facturaRegistroVO) {
			result.setMensaje("Datos de la factura actualizados correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar los datos de la factura");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultRegistro borrarFacturaRegistro(Long id) {
		ResultRegistro result = new ResultRegistro();
		if (facturaRegistroDao.borrar(facturaRegistroDao.getFacturaRegistro(id))) {
			result.setMensaje("Datos de la factura eliminados correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando los datos de la factura");
		}
		return result;
	}

	@Override
	public ResultRegistro validarFacturaRegistro(FacturaRegistroDto facturaRegistroDto) {
		ResultRegistro result = new ResultRegistro();

		if (!UtilesValidaciones.validarObligatoriedad(facturaRegistroDto.getFechaPago())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La fecha de pago de la factura es obligatoria.");
		} else if (!UtilesValidaciones.validarFecha(facturaRegistroDto.getFechaPago())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La fecha de pago de la factura no tiene el formato correcto.");
		}

		if (StringUtils.isBlank(facturaRegistroDto.getIdTransferencia())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El identificador de transferencia de la factura es obligatorio.");
		}

		if (StringUtils.isBlank(facturaRegistroDto.getCifEmisor())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El CIF de la entidad emisora de la factura es obligatorio.");
		} else if (!NIFValidator.isValidCIF(facturaRegistroDto.getCifEmisor().toUpperCase())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El CIF de la entidad emisora de la factura no tiene el formato correcto.");
		}

		if (StringUtils.isBlank(facturaRegistroDto.getNumSerie())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número de serie de la factura es obligatorio.");
		}

		if (StringUtils.isBlank(facturaRegistroDto.getEjercicio())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El ejercicio de la factura es obligatorio.");
		}

		if (StringUtils.isBlank(facturaRegistroDto.getNumFactura())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número de la factura es obligatorio.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(facturaRegistroDto.getFechaFactura())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La fecha de emisión de la factura es obligatoria.");
		} else if (!UtilesValidaciones.validarFecha(facturaRegistroDto.getFechaFactura())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La fecha de emisión de la factura no tiene el formato correcto.");
		}

		return result;
	}

	@Override
	@Transactional
	public List<FacturaRegistroDto> getFacturasPorTramite(BigDecimal idTramiteRegistro) {
		return conversor.transform(facturaRegistroDao.getFacturasRegistroPorTramite(idTramiteRegistro), FacturaRegistroDto.class);
	}

}
