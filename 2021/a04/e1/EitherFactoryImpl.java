package a04.e1;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class EitherFactoryImpl implements EitherFactory{

    @Override
    public <A, B> Either<A, B> success(B b) {
        return new AbstractEither<A,B>(){

            @Override
            public Pair<Optional<A>, Optional<B>> getCurrent() {
                return new Pair<Optional<A>,Optional<B>>(Optional.empty(), optionalOfFaulty(() -> b));
            }
            
        };
    }

    public <E> Optional<E> optionalOfFaulty(Supplier<E> supplier){
        try {
			return Optional.of(supplier.get());
		} catch (Exception e) {
			return Optional.empty();
		}
    } 

    public <E> Exception getException(Supplier<E> supplier){
        try {
            Optional.of(supplier.get());
			return null;
		} catch (Exception e) {
			return e;
		}
    } 


    @Override
    public <A, B> Either<A, B> failure(A a) {
        return new AbstractEither<A,B>(){

            @Override
            public Pair<Optional<A>, Optional<B>> getCurrent() {
                return new Pair<Optional<A>,Optional<B>>(optionalOfFaulty(() -> a), Optional.empty());
            }
            
        };
    }



    @Override
    public <B> Either<Exception, B> of(Supplier<B> computation) {
        return new AbstractEither<Exception ,B>(){

            @Override
            public Pair<Optional<Exception>, Optional<B>> getCurrent() {
                return new Pair<>(Optional.ofNullable(getException(computation)), optionalOfFaulty(computation));
            }
            
        };
    }

    @Override
    public <A, B, C> Either<A, List<C>> traverse(List<B> list, Function<B, Either<A, C>> function) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'traverse'");
    }
    
}
