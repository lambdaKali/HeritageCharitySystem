package GUIS;


import celtic.CelticServices;
import common.DataHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class CelticGUI extends JFrame {
    private JTextField memberIdTextField;
    private JTextArea entryCreditsTextArea;
    private JButton redeemEntryCreditButton;
    private JButton refreshButton;
    private CelticServices celticServices;

    public CelticGUI(CelticServices celticServices) {
        this.celticServices = celticServices;
        setTitle("Celtic Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        memberIdTextField = new JTextField(20);
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Member ID:"));
        inputPanel.add(memberIdTextField);
        panel.add(inputPanel, BorderLayout.NORTH);

        entryCreditsTextArea = new JTextArea();
        entryCreditsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(entryCreditsTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        redeemEntryCreditButton = new JButton("Redeem Entry Credit");
        redeemEntryCreditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int memberId = Integer.parseInt(memberIdTextField.getText());
                try {
                    celticServices.redeemEntryCredit(memberId);
                    updateEntryCreditsDisplay();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonPanel.add(redeemEntryCreditButton);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEntryCreditsDisplay();
            }
        });
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);
    }

    public void showGUI() {
        setVisible(true);
        updateEntryCreditsDisplay();
    }

    private void updateEntryCreditsDisplay() {
        try {
            StringBuilder sb = new StringBuilder();
            Map<Integer, List<DataHolder>> entryCredits = celticServices.getEntryCredits();
            for (Map.Entry<Integer, List<DataHolder>> entry : entryCredits.entrySet()) {
                for (DataHolder dataHolder : entry.getValue()) {
                    sb.append("Member ID: ").append(entry.getKey())
                            .append(", Site Obtained: ").append(dataHolder.getSiteObtained())
                            .append(", Expiration Date: ").append(dataHolder.getExpirationDate())
                            .append("\n");
                }
            }
            entryCreditsTextArea.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
