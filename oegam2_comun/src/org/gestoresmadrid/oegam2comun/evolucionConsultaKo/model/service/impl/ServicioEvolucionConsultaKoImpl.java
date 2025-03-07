package org.gestoresmadrid.oegam2comun.evolucionConsultaKo.model.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.consultaKo.model.enumerados.EstadoKo;
import org.gestoresmadrid.core.consultaKo.model.enumerados.OperacionConsultaKo;
import org.gestoresmadrid.core.consultaKo.model.enumerados.TipoDocumentoKoEnum;
import org.gestoresmadrid.core.consultaKo.model.enumerados.TipoTramiteKo;
import org.gestoresmadrid.core.evolucionConsultaKo.model.dao.EvolucionConsultaKoDao;
import org.gestoresmadrid.core.evolucionConsultaKo.model.vo.EvolucionConsultaKoVO;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ResultadoConsultaKoBean;
import org.gestoresmadrid.oegam2comun.evolucionConsultaKo.model.service.ServicioEvolucionConsultaKo;
import org.gestoresmadrid.oegam2comun.evolucionConsultaKo.view.bean.EvolucionConsultaKoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionConsultaKoImpl implements ServicioEvolucionConsultaKo {

	private static final long serialVersionUID = -3390196041139495201L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionConsultaKoImpl.class);

	@Autowired
	EvolucionConsultaKoDao evolucionConsultaKoDao;

	@Override
	@Transactional
	public ResultadoConsultaKoBean guardarEvolucion(Long id,String matricula, BigDecimal idUsuario, String tipoTramite,
			String tipo, OperacionConsultaKo operacion, Date fecha, String estadoAnt, String estadoNuevo) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		try {
			EvolucionConsultaKoVO evolucionConsultaKoVO = new EvolucionConsultaKoVO();
			if(estadoAnt != null && !estadoAnt.isEmpty()){
				evolucionConsultaKoVO.setEstadoAnterior(new BigDecimal(estadoAnt));
			}
			if(estadoNuevo != null && !estadoNuevo.isEmpty()){
				evolucionConsultaKoVO.setEstadoNuevo(new BigDecimal(estadoNuevo));
			}
			evolucionConsultaKoVO.setFechaCambio(fecha);
			evolucionConsultaKoVO.setIdUsuario(idUsuario);
			evolucionConsultaKoVO.setMatricula(matricula);
			evolucionConsultaKoVO.setOperacion(operacion.getValorEnum());
			evolucionConsultaKoVO.setTipoTramite(tipoTramite);
			evolucionConsultaKoVO.setTipo(tipo);
			evolucionConsultaKoVO.setIdConsultaKo(id);
			evolucionConsultaKoDao.guardar(evolucionConsultaKoVO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion del KO, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la evolución.");
		}
		return resultado;
	}

	@Override
	public List<EvolucionConsultaKoBean> convertirListaEnBeanPantallaConsulta(List<EvolucionConsultaKoVO> lista) {
		List<EvolucionConsultaKoBean> listaPantalla = new ArrayList<EvolucionConsultaKoBean>();
		for(EvolucionConsultaKoVO evolucionConsultaKoVO : lista){
			EvolucionConsultaKoBean evolucionConsultaKoBean = new EvolucionConsultaKoBean();
			evolucionConsultaKoBean.setEstadoAnterior(EstadoKo.convertirEstadoBigDecimal(evolucionConsultaKoVO.getEstadoAnterior()));
			evolucionConsultaKoBean.setEstadoNuevo(EstadoKo.convertirEstadoBigDecimal(evolucionConsultaKoVO.getEstadoNuevo()));
			evolucionConsultaKoBean.setFechaCambio(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(evolucionConsultaKoVO.getFechaCambio()));
			evolucionConsultaKoBean.setMatricula(evolucionConsultaKoVO.getMatricula());
			evolucionConsultaKoBean.setOperacion(OperacionConsultaKo.convertirValor(evolucionConsultaKoVO.getOperacion()));
			evolucionConsultaKoBean.setTipoDocumento(TipoDocumentoKoEnum.convertirTexto(evolucionConsultaKoVO.getTipo()));
			evolucionConsultaKoBean.setTipoTramite(TipoTramiteKo.convertirTexto(evolucionConsultaKoVO.getTipoTramite()));
			evolucionConsultaKoBean.setUsuario(evolucionConsultaKoVO.getUsuario().getApellidosNombre());
			listaPantalla.add(evolucionConsultaKoBean);
		}
		return listaPantalla;
	}

}