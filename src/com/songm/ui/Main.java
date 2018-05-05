package com.songm.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.songm.utils.Encoder;

/**
 * 主界面及监听
 * @author SongM
 * @date 2017年10月21日 下午4:21:15
 */
public class Main extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8976463897542486463L;
	private JTabbedPane tabPane = new JTabbedPane(); // 选项卡布局
	
	private JButton button01 = new JButton("..."); // 需要转换的文件选择按钮
	private JTextField text01 = new JTextField(); // 需要转换的文件路径  
	private JComboBox<String> comboBox0 = new JComboBox<String>(); // 转换后的编码格式下拉列表
	private JButton button03 = new JButton("立即转换"); // 转换按钮
	public static JScrollPane scroll = null;
    public static JTextArea jTextArea0 = new JTextArea(); // 显示转换进度
	
	private JButton button1 = new JButton("..."); // 需要转换的文件夹选择按钮
	private JButton button2 = new JButton("..."); // 转后储存的文件夹选择按钮
	private JTextField text1 = new JTextField(); // 需要转换的文件夹路径  
	private JTextField text2 = new JTextField(); // 转后储存的文件夹路径  
	private JComboBox<String> comboBox = new JComboBox<String>(); // 转换后的编码格式下拉列表
	private JButton button3 = new JButton("立即转换"); // 转换按钮
    public static JTextArea jTextArea = new JTextArea(); // 显示转换进度
    private JFileChooser jfc = new JFileChooser(); // 文件选择器
    
    public static StringBuffer msg = new StringBuffer(); // 进度

	public Main() {
		setTitle("文件编码转换工具(SongM)"); // 标题
		setIconImage(new ImageIcon("./icon/logo.jpg").getImage());
		setSize(340, 325); // 窗口大小
		setLocationRelativeTo(null); // 窗口居中
		setContentPane(tabPane); // 设置布局
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 使能关闭窗口，结束程序
		setResizable(false); // 不能改变窗口大小
		setVisible(true); // 显示
		
		// 单个文件UI
		Container container0 = new Container();
		JLabel label01 = new JLabel("需要转换的文件");
		JLabel label03 = new JLabel("转换后的编码格式");
		JScrollPane scroll0 = new JScrollPane(jTextArea0); // 滚动条
	    jTextArea0.setEditable(false); // 设置不可写
	    comboBox0.addItem("UTF-8");
	    comboBox0.addItem("ASCII");
	    comboBox0.addItem("ISO-8859-1");
	    comboBox0.addItem("GB2312");
	    comboBox0.addItem("GBK");
	    comboBox0.addItem("UTF-16");
	    comboBox0.addItem("GB18030");
	    // 定义控件位置
	    label01.setBounds(10, 10, 120, 20);  
        text01.setBounds(125, 10, 120, 20);  
        button01.setBounds(260, 10, 50, 20);  
        label03.setBounds(10, 35, 120, 20);
        comboBox0.setBounds(125, 35, 120, 20);
        button03.setBounds(10, 60, 120, 20);
        scroll0.setBounds(10, 85, 310, 175);
        // 控件添加点击监听事件
        button01.addActionListener(this);
        button03.addActionListener(this);
        // 添加控件
 		container0.add(label01);  
 		container0.add(text01);  
 		container0.add(button01);  
 		container0.add(label03);
 		container0.add(comboBox0);
 		container0.add(button03);
 		container0.add(scroll0);
		
		// 批量UI
		Container container = new Container();
		JLabel label1 = new JLabel("需要转换的文件夹");
		JLabel label2 = new JLabel("转后储存的文件夹");
		JLabel label3 = new JLabel("转换后的编码格式");
		scroll = new JScrollPane(jTextArea); // 滚动条
	    jTextArea.setEditable(false); // 设置不可写
	    comboBox.addItem("UTF-8");
	    comboBox.addItem("ASCII");
	    comboBox.addItem("ISO-8859-1");
	    comboBox.addItem("GB2312");
	    comboBox.addItem("GBK");
	    comboBox.addItem("UTF-16");
	    comboBox.addItem("GB18030");
	    // 定义控件位置
	    label1.setBounds(10, 10, 120, 20);  
        text1.setBounds(125, 10, 120, 20);  
        button1.setBounds(260, 10, 50, 20);  
        label2.setBounds(10, 35, 120, 20);  
        text2.setBounds(125, 35, 120, 20);  
        button2.setBounds(260, 35, 50, 20); 
        label3.setBounds(10, 60, 120, 20);
        comboBox.setBounds(125, 60, 120, 20);
        button3.setBounds(10, 85, 120, 20);
        scroll.setBounds(10, 110, 310, 150);
        // 控件添加点击监听事件
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        // 添加控件
		container.add(label1);  
		container.add(text1);  
		container.add(button1);  
		container.add(label2);  
		container.add(text2);  
		container.add(button2);
		container.add(label3);
		container.add(comboBox);
		container.add(button3);
		container.add(scroll);
		tabPane.add("单个操作", container0); // 添加布局
		tabPane.add("批量操作", container); // 添加布局
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// 选择需要转换的文件
		if(e.getSource().equals(button01)) {
			jfc.setFileSelectionMode(0);// 设定只能选择到文件  
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句  
            if (state == 1) {  
                return;// 撤销则返回  
            } else {  
                File f = jfc.getSelectedFile();// f为选择到的文件  
                text01.setText(f.getAbsolutePath());  
            }
		}
		// 立即转换
		if(e.getSource().equals(button03)) {
			// 先清空进度内容
			jTextArea0.setText(null);
			// 获取需要转换的文件
			String src = text01.getText();
			// 获取选择的编码
			String encode = comboBox0.getSelectedItem().toString();
			// 转换
			if(!src.equals("")) {
				Encoder.convertFile(src, encode);
				jTextArea0.setText(src + "  转换成功\n\n完成！！！");
			}
		}
		
		// 选择需要转换的文件夹
		if(e.getSource().equals(button1)) {
			jfc.setFileSelectionMode(1); // 设定只能选择到文件夹  
            int state = jfc.showOpenDialog(null); // 此句是打开文件选择器界面的触发语句  
            if (state == 1) {  
                return;  
            } else {  
                File f = jfc.getSelectedFile(); // f为选择到的目录  
                text1.setText(f.getAbsolutePath());  
            }
		}
		
		// 选择转换后储存的文件夹
		if(e.getSource().equals(button2)) {
			jfc.setFileSelectionMode(1); // 设定只能选择到文件夹  
            int state = jfc.showOpenDialog(null); // 此句是打开文件选择器界面的触发语句  
            if (state == 1) {  
                return;  
            } else {  
                File f = jfc.getSelectedFile(); // f为选择到的目录  
                text2.setText(f.getAbsolutePath());  
            }
		}
		
		// 立即转换
		if(e.getSource().equals(button3)) {
			// 先清空进度内容
			jTextArea.setText(null);
			// 获取需要转换的文件夹
			String src = text1.getText();
			// 获取储存的文件夹
			String path = text2.getText();
			// 获取选择的编码
			String encode = comboBox.getSelectedItem().toString();
			// 转换
			if(!src.equals("") && !path.equals("")) {
				Encoder.getFileCount(src); //获取文件总个数
				new Thread(new Runnable() {
					@Override
					public void run() {
						Encoder.convertFolder(src, path, encode);
					}
				}).start();
				//msg.append("\n完成！！！");
				//jTextArea.setText(msg.toString());
			}
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}
