
package ru.demskv.webapplicationproject.assignment;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class AssignmentService {

        @EJB
        AssignmentDAO assignmentDAO;
                 
         public Long countAll() {
            return assignmentDAO.countAll();
         }
        
        public List<Assignment> findAll(int from, int limit, String columnName, boolean desc, Integer filterId) {
            if(limit<=0) limit=1;
            return assignmentDAO.findAll(from, limit, columnName, desc, filterId);
        }
        
        public Optional<Assignment> findById(int id) {
            return assignmentDAO.findById(id);
        }
        
        public void create(Assignment assignment){
            assignmentDAO.create(assignment);
        }
        
        public void update(Assignment assignment){
            assignmentDAO.update(assignment);
        }
        
        public void deleteById(int id){
            assignmentDAO.deleteById(id);
        }
}
