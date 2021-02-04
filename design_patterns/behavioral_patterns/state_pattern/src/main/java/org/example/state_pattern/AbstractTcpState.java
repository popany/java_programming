package org.example.state_pattern;

public abstract class AbstractTcpState implements TcpState {
    private TcpContext tcpContext;

    public AbstractTcpState(TcpContext tcpContext) {
        this.tcpContext = tcpContext;
    }

    public TcpContext getTcpContext() {
        return tcpContext;
    }

    public void applPassiveOpen() {
    }

    public void applActiveOpen() {
    }

    public void applClose() {
    }

    public void applSendData() {
    }

    public void timeout() {
    } 

    public void recvSyn() {
    }

    public void recvAck() {
    }

    public void recvSynAck() {
    }

    public void recvFin() {
    }

    public void recvFinAck() {
    }

    public void recvRst() {
    }
}
