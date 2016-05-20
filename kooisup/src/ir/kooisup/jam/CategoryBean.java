package ir.kooisup.jam;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CategoryBean {
	private List<String> categoryList;
	private DBHandler db = DBHandler.getInstance();
	
	public CategoryBean() {
		// TODO Auto-generated constructor stub
		
		if(db.findCategory("MATH") == null)
			db.insertCategory("MATH");
		if(db.findCategory("ART") == null)
			db.insertCategory("ART");
		if(db.findCategory("SCIENCE") == null)
			db.insertCategory("SCIENCE");
		if(db.findCategory("MATH2") == null)
			db.insertCategory("MATH2");
		if(db.findCategory("ART2") == null)
			db.insertCategory("ART2");
		if(db.findCategory("SCIENCE2") == null)
			db.insertCategory("SCIENCE2");
		
		
		this.categoryList = db.findCategories();
		
	}
	
	public List<String> getCategoryList(){
		return categoryList;
	}
	
	public void antiBabak(){
		System.out.println("az hamin tiribun elam mikonam ridi :|");
	}
}
