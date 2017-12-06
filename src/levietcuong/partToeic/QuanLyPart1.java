package levietcuong.partToeic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

public class QuanLyPart1 extends JPanel {
	private JRadioButton rdButtonA, rdButtonB, rdButtonC, rdButtonD;
	private JButton btnLeft, btnRight, btnSubmit;
	private JPanel panelMain;
	private CauHoi qtTemp;
	private JList<CauHoi> lstCauHoi;
	private int score = 0, thuTuDe = 1;
	public Player audioPart1;
	private String urlAudio;
	private ButtonGroup group;
	private boolean submit = false;
	private boolean lichSulam;
	private JPanel panelTitle;
	private JPanel panelButton;
	private JLabel lblImage;
	
	private ArrayList<CauHoi> arrCauHoi;
	
	public QuanLyPart1(ArrayList<CauHoi> lstPart1) {
		// TODO Auto-generated constructor stub	
		this.arrCauHoi = lstPart1;
		//kiểm tra lịch sử làm
		KiemTraLichSuLam(lstPart1);
		
		addControlls();
		addEvents();
		
	}
	
	public JList<CauHoi> getLstCauHoi() {
		return lstCauHoi;
	}

	public JButton getBtnSubmit() {
		return btnSubmit;
	}

	public void displayAudio(){
		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					audioPart1 = new Player(new FileInputStream(new File(urlAudio)));
					audioPart1.play();
					
					if(audioPart1.isComplete()){
						audioPart1.close();
					}
				} catch (FileNotFoundException | JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		thread1.setDaemon(true);
		thread1.start();
		
		
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		btnLeft.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				getCauHoiTruoc();
			}
		});

		btnRight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getCauHoiSau();
			 }
				
		});
		
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				audioPart1.close();
				submit = true;
				//kiểm tra đáp án	
				score = TinhDiem();
				JOptionPane.showMessageDialog(null, score + "/" + arrCauHoi.size()+"\n. Moi ban xem dap an.");
				
				lstCauHoi.setSelectedIndex(0);
				lblImage.setIcon(new ImageIcon(lstCauHoi.getSelectedValue().getAnhURL()));
				
				//update lại radiobuton
				UpdateRadioButton();
				lichSulam = true;
				
				if(LuyenNghePanel.lstPart1 != null){
					LuyenNghePanel.lstPart1.validate();
					LuyenNghePanel.lstPart1.repaint();
				}
				
			}
		});
		
		lstCauHoi.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(e.getValueIsAdjusting()){
					lblImage.setIcon(new ImageIcon(lstCauHoi.getModel().getElementAt(lstCauHoi.getSelectedIndex()).getAnhURL()));
					
					if(lstCauHoi.getSelectedIndex() == lstCauHoi.getLastVisibleIndex()){
						if(ThiThuPanel.btnNextPart != null) {
							ThiThuPanel.btnNextPart.setVisible(true);
						}
					}
					//load lại radiobutton
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
	
	public void getCauHoiTruoc() {
		btnRight.setEnabled(true);
		
		int cauHoiHienTai = lstCauHoi.getSelectedIndex();
		lstCauHoi.setSelectedIndex(cauHoiHienTai-1);
		
		//load lại ảnh
		lblImage.setIcon(new ImageIcon(lstCauHoi.getSelectedValue().getAnhURL()));
		/*load lại radiobutton*/
		UpdateRadioButton();
		if(cauHoiHienTai == 0){
			btnLeft.setEnabled(false);
		}
	}
	
	public void getCauHoiSau() {
		btnLeft.setEnabled(true);
		
		int index = lstCauHoi.getSelectedIndex();
		lstCauHoi.setSelectedIndex(index+1);
		
		if(lstCauHoi.getSelectedIndex() == lstCauHoi.getLastVisibleIndex()){
			if(ThiThuPanel.btnNextPart != null) {
				ThiThuPanel.btnNextPart.setVisible(true);
			}
		}
		//load lại ảnh
		lblImage.setIcon(new ImageIcon(lstCauHoi.getSelectedValue().getAnhURL()));
		UpdateRadioButton();
		 
		if(index == lstCauHoi.getLastVisibleIndex()){
			btnRight.setEnabled(false);
		}
	}
	
	public int  TinhDiem(){
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
		//load lại đáp án
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
		if (luaChon.equals("D")) {
			rdButtonD.setSelected(true);
		}
		
		if(submit == true){
			CauHoi cauHoi = lstCauHoi.getModel().getElementAt(lstCauHoi.getSelectedIndex());
			rdButtonA.setText("A. " +cauHoi.getTraLoiA());
			rdButtonB.setText("B. " +cauHoi.getTraLoiB());
			rdButtonC.setText("C. " +cauHoi.getTraLoiC());
			rdButtonD.setText("D. " +cauHoi.getTraLoiD());
			
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
				rdButtonD.setForeground(Color.red);
				rdButtonB.setForeground(Color.black);
				rdButtonC.setForeground(Color.black);
				rdButtonA.setForeground(Color.black);
			}
			 
		}
	}

	private void addControlls() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		
		add(panelTitle = createPanelTitle(), BorderLayout.PAGE_START); //header
		add(panelButton = createPanelButton(), BorderLayout.PAGE_END); //button
		
		//tạo list câu hỏi
		lstCauHoi = createListPart1(arrCauHoi);
		JScrollPane scroll = new JScrollPane(lstCauHoi);
		add(scroll, BorderLayout.EAST);
		lstCauHoi.setSelectedIndex(0);
		
		//tạo panel câu hỏi.
		add(panelMain = createMainPanel(), BorderLayout.CENTER);
	}	
	
	private JPanel createPanelButton() {
		// TODO Auto-generated method stub
		JPanel panelButton = new JPanel(new FlowLayout());
		btnLeft = new JButton("Back");
		btnLeft.setIcon(new MyImage("src/image/left.png", 20, 20).getImg());

		btnRight = new JButton("Next");
		btnRight.setIcon(new MyImage("src/image/right.png", 20, 20).getImg());

		btnSubmit = new JButton("Submit");
		btnSubmit.setIcon(new MyImage("src/image/submit.jpg", 20, 20).getImg());
		btnSubmit.setEnabled(true);
		
		panelButton.add(btnLeft);
		panelButton.add(btnRight);
		panelButton.add(btnSubmit);
		return panelButton;
	}

	private JPanel createPanelTitle() {
		// TODO Auto-generated method stub
		JPanel panelTitle = new JPanel();
		JLabel lblTieude = new JLabel("Part 1: Picture");
		lblTieude.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
		lblTieude.setForeground(Color.blue);
		
		panelTitle.add(lblTieude);
		
		return panelTitle;
	}

	public JPanel createMainPanel() {
		JPanel panelMain = new JPanel(new BorderLayout());

		lblImage = new JLabel();
		lblImage.setIcon(new ImageIcon(lstCauHoi.getSelectedValue().getAnhURL()));
		JPanel pnImageCauHoi = new JPanel();
		pnImageCauHoi.add(lblImage);
		panelMain.add(pnImageCauHoi, BorderLayout.CENTER);

		group = new ButtonGroup();
		rdButtonA = new JRadioButton("A");
		rdButtonB = new JRadioButton("B");
		rdButtonC = new JRadioButton("C");
		rdButtonD = new JRadioButton("D");

		group.add(rdButtonA);
		group.add(rdButtonB);
		group.add(rdButtonC);
		group.add(rdButtonD);
		
		JPanel panel = new JPanel(new GridLayout(10, 0, 10, 10));
		panel.add(rdButtonA);
		panel.add(rdButtonB);
		panel.add(rdButtonC);
		panel.add(rdButtonD);

		panelMain.add(panel, BorderLayout.WEST);

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
	
	public JList<CauHoi> createListPart1(ArrayList<CauHoi> lstPart1){
		JList<CauHoi> lstCauHoi = new JList<>();
		DefaultListModel<CauHoi> model = new  DefaultListModel<>();
		for(int i = 0; i < lstPart1.size(); i++){
			model.addElement(lstPart1.get(i));
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
			return "Part 1 - Bộ câu: " + thuTuDe + "- Đã hoàn thành";
		}
		
		return "Part 1 - Bộ câu: " + thuTuDe + " - Chưa hoàn thành";
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
