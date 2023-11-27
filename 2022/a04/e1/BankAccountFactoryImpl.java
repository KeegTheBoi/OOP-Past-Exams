package a04.e1;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class BankAccountFactoryImpl implements BankAccountFactory {


    private abstract class AbstractBankAccount implements BankAccount{

        protected int balance;
        @Override
        public int getBalance() {                
            return this.balance;
        }

        @Override
        public void deposit(int amount) {
            if(amount > 0){
                this.balance  += amount; 
            }               
        }

        @Override
        public abstract boolean withdraw(int amount);

    }

    private class GeneralBankAccount extends AbstractBankAccount{
        private boolean alwaysPossible;
        private final BiPredicate<Integer, Integer> allowPolicy;
        private boolean possible = true;

        public GeneralBankAccount(boolean alwaysPossible, BiPredicate<Integer, Integer> allowPolicy){
            this.alwaysPossible = alwaysPossible;
            this.allowPolicy = allowPolicy;
        }
        public boolean withdraw(int amount) {
            if(this.possible && !allowPolicy.test(amount, this.balance)){
                this.balance -= amount;
                return true;
            }
            if(!alwaysPossible){
                this.possible = false;
            }
            return false;
        }
        
    }

    @Override
    public BankAccount createBasic() {
        return new GeneralBankAccount(true, (a, b) -> a > b);
    }

    @Override
    public BankAccount createWithFee(UnaryOperator<Integer> feeFunction) {
        return new GeneralBankAccount(true, (a, b) -> a > b){
            @Override
            public boolean withdraw(int amount) {
                return super.withdraw(amount + feeFunction.apply(amount));
            }
        };
    }

    private class CreditBankAccount extends AbstractBankAccount{

        private final Predicate<Integer> allowCredit;
        private final UnaryOperator<Integer> rateFunction;
        public CreditBankAccount(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction){
            this.allowCredit = allowedCredit;
            this.rateFunction = rateFunction;
        }
        public boolean withdraw(int amount) {
            final int credit = this.balance - amount;
                if(allowCredit.test(-credit)){
                    this.balance -= amount + (credit < 0 ? rateFunction.apply(credit) : 0);
                    return true;
                }
                return false;
        }
        
    }

    @Override
    public BankAccount createWithCredit(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction) {
        return new CreditBankAccount(allowedCredit, rateFunction);
    }



    @Override
    public BankAccount createWithBlock(BiPredicate<Integer, Integer> blockingPolicy) {
        return new GeneralBankAccount(false, blockingPolicy);
    }

    private class CreditFeeBankAccount extends CreditBankAccount{
        private final UnaryOperator<Integer> feeOperator;
        public CreditFeeBankAccount(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction, UnaryOperator<Integer> feeOperator){
            super(allowedCredit, rateFunction);
            this.feeOperator = feeOperator;
        }
        public boolean withdraw(int amount) {
            return super.withdraw(amount + feeOperator.apply(amount));
        }
        
    }

    @Override
    public BankAccount createWithFeeAndCredit(UnaryOperator<Integer> feeFunction, Predicate<Integer> allowedCredit,
            UnaryOperator<Integer> rateFunction) {
        return new CreditFeeBankAccount(allowedCredit, rateFunction, feeFunction);
    }
    
}
