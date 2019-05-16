import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.awt.event.ActionEvent;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.UIManager;
public class lightup {

	private JFrame frmLittleGame;
	private JTextField txtSteps;
	private int[][] tag=new int[7][7];
	private JButton[][] jb=new JButton[7][7];
	private JTextField txtLightThemUp;
	private int[] dx = {0,0,1,-1};
	private int[] dy = {1,-1,0,0};
	private int counter=0;
	private int music_on=0;
	private Clip clip;
	void update(int x,int y) {
		if(this.tag[x][y]==1) {
			jb[x][y].setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-light.png")));
		}
		else {
			jb[x][y].setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		}
	}
	
	boolean check() {
		for(int i=1;i<=6;i++)
			for(int j=1;j<=6;j++)
				if(this.tag[i][j]==0)return false;
		return true;
	}
	
	void victory() {
		JTextField co=(JTextField)this.frmLittleGame.getContentPane().getComponent(2);
		co.setFont(new Font("Courier New",Font.PLAIN,25));
		co.setForeground(Color.red);
		co.setText("Congratulations!");
	}
	
	void press(int x,int y) {
		this.tag[x][y]^=1;
		update(x,y);
		for(int i=0;i<4;i++) {
			int eks=x+dx[i],wai=y+dy[i];
			if(eks>=1&&eks<=6&&wai>=1&&wai<=6) {
				this.tag[eks][wai]^=1;
				update(eks,wai);
			}
		}
		counter++;
		JTextField co=(JTextField)this.frmLittleGame.getContentPane().getComponent(4);
		co.setText("Steps: "+String.valueOf(counter));
		if(check())victory();
	}
	
	void Initialize() {
		counter=0;
		JTextField co=(JTextField)this.frmLittleGame.getContentPane().getComponent(4);
		co.setText("Steps: "+String.valueOf(counter));
		co=(JTextField)this.frmLittleGame.getContentPane().getComponent(2);
		co.setText(" Light them up!");
		co.setForeground(Color.black);
		for(int i=1;i<=6;i++)
			for(int j=1;j<=6;j++) {
				double d=Math.random();
				if(d<0.5)this.tag[i][j]=1;
				else this.tag[i][j]=0;
				update(i,j);
			}
		if(check())victory();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lightup window = new lightup();
					window.Initialize();
					window.frmLittleGame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public lightup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLittleGame = new JFrame();
		frmLittleGame.setTitle("Little Game V1.0");
		frmLittleGame.getContentPane().setBackground(UIManager.getColor("Button.darkShadow"));
		frmLittleGame.setResizable(false);
		frmLittleGame.setBounds(100, 100, 800, 600);
		frmLittleGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{130, 130, 0, 130, 130, 130, 130, 0};
		gridBagLayout.rowHeights = new int[]{79, 0, 79, 79, 79, 79, 79, 79, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmLittleGame.getContentPane().setLayout(gridBagLayout);
		
		JButton btnNewButton = new JButton("Reset");
		btnNewButton.setFont(new Font("Courier New", Font.PLAIN, 20));
		btnNewButton.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Initialize();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		frmLittleGame.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Music!");
		btnNewButton_1.setFont(new Font("Courier New", Font.PLAIN, 20));
		btnNewButton_1.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaMuteDisabled.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(music_on==0) {
				try {
					 clip = AudioSystem.getClip();
					 AudioInputStream inputStream = AudioSystem.getAudioInputStream(
				     lightup.class.getResourceAsStream("bgm.wav"));
				     clip.open(inputStream);
				     int INF=2147483647;
				     clip.loop(INF);
				     music_on=1;
				     }
				 catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				  }
			}
			else {
				clip.stop();
				music_on=0;
			}
		}
	});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 0;
		frmLittleGame.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_4 = new JButton("Help");
		btnNewButton_4.setFont(new Font("Courier New", Font.PLAIN, 20));
		btnNewButton_4.setIcon(new ImageIcon(lightup.class.getResource("/icons/full/message_info@2x.png")));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "本游戏是一个课程作业。\n点击当前为黑色的按钮可以将其和周围的方块变白。当所有方块变白时，游戏胜利结束：P\n 按reset键可以再来一盘，而按music你甚至可以欣赏BGM！\n（别忘了再按一次music可以静音哦）","玩法介绍",JOptionPane.INFORMATION_MESSAGE);  
			}
		});
		
		txtLightThemUp = new JTextField();
		txtLightThemUp.setBackground(UIManager.getColor("Button.darkShadow"));
		txtLightThemUp.setFont(new Font("Courier New", Font.PLAIN, 25));
		txtLightThemUp.setText(" Light Them Up!");
		txtLightThemUp.setEditable(false);
		GridBagConstraints gbc_txtLightThemUp = new GridBagConstraints();
		gbc_txtLightThemUp.gridwidth = 2;
		gbc_txtLightThemUp.insets = new Insets(0, 0, 5, 5);
		gbc_txtLightThemUp.fill = GridBagConstraints.BOTH;
		gbc_txtLightThemUp.gridx = 3;
		gbc_txtLightThemUp.gridy = 0;
		frmLittleGame.getContentPane().add(txtLightThemUp, gbc_txtLightThemUp);
		txtLightThemUp.setColumns(10);
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_4.gridx = 5;
		gbc_btnNewButton_4.gridy = 0;
		frmLittleGame.getContentPane().add(btnNewButton_4, gbc_btnNewButton_4);
		
		txtSteps = new JTextField();
		txtSteps.setFont(new Font("Courier New", Font.PLAIN, 20));
		txtSteps.setEditable(false);
		txtSteps.setText("Steps: 0");
		GridBagConstraints gbc_txtSteps = new GridBagConstraints();
		gbc_txtSteps.insets = new Insets(0, 0, 5, 0);
		gbc_txtSteps.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSteps.gridx = 6;
		gbc_txtSteps.gridy = 0;
		frmLittleGame.getContentPane().add(txtSteps, gbc_txtSteps);
		txtSteps.setColumns(10);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 3;
		gbc_verticalStrut.gridy = 1;
		frmLittleGame.getContentPane().add(verticalStrut, gbc_verticalStrut);
		
		JButton btnNewButton_0101 = new JButton("");
		btnNewButton_0101.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0101.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0101.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0101.setBorderPainted(false);//不打印边框  
		btnNewButton_0101.setBorder(null);//除去边框  
		btnNewButton_0101.setText(null);//除去按钮的默认名称  
		btnNewButton_0101.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0101.setContentAreaFilled(false);//除去默认的背景填充
        jb[1][1]=btnNewButton_0101;
		btnNewButton_0101.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(1,1);
			}
		});
		GridBagConstraints gbc_btnNewButton_0101 = new GridBagConstraints();
		gbc_btnNewButton_0101.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0101.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0101.gridx = 0;
		gbc_btnNewButton_0101.gridy = 2;
		frmLittleGame.getContentPane().add(btnNewButton_0101, gbc_btnNewButton_0101);
		
		JButton btnNewButton_0102 = new JButton("");
		btnNewButton_0102.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0102.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0102.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0102.setBorderPainted(false);//不打印边框  
		btnNewButton_0102.setBorder(null);//除去边框  
		btnNewButton_0102.setText(null);//除去按钮的默认名称  
		btnNewButton_0102.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0102.setContentAreaFilled(false);//除去默认的背景填充
        jb[1][2]=btnNewButton_0102;
		GridBagConstraints gbc_btnNewButton_0102 = new GridBagConstraints();
		gbc_btnNewButton_0102.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0102.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0102.gridx = 1;
		gbc_btnNewButton_0102.gridy = 2;
		btnNewButton_0102.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(1,2);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0102, gbc_btnNewButton_0102);
		
		JButton btnNewButton_0103 = new JButton("");
		btnNewButton_0103.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0103.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0103.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0103.setBorderPainted(false);//不打印边框  
		btnNewButton_0103.setBorder(null);//除去边框  
		btnNewButton_0103.setText(null);//除去按钮的默认名称  
		btnNewButton_0103.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0103.setContentAreaFilled(false);//除去默认的背景填充
        jb[1][3]=btnNewButton_0103;
		GridBagConstraints gbc_btnNewButton_0103 = new GridBagConstraints();
		gbc_btnNewButton_0103.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0103.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0103.gridx = 3;
		gbc_btnNewButton_0103.gridy = 2;
		btnNewButton_0103.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(1,3);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0103, gbc_btnNewButton_0103);
		
		JButton btnNewButton_0104 = new JButton("");
		btnNewButton_0104.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0104.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0104.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0104.setBorderPainted(false);//不打印边框  
		btnNewButton_0104.setBorder(null);//除去边框  
		btnNewButton_0104.setText(null);//除去按钮的默认名称  
		btnNewButton_0104.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0104.setContentAreaFilled(false);//除去默认的背景填充
        jb[1][4]=btnNewButton_0104;
		GridBagConstraints gbc_btnNewButton_0104 = new GridBagConstraints();
		gbc_btnNewButton_0104.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0104.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0104.gridx = 4;
		gbc_btnNewButton_0104.gridy = 2;
		btnNewButton_0104.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(1,4);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0104, gbc_btnNewButton_0104);
		
		JButton btnNewButton_0105 = new JButton("");
		btnNewButton_0105.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0105.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0105.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0105.setBorderPainted(false);//不打印边框  
		btnNewButton_0105.setBorder(null);//除去边框  
		btnNewButton_0105.setText(null);//除去按钮的默认名称  
		btnNewButton_0105.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0105.setContentAreaFilled(false);//除去默认的背景填充
        jb[1][5]=btnNewButton_0105;
		GridBagConstraints gbc_btnNewButton_0105 = new GridBagConstraints();
		gbc_btnNewButton_0105.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0105.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0105.gridx = 5;
		gbc_btnNewButton_0105.gridy = 2;
		btnNewButton_0105.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(1,5);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0105, gbc_btnNewButton_0105);
		
		JButton btnNewButton_0106 = new JButton("");
		btnNewButton_0106.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0106.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0106.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0106.setBorderPainted(false);//不打印边框  
		btnNewButton_0106.setBorder(null);//除去边框  
		btnNewButton_0106.setText(null);//除去按钮的默认名称  
		btnNewButton_0106.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0106.setContentAreaFilled(false);//除去默认的背景填充
        jb[1][6]=btnNewButton_0106;
		GridBagConstraints gbc_btnNewButton_0106 = new GridBagConstraints();
		gbc_btnNewButton_0106.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0106.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_0106.gridx = 6;
		gbc_btnNewButton_0106.gridy = 2;
		btnNewButton_0106.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(1,6);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0106, gbc_btnNewButton_0106);
		
		JButton btnNewButton_0201 = new JButton("");
		btnNewButton_0201.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0201.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0201.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0201.setBorderPainted(false);//不打印边框  
		btnNewButton_0201.setBorder(null);//除去边框  
		btnNewButton_0201.setText(null);//除去按钮的默认名称  
		btnNewButton_0201.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0201.setContentAreaFilled(false);//除去默认的背景填充
        jb[2][1]=btnNewButton_0201;
		GridBagConstraints gbc_btnNewButton_0201 = new GridBagConstraints();
		gbc_btnNewButton_0201.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0201.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0201.gridx = 0;
		gbc_btnNewButton_0201.gridy = 3;
		btnNewButton_0201.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(2,1);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0201, gbc_btnNewButton_0201);
		
		JButton btnNewButton_0202 = new JButton("");
		btnNewButton_0202.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0202.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0202.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0202.setBorderPainted(false);//不打印边框  
		btnNewButton_0202.setBorder(null);//除去边框  
		btnNewButton_0202.setText(null);//除去按钮的默认名称  
		btnNewButton_0202.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0202.setContentAreaFilled(false);//除去默认的背景填充
        jb[2][2]=btnNewButton_0202;
		GridBagConstraints gbc_btnNewButton_0202 = new GridBagConstraints();
		gbc_btnNewButton_0202.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0202.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0202.gridx = 1;
		gbc_btnNewButton_0202.gridy = 3;
		btnNewButton_0202.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(2,2);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0202, gbc_btnNewButton_0202);
		
		JButton btnNewButton_0203 = new JButton("");
		btnNewButton_0203.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0203.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0203.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0203.setBorderPainted(false);//不打印边框  
		btnNewButton_0203.setBorder(null);//除去边框  
		btnNewButton_0203.setText(null);//除去按钮的默认名称  
		btnNewButton_0203.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0203.setContentAreaFilled(false);//除去默认的背景填充
        jb[2][3]=btnNewButton_0203;
		GridBagConstraints gbc_btnNewButton_0203 = new GridBagConstraints();
		gbc_btnNewButton_0203.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0203.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0203.gridx = 3;
		gbc_btnNewButton_0203.gridy = 3;
		btnNewButton_0203.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(2,3);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0203, gbc_btnNewButton_0203);
		
		JButton btnNewButton_0204 = new JButton("");
		btnNewButton_0204.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0204.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0204.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0204.setBorderPainted(false);//不打印边框  
		btnNewButton_0204.setBorder(null);//除去边框  
		btnNewButton_0204.setText(null);//除去按钮的默认名称  
		btnNewButton_0204.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0204.setContentAreaFilled(false);//除去默认的背景填充
        jb[2][4]=btnNewButton_0204;
		GridBagConstraints gbc_btnNewButton_0204 = new GridBagConstraints();
		gbc_btnNewButton_0204.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0204.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0204.gridx = 4;
		gbc_btnNewButton_0204.gridy = 3;
		btnNewButton_0204.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(2,4);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0204, gbc_btnNewButton_0204);
		
		JButton btnNewButton_0205 = new JButton("");
		btnNewButton_0205.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0205.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0205.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0205.setBorderPainted(false);//不打印边框  
		btnNewButton_0205.setBorder(null);//除去边框  
		btnNewButton_0205.setText(null);//除去按钮的默认名称  
		btnNewButton_0205.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0205.setContentAreaFilled(false);//除去默认的背景填充
        jb[2][5]=btnNewButton_0205;
		GridBagConstraints gbc_btnNewButton_0205 = new GridBagConstraints();
		gbc_btnNewButton_0205.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0205.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0205.gridx = 5;
		gbc_btnNewButton_0205.gridy = 3;
		btnNewButton_0205.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(2,5);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0205, gbc_btnNewButton_0205);
		
		JButton btnNewButton_0206 = new JButton("");
		btnNewButton_0206.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0206.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0206.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0206.setBorderPainted(false);//不打印边框  
		btnNewButton_0206.setBorder(null);//除去边框  
		btnNewButton_0206.setText(null);//除去按钮的默认名称  
		btnNewButton_0206.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0206.setContentAreaFilled(false);//除去默认的背景填充
        jb[2][6]=btnNewButton_0206;
		GridBagConstraints gbc_btnNewButton_0206 = new GridBagConstraints();
		gbc_btnNewButton_0206.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0206.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_0206.gridx = 6;
		gbc_btnNewButton_0206.gridy = 3;
		btnNewButton_0206.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(2,6);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0206, gbc_btnNewButton_0206);
		
		JButton btnNewButton_0301 = new JButton("");
		btnNewButton_0301.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0301.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0301.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0301.setBorderPainted(false);//不打印边框  
		btnNewButton_0301.setBorder(null);//除去边框  
		btnNewButton_0301.setText(null);//除去按钮的默认名称  
		btnNewButton_0301.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0301.setContentAreaFilled(false);//除去默认的背景填充
        jb[3][1]=btnNewButton_0301;
		GridBagConstraints gbc_btnNewButton_0301 = new GridBagConstraints();
		gbc_btnNewButton_0301.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0301.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0301.gridx = 0;
		gbc_btnNewButton_0301.gridy = 4;
		btnNewButton_0301.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(3,1);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0301, gbc_btnNewButton_0301);
		
		JButton btnNewButton_0302 = new JButton("");
		btnNewButton_0302.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0302.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0302.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0302.setBorderPainted(false);//不打印边框  
		btnNewButton_0302.setBorder(null);//除去边框  
		btnNewButton_0302.setText(null);//除去按钮的默认名称  
		btnNewButton_0302.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0302.setContentAreaFilled(false);//除去默认的背景填充
        jb[3][2]=btnNewButton_0302;
		GridBagConstraints gbc_btnNewButton_0302 = new GridBagConstraints();
		gbc_btnNewButton_0302.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0302.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0302.gridx = 1;
		gbc_btnNewButton_0302.gridy = 4;
		btnNewButton_0302.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(3,2);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0302, gbc_btnNewButton_0302);
		
		JButton btnNewButton_0303 = new JButton("");
		btnNewButton_0303.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0303.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0303.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0303.setBorderPainted(false);//不打印边框  
		btnNewButton_0303.setBorder(null);//除去边框  
		btnNewButton_0303.setText(null);//除去按钮的默认名称  
		btnNewButton_0303.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0303.setContentAreaFilled(false);//除去默认的背景填充
        jb[3][3]=btnNewButton_0303;
		GridBagConstraints gbc_btnNewButton_0303 = new GridBagConstraints();
		gbc_btnNewButton_0303.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0303.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0303.gridx = 3;
		gbc_btnNewButton_0303.gridy = 4;
		btnNewButton_0303.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(3,3);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0303, gbc_btnNewButton_0303);
		
		JButton btnNewButton_0304 = new JButton("");
		btnNewButton_0304.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0304.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0304.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0304.setBorderPainted(false);//不打印边框  
		btnNewButton_0304.setBorder(null);//除去边框  
		btnNewButton_0304.setText(null);//除去按钮的默认名称  
		btnNewButton_0304.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0304.setContentAreaFilled(false);//除去默认的背景填充
        jb[3][4]=btnNewButton_0304;
		GridBagConstraints gbc_btnNewButton_0304 = new GridBagConstraints();
		gbc_btnNewButton_0304.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0304.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0304.gridx = 4;
		gbc_btnNewButton_0304.gridy = 4;
		btnNewButton_0304.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(3,4);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0304, gbc_btnNewButton_0304);
		
		JButton btnNewButton_0305 = new JButton("");
		btnNewButton_0305.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0305.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0305.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0305.setBorderPainted(false);//不打印边框  
		btnNewButton_0305.setBorder(null);//除去边框  
		btnNewButton_0305.setText(null);//除去按钮的默认名称  
		btnNewButton_0305.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0305.setContentAreaFilled(false);//除去默认的背景填充
        jb[3][5]=btnNewButton_0305;
		GridBagConstraints gbc_btnNewButton_0305 = new GridBagConstraints();
		gbc_btnNewButton_0305.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0305.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0305.gridx = 5;
		gbc_btnNewButton_0305.gridy = 4;
		btnNewButton_0305.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(3,5);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0305, gbc_btnNewButton_0305);
		
		JButton btnNewButton_0306 = new JButton("");
		btnNewButton_0306.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0306.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0306.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0306.setBorderPainted(false);//不打印边框  
		btnNewButton_0306.setBorder(null);//除去边框  
		btnNewButton_0306.setText(null);//除去按钮的默认名称  
		btnNewButton_0306.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0306.setContentAreaFilled(false);//除去默认的背景填充
        jb[3][6]=btnNewButton_0306;
		GridBagConstraints gbc_btnNewButton_0306 = new GridBagConstraints();
		gbc_btnNewButton_0306.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0306.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_0306.gridx = 6;
		gbc_btnNewButton_0306.gridy = 4;
		btnNewButton_0306.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(3,6);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0306, gbc_btnNewButton_0306);
		
		JButton btnNewButton_0401 = new JButton("");
		btnNewButton_0401.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0401.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0401.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0401.setBorderPainted(false);//不打印边框  
		btnNewButton_0401.setBorder(null);//除去边框  
		btnNewButton_0401.setText(null);//除去按钮的默认名称  
		btnNewButton_0401.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0401.setContentAreaFilled(false);//除去默认的背景填充
        jb[4][1]=btnNewButton_0401;
		GridBagConstraints gbc_btnNewButton_0401 = new GridBagConstraints();
		gbc_btnNewButton_0401.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0401.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0401.gridx = 0;
		gbc_btnNewButton_0401.gridy = 5;
		btnNewButton_0401.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(4,1);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0401, gbc_btnNewButton_0401);
		
		JButton btnNewButton_0402 = new JButton("");
		btnNewButton_0402.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0402.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0402.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0402.setBorderPainted(false);//不打印边框  
		btnNewButton_0402.setBorder(null);//除去边框  
		btnNewButton_0402.setText(null);//除去按钮的默认名称  
		btnNewButton_0402.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0402.setContentAreaFilled(false);//除去默认的背景填充
        jb[4][2]=btnNewButton_0402;
		GridBagConstraints gbc_btnNewButton_0402 = new GridBagConstraints();
		gbc_btnNewButton_0402.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0402.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0402.gridx = 1;
		gbc_btnNewButton_0402.gridy = 5;
		btnNewButton_0402.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(4,2);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0402, gbc_btnNewButton_0402);
		
		JButton btnNewButton_0403 = new JButton("");
		btnNewButton_0403.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0403.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0403.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0403.setBorderPainted(false);//不打印边框  
		btnNewButton_0403.setBorder(null);//除去边框  
		btnNewButton_0403.setText(null);//除去按钮的默认名称  
		btnNewButton_0403.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0403.setContentAreaFilled(false);//除去默认的背景填充
        jb[4][3]=btnNewButton_0403;
		GridBagConstraints gbc_btnNewButton_0403 = new GridBagConstraints();
		gbc_btnNewButton_0403.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0403.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0403.gridx = 3;
		gbc_btnNewButton_0403.gridy = 5;
		btnNewButton_0403.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(4,3);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0403, gbc_btnNewButton_0403);
		
		JButton btnNewButton_0404 = new JButton("");
		btnNewButton_0404.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0404.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0404.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0404.setBorderPainted(false);//不打印边框  
		btnNewButton_0404.setBorder(null);//除去边框  
		btnNewButton_0404.setText(null);//除去按钮的默认名称  
		btnNewButton_0404.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0404.setContentAreaFilled(false);//除去默认的背景填充
        jb[4][4]=btnNewButton_0404;
		GridBagConstraints gbc_btnNewButton_0404 = new GridBagConstraints();
		gbc_btnNewButton_0404.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0404.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0404.gridx = 4;
		gbc_btnNewButton_0404.gridy = 5;
		btnNewButton_0404.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(4,4);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0404, gbc_btnNewButton_0404);
		
		JButton btnNewButton_0405 = new JButton("");
		btnNewButton_0405.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0405.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0405.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0405.setBorderPainted(false);//不打印边框  
		btnNewButton_0405.setBorder(null);//除去边框  
		btnNewButton_0405.setText(null);//除去按钮的默认名称  
		btnNewButton_0405.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0405.setContentAreaFilled(false);//除去默认的背景填充
        jb[4][5]=btnNewButton_0405;
		GridBagConstraints gbc_btnNewButton_0405 = new GridBagConstraints();
		gbc_btnNewButton_0405.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0405.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0405.gridx = 5;
		gbc_btnNewButton_0405.gridy = 5;
		btnNewButton_0405.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(4,5);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0405, gbc_btnNewButton_0405);
		
		JButton btnNewButton_0406 = new JButton("");
		btnNewButton_0406.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0406.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0406.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0406.setBorderPainted(false);//不打印边框  
		btnNewButton_0406.setBorder(null);//除去边框  
		btnNewButton_0406.setText(null);//除去按钮的默认名称  
		btnNewButton_0406.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0406.setContentAreaFilled(false);//除去默认的背景填充
        jb[4][6]=btnNewButton_0406;
		GridBagConstraints gbc_btnNewButton_0406 = new GridBagConstraints();
		gbc_btnNewButton_0406.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0406.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_0406.gridx = 6;
		gbc_btnNewButton_0406.gridy = 5;
		btnNewButton_0406.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(4,6);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0406, gbc_btnNewButton_0406);
		
		JButton btnNewButton_0501 = new JButton("");
		btnNewButton_0501.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0501.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0501.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0501.setBorderPainted(false);//不打印边框  
		btnNewButton_0501.setBorder(null);//除去边框  
		btnNewButton_0501.setText(null);//除去按钮的默认名称  
		btnNewButton_0501.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0501.setContentAreaFilled(false);//除去默认的背景填充
        jb[5][1]=btnNewButton_0501;
		GridBagConstraints gbc_btnNewButton_0501 = new GridBagConstraints();
		gbc_btnNewButton_0501.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0501.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0501.gridx = 0;
		gbc_btnNewButton_0501.gridy = 6;
		btnNewButton_0501.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(5,1);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0501, gbc_btnNewButton_0501);
		
		JButton btnNewButton_0502 = new JButton("");
		btnNewButton_0502.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0502.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0502.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0502.setBorderPainted(false);//不打印边框  
		btnNewButton_0502.setBorder(null);//除去边框  
		btnNewButton_0502.setText(null);//除去按钮的默认名称  
		btnNewButton_0502.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0502.setContentAreaFilled(false);//除去默认的背景填充
        jb[5][2]=btnNewButton_0502;
		GridBagConstraints gbc_btnNewButton_0502 = new GridBagConstraints();
		gbc_btnNewButton_0502.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0502.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0502.gridx = 1;
		gbc_btnNewButton_0502.gridy = 6;
		btnNewButton_0502.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(5,2);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0502, gbc_btnNewButton_0502);
		
		JButton btnNewButton_0503 = new JButton("");
		btnNewButton_0503.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0503.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0503.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0503.setBorderPainted(false);//不打印边框  
		btnNewButton_0503.setBorder(null);//除去边框  
		btnNewButton_0503.setText(null);//除去按钮的默认名称  
		btnNewButton_0503.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0503.setContentAreaFilled(false);//除去默认的背景填充
        jb[5][3]=btnNewButton_0503;
		GridBagConstraints gbc_btnNewButton_0503 = new GridBagConstraints();
		gbc_btnNewButton_0503.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0503.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0503.gridx = 3;
		gbc_btnNewButton_0503.gridy = 6;
		btnNewButton_0503.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(5,3);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0503, gbc_btnNewButton_0503);
		
		JButton btnNewButton_0504 = new JButton("");
		btnNewButton_0504.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0504.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0504.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0504.setBorderPainted(false);//不打印边框  
		btnNewButton_0504.setBorder(null);//除去边框  
		btnNewButton_0504.setText(null);//除去按钮的默认名称  
		btnNewButton_0504.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0504.setContentAreaFilled(false);//除去默认的背景填充
        jb[5][4]=btnNewButton_0504;
		GridBagConstraints gbc_btnNewButton_0504 = new GridBagConstraints();
		gbc_btnNewButton_0504.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0504.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0504.gridx = 4;
		gbc_btnNewButton_0504.gridy = 6;
		btnNewButton_0504.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(5,4);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0504, gbc_btnNewButton_0504);
		
		JButton btnNewButton_0505 = new JButton("");
		btnNewButton_0505.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0505.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0505.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0505.setBorderPainted(false);//不打印边框  
		btnNewButton_0505.setBorder(null);//除去边框  
		btnNewButton_0505.setText(null);//除去按钮的默认名称  
		btnNewButton_0505.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0505.setContentAreaFilled(false);//除去默认的背景填充
        jb[5][5]=btnNewButton_0505;
		GridBagConstraints gbc_btnNewButton_0505 = new GridBagConstraints();
		gbc_btnNewButton_0505.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0505.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_0505.gridx = 5;
		gbc_btnNewButton_0505.gridy = 6;
		btnNewButton_0505.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(5,5);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0505, gbc_btnNewButton_0505);
		
		JButton btnNewButton_0506 = new JButton("New button");
		btnNewButton_0506.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0506.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0506.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0506.setBorderPainted(false);//不打印边框  
		btnNewButton_0506.setBorder(null);//除去边框  
		btnNewButton_0506.setText(null);//除去按钮的默认名称  
		btnNewButton_0506.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0506.setContentAreaFilled(false);//除去默认的背景填充
        jb[5][6]=btnNewButton_0506;
		GridBagConstraints gbc_btnNewButton_0506 = new GridBagConstraints();
		gbc_btnNewButton_0506.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0506.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_0506.gridx = 6;
		gbc_btnNewButton_0506.gridy = 6;
		btnNewButton_0506.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(5,6);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0506, gbc_btnNewButton_0506);
		
		JButton btnNewButton_0601 = new JButton("");
		btnNewButton_0601.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0601.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0601.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0601.setBorderPainted(false);//不打印边框  
		btnNewButton_0601.setBorder(null);//除去边框  
		btnNewButton_0601.setText(null);//除去按钮的默认名称  
		btnNewButton_0601.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0601.setContentAreaFilled(false);//除去默认的背景填充
        jb[6][1]=btnNewButton_0601;
		GridBagConstraints gbc_btnNewButton_0601 = new GridBagConstraints();
		gbc_btnNewButton_0601.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0601.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_0601.gridx = 0;
		gbc_btnNewButton_0601.gridy = 7;
		btnNewButton_0601.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(6,1);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0601, gbc_btnNewButton_0601);
		
		JButton btnNewButton_0602 = new JButton("");
		btnNewButton_0602.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0602.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0602.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0602.setBorderPainted(false);//不打印边框  
		btnNewButton_0602.setBorder(null);//除去边框  
		btnNewButton_0602.setText(null);//除去按钮的默认名称  
		btnNewButton_0602.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0602.setContentAreaFilled(false);//除去默认的背景填充
        jb[6][2]=btnNewButton_0602;
		GridBagConstraints gbc_btnNewButton_0602 = new GridBagConstraints();
		gbc_btnNewButton_0602.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0602.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_0602.gridx = 1;
		gbc_btnNewButton_0602.gridy = 7;
		btnNewButton_0602.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(6,2);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0602, gbc_btnNewButton_0602);
		
		JButton btnNewButton_0603 = new JButton("");
		btnNewButton_0603.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0603.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0603.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0603.setBorderPainted(false);//不打印边框  
		btnNewButton_0603.setBorder(null);//除去边框  
		btnNewButton_0603.setText(null);//除去按钮的默认名称  
		btnNewButton_0603.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0603.setContentAreaFilled(false);//除去默认的背景填充
        jb[6][3]=btnNewButton_0603;
		GridBagConstraints gbc_btnNewButton_0603 = new GridBagConstraints();
		gbc_btnNewButton_0603.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0603.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_0603.gridx = 3;
		gbc_btnNewButton_0603.gridy = 7;
		btnNewButton_0603.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(6,3);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0603, gbc_btnNewButton_0603);
		
		JButton btnNewButton_0604 = new JButton("");
		btnNewButton_0604.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0604.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0604.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0604.setBorderPainted(false);//不打印边框  
		btnNewButton_0604.setBorder(null);//除去边框  
		btnNewButton_0604.setText(null);//除去按钮的默认名称  
		btnNewButton_0604.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0604.setContentAreaFilled(false);//除去默认的背景填充
        jb[6][4]=btnNewButton_0604;
		GridBagConstraints gbc_btnNewButton_0604 = new GridBagConstraints();
		gbc_btnNewButton_0604.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0604.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_0604.gridx = 4;
		gbc_btnNewButton_0604.gridy = 7;
		btnNewButton_0604.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(6,4);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0604, gbc_btnNewButton_0604);
		
		JButton btnNewButton_0605 = new JButton("");
		btnNewButton_0605.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0605.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0605.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0605.setBorderPainted(false);//不打印边框  
		btnNewButton_0605.setBorder(null);//除去边框  
		btnNewButton_0605.setText(null);//除去按钮的默认名称  
		btnNewButton_0605.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0605.setContentAreaFilled(false);//除去默认的背景填充
        jb[6][5]=btnNewButton_0605;
		GridBagConstraints gbc_btnNewButton_0605 = new GridBagConstraints();
		gbc_btnNewButton_0605.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0605.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_0605.gridx = 5;
		gbc_btnNewButton_0605.gridy = 7;
		btnNewButton_0605.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(6,5);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0605, gbc_btnNewButton_0605);
		
		JButton btnNewButton_0606 = new JButton("");
		btnNewButton_0606.setIcon(new ImageIcon(lightup.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-dark.png")));
		btnNewButton_0606.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
		btnNewButton_0606.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
		btnNewButton_0606.setBorderPainted(false);//不打印边框  
		btnNewButton_0606.setBorder(null);//除去边框  
		btnNewButton_0606.setText(null);//除去按钮的默认名称  
		btnNewButton_0606.setFocusPainted(false);//除去焦点的框  
        btnNewButton_0606.setContentAreaFilled(false);//除去默认的背景填充
        jb[6][6]=btnNewButton_0606;
		GridBagConstraints gbc_btnNewButton_0606 = new GridBagConstraints();
		gbc_btnNewButton_0606.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_0606.gridx = 6;
		gbc_btnNewButton_0606.gridy = 7;
		btnNewButton_0606.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				press(6,6);
			}
		});
		frmLittleGame.getContentPane().add(btnNewButton_0606, gbc_btnNewButton_0606);
	}

}
