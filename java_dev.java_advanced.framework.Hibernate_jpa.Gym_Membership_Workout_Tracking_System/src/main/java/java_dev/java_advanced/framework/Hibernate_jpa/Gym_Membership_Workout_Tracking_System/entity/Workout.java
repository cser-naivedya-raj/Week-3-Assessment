package java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Workout implements Serializable {

    @Id
    private Integer id;

    private String exerciseName;
    private int duration;
    private LocalDate workoutDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Workout() { }

    public Workout(Integer id, String exerciseName, int duration, LocalDate workoutDate) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.duration = duration;
        this.workoutDate = workoutDate;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public LocalDate getWorkoutDate() { return workoutDate; }
    public void setWorkoutDate(LocalDate workoutDate) { this.workoutDate = workoutDate; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    @Override
    public String toString() {
        return "Workout [id=" + id + ", exerciseName=" + exerciseName + ", duration=" + duration + ", workoutDate=" + workoutDate + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, exerciseName, id, member, workoutDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Workout other = (Workout) obj;
        return duration == other.duration && Objects.equals(exerciseName, other.exerciseName)
                && Objects.equals(id, other.id) && Objects.equals(member, other.member)
                && Objects.equals(workoutDate, other.workoutDate);
    }
}