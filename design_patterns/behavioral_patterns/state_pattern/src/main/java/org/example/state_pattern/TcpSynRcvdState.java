package org.example.state_pattern;

public class TcpSynRcvdState extends AbstractTcpState {
    boolean previousStateIsListen = false; 

    public TcpSynRcvdState(TcpContext tcpContext) {
        super(tcpContext);
    }

    public void setPreviousIsListen() {
        previousStateIsListen = true;
    } 

    public void recvAck() {
        TcpEstablishedState nextState = new TcpEstablishedState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("              recv: ACK");
        System.out.println("SYN_RCVD -------------------> ESTABLISHED");
        System.out.println("           send: <nothing>");
    }

    public void applClose() {
        TcpFinWait1State nextState = new TcpFinWait1State(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("           appl: close");
        System.out.println("SYN_RCVD ---------------> FIN_WAIT_1");
        System.out.println("            send: FIN");
    }

    public void recvRst() {
        if (previousStateIsListen) {
            TcpListenState nextState = new TcpListenState(getTcpContext());
            getTcpContext().setTcpState(nextState);

            System.out.println("           send: SYN, ACK");
            System.out.println("SYN_RCVD ------------------> LISTEN");
            System.out.println("             recv: RST");
        }
    }
}
