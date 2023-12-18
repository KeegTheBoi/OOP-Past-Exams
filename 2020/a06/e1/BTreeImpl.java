
package a06.e1;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.*;

/**
 * the interface for a binary tree, where each leafs holds a value of type L
 * note a BTree is hence either a leaf, or a node with a left and right BTree
 */
public class BTreeImpl<L> implements BTree<L> {
    private final Optional<BTree<L>> left;
    private final Optional<BTree<L>> right;
    private final Optional<L> leaf;
    
    public BTreeImpl(final L leaf) {
        this.leaf = Optional.of(leaf);
        this.left = Optional.empty();
        this.right = Optional.empty();
    }
    
    public BTreeImpl(final BTree<L> left, final BTree<L> right) {
        this.leaf = Optional.empty();
        this.left = Optional.of(left);
        this.right = Optional.of(right);
    }

	@Override
	public boolean isLeaf() {
	    return this.leaf.isPresent();
	}
	
	 @Override
	public L getLeaf(){
	    return this.leaf.orElseThrow(NoSuchElementException::new);
	}

	 @Override
	public BTree<L> getLeft() {
	    return this.left.orElseThrow(NoSuchElementException::new);
	}
	
	 @Override
	public BTree<L> getRight() {
	    return this.right.orElseThrow(NoSuchElementException::new);
	}
	
	 @Override
	public L compute(BinaryOperator<L> function) {
	    return isLeaf() ? this.getLeaf() : function.apply(this.getLeft().compute(function), this.getRight().compute(function));
	}
	
	 @Override
	public <O> BTree<O> map(Function<L,O> mapper) {

	    return isLeaf() ? new BTreeImpl<O>(mapper.apply(this.getLeaf())) : new BTreeImpl<O>(this.getLeft().map(mapper), this.getRight().map(mapper));
	}
	
	 @Override
	public BTree<L> symmetric() {
	    return isLeaf() ? this : new BTreeImpl<L>(this.getRight().symmetric(), this.getLeft().symmetric());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BTreeImpl other = (BTreeImpl) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		if (leaf == null) {
			if (other.leaf != null)
				return false;
		} else if (!leaf.equals(other.leaf))
			return false;
		return true;
	}

	
}



