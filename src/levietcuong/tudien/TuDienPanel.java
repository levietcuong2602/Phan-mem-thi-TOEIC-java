package levietcuong.tudien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import levietcuong.Home.MyImage;

public class TuDienPanel extends JPanel {
	private JPanel panelTraTu;
	private JTextField txtTraTu;
	private JButton btnTraTu;
	private JPanel panelTuAnh;
	private JLabel lblTuAnh;
	private JButton btnPhatAm;
	private JPanel panelNghia;
	private JTextArea txtNghia;
	private JLabel lblAnhMinhHoa;

	private String audio;
	public TuDienPanel() {
		// TODO Auto-generated constructor stub
		setLayout(new GridLayout(2, 1, 10, 10));
		panelTraTu = createPanelTraTu();
		add(panelTraTu);
		
		//panel nghĩa
		panelNghia = createPanelNghia();
		add(panelNghia);
		panelNghia.setVisible(false);
		addEvents();
	}

	private JPanel createPanelNghia() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
		panel.setBorder(new EmptyBorder(0, 50, 50, 100));
		txtNghia = new JTextArea();
		JScrollPane scrol = new JScrollPane(txtNghia);
		panel.add(scrol, BorderLayout.CENTER);
		
		//hinh ảnh
		JPanel panel2 = new JPanel();
		panel2.setBorder(BorderFactory.createEtchedBorder());
		lblAnhMinhHoa = new JLabel();
		panel2.add(lblAnhMinhHoa);
		panel.add(panel2);
		return panel;
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		btnTraTu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TraTuMethod();
				return;
			}
		});
		
		btnPhatAm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						PhatAmMethod();
					}
				}).start();
				return;
			}

		});
		
		txtTraTu.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					TraTuMethod();
					return;
				}
			}
		});
	}
	
	private void PhatAmMethod() {
		// TODO Auto-generated method stub
		try {
			Player play = new Player(new FileInputStream(audio));
			play.play();
		} catch (FileNotFoundException | JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void TraTuMethod() {
		// TODO Auto-generated method stub
		String word = txtTraTu.getText();
		if(word.equals("")){
			JOptionPane.showMessageDialog(null, "Nhập từ cần tra");
			return;
		}
		
		TraNghiaCuaTu(word);
	}

	private void TraNghiaCuaTu(String word) {
		// TODO Auto-generated method stub
		//tìm trong csdl
		String result = "";
		String image = "";
		audio = "";
		
		String url = "jdbc:mysql://localhost/dethi";
		String username = "root";
		String password = "vietcuong";
		String sql = "select * from tudien where tutienganh='"+word+"'";
		try {
			Connection conn = (Connection) DriverManager.getConnection(url, username, password);
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()){
				result = rs.getString(2);
				image = rs.getString(3);
				audio = rs.getString(4);
				
				lblTuAnh.setText(word);
				panelTuAnh.setVisible(true);
				
				txtNghia.setText(result);
				panelNghia.setVisible(true);
				lblAnhMinhHoa.setIcon(new MyImage(image, 300, 300).getImg());
				
				return;
			}
			
			JOptionPane.showMessageDialog(null, "Không tìm thấy từ");
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private JPanel createPanelTraTu() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		//title
		JPanel panelTitle = new JPanel();
		JLabel lblTitle = new JLabel();
		lblTitle.setIcon(new MyImage("src/image/laban.png", 1000, 300).getImg());
		panelTitle.add(lblTitle);
		panel.add(panelTitle);
		
		JComboBox<String> cbModel = new JComboBox<String>();
		cbModel.addItem("Anh - Việt");
		
		//khung tra từ
		JPanel panel1 = new JPanel(new GridLayout(3, 1, 10, 10));
		JPanel panel2 = new JPanel(new BorderLayout(10, 0));
		panel2.setBorder(new EmptyBorder(0, 300, 0, 300));
		
		txtTraTu = new JTextField();
		JPanel panel3 = new JPanel(new BorderLayout(10, 0));
		panel3.add(txtTraTu, BorderLayout.CENTER);
		panel3.add(cbModel, BorderLayout.EAST);
		
		btnTraTu = new JButton("Tra từ");
		panel2.add(panel3, BorderLayout.CENTER);
		panel2.add(btnTraTu, BorderLayout.EAST);
		panel1.add(panel2);
	
		//panel từ tiếng anh
		panelTuAnh = new JPanel(new BorderLayout(10, 0));
		panelTuAnh.setBorder(new EmptyBorder(0, 100, 0, 1000));
		lblTuAnh = new JLabel("Girl");
		lblTuAnh.setFont(new Font("Arial", Font.BOLD, 16));
		btnPhatAm = new JButton("Phát âm");
		JPanel pnPhatAm = new JPanel();
		pnPhatAm.setBorder(new EmptyBorder(5, 0, 5, 0));
		pnPhatAm.add(btnPhatAm);
		panelTuAnh.add(lblTuAnh, BorderLayout.CENTER);
		panelTuAnh.add(pnPhatAm, BorderLayout.EAST);
		panel1.add(panelTuAnh);
		panel1.add(new JSeparator());
		//ẩn từ
		panelTuAnh.setVisible(false);
		
		panel.add(panel1);
		return panel;
	}
}
