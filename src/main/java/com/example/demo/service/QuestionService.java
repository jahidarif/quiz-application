package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.dao.QuestionDao;
import com.example.demo.model.Question; // ✅ Import the Question entity

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao; // ✅ Best practice: Use "private" access modifier

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Question>> getQuestionByCategory(String category)
    {
        try
        {
           return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String>  addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        if(questionDao.existsById(id)) {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Deletion Successfull",HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>("Deletion Successfull",HttpStatus.NO_CONTENT);
        }
    }
    public ResponseEntity<String> updateQuestion(Integer id, Question updatedQuestion)
    {
        if(questionDao.existsById(id))
        {
            Question existingQuestion=questionDao.findById(id).orElse(null);
            if(existingQuestion!=null)
            {
                existingQuestion.setOption4(updatedQuestion.getOption4());
                existingQuestion.setRight_answer(updatedQuestion.getRight_answer());
                questionDao.save(existingQuestion);
                return new ResponseEntity<>("updated successfully",HttpStatus.OK);

            }
            else
            {
                return new ResponseEntity<>("question does not exist",HttpStatus.NOT_FOUND);
            }
        }
        else
        {
            return new ResponseEntity<>("question does not exist",HttpStatus.NOT_FOUND);
        }
    }


}
