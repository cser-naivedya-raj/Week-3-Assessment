package java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Member implements Serializable {

    @Id
    private Integer id;

    private String name;
    private String membershipType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workout> workouts = new ArrayList<>();

    public Member() {
    }

    public Member(Integer id, String name, String membershipType) {
        this.id = id;
        this.name = name;
        this.membershipType = membershipType;
        this.workouts = new ArrayList<>();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }

    public List<Workout> getWorkouts() { return workouts; }
    public void setWorkouts(List<Workout> workouts) { this.workouts = workouts; }

    @Override
    public String toString() {
        return "Member [id=" + id + ", name=" + name + ", membershipType=" + membershipType + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, membershipType, name, workouts);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Member other = (Member) obj;
        return Objects.equals(id, other.id) && Objects.equals(membershipType, other.membershipType)
                && Objects.equals(name, other.name) && Objects.equals(workouts, other.workouts);
    }
}