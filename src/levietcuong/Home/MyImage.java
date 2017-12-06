package levietcuong.Home;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class MyImage {
	private String fileName;
	private int width, heigh;
	private ImageIcon img;
	
	public MyImage(String fileName, int width, int heigh) {
		// TODO Auto-generated constructor stub
		this.fileName = fileName;
		this.width = width;
		this.heigh = heigh;
		
		this.img = (ImageIcon) load(fileName, width, heigh);
	}
	
	public ImageIcon getImg() {
		return img;
	}
	
	public Icon load(String linkImage, int k, int m) {
		try {
			BufferedImage image = ImageIO.read(new File(linkImage));// đọc ảnh
																	// dùng
																	// BufferedImage
			int x = k;
			int y = m;
			int ix = image.getWidth();
			int iy = image.getHeight();
			int dx = 0, dy = 0;

			if (x / y > ix / iy) {
				dy = y;
				dx = dy * ix / iy;
			} else {
				dx = x;
				dy = dx * iy / ix;
			}

			return new ImageIcon(image.getScaledInstance(dx, dy, image.SCALE_SMOOTH));

		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}
}
