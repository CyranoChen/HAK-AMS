package com.nlia.fqdb.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.math.BigDecimal;

/**
 * The persistent class for the FLIGHT_LOAD_DATA database table.
 * 
 */
@Entity
@Table(name = "FLIGHT_LOAD_DATA")
public class FlightLoadData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "JHRQ")
    @Temporal(TIMESTAMP)
    private Date jhrq;
    @Column(name = "SJRQ")
    @Temporal(TIMESTAMP)
    private Date sjrq;
    @Column(name = "CYR")
    private String cyr;
    @Column(name = "DM")
    private String dm;
    @Column(name = "SS")
    private String ss;
    @Column(name = "HBH")
    private String hbh;
    @Column(name = "JX")
    private String jx;
    @Column(name = "HBXZ")
    private String hbxz;
    @Column(name = "HX")
    private String hx;
    @Column(name = "HX0")
    private String hx0;
    @Column(name = "HXFL")
    private String hxfl;
    @Column(name = "HD")
    private String hd;
    @Column(name = "HDFL")
    private String hdfl;
    @Column(name = "ZDYZ")
    private Long zdyz;
    @Column(name = "ZDZW")
    private Long zdzw;
    @Column(name = "PEYZ")
    private Long peyz;
    @Column(name = "PEZW")
    private Long pezw;
    @Column(name = "KGYZ")
    private Long kgyz;
    @Column(name = "KGZW")
    private Long kgzw;
    @Column(name = "IO")
    private String io;
    @Column(name = "JCN")
    private Long jcn;
    @Column(name = "JQJD")
    private String jqjd;
    @Column(name = "QJSJ")
    private String qjsj;
    @Column(name = "CR")
    private Long cr;
    @Column(name = "ET")
    private Long et;
    @Column(name = "YE")
    private Long ye;
    @Column(name = "CRWH")
    private Long crwh;
    @Column(name = "ETWH")
    private Long etwh;
    @Column(name = "YEWH")
    private Long yewh;
    @Column(name = "XL")
    private Long xl;
    @Column(name = "YJ")
    private Long yj;
    @Column(name = "HW")
    private Long hw;
    @Column(name = "PBM")
    private String pbm;
    @Column(name = "XG")
    private String xg;
    @Column(name = "BC")
    private String bc;
    @Column(name = "JH")
    private String jh;
    @Column(name = "WJHZ")
    private Long wjhz;
    @Column(name = "XLJS")
    private Long xljs;
    @Column(name = "OPERATION_STATUS")
    private String operationstatus;
    @Column(name = "FLIGHT_STATUS")
    private String flightstatus;
    @Column(name = "FLIGHT_BASE_ID")
    private Long flightbaseid;
    @Column(name = "FLIGHT_DATA_ID")
    private Long flightdataid;
    @Column(name = "FLIGHT_IDENTITY")
    private String flightIdentity;
    
    public FlightLoadData(){
        //Long id;
        //Date jhrq;
        //Date sjrq;
        this.cyr="";
        this.dm="";
        this.ss="";
        this.hbh="";
        this.jx="";
        this.hbxz="";
        this.hx="";
        this.hx0="";
        this.hxfl="";
        this.hd="";
        this.hdfl="";
        this.zdyz=0L;
        this.zdzw=0L;
        this.peyz=0L;
        this.pezw=0L;
        this.kgyz=0L;
        this.kgzw=0L;
        this.io="";
        this.jcn=0L;
        this.jqjd="";
        this.qjsj="";
        this.cr=0L;
        this.et=0L;
        this.ye=0L;
        this.crwh=0L;
        this.etwh=0L;
        this.yewh=0L;
        this.xl=0L;
        this.yj=0L;
        this.hw=0L;
        this.pbm="";
        this.xg="";
        this.bc="";
        this.jh="";
        this.wjhz=0L;
        this.xljs=0L;
        this.operationstatus="";
        this.flightstatus="";
        //Long flightbaseid;
        //Long flightdataid;
        this.flightIdentity="";
    }
    
    public String getJhrq2s() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.CHINA);
        return sdf.format(jhrq);
    }

    public String getSjrq2s() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.CHINA);
        return sdf.format(sjrq);
    }
    
    @Transient
    //行号
    private int rowNumber;
    
    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Transient
    //校验出错信息
    private String verifyDescription;

    public String getVerifyDescription() {
        return verifyDescription;
    }
    
    @Transient
    /*
     * 出错位置
     * 如{1:{1;2}}　第一行有，第一个第二个单元格出错
     */
    private Map<Integer,ArrayList<Integer>> errorMessage;

    public Map<Integer, ArrayList<Integer>> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Map<Integer, ArrayList<Integer>> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setVerifyDescription(String verifyDescription) {
        this.verifyDescription = verifyDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getJhrq() {
        return jhrq;
    }

    public void setJhrq(Date jhrq) {
        this.jhrq = jhrq;
    }

    public Date getSjrq() {
        return sjrq;
    }

    public void setSjrq(Date sjrq) {
        this.sjrq = sjrq;
    }

    public String getCyr() {
        return cyr;
    }

    public void setCyr(String cyr) {
        this.cyr = cyr;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getHbh() {
        return hbh;
    }

    public void setHbh(String hbh) {
        this.hbh = hbh;
    }

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx;
    }

    public String getHbxz() {
        return hbxz;
    }

    public void setHbxz(String hbxz) {
        this.hbxz = hbxz;
    }

    public String getHx() {
        return hx;
    }

    public void setHx(String hx) {
        this.hx = hx;
    }

    public String getHx0() {
        return hx0;
    }

    public void setHx0(String hx0) {
        this.hx0 = hx0;
    }

    public String getHxfl() {
        return hxfl;
    }

    public void setHxfl(String hxfl) {
        this.hxfl = hxfl;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public String getHdfl() {
        return hdfl;
    }

    public void setHdfl(String hdfl) {
        this.hdfl = hdfl;
    }

    public Long getZdyz() {
        return zdyz;
    }

    public void setZdyz(Long zdyz) {
        this.zdyz = zdyz;
    }

    public Long getZdzw() {
        return zdzw;
    }

    public void setZdzw(Long zdzw) {
        this.zdzw = zdzw;
    }

    public Long getPeyz() {
        return peyz;
    }

    public void setPeyz(Long peyz) {
        this.peyz = peyz;
    }

    public Long getPezw() {
        return pezw;
    }

    public void setPezw(Long pezw) {
        this.pezw = pezw;
    }

    public Long getKgyz() {
        return kgyz;
    }

    public void setKgyz(Long kgyz) {
        this.kgyz = kgyz;
    }

    public Long getKgzw() {
        return kgzw;
    }

    public void setKgzw(Long kgzw) {
        this.kgzw = kgzw;
    }

    public String getIo() {
        return io;
    }

    public void setIo(String io) {
        this.io = io;
    }

    public Long getJcn() {
        return jcn;
    }

    public void setJcn(Long jcn) {
        this.jcn = jcn;
    }

    public String getJqjd() {
        return jqjd;
    }

    public void setJqjd(String jqjd) {
        this.jqjd = jqjd;
    }

    public String getQjsj() {
        return qjsj;
    }

    public void setQjsj(String qjsj) {
        this.qjsj = qjsj;
    }

    public Long getCr() {
        return cr;
    }

    public void setCr(Long cr) {
        this.cr = cr;
    }

    public Long getEt() {
        return et;
    }

    public void setEt(Long et) {
        this.et = et;
    }

    public Long getYe() {
        return ye;
    }

    public void setYe(Long ye) {
        this.ye = ye;
    }

    public Long getCrwh() {
        return crwh;
    }

    public void setCrwh(Long crwh) {
        this.crwh = crwh;
    }

    public Long getEtwh() {
        return etwh;
    }

    public void setEtwh(Long etwh) {
        this.etwh = etwh;
    }

    public Long getYewh() {
        return yewh;
    }

    public void setYewh(Long yewh) {
        this.yewh = yewh;
    }

    public Long getXl() {
        return xl;
    }

    public void setXl(Long xl) {
        this.xl = xl;
    }

    public Long getYj() {
        return yj;
    }

    public void setYj(Long yj) {
        this.yj = yj;
    }

    public Long getHw() {
        return hw;
    }

    public void setHw(Long hw) {
        this.hw = hw;
    }

    public String getPbm() {
        return pbm;
    }

    public void setPbm(String pbm) {
        this.pbm = pbm;
    }

    public String getXg() {
        return xg;
    }

    public void setXg(String xg) {
        this.xg = xg;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh;
    }

    public Long getWjhz() {
        return wjhz;
    }

    public void setWjhz(Long wjhz) {
        this.wjhz = wjhz;
    }

    public Long getXljs() {
        return xljs;
    }

    public void setXljs(Long xljs) {
        this.xljs = xljs;
    }

    public String getOperationstatus() {
        return operationstatus;
    }

    public void setOperationstatus(String operationstatus) {
        this.operationstatus = operationstatus;
    }

    public String getFlightstatus() {
        return flightstatus;
    }

    public void setFlightstatus(String flightstatus) {
        this.flightstatus = flightstatus;
    }

    public Long getFlightbaseid() {
        return flightbaseid;
    }

    public void setFlightbaseid(Long flightbaseid) {
        this.flightbaseid = flightbaseid;
    }

    public Long getFlightdataid() {
        return flightdataid;
    }

    public void setFlightdataid(Long flightdataid) {
        this.flightdataid = flightdataid;
    }
    
    public String getFlightIdentity() {
        return flightIdentity;
    }

    public void setFlightIdentity(String flightIdentity) {
        this.flightIdentity = flightIdentity;
    }
    //0 自动 1 手动 2 舱单导入
    @Column(name = "GENERATE_METHOD")
    private String generateMethod;

    public String getGenerateMethod() {
        return generateMethod;
    }

    public void setGenerateMethod(String generateMethod) {
        this.generateMethod = generateMethod;
    }
    
    //航段顺序
    @Column(name = "LEGNO")
    private String legNo;

    public String getLegNo() {
        return legNo;
    }

    public void setLegNo(String legNo) {
        this.legNo = legNo;
    }
}