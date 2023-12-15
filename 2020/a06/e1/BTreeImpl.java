package a06.e1;

import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class BTreeImpl<L> implements BTree<L> {

    private boolean isLeaf;
    private L leaf;
    private BTree<L> left;
    private BTree<L> right;

    public BTreeImpl(boolean isLeaf, L leaf) {
        this.isLeaf = isLeaf;
        this.leaf = leaf;
    }

    public BTreeImpl(BTree<L> left, BTree<L> right) {
        this.isLeaf = false;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isLeaf() {
        return this.isLeaf;
    }

    @Override
    public L getLeaf(){
        if(this.isLeaf()) {
            return this.leaf;
        }
        throw new NoSuchElementException();
    }

    @Override
    public BTree<L> getLeft() {
        if(this.isLeaf()) {
            throw new NoSuchElementException();
        }
        return this.left;
    }

    @Override
    public BTree<L> getRight() {
        if(this.isLeaf()) {
            throw new NoSuchElementException();
        } 
        return this.right;
    }

    @Override
    public L compute(BinaryOperator<L> function) {
        return this.isLeaf() ? this.getLeaf() : function.apply(getLeft().compute(function), getRight().compute(function));
    
    }

   

    @Override
    public <O> BTree<O> map(Function<L, O> mapper) {
        return new BTreeImpl<O>(isLeaf, mapper.apply(this.getLeaf()));
    }

    @Override
    public BTree<L> symmetric() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'symmetric'");
    }

    
    
}
