package com.wabu.d2project.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Data
public class Notification {
	@Id
    @Column(name="notificationId")
    private String notificationId;
    
    @Column(name="friendId")
    private String friendId;
    
    @Column(name="notificationContent")
    private String notificationContent;
    
    @Column(name="isRead")
    private boolean isRead;
    
    @Override
    public String toString() {
        return String.format(
                "notification[id=%s, friendId=%s, notificationContent='%s', isRead='%s']",
                notificationId, friendId, notificationContent, isRead);
    }
}
