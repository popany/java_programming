package org.example.state_pattern;

public class TcpSynSentState extends AbstractTcpState {
    public TcpSynSentState(TcpContext tcpContext) {
        super(tcpContext);
    }

    void changeToClose() {
        TcpClosedState nextState = new TcpClosedState(getTcpContext());
        getTcpContext().setTcpState(nextState);
    }

    public void applClose() {
        changeToClose();

        System.out.println("        appl: close");
        System.out.println("SYN_SENT -----------> CLOSE");
    }

    public void timeout() {
        changeToClose();

        System.out.println("           timeout ");
        System.out.println("SYN_SENT -----------> CLOSE");
    }

    public void recvSyn() {
        TcpSynRcvdState nextState = new TcpSynRcvdState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("             recv: SYN");
        System.out.println("SYN_SENT ------------------> SYN_RCVD");
        System.out.println("           send: SYN, ACK");
    }

    public void recvSynAck() {
        TcpEstablishedState nextState = new TcpEstablishedState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("           recv: SYN, ACK");
        System.out.println("SYN_SENT ------------------> ESTABLISHED");
        System.out.println("             send: ACK");
    }
}
