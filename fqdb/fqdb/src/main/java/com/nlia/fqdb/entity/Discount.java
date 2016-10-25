package com.nlia.fqdb.entity;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.nlia.fqdb.service.impl.FlightBaseManager;
import com.nlia.fqdb.vo.RuleGroup;

@SuppressWarnings("rawtypes")
@Entity
@Table(name = "AIRCRAFT_DISCOUNT")
public class Discount implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "AAA_ID")
    private Long aircraftAirlineAlterationId;

    public Long getAircraftAirlineAlterationId() {
        return aircraftAirlineAlterationId;
    }

    public void setAircraftAirlineAlterationId(Long aircraftAirlineAlterationId) {
        this.aircraftAirlineAlterationId = aircraftAirlineAlterationId;
    }
   
//    @ManyToOne
//    @JoinColumn(name = "AAA_Id", referencedColumnName = "id")
//    private AircraftAirlineAlteration aircraftAirlineAlteration;
//    
//    public AircraftAirlineAlteration getAircraftAirlineAlteration() {
//        return aircraftAirlineAlteration;
//    }
//
//    public void setAircraftAirlineAlteration(
//            AircraftAirlineAlteration aircraftAirlineAlteration) {
//        this.aircraftAirlineAlteration = aircraftAirlineAlteration;
//    }

    @Column(name="CHARGETYPE_ID")
    private Long chargeTypeId;
    
    @Column(name="CHARGESUBJECT_ID")
    private Long chargeSubjectId;
    
    @Column(name="DISCOUNT")
    private double discount;

    @Column(name="REMOVE_FLAG")
    private String removeFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Long getChargeTypeId() {
        return chargeTypeId;
    }

    public void setChargeTypeId(Long chargeTypeId) {
        this.chargeTypeId = chargeTypeId;
    }

    public Long getChargeSubjectId() {
        return chargeSubjectId;
    }

    public void setChargeSubjectId(Long chargeSubjectId) {
        this.chargeSubjectId = chargeSubjectId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(String removeFlag) {
        this.removeFlag = removeFlag;
    }
    
    @Column(name="CHARGE_TYPE_NAME")
    private String  chargeTypeName;
    
    @Column(name="CHARGE_SUBJECT_NAME")
    private String chargeSubjectName;

    public String getChargeTypeName() {
        return chargeTypeName;
    }

    public void setChargeTypeName(String chargeTypeName) {
        this.chargeTypeName = chargeTypeName;
    }

    public String getChargeSubjectName() {
        return chargeSubjectName;
    }

    public void setChargeSubjectName(String chargeSubjectName) {
        this.chargeSubjectName = chargeSubjectName;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
