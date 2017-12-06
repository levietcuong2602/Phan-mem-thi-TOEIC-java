package levietcuong.admin;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import levietcuong.Home.MyImage;

public class XemAnhFrame extends JFrame {
	private JLabel labelImage;
	public XemAnhFrame(String filePath) {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		setTitle("Xem ảnh");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		
		labelImage = new JLabel();
		labelImage.setIcon(new MyImage(filePath, 500, 500).getImg());
		add(labelImage);
	}
}
