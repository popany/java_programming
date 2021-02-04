package org.example.state_pattern;

public class TcpLastAckState extends AbstractTcpState {
    public TcpLastAckState(TcpContext tcpContext) {
        super(tcpContext);
    }

    public void recvAck() {
        TcpClosedState nextState = new TcpClosedState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("              recv: ACK");
        System.out.println("LAST_ACK -------------------> CLOSED");
        System.out.println("           send: <nothing>");
    }
}
