package trafico.beans;

import java.math.BigDecimal;


public class DgtCodigoItvBean { 
      private String codigoItv; 
      private String bastidor; 
      private String tipoVehiculoIndustria; 
      private String tipoVehiculoTrafico; 
      private String marca; 
      private String modelo; 
      private String tara; 
      private String mma; 
      private String plazas; 
      private String carburante; 
      private String cilindrada; 
      private BigDecimal potenciaFiscal; 
      private String potenciaReal; 
      private String co2; 

    /**
     * Constructor vacio del Bean
     */
    public DgtCodigoItvBean () {
        init();
    } 
  
    /** 
     * Inicializacion de variables (Si las hay)
     */
    protected void init() {
      // Valores iniciales
    }
  
    /**
     * Método Setter para el campo CODIGO_ITV
     * @param codigoItv
     */
    public void  setCodigoItv ( String codigoItv ) {
        this.codigoItv = codigoItv;
    }
  
    /**
     * Método Getter para el campo CODIGO_ITV
     * @return el valor
     */ 
    public String getCodigoItv() {
        return codigoItv;
    }
  
    /**
     * Método Setter para el campo BASTIDOR
     * @param bastidor
     */
    public void  setBastidor ( String bastidor ) {
        this.bastidor = bastidor;
    }
  
    /**
     * Método Getter para el campo BASTIDOR
     * @return el valor
     */ 
    public String getBastidor() {
        return bastidor;
    }
  
    /**
     * Método Setter para el campo TIPO_VEHICULO_INDUSTRIA
     * @param tipoVehiculoIndustria
     */
    public void  setTipoVehiculoIndustria ( String tipoVehiculoIndustria ) {
        this.tipoVehiculoIndustria = tipoVehiculoIndustria;
    }
  
    /**
     * Método Getter para el campo TIPO_VEHICULO_INDUSTRIA
     * @return el valor
     */ 
    public String getTipoVehiculoIndustria() {
        return tipoVehiculoIndustria;
    }
  
    /**
     * Método Setter para el campo TIPO_VEHICULO_TRAFICO
     * @param tipoVehiculoTrafico
     */
    public void  setTipoVehiculoTrafico ( String tipoVehiculoTrafico ) {
        this.tipoVehiculoTrafico = tipoVehiculoTrafico;
    }
  
    /**
     * Método Getter para el campo TIPO_VEHICULO_TRAFICO
     * @return el valor
     */ 
    public String getTipoVehiculoTrafico() {
        return tipoVehiculoTrafico;
    }
  
    /**
     * Método Setter para el campo MARCA
     * @param marca
     */
    public void  setMarca ( String marca ) {
        this.marca = marca;
    }
  
    /**
     * Método Getter para el campo MARCA
     * @return el valor
     */ 
    public String getMarca() {
        return marca;
    }
  
    /**
     * Método Setter para el campo MODELO
     * @param modelo
     */
    public void  setModelo ( String modelo ) {
        this.modelo = modelo;
    }
  
    /**
     * Método Getter para el campo MODELO
     * @return el valor
     */ 
    public String getModelo() {
        return modelo;
    }
  
    /**
     * Método Setter para el campo TARA
     * @param tara
     */
    public void  setTara ( String tara ) {
        this.tara = tara;
    }
  
    /**
     * Método Getter para el campo TARA
     * @return el valor
     */ 
    public String getTara() {
        return tara;
    }
  
    /**
     * Método Setter para el campo MMA
     * @param mma
     */
    public void  setMma ( String mma ) {
        this.mma = mma;
    }
  
    /**
     * Método Getter para el campo MMA
     * @return el valor
     */ 
    public String getMma() {
        return mma;
    }
  
    /**
     * Método Setter para el campo PLAZAS
     * @param plazas
     */
    public void  setPlazas ( String plazas ) {
        this.plazas = plazas;
    }
  
    /**
     * Método Getter para el campo PLAZAS
     * @return el valor
     */ 
    public String getPlazas() {
        return plazas;
    }
  
    /**
     * Método Setter para el campo CARBURANTE
     * @param carburante
     */
    public void  setCarburante ( String carburante ) {
        this.carburante = carburante;
    }
  
    /**
     * Método Getter para el campo CARBURANTE
     * @return el valor
     */ 
    public String getCarburante() {
        return carburante;
    }
  
    /**
     * Método Setter para el campo CILINDRADA
     * @param cilindrada
     */
    public void  setCilindrada ( String cilindrada ) {
        this.cilindrada = cilindrada;
    }
  
    /**
     * Método Getter para el campo CILINDRADA
     * @return el valor
     */ 
    public String getCilindrada() {
        return cilindrada;
    }
  
    /**
     * Método Setter para el campo POTENCIA_FISCAL
     * @param potenciaFiscal
     */
    public void  setPotenciaFiscal ( BigDecimal potenciaFiscal ) {
        this.potenciaFiscal = potenciaFiscal;
    }
  
    /**
     * Método Getter para el campo POTENCIA_FISCAL
     * @return el valor
     */ 
    public BigDecimal getPotenciaFiscal() {
        return potenciaFiscal;
    }
  
    /**
     * Método Setter para el campo POTENCIA_REAL
     * @param potenciaReal
     */
    public void  setPotenciaReal ( String potenciaReal ) {
        this.potenciaReal = potenciaReal;
    }
  
    /**
     * Método Getter para el campo POTENCIA_REAL
     * @return el valor
     */ 
    public String getPotenciaReal() {
        return potenciaReal;
    }
  
    /**
     * Método Setter para el campo CO2
     * @param co2
     */
    public void  setCo2 ( String co2 ) {
        this.co2 = co2;
    }
  
    /**
     * Método Getter para el campo CO2
     * @return el valor
     */ 
    public String getCo2() {
        return co2;
    }

}
