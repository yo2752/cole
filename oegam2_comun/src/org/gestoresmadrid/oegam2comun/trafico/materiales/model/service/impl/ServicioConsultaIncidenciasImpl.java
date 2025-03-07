package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.axis.utils.StringUtils;
import org.gestoresmadrid.core.trafico.materiales.model.dao.IncidenciaDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.Delegacion;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoIncidencia;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.TipoIncidencia;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaIntervaloVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaSerialVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaSerialVOId;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.core.trafico.model.dao.JefaturaTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaIncidencias;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.IncidenciasResultadosBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.IncidenciaInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaDto;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaIntervalosDto;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaSerialDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaIncidenciasImpl implements ServicioConsultaIncidencias {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4655427874661001716L;

	private ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaPedidosImpl.class);

	@Resource private IncidenciaDao      incidenciaDao;
	@Resource private JefaturaTraficoDao jefaturaTraficoDao;
	@Resource private MaterialDao        materialDao;

	@Autowired private Conversor          conversor;
	@Autowired ServicioConsultaMateriales servicioConsultaMateriales;
	@Autowired ServicioConsultaPedidos    servicioConsultaPedidos;
	

	@Override
	public List<IncidenciasResultadosBean> convertirListaEnBeanPantalla(List<IncidenciaVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<IncidenciasResultadosBean> listaBean = new ArrayList<IncidenciasResultadosBean>();
			
			for (IncidenciaVO incidenciaVO : lista) {
				IncidenciasResultadosBean incidenciasResultadosBean = conversor.transform(incidenciaVO, IncidenciasResultadosBean.class);
				
				String tipo = TipoIncidencia.convertirTexto(incidenciaVO.getTipo());
				incidenciasResultadosBean.setTipo(tipo);
				
				listaBean.add(incidenciasResultadosBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly=true)
	public IncidenciaDto getIncidencia(Long incidenciaId) {
		IncidenciaVO incidenciaVO = incidenciaDao.findIncidenciaByPk(incidenciaId);
		return this.getDtoFromVO(incidenciaVO);
	}

	@Override
	@Transactional(readOnly=true)
	public IncidenciaVO obtenerIncidenciaForJefaturaMaterialNumSerie(String jefatura, Long materia, String numSerie) {
		IncidenciaVO incidenciaVO = incidenciaDao.findIncidenciaByJefaturaMaterialNumSerie(jefatura, materia, numSerie);
		return incidenciaVO;
	}

	@Override
	@Transactional(readOnly=true)
	public IncidenciaVO obtenerIncidenciaVO(Long incidenciaId) {
		IncidenciaVO incidenciaVO = incidenciaDao.findIncidenciaByPk(incidenciaId);
		return incidenciaVO;
	}


	@Override
	@Transactional(readOnly=true)
	public IncidenciaDto obtenerIncidencia(Long incidenciaId) {
		IncidenciaVO incidenciaVO = incidenciaDao.findIncidenciaByPk(incidenciaId, true);
		IncidenciaDto incidenciaDto = getDtoFromVO(incidenciaVO);
		return incidenciaDto;
	}

	@Override
	public IncidenciaVO getVOFromDto(IncidenciaDto incidenciaDto) {
		IncidenciaVO inciVO = conversor.transform(incidenciaDto, IncidenciaVO.class);
		
		inciVO.setTipo(incidenciaDto.getTipo());
		//TODO poner en Dozer
		inciVO.setEstado(incidenciaDto.getEstadoInc());
		
		return inciVO;
	}

	@Override
	public IncidenciaDto getDtoFromVO(IncidenciaVO incidenciaVO) {
		
		IncidenciaDto incidenciaDto = null;
		incidenciaDto = conversor.transform(incidenciaVO, IncidenciaDto.class);
		
		EstadoIncidencia estado = EstadoIncidencia.convertir(incidenciaVO.getEstado());
		incidenciaDto.setEstadoNom(estado.getNombreEnum());
		
		TipoIncidencia tipo = TipoIncidencia.convertir(incidenciaVO.getTipo());
		incidenciaDto.setTipoNombre(tipo.getNombreEnum());
		
		if (incidenciaDto.getListaSerial() == null) {
			incidenciaDto.setListaSerial(new ArrayList<IncidenciaSerialDto>());
		}
		
		if (incidenciaDto.getListaSerial().isEmpty() && !incidenciaVO.getListaSeriales().isEmpty()) {
			List<IncidenciaSerialDto> seriales = new ArrayList<IncidenciaSerialDto>();
			for(IncidenciaSerialVO item: incidenciaVO.getListaSeriales()) {
				IncidenciaSerialDto serial = new IncidenciaSerialDto();
				serial.setIncidenciaInve(item.getIncidenciaInvId());
				serial.setNumSerie(item.getPk().getNumSerie());
				seriales.add(serial);
			}
			incidenciaDto.setListaSerial(seriales);
		}
		
		if(incidenciaDto.getListaIntervalos() == null) {
			incidenciaDto.setListaIntervalos(new ArrayList<IncidenciaIntervalosDto>());
		}
		
		if (incidenciaDto.getListaIntervalos().isEmpty() && !incidenciaVO.getListaIntervalos().isEmpty()) {
			List<IncidenciaIntervalosDto> intervalos = new ArrayList<IncidenciaIntervalosDto>();
			for(IncidenciaIntervaloVO item: incidenciaVO.getListaIntervalos()) {
				IncidenciaIntervalosDto intervalo = new IncidenciaIntervalosDto();
				intervalo.setNumSerieIni(item.getPk().getNumSerieIni());
				intervalo.setNumSerieFin(item.getNumSerieFin());
				intervalos.add(intervalo);
			}
			incidenciaDto.setListaIntervalos(intervalos);
		}
		
		return incidenciaDto;
	}

	@Override
	public IncidenciaDto getDtoFromInfoRest(IncidenciaInfoRest incidenciaInfoRest) {
		IncidenciaDto incidenciaDto = conversor.transform(incidenciaInfoRest, IncidenciaDto.class);
		
		String tipoIncidencia = TipoIncidencia.convertirNombre(incidenciaInfoRest.getTipo());
		String jefatura = Delegacion.convertirTexto(incidenciaInfoRest.getDelegacion().getId());
		
		incidenciaDto.setJefaturaProvincial(jefatura);
		
		incidenciaDto.setMateriaId(incidenciaInfoRest.getMaterial().getId());
		incidenciaDto.setMateriaNombre(incidenciaInfoRest.getMaterial().getNombre());
		
		EstadoIncidencia estadoInc = EstadoIncidencia.Activo;
		incidenciaDto.setEstadoInc(Long.valueOf(estadoInc.getValorEnum()));
		incidenciaDto.setEstadoNom(estadoInc.getNombreEnum());
		
		incidenciaDto.setTipo(tipoIncidencia);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		try {
			incidenciaDto.setFecha(sdf.parse(incidenciaInfoRest.getFecha()));
		} catch (ParseException e) {
			log.error("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
		}
		
		return incidenciaDto;
	}

	@Override
	@Transactional(readOnly=true)
	public IncidenciaVO getVOFromInfoRest(IncidenciaInfoRest incidenciaInfoRest) {
		IncidenciaVO inciVO = conversor.transform(incidenciaInfoRest, IncidenciaVO.class);
		
		String jefaturaProvincial = Delegacion.convertirTexto(incidenciaInfoRest.getDelegacion().getId());
		JefaturaTraficoVO jtVO = jefaturaTraficoDao.getJefatura(jefaturaProvincial);
		inciVO.setJefaturaProvincial(jtVO);
		
		Long materialId = inciVO.getMaterialVO().getMaterialId();
		MaterialVO materialVO = servicioConsultaMateriales.getMaterialForPrimaryKey(materialId);
		inciVO.setMaterialVO(materialVO);

		//TODO poner el numero de serial
		List<IncidenciaSerialVO> listaSeriales = new ArrayList<IncidenciaSerialVO>();
		IncidenciaSerialVO incidenciaSerialVO = new IncidenciaSerialVO();
		
		IncidenciaSerialVOId pkSerial = new IncidenciaSerialVOId();
		pkSerial.setNumSerie(incidenciaInfoRest.getNumserie());
		
		incidenciaSerialVO.setPk(pkSerial);
		incidenciaSerialVO.setIncidenciaInvId(incidenciaInfoRest.getId());
		
		listaSeriales.add(incidenciaSerialVO);
		inciVO.setListaSeriales(listaSeriales);
		
		List<IncidenciaIntervaloVO> listaIntervalos = new ArrayList<IncidenciaIntervaloVO>();
		inciVO.setListaIntervalos(listaIntervalos);
		
		if (StringUtils.isEmpty(incidenciaInfoRest.getObservaciones())) {
			inciVO.setObservaciones(null);
		}
		
		inciVO.setEstado(Long.valueOf(EstadoIncidencia.Borrador.getValorEnum()));
		
		return inciVO;
	}

}
