package org.example.state_pattern;

public class TcpListenState extends AbstractTcpState {
    public TcpListenState(TcpContext tcpContext) {
        super(tcpContext);
    }
    
    public void applSendData() {
        System.out.println("Not supported by Berkeley sockets: ");

        System.out.println("        appl: send data");
        System.out.println("LISTEN ------------------> SYN_SENT");
        System.out.println("            send: SYN");
    }

    public void recvSyn() {
        TcpSynRcvdState nextState = new TcpSynRcvdState(getTcpContext());
        nextState.setPreviousIsListen();
        getTcpContext().setTcpState(nextState);

        System.out.println("           recv: SYN");
        System.out.println("LISTEN ------------------> SYN_RCVD");
        System.out.println("         send: SYN, ACK");
    }
}
