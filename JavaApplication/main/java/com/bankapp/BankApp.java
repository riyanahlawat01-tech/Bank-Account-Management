package com.bankapp;

import javax.swing.*;
import java.awt.*;

/**
 * Main entry point for the Bank Account Manager application.
 * Displays student info on launch and provides a menu-driven GUI.
 *
 * Student: Riyan Ahlawat | SAP-ID: 590015207 | Roll: 24010113016
 */
public class BankApp {

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Bank bank;

    /** Constructs the application, initializes bank and GUI. */
    public BankApp() {
        bank = new Bank("RiyanBank");
        frame = new JFrame("Bank Account Manager");
        frame.setSize(450, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(openingPanel(),   "opening");
        mainPanel.add(menuPanel(),      "menu");
        mainPanel.add(createPanel(),    "create");
        mainPanel.add(depositPanel(),   "deposit");
        mainPanel.add(withdrawPanel(),  "withdraw");
        mainPanel.add(transferPanel(),  "transfer");
        mainPanel.add(statementPanel(), "statement");

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ── OPENING SCREEN ──────────────────────────────────────────────────────────
    JPanel openingPanel() {
        JPanel p = new JPanel(null);
        p.setBackground(new Color(15, 52, 96));

        JLabel title = new JLabel("Bank Account Manager", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(50, 60, 340, 35);

        JLabel name  = new JLabel("Student : Riyan Ahlawat",  SwingConstants.CENTER);
        JLabel sap   = new JLabel("SAP-ID  : 590015207",      SwingConstants.CENTER);
        JLabel roll  = new JLabel("Roll No : 24010113016",    SwingConstants.CENTER);

        for (JLabel l : new JLabel[]{name, sap, roll}) {
            l.setForeground(Color.CYAN);
            l.setFont(new Font("Monospaced", Font.PLAIN, 14));
        }
        name.setBounds(50, 130, 340, 25);
        sap .setBounds(50, 160, 340, 25);
        roll.setBounds(50, 190, 340, 25);

        JLabel course = new JLabel("Course: Software Testing  |  B.Tech CSE", SwingConstants.CENTER);
        course.setForeground(Color.LIGHT_GRAY);
        course.setFont(new Font("Arial", Font.ITALIC, 11));
        course.setBounds(50, 230, 340, 20);

        JButton enter = new JButton("Enter Application");
        enter.setBounds(140, 300, 160, 38);
        enter.setBackground(new Color(0, 188, 212));
        enter.setForeground(Color.WHITE);
        enter.setFocusPainted(false);
        enter.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        p.add(title); p.add(name); p.add(sap); p.add(roll);
        p.add(course); p.add(enter);
        return p;
    }

    // ── MAIN MENU ───────────────────────────────────────────────────────────────
    JPanel menuPanel() {
        JPanel p = new JPanel(null);
        p.setBackground(new Color(30, 60, 90));

        JLabel lbl = new JLabel("-- Main Menu --", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(100, 30, 250, 35);

        String[] labels  = {"1. Create Account", "2. Deposit", "3. Withdraw", "4. Transfer", "5. View Statement"};
        String[] targets = {"create", "deposit", "withdraw", "transfer", "statement"};
        int y = 100;
        for (int i = 0; i < labels.length; i++) {
            JButton b = new JButton(labels[i]);
            b.setBounds(130, y, 190, 38);
            b.setBackground(new Color(0, 120, 200));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            final String target = targets[i];
            b.addActionListener(e -> cardLayout.show(mainPanel, target));
            p.add(b);
            y += 55;
        }
        p.add(lbl);
        return p;
    }

    // ── CREATE ACCOUNT ──────────────────────────────────────────────────────────
    JPanel createPanel() {
        JPanel p = new JPanel(null);

        JLabel title = label("Create Account", 130, 20, 200, 28, true);
        JLabel l1 = label("Account No:", 60, 70, 120, 25, false);
        JLabel l2 = label("Holder Name:", 60, 110, 120, 25, false);
        JLabel l3 = label("Initial Deposit:", 60, 150, 130, 25, false);

        JTextField accField  = field(190, 70);
        JTextField nameField = field(190, 110);
        JTextField amtField  = field(190, 150);

        JButton create = new JButton("Create");
        create.setBounds(160, 200, 120, 35);
        create.addActionListener(e -> {
            try {
                String acc  = accField.getText().trim();
                String name = nameField.getText().trim();
                double amt  = Double.parseDouble(amtField.getText().trim());
                bank.createAccount(acc, name, amt);
                JOptionPane.showMessageDialog(frame, "Account Created!\nAccount No: " + acc);
                accField.setText(""); nameField.setText(""); amtField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        JButton back = backBtn();
        p.add(title); p.add(l1); p.add(l2); p.add(l3);
        p.add(accField); p.add(nameField); p.add(amtField);
        p.add(create); p.add(back);
        return p;
    }

    // ── DEPOSIT ─────────────────────────────────────────────────────────────────
    JPanel depositPanel() {
        JPanel p = new JPanel(null);
        JLabel title = label("Deposit Money", 130, 20, 200, 28, true);
        JLabel l1 = label("Account No:",  60, 80, 120, 25, false);
        JLabel l2 = label("Amount:",      60, 120, 120, 25, false);

        JTextField accField = field(190, 80);
        JTextField amtField = field(190, 120);

        JButton btn = new JButton("Deposit");
        btn.setBounds(160, 170, 120, 35);
        btn.addActionListener(e -> {
            try {
                BankAccount acc = getAccount(accField.getText().trim());
                acc.deposit(Double.parseDouble(amtField.getText().trim()));
                JOptionPane.showMessageDialog(frame, "Deposit Successful!\nNew Balance: Rs. " + acc.getBalance());
                accField.setText(""); amtField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        JButton back = backBtn();
        p.add(title); p.add(l1); p.add(l2); p.add(accField); p.add(amtField); p.add(btn); p.add(back);
        return p;
    }

    // ── WITHDRAW ────────────────────────────────────────────────────────────────
    JPanel withdrawPanel() {
        JPanel p = new JPanel(null);
        JLabel title = label("Withdraw Money", 130, 20, 200, 28, true);
        JLabel l1 = label("Account No:",  60, 80, 120, 25, false);
        JLabel l2 = label("Amount:",      60, 120, 120, 25, false);

        JTextField accField = field(190, 80);
        JTextField amtField = field(190, 120);

        JButton btn = new JButton("Withdraw");
        btn.setBounds(160, 170, 120, 35);
        btn.addActionListener(e -> {
            try {
                BankAccount acc = getAccount(accField.getText().trim());
                acc.withdraw(Double.parseDouble(amtField.getText().trim()));
                JOptionPane.showMessageDialog(frame, "Withdrawal Successful!\nNew Balance: Rs. " + acc.getBalance());
                accField.setText(""); amtField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        JButton back = backBtn();
        p.add(title); p.add(l1); p.add(l2); p.add(accField); p.add(amtField); p.add(btn); p.add(back);
        return p;
    }

    // ── TRANSFER ────────────────────────────────────────────────────────────────
    JPanel transferPanel() {
        JPanel p = new JPanel(null);
        JLabel title  = label("Transfer Money",   130, 20,  200, 28, true);
        JLabel l1 = label("From Account:", 50, 80,  130, 25, false);
        JLabel l2 = label("To Account:",   50, 120, 130, 25, false);
        JLabel l3 = label("Amount:",       50, 160, 130, 25, false);

        JTextField fromField = field(190, 80);
        JTextField toField   = field(190, 120);
        JTextField amtField  = field(190, 160);

        JButton btn = new JButton("Transfer");
        btn.setBounds(160, 210, 120, 35);
        btn.addActionListener(e -> {
            try {
                BankAccount from = getAccount(fromField.getText().trim());
                BankAccount to   = getAccount(toField.getText().trim());
                from.transfer(to, Double.parseDouble(amtField.getText().trim()));
                JOptionPane.showMessageDialog(frame, "Transfer Successful!");
                fromField.setText(""); toField.setText(""); amtField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        JButton back = backBtn();
        p.add(title); p.add(l1); p.add(l2); p.add(l3);
        p.add(fromField); p.add(toField); p.add(amtField); p.add(btn); p.add(back);
        return p;
    }

    // ── VIEW STATEMENT ──────────────────────────────────────────────────────────
    JPanel statementPanel() {
        JPanel p = new JPanel(null);
        JLabel title = label("View Statement", 130, 20, 200, 28, true);
        JLabel l1    = label("Account No:", 60, 70, 120, 25, false);

        JTextField accField = field(190, 70);
        JTextArea  area     = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(30, 130, 380, 240);

        JButton btn = new JButton("View");
        btn.setBounds(190, 100, 100, 28);
        btn.addActionListener(e -> {
            try {
                BankAccount acc = getAccount(accField.getText().trim());
                area.setText(acc.getStatement());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        JButton back = backBtn();
        p.add(title); p.add(l1); p.add(accField); p.add(btn); p.add(scroll); p.add(back);
        return p;
    }

    // ── HELPERS ─────────────────────────────────────────────────────────────────
    private JLabel label(String text, int x, int y, int w, int h, boolean bold) {
        JLabel l = new JLabel(text);
        l.setBounds(x, y, w, h);
        l.setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, bold ? 16 : 13));
        return l;
    }

    private JTextField field(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 180, 28);
        return tf;
    }

    private JButton backBtn() {
        JButton b = new JButton("Back");
        b.setBounds(160, 430, 120, 30);
        b.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
        return b;
    }

    private BankAccount getAccount(String accNo) {
        BankAccount acc = bank.findAccount(accNo);
        if (acc == null) throw new IllegalArgumentException("Account not found: " + accNo);
        return acc;
    }

    /** Application entry point. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankApp::new);
    }
}
