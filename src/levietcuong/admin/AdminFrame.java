package levietcuong.admin;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import levietcuong.luyendoc.LuyenDocPanel;
import levietcuong.luyennghe.LuyenNghePanel;
import levietcuong.thithu.ThiThuPanel;

public class AdminFrame extends JFrame implements ActionListener{
	private CardLayout card;
	private JMenuBar menuBar;
	private JMenu mnLuyenNghe, mnLuyenDoc, mnThiThu, mnLuyenNgheDevelop, mnLuyenDocDevelop, mnTuDien;
	private JMenuItem miLuyenNgheUser, miLuyenDocUser, miThiThuUser, miXayDungPart1, miXayDungPart2,
	miXayDungPart3, miXayDungPart4, miXayDungPart5, miXayDungPart6;
	private Container con;
	private JMenuItem miTuDien;
	private JMenuItem miXayDungPart7;
	 public AdminFrame() {
		// TODO Auto-generated constructor stub
		 addControlls();
		 addEvents();
		 showWindows();
	}

	private void showWindows() {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("Hệ thống phát triển phần mềm");
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		miLuyenNgheUser.addActionListener(this);
		
	}

	private void addControlls() {
		// TODO Auto-generated method stub
		con = getContentPane();
		con.setLayout(card = new CardLayout());
		initMenuBar();
		addPanelCardLayout();
	}
	
	private void addPanelCardLayout() {
		// TODO Auto-generated method stub
		con.add(new LuyenNghePanel(), "LuyenNgheUser");
		con.add(new LuyenDocPanel(), "LuyenDocUser");
		con.add(new ThiThuPanel(), "ThiThuUser");
		con.add(new PanelXayDungPart1(), "Part1");
		con.add(new PanelXayDungPart2(), "Part2");
		con.add(new PanelXayDungPart3(), "Part3");
		con.add(new PanelXayDungPart4(), "Part4");
		con.add(new PanelXayDungPart5(), "Part5");
		con.add(new PanelXayDungPart6(), "Part6");
		con.add(new PanelXayDungPart7(), "Part7");
		con.add(new PanelTuDien(), "TuDien");
	}

	private void initMenuBar() {
		// TODO Auto-generated method stub
		menuBar = new JMenuBar();
		menuBar.add(mnLuyenNghe = new JMenu("Chức năng luyện nghe"));
		mnLuyenNghe.add(miLuyenNgheUser = createMenuItem("Luyện Nghe User"));
		mnLuyenNghe.add(mnLuyenNgheDevelop = new JMenu("Developer Phần Nghe"));
		
		mnLuyenNgheDevelop.add(miXayDungPart1 = createMenuItem("Xây Dựng Bộ Nghe Part 1"));
		mnLuyenNgheDevelop.add(miXayDungPart2 = createMenuItem("Xây Dựng Bộ Nghe Part 2"));
		mnLuyenNgheDevelop.add(miXayDungPart3 = createMenuItem("Xây Dựng Bộ Nghe Part 3"));
		mnLuyenNgheDevelop.add(miXayDungPart4 = createMenuItem("Xây Dựng Bộ Nghe Part 4"));
		
		menuBar.add(mnLuyenDoc = new JMenu("Chức năng Luyện Đọc"));
		mnLuyenDoc.add(miLuyenDocUser = createMenuItem("Luyện Đọc User"));
		mnLuyenDoc.add(mnLuyenDocDevelop = new JMenu("Developer Phần Đọc"));
		mnLuyenDocDevelop.add(miXayDungPart5 = createMenuItem("Xây Dựng Câu Hỏi Part 5"));
		mnLuyenDocDevelop.add(miXayDungPart6 = createMenuItem("Xây Dựng Câu Hỏi Part 6"));
		mnLuyenDocDevelop.add(miXayDungPart7 = createMenuItem("Xây Dựng Câu Hỏi Part 7"));
		menuBar.add(mnTuDien = new JMenu("Từ Điển Anh-Việt"));
		mnTuDien.add(miTuDien = createMenuItem("Phát triển từ điển"));
		
		menuBar.add(mnThiThu = new JMenu("Chức năng thi thử"));
		mnThiThu.add(miThiThuUser = createMenuItem("Thi Thử User"));
		
		setJMenuBar(menuBar);
	}
	
	private JMenuItem createMenuItem(String name) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(this);
		return menuItem;
	}

	public static void main(String[] args) {
		new AdminFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == miLuyenNgheUser){
			card.show(con, "LuyenNgheUser");
			return;
		}
		if(e.getSource() == miLuyenDocUser){
			card.show(con, "LuyenDocUser");
			return;
		}
		if(e.getSource() == miThiThuUser){
			card.show(con, "ThiThuUser");
			return;
		}
		
		if(e.getSource() == miXayDungPart1){
			card.show(con, "Part1");
			return;
		}
		
		if(e.getSource() == miXayDungPart2){
			card.show(con, "Part2");
			return;
		}
		
		if(e.getSource() == miXayDungPart3){
			card.show(con, "Part3");
			return;
		}
		
		if(e.getSource() == miXayDungPart4){
			card.show(con, "Part4");
			return;
		}
		
		if(e.getSource() == miXayDungPart5){
			card.show(con, "Part5");
			return;
		}
		
		if(e.getSource() == miXayDungPart6){
			card.show(con, "Part6");
			return;
		}
		
		if(e.getSource() == miXayDungPart7){
			card.show(con, "Part7");
			return;
		}
		
		if(e.getSource() == miTuDien){
			card.show(con, "TuDien");
			return;
		}
	}
}
