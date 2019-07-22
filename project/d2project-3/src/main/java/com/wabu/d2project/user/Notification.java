package com.wabu.d2project.user;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class Notification {
	@Id
    private String notificationId;
    
    private String friendId;
    
    private String notificationContent;
    
    private boolean isRead;
    
    @Override
    public String toString() {
        return String.format(
                "notification[id=%s, friendId=%s, notificationContent='%s', isRead='%s']",
                notificationId, friendId, notificationContent, isRead);
    }
}
