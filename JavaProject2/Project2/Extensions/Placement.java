package Project2.Extensions;

public class Placement {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public Placement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Placement() {
        x = 0;
        y = 0;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof Placement)) return false;
        Placement objectPlacement = (Placement) object;
        return Integer.compare(x, objectPlacement.x) == 0 && Integer.compare(y, objectPlacement.y) == 0;
    }
};
