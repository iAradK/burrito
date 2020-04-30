package OOP.Solution;

import OOP.Provided.*;
import com.sun.source.tree.CompoundAssignmentTree;

import java.util.*;
import java.util.function.Predicate;




public class ProfesorImpl implements  Profesor{
    int id_ = 0;
    String name_ = "";
    Set<Profesor> friends_ = new HashSet<Profesor>();
    Set<CasaDeBurrito> fav_resturantes_ = new HashSet<CasaDeBurrito>();

    ProfesorImpl(int id, String name){
        id_ = id;
        name_ = name;
    }

    @Override
    public int getId() {
        return id_;
    }

    @Override
    public Profesor favorite(CasaDeBurrito c) throws UnratedFavoriteCasaDeBurritoException {
        if(c.isRatedBy(this) ) {
            fav_resturantes_.add(c);
            return this;
        }
        throw new UnratedFavoriteCasaDeBurritoException();
    }

    @Override
    public Collection<CasaDeBurrito> favorites() {
        Set<CasaDeBurrito> out = new HashSet<>();
        out = Set.copyOf(this.fav_resturantes_);
        return out;
    }

    @Override
    public Profesor addFriend(Profesor p) throws SameProfesorException, ConnectionAlreadyExistsException {
        if(this.equals(p)){ throw new SameProfesorException();}
        if(friends_.contains(p)){throw new ConnectionAlreadyExistsException();}
        else{
            friends_.add(p);
        }
        return this;
    }

    @Override
    public Set<Profesor> getFriends() {
        Set<Profesor> out = new HashSet<Profesor>();
        out = Set.copyOf(friends_);
        return out;
    }

    @Override
    public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
        Set<Profesor> out = new HashSet<Profesor>();
        for(Profesor cur : friends_){
            if(p.test(cur)){
                out.add(cur);
            }
        }
        return out;
    }

    @Override
    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
        SortedSet<CasaDeBurrito> out = new TreeSet<CasaDeBurrito>(comp);

        for(CasaDeBurrito cur : fav_resturantes_){
            if(p.test(cur)){
                out.add(cur);
            }
        }
        return out;

    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(int rLimit) {
        Predicate<CasaDeBurrito> predicate  = new Predicate<CasaDeBurrito>() {
            @Override
            public boolean test(CasaDeBurrito casaDeBurrito) {
                return casaDeBurrito.averageRating() >= rLimit;
            }
        };

        Comparator<CasaDeBurrito> comp = new Comparator<CasaDeBurrito>() {
            @Override
            public int compare(CasaDeBurrito o1, CasaDeBurrito o2) {
                if (o1.averageRating() > o2.averageRating()) return -1;
                if (o1.averageRating() < o2.averageRating()) return 1;
                if(o1.distance() < o2.distance()) return -1;
                if(o1.distance() > o2.distance()) return 1;
                if(o1.getId() < o2.getId()) return -1;
                if(o1.getId() >o2.getId()) return 1;
                return 0;
            }
        };

        return this.filterAndSortFavorites(comp, predicate);
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(int dLimit) {
        Predicate<CasaDeBurrito> predicate  = new Predicate<CasaDeBurrito>() {
            @Override
            public boolean test(CasaDeBurrito casaDeBurrito) {
                return casaDeBurrito.distance() <= dLimit;
            }
        };

        Comparator<CasaDeBurrito> comp = new Comparator<CasaDeBurrito>() {
            @Override
            public int compare(CasaDeBurrito o1, CasaDeBurrito o2) {
                if(o1.distance() < o2.distance()) return -1;
                if(o1.distance() > o2.distance()) return 1;
                if (o1.averageRating() > o2.averageRating()) return -1;
                if (o1.averageRating() < o2.averageRating()) return 1;
                if(o1.getId() < o2.getId()) return -1;
                if(o1.getId() >o2.getId()) return 1;
                return 0;
            }
        };
        return this.filterAndSortFavorites(comp, predicate);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Profesor)) return false;
        Profesor other = (Profesor)o;
        return this.id_ == other.getId();
    }

    @Override
    public String toString() {
        Predicate<CasaDeBurrito> predicate  = new Predicate<CasaDeBurrito>() {
            @Override
            public boolean test(CasaDeBurrito casaDeBurrito) {
                return true;
            }
        };

        Comparator<CasaDeBurrito> comp = new Comparator<CasaDeBurrito>() {
            @Override
            public int compare(CasaDeBurrito o1, CasaDeBurrito o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        Collection<CasaDeBurrito> casas = filterAndSortFavorites(comp, predicate);
        String out = new String();
        out += "Profesor: " + name_ + ".\n";
        out += "Id: " + id_ + ".";
        for(CasaDeBurrito casa : casas){
            out += "\n" + casa.getName() + ".";
        }

        return out;

    }

    @Override
    public int compareTo(Profesor o) {
        if(this.id_ < o.getId()) return -1;
        if(this.id_ > o.getId()) return 1;
        return 0;
    }
}
