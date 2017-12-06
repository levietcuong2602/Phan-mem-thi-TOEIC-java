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
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import levietcuong.Home.MyImage;
import levietcuong.luyendoc.LuyenDocPanel;
import levietcuong.partToeic.LoadBoCauHoi;

public class ThiThuPanel2 extends JPanel{
	private JList<Test> lstTest;
	private JButton btnTaoDe, btnDeGoc;
	public static JButton  btnNextPart, btnStart, btnNopBai;
	private Image img;
	private boolean checkBaiNghe = false, checkBaiDoc;
	private JPanel panelMain, pnGioiThieu, pnGioiThieu2, pnDongHo,
	pnButtonNgheDoc, panelTaoDe, panelButton;
	private int partCurrent = 1;
	private int min, soCauNghe = 0, soCauDoc = 0;
	private String time;
	private Thread thread;
	
	public ThiThuPanel2() {
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
		btnDeGoc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				initListTest();		
				
			}
		});
		
		btnTaoDe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				initListRandomTest();
			}
		});
		
		btnNextPart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				//neu dang lam phan nghe
		
				if(partCurrent == 1){
					btnNextPart.setVisible(false);
					
					partCurrent = 2;
					lstTest.getSelectedValue().getPart1().audioPart1.close();
					lstTest.getSelectedValue().getPart1().setVisible(false);
					pnButtonNgheDoc.add(lstTest.getSelectedValue().getPart2());
					lstTest.getSelectedValue().getPart2().displayAudio();
					lstTest.getSelectedValue().getPart2().getBtnSubmit().setVisible(false);
					
				}else if(partCurrent == 2){
					btnNextPart.setVisible(false);
					
					partCurrent = 3;
					lstTest.getSelectedValue().getPart2().audioPart2.close();
					lstTest.getSelectedValue().getPart2().setVisible(false);
					pnButtonNgheDoc.add(lstTest.getSelectedValue().getPart3());
					lstTest.getSelectedValue().getPart3().displayAudio();
					lstTest.getSelectedValue().getPart3().getBtnSubmit().setVisible(false);
				}else if(partCurrent == 3){
					btnNextPart.setVisible(false);
					
					partCurrent = 4;
					lstTest.getSelectedValue().getPart3().audioPart3.close();
					lstTest.getSelectedValue().getPart3().setVisible(false);
					pnButtonNgheDoc.add(lstTest.getSelectedValue().getPart4());
					lstTest.getSelectedValue().getPart4().displayAudio();
					lstTest.getSelectedValue().getPart4().getBtnSubmit().setVisible(false);
					
					btnNopBai.setVisible(true);
				}
				
				if(partCurrent == 5){
					btnNextPart.setVisible(false);
					
					partCurrent = 6;
					lstTest.getSelectedValue().getPart5().setVisible(false);
					pnButtonNgheDoc.add(lstTest.getSelectedValue().getPart6());
					lstTest.getSelectedValue().getPart6().getBtnSubmit().setVisible(false);
				}else if(partCurrent == 6){
					
					btnNopBai.setVisible(true);
				}else if(partCurrent == 7){
					
				}
			}
			
		});
		
		btnNopBai.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(checkBaiNghe == true){
					lstTest.getSelectedValue().getPart4().setVisible(false);
					lstTest.getSelectedValue().getPart4().audioPart4.close();
					panelButton.setVisible(true);
					
					lstTest.getSelectedValue().getPart1().TinhDiem();
					int diemPart1 = lstTest.getSelectedValue().getPart1().getScore();
					lstTest.getSelectedValue().getPart2().TinhDiem();
					int diemPart2 = lstTest.getSelectedValue().getPart2().getScore();
					lstTest.getSelectedValue().getPart3().TinhDiem();
					int diemPart3 = lstTest.getSelectedValue().getPart3().getScore();
					lstTest.getSelectedValue().getPart4().TinhDiem();
					int diemPart4 = lstTest.getSelectedValue().getPart4().getScore();

					soCauNghe = diemPart1 + diemPart2 + diemPart3 + diemPart4;
				}else{
					lstTest.getSelectedValue().getPart5().setVisible(false);
					panelButton.setVisible(true);
					
					lstTest.getSelectedValue().getPart5().TinhDiem();
					int diemPart5 = lstTest.getSelectedValue().getPart1().getScore();
					lstTest.getSelectedValue().getPart6().TinhDiem();
					int diemPart6 = lstTest.getSelectedValue().getPart1().getScore();
					
					soCauDoc = diemPart5 + diemPart6;
				}
				
				if(checkBaiNghe && checkBaiDoc){
					JOptionPane.showMessageDialog(null, "Điểm phần nghe: " + soCauNghe + "\n Điểm phần đọc: " + soCauDoc);
				}
				
				validate();
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Dimension d = getSize();
		g.drawImage(img, 0, 0, d.width, d.height, null);
//		g.drawImage(img, 0, 30, getWidth(), getHeight(), null);
	}
	
	public void addPanelStack(){
		createGioiThieu();
		createPanelNgheDoc();
		panelMain.add(pnGioiThieu, "GioiThieu");
		panelMain.add(pnButtonNgheDoc, "NgheDoc");
		
		add(panelMain, BorderLayout.CENTER);
	}
	
	private void addControlls() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		
		/*page start*/
		add(panelTaoDe = createPanelTaoDe());
		
		/*center*/
		panelMain = new JPanel(new CardLayout());
		JPanel pnNopBai = new JPanel(new FlowLayout());
		pnNopBai.add(btnNopBai);
		add(pnNopBai, BorderLayout.PAGE_END);
		btnNopBai.setVisible(false);
		
		pnButtonNgheDoc = new JPanel();
	}
	
	public JPanel createPanelTaoDe(){
		JPanel panelTaoDe = new JPanel(new FlowLayout());
		panelTaoDe.setBackground(new Color(0x9999));
		btnDeGoc = new JButton("Tạo đề gốc");
		btnTaoDe = new JButton("Tự động tạo đề");
		panelTaoDe.add(btnDeGoc);
		panelTaoDe.add(btnTaoDe);
		
		return panelTaoDe;
	}
	
	public void initListRandomTest(){
		lstTest = new JList<Test>();
		DefaultListModel<Test> model = new DefaultListModel<>();
		model.addElement(new LoadBoCauHoi().testRandom);
		lstTest.setModel(model);
		
		JScrollPane src = new JScrollPane(lstTest);
		add(src, BorderLayout.WEST);
		
		JOptionPane.showMessageDialog(null, "Da tao de !"); 	
		addPanelStack();
		validate();
		
		lstTest.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				pnGioiThieu2.setVisible(true);
			}
		});
	}
	
	private void initListTest() {
		// TODO Auto-generated method stub
		//khởi tạo thong bao
		JOptionPane.showMessageDialog(null, "Da tao de !"); 

		
		lstTest = new JList<Test>();
		DefaultListModel<Test> model = new DefaultListModel<>();
		
		model.addElement(new LoadBoCauHoi(1).test);
		lstTest.setModel(model);
		
	
		JScrollPane src = new JScrollPane(lstTest);
		add(src, BorderLayout.WEST);
		
		addPanelStack();
		validate();
		
		lstTest.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				pnGioiThieu2.setVisible(true);
			}
		});
		
		
	}
	
	public void createGioiThieu() {
		pnGioiThieu = new JPanel(new GridLayout(0, 1));
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
		
		createGioiThieu2();
		pnGioiThieu.add(pnGioiThieu2);
		pnGioiThieu2.setVisible(false);
	}

	public void createGioiThieu2() {
		JButton btnStart = new JButton();
		btnStart.setIcon(new MyImage("src/image/start.jpg", 100, 200).getImg());
		pnGioiThieu2 = new JPanel(new BorderLayout());
		pnGioiThieu2.setBackground(new Color(0x009999));
		JLabel lbl = new JLabel(
				"Bạn hãy click vào nút Start bên dưới để bắt đầu " + "làm bài. Chúc các bạn đạt điểm số thật cao!");
		lbl.setForeground(Color.black);
		lbl.setFont(new Font("Arial", Font.ITALIC, 20));
		pnGioiThieu2.add(lbl, BorderLayout.PAGE_START);
		JPanel pnbutton = new JPanel();
		pnbutton.add(btnStart);
		pnGioiThieu2.add(pnbutton, BorderLayout.PAGE_END);

		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panelTaoDe.setVisible(false);
				CardLayout card = (CardLayout) panelMain.getLayout();
				card.show(panelMain, "NgheDoc");
			}
		});

	}

	public void createPanelNgheDoc() {
		pnButtonNgheDoc = new JPanel(new BorderLayout());
		JPanel pnWatch = createWatch();
		pnWatch.setBackground(new Color(0x009999));
		pnButtonNgheDoc.add(pnWatch, BorderLayout.PAGE_START);

		JButton btnNghe = new JButton("Click vào để bắt đầu phần nghe");
		JButton btnDoc = new JButton("Click vào để bắt đầu phần đọc");
		panelButton = new JPanel();
		panelButton.add(btnNghe);
		panelButton.add(btnDoc);
		pnButtonNgheDoc.add(panelButton);
		
		// xử lý sự kiện cho phần nghe, đọc
		btnDoc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(checkBaiNghe == true){
					btnNghe.setEnabled(false);
					if(partCurrent == 1){
						lstTest.getSelectedValue().getPart1().audioPart1.close();
					}else if(partCurrent == 2){
						lstTest.getSelectedValue().getPart2().audioPart2.close();
					}else if(partCurrent == 3){
						lstTest.getSelectedValue().getPart3().audioPart3.close();
					}else if(partCurrent == 4){
						lstTest.getSelectedValue().getPart4().audioPart4.close();
					}
				}
				
				btnNopBai.setVisible(false);
				
				checkBaiDoc = true;
				panelButton.setVisible(false);
				partCurrent = 5;
				pnButtonNgheDoc.add(lstTest.getSelectedValue().getPart5(), BorderLayout.CENTER);
				lstTest.getSelectedValue().getPart5().getBtnSubmit().setVisible(false);
				
				//tao nut next
				JPanel panel = new JPanel(new FlowLayout());
				panel.add(btnNextPart);
				add(panel, BorderLayout.PAGE_START);
				btnNextPart.setVisible(false);
				
				validate();
				repaint();
			}
		});

		btnNghe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				checkBaiNghe = true;
	
				panelButton.setVisible(false);
	
				btnNopBai.setVisible(false);
				partCurrent = 1;
				//tao panelnghe
				pnButtonNgheDoc.add(lstTest.getSelectedValue().getPart1(), BorderLayout.CENTER);
				lstTest.getSelectedValue().getPart1().displayAudio();
				lstTest.getSelectedValue().getPart1().getBtnSubmit().setVisible(false);	
				
				//tao nut next
				JPanel panel = new JPanel(new FlowLayout());
				panel.add(btnNextPart);
				add(panel, BorderLayout.PAGE_START);
				btnNextPart.setVisible(false);
				
				thread.start();
			
				validate();
				repaint();
			}
		});
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
		
		f.add(new ThiThuPanel2());
		f.validate();
		f.repaint();
	}
}
