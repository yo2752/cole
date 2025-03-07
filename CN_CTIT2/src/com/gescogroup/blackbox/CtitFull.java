/**
 * CtitFull.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CtitFull  extends com.gescogroup.blackbox.Dossier  implements java.io.Serializable {
    private java.lang.Boolean agricultureVehicle;

    private java.lang.String buyerFiscalId;

    private java.lang.String buyerFullName;

    private java.lang.String CEMIEDMTExemption;

    private java.lang.String CEMIEDMTNoSubjection;

    private java.lang.String CEMIEDMTPayment;

    private java.lang.String CETITPExemption;

    private java.lang.String CETITPNoSubjection;

    private java.lang.String CETITPPayment;

    private com.gescogroup.blackbox.CtitFullAdvise[] ctitFullAdvise;

    private com.gescogroup.blackbox.CtitFullImpediment[] ctitFullImpediment;

    private java.lang.Boolean DUA;

    private java.lang.Boolean IVA;

    private java.lang.Boolean IVTM;

    private java.lang.String inputRegNumber;

    private com.gescogroup.blackbox.CtitFullLicense license;

    private java.util.Calendar matriculationDate;

    private java.lang.String motive;

    private java.lang.String outputRegNumber;

    private java.lang.String plateNumber;

    private java.lang.Boolean purposeChange;

    private java.lang.String purposeChangeTaxCode;

    private java.lang.Boolean renting;

    private com.gescogroup.blackbox.CtitFullReport report;

    private java.lang.String reportTaxCode;

    private java.lang.String resultCode;

    private java.lang.String sellerFiscalId;

    private java.lang.String sellerFullName;

    private com.gescogroup.blackbox.CtitStatus status;

    private java.lang.String vehicleKind;

    private java.lang.String vehiclePurpose;

    public CtitFull() {
    }

    public CtitFull(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           com.gescogroup.blackbox.User createdBy,
           com.gescogroup.blackbox.User modifiedBy,
           com.gescogroup.blackbox.Agency agency,
           com.gescogroup.blackbox.Agent agent,
           java.lang.String dgtTaxCode,
           java.lang.String dossierNumber,
           java.lang.Boolean agricultureVehicle,
           java.lang.String buyerFiscalId,
           java.lang.String buyerFullName,
           java.lang.String CEMIEDMTExemption,
           java.lang.String CEMIEDMTNoSubjection,
           java.lang.String CEMIEDMTPayment,
           java.lang.String CETITPExemption,
           java.lang.String CETITPNoSubjection,
           java.lang.String CETITPPayment,
           com.gescogroup.blackbox.CtitFullAdvise[] ctitFullAdvise,
           com.gescogroup.blackbox.CtitFullImpediment[] ctitFullImpediment,
           java.lang.Boolean DUA,
           java.lang.Boolean IVA,
           java.lang.Boolean IVTM,
           java.lang.String inputRegNumber,
           com.gescogroup.blackbox.CtitFullLicense license,
           java.util.Calendar matriculationDate,
           java.lang.String motive,
           java.lang.String outputRegNumber,
           java.lang.String plateNumber,
           java.lang.Boolean purposeChange,
           java.lang.String purposeChangeTaxCode,
           java.lang.Boolean renting,
           com.gescogroup.blackbox.CtitFullReport report,
           java.lang.String reportTaxCode,
           java.lang.String resultCode,
           java.lang.String sellerFiscalId,
           java.lang.String sellerFullName,
           com.gescogroup.blackbox.CtitStatus status,
           java.lang.String vehicleKind,
           java.lang.String vehiclePurpose) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels,
            createdBy,
            modifiedBy,
            agency,
            agent,
            dgtTaxCode,
            dossierNumber);
        this.agricultureVehicle = agricultureVehicle;
        this.buyerFiscalId = buyerFiscalId;
        this.buyerFullName = buyerFullName;
        this.CEMIEDMTExemption = CEMIEDMTExemption;
        this.CEMIEDMTNoSubjection = CEMIEDMTNoSubjection;
        this.CEMIEDMTPayment = CEMIEDMTPayment;
        this.CETITPExemption = CETITPExemption;
        this.CETITPNoSubjection = CETITPNoSubjection;
        this.CETITPPayment = CETITPPayment;
        this.ctitFullAdvise = ctitFullAdvise;
        this.ctitFullImpediment = ctitFullImpediment;
        this.DUA = DUA;
        this.IVA = IVA;
        this.IVTM = IVTM;
        this.inputRegNumber = inputRegNumber;
        this.license = license;
        this.matriculationDate = matriculationDate;
        this.motive = motive;
        this.outputRegNumber = outputRegNumber;
        this.plateNumber = plateNumber;
        this.purposeChange = purposeChange;
        this.purposeChangeTaxCode = purposeChangeTaxCode;
        this.renting = renting;
        this.report = report;
        this.reportTaxCode = reportTaxCode;
        this.resultCode = resultCode;
        this.sellerFiscalId = sellerFiscalId;
        this.sellerFullName = sellerFullName;
        this.status = status;
        this.vehicleKind = vehicleKind;
        this.vehiclePurpose = vehiclePurpose;
    }


    /**
     * Gets the agricultureVehicle value for this CtitFull.
     * 
     * @return agricultureVehicle
     */
    public java.lang.Boolean getAgricultureVehicle() {
        return agricultureVehicle;
    }


    /**
     * Sets the agricultureVehicle value for this CtitFull.
     * 
     * @param agricultureVehicle
     */
    public void setAgricultureVehicle(java.lang.Boolean agricultureVehicle) {
        this.agricultureVehicle = agricultureVehicle;
    }


    /**
     * Gets the buyerFiscalId value for this CtitFull.
     * 
     * @return buyerFiscalId
     */
    public java.lang.String getBuyerFiscalId() {
        return buyerFiscalId;
    }


    /**
     * Sets the buyerFiscalId value for this CtitFull.
     * 
     * @param buyerFiscalId
     */
    public void setBuyerFiscalId(java.lang.String buyerFiscalId) {
        this.buyerFiscalId = buyerFiscalId;
    }


    /**
     * Gets the buyerFullName value for this CtitFull.
     * 
     * @return buyerFullName
     */
    public java.lang.String getBuyerFullName() {
        return buyerFullName;
    }


    /**
     * Sets the buyerFullName value for this CtitFull.
     * 
     * @param buyerFullName
     */
    public void setBuyerFullName(java.lang.String buyerFullName) {
        this.buyerFullName = buyerFullName;
    }


    /**
     * Gets the CEMIEDMTExemption value for this CtitFull.
     * 
     * @return CEMIEDMTExemption
     */
    public java.lang.String getCEMIEDMTExemption() {
        return CEMIEDMTExemption;
    }


    /**
     * Sets the CEMIEDMTExemption value for this CtitFull.
     * 
     * @param CEMIEDMTExemption
     */
    public void setCEMIEDMTExemption(java.lang.String CEMIEDMTExemption) {
        this.CEMIEDMTExemption = CEMIEDMTExemption;
    }


    /**
     * Gets the CEMIEDMTNoSubjection value for this CtitFull.
     * 
     * @return CEMIEDMTNoSubjection
     */
    public java.lang.String getCEMIEDMTNoSubjection() {
        return CEMIEDMTNoSubjection;
    }


    /**
     * Sets the CEMIEDMTNoSubjection value for this CtitFull.
     * 
     * @param CEMIEDMTNoSubjection
     */
    public void setCEMIEDMTNoSubjection(java.lang.String CEMIEDMTNoSubjection) {
        this.CEMIEDMTNoSubjection = CEMIEDMTNoSubjection;
    }


    /**
     * Gets the CEMIEDMTPayment value for this CtitFull.
     * 
     * @return CEMIEDMTPayment
     */
    public java.lang.String getCEMIEDMTPayment() {
        return CEMIEDMTPayment;
    }


    /**
     * Sets the CEMIEDMTPayment value for this CtitFull.
     * 
     * @param CEMIEDMTPayment
     */
    public void setCEMIEDMTPayment(java.lang.String CEMIEDMTPayment) {
        this.CEMIEDMTPayment = CEMIEDMTPayment;
    }


    /**
     * Gets the CETITPExemption value for this CtitFull.
     * 
     * @return CETITPExemption
     */
    public java.lang.String getCETITPExemption() {
        return CETITPExemption;
    }


    /**
     * Sets the CETITPExemption value for this CtitFull.
     * 
     * @param CETITPExemption
     */
    public void setCETITPExemption(java.lang.String CETITPExemption) {
        this.CETITPExemption = CETITPExemption;
    }


    /**
     * Gets the CETITPNoSubjection value for this CtitFull.
     * 
     * @return CETITPNoSubjection
     */
    public java.lang.String getCETITPNoSubjection() {
        return CETITPNoSubjection;
    }


    /**
     * Sets the CETITPNoSubjection value for this CtitFull.
     * 
     * @param CETITPNoSubjection
     */
    public void setCETITPNoSubjection(java.lang.String CETITPNoSubjection) {
        this.CETITPNoSubjection = CETITPNoSubjection;
    }


    /**
     * Gets the CETITPPayment value for this CtitFull.
     * 
     * @return CETITPPayment
     */
    public java.lang.String getCETITPPayment() {
        return CETITPPayment;
    }


    /**
     * Sets the CETITPPayment value for this CtitFull.
     * 
     * @param CETITPPayment
     */
    public void setCETITPPayment(java.lang.String CETITPPayment) {
        this.CETITPPayment = CETITPPayment;
    }


    /**
     * Gets the ctitFullAdvise value for this CtitFull.
     * 
     * @return ctitFullAdvise
     */
    public com.gescogroup.blackbox.CtitFullAdvise[] getCtitFullAdvise() {
        return ctitFullAdvise;
    }


    /**
     * Sets the ctitFullAdvise value for this CtitFull.
     * 
     * @param ctitFullAdvise
     */
    public void setCtitFullAdvise(com.gescogroup.blackbox.CtitFullAdvise[] ctitFullAdvise) {
        this.ctitFullAdvise = ctitFullAdvise;
    }

    public com.gescogroup.blackbox.CtitFullAdvise getCtitFullAdvise(int i) {
        return this.ctitFullAdvise[i];
    }

    public void setCtitFullAdvise(int i, com.gescogroup.blackbox.CtitFullAdvise _value) {
        this.ctitFullAdvise[i] = _value;
    }


    /**
     * Gets the ctitFullImpediment value for this CtitFull.
     * 
     * @return ctitFullImpediment
     */
    public com.gescogroup.blackbox.CtitFullImpediment[] getCtitFullImpediment() {
        return ctitFullImpediment;
    }


    /**
     * Sets the ctitFullImpediment value for this CtitFull.
     * 
     * @param ctitFullImpediment
     */
    public void setCtitFullImpediment(com.gescogroup.blackbox.CtitFullImpediment[] ctitFullImpediment) {
        this.ctitFullImpediment = ctitFullImpediment;
    }

    public com.gescogroup.blackbox.CtitFullImpediment getCtitFullImpediment(int i) {
        return this.ctitFullImpediment[i];
    }

    public void setCtitFullImpediment(int i, com.gescogroup.blackbox.CtitFullImpediment _value) {
        this.ctitFullImpediment[i] = _value;
    }


    /**
     * Gets the DUA value for this CtitFull.
     * 
     * @return DUA
     */
    public java.lang.Boolean getDUA() {
        return DUA;
    }


    /**
     * Sets the DUA value for this CtitFull.
     * 
     * @param DUA
     */
    public void setDUA(java.lang.Boolean DUA) {
        this.DUA = DUA;
    }


    /**
     * Gets the IVA value for this CtitFull.
     * 
     * @return IVA
     */
    public java.lang.Boolean getIVA() {
        return IVA;
    }


    /**
     * Sets the IVA value for this CtitFull.
     * 
     * @param IVA
     */
    public void setIVA(java.lang.Boolean IVA) {
        this.IVA = IVA;
    }


    /**
     * Gets the IVTM value for this CtitFull.
     * 
     * @return IVTM
     */
    public java.lang.Boolean getIVTM() {
        return IVTM;
    }


    /**
     * Sets the IVTM value for this CtitFull.
     * 
     * @param IVTM
     */
    public void setIVTM(java.lang.Boolean IVTM) {
        this.IVTM = IVTM;
    }


    /**
     * Gets the inputRegNumber value for this CtitFull.
     * 
     * @return inputRegNumber
     */
    public java.lang.String getInputRegNumber() {
        return inputRegNumber;
    }


    /**
     * Sets the inputRegNumber value for this CtitFull.
     * 
     * @param inputRegNumber
     */
    public void setInputRegNumber(java.lang.String inputRegNumber) {
        this.inputRegNumber = inputRegNumber;
    }


    /**
     * Gets the license value for this CtitFull.
     * 
     * @return license
     */
    public com.gescogroup.blackbox.CtitFullLicense getLicense() {
        return license;
    }


    /**
     * Sets the license value for this CtitFull.
     * 
     * @param license
     */
    public void setLicense(com.gescogroup.blackbox.CtitFullLicense license) {
        this.license = license;
    }


    /**
     * Gets the matriculationDate value for this CtitFull.
     * 
     * @return matriculationDate
     */
    public java.util.Calendar getMatriculationDate() {
        return matriculationDate;
    }


    /**
     * Sets the matriculationDate value for this CtitFull.
     * 
     * @param matriculationDate
     */
    public void setMatriculationDate(java.util.Calendar matriculationDate) {
        this.matriculationDate = matriculationDate;
    }


    /**
     * Gets the motive value for this CtitFull.
     * 
     * @return motive
     */
    public java.lang.String getMotive() {
        return motive;
    }


    /**
     * Sets the motive value for this CtitFull.
     * 
     * @param motive
     */
    public void setMotive(java.lang.String motive) {
        this.motive = motive;
    }


    /**
     * Gets the outputRegNumber value for this CtitFull.
     * 
     * @return outputRegNumber
     */
    public java.lang.String getOutputRegNumber() {
        return outputRegNumber;
    }


    /**
     * Sets the outputRegNumber value for this CtitFull.
     * 
     * @param outputRegNumber
     */
    public void setOutputRegNumber(java.lang.String outputRegNumber) {
        this.outputRegNumber = outputRegNumber;
    }


    /**
     * Gets the plateNumber value for this CtitFull.
     * 
     * @return plateNumber
     */
    public java.lang.String getPlateNumber() {
        return plateNumber;
    }


    /**
     * Sets the plateNumber value for this CtitFull.
     * 
     * @param plateNumber
     */
    public void setPlateNumber(java.lang.String plateNumber) {
        this.plateNumber = plateNumber;
    }


    /**
     * Gets the purposeChange value for this CtitFull.
     * 
     * @return purposeChange
     */
    public java.lang.Boolean getPurposeChange() {
        return purposeChange;
    }


    /**
     * Sets the purposeChange value for this CtitFull.
     * 
     * @param purposeChange
     */
    public void setPurposeChange(java.lang.Boolean purposeChange) {
        this.purposeChange = purposeChange;
    }


    /**
     * Gets the purposeChangeTaxCode value for this CtitFull.
     * 
     * @return purposeChangeTaxCode
     */
    public java.lang.String getPurposeChangeTaxCode() {
        return purposeChangeTaxCode;
    }


    /**
     * Sets the purposeChangeTaxCode value for this CtitFull.
     * 
     * @param purposeChangeTaxCode
     */
    public void setPurposeChangeTaxCode(java.lang.String purposeChangeTaxCode) {
        this.purposeChangeTaxCode = purposeChangeTaxCode;
    }


    /**
     * Gets the renting value for this CtitFull.
     * 
     * @return renting
     */
    public java.lang.Boolean getRenting() {
        return renting;
    }


    /**
     * Sets the renting value for this CtitFull.
     * 
     * @param renting
     */
    public void setRenting(java.lang.Boolean renting) {
        this.renting = renting;
    }


    /**
     * Gets the report value for this CtitFull.
     * 
     * @return report
     */
    public com.gescogroup.blackbox.CtitFullReport getReport() {
        return report;
    }


    /**
     * Sets the report value for this CtitFull.
     * 
     * @param report
     */
    public void setReport(com.gescogroup.blackbox.CtitFullReport report) {
        this.report = report;
    }


    /**
     * Gets the reportTaxCode value for this CtitFull.
     * 
     * @return reportTaxCode
     */
    public java.lang.String getReportTaxCode() {
        return reportTaxCode;
    }


    /**
     * Sets the reportTaxCode value for this CtitFull.
     * 
     * @param reportTaxCode
     */
    public void setReportTaxCode(java.lang.String reportTaxCode) {
        this.reportTaxCode = reportTaxCode;
    }


    /**
     * Gets the resultCode value for this CtitFull.
     * 
     * @return resultCode
     */
    public java.lang.String getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this CtitFull.
     * 
     * @param resultCode
     */
    public void setResultCode(java.lang.String resultCode) {
        this.resultCode = resultCode;
    }


    /**
     * Gets the sellerFiscalId value for this CtitFull.
     * 
     * @return sellerFiscalId
     */
    public java.lang.String getSellerFiscalId() {
        return sellerFiscalId;
    }


    /**
     * Sets the sellerFiscalId value for this CtitFull.
     * 
     * @param sellerFiscalId
     */
    public void setSellerFiscalId(java.lang.String sellerFiscalId) {
        this.sellerFiscalId = sellerFiscalId;
    }


    /**
     * Gets the sellerFullName value for this CtitFull.
     * 
     * @return sellerFullName
     */
    public java.lang.String getSellerFullName() {
        return sellerFullName;
    }


    /**
     * Sets the sellerFullName value for this CtitFull.
     * 
     * @param sellerFullName
     */
    public void setSellerFullName(java.lang.String sellerFullName) {
        this.sellerFullName = sellerFullName;
    }


    /**
     * Gets the status value for this CtitFull.
     * 
     * @return status
     */
    public com.gescogroup.blackbox.CtitStatus getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CtitFull.
     * 
     * @param status
     */
    public void setStatus(com.gescogroup.blackbox.CtitStatus status) {
        this.status = status;
    }


    /**
     * Gets the vehicleKind value for this CtitFull.
     * 
     * @return vehicleKind
     */
    public java.lang.String getVehicleKind() {
        return vehicleKind;
    }


    /**
     * Sets the vehicleKind value for this CtitFull.
     * 
     * @param vehicleKind
     */
    public void setVehicleKind(java.lang.String vehicleKind) {
        this.vehicleKind = vehicleKind;
    }


    /**
     * Gets the vehiclePurpose value for this CtitFull.
     * 
     * @return vehiclePurpose
     */
    public java.lang.String getVehiclePurpose() {
        return vehiclePurpose;
    }


    /**
     * Sets the vehiclePurpose value for this CtitFull.
     * 
     * @param vehiclePurpose
     */
    public void setVehiclePurpose(java.lang.String vehiclePurpose) {
        this.vehiclePurpose = vehiclePurpose;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitFull)) return false;
        CtitFull other = (CtitFull) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.agricultureVehicle==null && other.getAgricultureVehicle()==null) || 
             (this.agricultureVehicle!=null &&
              this.agricultureVehicle.equals(other.getAgricultureVehicle()))) &&
            ((this.buyerFiscalId==null && other.getBuyerFiscalId()==null) || 
             (this.buyerFiscalId!=null &&
              this.buyerFiscalId.equals(other.getBuyerFiscalId()))) &&
            ((this.buyerFullName==null && other.getBuyerFullName()==null) || 
             (this.buyerFullName!=null &&
              this.buyerFullName.equals(other.getBuyerFullName()))) &&
            ((this.CEMIEDMTExemption==null && other.getCEMIEDMTExemption()==null) || 
             (this.CEMIEDMTExemption!=null &&
              this.CEMIEDMTExemption.equals(other.getCEMIEDMTExemption()))) &&
            ((this.CEMIEDMTNoSubjection==null && other.getCEMIEDMTNoSubjection()==null) || 
             (this.CEMIEDMTNoSubjection!=null &&
              this.CEMIEDMTNoSubjection.equals(other.getCEMIEDMTNoSubjection()))) &&
            ((this.CEMIEDMTPayment==null && other.getCEMIEDMTPayment()==null) || 
             (this.CEMIEDMTPayment!=null &&
              this.CEMIEDMTPayment.equals(other.getCEMIEDMTPayment()))) &&
            ((this.CETITPExemption==null && other.getCETITPExemption()==null) || 
             (this.CETITPExemption!=null &&
              this.CETITPExemption.equals(other.getCETITPExemption()))) &&
            ((this.CETITPNoSubjection==null && other.getCETITPNoSubjection()==null) || 
             (this.CETITPNoSubjection!=null &&
              this.CETITPNoSubjection.equals(other.getCETITPNoSubjection()))) &&
            ((this.CETITPPayment==null && other.getCETITPPayment()==null) || 
             (this.CETITPPayment!=null &&
              this.CETITPPayment.equals(other.getCETITPPayment()))) &&
            ((this.ctitFullAdvise==null && other.getCtitFullAdvise()==null) || 
             (this.ctitFullAdvise!=null &&
              java.util.Arrays.equals(this.ctitFullAdvise, other.getCtitFullAdvise()))) &&
            ((this.ctitFullImpediment==null && other.getCtitFullImpediment()==null) || 
             (this.ctitFullImpediment!=null &&
              java.util.Arrays.equals(this.ctitFullImpediment, other.getCtitFullImpediment()))) &&
            ((this.DUA==null && other.getDUA()==null) || 
             (this.DUA!=null &&
              this.DUA.equals(other.getDUA()))) &&
            ((this.IVA==null && other.getIVA()==null) || 
             (this.IVA!=null &&
              this.IVA.equals(other.getIVA()))) &&
            ((this.IVTM==null && other.getIVTM()==null) || 
             (this.IVTM!=null &&
              this.IVTM.equals(other.getIVTM()))) &&
            ((this.inputRegNumber==null && other.getInputRegNumber()==null) || 
             (this.inputRegNumber!=null &&
              this.inputRegNumber.equals(other.getInputRegNumber()))) &&
            ((this.license==null && other.getLicense()==null) || 
             (this.license!=null &&
              this.license.equals(other.getLicense()))) &&
            ((this.matriculationDate==null && other.getMatriculationDate()==null) || 
             (this.matriculationDate!=null &&
              this.matriculationDate.equals(other.getMatriculationDate()))) &&
            ((this.motive==null && other.getMotive()==null) || 
             (this.motive!=null &&
              this.motive.equals(other.getMotive()))) &&
            ((this.outputRegNumber==null && other.getOutputRegNumber()==null) || 
             (this.outputRegNumber!=null &&
              this.outputRegNumber.equals(other.getOutputRegNumber()))) &&
            ((this.plateNumber==null && other.getPlateNumber()==null) || 
             (this.plateNumber!=null &&
              this.plateNumber.equals(other.getPlateNumber()))) &&
            ((this.purposeChange==null && other.getPurposeChange()==null) || 
             (this.purposeChange!=null &&
              this.purposeChange.equals(other.getPurposeChange()))) &&
            ((this.purposeChangeTaxCode==null && other.getPurposeChangeTaxCode()==null) || 
             (this.purposeChangeTaxCode!=null &&
              this.purposeChangeTaxCode.equals(other.getPurposeChangeTaxCode()))) &&
            ((this.renting==null && other.getRenting()==null) || 
             (this.renting!=null &&
              this.renting.equals(other.getRenting()))) &&
            ((this.report==null && other.getReport()==null) || 
             (this.report!=null &&
              this.report.equals(other.getReport()))) &&
            ((this.reportTaxCode==null && other.getReportTaxCode()==null) || 
             (this.reportTaxCode!=null &&
              this.reportTaxCode.equals(other.getReportTaxCode()))) &&
            ((this.resultCode==null && other.getResultCode()==null) || 
             (this.resultCode!=null &&
              this.resultCode.equals(other.getResultCode()))) &&
            ((this.sellerFiscalId==null && other.getSellerFiscalId()==null) || 
             (this.sellerFiscalId!=null &&
              this.sellerFiscalId.equals(other.getSellerFiscalId()))) &&
            ((this.sellerFullName==null && other.getSellerFullName()==null) || 
             (this.sellerFullName!=null &&
              this.sellerFullName.equals(other.getSellerFullName()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.vehicleKind==null && other.getVehicleKind()==null) || 
             (this.vehicleKind!=null &&
              this.vehicleKind.equals(other.getVehicleKind()))) &&
            ((this.vehiclePurpose==null && other.getVehiclePurpose()==null) || 
             (this.vehiclePurpose!=null &&
              this.vehiclePurpose.equals(other.getVehiclePurpose())));
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
        if (getAgricultureVehicle() != null) {
            _hashCode += getAgricultureVehicle().hashCode();
        }
        if (getBuyerFiscalId() != null) {
            _hashCode += getBuyerFiscalId().hashCode();
        }
        if (getBuyerFullName() != null) {
            _hashCode += getBuyerFullName().hashCode();
        }
        if (getCEMIEDMTExemption() != null) {
            _hashCode += getCEMIEDMTExemption().hashCode();
        }
        if (getCEMIEDMTNoSubjection() != null) {
            _hashCode += getCEMIEDMTNoSubjection().hashCode();
        }
        if (getCEMIEDMTPayment() != null) {
            _hashCode += getCEMIEDMTPayment().hashCode();
        }
        if (getCETITPExemption() != null) {
            _hashCode += getCETITPExemption().hashCode();
        }
        if (getCETITPNoSubjection() != null) {
            _hashCode += getCETITPNoSubjection().hashCode();
        }
        if (getCETITPPayment() != null) {
            _hashCode += getCETITPPayment().hashCode();
        }
        if (getCtitFullAdvise() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCtitFullAdvise());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCtitFullAdvise(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCtitFullImpediment() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCtitFullImpediment());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCtitFullImpediment(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDUA() != null) {
            _hashCode += getDUA().hashCode();
        }
        if (getIVA() != null) {
            _hashCode += getIVA().hashCode();
        }
        if (getIVTM() != null) {
            _hashCode += getIVTM().hashCode();
        }
        if (getInputRegNumber() != null) {
            _hashCode += getInputRegNumber().hashCode();
        }
        if (getLicense() != null) {
            _hashCode += getLicense().hashCode();
        }
        if (getMatriculationDate() != null) {
            _hashCode += getMatriculationDate().hashCode();
        }
        if (getMotive() != null) {
            _hashCode += getMotive().hashCode();
        }
        if (getOutputRegNumber() != null) {
            _hashCode += getOutputRegNumber().hashCode();
        }
        if (getPlateNumber() != null) {
            _hashCode += getPlateNumber().hashCode();
        }
        if (getPurposeChange() != null) {
            _hashCode += getPurposeChange().hashCode();
        }
        if (getPurposeChangeTaxCode() != null) {
            _hashCode += getPurposeChangeTaxCode().hashCode();
        }
        if (getRenting() != null) {
            _hashCode += getRenting().hashCode();
        }
        if (getReport() != null) {
            _hashCode += getReport().hashCode();
        }
        if (getReportTaxCode() != null) {
            _hashCode += getReportTaxCode().hashCode();
        }
        if (getResultCode() != null) {
            _hashCode += getResultCode().hashCode();
        }
        if (getSellerFiscalId() != null) {
            _hashCode += getSellerFiscalId().hashCode();
        }
        if (getSellerFullName() != null) {
            _hashCode += getSellerFullName().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getVehicleKind() != null) {
            _hashCode += getVehicleKind().hashCode();
        }
        if (getVehiclePurpose() != null) {
            _hashCode += getVehiclePurpose().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CtitFull.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFull"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agricultureVehicle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agricultureVehicle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buyerFiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "buyerFiscalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buyerFullName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "buyerFullName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CEMIEDMTExemption");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CEMIEDMTExemption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CEMIEDMTNoSubjection");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CEMIEDMTNoSubjection"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CEMIEDMTPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CEMIEDMTPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CETITPExemption");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CETITPExemption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CETITPNoSubjection");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CETITPNoSubjection"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CETITPPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CETITPPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitFullAdvise");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitFullAdvise"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFullAdvise"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitFullImpediment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitFullImpediment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFullImpediment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DUA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DUA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IVA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IVA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IVTM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IVTM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inputRegNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inputRegNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("license");
        elemField.setXmlName(new javax.xml.namespace.QName("", "license"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFullLicense"));
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
        elemField.setFieldName("motive");
        elemField.setXmlName(new javax.xml.namespace.QName("", "motive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputRegNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "outputRegNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("purposeChange");
        elemField.setXmlName(new javax.xml.namespace.QName("", "purposeChange"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purposeChangeTaxCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "purposeChangeTaxCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("renting");
        elemField.setXmlName(new javax.xml.namespace.QName("", "renting"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("report");
        elemField.setXmlName(new javax.xml.namespace.QName("", "report"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitFullReport"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportTaxCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reportTaxCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellerFiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sellerFiscalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellerFullName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sellerFullName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitStatus"));
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
        elemField.setFieldName("vehiclePurpose");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vehiclePurpose"));
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
