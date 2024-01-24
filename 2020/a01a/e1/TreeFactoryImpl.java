package a01a.e1;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TreeFactoryImpl implements TreeFactory {

    @Override
    public <E> Tree<E> singleValue(E root) {
        return new Tree<E>() {

            @Override
            public E getRoot() {
                return root;
            }

            @Override
            public List<Tree<E>> getChildren() {
                return Collections.emptyList();
            }

            @Override
            public Set<E> getLeafs() {
                return this.getAll();
            }

            @Override
            public Set<E> getAll() {
                return Collections.singleton(root);
            }

            public String toString() {
                return root + "[]";
            }
            
        };
    }

    private <E> Tree<E> oneLevelChildre(E root, List<Tree<E>> childrens){
        return new Tree<E>() {

            @Override
            public E getRoot() {
                return root;
            }

            @Override
            public List<Tree<E>> getChildren() {
                return childrens;
            }

            @Override
            public Set<E> getLeafs() {
                return this.getChildren().stream().flatMap(c -> c.getLeafs().stream()).collect(Collectors.toSet());
            }

            @Override
            public Set<E> getAll() {
                var withRoot = this.getChildren().stream().flatMap(c -> c.getAll().stream()).collect(Collectors.toSet());
                withRoot.add(root);
                return withRoot;
            }

            public String toString() {
                var t = this.getChildren().stream().map(c -> c.getRoot() + c.getChildren().toString()).collect(Collectors.joining(", "));
                return root + "[" + t + "]";
            }
            
        };
    }

    @Override
    public <E> Tree<E> twoChildren(E root, Tree<E> child1, Tree<E> child2) {
        return oneLevelChildre(root, List.of(child1, child2));
    }

    @Override
    public <E> Tree<E> oneLevel(E root, List<E> children) {
        return oneLevelChildre(root, children.stream().map(this::singleValue).collect(Collectors.toList()));
    }

    @Override
    public <E> Tree<E> chain(E root, List<E> list) {
        
        return null;
    }

}
