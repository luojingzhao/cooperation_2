import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student {
	
	//ѧ��id
	public String mS_id;
	//ѧ������ʱ���
	public ArrayList<String> mS_free_time;
//	//��־ѧ������ʱ���Ƿ��Ѿ���ռ��
//	public ArrayList<Boolean> mS_is_free_time;
	//ѧ����Ȥ��ǩ
	public ArrayList<String> mS_like_tag;
	//ѧ��־Ը����
	public ArrayList<String> mS_like_department;
	
	//ѧ������Ĳ���
	public ArrayList<Department> mS_departments;
	//����ѧ��־Ը���ŷ���
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
