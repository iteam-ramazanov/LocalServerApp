package com.iteam_ramazanov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class PublicChatRoomServerWindow extends JFrame implements TCPConnectionListener {
    
    private static final int WIDTH = 375;
    private static final int HEIGHT = 500;
    private static final String NEW_LINE = "\r\n";
    private static final int ALLOWED_MIN_PORT_NUMBER = 49152;
    private static final int ALLOWED_MAX_PORT_NUMBER = 65535;
    
    private ServerThread serverThread;
    private final ArrayList<TCPConnection> connections = new ArrayList<>();
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PublicChatRoomServerWindow();
            }
        });
    }
    
    private  final  JLabel       labelYourIPAddress     =  new  JLabel();
    private  final  JLabel       labelTheIPAddress      =  new  JLabel();
    private  final  JLabel       labelServerPortNumber  =  new  JLabel();
    private  final  JTextField   fieldInputPort         =  new  JTextField("56789", 7);
    private  final  JTextArea    areaLogs               =  new  JTextArea("");
    private  final  JScrollPane  areaLogsScrollPane     =  new  JScrollPane(areaLogs);
    private  final  JButton      buttonRunServer        =  new  JButton("Запуск сервера");
    private  final  JButton      buttonStopServer       =  new  JButton("Остановка сервера");
    
    private void log(String line) {
        areaLogs.append(line + NEW_LINE);
    }
    
    private int portNumber(String str) {
        if ((str == null) || (str.isEmpty())) return 0;
        if (str.charAt(0) == '0') return 0;
        int result = 0;
        for (char c: str.toCharArray()) {
            if (!Character.isDigit(c)) return 0;
            result = result * 10 + Character.getNumericValue(c);
            if (result > ALLOWED_MAX_PORT_NUMBER) return 0;
        }
        if (result < ALLOWED_MIN_PORT_NUMBER) return 0;
        return result;
    }
    
    private class ServerThread extends Thread {
        private int port;
        
        public ServerThread(int port) {
            super();
            this.port = port;
        }
        
        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(port)){
                log("Server has been started");
                while (!isInterrupted()) {
                    try {
                        new TCPConnection(PublicChatRoomServerWindow.this, serverSocket.accept());
                    } catch (IOException e) {
                        log("TCPConnection exception: " + e);
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                log("IOException when getting server socket: " + e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
    
    private void runServer() {
        if (serverThread == null) {
            int port = portNumber(fieldInputPort.getText());
            if (port != 0) {
                log("Running server ...");
                // TODO Устранить появления исключения IOException when getting server socket: java.net.BindException: Address already in use (Bind failed)
                serverThread = new ServerThread(port);
                serverThread.start();
            } else {
                JOptionPane.showMessageDialog(this,
                                              "Выберите порт в диапазоне " + ALLOWED_MIN_PORT_NUMBER + "-" + ALLOWED_MAX_PORT_NUMBER,
                                              "Недопустимый номер порта",
                                              JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
    
    private void stopServer() {
        if (serverThread != null) {
            log("Stopping server ...");
            serverThread.interrupt();
            serverThread = null;
            log("Server has been stopped");
        }
    }
    
    private PublicChatRoomServerWindow() {
        
        serverThread = null;
        
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
    
    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllClients("New client with IP address " + tcpConnection.getIPAddressAsString() + " connected to the port" + tcpConnection.getPortAsString());
    }
    
    @Override
    public void onReceiveString(TCPConnection tcpConnection, String message) {
        sendToAllClients(message);
    }
    
    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllClients("Client with IP address " + tcpConnection.getIPAddressAsString() + " disconnected");
    }
    
    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        log("TCPConnection exception: " + e);
    }
    
    private void sendToAllClients(String message) {
        log(message);
        for (TCPConnection connection: connections) {
            connection.sendString(message);
        }
    }
}
