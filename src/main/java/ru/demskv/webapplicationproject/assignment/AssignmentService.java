
package ru.demskv.webapplicationproject.assignment;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Singleton
public class AssignmentService {

        @EJB
        AssignmentDAO assignmentDAO;
        
        public List<Assignment> findAll() {
            return assignmentDAO.findAll();
        }
        
        public Optional<Assignment> findById(int id) {
            return assignmentDAO.findById(id);
        }
        
        public Optional<Assignment> create(Assignment assignment){
            return assignmentDAO.create(assignment);
        }
        
        public Assignment update(Assignment assignment){
            return assignmentDAO.update(assignment);
        }
        
        public boolean deleteById(int id){
            return assignmentDAO.delete(findById(id).get());
        }
}
