package a06.e1;

import java.util.List;

public class BTreeFactoryImpl implements BTreeFactory {

    @Override
    public <L> BTree<L> leaf(L value) {
        return new BTreeImpl<L>(true, value);
    }

    @Override
    public <L> BTree<L> compose(BTree<L> left, BTree<L> right) {
        return new BTreeImpl<L>(left, right);
    }

    @Override
    public <L> BTree<L> nested(List<L> leafs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nested'");
    }

}
