package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.DatRegMercantilDao;
import org.gestoresmadrid.core.registradores.model.vo.DatRegMercantilVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioDatRegMercantil;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.DatRegMercantilDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioDatRegMercantilImpl implements ServicioDatRegMercantil {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private DatRegMercantilDao datRegMercantilDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Transactional
	@Override
	public ResultRegistro getDatRegMercantil(String identificador) {
		ResultRegistro result = new ResultRegistro();

		DatRegMercantilDto datRegMercantilDto = conversor.transform(datRegMercantilDao.getDatRegMercantil(identificador), DatRegMercantilDto.class);

		if(null != datRegMercantilDto){
			result.setObj(datRegMercantilDto);
		}else{
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener los datos del registro mercantil");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultRegistro guardarOActualizarDatRegMercantil(DatRegMercantilDto datRegMercantilDto) {
		ResultRegistro result;

		result = validarDatRegMercantil(datRegMercantilDto);
		if(result.isError())
			return result;

		//Si tenemos el identificador de registro mercantil entonces actualizamos, si no añadimos
		if (0 != datRegMercantilDto.getIdDatRegMercantil()) {
			datRegMercantilDto.setFecModificacion(utilesFecha.getTimestampActual());
		}else{
			datRegMercantilDto.setFecCreacion(utilesFecha.getTimestampActual());
		}

		DatRegMercantilVO datRegMercantilVO = datRegMercantilDao.guardarOActualizar(conversor.transform(datRegMercantilDto, DatRegMercantilVO.class));

		if(null != datRegMercantilVO){
			result.setObj(datRegMercantilVO.getIdDatRegMercantil());
		}else{
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar los datos de registro mercantil");
		}

		return result;

	}
	
	@Transactional
	@Override
	public ResultRegistro guardarOActualizarDatRegMercantilCancelacion(DatRegMercantilDto datRegMercantilDto) {
		ResultRegistro result;

		result = validarDatRegMercantilCancelacion(datRegMercantilDto);
		if(result.isError())
			return result;

		//Si tenemos el identificador de registro mercantil entonces actualizamos, si no añadimos
		if (0 != datRegMercantilDto.getIdDatRegMercantil()) {
			datRegMercantilDto.setFecModificacion(utilesFecha.getTimestampActual());
		}else{
			datRegMercantilDto.setFecCreacion(utilesFecha.getTimestampActual());
		}

		DatRegMercantilVO datRegMercantilVO = datRegMercantilDao.guardarOActualizar(conversor.transform(datRegMercantilDto, DatRegMercantilVO.class));

		if(null != datRegMercantilVO){
			result.setObj(datRegMercantilVO.getIdDatRegMercantil());
		}else{
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar los datos de registro mercantil");
		}

		return result;

	}
	

	@Transactional
	@Override
	public ResultRegistro borrarDatRegMercantil(String id){
		ResultRegistro result = new ResultRegistro();
		if (datRegMercantilDao.borrar(datRegMercantilDao.getDatRegMercantil(id))){
			result.setMensaje("Datos del registro eliminados correctamente");
		}else{
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando los datos del registro");
		}
		return result;
	}

	@Override
	public ResultRegistro validarDatRegMercantil(DatRegMercantilDto datRegMercantilDto) {
		ResultRegistro result = new ResultRegistro();

		if(null != datRegMercantilDto 
				&& (StringUtils.isNotBlank(datRegMercantilDto.getCodRegistroMercantil())
						|| UtilesValidaciones.validarObligatoriedad(datRegMercantilDto.getTomo())
						|| UtilesValidaciones.validarObligatoriedad(datRegMercantilDto.getLibro())
						|| UtilesValidaciones.validarObligatoriedad(datRegMercantilDto.getFolio())
						|| StringUtils.isNotBlank(datRegMercantilDto.getHoja())
						|| StringUtils.isNotBlank(datRegMercantilDto.getNumInscripcion()))){

			if(StringUtils.isBlank(datRegMercantilDto.getCodRegistroMercantil())){
				result.setError(Boolean.TRUE);
				result.addValidacion("El registro mercantil es obligatorio.");
			}else if(!UtilesValidaciones.validarNumero(datRegMercantilDto.getCodRegistroMercantil())){
				result.setError(Boolean.TRUE);
				result.addValidacion("El registro mercantil no tiene el formato correcto.");
			}


			if(!UtilesValidaciones.validarObligatoriedad(datRegMercantilDto.getTomo())){
				result.setError(Boolean.TRUE);
				result.addValidacion("El tomo inscrip. del registro mercantil es obligatorio.");
			}else if(!UtilesValidaciones.validarNumero(datRegMercantilDto.getTomo())){
				result.setError(Boolean.TRUE);
				result.addValidacion("El tomo inscrip. del registro mercantil no tiene el formato correcto.");
			}

			if(!UtilesValidaciones.validarObligatoriedad(datRegMercantilDto.getLibro())){
				result.setError(Boolean.TRUE);
				result.addValidacion("El libro inscrip. del registro mercantil es obligatorio.");
			}else if(!UtilesValidaciones.validarNumero(datRegMercantilDto.getLibro())){
				result.setError(Boolean.TRUE);
				result.addValidacion("El libro inscrip. del registro mercantil no tiene el formato correcto.");
			}

			if(!UtilesValidaciones.validarObligatoriedad(datRegMercantilDto.getFolio())){
				result.setError(Boolean.TRUE);
				result.addValidacion("El folio inscrip. del registro mercantil es obligatorio.");
			}else if(!UtilesValidaciones.validarNumero(datRegMercantilDto.getFolio())){
				result.setError(Boolean.TRUE);
				result.addValidacion("El folio inscrip. del registro mercantil no tiene el formato correcto.");
			}

			if(StringUtils.isBlank(datRegMercantilDto.getHoja())){
				result.setError(Boolean.TRUE);
				result.addValidacion("La hoja inscrip. del registro mercantil es obligatorio.");
			}

			if(StringUtils.isBlank(datRegMercantilDto.getNumInscripcion())){
				result.setError(Boolean.TRUE);
				result.addValidacion("El Número inscrip. del registro mercantil es obligatorio.");
			}
		}

		return result;
	}

	@Override
	public ResultRegistro validarDatRegMercantilCancelacion(DatRegMercantilDto datRegMercantilDto) {
		ResultRegistro result = new ResultRegistro();


		if(StringUtils.isBlank(datRegMercantilDto.getCodRegistroMercantil())){
			result.setError(Boolean.TRUE);
			result.addValidacion("El registro mercantil es obligatorio.");
		}else if(!UtilesValidaciones.validarNumero(datRegMercantilDto.getCodRegistroMercantil())){
			result.setError(Boolean.TRUE);
			result.addValidacion("El registro mercantil no tiene el formato correcto.");
		}

		if(!UtilesValidaciones.validarObligatoriedad(datRegMercantilDto.getTomo())){
			result.setError(Boolean.TRUE);
			result.addValidacion("El tomo inscrip. del registro mercantil es obligatorio.");
		}else if(!UtilesValidaciones.validarNumero(datRegMercantilDto.getTomo())){
			result.setError(Boolean.TRUE);
			result.addValidacion("El tomo inscrip. del registro mercantil no tiene el formato correcto.");
		}


		if(!UtilesValidaciones.validarObligatoriedad(datRegMercantilDto.getFolio())){
			result.setError(Boolean.TRUE);
			result.addValidacion("El folio inscrip. del registro mercantil es obligatorio.");
		}else if(!UtilesValidaciones.validarNumero(datRegMercantilDto.getFolio())){
			result.setError(Boolean.TRUE);
			result.addValidacion("El folio inscrip. del registro mercantil no tiene el formato correcto.");
		}

		if(StringUtils.isBlank(datRegMercantilDto.getNumInscripcion())){
			result.setError(Boolean.TRUE);
			result.addValidacion("El Número inscrip. del registro mercantil es obligatorio.");
		}


		return result;
	}

}
