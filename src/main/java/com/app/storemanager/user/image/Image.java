package com.app.storemanager.user.image;
import com.app.storemanager.user.baseuser.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;


import javax.persistence.*;

@Data
@Entity
public class Image  {
    @Id
    @GeneratedValue
    Long id;
    @Lob
    byte[] content;
    @OneToOne(mappedBy = "image")
    @JsonBackReference
    private User user;


}
