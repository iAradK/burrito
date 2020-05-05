package OOP.Solution;


import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CartelDeNachosImpl implements CartelDeNachos {

    private final SortedSet<Profesor> _profesors;
    private Set<CasaDeBurrito> _casas;

    public CartelDeNachosImpl(){
        _profesors = new TreeSet<Profesor>(compareProfesors);
        _casas = new HashSet<CasaDeBurrito>();

    }

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


    @Override
    public Profesor joinCartel(int id, String name) throws Profesor.ProfesorAlreadyInSystemException {
        Profesor prof = new ProfesorImpl(id, name);
        if (this._profesors.contains(prof) == true) throw new Profesor.ProfesorAlreadyInSystemException();
        _profesors.add(prof);
        return prof;
    }

    @Override
    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu)
                                                throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        for (CasaDeBurrito c : this._casas) {
            if (c.getId() == id) throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
        }
        CasaDeBurrito casa = new CasaDeBurritoImpl(id, name, dist, menu);
        _casas.add(casa);
        return casa;
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
        Set<CasaDeBurrito> out = new HashSet<CasaDeBurrito>();
        for(CasaDeBurrito c : _casas){
            out.add(c);
        }
        return out;
    }

    @Override
    public Profesor getProfesor(int id) throws Profesor.ProfesorNotInSystemException {
        for (Profesor p : this._profesors) {
            if (p.getId() == id) return p;
        }
        throw new Profesor.ProfesorNotInSystemException();
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
        if (this._profesors.contains(p) == false) throw new Profesor.ProfesorNotInSystemException();
        if (this._casas.contains(c) == false) throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        if (t < 0) throw new ImpossibleConnectionException();
        List<pairOf_Prof_t> visited = new LinkedList<>();
        return aux_getRecommendation(p, c, t, 0 ,visited);
    }

    private class pairOf_Prof_t { // Cause we cant the pair lib
        int so_far;
        Profesor p;
        pairOf_Prof_t(int _t, Profesor _p) {
            so_far = _t;
            p = _p;
        }

        int GetT() {
            return so_far;
        }

        Profesor GetProfesor() {
            return p;
        }
    }
    
    private boolean SetContainsPair(List<pairOf_Prof_t> visited, pairOf_Prof_t pair) {
        for (pairOf_Prof_t p : visited) {
            if (p.GetProfesor() == pair.GetProfesor() &&
                        p.so_far <= pair.so_far) return true;
        }
        return false;
    }

    private boolean aux_getRecommendation(Profesor p, CasaDeBurrito c, int t, int so_far,
                                          List<pairOf_Prof_t> visited) {
        if (p.favorites().contains(c)) return true;
        if (t == 0) return false; // Cause it is not in p
        for (Profesor friend : p.getFriends()) {
            pairOf_Prof_t pair = new pairOf_Prof_t(so_far, friend);
            if (SetContainsPair(visited, pair) == false) { // Haven't checked yet
                visited.add(pair);
                if (aux_getRecommendation(friend, c, t-1, so_far+1, visited)) return true;
            }
        }
        return false;
    }

    private int clacScoreByProfesor(Profesor p, CasaDeBurrito c) {
        int score = 0;
        for (Profesor friend : p.getFriends()) {
            if (friend.favorites().contains(c) == true) {
                score++;
            }
        }
        return score;
    }

    private int getScoreOfCasa(CasaDeBurrito c) {
        int score = 0;
        for (Profesor p : this._profesors) { // Clac total score
            score += clacScoreByProfesor(p, c);
        }
        return score;
    }

    private class pairOf_id_score { // Cause we cant the pair lib
        int score;
        Integer id;

        pairOf_id_score(int _id, Integer _score) {
            id = _id;
            score = _score;
        }

        public int compareTo(pairOf_id_score p) {
            return this.score - p.score;
        }

        public boolean equals(pairOf_id_score p) {
            if (p == null) return false;
            else return (this.id == p.id);
        }
    }

    @Override
    public List<Integer> getMostPopularRestaurantsIds() {
        List<pairOf_id_score> score_list = new LinkedList<pairOf_id_score>();
        for (CasaDeBurrito c : this._casas) {
            int score = getScoreOfCasa(c);
            pairOf_id_score pair = new pairOf_id_score(c.getId(), score);
            score_list.add(pair);
        }

        score_list = score_list.stream().sorted().collect(Collectors.toList());

        List<Integer> ids = new LinkedList<Integer>();

        for (pairOf_id_score i : score_list) {
            ids.add(i.id);
        }

        return ids;
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
        // SortedSet<CasaDeBurrito> casas = new TreeSet<>(compareCasasId);
        out.append("Registered profesores: ");
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

        SortedSet sortedSet = new TreeSet(_casas);
        _casas = sortedSet; // Sort casas
        for(CasaDeBurrito c : _casas){
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