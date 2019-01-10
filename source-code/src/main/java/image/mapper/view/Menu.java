package image.mapper.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class Menu extends JPopupMenu {
	private JPanel fatherPanel;
	public Menu(final JPanel fatherPanel){
		this.fatherPanel = fatherPanel;
		addItem("Close", new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("fechando");
				System.exit(0);
			}
		});
	}

	private void addItem(String label, ActionListener a) {
		JMenuItem fistItem = new JMenuItem(label);
		fistItem.addActionListener(a);
		add(fistItem);
	}
}
