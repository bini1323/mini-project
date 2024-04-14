package ui;

import dao.*;
import session.UserSession;
import ui.common.CommonValue;
import vo.ApplyVO;
import vo.EmployeeVO;
import vo.StockVO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ApplyHistorySwing extends JFrame {
    private JPanel parentPanel, historyPaneContainer, buttonPanel;
    private ScrollPane historyPane;
    private JButton acceptButton, refuseButton;
    private ArrayList<String> listItems;
    private DefaultListModel<String> listModel;
    private JList<String> applyList;
    private ApplyDAO applyDAO;
    private EmployeeDAO employeeDAO;
    private StockDAO stockDAO;

    public ApplyHistorySwing() {
        applyDAO = new ApplyDAOImpl();
        employeeDAO = new EmployeeDAOImpl();
        stockDAO = new StockDAOImpl();

        setTitle(CommonValue.TITLE);
        setSize(CommonValue.WIDTH, CommonValue.HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        init();

        setVisible(true);
    }

    private void init() {
        parentPanel = new JPanel(new BorderLayout());
        parentPanel.setBackground(new Color(CommonValue.APPLY_HISTORY_BACKGROUND_COLOR));
        parentPanel.setBorder(BorderFactory.createEmptyBorder(50, 70, 50, 70));
        add(parentPanel);

        historyPaneContainer = new JPanel(new GridLayout(1, 1));
        historyPaneContainer.setBorder(new LineBorder(Color.BLACK, 2));
        parentPanel.add(historyPaneContainer, BorderLayout.CENTER);

        historyPane = new ScrollPane();
        historyPaneContainer.add(historyPane);
        
        listItems = new ArrayList<>();
        listModel = new DefaultListModel<>();
        applyList = new JList<>(listModel);
        applyList.setCellRenderer(new CellRender());
        historyPane.add(applyList);

        for (ApplyVO applyVO : applyDAO.findByApplyConditionOrderByApplyDateASC("W")) {
            StringBuffer sb = new StringBuffer();
            EmployeeVO employeeVO = employeeDAO.selectById(applyVO.getEmployeeId());
            StockVO stockVO = stockDAO.selectById(applyVO.getStockId());
            sb.append("신청ID : ").append(applyVO.getApplyId()).append("\n");
            sb.append("사원명 : ").append(employeeVO.getName()).append("\n");
            sb.append("신청부품 : ").append(stockVO.getPname()).append("\n");
            sb.append("신청수량 : ").append(applyVO.getCnt()).append("\n");
            sb.append("신청날짜 : ").append(applyVO.getCreatedDate());
            listItems.add(sb.toString());
            listModel.addElement(sb.toString());
        }

        acceptButton = new JButton("승인");
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = applyList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String applyId = listModel.get(selectedIndex).split("\n")[0].split(" ")[2];
                    if (acceptApply(Long.parseLong(applyId)) < 2) {
                        return ;
                    }
                    listItems.remove(selectedIndex);
                    listModel.removeElementAt(selectedIndex);
                }

            }

            private int acceptApply(long applyId) { // 권한 여부에 따라 실행, 안 실행, 그리고 재고 수량 갯수 업데이트
                ApplyVO applyVO = applyDAO.selectById(applyId);
                EmployeeVO employeeVO = employeeDAO.selectById(UserSession.getUserId());
                StockVO stockVO = stockDAO.selectById(applyVO.getStockId());
                if (verifyJob(employeeVO.getJob())) {
                    JOptionPane.showMessageDialog(getContentPane(), "승인 권한이 없습니다", "Message", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
                if (verifyStockCount(stockVO.getStockNum(), applyVO.getCnt())) {
                    JOptionPane.showMessageDialog(getContentPane(), "부품 재고가 부족합니다", "Message", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
                int count = 0;
                count += applyDAO.acceptApply(applyVO);
                count += stockDAO.updateStockNum(stockVO, applyVO);
                return count;
            }

            private boolean verifyJob(String job) {
                if (!job.equals("대리")) {
                    return true;
                }
                return false;
            }

            private boolean verifyStockCount(int stockCount, int applyCount) {
                if (stockCount < applyCount) {
                    return true;
                }
                return false;
            }
        });

        refuseButton = new JButton("거부");
        refuseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = applyList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String applyId = listModel.get(selectedIndex).split("\n")[0].split(" ")[2];
                    if (refuseApply(Long.parseLong(applyId)) < 1) {
                        return ;
                    }
                    listItems.remove(selectedIndex);
                    listModel.removeElementAt(selectedIndex);
                }
            }

            private int refuseApply(long applyId) {
                ApplyVO applyVO = applyDAO.selectById(applyId);
                EmployeeVO employeeVO = employeeDAO.selectById(UserSession.getUserId());
                if (verifyJob(employeeVO.getJob())) {
                    JOptionPane.showMessageDialog(getContentPane(), "거부 권한이 없습니다", "Message", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
                int count = applyDAO.refuseApply(applyVO);
                return count;
            }

            private boolean verifyJob(String job) {
                if (!job.equals("대리")) {
                    return true;
                }
                return false;
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(CommonValue.APPLY_HISTORY_BACKGROUND_COLOR));
        buttonPanel.add(acceptButton);
        buttonPanel.add(refuseButton);
        parentPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private class CellRender extends JPanel implements ListCellRenderer<String> {
        private JLabel label1, label2, label3, label4;

        public CellRender() {
            setLayout(new GridLayout(4, 1));

            label1 = new JLabel();
            label1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            label2 = new JLabel();
            label2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            label3 = new JLabel();
            label3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            label4 = new JLabel();
            label4.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            add(label1);
            add(label2);
            add(label3);
            add(label4);
        }

        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            String[] values = value.split("\n");
            label1.setText(values[1]);
            label2.setText(values[2]);
            label3.setText(values[3]);
            label4.setText(values[4]);
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }
}
