package a03a.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import a03a.e1.Evaluation.Question;
import a03a.e1.Evaluation.Result;

public class EvaluationBuilderImpl implements EvaluationBuilder {

    private record StudentResult(String course, int student, Map<Question, Result> map) {}

    List<StudentResult> list = new ArrayList<>();

    @Override
    public EvaluationBuilder addEvaluationByMap(String course, int student, Map<Question, Result> results) {
        list.add(new StudentResult(course, student, results));
        return this;
    }

    @Override
    public EvaluationBuilder addEvaluationByResults(String course, int student, Result resOverall, Result resInterest,
            Result resClarity) {
        list.add(new StudentResult(course, student, 
                Map.<Question, Result>of(
                    Question.OVERALL, resOverall,
                    Question.INTEREST, resInterest,
                    Question.CLARITY, resClarity
                )
            )
        );
            return this;
    }

    @Override
    public Evaluation build() {
        return new Evaluation() {

            @Override
            public Map<Question, Result> results(String course, int student) {
                return list.stream().filter(r -> r.course().equals(course) && r.student() == student)
                    .map(StudentResult::map).findFirst().orElse(Map.of());
            }

            @Override
            public Map<Result, Long> resultsCountForCourseAndQuestion(String course, Question questions) {
                return list.stream().filter(p -> p.course.equals(course))
                    .map(StudentResult::map)
                    .collect(Collectors.groupingBy(m -> m.get(questions), Collectors.counting()));
            }

            @Override
            public Map<Result, Long> resultsCountForStudent(int student) {
                return list.stream().filter(p -> p.student == student)
                    .map(StudentResult::map)
                    .map(Map::entrySet)
                    .flatMap(Set::stream)
                    .map(Map.Entry::getValue)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())); 
            }

            @Override
            public double coursePositiveResultsRatio(String course, Question question) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'coursePositiveResultsRatio'");
            }
            
        };
    }

}
