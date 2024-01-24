package a02a.e1;

import java.util.Objects;

public class SequenceAcceptorImpl implements SequenceAcceptor {
    
    SequenceAcceptorSingleton basic = SequenceAcceptorSingleton.getInstance();
    Sequence seq;

    public SequenceAcceptorImpl() {

    }

    @Override
    public void reset(Sequence sequence) {
        this.seq = sequence;
        switch (sequence) {
            case POWER2:
                basic = SequenceAcceptorSingleton.powerUp();
                break;
            case FLIP:
            basic = SequenceAcceptorSingleton.flip();
                break;
            case RAMBLE:
                basic = SequenceAcceptorSingleton.ramble();
                break;
            case FIBONACCI:
                basic = SequenceAcceptorSingleton.fibonacci();
                break;
        }   
    }

    @Override
    public void reset() {
        if(!Objects.isNull(seq)) {
            reset(this.seq);
            return;
        }
        throw new IllegalStateException();
    }

    @Override
    public void acceptElement(int i) {
        if(Objects.isNull(seq)) {
            throw new IllegalStateException();
        }
        if(basic.getIter().next() != i) {
            throw new IllegalStateException();
        }
    }


}
