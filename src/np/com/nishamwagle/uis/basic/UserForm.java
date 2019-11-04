package np.com.nishamwagle.uis.basic;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import com.toedter.calendar.JDateChooser;

import np.com.nishamwagle.uis.dao.UserDao;
import np.com.nishamwagle.uis.dao.UserDaoImpl;
import np.com.nishamwagle.uis.model.User;

import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;

public class UserForm extends JFrame {

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JTextField unameFld;
	public JPasswordField pwFld;
	public JTextField emailFld;
	public JRadioButton maleRadioBtn;
	public JRadioButton femaleRadioBtn;
	public JCheckBox codeChkBox;
	public JCheckBox readChkBox;
	public JComboBox<String> nationalityCombo;
	public JDateChooser dateChooser;
	public JLabel idLbl;



	/**
	 * Create the frame.
	 */
	public UserForm() {
		setResizable(false);
		setTitle("User Form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 400, 500);
		setSize(350, 455);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUsername.setBounds(10, 83, 86, 29);
		contentPane.add(lblUsername);

		unameFld = new JTextField();
		unameFld.setBounds(106, 83, 230, 31);
		contentPane.add(unameFld);
		unameFld.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword.setBounds(10, 123, 86, 29);
		contentPane.add(lblPassword);
		
		pwFld = new JPasswordField();
		pwFld.setBounds(106, 123, 230, 31);
		contentPane.add(pwFld);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEmail.setBounds(10, 163, 86, 29);
		contentPane.add(lblEmail);

		emailFld = new JTextField();
		emailFld.setColumns(10);
		emailFld.setBounds(106, 163, 230, 31);
		contentPane.add(emailFld);

		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGender.setBounds(10, 203, 86, 29);
		contentPane.add(lblGender);

		maleRadioBtn = new JRadioButton("Male");
		maleRadioBtn.setSelected(true);
		maleRadioBtn.setBounds(106, 201, 109, 31);
		contentPane.add(maleRadioBtn);

		femaleRadioBtn = new JRadioButton("Female");
		femaleRadioBtn.setBounds(227, 201, 109, 31);
		contentPane.add(femaleRadioBtn);

		ButtonGroup genderGroup = new ButtonGroup();
		genderGroup.add(maleRadioBtn);
		genderGroup.add(femaleRadioBtn);

		JLabel lblHobbies = new JLabel("Hobbies");
		lblHobbies.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblHobbies.setBounds(10, 243, 86, 29);
		contentPane.add(lblHobbies);

		codeChkBox = new JCheckBox("Coding");
		codeChkBox.setBounds(106, 244, 97, 31);
		contentPane.add(codeChkBox);

		readChkBox = new JCheckBox("Reading");
		readChkBox.setBounds(229, 244, 107, 31);
		contentPane.add(readChkBox);

		JLabel lblNationality = new JLabel("Date of Birth");
		lblNationality.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNationality.setBounds(10, 323, 97, 29);
		contentPane.add(lblNationality);

		nationalityCombo = new JComboBox<>();
		nationalityCombo.setModel(new DefaultComboBoxModel<>(new String[] { "Nepali", "American" }));
		nationalityCombo.setBounds(106, 283, 230, 29);
		contentPane.add(nationalityCombo);

		JLabel label = new JLabel("Nationality");
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		label.setBounds(10, 283, 86, 29);
		contentPane.add(label);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(112, 323, 224, 29);
		contentPane.add(dateChooser);

		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				UserDao userDao = new UserDaoImpl();
				User user = new User();
				user.setUsername(unameFld.getText());
				user.setPassword(new String(pwFld.getPassword()));
				user.setEmail(emailFld.getText());
				if (maleRadioBtn.isSelected()) {
					user.setGender(maleRadioBtn.getText());
				} else {
					user.setGender(femaleRadioBtn.getText());
				}
				
				String hobbies = "";
				if (readChkBox.isSelected()) {
					hobbies = readChkBox.getText() + hobbies + ",";
				}
				if (codeChkBox.isSelected()) {
					hobbies = codeChkBox.getText() + hobbies + ",";
				}
				user.setHobbies(hobbies);
				user.setNationality((String) nationalityCombo.getSelectedItem());
				user.setDob(dateChooser.getDate());

				String userId = idLbl.getText();
				
				if(userId == null || userId.isEmpty()) {
					
					int saved = userDao.saveUser(user);
					if(saved >=1 ) {
						JOptionPane.showMessageDialog(new UserForm(), "User info is saved successfully!!!", "Saved", JOptionPane.PLAIN_MESSAGE);
						UserTable userTable = new UserTable();
						userTable.setVisible(true);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(new UserForm(), "Oops!!! Error occurred in db!!!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					user.setId(Integer.parseInt(userId));
					int updated = userDao.editUser(user);
					if(updated >=1 ) {
						JOptionPane.showMessageDialog(new UserForm(), "User info is updated successfully!!!", "Updated", JOptionPane.PLAIN_MESSAGE);
						UserTable userTable = new UserTable();
						userTable.setVisible(true);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(new UserForm(), "Oops!!! Error occurred in db!!!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		submitBtn.setForeground(SystemColor.desktop);
		submitBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		submitBtn.setBounds(227, 384, 109, 29);
		contentPane.add(submitBtn);
		
		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserTable userTable = new UserTable();
				userTable.setVisible(true);
				dispose();
			}
		});
		button.setForeground(Color.BLACK);
		button.setFont(new Font("Tahoma", Font.BOLD, 15));
		button.setBounds(10, 384, 109, 29);
		contentPane.add(button);
		
	
		idLbl = new JLabel("");
		idLbl.setBounds(10, 11, 46, 14);
		contentPane.add(idLbl);
	}
}
