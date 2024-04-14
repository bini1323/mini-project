package ui;

import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import session.UserSession;
import vo.EmployeeVO;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class LoginSwing extends JFrame implements ActionListener {
    private EmployeeDAO employeeDAO;

    JPanel jp;
    JLabel[] jlM;
    JTextField jtM;
    JPasswordField jpfM;
    JButton[] jbM;
    JButton jbMem;

    //생성자
    public LoginSwing() {
        employeeDAO = new EmployeeDAOImpl();
        this.setTitle("회원 로그인");

        this.getContentPane().setLayout(null);
        jp = new JPanel();
        jp.setBackground(Color.GRAY);
        jp.setBorder(new EtchedBorder());
        jp.setBounds(0, 0, 310, 280);

        jp.setLayout(null);

        JPanel jpM = new JPanel();
        jpM.setLayout(null);
        jpM.setBounds(10, 30, 275, 180);
        jpM.setBackground(Color.GRAY);
        jpM.setBorder(new TitledBorder("로그인"));
        jp.add(jpM);

        jlM = new JLabel[2];
        jtM = new JTextField();
        jpfM = new JPasswordField();
        jbM = new JButton[2];

        for (int j = 0; j < jlM.length; j++) {

            jlM[j] = new JLabel();
            jlM[j].setHorizontalAlignment(SwingConstants.CENTER);
            jbM[j] = new JButton();
            jbM[j].addActionListener(this);
            jpM.add(jlM[j]);
            jpM.add(jbM[j]);
        }
        jlM[0].setText("이메일");
        jlM[1].setText("패스워드");

        jtM = new JTextField(10);
        jpfM = new JPasswordField(10);
        jpM.add(jtM);
        jpM.add(jpfM);

        jbM[0].setText("로그인");
        jbM[1].setText("로그아웃");

        jbMem = new JButton();
        jbMem.addActionListener(this);
        jbMem.setText("회원가입");
        jpM.add(jbMem);

        jlM[0].setBounds(10, 20, 100, 30);
        jtM.setBounds(110, 20, 150, 30);

        jlM[1].setBounds(10, 55, 100, 30);
        jpfM.setBounds(110, 55, 150, 30);

        jbM[0].setBounds(40, 92, 100, 30);
        jbM[1].setBounds(150, 92, 100, 30);
        jbMem.setBounds(40, 130, 210, 30);

        this.getContentPane().add(jp);

        //화면중앙에 배치하기
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        this.setLocation(screenSize.width / 2 - 150, screenSize.height / 2 - 100);

        //this.setLocation(100, 100);
        this.setSize(310, 280);
        this.setResizable(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }

    //로그인 체크
    public boolean loginCheck(String email, String password) {
        EmployeeVO evo = new EmployeeVO(email, password);
        EmployeeVO result = employeeDAO.loginCheck(evo);

        if (result != null) {
            System.out.println("SUCCESS");
            UserSession.setLoginUser(result);
            return true;
        }
        System.out.println("FAIL");
        return false;
    }

    //로그아웃 체크
    public void logoutCheck() {
        int conFirm = JOptionPane.showConfirmDialog(this, "로그아웃 하시겠습니까 ?");
        if (conFirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    //로그인 JTextField, JPasswordField 초기화
    public void jtMSetText() {
        jtM.setText("");
        jpfM.setText("");
    }

    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent e) {

        JButton jbnt = (JButton) e.getSource();
        String loginCmd = jbnt.getText();

        if ("로그인".equals(loginCmd)) {
            String email = jtM.getText();
            String password = jpfM.getText();
            if (loginCheck(email, password)) {
                this.dispose();
                new MainSwing();
            }
        }
        if ("로그아웃".equals(loginCmd)) {
            logoutCheck();
        }
        if ("회원가입".equals(loginCmd)) {
            new ResisterSwing();
        }
    }

}

