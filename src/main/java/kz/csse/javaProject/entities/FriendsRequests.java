package kz.csse.javaProject.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "t_friends_requests")
@AllArgsConstructor
@NoArgsConstructor
public class FriendsRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users userFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users userTo;

    @Column(name = "added_time")
    private Timestamp added_time;

//    @JsonProperty("flight_id")
//    private void unpackNested(Long flight_id) {
//        this.userFrom = new Users();
//        userFrom.setId(flight_id);
//    }
}
