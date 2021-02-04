package org.example.state_pattern;

public class TcpContext {
    TcpState tcpState;

    public void setTcpState(TcpState tcpState) {
        this.tcpState = tcpState;
    }

    public void applPassiveOpen() {
        System.out.println("Appl passive open:");
        tcpState.applPassiveOpen();
    }

    public void applActiveOpen() {
        System.out.println("Appl active open:");
        tcpState.applActiveOpen();
    }

    public void applClose() {
        System.out.println("Appl close:");
        tcpState.applClose();
    }

    public void applSendData() {
        System.out.println("Appl send data:");
        tcpState.applSendData();
    }

    public void timeout() {
        System.out.println("Timeout:");
        tcpState.timeout();
    } 

    public void recvSyn() {
        System.out.println("Recv SYN:");
        tcpState.recvSyn();
    }

    public void recvAck() {
        System.out.println("Recv ACK:");
        tcpState.recvAck();
    }

    public void recvSynAck() {
        System.out.println("Recv SYN ACK:");
        tcpState.recvSynAck();
    }

    public void recvFin() {
        System.out.println("Recv FIN:");
        tcpState.recvFin();
    }

    public void recvFinAck() {
        System.out.println("Recv FIN ACK:");
        tcpState.recvFinAck();
    }

    public void recvRst() {
        System.out.println("Recv RST:");
        tcpState.recvRst();
    }
}
