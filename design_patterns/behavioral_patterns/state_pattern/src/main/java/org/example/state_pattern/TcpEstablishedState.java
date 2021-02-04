package org.example.state_pattern;

public class TcpEstablishedState extends AbstractTcpState {
    public TcpEstablishedState(TcpContext tcpContext) {
        super(tcpContext);
    }
    
    public void applClose() {
        TcpFinWait1State nextState = new TcpFinWait1State(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("              appl: close");
        System.out.println("ESTABLISHED ---------------> FIN_WAIT_1");
        System.out.println("               send: FIN");
    }

    public void recvFin() {
        TcpCloseWaitState nextState = new TcpCloseWaitState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("              recv: FIN");
        System.out.println("ESTABLISHED -------------> CLOSE_WAIT");
        System.out.println("              send: ACK");
    }
}
