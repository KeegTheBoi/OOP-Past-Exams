package a04.e2;

import java.util.Random;
import java.util.function.BiFunction;

public class Operator {

        public enum Operand{

            ADD(Integer::sum), MINUS((x, y) -> x - y), MULTIPLY((x, y) -> x * y);

            private BiFunction<Integer, Integer, Integer> trasf;
            
            private Operand(BiFunction<Integer, Integer, Integer> trasf) {
                this.trasf = trasf;
            }


            public int getResult(int x, int y) {
                return trasf.apply(x, y);
            }

            public static Operand getRand() {
                return Operand.values()[new Random().nextInt(1, values().length)];
            }
        }

        private int val;
        private Operand operand;
        private boolean isValue;
        

        public Operand getOperand() {
            return this.operand;
        }

        public boolean isValue() {
            return isValue;
        }

        public int getVal() {
            return val;
        }

        public Operator(int val){
            this.val = val;
            isValue = true;
        }

        public Operator(Operand oper) {
            this.operand = oper;
        }

    }
