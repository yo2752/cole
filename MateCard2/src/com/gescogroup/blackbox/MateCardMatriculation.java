/**
 * MateCardMatriculation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class MateCardMatriculation  extends com.gescogroup.blackbox.Dossier  implements java.io.Serializable {
    private java.lang.String brand;

    private java.lang.String CEM;

    private java.lang.String codeHomologation;

    private java.lang.String codeITV;

    private java.lang.Double cubicCapacity;

    private java.util.Calendar expirationDateITV;

    private java.util.Calendar filingDate;

    private java.lang.Boolean has05;

    private java.lang.Boolean has06;

    private java.lang.Boolean has576;

    private com.gescogroup.blackbox.ItvCardType itvCardType;

    private java.lang.String kindITV;

    private com.gescogroup.blackbox.MateCardLicense license;

    private java.util.Calendar limitationDate;

    private java.lang.String localDGTDivision;

    private java.lang.Integer masa;

    private java.util.Calendar matriculationDate;

    private java.lang.Integer maxPlaces;

    private java.lang.String model;

    private java.lang.Integer mtma;

    private java.lang.String plateNumber;

    private java.lang.Double power;

    private java.lang.Double powerWeight;

    private java.lang.Integer seatPlaces;

    private java.lang.String serialNumber;

    private java.lang.Integer standUpPlaces;

    private com.gescogroup.blackbox.MateMatriculationState status;

    private java.lang.Integer tara;

    private java.lang.String variant;

    private java.lang.String vehicleColor;

    private java.lang.String vehicleFuel;

    private java.lang.String vehicleHomologation;

    private java.lang.String vehicleKind;

    private java.lang.String vehicleOrigin;

    private com.gescogroup.blackbox.MateCardVehicleOwner vehicleOwner;

    private java.lang.String vehiclePurpose;

    private java.lang.String vehicleVersion;

    public MateCardMatriculation() {
    }

    public MateCardMatriculation(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.User createdBy,
           com.gescogroup.blackbox.User modifiedBy,
           com.gescogroup.blackbox.Agency agency,
           com.gescogroup.blackbox.Agent agent,
           java.lang.String dgtTaxCode,
           java.lang.String dossierNumber,
           java.lang.String brand,
           java.lang.String CEM,
           java.lang.String codeHomologation,
           java.lang.String codeITV,
           java.lang.Double cubicCapacity,
           java.util.Calendar expirationDateITV,
           java.util.Calendar filingDate,
           java.lang.Boolean has05,
           java.lang.Boolean has06,
           java.lang.Boolean has576,
           com.gescogroup.blackbox.ItvCardType itvCardType,
           java.lang.String kindITV,
           com.gescogroup.blackbox.MateCardLicense license,
           java.util.Calendar limitationDate,
           java.lang.String localDGTDivision,
           java.lang.Integer masa,
           java.util.Calendar matriculationDate,
           java.lang.Integer maxPlaces,
           java.lang.String model,
           java.lang.Integer mtma,
           java.lang.String plateNumber,
           java.lang.Double power,
           java.lang.Double powerWeight,
           java.lang.Integer seatPlaces,
           java.lang.String serialNumber,
           java.lang.Integer standUpPlaces,
           com.gescogroup.blackbox.MateMatriculationState status,
           java.lang.Integer tara,
           java.lang.String variant,
           java.lang.String vehicleColor,
           java.lang.String vehicleFuel,
           java.lang.String vehicleHomologation,
           java.lang.String vehicleKind,
           java.lang.String vehicleOrigin,
           com.gescogroup.blackbox.MateCardVehicleOwner vehicleOwner,
           java.lang.String vehiclePurpose,
           java.lang.String vehicleVersion) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            createdBy,
            modifiedBy,
            agency,
            agent,
            dgtTaxCode,
            dossierNumber);
        this.brand = brand;
        this.CEM = CEM;
        this.codeHomologation = codeHomologation;
        this.codeITV = codeITV;
        this.cubicCapacity = cubicCapacity;
        this.expirationDateITV = expirationDateITV;
        this.filingDate = filingDate;
        this.has05 = has05;
        this.has06 = has06;
        this.has576 = has576;
        this.itvCardType = itvCardType;
        this.kindITV = kindITV;
        this.license = license;
        this.limitationDate = limitationDate;
        this.localDGTDivision = localDGTDivision;
        this.masa = masa;
        this.matriculationDate = matriculationDate;
        this.maxPlaces = maxPlaces;
        this.model = model;
        this.mtma = mtma;
        this.plateNumber = plateNumber;
        this.power = power;
        this.powerWeight = powerWeight;
        this.seatPlaces = seatPlaces;
        this.serialNumber = serialNumber;
        this.standUpPlaces = standUpPlaces;
        this.status = status;
        this.tara = tara;
        this.variant = variant;
        this.vehicleColor = vehicleColor;
        this.vehicleFuel = vehicleFuel;
        this.vehicleHomologation = vehicleHomologation;
        this.vehicleKind = vehicleKind;
        this.vehicleOrigin = vehicleOrigin;
        this.vehicleOwner = vehicleOwner;
        this.vehiclePurpose = vehiclePurpose;
        this.vehicleVersion = vehicleVersion;
    }


    /**
     * Gets the brand value for this MateCardMatriculation.
     * 
     * @return brand
     */
    public java.lang.String getBrand() {
        return brand;
    }


    /**
     * Sets the brand value for this MateCardMatriculation.
     * 
     * @param brand
     */
    public void setBrand(java.lang.String brand) {
        this.brand = brand;
    }


    /**
     * Gets the CEM value for this MateCardMatriculation.
     * 
     * @return CEM
     */
    public java.lang.String getCEM() {
        return CEM;
    }


    /**
     * Sets the CEM value for this MateCardMatriculation.
     * 
     * @param CEM
     */
    public void setCEM(java.lang.String CEM) {
        this.CEM = CEM;
    }


    /**
     * Gets the codeHomologation value for this MateCardMatriculation.
     * 
     * @return codeHomologation
     */
    public java.lang.String getCodeHomologation() {
        return codeHomologation;
    }


    /**
     * Sets the codeHomologation value for this MateCardMatriculation.
     * 
     * @param codeHomologation
     */
    public void setCodeHomologation(java.lang.String codeHomologation) {
        this.codeHomologation = codeHomologation;
    }


    /**
     * Gets the codeITV value for this MateCardMatriculation.
     * 
     * @return codeITV
     */
    public java.lang.String getCodeITV() {
        return codeITV;
    }


    /**
     * Sets the codeITV value for this MateCardMatriculation.
     * 
     * @param codeITV
     */
    public void setCodeITV(java.lang.String codeITV) {
        this.codeITV = codeITV;
    }


    /**
     * Gets the cubicCapacity value for this MateCardMatriculation.
     * 
     * @return cubicCapacity
     */
    public java.lang.Double getCubicCapacity() {
        return cubicCapacity;
    }


    /**
     * Sets the cubicCapacity value for this MateCardMatriculation.
     * 
     * @param cubicCapacity
     */
    public void setCubicCapacity(java.lang.Double cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
    }


    /**
     * Gets the expirationDateITV value for this MateCardMatriculation.
     * 
     * @return expirationDateITV
     */
    public java.util.Calendar getExpirationDateITV() {
        return expirationDateITV;
    }


    /**
     * Sets the expirationDateITV value for this MateCardMatriculation.
     * 
     * @param expirationDateITV
     */
    public void setExpirationDateITV(java.util.Calendar expirationDateITV) {
        this.expirationDateITV = expirationDateITV;
    }


    /**
     * Gets the filingDate value for this MateCardMatriculation.
     * 
     * @return filingDate
     */
    public java.util.Calendar getFilingDate() {
        return filingDate;
    }


    /**
     * Sets the filingDate value for this MateCardMatriculation.
     * 
     * @param filingDate
     */
    public void setFilingDate(java.util.Calendar filingDate) {
        this.filingDate = filingDate;
    }


    /**
     * Gets the has05 value for this MateCardMatriculation.
     * 
     * @return has05
     */
    public java.lang.Boolean getHas05() {
        return has05;
    }


    /**
     * Sets the has05 value for this MateCardMatriculation.
     * 
     * @param has05
     */
    public void setHas05(java.lang.Boolean has05) {
        this.has05 = has05;
    }


    /**
     * Gets the has06 value for this MateCardMatriculation.
     * 
     * @return has06
     */
    public java.lang.Boolean getHas06() {
        return has06;
    }


    /**
     * Sets the has06 value for this MateCardMatriculation.
     * 
     * @param has06
     */
    public void setHas06(java.lang.Boolean has06) {
        this.has06 = has06;
    }


    /**
     * Gets the has576 value for this MateCardMatriculation.
     * 
     * @return has576
     */
    public java.lang.Boolean getHas576() {
        return has576;
    }


    /**
     * Sets the has576 value for this MateCardMatriculation.
     * 
     * @param has576
     */
    public void setHas576(java.lang.Boolean has576) {
        this.has576 = has576;
    }


    /**
     * Gets the itvCardType value for this MateCardMatriculation.
     * 
     * @return itvCardType
     */
    public com.gescogroup.blackbox.ItvCardType getItvCardType() {
        return itvCardType;
    }


    /**
     * Sets the itvCardType value for this MateCardMatriculation.
     * 
     * @param itvCardType
     */
    public void setItvCardType(com.gescogroup.blackbox.ItvCardType itvCardType) {
        this.itvCardType = itvCardType;
    }


    /**
     * Gets the kindITV value for this MateCardMatriculation.
     * 
     * @return kindITV
     */
    public java.lang.String getKindITV() {
        return kindITV;
    }


    /**
     * Sets the kindITV value for this MateCardMatriculation.
     * 
     * @param kindITV
     */
    public void setKindITV(java.lang.String kindITV) {
        this.kindITV = kindITV;
    }


    /**
     * Gets the license value for this MateCardMatriculation.
     * 
     * @return license
     */
    public com.gescogroup.blackbox.MateCardLicense getLicense() {
        return license;
    }


    /**
     * Sets the license value for this MateCardMatriculation.
     * 
     * @param license
     */
    public void setLicense(com.gescogroup.blackbox.MateCardLicense license) {
        this.license = license;
    }


    /**
     * Gets the limitationDate value for this MateCardMatriculation.
     * 
     * @return limitationDate
     */
    public java.util.Calendar getLimitationDate() {
        return limitationDate;
    }


    /**
     * Sets the limitationDate value for this MateCardMatriculation.
     * 
     * @param limitationDate
     */
    public void setLimitationDate(java.util.Calendar limitationDate) {
        this.limitationDate = limitationDate;
    }


    /**
     * Gets the localDGTDivision value for this MateCardMatriculation.
     * 
     * @return localDGTDivision
     */
    public java.lang.String getLocalDGTDivision() {
        return localDGTDivision;
    }


    /**
     * Sets the localDGTDivision value for this MateCardMatriculation.
     * 
     * @param localDGTDivision
     */
    public void setLocalDGTDivision(java.lang.String localDGTDivision) {
        this.localDGTDivision = localDGTDivision;
    }


    /**
     * Gets the masa value for this MateCardMatriculation.
     * 
     * @return masa
     */
    public java.lang.Integer getMasa() {
        return masa;
    }


    /**
     * Sets the masa value for this MateCardMatriculation.
     * 
     * @param masa
     */
    public void setMasa(java.lang.Integer masa) {
        this.masa = masa;
    }


    /**
     * Gets the matriculationDate value for this MateCardMatriculation.
     * 
     * @return matriculationDate
     */
    public java.util.Calendar getMatriculationDate() {
        return matriculationDate;
    }


    /**
     * Sets the matriculationDate value for this MateCardMatriculation.
     * 
     * @param matriculationDate
     */
    public void setMatriculationDate(java.util.Calendar matriculationDate) {
        this.matriculationDate = matriculationDate;
    }


    /**
     * Gets the maxPlaces value for this MateCardMatriculation.
     * 
     * @return maxPlaces
     */
    public java.lang.Integer getMaxPlaces() {
        return maxPlaces;
    }


    /**
     * Sets the maxPlaces value for this MateCardMatriculation.
     * 
     * @param maxPlaces
     */
    public void setMaxPlaces(java.lang.Integer maxPlaces) {
        this.maxPlaces = maxPlaces;
    }


    /**
     * Gets the model value for this MateCardMatriculation.
     * 
     * @return model
     */
    public java.lang.String getModel() {
        return model;
    }


    /**
     * Sets the model value for this MateCardMatriculation.
     * 
     * @param model
     */
    public void setModel(java.lang.String model) {
        this.model = model;
    }


    /**
     * Gets the mtma value for this MateCardMatriculation.
     * 
     * @return mtma
     */
    public java.lang.Integer getMtma() {
        return mtma;
    }


    /**
     * Sets the mtma value for this MateCardMatriculation.
     * 
     * @param mtma
     */
    public void setMtma(java.lang.Integer mtma) {
        this.mtma = mtma;
    }


    /**
     * Gets the plateNumber value for this MateCardMatriculation.
     * 
     * @return plateNumber
     */
    public java.lang.String getPlateNumber() {
        return plateNumber;
    }


    /**
     * Sets the plateNumber value for this MateCardMatriculation.
     * 
     * @param plateNumber
     */
    public void setPlateNumber(java.lang.String plateNumber) {
        this.plateNumber = plateNumber;
    }


    /**
     * Gets the power value for this MateCardMatriculation.
     * 
     * @return power
     */
    public java.lang.Double getPower() {
        return power;
    }


    /**
     * Sets the power value for this MateCardMatriculation.
     * 
     * @param power
     */
    public void setPower(java.lang.Double power) {
        this.power = power;
    }


    /**
     * Gets the powerWeight value for this MateCardMatriculation.
     * 
     * @return powerWeight
     */
    public java.lang.Double getPowerWeight() {
        return powerWeight;
    }


    /**
     * Sets the powerWeight value for this MateCardMatriculation.
     * 
     * @param powerWeight
     */
    public void setPowerWeight(java.lang.Double powerWeight) {
        this.powerWeight = powerWeight;
    }


    /**
     * Gets the seatPlaces value for this MateCardMatriculation.
     * 
     * @return seatPlaces
     */
    public java.lang.Integer getSeatPlaces() {
        return seatPlaces;
    }


    /**
     * Sets the seatPlaces value for this MateCardMatriculation.
     * 
     * @param seatPlaces
     */
    public void setSeatPlaces(java.lang.Integer seatPlaces) {
        this.seatPlaces = seatPlaces;
    }


    /**
     * Gets the serialNumber value for this MateCardMatriculation.
     * 
     * @return serialNumber
     */
    public java.lang.String getSerialNumber() {
        return serialNumber;
    }


    /**
     * Sets the serialNumber value for this MateCardMatriculation.
     * 
     * @param serialNumber
     */
    public void setSerialNumber(java.lang.String serialNumber) {
        this.serialNumber = serialNumber;
    }


    /**
     * Gets the standUpPlaces value for this MateCardMatriculation.
     * 
     * @return standUpPlaces
     */
    public java.lang.Integer getStandUpPlaces() {
        return standUpPlaces;
    }


    /**
     * Sets the standUpPlaces value for this MateCardMatriculation.
     * 
     * @param standUpPlaces
     */
    public void setStandUpPlaces(java.lang.Integer standUpPlaces) {
        this.standUpPlaces = standUpPlaces;
    }


    /**
     * Gets the status value for this MateCardMatriculation.
     * 
     * @return status
     */
    public com.gescogroup.blackbox.MateMatriculationState getStatus() {
        return status;
    }


    /**
     * Sets the status value for this MateCardMatriculation.
     * 
     * @param status
     */
    public void setStatus(com.gescogroup.blackbox.MateMatriculationState status) {
        this.status = status;
    }


    /**
     * Gets the tara value for this MateCardMatriculation.
     * 
     * @return tara
     */
    public java.lang.Integer getTara() {
        return tara;
    }


    /**
     * Sets the tara value for this MateCardMatriculation.
     * 
     * @param tara
     */
    public void setTara(java.lang.Integer tara) {
        this.tara = tara;
    }


    /**
     * Gets the variant value for this MateCardMatriculation.
     * 
     * @return variant
     */
    public java.lang.String getVariant() {
        return variant;
    }


    /**
     * Sets the variant value for this MateCardMatriculation.
     * 
     * @param variant
     */
    public void setVariant(java.lang.String variant) {
        this.variant = variant;
    }


    /**
     * Gets the vehicleColor value for this MateCardMatriculation.
     * 
     * @return vehicleColor
     */
    public java.lang.String getVehicleColor() {
        return vehicleColor;
    }


    /**
     * Sets the vehicleColor value for this MateCardMatriculation.
     * 
     * @param vehicleColor
     */
    public void setVehicleColor(java.lang.String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }


    /**
     * Gets the vehicleFuel value for this MateCardMatriculation.
     * 
     * @return vehicleFuel
     */
    public java.lang.String getVehicleFuel() {
        return vehicleFuel;
    }


    /**
     * Sets the vehicleFuel value for this MateCardMatriculation.
     * 
     * @param vehicleFuel
     */
    public void setVehicleFuel(java.lang.String vehicleFuel) {
        this.vehicleFuel = vehicleFuel;
    }


    /**
     * Gets the vehicleHomologation value for this MateCardMatriculation.
     * 
     * @return vehicleHomologation
     */
    public java.lang.String getVehicleHomologation() {
        return vehicleHomologation;
    }


    /**
     * Sets the vehicleHomologation value for this MateCardMatriculation.
     * 
     * @param vehicleHomologation
     */
    public void setVehicleHomologation(java.lang.String vehicleHomologation) {
        this.vehicleHomologation = vehicleHomologation;
    }


    /**
     * Gets the vehicleKind value for this MateCardMatriculation.
     * 
     * @return vehicleKind
     */
    public java.lang.String getVehicleKind() {
        return vehicleKind;
    }


    /**
     * Sets the vehicleKind value for this MateCardMatriculation.
     * 
     * @param vehicleKind
     */
    public void setVehicleKind(java.lang.String vehicleKind) {
        this.vehicleKind = vehicleKind;
    }


    /**
     * Gets the vehicleOrigin value for this MateCardMatriculation.
     * 
     * @return vehicleOrigin
     */
    public java.lang.String getVehicleOrigin() {
        return vehicleOrigin;
    }


    /**
     * Sets the vehicleOrigin value for this MateCardMatriculation.
     * 
     * @param vehicleOrigin
     */
    public void setVehicleOrigin(java.lang.String vehicleOrigin) {
        this.vehicleOrigin = vehicleOrigin;
    }


    /**
     * Gets the vehicleOwner value for this MateCardMatriculation.
     * 
     * @return vehicleOwner
     */
    public com.gescogroup.blackbox.MateCardVehicleOwner getVehicleOwner() {
        return vehicleOwner;
    }


    /**
     * Sets the vehicleOwner value for this MateCardMatriculation.
     * 
     * @param vehicleOwner
     */
    public void setVehicleOwner(com.gescogroup.blackbox.MateCardVehicleOwner vehicleOwner) {
        this.vehicleOwner = vehicleOwner;
    }


    /**
     * Gets the vehiclePurpose value for this MateCardMatriculation.
     * 
     * @return vehiclePurpose
     */
    public java.lang.String getVehiclePurpose() {
        return vehiclePurpose;
    }


    /**
     * Sets the vehiclePurpose value for this MateCardMatriculation.
     * 
     * @param vehiclePurpose
     */
    public void setVehiclePurpose(java.lang.String vehiclePurpose) {
        this.vehiclePurpose = vehiclePurpose;
    }


    /**
     * Gets the vehicleVersion value for this MateCardMatriculation.
     * 
     * @return vehicleVersion
     */
    public java.lang.String getVehicleVersion() {
        return vehicleVersion;
    }


    /**
     * Sets the vehicleVersion value for this MateCardMatriculation.
     * 
     * @param vehicleVersion
     */
    public void setVehicleVersion(java.lang.String vehicleVersion) {
        this.vehicleVersion = vehicleVersion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MateCardMatriculation)) return false;
        MateCardMatriculation other = (MateCardMatriculation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.brand==null && other.getBrand()==null) || 
             (this.brand!=null &&
              this.brand.equals(other.getBrand()))) &&
            ((this.CEM==null && other.getCEM()==null) || 
             (this.CEM!=null &&
              this.CEM.equals(other.getCEM()))) &&
            ((this.codeHomologation==null && other.getCodeHomologation()==null) || 
             (this.codeHomologation!=null &&
              this.codeHomologation.equals(other.getCodeHomologation()))) &&
            ((this.codeITV==null && other.getCodeITV()==null) || 
             (this.codeITV!=null &&
              this.codeITV.equals(other.getCodeITV()))) &&
            ((this.cubicCapacity==null && other.getCubicCapacity()==null) || 
             (this.cubicCapacity!=null &&
              this.cubicCapacity.equals(other.getCubicCapacity()))) &&
            ((this.expirationDateITV==null && other.getExpirationDateITV()==null) || 
             (this.expirationDateITV!=null &&
              this.expirationDateITV.equals(other.getExpirationDateITV()))) &&
            ((this.filingDate==null && other.getFilingDate()==null) || 
             (this.filingDate!=null &&
              this.filingDate.equals(other.getFilingDate()))) &&
            ((this.has05==null && other.getHas05()==null) || 
             (this.has05!=null &&
              this.has05.equals(other.getHas05()))) &&
            ((this.has06==null && other.getHas06()==null) || 
             (this.has06!=null &&
              this.has06.equals(other.getHas06()))) &&
            ((this.has576==null && other.getHas576()==null) || 
             (this.has576!=null &&
              this.has576.equals(other.getHas576()))) &&
            ((this.itvCardType==null && other.getItvCardType()==null) || 
             (this.itvCardType!=null &&
              this.itvCardType.equals(other.getItvCardType()))) &&
            ((this.kindITV==null && other.getKindITV()==null) || 
             (this.kindITV!=null &&
              this.kindITV.equals(other.getKindITV()))) &&
            ((this.license==null && other.getLicense()==null) || 
             (this.license!=null &&
              this.license.equals(other.getLicense()))) &&
            ((this.limitationDate==null && other.getLimitationDate()==null) || 
             (this.limitationDate!=null &&
              this.limitationDate.equals(other.getLimitationDate()))) &&
            ((this.localDGTDivision==null && other.getLocalDGTDivision()==null) || 
             (this.localDGTDivision!=null &&
              this.localDGTDivision.equals(other.getLocalDGTDivision()))) &&
            ((this.masa==null && other.getMasa()==null) || 
             (this.masa!=null &&
              this.masa.equals(other.getMasa()))) &&
            ((this.matriculationDate==null && other.getMatriculationDate()==null) || 
             (this.matriculationDate!=null &&
              this.matriculationDate.equals(other.getMatriculationDate()))) &&
            ((this.maxPlaces==null && other.getMaxPlaces()==null) || 
             (this.maxPlaces!=null &&
              this.maxPlaces.equals(other.getMaxPlaces()))) &&
            ((this.model==null && other.getModel()==null) || 
             (this.model!=null &&
              this.model.equals(other.getModel()))) &&
            ((this.mtma==null && other.getMtma()==null) || 
             (this.mtma!=null &&
              this.mtma.equals(other.getMtma()))) &&
            ((this.plateNumber==null && other.getPlateNumber()==null) || 
             (this.plateNumber!=null &&
              this.plateNumber.equals(other.getPlateNumber()))) &&
            ((this.power==null && other.getPower()==null) || 
             (this.power!=null &&
              this.power.equals(other.getPower()))) &&
            ((this.powerWeight==null && other.getPowerWeight()==null) || 
             (this.powerWeight!=null &&
              this.powerWeight.equals(other.getPowerWeight()))) &&
            ((this.seatPlaces==null && other.getSeatPlaces()==null) || 
             (this.seatPlaces!=null &&
              this.seatPlaces.equals(other.getSeatPlaces()))) &&
            ((this.serialNumber==null && other.getSerialNumber()==null) || 
             (this.serialNumber!=null &&
              this.serialNumber.equals(other.getSerialNumber()))) &&
            ((this.standUpPlaces==null && other.getStandUpPlaces()==null) || 
             (this.standUpPlaces!=null &&
              this.standUpPlaces.equals(other.getStandUpPlaces()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.tara==null && other.getTara()==null) || 
             (this.tara!=null &&
              this.tara.equals(other.getTara()))) &&
            ((this.variant==null && other.getVariant()==null) || 
             (this.variant!=null &&
              this.variant.equals(other.getVariant()))) &&
            ((this.vehicleColor==null && other.getVehicleColor()==null) || 
             (this.vehicleColor!=null &&
              this.vehicleColor.equals(other.getVehicleColor()))) &&
            ((this.vehicleFuel==null && other.getVehicleFuel()==null) || 
             (this.vehicleFuel!=null &&
              this.vehicleFuel.equals(other.getVehicleFuel()))) &&
            ((this.vehicleHomologation==null && other.getVehicleHomologation()==null) || 
             (this.vehicleHomologation!=null &&
              this.vehicleHomologation.equals(other.getVehicleHomologation()))) &&
            ((this.vehicleKind==null && other.getVehicleKind()==null) || 
             (this.vehicleKind!=null &&
              this.vehicleKind.equals(other.getVehicleKind()))) &&
            ((this.vehicleOrigin==null && other.getVehicleOrigin()==null) || 
             (this.vehicleOrigin!=null &&
              this.vehicleOrigin.equals(other.getVehicleOrigin()))) &&
            ((this.vehicleOwner==null && other.getVehicleOwner()==null) || 
             (this.vehicleOwner!=null &&
              this.vehicleOwner.equals(other.getVehicleOwner()))) &&
            ((this.vehiclePurpose==null && other.getVehiclePurpose()==null) || 
             (this.vehiclePurpose!=null &&
              this.vehiclePurpose.equals(other.getVehiclePurpose()))) &&
            ((this.vehicleVersion==null && other.getVehicleVersion()==null) || 
             (this.vehicleVersion!=null &&
              this.vehicleVersion.equals(other.getVehicleVersion())));
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
        if (getBrand() != null) {
            _hashCode += getBrand().hashCode();
        }
        if (getCEM() != null) {
            _hashCode += getCEM().hashCode();
        }
        if (getCodeHomologation() != null) {
            _hashCode += getCodeHomologation().hashCode();
        }
        if (getCodeITV() != null) {
            _hashCode += getCodeITV().hashCode();
        }
        if (getCubicCapacity() != null) {
            _hashCode += getCubicCapacity().hashCode();
        }
        if (getExpirationDateITV() != null) {
            _hashCode += getExpirationDateITV().hashCode();
        }
        if (getFilingDate() != null) {
            _hashCode += getFilingDate().hashCode();
        }
        if (getHas05() != null) {
            _hashCode += getHas05().hashCode();
        }
        if (getHas06() != null) {
            _hashCode += getHas06().hashCode();
        }
        if (getHas576() != null) {
            _hashCode += getHas576().hashCode();
        }
        if (getItvCardType() != null) {
            _hashCode += getItvCardType().hashCode();
        }
        if (getKindITV() != null) {
            _hashCode += getKindITV().hashCode();
        }
        if (getLicense() != null) {
            _hashCode += getLicense().hashCode();
        }
        if (getLimitationDate() != null) {
            _hashCode += getLimitationDate().hashCode();
        }
        if (getLocalDGTDivision() != null) {
            _hashCode += getLocalDGTDivision().hashCode();
        }
        if (getMasa() != null) {
            _hashCode += getMasa().hashCode();
        }
        if (getMatriculationDate() != null) {
            _hashCode += getMatriculationDate().hashCode();
        }
        if (getMaxPlaces() != null) {
            _hashCode += getMaxPlaces().hashCode();
        }
        if (getModel() != null) {
            _hashCode += getModel().hashCode();
        }
        if (getMtma() != null) {
            _hashCode += getMtma().hashCode();
        }
        if (getPlateNumber() != null) {
            _hashCode += getPlateNumber().hashCode();
        }
        if (getPower() != null) {
            _hashCode += getPower().hashCode();
        }
        if (getPowerWeight() != null) {
            _hashCode += getPowerWeight().hashCode();
        }
        if (getSeatPlaces() != null) {
            _hashCode += getSeatPlaces().hashCode();
        }
        if (getSerialNumber() != null) {
            _hashCode += getSerialNumber().hashCode();
        }
        if (getStandUpPlaces() != null) {
            _hashCode += getStandUpPlaces().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getTara() != null) {
            _hashCode += getTara().hashCode();
        }
        if (getVariant() != null) {
            _hashCode += getVariant().hashCode();
        }
        if (getVehicleColor() != null) {
            _hashCode += getVehicleColor().hashCode();
        }
        if (getVehicleFuel() != null) {
            _hashCode += getVehicleFuel().hashCode();
        }
        if (getVehicleHomologation() != null) {
            _hashCode += getVehicleHomologation().hashCode();
        }
        if (getVehicleKind() != null) {
            _hashCode += getVehicleKind().hashCode();
        }
        if (getVehicleOrigin() != null) {
            _hashCode += getVehicleOrigin().hashCode();
        }
        if (getVehicleOwner() != null) {
            _hashCode += getVehicleOwner().hashCode();
        }
        if (getVehiclePurpose() != null) {
            _hashCode += getVehiclePurpose().hashCode();
        }
        if (getVehicleVersion() != null) {
            _hashCode += getVehicleVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MateCardMatriculation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateCardMatriculation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brand");
        elemField.setXmlName(new javax.xml.namespace.QName("", "brand"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CEM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CEM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codeHomologation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codeHomologation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codeITV");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codeITV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cubicCapacity");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cubicCapacity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expirationDateITV");
        elemField.setXmlName(new javax.xml.namespace.QName("", "expirationDateITV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filingDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "filingDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("has05");
        elemField.setXmlName(new javax.xml.namespace.QName("", "has05"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("has06");
        elemField.setXmlName(new javax.xml.namespace.QName("", "has06"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("has576");
        elemField.setXmlName(new javax.xml.namespace.QName("", "has576"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itvCardType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "itvCardType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "itvCardType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("kindITV");
        elemField.setXmlName(new javax.xml.namespace.QName("", "kindITV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("license");
        elemField.setXmlName(new javax.xml.namespace.QName("", "license"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateCardLicense"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limitationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limitationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localDGTDivision");
        elemField.setXmlName(new javax.xml.namespace.QName("", "localDGTDivision"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("masa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "masa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matriculationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matriculationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxPlaces");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxPlaces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("model");
        elemField.setXmlName(new javax.xml.namespace.QName("", "model"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mtma");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mtma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("plateNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "plateNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("power");
        elemField.setXmlName(new javax.xml.namespace.QName("", "power"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("powerWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("", "powerWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seatPlaces");
        elemField.setXmlName(new javax.xml.namespace.QName("", "seatPlaces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serialNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("standUpPlaces");
        elemField.setXmlName(new javax.xml.namespace.QName("", "standUpPlaces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateMatriculationState"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tara");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tara"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variant");
        elemField.setXmlName(new javax.xml.namespace.QName("", "variant"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleColor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vehicleColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleFuel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vehicleFuel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleHomologation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vehicleHomologation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleKind");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vehicleKind"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleOrigin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vehicleOrigin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleOwner");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vehicleOwner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateCardVehicleOwner"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehiclePurpose");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vehiclePurpose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vehicleVersion"));
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
