package a03b.e1;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LazyTreeFactoryImpl implements LazyTreeFactory{

    @Override
    public <X> LazyTree<X> constantInfinite(X value) {
        return cons(Optional.ofNullable(value), () -> constantInfinite(value), () -> constantInfinite(value));
    }

    @Override
    public <X> LazyTree<X> fromMap(X root, Map<X, Pair<X, X>> map) {
        return this.cons(Optional.ofNullable(root), () -> fromMap(Objects.isNull(map.get(root)) ? null : map.get(root).getX(), map), 
            () -> fromMap(Objects.isNull(map.get(root)) ? null : (map.get(root).getY()), map));
    }

    @Override
    public <X> LazyTree<X> cons(Optional<X> root, Supplier<LazyTree<X>> leftSupp, Supplier<LazyTree<X>> rightSupp) {
        return new LazyTree<X>() {

            @Override
            public boolean hasRoot() {
                return root.isPresent();
            }

            @Override
            public X root() {
                return root.orElseThrow();
            }

            @Override
            public LazyTree<X> left() {
                return leftSupp.get();
            }

            @Override
            public LazyTree<X> right() {
                return rightSupp.get();
            }
            
        };
    }

    @Override
    public <X> LazyTree<X> fromTwoIterations(X root, UnaryOperator<X> leftOp, UnaryOperator<X> rightOp) {
        return this.cons(Optional.ofNullable(root), () -> fromTwoIterations(leftOp.apply(root), leftOp
        , rightOp),
         () -> fromTwoIterations(rightOp.apply(root), leftOp
        , rightOp));
    }

    @Override
    public <X> LazyTree<X> fromTreeWithBound(LazyTree<X> tree, int bound) {
        if(bound == 0 || !tree.hasRoot()){
            return this.constantInfinite(null);
        }
        return this.cons(Optional.ofNullable(tree.root()), () -> fromTreeWithBound(tree.left(), bound - 1), 
            () -> fromTreeWithBound(tree.right(), bound - 1)); 
        
    }
    
}
