package org.gestoresmadrid.oegam2.trafico.prematriculados.controller.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.trafico.prematriculados.model.enumerados.EstadoConsultaVehiculoPrematiculado;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2.trafico.prematriculados.view.DecoratorVehiculoPrematriculado;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.ServicioVehiculosPrematriculados;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.beans.ConsultaTarjetaBean;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto.VehiculoPrematriculadoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaTarjetaResultadosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 6115556321716399427L;
	private static final String RESULTADO_DESCARGAR_FICHERO = "descargaFichero";
	private static ILoggerOegam log = LoggerOegam.getLogger(ConsultaTarjetaResultadosAction.class);

	private InputStream inputStream;
	private String fileName;

	private String listaVehiculos;

	private ConsultaTarjetaBean consultaTarjetaBean;

	@Resource
	private ModelPagination modeloConsultaTarjetaPaginated;

	@Resource
	private ServicioVehiculosPrematriculados servicioVehiculosPrematriculados;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaTarjetaPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaTarjetaBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		consultaTarjetaBean = new ConsultaTarjetaBean();
		consultaTarjetaBean.setFecha(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Class<?> getClaseDecorator() {
		return DecoratorVehiculoPrematriculado.class;
	}

	public String descargar() {
		try {
			if (listaVehiculos == null) {
				addActionError("Debe seleccionar al menos una fila");
				return getResultadoPorDefecto();
			}
			Long[] ids = utiles.convertirStringArrayToLongArray(listaVehiculos.replace(" ", "").split(","));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zip = new ZipOutputStream(baos);
			if (ids == null || ids.length == 0) {
				addActionError("Debe seleccionar al menos una fila");
				return getResultadoPorDefecto();
			}
			List<VehiculoPrematriculadoDto> vehiculoPrematriculadoDto = servicioVehiculosPrematriculados.buscar(ids,
					utilesColegiado.tienePermisoAdmin() ? null : utilesColegiado.getNumColegiadoSession());
			for (VehiculoPrematriculadoDto vehiculo : vehiculoPrematriculadoDto) {
				if (vehiculo.getEstadoCaracteristicas() != null && EstadoConsultaVehiculoPrematiculado.FINALIZADO
						.getValorEnum().equals(vehiculo.getEstadoCaracteristicas())) {
					anadirFileToZip(servicioVehiculosPrematriculados.buscarCaracteristicasDocumento(vehiculo.getId(),
							vehiculo.getFechaAlta()), zip, vehiculo.getId());
				}
				if (vehiculo.getEstadoFichaTecnica() != null && EstadoConsultaVehiculoPrematiculado.FINALIZADO
						.getValorEnum().equals(vehiculo.getEstadoFichaTecnica())) {
					anadirFileToZip(servicioVehiculosPrematriculados.buscarFichaTecnicaDocumento(vehiculo.getId(),
							vehiculo.getFechaAlta()), zip, vehiculo.getId());
				}
			}

			zip.close();

			ByteArrayInputStream stream = new ByteArrayInputStream(baos.toByteArray());
			setInputStream(stream);
			setFileName("Respuesta Eitv " + Calendar.getInstance().getTimeInMillis() + ".zip");
			log.debug("Enviando el ZIP");
		} catch (IOException e) {
			log.error("No se han podido obtener los ficheros");
			addActionError("No se han podido obtener los ficheros. Inténtelo más tarde");
			navegar();
			return getResultadoPorDefecto();
		}
		return RESULTADO_DESCARGAR_FICHERO;
	}

	private void anadirFileToZip(File file, ZipOutputStream zip, Long id) {
		if (file != null) {
			try {
				FileInputStream is = new FileInputStream(file);
				ZipEntry zipEntry = new ZipEntry(file.getName());
				zip.putNextEntry(zipEntry);
				byte[] buffer = new byte[2048];
				int byteCount;
				while (-1 != (byteCount = is.read(buffer))) {
					zip.write(buffer, 0, byteCount);
				}
				zip.closeEntry();
				is.close();
				if (file.lastModified() > 0) {
					zipEntry.setTime(file.lastModified());
				}
			} catch (FileNotFoundException e) {
				log.error("No se ha recuperdado el documento para el identificador:" + id);
				addActionError("No se ha recuperdado el documento para el identificador:" + id);
			} catch (IOException e) {
				log.error("No se ha recuperdado el documento para el identificador:" + id);
				addActionError("No se ha recuperdado el documento para el identificador:" + id);
			}
		} else {
			addActionError("No se ha recuperdado el documento para el identificador:" + id);
		}
	}

	public String getListaVehiculos() {
		return listaVehiculos;
	}

	public void setListaVehiculos(String listaVehiculos) {
		this.listaVehiculos = listaVehiculos;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ServicioVehiculosPrematriculados getServicioVehiculosPrematriculados() {
		return servicioVehiculosPrematriculados;
	}

	public void setServicioVehiculosPrematriculados(ServicioVehiculosPrematriculados servicioVehiculosPrematriculados) {
		this.servicioVehiculosPrematriculados = servicioVehiculosPrematriculados;
	}

	public ConsultaTarjetaBean getConsultaTarjetaBean() {
		return consultaTarjetaBean;
	}

	public void setConsultaTarjetaBean(ConsultaTarjetaBean consultaTarjetaBean) {
		this.consultaTarjetaBean = consultaTarjetaBean;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaTarjetaBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaTarjetaBean = (ConsultaTarjetaBean) object;
	}

}