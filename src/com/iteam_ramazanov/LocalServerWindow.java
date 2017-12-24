package com.iteam_ramazanov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LocalServerWindow extends JFrame {
    
    private static final int PORT = 5678;
    private static final int WIDTH = 375;
    private static final int HEIGHT = 500;
    private static final String NEW_LINE = "\r\n";
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LocalServerWindow();
            }
        });
    }
    
    private  final  JLabel       labelYourIPAddress     =  new  JLabel();
    private  final  JLabel       labelTheIPAddress      =  new  JLabel();
    private  final  JLabel       labelServerPortNumber  =  new  JLabel();
    private  final  JTextField   fieldInputPort         =  new  JTextField("5678", 7);
    private  final  JTextArea    areaLogs               =  new  JTextArea("");
    private  final  JScrollPane  areaLogsScrollPane     =  new  JScrollPane(areaLogs);
    private  final  JButton      buttonRunServer        =  new  JButton("Запуск сервера");
    private  final  JButton      buttonStopServer       =  new  JButton("Остановка сервера");
    
    private void log(String line) {
        areaLogs.append(line + NEW_LINE);
    }
    
    private void runServer() {
        log("Running server ...");
        log("Server has been started");
    }
    
    private void stopServer() {
        log("Stopping server ...");
        log("Server has been stopped");
    }
    
    private LocalServerWindow() {
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setTitle("Локальный сервер");
        
        Container contentPane = getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);
        
        labelYourIPAddress.setText("Ваш IP адрес: ");
        labelTheIPAddress.setText("192.168.0.54");
        labelServerPortNumber.setText("Номер порта: ");
        fieldInputPort.setHorizontalAlignment(SwingConstants.RIGHT);
        areaLogs.setEditable(false);
        areaLogs.setLineWrap(true);
        areaLogs.setMargin(new Insets(2, 2, 2, 2));
        areaLogsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
        buttonRunServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                runServer();
            }
        });
    
        buttonStopServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                stopServer();
            }
        });
    
        add(labelYourIPAddress);
        add(labelTheIPAddress);
        add(labelServerPortNumber);
        add(fieldInputPort);
        add(areaLogsScrollPane);
        add(buttonRunServer);
        add(buttonStopServer);
    
        layout.putConstraint(SpringLayout.WEST,   labelYourIPAddress,      10,
                             SpringLayout.WEST,   contentPane);
        layout.putConstraint(SpringLayout.NORTH,  labelYourIPAddress,       5,
                             SpringLayout.NORTH,  contentPane);
        layout.putConstraint(SpringLayout.EAST,   labelTheIPAddress,       -5,
                             SpringLayout.EAST,   contentPane);
        layout.putConstraint(SpringLayout.NORTH,  labelTheIPAddress,        0,
                             SpringLayout.NORTH,  labelYourIPAddress);
        layout.putConstraint(SpringLayout.EAST,   fieldInputPort,           2,
                             SpringLayout.EAST,   labelTheIPAddress);
        layout.putConstraint(SpringLayout.NORTH,  fieldInputPort,           3,
                             SpringLayout.SOUTH,  labelTheIPAddress);
        layout.putConstraint(SpringLayout.WEST,   labelServerPortNumber,    0,
                             SpringLayout.WEST,   labelYourIPAddress);
        layout.putConstraint(SpringLayout.NORTH,  labelServerPortNumber,    6,
                             SpringLayout.NORTH,  fieldInputPort);
        layout.putConstraint(SpringLayout.WEST,   areaLogsScrollPane,       0,
                             SpringLayout.WEST,   labelServerPortNumber);
        layout.putConstraint(SpringLayout.EAST,   areaLogsScrollPane,      -2,
                             SpringLayout.EAST,   labelTheIPAddress);
        layout.putConstraint(SpringLayout.NORTH,  areaLogsScrollPane,       3,
                             SpringLayout.SOUTH,  fieldInputPort);
        layout.putConstraint(SpringLayout.WEST,   buttonRunServer,         -6,
                             SpringLayout.WEST,   areaLogsScrollPane);
        layout.putConstraint(SpringLayout.SOUTH,  buttonRunServer,         -5,
                             SpringLayout.SOUTH,  contentPane);
        layout.putConstraint(SpringLayout.SOUTH,  areaLogsScrollPane,      -3,
                             SpringLayout.NORTH,  buttonRunServer);
        layout.putConstraint(SpringLayout.EAST,   buttonStopServer,         6,
                             SpringLayout.EAST,   areaLogsScrollPane);
        layout.putConstraint(SpringLayout.NORTH,  buttonStopServer,         0,
                             SpringLayout.NORTH,  buttonRunServer);
        
        setVisible(true);
        requestFocusInWindow();
    }
    
}
