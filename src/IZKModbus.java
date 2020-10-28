import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.exception.ModbusChecksumException;
import com.intelligt.modbus.jlibmodbus.serial.*;
import jssc.SerialPortList;

import java.io.IOException;


public class IZKModbus {


    public static void main(String[] args) {
        ModbusIZKUI modbusIZKUI = new ModbusIZKUI();
        modbusIZKUI.getLabel1().setText("");
        modbusIZKUI.getLabel2().setText("");
        modbusIZKUI.getTextArea1().setText("Нажмите кнопку что бы начать");
        while (true) {
            try {
                if (modbusIZKUI.isB1()) break;
                else Thread.sleep(500);

            } catch (InterruptedException e) {
                e.getStackTrace();
            }
        }
        modbusIZKUI.setB1(false);

        Terminal terminal = new Terminal(modbusIZKUI);
        String comName = terminal.getComName();
        SerialParameters sp = new SerialParameters();
        Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);

        try {
            sp.setDevice(comName);
            sp.setBaudRate(SerialPort.BaudRate.BAUD_RATE_19200);
            sp.setDataBits(8);
            sp.setParity(SerialPort.Parity.NONE);
            sp.setStopBits(1);
            SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
            ModbusMaster m = ModbusMasterFactory.createModbusMasterASCII(sp);

            m.connect();
            int slaveId = 80;
            ModbusReader modbusReader = new ModbusReader(m,80);
            modbusIZKUI.getLabel2().setText("Подключено к "+comName);
            modbusIZKUI.getLabel1().setText("Выберите измерительный канал:");
            String[] channelsNumbers = {"Канал 1","Канал 2","Канал 3","Канал 4","Канал 5","Канал 6" };
            modbusIZKUI.getList1().setListData(channelsNumbers);

            int offset = 0;
            int quantity = 32;
            int i = 0;
            String s;

            try {
                while (true) {
                    int mode1[] = modbusReader.readModeRegister(1);
                    System.out.println(mode1[0]);

                    modbusIZKUI.getLabelChannel().setText(channelsNumbers[mode1[0]]);



                    int[] registerValues = modbusReader.readRegisters(0,32,2);
                    for (int qwe: registerValues) {
                        System.out.print(qwe);
                    }
                    System.out.println("");

                    int sensorAddress = registerValues[0];
                    String date = String.format("%d.%d.%d", registerValues[1],registerValues[2],registerValues[3]);
                    String time = String.format("%d:%d:%d",registerValues[4],registerValues[5],registerValues[6]);
                    float level = hexToFloat(registerValues[7], registerValues[8]);
                    float level2 = hexToFloat(registerValues[9], registerValues[10]);
                    float pressure = hexToFloat(registerValues[11], registerValues[12]);
                    float pressure2 = hexToFloat(registerValues[13], registerValues[14]);
                    float volumePers = hexToFloat(registerValues[15], registerValues[16]);
                    float volume = hexToFloat(registerValues[17], registerValues[18]);
                    float massLiq = hexToFloat(registerValues[21], registerValues[22]);
                    float massGas = hexToFloat(registerValues[23], registerValues[24]);
                    float sumMass = hexToFloat(registerValues[25], registerValues[26]);
                    float densLiq = hexToFloat(registerValues[27], registerValues[28]);
                    float densGas = hexToFloat(registerValues[29], registerValues[30]);





                    //  int[] registerValues = m.readInputRegisters(slaveId, offset, quantity);
                    //     for (int value : registerValues) {
                    //        System.out.println("Address: " + offset++ + ", Value " + value);
                    //    }
                    //   Thread.sleep(1000);
                   //уровень
                    int[] registerLevel = m.readInputRegisters(slaveId, 7, 2);
                    s = Integer.toHexString(registerLevel[1]) + Integer.toHexString(registerLevel[0]);
                   Long l = Long.parseLong(s, 16);
                    level = Float.intBitsToFloat(l.intValue());

                    //температура
                  /*  int[] registerTemp = m.readInputRegisters(slaveId, 46, 2);
                    s = Integer.toHexString(registerTemp[1]) + Integer.toHexString(registerLevel[0]);
                    Long t = Long.parseLong(s, 16);
                    float temp = Float.intBitsToFloat(t.intValue());*/

                    modbusIZKUI.getTextArea1().setText("Уровень: ");
                    modbusIZKUI.getTextArea1().append(String.valueOf(level) + " мм");
           /*         modbusIZKUI.getTextArea2().setText("Температура: ");
                    modbusIZKUI.getTextArea2().append(String.valueOf(temp) + " градусов цельсия");*/
                   // Thread.sleep(500);

               //     if (modbusIZKUI.isB1() && modbusIZKUI.getTextField1().getText().equals("exit")) {

                  //      modbusIZKUI.setB1(false);
                 //       break;
                //    }

                    // i = i < 1 ? 1 : 0;
                    //  m.writeSingleRegister(80, 1, i);
                    //   Thread.sleep(2000);
               if (modbusIZKUI.getCurrentSelected()>0){
                   m.writeSingleRegister(slaveId, 1, modbusIZKUI.getCurrentSelected()-1);
                 //  Thread.sleep(500);
                   modbusIZKUI.setCurrentSelected(0);
                   modbusIZKUI.getList1().clearSelection();
               }
                }

            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    m.disconnect();
                } catch (ModbusChecksumException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static Float hexToFloat(int value1, int value2){
        String s = Integer.toHexString(value2) + Integer.toHexString(value1);
        Long l = Long.parseLong(s, 16);
        return Float.intBitsToFloat(l.intValue());
    }
}
