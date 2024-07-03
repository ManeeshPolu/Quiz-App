package com.maneesh.quizapp.controller;


import com.maneesh.quizapp.model.Question;
import com.maneesh.quizapp.model.QuestionWrapper;
import com.maneesh.quizapp.model.Response;
import com.maneesh.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController

{
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int numQ, @RequestParam String title)
    {
        return quizService.createQuiz(category,numQ,title);

    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(@PathVariable Integer id){
        return quizService.getQuizQuestion(id);

    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> calculate(@PathVariable Integer id, @RequestBody List<Response> responses)
    {

        return quizService.calculate(id,responses);
    }


}
