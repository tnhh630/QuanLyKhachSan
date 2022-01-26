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
 * @author Trần Trung Nghĩa <PS14820>
 */
public class Chat {

        private String maNhanVienGui;
        private String maNhanVienNhan;
        private String noiDung;
        private Date ngayChat;

    public Chat() {
    }

    public Chat(String maNhanVienGui, String maNhanVienNhan, String noiDung, Date ngayChat) {
        this.maNhanVienGui = maNhanVienGui;
        this.maNhanVienNhan = maNhanVienNhan;
        this.noiDung = noiDung;
        this.ngayChat = ngayChat;
    }

    public String getMaNhanVienGui() {
        return maNhanVienGui;
    }

    public void setMaNhanVienGui(String maNhanVienGui) {
        this.maNhanVienGui = maNhanVienGui;
    }

    public String getMaNhanVienNhan() {
        return maNhanVienNhan;
    }

    public void setMaNhanVienNhan(String maNhanVienNhan) {
        this.maNhanVienNhan = maNhanVienNhan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Date getNgayChat() {
        return ngayChat;
    }

    public void setNgayChat(Date ngayChat) {
        this.ngayChat = ngayChat;
    }

}
