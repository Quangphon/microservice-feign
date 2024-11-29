package com.quangoi.question_service.controller;

import com.quangoi.question_service.model.Question;
import com.quangoi.question_service.model.QuestionWrapper;
import com.quangoi.question_service.model.Response;
import com.quangoi.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getALlQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    // Quiz Service
    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions) {
        return questionService.getQuestionsForQuiz(categoryName, numQuestions);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestsFromId(@RequestBody List<Integer> questionIds) {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromId( questionIds);
    }

    // Quiz Service
    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> response) {
        return questionService.getScore(response);
    }
}
