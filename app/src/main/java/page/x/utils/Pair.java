package page.x.utils;

public class Pair<T1, T2> {

    private T1 pair1;

    private T2 pair2;

    public Pair(T1 pair1, T2 pair2) {
        this.pair1 = pair1;
        this.pair2 = pair2;
    }

    public T1 getPair1() {
        return this.pair1;
    }

    public T2 getPair2() {
        return this.pair2;
    }

    public void setPair1(T1 pair1) {
        this.pair1 = pair1;
    }

    public void setPair2(T2 pair2) {
        this.pair2 = pair2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pair1 == null) ? 0 : pair1.hashCode());
        result = prime * result + ((pair2 == null) ? 0 : pair2.hashCode());
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
        Pair<?, ?> other = (Pair<?, ?>) obj;
        if (pair1 == null) {
            if (other.pair1 != null)
                return false;
        } else if (!pair1.equals(other.pair1))
            return false;
        if (pair2 == null) {
            if (other.pair2 != null)
                return false;
        } else if (!pair2.equals(other.pair2))
            return false;
        return true;
    }    

}
