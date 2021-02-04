package org.example.state_pattern;

public class TcpCloseWaitState extends AbstractTcpState {
    public TcpCloseWaitState(TcpContext tcpContext) {
        super(tcpContext);
    }

    public void applClose() {
        TcpLastAckState nextState = new TcpLastAckState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("             appl: close");
        System.out.println("CLOSE_WAIT ---------------> LAST_ACK");
        System.out.println("              send: FIN");
    }
}
