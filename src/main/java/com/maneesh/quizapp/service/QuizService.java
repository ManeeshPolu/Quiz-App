package com.maneesh.quizapp.service;


import com.maneesh.quizapp.model.Question;
import com.maneesh.quizapp.model.QuestionWrapper;
import com.maneesh.quizapp.model.Quiz;
import com.maneesh.quizapp.model.Response;
import com.maneesh.quizapp.repository.QuestionDao;
import com.maneesh.quizapp.repository.QuizDao;
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
    QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();

        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);



    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {

        Optional<Quiz> quiz = quizDao.findById(id);

        List<Question> questionFromDB = quiz.get().getQuestions();

        List<QuestionWrapper> questionToUser = new ArrayList<>();

        for(Question q:questionFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionToUser.add(qw);
        }



        return new ResponseEntity<>(questionToUser,HttpStatus.OK);



    }

    public ResponseEntity<Integer> calculate(Integer id, List<Response> responses) {

        Quiz quiz = quizDao.findById(id).get();

        List<Question> question = quiz.getQuestions();

        int right =0;
        int i=0;
        for(Response r : responses)
        {
            if(r.getResponse().equals(question.get(i).getRightAnswer()))
                right++;

            i++;
        }

        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
