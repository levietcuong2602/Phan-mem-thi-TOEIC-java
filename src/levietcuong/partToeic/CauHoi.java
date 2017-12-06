package levietcuong.partToeic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.naming.ldap.Rdn;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CauHoi {
	private String anhURL;
	private String luaChon = "";
	private String traLoiA, traLoiB, traLoiC, traLoiD;
	private String dapAn;
	private int indexCauHoi;
	private String lichSuLam;
	private String deBai;
	private String doanHoiThoai;
	
	public CauHoi(String anhURL, String traLoiA, String traLoiB, String traLoiC, String traLoiD, String dapAn,
			String lichSuLam, String deBai, String doanHoiThoai) {
		super();
		this.anhURL = anhURL;
		this.traLoiA = traLoiA;
		this.traLoiB = traLoiB;
		this.traLoiC = traLoiC;
		this.traLoiD = traLoiD;
		this.dapAn = dapAn;
		this.lichSuLam = lichSuLam;
		this.deBai = deBai;
		this.doanHoiThoai = doanHoiThoai;
	}

	//khoi tao cho doi tuong cau hoi part 1
	public CauHoi(String anhURL, String traLoiA, String traLoiB, String traLoiC, String traLoiD, String dapAn, String lichSuLam) {
		super();
		this.anhURL = anhURL;
		this.traLoiA = traLoiA;
		this.traLoiB = traLoiB;
		this.traLoiC = traLoiC;
		this.traLoiD = traLoiD;
		this.dapAn = dapAn;
		this.lichSuLam = lichSuLam;
	}

	public CauHoi(String anhURL, String dapAn) {
		super();
		this.anhURL = anhURL;
		this.dapAn = dapAn;
	}
	
	public CauHoi(String anhURL, String dapAn, int index) {
		super();
		this.anhURL = anhURL;
		this.dapAn = dapAn;
		this.indexCauHoi = index;
	}
	
	//khởi tạo cho part2
	public CauHoi(String debai, String traLoiA, String traLoiB, String traLoiC, String dapAn, String lichSuLam) {
		super();
		this.traLoiA = traLoiA;
		this.traLoiB = traLoiB;
		this.traLoiC = traLoiC;
		this.dapAn = dapAn;
		this.lichSuLam = lichSuLam;
	}
	
	public CauHoi(String anhURL, String traLoiA, String traLoiB, String traLoiC, String traLoiD, String dapAn,
			String lichSuLam, String deBai) {
		super();
		this.anhURL = anhURL;
		this.traLoiA = traLoiA;
		this.traLoiB = traLoiB;
		this.traLoiC = traLoiC;
		this.traLoiD = traLoiD;
		this.dapAn = dapAn;
		this.lichSuLam = lichSuLam;
		this.deBai = deBai;
	}
	
	public CauHoi(String anhURL, String luaChon, String traLoiA, String traLoiB, String traLoiC, String traLoiD,
			String dapAn, int indexCauHoi, String lichSuLam, String deBai, String doanHoiThoai) {
		super();
		this.anhURL = anhURL;
		this.luaChon = luaChon;
		this.traLoiA = traLoiA;
		this.traLoiB = traLoiB;
		this.traLoiC = traLoiC;
		this.traLoiD = traLoiD;
		this.dapAn = dapAn;
		this.indexCauHoi = indexCauHoi;
		this.lichSuLam = lichSuLam;
		this.deBai = deBai;
		this.doanHoiThoai = doanHoiThoai;
	}

	public String getLichSuLam() {
		return lichSuLam;
	}

	public void setLichSuLam(String lichSuLam) {
		this.lichSuLam = lichSuLam;
	}

	public String getDeBai() {
		return deBai;
	}
	
	public void setDeBai(String deBai) {
		this.deBai = deBai;
	}
	
	public void setAnhURL(String anhURL) {
		this.anhURL = anhURL;
	}

	public String getAnhURL() {
		return anhURL;
	}

	public void setLuaChon(String cauTraLoi) {
		this.luaChon = cauTraLoi;
	}
	
	public String getLuaChon(){
		return this.luaChon;
	}

	public String getTraLoiA() {
		return traLoiA;
	}

	public void setTraLoiA(String traLoiA) {
		this.traLoiA = traLoiA;
	}

	public String getTraLoiB() {
		return traLoiB;
	}

	public void setTraLoiB(String traLoiB) {
		this.traLoiB = traLoiB;
	}

	public String getTraLoiC() {
		return traLoiC;
	}

	public void setTraLoiC(String traLoiC) {
		this.traLoiC = traLoiC;
	}

	public String getTraLoiD() {
		return traLoiD;
	}

	public void setTraLoiD(String traLoiD) {
		this.traLoiD = traLoiD;
	}

	public String getDapAn() {
		return dapAn;
	}

	public void setDapAn(String dapAn) {
		this.dapAn = dapAn;
	}
	
	public int getIndexCauHoi() {
		return indexCauHoi;
	}

	public void setIndexCauHoi(int indexCauHoi) {
		this.indexCauHoi = indexCauHoi;
	}

	public String getDoanHoiThoai() {
		return doanHoiThoai;
	}

	public void setDoanHoiThoai(String doanHoiThoai) {
		this.doanHoiThoai = doanHoiThoai;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(luaChon.equals("")){
			return "Câu "+ indexCauHoi + ": chưa chọn đáp án";
		}
		return "Câu " + indexCauHoi +": " + luaChon;
	}
}
