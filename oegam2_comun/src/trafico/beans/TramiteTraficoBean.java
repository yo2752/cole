package trafico.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.EvolucionTramiteTraficoDto;

import escrituras.beans.Persona;
import trafico.utiles.enumerados.NPasos;
import trafico.utiles.enumerados.TipoMotivoExencion;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;

/**
 * 
 "NUM_EXPEDIENTE" NUMBER NOT NULL ENABLE, "TIPO_TRAMITE" VARCHAR2(18 BYTE) NOT
 * NULL ENABLE, "ID_CONTRATO" NUMBER(18,0) NOT NULL ENABLE, "ID_USUARIO"
 * NUMBER(18,0) NOT NULL ENABLE, "NUM_COLEGIADO" VARCHAR2(4 BYTE) NOT NULL
 * ENABLE, "ID_VEHICULO" NUMBER, "CODIGO_TASA" VARCHAR2(12 BYTE), "ESTADO"
 * NUMBER NOT NULL ENABLE, "REF_PROPIA" VARCHAR2(50 BYTE), "FECHA_ALTA" DATE NOT
 * NULL ENABLE, "FECHA_PRESENTACION" DATE, "FECHA_ULT_MODIF" DATE NOT NULL
 * ENABLE, "FECHA_IMPRESION" DATE, "JEFATURA_PROVINCIAL" VARCHAR2(3 BYTE),
 * "ANOTACIONES" NVARCHAR2(500), "RENTING" VARCHAR2(2 BYTE), "CAMBIO_DOMICILIO"
 * VARCHAR2(2 BYTE), "IEDTM" VARCHAR2(2 BYTE), "MODELO_IEDTM" VARCHAR2(15 BYTE),
 * "FECHA_IEDTM" DATE, "N_REG_IEDTM" VARCHAR2(10 BYTE), "FINANCIERA_IEDTM"
 * VARCHAR2(30 BYTE), "EXENTO_IEDTM" VARCHAR2(2 BYTE), "NO_SUJECION_IEDTM"
 * VARCHAR2(2 BYTE), "CEM" VARCHAR2(8 BYTE), "RESPUESTA" VARCHAR2(300 BYTE),
 * CONSTRAINT "TRAMITE_TRAFICO_PK" PRIMARY KEY ("NUM_EXPEDIENTE") USING INDEX
 * PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS STORAGE(INITIAL 65536
 * NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645 PCTINCREASE 0 FREELISTS 1
 * FREELIST GROUPS 1 BUFFER_POOL DEFAULT) TABLESPACE "OEGAM_TB" ENABLE,
 * CONSTRAINT "TRAMTRAF_TIPO_TRA_FK" FOREIGN KEY ("TIPO_TRAMITE") REFERENCES
 * "OEGAM2"."TIPO_TRAMITE" ("TIPO_TRAMITE") ENABLE, CONSTRAINT
 * "TRATRAFICO_CONTRATO_FK" FOREIGN KEY ("ID_CONTRATO") REFERENCES
 * "OEGAM2"."CONTRATO" ("ID_CONTRATO") ENABLE, CONSTRAINT "TRATRAFICO_JEFAT_FK"
 * FOREIGN KEY ("JEFATURA_PROVINCIAL") REFERENCES "OEGAM2"."JEFATURA_TRAFICO"
 * ("JEFATURA_PROVINCIAL") ENABLE, CONSTRAINT "TRATRAFICO_TASA_FK" FOREIGN KEY
 * ("CODIGO_TASA") REFERENCES "OEGAM2"."TASA" ("CODIGO_TASA") ENABLE, CONSTRAINT
 * "TRATRAFICO_USUARIO_FK" FOREIGN KEY ("ID_USUARIO") REFERENCES
 * "OEGAM2"."USUARIO" ("ID_USUARIO") ENABLE, CONSTRAINT "TRATRAFICO_VEHI_FK"
 * FOREIGN KEY ("NUM_COLEGIADO", "ID_VEHICULO") REFERENCES "OEGAM2"."VEHICULO"
 * ("NUM_COLEGIADO", "ID_VEHICULO")
 * 
 * 
 */

public class TramiteTraficoBean {

	// Tabla TRAMITE_TRAFICO

	private BigDecimal numExpediente;
	// Bean Tipo Tramite
	private TipoTramiteTrafico tipoTramite;
	private BigDecimal idContrato;
	private String cif;
	private String razonSocial;
	private BigDecimal idUsuario;
	private String numColegiado;

	// Bean Vehiculo
	private VehiculoBean vehiculo;
	// Bean Tasa
	private Tasa tasa;
	// Enumerado Estado
	private EstadoTramiteTrafico estado;

	private String referenciaPropia;
	private Fecha fechaCreacion;
	private Fecha fechaPresentacion;
	private Fecha fechaUltModif;
	private Fecha fechaImpresion;

	// Bean Jefatura Provincial
	private JefaturaTrafico jefaturaTrafico;

	private String anotaciones;
	private String renting;
	private String carsharing;
	private String cambioDomicilio;

	private String iedtm;
	private String modeloIedtm;
	private Fecha fechaIedtm;
	private String exentoIedtm;
	private String noSujetoIedtm;
	private TipoMotivoExencion numRegIedtm;
	private String financieraIedtm;

	private String cemIedtm;
	private String respuesta;
	private String exentoCem;
	private String cema;

	private TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean;

	private NPasos nPasos;

	private Persona nombreTitular;
	private BigDecimal idTipoCreacion;
	private Fecha fechaFactura;
	private Fecha fechaContrato;

	private EvolucionTramiteTraficoDto evolucionTramiteTraficoDto;

	public TramiteTraficoBean() {
	}

	public TramiteTraficoBean(boolean inicio) {
		this();
		fechaCreacion = new Fecha();
		fechaPresentacion = new Fecha();
		fechaUltModif = new Fecha();
		fechaImpresion = new Fecha();
		fechaIedtm = new Fecha();
		vehiculo = new VehiculoBean(true);
		tasa = new Tasa(true);
		jefaturaTrafico = new JefaturaTrafico(true);
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public void setPasos(String pasos) {
		this.nPasos= NPasos.convertir(pasos);
	}

	public NPasos getnPasos() {
		return nPasos;
	}

	public TipoTramiteTrafico getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = TipoTramiteTrafico.convertir(tipoTramite);
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public VehiculoBean getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoBean vehiculo) {
		this.vehiculo = vehiculo;
	}

	public EstadoTramiteTrafico getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = EstadoTramiteTrafico.convertir(estado);
	}

	public String getReferenciaPropia() {
		return referenciaPropia;
	}

	public void setReferenciaPropia(String referenciaPropia) {
		this.referenciaPropia = referenciaPropia;
	}

	public Fecha getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Fecha fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Fecha getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Fecha fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public Fecha getFechaUltModif() {
		return fechaUltModif;
	}

	public void setFechaUltModif(Fecha fechaUltModif) {
		this.fechaUltModif = fechaUltModif;
	}

	public Fecha getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(Fecha fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public JefaturaTrafico getJefaturaTrafico() {
		return jefaturaTrafico;
	}

	public void setJefaturaTrafico(JefaturaTrafico jefaturaTrafico) {
		this.jefaturaTrafico = jefaturaTrafico;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public String getRenting() {
		return renting;
	}

	public void setRenting(String renting) {
		this.renting = renting;
	}

	public String getCambioDomicilio() {
		return cambioDomicilio;
	}

	public void setCambioDomicilio(String cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}

	public String getIedtm() {
		return iedtm;
	}

	public void setIedtm(String iedtm) {
		this.iedtm = iedtm;
	}

	public String getModeloIedtm() {
		return modeloIedtm;
	}

	public void setModeloIedtm(String modeloIedtm) {
		this.modeloIedtm = modeloIedtm;
	}

	public Fecha getFechaIedtm() {
		return fechaIedtm;
	}

	public void setFechaIedtm(Fecha fechaIedtm) {
		this.fechaIedtm = fechaIedtm;
	}

	public String getExentoIedtm() {
		return exentoIedtm;
	}

	public void setExentoIedtm(String exentoIedtm) {
		this.exentoIedtm = exentoIedtm;
	}

	public String getNoSujetoIedtm() {
		return noSujetoIedtm;
	}

	public void setNoSujetoIedtm(String noSujetoIedtm) {
		this.noSujetoIedtm = noSujetoIedtm;
	}

	public TipoMotivoExencion getNumRegIedtm() {
		return numRegIedtm;
	}

	public void setNumRegIedtm(String numRegIedtm) {
		this.numRegIedtm = TipoMotivoExencion.convertir(numRegIedtm);
	}

	public String getFinancieraIedtm() {
		return financieraIedtm;
	}

	public void setFinancieraIedtm(String financieraIedtm) {
		this.financieraIedtm = financieraIedtm;
	}

	public String getCemIedtm() {
		return cemIedtm;
	}

	public void setCemIedtm(String cemIedtm) {
		if(cemIedtm != null){
			this.cemIedtm = cemIedtm.toUpperCase();
		}
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getExentoCem() {
		return exentoCem;
	}

	public void setExentoCem(String exentoCem) {
		this.exentoCem = exentoCem;
	}

	public String getCema() {
		return cema;
	}

	public void setCema(String cema) {
		this.cema = cema;
	}

	public Tasa getTasa() {
		return tasa;
	}

	public void setTasa(Tasa tasa) {
		this.tasa = tasa;
	}

	public Persona getNombreTitular() {
		return nombreTitular;
	}

	public void setNombreTitular(Persona nombreTitular) {
		this.nombreTitular = nombreTitular;
	}

	public BigDecimal getIdTipoCreacion() {
		return idTipoCreacion;
	}

	public void setIdTipoCreacion(BigDecimal idTipoCreacion) {
		this.idTipoCreacion = idTipoCreacion;
	}

	public Fecha getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Fecha fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Fecha getFechaContrato() {
		return fechaContrato;
	}

	public void setFechaContrato(Fecha fechaContrato) {
		this.fechaContrato = fechaContrato;
	}

	public TramiteTraficoTransmisionBean getTramiteTraficoTransmisionBean() {
		return tramiteTraficoTransmisionBean;
	}

	public void setTramiteTraficoTransmisionBean(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) {
		this.tramiteTraficoTransmisionBean = tramiteTraficoTransmisionBean;
	}

	public String getCarsharing() {
		return carsharing;
	}

	public void setCarsharing(String carsharing) {
		this.carsharing = carsharing;
	}

	public EvolucionTramiteTraficoDto getEvolucionTramiteTraficoDto() {
		return evolucionTramiteTraficoDto;
	}

	public void setEvolucionTramiteTraficoDto(EvolucionTramiteTraficoDto evolucionTramiteTraficoDto) {
		this.evolucionTramiteTraficoDto = evolucionTramiteTraficoDto;
	}

}