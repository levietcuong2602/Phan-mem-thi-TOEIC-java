package levietcuong.thithu;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import levietcuong.Home.MyImage;

public class TestRender extends JPanel implements ListCellRenderer<Test>{
	private JLabel lblText, lblIcon;
	
	public TestRender() {
		// TODO Auto-generated constructor stub
		setLayout(new FlowLayout());
		lblText = new JLabel();
		lblIcon = new JLabel();
		add(lblIcon);
		add(lblText);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Test> list, Test value, int arg2, boolean isSelected,
			boolean arg4) {
		// TODO Auto-generated method stub
		//set text
		lblText.setText(value.getName());
		lblIcon.setIcon(new MyImage("", 40, 40).getImg());
		
		// set Opaque to change background color of JLabel
        lblText.setOpaque(true);
        lblIcon.setOpaque(true);
        
        // when select item
        if (isSelected) {
            lblText.setBackground(list.getSelectionBackground());
            lblIcon.setBackground(list.getSelectionBackground());
            setBackground(list.getSelectionBackground());
        } else { // when don't select
            lblText.setBackground(list.getBackground());
            lblIcon.setBackground(list.getBackground());
            setBackground(list.getBackground());
        }
		
		return this;
	}

}
