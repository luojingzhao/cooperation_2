import java.util.ArrayList;

public class Department {
	
	//部门标签
	public String mB_id;
	//部门学生上限
	public int mB_student_num;
	//部门特点标签
	public ArrayList<String> mB_tag;
	//部门常规活动时间
	public ArrayList<String> mB_normal_time;
	
	//部门录取的学生
	public ArrayList<Student> mB_in_students;
	//部门评价分数
	public int mB_all_students_num = 0;
	public Student mB_all_students[] = new Student[305];
	
	public Department(){
		mB_tag = new ArrayList<String>();
		mB_normal_time = new ArrayList<String>();
		mB_in_students = new ArrayList<Student>();
	}
	
	
	

}
