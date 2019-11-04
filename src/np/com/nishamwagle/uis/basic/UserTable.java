package np.com.nishamwagle.uis.basic;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import np.com.nishamwagle.uis.dao.UserDao;
import np.com.nishamwagle.uis.dao.UserDaoImpl;
import np.com.nishamwagle.uis.model.User;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class UserTable extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	UserDao userDao = new UserDaoImpl();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserTable frame = new UserTable();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserTable() {
		setTitle("User Data");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 350);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setBounds(10, 11, 554, 229);
		String [] column = {"ID", "Username", "Password", "Email", "Gender", "Hobbies", "Nationality", "DOB"};
		DefaultTableModel tableModel = new DefaultTableModel(column, 0);
		table.setModel(tableModel);
		
		loadDataInTable();
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 594, 229);
		contentPane.add(scrollPane);
		
		JButton newBtn = new JButton("New");
		newBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserForm userForm = new UserForm();
				userForm.setVisible(true);
				dispose();
			}
		});
		newBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		newBtn.setBounds(10, 285, 114, 27);
		contentPane.add(newBtn);
		
		JButton editBtn = new JButton("Edit");
		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
				int selectedRow = table.getSelectedRow();
				if(selectedRow != -1) {
					Object id = tableModel.getValueAt(selectedRow, 0);
					Object username = tableModel.getValueAt(selectedRow, 1);
					Object password = tableModel.getValueAt(selectedRow, 2);
					Object email = tableModel.getValueAt(selectedRow, 3);
					Object gender = tableModel.getValueAt(selectedRow, 4);
					Object hobbies = tableModel.getValueAt(selectedRow, 5);
					Object nationality = tableModel.getValueAt(selectedRow, 6);
					Object dob = tableModel.getValueAt(selectedRow, 7);

					UserForm userForm = new UserForm();
					userForm.idLbl.setText(id.toString());
					userForm.unameFld.setText(username.toString());;
					userForm.pwFld.setText(password.toString());;
					userForm.emailFld.setText(email.toString());;
					if (gender.equals("Male")) {
						userForm.maleRadioBtn.setSelected(true);
					}else {
						userForm.femaleRadioBtn.setSelected(true);
					}
					
					String [] hobby = hobbies.toString().split(",");
					for (String h: hobby) {
						if (h.equals("Reading")) {
							userForm.readChkBox.setSelected(true);
						}
						if (h.equals("Coding")) {
							userForm.codeChkBox.setSelected(true);
						}
					}
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					Date date;
					try {
						date = sf.parse(dob.toString());
						userForm.dateChooser.setDate(date);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					userForm.nationalityCombo.setSelectedItem(nationality);

					userForm.setVisible(true);
					dispose();
				}
			}
		});
		editBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		editBtn.setBounds(134, 285, 114, 27);
		contentPane.add(editBtn);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
				int selectedRow = table.getSelectedRow();
				if(selectedRow != -1) {	
					int deleteConfirm = JOptionPane.showConfirmDialog(new UserTable(), "Are you sure you want to delete this data?", "Delete!!!", JOptionPane.YES_NO_OPTION);
					if (deleteConfirm == 0) {						
						Object id = tableModel.getValueAt(selectedRow, 0);
						userDao.deleteUserInfo(Integer.parseInt(id.toString()));
						loadDataInTable();
					}
				}else {
					JOptionPane.showMessageDialog(new UserTable(), "Please Select any row to delete!!!", "Select One!!!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		deleteBtn.setBounds(490, 285, 114, 27);
		contentPane.add(deleteBtn);
	}
	
	public void loadDataInTable() {
		DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
		tableModel.setRowCount(0);
		List<User> userlist = userDao.getAllUserInfo();
		for (User u : userlist) {
			tableModel.addRow(new Object[] {
					u.getId(),
					u.getUsername(),
					u.getPassword(),
					u.getEmail(),
					u.getGender(),
					u.getHobbies(),
					u.getNationality(),
					u.getDob()
			});
		}
	}
}
