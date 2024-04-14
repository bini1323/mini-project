package ui;

import ui.common.CommonValue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MainSwing extends JFrame {
    private JPanel parentPanel, childPanel;
    private BufferedImage img = null;

    public MainSwing() {
        setTitle(CommonValue.TITLE);
        setSize(CommonValue.WIDTH, CommonValue.HEIGHT);

        createPanel();
        createButton();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createPanel() {
        loadImg();

        parentPanel = new JPanel(new BorderLayout()) {
            public void paint(Graphics g) {
                g.drawImage(img, 0, 0, null);
            }
        };

        childPanel = new JPanel(new GridLayout(1, 3));
        childPanel.setBorder(BorderFactory.createEmptyBorder(150, 80, 150, 80));

        parentPanel.add(childPanel, BorderLayout.CENTER);
        add(parentPanel);
    }

    private void loadImg() {
        try {
            img = ImageIO.read(new File("/Users/jangtaehyeon/Desktop/cloud.jpeg"));
        } catch(IOException e) {
            System.out.println("이미지 불러오기 실패");
        }
    }

    private void createButton() {
        JPanel buttonContainer1 = new JPanel(new GridLayout(1, 1));
        buttonContainer1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JButton button1 = new JButton("부품 관리");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new StockSwing();
            }
        });
        buttonContainer1.add(button1);
        childPanel.add(buttonContainer1);

        JPanel buttonContainer2 = new JPanel(new GridLayout(1, 1));
        buttonContainer2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JButton button2 = new JButton("부품 신청");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ApplySwing();
            }
        });
        buttonContainer2.add(button2);
        childPanel.add(buttonContainer2);

        JPanel buttonContainer3 = new JPanel(new GridLayout(1, 1));
        buttonContainer3.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JButton button3 = new JButton("부품 신청 내역");
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ApplyHistorySwing();
            }
        });
        buttonContainer3.add(button3);
        childPanel.add(buttonContainer3);
    }
}
