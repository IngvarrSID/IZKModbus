import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModbusIZKUI extends JFrame{
    private JButton button1;
    private JPanel panel1;
    private JTextField address;
    private JTextField data;
    private JTextField level;
    private JList<String> list1;
    private JLabel label1;
    private JLabel label2;
    private JLabel labelChannel;
    private JTextField volume;
    private JTextField mass;
    private JTextField density;
    private JTextField tempSens;
    private JTextField eLiquid;
    private JTextField period;
    private JTextField cs;
    private JLabel status;
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
        data.addActionListener(new ActionListener() {
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
        setSize(500,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    public boolean isB1() {
        return b1;
    }

    public void setB1(boolean b1) {
        this.b1 = b1;
    }


    public JTextField getTextField1() {
        return data;
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

    public JTextField getAddress() {
        return address;
    }

    public JTextField getData() {
        return data;
    }

    public JTextField getLevel() {
        return level;
    }

    public JTextField getVolume() {
        return volume;
    }

    public JTextField getMass() {
        return mass;
    }

    public JTextField getDensity() {
        return density;
    }

    public JTextField getTempSens() {
        return tempSens;
    }

    public JTextField geteLiquid() {
        return eLiquid;
    }

    public JTextField getPeriod() {
        return period;
    }

    public JTextField getCs() {
        return cs;
    }

    public JLabel getStatus() {
        return status;
    }
}

