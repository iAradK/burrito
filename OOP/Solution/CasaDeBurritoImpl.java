package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;

public class CasaDeBurritoImpl implements CasaDeBurrito {
    private final int _id;
    private final String _name;
    private final int _dist;
    private Set<String> _menu;
    private Map<Profesor, Integer> prof_list;

    public CasaDeBurritoImpl(int id, String name, int dist, Set<String> menu){
        _id = id;
        _name = name;
        _dist = dist;
        _menu = menu;
        prof_list = new HashMap<Profesor, Integer>();
    }

    /**
     * @return the id of the casa de burrito.
     * */
    public int getId() {
        return _id;
    }

    /**
     * @return the name of the casa de burrito.
     * */
    public String getName() {
        return _name;
    }

    /**
     * @return the distance from the Technion.*/
    public int distance() {
        return _dist;
    }

    /**
     * @return true iff the profesor rated this CasaDeBurrito
     * @param p - a profesor
     * */
    public boolean isRatedBy(Profesor p) {
        return prof_list.containsKey(p);
    }

    /**
     * rate the CasaDeBurrito by a profesor
     * @return the object to allow concatenation of function calls.
     * @param p - the profesor rating the CasaDeBurrito
     * @param r - the rating
     * */
    public CasaDeBurrito rate(Profesor p, int r) throws RateRangeException {
        if (r < 0 || r > 5) throw new RateRangeException();
        prof_list.put(p, r);
        return this;
    }

    /**
     * @return the number of rating the CasaDeBurrito has received
     * */
    public int numberOfRates() {
        return prof_list.size();
    }

    /**
     * @return the CasaDeBurrito's average rating
     * */
    public double averageRating() {
        if (numberOfRates() == 0) return 0;
        int sum = prof_list.values().stream().reduce(0, Integer::sum);
        return (1.0*sum/numberOfRates());
    }

    /**
     * @return the CasaDeBurrito's description as a string in the following format:
     * <format>
     * CasaDeBurrito: <name>.
     * Id: <id>.
     * Distance: <dist>.
     * Menu: <menuItem1, menuItem2, menuItem3...>.
     * </format>
     * No newline at the end of the string.
     * Note: Menu items are ordered by lexicographical order, asc.
     *
     * Example:
     *
     * CasaDeBurrito: BBB.
     * Id: 1.
     * Distance: 5.
     * Menu: Cola, French Fries, Steak.
     * */
    @Override
    public String toString() {
        SortedSet sortedSet = new TreeSet(_menu);
        _menu = sortedSet;
        String ret = "CasaDeBurrito: " + _name + ".\nId: " + _id + ".\nDistance: " +
                _dist + ".\nMenu: ";

        boolean first = true;
        for (String temp : _menu) {
            if (first == false) ret += ", ";
            ret += temp;
            first = false;
        }

        ret += ".";
        return ret;
    }

    @Override
    public int compareTo(CasaDeBurrito c) {
        return _id - c.getId();
    }

    @Override
    public boolean equals(CasaDeBurrito c) {
        if (c == null) return false;
        else return (c.getId() == _id);
    }
}
