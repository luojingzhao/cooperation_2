import java.util.ArrayList;

public class Department {
	
	//���ű�ǩ
	public String mB_id;
	//����ѧ������
	public int mB_student_num;
	//�����ص��ǩ
	public ArrayList<String> mB_tag;
	//���ų���ʱ��
	public ArrayList<String> mB_normal_time;
	
	//����¼ȡ��ѧ��
	public ArrayList<Student> mB_in_students;
	//�������۷���
	public int mB_all_students_num = 0;
	public Student mB_all_students[] = new Student[305];
	
	public Department(){
		mB_tag = new ArrayList<String>();
		mB_normal_time = new ArrayList<String>();
		mB_in_students = new ArrayList<Student>();
	}
	
	
	

}
