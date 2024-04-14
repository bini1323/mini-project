package ui;

import dao.ApplyDAO;
import dao.ApplyDAOImpl;
import dao.StockDAO;
import dao.StockDAOImpl;
import session.UserSession;
import ui.common.CommonValue;
import vo.ApplyVO;
import vo.StockVO;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.*;

public class ApplySwing extends JFrame{
    private ApplyDAO applyDAO;
    private StockDAO stockDAO;
    private JPanel inpuPanel, childPanel1, childPanel2, childPanel3;
    private JTextField partNameField, cntField;
    private JButton partApplyButton;
    private JTextArea outputArea;

    public ApplySwing() {
        applyDAO = new ApplyDAOImpl();
        stockDAO = new StockDAOImpl();

        setTitle(CommonValue.TITLE);
        setSize(CommonValue.WIDTH, CommonValue.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createInputPanel();
        createScrollPanel();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void createInputPanel() {
        inpuPanel = new JPanel(new GridLayout(4, 1));
        inpuPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        childPanel1 = new JPanel(new GridLayout(1, 2));
        childPanel1.add(new JLabel("부품 이름:"));
        partNameField = new JTextField();
        childPanel1.add(partNameField);
        inpuPanel.add(childPanel1);

        childPanel2 = new JPanel(new GridLayout(1, 2));
        childPanel2.add(new JLabel("수량:"));
        cntField = new JTextField();
        childPanel2.add(cntField);
        inpuPanel.add(childPanel2);

        JPanel emptyPanel = new JPanel();
        emptyPanel.setVisible(false);
        inpuPanel.add(emptyPanel);

        childPanel3 = new JPanel(new GridLayout(1, 1));
        partApplyButton = new JButton("부품 신청");
        partApplyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertApply();
            }
        });
        childPanel3.add(partApplyButton);
        childPanel3.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
        inpuPanel.add(childPanel3);

        add(inpuPanel, BorderLayout.NORTH);
    }

    private void insertApply() {
        String partName = partNameField.getText();
        int cnt = Integer.parseInt(cntField.getText());

        // appyId 자동 증가
        ApplyVO lastData = applyDAO.selectLast();
        long applyId = lastData.getApplyId() + 1;
        // 부품이름에 해당하는 id 조회
        StockVO stockVO = stockDAO.selectByPartName(partName);
        String stockId = stockVO.getPid();
        if (checkNullString(stockId)) {
            JOptionPane.showMessageDialog(this, "해당 부품은 현재 존재하지 않습니다", "Message", JOptionPane.ERROR_MESSAGE);
            System.out.println("해당 부품이 존재하지 않습니다");
            return;
        }

        ApplyVO applyVO = new ApplyVO(applyId, cnt, Long.parseLong(stockId), UserSession.getUserId());
        int result = applyDAO.insert(applyVO);
        if (result == 1) {
            JOptionPane.showMessageDialog(this, "부품 신청이 완료 되었습니다", "Message", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "예기치 못한 이유로 부품 신청이 취소 되었습니다", "Message", JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean checkNullString(String s) {
        if (s != null) {
            return false;
        }
        return true;
    }

    private void createScrollPanel() {
        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------현재 부품 재고 리스트-------------------").append("\n");
        List<StockVO> products = stockDAO.getAllProducts();
        for (StockVO product : products) {
            sb.append("-").append(product.getPid()).append("-\n");
            sb.append("재고이름: ").append(product.getPname()).append("\n");
            sb.append("재고 수량: ").append(product.getStockNum()).append("\n");
            sb.append("장소: ").append(product.getPlace()).append("\n");
            sb.append("입고날짜: ").append(product.getSdate()).append("\n");
            sb.append("오버홀날짜: ").append(product.getOdate()).append("\n");
            sb.append("-------------------------------------------").append("\n");
        }
        outputArea.setText(sb.toString());
        add(scrollPane, BorderLayout.CENTER);
    }

}
