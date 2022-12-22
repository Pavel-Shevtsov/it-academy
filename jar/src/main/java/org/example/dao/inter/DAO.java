package org.example.dao.inter;

public interface DAO <P,T>{
    void add (P p);
    void delete (T  id);
    void update (P p);
    P getById (T id);
}
