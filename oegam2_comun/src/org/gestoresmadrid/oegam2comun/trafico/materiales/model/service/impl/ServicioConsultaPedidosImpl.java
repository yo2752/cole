package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.PedidoDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.Delegacion;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoPedido;
import org.gestoresmadrid.core.trafico.materiales.model.vo.AutorVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.PedidoVO;
import org.gestoresmadrid.core.trafico.model.dao.JefaturaTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaPedidos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaUsuarioConsejo;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.PedidosResultadosBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.PedidoInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.PedidoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaPedidosImpl implements ServicioConsultaPedidos {

	/**
	 * 
	 */
	private static final long serialVersionUID = 391324439563996711L;
	private ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaPedidosImpl.class);

	private static final String TIPO_PERMISO = "PC";


	@Resource PedidoDao          pedidoDao;
	@Resource MaterialDao        materialDao;
	@Resource JefaturaTraficoDao jefaturaTraficoDao;

	@Autowired Conversor                      conversor;
	@Autowired ServicioConsultaMateriales     servicioConsultaMateriales;
	@Autowired ServicioConsultaUsuarioConsejo servicioConsultaUsuarioConsejo;

	@Override
	public List<PedidosResultadosBean> convertirListaEnBeanPantalla(List<PedidoVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<PedidosResultadosBean> listaBean = new ArrayList<PedidosResultadosBean>();

			for (PedidoVO pedidoVO : lista) {
				PedidosResultadosBean pedidosResultadosBean = conversor.transform(pedidoVO,
						PedidosResultadosBean.class);
				
				String estado = EstadoPedido.convertirEstadoLong(pedidoVO.getEstado());
				pedidosResultadosBean.setEstado(estado);
				
				String jefatura = pedidoVO.getJefaturaProvincial().getDescripcion();
				pedidosResultadosBean.setJefaturaProvincial(jefatura);

				String pedidoDgt = "Y".equals(pedidoVO.getPedidoDgt())? "Si": "No";
				pedidosResultadosBean.setPedidoDgt(pedidoDgt);

				String material = pedidoVO.getMaterialVO().getName();
				pedidosResultadosBean.setMaterialVO(material);
				
				pedidosResultadosBean.setPedidoPermisosEntregado(isPedidoPermisosEntregado(pedidoVO));
				pedidosResultadosBean.setPedidoEntregado(isPedidoEntregadoSinSeriales(pedidoVO));
				pedidosResultadosBean.setSolicitarPedido(isSolicitarPedido(pedidoVO));
				pedidosResultadosBean.setEntregarPedido(isEntregarPedido(pedidoVO));
				
				listaBean.add(pedidosResultadosBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly=true)
	public PedidoDto getPedidoDto(Long pedidoId) {
		PedidoVO resultado = pedidoDao.findPedidoByPK(pedidoId);

		
		boolean pedidoPermiso = TIPO_PERMISO.equals(resultado.getMaterialVO().getTipo());
		boolean pedidoPermisoEntregado = pedidoPermiso && 
				                         resultado.getEstado().equals(new Long(EstadoPedido.Entregado.getValorEnum()));

		PedidoDto pedidoDto = this.getDtoFromVO(resultado);
		
		pedidoDto.setPedidoPermisosEntregado(pedidoPermisoEntregado);
		pedidoDto.setPedidoPermisos(pedidoPermiso);
		return pedidoDto;
		
	}

	@Override
	@Transactional(readOnly=true)
	public Long validadMaterial(String jefatura, Long materialId) {
		PedidoVO pedido = pedidoDao.findPedidoForJefaturaMateria(jefatura, materialId);
		return (pedido == null)? null: pedido.getPedidoId();
	}
	
	@Override
	@Transactional(readOnly=true)
	public PedidoVO obtenerPedidoInventario(PedidoDto pedidoDto) {
		PedidoVO pedidoVO = pedidoDao.findPedidoByInventario(pedidoDto.getCodigoInicial(),
				                                             pedidoDto.getCodigoFinal(),
				                                             pedidoDto.getJefaturaProvincial());
		return pedidoVO;
	}

	@Override
	@Transactional(readOnly=true)
	public PedidoVO obtenerPedidoCompleto(Long pedidoId) {
		PedidoVO pedidoVO = pedidoDao.findPedidoCompleto(pedidoId);
		return pedidoVO;
	}

	@Override
	@Transactional(readOnly=true)
	public PedidoVO obtenerPedidoForInvetarioId(Long inventarioId) {
		PedidoVO pedidoVO = pedidoDao.findPedidoByInventarioId(inventarioId, true);
		return pedidoVO;
	}

	@Override
	@Transactional(readOnly=true)
	public PedidoVO obtenerPedidoForPrimaryKey(Long pedidoId) {
		PedidoVO pedidoVO = pedidoDao.findPedidoByPK(pedidoId);
		
		return pedidoVO;
	}


	@Override
	@Transactional(readOnly=true)
	public PedidoVO obtenerPedidoByJefaturaMaterialSerial(String jefatura, Long materialId, String serial) {
		PedidoVO pedidoVO = pedidoDao.findPedidoByJefaturaMaterialSerial(jefatura, materialId, serial);
		return pedidoVO;
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public PedidoDto getDtoFromVO(PedidoVO pedidoVO) {
		PedidoDto ped = conversor.transform(pedidoVO, PedidoDto.class);
		ped.setNomEstado(EstadoPedido.convertirTexto(pedidoVO.getEstado()));
		
		return ped;
	}

	@Override
	@Transactional(readOnly=true)
	public PedidoVO getVOFromDto(PedidoDto pedidoDto) {
		PedidoVO pedidoVO = conversor.transform(pedidoDto, PedidoVO.class);
		
		JefaturaTraficoVO jtVO = jefaturaTraficoDao.getJefatura(pedidoDto.getJefaturaProvincial());
		pedidoVO.setJefaturaProvincial(jtVO);
		pedidoVO.setFecha(new Date());
		
		MaterialVO materialVO = materialDao.findByPrimaryKey(pedidoDto.getMaterialId());
		pedidoVO.setMaterialVO(materialVO);
		
		Double total = materialVO.getPrecio() * pedidoDto.getUnidades();
		pedidoVO.setTotal(total);
		
		if (pedidoVO.getPedidoDgt() == null || StringUtils.isEmpty(pedidoVO.getPedidoDgt())) {
			pedidoVO.setPedidoDgt(materialVO.getName().contains("Permiso")? "Y": "N");
		} else {
			pedidoVO.setPedidoDgt(pedidoDto.getPedidoDgt());
		}

		AutorVO autorVO = servicioConsultaUsuarioConsejo.obtenerAutorFromUsuarioColegio();
		pedidoVO.setAutorVO(autorVO);
		
		if (pedidoVO.getEstado() == null) {
			pedidoVO.setEstado(new Long(EstadoPedido.Iniciado.getValorEnum()));
		} else {
			pedidoVO.setEstado(pedidoDto.getEstado());
		}

		return pedidoVO;
	}

	@Override
	public PedidoDto getDtoFromInfoRest(PedidoInfoRest pedidoInfoRest) {
		PedidoDto pedidoDto = conversor.transform(pedidoInfoRest, PedidoDto.class);
		
		pedidoDto.setPedidoInvId(pedidoInfoRest.getId());

		String jefatura = Delegacion.convertirTexto(pedidoInfoRest.getDelegacion().getId());
		pedidoDto.setJefaturaProvincial(jefatura);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		try {
			pedidoDto.setFecha(sdf.parse(pedidoInfoRest.getFecha()));
		} catch (ParseException e) {
			log.error("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
		}
		
		pedidoDto.setCodigoInicial(pedidoInfoRest.getCodInicial());
		pedidoDto.setCodigoFinal(pedidoInfoRest.getCodFinal());
		Long estado = EstadoPedido.convertirNombre(pedidoInfoRest.getEstado());
		pedidoDto.setEstado(estado);
		
		return pedidoDto;
	}

	@Override
	@Transactional(readOnly=true)
	public PedidoVO getVOFromInfoRest(PedidoInfoRest pedidoInfoRest) {
		PedidoVO pedidoVO = conversor.transform(pedidoInfoRest, PedidoVO.class);
		
		String jefaturaProvincial = Delegacion.convertirTexto(pedidoInfoRest.getDelegacion().getId());
		JefaturaTraficoVO jtVO = jefaturaTraficoDao.getJefatura(jefaturaProvincial);
		pedidoVO.setJefaturaProvincial(jtVO);
		
		Long estado = EstadoPedido.convertirNombre(pedidoInfoRest.getEstado());
		pedidoVO.setEstado(estado);
		
		Long materialId = pedidoVO.getMaterialVO().getMaterialId();
		MaterialVO materialVO = servicioConsultaMateriales.getMaterialForPrimaryKey(materialId);

		pedidoVO.setMaterialVO(materialVO);

		if (StringUtils.isEmpty(pedidoInfoRest.getCodInicial())) {
			pedidoVO.setCodigoInicial(null);
		}
		if (StringUtils.isEmpty(pedidoInfoRest.getCodFinal())) {
			pedidoVO.setCodigoFinal(null);
		}
		
		if (StringUtils.isEmpty(pedidoInfoRest.getPedidoDgt())) {
			pedidoVO.setPedidoDgt("N");
		}
		
		return pedidoVO;
	}

	@Override
	public PedidoVO obtenerPedidoForSerialJefaturq(String serial, String jefatura) {
		PedidoVO pedidoVO = pedidoDao.findPedidoByJefaturaSerial(serial, jefatura);
		return pedidoVO;
	}

	@Override
	public boolean isPedidoPermisosEntregado(PedidoVO pedidoVO) {
		boolean es = true;
		
		es = es && (StringUtils.isNotEmpty(pedidoVO.getCodigoInicial()) && 
	                StringUtils.isNotEmpty(pedidoVO.getCodigoFinal()));
		es = es && (isPedidoPermisos(pedidoVO) && 
	                pedidoVO.getEstado().equals(new Long(EstadoPedido.Entregado.getValorEnum())));
		
		return es;
	}

	public boolean isPedidoEntregadoSinSeriales(PedidoVO pedidoVO) {
		
		boolean sinSeriales = StringUtils.isEmpty(pedidoVO.getCodigoInicial()) && 
				              StringUtils.isEmpty(pedidoVO.getCodigoFinal());
		boolean entregado = pedidoVO.getEstado().equals(new Long(EstadoPedido.Entregado.getValorEnum()));
		
		return sinSeriales && entregado;
	}

	@Override
	public boolean isPedidoPermisos(PedidoVO pedidoVO) {
		return TIPO_PERMISO.equals(pedidoVO.getMaterialVO().getTipo());
	}

	private boolean isSolicitarPedido(PedidoVO pedidoVO) {
		EstadoPedido estado = EstadoPedido.convertir(pedidoVO.getEstado());
		return EstadoPedido.Borrador.equals(estado)? true: false;
	}

	private boolean isEntregarPedido(PedidoVO pedidoVO) {
		// TODO Auto-generated method stub
		EstadoPedido estado = EstadoPedido.convertir(pedidoVO.getEstado());
		return EstadoPedido.Solicitado_Consejo.equals(estado)? true: false;
	}


}
