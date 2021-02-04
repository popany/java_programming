package org.example.state_pattern;

public class TcpTimeWaitState extends AbstractTcpState {
    public TcpTimeWaitState(TcpContext tcpContext) {
        super(tcpContext);
    }

    public void timeout() {
        TcpClosedState nextState = new TcpClosedState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("            2MSL timeout");
        System.out.println("TIME_WAIT ----------------> CLOSED");
    }
}
