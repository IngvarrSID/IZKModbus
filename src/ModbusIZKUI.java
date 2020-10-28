import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModbusIZKUI extends JFrame{
    private JButton button1;
    private JPanel panel1;
    private JTextArea textArea1;
    private JTextField textField1;
    private JTextArea textArea2;
    private JList<String> list1;
    private JLabel label1;
    private JLabel label2;
    private JLabel labelChannel;
    private DefaultListModel<String> dlm;
    private int currentSelected;



    private boolean b1 = false;

    public ModbusIZKUI() {
        setContentPane(panel1);
        setLocationRelativeTo(null);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b1 = !b1;
                System.out.println("Кнопка нажата " + b1);
            }
        });
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b1 = !b1;
                System.out.println("Кнопка нажата " + b1);
                JOptionPane.showMessageDialog(ModbusIZKUI.this,
                        "Поздравляю, вы нажали Enter");
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selected = ((JList<?>)e.getSource()).getSelectedIndex();
                currentSelected = selected + 1;
              //  System.out.println(currentSelected);
            }
        });
        setVisible(true);
        setSize(500,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public boolean isB1() {
        return b1;
    }

    public void setB1(boolean b1) {
        this.b1 = b1;
    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public JTextArea getTextArea2() {
        return textArea2;
    }

    public JTextField getTextField1() {
        return textField1;
    }


    public JPanel getPanel1() {
        return panel1;
    }

    public JList<String> getList1() {
        return list1;
    }

    public int getCurrentSelected() {
        return currentSelected;
    }

    public void setCurrentSelected(int currentSelected) {
        this.currentSelected = currentSelected;
    }

    public JLabel getLabel1() {
        return label1;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public JLabel getLabelChannel() {
        return labelChannel;
    }
}

