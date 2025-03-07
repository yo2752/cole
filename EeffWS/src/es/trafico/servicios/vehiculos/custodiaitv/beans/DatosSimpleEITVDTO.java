/**
 * DatosSimpleEITVDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.trafico.servicios.vehiculos.custodiaitv.beans;

public class DatosSimpleEITVDTO  implements java.io.Serializable {
    private es.trafico.servicios.vehiculos.custodiaitv.beans.BaseEITVDTO baseeitvdto;

    private java.lang.String concesionarioClienteComercial;

    private java.lang.String custodioActual;

    private java.lang.String custodioSiguiente;

    private java.lang.String denominacionEstadoFinanciero;

    private java.lang.String dninifNieClienteFinal;

    private java.lang.String estadoBastidor;

    private java.lang.String fechaFacturaFinal;

    private java.lang.String fechaFacturaMarca;

    private java.lang.String firCif;

    private java.lang.String FIRMarca;

    private java.lang.String importeFacturaFinal;

    private java.lang.String importeFacturaMarca;

    private java.lang.String motivo;

    private java.lang.String numeroFacturaFinal;

    private java.lang.String numeroFacturaMarca;

    private java.lang.String custodioAnterior;

    private java.lang.String custodioFinal;

    private java.lang.String entidadCredito;

    private es.trafico.servicios.vehiculos.custodiaitv.beans.DatosHistoricoEITVDTO datosHistoricosITV;

    private java.lang.String[] nombreApellidosClienteFinal;

    private java.lang.String[] direccionCliente;

    public DatosSimpleEITVDTO() {
    }

    public DatosSimpleEITVDTO(
           es.trafico.servicios.vehiculos.custodiaitv.beans.BaseEITVDTO baseeitvdto,
           java.lang.String concesionarioClienteComercial,
           java.lang.String custodioActual,
           java.lang.String custodioSiguiente,
           java.lang.String denominacionEstadoFinanciero,
           java.lang.String dninifNieClienteFinal,
           java.lang.String estadoBastidor,
           java.lang.String fechaFacturaFinal,
           java.lang.String fechaFacturaMarca,
           java.lang.String firCif,
           java.lang.String FIRMarca,
           java.lang.String importeFacturaFinal,
           java.lang.String importeFacturaMarca,
           java.lang.String motivo,
           java.lang.String numeroFacturaFinal,
           java.lang.String numeroFacturaMarca,
           java.lang.String custodioAnterior,
           java.lang.String custodioFinal,
           java.lang.String entidadCredito,
           es.trafico.servicios.vehiculos.custodiaitv.beans.DatosHistoricoEITVDTO datosHistoricosITV,
           java.lang.String[] nombreApellidosClienteFinal,
           java.lang.String[] direccionCliente) {
           this.baseeitvdto = baseeitvdto;
           this.concesionarioClienteComercial = concesionarioClienteComercial;
           this.custodioActual = custodioActual;
           this.custodioSiguiente = custodioSiguiente;
           this.denominacionEstadoFinanciero = denominacionEstadoFinanciero;
           this.dninifNieClienteFinal = dninifNieClienteFinal;
           this.estadoBastidor = estadoBastidor;
           this.fechaFacturaFinal = fechaFacturaFinal;
           this.fechaFacturaMarca = fechaFacturaMarca;
           this.firCif = firCif;
           this.FIRMarca = FIRMarca;
           this.importeFacturaFinal = importeFacturaFinal;
           this.importeFacturaMarca = importeFacturaMarca;
           this.motivo = motivo;
           this.numeroFacturaFinal = numeroFacturaFinal;
           this.numeroFacturaMarca = numeroFacturaMarca;
           this.custodioAnterior = custodioAnterior;
           this.custodioFinal = custodioFinal;
           this.entidadCredito = entidadCredito;
           this.datosHistoricosITV = datosHistoricosITV;
           this.nombreApellidosClienteFinal = nombreApellidosClienteFinal;
           this.direccionCliente = direccionCliente;
    }


    /**
     * Gets the baseeitvdto value for this DatosSimpleEITVDTO.
     * 
     * @return baseeitvdto
     */
    public es.trafico.servicios.vehiculos.custodiaitv.beans.BaseEITVDTO getBaseeitvdto() {
        return baseeitvdto;
    }


    /**
     * Sets the baseeitvdto value for this DatosSimpleEITVDTO.
     * 
     * @param baseeitvdto
     */
    public void setBaseeitvdto(es.trafico.servicios.vehiculos.custodiaitv.beans.BaseEITVDTO baseeitvdto) {
        this.baseeitvdto = baseeitvdto;
    }


    /**
     * Gets the concesionarioClienteComercial value for this DatosSimpleEITVDTO.
     * 
     * @return concesionarioClienteComercial
     */
    public java.lang.String getConcesionarioClienteComercial() {
        return concesionarioClienteComercial;
    }


    /**
     * Sets the concesionarioClienteComercial value for this DatosSimpleEITVDTO.
     * 
     * @param concesionarioClienteComercial
     */
    public void setConcesionarioClienteComercial(java.lang.String concesionarioClienteComercial) {
        this.concesionarioClienteComercial = concesionarioClienteComercial;
    }


    /**
     * Gets the custodioActual value for this DatosSimpleEITVDTO.
     * 
     * @return custodioActual
     */
    public java.lang.String getCustodioActual() {
        return custodioActual;
    }


    /**
     * Sets the custodioActual value for this DatosSimpleEITVDTO.
     * 
     * @param custodioActual
     */
    public void setCustodioActual(java.lang.String custodioActual) {
        this.custodioActual = custodioActual;
    }


    /**
     * Gets the custodioSiguiente value for this DatosSimpleEITVDTO.
     * 
     * @return custodioSiguiente
     */
    public java.lang.String getCustodioSiguiente() {
        return custodioSiguiente;
    }


    /**
     * Sets the custodioSiguiente value for this DatosSimpleEITVDTO.
     * 
     * @param custodioSiguiente
     */
    public void setCustodioSiguiente(java.lang.String custodioSiguiente) {
        this.custodioSiguiente = custodioSiguiente;
    }


    /**
     * Gets the denominacionEstadoFinanciero value for this DatosSimpleEITVDTO.
     * 
     * @return denominacionEstadoFinanciero
     */
    public java.lang.String getDenominacionEstadoFinanciero() {
        return denominacionEstadoFinanciero;
    }


    /**
     * Sets the denominacionEstadoFinanciero value for this DatosSimpleEITVDTO.
     * 
     * @param denominacionEstadoFinanciero
     */
    public void setDenominacionEstadoFinanciero(java.lang.String denominacionEstadoFinanciero) {
        this.denominacionEstadoFinanciero = denominacionEstadoFinanciero;
    }


    /**
     * Gets the dninifNieClienteFinal value for this DatosSimpleEITVDTO.
     * 
     * @return dninifNieClienteFinal
     */
    public java.lang.String getDninifNieClienteFinal() {
        return dninifNieClienteFinal;
    }


    /**
     * Sets the dninifNieClienteFinal value for this DatosSimpleEITVDTO.
     * 
     * @param dninifNieClienteFinal
     */
    public void setDninifNieClienteFinal(java.lang.String dninifNieClienteFinal) {
        this.dninifNieClienteFinal = dninifNieClienteFinal;
    }


    /**
     * Gets the estadoBastidor value for this DatosSimpleEITVDTO.
     * 
     * @return estadoBastidor
     */
    public java.lang.String getEstadoBastidor() {
        return estadoBastidor;
    }


    /**
     * Sets the estadoBastidor value for this DatosSimpleEITVDTO.
     * 
     * @param estadoBastidor
     */
    public void setEstadoBastidor(java.lang.String estadoBastidor) {
        this.estadoBastidor = estadoBastidor;
    }


    /**
     * Gets the fechaFacturaFinal value for this DatosSimpleEITVDTO.
     * 
     * @return fechaFacturaFinal
     */
    public java.lang.String getFechaFacturaFinal() {
        return fechaFacturaFinal;
    }


    /**
     * Sets the fechaFacturaFinal value for this DatosSimpleEITVDTO.
     * 
     * @param fechaFacturaFinal
     */
    public void setFechaFacturaFinal(java.lang.String fechaFacturaFinal) {
        this.fechaFacturaFinal = fechaFacturaFinal;
    }


    /**
     * Gets the fechaFacturaMarca value for this DatosSimpleEITVDTO.
     * 
     * @return fechaFacturaMarca
     */
    public java.lang.String getFechaFacturaMarca() {
        return fechaFacturaMarca;
    }


    /**
     * Sets the fechaFacturaMarca value for this DatosSimpleEITVDTO.
     * 
     * @param fechaFacturaMarca
     */
    public void setFechaFacturaMarca(java.lang.String fechaFacturaMarca) {
        this.fechaFacturaMarca = fechaFacturaMarca;
    }


    /**
     * Gets the firCif value for this DatosSimpleEITVDTO.
     * 
     * @return firCif
     */
    public java.lang.String getFirCif() {
        return firCif;
    }


    /**
     * Sets the firCif value for this DatosSimpleEITVDTO.
     * 
     * @param firCif
     */
    public void setFirCif(java.lang.String firCif) {
        this.firCif = firCif;
    }


    /**
     * Gets the FIRMarca value for this DatosSimpleEITVDTO.
     * 
     * @return FIRMarca
     */
    public java.lang.String getFIRMarca() {
        return FIRMarca;
    }


    /**
     * Sets the FIRMarca value for this DatosSimpleEITVDTO.
     * 
     * @param FIRMarca
     */
    public void setFIRMarca(java.lang.String FIRMarca) {
        this.FIRMarca = FIRMarca;
    }


    /**
     * Gets the importeFacturaFinal value for this DatosSimpleEITVDTO.
     * 
     * @return importeFacturaFinal
     */
    public java.lang.String getImporteFacturaFinal() {
        return importeFacturaFinal;
    }


    /**
     * Sets the importeFacturaFinal value for this DatosSimpleEITVDTO.
     * 
     * @param importeFacturaFinal
     */
    public void setImporteFacturaFinal(java.lang.String importeFacturaFinal) {
        this.importeFacturaFinal = importeFacturaFinal;
    }


    /**
     * Gets the importeFacturaMarca value for this DatosSimpleEITVDTO.
     * 
     * @return importeFacturaMarca
     */
    public java.lang.String getImporteFacturaMarca() {
        return importeFacturaMarca;
    }


    /**
     * Sets the importeFacturaMarca value for this DatosSimpleEITVDTO.
     * 
     * @param importeFacturaMarca
     */
    public void setImporteFacturaMarca(java.lang.String importeFacturaMarca) {
        this.importeFacturaMarca = importeFacturaMarca;
    }


    /**
     * Gets the motivo value for this DatosSimpleEITVDTO.
     * 
     * @return motivo
     */
    public java.lang.String getMotivo() {
        return motivo;
    }


    /**
     * Sets the motivo value for this DatosSimpleEITVDTO.
     * 
     * @param motivo
     */
    public void setMotivo(java.lang.String motivo) {
        this.motivo = motivo;
    }


    /**
     * Gets the numeroFacturaFinal value for this DatosSimpleEITVDTO.
     * 
     * @return numeroFacturaFinal
     */
    public java.lang.String getNumeroFacturaFinal() {
        return numeroFacturaFinal;
    }


    /**
     * Sets the numeroFacturaFinal value for this DatosSimpleEITVDTO.
     * 
     * @param numeroFacturaFinal
     */
    public void setNumeroFacturaFinal(java.lang.String numeroFacturaFinal) {
        this.numeroFacturaFinal = numeroFacturaFinal;
    }


    /**
     * Gets the numeroFacturaMarca value for this DatosSimpleEITVDTO.
     * 
     * @return numeroFacturaMarca
     */
    public java.lang.String getNumeroFacturaMarca() {
        return numeroFacturaMarca;
    }


    /**
     * Sets the numeroFacturaMarca value for this DatosSimpleEITVDTO.
     * 
     * @param numeroFacturaMarca
     */
    public void setNumeroFacturaMarca(java.lang.String numeroFacturaMarca) {
        this.numeroFacturaMarca = numeroFacturaMarca;
    }


    /**
     * Gets the custodioAnterior value for this DatosSimpleEITVDTO.
     * 
     * @return custodioAnterior
     */
    public java.lang.String getCustodioAnterior() {
        return custodioAnterior;
    }


    /**
     * Sets the custodioAnterior value for this DatosSimpleEITVDTO.
     * 
     * @param custodioAnterior
     */
    public void setCustodioAnterior(java.lang.String custodioAnterior) {
        this.custodioAnterior = custodioAnterior;
    }


    /**
     * Gets the custodioFinal value for this DatosSimpleEITVDTO.
     * 
     * @return custodioFinal
     */
    public java.lang.String getCustodioFinal() {
        return custodioFinal;
    }


    /**
     * Sets the custodioFinal value for this DatosSimpleEITVDTO.
     * 
     * @param custodioFinal
     */
    public void setCustodioFinal(java.lang.String custodioFinal) {
        this.custodioFinal = custodioFinal;
    }


    /**
     * Gets the entidadCredito value for this DatosSimpleEITVDTO.
     * 
     * @return entidadCredito
     */
    public java.lang.String getEntidadCredito() {
        return entidadCredito;
    }


    /**
     * Sets the entidadCredito value for this DatosSimpleEITVDTO.
     * 
     * @param entidadCredito
     */
    public void setEntidadCredito(java.lang.String entidadCredito) {
        this.entidadCredito = entidadCredito;
    }


    /**
     * Gets the datosHistoricosITV value for this DatosSimpleEITVDTO.
     * 
     * @return datosHistoricosITV
     */
    public es.trafico.servicios.vehiculos.custodiaitv.beans.DatosHistoricoEITVDTO getDatosHistoricosITV() {
        return datosHistoricosITV;
    }


    /**
     * Sets the datosHistoricosITV value for this DatosSimpleEITVDTO.
     * 
     * @param datosHistoricosITV
     */
    public void setDatosHistoricosITV(es.trafico.servicios.vehiculos.custodiaitv.beans.DatosHistoricoEITVDTO datosHistoricosITV) {
        this.datosHistoricosITV = datosHistoricosITV;
    }


    /**
     * Gets the nombreApellidosClienteFinal value for this DatosSimpleEITVDTO.
     * 
     * @return nombreApellidosClienteFinal
     */
    public java.lang.String[] getNombreApellidosClienteFinal() {
        return nombreApellidosClienteFinal;
    }


    /**
     * Sets the nombreApellidosClienteFinal value for this DatosSimpleEITVDTO.
     * 
     * @param nombreApellidosClienteFinal
     */
    public void setNombreApellidosClienteFinal(java.lang.String[] nombreApellidosClienteFinal) {
        this.nombreApellidosClienteFinal = nombreApellidosClienteFinal;
    }


    /**
     * Gets the direccionCliente value for this DatosSimpleEITVDTO.
     * 
     * @return direccionCliente
     */
    public java.lang.String[] getDireccionCliente() {
        return direccionCliente;
    }


    /**
     * Sets the direccionCliente value for this DatosSimpleEITVDTO.
     * 
     * @param direccionCliente
     */
    public void setDireccionCliente(java.lang.String[] direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatosSimpleEITVDTO)) return false;
        DatosSimpleEITVDTO other = (DatosSimpleEITVDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.baseeitvdto==null && other.getBaseeitvdto()==null) || 
             (this.baseeitvdto!=null &&
              this.baseeitvdto.equals(other.getBaseeitvdto()))) &&
            ((this.concesionarioClienteComercial==null && other.getConcesionarioClienteComercial()==null) || 
             (this.concesionarioClienteComercial!=null &&
              this.concesionarioClienteComercial.equals(other.getConcesionarioClienteComercial()))) &&
            ((this.custodioActual==null && other.getCustodioActual()==null) || 
             (this.custodioActual!=null &&
              this.custodioActual.equals(other.getCustodioActual()))) &&
            ((this.custodioSiguiente==null && other.getCustodioSiguiente()==null) || 
             (this.custodioSiguiente!=null &&
              this.custodioSiguiente.equals(other.getCustodioSiguiente()))) &&
            ((this.denominacionEstadoFinanciero==null && other.getDenominacionEstadoFinanciero()==null) || 
             (this.denominacionEstadoFinanciero!=null &&
              this.denominacionEstadoFinanciero.equals(other.getDenominacionEstadoFinanciero()))) &&
            ((this.dninifNieClienteFinal==null && other.getDninifNieClienteFinal()==null) || 
             (this.dninifNieClienteFinal!=null &&
              this.dninifNieClienteFinal.equals(other.getDninifNieClienteFinal()))) &&
            ((this.estadoBastidor==null && other.getEstadoBastidor()==null) || 
             (this.estadoBastidor!=null &&
              this.estadoBastidor.equals(other.getEstadoBastidor()))) &&
            ((this.fechaFacturaFinal==null && other.getFechaFacturaFinal()==null) || 
             (this.fechaFacturaFinal!=null &&
              this.fechaFacturaFinal.equals(other.getFechaFacturaFinal()))) &&
            ((this.fechaFacturaMarca==null && other.getFechaFacturaMarca()==null) || 
             (this.fechaFacturaMarca!=null &&
              this.fechaFacturaMarca.equals(other.getFechaFacturaMarca()))) &&
            ((this.firCif==null && other.getFirCif()==null) || 
             (this.firCif!=null &&
              this.firCif.equals(other.getFirCif()))) &&
            ((this.FIRMarca==null && other.getFIRMarca()==null) || 
             (this.FIRMarca!=null &&
              this.FIRMarca.equals(other.getFIRMarca()))) &&
            ((this.importeFacturaFinal==null && other.getImporteFacturaFinal()==null) || 
             (this.importeFacturaFinal!=null &&
              this.importeFacturaFinal.equals(other.getImporteFacturaFinal()))) &&
            ((this.importeFacturaMarca==null && other.getImporteFacturaMarca()==null) || 
             (this.importeFacturaMarca!=null &&
              this.importeFacturaMarca.equals(other.getImporteFacturaMarca()))) &&
            ((this.motivo==null && other.getMotivo()==null) || 
             (this.motivo!=null &&
              this.motivo.equals(other.getMotivo()))) &&
            ((this.numeroFacturaFinal==null && other.getNumeroFacturaFinal()==null) || 
             (this.numeroFacturaFinal!=null &&
              this.numeroFacturaFinal.equals(other.getNumeroFacturaFinal()))) &&
            ((this.numeroFacturaMarca==null && other.getNumeroFacturaMarca()==null) || 
             (this.numeroFacturaMarca!=null &&
              this.numeroFacturaMarca.equals(other.getNumeroFacturaMarca()))) &&
            ((this.custodioAnterior==null && other.getCustodioAnterior()==null) || 
             (this.custodioAnterior!=null &&
              this.custodioAnterior.equals(other.getCustodioAnterior()))) &&
            ((this.custodioFinal==null && other.getCustodioFinal()==null) || 
             (this.custodioFinal!=null &&
              this.custodioFinal.equals(other.getCustodioFinal()))) &&
            ((this.entidadCredito==null && other.getEntidadCredito()==null) || 
             (this.entidadCredito!=null &&
              this.entidadCredito.equals(other.getEntidadCredito()))) &&
            ((this.datosHistoricosITV==null && other.getDatosHistoricosITV()==null) || 
             (this.datosHistoricosITV!=null &&
              this.datosHistoricosITV.equals(other.getDatosHistoricosITV()))) &&
            ((this.nombreApellidosClienteFinal==null && other.getNombreApellidosClienteFinal()==null) || 
             (this.nombreApellidosClienteFinal!=null &&
              java.util.Arrays.equals(this.nombreApellidosClienteFinal, other.getNombreApellidosClienteFinal()))) &&
            ((this.direccionCliente==null && other.getDireccionCliente()==null) || 
             (this.direccionCliente!=null &&
              java.util.Arrays.equals(this.direccionCliente, other.getDireccionCliente())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getBaseeitvdto() != null) {
            _hashCode += getBaseeitvdto().hashCode();
        }
        if (getConcesionarioClienteComercial() != null) {
            _hashCode += getConcesionarioClienteComercial().hashCode();
        }
        if (getCustodioActual() != null) {
            _hashCode += getCustodioActual().hashCode();
        }
        if (getCustodioSiguiente() != null) {
            _hashCode += getCustodioSiguiente().hashCode();
        }
        if (getDenominacionEstadoFinanciero() != null) {
            _hashCode += getDenominacionEstadoFinanciero().hashCode();
        }
        if (getDninifNieClienteFinal() != null) {
            _hashCode += getDninifNieClienteFinal().hashCode();
        }
        if (getEstadoBastidor() != null) {
            _hashCode += getEstadoBastidor().hashCode();
        }
        if (getFechaFacturaFinal() != null) {
            _hashCode += getFechaFacturaFinal().hashCode();
        }
        if (getFechaFacturaMarca() != null) {
            _hashCode += getFechaFacturaMarca().hashCode();
        }
        if (getFirCif() != null) {
            _hashCode += getFirCif().hashCode();
        }
        if (getFIRMarca() != null) {
            _hashCode += getFIRMarca().hashCode();
        }
        if (getImporteFacturaFinal() != null) {
            _hashCode += getImporteFacturaFinal().hashCode();
        }
        if (getImporteFacturaMarca() != null) {
            _hashCode += getImporteFacturaMarca().hashCode();
        }
        if (getMotivo() != null) {
            _hashCode += getMotivo().hashCode();
        }
        if (getNumeroFacturaFinal() != null) {
            _hashCode += getNumeroFacturaFinal().hashCode();
        }
        if (getNumeroFacturaMarca() != null) {
            _hashCode += getNumeroFacturaMarca().hashCode();
        }
        if (getCustodioAnterior() != null) {
            _hashCode += getCustodioAnterior().hashCode();
        }
        if (getCustodioFinal() != null) {
            _hashCode += getCustodioFinal().hashCode();
        }
        if (getEntidadCredito() != null) {
            _hashCode += getEntidadCredito().hashCode();
        }
        if (getDatosHistoricosITV() != null) {
            _hashCode += getDatosHistoricosITV().hashCode();
        }
        if (getNombreApellidosClienteFinal() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNombreApellidosClienteFinal());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNombreApellidosClienteFinal(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDireccionCliente() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDireccionCliente());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDireccionCliente(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatosSimpleEITVDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "DatosSimpleEITVDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baseeitvdto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "baseeitvdto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "BaseEITVDTO"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("concesionarioClienteComercial");
        elemField.setXmlName(new javax.xml.namespace.QName("", "concesionarioClienteComercial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custodioActual");
        elemField.setXmlName(new javax.xml.namespace.QName("", "custodioActual"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custodioSiguiente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "custodioSiguiente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominacionEstadoFinanciero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominacionEstadoFinanciero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dninifNieClienteFinal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dninifNieClienteFinal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estadoBastidor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estadoBastidor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaFacturaFinal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaFacturaFinal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaFacturaMarca");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaFacturaMarca"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firCif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "firCif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FIRMarca");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FIRMarca"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importeFacturaFinal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importeFacturaFinal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importeFacturaMarca");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importeFacturaMarca"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "motivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroFacturaFinal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroFacturaFinal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroFacturaMarca");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroFacturaMarca"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custodioAnterior");
        elemField.setXmlName(new javax.xml.namespace.QName("", "custodioAnterior"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custodioFinal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "custodioFinal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entidadCredito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entidadCredito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosHistoricosITV");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datosHistoricosITV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.custodiaitv.vehiculos.servicios.trafico.es", "DatosHistoricoEITVDTO"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreApellidosClienteFinal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreApellidosClienteFinal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccionCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "direccionCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "string"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
