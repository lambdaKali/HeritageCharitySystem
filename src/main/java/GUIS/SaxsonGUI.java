package GUIS;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import saxon.Member;
import saxon.SaxsonServices;

public class SaxsonGUI extends JFrame {
    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JTextField ageTextField;

    private JButton addMemberButton;
    private JButton submitVisitorReportButton;
    private JButton getAllMembersButton;
    private SaxsonServices saxsonServices;

    public SaxsonGUI(SaxsonServices saxsonServices) {
        this.saxsonServices = saxsonServices;
        setTitle("Saxson Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1));

        panel.add(new JLabel("Name:"));
        nameTextField = new JTextField(20);
        panel.add(nameTextField);

        panel.add(new JLabel("Email:"));
        emailTextField = new JTextField(20);
        panel.add(emailTextField);

        panel.add(new JLabel("Age:"));
        ageTextField = new JTextField(20);
        panel.add(ageTextField);
        
        panel.add(new JLabel("ID:"));
        idTextField = new JTextField(20);
        panel.add(idTextField);

        addMemberButton = new JButton("Add Member");
        addMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idTextField.getText());
                String name = nameTextField.getText();
                String email = emailTextField.getText();
                int age = Integer.parseInt(ageTextField.getText());
                Member member = new Member(name, email, age, id);
                try {
                    saxsonServices.addMember(member);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(addMemberButton);

        submitVisitorReportButton = new JButton("Submit Visitor Report");
        submitVisitorReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int memberId = Integer.parseInt(idTextField.getText());
                try {
                    saxsonServices.submitVisitorReport(memberId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(submitVisitorReportButton);

        getAllMembersButton = new JButton("Get All Members");
        getAllMembersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println(saxsonServices.getAllMembers());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(getAllMembersButton);

        getContentPane().add(panel);
    }

    public void showGUI() {
        setVisible(true);
    }
}
