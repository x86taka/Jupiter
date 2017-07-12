package cn.nukkit.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Splash extends JFrame{
	JLabel label;
	JLabel text;
	
	public Splash(String splashText) throws Exception{
			BufferedImage image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("lang/jpn/Jupiter.jpg"));
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			label = new JLabel(new ImageIcon(image));
			label.setSize(408, 48);
			text = new JLabel(splashText);
			text.setSize(200, 30);
			text.setBackground(Color.BLACK);
			text.setForeground(Color.WHITE);
			text.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 20));
			this.setSize(screenSize.width, 48);
			this.setLocation(0, 0);
			this.getContentPane().setBackground(Color.BLACK);
			this.setLayout(new BorderLayout());
			this.getContentPane().add(label, BorderLayout.CENTER);
			this.getContentPane().add(text, BorderLayout.EAST);
			this.setResizable(false);
			this.setUndecorated(true);
			this.setVisible(true);
			Thread.sleep(2000);
			this.setVisible(false);
			return;
	}
}
