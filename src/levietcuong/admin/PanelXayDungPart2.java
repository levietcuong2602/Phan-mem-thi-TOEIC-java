package levietcuong.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import levietcuong.partToeic.CauHoi;
import levietcuong.partToeic.QuanLyPart1;
import levietcuong.partToeic.QuanLyPart2;

public class PanelXayDungPart2 extends JPanel implements ActionListener{
	private JPanel panelTitile, panelMain, panelMain2, panelButtom, panelInput;
	private JLabel lblTitile, lblBoCauHoi, lblCau, lblTraLoiA, lblTraLoiB, lblTraLoiC,
			lblDapAn;
	private JTable tablePart2, tableBoCauHoi;
	private JButton btnThemBoCauHoi, btnAudio;
	private JTextField txtBoCauHoi, txtCau, txtTraLoiA, txtTraLoiB, txtTraLoiC, txtDapAn;
	private String audioURL = "";
	private JButton btnThemCauHoi;
	private JButton btnSuaCauHoi;
	private JButton btnXoaCauHoi;
	private JButton btnResetTable;
	 private JButton btnXemNguoiDung;

	private boolean checkSuaCauHoi = false;
	private JButton btnSuaBoCauHoi;
	private JButton btnXoaBoCauHoi;
	private JTextField txtTestID;
	private JTextField txtPartID;
	private JLabel lblTestID;
	private JLabel lblPartID;

	private ArrayList<CauHoi> lstCauHoiPart2;
	private boolean checkSuaCauHoi2 = false;
	private Player play;
	private Thread threadAudio;
	private JButton btnStop;
	private XemAnhFrame frameImage;

	private boolean checkAudio;
	private boolean checkThemCauhoi = false;
	private JLabel lblDebai;
	private JTextField txtDeBai;
	
	public PanelXayDungPart2() {
		addConstrolls();
		addEvents();
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		tableBoCauHoi.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int column = tableBoCauHoi.getSelectedColumn();
				int row = tableBoCauHoi.getSelectedRow();

				if (column == 2) {
					if (tableBoCauHoi.getValueAt(row, column) == null) {
						return;
					}
					String pathname = tableBoCauHoi.getValueAt(row, column).toString();
					if (play == null) {
						try {
							play = new Player(new FileInputStream(new File(pathname)));
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (JavaLayerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						threadAudio = new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									play.play();
								} catch (JavaLayerException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						threadAudio.start();
					} else {
						threadAudio.stop();
						play.close();
						try {
							play = new Player(new FileInputStream(new File(pathname)));
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (JavaLayerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						threadAudio = new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									play.play();
								} catch (JavaLayerException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						threadAudio.start();
					}

				}
			}

	@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

	@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

	@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getClickCount() == 1) {
					int row = tableBoCauHoi.getSelectedRow();
					DefaultTableModel model = (DefaultTableModel) tableBoCauHoi.getModel();
					int testid = Integer.parseInt(model.getValueAt(row, 0).toString());
					if(checkThemCauhoi == true){
						DefaultTableModel modelPart2 = (DefaultTableModel) tablePart2.getModel();
						//neu trong bang tablePart2 khong co du lieu
						if(modelPart2.getRowCount() == 0){
							return;
						}
						
						//xem bo cau hoi ma dang trong che do them cau hoi
						int testID = Integer.parseInt(modelPart2.getValueAt(0, 1).toString());
						if(testID == testid){
							return;
						}
						
						int chon = JOptionPane.showConfirmDialog(null, "Đang thêm bộ câu hỏi. Bạn có muốn hủy không?");
						if(chon != 0){
							return;
						}
					}
					
					LoadDuLieuPart2(testid);
					return;
				}

			}

	});

	}

	private void addConstrolls() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout(15, 15));
		createPanelTitile();
		createMainPanel();
	}

	public void createMainPanel() {
		panelMain = new JPanel(new GridLayout(2, 1, 10, 10));
		createTabelPart1();

		add(panelMain, BorderLayout.CENTER);
		createButtomPanel();
	}

	public void createButtomPanel() {
		panelButtom = new JPanel(new BorderLayout(10, 10));
		// bảng 2
		createTableBoCauHoi();
		createInputData();

		panelMain.add(panelButtom);
	}

	private void createInputData() {
		// TODO Auto-generated method stub
		panelInput = new JPanel(new BorderLayout());
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBackground(new Color(0x9999));
		JLabel label = new JLabel("Nhập bộ câu hỏi");
		panel.add(label);
		panelInput.add(panel, BorderLayout.NORTH);

		JPanel panelMainInput = new JPanel(new GridLayout(0, 2, 30, 10));
		panelMainInput.setBorder(new EmptyBorder(10, 35, 0, 35));
		panelInput.add(panelMainInput, BorderLayout.CENTER);
		lblBoCauHoi = new JLabel("Bộ câu hỏi: ");
		lblCau = new JLabel("Câu: ");
		lblDebai = new JLabel("Đề bài: ");
		lblTraLoiA = new JLabel("Trả lời A: ");
		lblTraLoiB = new JLabel("Trả lời B: ");
		lblTraLoiC = new JLabel("Trả lời C: ");
		lblDapAn = new JLabel("Đáp án: ");

		txtBoCauHoi = new JTextField(20);
		txtCau = new JTextField(20);
		txtDeBai = new JTextField(20);
		txtTraLoiA = new JTextField(20);
		txtTraLoiB = new JTextField(20);
		txtTraLoiC = new JTextField(20);
		txtDapAn = new JTextField(20);

		JPanel panel1 = new JPanel(new GridLayout(5, 1, 10, 10));
		panel1.add(lblBoCauHoi);
		panel1.add(lblCau);
		panel1.add(lblDebai);
		panel1.add(lblTraLoiA);

		JPanel panel3 = new JPanel(new GridLayout(5, 1, 10, 10));
		panel3.add(lblTraLoiB);
		panel3.add(lblTraLoiC);
		panel3.add(lblDapAn);

		JPanel panel2 = new JPanel(new GridLayout(5, 1, 30, 10));
		panel2.add(txtBoCauHoi);
		panel2.add(txtCau);
		panel2.add(txtDeBai);
		panel2.add(txtTraLoiA);

		JPanel panel4 = new JPanel(new GridLayout(5, 1, 50, 10));
		panel4.add(txtTraLoiB);
		panel4.add(txtTraLoiC);
		panel4.add(txtDapAn);

		JPanel panelLeft = new JPanel(new BorderLayout(15, 15));
		panelLeft.add(panel1, BorderLayout.WEST);
		panelLeft.add(panel2, BorderLayout.CENTER);

		JPanel panelRight = new JPanel(new BorderLayout(15, 15));
		panelRight.add(panel3, BorderLayout.WEST);
		panelRight.add(panel4, BorderLayout.CENTER);

		panelMainInput.add(panelLeft);
		panelMainInput.add(panelRight);

		btnThemCauHoi = createButton("Thêm câu hỏi");
		btnSuaCauHoi = createButton("Sửa Câu hỏi");
		btnXoaCauHoi = createButton("Xóa câu hỏi");
		btnResetTable = createButton("Reset bảng");
		JPanel pnButton = new JPanel(new GridLayout(1, 3, 10, 10));

		JPanel panel5 = new JPanel(new GridLayout(0, 1, 10, 10));
		pnButton.add(panel5);
		panel5.add(btnThemCauHoi);
		panel5.add(btnXoaCauHoi);

		JPanel panel6 = new JPanel(new GridLayout(0, 1, 10, 10));
		pnButton.add(panel6);
		panel6.add(btnSuaCauHoi);
		panel6.add(btnResetTable);

		 JPanel panel7 = new JPanel(new GridLayout(2, 1, 10, 10));
		 btnXemNguoiDung = createButton("Xem giao diện người dùng");
		 pnButton.add(panel7);
		 panel7.add(btnXemNguoiDung);

		panelInput.add(pnButton, BorderLayout.SOUTH);

		panelButtom.add(panelInput, BorderLayout.CENTER);
	}

	public JButton createButton(String name) {
		JButton button = new JButton(name);

		button.addActionListener(this);
		return button;
	}

	public void createTableBoCauHoi() {
		tableBoCauHoi = new JTable();
		tableBoCauHoi.setCellSelectionEnabled(true);
		DefaultTableModel model = new DefaultTableModel();

		String[] title = { "TestID", "PartID", "AudioURL" };
		model.setColumnIdentifiers(title);
		tableBoCauHoi.setModel(model);
		JScrollPane scroll = new JScrollPane(tableBoCauHoi);

		JPanel panel = new JPanel(new BorderLayout());
		JLabel lbltitle = new JLabel("Bộ câu hỏi");
		JPanel panelTitle = new JPanel(new FlowLayout());
		panelTitle.setBackground(new Color(0x9999));
		panelTitle.add(lbltitle);

		// thêm bảng 2
		panel.add(panelTitle, BorderLayout.PAGE_START);
		panel.add(scroll, BorderLayout.CENTER);
		LoadBoCauHoi();
		// thêm nút chọn audiobutton
		JPanel panel2 = new JPanel(new GridLayout(2, 1, 5, 5));
		// thêm textfield
		lblTestID = new JLabel("TestID: ");
		lblPartID = new JLabel("PartID: ");
		txtTestID = new JTextField();
		txtPartID = new JTextField();
		btnAudio = new JButton("Chọn file Audio");
		btnStop = createButton("Dừng");
		JLabel lblChonAudio = new JLabel("Chọn Audio: ");

		JPanel panel3 = new JPanel(new BorderLayout(110, 15));
		panel3.setBorder(new EmptyBorder(0, 50, 0, 0));
		JPanel panel4 = new JPanel(new GridLayout(3, 1, 5, 5));
		panel4.add(lblTestID);
		panel4.add(lblPartID);
		panel4.add(lblChonAudio);
		panel3.add(panel4, BorderLayout.WEST);

		JPanel panel5 = new JPanel(new GridLayout(3, 1, 5, 5));
		panel5.add(txtTestID);
		panel5.add(txtPartID);
		JPanel panel9 = new JPanel(new GridLayout(1, 2, 5, 5));
		panel9.add(btnAudio);
		panel9.add(btnStop);
		panel5.add(panel9);
		panel3.add(panel5, BorderLayout.CENTER);
		panel2.add(panel3);

		btnThemBoCauHoi = createButton("Thêm Bộ Câu Hỏi");
		btnThemBoCauHoi.setEnabled(false);
		btnSuaBoCauHoi = createButton("Sửa Bộ Câu Hỏi");
		btnSuaBoCauHoi.setEnabled(false);
		btnXoaBoCauHoi = createButton("Xóa bộ câu hỏi");
		
		JPanel panel6 = new JPanel(new GridLayout(1, 3, 5, 5));
		JPanel panel7 = new JPanel(new GridLayout(2, 1, 5, 5));
		JPanel panel8 = new JPanel(new GridLayout(2, 1, 5, 5));
		panel7.add(btnThemBoCauHoi);
		panel7.add(btnXoaBoCauHoi);
		panel6.add(panel7);

		panel8.add(btnSuaBoCauHoi);
		panel6.add(panel8);
		panel2.add(panel6);

		panel.add(panel2, BorderLayout.SOUTH);
		panelButtom.add(panel, BorderLayout.WEST);
		btnAudio.addActionListener(this);
	}

	private void LoadBoCauHoi() {
		// TODO Auto-generated method stub

		DefaultTableModel model = (DefaultTableModel) tableBoCauHoi.getModel();
		Connection conn = getConnection();
		try {
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from phannghe where partid = 2");
			int row = 0;
			while (rs.next()) {
				model.addRow(new Object[0]);
				model.setValueAt(rs.getInt(1), row, 0);
				model.setValueAt(rs.getInt(2), row, 1);
				model.setValueAt(rs.getString(3), row, 2);

				row++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createTabelPart1() {
		// TODO Auto-generated method stub
		tablePart2 = new JTable();
		tablePart2.setCellSelectionEnabled(true);
		tablePart2.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				if (columnIndex == 0) {
					return Boolean.class;
				}
				return String.class;
			}
		};
		String[] tabelTitle = { "Select", "Bộ câu hỏi", "Câu", "Đề Bài", "Trả Lời A", "Trả Lời B",
				"Trả Lời C", "Đáp Án" };
		model.setColumnIdentifiers(tabelTitle);
		tablePart2.setModel(model);
		JScrollPane scroll = new JScrollPane(tablePart2);
		panelMain.add(scroll);
	}

	public void LoadDuLieuPart2(int testid) {
		ResetTableCauHoi();

		DefaultTableModel model = (DefaultTableModel) tablePart2.getModel();
		Connection conn = getConnection();
		try {
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from cauhoi where partid = 2 and testid = " + testid);
			int row = 0;
			while (rs.next()) {
				model.addRow(new Object[0]);
				model.setValueAt(false, row, 0);
				model.setValueAt(rs.getInt(1), row, 1);
				model.setValueAt(rs.getInt(3), row, 2);
				model.setValueAt(rs.getString(4), row, 3);
				model.setValueAt(rs.getString(6), row, 4);
				model.setValueAt(rs.getString(7), row, 5);
				model.setValueAt(rs.getString(8), row, 6);
				model.setValueAt(rs.getString(10), row, 7);

				row++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateCauHoi(String sqlCommand, CauHoi cauhoi, int testID, int partID){
		Connection conn = getConnection();
		try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sqlCommand);
			
			ps.setInt(1, testID); //testid
			ps.setInt(2, partID); //partid
			ps.setInt(3, cauhoi.getIndexCauHoi()); //thu tu cau
			ps.setString(4, cauhoi.getDeBai()); // debai
			ps.setString(5, cauhoi.getAnhURL()); //duong dan anh
			ps.setString(6, cauhoi.getTraLoiA()); //a
			ps.setString(7, cauhoi.getTraLoiB()); //b
			ps.setString(8, cauhoi.getTraLoiC()); //c
			ps.setString(9, cauhoi.getTraLoiD()); //d
			ps.setString(10, cauhoi.getDapAn()); //dapan
			ps.setString(11, "F"); //lsu lam
			ps.setString(12, cauhoi.getDoanHoiThoai()); //doan hoi thoai
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/dethi", "root", "vietcuong");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	public boolean CheckFieldWrong() {
		/* kiem tra testid */
		// kiểm tra rỗng
		String testID = txtBoCauHoi.getText();
		if (testID.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần nhập bộ câu hỏi trước khi thêm");
			return true;
		}

		// kiểm tra trùng
		DefaultTableModel model1 = (DefaultTableModel) tableBoCauHoi.getModel();
		for (int i = 0; i < model1.getRowCount(); i++) {
			if (testID.equals(model1.getValueAt(i, 0).toString()) && model1.getValueAt(i, 2) != null) {
				int chon = JOptionPane.showConfirmDialog(null, "Bộ câu hỏi đã tồn tại.Bạn vẫn muốn tiếp tục?");
				if(chon != 0){
					return true;
				}
			}
		}

		/* Kiểm tra số thứ tự câu */
		String thuTuCau = txtCau.getText();
		if (thuTuCau.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần nhập vào thứ tự câu");
			return true;
		}

		// kiểm tra trùng
		DefaultTableModel model = (DefaultTableModel) tablePart2.getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			if (thuTuCau.equals(model.getValueAt(i, 2).toString())) {
				JOptionPane.showMessageDialog(null, "Trùng thứ tự câu");
				return true;
			}
		}

		/* kiểm tra đề bài */
		String deBai = txtDeBai.getText();
		if (deBai.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần nhập vào đề bài");
			return true;
		}

		/* kiểm tra đáp án A */
		String traloiA = txtTraLoiA.getText();
		if (traloiA.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần nhâp trả lời A");
			return true;
		}

		String traloiB = txtTraLoiB.getText();
		if (traloiB.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần nhâp trả lời B");
			return true;
		}

		String traloiC = txtTraLoiC.getText();
		if (traloiC.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần nhâp trả lời C");
			return true;
		}

		String dapan = txtDapAn.getText();
		if (dapan.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần nhâp đáp án");
			return true;
		}

		return false;
	}

	public void ThemCauHoi() {
		if (!CheckFieldWrong()) {
			String testid = txtBoCauHoi.getText();
			String cau = txtCau.getText();
			String deBai = txtDeBai.getText();
			String traloiA = txtTraLoiA.getText();
			String traloiB = txtTraLoiB.getText();
			String traloiC = txtTraLoiC.getText();
			String dapan = txtDapAn.getText();
			
			// thêm vào bảng
			DefaultTableModel model = (DefaultTableModel) tablePart2.getModel();
			//kiem tra xem có đúng bộ câu hỏi
			if(model.getRowCount() != 0 && !testid.equals(model.getValueAt(0, 1).toString())){
				JOptionPane.showMessageDialog(null, "Reset bảng hoặc không đúng bộ câu hỏi đang thêm");
				return;
			}
			
			//thêm vào bảng bộ câu hỏi phần nghe
			DefaultTableModel model2 = (DefaultTableModel) tableBoCauHoi.getModel();
			int count2 = model2.getRowCount();
			for(int i = 0; i < count2;){
				if(testid.equals(model2.getValueAt(i, 0).toString())){
					break;
				}
				i++;
				
				if(i == count2){
					Object[] addRow = {testid, 2, null};
					model2.addRow(addRow);
					
					txtTestID.setText(testid);
					txtPartID.setText("2");
				}
			}
			
			
			Vector<Object> vector = new Vector<>();
			vector.add(false);
			vector.add(testid);
			vector.add(cau);
			vector.add(deBai);
			vector.add(traloiA);
			vector.add(traloiB);
			vector.add(traloiC);
			vector.add(dapan);

			model.addRow(vector);

			// thêm vào cơ sở dữ liệu
//			Connection conn = getConnection();
//			try {
//				PreparedStatement ps = (PreparedStatement) conn
//						.prepareStatement("insert into cauhoi values(?,?,?,?,?,?,?,?,?,?,?,?)");
//				ps.setInt(1, Integer.parseInt(testid));
//				ps.setInt(2, 1);
//				ps.setInt(3, Integer.parseInt(cau));
//				ps.setString(4, null);
//				ps.setString(5, imageURL);
//				ps.setString(6, traloiA);
//				ps.setString(7, traloiB);
//				ps.setString(8, traloiC);
//				ps.setString(9, traloiD);
//				ps.setString(10, dapan);
//				ps.setString(11, "F");
//				ps.setString(12, null);
//
////				ps.executeUpdate();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				JOptionPane.showMessageDialog(null, "Error");
//				e.printStackTrace();
//			}
			
			//cho thao tác với bộ câu hỏi
			btnThemBoCauHoi.setEnabled(true);
			btnSuaBoCauHoi.setEnabled(true);
			
			InitTextFieldCauHoi();
		}
	}

	private void createPanelTitile() {
		// TODO Auto-generated method stub
		panelTitile = new JPanel(new FlowLayout());
		panelTitile.setBackground(new Color(0x9999));
		lblTitile = new JLabel("Xây dựng Bộ câu hỏi Part 2");
		lblTitile.setFont(new Font("Arial", Font.BOLD, 16));
		panelTitile.add(lblTitile);

		add(panelTitile, BorderLayout.NORTH);
	}

	public void ResetTableCauHoi() {	
		DefaultTableModel model = (DefaultTableModel) tablePart2.getModel();

		for (int i = 0; model.getRowCount() != 0;) {
			model.removeRow(i);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnAudio) {
			//thêm bộ lọc file
			JFileChooser chooser = new JFileChooser();
			
			//thêm file bắt đầu
			chooser.setCurrentDirectory(new File("dethi/part2"));
			
			FileFilter filter = new FileNameExtensionFilter("MP3 file", new String[] { "MP3"});
			chooser.setFileFilter(filter);
			
			int select = chooser.showOpenDialog(null);
			if (select == JFileChooser.APPROVE_OPTION) {
				File fileSelect = chooser.getSelectedFile();
				audioURL = fileSelect.getPath();
				
				int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn mở file vừa chọn");
				if(chon == JOptionPane.YES_OPTION){
					try {
						Desktop.getDesktop().open(new File(audioURL));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			return;
		}

		if (e.getSource() == btnThemCauHoi) {
			//check thêm câu hỏi
			checkThemCauhoi = true;
			
			ThemCauHoi();
			return;
		}

		if (e.getSource() == btnResetTable) {
			int chon = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn reset bảng.");
			if(chon != 0){
				return;
			}
			ResetTableCauHoi();
			return;
		}

		if (e.getSource() == btnXoaCauHoi) {
			XoaCauHoi();
			InitTextFieldCauHoi();
			return;
		}

		if (e.getSource() == btnSuaCauHoi) {
			int row = tablePart2.getSelectedRow();
			if(row == -1){
				return;
			}
			
			if (checkSuaCauHoi == false) {
				// load vào textfield

				DefaultTableModel model = (DefaultTableModel) tablePart2.getModel();
				txtBoCauHoi.setText(model.getValueAt(row, 1).toString());
				txtCau.setText(model.getValueAt(row, 2).toString());
				txtDeBai.setText(model.getValueAt(row, 3).toString());
				txtTraLoiA.setText(model.getValueAt(row, 4).toString());
				txtTraLoiB.setText(model.getValueAt(row, 5).toString());
				txtTraLoiC.setText(model.getValueAt(row, 6).toString());
				txtDapAn.setText(model.getValueAt(row, 7).toString());
				checkSuaCauHoi = true;
			} else {
				String boCauHoi = txtBoCauHoi.getText();
				String cauHoi = txtCau.getText();
				String deBai = txtDeBai.getText();
				String traLoiA = txtTraLoiA.getText();
				String traLoiB = txtTraLoiB.getText();
				String traLoiC = txtTraLoiC.getText();
				String dapan = txtDapAn.getText();

				if(boCauHoi.equals("") || cauHoi.equals("") || traLoiA.equals("") || traLoiB.equals("") 
						|| traLoiC.equals("") || deBai.equals("")){
					JOptionPane.showMessageDialog(null, "Bạn cần nhập vào dữ liệu đầy đủ");
					return;
				}			
				// sửa trong bảng
				DefaultTableModel model = (DefaultTableModel) tablePart2.getModel();
				model.setValueAt(boCauHoi, row, 1);
				model.setValueAt(cauHoi, row, 2);
				model.setValueAt(deBai, row, 3);
				model.setValueAt(traLoiA, row, 4);
				model.setValueAt(traLoiB, row, 5);
				model.setValueAt(traLoiC, row, 6);
				model.setValueAt(dapan, row, 7);

				// sửa trong cơ sở dữ liệu
				Connection conn = getConnection();
				try {
					PreparedStatement ps = (PreparedStatement) conn
							.prepareStatement("update cauhoi set debai = ?, traloiA = ?, traloiB = ?, traloiC = ?, dapan = ? where testid = ? and partid = ? and cauhoi = ?");
					ps.setString(1, deBai);
					ps.setString(2, traLoiA);
					ps.setString(3, traLoiB);
					ps.setString(4, traLoiC);
					ps.setString(5, dapan);
					ps.setInt(6, Integer.parseInt(boCauHoi));
					ps.setInt(7, 2);
					ps.setInt(8, Integer.parseInt(cauHoi));

					ps.executeUpdate();
					//xóa dữ liệu trên textfield
					InitTextFieldCauHoi();
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					JOptionPane.showInternalMessageDialog(null, "Error!!!!");
					ex.printStackTrace();
				}
				checkSuaCauHoi = false;
			}
			
			return;

		}

		if (e.getSource() == btnThemBoCauHoi) {
			checkThemCauhoi = false;
			
			String testID = txtTestID.getText();
			String partID = txtPartID.getText();
			
			/* Kiểm tra rỗng */
			if (testID.equals("") || partID.equals("") || audioURL.equals("")) {
				JOptionPane.showMessageDialog(null, "Bạn cần nhập đầy đủ thông tin");
				return;
			}

			DefaultTableModel model = (DefaultTableModel) tableBoCauHoi.getModel();
			/* Kiểm tra trùng */
			boolean check = true;
			for (int i = 0; i < model.getRowCount(); i++) {
				if (model.getValueAt(i, 2) != null && testID.equals(model.getValueAt(i, 0).toString())) {
					JOptionPane.showMessageDialog(null, "Bộ câu hỏi đã tồn tại. Vui lòng nhập lại");
					check = false;
					break;
				}

			}

			if (check) {
				Object[] rowData = { testID, partID, audioURL };
				if(model.getValueAt(model.getRowCount()-1, 2) == null){
					model.setValueAt(audioURL, model.getRowCount()-1, 2);
				}else{
					model.addRow(rowData);
				}
				//thêm trong bảng phan nghe
				Connection conn = getConnection();
				try {
					PreparedStatement ps = (PreparedStatement) conn
							.prepareStatement("insert into phannghe values (?, ?, ?)");
					ps.setInt(1, Integer.parseInt(testID));
					ps.setInt(2, Integer.parseInt(partID));
					ps.setString(3, audioURL);

					 ps.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//thêm trong bảng cauhoi
				int cauHoi;
				String deBai = "";
				String trLoiA = "";
				String trLoiB = "";
				String trLoiC = "";
				String trLoiD = "";
				String dapAn = "";
				String sql = "insert into cauhoi values(?,?,?,?,?,?,?,?,?,?,?,?)";
				
				CauHoi cauhoi = null;
				DefaultTableModel modelPart2 = (DefaultTableModel) tablePart2.getModel();
				int count = modelPart2.getRowCount();
				for(int row = 0; row < count; row++){
					deBai = modelPart2.getValueAt(row, 3).toString();
					cauHoi = Integer.parseInt(modelPart2.getValueAt(row, 2).toString());
					trLoiA = modelPart2.getValueAt(row, 4).toString();
					trLoiB = modelPart2.getValueAt(row, 5).toString();
					trLoiC = modelPart2.getValueAt(row, 6).toString();
					trLoiD = modelPart2.getValueAt(row, 7).toString();
					dapAn = trLoiA = modelPart2.getValueAt(row, 7).toString();
					
					cauhoi = new CauHoi(null, trLoiA, trLoiB, trLoiC, null, dapAn, null, deBai, null);
					cauhoi.setIndexCauHoi(cauHoi);
					updateCauHoi(sql, cauhoi, Integer.parseInt(testID), Integer.parseInt(partID));
				}
				audioURL = "";
			}
			
		}

		if (e.getSource() == btnXoaBoCauHoi) {
			// xóa trên bảng
			int index = tableBoCauHoi.getSelectedRow();

			if (index == -1) {
				JOptionPane.showMessageDialog(null, "Bạn cần chọn dòng cần xóa");
				return;
			}
			//xóa trong bảng phần nghe
			DefaultTableModel model = (DefaultTableModel) tableBoCauHoi.getModel();
			String testID = model.getValueAt(index, 0).toString();
			String partID = model.getValueAt(index, 1).toString();
			model.removeRow(index); 
			
			//xóa trong bảng câu hỏi
			DefaultTableModel model2 = (DefaultTableModel) tablePart2.getModel();
			int count = model2.getRowCount();
			while(model2.getRowCount() != 0){
				model2.removeRow(0);
			}
			
			//xoa trong csdl khi bang tablePart2 rong
			Connection conn = getConnection();
			if(count == 0){
				try {
					PreparedStatement ps = (PreparedStatement) conn
							.prepareStatement("delete from phannghe where phannghe.testid=? and phannghe.partid=?");
					ps.setInt(1, Integer.parseInt(testID));
					ps.setInt(2, Integer.parseInt(partID));
	
					ps.executeUpdate();
					return;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Error");
					e1.printStackTrace();
				}
			}
			
			// xóa trong csdl bảng phần nghe, và bảng cauhoi
			try {
				PreparedStatement ps = (PreparedStatement) conn
						.prepareStatement("delete phannghe, cauhoi from phannghe, cauhoi where phannghe.testid=cauhoi.testid and phannghe.partid=cauhoi.partid and "
								+ "phannghe.testid= ? and phannghe.partid=?");
				ps.setInt(1, Integer.parseInt(testID));
				ps.setInt(2, Integer.parseInt(partID));

				ps.executeUpdate();
				return;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Error");
				e1.printStackTrace();
			}
			
		}

		if (e.getSource() == btnSuaBoCauHoi) {
			int row = tableBoCauHoi.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "Bạn cần chọn dòng cần sửa");
				return;
			}

			if (checkSuaCauHoi2 == false) {
				// load vào textfield
				checkSuaCauHoi2 = true;

				DefaultTableModel model = (DefaultTableModel) tableBoCauHoi.getModel();
				txtTestID.setText(model.getValueAt(row, 0).toString());
				txtPartID.setText(model.getValueAt(row, 1).toString());
				audioURL = model.getValueAt(row, 2).toString();
			} else {
				// sửa trong bảng
				checkSuaCauHoi2 = false;
				String testid = txtTestID.getText();
				String partid = txtPartID.getText();

				DefaultTableModel model = (DefaultTableModel) tablePart2.getModel();
				model.setValueAt(testid, row, 0);
				model.setValueAt(partid, row, 1);
				model.setValueAt(audioURL, row, 2);

				// lưu vào csdl
				Connection conn = getConnection();
				try {
					PreparedStatement ps = (PreparedStatement) conn
							.prepareStatement("update phannghe set audioURL = ? where testid = ? and partid = ?");
					ps.setString(1, audioURL);
					ps.setInt(2, Integer.parseInt(testid));
					ps.setInt(3, Integer.parseInt(partid));

					ps.executeUpdate();
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}

			}
			
			return;
		}

		if (e.getSource() == btnStop) {
			if (play != null) {
				threadAudio.stop();
				play.close();
			}
			return;
		}
		
		if(e.getSource() == btnXemNguoiDung){
			TaoBoCauHoi();
			return;
		}
		
	}

	private void InitTextFieldCauHoi() {
		// TODO Auto-generated method stub
		txtBoCauHoi.setText("");
		txtCau.setText("");
		txtDeBai.setText("");;
		txtTraLoiA.setText("");
		txtTraLoiB.setText("");
		txtTraLoiC.setText("");
		txtDapAn.setText("");
	}

	private void TaoBoCauHoi() {
		// TODO Auto-generated method stub
		String testID = "";
		String deBai = "";
		String traLoiA = "";
		String traLoiB = "";
		String traLoiC = "";
		String dapAn = "";
		String lichSuLam = "F";
		ArrayList<CauHoi> lstCauHoi = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) tablePart2.getModel();
		
		if(model.getRowCount() == 0){
			return;
		}
		testID = model.getValueAt(0, 1).toString();
		
		int count = model.getRowCount();
		for(int row = 0; row < count; row++){
			deBai = model.getValueAt(row, 3).toString();
			traLoiA = model.getValueAt(row, 4).toString();
			traLoiB = model.getValueAt(row, 5).toString();
			traLoiC = model.getValueAt(row, 6).toString();
			dapAn = model.getValueAt(row, 7).toString();
			
			lstCauHoi.add(new CauHoi(deBai, traLoiA, traLoiB, traLoiC, dapAn, lichSuLam));
		}
		
		//lấy link audio
		String audioURL = "";
		DefaultTableModel model2 = (DefaultTableModel) tableBoCauHoi.getModel();
		int count2 = model2.getRowCount();
		for(int i = 0; i < count2; i++){
			if(testID.equals(model2.getValueAt(i, 0).toString())){
				audioURL = model2.getValueAt(i, 2).toString();
				break;
			}
		}
		
		if(audioURL.equals("")){
			JOptionPane.showMessageDialog(null, "Bạn cần thêm audio cho bộ câu hỏi.");
			return;
		}
		
		//thông báo
		JOptionPane.showMessageDialog(null, "Đã tạo mới bộ câu hỏi");
	
		//tạo frame mới
		QuanLyPart2 part2 = new QuanLyPart2(lstCauHoi);
		part2.setUrlAudio(audioURL);
		
		JFrame f = new JFrame();
		f.setSize(600, 600);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		
		f.add(part2);
		part2.displayAudio();
		
		f.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				part2.audioPart2.close();
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return;
	}

	private void XoaCauHoi() {
		String testid = "";
		String cau = "";
		// xóa trên bảng
		DefaultTableModel model = (DefaultTableModel) tablePart2.getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			boolean chk = (Boolean) model.getValueAt(i, 0);
			if (chk == true) {
				testid = model.getValueAt(i, 1).toString();
				cau = model.getValueAt(i, 2).toString();
				model.removeRow(i);
				i--;
			}
		}

		// xóa trong csdl
		// thêm vào cơ sở dữ liệu
		if(checkThemCauhoi == true){ 
			//đang trong chế độ thêm bộ câu hỏi
			return;
		}
		
		Connection conn = getConnection();
		try {
			PreparedStatement ps = (PreparedStatement) conn
					.prepareStatement("delete from cauhoi where testid=? and partid=? and cauhoi=?");
			ps.setInt(1, Integer.parseInt(testid));
			ps.setInt(2, 2);
			ps.setInt(3, Integer.parseInt(cau));
	
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
