package ui;

import dao.StockDAOImpl;
import ui.common.CommonValue;
import vo.StockVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class StockSwing extends JFrame {
    private StockDAOImpl stockDAO;
    private JTextField partNameField, stockDateField,  placeField, stockNumField;
    private JTextArea outputArea;

    public StockSwing() {
        stockDAO = new StockDAOImpl();

        setTitle(CommonValue.TITLE);
        setSize(CommonValue.WIDTH, CommonValue.HEIGHT);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20 , 30));
        inputPanel.add(new JLabel("부품 이름:"));
        partNameField = new JTextField();
        inputPanel.add(partNameField);
        inputPanel.add(new JLabel("입고 날짜:"));
        stockDateField = new JTextField();
        inputPanel.add(stockDateField);
        inputPanel.add(new JLabel("장소:"));
        placeField = new JTextField();
        inputPanel.add(placeField);
        inputPanel.add(new JLabel("재고 수량:"));
        stockNumField = new JTextField();
        inputPanel.add(stockNumField);

        JButton addButton = new JButton("부품 추가");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        inputPanel.add(addButton);

        JButton getAllButton = new JButton("모든 부품 조회");
        getAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getAllProducts();
            }
        });
        inputPanel.add(getAllButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addProduct() {
        String partName = partNameField.getText();
        String stockDate = stockDateField.getText();
        String place = placeField.getText();
        int stockNum = Integer.parseInt(stockNumField.getText());

        StockVO lastData = stockDAO.selectLast();
        long pid = Long.parseLong(lastData.getPid()) + 1;
        StockVO newProduct = new StockVO(String.valueOf(pid), partName, stockNum, stockDate, place, stockDate, null);
        int result = stockDAO.insertStock(newProduct);
        if (result > 0) {
            outputArea.setText("부품이 성공적으로 추가되었습니다.");
        } else {
            outputArea.setText("부품 추가에 실패했습니다.");
        }
    }

    private void getAllProducts() {
        List<StockVO> products = stockDAO.getAllProducts();
        StringBuilder sb = new StringBuilder();
        for (StockVO product : products) {
            sb.append("-").append(product.getPid()).append("-\n");
            sb.append("재고이름: ").append(product.getPname()).append("\n");
            sb.append("재고 수량: ").append(product.getStockNum()).append("\n");
            sb.append("장소: ").append(product.getPlace()).append("\n");
            sb.append("입고일자: ").append(product.getSdate()).append("\n");
            sb.append("오버홀 날짜: ").append(product.getOdate()).append("\n");
            sb.append("-------------------------------------------").append("\n");
        }
        outputArea.setText(sb.toString());
    }
}