import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student {
	
	//学生id
	public String mS_id;
	//学生空闲时间段
	public ArrayList<String> mS_free_time;
//	//标志学生空闲时间是否已经被占用
//	public ArrayList<Boolean> mS_is_free_time;
	//学生兴趣标签
	public ArrayList<String> mS_like_tag;
	//学生志愿部门
	public ArrayList<String> mS_like_department;
	
	//学生参与的部门
	public ArrayList<Department> mS_departments;
	//保存学生志愿部门分数
	public Map<String, Float> mS_score;
	
	public Student(){
		mS_free_time = new ArrayList<String>();
//		mS_is_free_time = new ArrayList<Boolean>();
		mS_like_tag = new ArrayList<String>();
		mS_like_department = new ArrayList<String>(); 
		
		mS_departments = new ArrayList<Department>(); 
		mS_score = new HashMap<String, Float>();
	}
	
	

}
