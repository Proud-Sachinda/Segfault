package com.Controllers;

import java.util.ArrayList;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

public class QuestionController {

    // question items
    private ArrayList<Question> questionArrayList;

    public void addQuestionItem(int used, int marks, String code,
                                String date, int difficulty, String subject,
                                String question, boolean variant, String published,
                                String [] tags){

        // create new question item
        Question item = new Question();

        // set variables
        item.setUsed(used);
        item.setMarks(marks);
        item.setCode(code);
        item.setDate(date);
        item.setDifficulty(difficulty);
        item.setSubject(subject);
        item.setQuestion(question);
        item.setVariant(variant);
        item.setPublished(published);
        item.setTags(tags);

        // add to question list
        questionArrayList.add(item);
    }

    public ArrayList<Question> getQuestionArrayList() {
        return questionArrayList;
    }

    public class Question extends Model {
        

        // attributes
        private int used;
        private int marks;
        private String code;
        private String date;
        private int difficulty;
        private String subject;
        private String question;
        private boolean variant;
        private String published;
        private String [] tags;

        public int getUsed() {
            return used;
        }

        private void setUsed(int used) {
            this.used = used;
        }

        public int getMarks() {
            return marks;
        }

        private void setMarks(int marks) {
            this.marks = marks;
        }

        public String getCode() {
            return code;
        }

        private void setCode(String code) {
            this.code = code;
        }

        public String getDate() {
            return date;
        }

        private void setDate(String date) {
            this.date = date;
        }

        public int getDifficulty() {
            return difficulty;
        }

        private void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public String getSubject() {
            return subject;
        }

        private void setSubject(String subject) {
            this.subject = subject;
        }

        public String getQuestion() {
            return question;
        }

        private void setQuestion(String question) {
            this.question = question;
        }

        public boolean isVariant() {
            return variant;
        }

        private void setVariant(boolean variant) {
            this.variant = variant;
        }

        public String getPublished() {
            return published;
        }

        private void setPublished(String published) {
            this.published = published;
        }

        public String [] getTags() {
            return tags;
        }

        private void setTags(String [] tags) {
            this.tags = tags;
        }
    }
}
