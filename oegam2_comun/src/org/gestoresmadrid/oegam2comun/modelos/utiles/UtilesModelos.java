package org.gestoresmadrid.oegam2comun.modelos.utiles;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados.TipoCuentaBancaria;
import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.modelos.model.enumerados.Decision;
import org.gestoresmadrid.core.modelos.model.enumerados.DerechoReal;
import org.gestoresmadrid.core.modelos.model.enumerados.DupleTriple;
import org.gestoresmadrid.core.modelos.model.enumerados.DuracionDerechoReal;
import org.gestoresmadrid.core.modelos.model.enumerados.DuracionRenta;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoModelos;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.core.modelos.model.enumerados.PeriodoRenta;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoBien;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.model.service.ServicioDatosBancariosFavoritos;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvinciaCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoViaCam;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.MunicipioCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ProvinciaCamDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaCamDto;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioConcepto;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioOficinaLiquidadora;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioTipoDocumentoEscr;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.OficinaLiquidadoraDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.TipoDocumentoEscrDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.utiles.enumerados.SexoPersona;

public class UtilesModelos implements Serializable {

	private static final long serialVersionUID = -1740208155222416012L;

	private static UtilesModelos utilesModelos;

	@Autowired
	private ServicioOficinaLiquidadora servicioOficinaLiquidadora;

	@Autowired
	private ServicioTipoDocumentoEscr servicioTipoDocumentoEscr;

	@Autowired
	private ServicioConcepto servicioConcepto;

	@Autowired
	private ServicioProvinciaCam servicioProvinciaCam;

	@Autowired
	private ServicioTipoViaCam servicioTipoViaCam;

	@Autowired
	private ServicioDatosBancariosFavoritos servicioDatosBancariosFavoritos;

	@Autowired
	private ServicioMunicipioCam servicioMunicipioCam;

	@Autowired
	private ServicioPueblo servicioPueblo;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public UtilesModelos() {
		super();
	}

	public static UtilesModelos getInstance() {
		if (utilesModelos == null) {
			utilesModelos = new UtilesModelos();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesModelos);
		}
		return utilesModelos;
	}

	public List<OficinaLiquidadoraDto> getListaOficinasLiquidadoras(Modelo600_601Dto modeloDto) {
		if (modeloDto == null || modeloDto.getOficinaLiquidadora() == null || modeloDto.getOficinaLiquidadora().getIdProvincia() == null || modeloDto.getOficinaLiquidadora().getIdProvincia()
				.isEmpty()) {
			return Collections.emptyList();
		}
		return servicioOficinaLiquidadora.getListaOficinasLiquidadoras(modeloDto.getOficinaLiquidadora().getIdProvincia());
	}

	public List<TipoDocumentoEscrDto> getTiposDocumentos(ModeloDto modelo) {
		return servicioTipoDocumentoEscr.getListaDocumentosPorModelo(modelo);
	}

	public DuracionRenta[] getDuracionRenta() {
		return DuracionRenta.values();
	}

	public PeriodoRenta[] getPeriodosRenta() {
		return PeriodoRenta.values();
	}

	public List<ConceptoDto> getListaConceptosPorModeloDto(ModeloDto modelo) {
		return servicioConcepto.getlistaConceptosPorModelo(modelo);
	}

	public List<ConceptoDto> getListaConceptosPorModeloYVersion(String modelo, String version) {
		return servicioConcepto.getlistaConceptosPorModelo(modelo, version);
	}

	public List<ProvinciaCamDto> getListaProvincias() {
		return servicioProvinciaCam.getListaProvinciasCam();
	}

	public String getProvinciaNombre(String idProvincia) {
		return servicioProvinciaCam.getProvinciaNombre(idProvincia);
	}

	public DupleTriple[] getDupleTri() {
		return DupleTriple.values();
	}

	public Decision[] getDecision() {
		return Decision.values();
	}

	public Estado[] getComboEstados() {
		return Estado.values();
	}

	public TipoPersona[] getComboTipoPersonas() {
		return TipoPersona.values();
	}

	public SexoPersona[] getComboSexo() {
		return SexoPersona.values();
	}

	public EstadoModelos[] getEstadoModelos() {
		return EstadoModelos.values();
	}

	public Modelo[] getModelos() {
		return Modelo.values();
	}

	public List<ConceptoDto> getListaConceptosPorModelo(String modelo, String version) {
		return servicioConcepto.getlistaConceptosPorModelo(modelo, version);
	}

	public Boolean esConceptoBienes(Modelo600_601Dto modeloDto) {
		if (modeloDto != null && modeloDto.getConcepto() != null) {
			return servicioConcepto.esConceptoBienes(modeloDto.getConcepto());
		}
		return null;
	}

	public TipoCuentaBancaria[] getTiposCuentasBancarias() {
		return TipoCuentaBancaria.values();
	}

	public Boolean esGuardable(Modelo600_601Dto modeloDto) {
		if (modeloDto != null) {
			if (modeloDto.getEstado() == null) {
				return true;
			} else if (!EstadoModelos.FinalizadoOK.getValorEnum().equals(modeloDto.getEstado()) && !esGeneradaRemesa(modeloDto) && !EstadoModelos.Pendiente_Presentacion.getValorEnum().equals(modeloDto
					.getEstado())) {
				return true;
			}
		}
		return false;
	}

	public Boolean esValidableModelos(Modelo600_601Dto modeloDto) {
		if (modeloDto != null && modeloDto.getEstado() != null) {
			if (EstadoModelos.Inicial.getValorEnum().equals(modeloDto.getEstado()) && !esGeneradaRemesa(modeloDto)) {
				return true;
			}
		}
		return false;
	}

	public Boolean esAutoLiquidable(Modelo600_601Dto modeloDto) {
		if (modeloDto != null && modeloDto.getEstado() != null) {
			if (EstadoModelos.Validado.getValorEnum().equals(modeloDto.getEstado()) && !esGeneradaRemesa(modeloDto)) {
				return true;
			}
		}
		return false;
	}

	public Boolean mostrarDatosBancarios(Modelo600_601Dto modeloDto) {
		if (modeloDto != null && modeloDto.getEstado() != null) {
			if (EstadoModelos.Autoliquidable.getValorEnum().equals(modeloDto.getEstado())) {
				return true;
			} else if (EstadoModelos.Pendiente_Presentacion.getValorEnum().equals(modeloDto.getEstado())) {
				return true;
			} else if (EstadoModelos.FinalizadoOK.getValorEnum().equals(modeloDto.getEstado()) && modeloDto.getDatosBancarios() != null) {
				return true;
			} else if (EstadoModelos.FinalizadoKO.getValorEnum().equals(modeloDto.getEstado()) && modeloDto.getDatosBancarios() != null) {
				return true;
			}
		}
		return false;
	}

	public Boolean esPresentable(Modelo600_601Dto modeloDto) {
		if (modeloDto != null && modeloDto.getEstado() != null) {
			if (EstadoModelos.Autoliquidable.getValorEnum().equals(modeloDto.getEstado())) {
				return true;
			}
		}
		return false;
	}

	public Boolean esGeneradaRemesa(Modelo600_601Dto modeloDto) {
		if (modeloDto != null && modeloDto.getEstado() != null) {
			if (modeloDto.getRemesa() != null && modeloDto.getRemesa().getIdRemesa() != null) {
				return true;
			}
		}
		return false;
	}

	public Boolean esFinalizadoOk(Modelo600_601Dto modeloDto) {
		if (modeloDto != null && modeloDto.getEstado() != null) {
			if (EstadoModelos.FinalizadoOK.getValorEnum().equals(modeloDto.getEstado())) {
				return true;
			}
		}
		return false;
	}

	public Boolean esPtePresentacion(Modelo600_601Dto modeloDto) {
		if (modeloDto != null && modeloDto.getEstado() != null) {
			if (EstadoModelos.Pendiente_Presentacion.getValorEnum().equals(modeloDto.getEstado())) {
				return true;
			}
		}
		return false;
	}

	public String convertirTextoLiquidaciones(String convertir) {
		if (convertir != null) {
			if (convertir.contains(".")) {
				convertir = convertir.replace(".", ",");
			}
			return convertir;
		}
		return "0,00";
	}

	public DerechoReal[] getListaDerechosReales() {
		return DerechoReal.values();
	}

	public DuracionDerechoReal[] getDuracionDerechoReal() {
		return DuracionDerechoReal.values();
	}

	public List<DatosBancariosFavoritosDto> getListaDatosBancariosFavoritos(Modelo600_601Dto modeloDto) {
		if (modeloDto != null && modeloDto.getContrato() != null && modeloDto.getContrato().getIdContrato() != null) {
			return servicioDatosBancariosFavoritos.getListaDatosBancariosFavoritosModeloPorContrato(modeloDto.getContrato().getIdContrato().longValue());
		}
		return Collections.emptyList();
	}

	public List<DatosBancariosFavoritosDto> getListaDatosBancariosFavoritosDesdeConsulta() {
		return servicioDatosBancariosFavoritos.getListaDatosBancariosFavoritosModeloPorContrato(utilesColegiado.getIdContratoSession());
	}

	public List<MunicipioCamDto> getListaMunicipiosPorProvincia(Modelo600_601Dto modeloDto, String tipo) {
		DireccionDto direccion = null;
		if (TipoBien.Urbano.getValorEnum().equals(tipo)) {
			if (modeloDto.getBienUrbano() != null) {
				direccion = modeloDto.getBienUrbano().getDireccion();
			}
		} else if (TipoBien.Rustico.getValorEnum().equals(tipo)) {
			if (modeloDto.getBienRustico() != null) {
				direccion = modeloDto.getBienRustico().getDireccion();
			}
		} else if (TipoBien.Otro.getValorEnum().equals(tipo)) {
			if (modeloDto.getOtroBien() != null) {
				direccion = modeloDto.getOtroBien().getDireccion();
			}
		} else if (TipoInterviniente.SujetoPasivo.getValorEnum().equals(tipo)) {
			if (modeloDto.getSujetoPasivo() != null) {
				direccion = modeloDto.getSujetoPasivo().getDireccion();
			}
		} else if (TipoInterviniente.Transmitente.getValorEnum().equals(tipo)) {
			if (modeloDto.getTransmitente() != null) {
				direccion = modeloDto.getTransmitente().getDireccion();
			}
		} else if (TipoInterviniente.Presentador.getValorEnum().equals(tipo)) {
			if (modeloDto.getPresentador() != null) {
				direccion = modeloDto.getPresentador().getDireccion();
			}
		}
		if (direccion != null && direccion.getIdProvincia() != null && !direccion.getIdProvincia().isEmpty()) {
			return servicioMunicipioCam.getListaMunicipiosPorProvincia(direccion.getIdProvincia());
		}
		return Collections.emptyList();
	}

	public List<PuebloDto> getListaPueblosPorMunicipioYProvincia(Modelo600_601Dto modeloDto, String tipo) {
		DireccionDto direccion = null;
		if (TipoBien.Urbano.getValorEnum().equals(tipo)) {
			if (modeloDto.getBienUrbano() != null) {
				direccion = modeloDto.getBienUrbano().getDireccion();
			}
		} else if (TipoBien.Rustico.getValorEnum().equals(tipo)) {
			if (modeloDto.getBienRustico() != null) {
				direccion = modeloDto.getBienRustico().getDireccion();
			}
		} else if (TipoInterviniente.SujetoPasivo.getValorEnum().equals(tipo)) {
			if (modeloDto.getSujetoPasivo() != null) {
				direccion = modeloDto.getSujetoPasivo().getDireccion();
			}
		} else if (TipoInterviniente.Transmitente.getValorEnum().equals(tipo)) {
			if (modeloDto.getTransmitente() != null) {
				direccion = modeloDto.getTransmitente().getDireccion();
			}
		} else if (TipoInterviniente.Presentador.getValorEnum().equals(tipo)) {
			if (modeloDto.getPresentador() != null) {
				direccion = modeloDto.getPresentador().getDireccion();
			}
		}
		if (direccion != null && direccion.getIdProvincia() != null && !direccion.getIdProvincia().isEmpty() && direccion.getIdMunicipio() != null && !direccion.getIdMunicipio().isEmpty()) {
			return servicioPueblo.listaPueblos(direccion.getIdProvincia(), direccion.getIdMunicipio());
		}
		return Collections.emptyList();
	}

	public List<TipoViaCamDto> getListaTiposVias(Modelo600_601Dto modeloDto, String tipo) {
		DireccionDto direccion = null;
		if (TipoBien.Urbano.getValorEnum().equals(tipo)) {
			if (modeloDto.getBienUrbano() != null) {
				direccion = modeloDto.getBienUrbano().getDireccion();
			}
		} else if (TipoBien.Rustico.getValorEnum().equals(tipo)) {
			if (modeloDto.getBienRustico() != null) {
				direccion = modeloDto.getBienRustico().getDireccion();
			}
		} else if (TipoInterviniente.SujetoPasivo.getValorEnum().equals(tipo)) {
			if (modeloDto.getSujetoPasivo() != null) {
				direccion = modeloDto.getSujetoPasivo().getDireccion();
			}
		} else if (TipoInterviniente.Transmitente.getValorEnum().equals(tipo)) {
			if (modeloDto.getTransmitente() != null) {
				direccion = modeloDto.getTransmitente().getDireccion();
			}
		} else if (TipoInterviniente.Presentador.getValorEnum().equals(tipo)) {
			if (modeloDto.getPresentador() != null) {
				direccion = modeloDto.getPresentador().getDireccion();
			}
		}
		if (direccion != null && direccion.getIdProvincia() != null && !direccion.getIdProvincia().isEmpty() && direccion.getIdMunicipio() != null && !direccion.getIdMunicipio().isEmpty()) {
			return servicioTipoViaCam.getListaTipoVias();
		}
		return Collections.emptyList();
	}

	public Boolean esTipoImpuestoExento(Modelo600_601Dto modeloDto) {
		if (modeloDto != null && modeloDto.getTipoImpuesto() != null) {
			if (modeloDto.getTipoImpuesto().getSujetoExento() != null && !modeloDto.getTipoImpuesto().getSujetoExento().isEmpty()) {
				if ("E".equals(modeloDto.getTipoImpuesto().getSujetoExento())) {
					return true;
				}
			}
		}
		return false;
	}

	public Boolean esTipoImpuestoNoSujeto(Modelo600_601Dto modeloDto) {
		if (modeloDto != null && modeloDto.getTipoImpuesto() != null) {
			if (modeloDto.getTipoImpuesto().getSujetoExento() != null && !modeloDto.getTipoImpuesto().getSujetoExento().isEmpty()) {
				if ("S".equals(modeloDto.getTipoImpuesto().getSujetoExento())) {
					return true;
				}
			}
		}
		return false;
	}

}
