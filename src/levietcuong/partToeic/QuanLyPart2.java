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
import java.util.ArrayList;
import java.util.Properties;
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
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import levietcuong.Home.MyImage;
import levietcuong.luyennghe.LuyenNghePanel;
import levietcuong.thithu.ThiThuPanel;

public class QuanLyPart2 extends JPanel {
	private JRadioButton rdButtonA, rdButtonB, rdButtonC;
	private JButton btnLeft, btnRight, btnSubmit;
	private JPanel panelMain;
	private JList<CauHoi> lstCauHoi;
	private int score = 0, thuTuDe = 1;
	public Player audioPart2;
	private String urlAudio;
	private ButtonGroup group;
	private boolean submit = false;
	private boolean lichSulam = false;
	private JLabel lblDeBai;
	private JPanel panelHeader;
	private JPanel panelButton;
	
	private ArrayList<CauHoi> arrCauHoi;
	public QuanLyPart2(ArrayList<CauHoi> lstPart2) {
		// TODO Auto-generated constructor stub	
		//kiểm tra lịch sử làm
		KiemTraLichSuLam(lstPart2);
		
		this.arrCauHoi = lstPart2;
		
		addControlls();
		addEvents();
	}
	
	public JButton getBtnSubmit() {
		return btnSubmit;
	}

	public void displayAudio(){
		btnSubmit.setEnabled(true);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					audioPart2 = new Player(new FileInputStream(new File(urlAudio)));
					audioPart2.play();
					
					if(audioPart2.isComplete()){
						audioPart2.close();
					}
				} catch (FileNotFoundException | JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
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
				//cập nhật lại đề bài:
				lblDeBai.setText("Câu " + (lstCauHoi.getSelectedIndex()+1));
				
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
				
				if(lstCauHoi.getSelectedIndex() == lstCauHoi.getLastVisibleIndex()){
					if(ThiThuPanel.btnNextPart != null) {
						ThiThuPanel.btnNextPart.setVisible(true);
					}
				}
				//cập nhật lại thứ tự câu:
				lblDeBai.setText("Câu " + (lstCauHoi.getSelectedIndex()+1));
				//load lại đáp án
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
				audioPart2.close();
				submit = true;
				score = TinhDiem();
				JOptionPane.showMessageDialog(null, score + "/" + arrCauHoi.size()+ "\nMoi ban xem dap an.");
				
				lstCauHoi.setSelectedIndex(0);
				UpdateRadioButton();
				lichSulam = true;
				if(LuyenNghePanel.lstPart2 != null){
					LuyenNghePanel.lstPart2.validate();
					LuyenNghePanel.lstPart2.repaint();
				}
			}
		});
		
		lstCauHoi.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(e.getValueIsAdjusting()){
					//cập nhật lại đề bài:
					lblDeBai.setText("Câu " + (lstCauHoi.getSelectedIndex()+1));
					
					if(lstCauHoi.getSelectedIndex() == lstCauHoi.getLastVisibleIndex()){
						if(ThiThuPanel.btnNextPart != null) {
							ThiThuPanel.btnNextPart.setVisible(true);
						}
					}
					//load lại đáp án
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
	
	public int TinhDiem(){
		int score = 0;
		//kiểm tra đáp án
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
		String luaChon = lstCauHoi.getModel().getElementAt(lstCauHoi.getSelectedIndex()).getLuaChon();
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
		
		if(submit == true){
			CauHoi cauHoi = lstCauHoi.getModel().getElementAt(lstCauHoi.getSelectedIndex());
			//cập nhật lại đề bài
			lblDeBai.setText("Câu " + (lstCauHoi.getSelectedIndex()+1)+ ": " + cauHoi.getDeBai());
			
			rdButtonA.setText("A. "+cauHoi.getTraLoiA());
			rdButtonB.setText("B. "+cauHoi.getTraLoiB());
			rdButtonC.setText("C. "+cauHoi.getTraLoiC());
			
			if(cauHoi.getDapAn().equalsIgnoreCase("A")){
				rdButtonA.setForeground(Color.red);
				rdButtonB.setForeground(Color.black);
				rdButtonC.setForeground(Color.black);
				
			}else if(cauHoi.getDapAn().equalsIgnoreCase("B")){
				rdButtonB.setForeground(Color.red);
				rdButtonA.setForeground(Color.black);
				rdButtonC.setForeground(Color.black);
			}else if(cauHoi.getDapAn().equalsIgnoreCase("C")){
				rdButtonC.setForeground(Color.red);
				rdButtonB.setForeground(Color.black);
				rdButtonA.setForeground(Color.black);
			}else if(cauHoi.getDapAn().equalsIgnoreCase("D")){
				rdButtonB.setForeground(Color.black);
				rdButtonC.setForeground(Color.black);
				rdButtonA.setForeground(Color.black);
			}
			 
		}
	}
	
	private void addControlls() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		
		add(panelHeader = createPanelHeader(), BorderLayout.PAGE_START);
		add(panelButton = createPanelButton(), BorderLayout.PAGE_END);
		
		//tạo list câu hỏi
		lstCauHoi = createListPart2(arrCauHoi);
		JScrollPane scroll = new JScrollPane(lstCauHoi);
		lstCauHoi.setSelectedIndex(0);
		add(scroll, BorderLayout.EAST);
		
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
		btnSubmit.setEnabled(false);
		
		panel.add(btnLeft);
		panel.add(btnRight);
		panel.add(btnSubmit);
		return panel;
	}

	public JPanel createPanelHeader() {
		JPanel panel = new JPanel();
		JLabel lblTieude = new JLabel("Part 2: Question & Respond");
		lblTieude.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
		panel.setBackground(new Color(0x9999));
		panel.add(lblTieude);
		
		return panel;
	}
	
	public JPanel createMainPanel() {
		JPanel panelMain = new JPanel(new BorderLayout());
		
		JPanel pnTieuDeCau = new JPanel();
		JLabel lblTieuDeCau = new JLabel();
		lblTieuDeCau.setIcon(new MyImage("src/image/iconPart2.jpg", 600, 400).getImg());
		pnTieuDeCau.add(lblTieuDeCau);
		panelMain.add(pnTieuDeCau, BorderLayout.PAGE_START);
		
		lblDeBai = new JLabel();
		lblDeBai.setText("Câu "+lstCauHoi.getSelectedValue().getIndexCauHoi());
		
		group = new ButtonGroup();
		rdButtonA = new JRadioButton("A");
		rdButtonB = new JRadioButton("B");
		rdButtonC = new JRadioButton("C");

		group.add(rdButtonA);
		group.add(rdButtonB);
		group.add(rdButtonC);
		
		JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
		panel.add(lblDeBai);
		
		panel.add(rdButtonA);
		panel.add(rdButtonB);
		panel.add(rdButtonC);

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

		String luaChon = lstCauHoi.getModel().getElementAt(lstCauHoi.getSelectedIndex()).getLuaChon();
		if (luaChon.equals("A")) {
			rdButtonA.setSelected(true);
		} else if (luaChon.equals("B")) {
			rdButtonB.setSelected(true);
		}
		if (luaChon.equals("C")) {
			rdButtonC.setSelected(true);
		}
		if(luaChon.equals("")){
			group.clearSelection();
		}
		
		return panelMain;
	}
	
	public JList<CauHoi> createListPart2(ArrayList<CauHoi> lstPart2){
		JList<CauHoi>lstCauHoi = new JList<>();
		DefaultListModel<CauHoi> model = new  DefaultListModel<>();
		for(int i = 0; i < lstPart2.size(); i++){
			model.addElement(lstPart2.get(i));
			model.get(i).setIndexCauHoi(i+1);
		}
		lstCauHoi.setModel(model);
		return lstCauHoi;
	}
	
	public String getUrlAudio() {
		return urlAudio;
	}

	public void setUrlAudio(String urlAudio) {
		this.urlAudio = urlAudio;
	}

	public void setThuTuDe(int index){
		this.thuTuDe = index;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(lichSulam == true){
			return "Part 2 - Bộ câu: " + thuTuDe + "- Đã hoàn thành";
		}
		return "Part 2 - Bộ câu: " + thuTuDe + " - Chưa hoàn thành";
	}
	
	public void KiemTraLichSuLam(ArrayList<CauHoi> list){
		int check = 0;
		int count = list.size();
		for(int i = 0; i < count; i++){
			if(list.get(i).getLichSuLam().equals("T")){
				check++;
			}
		}
		
		if(check == count){
			lichSulam = true;
		}else{
			lichSulam = false;
		}
	}
}
