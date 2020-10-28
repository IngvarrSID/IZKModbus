import jssc.SerialPortList;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Terminal {



    private String comName;


    public Terminal(ModbusIZKUI modbusIZKUI) {
        modbusIZKUI.getLabel1().setText("Доступны COM порты:");
        ConsoleHelper.writeMessage("Выберите COM порт из доступных:");
        String[] portNames = SerialPortList.getPortNames();
        modbusIZKUI.getList1().setListData(portNames);
        modbusIZKUI.getTextArea1().setText("Выберите порт из доступных:");
        for (String s : portNames) {
            System.out.println(s);
        }
        while (comName == null) {
            try {
                if (modbusIZKUI.getCurrentSelected() > 0) {
                    comName = portNames[modbusIZKUI.getCurrentSelected() - 1];
                    modbusIZKUI.setCurrentSelected(0);
                }
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        }


    public String getComName() {
        return comName;
    }
}
