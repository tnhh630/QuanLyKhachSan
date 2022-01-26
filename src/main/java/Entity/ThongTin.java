/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Helper.xDate;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class ThongTin {
    private String hoten;
    private String loaiphong;
    private Date ngaydat = xDate.addDays(new Date(), -365 * 20);
    private Date ngaytra = xDate.addDays(new Date(), -365 * 20);
    private double giaphong;
    private double tienphong;
    private double datcoc;

    public ThongTin() {
    }

    public ThongTin(String hoten, String loaiphong, double giaphong, double tienphong, double datcoc) {
        this.hoten = hoten;
        this.loaiphong = loaiphong;
        this.giaphong = giaphong;
        this.tienphong = tienphong;
        this.datcoc = datcoc;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getLoaiphong() {
        return loaiphong;
    }

    public void setLoaiphong(String loaiphong) {
        this.loaiphong = loaiphong;
    }

    public Date getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(Date ngaydat) {
        this.ngaydat = ngaydat;
    }

    public Date getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(Date ngaytra) {
        this.ngaytra = ngaytra;
    }

    public double getGiaphong() {
        return giaphong;
    }

    public void setGiaphong(double giaphong) {
        this.giaphong = giaphong;
    }

    public double getTienphong() {
        return tienphong;
    }

    public void setTienphong(double tienphong) {
        this.tienphong = tienphong;
    }

    public double getDatcoc() {
        return datcoc;
    }

    public void setDatcoc(double datcoc) {
        this.datcoc = datcoc;
    }
    
    
    
    
}
