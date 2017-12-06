package levietcuong.luyendoc;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import levietcuong.Home.MyImage;
import levietcuong.partToeic.CauHoi;
import levietcuong.partToeic.LoadBoCauHoi;
import levietcuong.partToeic.QuanLyPart1;
import levietcuong.partToeic.QuanLyPart3;
import levietcuong.partToeic.QuanLyPart4;
import levietcuong.partToeic.QuanLyPart5;
import levietcuong.partToeic.QuanLyPart6;
import levietcuong.partToeic.QuanLyPart7;

public class LuyenDocPanel extends JPanel implements ActionListener{
	private JButton btnPart5, btnPart6, btnPart7;
	private JPanel panelMain;
	public JList<QuanLyPart5> lstPart5;
	public JList<QuanLyPart6> lstPart6;
	public JList<QuanLyPart7> lstPart7;
	private Image img;
	private JPanel pnMainPart5;
	private JPanel panelPart5, panelPart6;
	private JPanel pnMainPart6, pnMainPart7;
	private JPanel panelPart7;
	
	public LuyenDocPanel() {
		// TODO Auto-generated constructor
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
		
		lstPart5.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				CardLayout card = (CardLayout) pnMainPart5.getLayout();
				card.show(pnMainPart5, "Panel"+lstPart5.getSelectedIndex());
			}
		});
		
		lstPart6.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				CardLayout card = (CardLayout) pnMainPart6.getLayout();
				card.show(pnMainPart6, "Panel"+lstPart6.getSelectedIndex());
			}
		});
		
		lstPart7.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				CardLayout card = (CardLayout) pnMainPart7.getLayout();
				card.show(pnMainPart7, "Panel"+lstPart7.getSelectedIndex());
			}
		});
	}

	private void addControlls() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		
		panelMain = new JPanel(new CardLayout());
		panelMain.add(panelPart5 = createPanelPart5(), "Part 5");
		panelMain.add(panelPart6 = createPanelPart6(), "Part 6");
		panelMain.add(panelPart7 = createPanelPart7(), "Part 7");
		add(panelMain, BorderLayout.CENTER);
		
		JPanel pnButton = new JPanel();
		add(pnButton = createPanelButton(), BorderLayout.WEST);
	}
	
	private JPanel createPanelPart7() {
		// TODO Auto-generated method stub
		JPanel panelPart7 = new JPanel(new BorderLayout());
		
		ArrayList<CauHoi> dsCauHoi = new ArrayList<>();
		lstPart7 = new JList<QuanLyPart7>();
		DefaultListModel<QuanLyPart7> model = new DefaultListModel<>();
		
		try {
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/dethi", "root", "vietcuong");
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select distinct testid from cauhoi where partid='7'");
			int testID;
			while(rs.next()) {
				testID = rs.getInt(1);
				dsCauHoi = new LoadBoCauHoi(testID, 7).lstCauHoi;
				QuanLyPart7 qlyPart7 = new QuanLyPart7(dsCauHoi);
				qlyPart7.setThuTuDe(testID);
				model.addElement(qlyPart7);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lstPart7.setModel(model);
		panelPart7.add(lstPart7, BorderLayout.WEST);

		/*center*/
		pnMainPart7 = new JPanel(new CardLayout());
		pnMainPart7.add(createPanelHuongDan("Chiến lược part 7 TOEIC", "dethi/part7/chienluocpart7.txt"), "PanelHD");
		for(int i = 0; i < model.size(); i++) {
			pnMainPart7.add(model.get(i), "Panel"+i);
		}	
		panelPart7.add(pnMainPart7, BorderLayout.CENTER);
		
		return panelPart7;
	}

	private JPanel createPanelPart6() {
		// TODO Auto-generated method stub
		JPanel panelPart6 = new JPanel(new BorderLayout());
		
		ArrayList<CauHoi> dsCauHoi = new ArrayList<>();
		lstPart6 = new JList<QuanLyPart6>();
		DefaultListModel<QuanLyPart6> model = new DefaultListModel<>();
		
		try {
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/dethi", "root", "vietcuong");
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select distinct testid from cauhoi where partid='6'");
			int testID;
			while(rs.next()) {
				testID = rs.getInt(1);
				dsCauHoi = new LoadBoCauHoi(testID, 6).lstCauHoi;
				QuanLyPart6 qlyPart6 = new QuanLyPart6(dsCauHoi);
				qlyPart6.setThuTuDe(testID);
				model.addElement(qlyPart6);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lstPart6.setModel(model);
		panelPart6.add(lstPart6, BorderLayout.WEST);

		/*center*/
		pnMainPart6 = new JPanel(new CardLayout());
		pnMainPart6.add(createPanelHuongDan("Chiến lược part 6 TOEIC", "dethi/part6/chienluocpart6.txt"), "PanelHD");
		for(int i = 0; i < model.size(); i++) {
			pnMainPart6.add(model.get(i), "Panel"+i);
		}	
		panelPart6.add(pnMainPart6, BorderLayout.CENTER);
		
		return panelPart6;
	}

	private JPanel createPanelButton() {
		// TODO Auto-generated method stub
		JPanel pnButton = new JPanel(new GridLayout(10, 0, 10, 10));
		btnPart5 = createButton("Part 5");
		btnPart6 = createButton("Part 6");
		btnPart7 = createButton("Part 7");
		
		pnButton.add(btnPart5);
		pnButton.add(btnPart6);
		pnButton.add(btnPart7);
		
		return pnButton;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(img, 0, 30, getWidth(), getHeight(), null);
	}
	
	private JPanel createPanelPart5() {
		// TODO Auto-generated method stub
		JPanel panelPart5 = new JPanel(new BorderLayout());
		
		ArrayList<CauHoi> dsCauHoi = new ArrayList<>();
		lstPart5 = new JList<QuanLyPart5>();
		DefaultListModel<QuanLyPart5> model = new DefaultListModel<>();
		
		try {
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/dethi", "root", "vietcuong");
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select distinct testid from cauhoi where partid='5'");
			int testID;
			while(rs.next()) {
				testID = rs.getInt(1);
				dsCauHoi = new LoadBoCauHoi(testID, 5).lstCauHoi;
				QuanLyPart5 qlyPart5 = new QuanLyPart5(dsCauHoi);
				qlyPart5.setThuTuDe(testID);
				model.addElement(qlyPart5);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lstPart5.setModel(model);
		panelPart5.add(lstPart5, BorderLayout.WEST);

		/*center*/
		pnMainPart5 = new JPanel(new CardLayout());
		pnMainPart5.add(createPanelHuongDan("Chiến lược part 5 TOEIC", "dethi/part5/chienluocpart5.txt"), "PanelHD");
		for(int i = 0; i < model.size(); i++) {
			pnMainPart5.add(model.get(i), "Panel"+i);
		}	
		panelPart5.add(pnMainPart5, BorderLayout.CENTER);
		
		return panelPart5;
	}
	
	private Component createPanelHuongDan(String title, String fileName) {
		// TODO Auto-generated method stub
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

	public JButton createButton(String name) {
		JButton btn = new JButton(name);
		btn.setIcon(new MyImage("src/image/icon.jpg", 50, 40).getImg());
		btn.setFont(new Font("Arial", Font.BOLD, 20));

		btn.addActionListener(this);
		return btn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnPart5) {
			CardLayout card = (CardLayout) panelMain.getLayout();
			card.show(panelMain, "Part 5");
		}
		
		if(e.getSource() == btnPart6) {
			CardLayout card = (CardLayout) panelMain.getLayout();
			card.show(panelMain, "Part 6");
		}
		
		if(e.getSource() == btnPart7) {
			CardLayout card = (CardLayout) panelMain.getLayout();
			card.show(panelMain, "Part 7");
		}
	}
}
