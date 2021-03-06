package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import database.DbUtils;
import entities.Bed;
import entities.Department;
import entities.Hospital;
import entities.Room;

public class GUIDieuChuyenBenhNhan extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
	private JButton btnAddPatient;
	private DefaultMutableTreeNode root;
	private JTree tree;
	private ArrayList<Department> listDepartment;
	private ArrayList<Room> listRoom;
	private ArrayList<Bed> listBed;
	private String bedName;
	private String roomID;
	private String departmentName;
	private String bedID;
	private TableModel tableModel;
	private DefaultTreeModel treeModel;
	private String status;

	private int row;

	/**
	 * Create the frame.
	 */
	public GUIDieuChuyenBenhNhan() {
		setTitle("HOSPITAL BED MANAGEMENT");
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// ------------- North ---------------
		JPanel pnNorth = new JPanel();
		pnNorth.setPreferredSize(new Dimension(1000, 60));
		Font font = new Font("Times New Roman", Font.BOLD, 35);
		JLabel lblTitle = new JLabel("MANAGEMENT PATIENT"); // title
		lblTitle.setFont(font);
		lblTitle.setForeground(Color.RED);
		pnNorth.add(lblTitle);
		contentPane.add(pnNorth, BorderLayout.NORTH);

		//------------- West ---------------
		JPanel pnWest = new JPanel(new BorderLayout());
		pnWest.setPreferredSize(new Dimension(300, 600));

		// North-West
		JPanel pnWestNorth = new JPanel(new BorderLayout());
		pnWestNorth.setPreferredSize(new Dimension(300, 540));

		// Data tree
		root = new DefaultMutableTreeNode("Department Listing");
		treeModel = new DefaultTreeModel(root);
		treeModel.addTreeModelListener(new MyTreeModelListener());

		// tree
		tree = new JTree(treeModel);
		tree.setEditable(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);

		JScrollPane jsTree = new JScrollPane(tree);
		pnWestNorth.add(jsTree);
		pnWest.add(pnWestNorth, BorderLayout.NORTH);
		contentPane.add(pnWest, BorderLayout.WEST);

		//-------------- East --------------
		JPanel pnEast = new JPanel(new BorderLayout());
		pnEast.setPreferredSize(new Dimension(670, 600));

		// North-East
		JPanel pnEastNorth = new JPanel(new BorderLayout());
		pnEastNorth.setPreferredSize(new Dimension(670, 540));
		pnEastNorth.add(new JScrollPane(table = new JTable()));
		pnEast.add(pnEastNorth, BorderLayout.NORTH);

		// South-East
		JPanel pnEastSouth = new JPanel();
		pnEastSouth.setPreferredSize(new Dimension(670, 40));
		pnEastSouth.add(btnAddPatient = new JButton("Add Patient"));
		pnEast.add(pnEastSouth, BorderLayout.SOUTH);
		contentPane.add(pnEast, BorderLayout.EAST);

		// event
		updateTree();

		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				Object nodeInfo = node.getUserObject();
				if(nodeInfo instanceof Department){
					Department department = (Department) nodeInfo;
					System.out.println(department.getDepartmentID());
				} else if(nodeInfo instanceof Room){
					Room room = (Room) nodeInfo;
					listBed = room.getListBed(room.getRoomID());
					System.out.println(room.getRoomID());
					tableModel = new TableModel(listBed);
					table.setModel(tableModel);
				}
			}
		});
		
		btnAddPatient.addActionListener(this);
		btnAddPatient.setEnabled(false);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getBedIDAndRoomID();
			}
		});
		
	}

	private void updateTree() {
		listDepartment = new Hospital().getListDepartment();
		for(Department department : listDepartment){
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(department);
			treeModel.insertNodeInto(child,root, root.getChildCount());
			listRoom =  department.getListRoom();
			for(Room room : listRoom){
				DefaultMutableTreeNode child1 = new DefaultMutableTreeNode(room);
				treeModel.insertNodeInto(child1, child, child.getChildCount());
			}	
		}
	}

	public void getBedIDAndRoomID(){
		row = table.getSelectedRow();
		if(row >= 0){
			bedID = (String) table.getValueAt(row, 0);
			bedName = (String) table.getValueAt(row, 1);
			status = (String) table.getValueAt(row, 3);
			roomID = (String) table.getValueAt(row, 4);
			if(status.equals("Empty")){
				btnAddPatient.setEnabled(true);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnAddPatient)){
			String roomName = "";
			Room r = new Room();
			departmentName = r.findDepartment(roomID);
			Department d = new Department();
			for(Room r1 : d.getListRoom1()){
				if(r1.getRoomID().equals(roomID)){
					roomName = r1.getRoomName();
					break;
				}
			}
			GUIAddPatient gui = new GUIAddPatient(bedName, roomName, departmentName, bedID, roomID, row);
			gui.setVisible(true);
			
		}
	}
	
	public void setStatus(String roomid, int roww) {
//		JOptionPane.showMessageDialog(null, "Information Patient was updated", "Notification", JOptionPane.INFORMATION_MESSAGE);
		Room room = new Room();
		ArrayList<Bed> lstBed = new ArrayList<Bed>();
		lstBed = room.getListBed(roomid);
		
		System.out.println("received roomid: " + roomid);
		System.out.println(lstBed);
		
		TableModel model = new TableModel(lstBed);
		System.out.println(model);
		System.out.println("roww: " + roww);
		
		
//		try {
//			String sql = "select * from ...";
//			pst = con.prepareStatement(sql);
//			rs = pst.executeQuery();
//			table.setModel(DbUtils.resultSetToTableModel(rs));
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, e);
//		}
	}

	public static void main(String[] args) {
		new GUIDieuChuyenBenhNhan().setVisible(true);
	}
	

}
