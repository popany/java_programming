package org.example.state_pattern;

public class TcpFinWait2State extends AbstractTcpState {
    public TcpFinWait2State(TcpContext tcpContext) {
        super(tcpContext);
    }

    public void recvFin() {
        TcpTimeWaitState nextState = new TcpTimeWaitState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("             recv: FIN");
        System.out.println("FIN_WAIT_2 -------------> TIME_WAIT");
        System.out.println("             send: ACK");
    }
}
