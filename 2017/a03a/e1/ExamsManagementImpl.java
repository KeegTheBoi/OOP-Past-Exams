package a03a.e1;

import java.util.*;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ExamsManagementImpl implements ExamsManagement{
    Map<Integer, String> students;
    List<Pair<String, Integer>> exams;
    List<Pair<String, Integer>> studentRegistered;
    Deque<String> examsStatus;
    Map<Integer, Integer> evaluations; 
    @Override
    public void createStudent(int studentId, String name) {
        students.put(studentId, name);
    }

    @Override
    public void createExam(String examName, int incrementalId) {
        exams.add(new Pair<String,Integer>(examName,incrementalId));
    }

    @Override
    public void registerStudent(String examName, int studentId) {
        studentRegistered.add(new Pair<String,Integer>(examName, studentId));
    }

    @Override
    public void examStarted(String examName) {
        examsStatus.addLast(examName);

    }

    @Override
    public void registerEvaluation(int studentId, int evaluation) {
        if(studentRegistered.contains(new Pair<>(examsStatus.getLast(), studentId))) {
            evaluations.put(studentId, evaluation);
        }
    }

    @Override
    public void examFinished() {
        examsStatus.removeLast();
    }

    @Override
    public Set<Integer> examList(String examName) {
        return studentRegistered.stream().collect(Collectors.groupingBy(Pair::getX, Collectors.mapping(Pair::getY, Collectors.toSet()))).
            get(examName);
    }

    @Override
    public Optional<Integer> lastEvaluation(int studentId) {
        return exams.stream().max(Comparator.comparingInt(Pair::getY))
            .map(Pair::getX)
            .filter(s -> studentRegistered.contains(new Pair<String, Integer>(s, studentId)))
            .map(f -> studentRegistered.stream().filter(new Pair<String, Integer>(s, studentId)).findFirst().get())
            .map(Pair::getY)
            .filter(k -> evaluations.containsKey(s))
            .map(f -> evaluations.get(f));
    }

    @Override
    public Map<String, Integer> examStudentToEvaluation(String examName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'examStudentToEvaluation'");
    }

    @Override
    public Map<Integer, Integer> examEvaluationToCount(String examName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'examEvaluationToCount'");
    }

}
