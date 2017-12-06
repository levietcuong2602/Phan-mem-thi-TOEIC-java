package levietcuong.Home;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import levietcuong.luyendoc.LuyenDocPanel;
import levietcuong.luyennghe.LuyenNghePanel;
import levietcuong.thithu.ThiThuPanel;
import levietcuong.tudien.TuDienPanel;

public class MainFrame extends JFrame{
	private JTabbedPane tp;
	
	public MainFrame() {
		// TODO Auto-generated constructor stub
		addControlls();
		showWindows();
	}

	public void showWindows() {
		// TODO Auto-generated method stub
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("He thong luyen thi toeic tren desktop");
	}

	private void addControlls() {
		// TODO Auto-generated method stub
		tp = new JTabbedPane();
		tp.addTab("Luyện Nghe", new LuyenNghePanel());
		tp.addTab("Luyện Đọc", new LuyenDocPanel());
		tp.addTab("Thi Thử", new ThiThuPanel());
		tp.addTab("Từ Điển", new TuDienPanel());
		tp.addTab("Giới thiệu phần mềm", new JPanel());
		
		JLabel lblLuyenNghe = new JLabel("Luyện Nghe");
		lblLuyenNghe.setIcon(new MyImage("src/image/luyennghe.png", 50, 40).getImg());
		
		JLabel lblLuyenDoc = new JLabel("Luyện Đọc");
		lblLuyenDoc.setIcon(new MyImage("src/image/luyendoc.jpg", 50, 40).getImg());
		
		JLabel lblThiThu = new JLabel("Thi Thử");
		lblThiThu.setIcon(new MyImage("src/image/test.png", 50, 40).getImg());
		
		JLabel lblTuDien = new JLabel("Từ Điển");
		lblTuDien.setIcon(new MyImage("src/image/Part2icon.jpg", 50, 40).getImg());
		
		tp.setTabComponentAt(0, lblLuyenNghe);
		tp.setTabComponentAt(1, lblLuyenDoc);
		tp.setTabComponentAt(2, lblThiThu);
		tp.setTabComponentAt(3, lblTuDien);
	
		add(tp);
		
		this.setIconImage(new MyImage("src/image/toeic.png", 40, 40).getImg().getImage());
	}
}
