package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDetalleDocPrmDstvFichaDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.DetalleDocPrmDstvFichaDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDetalleDocPrmDstvFichaDgtImpl implements ServicioDetalleDocPrmDstvFichaDgt{

	private static final long serialVersionUID = 1568439170784308325L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDetalleDocPrmDstvFichaDgtImpl.class);

	private static final String NO_EXISTEN_EXPEDIENTES_ASOCIADOS_AL_DOCUMENTO = "No existen expedientes asociados al documento.";

	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;

	@Override
	@Transactional(readOnly=true)
	public ResultadoPermisoDistintivoItvBean getTramitesDocPrmDstvFicha(String docId, int page, String resultadosPorPagina) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(docId != null && !docId.isEmpty()){
				int resultPorPagina = resultadosPorPagina.equals("0") ? 5 : Integer.parseInt(resultadosPorPagina);
				List<DetalleDocPrmDstvFichaDgtBean> listAux = new ArrayList<>();
				int e = 0;
				int eInicio = 0;
				int eFinal = 0;
				if(page != 1){
					e = page - 1;
					eInicio =  e * resultPorPagina;
					eFinal = page * resultPorPagina;
				} else {
					eFinal = resultPorPagina;
				}
				int totalLista = 0;
				DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPermDistFicha(docId, Boolean.FALSE);
				if(docPermDistItvVO != null){
					if(TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(docPermDistItvVO.getTipo())){
						List<DetalleDocPrmDstvFichaDgtBean> listaDetalle = servicioDocPrmDstvFicha.getListaDetalleDocFichaPermiso(docPermDistItvVO.getIdDocPermDistItv());
						if (listaDetalle != null && !listaDetalle.isEmpty()) {
							if (eFinal > listaDetalle.size()){
								eFinal = listaDetalle.size();
							}
							totalLista = listaDetalle.size();
							if (listaDetalle.size() > resultPorPagina) {
								for (e = eInicio; e<eFinal; e++) {
									listAux.add(listaDetalle.get(e));
								}
							} else {
								listAux = listaDetalle;
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(NO_EXISTEN_EXPEDIENTES_ASOCIADOS_AL_DOCUMENTO);
						}
					} else if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())) {
						List<DetalleDocPrmDstvFichaDgtBean> listaDetalle = servicioDocPrmDstvFicha.getListaDetalleDocDstv(docPermDistItvVO.getIdDocPermDistItv());
						if (listaDetalle != null && !listaDetalle.isEmpty()) {
							if (eFinal > listaDetalle.size()) {
								eFinal = listaDetalle.size();
							}
							totalLista = listaDetalle.size();
							if (listaDetalle.size()>resultPorPagina) {
								for (e = eInicio; e < eFinal; e++) {
									listAux.add(listaDetalle.get(e));
								}
							}else{
								listAux = listaDetalle;
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(NO_EXISTEN_EXPEDIENTES_ASOCIADOS_AL_DOCUMENTO);
						}
					} else if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())){
						List<TramiteTrafDto> listaTramites = servicioDocPrmDstvFicha.getListaTramitesPermisos(docPermDistItvVO.getIdDocPermDistItv());
						if(listaTramites != null && !listaTramites.isEmpty()){
							if(eFinal > listaTramites.size()){
								eFinal = listaTramites.size();
							}
							totalLista = listaTramites.size();
							if(listaTramites.size()>resultPorPagina){
								for(e = eInicio; e < eFinal; e++){
									DetalleDocPrmDstvFichaDgtBean detalle = new DetalleDocPrmDstvFichaDgtBean();
									detalle.setMatricula(listaTramites.get(e).getVehiculoDto().getMatricula());
									detalle.setNumExpediente(listaTramites.get(e).getNumExpediente().toString());
									detalle.setNumSerie(listaTramites.get(e).getnSeriePermiso());
									detalle.setTipoDocumento(TipoDocumentoImprimirEnum.convertirTexto(docPermDistItvVO.getTipo()));
									listAux.add(detalle);
								}
							}else{
								for(TramiteTrafDto tramiteTrafDto : listaTramites){
									DetalleDocPrmDstvFichaDgtBean detalle = new DetalleDocPrmDstvFichaDgtBean();
									detalle.setMatricula(tramiteTrafDto.getVehiculoDto().getMatricula());
									detalle.setNumExpediente(tramiteTrafDto.getNumExpediente().toString());
									detalle.setNumSerie(tramiteTrafDto.getnSeriePermiso());
									detalle.setTipoDocumento(TipoDocumentoImprimirEnum.convertirTexto(docPermDistItvVO.getTipo()));
									listAux.add(detalle);
								}
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(NO_EXISTEN_EXPEDIENTES_ASOCIADOS_AL_DOCUMENTO);
						}
					} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())){
						List<TramiteTrafDto> listaTramites = servicioDocPrmDstvFicha.getListaTramitesFichasTecnicas(docPermDistItvVO.getIdDocPermDistItv());
						if(listaTramites != null && !listaTramites.isEmpty()){
							if(eFinal > listaTramites.size()){
								eFinal = listaTramites.size();
							}
							totalLista = listaTramites.size();
							if(listaTramites.size()>resultPorPagina){
								for(e=eInicio;e<eFinal;e++){
									DetalleDocPrmDstvFichaDgtBean detalle = new DetalleDocPrmDstvFichaDgtBean();
									detalle.setMatricula(listaTramites.get(e).getVehiculoDto().getMatricula());
									detalle.setNumExpediente(listaTramites.get(e).getNumExpediente().toString());
									detalle.setTipoDocumento(TipoDocumentoImprimirEnum.convertirTexto(docPermDistItvVO.getTipo()));
									listAux.add(detalle);
								}
							}else{
								for(TramiteTrafDto tramiteTrafDto : listaTramites){
									DetalleDocPrmDstvFichaDgtBean detalle = new DetalleDocPrmDstvFichaDgtBean();
									detalle.setMatricula(tramiteTrafDto.getVehiculoDto().getMatricula());
									detalle.setNumExpediente(tramiteTrafDto.getNumExpediente().toString());
									detalle.setTipoDocumento(TipoDocumentoImprimirEnum.convertirTexto(docPermDistItvVO.getTipo()));
									listAux.add(detalle);
								}
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(NO_EXISTEN_EXPEDIENTES_ASOCIADOS_AL_DOCUMENTO);
						}
					}
					if(listAux != null && !listAux.isEmpty()){
						resultado.setListaDetallePaginacion(new PaginatedListImpl(resultPorPagina,page, "matricula", null,totalLista, listAux));
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se pueden obtener el listado de expedientes.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de los trámites del documento, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener la lista de los trámites del documento.");
		}
		return resultado;
	}
}