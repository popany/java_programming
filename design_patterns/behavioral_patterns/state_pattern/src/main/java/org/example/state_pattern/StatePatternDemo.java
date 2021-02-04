// https://refactoring.guru/design-patterns/state
// https://users.cs.northwestern.edu/~agupta/cs340/project2/TCPIP_State_Transition_Diagram.pdf

package org.example.state_pattern;

public class StatePatternDemo {
    static TcpContext tcpContext = new TcpContext();

    static void callAllStateTransitionAction(TcpState tcpState) {
        System.out.println("");
        tcpContext.setTcpState(tcpState);
        tcpContext.applPassiveOpen();

        System.out.println("");
        tcpContext.setTcpState(tcpState);
        tcpContext.applActiveOpen();

        System.out.println("");
        tcpContext.setTcpState(tcpState);
        tcpContext.applClose();

        System.out.println("");
        tcpContext.setTcpState(tcpState);
        tcpContext.applSendData();

        System.out.println("");
        tcpContext.setTcpState(tcpState);
        tcpContext.timeout(); 

        System.out.println("");
        tcpContext.setTcpState(tcpState);
        tcpContext.recvSyn();

        System.out.println("");
        tcpContext.setTcpState(tcpState);
        tcpContext.recvAck();

        System.out.println("");
        tcpContext.setTcpState(tcpState);
        tcpContext.recvSynAck();

        System.out.println("");
        tcpContext.setTcpState(tcpState);
        tcpContext.recvFin();

        System.out.println("");
        tcpContext.setTcpState(tcpState);
        tcpContext.recvFinAck();

        System.out.println("");
        tcpContext.setTcpState(tcpState);
        tcpContext.recvRst();
    }

    static void printTcpCloseStateTransition() {
        System.out.println("============================================");
        System.out.println("Tcp CLOSE state transition:");
        callAllStateTransitionAction(new TcpClosedState(tcpContext));
        System.out.println("");
    }

    static void printTcpListenStateTransition() {
        System.out.println("============================================");
        System.out.println("Tcp LISTEN state transition:");
        callAllStateTransitionAction(new TcpListenState(tcpContext));
        System.out.println("");
    }

    static void printTcpSynRcvdStateTransition() {
        System.out.println("============================================");
        System.out.println("Tcp SYN_RCVD state transition:");
        callAllStateTransitionAction(new TcpSynRcvdState(tcpContext));
        System.out.println("");
    }

    static void printTcpSynSentStateTransition() {
        System.out.println("============================================");
        System.out.println("Tcp SYN_SENT state transition:");
        callAllStateTransitionAction(new TcpSynSentState(tcpContext));
        System.out.println("");
    }

    static void printTcpEstablishedStateTransition() {
        System.out.println("============================================");
        System.out.println("Tcp ESTABLISHED state transition:");
        callAllStateTransitionAction(new TcpEstablishedState(tcpContext));
        System.out.println("");
    }

    static void printTcpFinWait1StateTransition() {
        System.out.println("============================================");
        System.out.println("Tcp FIN_WAIT_1 state transition:");
        callAllStateTransitionAction(new TcpFinWait1State(tcpContext));
        System.out.println("");
    }

    static void printTcpFinWait2StateTransition() {
        System.out.println("============================================");
        System.out.println("Tcp FIN_WAIT_2 state transition:");
        callAllStateTransitionAction(new TcpFinWait2State(tcpContext));
        System.out.println("");
    }

    static void printTcpTimeWaitStateTransition() {
        System.out.println("============================================");
        System.out.println("Tcp TIME_WAIT state transition:");
        callAllStateTransitionAction(new TcpTimeWaitState(tcpContext));
        System.out.println("");
    }

    static void printTcpClosingStateTransition() {
        System.out.println("============================================");
        System.out.println("Tcp CLOSING state transition:");
        callAllStateTransitionAction(new TcpClosingState(tcpContext));
        System.out.println("");
    }

    static void printTcpCloseWaitStateTransition() {
        System.out.println("============================================");
        System.out.println("Tcp CLOSE_WAIT state transition:");
        callAllStateTransitionAction(new TcpCloseWaitState(tcpContext));
        System.out.println("");
    }

    static void printTcpLastAckStateTransition() {
        System.out.println("============================================");
        System.out.println("Tcp LAST_ACK state transition:");
        callAllStateTransitionAction(new TcpLastAckState(tcpContext));
        System.out.println("");
    }

    static void printPassiveOpenProcess() {
        System.out.println("============================================");
        System.out.println("Tcp passive open process:");
        tcpContext.setTcpState(new TcpClosedState(tcpContext));
        System.out.println("");

        tcpContext.applPassiveOpen();
        System.out.println("");

        tcpContext.recvSyn();
        System.out.println("");

        tcpContext.recvAck();
        System.out.println("");
    }

    static void printActiveOpenProcess() {
        System.out.println("============================================");
        System.out.println("Tcp active open process:");
        tcpContext.setTcpState(new TcpClosedState(tcpContext));
        System.out.println("");

        tcpContext.applActiveOpen();
        System.out.println("");

        tcpContext.recvSynAck();
        System.out.println("");
    }

    static void printSimultaneousOpenProcess() {
        System.out.println("============================================");
        System.out.println("Tcp active open process:");
        tcpContext.setTcpState(new TcpClosedState(tcpContext));
        System.out.println("");

        tcpContext.applActiveOpen();
        System.out.println("");

        tcpContext.recvSyn();
        System.out.println("");
 
        tcpContext.recvAck();
        System.out.println("");
    }

    static void printPassiveCloseProcess() {
        System.out.println("============================================");
        System.out.println("Tcp passive close process:");
        tcpContext.setTcpState(new TcpEstablishedState(tcpContext));
        System.out.println("");

        tcpContext.recvFin();
        System.out.println("");

        tcpContext.applClose();
        System.out.println("");

        tcpContext.recvAck();
        System.out.println("");
    }

    static void printActiveCloseProcess() {
        System.out.println("============================================");
        System.out.println("Tcp active close process:");
        tcpContext.setTcpState(new TcpEstablishedState(tcpContext));
        System.out.println("");

        tcpContext.applClose();
        System.out.println("");

        tcpContext.recvAck();
        System.out.println("");

        tcpContext.recvFin();
        System.out.println("");

        tcpContext.timeout();
        System.out.println("");
    }

    static void printSimultaneousCloseProcess() {
        System.out.println("============================================");
        System.out.println("Tcp simultaneous close process:");
        tcpContext.setTcpState(new TcpEstablishedState(tcpContext));
        System.out.println("");

        tcpContext.applClose();
        System.out.println("");

        tcpContext.recvFin();
        System.out.println("");

        tcpContext.recvAck();
        System.out.println("");

        tcpContext.timeout();
        System.out.println("");
    }

    public static void main(String[] args) {
        printTcpCloseStateTransition();
        printTcpListenStateTransition();
        printTcpSynRcvdStateTransition();
        printTcpSynSentStateTransition();
        printTcpEstablishedStateTransition();
        printTcpFinWait1StateTransition();
        printTcpFinWait2StateTransition();
        printTcpTimeWaitStateTransition();
        printTcpClosingStateTransition();
        printTcpCloseWaitStateTransition();
        printTcpLastAckStateTransition();

        printPassiveOpenProcess();
        printActiveOpenProcess();
        printSimultaneousOpenProcess();           

        printPassiveCloseProcess();
        printActiveCloseProcess();
        printSimultaneousCloseProcess();           
    }
}
