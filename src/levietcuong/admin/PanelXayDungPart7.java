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
import java.awt.image.FilteredImageSource;
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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
import levietcuong.partToeic.QuanLyPart3;
import levietcuong.partToeic.QuanLyPart6;
import levietcuong.partToeic.QuanLyPart7;

public class PanelXayDungPart7 extends JPanel implements ActionListener {
	private JPanel panelLeft, panelRight, panelTitile, panelMain, panelMain2, panelButtom, panelInput;
	private JLabel lblTitile, lblBoCauHoi, lblCau, lblTraLoiA, lblTraLoiB, lblTraLoiC, lblTraLoiD, lblDapAn;
	private JTable tablePart7;
	private JFileChooser fileAudio = new JFileChooser();
	private JButton btnThemBoCauHoi, btnAudio;
	private JTextField txtDeBai, txtBoCauHoi, txtCau, txtTraLoiA, txtTraLoiB, txtTraLoiC, txtTraLoiD, txtDapAn;
	private JButton btnThemCauHoi;
	private JButton btnSuaCauHoi;
	private JButton btnXoaCauHoi;
	private JButton btnResetTable;
	private JButton btnCapNhat;

	private boolean checkSuaCauHoi = false;
	private boolean checkSuaCauHoi2 = false;

	private JPanel panelMainRight;
	private String imageURL = "";
	private JLabel lblImage;
	private JLabel lblDeBai;
	private JButton btnImage;

	public PanelXayDungPart7() {
		addConstrolls();
	}

	private void addConstrolls() {
		// TODO Auto-generated method stub
		setLayout(new GridLayout(1, 2, 10, 10));
		// thêm giao diện nhà phát triển
		panelLeft = new JPanel(new BorderLayout());
		panelLeft.setBorder(BorderFactory.createEtchedBorder());
		panelTitile = createPanelTitile(); //title nhà phát triển
		panelLeft.add(panelTitile, BorderLayout.PAGE_START);
		panelMain = createMainPanel();
		panelLeft.add(panelMain, BorderLayout.CENTER);
		add(panelLeft);
		
		// giao diện người dùng
		panelRight = createPanelDungThu();
		panelRight.setBorder(BorderFactory.createEtchedBorder());
		panelMainRight = createMainNguoiDung();
		panelRight.add(panelMainRight, BorderLayout.CENTER);
		add(panelRight);
	}
	
	private JPanel createMainNguoiDung() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new BorderLayout());
		String image = "";
		String deBai = "";
		String traLoiA = "";
		String traLoiB = "";
		String traLoiC = "";
		String traLoiD = "";
		String dapAn = "";
		String lichSuLam = "F";
		
		//lấy dữ liệu trên bảng
		ArrayList<CauHoi> lstCauHoi = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) tablePart7.getModel();
		int count = model.getRowCount();
		for(int row = 0; row < count; row++){
			image = model.getValueAt(row, 3).toString();
			deBai = model.getValueAt(row, 4).toString();
			traLoiA = model.getValueAt(row, 5).toString();
			traLoiB = model.getValueAt(row, 6).toString();
			traLoiC = model.getValueAt(row, 7).toString();
			traLoiD = model.getValueAt(row, 8).toString();
			dapAn = model.getValueAt(row, 9).toString();
			
			lstCauHoi.add(new CauHoi(image, traLoiA, traLoiB, traLoiC, traLoiD, dapAn, lichSuLam, deBai));
		}
		if(lstCauHoi.size() != 0) {
			QuanLyPart7 part7 = new QuanLyPart7(lstCauHoi);
			panel.add(part7);
		}
		return panel;
	}

	private JPanel createPanelTitile() {
		// TODO Auto-generated method stub
		JPanel panelTitile = new JPanel(new FlowLayout());
		panelTitile.setBackground(new Color(0x9999));
		lblTitile = new JLabel("Xây dựng Bộ câu hỏi Part 7");
		lblTitile.setFont(new Font("Arial", Font.BOLD, 16));
		panelTitile.add(lblTitile);

		return panelTitile;
	}

	public JPanel createMainPanel() {
		JPanel panelMain = new JPanel(new GridLayout(2, 1, 0, 0));
		//thêm bảng câu hỏi part 7
		tablePart7 = createTabelPart7();
		JScrollPane scroll = new JScrollPane(tablePart7);
		panelMain.add(scroll);
		
		//thêm các thao tác trên main panel
		panelButtom = new JPanel(new BorderLayout(10, 10));
		panelInput = createInputData();
		panelButtom.add(panelInput, BorderLayout.CENTER);
		panelMain.add(panelButtom);
		
		return panelMain;
	}

	private JPanel createPanelDungThu() {
		// TODO Auto-generated method stub
		JPanel panelRight = new JPanel(new BorderLayout());

		JLabel titleNguoiDung = new JLabel("Người dùng");
		titleNguoiDung.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel panelTitle = new JPanel(new FlowLayout());
		panelTitle.setBackground(new Color(0x9999));
		panelTitle.add(titleNguoiDung);
		panelRight.add(panelTitle, BorderLayout.PAGE_START);
		
		return panelRight;
	}

	private JPanel createInputData() {
		// TODO Auto-generated method stub
		JPanel panelInput = new JPanel(new BorderLayout());
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBackground(new Color(0x9999));
		JLabel label = new JLabel("Nhập bộ câu hỏi");
		label.setFont(new Font("Arial", Font.BOLD, 16));
		panel.add(label);
		panelInput.add(panel, BorderLayout.NORTH);

		JPanel panelMainInput = new JPanel(new GridLayout(0, 2, 30, 10));
		panelMainInput.setBorder(new EmptyBorder(10, 35, 35, 0));
		panelInput.add(panelMainInput, BorderLayout.CENTER);
		lblBoCauHoi = new JLabel("Bộ câu hỏi: ");
		lblCau = new JLabel("Câu: ");
		lblImage = new JLabel("Image URL:");
		lblDeBai = new JLabel("Đề bài:");
		lblTraLoiA = new JLabel("Trả lời A: ");
		lblTraLoiB = new JLabel("Trả lời B: ");
		lblTraLoiC = new JLabel("Trả lời C: ");
		lblTraLoiD = new JLabel("Trả lời D: ");
		lblDapAn = new JLabel("Đáp án: ");

		txtBoCauHoi = new JTextField(20);
		txtCau = new JTextField(20);
		txtDeBai = new JTextField();
		btnImage = createButton("Image");
		txtTraLoiA = new JTextField(20);
		txtTraLoiB = new JTextField(20);
		txtTraLoiC = new JTextField(20);
		txtTraLoiD = new JTextField(20);
		txtDapAn = new JTextField(20);

		JPanel panel1 = new JPanel(new GridLayout(5, 1, 10, 10));
		panel1.add(lblBoCauHoi);
		panel1.add(lblCau);
		panel1.add(lblImage);
		panel1.add(lblDeBai);

		JPanel panel3 = new JPanel(new GridLayout(5, 1, 10, 10));
		panel3.add(lblTraLoiA);
		panel3.add(lblTraLoiB);
		panel3.add(lblTraLoiC);
		panel3.add(lblTraLoiD);
		panel3.add(lblDapAn);

		JPanel panel2 = new JPanel(new GridLayout(5, 1, 10, 10));
		panel2.add(txtBoCauHoi);
		panel2.add(txtCau);
		panel2.add(btnImage);
		panel2.add(txtDeBai);

		JPanel panel4 = new JPanel(new GridLayout(5, 1, 50, 10));
		panel4.add(txtTraLoiA);
		panel4.add(txtTraLoiB);
		panel4.add(txtTraLoiC);
		panel4.add(txtTraLoiD);
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
		btnCapNhat = createButton("Cập nhật người dùng");
		pnButton.add(panel7);
		panel7.add(btnCapNhat);

		panelInput.add(pnButton, BorderLayout.SOUTH);
		
		return panelInput;
	}

	public JButton createButton(String name) {
		JButton button = new JButton(name);

		button.addActionListener(this);
		return button;
	}

	private JTable createTabelPart7() {
		// TODO Auto-generated method stub
		JTable tablePart7 = new JTable();
		tablePart7.setCellSelectionEnabled(true);
		tablePart7.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

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
		String[] tabelTitle = { "Select", "Bộ câu hỏi", "Câu", "Image", "Đề bài", "Trả Lời A", "Trả Lời B",
				"Trả Lời C", "Trả Lời D", "Đáp Án" };
		model.setColumnIdentifiers(tabelTitle);
		tablePart7.setModel(model);

		LoadDuLieuPart7(model);
		
		return tablePart7;
	}

	public void LoadDuLieuPart7(DefaultTableModel model) {
		Connection conn = getConnection();
		try {
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from cauhoi where partid = 7");
			int row = 0;
			while (rs.next()) {
				model.addRow(new Object[0]);
				model.setValueAt(false, row, 0); //select
				model.setValueAt(rs.getInt(1), row, 1); //bo cau hoi
				model.setValueAt(rs.getInt(3), row, 2); //cau
				model.setValueAt(rs.getString(5), row, 3); //image
				model.setValueAt(rs.getString(4), row, 4); //de bai
				model.setValueAt(rs.getString(6), row, 5); //trloi A
				model.setValueAt(rs.getString(7), row, 6); //trloi B
				model.setValueAt(rs.getString(8), row, 7); //trloi C
				model.setValueAt(rs.getString(9), row, 8); //trloi D
				model.setValueAt(rs.getString(10), row, 9); //dap an
				
				row++;
			}
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

		/* Kiểm tra số thứ tự câu */
		String thuTuCau = txtCau.getText();
		if (thuTuCau.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần nhập vào thứ tự câu");
			return true;
		}

		// kiểm tra trùng thứ tự câu
		DefaultTableModel model = (DefaultTableModel) tablePart7.getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			if (thuTuCau.equals(model.getValueAt(i, 2).toString())
					&& testID.equals(model.getValueAt(i, 1).toString())) {
				JOptionPane.showMessageDialog(null, "Trùng thứ tự câu trong bộ câu hỏi: " + testID);
				return true;
			}
		}
		
		/*Kiểm tra image url*/
		if(imageURL.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần chọn đường dẫn ảnh.");
			return true;
		}
		
		/*kiểm tra đoạn văn*/
		String doanHT = txtDeBai.getText();
		if(doanHT.equals("")){
			JOptionPane.showMessageDialog(null, "Bạn cần nhâp đề bài.");
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

		String traloiD = txtTraLoiD.getText();
		if (traloiD.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần nhâp trả lời D");
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
			String traloiD = txtTraLoiD.getText();
			String dapan = txtDapAn.getText();

			// thêm vào bảng
			DefaultTableModel model = (DefaultTableModel) tablePart7.getModel();

			Vector<Object> vector = new Vector<>();
			vector.add(false);
			vector.add(testid);
			vector.add(cau);
			vector.add(imageURL);
			vector.add(deBai);
			vector.add(traloiA);
			vector.add(traloiB);
			vector.add(traloiC);
			vector.add(traloiD);
			vector.add(dapan);

			model.addRow(vector);

			// thêm vào cơ sở dữ liệu
			Connection conn = getConnection();
			try {
				PreparedStatement ps = (PreparedStatement) conn
						.prepareStatement("insert into cauhoi values(?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, Integer.parseInt(testid));
				ps.setInt(2, 7);
				ps.setInt(3, Integer.parseInt(cau));
				ps.setString(4, deBai);
				ps.setString(5, imageURL);
				ps.setString(6, traloiA);
				ps.setString(7, traloiB);
				ps.setString(8, traloiC);
				ps.setString(9, traloiD);
				ps.setString(10, dapan);
				ps.setString(11, "F");
				ps.setString(12, null);

				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imageURL = "";
			InitTextFieldCauHoi();
		}
	}

	public void ResetTableCauHoi() {
		DefaultTableModel model = (DefaultTableModel) tablePart7.getModel();

		for (int i = 0; model.getRowCount() != 0;) {
			model.removeRow(i);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnThemCauHoi) {
			ThemCauHoi();
			return;
		}

		if (e.getSource() == btnResetTable) {
			ResetTableCauHoi();
			return;
		}

		if (e.getSource() == btnXoaCauHoi) {
			XoaCauHoi();
			InitTextFieldCauHoi();
			return;
		}

		if (e.getSource() == btnSuaCauHoi) {
			int row = tablePart7.getSelectedRow();
			if (row == -1) {
				return;
			}

			if (checkSuaCauHoi == false) {
				// load vào textfield
				checkSuaCauHoi = true;

				DefaultTableModel model = (DefaultTableModel) tablePart7.getModel();
				txtBoCauHoi.setText(model.getValueAt(row, 1).toString());
				txtCau.setText(model.getValueAt(row, 2).toString());
				imageURL = model.getValueAt(row, 3).toString();
				txtDeBai.setText(model.getValueAt(row, 4).toString());
				txtTraLoiA.setText(model.getValueAt(row, 5).toString());
				txtTraLoiB.setText(model.getValueAt(row, 6).toString());
				txtTraLoiC.setText(model.getValueAt(row, 7).toString());
				txtTraLoiD.setText(model.getValueAt(row, 8).toString());
				txtDapAn.setText(model.getValueAt(row, 9).toString());
			} else {
				checkSuaCauHoi = false;
				String boCauHoi = txtBoCauHoi.getText();
				String cauHoi = txtCau.getText();
				String debai = txtDeBai.getText();
				String traLoiA = txtTraLoiA.getText();
				String traLoiB = txtTraLoiB.getText();
				String traLoiC = txtTraLoiC.getText();
				String traLoiD = txtTraLoiD.getText();
				String dapan = txtDapAn.getText();

				// sửa trong bảng
				DefaultTableModel model = (DefaultTableModel) tablePart7.getModel();
				model.setValueAt(boCauHoi, row, 1);
				model.setValueAt(cauHoi, row, 2);
				model.setValueAt(imageURL, row, 3);
				model.setValueAt(debai, row, 4);
				model.setValueAt(traLoiA, row, 5);
				model.setValueAt(traLoiB, row, 6);
				model.setValueAt(traLoiC, row, 7);
				model.setValueAt(traLoiD, row, 8);
				model.setValueAt(dapan, row, 9);

				// sửa trong cơ sở dữ liệu
				Connection conn = getConnection();
				try {
					PreparedStatement ps = (PreparedStatement) conn.prepareStatement(
							"update cauhoi set debai = ?, imageURL = ?, traloiA = ?, traloiB = ?, traloiC = ?, traloiD = ?, dapan = ? where testid = ? and partid = ? and cauhoi = ?");
					ps.setString(1, debai);
					ps.setString(2, imageURL);
					ps.setString(3, traLoiA);
					ps.setString(4, traLoiB);
					ps.setString(5, traLoiC);
					ps.setString(6, traLoiD);
					ps.setString(7, dapan);
					ps.setInt(8, Integer.parseInt(boCauHoi));
					ps.setInt(9, 7); // partid
					ps.setInt(10, Integer.parseInt(cauHoi));

					ps.executeUpdate();
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					JOptionPane.showInternalMessageDialog(null, "Error!!!!");
					ex.printStackTrace();
				}
				InitTextFieldCauHoi();
			}
			return;

		}
		
		if(e.getSource() == btnCapNhat){
			UpdateGiaoDienNguoiDung();
		}
		
		if(e.getSource() == btnImage) {
			/*Khởi tạo file choosrer*/
			JFileChooser chooser = new JFileChooser();
			
			/*Tạo file bắt đầu*/
			chooser.setCurrentDirectory(new File("dethi/part7/"));
			
			/*Tạo bộ lọc cho file*/
			FileFilter filter = new FileNameExtensionFilter("Image file", new String[] {"PNG", "JPG"});
			chooser.setFileFilter(filter);
			
			/*sau khi đã chọn file*/
			int select = chooser.showOpenDialog(null);
			if(select == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				imageURL = file.getAbsolutePath();
			}
			
			return;
		}

	}

	private void InitTextFieldCauHoi() {
		// TODO Auto-generated method stub
		txtBoCauHoi.setText("");
		txtCau.setText("");
		txtDeBai.setText("");
		txtTraLoiA.setText("");
		txtTraLoiB.setText("");
		txtTraLoiC.setText("");
		txtTraLoiD.setText("");
		txtDapAn.setText("");
	}

	// xem giao diện người dùng
	private void UpdateGiaoDienNguoiDung() {
		// TODO Auto-generated method stub
		//tắt panel cũ
		panelMainRight.setVisible(false);
		//tạo panel mới
		panelMainRight = createMainNguoiDung();
		panelRight.add(panelMainRight, BorderLayout.CENTER);
		
		JOptionPane.showMessageDialog(null, "Đã cập nhật lại bộ câu hỏi trong giao diện người dùng.");
	}

	private void XoaCauHoi() {
		String testID = "";
		String cau = "";
		// xóa trên bảng
		DefaultTableModel model = (DefaultTableModel) tablePart7.getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			boolean chk = (Boolean) model.getValueAt(i, 0);
			if (chk == true) {
				testID = model.getValueAt(i, 1).toString();
				cau = model.getValueAt(i, 2).toString();
				// delete data trong csdl
				deleteData(testID, "7", cau);

				model.removeRow(i);
				i--;
			}
		}

	}

	public void deleteData(String testID, String partID, String cau) {
		Connection conn = getConnection();
		try {
			PreparedStatement ps = (PreparedStatement) conn
					.prepareStatement("delete from cauhoi where testid=? and partid=? and cauhoi=?");
			ps.setInt(1, Integer.parseInt(testID));
			ps.setInt(2, Integer.parseInt(partID));
			ps.setInt(3, Integer.parseInt(cau));

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
