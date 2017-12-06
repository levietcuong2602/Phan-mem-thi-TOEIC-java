package levietcuong.partToeic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import levietcuong.Home.MyImage;
import levietcuong.luyendoc.LuyenDocPanel;
import levietcuong.luyennghe.LuyenNghePanel;
import levietcuong.thithu.ThiThuPanel;

public class QuanLyPart6 extends JPanel {
	private JRadioButton rdButtonA, rdButtonB, rdButtonC, rdButtonD;
	private JButton btnLeft, btnRight, btnSubmit;
	private JPanel panelMain;
	private JList<CauHoi> lstCauHoi;
	private int score = 0, thuTuDe = 1;
	private ButtonGroup group;
	private boolean submit = false, checkPart = false;
	private boolean lichLuyenTap = false;
	private JLabel lblDeBai;
	
	private ArrayList<CauHoi> arrCauHoi;
	private JPanel panelHeader;
	private JPanel panelButton;
	private JTextArea txtDoanHoiThoai;
	
	public QuanLyPart6(ArrayList<CauHoi> lstPart6) {
		// TODO Auto-generated constructor stub
		this.arrCauHoi = lstPart6;
		//kiểm tra lịch sử làm
		KiemTralichLuyenTap(lstPart6);
		
		addControlls();
		addEvents();
	}
	
	public JButton getBtnSubmit() {
		return btnSubmit;
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		btnLeft.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				btnRight.setEnabled(true);
				int index = lstCauHoi.getSelectedIndex();
				lstCauHoi.setSelectedIndex(index-1);
				
				/*cập nhật lại đoạn hội thoại*/
				txtDoanHoiThoai.setText(lstCauHoi.getSelectedValue().getDoanHoiThoai());
				//cập nhật lại đề bài:
				lblDeBai.setText("Câu "+ lstCauHoi.getSelectedValue().getIndexCauHoi());
				//load lại radiobutton
				UpdateRadioButton();
				if(index == 0){
					btnLeft.setEnabled(false);
				}
			}
		});

		btnRight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnLeft.setEnabled(true);
				int index = lstCauHoi.getSelectedIndex();
				lstCauHoi.setSelectedIndex(index+1);
				
				/*cập nhật lại đoạn hội thoại*/
				txtDoanHoiThoai.setText(lstCauHoi.getSelectedValue().getDoanHoiThoai());
				//cập nhật lại đề bài:
				lblDeBai.setText("Câu "+ lstCauHoi.getSelectedValue().getIndexCauHoi());
				
				//cập nhật lại radio button (cập nhật lựa chọn)
				UpdateRadioButton();
				
				if(index == lstCauHoi.getLastVisibleIndex()){
					btnRight.setEnabled(false);
				}
			 }
				
		});
		
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				submit();
				lichLuyenTap = true;
					
			}
		});
		
		lstCauHoi.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(e.getValueIsAdjusting()){
					/*cập nhật lại đoạn hội thoại*/
					txtDoanHoiThoai.setText(lstCauHoi.getSelectedValue().getDoanHoiThoai());
					//cập nhật lại đề bài:
					lblDeBai.setText("Câu "+ lstCauHoi.getSelectedValue().getIndexCauHoi());
					
					//cập nhật lại radio button (cập nhật lựa chọn)
					UpdateRadioButton();
					
					int index = lstCauHoi.getSelectedIndex();
					if(index == lstCauHoi.getLastVisibleIndex()){
						btnRight.setEnabled(false);
					}else {
						btnRight.setEnabled(true);
					}
				}
			}
		});
	}
	
	public void submit(){
		submit = true;
		//kiểm tra đáp án
		score = TinhDiem();
		JOptionPane.showMessageDialog(null, score + "/" + arrCauHoi.size()+ "\nMoi ban xem dap an.");
		
		lstCauHoi.setSelectedIndex(0);
		
		UpdateRadioButton();
	}
	
	public int TinhDiem(){
		//kiểm tra đáp án
		int score = 0;
		DefaultListModel<CauHoi> model = (DefaultListModel<CauHoi>) lstCauHoi.getModel();
		for(int i = 0; i < arrCauHoi.size(); i++){
			if(model.get(i).getLuaChon().equals(model.get(i).getDapAn())){
				score++;
			}				
		}
		return score;
	}
	
	public int getScore() {
		return score;
	}

	public void UpdateRadioButton(){
		//load lại lựa chọn
		CauHoi cauHoi = lstCauHoi.getModel().getElementAt(lstCauHoi.getSelectedIndex());
		String luaChon = cauHoi.getLuaChon();
		
		rdButtonA.setText("A. " + cauHoi.getTraLoiA());
		rdButtonB.setText("B. " + cauHoi.getTraLoiB());
		rdButtonC.setText("C. " + cauHoi.getTraLoiC());
		rdButtonD.setText("D. " + cauHoi.getTraLoiD());
		
		if(luaChon.equalsIgnoreCase("")){
			group.clearSelection();
		}
		if (luaChon.equals("A")) {
			rdButtonA.setSelected(true);
		} else if (luaChon.equals("B")) {
			rdButtonB.setSelected(true);
		}
		if (luaChon.equals("C")) {
			rdButtonC.setSelected(true);
		}
		if (luaChon.equals("D")) {
			rdButtonD.setSelected(true);
		}
		
		if(submit == true){
			//cập nhật lại doan hoi thoai
			if(cauHoi.getDapAn().equalsIgnoreCase("A")){
				rdButtonA.setForeground(Color.red);
				rdButtonB.setForeground(Color.black);
				rdButtonC.setForeground(Color.black);
				rdButtonD.setForeground(Color.black);
			}else if(cauHoi.getDapAn().equalsIgnoreCase("B")){
				rdButtonB.setForeground(Color.red);
				rdButtonA.setForeground(Color.black);
				rdButtonC.setForeground(Color.black);
				rdButtonD.setForeground(Color.black);
			}else if(cauHoi.getDapAn().equalsIgnoreCase("C")){
				rdButtonC.setForeground(Color.red);
				rdButtonB.setForeground(Color.black);
				rdButtonA.setForeground(Color.black);
				rdButtonD.setForeground(Color.black);
			}else if(cauHoi.getDapAn().equalsIgnoreCase("D")){
				rdButtonB.setForeground(Color.black);
				rdButtonC.setForeground(Color.black);
				rdButtonA.setForeground(Color.black);
				rdButtonD.setForeground(Color.red);
			}
			 
		}
	}

	private void addControlls() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		
		add(panelHeader = createPanelHeader(), BorderLayout.PAGE_START);
		add(panelButton = createPanelButton(), BorderLayout.PAGE_END);
		
		//tạo list câu hỏi
		lstCauHoi = createListPart4(arrCauHoi);
		JScrollPane scroll = new JScrollPane(lstCauHoi);
		add(scroll, BorderLayout.EAST);
		lstCauHoi.setSelectedIndex(0);
		
		//tạo panel câu hỏi.
		add(panelMain = createMainPanel(), BorderLayout.CENTER);
	}	
	
	private JPanel createPanelButton() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new FlowLayout());
		btnLeft = new JButton("Back");
		btnLeft.setIcon(new MyImage("src/image/left.png", 20, 20).getImg());

		btnRight = new JButton("Next");
		btnRight.setIcon(new MyImage("src/image/right.png", 20, 20).getImg());

		btnSubmit = new JButton("Submit");
		btnSubmit.setIcon(new MyImage("src/image/submit.jpg", 20, 20).getImg());
		
		panel.add(btnLeft);
		panel.add(btnRight);
		panel.add(btnSubmit);
		return panel;
	}

	private JPanel createPanelHeader() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel();
		JLabel lblTieude = new JLabel("");
		lblTieude.setText("Part 6 Incomplement Text");;
		lblTieude.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
		panel.setBackground(new Color(0x9999));
		panel.add(lblTieude);
		
		return panel;
	}

	public JPanel createMainPanel() {
		CauHoi cauhoi = lstCauHoi.getSelectedValue();
		
		JPanel panelMain = new JPanel(new BorderLayout());		
		JPanel pnHoiThoai = new JPanel(new BorderLayout());
		txtDoanHoiThoai = new JTextArea();
		txtDoanHoiThoai.setWrapStyleWord(true);
		txtDoanHoiThoai.setLineWrap(true);
		txtDoanHoiThoai.setEditable(false);
		txtDoanHoiThoai.setText(cauhoi.getDoanHoiThoai());
		JScrollPane scrol = new JScrollPane(txtDoanHoiThoai);
		pnHoiThoai.add(scrol);
		panelMain.add(pnHoiThoai, BorderLayout.NORTH);
		
		//panel đề bài
		JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
		lblDeBai = new JLabel();
		lblDeBai.setText("Câu "+ cauhoi.getIndexCauHoi());
		panel.add(lblDeBai);
		
		group = new ButtonGroup();
		rdButtonA = new JRadioButton("A. " + cauhoi.getTraLoiA());
		rdButtonB = new JRadioButton("B. " + cauhoi.getTraLoiB());
		rdButtonC = new JRadioButton("C. " + cauhoi.getTraLoiC());
		rdButtonD = new JRadioButton("D. " + cauhoi.getTraLoiD());
		
		group.add(rdButtonA);
		group.add(rdButtonB);
		group.add(rdButtonC);
		group.add(rdButtonD);
		
		panel.add(new JSeparator());
		panel.add(rdButtonA);
		panel.add(rdButtonB);
		panel.add(rdButtonC);
		panel.add(rdButtonD);
		panel.add(new JSeparator());

		panelMain.add(panel, BorderLayout.CENTER);
		
		rdButtonA.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = lstCauHoi.getSelectedIndex();
				lstCauHoi.getModel().getElementAt(index).setLuaChon("A");
				lstCauHoi.repaint();
			}
		});
		rdButtonB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = lstCauHoi.getSelectedIndex();
				lstCauHoi.getModel().getElementAt(index).setLuaChon("B");
				lstCauHoi.repaint();
			}
		});
		rdButtonC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = lstCauHoi.getSelectedIndex();
				lstCauHoi.getModel().getElementAt(index).setLuaChon("C");
				lstCauHoi.repaint();
			}
		});
		
		rdButtonD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = lstCauHoi.getSelectedIndex();
				lstCauHoi.getModel().getElementAt(index).setLuaChon("D");
				lstCauHoi.repaint();
			}
		});

		String luaChon = lstCauHoi.getModel().getElementAt(lstCauHoi.getSelectedIndex()).getLuaChon();
		if (luaChon.equals("A")) {
			rdButtonA.setSelected(true);
		} else if (luaChon.equals("B")) {
			rdButtonB.setSelected(true);
		}
		if (luaChon.equals("C")) {
			rdButtonC.setSelected(true);
		}
		if (luaChon.equals("D")) {
			rdButtonD.setSelected(true);
		}
		if(luaChon.equals("")){
			group.clearSelection();
		}
		
		return panelMain;
	}
	
	public JList<CauHoi> createListPart4(ArrayList<CauHoi> lstPart6){
		JList<CauHoi>lstCauHoi = new JList<>();
		DefaultListModel<CauHoi> model = new  DefaultListModel<>();
		for(int i = 0; i < lstPart6.size(); i++){
			model.addElement(lstPart6.get(i));
			model.get(i).setIndexCauHoi(i+1);
		}
		lstCauHoi.setModel(model);
		
		return lstCauHoi;
	}
	
	public void setThuTuDe(int index){
		this.thuTuDe = index;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(lichLuyenTap == true){
			return "Part 6 - Bộ câu: " + thuTuDe + "- Đã hoàn thành";
		}
		return "Part 6 - Bộ câu: " + thuTuDe + " - Chưa hoàn thành";
	}
	
	public void KiemTralichLuyenTap(ArrayList<CauHoi> list){
		int check = 0;
		int count = list.size();
		for(int i = 0; i < count; i++){
			if(list.get(i).getLichSuLam().equals("T")){
				check++;
			}
		}
		
		if(check == count){
			lichLuyenTap = true;
		}else{
			lichLuyenTap = false;
		}
		
	}
}
