package a06a.e1;

import java.util.Map;

public class SRServiceFactoryImpl implements SRServiceFactory {



    public class LimitedClientService extends SecureService {
        
        private final int nReicevers;
        private final int nSenders;
        private int countReic;
        private int countSend;

        public LimitedClientService(int nReicevers, int nSenders) {
            this.nReicevers = nReicevers;
            this.nSenders = nSenders;
        }

        @Override
        public int enter() {
            return super.enter();
        }

        @Override
        public void goReceive(int id) {
            super.goReceive(id);
            if(mapper.get(id).equals(State.FREE) && countReic < nReicevers) {
                mapper.put(id, State.REICEVE);
                countReic++;
            }
            else {
                lockAll();
                super.goReceive(id);
            }
        }

        @Override
        public void goSend(int id) {
            super.goSend(id);
            if(mapper.get(id).equals(State.FREE) && countSend < nSenders) {
                mapper.put(id, State.SEND);
                countSend++;
            }
            else{
                lockAll();
                super.goSend(id);
            }
        }

        @Override
        public void exit(int id) {
            unlockAll();
            if(this.mapper.get(id).equals(State.SEND)) {
                countSend--;
            } 
            else {
                countReic--;
            }
            this.mapper.remove(id);
            super.exit(id);
            
           
        }

        private void lockAll() {
            this.mapper.forEach((k, v) -> {
                if(this.mapper.get(k).equals(State.FREE)) {
                    this.mapper.put(k, State.WAIT);
                }
            });
        }

        private void unlockAll() {
              this.mapper.forEach((k, v) -> {
                if(this.mapper.get(k).equals(State.WAIT)) {
                    this.mapper.put(k, State.FREE);
                }
            });
        }   
    }

    @Override
    public SRService createMaximumAccess() {
        return new LimitedClientService(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public SRService createWithNoConcurrentAccess() {
        return new LimitedClientService(1, 1);
    }

    @Override
    public SRService createManyReceiveAndMaxTwoSend() {
        return new LimitedClientService(Integer.MAX_VALUE, 2);
    }

}
