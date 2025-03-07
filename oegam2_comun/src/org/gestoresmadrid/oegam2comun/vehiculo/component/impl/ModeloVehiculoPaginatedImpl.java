package org.gestoresmadrid.oegam2comun.vehiculo.component.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloVehiculoPaginated")
@Transactional(readOnly = true)
public class ModeloVehiculoPaginatedImpl extends AbstractModelPagination {

	@Resource
	private VehiculoDao vehiculoDao;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private Conversor conversor;

	@Override
	protected GenericDao<?> getDao() {
		return vehiculoDao;
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		List<?> list;
		List<VehiculoDto> listaFinal = new ArrayList<>();
		int numElementosTotales = getDao().numeroElementos(beanCriterios);
		if (numElementosTotales > 0) {
			int firstResult = 0;
			int maxResults = resPag;

			firstResult = resPag * (page - 1);
			if (firstResult >= numElementosTotales) {
				if (numElementosTotales % resPag == 0) {
					page = (numElementosTotales / resPag);
				} else {
					page = (numElementosTotales / resPag) + 1;
				}
				firstResult = resPag * (page - 1);
			}

			BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
			ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);

			List<AliasQueryBean> alias = crearListaAlias();
			list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath, alias);

			if (list != null && !list.isEmpty()) {
				Iterator it = (Iterator) list.iterator();
				while (it.hasNext()) {
					VehiculoVO vehiculoVO = (VehiculoVO) it.next();
					VehiculoDto vehiculoDto = conversor.transform(vehiculoVO, VehiculoDto.class);
					if (vehiculoDto.getTipoVehiculo() != null && !"".equals(vehiculoDto.getTipoVehiculo())) {
						vehiculoDto.setTipoVehiculoDes(vehiculoDto.getTipoVehiculo() + " - " + servicioVehiculo.obtenerTipoVehiculoDescripcion(vehiculoDto.getTipoVehiculo()));
					}
					listaFinal.add(vehiculoDto);
				}
			}
		} else {
			list = Collections.emptyList();
		}
		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, listaFinal);
	}

	public VehiculoDao getVehiculoDao() {
		return vehiculoDao;
	}

	public void setVehiculoDao(VehiculoDao vehiculoDao) {
		this.vehiculoDao = vehiculoDao;
	}
}