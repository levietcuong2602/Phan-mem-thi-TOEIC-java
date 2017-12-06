package levietcuong.thithu;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ProgressMonitor;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import levietcuong.Home.MyImage;
import levietcuong.luyendoc.LuyenDocPanel;
import levietcuong.partToeic.LoadBoCauHoi;
import levietcuong.partToeic.QuanLyPart1;
import levietcuong.partToeic.QuanLyPart2;
import levietcuong.partToeic.QuanLyPart3;
import levietcuong.partToeic.QuanLyPart4;
import levietcuong.partToeic.QuanLyPart5;
import levietcuong.partToeic.QuanLyPart6;

public class ThiThuPanel extends JPanel implements ActionListener{
	private JList<Test> listTestDeGoc;
	private JButton btnRandom, btnDeGoc;
	public static JButton  btnNextPart, btnStart, btnNopBai;
	private Image img;
	private JPanel panelMain, pnGioiThieu, panelButtonStart, pnDongHo,
	panelNgheDoc, panelTaoDe;
	private int luaChon = -1;
	private int min, soCauNghe = 0, soCauDoc = 0;
	private String time;
	private Thread thread;
	private JPanel panelHuongDan;
	private JPanel panelTaoDeGoc;
	private JPanel panelDeGoc;
	private JButton btnNghe;
	private JButton btnDoc;
	private JList listPart;
	private JPanel panelMainNgheDoc;
	private JPanel panelSouth;
	private String modeNgheDoc = "";
	
	public ThiThuPanel() {
		// TODO Auto-generated constructor stub
		
		try {
			img = ImageIO.read(new File("src/image/background.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnNextPart = new JButton("Next Part");
		
		addControlls();
		addEvents();
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		listTestDeGoc.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(e.getValueIsAdjusting()) {
					panelButtonStart.setVisible(true);
					return;
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Dimension d = getSize();
		g.drawImage(img, 0, 0, d.width, d.height, null);
	}

	private void addControlls() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		
		/*page start*/
		add(panelTaoDe = createPanelButton(), BorderLayout.PAGE_START);
		
		/*center*/
		panelMain = new JPanel(new CardLayout());
		panelMain.add(new JPanel(), "Panel0");
		panelMain.add(panelDeGoc = createPanelDeGoc(), "PanelDeGoc");
		panelMain.add(panelNgheDoc = createPanelNgheDoc(), "PanelNgheDoc");
		add(panelMain, BorderLayout.CENTER);
		
		/*South: panel chuyển part và nộp bài*/
		panelSouth = new JPanel(new FlowLayout());
		panelSouth.setBorder(new EmptyBorder(0, 200, 0, 0));
		btnNextPart = createButton("Chuyển Part tiếp");
		btnNextPart.setIcon(new MyImage("src/image/right.png", 20, 20).getImg());
		btnNopBai = createButton("Nộp bài");
		btnNopBai.setIcon(new MyImage("src/image/submit.jpg", 20, 20).getImg());
		panelSouth.add(btnNextPart);
		panelSouth.add(btnNopBai);
		add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setVisible(false);
	}
	
	private JPanel createPanelDeGoc() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new BorderLayout());
		
		/*west*/
		listTestDeGoc = new JList<Test>();
		DefaultListModel<Test> model = new DefaultListModel<>();
		model.addElement(new LoadBoCauHoi(1).test);
		listTestDeGoc.setModel(model);
		JScrollPane src = new JScrollPane(listTestDeGoc);
		panel.add(src, BorderLayout.WEST);
		
		/*center*/
		panel.add(panelHuongDan = createPanelGioiThieu(), BorderLayout.CENTER);
		
		return panel;
	}

	public JPanel createPanelButton(){
		JPanel panelTaoDe = new JPanel(new FlowLayout());
		panelTaoDe.setBackground(new Color(0x9999));
		btnDeGoc = createButton("Tạo đề gốc");
		btnRandom = createButton("Tự động tạo đề");
		panelTaoDe.add(btnDeGoc);
		panelTaoDe.add(btnRandom);
		
		return panelTaoDe;
	}
	
	private JButton createButton(String name) {
		// TODO Auto-generated method stub
		JButton button = new JButton(name);
		button.addActionListener(this);
		return button;
	}

	public void initListRandomTest(){
		listTestDeGoc = new JList<Test>();
		DefaultListModel<Test> model = new DefaultListModel<>();
		model.addElement(new LoadBoCauHoi().testRandom);
		listTestDeGoc.setModel(model);
		
		JScrollPane src = new JScrollPane(listTestDeGoc);
		add(src, BorderLayout.WEST);
		
		JOptionPane.showMessageDialog(null, "Da tao de !"); 	
		
		listTestDeGoc.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				panelButtonStart.setVisible(true);
			}
		});
	}

	public JPanel createPanelGioiThieu() {
		JPanel pnGioiThieu = new JPanel(new GridLayout(0, 1));
		pnGioiThieu.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.red), ""));
		JTextArea txtHuongDan = new JTextArea();
		txtHuongDan.setText("Chào mừng các bạn đến với đề thi thử TOEIC"
				+ " trong chương trình Luyện thi TOEIC online của TiếngAnh123.Com!"
				+ " Đây là bài thi mô phỏng dạng đề thi TOEIC thực tế do đội ngũ "
				+ "giáo viên của TiếngAnh123.Com kì công biên soạn. Bài làm của các"
				+ " bạn sẽ được chấm điểm và thông báo kết quả ngay sau khi các bạn" + " nộp bài.");
		txtHuongDan.setFont(new Font("Arial", Font.ITALIC, 24));
		txtHuongDan.setWrapStyleWord(true);
		txtHuongDan.setLineWrap(true);
		pnGioiThieu.add(txtHuongDan);
		
		/*panel 2*/
		pnGioiThieu.add(panelButtonStart = createPanelButtonStart());
		panelButtonStart.setVisible(false);
		
		return pnGioiThieu;
	}

	public JPanel createPanelButtonStart() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(0x009999));
		
		btnStart = createButton("Start");
		btnStart.setIcon(new MyImage("src/image/start.jpg", 100, 200).getImg());
		JLabel lbl = new JLabel(
				"Bạn hãy click vào nút Start bên dưới để bắt đầu " + "làm bài. Chúc các bạn đạt điểm số thật cao!");
		lbl.setForeground(Color.black);
		lbl.setFont(new Font("Arial", Font.ITALIC, 20));
		panel.add(lbl, BorderLayout.PAGE_START);
		JPanel pnbutton = new JPanel();
		pnbutton.add(btnStart);
		panel.add(pnbutton, BorderLayout.PAGE_END);

		return panel;
	}

	public JPanel createPanelNgheDoc() {
		JPanel panelNgheDoc = new JPanel(new BorderLayout());
		JPanel pnWatch = createWatch();
		pnWatch.setBackground(new Color(0x009999));
		panelNgheDoc.add(pnWatch, BorderLayout.PAGE_START);

		btnNghe = createButton("Click vào để bắt đầu phần nghe");
		btnDoc = createButton("Click vào để bắt đầu phần đọc");
		
		panelMainNgheDoc = new JPanel(new CardLayout());
		JPanel panelButton = new JPanel();
		panelButton.add(btnNghe);
		panelButton.add(btnDoc);
		
		panelMainNgheDoc.add(panelButton, "PanelButton");
		panelNgheDoc.add(panelMainNgheDoc);
		
		return panelNgheDoc;
	}
	
	public JPanel createWatch() {
		JPanel panel = new JPanel();
		JLabel lblTime = new JLabel();
		
		lblTime.setIcon(new levietcuong.Home.MyImage("src/image/watch.png", 20, 20).getImg());
		min = 120;

		time = 120 + ":00";
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int second = 0;
				int i = 1;
				while (i > 0) {
					i--;
					time = min + ":" + second;

					if (i <= 0) {
						second--;
						time = min + ":" + second;
						i = 1000;
					}

					if (second < 10) {
						time = min + ":0" + second;
					}

					if (second < 0) {
						min--;
						time = min + ":" + second;
						second = 59;
					}

					if (min < 0) {
						i = -1;
						time = "000" + ":00";
						thread.stop();
					}
					lblTime.setText(time);
					try {
						thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		lblTime.setText(time);

		panel.add(lblTime);
		lblTime.setFont(new Font("Arial", Font.BOLD, 14));
		lblTime.setForeground(Color.black);

		return panel;
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(500, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setLayout(new BorderLayout());
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		f.add(new ThiThuPanel());
		f.validate();
		f.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnDeGoc) {
			CardLayout card = (CardLayout) panelMain.getLayout();
			card.show(panelMain, "PanelDeGoc");
			
			luaChon = 1;
			return;
		}
		
		if(e.getSource() == btnStart) {
			CardLayout card = (CardLayout) panelMain.getLayout();
			card.show(panelMain, "PanelNgheDoc");
			
			listPart = createListPart();
			JScrollPane scr = new JScrollPane(listPart);
			panelNgheDoc.add(scr, BorderLayout.WEST);
			
			/*thêm cardlayout các panel part*/
			DefaultListModel model = (DefaultListModel) listPart.getModel();
			panelMainNgheDoc.add((QuanLyPart1)model.get(0), "Part1");
			panelMainNgheDoc.add((QuanLyPart2)model.get(1), "Part2");
			panelMainNgheDoc.add((QuanLyPart3)model.get(2), "Part3");
			panelMainNgheDoc.add((QuanLyPart4)model.get(3), "Part4");
			panelMainNgheDoc.add((QuanLyPart5)model.get(4), "Part5");
			panelMainNgheDoc.add((QuanLyPart6)model.get(5), "Part6");
			
		}
		
		if(e.getSource() == btnNghe) {
			panelTaoDe.setVisible(false);
			/*Cho đồng hồ chạy*/
			if(thread.isInterrupted() == false) {
				thread.start();
			}
			
			/*chọn đến part 1*/
			listPart.setSelectedIndex(0);
			
			/*panel main nghe doc chọn đến panel part1*/
			CardLayout card = (CardLayout) panelMainNgheDoc.getLayout();
			card.show(panelMainNgheDoc, "Part1");
			
			QuanLyPart1 part1 = (QuanLyPart1) listPart.getSelectedValue();
			part1.getBtnSubmit().setVisible(false);
			part1.displayAudio();
			
			/*Hiện button nộp bài và chuyển part*/
			panelSouth.setVisible(true);
			btnNopBai.setText("Kết thúc phần nghe");
			
			/*mode*/
			modeNgheDoc = "listen";
		}
		
		if(e.getSource() == btnNextPart) {
			int partHienTai = listPart.getSelectedIndex();
			/*tắt audio nghe*/
			tatAudio(partHienTai);
			
			/*chuyển sang part tiếp theo*/
			listPart.setSelectedIndex(partHienTai+1); 
			/*Cập nhật lại part hiện tại*/
			partHienTai = listPart.getSelectedIndex();
			if(partHienTai == 3) {
				/*hết phân nghe*/
				btnNextPart.setEnabled(false);
			}else if(partHienTai == 5) {
				/*hết phân đọc*/
				btnNextPart.setEnabled(false);
			}
			/*phát audio*/
			phatAudio(partHienTai);
			
			/*cho panel main nghe đọc chuyển panel*/
			CardLayout card = (CardLayout) panelMainNgheDoc.getLayout();
			card.show(panelMainNgheDoc, "Part"+(partHienTai+1));				
		}
		
		if(e.getSource() == btnNopBai) {
			if(modeNgheDoc.equalsIgnoreCase("listen")) {
				int partHienTai = listPart.getSelectedIndex();
				/*tắt audio nghe*/
				tatAudio(partHienTai);
				
				/*Chuyển sang panel chọn phần đọc*/
				CardLayout card = (CardLayout) panelMainNgheDoc.getLayout();
				card.show(panelMainNgheDoc, "PanelButton");
			}
		}
	}
	
	public void tatAudio(int partHienTai) {
		/*tắt audio nghe*/
		if(partHienTai == 0) {
			//đóng audio part 1
			QuanLyPart1 part1 = (QuanLyPart1) listPart.getSelectedValue();
			part1.audioPart1.close();
			
		}else if(partHienTai == 1) {
			//đóng audio part2
			QuanLyPart2 part2 = (QuanLyPart2) listPart.getSelectedValue();
			part2.audioPart2.close();
		}else if(partHienTai == 2) {
			/*đóng audio part 3*/
			QuanLyPart3 part3 = (QuanLyPart3) listPart.getSelectedValue();
			part3.audioPart3.close();
		}else if(partHienTai == 3) {
			/*đóng audio part 4*/
			QuanLyPart4 part4 = (QuanLyPart4) listPart.getSelectedValue();
			part4.audioPart4.close();
		}
	}
	
	public void phatAudio(int partHienTai) {
		if(partHienTai == 0) {
			//phát audio part1
			QuanLyPart1 part1 = (QuanLyPart1) listPart.getSelectedValue();
			part1.displayAudio();
			part1.getBtnSubmit().setVisible(false);
		}else if(partHienTai == 1) {
			//phát audio part 2
			QuanLyPart2 part2 = (QuanLyPart2) listPart.getSelectedValue();
			part2.displayAudio();
			part2.getBtnSubmit().setVisible(false);
		}else if(partHienTai == 2) {
			/*phát audio part 3*/
			QuanLyPart3 part3 = (QuanLyPart3) listPart.getSelectedValue();
			part3.displayAudio();
			part3.getBtnSubmit().setVisible(false);
		}else if(partHienTai == 3) {
			/*phát audio part 4*/
			QuanLyPart4 part4 = (QuanLyPart4) listPart.getSelectedValue();
			part4.displayAudio();
			part4.getBtnSubmit().setVisible(false);
		}
	}

 	private JList createListPart() {
		// TODO Auto-generated method stub
		/*west*/
		JList listPart = new JList<>();
		DefaultListModel<Object> model = new DefaultListModel<>();
		if(luaChon == 1) {
			Test test = listTestDeGoc.getSelectedValue();
			model.addElement(test.getPart1());
			model.addElement(test.getPart2());
			model.addElement(test.getPart3());
			model.addElement(test.getPart4());
			model.addElement(test.getPart5());
			model.addElement(test.getPart6());
			
		}
		listPart.setModel(model);
		
		return listPart;
	}
}
