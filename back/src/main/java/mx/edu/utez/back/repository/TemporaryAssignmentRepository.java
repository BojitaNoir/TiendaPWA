package mx.edu.utez.back.repository;

import mx.edu.utez.back.model.TemporaryAssignment;
import org.springframework.stereotype.Repository;

@Repository
public class TemporaryAssignmentRepository extends AbstractFirestoreRepository<TemporaryAssignment> {
    public TemporaryAssignmentRepository() {
        super("temporary_assignments", TemporaryAssignment.class);
    }
}
