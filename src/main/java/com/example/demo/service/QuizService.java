package com.example.demo.service;

import com.example.demo.dao.QuestionDao;
import com.example.demo.dao.QuizDao;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionWrapper;
import com.example.demo.model.Quiz;
import com.example.demo.model.Response;
import org.hibernate.event.spi.ResolveNaturalIdEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private QuizDao quizDao;

    @Transactional
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        // Ensure there are questions for the given category and numQ
        if (numQ <= 0) {
            return new ResponseEntity<>("Number of questions must be greater than 0", HttpStatus.BAD_REQUEST);
        }

        List<Question> questions = questionDao.findQuestionByCategory(category, numQ);

        if (questions.isEmpty()) {
            return new ResponseEntity<>("No questions found for the given category", HttpStatus.BAD_REQUEST);
        }

        // Create and save the quiz
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizDao.save(quiz);

        return new ResponseEntity<>("Quiz created successfully", HttpStatus.OK);
    }
    public ResponseEntity<List<QuestionWrapper>> getQuizById(Integer id)
    {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question> questionFromdb=quiz.get().getQuestions();
        List<QuestionWrapper> questionFrouser=new ArrayList<>();
        for(Question q:questionFromdb)
        {
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestion_title(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionFrouser.add(qw);

        }
        return new ResponseEntity<>(questionFrouser,HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculate(Integer id, List<Response> responses) {
        Quiz quiz=quizDao.findById(id).get();
        List<Question> questions=quiz.getQuestions();
        Integer right=0;
        Integer i=0;
        for(Response response:responses)
        {
            if(response.getResponse().equals(questions.get(i).getRight_answer()))
            {
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);

    }
}
