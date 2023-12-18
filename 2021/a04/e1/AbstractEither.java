package a04.e1;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractEither<A,B> implements Either<A, B>{
    private boolean failed;
    private Optional<B> cur;
    private B acceptor;

    public AbstractEither(){
    }

    public abstract Pair<Optional<A>, Optional<B>> getCurrent();

    @Override
    public boolean isFailure() {
        return this.getFailure().isPresent();
    }

    @Override
    public boolean isSuccess() {
        return this.getSuccess().isPresent();
    }

    @Override
    public Optional<A> getFailure() {
        return this.getCurrent().get1();
    }

    @Override
    public Optional<B> getSuccess() {
        return this.getCurrent().get2();
    }

    @Override
    public B orElse(B other) {
        return this.getCurrent().get2().orElse(other);
    }

    @Override
    public <B1> Either<A, B1> map(Function<B, B1> function){
        Optional<B1> mapper;
            
        mapper = this.getCurrent().get2().map(function);
        return new AbstractEither<A,B1>() {

            @Override
            public Pair<Optional<A>, Optional<B1>> getCurrent() {
                return new Pair<>(Optional.empty(), mapper);
            } 
        };       
        
    }

    @Override
    public <B1> Either<A, B1> flatMap(Function<B, Either<A, B1>> function) {
        return null;
    }

    @Override
    public <A1> Either<A1,B> filterOrElse(Predicate<B> predicate, A1 failure) {
        // TODO Auto-generated method stub
        Optional<B> filter = this.getCurrent().get2().filter(predicate);
        return new AbstractEither<A1,B>() {

            @Override
            public Pair<Optional<A1>, Optional<B>> getCurrent() {
                return new Pair<>(Optional.of(failure), filter);
            }
            
        };
    }

    @Override
    public <C> C fold(Function<A,C> funFailure, Function<B,C> funSuccess) {
        return this.isSuccess() ? 
            funSuccess.apply(this.getSuccess().get()) 
            :funFailure.apply(this.getFailure().get());
    }
}
