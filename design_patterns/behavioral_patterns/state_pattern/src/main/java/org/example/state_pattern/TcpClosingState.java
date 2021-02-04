package org.example.state_pattern;

public class TcpClosingState extends AbstractTcpState {
    public TcpClosingState(TcpContext tcpContext) {
        super(tcpContext);
    }

    public void recvAck() {
        TcpTimeWaitState nextState = new TcpTimeWaitState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("             recv: ACK");
        System.out.println("CLOSING -------------------> TIME_WAIT");
        System.out.println("          send: <nothing>");
    }
}
