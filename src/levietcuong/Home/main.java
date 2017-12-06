package levietcuong.Home;

import javax.swing.SwingUtilities;

public class main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new MainFrame();
			}
		});

	}
}
