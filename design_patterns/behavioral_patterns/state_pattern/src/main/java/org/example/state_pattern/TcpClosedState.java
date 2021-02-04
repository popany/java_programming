package org.example.state_pattern;

public class TcpClosedState extends AbstractTcpState {
    public TcpClosedState(TcpContext tcpContext) {
        super(tcpContext);
    }

    public void applPassiveOpen() {
        TcpListenState nextState = new TcpListenState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("        appl: passive open");
        System.out.println("CLOSE ----------------------> LISTEN");
        System.out.println("         send: <nothing>");
    }

    public void applActiveOpen() {
        TcpSynSentState nextState = new TcpSynSentState(getTcpContext());
        getTcpContext().setTcpState(nextState);

        System.out.println("        appl: active open");
        System.out.println("CLOSE ---------------------> SYN_SENT");
        System.out.println("            send: SYN");
    }
}
