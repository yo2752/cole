/**
 * Justificante.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.justificanteprofesional.ws.blackbox;

public class Justificante  extends org.gestoresmadrid.justificanteprofesional.ws.blackbox.AbstractProcessEntity  implements java.io.Serializable {
    private java.lang.String bastidor;

    private java.lang.String codigoGestor;

    private java.lang.String doiTitular;

    private java.lang.String domicilioGestor;

    private java.lang.String domicilioTitular;

    private java.util.Calendar fechaPresentacion;

    private java.lang.String fechaProvincia;

    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.Agent gestor;

    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.Agency gestoria;

    private java.lang.String justificanteURI;

    private java.lang.String marcaVehiculo;

    private java.lang.String matricula;

    private java.lang.String modeloVehiculo;

    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.Municipality municipioGestor;

    private java.lang.String municipioTitular;

    private java.lang.String nombreColegio;

    private java.lang.String nombreGestor;

    private java.lang.String nombreTitular;

    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.ExternalSystem plataforma;

    private java.lang.String primerApellidoTitular;

    private java.lang.String provinciaGestoria;

    private java.lang.String segundoApellidoTitular;

    public Justificante() {
    }

    public Justificante(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.UserLabel[] userLabels,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.User createdBy,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.User modifiedBy,
           java.lang.String bastidor,
           java.lang.String codigoGestor,
           java.lang.String doiTitular,
           java.lang.String domicilioGestor,
           java.lang.String domicilioTitular,
           java.util.Calendar fechaPresentacion,
           java.lang.String fechaProvincia,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.Agent gestor,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.Agency gestoria,
           java.lang.String justificanteURI,
           java.lang.String marcaVehiculo,
           java.lang.String matricula,
           java.lang.String modeloVehiculo,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.Municipality municipioGestor,
           java.lang.String municipioTitular,
           java.lang.String nombreColegio,
           java.lang.String nombreGestor,
           java.lang.String nombreTitular,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.ExternalSystem plataforma,
           java.lang.String primerApellidoTitular,
           java.lang.String provinciaGestoria,
           java.lang.String segundoApellidoTitular) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels,
            createdBy,
            modifiedBy);
        this.bastidor = bastidor;
        this.codigoGestor = codigoGestor;
        this.doiTitular = doiTitular;
        this.domicilioGestor = domicilioGestor;
        this.domicilioTitular = domicilioTitular;
        this.fechaPresentacion = fechaPresentacion;
        this.fechaProvincia = fechaProvincia;
        this.gestor = gestor;
        this.gestoria = gestoria;
        this.justificanteURI = justificanteURI;
        this.marcaVehiculo = marcaVehiculo;
        this.matricula = matricula;
        this.modeloVehiculo = modeloVehiculo;
        this.municipioGestor = municipioGestor;
        this.municipioTitular = municipioTitular;
        this.nombreColegio = nombreColegio;
        this.nombreGestor = nombreGestor;
        this.nombreTitular = nombreTitular;
        this.plataforma = plataforma;
        this.primerApellidoTitular = primerApellidoTitular;
        this.provinciaGestoria = provinciaGestoria;
        this.segundoApellidoTitular = segundoApellidoTitular;
    }


    /**
     * Gets the bastidor value for this Justificante.
     * 
     * @return bastidor
     */
    public java.lang.String getBastidor() {
        return bastidor;
    }


    /**
     * Sets the bastidor value for this Justificante.
     * 
     * @param bastidor
     */
    public void setBastidor(java.lang.String bastidor) {
        this.bastidor = bastidor;
    }


    /**
     * Gets the codigoGestor value for this Justificante.
     * 
     * @return codigoGestor
     */
    public java.lang.String getCodigoGestor() {
        return codigoGestor;
    }


    /**
     * Sets the codigoGestor value for this Justificante.
     * 
     * @param codigoGestor
     */
    public void setCodigoGestor(java.lang.String codigoGestor) {
        this.codigoGestor = codigoGestor;
    }


    /**
     * Gets the doiTitular value for this Justificante.
     * 
     * @return doiTitular
     */
    public java.lang.String getDoiTitular() {
        return doiTitular;
    }


    /**
     * Sets the doiTitular value for this Justificante.
     * 
     * @param doiTitular
     */
    public void setDoiTitular(java.lang.String doiTitular) {
        this.doiTitular = doiTitular;
    }


    /**
     * Gets the domicilioGestor value for this Justificante.
     * 
     * @return domicilioGestor
     */
    public java.lang.String getDomicilioGestor() {
        return domicilioGestor;
    }


    /**
     * Sets the domicilioGestor value for this Justificante.
     * 
     * @param domicilioGestor
     */
    public void setDomicilioGestor(java.lang.String domicilioGestor) {
        this.domicilioGestor = domicilioGestor;
    }


    /**
     * Gets the domicilioTitular value for this Justificante.
     * 
     * @return domicilioTitular
     */
    public java.lang.String getDomicilioTitular() {
        return domicilioTitular;
    }


    /**
     * Sets the domicilioTitular value for this Justificante.
     * 
     * @param domicilioTitular
     */
    public void setDomicilioTitular(java.lang.String domicilioTitular) {
        this.domicilioTitular = domicilioTitular;
    }


    /**
     * Gets the fechaPresentacion value for this Justificante.
     * 
     * @return fechaPresentacion
     */
    public java.util.Calendar getFechaPresentacion() {
        return fechaPresentacion;
    }


    /**
     * Sets the fechaPresentacion value for this Justificante.
     * 
     * @param fechaPresentacion
     */
    public void setFechaPresentacion(java.util.Calendar fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }


    /**
     * Gets the fechaProvincia value for this Justificante.
     * 
     * @return fechaProvincia
     */
    public java.lang.String getFechaProvincia() {
        return fechaProvincia;
    }


    /**
     * Sets the fechaProvincia value for this Justificante.
     * 
     * @param fechaProvincia
     */
    public void setFechaProvincia(java.lang.String fechaProvincia) {
        this.fechaProvincia = fechaProvincia;
    }


    /**
     * Gets the gestor value for this Justificante.
     * 
     * @return gestor
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.Agent getGestor() {
        return gestor;
    }


    /**
     * Sets the gestor value for this Justificante.
     * 
     * @param gestor
     */
    public void setGestor(org.gestoresmadrid.justificanteprofesional.ws.blackbox.Agent gestor) {
        this.gestor = gestor;
    }


    /**
     * Gets the gestoria value for this Justificante.
     * 
     * @return gestoria
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.Agency getGestoria() {
        return gestoria;
    }


    /**
     * Sets the gestoria value for this Justificante.
     * 
     * @param gestoria
     */
    public void setGestoria(org.gestoresmadrid.justificanteprofesional.ws.blackbox.Agency gestoria) {
        this.gestoria = gestoria;
    }


    /**
     * Gets the justificanteURI value for this Justificante.
     * 
     * @return justificanteURI
     */
    public java.lang.String getJustificanteURI() {
        return justificanteURI;
    }


    /**
     * Sets the justificanteURI value for this Justificante.
     * 
     * @param justificanteURI
     */
    public void setJustificanteURI(java.lang.String justificanteURI) {
        this.justificanteURI = justificanteURI;
    }


    /**
     * Gets the marcaVehiculo value for this Justificante.
     * 
     * @return marcaVehiculo
     */
    public java.lang.String getMarcaVehiculo() {
        return marcaVehiculo;
    }


    /**
     * Sets the marcaVehiculo value for this Justificante.
     * 
     * @param marcaVehiculo
     */
    public void setMarcaVehiculo(java.lang.String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }


    /**
     * Gets the matricula value for this Justificante.
     * 
     * @return matricula
     */
    public java.lang.String getMatricula() {
        return matricula;
    }


    /**
     * Sets the matricula value for this Justificante.
     * 
     * @param matricula
     */
    public void setMatricula(java.lang.String matricula) {
        this.matricula = matricula;
    }


    /**
     * Gets the modeloVehiculo value for this Justificante.
     * 
     * @return modeloVehiculo
     */
    public java.lang.String getModeloVehiculo() {
        return modeloVehiculo;
    }


    /**
     * Sets the modeloVehiculo value for this Justificante.
     * 
     * @param modeloVehiculo
     */
    public void setModeloVehiculo(java.lang.String modeloVehiculo) {
        this.modeloVehiculo = modeloVehiculo;
    }


    /**
     * Gets the municipioGestor value for this Justificante.
     * 
     * @return municipioGestor
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.Municipality getMunicipioGestor() {
        return municipioGestor;
    }


    /**
     * Sets the municipioGestor value for this Justificante.
     * 
     * @param municipioGestor
     */
    public void setMunicipioGestor(org.gestoresmadrid.justificanteprofesional.ws.blackbox.Municipality municipioGestor) {
        this.municipioGestor = municipioGestor;
    }


    /**
     * Gets the municipioTitular value for this Justificante.
     * 
     * @return municipioTitular
     */
    public java.lang.String getMunicipioTitular() {
        return municipioTitular;
    }


    /**
     * Sets the municipioTitular value for this Justificante.
     * 
     * @param municipioTitular
     */
    public void setMunicipioTitular(java.lang.String municipioTitular) {
        this.municipioTitular = municipioTitular;
    }


    /**
     * Gets the nombreColegio value for this Justificante.
     * 
     * @return nombreColegio
     */
    public java.lang.String getNombreColegio() {
        return nombreColegio;
    }


    /**
     * Sets the nombreColegio value for this Justificante.
     * 
     * @param nombreColegio
     */
    public void setNombreColegio(java.lang.String nombreColegio) {
        this.nombreColegio = nombreColegio;
    }


    /**
     * Gets the nombreGestor value for this Justificante.
     * 
     * @return nombreGestor
     */
    public java.lang.String getNombreGestor() {
        return nombreGestor;
    }


    /**
     * Sets the nombreGestor value for this Justificante.
     * 
     * @param nombreGestor
     */
    public void setNombreGestor(java.lang.String nombreGestor) {
        this.nombreGestor = nombreGestor;
    }


    /**
     * Gets the nombreTitular value for this Justificante.
     * 
     * @return nombreTitular
     */
    public java.lang.String getNombreTitular() {
        return nombreTitular;
    }


    /**
     * Sets the nombreTitular value for this Justificante.
     * 
     * @param nombreTitular
     */
    public void setNombreTitular(java.lang.String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }


    /**
     * Gets the plataforma value for this Justificante.
     * 
     * @return plataforma
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.ExternalSystem getPlataforma() {
        return plataforma;
    }


    /**
     * Sets the plataforma value for this Justificante.
     * 
     * @param plataforma
     */
    public void setPlataforma(org.gestoresmadrid.justificanteprofesional.ws.blackbox.ExternalSystem plataforma) {
        this.plataforma = plataforma;
    }


    /**
     * Gets the primerApellidoTitular value for this Justificante.
     * 
     * @return primerApellidoTitular
     */
    public java.lang.String getPrimerApellidoTitular() {
        return primerApellidoTitular;
    }


    /**
     * Sets the primerApellidoTitular value for this Justificante.
     * 
     * @param primerApellidoTitular
     */
    public void setPrimerApellidoTitular(java.lang.String primerApellidoTitular) {
        this.primerApellidoTitular = primerApellidoTitular;
    }


    /**
     * Gets the provinciaGestoria value for this Justificante.
     * 
     * @return provinciaGestoria
     */
    public java.lang.String getProvinciaGestoria() {
        return provinciaGestoria;
    }


    /**
     * Sets the provinciaGestoria value for this Justificante.
     * 
     * @param provinciaGestoria
     */
    public void setProvinciaGestoria(java.lang.String provinciaGestoria) {
        this.provinciaGestoria = provinciaGestoria;
    }


    /**
     * Gets the segundoApellidoTitular value for this Justificante.
     * 
     * @return segundoApellidoTitular
     */
    public java.lang.String getSegundoApellidoTitular() {
        return segundoApellidoTitular;
    }


    /**
     * Sets the segundoApellidoTitular value for this Justificante.
     * 
     * @param segundoApellidoTitular
     */
    public void setSegundoApellidoTitular(java.lang.String segundoApellidoTitular) {
        this.segundoApellidoTitular = segundoApellidoTitular;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Justificante)) return false;
        Justificante other = (Justificante) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.bastidor==null && other.getBastidor()==null) || 
             (this.bastidor!=null &&
              this.bastidor.equals(other.getBastidor()))) &&
            ((this.codigoGestor==null && other.getCodigoGestor()==null) || 
             (this.codigoGestor!=null &&
              this.codigoGestor.equals(other.getCodigoGestor()))) &&
            ((this.doiTitular==null && other.getDoiTitular()==null) || 
             (this.doiTitular!=null &&
              this.doiTitular.equals(other.getDoiTitular()))) &&
            ((this.domicilioGestor==null && other.getDomicilioGestor()==null) || 
             (this.domicilioGestor!=null &&
              this.domicilioGestor.equals(other.getDomicilioGestor()))) &&
            ((this.domicilioTitular==null && other.getDomicilioTitular()==null) || 
             (this.domicilioTitular!=null &&
              this.domicilioTitular.equals(other.getDomicilioTitular()))) &&
            ((this.fechaPresentacion==null && other.getFechaPresentacion()==null) || 
             (this.fechaPresentacion!=null &&
              this.fechaPresentacion.equals(other.getFechaPresentacion()))) &&
            ((this.fechaProvincia==null && other.getFechaProvincia()==null) || 
             (this.fechaProvincia!=null &&
              this.fechaProvincia.equals(other.getFechaProvincia()))) &&
            ((this.gestor==null && other.getGestor()==null) || 
             (this.gestor!=null &&
              this.gestor.equals(other.getGestor()))) &&
            ((this.gestoria==null && other.getGestoria()==null) || 
             (this.gestoria!=null &&
              this.gestoria.equals(other.getGestoria()))) &&
            ((this.justificanteURI==null && other.getJustificanteURI()==null) || 
             (this.justificanteURI!=null &&
              this.justificanteURI.equals(other.getJustificanteURI()))) &&
            ((this.marcaVehiculo==null && other.getMarcaVehiculo()==null) || 
             (this.marcaVehiculo!=null &&
              this.marcaVehiculo.equals(other.getMarcaVehiculo()))) &&
            ((this.matricula==null && other.getMatricula()==null) || 
             (this.matricula!=null &&
              this.matricula.equals(other.getMatricula()))) &&
            ((this.modeloVehiculo==null && other.getModeloVehiculo()==null) || 
             (this.modeloVehiculo!=null &&
              this.modeloVehiculo.equals(other.getModeloVehiculo()))) &&
            ((this.municipioGestor==null && other.getMunicipioGestor()==null) || 
             (this.municipioGestor!=null &&
              this.municipioGestor.equals(other.getMunicipioGestor()))) &&
            ((this.municipioTitular==null && other.getMunicipioTitular()==null) || 
             (this.municipioTitular!=null &&
              this.municipioTitular.equals(other.getMunicipioTitular()))) &&
            ((this.nombreColegio==null && other.getNombreColegio()==null) || 
             (this.nombreColegio!=null &&
              this.nombreColegio.equals(other.getNombreColegio()))) &&
            ((this.nombreGestor==null && other.getNombreGestor()==null) || 
             (this.nombreGestor!=null &&
              this.nombreGestor.equals(other.getNombreGestor()))) &&
            ((this.nombreTitular==null && other.getNombreTitular()==null) || 
             (this.nombreTitular!=null &&
              this.nombreTitular.equals(other.getNombreTitular()))) &&
            ((this.plataforma==null && other.getPlataforma()==null) || 
             (this.plataforma!=null &&
              this.plataforma.equals(other.getPlataforma()))) &&
            ((this.primerApellidoTitular==null && other.getPrimerApellidoTitular()==null) || 
             (this.primerApellidoTitular!=null &&
              this.primerApellidoTitular.equals(other.getPrimerApellidoTitular()))) &&
            ((this.provinciaGestoria==null && other.getProvinciaGestoria()==null) || 
             (this.provinciaGestoria!=null &&
              this.provinciaGestoria.equals(other.getProvinciaGestoria()))) &&
            ((this.segundoApellidoTitular==null && other.getSegundoApellidoTitular()==null) || 
             (this.segundoApellidoTitular!=null &&
              this.segundoApellidoTitular.equals(other.getSegundoApellidoTitular())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getBastidor() != null) {
            _hashCode += getBastidor().hashCode();
        }
        if (getCodigoGestor() != null) {
            _hashCode += getCodigoGestor().hashCode();
        }
        if (getDoiTitular() != null) {
            _hashCode += getDoiTitular().hashCode();
        }
        if (getDomicilioGestor() != null) {
            _hashCode += getDomicilioGestor().hashCode();
        }
        if (getDomicilioTitular() != null) {
            _hashCode += getDomicilioTitular().hashCode();
        }
        if (getFechaPresentacion() != null) {
            _hashCode += getFechaPresentacion().hashCode();
        }
        if (getFechaProvincia() != null) {
            _hashCode += getFechaProvincia().hashCode();
        }
        if (getGestor() != null) {
            _hashCode += getGestor().hashCode();
        }
        if (getGestoria() != null) {
            _hashCode += getGestoria().hashCode();
        }
        if (getJustificanteURI() != null) {
            _hashCode += getJustificanteURI().hashCode();
        }
        if (getMarcaVehiculo() != null) {
            _hashCode += getMarcaVehiculo().hashCode();
        }
        if (getMatricula() != null) {
            _hashCode += getMatricula().hashCode();
        }
        if (getModeloVehiculo() != null) {
            _hashCode += getModeloVehiculo().hashCode();
        }
        if (getMunicipioGestor() != null) {
            _hashCode += getMunicipioGestor().hashCode();
        }
        if (getMunicipioTitular() != null) {
            _hashCode += getMunicipioTitular().hashCode();
        }
        if (getNombreColegio() != null) {
            _hashCode += getNombreColegio().hashCode();
        }
        if (getNombreGestor() != null) {
            _hashCode += getNombreGestor().hashCode();
        }
        if (getNombreTitular() != null) {
            _hashCode += getNombreTitular().hashCode();
        }
        if (getPlataforma() != null) {
            _hashCode += getPlataforma().hashCode();
        }
        if (getPrimerApellidoTitular() != null) {
            _hashCode += getPrimerApellidoTitular().hashCode();
        }
        if (getProvinciaGestoria() != null) {
            _hashCode += getProvinciaGestoria().hashCode();
        }
        if (getSegundoApellidoTitular() != null) {
            _hashCode += getSegundoApellidoTitular().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Justificante.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificante"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bastidor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bastidor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoGestor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoGestor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doiTitular");
        elemField.setXmlName(new javax.xml.namespace.QName("", "doiTitular"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("domicilioGestor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "domicilioGestor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("domicilioTitular");
        elemField.setXmlName(new javax.xml.namespace.QName("", "domicilioTitular"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaPresentacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaPresentacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaProvincia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaProvincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gestor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gestor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agent"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gestoria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gestoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agency"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("justificanteURI");
        elemField.setXmlName(new javax.xml.namespace.QName("", "justificanteURI"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("marcaVehiculo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "marcaVehiculo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matricula");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matricula"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modeloVehiculo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modeloVehiculo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipioGestor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "municipioGestor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "municipality"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipioTitular");
        elemField.setXmlName(new javax.xml.namespace.QName("", "municipioTitular"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreColegio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreColegio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreGestor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreGestor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreTitular");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreTitular"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("plataforma");
        elemField.setXmlName(new javax.xml.namespace.QName("", "plataforma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "externalSystem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primerApellidoTitular");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primerApellidoTitular"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provinciaGestoria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provinciaGestoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segundoApellidoTitular");
        elemField.setXmlName(new javax.xml.namespace.QName("", "segundoApellidoTitular"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
