package com.mycompany.servidorudp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorUDP {
    public static void main(String[] args) {
        final int PORTA = 9999;
        try (DatagramSocket socket = new DatagramSocket(PORTA)) {
            System.out.println("Servidor UDP iniciado na porta " + PORTA);
            byte[] buffer = new byte[1024];
            while (true) {
                // 1. Aguarda receber um pacote
                DatagramPacket pacoteRecebido = new DatagramPacket(buffer, buffer.length);
                socket.receive(pacoteRecebido); // bloqueia até chegar algo
                String mensagem = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength());
                InetAddress enderecoCliente = pacoteRecebido.getAddress();
                int portaCliente = pacoteRecebido.getPort();
                System.out.println("Recebido de " + enderecoCliente + ":" + portaCliente 
                                + " -> " + mensagem);

                // 2. Monta e envia a resposta para o mesmo cliente
                String resposta = "Servidor recebeu: " + mensagem;
                byte[] dadosResposta = resposta.getBytes();

                DatagramPacket pacoteResposta = new DatagramPacket(
                        dadosResposta, dadosResposta.length, enderecoCliente, portaCliente);
                socket.send(pacoteResposta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
