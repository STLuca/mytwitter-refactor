package com.example.EventHandlers;

import com.example.Entities.User;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RepositoryEventHandler
public class EventHandlers {

    BCryptPasswordEncoder passwordEncoder;

    public EventHandlers(BCryptPasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @HandleBeforeCreate
    public void handleUserSave(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getProfilePic() == null || user.getProfilePic().isEmpty()){
            user.setProfilePic("https://www.qualiscare.com/wp-content/uploads/2017/08/default-user.png");
        }
    }

}
