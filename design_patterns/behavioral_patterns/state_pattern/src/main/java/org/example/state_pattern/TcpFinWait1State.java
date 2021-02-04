package org.example.state_pattern;

public class TcpFinWait1State extends AbstractTcpState {
    public TcpFinWait1State(TcpContext tcpContext) {
        super(tcpContext);
    }

    public void recvFin() {
        TcpClosingState nextState = new TcpClosingState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("             recv: FIN");
        System.out.println("FIN_WAIT_1 -------------> CLOSING");
        System.out.println("             send: ACK");
    }

    public void recvFinAck() {
        TcpTimeWaitState nextState = new TcpTimeWaitState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("             recv: FIN, ACK");
        System.out.println("FIN_WAIT_1 ------------------> TIME_WAIT");
        System.out.println("               send: ACK");
    }

    public void recvAck() {
        TcpFinWait2State nextState = new TcpFinWait2State(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("                recv: ACK");
        System.out.println("FIN_WAIT_1 -------------------> FIN_WAIT_2");
        System.out.println("             send: <nothing>");
    }
}
