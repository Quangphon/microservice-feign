package com.quangoi.quiz_service.service;

import com.quangoi.quiz_service.dao.QuizDao;
import com.quangoi.quiz_service.feign.QuizInterface;
import com.quangoi.quiz_service.model.QuestionWrapper;
import com.quangoi.quiz_service.model.Quiz;
import com.quangoi.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();

        ResponseEntity<List<QuestionWrapper>> questions= quizInterface.getQuestsFromId(questionIds);

        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        ResponseEntity<Integer> score = quizInterface.getScore(responses);

        return score;
    }
}
