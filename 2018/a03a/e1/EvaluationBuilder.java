package a03a.e1;

import java.util.Map;

import a03a.sol1.Evaluation.*;

/**
 * A builder of ConferenceReviewing, with fluent interface and method build() to be called just once
 */
interface EvaluationBuilder {
	
	/**
	 * @param course
	 * @param student
	 * @param results
	 * @return this, and adds an evaluation: results must provide a result to each question
	 */
	EvaluationBuilder addEvaluationByMap(String course, int student, Map<a03a.e1.Evaluation.Question, a03a.e1.Evaluation.Result> results);
	
	/**
	 * @param course
	 * @param student
	 * @param results
	 * @return this, and provides a variation of the above method, directly specifying the three results
	 */
	EvaluationBuilder addEvaluationByResults(String course, int student, a03a.e1.Evaluation.Result fullyPositive, a03a.e1.Evaluation.Result weaklyPositive, a03a.e1.Evaluation.Result fullyPositive2);
	
	/**
	 * @return and builds the ConferenceReviewing
	 */
	Evaluation build();
}

