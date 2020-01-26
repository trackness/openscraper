package com.trackness.openscraper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenScraper {
    private JPanel rootPanel;
    private JLabel helloLabel;
    private JButton pressMeButton;

    public OpenScraper() {
        pressMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helloLabel.setText("Hello World!");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("OpenScraperGuiMain");
        frame.setContentPane(new OpenScraper().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
