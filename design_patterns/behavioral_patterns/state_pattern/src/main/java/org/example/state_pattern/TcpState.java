package org.example.state_pattern;

public interface TcpState {
    void applPassiveOpen();
    void applActiveOpen();
    void applClose();
    void applSendData();
    void timeout(); 
    void recvSyn();
    void recvAck();
    void recvSynAck();
    void recvFin();
    void recvFinAck();
    void recvRst();
}
