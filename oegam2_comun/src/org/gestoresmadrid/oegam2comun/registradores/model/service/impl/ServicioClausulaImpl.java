package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.ClausulaDao;
import org.gestoresmadrid.core.registradores.model.vo.ClausulaVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioClausula;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ClausulaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioClausulaImpl implements ServicioClausula {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private ClausulaDao clausulaDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultRegistro getClausula(String identificador) {
		ResultRegistro result = new ResultRegistro();

		ClausulaDto clausulaDto = conversor.transform(clausulaDao.getClausula(identificador), ClausulaDto.class);

		if(null != clausulaDto){
			result.setObj(clausulaDto);
		}else{
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener la cláusula");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarClausula(ClausulaDto clausulaDto, BigDecimal idTramiteRegistro) {
		ResultRegistro result;

		result = validarClausula(clausulaDto);
		if(result.isError())
			return result;
		
		//Si tenemos el identificador de clausula entonces actualizamos, si no añadimos
		if (0 != clausulaDto.getIdClausula()) {
			clausulaDto.setFecModificacion(utilesFecha.getTimestampActual());
		}else{
			clausulaDto.setIdTramiteRegistro(idTramiteRegistro);
			clausulaDto.setFecCreacion(utilesFecha.getTimestampActual());
		}

		ClausulaVO clausulaVO = clausulaDao.guardarOActualizar(conversor.transform(clausulaDto, ClausulaVO.class));

		if(null != clausulaVO){
			result.setMensaje("Cláusula actualizada correctamente");
		}else{
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar la cláusula");
		}

		return result;
	}


	@Override
	@Transactional
	public ResultRegistro borrarClausula(String id){
		ResultRegistro result = new ResultRegistro();
		if (clausulaDao.borrar(clausulaDao.getClausula(id))){
			result.setMensaje("Cláusula eliminada correctamente");
		}else{
			result.setError(Boolean.TRUE);
			result.setMensaje("Error borrando cláusula");
		}
		return result;
	}

	@Override
	public ResultRegistro validarClausula(ClausulaDto clausulaDto) {
		ResultRegistro result = new ResultRegistro();

		if(StringUtils.isBlank(clausulaDto.getTipoClausula())){
			result.setError(Boolean.TRUE);
			result.addValidacion(" El tipo de clásula es obligatorio.");
		}

		if(!UtilesValidaciones.validarObligatoriedad(clausulaDto.getNumero())){
			result.setError(Boolean.TRUE);
			result.addValidacion("El número de cláusula es obligatorio.");
		}else if(!UtilesValidaciones.validarNumero(clausulaDto.getNumero())){
			result.setError(Boolean.TRUE);
			result.addValidacion("El número de cláusula no tiene el formato correcto.");
		}

		if(StringUtils.isBlank(clausulaDto.getNombre())){
			result.setError(Boolean.TRUE);
			result.addValidacion("El nombre de clásula es obligatorio.");
		}

		if(StringUtils.isBlank(clausulaDto.getDescripcion())){
			result.setError(Boolean.TRUE);
			result.addValidacion("La descripción de clásula es obligatoria.");
		}

		return result;
	}

}
