package ui;

import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import vo.EmployeeVO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;


public class ResisterSwing extends JFrame implements ActionListener {
	private JLabel la2, la3, la5, la6, la7,la8, la9;
	private JTextField tf2, tf3, tf5, tf6, tf7, tf9;
	private JPasswordField tf8;
	private JButton bt2;
	private JPanel pa1, pa2;
	private JButton btClose;
	private EmployeeDAO employeeDAO;

	public ResisterSwing() throws HeadlessException {
		employeeDAO = new EmployeeDAOImpl();

		this.setTitle("Swing");
		this.setLayout(new BorderLayout());

		pa1 = new JPanel();
		pa1.setBackground(Color.gray);
		pa2 = new JPanel();
		pa2.setBackground(Color.gray);

		pa1.setLayout(new GridLayout(7,2));

		la2 = new JLabel("이름", JLabel.CENTER);
		la3 = new JLabel("부서명", JLabel.CENTER);
		la5 = new JLabel("생년월일", JLabel.CENTER);
		la6 = new JLabel("주소", JLabel.CENTER);
		la7 = new JLabel("이메일", JLabel.CENTER);
		la8 = new JLabel("패스워드", JLabel.CENTER);
		la9 = new JLabel("전화번호", JLabel.CENTER);

		tf2 = new JTextField(10);
		tf3 = new JTextField(10);
		tf5 = new JTextField(10);
		tf6 = new JTextField(10);
		tf7 = new JTextField(10);
		tf8 = new JPasswordField(10);
		tf9 = new JTextField(10);

		pa1.add(la2);
		pa1.add(tf2);
		pa1.add(la3);
		pa1.add(tf3);
		pa1.add(la5);
		pa1.add(tf5);
		pa1.add(la6);
		pa1.add(tf6);
		pa1.add(la7);
		pa1.add(tf7);
		pa1.add(la8);
		pa1.add(tf8);
		pa1.add(la9);
		pa1.add(tf9);

		pa2.setLayout(new FlowLayout(FlowLayout.CENTER));

		bt2 = new JButton("저장");
		btClose = new JButton("취소");

		pa2.add(bt2);
		pa2.add(btClose);

		bt2.addActionListener(this);
		btClose.addActionListener(this);

		//JPanel 두장 JFrame 붙이기
		this.getContentPane().add(pa1, BorderLayout.CENTER);
		this.getContentPane().add(pa2, BorderLayout.SOUTH);

		this.setSize(900, 600);
		this.setLocation(200, 200);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String btnCmd = String.valueOf(e.getActionCommand());

		if ("저장".equals(btnCmd)) {
			String name	= tf2.getText();
			String dep 	= tf3.getText();
			String birthday	= tf5.getText();
			String address = tf6.getText();
			String email = tf7.getText();
			String password = tf8.getText();
			String tel = tf9.getText();

			// 이메일 중복 처리
			if (employeeDAO.isExistsByEmail(email)) {
				JOptionPane.showMessageDialog(this, "이메일이 중복 되었습니다", "Message",JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			EmployeeVO lastData = employeeDAO.selectLast();
			long employeeId = Long.parseLong(lastData.getEmployeeId()) + 1;

			EmployeeVO evo = new EmployeeVO(String.valueOf(employeeId), name, dep, null, birthday,
					address, email, password, tel, null, null);
			int nCnt = employeeDAO.insert(evo);
			System.out.println(nCnt);

			JOptionPane.showMessageDialog(this, "회원가입이 완료 되었습니다.", "Message",JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
		if("취소".equals(btnCmd)) {
			dispose();
		}
	}

}

