package java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String studentName;
    private String enrollmentDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Enrollment() {}

    public Enrollment(String studentName, String enrollmentDate, Course course) {
        this.studentName = studentName;
        this.enrollmentDate = enrollmentDate;
        this.course = course;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(String enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

	@Override
	public String toString() {
		return "Enrollment [id=" + id + ", studentName=" + studentName + ", enrollmentDate=" + enrollmentDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(course, enrollmentDate, id, studentName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enrollment other = (Enrollment) obj;
		return Objects.equals(course, other.course) && Objects.equals(enrollmentDate, other.enrollmentDate)
				&& id == other.id && Objects.equals(studentName, other.studentName);
	}



}