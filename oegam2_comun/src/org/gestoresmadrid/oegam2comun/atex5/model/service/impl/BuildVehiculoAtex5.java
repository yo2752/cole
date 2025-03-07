package org.gestoresmadrid.oegam2comun.atex5.model.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosAdministrativosAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosCotitularAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosDefectoItvAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosDomicilioAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosElementoSeguridadAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosHojaRescateAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosIncidenciaAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosItvAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosLibroTallerAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosMotivoReformaAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosNcapAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosPersonaAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosReformaAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosResponsableAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosSeguridadAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosSeguroAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosTramitesAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.VehiculoAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Arrendatario;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Arrendatarios;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Avisos;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Baja;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Bajas;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.ConductorHabitual;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.ConductoresHabituales;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Cotitulares;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.DefectosItv;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Denegatorias;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Duplicados;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Embargos;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Impagos;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Itv;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Itvs;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.LimitacionesDisposicion;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.MotivosReforma;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Poseedores;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Precintos;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Prorrogas;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Reformas;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Rematriculaciones;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Seguro;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Transferencias;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Tutores;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Vehiculo;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildVehiculoAtex5 implements Serializable{

	private static final long serialVersionUID = -5327677916602046732L;
	
	@Autowired
	Conversor conversor;

	public VehiculoAtex5Dto convertirVehiculoXmlToVehiculoAtex5(Vehiculo vehiculoAtex5) {
		VehiculoAtex5Dto vehiculoAtex5Dto = conversor.transform(vehiculoAtex5, VehiculoAtex5Dto.class,"vehiculoAtex5Dto");
		if(vehiculoAtex5Dto != null){
			rellenarDatosCotitular(vehiculoAtex5Dto,vehiculoAtex5);
			rellenarDatosTitular(vehiculoAtex5Dto,vehiculoAtex5);
			rellenarDatosResponsables(vehiculoAtex5Dto,vehiculoAtex5);
			rellenarDatosTramites(vehiculoAtex5Dto,vehiculoAtex5);
			rellenarDatosAdministrativos(vehiculoAtex5Dto,vehiculoAtex5);
			rellenarDatosItvsReformas(vehiculoAtex5Dto,vehiculoAtex5);
			rellanarDatosSeguro(vehiculoAtex5Dto,vehiculoAtex5);
			rellenarDatosSeguridad(vehiculoAtex5Dto,vehiculoAtex5);
			rellenarDatosLibroTaller(vehiculoAtex5Dto,vehiculoAtex5);
		}
		return vehiculoAtex5Dto;
	}
	

	private void rellenarDatosLibroTaller(VehiculoAtex5Dto vehiculoAtex5Dto, Vehiculo vehiculoAtex5) {
		if(vehiculoAtex5.getDatosLibroTaller() != null){
			DatosLibroTallerAtex5Dto datosLibroTallerAtex5Dto = new DatosLibroTallerAtex5Dto();
			if(vehiculoAtex5.getDatosLibroTaller().getListaDetalleIncidencias() != null
					&& vehiculoAtex5.getDatosLibroTaller().getListaDetalleIncidencias().getDetalleIncidencia() != null 
					&& !vehiculoAtex5.getDatosLibroTaller().getListaDetalleIncidencias().getDetalleIncidencia().isEmpty()){
				datosLibroTallerAtex5Dto.setListaDetalleIncidencias(conversor.transform(vehiculoAtex5.getDatosLibroTaller().getListaDetalleIncidencias().getDetalleIncidencia(), DatosIncidenciaAtex5Dto.class));
				vehiculoAtex5Dto.setExistenDatosLibroTalles(Boolean.TRUE);
			}
			if(vehiculoAtex5.getDatosLibroTaller().getDetalleIncidenciaSeleccionada() != null){
				datosLibroTallerAtex5Dto.setDetalleIncidenciaSeleccionada(conversor.transform(vehiculoAtex5.getDatosLibroTaller().getDetalleIncidenciaSeleccionada(), DatosIncidenciaAtex5Dto.class));
				vehiculoAtex5Dto.setExistenDatosLibroTalles(Boolean.TRUE);
			}
			vehiculoAtex5Dto.setDatosLibroTallerDto(datosLibroTallerAtex5Dto);
		}
	}


	private void rellenarDatosSeguridad(VehiculoAtex5Dto vehiculoAtex5Dto, Vehiculo vehiculoAtex5) {
		if(vehiculoAtex5.getDatosSeguridad() != null){
			DatosSeguridadAtex5Dto datosSeguridadAtex5Dto = new DatosSeguridadAtex5Dto();
			if(vehiculoAtex5.getDatosSeguridad().getListaElementosSeguridad() != null 
				&& vehiculoAtex5.getDatosSeguridad().getListaElementosSeguridad().getElementoSeguridad() != null 
				&& !vehiculoAtex5.getDatosSeguridad().getListaElementosSeguridad().getElementoSeguridad().isEmpty()){
				datosSeguridadAtex5Dto.setListaElementosSeguridad(
						conversor.transform(vehiculoAtex5.getDatosSeguridad().getListaElementosSeguridad().getElementoSeguridad(), DatosElementoSeguridadAtex5Dto.class));
				vehiculoAtex5Dto.setExistenDatosSeguridad(Boolean.TRUE);
			}
			if(vehiculoAtex5.getDatosSeguridad().getNcap() != null && !vehiculoAtex5.getDatosSeguridad().getNcap().isEmpty()){
				datosSeguridadAtex5Dto.setListaNcap(conversor.transform(vehiculoAtex5.getDatosSeguridad().getNcap(), DatosNcapAtex5Dto.class));
				vehiculoAtex5Dto.setExistenDatosSeguridad(Boolean.TRUE);
			}
			if(vehiculoAtex5.getDatosSeguridad().getHojaRescate() != null && !vehiculoAtex5.getDatosSeguridad().getHojaRescate().isEmpty()){
				datosSeguridadAtex5Dto.setListaHojaRescate(conversor.transform(vehiculoAtex5.getDatosSeguridad().getHojaRescate(), DatosHojaRescateAtex5Dto.class));
				vehiculoAtex5Dto.setExistenDatosSeguridad(Boolean.TRUE);
			}
			vehiculoAtex5Dto.setDatosSeguridadDto(datosSeguridadAtex5Dto);
		}
	}


	private void rellanarDatosSeguro(VehiculoAtex5Dto vehiculoAtex5Dto, Vehiculo vehiculoAtex5) {
		if(vehiculoAtex5.getDatosSeguros() != null && vehiculoAtex5.getDatosSeguros().getListaSeguros() != null
			&& vehiculoAtex5.getDatosSeguros().getListaSeguros().getSeguro() != null 
			&& !vehiculoAtex5.getDatosSeguros().getListaSeguros().getSeguro().isEmpty()){
			List<DatosSeguroAtex5Dto> listaSeguros = new ArrayList<DatosSeguroAtex5Dto>();
			for(Seguro seguro : vehiculoAtex5.getDatosSeguros().getListaSeguros().getSeguro()){
				DatosSeguroAtex5Dto datosSeguroAtex5 = conversor.transform(seguro, DatosSeguroAtex5Dto.class);
				if(seguro.getFechaFin() != null && !seguro.getFechaFin().isEmpty() && seguro.getFechaFin().get(0) != null 
					&& seguro.getFechaFin().get(0).getValue() != null 
					&& seguro.getFechaFin().get(0).getValue().get(0) != null){
					datosSeguroAtex5.setFechaFin(seguro.getFechaFin().get(0).getValue().get(0).toGregorianCalendar().getTime());
				}
				listaSeguros.add(datosSeguroAtex5);
			}
			vehiculoAtex5Dto.setListaSegurosDto(listaSeguros);
			vehiculoAtex5Dto.setExistenDatosSeguros(Boolean.TRUE);
		}
		
	}

	private void rellenarDatosItvsReformas(VehiculoAtex5Dto vehiculoAtex5Dto, Vehiculo vehiculoAtex5) {
		if(vehiculoAtex5.getDatosITVReformas() != null){
			if(vehiculoAtex5.getDatosITVReformas().getListaItvs() != null){
				Itvs itvs = vehiculoAtex5.getDatosITVReformas().getListaItvs().getValue();
				if(itvs != null && itvs.getItv() != null && !itvs.getItv().isEmpty()){
					List<DatosItvAtex5Dto> listaItv = new ArrayList<DatosItvAtex5Dto>();
					for(Itv itv : itvs.getItv()){
						DatosItvAtex5Dto datosItvAtex5Dto = conversor.transform(itv, DatosItvAtex5Dto.class);
						if(itv.getListaDefectosItv() != null){
							DefectosItv defectosItv = itv.getListaDefectosItv().getValue();
							datosItvAtex5Dto.setListaDefectos(conversor.transform(defectosItv.getDefectoItv(), DatosDefectoItvAtex5Dto.class));
						}
						if(itv.getFechaFinAnterior() != null && !itv.getFechaFinAnterior().isEmpty() 
							&& itv.getFechaFinAnterior().get(0) != null 
							&& itv.getFechaFinAnterior().get(0).getValue() != null && itv.getFechaFinAnterior().get(0).getValue().get(0) != null){
							datosItvAtex5Dto.setFechaFinAnterior(itv.getFechaFinAnterior().get(0).getValue().get(0).toGregorianCalendar().getTime());
						}
						if(itv.getFechaItv() != null && !itv.getFechaItv().isEmpty() && itv.getFechaItv().get(0) != null 
							&& itv.getFechaItv().get(0).getValue() != null && itv.getFechaItv().get(0).getValue().get(0) != null){
							datosItvAtex5Dto.setFechaItv(itv.getFechaItv().get(0).getValue().get(0).toGregorianCalendar().getTime());
						}
						listaItv.add(datosItvAtex5Dto);
					}
					vehiculoAtex5Dto.setListaItvsDto(listaItv);
					vehiculoAtex5Dto.setExistenDatosItvs(Boolean.TRUE);
				}
			}
			if(vehiculoAtex5.getDatosITVReformas().getListaReformas() != null){
				Reformas reformas = vehiculoAtex5.getDatosITVReformas().getListaReformas().getValue();
				if(reformas != null && reformas.getReforma() != null){
					DatosReformaAtex5Dto datosReformaAtex5Dto = conversor.transform(reformas.getReforma(), DatosReformaAtex5Dto.class);
					if(reformas.getReforma().getListaMotivosReforma() != null){
						MotivosReforma motivosReforma = reformas.getReforma().getListaMotivosReforma().getValue();
						datosReformaAtex5Dto.setListaMotivosReforma(conversor.transform(motivosReforma.getMotivoReforma(), DatosMotivoReformaAtex5Dto.class));
						vehiculoAtex5Dto.setExistenDatosItvs(Boolean.TRUE);
					}
				}
			}
		}
	}

	private void rellenarDatosAdministrativos(VehiculoAtex5Dto vehiculoAtex5Dto, Vehiculo vehiculoAtex5) {
		if(vehiculoAtex5.getDatosAdministrativos() != null){
			if(vehiculoAtex5.getDatosAdministrativos().getListaAvisos() != null){
				Avisos avisos = vehiculoAtex5.getDatosAdministrativos().getListaAvisos().getValue();
				if(avisos != null && avisos.getAviso() != null && !avisos.getAviso().isEmpty()){
					vehiculoAtex5Dto.setListaAvisosDto(conversor.transform(avisos.getAviso(), DatosAdministrativosAtex5Dto.class,"avisoAtex5"));
					vehiculoAtex5Dto.setExistenDatosAdministrativos(Boolean.TRUE);
				}
			}
			if(vehiculoAtex5.getDatosAdministrativos().getListaDenegatorias() != null){
				Denegatorias denegatorias = vehiculoAtex5.getDatosAdministrativos().getListaDenegatorias().getValue();
				if(denegatorias != null && denegatorias.getDenegatoria() != null && !denegatorias.getDenegatoria().isEmpty()){
					vehiculoAtex5Dto.setListaDenegatoriasDto(conversor.transform(denegatorias.getDenegatoria(), DatosAdministrativosAtex5Dto.class,"denegatoriaAtex5"));
					vehiculoAtex5Dto.setExistenDatosAdministrativos(Boolean.TRUE);
				}
			}
			if(vehiculoAtex5.getDatosAdministrativos().getListaEmbargos() != null){
				Embargos embargos = vehiculoAtex5.getDatosAdministrativos().getListaEmbargos().getValue();
				if(embargos != null && embargos.getEmbargo() != null && !embargos.getEmbargo().isEmpty()){
					vehiculoAtex5Dto.setListaEmbargosDto(conversor.transform(embargos.getEmbargo(), DatosAdministrativosAtex5Dto.class,"embargoAtex5"));
					vehiculoAtex5Dto.setExistenDatosAdministrativos(Boolean.TRUE);
				}
			}
			if(vehiculoAtex5.getDatosAdministrativos().getListaImpagos() != null){
				Impagos impagos = vehiculoAtex5.getDatosAdministrativos().getListaImpagos().getValue();
				if(impagos != null && impagos.getImpago() != null && !impagos.getImpago().isEmpty()){
					vehiculoAtex5Dto.setListaImpagosDto(conversor.transform(impagos.getImpago(), DatosAdministrativosAtex5Dto.class,"impagoAtex5"));
					vehiculoAtex5Dto.setExistenDatosAdministrativos(Boolean.TRUE);
				}
			}
			if(vehiculoAtex5.getDatosAdministrativos().getListaLimitaciones() != null){
				LimitacionesDisposicion limitacionesDisposicion = vehiculoAtex5.getDatosAdministrativos().getListaLimitaciones().getValue();
				if(limitacionesDisposicion != null && limitacionesDisposicion.getLimitacionDisposicion() != null){
					vehiculoAtex5Dto.setLimitacionesDisposicionDto(conversor.transform(limitacionesDisposicion.getLimitacionDisposicion(), DatosAdministrativosAtex5Dto.class,"limitacionDisposicionAtex5"));
					vehiculoAtex5Dto.setExistenDatosAdministrativos(Boolean.TRUE);
				}
			}
			if(vehiculoAtex5.getDatosAdministrativos().getListaPrecintos() != null){
				Precintos precintos = vehiculoAtex5.getDatosAdministrativos().getListaPrecintos().getValue();
				if(precintos != null && precintos.getPrecinto() != null && !precintos.getPrecinto().isEmpty()){
					vehiculoAtex5Dto.setListaPrecintosDto(conversor.transform(precintos.getPrecinto(), DatosAdministrativosAtex5Dto.class,"precintoAtex5"));
					vehiculoAtex5Dto.setExistenDatosAdministrativos(Boolean.TRUE);
				}
			}
		}
	}

	private void rellenarDatosTramites(VehiculoAtex5Dto vehiculoAtex5Dto, Vehiculo vehiculoAtex5) {
		if(vehiculoAtex5.getDatosTramites() != null){
			if(vehiculoAtex5.getDatosTramites().getListaBajas() != null){
				Bajas bajas = vehiculoAtex5.getDatosTramites().getListaBajas().getValue();
				if(bajas != null && bajas.getBaja() != null && !bajas.getBaja().isEmpty()){
					List<DatosTramitesAtex5Dto> listaTramitesBaja = new ArrayList<DatosTramitesAtex5Dto>();
					for(Baja baja : bajas.getBaja()){
						DatosTramitesAtex5Dto tramiteBajaVehAtex5 = conversor.transform(baja, DatosTramitesAtex5Dto.class,"tramiteBajaAtex5");
						if(baja.getFechaFin() != null && !baja.getFechaFin().isEmpty() && baja.getFechaFin().get(0) != null 
							&& baja.getFechaFin().get(0).getValue() != null && baja.getFechaFin().get(0).getValue().get(0) != null){
							tramiteBajaVehAtex5.setFechaFin(baja.getFechaFin().get(0).getValue().get(0).toGregorianCalendar().getTime());
						}
						listaTramitesBaja.add(tramiteBajaVehAtex5);
					}
					vehiculoAtex5Dto.setListaBajasDto(listaTramitesBaja);
					vehiculoAtex5Dto.setExistenDatosTramites(Boolean.TRUE);
				}
			}
			if(vehiculoAtex5.getDatosTramites().getListaDuplicados() != null){
				Duplicados duplicados = vehiculoAtex5.getDatosTramites().getListaDuplicados().getValue();
				if(duplicados != null && duplicados.getDuplicado() != null && !duplicados.getDuplicado().isEmpty()){
					vehiculoAtex5Dto.setListaDuplicadosDto(conversor.transform(duplicados.getDuplicado(), DatosTramitesAtex5Dto.class,"tarmiteDuplicadoAtex5"));
					vehiculoAtex5Dto.setExistenDatosTramites(Boolean.TRUE);	
				}
			}
			if(vehiculoAtex5.getDatosTramites().getListaProrrogas() != null){
				Prorrogas prorrogas = vehiculoAtex5.getDatosTramites().getListaProrrogas().getValue();
				if(prorrogas != null && prorrogas.getProrroga() != null && !prorrogas.getProrroga().isEmpty()){
					vehiculoAtex5Dto.setListaProrrogasDto(conversor.transform(prorrogas.getProrroga(), DatosTramitesAtex5Dto.class,"tramiteProrrogaAtex5"));
					vehiculoAtex5Dto.setExistenDatosTramites(Boolean.TRUE);
				}
			}
			if(vehiculoAtex5.getDatosTramites().getListaRematriculaciones() != null){
				Rematriculaciones rematriculaciones = vehiculoAtex5.getDatosTramites().getListaRematriculaciones().getValue();
				if(rematriculaciones != null && rematriculaciones.getRematriculacion() != null && !rematriculaciones.getRematriculacion().isEmpty()){
					vehiculoAtex5Dto.setListaRematriculacionesDto(conversor.transform(rematriculaciones.getRematriculacion(), DatosTramitesAtex5Dto.class,"tramiteReMatriculacionAtex5"));
					vehiculoAtex5Dto.setExistenDatosTramites(Boolean.TRUE);
				}
			}
			if(vehiculoAtex5.getDatosTramites().getListaTransferencias() != null){
				Transferencias transferencias = vehiculoAtex5.getDatosTramites().getListaTransferencias().getValue();
				if(transferencias != null && transferencias.getTransferencia() != null && !transferencias.getTransferencia().isEmpty()){
					vehiculoAtex5Dto.setListaTransferenciasDto(conversor.transform(transferencias.getTransferencia(), DatosTramitesAtex5Dto.class,"tramiteTransferenciaAtex5"));
					vehiculoAtex5Dto.setExistenDatosTramites(Boolean.TRUE);
				}
			}
		}
	}

	private void rellenarDatosResponsables(VehiculoAtex5Dto vehiculoAtex5Dto, Vehiculo vehiculoAtex5) {
		if(vehiculoAtex5.getDatosResponsables() != null){
			if(vehiculoAtex5.getDatosResponsables().getListaArrendatarios() != null){
				Arrendatarios arrendatarios = vehiculoAtex5.getDatosResponsables().getListaArrendatarios().getValue();
				if(arrendatarios != null && arrendatarios.getArrendatario() != null && !arrendatarios.getArrendatario().isEmpty()){
					List<DatosResponsableAtex5Dto> listaArrendatarios = new ArrayList<DatosResponsableAtex5Dto>();
					for(Arrendatario arrendatario :arrendatarios.getArrendatario()){
						DatosResponsableAtex5Dto datosArrendatario = conversor.transform(arrendatario, DatosResponsableAtex5Dto.class);
						if(arrendatario.getFechaFin() != null && !arrendatario.getFechaFin().isEmpty() && arrendatario.getFechaFin().get(0) != null 
							&& arrendatario.getFechaFin().get(0).getValue() != null && arrendatario.getFechaFin().get(0).getValue().get(0) != null){
							datosArrendatario.setFechaFin(arrendatario.getFechaFin().get(0).getValue().get(0).toGregorianCalendar().getTime());
						}
						listaArrendatarios.add(datosArrendatario);
					}
					vehiculoAtex5Dto.setListaArrendatariosDto(listaArrendatarios);
					vehiculoAtex5Dto.setExistenDatosResponsables(Boolean.TRUE);
				}
			}
			if(vehiculoAtex5.getDatosResponsables().getListaConductoresHabituales() != null){
				ConductoresHabituales conductoresHabituales = vehiculoAtex5.getDatosResponsables().getListaConductoresHabituales().getValue();
				if(conductoresHabituales != null && conductoresHabituales.getConductorHabitual() != null && !conductoresHabituales.getConductorHabitual().isEmpty()) {
					List<DatosResponsableAtex5Dto> listaConductorHabitual = new ArrayList<DatosResponsableAtex5Dto>();
					for(ConductorHabitual conductorHabitual : conductoresHabituales.getConductorHabitual()){
						DatosResponsableAtex5Dto datosConductorHabitual = conversor.transform(conductorHabitual, DatosResponsableAtex5Dto.class);
						if(conductorHabitual.getFechaFin() != null && !conductorHabitual.getFechaFin().isEmpty() && conductorHabitual.getFechaFin().get(0) != null 
							&& conductorHabitual.getFechaFin().get(0).getValue() != null && conductorHabitual.getFechaFin().get(0).getValue().get(0) != null){
							datosConductorHabitual.setFechaFin(conductorHabitual.getFechaFin().get(0).getValue().get(0).toGregorianCalendar().getTime());
						}
						listaConductorHabitual.add(datosConductorHabitual);
					}
					vehiculoAtex5Dto.setListaConductoresHabitualesDto(listaConductorHabitual);
					vehiculoAtex5Dto.setExistenDatosResponsables(Boolean.TRUE);
				}
			}
			if(vehiculoAtex5.getDatosResponsables().getListaPoseedores() != null){
				Poseedores poseedores = vehiculoAtex5.getDatosResponsables().getListaPoseedores().getValue();
				if(poseedores != null && poseedores.getPoseedor() != null && !poseedores.getPoseedor().isEmpty()){
					vehiculoAtex5Dto.setListaPoseedoresDto(conversor.transform(poseedores.getPoseedor(), DatosResponsableAtex5Dto.class));
					vehiculoAtex5Dto.setExistenDatosResponsables(Boolean.TRUE);
				}
			}
			if(vehiculoAtex5.getDatosResponsables().getListaTutores() != null){
				Tutores tutores = vehiculoAtex5.getDatosResponsables().getListaTutores().getValue();
				if(tutores != null && tutores.getTutor() != null && !tutores.getTutor().isEmpty()){
					vehiculoAtex5Dto.setListaTutoresDto(conversor.transform(tutores.getTutor(), DatosResponsableAtex5Dto.class));
					vehiculoAtex5Dto.setExistenDatosResponsables(Boolean.TRUE);
				}
			}
		}
	}

	private void rellenarDatosTitular(VehiculoAtex5Dto vehiculoAtex5Dto, Vehiculo vehiculoAtex5) {
		if(vehiculoAtex5.getDatosGenerales() != null && vehiculoAtex5.getDatosGenerales().getTitular() != null 
			&& vehiculoAtex5.getDatosGenerales().getTitular().getDatosPersona() != null){
			if(vehiculoAtex5.getDatosGenerales().getTitular().getDatosPersona().getPersonaFisica() != null 
				&& vehiculoAtex5.getDatosGenerales().getTitular().getDatosPersona().getPersonaFisica().getIdDocumento() != null 
				&& !vehiculoAtex5.getDatosGenerales().getTitular().getDatosPersona().getPersonaFisica().getIdDocumento().isEmpty()){
				vehiculoAtex5Dto.setPersonaFisicaDto(conversor.transform(vehiculoAtex5.getDatosGenerales().getTitular().getDatosPersona().getPersonaFisica(), DatosPersonaAtex5Dto.class));
				vehiculoAtex5Dto.getPersonaFisicaDto().setDomicilio(conversor.transform(vehiculoAtex5.getDatosGenerales().getTitular().getDomicilio(), DatosDomicilioAtex5Dto.class));
				vehiculoAtex5Dto.getPersonaFisicaDto().setDomicilioIne(conversor.transform(vehiculoAtex5.getDatosGenerales().getTitular().getDomicilioIne(), DatosDomicilioAtex5Dto.class));
			} else if(vehiculoAtex5.getDatosGenerales().getTitular().getDatosPersona().getPersonaJuridica() != null 
				&& vehiculoAtex5.getDatosGenerales().getTitular().getDatosPersona().getPersonaJuridica().getCif() != null 
				&& !vehiculoAtex5.getDatosGenerales().getTitular().getDatosPersona().getPersonaJuridica().getCif().isEmpty()){
				vehiculoAtex5Dto.setPersonaJuridicaDto(conversor.transform(vehiculoAtex5.getDatosGenerales().getTitular().getDatosPersona().getPersonaJuridica(), DatosPersonaAtex5Dto.class));
				vehiculoAtex5Dto.getPersonaJuridicaDto().setDomicilio(conversor.transform(vehiculoAtex5.getDatosGenerales().getTitular().getDomicilio(), DatosDomicilioAtex5Dto.class));
				vehiculoAtex5Dto.getPersonaJuridicaDto().setDomicilioIne(conversor.transform(vehiculoAtex5.getDatosGenerales().getTitular().getDomicilioIne(), DatosDomicilioAtex5Dto.class));
			}
		}
	}

	private void rellenarDatosCotitular(VehiculoAtex5Dto vehiculoAtex5Dto, Vehiculo vehiculoAtex5) {
		if(vehiculoAtex5.getDatosGenerales() != null
			&& vehiculoAtex5.getDatosGenerales().getListaCotitulares() != null){
			Cotitulares cotitulares = vehiculoAtex5.getDatosGenerales().getListaCotitulares().getValue();
			if(cotitulares != null && cotitulares.getCotitular() != null && !cotitulares.getCotitular().isEmpty()){
				List<DatosCotitularAtex5Dto> listaCotitulares = conversor.transform(cotitulares.getCotitular(), DatosCotitularAtex5Dto.class);
				vehiculoAtex5Dto.setListaCotitularesDto(listaCotitulares);
			}
		}
		
	}

}
