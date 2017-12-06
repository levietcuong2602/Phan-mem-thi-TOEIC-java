package levietcuong.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import levietcuong.Home.MyImage;
import levietcuong.tudien.TuDienPanel;
import levietcuong.tudien.Word;

public class PanelTuDien extends JPanel implements ActionListener{
	private JButton btnTraTu;
	private JButton btnThemTu;
	private JButton btnXoaTu;
	private JButton btnSound;
	private JPanel panelListTu;
	private JList<Word> lstTu;
	private JButton btnImage;
	private JPanel panelPhatTrien;
	private JTextField txtTuTiengAnh;
	private JTextArea txtTuTiengViet;
	private JTextField txtAnh;
	private JTextField txtAudio;
	private JButton btnSuaTu;
	private JButton btnAudio;
	
	private boolean modeSuaTu = false;
	private JPanel panelUser;
	private JButton btnNguoiDung;
	private JLabel lblAnhMinhHoa;
	private JButton btnReset;

	public PanelTuDien() {
		// TODO Auto-generated constructor stub
		addControlls();
		addEvents();
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		lstTu.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(lstTu.getSelectedIndex() != -1){
					modeSuaTu = true;
					Word word = lstTu.getSelectedValue();
					loadDataInputTextField(word.getTuAnh());
					
					lblAnhMinhHoa.setIcon(new MyImage(word.getImageURl(), 400, 400).getImg());
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								new Player(new FileInputStream(new File(word.getAudioURL()))).play();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JavaLayerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}).start();
					return;
				}
				
			}
		});
	}

	private void addControlls() {
		// TODO Auto-generated method stub
		setLayout(new GridLayout(0, 2, 10, 10));
		
		//panel nhà phát triển
		panelPhatTrien = createPanelPhatTrien();
		add(panelPhatTrien);
		
		panelUser = createPanelUser();
		add(panelUser);
	}
	
	private JPanel createPanelUser() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new BorderLayout(0, 10));
		
		//title
		JPanel panelTitle = new JPanel();
		panelTitle.setBackground(new Color(0x9999));
		JLabel lblTitle = new JLabel("Bấm vào nút bấm để hiện frame của người dùng");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
		panelTitle.add(lblTitle);
		panel.add(panelTitle, BorderLayout.PAGE_START);
		
		//center
		JPanel panelCenter = new JPanel(new GridLayout(2, 1, 10, 10));
		JPanel panel1 = new JPanel(new BorderLayout());
		panel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(), "Ảnh minh họa"));
		
		//tạo hiệu ứng cho hướng dẫn
		JPanel panel3 = new JPanel(new BorderLayout());
		panel3.setBorder(new EmptyBorder(0, 150, 0, 0));
		lblAnhMinhHoa = new JLabel();
		lblAnhMinhHoa.setIcon(new MyImage("src/image/background.jpg", 400, 400).getImg());
		panel3.add(lblAnhMinhHoa, BorderLayout.CENTER);
		panel1.add(panel3, BorderLayout.CENTER);
		panelCenter.add(panel1);
		
		//panel2
		JPanel panel2 = new JPanel(new BorderLayout());
		panel2.setBorder(new EmptyBorder(50, 250, 200, 200));
		btnNguoiDung = createButton("Hiển thị giao diện người dùng");
		panel2.add(btnNguoiDung, BorderLayout.CENTER);
		panelCenter.add(panel2);
		
		panel.add(panelCenter, BorderLayout.CENTER);
		return panel;
	}

	private JPanel createPanelPhatTrien() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new BorderLayout(5, 0));
		panel.setBorder(BorderFactory.createEtchedBorder());
		
		//title
		JPanel panelTitle = new JPanel();
		panelTitle.setBackground(new Color(0x9999));
		JLabel lblTitle = new JLabel("Phát triển chức năng từ điển");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
		panelTitle.add(lblTitle);
		panel.add(panelTitle, BorderLayout.PAGE_START);
		
		//west: ds tu
		panelListTu = createPanelListTu();
		panel.add(panelListTu, BorderLayout.WEST);
		
		//body: thêm từ
		JPanel panelBody = createPanelCenter();
		panel.add(panelBody, BorderLayout.CENTER);
				
		return panel;
	}

	private JPanel createPanelListTu() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new BorderLayout());
		lstTu = new JList<>();
		JScrollPane scroll = new JScrollPane(lstTu);
		panel.add(scroll, BorderLayout.CENTER);
		
		loadDanhSachTu();
		
		return panel;
	}

	private void loadDanhSachTu() {
		// TODO Auto-generated method stub
		DefaultListModel<Word> model = new DefaultListModel<>();
		try {
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/dethi", "root", "vietcuong");
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from tudien");
			while(rs.next()){
				model.addElement(new Word(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			
			lstTu.setModel(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private JPanel createPanelSouth() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new GridLayout(1, 4, 10, 10));
		panel.setBorder(new EmptyBorder(50, 10, 200, 10));
		btnThemTu = createButton("Thêm từ");
		btnXoaTu = createButton("Xóa từ");
		btnSuaTu = createButton("Sửa từ");
		btnTraTu = createButton("Tra từ");
		btnReset = createButton("Reset");
		
		panel.add(btnThemTu);
		panel.add(btnXoaTu);
		panel.add(btnSuaTu);
		panel.add(btnTraTu);
		panel.add(btnReset);
		
		return panel;
	}

	private JPanel createPanelCenter() {
		// TODO Auto-generated method stub
		JPanel panelBody = new JPanel(new GridLayout(2, 1, 10, 10));
		
		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
		
		JLabel lblTuTiengAnh = new JLabel("Nhập từ tiếng anh: ");
		JLabel lblTuTiengViet = new JLabel("Nhập từ tiếng việt: ");
		JLabel lblAnh = new JLabel("Nhập dẫn ảnh: ");
		JLabel lblAudio = new JLabel("Nhập đường dẫn âm thanh: ");
		
		txtTuTiengAnh = new JTextField(10);
		txtTuTiengViet = new JTextArea();
		JScrollPane scroll = new JScrollPane(txtTuTiengViet);
		txtAnh = new JTextField();
		txtAudio = new JTextField();
		
		btnSound = createButton("Sound");
		JPanel panel1 = new JPanel(new GridLayout(3, 1, 10, 10));
		JPanel panel2 = new JPanel(new BorderLayout(15, 0));
		panel2.add(lblTuTiengAnh, BorderLayout.WEST);
		JPanel panel6 = new JPanel(new BorderLayout());
		panel6.setBorder(new EmptyBorder(0, 43, 0, 0));
		panel6.add(txtTuTiengAnh, BorderLayout.CENTER);
		panel2.add(panel6, BorderLayout.CENTER);
		panel2.add(btnSound, BorderLayout.EAST);
		panel1.add(panel2);
		
		btnImage = createButton("Chọn Ảnh");
		JPanel panel3 = new JPanel(new BorderLayout(5, 0));
		panel3.add(lblAnh, BorderLayout.WEST);
		JPanel panel7 = new JPanel(new BorderLayout());
		panel7.setBorder(new EmptyBorder(0, 73, 0, 10));
		panel7.add(txtAnh);
		panel3.add(panel7, BorderLayout.CENTER);
		panel3.add(btnImage, BorderLayout.EAST);
		panel1.add(panel3);
		
		btnAudio = createButton("Chọn Audio");
		JPanel panel4 = new JPanel(new BorderLayout(5, 0));
		panel4.add(lblAudio, BorderLayout.WEST);
		panel4.add(txtAudio, BorderLayout.CENTER);
		panel4.add(btnAudio, BorderLayout.EAST);
		panel1.add(panel4);
		panel.add(panel1);
		
		JPanel panel5 = new JPanel(new BorderLayout(10, 0));
		panel5.add(lblTuTiengViet, BorderLayout.WEST);
		JPanel panel8 = new JPanel(new BorderLayout());
		panel8.setBorder(new EmptyBorder(0, 45, 0, 100));
		panel8.add(scroll);
		panel5.add(panel8, BorderLayout.CENTER);
		panel.add(panel5);
		
		panelBody.add(panel);
		
		//south: button
		JPanel panelSouth = createPanelSouth();
		panelBody.add(panelSouth);
		
		return panelBody;
	}

	public JButton createButton(String name){
		JButton button = new JButton(name);
		button.addActionListener(this);
		
		return button;
	}
	
	private void TaoAudioTuTiengAnh() {
		// TODO Auto-generated method stub
		String audio = txtAudio.getText();
		if(audio.equals("")){
			JOptionPane.showMessageDialog(null, "Không tìm thấy đường dẫn file. hãy chọn đường dẫn file");
			return;
		}
		
		try {
			new Player(new FileInputStream(new File(audio))).play();;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnImage){
			JFileChooser chooser = new JFileChooser();
			//tạo đường dẫn
			chooser.setCurrentDirectory(new File("tudien"));
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Image file", new String[]{"PNG", "JPG"});
			chooser.setFileFilter(filter);
			
			int chon = chooser.showOpenDialog(null);
			if(chon == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				String image = file.getAbsolutePath();
				txtAnh.setText(image);
				
				lblAnhMinhHoa.setIcon(new MyImage(txtAnh.getText(), 400, 400).getImg());
			}
			return;
		}
		
		if(e.getSource() == btnAudio){
			JFileChooser chooser = new JFileChooser();
			//tạo đường dẫn
			chooser.setCurrentDirectory(new File("tudien"));
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Audio file", new String[]{"MP3"});
			chooser.setFileFilter(filter);
			
			int chon = chooser.showOpenDialog(null);
			if(chon == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				String image = file.getAbsolutePath();
				txtAudio.setText(image);
				
				try {
					new Player(new FileInputStream(file)).play();;
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JavaLayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			return;
		}
		
		if(e.getSource() == btnThemTu){
			ThemTuMethod();
			return;
		}

		
		if(e.getSource() == btnXoaTu){
			XoaTu();
			return;
		}
		
		if(e.getSource() == btnSuaTu){
			SuaTu();
			return;
		}
		
		if(e.getSource() == btnTraTu){
			String word = JOptionPane.showInputDialog(null, "Nhập vào từ cần tìm: ");
			loadDataInputTextField(word);
			
			return;
		}
		
		if(e.getSource() == btnSound){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					TaoAudioTuTiengAnh();
				}
			}).start();
			return;
		}
		
		if(e.getSource() == btnNguoiDung){
			JFrame f = new JFrame();
			f.setSize(1000, 500);
			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			f.setVisible(true);
			
			f.setTitle("Panel người dùng");
			f.add(new TuDienPanel(), BorderLayout.CENTER);
		}
		
		if(e.getSource() == btnReset){
			deleteDataTexField();
			return;
		}
	}

	private void SuaTu() {
		// TODO Auto-generated method stub
		/*khi nhập từ text field*/
		String tuTA = txtTuTiengAnh.getText();
		if(tuTA.equals("")){
			JOptionPane.showMessageDialog(null, "Nhập từ tiếng anh cần sửa");
			return;
		}
		
		if(modeSuaTu == false){
			loadDataInputTextField(tuTA);
			
			modeSuaTu = true;
		}else{
			String tuTV = txtTuTiengViet.getText();
			String image = txtAnh.getText();
			String audio = txtAudio.getText();
			
			String sql = "update tudien set tutiengviet=?, hinhanh=?, amthanh=? where tutienganh=?";
			String[]data = {tuTV, image, audio, tuTA};
			excuteUpdate(sql, data);
			loadDanhSachTu();
			
			modeSuaTu = false;
			deleteDataTexField();
			return;
		}
	}

	private void loadDataInputTextField(String word) {
		// TODO Auto-generated method stub
		try {
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/dethi", "root", "vietcuong");
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from tudien where tutienganh='"+word+"'");
			
			while(rs.next()){
				txtTuTiengAnh.setText(rs.getString(1));
				txtTuTiengViet.setText(rs.getString(2));
				txtAnh.setText(rs.getString(3));
				txtAudio.setText(rs.getString(4));
				
				lblAnhMinhHoa.setIcon(new MyImage(txtAnh.getText(), 400, 400).getImg());
				return;
			}
			JOptionPane.showMessageDialog(null, "Không tìm thấy từ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Lỗi");
			e.printStackTrace();
		}
	}
	
	private void XoaTu() {
		// TODO Auto-generated method stub
		/*Nếu từ được chọn trong danh sách jlist*/
		if(lstTu.getSelectedIndex() != -1){
			Word word = lstTu.getSelectedValue();
			
			int chon = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa từ này?");
			if(chon != 0){
				return;
			}
			
			String sql = "delete from tudien where tutienganh=?";
			String[]data = {word.getTuAnh()};
			excuteUpdate(sql, data);
			loadDanhSachTu();
			deleteDataTexField();
			
			return;
		}
		
		/*nếu từ được nhập từ text field*/
		String tuTA = txtTuTiengAnh.getText();
		if(tuTA.equals("")){
			JOptionPane.showMessageDialog(null, "Nhập từ cần xóa hoặc chọn trong danh sách từ");
			return;
		}
		
		/*load dữ liệu vào text fiel và hỏi lại lần nữa xem có muốn xóa không*/
		loadDataInputTextField(tuTA);
		int chon = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa từ này?");
		if(chon != 0){
			return;
		}
		
		String sql = "delete from tudien where tutienganh=?";
		String[]data = {tuTA};
		excuteUpdate(sql, data);
		loadDanhSachTu();
		deleteDataTexField();
	}

	public void excuteUpdate(String sql, String []data){
		try {
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/dethi", "root", "vietcuong");
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			
			int length = data.length;
			for(int i = 1; i <= length; i++){
				ps.setString(i, data[i-1]);
			}
			
			ps.executeUpdate();
			loadDanhSachTu();
			deleteDataTexField();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Lỗi");
			e.printStackTrace();
		}
	}

	private void ThemTuMethod() {
		// TODO Auto-generated method stub
		String tuTA = txtTuTiengAnh.getText();
		String tuTV = txtTuTiengViet.getText();
		String image = txtAnh.getText();
		String audio = txtAudio.getText();
		
		if(tuTA.equals("") || tuTV.equals("") || image.equals("") || audio.equals("")){
			JOptionPane.showMessageDialog(null, "Bạn cần điền đầy đủ thông tin.");
			return;
		}
		/*Kiểm tra file có tồn tại không*/
		File file = new File(image);
		if(!file.exists()) {
			JOptionPane.showMessageDialog(null, "Đường dẫn ảnh không hợp lệ");
			return;
		}
		
		file = new File(audio);
		if(!file.exists()) {
			JOptionPane.showMessageDialog(null, "Đường dẫn audio không hợp lệ");
			return;
		}
		
		/*kiểm tra trùng*/
		DefaultListModel<Word> model = (DefaultListModel<Word>) lstTu.getModel();
		int count = model.getSize();
		Word word = null;
		for(int i = 0; i < count; i++) {
			word = model.getElementAt(i);
			
			if(tuTA.equals(word.getTuAnh())) {
				JOptionPane.showMessageDialog(null, "Từ đã tồn tại trong CSDL");
				return;
			}
		}
		
		/*thêm vào csdl*/
		String sql = "insert into tudien values(?, ?, ?, ?)";
		String []values = {tuTA, tuTV, image, audio};
		
		excuteUpdate(sql, values);
	}
	
	public void deleteDataTexField(){
		txtTuTiengAnh.setText("");
		txtTuTiengViet.setText("");
		txtAnh.setText("");
		txtAudio.setText("");
		
		lblAnhMinhHoa.setIcon(new MyImage("src/image/background.jpg", 400, 400).getImg());
	}
}
