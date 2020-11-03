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
            String[] channelsNumbers = {"Канал 1","Канал 2","Канал 3","Канал 4","Канал 5","Канал 6", "Все каналы" };
            modbusIZKUI.getList1().setListData(channelsNumbers);

            int offset = 0;
            int quantity = 32;
            int i = 0;
            String s;

            try {
                while (true) {
                    int mode1[] = modbusReader.readModeRegister(1);

                    modbusIZKUI.getLabelChannel().setText(channelsNumbers[mode1[0]]);


                    int[] registerValues = modbusReader.readRegisters(0,32,4);

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
                    int tempSensCount = registerValues[31];
                    float tempSens = hexToFloat(registerValues[32],registerValues[33]);
                    float temp1 = hexToFloat(registerValues[34],registerValues[35]);
                    float temp2 = hexToFloat(registerValues[36],registerValues[37]);
                    float temp3 = hexToFloat(registerValues[38],registerValues[39]);
                    float temp4 = hexToFloat(registerValues[40],registerValues[41]);
                    float temp5 = hexToFloat(registerValues[42],registerValues[43]);
                    float temp6 = hexToFloat(registerValues[44],registerValues[45]);
                    float tempSum = hexToFloat(registerValues[46],registerValues[47]);

                    String status = Integer.toBinaryString(registerValues[52]);
                    String statusRevers = "";
                    for (int j = 0; j < 16; j++) {
                        if(status.length()>j) statusRevers = status.charAt(j) + statusRevers;
                        else statusRevers = statusRevers + "0";
                    }
                        boolean dataExist = statusRevers.charAt(0) == '1';
                        boolean measuring = statusRevers.charAt(1) == '1';
                        boolean noData = statusRevers.charAt(2) == '1';
                        boolean nullPeriod = statusRevers.charAt(3) == '1';
                        boolean gradError = statusRevers.charAt(4) == '1';
                        boolean nullAddress = statusRevers.charAt(5) == '1';
                        boolean disChannel = statusRevers.charAt(6) == '1';
                        int sensType = Integer.parseInt(statusRevers.substring(7, 9), 2);
                        int sensWare = Integer.parseInt(statusRevers.substring(9,13),2);
                        int activStatus = Integer.parseInt(statusRevers.substring(13),2);;


                    String alarm = Integer.toBinaryString(registerValues[53]);
                    System.out.println(alarm);
                    String alarmRevers = "";
                    for (int j = 0; j < 16; j++) {
                        if(alarm.length()>j) alarmRevers = alarm.charAt(j) + alarmRevers;
                        else alarmRevers = alarmRevers + "0";
                    }
                    boolean min = alarmRevers.charAt(0) == 1;
                    boolean max = alarmRevers.charAt(1) == 1;
                    boolean emergencyMax = alarmRevers.charAt(2) == 1;
                    boolean lowDensity = alarmRevers.charAt(3) == 1;
                    boolean highPressure = alarmRevers.charAt(4) == 1;

                    float eLiquid = hexToFloat(registerValues[54],registerValues[55]);
                    float period = hexToFloat(registerValues[60],registerValues[61]);
                    float cs = hexToFloat(registerValues[64],registerValues[65]);
                    float tempError = hexToFloat(registerValues[68],registerValues[69]);
                    int adc = registerValues[70];
                    float temp7 = hexToFloat(registerValues[72],registerValues[73]);
                    float temp8 = hexToFloat(registerValues[74],registerValues[75]);
                    float temp9 = hexToFloat(registerValues[76],registerValues[77]);
                    float temp10 = hexToFloat(registerValues[78],registerValues[79]);
                    float temp11 = hexToFloat(registerValues[80],registerValues[81]);
                    float temp12 = hexToFloat(registerValues[82],registerValues[83]);
                    float temp13 = hexToFloat(registerValues[84],registerValues[85]);
                    float temp14 = hexToFloat(registerValues[86],registerValues[87]);
                    float temp15 = hexToFloat(registerValues[88],registerValues[89]);
                    float temp16 = hexToFloat(registerValues[90],registerValues[91]);
                    float temp17 = hexToFloat(registerValues[92],registerValues[93]);
                    float temp18 = hexToFloat(registerValues[94],registerValues[95]);
                    float temp19 = hexToFloat(registerValues[96],registerValues[97]);
                    float temp20 = hexToFloat(registerValues[98],registerValues[99]);


                    modbusIZKUI.getTextField1().setText(date + " " + time);






                    //  int[] registerValues = m.readInputRegisters(slaveId, offset, quantity);
                    //     for (int value : registerValues) {
                    //        System.out.println("Address: " + offset++ + ", Value " + value);
                    //    }
                    //   Thread.sleep(1000);
                   //уровень
                 /*   int[] registerLevel = m.readInputRegisters(slaveId, 7, 2);
                    s = Integer.toHexString(registerLevel[1]) + Integer.toHexString(registerLevel[0]);
                   Long l = Long.parseLong(s, 16);
                    level = Float.intBitsToFloat(l.intValue());*/

                    //температура
                  /*  int[] registerTemp = m.readInputRegisters(slaveId, 46, 2);
                    s = Integer.toHexString(registerTemp[1]) + Integer.toHexString(registerLevel[0]);
                    Long t = Long.parseLong(s, 16);
                    float temp = Float.intBitsToFloat(t.intValue());*/

                /*    modbusIZKUI.getTextArea1().setText("Уровень: ");
                    modbusIZKUI.getTextArea1().append(String.valueOf(level) + " мм");*/
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
            }  /*finally{
                try {
                    m.disconnect();
                } catch (ModbusChecksumException e1) {
                    e1.printStackTrace();
                }
            }*/
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
