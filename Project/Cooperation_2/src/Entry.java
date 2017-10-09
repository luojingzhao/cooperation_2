import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Entry {
	
	//学生列表
	public ArrayList<Student> mList_student;
	//部门列表
	public ArrayList<Department> mList_department;
	
	public Entry(){
		mList_student = new ArrayList<Student>();
		mList_department = new ArrayList<Department>();
	}
	
	//输入参数：无
	//返回：无
	//函数功能：解析input_data转化为mList_student和mList_department
	public void parse(){
		//获取输入数据input_data
		File file = new File("./input_data.txt");
		StringBuilder result = new StringBuilder();
	    try{
	    	BufferedReader br = new BufferedReader(new FileReader(file));
	    	String s = null;
	    	while((s = br.readLine())!=null){
	    		result.append(System.lineSeparator()+s);
	    	}
	    	br.close();    
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	    try {
	    	//解析
			JSONObject jo = new JSONObject(result.toString());
			
			//解析学生
			JSONArray jsonarray_students = jo.getJSONArray("students");
			for(int i=0;i<jsonarray_students.length();i++){
				Student student = new Student();
				JSONObject json_student = jsonarray_students.getJSONObject(i);
				//id
				student.mS_id = json_student.getString("student_no");
				
				//free time
				JSONArray jsonarray_free_time = json_student.getJSONArray("free_time");
				for(int j=0;j<jsonarray_free_time.length();j++){
					String temp_time = (String)jsonarray_free_time.get(j);
					student.mS_free_time.add(temp_time);
//					student.mS_is_free_time.add(true);
				}
				
				//like tag
				JSONArray jsonarray_like_tag = json_student.getJSONArray("tags");
				for(int j=0;j<jsonarray_like_tag.length();j++){
					String temp_tag = (String)jsonarray_like_tag.get(j);
					student.mS_like_tag.add(temp_tag);
				}
				
				//like department
				JSONArray jsonarray_like_departments = json_student.getJSONArray("applications_department");
				for(int j=0;j<jsonarray_like_departments.length();j++){
					String temp_department = (String)jsonarray_like_departments.get(j);
					student.mS_like_department.add(temp_department);
				}
				
				mList_student.add(student);	
			}
			
			//解析部门
			JSONArray jsonarray_departments = jo.getJSONArray("departments");
			for(int i=0;i<jsonarray_departments.length();i++){
				Department department = new Department();
				JSONObject json_department = jsonarray_departments.getJSONObject(i);
				
				//id
				department.mB_id = json_department.getString("department_no");
				
				//limit
				department.mB_student_num = json_department.getInt("member_limit");
				
				//normal time
				JSONArray jsonarray_normal_time = json_department.getJSONArray("event_schedules");
				for(int j=0;j<jsonarray_normal_time.length();j++){
					String temp_time = (String)jsonarray_normal_time.get(j);
					department.mB_normal_time.add(temp_time);
				}
				
				//tag
				JSONArray jsonarray_tag = json_department.getJSONArray("tags");
				for(int j=0;j<jsonarray_tag.length();j++){
					String temp_tag = (String)jsonarray_tag.get(j);
					department.mB_tag.add(temp_tag);
				}
				
				mList_department.add(department);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}
	
	//输入参数：无
	//返回：无
	//函数功能：对每个学生的志愿部门进行评分
	public void judge(){
		//进行评价
		for(int i=0;i<mList_student.size();i++){
			Student student = mList_student.get(i);
			//拿出部门
			for(int j=0;j<student.mS_like_department.size();j++){
				String temp_department_id = student.mS_like_department.get(j);
				Department department = get_department_by_id(temp_department_id);
				if(department == null){
					continue;
				}
				float score = touch_score(student, department);
				//record
				student.mS_score.put(temp_department_id, score);
				department.mB_all_students[department.mB_all_students_num] = student;
				department.mB_all_students_num ++;
			}
		}
	}
	
	//输入参数：无
	//返回：无
	//函数功能：部门从高到底录取学生
	public void enrolll(){
		//进行冒泡排序
		for(int i=0;i<mList_department.size();i++){
			Department department = mList_department.get(i);
			//单个department
			for(int j=0;j<department.mB_all_students_num;j++){
				for(int l=j;l<department.mB_all_students_num;l++){
					if(department.mB_all_students[j].mS_score.get(department.mB_id) < department.mB_all_students[l].mS_score.get(department.mB_id)){
						Student t = department.mB_all_students[j];
						department.mB_all_students[j] = department.mB_all_students[l];
						department.mB_all_students[l] = t;
					}
				}
			}
		}
		
		//录取
		for(int i=0;i<mList_department.size();i++){
			Department department = mList_department.get(i);
			int target_num = department.mB_student_num;
			int now_num = 0;
			
			//单个department
			for(int j=0;j<department.mB_all_students_num;j++){
				//数量已经够了
				if(now_num == target_num){
					break;
				}
				Student student = department.mB_all_students[j];
				//学生参加部门少于5
				if(student.mS_departments.size()<5){
					student.mS_departments.add(department);
					department.mB_in_students.add(student);
					now_num ++;
				}
				
			}
		}
	}
	
	//输入参数：无
	//返回：无
	//函数功能：输出结果到output_data
	public void display(){
		String result = "";
		
		result += "{";
		
		result += "\"unlucky_student\":[";
//		unlucky student
//		save empty student
		ArrayList<Student> array_emtpy_students = new ArrayList<Student>();
		for(int i=0;i<mList_student.size();i++){
			Student student = mList_student.get(i);
			if(student.mS_departments.size() == 0){
				array_emtpy_students.add(student);
			}
		}
		for(int i=0;i<array_emtpy_students.size();i++){
			Student s = array_emtpy_students.get(i);
			result += "\"" + s.mS_id + "\"";
			if(i != array_emtpy_students.size() -1){
				result += ",";
			}
		}
		result += "],";
		
//		admint
		result += "\"admitted\":[";
		
//		save full department
		ArrayList<Department> array_full_departments = new ArrayList<Department>();
		for(int i=0;i<mList_department.size();i++){
			Department department = mList_department.get(i);
			if(department.mB_in_students.size() != 0){
				array_full_departments.add(department);
			}
		}
		
		for(int i=0;i<array_full_departments.size();i++){
			Department department = array_full_departments.get(i);
			result += "{";
			
			result += "\"member\":[";
			for(int j=0;j<department.mB_in_students.size();j++){
				Student s = department.mB_in_students.get(j);
				if(j!=department.mB_in_students.size() - 1){
					result += "\""+ s.mS_id + "\"" + ",";
				}else{
					result += "\""+ s.mS_id + "\"";
				}
			}
			result += "],";
			
			result += "\"department_no\":" + "\""+ department.mB_id + "\"";
			
			result += "}";
			if(i != array_full_departments.size() - 1){
				result += ",";
			}
		}
		
		result += "],";
		
//		unlucky department
		result += "\"unlucky_department\":[";
//		save empty department
		ArrayList<Department> array_emtpy_departments = new ArrayList<Department>();
		for(int i=0;i<mList_department.size();i++){
			Department department = mList_department.get(i);
			if(department.mB_in_students.size() == 0){
				array_emtpy_departments.add(department);
			}
		}
		for(int i=0;i<array_emtpy_departments.size();i++){
			Department department = array_emtpy_departments.get(i);
			result += "\"" + department.mB_id + "\"";
			if(i != array_emtpy_departments.size() -1){
				result += ",";
			}
		}
		result += "]";
		
		result += "}";
		
//		System.out.println(result);
		//输出文件
		try {
			String content = result;

			File file = new File("./output_data.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public float touch_score(Student student, Department department){
//		score = 50%志愿 + 10%tag + 40%time
		float score = 0;
		
		float score_willing = 0;
		int index = 0;
		for(int i=0;i<student.mS_like_department.size();i++){
			if(student.mS_like_department.get(i) == department.mB_id){
				index = i;
				break;
			}
		}
		score_willing = (5 - index) * 10;
		
		float score_tag = 0;
		int same_tag_num = 0;
		for(int i=0;i<student.mS_like_tag.size();i++){
			for(int j=0;j<department.mB_tag.size();j++){
				if(student.mS_like_tag.get(i) == department.mB_tag.get(j)){
					same_tag_num ++;
				}
			}
		}
		score_tag = same_tag_num / department.mB_tag.size() * 10;
		
		//time
		//TODO
		
		score = score_willing + score_tag;
		
		return score;
	}
	
	public Department get_department_by_id(String id){
		for(int i=0;i<this.mList_department.size();i++){
			Department d = mList_department.get(i);
			if(d.mB_id.equals(id)){
				return d;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Entry entry = new Entry();
		entry.parse();
		entry.judge();
		entry.enrolll();
		entry.display();
	}
	

}
