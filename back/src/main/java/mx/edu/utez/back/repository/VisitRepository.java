package mx.edu.utez.back.repository;

import mx.edu.utez.back.model.Visit;
import org.springframework.stereotype.Repository;

@Repository
public class VisitRepository extends AbstractFirestoreRepository<Visit> {
    public VisitRepository() {
        super("visits", Visit.class);
    }
}
