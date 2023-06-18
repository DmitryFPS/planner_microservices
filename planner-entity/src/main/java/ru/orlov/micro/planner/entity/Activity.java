package ru.orlov.micro.planner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "activity", schema = "todo", catalog = "planner-todo")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "uuid", updatable = false)
    private String uuid; // создается только один раз с помощью триггера в БД

//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;

    @Column(name = "user_id")
    private String userId;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Activity activity = (Activity) o;
        return id.equals(activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}