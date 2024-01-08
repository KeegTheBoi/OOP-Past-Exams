package a02b.e1;

import java.util.Arrays;
import java.util.List;

import static a02b.e1.ExamsFactory.OOPExamActivities.LAB_REGISTER;

import java.util.*;
import java.util.Set;
import java.util.stream.*;

public class ExamsFactoryImpl implements ExamsFactory {

    @Override
    public CourseExam<SimpleExamActivities> simpleExam() {
        return new CourseExam<SimpleExamActivities>() {

            Iterator<Set<SimpleExamActivities>> pending = List.of(
                    Set.of(SimpleExamActivities.WRITTEN),
                    Set.of(SimpleExamActivities.ORAL),
                    Set.of(SimpleExamActivities.REGISTER)
              
                ).iterator();
            Set<SimpleExamActivities> next = pending.next(); 

            @Override
            public Set<SimpleExamActivities> activities() {
                return Arrays.stream(SimpleExamActivities.values()).collect(Collectors.toSet());
                
            }

            @Override
            public Set<SimpleExamActivities> getPendingActivities() {
                return next;
            }

            @Override
            public void completed(SimpleExamActivities a) {
                Optional.of(a).filter(next::contains).ifPresent(i -> next = pending.next());
            }

            @Override
            public boolean examOver() {
                return !pending.hasNext();
            }
        };
    }

    @Override
    public CourseExam<OOPExamActivities> simpleOopExam() {
        return new CourseExam<OOPExamActivities>() {
            Iterator<Set<OOPExamActivities>> project = List.of(
                    Set.of(OOPExamActivities.PROJ_SUBMIT),
                    Set.of(OOPExamActivities.PROJ_EXAM)
                ).iterator();
            Iterator<Set<OOPExamActivities>> lab = List.of(
                    Set.of(OOPExamActivities.LAB_EXAM)
                ).iterator();
            Iterator<Set<OOPExamActivities>> initial = List.of(
                Set.of(OOPExamActivities.PROJ_PROPOSE, LAB_REGISTER)
            ).iterator();
            Iterator<Set<OOPExamActivities>> end = List.of(
                Set.of(OOPExamActivities.FINAL_EVALUATION)
            ).iterator();

            Iterator<Set<OOPExamActivities>> fork;
            Set<OOPExamActivities> next = initial.next();
            private int count;

            @Override
            public Set<OOPExamActivities> activities() {
                return Arrays.stream(OOPExamActivities.values()).collect(Collectors.toSet());
            }

            @Override
            public Set<OOPExamActivities> getPendingActivities() {
                return next;
            }

            @Override
            public void completed(OOPExamActivities a) {
                if(next.contains(a)) {
                    if(count++ == 0) {
                        fork = a.equals(LAB_REGISTER) ? lab : project;
                    }
                    next = fork.next();
                }
            }

            @Override
            public boolean examOver() {
                return !next.isEmpty();
            }
            
        };
    }

    @Override
    public CourseExam<OOPExamActivities> fullOopExam() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fullOopExam'");
    }

}
