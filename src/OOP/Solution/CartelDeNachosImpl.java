package OOP.Solution;


import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;


public class CartelDeNachosImpl implements CartelDeNachos {
    Comparator<Profesor> compareProfesors = new Comparator<Profesor>() {
        @Override
        public int compare(Profesor p1, Profesor p2) {
            if(p1.getId() < p2.getId()) return -1;
            else if(p1.getId() > p2.getId()) return 1;
            return 0;
        }
    };
    Comparator<CasaDeBurrito> compareCasasId = new Comparator<CasaDeBurrito>() {
        @Override
        public int compare(CasaDeBurrito o1, CasaDeBurrito o2) {
            if(o1.getId() < o2.getId()) return -1;
            else if(o1.getId() > o2.getId()) return 1;
            return 0;

        }
    };
    Comparator<CasaDeBurrito> compareCasasDistance = new Comparator<CasaDeBurrito>() {
        @Override
        public int compare(CasaDeBurrito o1, CasaDeBurrito o2) {
            if(o1.distance() < o2.distance()) return -1;
            else if(o1.distance() > o2.distance()) return 1;
            else if (o1.averageRating() > o2.averageRating()) return -1;
            else if (o1.averageRating() < o2.averageRating()) return 1;
            else return Integer.compare(o1.getId(), o2.getId());
        }
    };

    Comparator<CasaDeBurrito> comparCasasRating = new Comparator<CasaDeBurrito>() {
        @Override
        public int compare(CasaDeBurrito o1, CasaDeBurrito o2) {
            if (o1.averageRating() > o2.averageRating()) return -1;
            else if (o1.averageRating() < o2.averageRating()) return 1;
            else if(o1.distance() < o2.distance()) return -1;
            else if(o1.distance() > o2.distance()) return 1;
            else return Integer.compare(o1.getId(), o2.getId());
        }
    };
    SortedSet<Profesor> _profesors = new TreeSet<Profesor>(compareProfesors);
    Set<CasaDeBurrito> _casas = new HashSet<CasaDeBurrito>();

    @Override
    public Profesor joinCartel(int id, String name) throws Profesor.ProfesorAlreadyInSystemException {
        return null;
    }

    @Override
    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu) throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        return null;
    }

    @Override
    public Collection<Profesor> registeredProfesores() {
        Set<Profesor> out = new HashSet<Profesor>();
        for(Profesor p : _profesors){

            out.add(p);
        }
        return out;
    }

    @Override
    public Collection<CasaDeBurrito> registeredCasasDeBurrito() {
        return null;
    }

    @Override
    public Profesor getProfesor(int id) throws Profesor.ProfesorNotInSystemException {
        return null;
    }

    @Override
    public CasaDeBurrito getCasaDeBurrito(int id) throws CasaDeBurrito.CasaDeBurritoNotInSystemException {
        for(CasaDeBurrito c : _casas){
            if(c.getId() == id ){
                return c;
            }
        }
        throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
    }

    @Override
    public CartelDeNachos addConnection(Profesor p1, Profesor p2) throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        boolean p1_in_system = false;
        boolean p2_in_system = false;
        for(Profesor p : _profesors){
            if(p1.equals(p)) p1_in_system = true;
            if(p2.equals(p)) p2_in_system = true;
        }
        if(!p1_in_system || !p2_in_system)
            throw new Profesor.ProfesorNotInSystemException();
        p1.addFriend(p2);
        p2.addFriend(p1);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(Profesor p) throws Profesor.ProfesorNotInSystemException {
        boolean p_in_system = false;
        for(Profesor p1 : _profesors){
            if (p1.equals(p)) {
                p_in_system = true;
                break;
            }
        }
        if(!p_in_system)
            throw new Profesor.ProfesorNotInSystemException();


        Set<CasaDeBurrito> out = new LinkedHashSet<>();
        SortedSet <Profesor> friends = new TreeSet<>(compareProfesors);
        friends.addAll(p.getFriends());

        for(Profesor friend: friends){
            SortedSet<CasaDeBurrito> casas = new TreeSet<>(comparCasasRating);
            out.addAll(casas);
        }
        return out;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(Profesor p) throws Profesor.ProfesorNotInSystemException {
        boolean p_in_system = false;
        for(Profesor p1 : _profesors){
            if (p1.equals(p)) {
                p_in_system = true;
                break;
            }
        }
        if(!p_in_system)
            throw new Profesor.ProfesorNotInSystemException();


        Set<CasaDeBurrito> out = new LinkedHashSet<>();
        SortedSet <Profesor> friends = new TreeSet<>(compareProfesors);
        friends.addAll(p.getFriends());

        for(Profesor friend: friends){
            SortedSet<CasaDeBurrito> casas = new TreeSet<>(compareCasasDistance);
            out.addAll(casas);
        }
        return out;
    }

    @Override
    public boolean getRecommendation(Profesor p, CasaDeBurrito c, int t) throws Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, ImpossibleConnectionException {
        return false;
    }

    @Override
    public List<Integer> getMostPopularRestaurantsIds() {
        return null;
    }

    /**
     * @return the cartel's description as a string in the following format:
     * <format>
     * Registered profesores: <profesorId1, profesorId2, profesorId3...>.
     * Registered casas de burrito: <casaId1, casaId2, casaId3...>.
     * Profesores:
     * <profesor1Id> -> [<friend1Id, friend2Id, friend3Id...>].
     * <profesor2Id> -> [<friend1Id, friend2Id, friend3Id...>].
     * ...
     * End profesores.
     * </format>
     * No newline at the end of the string.
     * Note: profesores, casas de burrito and friends' ids are ordered by natural integer order, asc.
     *
     * Example:
     *
     * Registered profesores: 1, 236703, 555555.
     * Registered casas de burrito: 12, 13.
     * Profesores:
     * 1 -> [236703, 555555555].
     * 236703 -> [1].
     * 555555 -> [1].
     * End profesores.
     * */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        SortedSet<CasaDeBurrito> casas = new TreeSet<>(compareCasasId);
        out.append("Registered profesores:");
        int i = 0;
        for(Profesor p : _profesors){
            if(i > 0){
                out.append(", ");
            }
            out.append(p.getId());
            i++;
        }
        out.append(".\n");
        out.append("Registered casas de burrito: ");
        i = 0;
        for(CasaDeBurrito c : casas){
            if(i > 0){
                out.append(", ");
            }
            out.append(c.getId());
            i++;
        }
        out.append(".\n");
        out.append("Profesores:\n");
        for(Profesor p : _profesors){
            out.append(p.getId());
            out.append(" -> [");
            SortedSet<Profesor> sorted_friends = new TreeSet<>(compareProfesors);
            sorted_friends.addAll(p.getFriends());
            i = 0;
            for(Profesor friend : sorted_friends){
                if(i > 0){
                    out.append(", ");
                }
                out.append(friend.getId());
                i++;
            }
            out.append("].\n");
        }
        out.append("End profesores.");
        return out.toString();
    }
}
