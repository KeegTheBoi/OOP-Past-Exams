package a06a.e1;

import java.util.HashMap;
import java.util.Map;


public class SecureService implements SRService {
        protected enum State{
            FREE, SEND, REICEVE, WAIT
        }
        protected Map<Integer, State> mapper;
        private int count;

        public SecureService() {
            mapper = new HashMap<>();
        }

        @Override
        public int enter() {
            mapper.put(count, State.FREE);
            return count++;
        }

        @Override
        public void goReceive(int id) {
            if(notAccept(id) || this.mapper.get(id).equals(State.SEND)) {
                throw new IllegalStateException();
            }
        }

        @Override
        public void goSend(int id) {
              if(notAccept(id) || this.mapper.get(id).equals(State.REICEVE)) {
                throw new IllegalStateException();
            }
        }

        @Override
        public void exit(int id) {
           
            if(notAccept(id) || this.mapper.get(id).equals(State.WAIT) || this.mapper.get(id).equals(State.FREE)) {
                throw new IllegalStateException();
            }

        }
        private boolean notAccept(int id) {
            return !this.mapper.containsKey(id) || this.mapper.get(id).equals(State.WAIT);
        }

    }