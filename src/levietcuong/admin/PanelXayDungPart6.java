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

public class PanelXayDungPart6 extends JPanel implements ActionListener {
	private JPanel panelLeft, panelRight, panelTitile, panelMain, panelMain2, panelButtom, panelInput;
	private JLabel lblTitile, lblBoCauHoi, lblCau, lblTraLoiA, lblTraLoiB, lblTraLoiC, lblTraLoiD, lblDapAn;
	private JTable tablePart6;
	private JFileChooser fileAudio = new JFileChooser();
	private JButton btnThemBoCauHoi, btnAudio;
	private JTextField txtBoCauHoi, txtCau, txtTraLoiA, txtTraLoiB, txtTraLoiC, txtTraLoiD, txtDapAn;
	private JButton btnThemCauHoi;
	private JButton btnSuaCauHoi;
	private JButton btnXoaCauHoi;
	private JButton btnResetTable;
	private JButton btnCapNhat;

	private boolean checkSuaCauHoi = false;
	private boolean checkSuaCauHoi2 = false;

	private JLabel lbldoanHT;
	private JTextArea txtdoanHT;
	private JPanel panelMainRight;

	public PanelXayDungPart6() {
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
		String doanHT = "";
		String traLoiA = "";
		String traLoiB = "";
		String traLoiC = "";
		String traLoiD = "";
		String dapAn = "";
		String lichSuLam = "F";
		
		//lấy dữ liệu trên bảng
		ArrayList<CauHoi> lstCauHoi = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) tablePart6.getModel();
		int count = model.getRowCount();
		for(int row = 0; row < count; row++){
			doanHT = model.getValueAt(row, 3).toString();
			traLoiA = model.getValueAt(row, 4).toString();
			traLoiB = model.getValueAt(row, 5).toString();
			traLoiC = model.getValueAt(row, 6).toString();
			traLoiD = model.getValueAt(row, 7).toString();
			dapAn = model.getValueAt(row, 8).toString();
			
			lstCauHoi.add(new CauHoi(null, traLoiA, traLoiB, traLoiC, traLoiD, dapAn, lichSuLam, null, doanHT));
		}
		QuanLyPart6 part6 = new QuanLyPart6(lstCauHoi);
		panel.add(part6);
		return panel;
	}

	private JPanel createPanelTitile() {
		// TODO Auto-generated method stub
		JPanel panelTitile = new JPanel(new FlowLayout());
		panelTitile.setBackground(new Color(0x9999));
		lblTitile = new JLabel("Xây dựng Bộ câu hỏi Part 6");
		lblTitile.setFont(new Font("Arial", Font.BOLD, 16));
		panelTitile.add(lblTitile);

		return panelTitile;
	}

	public JPanel createMainPanel() {
		JPanel panelMain = new JPanel(new GridLayout(2, 1, 0, 0));
		//thêm bảng câu hỏi part 5
		tablePart6 = createTabelPart6();
		JScrollPane scroll = new JScrollPane(tablePart6);
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
		lbldoanHT = new JLabel("Đoạn hội thoại:");
		lblTraLoiA = new JLabel("Trả lời A: ");
		lblTraLoiB = new JLabel("Trả lời B: ");
		lblTraLoiC = new JLabel("Trả lời C: ");
		lblTraLoiD = new JLabel("Trả lời D: ");
		lblDapAn = new JLabel("Đáp án: ");

		txtBoCauHoi = new JTextField(20);
		txtCau = new JTextField(20);
		txtdoanHT = new JTextArea();
		JScrollPane scroll = new JScrollPane(txtdoanHT);
		txtTraLoiA = new JTextField(20);
		txtTraLoiB = new JTextField(20);
		txtTraLoiC = new JTextField(20);
		txtTraLoiD = new JTextField(20);
		txtDapAn = new JTextField(20);

		JPanel panel1 = new JPanel(new GridLayout(2, 1, 10, 10));
		JPanel panel11 = new JPanel(new GridLayout(2, 1, 10, 10));
		panel11.add(lblBoCauHoi);
		panel11.add(lblCau);
		panel1.add(panel11);
		panel1.add(lbldoanHT);

		JPanel panel3 = new JPanel(new GridLayout(5, 1, 10, 10));
		panel3.add(lblTraLoiA);
		panel3.add(lblTraLoiB);
		panel3.add(lblTraLoiC);
		panel3.add(lblTraLoiD);
		panel3.add(lblDapAn);

		JPanel panel2 = new JPanel(new GridLayout(2, 1, 10, 10));
		JPanel panel21 = new JPanel(new GridLayout(2, 1, 10, 10));
		panel21.add(txtBoCauHoi);
		panel21.add(txtCau);
		panel2.add(panel21);
		panel2.add(scroll);

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

	private JTable createTabelPart6() {
		// TODO Auto-generated method stub
		JTable tablePart6 = new JTable();
		tablePart6.setCellSelectionEnabled(true);
		tablePart6.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

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
		String[] tabelTitle = { "Select", "Bộ câu hỏi", "Câu", "Đề bài", "Trả Lời A", "Trả Lời B",
				"Trả Lời C", "Trả Lời D", "Đáp Án" };
		model.setColumnIdentifiers(tabelTitle);
		tablePart6.setModel(model);

		LoadDuLieuPart6(model);
		
		return tablePart6;
	}

	public void LoadDuLieuPart6(DefaultTableModel model) {
		Connection conn = getConnection();
		try {
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from cauhoi where partid = 6");
			int row = 0;
			while (rs.next()) {
				model.addRow(new Object[0]);
				model.setValueAt(false, row, 0);
				model.setValueAt(rs.getInt(1), row, 1);
				model.setValueAt(rs.getInt(3), row, 2);
				model.setValueAt(rs.getString(12), row, 3);
				model.setValueAt(rs.getString(6), row, 4);
				model.setValueAt(rs.getString(7), row, 5);
				model.setValueAt(rs.getString(8), row, 6);
				model.setValueAt(rs.getString(9), row, 7);
				model.setValueAt(rs.getString(10), row, 8);

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
		DefaultTableModel model = (DefaultTableModel) tablePart6.getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			if (thuTuCau.equals(model.getValueAt(i, 2).toString())
					&& testID.equals(model.getValueAt(i, 1).toString())) {
				JOptionPane.showMessageDialog(null, "Trùng thứ tự câu trong bộ câu hỏi: " + testID);
				return true;
			}
		}
		
		/*kiểm tra đoạn văn*/
		String doanHT = txtdoanHT.getText();
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
			String doanHT = txtdoanHT.getText();
			String traloiA = txtTraLoiA.getText();
			String traloiB = txtTraLoiB.getText();
			String traloiC = txtTraLoiC.getText();
			String traloiD = txtTraLoiD.getText();
			String dapan = txtDapAn.getText();

			// thêm vào bảng
			DefaultTableModel model = (DefaultTableModel) tablePart6.getModel();

			Vector<Object> vector = new Vector<>();
			vector.add(false);
			vector.add(testid);
			vector.add(cau);
			vector.add(doanHT);
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
				ps.setInt(2, 6);
				ps.setInt(3, Integer.parseInt(cau));
				ps.setString(4, null);
				ps.setString(5, null);
				ps.setString(6, traloiA);
				ps.setString(7, traloiB);
				ps.setString(8, traloiC);
				ps.setString(9, traloiD);
				ps.setString(10, dapan);
				ps.setString(11, "F");
				ps.setString(12, doanHT);

				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void ResetTableCauHoi() {
		DefaultTableModel model = (DefaultTableModel) tablePart6.getModel();

		for (int i = 0; model.getRowCount() != 0;) {
			model.removeRow(i);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnThemCauHoi) {
			ThemCauHoi();
			InitTextFieldCauHoi();
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
			int row = tablePart6.getSelectedRow();
			if (row == -1) {
				return;
			}

			if (checkSuaCauHoi == false) {
				// load vào textfield
				checkSuaCauHoi = true;

				DefaultTableModel model = (DefaultTableModel) tablePart6.getModel();
				txtBoCauHoi.setText(model.getValueAt(row, 1).toString());
				txtCau.setText(model.getValueAt(row, 2).toString());
				txtdoanHT.setText(model.getValueAt(row, 3).toString());
				txtTraLoiA.setText(model.getValueAt(row, 4).toString());
				txtTraLoiB.setText(model.getValueAt(row, 5).toString());
				txtTraLoiC.setText(model.getValueAt(row, 6).toString());
				txtTraLoiD.setText(model.getValueAt(row, 7).toString());
				txtDapAn.setText(model.getValueAt(row, 8).toString());
			} else {
				checkSuaCauHoi = false;
				String boCauHoi = txtBoCauHoi.getText();
				String cauHoi = txtCau.getText();
				String doanHT = txtdoanHT.getText();
				String traLoiA = txtTraLoiA.getText();
				String traLoiB = txtTraLoiB.getText();
				String traLoiC = txtTraLoiC.getText();
				String traLoiD = txtTraLoiD.getText();
				String dapan = txtDapAn.getText();

				// sửa trong bảng
				DefaultTableModel model = (DefaultTableModel) tablePart6.getModel();
				model.setValueAt(boCauHoi, row, 1);
				model.setValueAt(cauHoi, row, 2);
				model.setValueAt(doanHT, row, 3);
				model.setValueAt(traLoiA, row, 4);
				model.setValueAt(traLoiB, row, 5);
				model.setValueAt(traLoiC, row, 6);
				model.setValueAt(traLoiD, row, 7);
				model.setValueAt(dapan, row, 8);

				// sửa trong cơ sở dữ liệu
				Connection conn = getConnection();
				try {
					PreparedStatement ps = (PreparedStatement) conn.prepareStatement(
							"update cauhoi set hoithoai = ?, traloiA = ?, traloiB = ?, traloiC = ?, traloiD = ?, dapan = ? where testid = ? and partid = ? and cauhoi = ?");
					ps.setString(1, doanHT);
					ps.setString(2, traLoiA);
					ps.setString(3, traLoiB);
					ps.setString(4, traLoiC);
					ps.setString(5, traLoiD);
					ps.setString(6, dapan);
					ps.setInt(7, Integer.parseInt(boCauHoi));
					ps.setInt(8, 6); // partid
					ps.setInt(9, Integer.parseInt(cauHoi));

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

	}

	private void InitTextFieldCauHoi() {
		// TODO Auto-generated method stub
		txtBoCauHoi.setText("");
		txtCau.setText("");
		txtdoanHT.setText("");
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
		DefaultTableModel model = (DefaultTableModel) tablePart6.getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			boolean chk = (Boolean) model.getValueAt(i, 0);
			if (chk == true) {
				testID = model.getValueAt(i, 1).toString();
				cau = model.getValueAt(i, 2).toString();
				// delete data trong csdl
				deleteData(testID, "6", cau);

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
