package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					// frame.pack(); suitable for system
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JPanel contentPane;
	private DefaultMutableTreeNode root;
	private JTree tree;
	private JButton buttonXemThongTin;
	private JTable tablePhong;
	private DefaultTableModel tableModePhong;
	private DefaultTableModel tableModeGiuong;
	private JTable tableGiuong;
	private JButton buttonThietLapGia;
	private JButton buttonHieuQuaKhaiThac;
	private JButton buttonMoRongGiuong;
	private JButton buttonCatGiuong;

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("Hệ thống Quản lý giường bệnh");
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// North
		JPanel pnNorth = new JPanel();
		pnNorth.setPreferredSize(new Dimension(1000, 60));

		Font font = new Font("Times New Roman", Font.BOLD, 35);
		JLabel lblTitle = new JLabel("Quản lý giường bệnh bệnh viện");
		lblTitle.setFont(font);
		lblTitle.setForeground(Color.DARK_GRAY);

		pnNorth.add(lblTitle);

		contentPane.add(pnNorth, BorderLayout.NORTH);

		// West
		JPanel pnWest = new JPanel(new BorderLayout());
		pnWest.setPreferredSize(new Dimension(300, 600));

		// North-West
		JPanel pnWestNorth = new JPanel(new BorderLayout());
		pnWestNorth.setPreferredSize(new Dimension(300, 540));

		// Data tree
		root = new DefaultMutableTreeNode("Danh sách Khoa");
		DefaultMutableTreeNode khoa1 = new DefaultMutableTreeNode("Khoa 1");
		DefaultMutableTreeNode khoa2 = new DefaultMutableTreeNode("Khoa 2");
		DefaultMutableTreeNode p1 = new DefaultMutableTreeNode("Phòng 1");
		DefaultMutableTreeNode p2 = new DefaultMutableTreeNode("Phòng 2");
		DefaultMutableTreeNode p3 = new DefaultMutableTreeNode("Phòng 3");
		DefaultMutableTreeNode p4 = new DefaultMutableTreeNode("Phòng 4");
		DefaultMutableTreeNode p5 = new DefaultMutableTreeNode("Phòng 5");
		khoa1.add(p1);
		khoa1.add(p2);
		khoa1.add(p3);
		khoa2.add(p4);
		khoa2.add(p5);
		root.add(khoa1);
		root.add(khoa2);

		// Tree
		tree = new JTree(root);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		JScrollPane jsTree = new JScrollPane(tree);
		
		pnWestNorth.add(jsTree);

		pnWest.add(pnWestNorth, BorderLayout.NORTH);

		// South-West
		JPanel pnWestSouth = new JPanel();
		pnWestSouth.setPreferredSize(new Dimension(300, 40));
		pnWestSouth.add(buttonXemThongTin = new JButton("Xem Thông Tin"));

		pnWest.add(pnWestSouth, BorderLayout.SOUTH);

		contentPane.add(pnWest, BorderLayout.WEST);

		// East
		JPanel pnEast = new JPanel(new BorderLayout());
		pnEast.setPreferredSize(new Dimension(670, 600));

		// North-East
		JPanel pnEastNorth = new JPanel(new BorderLayout());
		pnEastNorth.setPreferredSize(new Dimension(670, 540));

		// Table Phong
		String[] colNamePhong = { "Mã Phòng", "Giá Giường", "Giường Trống", "Giường Mở Rộng", "Tổng Số Giường"};
		tableModePhong = new DefaultTableModel(colNamePhong, 1);
		tablePhong = new JTable(tableModePhong);
		JScrollPane jsTablePhong = new JScrollPane(tablePhong);
		
		//Table Giuong
		String[] colNameGiuong = { "Mã Giường", "Giá Giường", "Loại", "Trạng Thái"};
		tableModeGiuong = new DefaultTableModel(colNameGiuong, 1);
		tableGiuong = new JTable(tableModeGiuong);
		JScrollPane jsTableGiuong= new JScrollPane(tableGiuong);

		//defauttable
		pnEastNorth.add(jsTablePhong);
		
		pnEast.add(pnEastNorth, BorderLayout.NORTH);

		// South-East
		JPanel pnEastSouth = new JPanel();
		//pnEastSouth.setLayout(new FlowLayout());
		pnEastSouth.setPreferredSize(new Dimension(670, 40));
		
		pnEastSouth.add(buttonThietLapGia = new JButton("Thiết Lập Giá"));
		pnEastSouth.add(buttonHieuQuaKhaiThac = new JButton("Hiệu Quả Khai Thác"));
		pnEastSouth.add(buttonMoRongGiuong= new JButton("Mở Rộng Giường"));
		pnEastSouth.add(buttonCatGiuong= new JButton("Cất Giường"));
		
		pnEast.add(pnEastSouth, BorderLayout.SOUTH);
		
		contentPane.add(pnEast, BorderLayout.EAST);
		
		buttonXemThongTin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		buttonThietLapGia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		buttonHieuQuaKhaiThac.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		buttonMoRongGiuong.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		buttonCatGiuong.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
