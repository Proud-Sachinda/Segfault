package com.Client;
import com.Client.SignInView;

    public class Authentication {

        private String username;
        private String password;

        public Authentication() {
            setUsername("username");
            setPassword("password");
        }

        private void setUsername(String username) {
            this.username = username;
        }

        private String getUsername(){
            return this.username;
        }

        private void setPassword(String password) {
            this.password = password;
        }

        private String getPassword(){
            return this.password;
        }

        public Boolean Authenticate(String username, String password){
            if(username.equals(getUsername()) && password.equals(getPassword())){
                return true;
            }
            return false;
        }

    }

