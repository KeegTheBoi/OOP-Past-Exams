package a01a.e2;

import java.util.List;

public interface Model {
    public boolean handleClick(int x, int y);
    public boolean verifydiagonal();
    public <X> List<X> history();
}
