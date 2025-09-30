package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.QuestionService; // ✅ Import QuestionService
import com.example.demo.model.Question; // ✅ Import Question entity
import java.util.List; // ✅ Import List

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService; // ✅ Fixed variable name (camelCase)

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {  // ✅ Fixed method name
        return questionService.getAllQuestions();  // ✅ Corrected method call
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category)
    {
        return questionService.getQuestionByCategory(category);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question)
    {
     return questionService.addQuestion(question);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id)
    {
        return questionService.deleteQuestion(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Integer id,@RequestBody Question updatedQuestion)
    {
        return questionService.updateQuestion(id,updatedQuestion);
    }

}
