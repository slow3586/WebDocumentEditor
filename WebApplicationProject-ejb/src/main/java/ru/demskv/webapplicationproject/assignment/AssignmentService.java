
package ru.demskv.webapplicationproject.assignment;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless(name="AssignmentServiceEJB")
public class AssignmentService implements AssignmentServiceLocal {

        @EJB(beanName="AssignmentDAOEJB")
        AssignmentDAOLocal assignmentDAO;
                 
        @Override
         public Long countAll() {
            return assignmentDAO.countAll();
         }
        
        @Override
        public List<Assignment> findAll(int from, int limit, String columnName, boolean desc, Integer filterId) {
            if(limit<=0) limit=1;
            return assignmentDAO.findAll(from, limit, columnName, desc, filterId);
        }
        
        @Override
        public Optional<Assignment> findById(int id) {
            return assignmentDAO.findById(id);
        }
        
        @Override
        public void create(Assignment assignment){
            assignmentDAO.create(assignment);
        }
        
        @Override
        public void update(Assignment assignment){
            assignmentDAO.update(assignment);
        }
        
        @Override
        public void deleteById(int id){
            assignmentDAO.deleteById(id);
        }
}
