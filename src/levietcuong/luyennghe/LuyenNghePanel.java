package levietcuong.luyennghe;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javazoom.jl.player.Player;
import levietcuong.Home.MyImage;
import levietcuong.partToeic.CauHoi;
import levietcuong.partToeic.LoadBoCauHoi;
import levietcuong.partToeic.QuanLyPart1;
import levietcuong.partToeic.QuanLyPart2;
import levietcuong.partToeic.QuanLyPart3;
import levietcuong.partToeic.QuanLyPart4;
import levietcuong.thithu.Test;
import levietcuong.thithu.TestRender;

public class LuyenNghePanel extends JPanel implements ActionListener{
	private JButton btnPart1, btnPart2, btnPart3, btnPart4;
	private JPanel panelMain, panelPart1, panelPart2, panelPart3, panelPart4, pnMainPart1, pnMainPart2, pnMainPart3, pnMainPart4;
	public static JList<QuanLyPart1> lstPart1;
	public static JList<QuanLyPart2> lstPart2;
	public static JList<QuanLyPart3> lstPart3;
	public static JList<QuanLyPart4> lstPart4;
	public static int buttonCurrent = -1, audioCurrent = -1;

	private Image img;
	
	public LuyenNghePanel() {
		// TODO Auto-generated constructor stub
		try {
			img = ImageIO.read(new File("src/image/background.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addControlls();
		addEvents();
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		lstPart1.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					/*Tắt audio đang phát trước đó*/
					TatAudioDangPhat();
					/*hiện panel chọn trong jlist */
					CardLayout card = (CardLayout) pnMainPart1.getLayout();
					card.show(pnMainPart1, "Panel"+lstPart1.getSelectedIndex());
					lstPart1.getSelectedValue().displayAudio();
					audioCurrent = lstPart1.getSelectedIndex();
				}
			}
		});

		lstPart2.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					/*Tắt audio đang phát trước đó*/
					TatAudioDangPhat();
					
					/*hiện panel chọn trong jlist */
					CardLayout card = (CardLayout) pnMainPart2.getLayout();
					card.show(pnMainPart2, "Panel"+lstPart2.getSelectedIndex());
					lstPart2.getSelectedValue().displayAudio();
					audioCurrent = lstPart2.getSelectedIndex();
				}
			}
		});
		
		lstPart3.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					/*Tắt audio đang phát trước đó*/
					TatAudioDangPhat();
					
					/*hiện panel chọn trong jlist */
					CardLayout card = (CardLayout) pnMainPart3.getLayout();
					card.show(pnMainPart3, "Panel"+lstPart3.getSelectedIndex());
					lstPart3.getSelectedValue().displayAudio();
					audioCurrent = lstPart3.getSelectedIndex();
				}
			}
		});

		lstPart4.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				btnPart4.setEnabled(false);
				if (e.getValueIsAdjusting()) {
					/*Tắt audio đang phát trước đó*/
					TatAudioDangPhat();
					
					/*hiện panel chọn trong jlist */
					CardLayout card = (CardLayout) pnMainPart4.getLayout();
					card.show(pnMainPart4, "Panel"+lstPart4.getSelectedIndex());
					lstPart4.getSelectedValue().displayAudio();
					audioCurrent = lstPart4.getSelectedIndex();
				}
			}
		});

	}
	
	public void TatAudioDangPhat(){
		if(buttonCurrent == 1){
			if(audioCurrent != -1){
				Player play = lstPart1.getModel().getElementAt(audioCurrent).audioPart1;
				if(play != null) {
					play.close();
				}
				audioCurrent = -1;
			}
		}else if(buttonCurrent == 2){
			if(audioCurrent != -1){
				Player play = lstPart2.getModel().getElementAt(audioCurrent).audioPart2;
				if(play != null) {
					play.close();
				}
				audioCurrent = -1;
			}
		}else if(buttonCurrent == 3){
			if(audioCurrent != -1){
				Player play = lstPart3.getModel().getElementAt(audioCurrent).audioPart3;
				if(play != null) {
					play.close();
				}
				audioCurrent = -1;
			}
		}else if(buttonCurrent == 4){
			if(audioCurrent != -1){
				Player play = lstPart4.getModel().getElementAt(audioCurrent).audioPart4;
				if(play != null) {
					play.close();
				}
				audioCurrent = -1;
			}
		}
	}

	private void addControlls() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		
		panelMain = new JPanel(new CardLayout());
		panelMain.add(panelPart1 = createPanelPart1(), "Part1");
		panelMain.add(panelPart2 = createPanelPart2(), "Part2");
		panelMain.add(panelPart3 = createPanelPart3(), "Part3");
		panelMain.add(panelPart4 = createPanelPart4(), "Part4");
		add(panelMain, BorderLayout.CENTER);

		JPanel pnButton = new JPanel();
		add(pnButton = createPanelButton(), BorderLayout.WEST);
	}

	private JPanel createPanelButton() {
		// TODO Auto-generated method stub
		JPanel pnButton = new JPanel(new GridLayout(10, 0, 10, 10));

		btnPart1 = createButton("Part 1");
		btnPart2 = createButton("Part 2");
		btnPart3 = createButton("Part 3");
		btnPart4 = createButton("Part 4");

		pnButton.add(btnPart1);
		pnButton.add(btnPart2);
		pnButton.add(btnPart3);
		pnButton.add(btnPart4);
		return pnButton;
	}

	private JPanel createPanelPart4() {
		// TODO Auto-generated method stub
		JPanel panelPart4 = new JPanel(new BorderLayout());
		
		lstPart4 = new JList<QuanLyPart4>();
		DefaultListModel<QuanLyPart4> model = createPart(4);
		lstPart4.setModel(model);
		panelPart4.add(lstPart4, BorderLayout.WEST);

		/*center*/
		pnMainPart4 = new JPanel(new CardLayout());
		pnMainPart4.add(createPanelHuongDan("Chiến lược part 4 TOEIC", "dethi/part4/chienluocpart4.txt"), "PanelHD");
		for(int i = 0; i < model.size(); i++) {
			pnMainPart4.add(model.get(i), "Panel"+i);
		}	
		panelPart4.add(pnMainPart4, BorderLayout.CENTER);
		
		return panelPart4;
	}

	private JPanel createPanelPart3() {
		// TODO Auto-generated method stub
		JPanel panelPart3 = new JPanel(new BorderLayout());
		
		/*west*/
		lstPart3 = new JList<QuanLyPart3>();
		DefaultListModel<QuanLyPart3> model = createPart(3);
		lstPart3.setModel(model);
		panelPart3.add(lstPart3, BorderLayout.WEST);

		/*center*/
		pnMainPart3 = new JPanel(new CardLayout());
		pnMainPart3.add(createPanelHuongDan("Chiến lược part 3 TOEIC", "dethi/part3/chienluocpart3.txt"), "PanelHD");
		for(int i = 0; i < model.size(); i++) {
			pnMainPart3.add(model.get(i), "Panel"+i);
		}	
		panelPart3.add(pnMainPart3, BorderLayout.CENTER);
		
		return panelPart3;
	}

	private JPanel createPanelPart2() {
		// TODO Auto-generated method stub
		JPanel panelPart2 = new JPanel(new BorderLayout());
		/*west*/
		lstPart2 = new JList<QuanLyPart2>();
		DefaultListModel<QuanLyPart2> model = createPart(2);
		lstPart2.setModel(model);
		panelPart2.add(lstPart2, BorderLayout.WEST);

		/*center*/
		pnMainPart2 = new JPanel(new CardLayout());
		pnMainPart2.add(createPanelHuongDan("Chiến lược part 2 TOEIC", "dethi/part2/chienluocpart2.txt"), "PanelHD");
		for(int i = 0; i < model.size(); i++) {
			pnMainPart2.add(model.get(i), "Panel"+i);
		}	
		panelPart2.add(pnMainPart2, BorderLayout.CENTER);
		
		return panelPart2;
	}

	private JPanel createPanelPart1() {
		// TODO Auto-generated method stub
		JPanel panelPart1 = new JPanel(new BorderLayout());
		
		/*west*/
		lstPart1 = new JList<QuanLyPart1>();
		DefaultListModel<QuanLyPart1> model = createPart(1);
		lstPart1.setModel(model);		
		panelPart1.add(new JScrollPane(lstPart1), BorderLayout.WEST);
		
		/*Center*/
		pnMainPart1 = new JPanel(new CardLayout());
		pnMainPart1.add(createPanelHuongDan("Chiến lược part 1 TOEIC", "dethi/part1/chienluocpart1.txt"), "PanelHuongDanPart1");
		for(int i = 0; i < model.size(); i++) {
			pnMainPart1.add(model.get(i), "Panel"+i);
		}
		panelPart1.add(pnMainPart1, BorderLayout.CENTER);
		
		return panelPart1;
	}
	
	public JPanel createPanelHuongDan(String title, String fileName) {
		JPanel pnMainPart = new JPanel(new BorderLayout());
		JLabel lblTieuDe = new JLabel(title);
		lblTieuDe.setForeground(Color.blue);
		lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));
		pnMainPart.add(lblTieuDe, BorderLayout.PAGE_START);
		
		JTextArea txtChienLuocPart1 = new JTextArea();
		//đọc file chiến lược part2
		FileReader fr = null;
		BufferedReader br = null;
		StringBuilder result = new StringBuilder();
		try {
			fr = new FileReader(new File(fileName));
			br = new BufferedReader(fr);
			
			String line = "";
			while((line = br.readLine()) != null){
				result.append(line).append("\n");
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		txtChienLuocPart1.setText(result.toString());
		txtChienLuocPart1.setFont(new Font("Arial", Font.ITALIC, 14));
		txtChienLuocPart1.setLineWrap(true);
		txtChienLuocPart1.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(txtChienLuocPart1);
		pnMainPart.add(scroll, BorderLayout.CENTER);
		
		return pnMainPart;
	}

	private DefaultListModel createPart(int part) {
		// TODO Auto-generated method stub
		DefaultListModel model = new DefaultListModel<>();
		ArrayList<CauHoi> dsCauHoi = new ArrayList<>();
		try {
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/dethi", "root", "vietcuong");
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from phannghe where partid='"+part+"'");
			int testID;
			while(rs.next()) {
				testID = rs.getInt(1);
				dsCauHoi = new LoadBoCauHoi(testID, part).lstCauHoi;
				if(part == 1) {
					QuanLyPart1 qlyPart1 = new QuanLyPart1(dsCauHoi);
					qlyPart1.setThuTuDe(testID);
					qlyPart1.setUrlAudio(rs.getString(3));
					model.addElement(qlyPart1);
				}
				
				if(part == 2) {
					QuanLyPart2 qlyPart2 = new QuanLyPart2(dsCauHoi);
					qlyPart2.setThuTuDe(testID);
					qlyPart2.setUrlAudio(rs.getString(3));
					model.addElement(qlyPart2);
				}
				
				if(part == 3) {
					QuanLyPart3 qlyPart3 = new QuanLyPart3(dsCauHoi);
					qlyPart3.setThuTuDe(testID);
					qlyPart3.setUrlAudio(rs.getString(3));
					model.addElement(qlyPart3);
				}
				
				if(part == 4) {
					QuanLyPart4 qlyPart4 = new QuanLyPart4(dsCauHoi);
					qlyPart4.setThuTuDe(testID);
					qlyPart4.setUrlAudio(rs.getString(3));
					model.addElement(qlyPart4);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}

	public JButton createButton(String name) {
		JButton btn = new JButton(name);

		btn.setIcon(new MyImage("src/image/icon.jpg", 50, 40).getImg());
		btn.setFont(new Font("Arial", Font.BOLD, 20));

		btn.addActionListener(this);
		return btn;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(img, 0, 30, getWidth(), getHeight(), null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnPart1) {
			/*Tắt audio đang phát trước đó*/
			TatAudioDangPhat();
			buttonCurrent = 1;
			
			CardLayout card = (CardLayout) panelMain.getLayout();
			card.show(panelMain, "Part1");
		}
		
		if(e.getSource() == btnPart2) {
			/*Tắt audio đang phát trước đó*/
			TatAudioDangPhat();
			
			buttonCurrent = 2;
			CardLayout card = (CardLayout) panelMain.getLayout();
			card.show(panelMain, "Part2");
		}
		
		if(e.getSource() == btnPart3) {
			/*Tắt audio đang phát trước đó*/
			TatAudioDangPhat();
			
			buttonCurrent = 3;
			CardLayout card = (CardLayout) panelMain.getLayout();
			card.show(panelMain, "Part3");
		}
		
		if(e.getSource() == btnPart4) {
			/*Tắt audio đang phát trước đó*/
			TatAudioDangPhat();
			
			buttonCurrent = 4;
			CardLayout card = (CardLayout) panelMain.getLayout();
			card.show(panelMain, "Part4");
		}
	}
}
