package a06.e1;

import java.util.List;

public class BTreeFactoryImpl implements BTreeFactory {


	
    @Override
	public <L> BTree<L> leaf(final L value) {
	    return new BTreeImpl<>(value);
	}
	
	/**
	 * @param <L>
	 * @param left
	 * @param right
	 * @return a tree made by left and right trees
	 */
	public <L> BTree<L> compose(final BTree<L> left, final BTree<L> right) {
	    return new BTreeImpl<>(left, right);
	}
	
	/**
	 * @param <L>
	 * @param leafs
	 * @return a tree whose leafs are exactly those in the argument, orderly.
	 * If input is list [1,2,3,4] it could return (1,(2,(3,4))) or (((1,2),3),4) or ((1,2),(3,4)), at choice
	 */
	public <L> BTree<L> nested(List<L> leafs) {
	    return leafs.size() == 2  ? 
                    compose(
                        leaf(leafs.get(0)),
                        leaf(leafs.get(1))
                    ) : 
                leafs.size() == 1 ? 
                    leaf(leafs.get(0)) :
	                compose(nested(leafs.subList(0, 2)) , nested(leafs.subList(2, leafs.size())));                                                                                                        
	}


}
